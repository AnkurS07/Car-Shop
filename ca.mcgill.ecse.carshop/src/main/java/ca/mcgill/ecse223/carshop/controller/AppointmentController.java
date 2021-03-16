
package ca.mcgill.ecse223.carshop.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.sql.Date;
import java.util.List;

import javax.management.RuntimeErrorException;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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
import ca.mcgill.ecse.carshop.model.Technician;
import ca.mcgill.ecse.carshop.model.Technician.TechnicianType;
import ca.mcgill.ecse.carshop.model.TimeSlot;
import ca.mcgill.ecse.carshop.model.User;

public class AppointmentController {

  public static Appointment makeAppointment(String customerName, Date date, Time time,
    List<String> timeSlotStrings, String mainServiceName, String... optionalServiceStrings) {

    Service mainService = findService(mainServiceName);
    Customer customer = findCustomer(customerName);
    List<Service> optionalServices = findServices(Arrays.asList(optionalServiceStrings));
    List<TimeSlot> timeSlots = generateTimeSlots(date, time, timeSlotStrings);

    Appointment appointment = makeAppointment(customer, date, time, timeSlots, mainService,
        optionalServices);

    return appointment;
  }

  public static Appointment makeAppointment(Customer customer, Date date, Time time,
      List<TimeSlot> timeSlots, Service mainService, List<Service> optionalServices) {
    CarShop carshop = CarShopApplication.getCarShop();
    Appointment appointment = new Appointment(customer, mainService, carshop); // TODO: check for
                                                                               // exceptions

    // TODO: verify timeslots, optinoalServices, list lengths, check null values

    appointment.addServiceBooking((Service) mainService, timeSlots.get(0));
    for (int i = 0; i < optionalServices.size(); i++) {
      appointment.addServiceBooking(optionalServices.get(i), timeSlots.get(i + 1));
    }

    return appointment;
  }

  public static boolean cancelAppointment(String customerName, String mainServiceName, Date date,
      Time time) throws Exception {
    return cancelAppointment(findCustomer(customerName), mainServiceName, date, time);
  }

  public static boolean cancelAppointment(Customer customer, String mainServiceName, Date date,
      Time time) throws Exception {
    User user = User.getWithUsername(CarShopApplication.getLoggedInUser());
    if (user instanceof Owner) {
      throw new RuntimeException("An owner cannot cancel an appointment");
    }
    
    if (user instanceof Technician) {
      throw new RuntimeException("A technician cannot cancel an appointment");
    }
    
    if (!customer.getUsername().equals(CarShopApplication.getLoggedInUser())) {
      throw new RuntimeException("A customer can only cancel their own appointments");
    }
    
    
    Appointment app = findAppointment(customer, mainServiceName, date, time);

    // TODO: Check for any exceptions

    app.delete();
    return true;
  }

  public static Appointment findAppointment(Customer customer, String mainServiceName, Date date,
      Time time) {
    for (Appointment a : customer.getAppointments()) {
      ServiceBooking b = a.getServiceBooking(0);
      if (mainServiceName.equals(b) && b.getTimeSlot().getStartDate().equals(date)
          && b.getTimeSlot().getStartTime().equals(time)) {
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
   * The formatString follows the conventions (bare minimum shown):
   * yyyy for year
   * MM for month
   * dd for days
   * HH for 24hr hours
   * hh for 12hr hours
   * mm for minutes
   * ss for seconds
   * aa for am/pm
   * 
   * 
   * @param  dateString
   * @param  formatString
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

  /**
   * Converts java.util.Date object to java.sql.Time object.
   * 
   * @param  date
   * @return
   */
  public static Time dateToTime(Date date) {
    return new Time(date.getTime());
  }

  public static List<TimeSlot> generateTimeSlots(Date date, Time time,
      String[] timeSlotStrings) {
    return generateTimeSlots(date, time, Arrays.asList(timeSlotStrings));
  }
  
  public static List<TimeSlot> generateTimeSlots(Date date, Time time,
      List<String> timeSlotStrings) {
    CarShop carshop = CarShopApplication.getCarShop();
    List<TimeSlot> timeSlots = new ArrayList<>();

    for (String t : timeSlotStrings) {
      String[] parts = t.split("-");
      timeSlots.add(new TimeSlot(date, dateToTime(parseDate(parts[0], "HH:mm")), date,
          dateToTime(parseDate(parts[1], "HH:mm")), carshop));
    }

    return timeSlots;
  }

  public static List<TimeSlot> generateTimeSlots(Date date, Time time,
      Service mainService, List<Service> optionals) {
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

  public static Time incrementTimeByMinutes(Time original, int minutesElapsed) {
    return new Time(original.getTime() + minutesElapsed * 60000);
  }

  public static Customer findCustomer(String username) {
    User user = User.getWithUsername(username);
    if (user instanceof Customer) {
      return (Customer) user;
    } else {
      return null;
    }
  }
  
  public static List<Service> findServices(String[] serviceNames) {
    return findServices(Arrays.asList(serviceNames));
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

  public static ServiceBooking getEarliestServiceBooking(Appointment app) {
    CarShop carshop = CarShopApplication.getCarShop();

    /* Earliest booking should be the first in the list */
    return app.getServiceBookings().size() > 0 ? app.getServiceBooking(0) : null;

    // ServiceBooking booking = null;
    // for (ServiceBooking b : app.getServiceBookings()) {
    // TimeSlot t = b.getTimeSlot();
    // if (booking == null ||
    // (t.getStartDate().compareTo(booking.getTimeSlot().getStartDate()) < -1
    // && t.getStartTime().compareTo(booking.getTimeSlot().getStartTime()) < -1)) {
    // booking = b;
    // }
    // }
    // return booking;
  }

  public static java.sql.Date getDate(Appointment a) {
    ServiceBooking b = getEarliestServiceBooking(a);
    return b.getTimeSlot().getStartDate();
  }

  public static java.sql.Time getTime(Appointment a) {
    ServiceBooking b = getEarliestServiceBooking(a);
    return b.getTimeSlot().getStartTime();
  }

  public static BusinessHour findBusinessHour(DayOfWeek day, Time startTime, Time endTime) {
    CarShop carshop = CarShopApplication.getCarShop();
    for (BusinessHour h : carshop.getBusiness().getBusinessHours()) {
      if (h.getDayOfWeek() == day && startTime.equals(h.getStartTime())
          && endTime.equals(h.getEndTime())) {
        return h;
      }
    }
    return null;
  }

  public static BusinessHour findGarageHour(Garage garage, DayOfWeek day, Time startTime,
      Time endTime) {
    for (BusinessHour h : garage.getBusinessHours()) {
      if (h.getDayOfWeek() == day && startTime.equals(h.getStartTime())
          && endTime.equals(h.getEndTime())) {
        return h;
      }
    }
    return null;
  }

  public static List<TimeSlot> generateTimeSlotsFromStarts(Date date, List<Time> times,
      Service... services) {
    List<TimeSlot> slots = new ArrayList<>();

    for (int i = 0; i < services.length; i++) {
      Time time = times.get(i);
      Service s = services[i];
      slots.add(new TimeSlot(date, time, date, incrementTimeByMinutes(time, s.getDuration()),
          s.getCarShop()));
    }
    return slots;
  }

  public static List<TimeSlot> generateTimeSlotsFromStarts(Date date, List<Time> times,
      Service mainService, Service[] optionalServices) {
    List<Service> services = new ArrayList<>();
    services.addAll(Arrays.asList(optionalServices));
    return generateTimeSlotsFromStarts(date, times, services.toArray(new Service[services.size()]));
  }
}