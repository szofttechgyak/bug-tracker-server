package hu.elte.inf.software.technology.bugtracker.rest.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import hu.elte.inf.software.technology.bugtracker.domain.ProjectHistory;
import hu.elte.inf.software.technology.bugtracker.service.ProjectHistoryService;

@RestController
public class ProjectHistoryController {

		
	@Autowired
    private ProjectHistoryService projectHistoryService;
	
	@RequestMapping(value = "/api/projectHistory", method = RequestMethod.GET)
    public ResponseEntity<List<ProjectHistory>> getAllProjectHistory() {
    	List<ProjectHistory> projectHistory = projectHistoryService.getAllProjectHistory();
    	if(projectHistory.isEmpty()){
            return new ResponseEntity<List<ProjectHistory>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<ProjectHistory>>(projectHistory, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/projectHistory/{projectHistoryId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProjectHistory> getProjectHistory(@PathVariable int projectHistoryId) {
    	ProjectHistory projectHistory = projectHistoryService.getProjectHistorytById(projectHistoryId);
        if (projectHistory == null) {
            System.out.println("Project with id " + projectHistoryId + " not found");
            return new ResponseEntity<ProjectHistory>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<ProjectHistory>(projectHistory, HttpStatus.OK);
    }
	
}
