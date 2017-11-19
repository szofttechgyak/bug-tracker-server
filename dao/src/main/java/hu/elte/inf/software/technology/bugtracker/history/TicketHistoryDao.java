package hu.elte.inf.software.technology.bugtracker.history;

import java.util.List;

import hu.elte.inf.software.technology.bugtracker.domain.TicketHistory;

public interface TicketHistoryDao {

	public List<TicketHistory> listTicketHistory();
	public TicketHistory getTicketHistoryById(int id);
	
}

