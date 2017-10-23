package hu.elte.inf.software.technology.bugtracker.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Ticket")
@Proxy(lazy = false)
public class Ticket implements Serializable{

    private int id;
	private String ticketName;
    private String ticketType;
    private User owner;
    private User reporter;
    private Project project;
    private String priority;
    private int spentTime;
    private String ticketDescription;
    private Set<Status> status;
    private Set<Comment> comment;
    
	public Ticket(){
    }
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticketId")
    public int getId() {
        return id;
    }
	
	@ManyToOne(fetch = FetchType.LAZY) //(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "projectId" ) //, nullable = false)
    public Project getProject() {
		return project;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ownerId") 		//, nullable = false)
	public User getOwner() {
		return owner;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "reporterId")	//, nullable = false)
	public User getReporter() {
		return reporter;
	}
	
	public void setProject(Project project) {
		this.project = project;
	}
	
	@JsonIgnore
	@OneToMany(mappedBy = "ticket")
	public Set<Status> getStatus() {
		return status;
	}

	public void setStatus(Set<Status> status) {
		this.status = status;
	}
	
	
	@JsonIgnore
	@OneToMany(mappedBy = "ticket")
	public Set<Comment> getComment() {
		return comment;
	}

	public void setComment(Set<Comment> comment) {
		this.comment = comment;
	}

    
    public String getTicketName() {
		return ticketName;
	}

	public void setTicketName(String ticketName) {
		this.ticketName = ticketName;
	}

	public String getTicketType() {
		return ticketType;
	}

	public void setTicketType(String ticketType) {
		this.ticketType = ticketType;
	}

	public String getTicketDescription() {
		return ticketDescription;
	}

	public void setTicketDescription(String ticketDescription) {
		this.ticketDescription = ticketDescription;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setOwner(User ownerId) {
		this.owner = ownerId;
	}
	
	public void setReporter(User reporterId) {
		this.reporter = reporterId;
	}
	
	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public int getSpentTime() {
		return spentTime;
	}

	public void setSpentTime(int spentTime) {
		this.spentTime = spentTime;
	}

}
