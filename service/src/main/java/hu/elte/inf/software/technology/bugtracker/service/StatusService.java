package hu.elte.inf.software.technology.bugtracker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hu.elte.inf.software.technology.bugtracker.domain.Status;
import hu.elte.inf.software.technology.bugtracker.statusdao.StatusDao;

@Service
public class StatusService {

	@Autowired
	StatusDao statusDao;
	
	public List<Status> getAllStatuses() {
    	return statusDao.listStatuses();
    } 
    
    public Status getStatusById(int id) {
    	return statusDao.getStatusById(id);
    }
    
    public void addStatus(Status status){
    	statusDao.addStatus(status);
    }
    
    public void updateStatus(Status status){
    	statusDao.updateStatus(status);
    }
    
    public void removeStatus(int id){
    	statusDao.removeStatus(id);
    }

	public StatusDao getStatusDao() {
		return statusDao;
	}

	public void setStatusDao(StatusDao statusDao) {
		this.statusDao = statusDao;
	}
}
