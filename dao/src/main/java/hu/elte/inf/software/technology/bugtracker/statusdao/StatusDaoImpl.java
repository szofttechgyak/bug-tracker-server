package hu.elte.inf.software.technology.bugtracker.statusdao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import hu.elte.inf.software.technology.bugtracker.domain.Status;
import hu.elte.inf.software.technology.bugtracker.domain.Ticket;

@Repository
@Transactional
@EnableTransactionManagement
public class StatusDaoImpl extends HibernateDaoSupport implements StatusDao {

	private HibernateTemplate hibernateTemplate;
	
	public StatusDaoImpl(SessionFactory sf) {
		setSessionFactory(sf);
	}

	@Override
	public void addStatus(Status status) {
		Session session = getSessionFactory().getCurrentSession();
		session.persist(status);
	}

	@Override
	public void updateStatus(Status status) {
		Session session = getSessionFactory().getCurrentSession();
		session.update(status);	
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Status> listStatuses() {
		Session session = getSessionFactory().getCurrentSession();
		List<Status> statusList = session.createQuery("from Status").list();
		return statusList;
	}

	@Override
	public Status getStatusById(int id) {
		Session session = getSessionFactory().getCurrentSession();		
		Status status = (Status) session.load(Status.class, new Integer(id));
		return status;
	}
	
	@Override
	public Status getCurrentStatusOfTicket(int ticketId) {
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery("from Status where ticketId = :id and endTime is null");
		query.setParameter("id", ticketId);
		List<Status> status = query.list();
		return status.isEmpty() ? null : status.get(0);
	}

	@Override
	public void removeStatus(int id) {
		Session session = getSessionFactory().getCurrentSession();
		Status status = (Status) session.load(Status.class, new Integer(id));
		if(null != status){
			session.delete(status);
		}
	}

}
