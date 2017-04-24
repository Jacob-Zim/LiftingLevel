package com.jacobzim.LiftingLevel.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jacobzim.LiftingLevel.interfaces.CalendarDateDao;
import com.jacobzim.LiftingLevel.interfaces.UserDao;
import com.jacobzim.LiftingLevel.models.CalendarDate;
import com.jacobzim.LiftingLevel.models.User;
import com.jacobzim.LiftingLevel.services.CalendarService;

@Controller
public class CalendarController extends AuthenticationController {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private CalendarDateDao dateDao;
	
	private CalendarService instance = new CalendarService();
	
	//navigates to the user calendar page
    @RequestMapping(value = "/calendar", method = RequestMethod.GET)
    public String getWorkoutCalendar(HttpServletRequest currentUser, HttpSession session, Model model) {
    	User user = userDao.findByName((String)currentUser.getSession().getAttribute("session_id"));
    	instance.calendarInitialize();
    	Calendar now = instance.getCalendar();
    	//aligns the dates to the proper position
    	int daysInMonth = instance.monthDateSetup(now);
    	//list of dates to append to the model
    	List<Integer> listForDaysInMonth = instance.dateList(daysInMonth);
    	//gets the date object for obtaining the month and year
    	Date currentDate = now.getTime();
    	List<CalendarDate> workoutInfoContainers = dateDao.findAllByUser(user);
    	String month = currentDate.toString().substring(4, 7);
    	String year = currentDate.toString().substring(24, 28);	
    	int monthIntRepresentation = (now.get(Calendar.MONTH) + 1);
    	int yearIntRepresentation = now.get(Calendar.YEAR);
    	//this will fix the overlap issue, currently working on the logic..
    	//instance.trueIfFreeSpace(workoutInfoContainers, dateIndex, monthIntRepresentation, yearIntRepresentation);
    	model.addAttribute("dateYearIntRep", yearIntRepresentation);
    	model.addAttribute("dateMonthIntRep", monthIntRepresentation);
    	model.addAttribute("dateMonth", month);
    	model.addAttribute("dateYear", year);
    	model.addAttribute("dateData", workoutInfoContainers);
    	model.addAttribute("daysInMonth", listForDaysInMonth);
    	return loginCheck(currentUser, "calendar");
    }
    
    //returns the next month when user clicks on forward button
    @RequestMapping(value = "/calendar", method = RequestMethod.POST, params="next")
    public String getNextMonth(HttpServletRequest currentUser, HttpSession session, Model model) {
    	User user = userDao.findByName((String)currentUser.getSession().getAttribute("session_id"));
    	if (instance.getCalendar() == null) {
    		instance.calendarInitialize();
    	}
    	instance.forwardMonth(instance.getCalendar());
    	//aligns the dates to the proper position
    	int daysInMonth = instance.monthDateSetup(instance.getCalendar());
    	//list of dates to append to the model
    	List<Integer> listForDaysInMonth = instance.dateList(daysInMonth);
    	//gets the date object for obtaining the month and year
    	Date currentDate = instance.getCalendar().getTime();
    	List<CalendarDate> workoutInfoContainers = dateDao.findAllByUser(user);
    	String month = currentDate.toString().substring(4, 7);
    	String year = currentDate.toString().substring(24, 28);
    	int monthIntRepresentation = (instance.getCalendar().get(Calendar.MONTH) + 1);
    	int yearIntRepresentation = instance.getCalendar().get(Calendar.YEAR);
    	model.addAttribute("dateYearIntRep", yearIntRepresentation);
    	model.addAttribute("dateMonthIntRep", monthIntRepresentation);
    	model.addAttribute("dateMonth", month);
    	model.addAttribute("dateYear", year);
    	model.addAttribute("dateData", workoutInfoContainers);
    	model.addAttribute("daysInMonth", listForDaysInMonth);
    	return loginCheck(currentUser, "calendar");
    }

    //returns the previous month when user clicks on back button
    @RequestMapping(value = "/calendar", method = RequestMethod.POST, params="prev")
    public String getPreviousMonth(HttpServletRequest currentUser, HttpSession session, Model model) {
    	User user = userDao.findByName((String)currentUser.getSession().getAttribute("session_id"));
    	if (instance.getCalendar() == null) {
    		instance.calendarInitialize();
    	}
    	instance.backMonth(instance.getCalendar());
    	int daysInMonth = instance.monthDateSetup(instance.getCalendar());
    	List<Integer> listForDaysInMonth = instance.dateList(daysInMonth);
    	Date currentDate = instance.getCalendar().getTime();
    	List<CalendarDate> workoutInfoContainers = dateDao.findAllByUser(user);
    	String month = currentDate.toString().substring(4, 7);
    	String year = currentDate.toString().substring(24, 28);
    	int monthIntRepresentation = (instance.getCalendar().get(Calendar.MONTH) + 1);
    	int yearIntRepresentation = instance.getCalendar().get(Calendar.YEAR);
    	model.addAttribute("dateYearIntRep", yearIntRepresentation);
    	model.addAttribute("dateMonthIntRep", monthIntRepresentation);
    	model.addAttribute("dateMonth", month);
    	model.addAttribute("dateYear", year);
    	model.addAttribute("dateData", workoutInfoContainers);
    	model.addAttribute("daysInMonth", listForDaysInMonth);
    	return loginCheck(currentUser, "calendar");
    }
    
    @RequestMapping(value = "/calendar", method = RequestMethod.POST, params="userOptionsBtn")
    public String chooseDateForWorkout(HttpServletRequest currentUser, HttpSession session, Model model) {
    	User user = userDao.findByName((String)currentUser.getSession().getAttribute("session_id"));
    	//instance.calendarInitialize();
    	Calendar now = instance.getCalendar();
    	//aligns the dates to the proper position
    	int daysInMonth = instance.monthDateSetup(now);
    	//list of dates to append to the model
    	List<Integer> listForDaysInMonth = instance.dateList(daysInMonth);
    	//gets the date object for obtaining the month and year
    	Date currentDate = now.getTime();
    	List<CalendarDate> workoutInfoContainers = dateDao.findAllByUser(user);
    	String month = currentDate.toString().substring(4, 7);
    	String year = currentDate.toString().substring(24, 28);
    	model.addAttribute("dateMonth", month);
    	model.addAttribute("dateYear", year);
    	int monthIntRepresentation = (instance.getCalendar().get(Calendar.MONTH) + 1);
    	int yearIntRepresentation = instance.getCalendar().get(Calendar.YEAR);
    	model.addAttribute("dateYearIntRep", yearIntRepresentation);
    	model.addAttribute("dateMonthIntRep", monthIntRepresentation);
    	model.addAttribute("dateData", workoutInfoContainers);
    	model.addAttribute("daysInMonth", listForDaysInMonth);
    	model.addAttribute("dateCreate", workoutInfoContainers);
    	return loginCheck(currentUser, "calendar");
    }
}
