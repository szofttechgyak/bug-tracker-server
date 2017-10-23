package hu.elte.inf.software.technology.bugtracker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.elte.inf.software.technology.bugtracker.domain.Ticket;
import hu.elte.inf.software.technology.bugtracker.ticketdao.TicketDao;

@Service
public class TicketService {
    
    @Autowired
    private TicketDao ticketDao;

	public List<Ticket> getAllTickets() {
    	return ticketDao.listTickets();
    } 
    
    public Ticket getTicketById(int id) {
    	return ticketDao.getTicketById(id);
    }    
    
    public void addTicket(Ticket ticket){
    	ticketDao.addTicket(ticket);
    }
    
    public void updateTicket(Ticket ticket){
    	ticketDao.updateTicket(ticket);
    }
    
    public void removeTicket(int ticketId){
    	ticketDao.removeTicket(ticketId);
    }
    
    public List<Ticket> listTicketsByUserId(int userId){
    	return ticketDao.listTicketsByUserId(userId);
    }
    
    public TicketDao getTicketDao() {
		return ticketDao;
	}

	public void setTicketDao(TicketDao ticketDao) {
		this.ticketDao = ticketDao;
	}
    
}
