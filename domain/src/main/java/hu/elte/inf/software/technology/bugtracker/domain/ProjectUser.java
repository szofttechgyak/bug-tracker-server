package hu.elte.inf.software.technology.bugtracker.domain;

import java.io.Serializable;

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
@Table(name = "ProjectUser")
@Proxy(lazy = false)
public class ProjectUser implements Serializable {
	
	private int id;
	private User user;
	private Project project;
	private String 	role;
	
	public ProjectUser(){
	}
	
	@Id
	@Column(name = "projectUserId")
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
	@JoinColumn(name = "projectId" )
	public Project getProject() {
		return project;
	}
	
	public void setUser(User user) {
		this.user = user;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public void setId(int id) {
		this.id = id;
	}

}
