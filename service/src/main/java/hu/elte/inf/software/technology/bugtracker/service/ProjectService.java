package hu.elte.inf.software.technology.bugtracker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.elte.inf.software.technology.bugtracker.domain.Project;
import hu.elte.inf.software.technology.bugtracker.projectdao.ProjectDao;

@Service
public class ProjectService {
	
	@Autowired
    private ProjectDao projectDao;

	public List<Project> getAllProjects() {
    	return projectDao.listProjects();
    } 
    
    public Project getProjectById(int id) {
    	return projectDao.getProjectById(id);
    }
    
    public void addProject(Project project){
    	projectDao.addProject(project);
    }
    
    public void updateProject(Project project){
    	projectDao.updateProject(project);
    }
    
    public void removeProject(int id){
    	projectDao.removeProject(id);
    }
    
    public ProjectDao getProjectDao() {
		return projectDao;
	}

	public void setProjectDao(ProjectDao projectDao) {
		this.projectDao = projectDao;
	}

}
