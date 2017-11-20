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
	
	@Autowired
	UserService userService;
	
	public List<ProjectHistory> getAllProjectHistory() {
    	return projectHistoryDao.listProjectHistory();
    } 
    
    public ProjectHistory getProjectHistorytById(int id) {
    	return projectHistoryDao.getProjectHistoryById(id);
    }
    
    public List<ProjectHistory> getProjectHistorytByProjectId(int projectId) {
    	List<ProjectHistory> history = projectHistoryDao.getProjectHistoryByProjectId(projectId);
    	for(ProjectHistory entry : history) {
    		if (entry.getFieldName().equals("defaultDeveloperId")) {
    			entry.setFieldName("defaultDeveloper");
    			entry.setOldValue(userService.getUserById(Integer.parseInt(entry.getOldValue())).getUserName());
    			entry.setNewValue(userService.getUserById(Integer.parseInt(entry.getNewValue())).getUserName());
    		}
    		else if (entry.getFieldName().equals("defaultApproverId")) {
    			entry.setFieldName("defaultApprover");
    			entry.setOldValue(userService.getUserById(Integer.parseInt(entry.getOldValue())).getUserName());
    			entry.setNewValue(userService.getUserById(Integer.parseInt(entry.getNewValue())).getUserName());
    		}
    	}    	
    	return history;
    }
    
	public ProjectHistoryDao getProjectHistoryDao() {
		return projectHistoryDao;
	}

	public void setProjectHistoryDao(ProjectHistoryDao projectHistoryDao) {
		this.projectHistoryDao = projectHistoryDao;
	}
}
