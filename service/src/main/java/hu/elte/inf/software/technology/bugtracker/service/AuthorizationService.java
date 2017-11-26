package hu.elte.inf.software.technology.bugtracker.service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.google.common.io.CharStreams;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import hu.elte.inf.software.technology.bugtracker.domain.ProjectUser;
import hu.elte.inf.software.technology.bugtracker.domain.Rule;
import hu.elte.inf.software.technology.bugtracker.domain.Ticket;
import hu.elte.inf.software.technology.bugtracker.domain.User;

@Service
public class AuthorizationService {
		
	@Autowired
	private UserService userService;
	
	@Autowired
	private TicketService ticketService;
	
	@Autowired
	private ProjectUserService projectUserService;
	
	@Autowired
	private StatusService statusService;
	
	private List<Rule> rules;
	
	public AuthorizationService() throws UnsupportedEncodingException, IOException {
        try(Reader reader = new InputStreamReader(AuthorizationService.class.getResourceAsStream("/rules.json"), "UTF-8")) {  
        	String rulesArrayJson = CharStreams.toString(reader);
            Type listType = new TypeToken<ArrayList<Rule>>(){}.getType();
            rules = new Gson().fromJson(rulesArrayJson, listType);
        }
    }
		
	public List<Rule> getAllowedChanges(int userId, int ticketId) {
		User user = userService.getUserById(userId);
		Ticket ticket = ticketService.getTicketById(ticketId);

		String role = getUsersRoleOnProject(ticket.getProject().getId(), userId);
		String currentTicketStatus = statusService.getCurrentStatusOfTicket(ticket.getId()).getStatusName();
		String ticketType = ticket.getTicketType();
		
		return getAllowedChanges(role, currentTicketStatus, ticketType);
	}
	
	private String getUsersRoleOnProject(int projectId, int userId) {
		Optional<ProjectUser> projectUser = projectUserService.getAssignedProjectUsers(projectId)
				.stream()
				.filter(u -> u.getUser().getId() == userId)
				.findFirst();
		
		if (projectUser.isPresent()) {
			return projectUser.get().getRole();
		}
		
		return null;
	}
		
	private List<Rule> getAllowedChanges(String role, String currentTicketStatus, String ticketType) {
		return rules.stream()
			.filter(r -> r.getRole().equals(role) && r.getOldStatus().equals(currentTicketStatus) && r.getTicketType().equals(ticketType))
			.collect(Collectors.toList());
	}	
	
	public boolean AuthorizeTicketChange(Ticket updatedTicket) {	
		Ticket oldTicket = ticketService.getTicketById(updatedTicket.getId());
		int projectId = updatedTicket.getProject().getId();
		
		String oldStatus = oldTicket.getCurrentStatus();
		String newStatus = updatedTicket.getCurrentStatus();
		
		if (oldStatus.equals(newStatus)) {
			return true;
		}
			
		String loggedInUsersRole = getUsersRoleOnProject(projectId, getLoggedInUsersId());
		String assigneeRole = getUsersRoleOnProject(projectId, updatedTicket.getOwner().getId());
		
		for(Rule rule : rules) {	
			if (rule.getTicketType().equals(updatedTicket.getTicketType()) && rule.getRole().equals(loggedInUsersRole)) {		
				if (rule.getOldStatus().equals(oldStatus) && rule.getNewStatus().equals(newStatus)) {
					if (rule.getAssigneeRole().equals(assigneeRole)) {
						return true;
					}
				}		
			}
			
		}
		
		return false;
	}
	
	private int getLoggedInUsersId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();
		return userService.getUserByUserName(userName).getId();
	}
}