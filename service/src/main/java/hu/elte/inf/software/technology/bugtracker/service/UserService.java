package hu.elte.inf.software.technology.bugtracker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.elte.inf.software.technology.bugtracker.domain.User;
import hu.elte.inf.software.technology.bugtracker.userdao.UserDao;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public List<User> getAllUsers() {
        return userDao.listUsers();
    }

    public User getUserById(int id) {
        return userDao.getUserById(id);
    }
    
    public User getUserByUserName(String userName) {
        return userDao.getUserByUserName(userName);
    }

    public void addUser(User user) {
        userDao.addUser(user);
    }

    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    public void removeUser(int id) {
        userDao.removeUser(id);
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

}
