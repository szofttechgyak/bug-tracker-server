package hu.elte.inf.software.technology.bugtracker.statusdao;

import java.util.List;

import hu.elte.inf.software.technology.bugtracker.domain.Status;;

public interface StatusDao {
	public void addStatus(Status status);
	public void updateStatus(Status status);
	public List<Status> listStatuses();
	public Status getStatusById(int id);
	public void removeStatus(int id);
}
