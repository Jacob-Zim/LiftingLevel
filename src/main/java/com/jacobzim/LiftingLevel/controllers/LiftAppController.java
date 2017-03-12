package com.jacobzim.LiftingLevel.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    public String getMain(HttpServletRequest currentUser) {
    	return loginCheck(currentUser, "main");
    }
    
    @RequestMapping(value = "/createlift", method = RequestMethod.GET)
    public String getCreateLiftPage(HttpServletRequest currentUser) {
    	return loginCheck(currentUser, "createlift");
    }
    
    @RequestMapping(value = "/createlift", method = RequestMethod.POST)
    public String createLift(HttpServletRequest currentUser, String liftName, String weight, String sets, String reps, String description, int id) {
    	User user = userDao.findByName((String)currentUser.getSession().getAttribute("session_id"));
    	Lift createdLift = new Lift(id, liftName, description, user, reps, sets, weight);
    	liftDao.save(createdLift);
    	return loginCheck(currentUser, "redirect:main");
    }
}
