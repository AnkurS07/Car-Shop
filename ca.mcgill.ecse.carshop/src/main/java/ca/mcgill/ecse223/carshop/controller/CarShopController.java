package ca.mcgill.ecse223.carshop.controller;

import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ca.mcgill.ecse.carshop.application.CarShopApplication;
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
import ca.mcgill.ecse.carshop.model.ServiceCombo;
import ca.mcgill.ecse.carshop.model.Technician;
import ca.mcgill.ecse.carshop.model.Technician.TechnicianType;
import ca.mcgill.ecse.carshop.model.TimeSlot;
import ca.mcgill.ecse.carshop.model.User;
import ca.mcgill.ecse223.carshop.persistence.CarshopPersistence;

/**
 * Controller class. Implements all controller methods as static methods. The controller should be stateless. It interacts with the View and the Model.
 * @author Maxime Bourassa
 * @author Julien Lefebvre
 * @author Ankur
 * @author Debopriyo
 */
public class CarShopController {

	private static final String regexEmail = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";

	public CarShopController() {
		// Empty constructor. All public methods are static so it won't actually be used.
	}

	/**
	 * Creates the owner account. Used in the background step.
	 * @param userName
	 * @param password
	 * @throws Exception
	 */
	public static void createOwner(String userName, String password) throws Exception {
		CarShop carShop = CarShopApplication.getCarShop();
		try {
			new Owner(userName, password, carShop);
			CarshopPersistence.save(carShop);			// Serialize the carShop and save to the disk
		}
		catch (RuntimeException e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * Creates the technician account. Used in the background step.
	 * @param userName
	 * @param password
	 * @param type
	 * @throws Exception
	 */
	public static void createTechnician(String userName, String password, TechnicianType type) throws Exception {
		CarShop carShop = CarShopApplication.getCarShop();
		try {
			Technician t = new Technician(userName, password, type, carShop);
			Garage g = new Garage(carShop, t);
			
			// set opening hours to the same as the business
			if(carShop.hasBusiness()) {
				for(BusinessHour h: carShop.getBusiness().getBusinessHours()) {
					BusinessHour copy = new BusinessHour(h.getDayOfWeek(), h.getStartTime(), h.getEndTime(), carShop);
				}
			}
			CarshopPersistence.save(carShop);			// Serialize the carShop and save to the disk
		}
		catch (RuntimeException e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * Creates the customer account. Used in the background step.
	 * @param userName
	 * @param password
	 * @throws Exception
	 * @return true if account successfully created
	 */
	public static boolean createCustomer(String userName, String password) throws Exception {
		CarShop carShop = CarShopApplication.getCarShop();
		boolean isCreated = false;
		checkValidityOfCustomerCreation(userName, password);
		try {
			carShop.addCustomer(userName, password);
			CarshopPersistence.save(carShop);			// Serialize the carShop and save to the disk
			isCreated = true;
		}
		catch (RuntimeException e) {
			throw new Exception(e.getMessage());
		}
		return isCreated;
	}

	/**
	 * Assigns all technicians that do not have a garage to a new garage.
	 * @throws Exception
	 */
	public static void assignTechniciansToGarages() throws Exception {
		CarShop carShop = CarShopApplication.getCarShop();
		try {
			for(Technician t: carShop.getTechnicians()) {
				if(t.getGarage() == null) {
					carShop.addGarage(t);					
				}
			}
			CarshopPersistence.save(carShop);			// Serialize the carShop and save to the disk
		}
		catch (RuntimeException e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * Gets the business as a transfer object. Contains basic business information, but no association to other classes. To be used by the view.
	 * @return
	 */
	public static TOBusiness getBusiness() {
		CarShop carShop = CarShopApplication.getCarShop();
		Business business = carShop.getBusiness();
		TOBusiness toBusiness = null;
		if(business != null) {
			toBusiness = new TOBusiness(business.getName(), business.getAddress(), business.getPhoneNumber(), business.getEmail());
		}
		return toBusiness;
	}

	/**
	 * Method used to set up a new business. Input and permission validation is taken care of in here.
	 * @param name
	 * @param address
	 * @param phoneNumber
	 * @param email
	 * @throws Exception
	 */
	public static void SetUpBusinessInformation(String name, String address, String phoneNumber, String email) throws Exception{
		//Move input validation to model
		CarShop carShop = CarShopApplication.getCarShop();
		try {
			if(!userIsOwner()) {
				throw new Exception("No permission to set up business information");
			}

			Pattern patternEmail = Pattern.compile(regexEmail);
			Matcher matcherEmail = patternEmail.matcher(email);
			if(!matcherEmail.matches()) {
				throw new Exception("Invalid email");
			}

			/*Pattern patternPhone = Pattern.compile(regexPhoneNumber);
			Matcher matcherPhone = patternPhone.matcher(phoneNumber);
			if(!matcherPhone.matches()) {
				throw new InvalidInputException("Invalid phone number", "be");
			}*/

			new Business(name, address, phoneNumber, email, carShop);
			CarshopPersistence.save(carShop);			// Serialize the carShop and save to the disk

		}
		catch (RuntimeException e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * Method used to update an existing business. Includes input and permission validation.
	 * @param name
	 * @param address
	 * @param phoneNumber
	 * @param email
	 * @throws Exception
	 */
	public static void updateBusinessInformation(String name, String address, String phoneNumber, String email) throws Exception {
		CarShop carShop = CarShopApplication.getCarShop();
		if(!userIsOwner()) {
			throw new Exception("No permission to update business information");
		}

		Pattern patternEmail = Pattern.compile(regexEmail);
		Matcher matcherEmail = patternEmail.matcher(email);
		if(!matcherEmail.matches()) {
			throw new Exception("Invalid email");
		}
		
		try {
			carShop.getBusiness().setName(name);
			carShop.getBusiness().setAddress(address);
			carShop.getBusiness().setPhoneNumber(phoneNumber);
			carShop.getBusiness().setEmail(email);
			CarshopPersistence.save(carShop);			// Serialize the carShop and save to the disk
		}
		catch (RuntimeException e) {
			throw new Exception(e.getMessage());
		}


	}

	/**
	 * Adds a new business hour and assigns it to a business. Includes input and permission validation.
	 * @param day
	 * @param startTime
	 * @param endTime
	 * @throws Exception
	 */
	public static void addBusinessHour(DayOfWeek day, Time startTime, Time endTime) throws Exception {
		CarShop carShop = CarShopApplication.getCarShop();
		try {
			if(!userIsOwner()) {
				throw new Exception("No permission to update business information");
			}

			if(startTime.compareTo(endTime) > 0) {
				throw new Exception("Start time must be before end time");
			}

			if(businessHoursOverlap(day, startTime, endTime, null)) {
				throw new Exception("The business hours cannot overlap");
			}

			carShop.getBusiness().addBusinessHour(new BusinessHour(day, startTime, endTime, carShop));
			CarshopPersistence.save(carShop);			// Serialize the carShop and save to the disk
		}
		catch (RuntimeException e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * Updates existing business hours. Includes input and permission validation.
	 * @param currentDay
	 * @param currentStartTime
	 * @param newDay
	 * @param newStartTime
	 * @param newEndTime
	 * @throws Exception
	 */
	public static void updateBusinessHour(DayOfWeek currentDay, Time currentStartTime, DayOfWeek newDay, Time newStartTime, Time newEndTime) throws Exception {
		BusinessHour currentHour = findBusinessHour(currentDay, currentStartTime);
		CarShop carShop = CarShopApplication.getCarShop();
		try {
			if(!userIsOwner()) {
				throw new Exception("No permission to update business information");
			}

			if(newStartTime.compareTo(newEndTime) > 0) {
				throw new Exception("Start time must be before end time");
			}

			if(businessHoursOverlap(newDay, newStartTime, newEndTime, currentHour != null ? Arrays.asList(currentHour) : null)) {
				throw new Exception("The business hours cannot overlap");
			}

			if(currentHour != null) {
				currentHour.setDayOfWeek(newDay);
				currentHour.setStartTime(newStartTime);
				currentHour.setEndTime(newEndTime);
			}
			CarshopPersistence.save(carShop);			// Serialize the carShop and save to the disk
		}
		catch (RuntimeException e) {
			throw new Exception(e.getMessage());
		}	
	}

	/**
	 * Removes a business hour from the model.
	 * @param day
	 * @param startTime
	 * @throws Exception
	 */
	public static void removeBusinessHour(DayOfWeek day, Time startTime) throws Exception {
		CarShop carShop = CarShopApplication.getCarShop();
		try {

			if(!userIsOwner()) {
				throw new Exception("No permission to update business information");
			}

			BusinessHour hourToRemove = findBusinessHour(day, startTime);
			if(hourToRemove != null) {
				carShop.getBusiness().removeBusinessHour(hourToRemove);
			}
			CarshopPersistence.save(carShop);			// Serialize the carShop and save to the disk
		}
		catch (RuntimeException e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * Queries if a given business hour exists or not. 
	 * @param day
	 * @param startTime
	 * @return
	 */
	public static boolean businessHourExists(DayOfWeek day, Time startTime) {
		return findBusinessHour(day, startTime) == null ? false: true;
	}

	/**
	 * Helper method used to find a given business hour. Returns null if the business hour is not found.
	 * @param day
	 * @param startTime
	 * @return
	 */
	private static BusinessHour findBusinessHour(DayOfWeek day, Time startTime) {
		CarShop carShop = CarShopApplication.getCarShop();
		BusinessHour businessHour = null;
		for(BusinessHour h: carShop.getBusiness().getBusinessHours()) {
			if(h.getDayOfWeek() == day && h.getStartTime().equals(startTime)) {
				businessHour = h;
			}
		}
		return businessHour;
	}

	/**
	 * Helper method used as part of input validation to make sure new business hours do not overlap with existing ones.
	 * @param day
	 * @param startTime
	 * @param endTime
	 * @param exclude
	 * @return
	 */
	private static boolean businessHoursOverlap(DayOfWeek day, Time startTime, Time endTime, List<BusinessHour> exclude) {
		CarShop carShop = CarShopApplication.getCarShop();
		boolean isOverlapping = false;

		for(BusinessHour h: carShop.getBusiness().getBusinessHours()) {
			if(exclude != null && exclude.contains(h)) {
				continue;
			}
			// For business hours on the same day we want
			// The startime of the existing schedule to be after the endtime of the new schedule or
			// The endtime of the existing schedule to be before the starttime of the new schedule
			if(h.getDayOfWeek() == day && !(h.getStartTime().compareTo(endTime) >= 0 || h.getEndTime().compareTo(startTime) <= 0)) {
				isOverlapping = true;
				break;
			}
		}

		return isOverlapping;
	}

	/**
	 * Adds a new time slot to a model and associates it to a business either as a vacation or a holiday. Includes input and permission validation.
	 * @param type
	 * @param startDate
	 * @param startTime
	 * @param endDate
	 * @param endTime
	 * @throws Exception
	 */
	public static void addTimeSlot(String type, Date startDate, Time startTime, Date endDate, Time endTime) throws Exception {
		CarShop carShop = CarShopApplication.getCarShop();
		try {
			if(!userIsOwner()) {
				throw new Exception("No permission to update business information");
			}

			if(startDate.compareTo(endDate)>0 || (startDate.compareTo(endDate) == 0 && startTime.compareTo(endTime)>0)) {
				throw new Exception("Start time must be before end time");
			}

			@SuppressWarnings("deprecation")
			Date currentDay = new Date(CarShopApplication.getSystemDate().getYear(), CarShopApplication.getSystemDate().getMonth(), CarShopApplication.getSystemDate().getDay());
			@SuppressWarnings("deprecation")
			Time currentTime = new Time(CarShopApplication.getSystemDate().getHours(), CarShopApplication.getSystemDate().getMinutes(), 0);

			if(startDate.compareTo(currentDay) < 0
					|| (startDate.compareTo(currentDay) == 0 && startTime.compareTo(currentTime) < 0)) {
				if(type.equals("vacation")) {
					throw new Exception("Vacation cannot start in the past");
				}
				else {
					throw new Exception("Holiday cannot start in the past");
				}
			}

			String overlapType = timeSlotsOverlap(startDate, startTime, endDate, endTime, null);
			if(overlapType != null) {
				if(type.equals("vacation") && overlapType.equals("vacation")) {
					throw new Exception("Vacation times cannot overlap");
				}
				else if(type.equals("holiday") && overlapType.equals("holiday")) {
					throw new Exception("Holiday times cannot overlap");
				}
				else {
					throw new Exception("Holiday and vacation times cannot overlap");
				}
			}

			TimeSlot newTimeSlot = carShop.addTimeSlot(startDate, startTime, endDate, endTime);
			if(type.equals("vacation")) {
				carShop.getBusiness().addVacation(newTimeSlot);
			} else {
				carShop.getBusiness().addHoliday(newTimeSlot);
			}
			CarshopPersistence.save(carShop);			// Serialize the carShop and save to the disk
		}
		catch (RuntimeException e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * Helper method to verify if a new time slot overlaps with an existing one. Used as part of input validation.
	 * @param startDate
	 * @param startTime
	 * @param endDate
	 * @param endTime
	 * @param exclude
	 * @return
	 */
	private static String timeSlotsOverlap(Date startDate, Time startTime, Date endDate, Time endTime, List<TimeSlot> exclude) {
		CarShop carShop = CarShopApplication.getCarShop();
		String overlapppingType = null;

		for(TimeSlot t: carShop.getBusiness().getVacations()) {
			if(exclude != null && exclude.contains(t)) {
				continue;
			}
			// Check if overlaps with vacations
			if(!(t.getStartDate().compareTo(endDate) >= 0 || t.getEndDate().compareTo(startDate) <= 0) 
					|| (t.getStartDate().compareTo(startDate) == 0 && !(t.getStartTime().compareTo(endTime) > 0 || t.getEndTime().compareTo(startTime) < 0))
					|| (t.getEndDate().compareTo(endDate) == 0 && !(t.getStartTime().compareTo(endTime) > 0 || t.getEndTime().compareTo(startTime) < 0))
					|| (t.getStartDate().compareTo(endDate) == 0 && t.getStartTime().compareTo(endTime) < 0)
					|| (t.getEndDate().compareTo(startDate) == 0 && (t.getEndTime().compareTo(startTime) > 0))) {
				overlapppingType = "vacation";
				break;
			}
		}

		for(TimeSlot t: carShop.getBusiness().getHolidays()) {
			if(exclude != null && exclude.contains(t)) {
				continue;
			}
			// Check if overlaps with holidays
			if(!(t.getStartDate().compareTo(endDate) >= 0 || t.getEndDate().compareTo(startDate) <= 0) 
					|| (t.getStartDate().compareTo(startDate) == 0 && !(t.getStartTime().compareTo(endTime) > 0 || t.getEndTime().compareTo(startTime) < 0))
					|| (t.getEndDate().compareTo(endDate) == 0 && !(t.getStartTime().compareTo(endTime) > 0 || t.getEndTime().compareTo(startTime) < 0))
					|| (t.getStartDate().compareTo(endDate) == 0 && t.getStartTime().compareTo(endTime) < 0)
					|| (t.getEndDate().compareTo(startDate) == 0 && (t.getEndTime().compareTo(startTime) > 0))) {
				overlapppingType = "holiday";
				break;
			}
		}

		return overlapppingType;
	}

	/**
	 * Gets a list of transfer objects corresponding to the existing time slots. To be used by the view.
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public static List<TOTimeSlot> getTimeSlots(String type) throws Exception {
		CarShop carShop = CarShopApplication.getCarShop();
		try {
			List<TOTimeSlot> timeSlots = new ArrayList<TOTimeSlot>();

			if(type.equals("vacation")) {
				for(TimeSlot t: carShop.getBusiness().getVacations()) {
					timeSlots.add(new TOTimeSlot(t.getStartDate(), t.getStartTime(), t.getEndDate(), t.getEndTime()));
				}
			} else {
				for(TimeSlot t: carShop.getBusiness().getHolidays()) {
					timeSlots.add(new TOTimeSlot(t.getStartDate(), t.getStartTime(), t.getEndDate(), t.getEndTime()));
				}
			}
			return timeSlots;
		}
		catch (RuntimeException e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * Updates an existing time slot. Includes input and permission validation.
	 * @param type
	 * @param oldStartDate
	 * @param oldStartTime
	 * @param newStartDate
	 * @param newStartTime
	 * @param newEndDate
	 * @param newEndTime
	 * @throws Exception
	 */
	public static void updateTimeSlot(String type, Date oldStartDate, Time oldStartTime, Date newStartDate, Time newStartTime, Date newEndDate, Time newEndTime) throws Exception {
		CarShop carShop = CarShopApplication.getCarShop();
		TimeSlot targetTimeSlot = null;
		try {
			if(!userIsOwner()) {
				throw new Exception("No permission to update business information");
			}

			if(newStartDate.compareTo(newEndDate)>0 || (newStartDate.compareTo(newEndDate) == 0 && newStartTime.compareTo(newEndTime)>0)) {
				throw new Exception("Start time must be before end time");
			}

			@SuppressWarnings("deprecation")
			Date currentDay = new Date(CarShopApplication.getSystemDate().getYear(), CarShopApplication.getSystemDate().getMonth(), CarShopApplication.getSystemDate().getDay());
			@SuppressWarnings("deprecation")
			Time currentTime = new Time(CarShopApplication.getSystemDate().getHours(), CarShopApplication.getSystemDate().getMinutes(), 0);

			if(newStartDate.compareTo(currentDay) < 0
					|| (newStartDate.compareTo(currentDay) == 0 && newStartTime.compareTo(currentTime) < 0)) {
				if(type.equals("vacation")) {
					throw new Exception("Vacation cannot start in the past");
				}
				else {
					throw new Exception("Holiday cannot start in the past");
				}
			}

			if(type.equals("vacation")) {
				for(TimeSlot t: carShop.getBusiness().getVacations()) {
					if(t.getStartDate().equals(oldStartDate) && t.getStartTime().equals(oldStartTime)) {
						targetTimeSlot = t;
						break;
					}
				}
			} else {
				for(TimeSlot t: carShop.getBusiness().getHolidays()) {
					if(t.getStartDate().equals(oldStartDate) && t.getStartTime().equals(oldStartTime)) {
						targetTimeSlot = t;
						break;
					}
				}
			}

			String overlapType = timeSlotsOverlap(newStartDate, newStartTime, newEndDate, newEndTime, targetTimeSlot == null ? null : Arrays.asList(targetTimeSlot));
			if(overlapType != null) {
				if(type.equals("vacation") && overlapType.equals("vacation")) {
					throw new Exception("Vacation times cannot overlap");
				}
				else if(type.equals("holiday") && overlapType.equals("holiday")) {
					throw new Exception("Holiday times cannot overlap");
				}
				else {
					throw new Exception("Holiday and vacation times cannot overlap");
				}
			}

			targetTimeSlot.setStartDate(newStartDate);
			targetTimeSlot.setStartTime(newStartTime);
			targetTimeSlot.setEndDate(newEndDate);
			targetTimeSlot.setEndTime(newEndTime);
			CarshopPersistence.save(carShop);			// Serialize the carShop and save to the disk
		}
		catch (RuntimeException e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * Finds and returns a given time slot as a transfer object. If the time slot doesn't exist, return null.
	 * @param type
	 * @param startDate
	 * @param startTime
	 * @return
	 * @throws Exception
	 */
	public static TOTimeSlot findTimeSlot(String type, Date startDate, Time startTime) throws Exception {
		TOTimeSlot timeSlot = null;
		for(TOTimeSlot t: getTimeSlots(type)) {
			if(t.getStartDate().equals(startDate)
					&& t.getStartTime().equals(startTime)) {
				timeSlot = t;
				break;
			}
		}
		return timeSlot;
	}

	/**
	 * Removes a given time slot from the model. Includes input and permission validation.
	 * @param type
	 * @param startDate
	 * @param startTime
	 * @param endDate
	 * @param endTime
	 * @throws Exception
	 */
	public static void removeTimeSlot(String type, Date startDate, Time startTime, Date endDate, Time endTime) throws Exception {
		CarShop carShop = CarShopApplication.getCarShop();
		try {

			if(!userIsOwner()) {
				throw new Exception("No permission to update business information");
			}

			TimeSlot timeSlotToRemove = null;
			if(type.equals("vacation")) {
				for(TimeSlot t: carShop.getBusiness().getVacations()) {
					if(t.getStartDate().equals(startDate) && t.getStartTime().equals(startTime) && t.getEndDate().equals(endDate) && t.getEndTime().equals(endTime)) {
						timeSlotToRemove = t;
						break;
					}
				}
				if(timeSlotToRemove != null) {
					carShop.getBusiness().removeVacation(timeSlotToRemove);
				}
			} else {
				for(TimeSlot t: carShop.getBusiness().getHolidays()) {
					if(t.getStartDate().equals(startDate) && t.getStartTime().equals(startTime) && t.getEndDate().equals(endDate) && t.getEndTime().equals(endTime)) {
						timeSlotToRemove = t;
						break;
					}
				}
				if(timeSlotToRemove != null) {
					carShop.getBusiness().removeHoliday(timeSlotToRemove);
				}
			}
			CarshopPersistence.save(carShop);			// Serialize the carShop and save to the disk
		}
		catch (RuntimeException e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * Checks in all of the users if any of them has the specified userName
	 * @param username
	 * @return a boolean indicating if there is a user with that userName
	 */
	public static boolean hasUserWithUsername(String username) {
		CarShop carShop = CarShopApplication.getCarShop();
		if (carShop.hasOwner()) {
			if (carShop.getOwner().getUsername().equals(username)) {
				return true;
			}
		}
		if (carShop.hasTechnicians()) {
			for (Technician tech : carShop.getTechnicians()) {
				if (tech.getUsername().equals(username)) {
					return true;
				}
			}
		}
		if (carShop.hasCustomers()) {
			for (Customer customer : carShop.getCustomers()) {
				if (customer.getUsername().equals(username)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Helper method to look if the logged in user is the owner.
	 * @return a boolean indicating if the user is owner.
	 */
	private static boolean userIsOwner() {
		String user = getLoggedInUser();
		return user != null && user.equals("owner");
	}

	/**
	 * Helper method to look if the logged in user is technician.
	 * @return a boolean indicating if the user is technician.
	 */
	private static boolean userIsTechnician() {
		String user = getLoggedInUser();
		return user != null && user.contains("-Technician");
	}

	/**
	 * Helper method to check the validity of the inputs to create a customer Account
	 * @param userName
	 * @param password
	 * @throws Exception
	 */
	private static void checkValidityOfCustomerCreation(String userName, String password) throws Exception{
		// If userName is already picked
		if (hasUserWithUsername(userName)) {
			throw new Exception("The username already exists");
		}
		// Can't enter empty userName or password
		if (userName == null || userName.equals("")) {
			throw new Exception("The user name cannot be empty");
		}
		if (password == null || password.equals("")) {
			throw new Exception("The password cannot be empty");
		}
		// Can't create a customer account while logged in as a technician or owner
		if (userIsOwner()) {
			throw new Exception("You must log out of the owner account before creating a customer account");
		}
		if (userIsTechnician()) {
			throw new Exception("You must log out of the technician account before creating a customer account");
		}
	}

	/**
	 * Helper method to get the logged in user
	 * @throws Exception
	 * @return the logged in user (String userName)
	 */
	public static String getLoggedInUser() {
		String user = null;
		try {
			user = CarShopApplication.getLoggedInUser();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	/**
	 * Helper method to check the validity of the inputs to update a customer Account
	 * @param newUserName
	 * @param newPassword
	 * @throws Exception
	 */
	private static void checkValidityOfCustomerUpdate(String newUserName, String newPassword) throws Exception{
		// If the user is owner, he can only change password
		if (userIsOwner()) {
			if (!getLoggedInUser().equals(newUserName)) {
				throw new Exception("Changing username of owner is not allowed");
			}
		}
		// If the user is technician, he can only change password
		if (userIsTechnician()) {
			if (!getLoggedInUser().equals(newUserName)) {
				throw new Exception("Changing username of technician is not allowed");
			}		
		}
		// If the userName already exists, and it is not his own account, userName already picked
		if (hasUserWithUsername(newUserName) && !getLoggedInUser().equals(newUserName)) {
			throw new Exception("Username not available");
		}
		// Can't enter empty userName or password
		if (newUserName == null || newUserName.equals("")) {
			throw new Exception("The user name cannot be empty");
		}
		if (newPassword == null || newPassword.equals("")) {
			throw new Exception("The password cannot be empty");
		}
	}

	/**
	 * Updates the customer account.
	 * @param newUserName
	 * @param newPassword
	 * @return true if account updated successfully
	 * @throws Exception
	 */
	public static boolean updateCustomer(String newUserName, String newPassword) throws Exception {
		CarShop carShop = CarShopApplication.getCarShop();
		boolean isUpdated = false;
		String currentUserName = getLoggedInUser();
		checkValidityOfCustomerUpdate(newUserName, newPassword);
		try {
			if (carShop.getOwner().getUsername().equals(currentUserName)) {
				carShop.getOwner().setUsername(newUserName);
				carShop.getOwner().setPassword(newPassword);
				isUpdated = true;
			}
			if(!isUpdated) {
				for (Customer customer : carShop.getCustomers()) {
					if (customer.getUsername().equals(currentUserName)) {
						customer.setUsername(newUserName);
						customer.setPassword(newPassword);
						isUpdated = true;
					}
				}
			}
			if(!isUpdated) {
				for (Technician tech : carShop.getTechnicians()) {
					if (tech.getUsername().equals(currentUserName)) {
						tech.setUsername(newUserName);
						tech.setPassword(newPassword);
						isUpdated = true;
					}
				}
			}
			CarshopPersistence.save(carShop);			// Serialize the carShop and save to the disk
		}
		catch (RuntimeException e) {
			throw new Exception(e.getMessage());
		}
		return isUpdated;
	}


	/**
	 * Used to add services
	 * @param name
	 * @param duration
	 * @param garage
	 * @throws Exception
	 */
	public static void addService(String name, int duration, Garage garage) throws Exception {
		CarShop carShop = CarShopApplication.getCarShop();
		// checks if the user logged in is the owner 
		if(!userIsOwner()) {
			throw new Exception("You are not authorized to perform this operation");
		}
		try {
			// checks if the parameters passed are true and then adds the service
			if(checkServiceParameters(duration) && (!addExistingServices(name, garage))) {
				garage.addService(name, carShop, duration);
				
			}
			CarshopPersistence.save(carShop);			// Serialize the carShop and save to the disk
		}
			
		catch (RuntimeException e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * Helper method to check if the service that has to be added is already in the model or not 
	 * @param name
	 * @param garage
	 * @return
	 * @throws Exception
	 */
	private static boolean addExistingServices(String name, Garage garage) throws Exception{
		CarShop carShop = CarShopApplication.getCarShop();
		boolean serviceExists = false;
		
		// checks if service that has to be added already exists
		for(Garage g: carShop.getGarages()) {
			if(g.equals(garage)) {
				for(Service s: g.getServices()) {
					if(s.getName().equals(name)) {
						serviceExists = true;
						throw new Exception("Service " + name + " already exists");
					}
				}
			}
		}
		// returns true if the service already exists else returns false
		return serviceExists;

	}

	/**
	 * Helper method to check if the duration for a service has a positive value
	 * @param duration
	 * @return
	 * @throws Exception
	 */
	private static boolean checkServiceParameters(int duration) throws Exception {
		boolean parameters = true;
		if(duration<=0) {
			parameters = false;
			 throw new Exception("Duration must be positive");
					
		}
		// returns true if the duration passed is positive else returns false
		return parameters;
	}

	
	/**
	 * Method used to update services in the model
	 * @param name
	 * @param changedname
	 * @param duration
	 * @param garage
	 * @throws Exception
	 */
	public static void updateService(String name,String changedname, int duration, Garage garage) throws Exception {
		CarShop carShop = CarShopApplication.getCarShop();
		Service serviceUpdate = null;
		
		//checks if the user logged is the owner 
		if(!userIsOwner()) {
			throw new Exception("You are not authorized to perform this operation");
		}
			
		// checks if the service to be added exists in the system
			for(int i=0; i < carShop.getBookableServices().size();i++) {
				if(carShop.getBookableService(i) instanceof Service) {
					serviceUpdate = (Service) carShop.getBookableService(i);
					if(serviceUpdate.getName().equals(name)) {
						break;
					}
					else {
						throw new Exception("This service cannot be added");
					}
				}
			}
	
			try {
			
				if(!updateExistingServices(serviceUpdate, changedname, duration) && checkServiceParameters(duration)){
					serviceUpdate.setName(changedname);
					serviceUpdate.setDuration(duration);
					serviceUpdate.setGarage(garage);
				}
				CarshopPersistence.save(carShop);			// Serialize the carShop and save to the disk
			}
		catch (RuntimeException e){
			throw new Exception(e.getMessage());

		}

	}
	
	/**
	 * Helper method to check is the service that has to be updated already exists 
	 * @param serviceUpdate
	 * @param changedName
	 * @param duration
	 * @return
	 * @throws Exception
	 */
	private static boolean updateExistingServices(Service serviceUpdate, String changedName, int duration) throws Exception {
		CarShop carShop = CarShopApplication.getCarShop();
		boolean serviceUpdate1 = false; 
		
		// checks for service to be update already exists or not
		for(BookableService s: carShop.getBookableServices()) {
			if(s instanceof Service) {
				if(!serviceUpdate.getName().equals(changedName) && s.getName().equals(changedName)) {
					throw new Exception("Service " + changedName + " already exists");
				}
			}
			
		}
		// returns true if the service already exists else returns false
		return serviceUpdate1;
	}
	
	public static void addServiceComboFromView(TOServiceCombo toCombo) throws Exception {

		String services = "";
		String mandatory = "";
		for(int i=0 ; i< toCombo.getServices().size(); i++) {
			services += toCombo.getService(i).getService().getName();
			mandatory += Boolean.toString(toCombo.getService(i).getMandatory());
			if (i < toCombo.getServices().size() - 1) {
				services += ",";
				mandatory += ",";
			}
		}
		
		defineServiceCombo(CarShopApplication.getLoggedInUser(), toCombo.getName(), services, mandatory, toCombo.getService(0).getService().getName());
	}

	/**
	 * Allows the owner to define a service combo
	 * @param user
	 * @param name
	 * @param service
	 * @param mandatory
	 * @param mainService
	 * @throws Exception
	 */
	
	public static void defineServiceCombo(String user, String name, String services, String mandatory, String mainService) throws Exception{
		CarShop carShop = CarShopApplication.getCarShop();
		Service service;
		boolean mandatoryToSet;
		ComboItem Item;
		String []serviceList = services.split(",");
		String[] mandatoryList = mandatory.split(",");
		
		if(!userIsOwner()) {
			throw new Exception ("You are not authorized to perform this operation");
		}
		if((serviceComboDuplicateCheck(name))!=null) {
			throw new Exception ("Service combo "+name+" already exists");
		}

		for(int i=0; i<serviceList.length; i++) {
			if(serviceCheck(serviceList[i])==null) {
				throw new Exception ("Service " + serviceList[i] + " does not exist");
			}
		}
		if(!(services.contains(mainService))) {
			throw new Exception("Main service must be included in the services");
		}
		for(int i=0; i<serviceList.length; i++) {
			if(serviceList[i].equals(mainService)) {
				if(!(Boolean.parseBoolean(mandatoryList[i]))) {
					throw new Exception ("Main service must be mandatory");
				}
			}
		}
		if(serviceList.length<2) {
			throw new Exception("A service Combo must contain at least 2 services");
		}
		if((serviceCheck(mainService))==null) {
			throw new Exception ("Service " + mainService + " does not exist");
		}
		
		try {
			ServiceCombo combo = new ServiceCombo(name, carShop);
			for(int i=0; i<serviceList.length; i++) {
				service = serviceCheck(serviceList[i]);
				mandatoryToSet = Boolean.valueOf(mandatoryList[i]);
				Item = new ComboItem(mandatoryToSet, service, combo);
				if(Item.getService().getName().equals(mainService)) {
					combo.setMainService(Item);
				}
				combo.addService(Item);
			}
			CarshopPersistence.save(carShop);			// Serialize the carShop and save to the disk
		}
		catch (RuntimeException e) {
			throw new Exception(e.getMessage());
		}
		
	}
	
	/**
	 * Allows the owner to update an existing service combo
	 * @param user
	 * @param existingCombo
	 * @param name
	 * @param service
	 * @param mandatory
	 * @param mainService
	 * @throws Exception
	 */
	
	public static void updateServiceCombo(String user, String existingCombo, String name, String services, String mandatory, String mainService) throws Exception{
		CarShop carShop = CarShopApplication.getCarShop();
		Service service;
		boolean mandatoryToSet;
		ComboItem Item;
		String []serviceList = services.split(",");
		String[] mandatoryList = mandatory.split(",");
		
		if(!userIsOwner()) {
			throw new Exception ("You are not authorized to perform this operation");
		}
		if((serviceComboDuplicateCheck(name))!=null && !name.equals(existingCombo)) {
			throw new Exception ("Service "+name+" already exists");
		}
		if((serviceCheck(mainService))==null) {
			throw new Exception ("Service " + mainService + " does not exist");
		}
		if(!(services.contains(mainService))) {
			throw new Exception("Main service must be included in the services");
		}
	
		for(int i=0; i<serviceList.length; i++) {
			if(serviceCheck(serviceList[i])==null) {
				throw new Exception ("Service " + serviceList[i] + " does not exist");
			}		
		}
		if(serviceList.length<2) {
			throw new Exception("A service Combo must contain at least 2 services");
		}
		for(int j=0; j<serviceList.length; j++) {
			if(serviceList[j].equals(mainService)) {
				if(!(Boolean.parseBoolean(mandatoryList[j]))) {
					throw new Exception ("Main service must be mandatory");
				}
			}
		}
		
		try {
			ServiceCombo combo = serviceComboDuplicateCheck(existingCombo);
			combo.setName(name);
			for (int i = 0; i < serviceList.length; i++) {
				service = serviceCheck(serviceList[i]);
				mandatoryToSet = Boolean.valueOf(mandatoryList[i]);
				Item = new ComboItem(mandatoryToSet, service, combo);
	
				combo.addService(Item);
				if (Item.getService().getName().equals(mainService)) {
					combo.setMainService(Item);
	
				}
			}
			CarshopPersistence.save(carShop);			// Serialize the carShop and save to the disk
		}
		catch (RuntimeException e) {
			throw new Exception(e.getMessage());
		}
		
	}
	
	

	/**
	 * Helper method to find a service 
	 * @param name
	 * @return the service that is searched for
	 */
	private static Service serviceCheck(String name) {
		CarShop carShop = CarShopApplication.getCarShop();
		BookableService specificService = null;
		for (BookableService service : carShop.getBookableServices()) {
			if (service instanceof Service) {
				if (service.getName().equals(name)) {
					specificService = service;

				}
			}
		}
		return (Service) specificService;
	}
	
	/**
	 * Helper method to find a service combo
	 * @param name
	 * @return the service combo that is searched for
	 */
	private static ServiceCombo serviceComboDuplicateCheck(String name) {
		CarShop carShop = CarShopApplication.getCarShop();
		BookableService specificServiceCombo = null;
		for (BookableService serviceCombo : carShop.getBookableServices()) {
			if (serviceCombo instanceof ServiceCombo) {
				if (serviceCombo.getName().equals(name)) {
					specificServiceCombo = serviceCombo;
				}
			}
		}
		return (ServiceCombo) specificServiceCombo;
	}
	
	public static boolean logIn(String username, String password) throws Exception{
		CarShop carShop = CarShopApplication.getCarShop();
		User currentUser = null;
		boolean isLoggedIn = false;

		
		List<User> users = new ArrayList<User>();
		users.addAll(carShop.getTechnicians());
		users.addAll(carShop.getCustomers());
		users.add(carShop.getOwner());
		
		try {
			if(User.hasWithUsername(username)) {
				currentUser = User.getWithUsername(username);
				if(currentUser.getPassword().equals(password)) {
					CarShopApplication.setLoggedInUser(currentUser.getUsername());
					isLoggedIn = true;
				}
				else {
					throw new Exception("Username/password not found");
				}
			}
			else if (username.equals("owner")) {
				createOwner(username, password);
			}
			else if (username.contains("Technician")) {
				TechnicianType technicianType = null;
				if (username.contains("Tire")) {
					technicianType = Technician.TechnicianType.valueOf("Tire");
				}
				else if (username.contains("Engine")) {
					technicianType = Technician.TechnicianType.valueOf("Engine");
				}
				else if (username.contains("Transmission")) {
					technicianType = Technician.TechnicianType.valueOf("Transmission");
				}
				else if (username.contains("Electronics")) {
					technicianType = Technician.TechnicianType.valueOf("Electronics");
				}
				else if (username.contains("Fluids")) {
					technicianType = Technician.TechnicianType.valueOf("Fluids");
				}
    			createTechnician(username, password, technicianType);
			}
			else {
				throw new Exception("Username/password not found");
			}
		} catch (RuntimeException e) {
			throw new Exception(e.getMessage());
		}
		
		return isLoggedIn;
	}
	
	public static boolean logout() throws Exception{
		boolean isLoggedOut = false;
		try {
			CarShopApplication.setLoggedInUser(null);
			isLoggedOut = true;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return isLoggedOut;
	}
	
	public static boolean createdAccount() throws Exception {
		boolean createdAccount = true;
		try {
			if (User.hasWithUsername(CarShopApplication.getLoggedInUser())) {
				createdAccount = false;
			}
		} catch (RuntimeException e) {
			throw new Exception(e.getMessage());
		}
		
		return createdAccount;
	}
	
	public static boolean createdGarage() throws Exception {
		CarShop carShop = CarShopApplication.getCarShop();
		boolean garageAssociatedToNewTechnician = true;
		
		for(Technician t: carShop.getTechnicians()) {
			if(!t.hasGarage()) {
				garageAssociatedToNewTechnician = false;
			}
		}
		return garageAssociatedToNewTechnician;
	}

	
	public static boolean hasOpeningHoursToGarageOfTechnicianType(String day, String startTime, String endTime, String type) throws Exception {
		CarShop carShop = CarShopApplication.getCarShop();
		Garage garage = carShop.getGarage(TechnicianType.valueOf(type).ordinal());
		List<BusinessHour>businessHours = garage.getBusinessHours();
		
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
		DayOfWeek dayOfWeek = DayOfWeek.valueOf(day);
		Time start = new Time(sdf.parse(startTime).getTime());
		Time end = new Time(sdf.parse(endTime).getTime());
		BusinessHour aBusinessHour = new BusinessHour(dayOfWeek, start, end, carShop);
		
		boolean hasBusinessHour = false;
		try {
			for (BusinessHour b: businessHours) {
				if (b == aBusinessHour) {
					hasBusinessHour = true;
				}
			}
		}
		catch (RuntimeException e) {
			throw new Exception(e.getMessage());
		}
		
		return hasBusinessHour;
	}
	
	public static boolean addHoursToGarageOfTechnicianType(String day, String startTime, String endTime, String type) throws Exception {
		CarShop carShop = CarShopApplication.getCarShop();
		Garage garage = carShop.getGarage(TechnicianType.valueOf(type).ordinal());
		boolean wasAdded = false;
		
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
		DayOfWeek dayOfWeek = DayOfWeek.valueOf(day);
		Time start = new Time(sdf.parse(startTime).getTime());
		Time end = new Time(sdf.parse(endTime).getTime());

		
		//add checks
		if(start.compareTo(end)>0) {
			throw new Exception("Start time must be before end time");
		}
		
		if(intersectsWithBusinessHour(dayOfWeek, start, end, garage.getBusinessHours())) {
			throw new Exception("The opening hours cannot overlap");
		}
		
		if(!overlapsWithBusinessHour(dayOfWeek, start, end, carShop.getBusiness().getBusinessHours())) {
			throw new Exception("The opening hours are not within the opening hours of the business");
		}
		
		if(!CarShopApplication.getLoggedInUser().equals(garage.getTechnician().getUsername())) {
			throw new Exception("You are not authorized to perform this operation");
		}
		
		try {
			BusinessHour newBusinessHour = new BusinessHour(dayOfWeek, start, end, carShop);
			garage.addBusinessHour(newBusinessHour);
			CarshopPersistence.save(carShop);			// Serialize the carShop and save to the disk
			wasAdded = true;
		}
		catch (RuntimeException e) {
			throw new Exception(e.getMessage());
		}
		return wasAdded;
	}
	
	public static boolean removeHoursToGarageOfTechnicianType(String day, String startTime, String endTime, String type) throws Exception {
		CarShop carShop = CarShopApplication.getCarShop();
		Garage garage = carShop.getGarage(TechnicianType.valueOf(type).ordinal());
		ArrayList<BusinessHour> toRemove = new ArrayList<BusinessHour>();
		boolean removed = false;
				
		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
		DayOfWeek dayOfWeek = DayOfWeek.valueOf(day);
		Time start = new Time(sdf.parse(startTime).getTime());
		Time end = new Time(sdf.parse(endTime).getTime());
		
		//add checks
		if(!CarShopApplication.getLoggedInUser().equals(garage.getTechnician().getUsername())) {
			throw new Exception("You are not authorized to perform this operation");
		}
		
		try {
			for(BusinessHour h: garage.getBusinessHours()) {
				if(h.getDayOfWeek().equals(dayOfWeek) && h.getStartTime().equals(start) && h.getEndTime().equals(end)) {
					toRemove.add(h);
				}
			}
			garage.removeBusinessHour(toRemove.get(0));
			toRemove.get(0).delete();
			CarshopPersistence.save(carShop);			// Serialize the carShop and save to the disk
			removed = true;
		}
		catch (RuntimeException e) {
			throw new Exception(e.getMessage());
		}
		return removed;
		
	}
	
	private static boolean overlapsWithBusinessHour(DayOfWeek day, Time startTime, Time endTime, List<BusinessHour> hours) {
		  
		  boolean contains = false;

			for(BusinessHour h: hours) {
				// For business hours on the same day we want
				// The startime of the existing schedule to be after the endtime of the new schedule or
				// The endtime of the existing schedule to be before the starttime of the new schedule
				if(h.getDayOfWeek() == day && h.getStartTime().compareTo(startTime) <= 0 && h.getEndTime().compareTo(endTime) >= 0) {
					contains = true;
					break;
				}
			}

			return contains;
	  }
	
	private static boolean intersectsWithBusinessHour(DayOfWeek day, Time startTime, Time endTime, List<BusinessHour> hours) {
		  
		  boolean isOverlapping = false;

			for(BusinessHour h: hours) {
				// For business hours on the same day we want
				// The startime of the existing schedule to be after the endtime of the new schedule or
				// The endtime of the existing schedule to be before the starttime of the new schedule
				if(h.getDayOfWeek() == day && !(h.getStartTime().compareTo(endTime) >= 0 || h.getEndTime().compareTo(startTime) <= 0)) {
					isOverlapping = true;
					break;
				}
			}

			return isOverlapping;
	  }
	
	public static boolean isCustomerLoggedIn() {
		boolean isCustomer = false;
		try {
			User user = User.getWithUsername(CarShopApplication.getLoggedInUser());
			if (user instanceof Customer) {
				isCustomer = true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isCustomer;
	}
	
	public static boolean isTechnicianLoggedIn() {
		boolean isTechnician = false;
		try {
			User user = User.getWithUsername(CarShopApplication.getLoggedInUser());
			if (user instanceof Technician) {
				isTechnician = true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isTechnician;
	}
	
	public static List<TOBusinessHour> getBusinessHours(String type){
		CarShop carShop = CarShopApplication.getCarShop();
		List<TOBusinessHour> businessHours = new ArrayList<TOBusinessHour>();
		try {
			
			if(type.equals("business")){
				for(BusinessHour bh: carShop.getBusiness().getBusinessHours()) {
					businessHours.add(new TOBusinessHour(bh.getDayOfWeek().name(), bh.getStartTime(), bh.getEndTime()));
				}
			} else {
				for(Garage g: carShop.getGarages()) {
					if(g.getTechnician().getType().name().toLowerCase().contains(type)) {
						for(BusinessHour bh: g.getBusinessHours()) {
							businessHours.add(new TOBusinessHour(bh.getDayOfWeek().name(), bh.getStartTime(), bh.getEndTime()));
						}
						break;
					}
				}
			}
			businessHours.sort(new BusinessHourComparator());
		} catch (Exception e) {
			
		}
		return businessHours;

	}
	
	static class BusinessHourComparator implements Comparator<TOBusinessHour>
	 {
		private List<String> days = new ArrayList<String>() {
            {
                add("Monday");
                add("Tuesday");
                add("Wednesday");
                add("Thursday");
                add("Friday");
                add("Saturday");
                add("Sunday");
            }
        };
		@Override
		public int compare(TOBusinessHour o1, TOBusinessHour o2) {
			return days.indexOf(o1.getDayOfWeek()) - days.indexOf(o2.getDayOfWeek());
		}
	 }
	
	public static String getLoggedInTechnicianType() {
		String type = "";
		try {
			User user = User.getWithUsername(CarShopApplication.getLoggedInUser());
			if (user instanceof Technician) {
				type = ((Technician)user).getType().name();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return type;
	}
	
	public static TOService getTOService(String name) {
		CarShop carShop = CarShopApplication.getCarShop();
		TOService service = null;
		for(BookableService bs: carShop.getBookableServices()) {
			if(bs.getName().equals(name)) {
				service = new TOService(name, ((Service) bs).getDuration());
				break;
			}
		}
		return service;
	}
	
	public static String getLoggedInUsername() throws Exception {
		return CarShopApplication.getLoggedInUser();
	}
	
	public static boolean addBusinessHourFromDayAndTime(String day, Time startTime, Time endTime) throws Exception {
		DayOfWeek dayOfWeek = DayOfWeek.valueOf(day);
		boolean wasAdded = false;
		try {
			CarShopController.addBusinessHour(dayOfWeek, startTime, endTime);
			wasAdded = true;
		} catch(Exception e) {
			throw new Exception(e.getMessage());
		}
		
		return wasAdded;
	}
	
	public static boolean removeBusinessHoursOnThatDay(String day) throws Exception{
		boolean wasRemoved = false;
		CarShop carShop = CarShopApplication.getCarShop();
		ArrayList<BusinessHour> bhOnThatDay = new ArrayList<BusinessHour>();
		try { 
			for (BusinessHour bh : carShop.getBusiness().getBusinessHours()) {
				if (bh.getDayOfWeek() == DayOfWeek.valueOf(day)) {
					bhOnThatDay.add(bh);
				}
			}
			if (bhOnThatDay.size() == 0) {
				throw new Exception("No existing hours on that day.");
			}
			else {
				for (BusinessHour bh : bhOnThatDay) {
					removeBusinessHour(DayOfWeek.valueOf(day), bh.getStartTime());
				}
			}
			wasRemoved = true;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
		return wasRemoved;
	}
}
	
	



