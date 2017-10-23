package hu.elte.inf.software.technology.bugtracker.rest.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import hu.elte.inf.software.technology.bugtracker.domain.Project;
import hu.elte.inf.software.technology.bugtracker.domain.User;
import hu.elte.inf.software.technology.bugtracker.service.ProjectService;
import hu.elte.inf.software.technology.bugtracker.service.ProjectUserService;
import hu.elte.inf.software.technology.bugtracker.service.UserService;

@RestController
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private ProjectService projectService;
    
    @Autowired
    private ProjectUserService projectUserService;

    @RequestMapping(value = "/api/users", method = RequestMethod.GET)
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/user/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUser(@PathVariable int userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            System.out.println("User with id " + userId + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/userByUserName/{userName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUserByUserName(@PathVariable String userName) {
        User user = userService.getUserByUserName(userName);
        if (user == null) {
            System.out.println("User with id " + userName + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/addUser", method = RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Void> addUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {

        /*
         * TODO isUserExist if (userService.isUserExist(user)) { System.out.println("A User with name " + user.getName() + " already exist"); return new
         * ResponseEntity<Void>(HttpStatus.CONFLICT); }
         */

        userService.addUser(user);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/user/{userid}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }
    
    @RequestMapping(value = "/api/assignedProjects/{userId}", method = RequestMethod.GET)
    public List<Project> getAssignedProjects(@PathVariable int userId) {
        return projectUserService.getAssignedProjects(userId);
    }

    @RequestMapping(value = "/api/updateUser/{userId}", method = RequestMethod.POST)
    public ResponseEntity<User> updateUser(@PathVariable int userId, @RequestBody User user) {
        User currUser = userService.getUserById(userId);
        if (currUser == null) {
            System.out.println("User with id " + userId + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        currUser.setUserName(user.getUserName());
        currUser.setEmailAddress(user.getEmailAddress());
        currUser.setPassword(user.getPassword());
        userService.updateUser(currUser);
        return new ResponseEntity<User>(currUser, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/removeUser/{userId}", method = RequestMethod.POST)
    public ResponseEntity<User> removeUser(@PathVariable int userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            System.out.println("Unable to delete. User with id " + userId + " not found");
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        userService.removeUser(userId);
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }
}
