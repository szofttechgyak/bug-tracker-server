package hu.elte.inf.software.technology.bugtracker.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.elte.inf.software.technology.bugtracker.domain.Project;
import hu.elte.inf.software.technology.bugtracker.domain.ProjectRole;
import hu.elte.inf.software.technology.bugtracker.domain.ProjectUser;
import hu.elte.inf.software.technology.bugtracker.domain.User;
import hu.elte.inf.software.technology.bugtracker.projectuserdao.ProjectUserDao;

@Service
public class ProjectUserService {

	@Autowired
	ProjectUserDao projectUserDao;
	
	public List<ProjectUser> getAllProjectUsers() {
    	return projectUserDao.listProjectUsers();
    } 
    
    public ProjectUser getProjectUserById(int id) {
    	return projectUserDao.getProjectUserById(id);
    }
    
    public List<Project> getAssignedProjects(int userId) {
    	return getAllProjectUsers()
    			.stream()
    			.filter(projectUser -> projectUser.getUser().getId() == userId)
    			.map(projectUser -> projectUser.getProject())
    			.collect(Collectors.toList());
    }
    
    public List<User> getAssignedUsers(int projectId) {
    	return getAllProjectUsers()
    			.stream()
    			.filter(projectUser -> projectUser.getProject().getId() == projectId)
    			.map(projectUser -> projectUser.getUser())
    			.collect(Collectors.toList());
    }
    
    public List<User> getUsersInRole(int projectId, String role) {
    	return getAssignedProjectUsers(projectId)
			.stream()
			.filter(pu -> pu.getRole().equals(role))
			.map(pu -> pu.getUser())
			.collect(Collectors.toList());
    }
    
    public List<ProjectUser> getAssignedProjectUsers(int projectId) {
    	return getAllProjectUsers()
    			.stream()
    			.filter(projectUser -> projectUser.getProject().getId() == projectId)
    			.collect(Collectors.toList());
    }
    
    public void addProjectUser(ProjectUser projectUser){
    	projectUserDao.addProjectUser(projectUser);
    }
    
    public void updateProjectUser(ProjectUser projectUser){
    	projectUserDao.updateProjectUser(projectUser);
    }
    
    public void removeProjectUser(int id){
    	projectUserDao.removeProjectUser(id);
    }

	public ProjectUserDao getProjectUserDao() {
		return projectUserDao;
	}

	public void setProjectUserDao(ProjectUserDao projectUserDao) {
		this.projectUserDao = projectUserDao;
	}
	
	public List<ProjectRole> getUserRolesByProject(int projectId){
		List<ProjectRole> projectRole = new ArrayList<ProjectRole>();
		String[] roles = {"Customer", "Developer", "Approver"};
		for (String role: roles)
		{
			ProjectRole pj = new ProjectRole();
			pj.setRoleName(role);
			pj.setUsers(getUsersInRole(projectId, role));
			projectRole.add(pj);
		}
		
		return projectRole;
	}
	
	
}
