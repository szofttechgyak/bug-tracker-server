package hu.elte.inf.software.technology.bugtracker.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


import org.hibernate.annotations.Proxy;

@Entity
@Table(name = "User")
@Proxy(lazy = false)
public class User implements Serializable{
	
	private int id;
	private String userName;
	private String emailAddress;
	private String password;
	private Set<Ticket> ownedTickets;
	private Set<Ticket> reportedTickets;
	private Set<Project> approvedProjects;
	private Set<Project> developedProjects;
	private Set<ProjectUser> projectUser;
	private Set<Status> status;	
	private Set<Comment> writtenComments;
	private boolean admin;
	
	public User(){
	}

	@Id
	@Column(name = "userId")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getId() {
		return id;
	}
	
	@JsonIgnore
	@OneToMany(mappedBy = "owner" ) 		//,fetch = FetchType.EAGER)
	public Set<Ticket> getOwnedTickets() {
		return ownedTickets;
	}
	
	@JsonIgnore
	@OneToMany(mappedBy = "reporter") 		//, fetch = FetchType.EAGER)
	public Set<Ticket> getReportedTickets() {
		return reportedTickets;
	}
	
	public void setReportedTickets(Set<Ticket> reportedTickets) {
		this.reportedTickets = reportedTickets;
	}
	
	
	@JsonIgnore
	@OneToMany(mappedBy = "owner") 		//, fetch = FetchType.EAGER)
	public Set<Comment> getWrittenComments() {
		return writtenComments;
	}
	
	public void setWrittenComments(Set<Comment> writtenComments) {
		this.writtenComments = writtenComments;
	}

	
	

	@JsonIgnore
	@OneToMany(mappedBy = "defaultApprover") //, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	public Set<Project> getApprovedProjects() {
		return approvedProjects;
	}
	
	@JsonIgnore
	@OneToMany(mappedBy = "defaultDeveloper") //, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	public Set<Project> getDevelopedProjects() {
		return developedProjects;
	}
	
	@JsonIgnore
	@OneToMany(mappedBy = "user") //,  fetch = FetchType.EAGER)
	public Set<ProjectUser> getProjectUser() {
		return projectUser;
	}

	public void setProjectUser(Set<ProjectUser> projectUser) {
		this.projectUser = projectUser;
	}
	
	@JsonIgnore
	@OneToMany(mappedBy = "user")
	public Set<Status> getStatus() {
		return status;
	}

	public void setStatus(Set<Status> status) {
		this.status = status;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setOwnedTickets(Set<Ticket> ownedTickets) {
		this.ownedTickets = ownedTickets;
	}

	public void setApproverTickets(Set<Project> approverTickets) {
		this.approvedProjects = approverTickets;
	}
	
	public void setApprovedProjects(Set<Project> approvedProjects) {
		this.approvedProjects = approvedProjects;
	}

	public void setDevelopedProjects(Set<Project> developedProjects) {
		this.developedProjects = developedProjects;
	}
	
}
