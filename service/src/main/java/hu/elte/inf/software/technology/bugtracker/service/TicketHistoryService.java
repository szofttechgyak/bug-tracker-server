package hu.elte.inf.software.technology.bugtracker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.elte.inf.software.technology.bugtracker.domain.ProjectHistory;
import hu.elte.inf.software.technology.bugtracker.domain.TicketHistory;
import hu.elte.inf.software.technology.bugtracker.history.TicketHistoryDao;

@Service
public class TicketHistoryService {

	@Autowired
	TicketHistoryDao ticketHistoryDao;
	
	public List<TicketHistory> getAllTicketHistory() {
    	return ticketHistoryDao.listTicketHistory();
    } 
    
    public TicketHistory getTicketHistoryById(int id) {
    	return ticketHistoryDao.getTicketHistoryById(id);
    }
    

	public TicketHistoryDao getTicketHistoryDao() {
		return ticketHistoryDao;
	}

	public void setTicketHistoryDao(TicketHistoryDao ticketHistoryDao) {
		this.ticketHistoryDao = ticketHistoryDao;
	}
	
    public List<TicketHistory> getTicketHistoryByTicketId(int ticketId) {
    	List<TicketHistory> history = ticketHistoryDao.getTicketHistoryByTicketId(ticketId); 	
    	return history;
    }
}
