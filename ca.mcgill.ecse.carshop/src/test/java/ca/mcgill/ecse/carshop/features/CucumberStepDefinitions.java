package ca.mcgill.ecse.carshop.features;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import ca.mcgill.ecse.carshop.application.CarShopApplication;
import ca.mcgill.ecse.carshop.model.BusinessHour.DayOfWeek;
import ca.mcgill.ecse.carshop.model.CarShop;
import ca.mcgill.ecse.carshop.model.Customer;
import ca.mcgill.ecse.carshop.model.Technician.TechnicianType;
import ca.mcgill.ecse223.carshop.controller.CarShopController;
import ca.mcgill.ecse223.carshop.controller.TOBusiness;
import ca.mcgill.ecse223.carshop.controller.TOTimeSlot;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * Steps definitions for the cucumber scenarios. These steps should not call the model directly and instead always pass through the controller.
 * Implement the code here to do the actions described in the test scenarios and implement the functionality in the controller.
 * Include extensive assertions to make there are no bugs in the system. Do not duplicate methods.
 * @author maxbo
 * @author Julien Lefebvre
 *
 */
public class CucumberStepDefinitions {
	
	private CarShop carShop;
	private String error;
    private int errorCntr;
    
    @After 
    public void tearDown() {
    	// Delete the car shop instance between each scenario to avoid information being carried over.
    	carShop.delete();
    	try {
			CarShopApplication.setLoggedInUser(null);	// Set the logged in user to null to avoid information being carried over.
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @Given("a Carshop system exists")
    public void thereIsACarSHopSystem() {
    	carShop = CarShopApplication.getCarShop();
    	error = "";
        errorCntr = 0;
    }
    
    @Given("an owner account exists in the system with username {string} and password {string}")
    public void ownerExists(String userName, String password) {
    	try {
    		CarShopController.createOwner(userName, password);
    	} catch (Exception e) {
    		error += e.getMessage();
    		errorCntr ++;
    	}
    }
    
    @Given("the following customers exist in the system:")
    public void customerExists(DataTable table) {
    	// In the background section, the data are mapped to Data tables.
    	try {
    		List<Map<String, String>> rows = table.asMaps();
    		for (Map<String, String> columns : rows) {
    			CarShopController.createCustomer(columns.get("username"), columns.get("password"));
    	    }
    	} catch (Exception e) {
    		error += e.getMessage();
    		errorCntr ++;
    	}
    }
    
    @Given("the following technicians exist in the system:")
    public void technicianExists(DataTable table) {
    	// In the background section, the data are mapped to Data tables.
    	try {
    		List<Map<String, String>> rows = table.asMaps();
    		for (Map<String, String> columns : rows) {
    			CarShopController.createTechnician(columns.get("username"), columns.get("password"), TechnicianType.valueOf(columns.get("type")));
    	    }
    	} catch (Exception e) {
    		error += e.getMessage();
    		errorCntr ++;
    	}
    }
    
    @Given("each technician has their own garage")
    public void eachTechnicianHasTheirOwnGarage() {
    	try {
    		CarShopController.assignTechniciansToGarages();
    	} catch (Exception e) {
    		error += e.getMessage();
    		errorCntr ++;
    	}
    }
    
    @Given("no business exists")
    public void noBusinessExists() {
    	assertNull(CarShopController.getBusiness());
    }
    
    @Given("the system's time and date is {string}")
    public void systemTimeAndDateIs(String date) {
    	// Uses the current singleton class to persist the date. Should be revisited later.
    	try {
    		// Changes string format to date.
    		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd+hh:mm");
    		java.util.Date d = sdf.parse(date);
    		CarShopApplication.setSystemDate(d);
    		assertEquals(d,CarShopApplication.getSystemDate());
    	}
	    catch (Exception e) {
			error += e.getMessage();
			errorCntr ++;
		}
    }
    
    @Given("the user is logged in to an account with username {string}")
    public void userIsLoggedIn(String userName) throws Exception {
    	try {
    		CarShopApplication.setLoggedInUser(userName);
        	// Make sure it worked.
        	assertEquals(userName, CarShopApplication.getLoggedInUser());
    	} catch (Exception e) {
    		error += e.getMessage();
    		errorCntr ++;
    	}
    }
    
    @When("the user tries to set up the business information with new {string} and {string} and {string} and {string}")
    public void setUpBusinessInfo(String name, String address, String phoneNumber, String email) {
    	try {
    		CarShopController.SetUpBusinessInformation(name, address, phoneNumber, email);
    	} catch (Exception e) {
    		error += e.getMessage();
    		errorCntr ++;
    	}
    }
    
    @Then("a new business with new {string} and {string} and {string} and {string} shall {string} created")
    public void theBusinessHasBeenCreated(String name, String address, String phoneNumber, String email, String result) {
    	if(errorCntr != 0) {
    		// If the business could not be created, no need to check anything.
    		assertEquals(result,"not be");
    	} else {
    		// If the business is created verify its information.
    		assertEquals(result,"be");
        	assertNotNull(CarShopController.getBusiness());
        	assertEquals(name, CarShopController.getBusiness().getName());
        	assertEquals(address, CarShopController.getBusiness().getAddress());
        	assertEquals(phoneNumber, CarShopController.getBusiness().getPhoneNumber());
        	assertEquals(email, CarShopController.getBusiness().getEmail());
    	}
    }
    
    @Then("an error message {string} shall {string} raised")
    public void anErrorMessageIsRaised(String errorMsg, String resultError) {
    	assertTrue(error.contains(errorMsg));
    	if(errorCntr != 0) {
    		// There were errors
    		assertEquals(resultError, "be");
    	} else {
    		// There was no error
    		assertEquals(resultError, "not be");
    	}
    	
    }
    
    @Given("a business exists with the following information:")
    public void businessExistsWithFollowingInformation(DataTable table) {
    	try {
    		List<Map<String, String>> rows = table.asMaps();
    		for (Map<String, String> columns : rows) {
    			// Set the logged in user to owner to be able to add the given business hour.
        		// Will be overwritten right after
    			String userName = CarShopApplication.getLoggedInUser();
    			CarShopApplication.setLoggedInUser("owner"); 
    			CarShopController.SetUpBusinessInformation(columns.get("name"), columns.get("address"), columns.get("phone number"), columns.get("email"));
    			CarShopApplication.setLoggedInUser(userName); 
    		}
    	} catch (Exception e) {
    		error += e.getMessage();
    		errorCntr ++;
    	}
    }
    
    @Given("the business has a business hour on {string} with start time {string} and end time {string}")
    public void businessHasABusinessHour(String day, String startTime, String endTime) {
    	try {
    		// Set the logged in user to owner to be able to add the given business hour.
    		// Will be overwritten by the next step in the scenario
    		String userName = CarShopApplication.getLoggedInUser();
    		CarShopApplication.setLoggedInUser("owner"); 
    		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
    		CarShopController.addBusinessHour(DayOfWeek.valueOf(day), new Time(sdf.parse(startTime).getTime()), new Time(sdf.parse(endTime).getTime()));
    		CarShopApplication.setLoggedInUser(userName); 
    	} catch (Exception e) {
    		error += e.getMessage();
    		errorCntr ++;
    	}
    }
    
    @When("the user tries to add a new business hour on {string} with start time {string} and end time {string}")
    public void userTriesToAddBusinessHour(String day, String startTime, String endTime) {
    	try {
    		// Changes string format to time.
    		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
    		CarShopController.addBusinessHour(DayOfWeek.valueOf(day), new Time(sdf.parse(startTime).getTime()), new Time(sdf.parse(endTime).getTime()));
    	} catch (Exception e) {
    		error += e.getMessage();
    		errorCntr ++;
    	}
    }
    
    @Then("a new business hour shall {string} created")
    public void aNewBusinessHourCreated(String result) {
    	if(errorCntr != 0) {
    		// There were errors
    		assertEquals(result, "not be");
    	} else {
    		// There was no error
    		assertEquals(result, "be");
    	}
    }
    
    @When("the user tries to access the business information")
    public void userTriesToAccessBusinessInformation() {
    	CarShopController.getBusiness();
    	// If the business is returned, the user can access the information.
    	assertNotNull(CarShopController.getBusiness());
    }
    
    @Then("the {string} and {string} and {string} and {string} shall be provided to the user")
    public void theInformationShallBeProvided(String name, String address, String phoneNumber, String email) {
    	// Verify the right information is provided.
    	assertEquals(name, CarShopController.getBusiness().getName());
    	assertEquals(address, CarShopController.getBusiness().getAddress());
    	assertEquals(phoneNumber, CarShopController.getBusiness().getPhoneNumber());
    	assertEquals(email, CarShopController.getBusiness().getEmail());
    }
    
    @Given("a {string} time slot exists with start time {string} at {string} and end time {string} at {string}")
    public void aTimeSlotExists(String type, String startDate, String startTime, String endDate, String endTime) {
		
		try {
	    	// Set the logged in user to owner to be able to add the given business hour.
			// Will be overwritten by the next step in the scenario
	    	String userName = CarShopApplication.getLoggedInUser();
	    	CarShopApplication.setLoggedInUser("owner"); 
			// Changes string format to date and time.
			SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdfTime = new SimpleDateFormat("hh:mm");
			CarShopController.addTimeSlot(type, 
					new Date (sdfDate.parse(startDate).getTime()), 
					new Time (sdfTime.parse(startTime).getTime()), 
					new Date (sdfDate.parse(endDate).getTime()), 
					new Time (sdfTime.parse(endTime).getTime()));
			CarShopApplication.setLoggedInUser(userName); 
		} catch (Exception e) {
    		error += e.getMessage();
    		errorCntr ++;
		}
    }
    
    @When("the user tries to add a new {string} with start date {string} at {string} and end date {string} at {string}")
    public void userTriesAddNewTimeSlot(String type, String startDate, String startTime, String endDate, String endTime) {
    	try {
    		// Changes string format to date and time.
			SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdfTime = new SimpleDateFormat("hh:mm");
			CarShopController.addTimeSlot(type, 
					new Date (sdfDate.parse(startDate).getTime()), 
					new Time (sdfTime.parse(startTime).getTime()), 
					new Date (sdfDate.parse(endDate).getTime()), 
					new Time (sdfTime.parse(endTime).getTime()));
		} catch (Exception e) {
    		error += e.getMessage();
    		errorCntr ++;
		}
    }
    
    @Then("a new {string} shall {string} be added with start date {string} at {string} and end date {string} at {string}")
    public void aNewTimeSlotShallBeAdded(String type, String result, String startDate, String startTime, String endDate, String endTime) {
    	try {
    		if(errorCntr != 0) {
    			assertEquals(result, "not be");
    		}
    		else {
    			boolean foundMatch = false;
    			// Changes string format to date and time.
    			SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
    			SimpleDateFormat sdfTime = new SimpleDateFormat("hh:mm");
    			for(TOTimeSlot t: CarShopController.getTimeSlots(type)) {
    				if(t.getStartDate().equals(new Date (sdfDate.parse(startDate).getTime())) 
    						&& t.getStartTime().equals(new Time (sdfTime.parse(startTime).getTime())) 
    						&& t.getEndDate().equals(new Date (sdfDate.parse(endDate).getTime()))
    						&& t.getEndTime().equals(new Time (sdfTime.parse(endTime).getTime()))) {
    					foundMatch = true;
    					break;
    				}
    			}
    			// Check that a match was found.
    			assertTrue(foundMatch);
    			assertEquals(result,"be");
    		}
		} catch (Exception e) {
			error += e.getMessage();
    		errorCntr ++;
		}
    }
    
    @When("the user tries to update the business information with new {string} and {string} and {string} and {string}")
    public void userTriesToUpdateBusinessInfo(String name, String address, String phoneNumber, String email) {
    	try {
    		CarShopController.updateBusinessInformation(name, address, phoneNumber, email);
    	} catch (Exception e) {
    		error += e.getMessage();
    		errorCntr ++;
    	}
    }
    
    @Then("the business information shall {string} updated with new {string} and {string} and {string} and {string}")
    public void businessInfoUpdatedWith(String result, String name, String address, String phoneNumber, String email) {
    	if(errorCntr != 0) {
    		// The business information could not be updated
    		assertEquals(result,"not be");
    	} else {
    		// The business has been updated. Verify the new information.
    		assertEquals(result,"be");
        	assertNotNull(CarShopController.getBusiness());
        	assertEquals(name, CarShopController.getBusiness().getName());
        	assertEquals(address, CarShopController.getBusiness().getAddress());
        	assertEquals(phoneNumber, CarShopController.getBusiness().getPhoneNumber());
        	assertEquals(email, CarShopController.getBusiness().getEmail());
    	}
    }
    
    @When("the user tries to change the business hour {string} at {string} to be on {string} starting at {string} and ending at {string}")
    public void userTriesToChangeBusinessHour(String currentDay, String currentStartTime, String newDay, String newStartTime, String newEndTime) {
    	try {
    		// Cast string to time
    		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
    		CarShopController.updateBusinessHour(DayOfWeek.valueOf(currentDay), new Time(sdf.parse(currentStartTime).getTime()), DayOfWeek.valueOf(newDay), new Time(sdf.parse(newStartTime).getTime()), new Time(sdf.parse(newEndTime).getTime()));
    	} catch (Exception e) {
    		error += e.getMessage();
    		errorCntr ++;
    	}
    }
    
    @Then("the business hour shall {string} be updated")
    public void businessHourUpdated(String result) {
    	if(errorCntr != 0) {
    		// There were errors
    		assertEquals(result, "not be");
    	} else {
    		// There was no error
    		assertEquals(result, "be");
    	}
    }
    
    @When("the user tries to remove the business hour starting {string} at {string}")
    public void userTriesRemoveBusinessHour(String day, String startTime) {
    	try {
    		// Cast string to time
    		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
    		CarShopController.removeBusinessHour(DayOfWeek.valueOf(day), new Time(sdf.parse(startTime).getTime()));
    	} catch (Exception e) {
    		error += e.getMessage();
    		errorCntr ++;
    	}
    }
    
    @Then("the business hour starting {string} at {string} shall {string} exist")
    public void businessHourShallExist(String day, String startTime, String result) {
    	// Cast string to time
    	SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
    	boolean exists;
		try {
			exists = CarShopController.businessHourExists(DayOfWeek.valueOf(day), new Time(sdf.parse(startTime).getTime()));
			if(result.equals("not")) {
				// The hour was removed. It doesn't exist anymore.
				assertTrue(!exists);
			} else {
				// The hour was not remove. It still exists.
				assertTrue(exists);
			}
		} catch (Exception e) {
    		error += e.getMessage();
    		errorCntr ++;
    	}
    	
    }
    
    @Then("an error message {string} shall {string} be raised")
    public void anErrorMessageShallBeRaised(String errorMsg, String resultError) {
    	if(errorCntr != 0) {
    		// There were errors. Verify the right errors were thrown with the error message.
    		assertEquals(resultError, "");
    		assertTrue(error.contains(errorMsg));
    	} else {
    		// There was no error.
    		assertEquals(resultError, "not");
    	}
    }
    
    @When("the user tries to change the {string} on {string} at {string} to be with start date {string} at {string} and end date {string} at {string}")
    public void userChangesTimeSlot(String type, String oldStartDate, String oldStartTime, String newStartDate, String newStartTime, String newEndDate, String newEndTime) {
    	try {
    		// Cast String to Time and Date.
    		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdfTime = new SimpleDateFormat("hh:mm");
			CarShopController.updateTimeSlot(type, 
							new Date (sdfDate.parse(oldStartDate).getTime()), 
							new Time (sdfTime.parse(oldStartTime).getTime()), 
							new Date (sdfDate.parse(newStartDate).getTime()), 
							new Time (sdfTime.parse(newStartTime).getTime()), 
							new Date (sdfDate.parse(newEndDate).getTime()), 
							new Time (sdfTime.parse(newEndTime).getTime()));
    	}
    	catch (Exception e) {
    		error += e.getMessage();
    		errorCntr ++;
    	}
    }
    
    @Then("the {string} shall {string} updated with start date {string} at {string} and end date {string} at {string}")
    public void timeSlotUpdated(String type, String result, String startDate, String startTime, String endDate, String endTime) {
    	// Cast String to Time and Date.
    	SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdfTime = new SimpleDateFormat("hh:mm");
    	TOTimeSlot toTimeSlot;
		try {
			toTimeSlot = CarShopController.findTimeSlot(type, 
					new Date (sdfDate.parse(startDate).getTime()), 
					new Time (sdfTime.parse(startTime).getTime()));
			
			if(errorCntr != 0) {
				// There were errors.
	    		assertEquals(result,"not be");
	    	} else {
	    		// There was not error. Verify the updated information.
	    		assertEquals(result,"be");
	        	assertNotNull(toTimeSlot);
	        	assertEquals(new Date (sdfDate.parse(startDate).getTime()), toTimeSlot.getStartDate());
	        	assertEquals(new Time (sdfTime.parse(startTime).getTime()), toTimeSlot.getStartTime());
	        	assertEquals(new Date (sdfDate.parse(endDate).getTime()), toTimeSlot.getEndDate());
	        	assertEquals(new Time (sdfTime.parse(endTime).getTime()), toTimeSlot.getEndTime());
	    	}
		} catch (Exception e) {
    		error += e.getMessage();
    		errorCntr ++;
		}
    }
    
    @When("the user tries to remove an existing {string} with start date {string} at {string} and end date {string} at {string}")
    public void userRemovesTimeSlot(String type, String startDate, String startTime, String endDate, String endTime) {
    	// Cast String to Time and Date.
    	SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdfTime = new SimpleDateFormat("hh:mm");
		try {
			CarShopController.removeTimeSlot(type, 
					new Date (sdfDate.parse(startDate).getTime()), 
					new Time (sdfTime.parse(startTime).getTime()), 
					new Date (sdfDate.parse(endDate).getTime()), 
					new Time (sdfTime.parse(endTime).getTime()));
		} catch (Exception e) {
    		error += e.getMessage();
    		errorCntr ++;
		}
    }
    
    @Then("the {string} with start date {string} at {string} shall {string} exist")
    public void timeSlotShallExist(String type, String startDate, String startTime, String result) {
    	// Cast String to Time and Date.
    	SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdfTime = new SimpleDateFormat("hh:mm");
    	TOTimeSlot toTimeSlot;
		try {
			toTimeSlot = CarShopController.findTimeSlot(type, 
					new Date (sdfDate.parse(startDate).getTime()), 
					new Time (sdfTime.parse(startTime).getTime()));
			
			if(errorCntr != 0) {
				// There were errors.
	    		assertEquals(result,"");
	    		assertNotNull(toTimeSlot);
	    	} else {
	    		// There was no error. The time slot was removed successfully.
	    		assertEquals(result,"not");
	        	assertNull(toTimeSlot);
	    	}
		} catch (Exception e) {
    		error += e.getMessage();
    		errorCntr ++;
		}
    }
    
    @Given("there is no existing username {string}")
    public void there_is_no_existing_username(String string) {
    	// The carShop is deleted in the tearDown, so there is no users
    	// Verify if it's true
        assertFalse(CarShopController.hasUserWithUsername(string));
    }

    @When("the user provides a new username {string} and a password {string}")
    public void the_user_provides_a_new_username_and_a_password(String string, String string2) {
    	try {
    		// Try to create a customer account
    		CarShopController.createCustomer(string, string2);
    	} catch (Exception e) {
    		error += e.getMessage();
    		errorCntr ++;
    	}
    }

    @Then("a new customer account shall be created")
    public void a_new_customer_account_shall_be_created() {
    	// If no errors were thrown, it means the account was created
        assertTrue(errorCntr == 0);
        // Verify if the account has been added
        assertTrue(carShop.hasCustomers());
    }

    @Then("the account shall have username {string} and password {string}")
    public void the_account_shall_have_username_and_password(String string, String string2) {
    	for (Customer customer : carShop.getCustomers()) {
    		if (customer.getUsername().equals(string)) {
    			assertTrue(customer.getPassword().equals(string2));
    		}
    	}
    }

    @Then("no new account shall be created")
    public void no_new_account_shall_be_created() {
    	// If an errors was thrown, it means the account was not created
    	assertTrue(errorCntr > 0);
    	// Verify that the number of customer has not increased 
        assertFalse(carShop.numberOfCustomers() > 1);
    }

    @Then("an error message {string} shall be raised")
    public void an_error_message_shall_be_raised(String string) {
    	assertTrue(error.contains(string));
    }

    @Given("there is an existing username {string}")
    public void there_is_an_existing_username(String string) {
    	try {
    		// Create a customer with the specified user name
    		CarShopController.createCustomer(string, "testPassword");
    		// Verify if it worked
    		assertTrue(CarShopController.hasUserWithUsername(string));
    	} catch (Exception e) {
    		error += e.getMessage();
    		errorCntr ++;
    	}
    }
    
    @When("the user tries to update account with a new username {string} and password {string}")
    public void the_user_tries_to_update_account_with_a_new_username_and_password(String string, String string2) {
    	try {
    		// Try to update customer account
    		CarShopController.updateCustomer(string, string2);
    	} catch (Exception e) {
    		error += e.getMessage();
    		errorCntr ++;
    	}
    }

    @Then("the account shall not be updated")
    public void the_account_shall_not_be_updated() {
    	// If an errors was thrown, it means the account was not updated
    	assertTrue(errorCntr > 0);
    }

}