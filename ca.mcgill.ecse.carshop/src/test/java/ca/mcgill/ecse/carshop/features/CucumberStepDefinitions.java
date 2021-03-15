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
import ca.mcgill.ecse.carshop.model.BookableService;
import ca.mcgill.ecse.carshop.model.Business;
import ca.mcgill.ecse.carshop.model.BusinessHour.DayOfWeek;
import ca.mcgill.ecse.carshop.model.CarShop;
import ca.mcgill.ecse.carshop.model.ComboItem;
import ca.mcgill.ecse.carshop.model.Customer;
import ca.mcgill.ecse.carshop.model.Garage;
import ca.mcgill.ecse.carshop.model.Owner;
import ca.mcgill.ecse.carshop.model.Service;
import ca.mcgill.ecse.carshop.model.Technician;
import ca.mcgill.ecse.carshop.model.Technician.TechnicianType;
import ca.mcgill.ecse.carshop.model.User;
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
 *
 */
public class CucumberStepDefinitions {

	private CarShop carShop;
	private String error;
	private int errorCntr;
	private Integer appointmentCntr = 0;
	private Integer prevAppointmentCntr = 0;
	private String updateAppointmentSuccess = null;
	private Date systemDate;
	private Time systemTime;
	private boolean exception;
	private static Business business;
	private static Owner owner;
	private static User currentUser;
	private static Service currService; 

	private static List<Map<String, String>> preservedProperties;
	private static List<Map<String, String>> existingServices;
	private static List<Map<String, String>> unavailableTS;
	private static List<Map<String, String>> availableTS;
	private static List<ComboItem> combosInService;
	private static int numCombos = 0;;
	private static int numServices = 0;
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

	@Given("an owner account exists in the system")
	public void an_owner_account_exists_in_the_system() {
		// Write code here that turns the phrase above into concrete actions
		try {
			carShop.setOwner(owner);
		} catch (Exception e) {
			error += e.getMessage();
			errorCntr ++;
		}
	}

	@Given("a business exists in the system")
	public void a_business_exists_in_the_system() {
		// Write code here that turns the phrase above into concrete actions
		carShop.setBusiness(business);
	}

	@Given("the Owner with username {string} is logged in")
	public void the_owner_with_username_is_logged_in(String string) {
		// Write code here that turns the phrase above into concrete actions
		try {
			CarShopApplication.setLoggedInUser(string);
			// Make sure it worked.
			assertEquals(string, CarShopApplication.getLoggedInUser());
		}
		catch(Exception e) {
			error += e.getMessage();
			errorCntr ++;
		}


	}


	@When("{string} initiates the addition of the service {string} with duration {string} belonging to the garage of {string} technician")
	public void initiates_the_addition_of_the_service_with_duration_belonging_to_the_garage_of_technician(String string, String string2, String string3, String string4) {
		// Write code here that turns the phrase above into concrete actions

		try {
			for(Technician t: carShop.getTechnicians()) {
				if(t.getType().name().equals(string4)){
					Garage g = t.getGarage();
					CarShopController.addService(string2, Integer.parseInt(string3), g);
					currService = (Service) Service.getWithName(string2);
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
		// Write code here that turns the phrase above into concrete actions

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
		// Write code here that turns the phrase above into concrete actions
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
		////        // Write code here that turns the phrase above into concrete actions
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
		// Write code here that turns the phrase above into concrete actions
		assertTrue(error.contains(string));
	}

	@Then("the service {string} shall not exist in the system")
	public void the_service_shall_not_exist_in_the_system(String string) {
		// Write code here that turns the phrase above into concrete actions
		assertNull(Service.getWithName(string));
	}

	@Given("the following services exist in the system:")
	public void the_following_services_exist_in_the_system(io.cucumber.datatable.DataTable dataTable) {
		// Write code here that turns the phrase above into concrete actions
		// For automatic transformation, change DataTable to one of
		// E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
		// Map<K, List<V>>. E,K,V must be a String, Integer, Float,
		// Double, Byte, Short, Long, BigInteger or BigDecimal.

		try {
			List<Map<String, String>> rows = dataTable.asMaps();
			for (Map<String, String> columns : rows) {
				Garage g = null;
				for(Technician t: carShop.getTechnicians()) {
					if(t.getType().name().equals(columns.get("garage"))){
						g = t.getGarage();

					}
				}
				// Set the logged in user to owner to be able to add the given business hour.
				// Will be overwritten right after
				String userName = CarShopApplication.getLoggedInUser();
				CarShopApplication.setLoggedInUser("owner"); 
				CarShopController.addService(columns.get("name"), Integer.parseInt(columns.get("duration")), g);
				CarShopApplication.setLoggedInUser(userName); 

			}
		} catch (Exception e) {
			error += e.getMessage();
			errorCntr ++;
		}

		//
		// For other transformations you can register a DataTableType.

	}

	@Then("the service {string} shall still preserve the following properties:")
	public void the_service_shall_still_preserve_the_following_properties(String string, io.cucumber.datatable.DataTable dataTable) {
		// Write code here that turns the phrase above into concrete actions
		// For automatic transformation, change DataTable to one of
		// E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
		// Map<K, List<V>>. E,K,V must be a String, Integer, Float,
		// Double, Byte, Short, Long, BigInteger or BigDecimal.
		//
		preservedProperties = dataTable.asMaps(String.class, String.class);
		assertEquals(Service.getWithName(string).getName(), preservedProperties.get(0).get("name"));
		assertEquals(((Service)Service.getWithName(string)).getDuration(), Integer.parseInt(preservedProperties.get(0).get("duration")));
		assertEquals(((Service)Service.getWithName(string)).getGarage().getTechnician().getType().name(), preservedProperties.get(0).get("garage"));
		//        // For other transformations you can register a DataTableType.
		//        throw new io.cucumber.java.PendingException();
	}

	@Given("the user with username {string} is logged in")
	public void the_user_with_username_is_logged_in(String string) {
		// Write code here that turns the phrase above into concrete actions
		try {
			CarShopApplication.setLoggedInUser(string);
			// Make sure it worked.
			assertEquals(string, CarShopApplication.getLoggedInUser());
		} catch (Exception e) {
			error += e.getMessage();
			errorCntr ++;
		}
	}

	@When("{string} initiates the update of the service {string} to name {string}, duration {string}, belonging to the garage of {string} technician")
	public void initiates_the_update_of_the_service_to_name_duration_belonging_to_the_garage_of_technician(String string, String string2, String string3, String string4, String string5) {
		// Write code here that turns the phrase above into concrete actions

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
		// Write code here that turns the phrase above into concrete actions
		//			if(carShop.hasBookableServices()) {
		//				int i = carShop.indexOfBookableService(Service.getWithName(string));
		//				assertEquals(string2, carShop.getBookableService(i).getName());
		//				assertEquals(Integer.parseInt(string3),((Service)carShop.getBookableService(i)).getDuration());
		//			}

		Service service = (Service) BookableService.getWithName(string2);
		assertEquals(string2, service.getName());
		assertEquals(Integer.parseInt(string3),service.getDuration());


	}

}
