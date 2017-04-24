package com.jacobzim.LiftingLevel.interfaces;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jacobzim.LiftingLevel.models.CalendarDate;
import com.jacobzim.LiftingLevel.models.Lift;
import com.jacobzim.LiftingLevel.models.User;

@Transactional
@Repository
public interface CalendarDateDao extends CrudRepository<CalendarDate, Integer> {
	
	public List<CalendarDate> findAllByUser(User user);
	
	public List<CalendarDate> findAllByLiftData(List<Lift> liftData);
	
	public CalendarDate findByDate(String date);
	
	public CalendarDate findByDateAndUser(String date, User user);
}
