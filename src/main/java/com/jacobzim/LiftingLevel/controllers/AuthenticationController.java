package com.jacobzim.LiftingLevel.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

public class AuthenticationController {

    public String loginCheck(HttpServletRequest currentUser, String directory) {
    	if (currentUser.getSession().getAttribute("session_id") == null) {
    		return "redirect:login";
    	}
    	else return directory;
    }
}
