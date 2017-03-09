package com.jacobzim.LiftingLevel.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jacobzim.LiftingLevel.interfaces.UserDao;

@Controller
public class LiftAppController extends AuthenticationController {

	@Autowired
	private UserDao userDao;
	
    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String getMain(HttpServletRequest currentUser) {
    	return loginCheck(currentUser, "main");
    }
}
