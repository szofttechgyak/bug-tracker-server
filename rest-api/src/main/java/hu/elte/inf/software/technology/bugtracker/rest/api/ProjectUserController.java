package hu.elte.inf.software.technology.bugtracker.rest.api;

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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import hu.elte.inf.software.technology.bugtracker.domain.Project;
import hu.elte.inf.software.technology.bugtracker.domain.ProjectUser;
import hu.elte.inf.software.technology.bugtracker.domain.User;
import hu.elte.inf.software.technology.bugtracker.service.ProjectService;
import hu.elte.inf.software.technology.bugtracker.service.ProjectUserService;
import hu.elte.inf.software.technology.bugtracker.service.UserService;

@RestController
public class ProjectUserController {
	
	@Autowired
	private ProjectUserService projectUserService;
	
	@Autowired
    private ProjectService projectService;
    
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/api/projectUsers", method = RequestMethod.GET)
    public ResponseEntity<List<ProjectUser>> getAllProjectUsers() {
    	List<ProjectUser> projectUser = projectUserService.getAllProjectUsers();
    	if(projectUser.isEmpty()){
            return new ResponseEntity<List<ProjectUser>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<ProjectUser>>(projectUser, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/projectUser/{projectUserId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectUser> getProjectUser(@PathVariable int projectUserId) {
        ProjectUser projectUser = projectUserService.getProjectUserById(projectUserId);
        if (projectUser == null) {
            System.out.println("ProjectUser with id " + projectUserId + " not found");
            return new ResponseEntity<ProjectUser>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<ProjectUser>(projectUser, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/addProjectUser",method = RequestMethod.POST, consumes="application/json")
    public ResponseEntity<Void> addProjectUser(@RequestBody ProjectUser projectUser, UriComponentsBuilder ucBuilder) {
    	/* TODO isProjectUserExist*/
    	User user = userService.getUserById(projectUser.getUser().getId());
    	Project project = projectService.getProjectById(projectUser.getProject().getId());
    	projectUser.setUser(user);
    	projectUser.setProject(project);
    	projectUserService.addProjectUser(projectUser);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/projectUser/{projectUserid}").buildAndExpand(projectUser.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/updateProjectUser/{projectUserId}",method = RequestMethod.POST, consumes="application/json")
    public ResponseEntity<ProjectUser> updateProjectUser(@PathVariable int projectUserId, @RequestBody ProjectUser projectUser) {
    	ProjectUser currProjectUser = projectUserService.getProjectUserById(projectUserId);
    	if (currProjectUser==null) {
            System.out.println("ProjectUser with id " + projectUserId + " not found");
            return new ResponseEntity<ProjectUser>(HttpStatus.NOT_FOUND);
        }
    	User user = userService.getUserById(projectUser.getUser().getId());
    	Project project = projectService.getProjectById(projectUser.getProject().getId());
    	currProjectUser = projectUser;
    	currProjectUser.setUser(user);
    	currProjectUser.setProject(project);
    	currProjectUser.setId(projectUserId);
        projectUserService.updateProjectUser(currProjectUser);
        return new ResponseEntity<ProjectUser>(currProjectUser, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/removeProjectUser/{projectUserId}", method = RequestMethod.POST)
    public ResponseEntity<ProjectUser> removeProjectUser(@PathVariable int projectUserId) {
        ProjectUser projectUser = projectUserService.getProjectUserById(projectUserId);
        if (projectUser == null) {
            System.out.println("Unable to delete. ProjectUser with id " + projectUserId + " not found");
            return new ResponseEntity<ProjectUser>(HttpStatus.NOT_FOUND);
        }
        projectUserService.removeProjectUser(projectUserId);
        return new ResponseEntity<ProjectUser>(HttpStatus.OK);
    }

}
