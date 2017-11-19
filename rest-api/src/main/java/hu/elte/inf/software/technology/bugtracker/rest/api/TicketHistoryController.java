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

import hu.elte.inf.software.technology.bugtracker.domain.TicketHistory;
import hu.elte.inf.software.technology.bugtracker.service.TicketHistoryService;

@RestController
public class TicketHistoryController {


	@Autowired
    private TicketHistoryService ticketHistoryService;
	
	@RequestMapping(value = "/api/ticketHistory", method = RequestMethod.GET)
    public ResponseEntity<List<TicketHistory>> getAllTicketHistory() {
    	List<TicketHistory> ticketHistory = ticketHistoryService.getAllTicketHistory();
    	if(ticketHistory.isEmpty()){
            return new ResponseEntity<List<TicketHistory>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<TicketHistory>>(ticketHistory, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/ticketHistory/{ticketHistoryId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TicketHistory> getTicketHistory(@PathVariable int ticketHistoryId) {
    	TicketHistory ticketHistory = ticketHistoryService.getTicketHistoryById(ticketHistoryId);
        if (ticketHistory == null) {
            System.out.println("Ticket with id " + ticketHistoryId + " not found");
            return new ResponseEntity<TicketHistory>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<TicketHistory>(ticketHistory, HttpStatus.OK);
    }
}
