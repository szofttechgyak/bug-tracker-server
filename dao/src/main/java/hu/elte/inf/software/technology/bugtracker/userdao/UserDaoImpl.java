package hu.elte.inf.software.technology.bugtracker.userdao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import hu.elte.inf.software.technology.bugtracker.domain.User;

@Repository
@Transactional
@EnableTransactionManagement
public class UserDaoImpl extends HibernateDaoSupport implements UserDao {

    private HibernateTemplate hibernateTemplate;

    public UserDaoImpl(SessionFactory sf) {
        setSessionFactory(sf);
    }

    @Override
    public void addUser(User user) {
        Session session = getSessionFactory().getCurrentSession();
        session.persist(user);

    }

    @Override
    public void updateUser(User user) {
        Session session = getSessionFactory().getCurrentSession();
        session.update(user);

    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        Session session = getSessionFactory().getCurrentSession();
        List<User> usersList = session.createQuery("from User").list();
        return usersList;
    }

    @Override
    public User getUserById(int id) {
        Session session = getSessionFactory().getCurrentSession();
        User user = (User) session.load(User.class, new Integer(id));
        return user;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public User getUserByUserName(String name) {
        Session session = getSessionFactory().getCurrentSession();
        Query query = session.createQuery("from User where userName = :name");
        query.setParameter("name", name);
        List<User> users = query.list();
        return users.isEmpty() ? null : users.iterator().next();
    }

    @Override
    public void removeUser(int id) {
        Session session = getSessionFactory().getCurrentSession();
        User user = (User) session.load(User.class, new Integer(id));
        if (null != user) {
            session.delete(user);
        }

    }

}
