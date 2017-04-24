package com.jacobzim.LiftingLevel.models;

import java.util.Calendar;
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
@Table(name = "dates")
public class CalendarDate {

	private User user;
	
	private int dateId;
	private String date;
	private String name;
	private String description;
	
	private List<Lift> liftData;
	
	public CalendarDate() {};
	
	public CalendarDate(String date, String name, String description, User user) {
		this.date = date;
		this.name = name;
		this.description = description;
		this.user = user;
	}

	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "user_id")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Id
	@GeneratedValue
	@Column(name="date_id")
	public int getDateId() {
		return dateId;
	}

	public void setDateId(int dateId) {
		this.dateId = dateId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@OneToMany(fetch=FetchType.LAZY,cascade=CascadeType.ALL,mappedBy="id")
	public List<Lift> getLiftData() {
		return liftData;
	}

	public void setLiftData(List<Lift> liftData) {
		this.liftData = liftData;
	}
	
	public int structureDayForComparison() {
		String dateToFormat = this.getDate();
		String dayToFormat = dateToFormat.substring(2, 4);
		int formatedDay = Integer.parseInt(dayToFormat);
		return formatedDay;
	}
	
	public int structureMonthForComparison() {
		String dateToFormat = this.getDate();
		String monthToFormat = dateToFormat.substring(0, 2);
		int formatedMonthInt = Integer.parseInt(monthToFormat);
		//String formatedMonth = ""+formatedMonthInt;
		//System.out.println(formatedMonth);
		return formatedMonthInt;
	}
	
	public int structureYearForComparison() {
		String dateToFormat = this.getDate();
		//System.out.println(dateToFormat);
		String yearToFormat = dateToFormat.substring(4, 8);
		int formatedYearInt = Integer.parseInt(yearToFormat);
		//String formatedYear = ""+formatedYearInt;
		//System.out.println(formatedYear);
		return formatedYearInt;
	}
}
