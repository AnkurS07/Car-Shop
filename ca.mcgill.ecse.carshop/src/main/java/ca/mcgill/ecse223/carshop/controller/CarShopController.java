package ca.mcgill.ecse223.carshop.controller;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ca.mcgill.ecse.carshop.application.CarShopApplication;
import ca.mcgill.ecse.carshop.model.Business;
import ca.mcgill.ecse.carshop.model.BusinessHour;
import ca.mcgill.ecse.carshop.model.BusinessHour.DayOfWeek;
import ca.mcgill.ecse.carshop.model.CarShop;
import ca.mcgill.ecse.carshop.model.Owner;
import ca.mcgill.ecse.carshop.model.Technician;
import ca.mcgill.ecse.carshop.model.Technician.TechnicianType;
import ca.mcgill.ecse.carshop.model.TimeSlot;

/**
 * Controller class. Implements all controller methods as static methods. The controller should be stateless. It interacts with the View and the Model.
 * @author maxbo
 *
 */
public class CarShopController {
	
	private static final String regexEmail = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
	private static java.util.Date systemDate;
	private static String loggedInUser;
	
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
			carShop.addTechnician(userName, password, type);
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
	 */
	public static void createCustomer(String userName, String password) throws Exception {
		CarShop carShop = CarShopApplication.getCarShop();
		try {
			carShop.addCustomer(userName, password);
		}
		catch (RuntimeException e) {
			throw new Exception(e.getMessage());
		}
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
			if(!userCanUpdateBusinessInformation()) {
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
		if(!userCanUpdateBusinessInformation()) {
			throw new Exception("No permission to update business information");
		}
		
		Pattern patternEmail = Pattern.compile(regexEmail);
		Matcher matcherEmail = patternEmail.matcher(email);
		if(!matcherEmail.matches()) {
			throw new Exception("Invalid email");
		}
		
		carShop.getBusiness().setName(name);
		carShop.getBusiness().setAddress(address);
		carShop.getBusiness().setPhoneNumber(phoneNumber);
		carShop.getBusiness().setEmail(email);
		
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
			if(!userCanUpdateBusinessInformation()) {
				throw new Exception("No permission to update business information");
			}
			
			if(startTime.compareTo(endTime) > 0) {
				throw new Exception("Start time must be before end time");
			}
			
			if(businessHoursOverlap(day, startTime, endTime, null)) {
				throw new Exception("The business hours cannot overlap");
			}
			
			carShop.getBusiness().addBusinessHour(new BusinessHour(day, startTime, endTime, carShop));
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
		try {
			if(!userCanUpdateBusinessInformation()) {
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
			
			if(!userCanUpdateBusinessInformation()) {
				throw new Exception("No permission to update business information");
			}
			
			BusinessHour hourToRemove = findBusinessHour(day, startTime);
			if(hourToRemove != null) {
				carShop.getBusiness().removeBusinessHour(hourToRemove);
			}
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
	 * Helper method to look if a user has the permission to update the business information.
	 * @return
	 */
	private static boolean userCanUpdateBusinessInformation() {
		String user = null;
		try {
			user = getLoggedInUser();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user != null && user.equals("owner");
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
			if(!userCanUpdateBusinessInformation()) {
				throw new Exception("No permission to update business information");
			}
			
			if(startDate.compareTo(endDate)>0 || (startDate.compareTo(endDate) == 0 && startTime.compareTo(endTime)>0)) {
				throw new Exception("Start time must be before end time");
			}
			
			@SuppressWarnings("deprecation")
			Date currentDay = new Date(getSystemDate().getYear(), getSystemDate().getMonth(), getSystemDate().getDay());
			@SuppressWarnings("deprecation")
			Time currentTime = new Time(getSystemDate().getHours(), getSystemDate().getMinutes(), 0);
			
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
			if(!userCanUpdateBusinessInformation()) {
				throw new Exception("No permission to update business information");
			}
			
			if(newStartDate.compareTo(newEndDate)>0 || (newStartDate.compareTo(newEndDate) == 0 && newStartTime.compareTo(newEndTime)>0)) {
				throw new Exception("Start time must be before end time");
			}
			
			@SuppressWarnings("deprecation")
			Date currentDay = new Date(getSystemDate().getYear(), getSystemDate().getMonth(), getSystemDate().getDay());
			@SuppressWarnings("deprecation")
			Time currentTime = new Time(getSystemDate().getHours(), getSystemDate().getMinutes(), 0);
			
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
			
			if(!userCanUpdateBusinessInformation()) {
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
		}
		catch (RuntimeException e) {
			throw new Exception(e.getMessage());
		}
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
			CarShopController.loggedInUser = loggedInUser;
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
			CarShopController.systemDate = date;
		}
		catch (RuntimeException e) {
			throw new Exception(e.getMessage());
		}
	}
	
}
