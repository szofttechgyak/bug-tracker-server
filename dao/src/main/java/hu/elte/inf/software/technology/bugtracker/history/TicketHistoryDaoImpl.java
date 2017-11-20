package hu.elte.inf.software.technology.bugtracker.history;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import hu.elte.inf.software.technology.bugtracker.domain.ProjectHistory;
import hu.elte.inf.software.technology.bugtracker.domain.TicketHistory;

@Repository
@Transactional
@EnableTransactionManagement
public class TicketHistoryDaoImpl extends HibernateDaoSupport implements TicketHistoryDao {

	private HibernateTemplate hibernateTemplate;
	
	public TicketHistoryDaoImpl(SessionFactory sf) {
		setSessionFactory(sf);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<TicketHistory> listTicketHistory() {
		Session session = getSessionFactory().getCurrentSession();
		List<TicketHistory> ticketHistorysList = session.createQuery("from TicketHistory").list();
		return ticketHistorysList;
	}

	@Override
	public TicketHistory getTicketHistoryById(int id) {
		Session session = getSessionFactory().getCurrentSession();		
		TicketHistory ticketHistory = (TicketHistory) session.load(TicketHistory.class, new Integer(id));
		return ticketHistory;
	}
	
	@Override
	public List<TicketHistory> getTicketHistoryByTicketId(int ticketId) {
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery("from TicketHistory where ticketId = :id");
		query.setParameter("id", ticketId);

		List<TicketHistory> ticketHistory = query.list();
		return ticketHistory;
	}

}
