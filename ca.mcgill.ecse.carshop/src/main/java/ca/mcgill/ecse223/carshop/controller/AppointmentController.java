
package ca.mcgill.ecse223.carshop.controller;

import static ca.mcgill.ecse223.carshop.controller.AppointmentController.parseDate;

import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import ca.mcgill.ecse.carshop.application.CarShopApplication;
import ca.mcgill.ecse.carshop.model.Appointment;
import ca.mcgill.ecse.carshop.model.BookableService;
import ca.mcgill.ecse.carshop.model.BusinessHour;
import ca.mcgill.ecse.carshop.model.BusinessHour.DayOfWeek;
import ca.mcgill.ecse.carshop.model.CarShop;
import ca.mcgill.ecse.carshop.model.Customer;
import ca.mcgill.ecse.carshop.model.Garage;
import ca.mcgill.ecse.carshop.model.Owner;
import ca.mcgill.ecse.carshop.model.Service;
import ca.mcgill.ecse.carshop.model.ServiceBooking;
import ca.mcgill.ecse.carshop.model.ServiceCombo;
import ca.mcgill.ecse.carshop.model.Technician.TechnicianType;
import ca.mcgill.ecse.carshop.model.TimeSlot;
import ca.mcgill.ecse.carshop.model.User;

public class AppointmentController {

	public static Appointment makeAppointment(boolean overrideErrors, Customer customer, String mainServiceName,
			Date date, Time time, List<TimeSlot> timeSlots, BookableService mainService, List<Service> optionalServices)
			throws Exception {
		CarShop carshop = CarShopApplication.getCarShop();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm");
		String t = sdf2.format(new Date(time.getTime()));
		if (t.charAt(0) == '0') {
			t = t.substring(1);
		}

		// do checks here
		if (!overrideErrors) {
			if (servicesOverlapping(timeSlots)) {
				throw new Exception("Time slots for two services are overlapping");
			}

			if (invalidTimeSlot(mainService.getName(), timeSlots.get(0))) {
				throw new Exception(
						"There are no available slots for " + mainServiceName + " on " + sdf.format(date) + " at " + t);
			}
		}
		Appointment appointment = new Appointment(customer, mainService, carshop); // TODO: check for
																					// exceptions

		// TODO: verify timeslots, optinoalServices, list lengths, check null values

		if(mainService instanceof Service) {
			appointment.addServiceBooking((Service) mainService, timeSlots.get(0));
		} else {
			//it's a service combo
			appointment.addServiceBooking(((ServiceCombo) mainService).getMainService().getService(), timeSlots.get(0));
		}
		
		for (int i = 0; i < optionalServices.size(); i++) {
			if (!overrideErrors && invalidTimeSlot(optionalServices.get(i).getName(), timeSlots.get(i+1))) {
				throw new Exception(
						"There are no available slots for " + optionalServices.get(i).getName() + " on " + sdf.format(date) + " at " + t);
			}
			appointment.addServiceBooking(optionalServices.get(i), timeSlots.get(i + 1));
		}

		return appointment;
	}

	public static void cancelAppointment(String customerName, String mainServiceName, Date date, Time time)
			throws Exception {

		Customer c;
		User user = User.getWithUsername(customerName);
		if (user instanceof Customer) {
			c = (Customer) user;
		} else if (user instanceof Owner) {
			throw new Exception("An owner cannot cancel an appointment");
		} else {
			throw new Exception("A technician cannot cancel an appointment");
		}

		if (!c.getUsername().equals(CarShopApplication.getLoggedInUser())) {
			throw new RuntimeException("A customer can only cancel their own appointments");
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String sysDate = sdf.format(new Date(CarShopApplication.getSystemDate().getTime()));
		Appointment app = findAppointment(c, mainServiceName, date, time);
		
		app.cancel(sdf.format(date), sysDate);
	}
	
	public static Appointment updateAppointment(boolean isNewService, Customer c, Appointment a, List<Service> newOptServices, List<TimeSlot> timeSlots, Date modificationDate) throws Exception {
		//check overlap
		// I did not put it in the state machine or else we need to copy
		// like 200 lines of helper methods and since we still need those here
		// to validate when a appointment is created it made no sense to duplicate them
		List<TimeSlot> totalTimeSlots = new ArrayList<TimeSlot>();
		for(ServiceBooking s: a.getServiceBookings()) {
			totalTimeSlots.add(s.getTimeSlot());
		}
		for(TimeSlot t: timeSlots) {
			totalTimeSlots.add(t);
		}
		
		if (servicesOverlapping(totalTimeSlots)) {
			throw new Exception("Time slots for two services are overlapping");
		}

		for(int i=0;i < newOptServices.size();i++) {
			if (invalidTimeSlot(newOptServices.get(i).getName(), timeSlots.get(i))) {
				throw new Exception("Invlalid time slot");
			}
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String appDate = sdf.format(a.getServiceBooking(0).getTimeSlot().getStartDate().getTime());
		String currentDate = sdf.format(modificationDate.getTime());
		
		a.update(newOptServices, timeSlots, currentDate, appDate, isNewService);

		return a;
	}

	public static Appointment findAppointment(Customer customer, String mainServiceName, Date date, Time time) {
		for (Appointment a : customer.getAppointments()) {
			ServiceBooking b = a.getServiceBooking(0);
			if (mainServiceName.contains(b.getService().getName()) && b.getTimeSlot().getStartDate().equals(date)
					&& b.getTimeSlot().getStartTime().equals(time)) {
				return a;
			}
		}
		return null;
	}
	
	public static void startAppointment(Date startDate, Appointment a) throws Exception {
		// add check with date in the state machine
		SimpleDateFormat day = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat time = new SimpleDateFormat("HH:mm");
		String appDateString = day.format(a.getServiceBooking(0).getTimeSlot().getStartDate().getTime());
		String appTimeString = time.format(a.getServiceBooking(0).getTimeSlot().getStartTime().getTime());
		String currentDateString = day.format(startDate.getTime());
		String currentTimeString = time.format(startDate.getTime());
		Date appDate = parseDate(appDateString, "yyyy-MM-dd");
		Date appTime = parseDate(appTimeString, "HH:mm");
		Date currentDate = parseDate(currentDateString, "yyyy-MM-dd");
		Date currentTime = parseDate(currentTimeString, "HH:mm");
		a.start(currentDate, appDate, currentTime, appTime);
	}
	
	public static void endAppointment(Date endDate,Appointment a) throws Exception {
		// add check with date in the state machine
		a.end();
	}
	
	public static void cancelAppointment(String date, Appointment a) throws Exception{
		java.util.Date currDate=new java.util.Date(); 
		Date date3 = parseDate(currDate.toString(), "yyyy-MM-dd+HH:mm");
		a.cancel(date, date3.toString());
	}
	
	public static String getAppointmentState(Appointment a) {
		return a.getAppStatusFullName();
	}
	
	/*private static Appointment findAppointment(Date startDate) {
		CarShop carShop = CarShopApplication.getCarShop();
		Appointment app = null;
		//find appointment with date
		for(Appointment a: carShop.getAppointments()) {
			Date sd = a.getServiceBooking(0).getTimeSlot().getStartDate();
			Time st = a.getServiceBooking(0).getTimeSlot().getStartTime();
			// if the appointment start date is within 30min of the appointment time, start it
			if(Math.abs(startDate.getTime() - sd.getTime() - st.getTime()) < 30*60*1000) {
				// this just changes state
				app = a;
				break;
			}
		}
		return app;
	}*/

	public static BookableService findBookableService(String name) {
		CarShop carShop = CarShopApplication.getCarShop();
		for(BookableService bs: carShop.getBookableServices()) {
			if(bs.getName().equals(name)) {
				return bs;
			}
		}
		return null;
	}
	
	public static Appointment findAppointment(List<TimeSlot> timeSlots) {
		CarShop carShop = CarShopApplication.getCarShop();
		boolean possibleMatch;
		for(Appointment a: carShop.getAppointments()) {
			possibleMatch = true;
			for(int i = 0; i < a.getServiceBookings().size() ; i++) {
				TimeSlot t1 = a.getServiceBooking(i).getTimeSlot();
				TimeSlot t2 = timeSlots.get(i);
				possibleMatch &= (t1.getStartDate().equals(t2.getStartDate())
						&& t1.getStartTime().equals(t2.getStartTime())
						&& t1.getEndDate().equals(t2.getEndDate())
						&& t1.getEndTime().equals(t2.getEndTime()));
			}
			if(possibleMatch) {
				return a;
			}
		}
		return null;
	}
	
	// HELPER METHODS //

	/**
	 * Converts a string date to a java.sql.Date object following the given
	 * formatString.
	 * 
	 * The formatString follows the conventions (bare minimum shown): yyyy for year
	 * MM for month dd for days HH for 24hr hours hh for 12hr hours mm for minutes
	 * ss for seconds aa for am/pm
	 * 
	 * 
	 * @param dateString
	 * @param formatString
	 * @return
	 */
	public static Date parseDate(String dateString, String formatString) {
		try {
			SimpleDateFormat format = new SimpleDateFormat(formatString);
			return new java.sql.Date(format.parse(dateString).getTime());
		} catch (ParseException e) {
			return null; // If date could not be parsed properly
		}
	}

	public static List<TimeSlot> generateTimeSlots(Date date, Time time, String[] timeSlotStrings) {
		return generateTimeSlots(date, time, Arrays.asList(timeSlotStrings));
	}
	
	public static List<TimeSlot> generateTimeSlots(Date date, Time time, Service mainService, List<Service> optionals) {
		CarShop carshop = CarShopApplication.getCarShop();
		List<TimeSlot> timeSlots = new ArrayList<>();

		Time oldTime = time;
		Time newTime = incrementTimeByMinutes(oldTime, mainService.getDuration());

		timeSlots.add(new TimeSlot(date, oldTime, date, newTime, carshop));

		for (Service opt : optionals) {
			oldTime = newTime;
			newTime = incrementTimeByMinutes(oldTime, opt.getDuration());
			timeSlots.add(new TimeSlot(date, oldTime, date, newTime, carshop));
		}

		return timeSlots;
	}

	public static Customer findCustomer(String username) throws Exception {
		User user = User.getWithUsername(username);
		if (user instanceof Customer) {
			return (Customer) user;
		} else {
			throw new Exception("Only customers can make an appointment");
		}
	}

	public static List<Service> findServices(List<String> serviceNames) {
		ArrayList<Service> services = new ArrayList<>();
		for (String name : serviceNames) {
			Service s = findService(name);
			if (s == null) {
				throw new RuntimeException("Warning: Service Does Not Exist!");
			}
			services.add(findService(name));
		}
		return services;
	}

	public static Service findService(String serviceName) {
		BookableService s = BookableService.getWithName(serviceName);
		if (s instanceof Service)
			return (Service) s;
		else if (s instanceof ServiceCombo)
			return ((ServiceCombo) s).getMainService().getService();
		else
			return null;
	}

	public static ServiceCombo findServiceCombo(String serviceName) {
		BookableService s = BookableService.getWithName(serviceName);
		if (s instanceof ServiceCombo)
			return (ServiceCombo) s;
		return null;
	}

	public static Appointment findAppointment(Customer customer, String mainService, List<String> optionals) {
		for (Appointment a : customer.getAppointments()) {
			boolean matchingApp = mainService.equals(a.getBookableService().getName());
			matchingApp &= optionals.size() + 1 == a.getServiceBookings().size();
			for (int i = 0; i < optionals.size() && i + 1 < a.getServiceBookings().size(); i++) {
				String bName = a.getServiceBooking(1 + i).getService().getName();
				matchingApp &= optionals.get(i).equals(bName);
			}
			if (matchingApp) {
				return a;
			}
		}
		return null;
	}

	public static Garage findGarage(String technicianType) {
		CarShop carshop = CarShopApplication.getCarShop();
		try {
			TechnicianType type = TechnicianType.valueOf(technicianType);
			for (Garage g : carshop.getGarages()) {
				if (type == g.getTechnician().getType()) {
					return g;
				}
			}
			return null;
		} catch (NullPointerException e) {
			return null;
		}
	}

	public static BusinessHour findBusinessHour(DayOfWeek day, Time startTime, Time endTime) {
		CarShop carshop = CarShopApplication.getCarShop();
		for (BusinessHour h : carshop.getBusiness().getBusinessHours()) {
			if (h.getDayOfWeek() == day && startTime.equals(h.getStartTime()) && endTime.equals(h.getEndTime())) {
				return h;
			}
		}
		return null;
	}

	public static BusinessHour findGarageHour(Garage garage, DayOfWeek day, Time startTime, Time endTime) {
		for (BusinessHour h : garage.getBusinessHours()) {
			if (h.getDayOfWeek() == day && startTime.equals(h.getStartTime()) && endTime.equals(h.getEndTime())) {
				return h;
			}
		}
		return null;
	}

	public static List<TimeSlot> generateTimeSlotsFromStarts(Date date, List<Time> times, Service mainService,
			Service[] optionalServices) {
		List<Service> services = new ArrayList<>();
		services.add(mainService);
		services.addAll(Arrays.asList(optionalServices));

		return generateTimeSlotsFromStarts(date, times, services.toArray(new Service[services.size()]));
	}
	
	// Private methods

	private static boolean servicesOverlapping(List<TimeSlot> timeSlots) {
		for (int i = 0; i < timeSlots.size(); i++) {
			for (int j = 0; j < timeSlots.size(); j++) {
				if (j != i && timeSlotOverlaps(timeSlots.get(i), timeSlots.get(j))) {
					TimeSlot t1 = timeSlots.get(i);
					TimeSlot t2 = timeSlots.get(j);
					if (!(t1.getStartTime().compareTo(t2.getEndTime()) >= 0
							|| t1.getEndTime().compareTo(t2.getStartTime()) <= 0)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	private static boolean invalidTimeSlot(String serviceName, TimeSlot t1) throws Exception {
		CarShop carShop = CarShopApplication.getCarShop();
		boolean invalid = false;
		@SuppressWarnings("deprecation")
		Date currentDay = new Date(CarShopApplication.getSystemDate().getYear(),
				CarShopApplication.getSystemDate().getMonth(), CarShopApplication.getSystemDate().getDay());
		@SuppressWarnings("deprecation")
		Time currentTime = new Time(CarShopApplication.getSystemDate().getHours(),
				CarShopApplication.getSystemDate().getMinutes(), 0);

		// appt in the past
		if (t1.getStartDate().compareTo(currentDay) < 0
				|| (t1.getStartDate().compareTo(currentDay) == 0 && t1.getStartTime().compareTo(currentTime) < 0)) {
			invalid = true;
		}

		// overlaps with another appointment
		for (Appointment a : carShop.getAppointments()) {
			for (ServiceBooking bs : a.getServiceBookings()) {
				if (serviceName.contains(
						bs.getService().getGarage().getTechnician().getType().name().toLowerCase().split("-")[0])
						&& timeSlotOverlaps(t1, bs.getTimeSlot())) {
					invalid = true;
					break;
				}
			}
		}

		for (TimeSlot t2 : carShop.getBusiness().getHolidays()) {
			if (timeSlotOverlaps(t1, t2)) {
				invalid = true;
				break;
			}
		}
		
		for (TimeSlot t2 : carShop.getBusiness().getVacations()) {
			if (timeSlotOverlaps(t1, t2)) {
				invalid = true;
				break;
			}
		}

		Calendar cal = Calendar.getInstance();
		cal.setTime(t1.getStartDate());
		DayOfWeek dayOfWeek = getDayOfWeek(cal.get(Calendar.DAY_OF_WEEK));

		if (!overlapsWithBusinessHour(dayOfWeek, t1.getStartTime(), t1.getEndTime(),
				carShop.getBusiness().getBusinessHours())) {
			invalid = true;
		}

		for (Garage g : carShop.getGarages()) {
			if (serviceName.contains(g.getTechnician().getType().name().toLowerCase().split("-")[0])
					&& !overlapsWithBusinessHour(dayOfWeek, t1.getStartTime(), t1.getEndTime(),
							g.getBusinessHours())) {
				invalid = true;
				break;
			}
		}
		return invalid;
	}

	private static boolean timeSlotOverlaps(TimeSlot t1, TimeSlot t2) {
		// Check if overlaps with vacations
		boolean overlapping = false;
		if (!(t2.getStartDate().compareTo(t1.getEndDate()) >= 0 || t2.getEndDate().compareTo(t1.getStartDate()) <= 0)
				|| (t2.getStartDate().compareTo(t1.getStartDate()) == 0
						&& !(t2.getStartTime().compareTo(t1.getEndTime()) > 0
								|| t2.getEndTime().compareTo(t1.getStartTime()) < 0))
				|| (t2.getEndDate().compareTo(t1.getEndDate()) == 0
						&& !(t2.getStartTime().compareTo(t1.getEndTime()) > 0
								|| t2.getEndTime().compareTo(t1.getStartTime()) < 0))
				|| (t2.getStartDate().compareTo(t1.getEndDate()) == 0
						&& t2.getStartTime().compareTo(t1.getEndTime()) < 0 && t2.getEndTime().compareTo(t1.getEndTime()) > 0)
				|| (t2.getEndDate().compareTo(t1.getStartDate()) == 0
						&& (t2.getEndTime().compareTo(t1.getStartTime()) > 0 && t2.getStartTime().compareTo(t1.getStartTime()) < 0))) {
			overlapping = true;
		}
		return overlapping;
	}

	private static boolean overlapsWithBusinessHour(DayOfWeek day, Time startTime, Time endTime,
			List<BusinessHour> hours) {

		boolean contains = false;

		for (BusinessHour h : hours) {
			// For business hours on the same day we want
			// The startime of the existing schedule to be after the endtime of the new
			// schedule or
			// The endtime of the existing schedule to be before the starttime of the new
			// schedule
			if (h.getDayOfWeek() == day && h.getStartTime().compareTo(startTime) <= 0
					&& h.getEndTime().compareTo(endTime) >= 0) {
				contains = true;
				break;
			}
		}

		return contains;
	}

	private static DayOfWeek getDayOfWeek(int i) {
		DayOfWeek d;
		if (i == 0) {
			d = DayOfWeek.Sunday;
		} else if (i == 1) {
			d = DayOfWeek.Monday;
		} else if (i == 2) {
			d = DayOfWeek.Tuesday;
		} else if (i == 3) {
			d = DayOfWeek.Wednesday;
		} else if (i == 4) {
			d = DayOfWeek.Thursday;
		} else if (i == 5) {
			d = DayOfWeek.Friday;
		} else {
			d = DayOfWeek.Saturday;
		}
		return d;
	}
	
	public static List<TimeSlot> generateTimeSlotsFromStarts(Date date, List<Time> times, Service... services) {
		List<TimeSlot> slots = new ArrayList<>();

		for (int i = 0; i < services.length; i++) {
			Time time = times.get(i);
			Service s = services[i];
			slots.add(new TimeSlot(date, time, date, incrementTimeByMinutes(time, s.getDuration()), s.getCarShop()));
		}
		return slots;
	}
	
	/**
	 * Converts java.util.Date object to java.sql.Time object.
	 * 
	 * @param date
	 * @return
	 */
	private static Time dateToTime(Date date) {
		return new Time(date.getTime());
	}

	private static List<TimeSlot> generateTimeSlots(Date date, Time time, List<String> timeSlotStrings) {
		CarShop carshop = CarShopApplication.getCarShop();
		List<TimeSlot> timeSlots = new ArrayList<>();

		for (String t : timeSlotStrings) {
			String[] parts = t.split("-");
			timeSlots.add(new TimeSlot(date, dateToTime(parseDate(parts[0], "HH:mm")), date,
					dateToTime(parseDate(parts[1], "HH:mm")), carshop));
		}

		return timeSlots;
	}

	private static Time incrementTimeByMinutes(Time original, int minutesElapsed) {
		return new Time(original.getTime() + minutesElapsed * 60000);
	}
}