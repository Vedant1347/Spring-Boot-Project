package com.demoSpring;

public class IdNotPresent extends RuntimeException{
	
	@Override
	public String getMessage() {
		return "Id Not Present...!";
	}
}