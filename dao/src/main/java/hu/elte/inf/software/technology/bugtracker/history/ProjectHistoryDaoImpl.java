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
import hu.elte.inf.software.technology.bugtracker.domain.Ticket;
import hu.elte.inf.software.technology.bugtracker.domain.TicketHistory;

@Repository
@Transactional
@EnableTransactionManagement
public class ProjectHistoryDaoImpl extends HibernateDaoSupport implements ProjectHistoryDao {

	private HibernateTemplate hibernateTemplate;
	
	public ProjectHistoryDaoImpl(SessionFactory sf) {
		setSessionFactory(sf);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectHistory> listProjectHistory() {
		Session session = getSessionFactory().getCurrentSession();
		List<ProjectHistory> projectHistorysList = session.createQuery("from ProjectHistory").list();
		return projectHistorysList;
	}

	@Override
	public ProjectHistory getProjectHistoryById(int id) {
		Session session = getSessionFactory().getCurrentSession();		
		ProjectHistory projectHistory = (ProjectHistory) session.load(ProjectHistory.class, new Integer(id));
		return projectHistory;
	}
	
	@Override
	public List<ProjectHistory> getProjectHistoryByProjectId(int projectId) {	
		Session session = getSessionFactory().getCurrentSession();
		Query query = session.createQuery("from ProjectHistory where projectId = :id");
		query.setParameter("id", projectId);

		List<ProjectHistory> projectHistory = query.list();
		return projectHistory;
	}

}
