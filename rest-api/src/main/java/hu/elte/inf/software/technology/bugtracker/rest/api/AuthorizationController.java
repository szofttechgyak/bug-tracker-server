package hu.elte.inf.software.technology.bugtracker.rest.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hu.elte.inf.software.technology.bugtracker.domain.Rule;
import hu.elte.inf.software.technology.bugtracker.service.AuthorizationService;

@RestController
public class AuthorizationController {

	@Autowired
	private AuthorizationService authorizationService;
	    
    @RequestMapping(value = "/api/getAllowedChanges", method = RequestMethod.GET)
    public ResponseEntity<List<Rule>> getAllowedChanges(@RequestParam("userId") int userId, @RequestParam("ticketId") int ticketId) {
    	List<Rule> allowedChanges = authorizationService.getAllowedChanges(userId, ticketId);
    	if (allowedChanges == null || allowedChanges.isEmpty()) {
            return new ResponseEntity<List<Rule>>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<List<Rule>>(allowedChanges, HttpStatus.OK);
    }
	
}
