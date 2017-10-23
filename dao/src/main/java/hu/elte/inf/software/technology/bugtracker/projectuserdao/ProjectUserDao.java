package hu.elte.inf.software.technology.bugtracker.projectuserdao;

import java.util.List;

import hu.elte.inf.software.technology.bugtracker.domain.ProjectUser;

public interface ProjectUserDao {
	public void addProjectUser(ProjectUser projectUser);
	public void updateProjectUser(ProjectUser projectUser);
	public List<ProjectUser> listProjectUsers();
	public ProjectUser getProjectUserById(int id);
	public void removeProjectUser(int id);
}
