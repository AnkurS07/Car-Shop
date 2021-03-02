package ca.mcgill.ecse223.carshop.controller;

import java.util.Date;

/**
 * Singleton class to persist a system date set by a user.
 * @author maxbo
 *
 */
public class SystemDate {

	private Date date;
	
	private static SystemDate systemDateInstance = null;

	private SystemDate() {
	}
	
	public static SystemDate getInstance() {
		if(systemDateInstance == null) {
			systemDateInstance = new SystemDate();
		} 
		return systemDateInstance;
	}
	
	public Date getCurrentDate() {
		return date;
	}
	
	public boolean setCurrentDate(Date date) {
		boolean wasSet = false;
		this.date = date;
		wasSet = true;
		return wasSet;
	}
}