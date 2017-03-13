package com.jacobzim.LiftingLevel.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "calendar")
public class Date {
	
	@Id
	private int id;

}
