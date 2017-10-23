package hu.elte.inf.software.technology.bugtracker.ticketdao;

import java.util.List;

import hu.elte.inf.software.technology.bugtracker.domain.Ticket;

public interface TicketDao {
	public void addTicket(Ticket ticket);
	public void updateTicket(Ticket ticket);
	public List<Ticket> listTickets();
	public List<Ticket> listTicketsByUserId(int id);
	public Ticket getTicketById(int id);
	public void removeTicket(int id);
}
