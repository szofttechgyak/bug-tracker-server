package hu.elte.inf.software.technology.bugtracker.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Project")
@Proxy(lazy = false)
public class Project implements Serializable{
	
	private int id;
	private String projectName;
	private String projectDescription;
	private User defaultApprover;
	private User defaultDeveloper;
	private int s1Time;
	private int s2Time;
	private int s3Time;
	private Set<Ticket> tickets;
	private Set<ProjectUser> projectUser;
	
	public Project(){
	}
	
	@Id
	@Column(name = "projectId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}
	
	@JsonIgnore
	@OneToMany(mappedBy = "project")
	public Set<Ticket> getTickets() {
		return tickets;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "defaultApproverId")
	public User getDefaultApprover() {
		return defaultApprover;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "defaultDeveloperId")
	public User getDefaultDeveloper() {
		return defaultDeveloper;
	}

	@JsonIgnore
	@OneToMany(mappedBy = "project")
	public Set<ProjectUser> getProjectUser() {
		return projectUser;
	}

	public void setProjectUser(Set<ProjectUser> projectUser) {
		this.projectUser = projectUser;
	}

	public void setTickets(Set<Ticket> tickets) {
		this.tickets = tickets;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectDescription() {
		return projectDescription;
	}

	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}

	public void setDefaultApprover(User defaultApproverId) {
		this.defaultApprover = defaultApproverId;
	}

	public void setDefaultDeveloper(User defaultDeveloperId) {
		this.defaultDeveloper = defaultDeveloperId;
	}

	public int getS1Time() {
		return s1Time;
	}

	public void setS1Time(int s1Time) {
		this.s1Time = s1Time;
	}

	public int getS2Time() {
		return s2Time;
	}

	public void setS2Time(int s2Time) {
		this.s2Time = s2Time;
	}

	public int getS3Time() {
		return s3Time;
	}

	public void setS3Time(int s3Time) {
		this.s3Time = s3Time;
	}
	
}
