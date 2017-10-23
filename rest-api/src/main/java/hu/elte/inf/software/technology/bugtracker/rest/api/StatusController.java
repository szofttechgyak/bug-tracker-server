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

import hu.elte.inf.software.technology.bugtracker.domain.Status;
import hu.elte.inf.software.technology.bugtracker.domain.Ticket;
import hu.elte.inf.software.technology.bugtracker.domain.User;
import hu.elte.inf.software.technology.bugtracker.service.StatusService;
import hu.elte.inf.software.technology.bugtracker.service.TicketService;
import hu.elte.inf.software.technology.bugtracker.service.UserService;

@RestController
public class StatusController {

	@Autowired
	private StatusService statusService;
	
	@Autowired
    private TicketService ticketService;
    
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/api/statuses", method = RequestMethod.GET)
    public ResponseEntity<List<Status>> getAllStatuses() {
    	List<Status> status = statusService.getAllStatuses();
    	if(status.isEmpty()){
            return new ResponseEntity<List<Status>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Status>>(status, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/status/{statusId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Status> getStatus(@PathVariable int statusId) {
    	Status status = statusService.getStatusById(statusId);
        if (status == null) {
            System.out.println("Status with id " + status + " not found");
            return new ResponseEntity<Status>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Status>(status, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/addStatus",method = RequestMethod.POST, consumes="application/json")
    public ResponseEntity<Void> addStatus(@RequestBody Status status, UriComponentsBuilder ucBuilder) {
    	User user = userService.getUserById(status.getUser().getId());
    	Ticket ticket = ticketService.getTicketById(status.getTicket().getId());
    	status.setUser(user);
    	status.setTicket(ticket);
    	statusService.addStatus(status);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/status/{statusId}").buildAndExpand(status.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/updateStatus/{statusId}",method = RequestMethod.POST, consumes="application/json")
    public ResponseEntity<Status> updateStatus(@PathVariable int statusId, @RequestBody Status status) {
    	Status currStatus = statusService.getStatusById(statusId);
    	if (currStatus==null) {
            System.out.println("Status with id " + statusId + " not found");
            return new ResponseEntity<Status>(HttpStatus.NOT_FOUND);
        }
    	User user = userService.getUserById(status.getUser().getId());
    	Ticket ticket = ticketService.getTicketById(status.getTicket().getId());
    	currStatus = status;
    	currStatus.setUser(user);
    	currStatus.setTicket(ticket);
    	currStatus.setId(statusId);
        statusService.updateStatus(currStatus);
        return new ResponseEntity<Status>(currStatus, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/removeStatus/{statusId}", method = RequestMethod.POST)
    public ResponseEntity<Status> removeStatus(@PathVariable int statusId) {
    	Status status = statusService.getStatusById(statusId);
        if (status == null) {
            System.out.println("Unable to delete. Status with id " + statusId + " not found");
            return new ResponseEntity<Status>(HttpStatus.NOT_FOUND);
        }
        statusService.removeStatus(statusId);
        return new ResponseEntity<Status>(HttpStatus.OK);
    }

    

}
