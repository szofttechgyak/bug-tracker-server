package hu.elte.inf.software.technology.bugtracker.domain;

public class Rule {	
	private String oldStatus;
	
	private String newStatus;
	
	private String role;
	
	private String assigneeRole;
	
	private String ticketType;
	
	public String getOldStatus() {
		return oldStatus;
	}

	public void setOldStatus(String oldStatus) {
		this.oldStatus = oldStatus;
	}

	public String getNewStatus() {
		return newStatus;
	}

	public void setNewStatus(String newStatus) {
		this.newStatus = newStatus;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getAssigneeRole() {
		return assigneeRole;
	}

	public void setAssigneeRole(String assigneeRole) {
		this.assigneeRole = assigneeRole;
	}

	public String getTicketType() {
		return ticketType;
	}

	public void setTicketType(String ticketType) {
		this.ticketType = ticketType;
	}

	@Override
	public String toString() {
		return "Rule [oldStatus=" + oldStatus + ", newStatus=" + newStatus + ", role=" + role + ", assigneeRole="
				+ assigneeRole + ", ticketType=" + ticketType + "]";
	}
}