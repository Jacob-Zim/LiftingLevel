package com.jacobzim.LiftingLevel.controllers;

import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jacobzim.LiftingLevel.interfaces.CalendarDateDao;
import com.jacobzim.LiftingLevel.interfaces.LiftDao;
import com.jacobzim.LiftingLevel.interfaces.UserDao;
import com.jacobzim.LiftingLevel.models.CalendarDate;
import com.jacobzim.LiftingLevel.models.Lift;
import com.jacobzim.LiftingLevel.models.User;
import com.jacobzim.LiftingLevel.services.CalendarService;

//hook up date and lift data
@Controller
public class LiftAppController extends AuthenticationController {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private LiftDao liftDao;
	
	@Autowired
	private CalendarDateDao dateDao;
	
	private CalendarService instance = new CalendarService();
	
	//works with the date field from the calendar page
	//displays the users' lift data from the date selected
    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String getMain(HttpServletRequest currentUser, Model model) {
    	User user = userDao.findByName((String)currentUser.getSession().getAttribute("session_id"));
    	//creates calendar object within CalendarService class if not already initialized
    	if (instance.getCalendar() == null) {
    		instance.calendarInitialize();
    	}
    	//object containing the lift data found through the selected date
    	//CalendarDate dateContainingLifts = instance.getCalendarDate();
    	CalendarDate dateContainingLifts = instance.useCurrentCalendarDate(user);
    	if (dateDao.findByDateAndUser(dateContainingLifts.getDate(), user) == null) {
    		dateDao.save(dateContainingLifts);
    	} else {
    		dateContainingLifts = dateDao.findByDateAndUser(dateContainingLifts.getDate(), user);
    	}
    	List<Lift> listOfCurrentDateLifts = liftDao.findAllByDate(dateContainingLifts);
    	String todaysDate = dateContainingLifts.getDate();
    	String day = ""+Integer.parseInt(todaysDate.substring(2, 4));
    	String month = ""+Integer.parseInt(todaysDate.substring(0, 2));
    	String year = ""+Integer.parseInt(todaysDate.substring(4, 8));
    	model.addAttribute("date", month+"-"+day+"-"+year);
    	model.addAttribute("liftData", listOfCurrentDateLifts);
    	return loginCheck(currentUser, "main");
    }
    //goes to the edit page with the users' selected lift data from the selected date
    @RequestMapping(value = "/main", method = RequestMethod.POST, params="editBtn")
    public String getEditLift(int editBtn, HttpServletRequest currentUser, Model model) {
    	User user = userDao.findByName((String)currentUser.getSession().getAttribute("session_id"));
    	//List<Lift> liftList = liftDao.findAllByUser(user);
    	CalendarDate dateContainingLifts = instance.useCurrentCalendarDate(user);
    	List<Lift> liftList = liftDao.findAllByDate((dateDao.findByDateAndUser(dateContainingLifts.getDate(), user)));
    	Lift liftToEdit = liftList.get(editBtn);
    	model.addAttribute("liftName", liftToEdit.getName());
    	model.addAttribute("liftDescription", liftToEdit.getDescription());
    	model.addAttribute("liftWeight", liftToEdit.getWeight());
    	model.addAttribute("liftSets", liftToEdit.getSets());
    	model.addAttribute("liftReps", liftToEdit.getReps());
    	model.addAttribute("liftNum", editBtn);
    	return loginCheck(currentUser, "editlift");
    }
    //edits the selected lift
    @RequestMapping(value = "/editlift", method = RequestMethod.POST, params="liftNumber")
    public String editLift(HttpServletRequest currentUser, String liftNumber, String liftName, String description, String weight, String sets, String reps) {
    	User user = userDao.findByName((String)currentUser.getSession().getAttribute("session_id"));
    	//List<Lift> liftList = liftDao.findAllByUser(user);
    	CalendarDate dateContainingLifts = instance.useCurrentCalendarDate(user);
    	List<Lift> liftList = liftDao.findAllByDate(dateDao.findByDateAndUser(dateContainingLifts.getDate(), user));
    	Lift liftToEdit = liftList.get(Integer.parseInt(liftNumber));
    	liftToEdit.setName(liftName);
    	liftToEdit.setDescription(description);
    	liftToEdit.setWeight(weight);
    	liftToEdit.setSets(sets);
    	liftToEdit.setReps(reps);
    	liftDao.save(liftToEdit);
    	return loginCheck(currentUser, "redirect:main");
    }
    //deletes selected lift
    @RequestMapping(value = "/main", method = RequestMethod.POST, params="deleteBtn")
    public String deleteLift(int deleteBtn, HttpServletRequest currentUser) {
    	User user = userDao.findByName((String)currentUser.getSession().getAttribute("session_id"));
    	//List<Lift> liftList = liftDao.findAllByUser(user);
    	CalendarDate dateContainingLifts = instance.useCurrentCalendarDate(user);
    	List<Lift> liftList = liftDao.findAllByDate(dateDao.findByDateAndUser(dateContainingLifts.getDate(), user));
    	liftDao.delete(liftList.get(deleteBtn).getId());
    	return loginCheck(currentUser, "redirect:main");
    }
    //navigates to create lift page
    @RequestMapping(value = "/createlift", method = RequestMethod.GET)
    public String getCreateLiftPage(HttpServletRequest currentUser) {
    	return loginCheck(currentUser, "createlift");
    }
    //creates a new lift for the user on the specified date
    @RequestMapping(value = "/createlift", method = RequestMethod.POST)
    public String createLift(HttpServletRequest currentUser, String liftName, String weight, String sets, String reps, String description) {
    	User user = userDao.findByName((String)currentUser.getSession().getAttribute("session_id"));
    	//instance is used here to be sure to use the user selected date not the current one
    	//using dateDao so there is only one date object per string date entry
    	CalendarDate instanceOfSelectedDate = instance.useCurrentCalendarDate(user);
    	CalendarDate usedDate = dateDao.findByDateAndUser(instanceOfSelectedDate.getDate(), user);
    	Lift createdLift = new Lift(liftName, description, reps, sets, weight, usedDate, user);
    	liftDao.save(createdLift);
    	return loginCheck(currentUser, "redirect:main");
    }
}
