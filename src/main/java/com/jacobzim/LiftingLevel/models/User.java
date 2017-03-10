package com.jacobzim.LiftingLevel.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {
	
	private String userId;
	private String password;
	private String sessionId;
	
	private List<Lift> liftData;
	
	public User() {};
	
	public User(String name, String password, String sessionId, List<Lift> liftData) {
		this.userId = name;
		this.password = password;
		this.sessionId = sessionId;
		this.liftData = liftData;
	}
		
	@OneToMany(fetch=FetchType.LAZY,cascade=CascadeType.ALL,mappedBy="id")
	public List<Lift> getLiftData() {
		return liftData;
	}

	public void setLiftData(List<Lift> liftData) {
		this.liftData = liftData;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
	@Id
	@GeneratedValue
	@Column(name="user_id")
	public String getName() {
		return userId;
	}

	public void setName(String name) {
		this.userId = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}

	/* public Map<String, Lift> getLiftData() {
		return liftData;
	}

	public void setLiftData(Map<String, Lift> liftData) {
		this.liftData = liftData;
	}
}
*/