package ca.mcgill.ecse223.carshop.controller;

import java.util.Date;
import java.sql.Time;
import ca.mcgill.ecse.carshop.application.CarShopApplication;
import ca.mcgill.ecse.carshop.model.Appointment;
import ca.mcgill.ecse.carshop.model.CarShop;
import ca.mcgill.ecse.carshop.model.Customer;
import ca.mcgill.ecse.carshop.model.ServiceBooking;
import ca.mcgill.ecse.carshop.model.TimeSlot;


public class AppointmentController {

	public static void cancelAppointment(String username, String appointmentName, Date date) {
		
		CarShop carshop = CarShopApplication.getCarShop();
		//make sure you have user by the name and the appointment on that date exists 
		
		Customer customer = null;
		
		for (Customer c: carshop.getCustomers()) {
			
			if (c.getUsername().equals(username)) {
				customer = c;
			}
			
		}
		
		for (Appointment a: customer.getAppointments()) {
			
			if (a.getBookableService().getName().equals(appointmentName)) {
				
			}
		}
		
		
		
	}
	
	
}
