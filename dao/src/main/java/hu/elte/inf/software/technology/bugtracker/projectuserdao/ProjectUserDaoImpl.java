package hu.elte.inf.software.technology.bugtracker.projectuserdao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import hu.elte.inf.software.technology.bugtracker.domain.ProjectUser;
import hu.elte.inf.software.technology.bugtracker.domain.Ticket;

@Repository
@Transactional
@EnableTransactionManagement
public class ProjectUserDaoImpl extends HibernateDaoSupport implements ProjectUserDao {

	private HibernateTemplate hibernateTemplate;
	
	public ProjectUserDaoImpl(SessionFactory sf) {
		setSessionFactory(sf);
	}
	
	@Override
	public void addProjectUser(ProjectUser projectUser) {
		Session session = getSessionFactory().getCurrentSession();
		session.persist(projectUser);
	}

	@Override
	public void updateProjectUser(ProjectUser projectUser) {
		Session session = getSessionFactory().getCurrentSession();
		session.update(projectUser);		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProjectUser> listProjectUsers() {
		Session session = getSessionFactory().getCurrentSession();
		List<ProjectUser> projectUsersList = session.createQuery("from ProjectUser").list();
		return projectUsersList;
	}

	@Override
	public ProjectUser getProjectUserById(int id) {
		Session session = getSessionFactory().getCurrentSession();		
		ProjectUser projectUser = (ProjectUser) session.load(ProjectUser.class, new Integer(id));
		return projectUser;
	}

	@Override
	public void removeProjectUser(int id) {
		Session session = getSessionFactory().getCurrentSession();
		ProjectUser projectUser = (ProjectUser) session.load(ProjectUser.class, new Integer(id));
		if(null != projectUser){
			session.delete(projectUser);
		}
		
	}

}
