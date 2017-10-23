package hu.elte.inf.software.technology.bugtracker.commentdao;

import java.util.List;

import hu.elte.inf.software.technology.bugtracker.domain.Comment;

public interface CommentDao {
	public void addComment(Comment comment);
	public void updateComment(Comment comment);
	public List<Comment> listComments();
	public Comment getCommentById(int id);
	public void removeComment(int id);
}
