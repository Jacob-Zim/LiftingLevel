package com.jacobzim.LiftingLevel.models;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "users")
public class User {
	
	@Id
	private String name;
	private String password;
	private boolean loginStatus;
	
	//Collection of lifts keys are the name of the lift
	//private Map<String, Lift> liftData;
	
	public User() {};
	
	public User(String name, String password, boolean loginStatus) {
		this.name = name;
		this.password = password;
		this.loginStatus = loginStatus;
	}
		
	public boolean getLoginStatus() {
		return loginStatus;
	}

	public void setLoginStatus(boolean loginStatus) {
		this.loginStatus = loginStatus;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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