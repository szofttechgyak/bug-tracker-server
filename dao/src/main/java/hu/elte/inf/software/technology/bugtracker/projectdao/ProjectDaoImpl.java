package hu.elte.inf.software.technology.bugtracker.projectdao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import hu.elte.inf.software.technology.bugtracker.domain.Project;

@Repository
@Transactional
@EnableTransactionManagement
public class ProjectDaoImpl extends HibernateDaoSupport implements ProjectDao {

	private HibernateTemplate hibernateTemplate;
	
	public ProjectDaoImpl(SessionFactory sf) {
		setSessionFactory(sf);
	}
	
	@Override
	public void addProject(Project project) {
		Session session = getSessionFactory().getCurrentSession();
		session.persist(project);
		
	}

	@Override
	public void updateProject(Project project) {
		Session session = getSessionFactory().getCurrentSession();
		session.update(project);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Project> listProjects() {
		Session session = getSessionFactory().getCurrentSession();
		List<Project> projectsList = session.createQuery("from Project").list();
		return projectsList;
	}

	@Override
	public Project getProjectById(int id) {
		Session session = getSessionFactory().getCurrentSession();		
		Project project = (Project) session.load(Project.class, new Integer(id));
		return project;
	}

	@Override
	public void removeProject(int id) {
		Session session = getSessionFactory().getCurrentSession();
		Project project = (Project) session.load(Project.class, new Integer(id));
		if(null != project){
			session.delete(project);
		}
		
	}

}
