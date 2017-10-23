package hu.elte.inf.software.technology.bugtracker.domain;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

@Entity
@Table(name = "Comment")
@Proxy(lazy = false)
public class Comment implements Serializable {
	
	private int id;
	private User owner;
	private Ticket ticket;
	private String 	description;
	private Date date;
	
	public Comment(){
	}
	
	@Id
	@Column(name = "commentId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId" )
	public User getOwner() {
		return owner;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ticketId" )
	public Ticket getTicket() {
		return ticket;
	}
	
	public void setOwner(User owner) {
		this.owner = owner;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setId(int id) {
		this.id = id;
	}

}
