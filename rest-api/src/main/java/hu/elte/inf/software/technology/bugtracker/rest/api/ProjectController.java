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
import hu.elte.inf.software.technology.bugtracker.domain.User;
import hu.elte.inf.software.technology.bugtracker.service.ProjectService;
import hu.elte.inf.software.technology.bugtracker.service.ProjectUserService;
import hu.elte.inf.software.technology.bugtracker.service.UserService;

@RestController
public class ProjectController {

    @Autowired
    private ProjectService projectService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private ProjectUserService projectUserService;

    @RequestMapping(value = "/api/projects", method = RequestMethod.GET)
    public ResponseEntity<List<Project>> getAllProjects() {
    	List<Project> project = projectService.getAllProjects();
    	if(project.isEmpty()){
            return new ResponseEntity<List<Project>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Project>>(project, HttpStatus.OK);
    }

    @RequestMapping(value = "/api/project/{projectId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Project> getProject(@PathVariable int projectId) {
        Project project = projectService.getProjectById(projectId);
        if (project == null) {
            System.out.println("Project with id " + projectId + " not found");
            return new ResponseEntity<Project>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Project>(project, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/addProject",method = RequestMethod.POST, consumes="application/json")
    public ResponseEntity<Void> addProject(@RequestBody Project project, UriComponentsBuilder ucBuilder) {
    	/* TODO isProjectExist
    	if (projectService.isProjectExist(project)) {
            System.out.println("A Project with name " + project.getName() + " already exist");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }*/
    	User defaultApprover = userService.getUserById(project.getDefaultApprover().getId());
    	User defaultDeveloper = userService.getUserById(project.getDefaultDeveloper().getId());
    	project.setDefaultApprover(defaultApprover);
    	project.setDefaultDeveloper(defaultDeveloper);
    	projectService.addProject(project);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/project/{projectid}").buildAndExpand(project.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/updateProject/{projectId}",method = RequestMethod.POST, consumes="application/json")
    public ResponseEntity<Project> updateProject(@PathVariable int projectId, @RequestBody Project project) {
    	Project currProject = projectService.getProjectById(projectId);
    	if (currProject==null) {
            System.out.println("Project with id " + projectId + " not found");
            return new ResponseEntity<Project>(HttpStatus.NOT_FOUND);
        }
    	User defaultApprover = userService.getUserById(project.getDefaultApprover().getId());
    	User defaultDeveloper = userService.getUserById(project.getDefaultDeveloper().getId());
    	currProject = project;
    	currProject.setDefaultApprover(defaultApprover);
    	currProject.setDefaultDeveloper(defaultDeveloper);
    	currProject.setId(projectId);
        projectService.updateProject(currProject);
        return new ResponseEntity<Project>(currProject, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/removeProject/{projectId}", method = RequestMethod.POST)
    public ResponseEntity<Project> removeProject(@PathVariable int projectId) {
        Project project = projectService.getProjectById(projectId);
        if (project == null) {
            System.out.println("Unable to delete. Project with id " + projectId + " not found");
            return new ResponseEntity<Project>(HttpStatus.NOT_FOUND);
        }
        projectService.removeProject(projectId);
        return new ResponseEntity<Project>(HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/assignedUsers/{projectId}", method = RequestMethod.GET)
    public List<User> getAssignedUsers(@PathVariable int projectId) {
        return projectUserService.getAssignedUsers(projectId);
    }

}
