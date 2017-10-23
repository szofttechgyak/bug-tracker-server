package hu.elte.inf.software.technology.bugtracker.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.elte.inf.software.technology.bugtracker.domain.Project;
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
	
}
