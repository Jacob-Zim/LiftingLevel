package com.jacobzim.LiftingLevel.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jacobzim.LiftingLevel.interfaces.UserDao;
import com.jacobzim.LiftingLevel.models.User;

@Controller
public class UserController {
	
	@Autowired
	private UserDao userDao;
	
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(){
        return "login";
    }
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(String name, String password, Model model){
    	User nameTaken = userDao.findByName(name);
    	if (userDao.findByName(name) == null) {
    		model.addAttribute("userNonExistant", "UserName does not exist!");
    		model.addAttribute("invalidPassword", "");
    		return "login";
    	} else if (!(nameTaken.getPassword().equals(password))) {
    		model.addAttribute("userNonExistant", "");
    		model.addAttribute("invalidPassword", "Invalid password");
    		return "login";
    	} else {
    		//create a user status method here
    		return "redirect:main";
    	}
    }
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerPage(Model model){
    	model.addAttribute("usermessage", "");
    	model.addAttribute("passwordmessage", "");
    	return "register";
    }
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(String name, String password, String confirmPassword, Model model){
    	User nameTaken = userDao.findByName(name);
    	if (nameTaken != null) {
    		model.addAttribute("usermessage", "The name " + name + " already exists!");
    		model.addAttribute("passwordmessage", "");
    		return "register";
    	}
    	else if (!(password.equals(confirmPassword))) {
    		model.addAttribute("passwordmessage", "Confirmation does not match original password!");
    		return "register";
    	} else {
    		User registeredUser = new User(name, password);
    		userDao.save(registeredUser);
    		model.addAttribute("passwordmessage", "");
    		model.addAttribute("usermessage", "Account created!");
    		return "register";
    	}
    }
    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String loginPost(){
    	return "main";
    }
}