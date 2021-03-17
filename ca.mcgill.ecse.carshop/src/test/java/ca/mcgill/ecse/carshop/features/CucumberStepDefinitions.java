package ca.mcgill.ecse.carshop.features;

import static ca.mcgill.ecse223.carshop.controller.AppointmentController.dateToTime;
import static ca.mcgill.ecse223.carshop.controller.AppointmentController.parseDate;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import ca.mcgill.ecse.carshop.application.CarShopApplication;
import ca.mcgill.ecse.carshop.model.Appointment;
import ca.mcgill.ecse.carshop.model.BookableService;
import ca.mcgill.ecse.carshop.model.Business;
import ca.mcgill.ecse.carshop.model.BusinessHour;
import ca.mcgill.ecse.carshop.model.BusinessHour.DayOfWeek;
import ca.mcgill.ecse.carshop.model.CarShop;
import ca.mcgill.ecse.carshop.model.ComboItem;
import ca.mcgill.ecse.carshop.model.Customer;
import ca.mcgill.ecse.carshop.model.Garage;
import ca.mcgill.ecse.carshop.model.Owner;
import ca.mcgill.ecse.carshop.model.Service;
import ca.mcgill.ecse.carshop.model.ServiceBooking;
import ca.mcgill.ecse.carshop.model.ServiceCombo;
import ca.mcgill.ecse.carshop.model.Technician;
import ca.mcgill.ecse.carshop.model.TimeSlot;
import ca.mcgill.ecse.carshop.model.Technician.TechnicianType;
import ca.mcgill.ecse.carshop.model.User;
import ca.mcgill.ecse223.carshop.controller.AppointmentController;
import ca.mcgill.ecse223.carshop.controller.CarShopController;
import ca.mcgill.ecse223.carshop.controller.TOBusiness;
import ca.mcgill.ecse223.carshop.controller.TOTimeSlot;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * Steps definitions for the cucumber scenarios. These steps should not call the model directly and instead always pass through the controller.
 * Implement the code here to do the actions described in the test scenarios and implement the functionality in the controller.
 * Include extensive assertions to make there are no bugs in the system. Do not duplicate methods.
 * @author maxbo
 * @author Julien Lefebvre
 * @author Ankur
 * @author Debopriyo
 *
 */
public class CucumberStepDefinitions {

	private CarShop carShop;
	private String error;
	private int errorCntr;
	
	private static Business business;
	private static Owner owner;
	

	private static List<Map<String, String>> preservedProperties;
	private static List<BookableService> allBookableServices = null;
	
	int numberOfAccounts=0;

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

	/*
	@Then("the account shall have username {string} and password {string}")
	public void the_account_shall_have_username_and_password(String string, String string2) {
		for (Customer customer : carShop.getCustomers()) {
			if (customer.getUsername().equals(string)) {
				assertTrue(customer.getPassword().equals(string2));
			}
		}
	}
	*/

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
			
			CarShopController.createCustomer(string, "testPassword");
			
			assertTrue(CarShopController.hasUserWithUsername(string));
		} catch (Exception e) {
			error += e.getMessage();
			errorCntr ++;
		}
	}

	@When("the user tries to update account with a new username {string} and password {string}")
	public void the_user_tries_to_update_account_with_a_new_username_and_password(String string, String string2) {
		try {
		
			CarShopController.updateCustomer(string, string2);
		} catch (Exception e) {
			error += e.getMessage();
			errorCntr ++;
		}
	}

	@Then("the account shall not be updated")
	public void the_account_shall_not_be_updated() {
		
		assertTrue(errorCntr > 0);
	}

	@Given("an owner account exists in the system")
	public void an_owner_account_exists_in_the_system() {
		
		try {
			owner = new Owner("owner", "owner", carShop);
			carShop.setOwner(owner);
		} catch (Exception e) {
			error += e.getMessage();
			errorCntr ++;
		}
	}

	@Given("a business exists in the system")
	public void a_business_exists_in_the_system() {
		
		carShop.setBusiness(business);
	}

	@Given("the Owner with username {string} is logged in")
	public void the_owner_with_username_is_logged_in(String string) {
		
		try {
			CarShopApplication.setLoggedInUser(string);
			
			assertEquals(string, CarShopApplication.getLoggedInUser());
		}
		catch(Exception e) {
			error += e.getMessage();
			errorCntr ++;
		}


	}


	@When("{string} initiates the addition of the service {string} with duration {string} belonging to the garage of {string} technician")
	public void initiates_the_addition_of_the_service_with_duration_belonging_to_the_garage_of_technician(String string, String string2, String string3, String string4) {
	

		try {
			for(Technician t: carShop.getTechnicians()) {
				if(t.getType().name().equals(string4)){
					Garage g = t.getGarage();
					CarShopController.addService(string2, Integer.parseInt(string3), g);
					
				}
			}
		}

		catch (Exception e) {
			error += e.getMessage();
			errorCntr ++;
		}

	}

	@Then("the service {string} shall exist in the system")
	public void the_service_shall_exist_in_the_system(String string) {
	
		boolean exists = false;
		for(int i=0; i<carShop.getBookableServices().size() ; i++) {
			if(carShop.getBookableService(i).getName().equals(string)) {
				exists = true;
			}

		}
		assertTrue(exists);
	}

	@Then("the service {string} shall belong to the garage of {string} technician")
	public void the_service_shall_belong_to_the_garage_of_technician(String string, String string2) {
		
		boolean c = false;
		for(Technician t: carShop.getTechnicians()) {
			if(t.getType().name().equals(string2)){

				Garage g = t.getGarage();
				if(g.getServices().contains(Service.getWithName(string))) {
					c=true;
				}
			}
		}
		assertTrue(c);

	}

	@Then("the number of services in the system shall be {string}")
	public void the_number_of_services_in_the_system_shall_be(String string) {
		int services = 0;

		for(BookableService b : carShop.getBookableServices()) {
			if(b instanceof Service) {
				services++;
			}
		}
		assertEquals(Integer.parseInt(string), services);

	}

	@Then("an error message with content {string} shall be raised")
	public void an_error_message_with_content_shall_be_raised(String string) {
		
		assertTrue(error.contains(string));
	}

	@Then("the service {string} shall not exist in the system")
	public void the_service_shall_not_exist_in_the_system(String string) {
		
		assertNull(Service.getWithName(string));
	}

	@Given("the following services exist in the system:")
	public void the_following_services_exist_in_the_system(io.cucumber.datatable.DataTable dataTable) {
		
		try {
			List<Map<String, String>> rows = dataTable.asMaps();
			for (Map<String, String> columns : rows) {
				Garage g = null;
				for(Technician t: carShop.getTechnicians()) {
					if(t.getType().name().equals(columns.get("garage"))){
						g = t.getGarage();

					}
				}
				
				String userName = CarShopApplication.getLoggedInUser();
				CarShopApplication.setLoggedInUser("owner"); 
				CarShopController.addService(columns.get("name"), Integer.parseInt(columns.get("duration")), g);
				CarShopApplication.setLoggedInUser(userName); 

			}
		} catch (Exception e) {
			error += e.getMessage();
			errorCntr ++;
		}
	}

	@Then("the service {string} shall still preserve the following properties:")
	public void the_service_shall_still_preserve_the_following_properties(String string, io.cucumber.datatable.DataTable dataTable) {
	
		preservedProperties = dataTable.asMaps(String.class, String.class);
		assertEquals(Service.getWithName(string).getName(), preservedProperties.get(0).get("name"));
		assertEquals(((Service)Service.getWithName(string)).getDuration(), Integer.parseInt(preservedProperties.get(0).get("duration")));
		assertEquals(((Service)Service.getWithName(string)).getGarage().getTechnician().getType().name(), preservedProperties.get(0).get("garage"));
		
	}

	@Given("the user with username {string} is logged in")
	public void the_user_with_username_is_logged_in(String string) {
		
		try {
			CarShopApplication.setLoggedInUser(string);
			
			assertEquals(string, CarShopApplication.getLoggedInUser());
		} catch (Exception e) {
			error += e.getMessage();
			errorCntr ++;
		}
	}

	@When("{string} initiates the update of the service {string} to name {string}, duration {string}, belonging to the garage of {string} technician")
	public void initiates_the_update_of_the_service_to_name_duration_belonging_to_the_garage_of_technician(String string, String string2, String string3, String string4, String string5) {
		

		try {
			if(string3.equals("<name>")) {
				CarShopController.updateService("","",0,null);
			}
			for(Technician t: carShop.getTechnicians()) {
				if(t.getType().name().equals(string5)){
					Garage g = t.getGarage();
					CarShopController.updateService(string2, string3, Integer.parseInt(string4), g);
				}
			}
		}
		catch(Exception e) {
			error += e.getMessage();
			errorCntr ++;

		}
	}

	@Then("the service {string} shall be updated to name {string}, duration {string}")
	public void the_service_shall_be_updated_to_name_duration(String string, String string2, String string3) {
		
		Service service = (Service) BookableService.getWithName(string2);
		assertEquals(string2, service.getName());
		assertEquals(Integer.parseInt(string3),service.getDuration());


	}

	
	@When("{string} initiates the definition of a service combo {string} with main service {string}, services {string} and mandatory setting {string}")
	public void initiates_the_definition_of_a_service_combo_with_main_service_services_and_mandatory_setting(String userName, String name, String mainService, String service, String mandatory) {
		// Write code here that turns the phrase above into concrete actions
		try {
			CarShopController.defineServiceCombo(userName, name, service, mandatory, mainService);
		}
		catch(Exception e){
			error += e.getMessage();
			errorCntr++;
		}
	}

	@Then("the service combo {string} shall exist in the system")
		public void the_service_combo_shall_exist_in_the_system(String string) {
		    // Write code here that turns the phrase above into concrete actions
		boolean comboExists = false;
		if(BookableService.getWithName(string) !=null && BookableService.getWithName(string) instanceof ServiceCombo) {
			comboExists = true;
		}
		assertTrue(comboExists);
	}
	
	@Then("the service combo {string} shall contain the services {string} with mandatory setting {string}")
	public void the_service_combo_shall_contain_the_services_with_mandatory_setting(String string, String string2, String string3) {
		    // Write code here that turns the phrase above into concrete actions
		ServiceCombo serviceCombo = (ServiceCombo) ServiceCombo.getWithName(string);

		String[] services = string2.split(",");
		String[] mandatory = string3.split(",");

		for (int i = 0; i < services.length; i++) {
			Service service1 = (Service) Service.getWithName(services[i]);
			boolean mandatory1 = Boolean.parseBoolean(mandatory[i]);
			ComboItem comboItem = new ComboItem(mandatory1, service1, serviceCombo);

			assertEquals(true, serviceCombo.getServices().contains(comboItem));
			assertEquals(true, serviceCombo.getServices().contains(comboItem));
		}	
	}
		
	@Then("the main service of the service combo {string} shall be {string}")
	public void the_main_service_of_the_service_combo_shall_be(String string, String string2) {
		    // Write code here that turns the phrase above into concrete actions
		assertEquals(((ServiceCombo) BookableService.getWithName(string)).getMainService().getService().getName(),string2);
	}
		
	@Then("the service {string} in service combo {string} shall be mandatory")
	public void the_service_in_service_combo_shall_be_mandatory(String string, String string2) {
		    // Write code here that turns the phrase above into concrete actions
		for (int i = 0; i < carShop.getBookableServices().size(); i++) {
			if (carShop.getBookableServices().get(i) instanceof ServiceCombo
					&& carShop.getBookableServices().get(i).getName().equals(string)) {
				ServiceCombo serviceC = (ServiceCombo) carShop.getBookableServices().get(i);
				for (ComboItem comboItem : serviceC.getServices()) {
					if (comboItem.getService().getName().equals(string2)) {
						assertEquals(true, comboItem.getMandatory());
					}
				}
			}
		}
	}
	
	@Then("the service combo {string} shall not exist in the system")
	public void the_service_combo_shall_not_exist_in_the_system(String string) {
		    // Write code here that turns the phrase above into concrete actions
		assertNull(ServiceCombo.getWithName(string));
	}

	@Then("the number of service combos in the system shall be {string}")
	public void the_number_of_service_combos_in_the_system_shall_be(String string) {
		    // Write code here that turns the phrase above into concrete actions
		allBookableServices = carShop.getBookableServices();
		int numCombos = 0;
		for (int i = 0; i < allBookableServices.size(); i++) {
			if (allBookableServices.get(i) instanceof ServiceCombo) {
				numCombos++;
			}
		}

		assertEquals(Integer.parseInt(string), numCombos);
	}

	@Given("the following service combos exist in the system:")
	public void the_following_service_combos_exist_in_the_system(io.cucumber.datatable.DataTable dataTable) {
		    // Write code here that turns the phrase above into concrete actions
		    // For automatic transformation, change DataTable to one of
		    // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
		    // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
		    // Double, Byte, Short, Long, BigInteger or BigDecimal.
		    //
		    // For other transformations you can register a DataTableType.
		List<Map<String, String>> subContent = dataTable.asMaps(String.class, String.class);
		for (Map<String, String> content : subContent) {
			ServiceCombo combo = new ServiceCombo(content.get("name"), carShop);
			carShop.addBookableService(combo);
			Service main = (Service) findServiceByName(content.get("mainService"));

			// extract the combo items
			String itemList = content.get("services");
			String[] items = itemList.split(",");
			String mandatoryList = content.get("mandatory");
			String[] mandatory = mandatoryList.split(",");

			for (int i = 0; i < items.length; i++) {

				BookableService thisService = findServiceByName(items[i]);
				boolean thisMandatory = Boolean.parseBoolean(mandatory[i]);
				if (thisService.getClass().equals(Service.class)) {
					Service serviceItem = (Service) thisService;
					if (items[i].equals(main.getName())) {
						combo.setMainService(new ComboItem(true, serviceItem, combo));
					} else {
						combo.addService(thisMandatory, serviceItem);
					}
				}

			}
			

		}
	}

	@Then("the service combo {string} shall preserve the following properties:")
	public void the_service_combo_shall_preserve_the_following_properties(String string, io.cucumber.datatable.DataTable dataTable) {
		    // Write code here that turns the phrase above into concrete actions
		    // For automatic transformation, change DataTable to one of
		    // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
		    // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
		    // Double, Byte, Short, Long, BigInteger or BigDecimal.
		    //
		    // For other transformations you can register a DataTableType.
		String services = "";
		String mandatory = "";
		List<Map<String, String>> list = dataTable.asMaps(String.class, String.class);
		for (int i = 0; i < list.size(); i++) {

			if (list.get(i).get("name").contains(string)) {
				ServiceCombo serviceCombo = (ServiceCombo) BookableService.getWithName(string);
				assertEquals(serviceCombo.getMainService().getService().getName(), list.get(i).get("mainService"));

				for (int j = 0; j < serviceCombo.getServices().size(); j++) {
					if (j == serviceCombo.getServices().size() - 1) {
						mandatory += serviceCombo.getServices().get(j).getMandatory();
						services += serviceCombo.getServices().get(j).getService().getName();
					} else {
						services += serviceCombo.getServices().get(j).getService().getName() + ",";
						mandatory += serviceCombo.getServices().get(j).getMandatory() + ",";
					}
				}
				assertEquals(services, list.get(i).get("services"));
				assertEquals(mandatory, list.get(i).get("mandatory"));

			}

		}
	}
	
	@When("{string} initiates the update of service combo {string} to name {string}, main service {string} and services {string} and mandatory setting {string}")
	public void initiates_the_update_of_service_combo_to_name_main_service_and_services_and_mandatory_setting(
			String username, String name, String newName, String mainService, String servicesString, String mandatoryString) {
		try {
			CarShopController.updateServiceCombo(username, name, newName, servicesString, mandatoryString, mainService);
		} 
		catch (Exception e) {
			error += e.getMessage();
			errorCntr++;
		}
	}
		
	@Then("the service combo {string} shall be updated to name {string}")
	public void the_service_combo_shall_be_updated_to_name(String string, String string2) {
		    // Write code here that turns the phrase above into concrete actions
		for (int i = 0; i < carShop.getBookableServices().size(); i++) {
			if (carShop.getBookableServices().get(i) instanceof ServiceCombo
					&& carShop.getBookableServices().get(i).getName().equals(string)) {

				assertEquals(string2, carShop.getBookableService(i).getName());

			}
		}
	}




	
	private BookableService findServiceByName(String serviceName) {
		BookableService thisService = null;

		List<BookableService> serviceList = carShop.getBookableServices();
		for (int i = 0; i < serviceList.size(); i++) {
			thisService = serviceList.get(i);
			if (thisService.getName().equals(serviceName)) {
				return thisService;
			}
		}

		return thisService;
	}    
    
	/* LogIn feature */
	// done
    @When("the user tries to log in with username {string} and password {string}")
    public void attemptedLogInWithUsernameAndPassword(String username, String password) {
    	try {
			CarShopController.logIn(username, password);
		} catch (Exception e) {
			error += e.getMessage();
			errorCntr ++;
		}
    }
    
    // done
    @Then("the user should be successfully logged in")
    public void userShouldBeLoggedIn() {
    	assertFalse(error.contains("Username/password not found"));
    }
    
    // done
    @Then("the user should not be logged in")
    public void userShouldNotBeLoggedIn() {
    	assertTrue(error.contains("Username/password not found"));
    }
   
   
    @Then("a new account shall be created") //done
    public void newAccountShallBeCreated() {
    	try {
        	assertTrue(CarShopController.createdAccount());
    	} catch (Exception e) {
			error += e.getMessage();
			errorCntr ++;
    	}
    }
    

	@Then("the account shall have username {string} and password {string}") // done
	public void theAccountShallHaveUsernameAndPassword(String string, String string2) {
		List<User> users = new ArrayList<User>();
		users.addAll(carShop.getTechnicians());
		users.addAll(carShop.getCustomers());
		users.add(carShop.getOwner());
		User currentUser;
		
		if(User.hasWithUsername(string)) {
			currentUser = User.getWithUsername(string);		
			if (currentUser.getPassword().equals(string2)) {
				assertTrue(currentUser.getPassword().equals(string2));
			}		
		}
	}
    
    @Then("the account shall have username {string}, password {string} and technician type {string}") // done
    public void theAccountShallHaveUsernameAndPasswordAndType(String username, String password, String type) {
    	List<User> users = new ArrayList<User>();
		users.addAll(carShop.getTechnicians());
		users.addAll(carShop.getCustomers());
		users.add(carShop.getOwner());
		User currentUser;
    	
		TechnicianType technicianType = null;
    	for(Technician t: carShop.getTechnicians()){
    		if(t.getType().name().equals(type)) {
    			technicianType = t.getType();
    		}
    	}
    	
    	if(User.hasWithUsername(username)) {
			currentUser = User.getWithUsername(username);		
			if (currentUser.getPassword().equals(password)) {
				assertTrue(currentUser.getPassword().equals(password));
				assertTrue(((Technician) currentUser).getType().equals(technicianType));
			}
		}
    }
    
    @Then("the corresponding garage for the technician shall be created") // Done
    public void garageForTechnicianShallBeCreated() {
    	try {
    		assertTrue(CarShopController.createdGarage());
    	} catch (Exception e) {
			error += e.getMessage();
			errorCntr ++;
    	}
    }
        
    // done
    @When("the user tries to add new business hours on {string} from {string} to {string} to garage belonging to the technician with type {string}")
    public void userTriesToAddNewBusinessHoursToGarageOfTechnicianWithType(String day, String startTime, String endTime, String type) {
    	try {
			CarShopController.addHoursToGarageOfTechnicianType(day, startTime, endTime, type);
    	} catch (Exception e) {
			error += e.getMessage();
			errorCntr ++;
    	}
    }
	
	
	//Appointment feature

	  @Given("the business has the following opening hours")
	  public void the_business_has_the_following_opening_hours(
	      io.cucumber.datatable.DataTable dataTable) {
		
		if(carShop.getBusiness() == null) {
			carShop.setBusiness(new Business("TheCarShop", "8888 Rue Somewhere, Montreal, QC, Canada",
		            "000-000-0000", "car@carshop.com", carShop));
		}
	    Business business = carShop.getBusiness();
	    
	    List<List<String>> rows = dataTable.asLists();

	    for (int i = 1; i < rows.size(); i++) {
	      List<String> columns = rows.get(i);

	      DayOfWeek day = DayOfWeek.valueOf(columns.get(0));
	      Time startTime = dateToTime(parseDate(columns.get(1), "HH:mm"));
	      Time endTime = dateToTime(parseDate(columns.get(2), "HH:mm"));

	      BusinessHour hours = new BusinessHour(day, startTime, endTime, carShop);
	      business.addBusinessHour(hours);
	    }
	  }

	  @Given("all garages has the following opening hours")
	  public void all_garages_has_the_following_opening_hours(
	      io.cucumber.datatable.DataTable dataTable) {
	    CarShop carshop = CarShopApplication.getCarShop();
	    Business business = carshop.getBusiness();
	    List<List<String>> rows = dataTable.asLists();

	    for (int i = 1; i < rows.size(); i++) {
	      List<String> columns = rows.get(i);

	      DayOfWeek day = DayOfWeek.valueOf(columns.get(0));
	      Time startTime = dateToTime(parseDate(columns.get(1), "HH:mm"));
	      Time endTime = dateToTime(parseDate(columns.get(2), "HH:mm"));

	      // TODO: Implement finding and adding opening hours
	      for (Garage garage : carshop.getGarages()) {
	          garage.addBusinessHour(new BusinessHour(day, startTime, endTime, carshop));
	      }
	    }
	  }

	  @Given("the business has the following holidays")
	  public void the_business_has_the_following_holidays(io.cucumber.datatable.DataTable dataTable) {
	    CarShop carShop = CarShopApplication.getCarShop();
	    Business business = carShop.getBusiness();
	    List<List<String>> rows = dataTable.asLists();

	    for (int i = 1; i < rows.size(); i++) {
	      List<String> columns = rows.get(i);

	      Date startDate = parseDate(columns.get(0), "yyyy-MM-dd");
	      Date endDate = parseDate(columns.get(1), "yyyy-MM-dd");
	      Time startTime = dateToTime(parseDate(columns.get(2), "HH:mm"));
	      Time endTime = dateToTime(parseDate(columns.get(3), "HH:mm"));

	      business.addHoliday(new TimeSlot(startDate, startTime, endDate, endTime, carShop));
	    }
	  }

	  @Given("the following appointments exist in the system:")
	  public void the_following_appointments_exist_in_the_system(io.cucumber.datatable.DataTable dataTable) {
	    List<List<String>> rows = dataTable.asLists();
	    
		try {
			String currentUser = CarShopApplication.getLoggedInUser();
		    
		    for (int i = 1; i < rows.size(); i++) {
		      List<String> columns = rows.get(i);

		      String customerName = columns.get(0);
		      String mainService = columns.get(1);
		      List<String> optServices = Arrays.asList(columns.get(2).split("\\,"));
		      Date date = parseDate(columns.get(3), "yyyy-MM-dd");

		      Time first = null;
		      for (String times : columns.get(4).split("\\,")) {
		        if (first == null)
		        	first = dateToTime(parseDate(times.split("-")[0], "HH:mm"));
		        	break;
		      }
		      CarShopApplication.setLoggedInUser(customerName);
		      // TODO: Add appointments to the system
		      AppointmentController.makeAppointment(true, customerName, date, first,
		          Arrays.asList(columns.get(4).split("\\,")),
		          mainService, optServices.toArray(new String[optServices.size()])
		          );
		    }
		    CarShopApplication.setLoggedInUser(currentUser);
		} catch (Exception e) {
			error += e.getMessage();
			errorCntr ++;
		}
    }
    
    // done
    @Then("the garage belonging to the technician with type {string} should have opening hours on {string} from {string} to {string}")
    public void garageOfTechnicianWithTypeShouldHaveOpeningHoursOnFromTo(String type, String day, String startTime, String endTime) {
    	try {
    		assertTrue(CarShopController.addedHoursToGarageOfTechnicianType(day, startTime, endTime, day));
    	} catch (Exception e) {
			error += e.getMessage();
			errorCntr ++;
    	}
    }
    
    //Scenario Outline: Remove opening hours successfully
    @Given("there are opening hours on {string} from {string} to {string} for garage belonging to the technician with type {string}")
    public void openingHoursForGarageOfTechnicianWithType(String day, String startTime, String endTime, String type) {
    	try {
			CarShopController.hasOpeningHoursToGarageOfTechnicianType(day, startTime, endTime, type);
    	} catch (Exception e) {
			error += e.getMessage();
			errorCntr ++;
		}
	  }

	  public static void addAppointmentToCarShop(String customerName, String mainServiceString,
	      List<String> optServiceStrings, Date date, List<TimeSlot> timeSlots) {
	    CarShop carshop = CarShopApplication.getCarShop();
	    User u = User.getWithUsername(customerName);
	    if (!(u instanceof Customer)) {
	      throw new IllegalArgumentException("Attempted to add appointment to non-customer!");
	    }
	    ;
	    Customer customer = (Customer) u;
	    BookableService mainService = null;
	    for (BookableService s : carshop.getBookableServices()) {
	      if (s.getName().equals(mainService)) {
	        mainService = s;
	      }
	    }

	    List<BookableService> optionalServices = new ArrayList<>();
	    for (String optName : optServiceStrings) {
	      for (BookableService s : carshop.getBookableServices()) {
	        if (s.getName().equals(optName)) {
	          optionalServices.add(s);
	        }
	      }
	    }

	    Appointment a = new Appointment(customer, mainService, carshop);
	    a.addServiceBooking((Service) mainService, timeSlots.get(0));
	    for (int i = 0; i < optionalServices.size(); i++) {
	      a.addServiceBooking((Service) optionalServices.get(i), timeSlots.get(i + 1));
	    }

	    customer.addAppointment(a);
	  }

	  @Given("{string} is logged in to their account")
	  public void is_logged_in_to_their_account(String string) throws Exception {
	    CarShopApplication.setLoggedInUser(string);
	  }

	  @When("{string} schedules an appointment on {string} for {string} with {string} at {string}")
	  public void schedules_an_appointment_on_for_with_at(String customer, String dateString,
	      String mainServiceString,
	      String optServiceStrings, String startTimeStrings) {

	    appointmentCount = CarShopApplication.getCarShop().getAppointments().size();
	    try {
	      // TODO: Implement this Controller method and set Error message

	      Date date = parseDate(dateString, "yyyy-MM-dd");

	      // Used a stream
	      List<Time> times = Arrays.stream(startTimeStrings.split("\\,"))
	          .map(s -> dateToTime(parseDate(s, "HH:mm"))).collect(Collectors.toList());

	      Service mainService = AppointmentController.findService(mainServiceString);
	      
	      List<Service> optionalServices;
	      List<TimeSlot> timeSlots;
	      if(optServiceStrings.equals("")) {
	    	  optionalServices = new ArrayList<Service>();
	    	  Time start = times.get(0);
	    	  
	    	  
	    	  SimpleDateFormat df = new SimpleDateFormat("HH:mm");
	    	  java.util.Date d = df.parse(startTimeStrings); 
	    	  Calendar cal = Calendar.getInstance();
	    	  cal.setTime(d);
	    	  cal.add(Calendar.MINUTE, mainService.getDuration());
	    	  Date dd = new java.sql.Date(cal.getTime().getTime());
	    	  Time end = new Time(dd.getTime());

	    	  timeSlots = new ArrayList<TimeSlot>();
	    	  timeSlots.add(new TimeSlot(date, start, date, end, carShop));
	    	  
	      }
	      else {
	    	  optionalServices = AppointmentController
	    	          .findServices(Arrays.asList(optServiceStrings.split("\\,")));
	    	  timeSlots = AppointmentController.generateTimeSlotsFromStarts(date, times,
	    	          mainService, optionalServices.toArray(new Service[optionalServices.size()]));
	      }
	      
	      

	      AppointmentController.makeAppointment(false, AppointmentController.findCustomer(customer), mainServiceString, date,
	          times.get(0), timeSlots, mainService, optionalServices);

	    } catch (Exception e) {
	      errorReportMessage = e.getMessage();
	    }
	  }

	  // Specific to AppointmentStepDefinitions
	  public static String errorReportMessage = "";

	  /**
	   * 
	   * @param string
	   */
	  @Then("the system shall report {string}")
	  public void the_system_shall_report(String string) {
	    assertEquals(string, errorReportMessage);
	  }

	  // Specific to AppointmentStepDefinitions
	  public static int appointmentCount = 0;

	  /**
	   * 
	   * @param int1
	   */
	  @Then("there shall be {int} more appointment in the system")
	  public void there_shall_be_more_appointment_in_the_system(Integer int1) {
	    CarShop carshop = CarShopApplication.getCarShop();
	    assertEquals(int1, carshop.getAppointments().size() - appointmentCount);
	  }

	  @When("{string} attempts to cancel their {string} appointment on {string} at {string}")
	  public void attempts_to_cancel_their_appointment_on_at(String customerName,
	      String mainServiceName,
	      String date, String time) {
		
		appointmentCount = CarShopApplication.getCarShop().getAppointments().size();
	    try {
	      // TODO: Implement this Controller method and set Error message
	      AppointmentController.cancelAppointment(customerName, mainServiceName,
	          parseDate(date, "yyyy-MM-dd"), dateToTime(parseDate(time, "HH:mm")));

	    } catch (Exception e) {
	      errorReportMessage = e.getMessage();
	    }
	  }

	  @Then("{string}'s {string} appointment on {string} at {string} shall be removed from the system")
	  public void s_appointment_on_at_shall_be_removed_from_the_system(String customer,
	      String mainServiceName,
	      String date, String startTime) {

	  try {
		    // TODO: use find appointment to detect removed appointments
		    Appointment app = AppointmentController.findAppointment(
		        AppointmentController.findCustomer(customer),
		        mainServiceName, parseDate(date, "yyyy-MM-dd"), dateToTime(parseDate(startTime, "HH:mm")));

		    assertNull(app);
		} catch (Exception e) {
			error += e.getMessage();
			errorCntr ++;
		}
    }
    
    // done
    @When("the user tries to remove opening hours on {string} from {string} to {string} to garage belonging to the technician with type {string}")
    public void userTriesToRemoveOpeningHoursToGarageOfTechnicianWithType(String day, String startTime, String endTime, String type) {
    	try {
    		if (!CarShopApplication.getLoggedInUser().equals(type))
    			CarShopController.removeHoursToGarageOfTechnicianType(day, startTime, endTime, type);
		} catch (Exception e) {
			error += e.getMessage();
			errorCntr ++;
		}
    }
    
    // done
    @Then("the garage belonging to the technician with type {string} should not have opening hours on {string} from {string} to {string}")
    public void garageOfTechnicianWithTypeShouldNotHaveOpeningHours(String type, String day, String startTime, String endTime) {
    	try {
    		if (!CarShopApplication.getLoggedInUser().equals(type))
    			assertFalse(CarShopController.addedHoursToGarageOfTechnicianType(day, startTime, endTime, day));
    		else if (CarShopApplication.getLoggedInUser().equals(type))
    			assertTrue(CarShopController.removedHoursToGarageOfTechnicianType(day, startTime, endTime, day));
    	} catch (Exception e) {
			error += e.getMessage();
			errorCntr ++;
    	}
    }

	  

	  public static Appointment carShopHasAppointment(Customer c, String appointmentName, Date date,
	      Time time) {
	    for (Appointment a : c.getAppointments()) {
	      if (a.getBookableService().getName().equals(appointmentName)
	          && AppointmentController.getDate(a).equals(date)
	          && AppointmentController.getTime(a).equals(time)) {
	        return a;
	      }
	    }
	    return null;
	  }

	  /**
	   * 
	   * @param int1
	   */
	  @Then("there shall be {int} less appointment in the system")
	  public void there_shall_be_less_appointment_in_the_system(Integer int1) {
	    CarShop carshop = CarShopApplication.getCarShop();
	    assertEquals(int1, appointmentCount - carshop.getAppointments().size());
	  }

	  @Then("{string} shall have a {string} appointment on {string} at {string} with the following properties")
	  public void shall_have_a_appointment_on_at_with_the_following_properties(String customer,
	      String mainService, String dateString, String timeString,
	      io.cucumber.datatable.DataTable dataTable) {
	    
		  try {
			  // TODO: Implement Finding appointments and verifying their properties

			    CarShop carshop = CarShopApplication.getCarShop();

			    Appointment app = AppointmentController.findAppointment(
			        AppointmentController.findCustomer(customer),
			        mainService, parseDate(dateString, "yyyy-MM-dd"),
			        dateToTime(parseDate(timeString, "HH:mm")));

			    assertNotNull(app);

			    List<List<String>> rows = dataTable.asLists();
			    for (int i = 1; i < rows.size(); i++) {
			      List<String> columns = rows.get(i);

			      Service colMainService = AppointmentController.findService(columns.get(0));
			      List<Service> colOptServices = AppointmentController
			          .findServices(columns.get(1).split("\\,"));
			      Date colDate = parseDate(columns.get(2), "yyyy-MM-dd");
			      List<TimeSlot> colSlots = AppointmentController.generateTimeSlots(colDate, null,
			          columns.get(3).split("\\,"));
			    }
			      // TODO: check the validity of the appointment based on parameters
			} catch (Exception e) {
				error += e.getMessage();
				errorCntr ++;
			}			    
	  }

	  @When("{string} attempts to cancel {string}'s {string} appointment on {string} at {string}")
	  public void attempts_to_cancel_s_appointment_on_at(String otherUser, String customerName,
	      String mainServiceName,
	      String date, String time) throws Exception {
		  
	    CarShopApplication.setLoggedInUser(customerName);

	    // TODO: Perform cancel test
	    attempts_to_cancel_their_appointment_on_at(otherUser, mainServiceName, date, time);
	  }

	  @When("{string} schedules an appointment on {string} for {string} at {string}")
	  public void schedules_an_appointment_on_for_at(String customer, String mainService, String date,
	      String time) {
	    // Write code here that turns the phrase above into concrete actions
	    schedules_an_appointment_on_for_with_at(customer, mainService, date, "", time);
	  }

	  @Then("{string} shall have a {string} appointment on {string} from {string} to {string}")
	  public void shall_have_a_appointment_on_from_to(String customerName, String serviceName, String stringDate,
	      String startTimes, String endTimes) {
	    
	  try {
		// Write code here that turns the phrase above into concrete actions
			Customer c = AppointmentController.findCustomer(customerName);
			Date date = parseDate(stringDate, "yyyy-MM-dd");
			
			List<String> st = Arrays.asList(startTimes.split("\\,"));
			List<String> et = Arrays.asList(endTimes.split("\\,"));
			
		    Appointment a = AppointmentController.findAppointment(c, serviceName, date, dateToTime(parseDate(st.get(0), "HH:mm")));
		   
		    assertNotNull(a);
		    assertEquals(customerName, a.getCustomer().getUsername());
		    assertTrue(serviceName.contains(a.getServiceBooking(0).getService().getName()));
		    
		    for(int i = 0; i < a.getServiceBookings().size(); i++) {
			    
			    assertEquals(dateToTime(parseDate(st.get(i), "HH:mm")), a.getServiceBooking(i).getTimeSlot().getStartTime());
			    assertEquals(dateToTime(parseDate(et.get(i), "HH:mm")), a.getServiceBooking(i).getTimeSlot().getEndTime());
			    assertEquals(date, a.getServiceBooking(i).getTimeSlot().getStartDate());
		    }
		} catch (Exception e) {
			error += e.getMessage();
			errorCntr ++;
		}  
		

	    
	  }

}
