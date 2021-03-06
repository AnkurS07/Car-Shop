package ca.mcgill.ecse.carshop.features;

import static ca.mcgill.ecse223.carshop.controller.AppointmentController.parseDate;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ca.mcgill.ecse.carshop.application.CarShopApplication;
import ca.mcgill.ecse.carshop.model.Appointment;
import ca.mcgill.ecse.carshop.model.Appointment.AppStatus;
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
import ca.mcgill.ecse223.carshop.controller.TOTimeSlot;
import ca.mcgill.ecse223.carshop.persistence.CarshopPersistence;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * Steps definitions for the cucumber scenarios. These steps should not call the
 * model directly and instead always pass through the controller. Implement the
 * code here to do the actions described in the test scenarios and implement the
 * functionality in the controller. Include extensive assertions to make there
 * are no bugs in the system. Do not duplicate methods.
 * 
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
	private static Garage garage;

	private static List<Map<String, String>> preservedProperties;
	private static List<BookableService> allBookableServices = null;
	
	// Specific to AppointmentStepDefinitions
	public static int appointmentCount = 0;
	
	private Appointment currentApp;

	int numberOfAccounts = 0;
	
	@Before
	public void beforeAll() {
		carShop = CarShopApplication.getCarShop();
		carShop.delete();
		this.carShop = null;
		currentApp = null;
		CarshopPersistence.save(carShop);
		try {
			CarShopApplication.setLoggedInUser(null); // Set the logged in user to null to avoid information being
														// carried over.
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@After
	public void tearDown() {
		// Delete the car shop instance between each scenario to avoid information being
		// carried over.
		carShop.delete();
		this.carShop = null;
		currentApp = null;
		CarshopPersistence.save(carShop);
		try {
			CarShopApplication.setLoggedInUser(null); // Set the logged in user to null to avoid information being
														// carried over.
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
			errorCntr++;
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
			errorCntr++;
		}
	}

	@Given("the following technicians exist in the system:")
	public void technicianExists(DataTable table) {
		// In the background section, the data are mapped to Data tables.
		try {
			List<Map<String, String>> rows = table.asMaps();
			for (Map<String, String> columns : rows) {
				CarShopController.createTechnician(columns.get("username"), columns.get("password"),
						TechnicianType.valueOf(columns.get("type")));
			}
		} catch (Exception e) {
			error += e.getMessage();
			errorCntr++;
		}
	}

	@Given("each technician has their own garage")
	public void eachTechnicianHasTheirOwnGarage() {
		try {
			CarShopController.assignTechniciansToGarages();
		} catch (Exception e) {
			error += e.getMessage();
			errorCntr++;
		}
	}

	@Given("no business exists")
	public void noBusinessExists() {
		assertNull(CarShopController.getBusiness());
	}

	@Given("the system's time and date is {string}")
	public void systemTimeAndDateIs(String date) {
		// Uses the current singleton class to persist the date. Should be revisited
		// later.
		try {
			// Changes string format to date.
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd+hh:mm");
			java.util.Date d = sdf.parse(date);
			CarShopApplication.setSystemDate(d);
			assertEquals(d, CarShopApplication.getSystemDate());
		} catch (Exception e) {
			error += e.getMessage();
			errorCntr++;
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
			errorCntr++;
		}
	}

	@When("the user tries to set up the business information with new {string} and {string} and {string} and {string}")
	public void setUpBusinessInfo(String name, String address, String phoneNumber, String email) {
		try {
			CarShopController.SetUpBusinessInformation(name, address, phoneNumber, email);
		} catch (Exception e) {
			error += e.getMessage();
			errorCntr++;
		}
	}

	@Then("a new business with new {string} and {string} and {string} and {string} shall {string} created")
	public void theBusinessHasBeenCreated(String name, String address, String phoneNumber, String email,
			String result) {
		if (errorCntr != 0) {
			// If the business could not be created, no need to check anything.
			assertEquals(result, "not be");
		} else {
			// If the business is created verify its information.
			assertEquals(result, "be");
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
		if (errorCntr != 0) {
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
				CarShopController.SetUpBusinessInformation(columns.get("name"), columns.get("address"),
						columns.get("phone number"), columns.get("email"));
				CarShopApplication.setLoggedInUser(userName);
			}
		} catch (Exception e) {
			error += e.getMessage();
			errorCntr++;
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
			CarShopController.addBusinessHour(DayOfWeek.valueOf(day), new Time(sdf.parse(startTime).getTime()),
					new Time(sdf.parse(endTime).getTime()));
			CarShopApplication.setLoggedInUser(userName);
		} catch (Exception e) {
			error += e.getMessage();
			errorCntr++;
		}
	}

	@When("the user tries to add a new business hour on {string} with start time {string} and end time {string}")
	public void userTriesToAddBusinessHour(String day, String startTime, String endTime) {
		try {
			// Changes string format to time.
			SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
			CarShopController.addBusinessHour(DayOfWeek.valueOf(day), new Time(sdf.parse(startTime).getTime()),
					new Time(sdf.parse(endTime).getTime()));
		} catch (Exception e) {
			error += e.getMessage();
			errorCntr++;
		}
	}

	@Then("a new business hour shall {string} created")
	public void aNewBusinessHourCreated(String result) {
		if (errorCntr != 0) {
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
			SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
			CarShopController.addTimeSlot(type, new Date(sdfDate.parse(startDate).getTime()),
					new Time(sdfTime.parse(startTime).getTime()), new Date(sdfDate.parse(endDate).getTime()),
					new Time(sdfTime.parse(endTime).getTime()));
			CarShopApplication.setLoggedInUser(userName);
		} catch (Exception e) {
			error += e.getMessage();
			errorCntr++;
		}
	}

	@When("the user tries to add a new {string} with start date {string} at {string} and end date {string} at {string}")
	public void userTriesAddNewTimeSlot(String type, String startDate, String startTime, String endDate,
			String endTime) {
		try {
			// Changes string format to date and time.
			SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
			CarShopController.addTimeSlot(type, new Date(sdfDate.parse(startDate).getTime()),
					new Time(sdfTime.parse(startTime).getTime()), new Date(sdfDate.parse(endDate).getTime()),
					new Time(sdfTime.parse(endTime).getTime()));
		} catch (Exception e) {
			error += e.getMessage();
			errorCntr++;
		}
	}

	@Then("a new {string} shall {string} be added with start date {string} at {string} and end date {string} at {string}")
	public void aNewTimeSlotShallBeAdded(String type, String result, String startDate, String startTime, String endDate,
			String endTime) {
		try {
			if (errorCntr != 0) {
				assertEquals(result, "not be");
			} else {
				boolean foundMatch = false;
				// Changes string format to date and time.
				SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
				for (TOTimeSlot t : CarShopController.getTimeSlots(type)) {
					if (t.getStartDate().equals(new Date(sdfDate.parse(startDate).getTime()))
							&& t.getStartTime().equals(new Time(sdfTime.parse(startTime).getTime()))
							&& t.getEndDate().equals(new Date(sdfDate.parse(endDate).getTime()))
							&& t.getEndTime().equals(new Time(sdfTime.parse(endTime).getTime()))) {
						foundMatch = true;
						break;
					}
				}
				// Check that a match was found.
				assertTrue(foundMatch);
				assertEquals(result, "be");
			}
		} catch (Exception e) {
			error += e.getMessage();
			errorCntr++;
		}
	}

	@When("the user tries to update the business information with new {string} and {string} and {string} and {string}")
	public void userTriesToUpdateBusinessInfo(String name, String address, String phoneNumber, String email) {
		try {
			CarShopController.updateBusinessInformation(name, address, phoneNumber, email);
		} catch (Exception e) {
			error += e.getMessage();
			errorCntr++;
		}
	}

	@Then("the business information shall {string} updated with new {string} and {string} and {string} and {string}")
	public void businessInfoUpdatedWith(String result, String name, String address, String phoneNumber, String email) {
		if (errorCntr != 0) {
			// The business information could not be updated
			assertEquals(result, "not be");
		} else {
			// The business has been updated. Verify the new information.
			assertEquals(result, "be");
			assertNotNull(CarShopController.getBusiness());
			assertEquals(name, CarShopController.getBusiness().getName());
			assertEquals(address, CarShopController.getBusiness().getAddress());
			assertEquals(phoneNumber, CarShopController.getBusiness().getPhoneNumber());
			assertEquals(email, CarShopController.getBusiness().getEmail());
		}
	}

	@When("the user tries to change the business hour {string} at {string} to be on {string} starting at {string} and ending at {string}")
	public void userTriesToChangeBusinessHour(String currentDay, String currentStartTime, String newDay,
			String newStartTime, String newEndTime) {
		try {
			// Cast string to time
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
			CarShopController.updateBusinessHour(DayOfWeek.valueOf(currentDay),
					new Time(sdf.parse(currentStartTime).getTime()), DayOfWeek.valueOf(newDay),
					new Time(sdf.parse(newStartTime).getTime()), new Time(sdf.parse(newEndTime).getTime()));
		} catch (Exception e) {
			error += e.getMessage();
			errorCntr++;
		}
	}

	@Then("the business hour shall {string} be updated")
	public void businessHourUpdated(String result) {
		if (errorCntr != 0) {
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
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
			CarShopController.removeBusinessHour(DayOfWeek.valueOf(day), new Time(sdf.parse(startTime).getTime()));
		} catch (Exception e) {
			error += e.getMessage();
			errorCntr++;
		}
	}

	@Then("the business hour starting {string} at {string} shall {string} exist")
	public void businessHourShallExist(String day, String startTime, String result) {
		// Cast string to time
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		boolean exists;
		try {
			exists = CarShopController.businessHourExists(DayOfWeek.valueOf(day),
					new Time(sdf.parse(startTime).getTime()));
			if (result.equals("not")) {
				// The hour was removed. It doesn't exist anymore.
				assertTrue(!exists);
			} else {
				// The hour was not remove. It still exists.
				assertTrue(exists);
			}
		} catch (Exception e) {
			error += e.getMessage();
			errorCntr++;
		}

	}

	@Then("an error message {string} shall {string} be raised")
	public void anErrorMessageShallBeRaised(String errorMsg, String resultError) {
		if (errorCntr != 0) {
			// There were errors. Verify the right errors were thrown with the error
			// message.
			assertEquals(resultError, "");
			assertTrue(error.contains(errorMsg));
		} else {
			// There was no error.
			assertEquals(resultError, "not");
		}
	}

	@When("the user tries to change the {string} on {string} at {string} to be with start date {string} at {string} and end date {string} at {string}")
	public void userChangesTimeSlot(String type, String oldStartDate, String oldStartTime, String newStartDate,
			String newStartTime, String newEndDate, String newEndTime) {
		try {
			// Cast String to Time and Date.
			SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
			CarShopController.updateTimeSlot(type, new Date(sdfDate.parse(oldStartDate).getTime()),
					new Time(sdfTime.parse(oldStartTime).getTime()), new Date(sdfDate.parse(newStartDate).getTime()),
					new Time(sdfTime.parse(newStartTime).getTime()), new Date(sdfDate.parse(newEndDate).getTime()),
					new Time(sdfTime.parse(newEndTime).getTime()));
		} catch (Exception e) {
			error += e.getMessage();
			errorCntr++;
		}
	}

	@Then("the {string} shall {string} updated with start date {string} at {string} and end date {string} at {string}")
	public void timeSlotUpdated(String type, String result, String startDate, String startTime, String endDate,
			String endTime) {
		// Cast String to Time and Date.
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
		TOTimeSlot toTimeSlot;
		try {
			toTimeSlot = CarShopController.findTimeSlot(type, new Date(sdfDate.parse(startDate).getTime()),
					new Time(sdfTime.parse(startTime).getTime()));

			if (errorCntr != 0) {
				// There were errors.
				assertEquals(result, "not be");
			} else {
				// There was not error. Verify the updated information.
				assertEquals(result, "be");
				assertNotNull(toTimeSlot);
				assertEquals(new Date(sdfDate.parse(startDate).getTime()), toTimeSlot.getStartDate());
				assertEquals(new Time(sdfTime.parse(startTime).getTime()), toTimeSlot.getStartTime());
				assertEquals(new Date(sdfDate.parse(endDate).getTime()), toTimeSlot.getEndDate());
				assertEquals(new Time(sdfTime.parse(endTime).getTime()), toTimeSlot.getEndTime());
			}
		} catch (Exception e) {
			error += e.getMessage();
			errorCntr++;
		}
	}

	@When("the user tries to remove an existing {string} with start date {string} at {string} and end date {string} at {string}")
	public void userRemovesTimeSlot(String type, String startDate, String startTime, String endDate, String endTime) {
		// Cast String to Time and Date.
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
		try {
			CarShopController.removeTimeSlot(type, new Date(sdfDate.parse(startDate).getTime()),
					new Time(sdfTime.parse(startTime).getTime()), new Date(sdfDate.parse(endDate).getTime()),
					new Time(sdfTime.parse(endTime).getTime()));
		} catch (Exception e) {
			error += e.getMessage();
			errorCntr++;
		}
	}

	@Then("the {string} with start date {string} at {string} shall {string} exist")
	public void timeSlotShallExist(String type, String startDate, String startTime, String result) {
		// Cast String to Time and Date.
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
		TOTimeSlot toTimeSlot;
		try {
			toTimeSlot = CarShopController.findTimeSlot(type, new Date(sdfDate.parse(startDate).getTime()),
					new Time(sdfTime.parse(startTime).getTime()));

			if (errorCntr != 0) {
				// There were errors.
				assertEquals(result, "");
				assertNotNull(toTimeSlot);
			} else {
				// There was no error. The time slot was removed successfully.
				assertEquals(result, "not");
				assertNull(toTimeSlot);
			}
		} catch (Exception e) {
			error += e.getMessage();
			errorCntr++;
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
			errorCntr++;
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
	 * @Then("the account shall have username {string} and password {string}")
	 * public void the_account_shall_have_username_and_password(String string,
	 * String string2) { for (Customer customer : carShop.getCustomers()) { if
	 * (customer.getUsername().equals(string)) {
	 * assertTrue(customer.getPassword().equals(string2)); } } }
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
			errorCntr++;
		}
	}

	@When("the user tries to update account with a new username {string} and password {string}")
	public void the_user_tries_to_update_account_with_a_new_username_and_password(String string, String string2) {
		try {

			CarShopController.updateCustomer(string, string2);
		} catch (Exception e) {
			error += e.getMessage();
			errorCntr++;
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
			errorCntr++;
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
		} catch (Exception e) {
			error += e.getMessage();
			errorCntr++;
		}

	}

	@When("{string} initiates the addition of the service {string} with duration {string} belonging to the garage of {string} technician")
	public void initiates_the_addition_of_the_service_with_duration_belonging_to_the_garage_of_technician(String string,
			String string2, String string3, String string4) {

		try {
			for (Technician t : carShop.getTechnicians()) {
				if (t.getType().name().equals(string4)) {
					Garage g = t.getGarage();
					CarShopController.addService(string2, Integer.parseInt(string3), g);

				}
			}
		}

		catch (Exception e) {
			error += e.getMessage();
			errorCntr++;
		}

	}

	@Then("the service {string} shall exist in the system")
	public void the_service_shall_exist_in_the_system(String string) {

		boolean exists = false;
		for (int i = 0; i < carShop.getBookableServices().size(); i++) {
			if (carShop.getBookableService(i).getName().equals(string)) {
				exists = true;
			}

		}
		assertTrue(exists);
	}

	@Then("the service {string} shall belong to the garage of {string} technician")
	public void the_service_shall_belong_to_the_garage_of_technician(String string, String string2) {

		boolean c = false;
		for (Technician t : carShop.getTechnicians()) {
			if (t.getType().name().equals(string2)) {

				Garage g = t.getGarage();
				if (g.getServices().contains(Service.getWithName(string))) {
					c = true;
				}
			}
		}
		assertTrue(c);

	}

	@Then("the number of services in the system shall be {string}")
	public void the_number_of_services_in_the_system_shall_be(String string) {
		int services = 0;

		for (BookableService b : carShop.getBookableServices()) {
			if (b instanceof Service) {
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
				for (Technician t : carShop.getTechnicians()) {
					if (t.getType().name().equals(columns.get("garage"))) {
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
			errorCntr++;
		}
	}

	@Then("the service {string} shall still preserve the following properties:")
	public void the_service_shall_still_preserve_the_following_properties(String string,
			io.cucumber.datatable.DataTable dataTable) {

		preservedProperties = dataTable.asMaps(String.class, String.class);
		assertEquals(Service.getWithName(string).getName(), preservedProperties.get(0).get("name"));
		assertEquals(((Service) Service.getWithName(string)).getDuration(),
				Integer.parseInt(preservedProperties.get(0).get("duration")));
		assertEquals(((Service) Service.getWithName(string)).getGarage().getTechnician().getType().name(),
				preservedProperties.get(0).get("garage"));

	}

	@Given("the user with username {string} is logged in")
	public void the_user_with_username_is_logged_in(String string) {

		try {
			CarShopApplication.setLoggedInUser(string);

			assertEquals(string, CarShopApplication.getLoggedInUser());
		} catch (Exception e) {
			error += e.getMessage();
			errorCntr++;
		}
	}

	@When("{string} initiates the update of the service {string} to name {string}, duration {string}, belonging to the garage of {string} technician")
	public void initiates_the_update_of_the_service_to_name_duration_belonging_to_the_garage_of_technician(
			String string, String string2, String string3, String string4, String string5) {

		try {
			if (string3.equals("<name>")) {
				CarShopController.updateService("", "", 0, null);
			}
			for (Technician t : carShop.getTechnicians()) {
				if (t.getType().name().equals(string5)) {
					Garage g = t.getGarage();
					CarShopController.updateService(string2, string3, Integer.parseInt(string4), g);
				}
			}
		} catch (Exception e) {
			error += e.getMessage();
			errorCntr++;

		}
	}

	@Then("the service {string} shall be updated to name {string}, duration {string}")
	public void the_service_shall_be_updated_to_name_duration(String string, String string2, String string3) {

		Service service = (Service) BookableService.getWithName(string2);
		assertEquals(string2, service.getName());
		assertEquals(Integer.parseInt(string3), service.getDuration());

	}

	@When("{string} initiates the definition of a service combo {string} with main service {string}, services {string} and mandatory setting {string}")
	public void initiates_the_definition_of_a_service_combo_with_main_service_services_and_mandatory_setting(
			String userName, String name, String mainService, String service, String mandatory) {
		// Write code here that turns the phrase above into concrete actions
		try {
			CarShopController.defineServiceCombo(userName, name, service, mandatory, mainService);
		} catch (Exception e) {
			error += e.getMessage();
			errorCntr++;
		}
	}

	@Then("the service combo {string} shall exist in the system")
	public void the_service_combo_shall_exist_in_the_system(String string) {
		// Write code here that turns the phrase above into concrete actions
		boolean comboExists = false;
		if (BookableService.getWithName(string) != null
				&& BookableService.getWithName(string) instanceof ServiceCombo) {
			comboExists = true;
		}
		assertTrue(comboExists);
	}

	@Then("the service combo {string} shall contain the services {string} with mandatory setting {string}")
	public void the_service_combo_shall_contain_the_services_with_mandatory_setting(String string, String string2,
			String string3) {
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
		assertEquals(((ServiceCombo) BookableService.getWithName(string)).getMainService().getService().getName(),
				string2);
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
	public void the_service_combo_shall_preserve_the_following_properties(String string,
			io.cucumber.datatable.DataTable dataTable) {
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
			String username, String name, String newName, String mainService, String servicesString,
			String mandatoryString) {
		try {
			CarShopController.updateServiceCombo(username, name, newName, servicesString, mandatoryString, mainService);
		} catch (Exception e) {
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
			errorCntr++;
		}
	}

	// done
	@Then("the user should be successfully logged in")
	public void userShouldBeLoggedIn() {
		assertFalse(error.contains("Username/password not found"));
	}

	@Then("the user shall be successfully logged in")
	public void userShallBeLoggedIn() {
		assertFalse(error.contains("Username/password not found"));
	}

	// done
	@Then("the user should not be logged in")
	public void userShouldNotBeLoggedIn() {
		assertTrue(error.contains("Username/password not found"));
	}

	@Then("a new account shall be created") // done
	public void newAccountShallBeCreated() {
		try {
			assertTrue(CarShopController.createdAccount());
		} catch (Exception e) {
			error += e.getMessage();
			errorCntr++;
		}
	}

	@Then("the account shall have username {string} and password {string}") // done
	public void theAccountShallHaveUsernameAndPassword(String string, String string2) {
		List<User> users = new ArrayList<User>();
		users.addAll(carShop.getTechnicians());
		users.addAll(carShop.getCustomers());
		users.add(carShop.getOwner());
		User currentUser;

		if (User.hasWithUsername(string)) {
			currentUser = User.getWithUsername(string);
			assertTrue(currentUser.getPassword().equals(string2));
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
		for (Technician t : carShop.getTechnicians()) {
			if (t.getType().name().equals(type)) {
				technicianType = t.getType();
			}
		}

		if (User.hasWithUsername(username)) {
			currentUser = User.getWithUsername(username);
			if (currentUser.getPassword().equals(password)) {
				assertTrue(currentUser.getPassword().equals(password));
				assertTrue(((Technician) currentUser).getType().equals(technicianType));
			}
			if (currentUser instanceof Technician) {
				garage = ((Technician) currentUser).getGarage();
			}
		}

	}

	@Then("the corresponding garage for the technician shall be created") // Done
	public void garageForTechnicianShallBeCreated() {
		try {
			assertTrue(CarShopController.createdGarage());
		} catch (Exception e) {
			error += e.getMessage();
			errorCntr++;
		}
	}

	// done
	@When("the user tries to add new business hours on {string} from {string} to {string} to garage belonging to the technician with type {string}")
	public void userTriesToAddNewBusinessHoursToGarageOfTechnicianWithType(String day, String startTime, String endTime,
			String type) {
		try {
			CarShopController.addHoursToGarageOfTechnicianType(day, startTime, endTime, type);
		} catch (Exception e) {
			error += e.getMessage();
			errorCntr++;
		}
	}

	// Appointment feature

	@Given("the business has the following opening hours")
	public void the_business_has_the_following_opening_hours(io.cucumber.datatable.DataTable dataTable) {

		if (carShop.getBusiness() == null) {
			carShop.setBusiness(new Business("TheCarShop", "8888 Rue Somewhere, Montreal, QC, Canada", "000-000-0000",
					"car@carshop.com", carShop));
		}
		Business business = carShop.getBusiness();

		List<List<String>> rows = dataTable.asLists();

		for (int i = 1; i < rows.size(); i++) {
			List<String> columns = rows.get(i);

			DayOfWeek day = DayOfWeek.valueOf(columns.get(0));
			Time startTime = new Time((parseDate(columns.get(1), "HH:mm")).getTime());
			Time endTime = new Time((parseDate(columns.get(2), "HH:mm")).getTime());

			BusinessHour hours = new BusinessHour(day, startTime, endTime, carShop);
			business.addBusinessHour(hours);
		}
	}

	@Given("the business has the following opening hours:")
	public void theBusinessHasOpeningHours(io.cucumber.datatable.DataTable dataTable) {

		if (carShop.getBusiness() == null) {
			carShop.setBusiness(new Business("TheCarShop", "8888 Rue Somewhere, Montreal, QC, Canada", "000-000-0000",
					"car@carshop.com", carShop));
		}
		Business business = carShop.getBusiness();

		List<List<String>> rows = dataTable.asLists();

		for (int i = 1; i < rows.size(); i++) {
			List<String> columns = rows.get(i);

			DayOfWeek day = DayOfWeek.valueOf(columns.get(0));
			Time startTime = new Time((parseDate(columns.get(1), "HH:mm")).getTime());
			Time endTime = new Time((parseDate(columns.get(2), "HH:mm")).getTime());

			BusinessHour hours = new BusinessHour(day, startTime, endTime, carShop);
			business.addBusinessHour(hours);
		}
	}

	@Given("all garages has the following opening hours")
	public void all_garages_has_the_following_opening_hours(io.cucumber.datatable.DataTable dataTable) {
		CarShop carshop = CarShopApplication.getCarShop();
		List<List<String>> rows = dataTable.asLists();

		for (int i = 1; i < rows.size(); i++) {
			List<String> columns = rows.get(i);

			DayOfWeek day = DayOfWeek.valueOf(columns.get(0));
			Time startTime = new Time((parseDate(columns.get(1), "HH:mm")).getTime());
			Time endTime = new Time((parseDate(columns.get(2), "HH:mm")).getTime());

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
			Time startTime = new Time((parseDate(columns.get(2), "HH:mm")).getTime());
			Time endTime = new Time((parseDate(columns.get(3), "HH:mm")).getTime());

			business.addHoliday(new TimeSlot(startDate, startTime, endDate, endTime, carShop));
		}
	}

	// done
	@Then("the garage belonging to the technician with type {string} should have opening hours on {string} from {string} to {string}")
	public void garageOfTechnicianWithTypeShouldHaveOpeningHoursOnFromTo(String type, String day, String startTime,
			String endTime) {
		try {
			Garage g = null;
			for (Technician t : carShop.getTechnicians()) {
				if (type.equals(t.getType().name())) {
					g = t.getGarage();
				}
			}
			Time st = new Time((parseDate(startTime, "HH:mm")).getTime());
			Time et = new Time((parseDate(endTime, "HH:mm")).getTime());
			assertNotNull(g);

			boolean foundHours = false;
			for (BusinessHour h : g.getBusinessHours()) {
				if (h.getDayOfWeek().name().equals(day)) {
					foundHours = true;
					assertEquals(st, h.getStartTime());
					assertEquals(et, h.getEndTime());
				}
			}
			assertTrue(foundHours);
		} catch (Exception e) {
			error += e.getMessage();
			errorCntr++;
		}
	}

	// Scenario Outline: Remove opening hours successfully
	@Given("there are opening hours on {string} from {string} to {string} for garage belonging to the technician with type {string}")
	public void openingHoursForGarageOfTechnicianWithType(String day, String startTime, String endTime, String type) {
		try {
			String currentUser = CarShopApplication.getLoggedInUser();
			for (Technician t : carShop.getTechnicians()) {
				if (t.getType().name().equals(type)) {
					CarShopApplication.setLoggedInUser(t.getUsername());
				}
			}
			CarShopController.addHoursToGarageOfTechnicianType(day, startTime, endTime, type);
			CarShopApplication.setLoggedInUser(currentUser);
		} catch (Exception e) {
			error += e.getMessage();
			errorCntr++;
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
			if (s.getName().equals(mainServiceString)) {
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
	public void schedules_an_appointment_on_for_with_at(String customer, String dateString, String mainServiceString,
			String optServiceStrings, String startTimeStrings) {

		appointmentCount = CarShopApplication.getCarShop().getAppointments().size();
		try {
			// TODO: Implement this Controller method and set Error message

			Date date = parseDate(dateString, "yyyy-MM-dd");

			// Used a stream
			List<Time> times = Arrays.stream(startTimeStrings.split("\\,"))
					.map(s -> new Time((parseDate(s, "HH:mm")).getTime())).collect(Collectors.toList());

			BookableService main = AppointmentController.findBookableService(mainServiceString);

			List<Service> optionalServices;
			List<TimeSlot> timeSlots;
			if (optServiceStrings.equals("")) {
				optionalServices = new ArrayList<Service>();
				Time start = times.get(0);

				SimpleDateFormat df = new SimpleDateFormat("HH:mm");
				java.util.Date d = df.parse(startTimeStrings);
				Calendar cal = Calendar.getInstance();
				cal.setTime(d);
				cal.add(Calendar.MINUTE, ((Service) main).getDuration());
				Date dd = new java.sql.Date(cal.getTime().getTime());
				Time end = new Time(dd.getTime());

				timeSlots = new ArrayList<TimeSlot>();
				timeSlots.add(new TimeSlot(date, start, date, end, carShop));

			} else {
				optionalServices = AppointmentController.findServices(Arrays.asList(optServiceStrings.split("\\,")));
				timeSlots = AppointmentController.generateTimeSlotsFromStarts(date, times, ((ServiceCombo)main).getMainService().getService(),
						optionalServices.toArray(new Service[optionalServices.size()]));
			}

			AppointmentController.makeAppointment(false, AppointmentController.findCustomer(customer),
					mainServiceString, date, times.get(0), timeSlots, main, optionalServices, new ArrayList<TimeSlot>());

		} catch (Exception e) {
			errorReportMessage = e.getMessage();
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
				String mainServiceName = columns.get(1);
				List<String> optServices = Arrays.asList(columns.get(2).split("\\,"));
				Date date = parseDate(columns.get(3), "yyyy-MM-dd");

				Time first = null;
				for (String times : columns.get(4).split("\\,")) {
					if (first == null)
						first = new Time((parseDate(times.split("-")[0], "HH:mm")).getTime());
					break;
				}
				CarShopApplication.setLoggedInUser(customerName);
				// TODO: Add appointments to the system

				Service mainService = AppointmentController.findService(mainServiceName);
				Customer customer = AppointmentController.findCustomer(customerName);
				List<Service> optionalServices = AppointmentController.findServices(optServices);
				List<TimeSlot> timeSlots = AppointmentController.generateTimeSlots(date, first,
						columns.get(4).split("\\,"));

				AppointmentController.makeAppointment(true, customer, mainServiceName, date, first, timeSlots,
						mainService, optionalServices, new ArrayList<TimeSlot>());

			}
			CarShopApplication.setLoggedInUser(currentUser);
		} catch (Exception e) {
			error += e.getMessage();
			errorCntr++;
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
	public void attempts_to_cancel_their_appointment_on_at(String customerName, String mainServiceName, String date,
			String time) {

		appointmentCount = CarShopApplication.getCarShop().getAppointments().size();
		try {
			// TODO: Implement this Controller method and set Error message
			AppointmentController.cancelAppointment(customerName, mainServiceName, parseDate(date, "yyyy-MM-dd"),
					new Time((parseDate(time, "HH:mm")).getTime()));

		} catch (Exception e) {
			errorReportMessage = e.getMessage();
		}
	}

	@Then("{string}'s {string} appointment on {string} at {string} shall be removed from the system")
	public void s_appointment_on_at_shall_be_removed_from_the_system(String customer, String mainServiceName,
			String date, String startTime) {

		try {
			// TODO: use find appointment to detect removed appointments
			Appointment app = AppointmentController.findAppointment(AppointmentController.findCustomer(customer),
					mainServiceName, parseDate(date, "yyyy-MM-dd"),
					new Time((parseDate(startTime, "HH:mm")).getTime()));

			assertNull(app);
		} catch (Exception e) {
			error += e.getMessage();
			errorCntr++;
		}
	}

	// done
	@When("the user tries to remove opening hours on {string} from {string} to {string} to garage belonging to the technician with type {string}")
	public void userTriesToRemoveOpeningHoursToGarageOfTechnicianWithType(String day, String startTime, String endTime,
			String type) {
		try {
			if (!CarShopApplication.getLoggedInUser().equals(type))
				CarShopController.removeHoursToGarageOfTechnicianType(day, startTime, endTime, type);
		} catch (Exception e) {
			error += e.getMessage();
			errorCntr++;
		}
	}

	// done
	@Then("the garage belonging to the technician with type {string} should not have opening hours on {string} from {string} to {string}")
	public void garageOfTechnicianWithTypeShouldNotHaveOpeningHours(String type, String day, String startTime,
			String endTime) {
		try {
			Garage g = null;
			for (Technician t : carShop.getTechnicians()) {
				if (type.equals(t.getType().name())) {
					g = t.getGarage();
				}
			}
			Time st = new Time((parseDate(startTime, "HH:mm")).getTime());
			Time et = new Time((parseDate(endTime, "HH:mm")).getTime());
			assertNotNull(g);

			boolean foundHours = false;
			for (BusinessHour h : g.getBusinessHours()) {
				if (h.getDayOfWeek().name().equals(day) && h.getStartTime().equals(st) && h.getEndTime().equals(et)) {
					foundHours = true;
				}
			}
			assertFalse(foundHours);
		} catch (Exception e) {
			error += e.getMessage();
			errorCntr++;
		}
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
	public void shall_have_a_appointment_on_at_with_the_following_properties(String customer, String mainService,
			String dateString, String timeString, io.cucumber.datatable.DataTable dataTable) {

		try {

			Appointment app = AppointmentController.findAppointment(AppointmentController.findCustomer(customer),
					mainService, parseDate(dateString, "yyyy-MM-dd"),
					new Time((parseDate(timeString, "HH:mm")).getTime()));

			assertNotNull(app);

			List<Service> bookings = new ArrayList<Service>();
			for (ServiceBooking s : app.getServiceBookings()) {
				bookings.add(s.getService());
			}
			List<TimeSlot> timeSlots = new ArrayList<TimeSlot>();
			for (ServiceBooking s : app.getServiceBookings()) {
				timeSlots.add(s.getTimeSlot());
			}

			List<List<String>> rows = dataTable.asLists();
			for (int i = 1; i < rows.size(); i++) {
				List<String> columns = rows.get(i);

				Service colMainService = AppointmentController.findService(columns.get(0));
				List<Service> colOptServices = AppointmentController
						.findServices(Arrays.asList(columns.get(1).split("\\,")));
				Date colDate = parseDate(columns.get(2), "yyyy-MM-dd");
				List<TimeSlot> colSlots = AppointmentController.generateTimeSlots(colDate, null,
						columns.get(3).split("\\,"));

				assertEquals(colMainService.getName(), app.getServiceBooking(0).getService().getName());
				for (Service s : colOptServices) {
					assertTrue(bookings.contains(s));
				}
				boolean foundMatch = false;
				for (TimeSlot t : colSlots) {
					for (TimeSlot appT : timeSlots) {
						if (t.getStartDate().equals(appT.getStartDate()) && t.getStartTime().equals(appT.getStartTime())
								&& t.getEndDate().equals(appT.getEndDate())
								&& t.getEndTime().equals(appT.getEndTime())) {
							foundMatch = true;
						}
					}
				}
				assertTrue(foundMatch);

			}
			// TODO: check the validity of the appointment based on parameters
		} catch (Exception e) {
			error += e.getMessage();
			errorCntr++;
		}
	}

	@When("{string} attempts to cancel {string}'s {string} appointment on {string} at {string}")
	public void attempts_to_cancel_s_appointment_on_at(String otherUser, String customerName, String mainServiceName,
			String date, String time) throws Exception {

		CarShopApplication.setLoggedInUser(customerName);

		// TODO: Perform cancel test
		attempts_to_cancel_their_appointment_on_at(otherUser, mainServiceName, date, time);
	}

	@When("{string} schedules an appointment on {string} for {string} at {string}")
	public void schedules_an_appointment_on_for_at(String customer, String mainService, String date, String time) {
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

			Appointment a = AppointmentController.findAppointment(c, serviceName, date,
					new Time((parseDate(st.get(0), "HH:mm")).getTime()));

			assertNotNull(a);
			assertEquals(customerName, a.getCustomer().getUsername());
			assertTrue(serviceName.contains(a.getServiceBooking(0).getService().getName()));

			for (int i = 0; i < a.getServiceBookings().size(); i++) {

				assertEquals(new Time((parseDate(st.get(i), "HH:mm")).getTime()),
						a.getServiceBooking(i).getTimeSlot().getStartTime());
				assertEquals(new Time((parseDate(et.get(i), "HH:mm")).getTime()),
						a.getServiceBooking(i).getTimeSlot().getEndTime());
				assertEquals(date, a.getServiceBooking(i).getTimeSlot().getStartDate());
			}
		} catch (Exception e) {
			error += e.getMessage();
			errorCntr++;
		}
	}

	@Then("the garage should have the same opening hours as the business")
	public void hasSameOpeningHoursAsBusiness() {
		Business b = carShop.getBusiness();
		if (b != null) {
			assertNotNull(garage);
			for (int i = 0; i < b.getBusinessHours().size(); i++) {
				assertEquals(b.getBusinessHour(i).getDayOfWeek(), garage.getBusinessHour(i).getDayOfWeek());
				assertEquals(b.getBusinessHour(i).getStartTime(), garage.getBusinessHour(i).getStartTime());
				assertEquals(b.getBusinessHour(i).getEndTime(), garage.getBusinessHour(i).getEndTime());
			}
		}
	}
	
	@When("{string} makes a {string} appointment with service {string} for the date {string} and start time {string} at {string}")
	public void userMakesAnAppointment(String customerName, String mainServiceString, String serviceStrings, String stringDate, String startTimes, String currentTime) {
		try {
			CarShopApplication.setLoggedInUser(customerName);
			// Write code here that turns the phrase above into concrete actions
			Customer c = AppointmentController.findCustomer(customerName);
			Date date = parseDate(stringDate, "yyyy-MM-dd");
			BookableService mainService = AppointmentController.findBookableService(mainServiceString);

			List<Time> times = Arrays.stream(startTimes.split("\\,"))
					.map(s -> new Time((parseDate(s, "HH:mm")).getTime())).collect(Collectors.toList());
			List<Service> services = new ArrayList<Service>();
			for(String str: Arrays.asList(serviceStrings.split("\\,"))) {
				services.add(AppointmentController.findService(str));
			}
			

			List<TimeSlot> timeSlots = new ArrayList<TimeSlot>();
			if(mainService instanceof Service) {
				timeSlots = AppointmentController.generateTimeSlotsFromStarts(date, times, (Service)mainService, services.toArray(new Service[services.size()]));
			} else {
				timeSlots = AppointmentController.generateTimeSlotsFromStarts(date, times, ((ServiceCombo)mainService).getMainService().getService(), services.toArray(new Service[services.size()]));
			}
			
			currentApp = AppointmentController.makeAppointment(false, c, mainServiceString, date, times.get(0), timeSlots, mainService, services, new ArrayList<TimeSlot>());
			
		} catch (Exception e) {
			error += e.getMessage();
			errorCntr++;
		}
	}
	
	@When("the owner starts the appointment at {string}") 
	public void ownerStartsAppointment (String startTime) {
		try {
			String currentUser = CarShopApplication.getLoggedInUser();
			CarShopApplication.setLoggedInUser("owner");
			
			Date date = parseDate(startTime, "yyyy-MM-dd+HH:mm");
			
			AppointmentController.startAppointment(date, currentApp);

			CarShopApplication.setLoggedInUser(currentUser);
			
		} catch (Exception e) {
			error += e.getMessage();
			errorCntr++;
		}
		
	}
	
	@Then("the appointment shall be in progress")
	public void appInProgress() {
		assertNotNull(currentApp);
		assertEquals(AppStatus.InProgress, currentApp.getAppStatus());
	}
	
	@Then("the service combo in the appointment shall be {string}")
	public void serviceComboShallBe(String name) {
		assertTrue(currentApp.getBookableService() instanceof ServiceCombo);
		assertEquals(name, currentApp.getBookableService().getName());
	}
	
	@Then("the service combo shall have {string} selected services")
	public void serviceComboHasSelectedService(String serviceNames) {
		List<Service> services = new ArrayList<Service>();
		for(String str: Arrays.asList(serviceNames.split("\\,"))) {
			services.add(AppointmentController.findService(str));
		}
		
		assertEquals(services.size(), currentApp.getServiceBookings().size());
		for(int i =0 ; i < services.size(); i++) {
			assertTrue(currentApp.getServiceBooking(i).getService().getName().equals(services.get(i).getName()));
		}
	}
	
	@Then("the appointment shall be for the date {string} with start time {string} and end time {string}")
	public void appointmentShallBeFor(String dateString, String startTimesString, String endTimesString) {
		
		List<Time> st = new ArrayList<Time>();
		for(String str: Arrays.asList(startTimesString.split("\\,"))) {
			st.add(new Time((parseDate(str, "HH:mm")).getTime()));
		}
		
		List<Time> et = new ArrayList<Time>();
		for(String str: Arrays.asList(endTimesString.split("\\,"))) {
			et.add(new Time((parseDate(str, "HH:mm")).getTime()));
		}
		
		Date date = parseDate(dateString,"yyyy-MM-dd");
		// Check date
		assertEquals(date, currentApp.getServiceBooking(0).getTimeSlot().getStartDate());
		
		// Check times
		for(int i = 0; i < st.size();i++) {
			assertEquals(st.get(i), currentApp.getServiceBooking(i).getTimeSlot().getStartTime());
			assertEquals(et.get(i), currentApp.getServiceBooking(i).getTimeSlot().getEndTime());
		}
		

	}
	
	
	@Then("the username associated with the appointment shall be {string}")
	public void usernameAssociatedWithAppointmentIs(String username) {
		assertEquals(username, currentApp.getCustomer().getUsername());
	}
	
	
	@Then("the user {string} shall have {int} no-show records")
	public void userShallHaveNoShow(String username, int count) {
		try {
			Customer c = AppointmentController.findCustomer(username);
			assertTrue(c.getNoShowCount() == count);
		} catch(Exception e) {
			error += e.getMessage();
			errorCntr++;
		}
		
	}
	
	@Then("the system shall have {int} appointments")
	public void systemShallHaveAppointments(int count) {
		assertEquals(count, carShop.getAppointments().size());
	}
	
	@Given("{string} has {int} no-show records")
	public void hasXNoShow(String username, int count) {
		try {
			Customer c = AppointmentController.findCustomer(username);
			c.setNoShowCount(count);
		} catch(Exception e) {
			error += e.getMessage();
			errorCntr++;
		}
	}
	
	@When("{string} attempts to add the optional service {string} to the service combo with start time {string} in the appointment at {string}")
	public void attemptsToAddOPtionalService(String username, String serviceName, String startTime, String currentDate) {
		
		List<Service> services = new ArrayList<Service>();
		services.add(AppointmentController.findService(serviceName));
		
		Date date = parseDate(currentDate, "yyyy-MM-dd+HH:mm");

		List<Time> times = new ArrayList<Time>();
		times.add(new Time((parseDate(startTime, "HH:mm")).getTime()));
		
		List<TimeSlot> timeSlots = AppointmentController.generateTimeSlotsFromStarts(currentApp.getServiceBooking(0).getTimeSlot().getStartDate(), times, 
				services.toArray(new Service[services.size()]));
		
		try {
			AppointmentController.updateAppointment(true, false, AppointmentController.findCustomer(username), currentApp, services, timeSlots, date);
		} catch (Exception e) {
			error += e.getMessage();
			errorCntr++;
		}
	}
	
	@Then("the appointment shall be booked")
	public void appointmentShallBeBooked() {
		assertNotNull(currentApp);
		assertEquals(AppStatus.Booked, currentApp.getAppStatus());
	}
	
	@When("{string} attempts to update the date to {string} and start time to {string} at {string}")
	public void attemptsToUpdateAppDate(String username, String newDateString, String startTimeStrings, String currentDate) {
		
		Date date = parseDate(currentDate, "yyyy-MM-dd+HH:mm");
		Date newDate = parseDate(newDateString, "yyyy-MM-dd");
		
		// Used a stream
		List<Time> times = Arrays.stream(startTimeStrings.split("\\,"))
				.map(s -> new Time((parseDate(s, "HH:mm")).getTime())).collect(Collectors.toList());

		List<Service> services = new ArrayList<Service>();
		assertNotNull(currentApp);
		for(ServiceBooking sb:currentApp.getServiceBookings()) {
			services.add(sb.getService());
		}
		
		List<TimeSlot> timeSlots = AppointmentController.generateTimeSlotsFromStarts(newDate, times, 
				services.toArray(new Service[services.size()]));
		
		try {
			AppointmentController.updateAppointment(false, false, AppointmentController.findCustomer(username), currentApp, services, timeSlots, date);
		} catch (Exception e) {
			error += e.getMessage();
			errorCntr++;
		}
	}
	
	
	@When("{string} makes a {string} appointment for the date {string} and time {string} at {string}")
	public void makes_a_appointment_for_the_date_and_time_at(String customerName, String mainServiceString, String stringDate, String startTime, String currentTime) {
		try {
			CarShopApplication.setLoggedInUser(customerName);
			Customer c = AppointmentController.findCustomer(customerName);
			Date date = parseDate(stringDate, "yyyy-MM-dd");
			BookableService mainService = AppointmentController.findBookableService(mainServiceString);

			List<Time> times = Arrays.stream(startTime.split("\\,"))
					.map(s -> new Time((parseDate(s, "HH:mm")).getTime())).collect(Collectors.toList());
			List<Service> services = new ArrayList<Service>();
			//for(String str: Arrays.asList(serviceStrings.split("\\,"))) {
			//	services.add(AppointmentController.findService(str));
			//}
			

			List<TimeSlot> timeSlots = new ArrayList<TimeSlot>();
			if(mainService instanceof Service) {
				timeSlots = AppointmentController.generateTimeSlotsFromStarts(date, times, (Service)mainService, services.toArray(new Service[services.size()]));
			} else {
				timeSlots = AppointmentController.generateTimeSlotsFromStarts(date, times, ((ServiceCombo)mainService).getMainService().getService(), services.toArray(new Service[services.size()]));
			}
			
			currentApp = AppointmentController.makeAppointment(false, c, mainServiceString, date, times.get(0), timeSlots, mainService, services, new ArrayList<TimeSlot>());
			
		} 
		catch (Exception e) {
			error += e.getMessage();
			errorCntr++;
		}
	}

	@When("{string} attempts to change the service in the appointment to {string} at {string}")
	public void attempts_to_change_the_service_in_the_appointment_to_at(String user, String serviceName, String time) {
	    // Write code here that turns the phrase above into concrete actions
		
		try {
			Customer c = AppointmentController.findCustomer(user);
			Date date = parseDate(time, "yyyy-MM-dd+HH:mm");
			
			List<Time> times = new ArrayList<Time>();
			times.add(currentApp.getServiceBooking(0).getTimeSlot().getStartTime());
			
			List<Service> services = new ArrayList<Service>();
			assertNotNull(currentApp);
			
			services.add(AppointmentController.findService(serviceName));
			/*for(ServiceBooking service:currentApp.getServiceBookings()) {
				services.add(service.getService());
				
			}*/
			List<TimeSlot> timeSlots = AppointmentController.generateTimeSlotsFromStarts(currentApp.getServiceBooking(0).getTimeSlot().getStartDate(), times, services.toArray(new Service[services.size()]));
			//List<TimeSlot> timeSlots = new ArrayList<TimeSlot>();
			
			currentApp = AppointmentController.updateAppointment(false, true, c, currentApp, services, timeSlots, date);
		} 
		catch (Exception e) {
			error = e.getMessage();
			errorCntr++;
		}
	}

	@Then("the service in the appointment shall be {string}")
	public void the_service_in_the_appointment_shall_be(String service) {
		assertEquals(service, currentApp.getBookableService().getName());
	}

	@When("{string} attempts to update the date to {string} and time to {string} at {string}")
	public void attempts_to_update_the_date_to_and_time_to_at(String username, String newTime, String startTime, String currentTime) {
		Date date = parseDate(currentTime, "yyyy-MM-dd+HH:mm");
		Date newDate = parseDate(newTime, "yyyy-MM-dd");
		
		// Used a stream
		List<Time> times = Arrays.stream(startTime.split("\\,"))
				.map(s -> new Time((parseDate(s, "HH:mm")).getTime())).collect(Collectors.toList());

		List<Service> services = new ArrayList<Service>();
		assertNotNull(currentApp);
		for(ServiceBooking sb:currentApp.getServiceBookings()) {
			services.add(sb.getService());
		}
		
		List<TimeSlot> timeSlots = AppointmentController.generateTimeSlotsFromStarts(newDate, times, 
				services.toArray(new Service[services.size()]));
		
		try {
			AppointmentController.updateAppointment(false, false, AppointmentController.findCustomer(username), currentApp, services, timeSlots, date);
		} catch (Exception e) {
			error += e.getMessage();
			errorCntr++;
		}
	}

	@When("{string} attempts to cancel the appointment at {string}")
	public void attempts_to_cancel_the_appointment_at(String string, String string2) {
	    try {

	        Date date = parseDate(string2, "yyyy-MM-dd+hh:mm");
	        CarShopApplication.setSystemDate(date);
	    	AppointmentController.cancelAppointment(string, currentApp.getBookableService().getName(),currentApp.getServiceBooking(0).getTimeSlot().getStartDate(), currentApp.getServiceBooking(0).getTimeSlot().getStartTime());
	    }
	    catch(Exception e)
	{
	    	error = e.getMessage();
	    	}
	}

	@Then("the system shall have {int} appointment")
	public void the_system_shall_have_appointment(Integer int1) {
	    // Write code here that turns the phrase above into concrete actions
	    assertEquals(int1, carShop.numberOfAppointments());
	}

	@When("the owner ends the appointment at {string}")
	public void the_owner_ends_the_appointment_at(String endTime) {
		try {
			String currentUser = CarShopApplication.getLoggedInUser();
			CarShopApplication.setLoggedInUser("owner");
			
			Date date = parseDate(endTime, "yyyy-MM-dd+HH:mm");
			
			AppointmentController.endAppointment(date, currentApp);;

			CarShopApplication.setLoggedInUser(currentUser);
			
		} catch (Exception e) {
			error += e.getMessage();
			errorCntr++;
		}
	}

	@When("the owner attempts to end the appointment at {string}")
	public void the_owner_attempts_to_end_the_appointment_at(String time) {
		try {
			String currentUser = CarShopApplication.getLoggedInUser();
			CarShopApplication.setLoggedInUser("owner");
			
			Date date = parseDate(time, "yyyy-MM-dd+HH:mm");
			AppointmentController.endAppointment(date, currentApp);

			CarShopApplication.setLoggedInUser(currentUser);
			
		} catch (Exception e) {
			error += e.getMessage();
			errorCntr++;
		}
	}
	
	@When("the owner attempts to register a no-show for the appointment at {string}")
	public void the_owner_attempts_to_register_a_no_show_for_the_appointment_at(String time) {
		try {
			String currentUser = CarShopApplication.getLoggedInUser();
			CarShopApplication.setLoggedInUser("owner");
						
			Date date = parseDate(time, "yyyy-MM-dd+HH:mm");
			AppointmentController.registerNoShow(date, currentApp);
			CarShopApplication.setLoggedInUser(currentUser);
		} catch(Exception e) {
			error += e.getMessage();
			errorCntr++;
		}
	}
}