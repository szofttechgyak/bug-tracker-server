package hu.elte.inf.software.technology.bugtracker.userdao;

import java.util.List;

import hu.elte.inf.software.technology.bugtracker.domain.User;

public interface UserDao {

    public void addUser(User user);

    public void updateUser(User user);

    public List<User> listUsers();

    public User getUserById(int id);
    
    public User getUserByUserName(String userName);

    public void removeUser(int id);

}
