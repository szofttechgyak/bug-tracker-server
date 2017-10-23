package hu.elte.inf.software.technology.bugtracker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.elte.inf.software.technology.bugtracker.commentdao.CommentDao;
import hu.elte.inf.software.technology.bugtracker.domain.Comment;

@Service
public class CommentService {

	@Autowired
	CommentDao commentDao;
	
	public List<Comment> getAllComments() {
    	return commentDao.listComments();
    } 
    
    public Comment getCommentById(int id) {
    	return commentDao.getCommentById(id);
    }
    
    public void addComment(Comment comment){
    	commentDao.addComment(comment);
    }
    
    public void updateComment(Comment comment){
    	commentDao.updateComment(comment);
    }
    
    public void removeComment(int id){
    	commentDao.removeComment(id);
    }

	public CommentDao getCommentDao() {
		return commentDao;
	}

	public void setCommentDao(CommentDao commentDao) {
		this.commentDao = commentDao;
	}
}
