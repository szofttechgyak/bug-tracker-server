package hu.elte.inf.software.technology.bugtracker.history;

import java.util.List;

import hu.elte.inf.software.technology.bugtracker.domain.ProjectHistory;

public interface ProjectHistoryDao {

	public List<ProjectHistory> listProjectHistory();
	public ProjectHistory getProjectHistoryById(int id);
	
}

