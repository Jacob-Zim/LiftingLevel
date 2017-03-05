package com.jacobzim.LiftingLevel.models;

import java.util.Map;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {

	@Id
	private String id;
	private String name;
	private String password;
	
	//Collection of lifts keys are the name of the lift
	//private Map<String, Lift> liftData;
	
	public User() {};
	
	public User(String id, String name, String password){
		this.id = id;
		this.name = name;
		this.password = password;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	//public Map<String, Lift> getLiftData() {
		//return liftData;
	//}

	//public void setLiftData(Map<String, Lift> liftData) {
		//this.liftData = liftData;
	//}
}
