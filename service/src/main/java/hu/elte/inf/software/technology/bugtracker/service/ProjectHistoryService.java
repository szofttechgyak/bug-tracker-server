package hu.elte.inf.software.technology.bugtracker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.elte.inf.software.technology.bugtracker.domain.ProjectHistory;
import hu.elte.inf.software.technology.bugtracker.history.ProjectHistoryDao;

@Service
public class ProjectHistoryService {

	@Autowired
	ProjectHistoryDao projectHistoryDao;
	
	public List<ProjectHistory> getAllProjectHistory() {
    	return projectHistoryDao.listProjectHistory();
    } 
    
    public ProjectHistory getProjectHistorytById(int id) {
    	return projectHistoryDao.getProjectHistoryById(id);
    }
    

	public ProjectHistoryDao getProjectHistoryDao() {
		return projectHistoryDao;
	}

	public void setProjectHistoryDao(ProjectHistoryDao projectHistoryDao) {
		this.projectHistoryDao = projectHistoryDao;
	}
}
