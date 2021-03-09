/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package ca.mcgill.ecse.carshop.application;

import ca.mcgill.ecse.carshop.model.CarShop;
import ca.mcgill.ecse223.carshop.controller.CarShopController;

public class CarShopApplication {
	
	private static CarShop carShop;
	private static java.util.Date systemDate;
	private static String loggedInUser;
	
    public String getGreeting() {
        return "Hello World!";
    }

    public static void main(String[] args) {
        System.out.println(new CarShopApplication().getGreeting());
    }
    
    public static CarShop getCarShop() {
    	if (carShop == null) {
    		carShop = new CarShop();
    	}
    	return carShop;
    	
    }
    
	/**
	 * Gets the current logged in user of the system.
	 * @return The current logged in user of the system.
	 * @throws Exception
	 */
	public static String getLoggedInUser() throws Exception {
		try {
			return loggedInUser;
		}
		catch (RuntimeException e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * Sets the current logged in user of the system.
	 * @return 
	 * @throws Exception
	 */	
	public static void setLoggedInUser(String loggedInUser) throws Exception {
		try {
			CarShopApplication.loggedInUser = loggedInUser;
		}
		catch (RuntimeException e) {
			throw new Exception(e.getMessage());
		}
	}
	
	/**
	 * Gets the current date of the system. Be careful, this uses java.util.date unlike the Date in the Umple models that is of type java.sql.Date
	 * @return The current date of the system.
	 * @throws Exception
	 */
	public static java.util.Date getSystemDate() throws Exception {
		try {
			return systemDate;
		}
		catch (RuntimeException e) {
			throw new Exception(e.getMessage());
		}
	}
	
	/**
	 * Sets the current date of the system. Uses a Singleton class to persist information.
	 * @param date Date to be set.
	 * @throws Exception
	 */
	public static void setSystemDate(java.util.Date date) throws Exception {
		try {
			CarShopApplication.systemDate = date;
		}
		catch (RuntimeException e) {
			throw new Exception(e.getMessage());
		}
	}
}
