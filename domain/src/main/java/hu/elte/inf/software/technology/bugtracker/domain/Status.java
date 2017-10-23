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
@Table(name = "Status")
@Proxy(lazy = false)
public class Status implements Serializable {
	
	private int id;
	private User user;
	private Ticket ticket;
	private String 	description;
	private Date startTime;
	private Date endTime;
	private String statusName;
	
	public Status(){
	}
	
	@Id
	@Column(name = "statusId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userId" )
	public User getUser() {
		return user;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ticketId" )
	public Ticket getTicket() {
		return ticket;
	}
	
	public void setUser(User user) {
		this.user = user;
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
	
	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	
	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public void setId(int id) {
		this.id = id;
	}

}
