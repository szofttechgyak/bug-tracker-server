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

import hu.elte.inf.software.technology.bugtracker.domain.Comment;
import hu.elte.inf.software.technology.bugtracker.domain.Ticket;
import hu.elte.inf.software.technology.bugtracker.domain.User;
import hu.elte.inf.software.technology.bugtracker.service.CommentService;
import hu.elte.inf.software.technology.bugtracker.service.TicketService;
import hu.elte.inf.software.technology.bugtracker.service.UserService;

@RestController
public class CommentController {

	@Autowired
	private CommentService commentService;
	
	@Autowired
    private TicketService ticketService;
    
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/api/comments", method = RequestMethod.GET)
    public ResponseEntity<List<Comment>> getAllComment() {
    	List<Comment> comment = commentService.getAllComments();
    	if(comment.isEmpty()){
            return new ResponseEntity<List<Comment>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Comment>>(comment, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/comment/{commentId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Comment> getComment(@PathVariable int commentId) {
    	Comment comment = commentService.getCommentById(commentId);
        if (comment == null) {
            System.out.println("Comment with id " + comment + " not found");
            return new ResponseEntity<Comment>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Comment>(comment, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/addComment",method = RequestMethod.POST, consumes="application/json")
    public ResponseEntity<Void> addComment(@RequestBody Comment comment, UriComponentsBuilder ucBuilder) {
    	User user = userService.getUserById(comment.getOwner().getId());
    	Ticket ticket = ticketService.getTicketById(comment.getTicket().getId());
    	comment.setOwner(user);
    	comment.setTicket(ticket);
    	commentService.addComment(comment);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/comment/{commentId}").buildAndExpand(comment.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/updateComment/{commentId}",method = RequestMethod.POST, consumes="application/json")
    public ResponseEntity<Comment> updateComment(@PathVariable int commentId, @RequestBody Comment comment) {
    	Comment currComment = commentService.getCommentById(commentId);
    	if (currComment==null) {
            System.out.println("Comment with id " + commentId + " not found");
            return new ResponseEntity<Comment>(HttpStatus.NOT_FOUND);
        }
    	User user = userService.getUserById(comment.getOwner().getId());
    	Ticket ticket = ticketService.getTicketById(comment.getTicket().getId());
    	currComment = comment;
    	currComment.setOwner(user);
    	currComment.setTicket(ticket);
    	currComment.setId(commentId);
        commentService.updateComment(currComment);
        return new ResponseEntity<Comment>(currComment, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/api/removeComment/{commentId}", method = RequestMethod.POST)
    public ResponseEntity<Comment> removeComment(@PathVariable int commentId) {
    	Comment comment = commentService.getCommentById(commentId);
        if (comment == null) {
            System.out.println("Unable to delete. Comment with id " + commentId + " not found");
            return new ResponseEntity<Comment>(HttpStatus.NOT_FOUND);
        }
        commentService.removeComment(commentId);
        return new ResponseEntity<Comment>(HttpStatus.OK);
    }

    
}
