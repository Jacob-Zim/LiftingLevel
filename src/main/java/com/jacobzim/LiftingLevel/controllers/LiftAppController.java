package com.jacobzim.LiftingLevel.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jacobzim.LiftingLevel.interfaces.LiftDao;
import com.jacobzim.LiftingLevel.interfaces.UserDao;
import com.jacobzim.LiftingLevel.models.Lift;
import com.jacobzim.LiftingLevel.models.User;

@Controller
public class LiftAppController extends AuthenticationController {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private LiftDao liftDao;
	
    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String getMain(HttpServletRequest currentUser, Model model) {
    	User user = userDao.findByName((String)currentUser.getSession().getAttribute("session_id"));
    	model.addAttribute("liftData", liftDao.findAllByUser(user));
    	return loginCheck(currentUser, "main");
    }
    
    @RequestMapping(value = "/main", method = RequestMethod.POST, params="editBtn")
    public String getEditLift(int editBtn, HttpServletRequest currentUser, Model model) {
    	User user = userDao.findByName((String)currentUser.getSession().getAttribute("session_id"));
    	List<Lift> liftList = liftDao.findAllByUser(user);
    	Lift liftToEdit = liftList.get(editBtn);
    	model.addAttribute("liftName", liftToEdit.getName());
    	model.addAttribute("liftDescription", liftToEdit.getDescription());
    	model.addAttribute("liftWeight", liftToEdit.getWeight());
    	model.addAttribute("liftSets", liftToEdit.getSets());
    	model.addAttribute("liftReps", liftToEdit.getReps());
    	model.addAttribute("liftNum", editBtn);
    	return loginCheck(currentUser, "editlift");
    }
    
    @RequestMapping(value = "/editlift", method = RequestMethod.POST, params="liftNumber")
    public String editLift(HttpServletRequest currentUser, String liftNumber, String liftName, String description, String weight, String sets, String reps) {
    	User user = userDao.findByName((String)currentUser.getSession().getAttribute("session_id"));
    	List<Lift> liftList = liftDao.findAllByUser(user);
    	Lift liftToEdit = liftList.get(Integer.parseInt(liftNumber));
    	liftToEdit.setName(liftName);
    	liftToEdit.setDescription(description);
    	liftToEdit.setWeight(weight);
    	liftToEdit.setSets(sets);
    	liftToEdit.setReps(reps);
    	liftDao.save(liftToEdit);
    	return loginCheck(currentUser, "redirect:main");
    }
    
    @RequestMapping(value = "/main", method = RequestMethod.POST, params="deleteBtn")
    public String deleteLift(int deleteBtn, HttpServletRequest currentUser) {
    	User user = userDao.findByName((String)currentUser.getSession().getAttribute("session_id"));
    	List<Lift> liftList = liftDao.findAllByUser(user);
    	liftDao.delete(liftList.get(deleteBtn).getId());
    	return loginCheck(currentUser, "redirect:main");
    }
    
    @RequestMapping(value = "/createlift", method = RequestMethod.GET)
    public String getCreateLiftPage(HttpServletRequest currentUser) {
    	return loginCheck(currentUser, "createlift");
    }
    
    @RequestMapping(value = "/createlift", method = RequestMethod.POST)
    public String createLift(HttpServletRequest currentUser, String liftName, String weight, String sets, String reps, String description) {
    	User user = userDao.findByName((String)currentUser.getSession().getAttribute("session_id"));
    	Lift createdLift = new Lift(liftName, description, user, reps, sets, weight);
    	liftDao.save(createdLift);
    	return loginCheck(currentUser, "redirect:main");
    }
}
