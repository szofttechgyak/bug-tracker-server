package hu.elte.inf.software.technology.bugtracker.rest.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import hu.elte.inf.software.technology.bugtracker.domain.Project;
import hu.elte.inf.software.technology.bugtracker.domain.Ticket;
import hu.elte.inf.software.technology.bugtracker.domain.User;
import hu.elte.inf.software.technology.bugtracker.service.ProjectService;
import hu.elte.inf.software.technology.bugtracker.service.TicketService;
import hu.elte.inf.software.technology.bugtracker.service.UserService;

@RestController
@EnableTransactionManagement
public class TicketController {
	
	@Autowired
    private TicketService ticketService;
	
	@Autowired
    private UserService userService;
	
	@Autowired
    private ProjectService projectService;

    @RequestMapping(value = "/api/tickets", method = RequestMethod.GET)
    public ResponseEntity<List<Ticket>> getAllTickets() {
        List<Ticket> tickets = ticketService.getAllTickets();
        if(tickets.isEmpty()){
            return new ResponseEntity<List<Ticket>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Ticket>>(tickets, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/ticket/{ticketId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Ticket> getTicket(@PathVariable int ticketId) {
        Ticket ticket = ticketService.getTicketById(ticketId);
        if (ticket == null) {
            System.out.println("Ticket with id " + ticketId + " not found");
            return new ResponseEntity<Ticket>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Ticket>(ticket, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/addTicket",method = RequestMethod.POST, consumes="application/json")
    public ResponseEntity<Void> addTicket(@RequestBody Ticket ticket, UriComponentsBuilder ucBuilder) {
    	/* TODO isTicketExist
    	if (ticketService.isTicketExist(ticket)) {
            System.out.println("A Ticket with name " + ticket.getName() + " already exist");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }*/
    	User owner = userService.getUserById(ticket.getOwner().getId());
    	User reporter = userService.getUserById(ticket.getReporter().getId());
    	Project project = projectService.getProjectById(ticket.getProject().getId());
    	ticket.setOwner(owner);
    	ticket.setReporter(reporter);
    	ticket.setProject(project);    	
    	ticketService.addTicket(ticket);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/ticket/{ticketid}").buildAndExpand(ticket.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/updateTicket/{ticketId}",method = RequestMethod.POST, consumes="application/json")
    public ResponseEntity<Ticket> updateTicket(@PathVariable int ticketId, @RequestBody Ticket ticket) {
    	Ticket currTicket = ticketService.getTicketById(ticketId);
    	if (currTicket == null) {
            System.out.println("Ticket with id " + ticketId + " not found");
            return new ResponseEntity<Ticket>(HttpStatus.NOT_FOUND);
        }
    	User owner = userService.getUserById(ticket.getOwner().getId());
    	User reporter = userService.getUserById(ticket.getReporter().getId());
    	Project project = projectService.getProjectById(ticket.getProject().getId());
    	currTicket = ticket;
    	currTicket.setOwner(owner);
    	currTicket.setReporter(reporter);
    	currTicket.setProject(project);  
    	currTicket.setId(ticketId);
        ticketService.updateTicket(currTicket);
        return new ResponseEntity<Ticket>(currTicket, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/removeTicket/{ticketId}", method = RequestMethod.POST)
    public ResponseEntity<Ticket> removeTicket(@PathVariable int ticketId) {
        Ticket ticket = ticketService.getTicketById(ticketId);
        if (ticket == null) {
            System.out.println("Unable to delete. Ticket with id " + ticketId + " not found");
            return new ResponseEntity<Ticket>(HttpStatus.NOT_FOUND);
        }
        ticketService.removeTicket(ticketId);
        return new ResponseEntity<Ticket>(HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/ticketByUser/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Ticket>> getTicketByUserId(@PathVariable int userId) {
    	List<Ticket> tickets = ticketService.listTicketsByUserId(userId);
        if(tickets.isEmpty()){
            return new ResponseEntity<List<Ticket>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Ticket>>(tickets, HttpStatus.OK);
    }
}
