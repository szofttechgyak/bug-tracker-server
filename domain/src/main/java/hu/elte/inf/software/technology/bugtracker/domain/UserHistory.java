package hu.elte.inf.software.technology.bugtracker.domain;

import java.io.Serializable;
import java.sql.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


import org.hibernate.annotations.Proxy;

@Entity
@Table(name = "UserHistory")
@Proxy(lazy = false)
public class UserHistory implements Serializable{
	
	private int id;
	private User user;
	private Date dateOfChange;
	private String fieldName;
	private String oldValue;
	private String newValue;
	
	public UserHistory(){
	}

	@Id
	@Column(name = "userHistoryId")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public int getId() {
		return id;
	}
	

	public void setUser(User user) {
		this.user = user;
	}

	
	@ManyToOne(fetch = FetchType.LAZY) //(cascade=CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "userId" ) //, nullable = false)
    public User getUser() {
		return user;
	}
	
	public Date getDateOfChange() {
		return dateOfChange;
	}

	public void setDateOfChange(Date dateOfChange) {
		this.dateOfChange = dateOfChange;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getOldValue() {
		return oldValue;
	}

	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}

	public String getNewValue() {
		return newValue;
	}

	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}

	public void setId(int id) {
		this.id = id;
	}


	
}
