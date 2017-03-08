package com.jacobzim.LiftingLevel.controllers;

public abstract class AbstractAuthenticationController {

	private boolean loginStatus;
	
	public String userLoggedIn() {
		if (loginStatus == false) {
			return "login";
		} else return null;
	}
    
}
