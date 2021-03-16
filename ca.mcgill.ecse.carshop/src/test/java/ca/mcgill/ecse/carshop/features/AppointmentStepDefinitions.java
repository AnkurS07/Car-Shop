package ca.mcgill.ecse.carshop.features;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ca.mcgill.ecse223.carshop.controller.AppointmentController.parseDate;
import static ca.mcgill.ecse223.carshop.controller.AppointmentController.dateToTime;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.sql.Date;
import java.util.List;
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
import ca.mcgill.ecse.carshop.model.TimeSlot;
import ca.mcgill.ecse.carshop.model.User;
import ca.mcgill.ecse223.carshop.controller.AppointmentController;
import ca.mcgill.ecse223.carshop.controller.CarShopController;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AppointmentStepDefinitions {

	private String error;
	private int errorCntr;

  @Given("the following service combos exist in the system:")
  public void the_following_service_combos_exist_in_the_system(
      io.cucumber.datatable.DataTable dataTable) {
    CarShop carshop = CarShopApplication.getCarShop();
    List<List<String>> rows = dataTable.asLists();

    for (int i = 1; i < rows.size(); i++) {
      List<String> columns = rows.get(i);

      String name = columns.get(0);
      String mainService = columns.get(1);
      List<String> services = Arrays.asList(columns.get(2).split("\\,"));
      List<String> mandatoryStrings = Arrays.asList(columns.get(3).split("\\,"));
      List<Boolean> mandatory = new ArrayList<>();
      for (String m : mandatoryStrings)
        mandatory.add(Boolean.parseBoolean(m));

      // TODO: Implement finding and adding ServiceCombos
      ServiceCombo c = AppointmentController.findServiceCombo(name);
      if (c == null) {
        c = new ServiceCombo(name, carshop);
        ComboItem item = new ComboItem(true,
            AppointmentController.findService(mainService), c);
        c.setMainService(item);
        for (int j = 0; j < services.size(); j++) {
          c.addService(mandatory.get(j), AppointmentController.findService(services.get(j)));
        }
      }
    }
  }

  public static void addServiceComboToCarShop(String name, String mainService,
      List<String> services, List<Boolean> mandatory) {
    CarShop carshop = CarShopApplication.getCarShop();
    ServiceCombo c = new ServiceCombo(name, carshop);
    carshop.addBookableService(c);
    for (int i = 0; i < services.size(); i++) {
      Service service = null;
      for (BookableService b : carshop.getBookableServices()) {
        if (b instanceof Service && b.getName().equals(services.get(i))) {
          service = (Service) b;
        }
      }
      c.addService(mandatory.get(i), service);
    }
  }

  public static boolean isServiceComboInCarShop(String name, String mainService,
      List<String> services, List<Boolean> mandatory) {
    CarShop carshop = CarShopApplication.getCarShop();
    for (BookableService c : carshop.getBookableServices()) {
      if (c instanceof ServiceCombo && c != null && c.getName().equals(name)
          && mainService.equals(((ServiceCombo) c).getMainService())) {
        boolean allMatch = true;
        for (int i = 0; i < mandatory.size(); i++) {
          ComboItem s = ((ServiceCombo) c).getServices().get(i);
          String n = services.get(i);
          boolean m = mandatory.get(i);
          if (s.getMandatory() != m || !s.getService().getName().equals(n)) {
            allMatch = false;
          }
        }
        if (allMatch) {
          return true;
        }
      }
    }
    return false;
  }

  @Given("the business has the following opening hours")
  public void the_business_has_the_following_opening_hours(
      io.cucumber.datatable.DataTable dataTable) {
    CarShop carshop = CarShopApplication.getCarShop();
    Business business = carshop.getBusiness();
    List<List<String>> rows = dataTable.asLists();

    for (int i = 1; i < rows.size(); i++) {
      List<String> columns = rows.get(i);

      DayOfWeek day = DayOfWeek.valueOf(columns.get(0));
      Time startTime = dateToTime(parseDate(columns.get(1), "HH:mm"));
      Time endTime = dateToTime(parseDate(columns.get(2), "HH:mm"));

      business.addBusinessHour(new BusinessHour(day, startTime, endTime, carshop));
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
	      AppointmentController.makeAppointment(customerName, date, first,
	          Arrays.asList(columns.get(4).split("\\,")),
	          mainService, optServices.toArray(new String[optServices.size()]));
	    }
	    CarShopApplication.setLoggedInUser(currentUser);
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
      List<Service> optionalServices = AppointmentController
          .findServices(Arrays.asList(optServiceStrings.split("\\,")));
      List<TimeSlot> timeSlots = AppointmentController.generateTimeSlotsFromStarts(date, times,
          mainService, optionalServices.toArray(new Service[optionalServices.size()]));

      AppointmentController.makeAppointment(AppointmentController.findCustomer(customer), date,
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

    // TODO: use find appointment to detect removed appointments
    Appointment app = AppointmentController.findAppointment(
        AppointmentController.findCustomer(customer),
        mainServiceName, parseDate(date, "yyyy-MM-dd"), dateToTime(parseDate(startTime, "HH:mm")));

    assertNull(app);
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

      // TODO: check the validity of the appointment based on parameters
    }
  }

  @When("{string} attempts to cancel {string}'s {string} appointment on {string} at {string}")
  public void attempts_to_cancel_s_appointment_on_at(String otherUser, String customerName,
      String mainServiceName,
      String date, String time) throws Exception {
    CarShopApplication.setLoggedInUser(otherUser);

    // TODO: Perform cancel test
    attempts_to_cancel_their_appointment_on_at(customerName, mainServiceName, date, time);
  }

  @When("{string} schedules an appointment on {string} for {string} at {string}")
  public void schedules_an_appointment_on_for_at(String customer, String mainService, String date,
      String time) {
    // Write code here that turns the phrase above into concrete actions
    schedules_an_appointment_on_for_with_at(customer, mainService, date, "", time);
  }

  @Then("{string} shall have a {string} appointment on {string} from {string} to {string}")
  public void shall_have_a_appointment_on_from_to(String string, String string2, String string3,
      String string4, String string5) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

}
