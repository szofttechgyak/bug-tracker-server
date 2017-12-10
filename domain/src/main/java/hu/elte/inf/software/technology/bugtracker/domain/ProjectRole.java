package hu.elte.inf.software.technology.bugtracker.domain;

import java.util.List;

public class ProjectRole {	
	private String roleName;
	
	private List<User> users;
	
	
	public String getRoleName() {
		return roleName;
	}


	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}


	public List<User> getUsers() {
		return users;
	}


	public void setUsers(List<User> users) {
		this.users = users;
	}

}