package hu.elte.inf.software.technology.bugtracker.commentdao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import hu.elte.inf.software.technology.bugtracker.domain.Comment;

@Repository
@Transactional
@EnableTransactionManagement
public class CommentDaoImpl  extends HibernateDaoSupport implements CommentDao {

	private HibernateTemplate hibernateTemplate;
	
	public CommentDaoImpl(SessionFactory sf) {
		setSessionFactory(sf);
	}

	@Override
	public void addComment(Comment comment) {
		Session session = getSessionFactory().getCurrentSession();
		session.persist(comment);
	}

	@Override
	public void updateComment(Comment comment) {
		Session session = getSessionFactory().getCurrentSession();
		session.update(comment);	
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Comment> listComments() {
		Session session = getSessionFactory().getCurrentSession();
		List<Comment> commentList = session.createQuery("from Comment").list();
		return commentList;
	}

	@Override
	public Comment getCommentById(int id) {
		Session session = getSessionFactory().getCurrentSession();		
		Comment comment = (Comment) session.load(Comment.class, new Integer(id));
		return comment;
	}

	@Override
	public void removeComment(int id) {
		Session session = getSessionFactory().getCurrentSession();
		Comment comment = (Comment) session.load(Comment.class, new Integer(id));
		if(null != comment){
			session.delete(comment);
		}
	}

	
}
