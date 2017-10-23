package hu.elte.inf.software.technology.bugtracker.projectdao;

import java.util.List;

import hu.elte.inf.software.technology.bugtracker.domain.Project;

public interface ProjectDao {
	
	public void addProject(Project project);
	public void updateProject(Project project);
	public List<Project> listProjects();
	public Project getProjectById(int id);
	public void removeProject(int id);

}
