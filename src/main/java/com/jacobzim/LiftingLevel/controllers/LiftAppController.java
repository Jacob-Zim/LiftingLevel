package com.jacobzim.LiftingLevel.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jacobzim.LiftingLevel.interfaces.UserDao;

@Controller
public class LiftAppController {

	@Autowired
	private UserDao userDao;
	
    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String loginPost(){
    	return "main";
    }
}
