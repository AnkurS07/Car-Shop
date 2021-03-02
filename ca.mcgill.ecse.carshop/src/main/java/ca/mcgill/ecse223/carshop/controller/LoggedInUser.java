package ca.mcgill.ecse223.carshop.controller;

public class LoggedInUser {
	
	private String userName;
	
	private static LoggedInUser loggedInUserInstance = null;

	private LoggedInUser() {
	}
	
	public static LoggedInUser getInstance() {
		if(loggedInUserInstance == null) {
			loggedInUserInstance = new LoggedInUser();
		} 
		return loggedInUserInstance;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public boolean setUserName(String userName) {
		boolean wasSet = false;
		this.userName = userName;
		wasSet = true;
		return wasSet;
	}
}
