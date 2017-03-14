package com.jacobzim.LiftingLevel.controllers;

import javax.servlet.http.HttpServletRequest;

public class AuthenticationController {

    public String loginCheck(HttpServletRequest currentUser, String directory) {
    	if (currentUser.getSession().getAttribute("session_id") == null) {
    		return "redirect:login";
    	}
    	else return directory;
    }
}
