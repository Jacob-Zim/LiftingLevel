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
public class UserController extends AuthenticationController{
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private LiftDao liftDao;
	
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }
    
    @RequestMapping(value = "/error", method = RequestMethod.GET)
    public String errorPage() {
        return "error";
    }
    
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String redirectLogin() {
        return "login";
    }
    
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(String name, String password, Model model, HttpServletRequest currentUser){
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
    		if (!(currentUser.getSession().getAttribute("session_id") == null)) {
    			model.addAttribute("userNonExistant", "Please log out before logging in again!");
    			return "login";
    		} 
    		else {
    			currentUser.getSession().setAttribute("session_id", nameTaken.getName());
    			return "redirect:main";
    		}
    	}
    }
    
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest currentUser) {
    	currentUser.getSession().invalidate();
        return "logout";
    }
    
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerPage(Model model) {
    	model.addAttribute("usermessage", "");
    	model.addAttribute("passwordmessage", "");
    	return "register";
    }
    
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(String name, String password, String confirmPassword, Model model, HttpServletRequest currentUser){
    	User nameTaken = userDao.findByName(name);
    	if (nameTaken != null) {
    		model.addAttribute("usermessage", "The name " + name + " already exists!");
    		model.addAttribute("passwordmessage", "");
    		return "register";
    	}
    	else if (!(password.equals(confirmPassword))) {
    		model.addAttribute("passwordmessage", "Confirmation does not match original password!");
    		return "register";
    	}
    	else {
    		User registeredUser = new User(name, password, "");
    		userDao.save(registeredUser);
    		model.addAttribute("passwordmessage", "");
    		model.addAttribute("usermessage", "Account created!");
    		return "register";
    	}
    }
    
    @RequestMapping(value = "/usrsettings", method = RequestMethod.GET)
    public String getUserSettings(HttpServletRequest userStatus) {
    	return loginCheck(userStatus, "usrsettings");
    }
    
    @RequestMapping(value = "/usrsettings", method = RequestMethod.POST, params="editUser")
    public String editUsername(HttpServletRequest userStatus, Model model, String name, String password) {
    	User currentName = userDao.findByName((String)userStatus.getSession().getAttribute("session_id"));
    	User nameTaken = userDao.findByName(name);
    	if (nameTaken != null) {
    		model.addAttribute("usermessage", "The name " + name + " already exists!");
    		model.addAttribute("passwordmessage", "");
    		return loginCheck(userStatus, "usrsettings");
    	}
    	else if (!(password.equals(currentName.getPassword()))) {
    		model.addAttribute("passwordmessage", "Invalid Password!");
    		return loginCheck(userStatus, "usrsettings");
    	}
    	else {
    		currentName.setName(name);
    		userDao.save(currentName);
    		model.addAttribute("passwordmessage", "Username has been changed!");
    		model.addAttribute("usermessage", "");
    		return loginCheck(userStatus, "redirect:logout");
    	}
    }
    
    @RequestMapping(value = "/usrsettings", method = RequestMethod.POST, params="editPassword")
    public String editPassword(HttpServletRequest userStatus, Model model, String newPassword, String confirmNewPassword, String oldPassword) {
    	User user = userDao.findByName((String)userStatus.getSession().getAttribute("session_id"));
    	if (!(newPassword.equals(confirmNewPassword))) {
    		model.addAttribute("invalidConfirmPassword", "Desired Password does not match confirmation!");
    		model.addAttribute("invalidPassword", "");
    	}
    	else if (!(oldPassword.equals(user.getPassword()))) {
    		model.addAttribute("invalidConfirmPassword", "");
    		model.addAttribute("invalidPassword", "Invalid Password!");
    	} else {
    		user.setPassword(newPassword);
    		userDao.save(user);
    		model.addAttribute("invalidConfirmPassword", "");
    		model.addAttribute("invalidPassword", "Password has been changed!");
    	}
    	return loginCheck(userStatus, "usrsettings");
    }
}