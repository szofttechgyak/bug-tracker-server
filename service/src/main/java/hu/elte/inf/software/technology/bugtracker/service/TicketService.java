package hu.elte.inf.software.technology.bugtracker.service;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.elte.inf.software.technology.bugtracker.domain.Status;
import hu.elte.inf.software.technology.bugtracker.domain.Ticket;
import hu.elte.inf.software.technology.bugtracker.statusdao.StatusDao;
import hu.elte.inf.software.technology.bugtracker.ticketdao.TicketDao;

@Service
public class TicketService {
    
	@Autowired
	private StatusService statusService;
	
    @Autowired
    private TicketDao ticketDao;
    
	public List<Ticket> getAllTickets() {
    	return ticketDao.listTickets();
    } 
    
    public Ticket getTicketById(int id) {
    	return ticketDao.getTicketById(id);
    }    
    
    public void addTicket(Ticket ticket){
    	ticket.setCurrentStatus(Status.NEW);
    	ticketDao.addTicket(ticket);
    	createNewStatusForTicket(ticket, Status.NEW);
    }
        
    public void updateTicket(Ticket ticket){
    	updateTicketStatus(ticket);
    	ticketDao.updateTicket(ticket);
    }
    
    private void updateTicketStatus(Ticket ticket) {
    	Status statusInDb = statusService.getCurrentStatusOfTicket(ticket.getId());
    	if (statusInDb != null) {
    		String oldStatus = statusInDb.getStatusName();
    		String newStatus = ticket.getCurrentStatus();
    		
    		if (!oldStatus.equals(newStatus)) {
    			statusInDb.setEndTime(new Date(new java.util.Date().getTime()));
    			statusService.updateStatus(statusInDb);
    			createNewStatusForTicket(ticket, newStatus);
    		}
    	}
    }
    
    private void createNewStatusForTicket(Ticket ticket, String statusName) {
    	Status status = new Status();	
    	status.setStartTime(new Date(new java.util.Date().getTime()));
    	status.setEndTime(null);
    	status.setStatusName(statusName);
    	status.setTicket(ticket);
    	status.setUser(ticket.getOwner());
    	statusService.addStatus(status);
    }
    
    public void removeTicket(int ticketId){
    	ticketDao.removeTicket(ticketId);
    }
    
    public List<Ticket> listTicketsByUserId(int userId){
    	return ticketDao.listTicketsByUserId(userId);
    }
    
    public List<Ticket> listTicketsByProjectId(int projectId) {
    	return ticketDao.listTicketsByProjectId(projectId);
    }
    
    public TicketDao getTicketDao() {
		return ticketDao;
	}

	public void setTicketDao(TicketDao ticketDao) {
		this.ticketDao = ticketDao;
	}
    
}
