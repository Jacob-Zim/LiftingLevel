package com.jacobzim.LiftingLevel.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "calendar")
public class Date {
	
	private int id;
	private String date;
	private User user;
	
	private List<Lift> liftData;
	
	
	public Date() {}
	
	public Date(User user, String date) {
		this.date = date;
		this.user = user;
	}

	@Id
	@GeneratedValue
	@Column(name="id")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@ManyToOne
	@JoinColumn(name = "user_id")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@OneToMany(fetch=FetchType.LAZY,cascade=CascadeType.ALL,mappedBy="id")
	public List<Lift> getLiftData() {
		return liftData;
	}

	public void setLiftData(List<Lift> liftData) {
		this.liftData = liftData;
	}

}
