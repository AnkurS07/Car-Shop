/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse223.carshop.model;
import java.util.*;
import java.sql.Date;
import java.sql.Time;

// line 122 "../../../../../CarShopModel.ump"
public class AppointmentCalendar
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //AppointmentCalendar Associations
  private List<Appointment> appointments;
  private CarShopModel carShopModel;
  private Business business;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public AppointmentCalendar(CarShopModel aCarShopModel, Business aBusiness)
  {
    appointments = new ArrayList<Appointment>();
    boolean didAddCarShopModel = setCarShopModel(aCarShopModel);
    if (!didAddCarShopModel)
    {
      throw new RuntimeException("Unable to create appointmentCalendar due to carShopModel. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (aBusiness == null || aBusiness.getAppointmentCalendar() != null)
    {
      throw new RuntimeException("Unable to create AppointmentCalendar due to aBusiness. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    business = aBusiness;
  }

  public AppointmentCalendar(CarShopModel aCarShopModel, String aAddressForBusiness, String aPhoneNumberForBusiness, String aEmailAddressForBusiness, CarShopModel aCarShopModelForBusiness)
  {
    appointments = new ArrayList<Appointment>();
    boolean didAddCarShopModel = setCarShopModel(aCarShopModel);
    if (!didAddCarShopModel)
    {
      throw new RuntimeException("Unable to create appointmentCalendar due to carShopModel. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    business = new Business(aAddressForBusiness, aPhoneNumberForBusiness, aEmailAddressForBusiness, this, aCarShopModelForBusiness);
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetMany */
  public Appointment getAppointment(int index)
  {
    Appointment aAppointment = appointments.get(index);
    return aAppointment;
  }

  public List<Appointment> getAppointments()
  {
    List<Appointment> newAppointments = Collections.unmodifiableList(appointments);
    return newAppointments;
  }

  public int numberOfAppointments()
  {
    int number = appointments.size();
    return number;
  }

  public boolean hasAppointments()
  {
    boolean has = appointments.size() > 0;
    return has;
  }

  public int indexOfAppointment(Appointment aAppointment)
  {
    int index = appointments.indexOf(aAppointment);
    return index;
  }
  /* Code from template association_GetOne */
  public CarShopModel getCarShopModel()
  {
    return carShopModel;
  }
  /* Code from template association_GetOne */
  public Business getBusiness()
  {
    return business;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfAppointments()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Appointment addAppointment(Date aDate, Time aTime, Client aClient)
  {
    return new Appointment(aDate, aTime, aClient, this);
  }

  public boolean addAppointment(Appointment aAppointment)
  {
    boolean wasAdded = false;
    if (appointments.contains(aAppointment)) { return false; }
    AppointmentCalendar existingAppointmentCalendar = aAppointment.getAppointmentCalendar();
    boolean isNewAppointmentCalendar = existingAppointmentCalendar != null && !this.equals(existingAppointmentCalendar);
    if (isNewAppointmentCalendar)
    {
      aAppointment.setAppointmentCalendar(this);
    }
    else
    {
      appointments.add(aAppointment);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeAppointment(Appointment aAppointment)
  {
    boolean wasRemoved = false;
    //Unable to remove aAppointment, as it must always have a appointmentCalendar
    if (!this.equals(aAppointment.getAppointmentCalendar()))
    {
      appointments.remove(aAppointment);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addAppointmentAt(Appointment aAppointment, int index)
  {  
    boolean wasAdded = false;
    if(addAppointment(aAppointment))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAppointments()) { index = numberOfAppointments() - 1; }
      appointments.remove(aAppointment);
      appointments.add(index, aAppointment);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveAppointmentAt(Appointment aAppointment, int index)
  {
    boolean wasAdded = false;
    if(appointments.contains(aAppointment))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAppointments()) { index = numberOfAppointments() - 1; }
      appointments.remove(aAppointment);
      appointments.add(index, aAppointment);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addAppointmentAt(aAppointment, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetOneToMany */
  public boolean setCarShopModel(CarShopModel aCarShopModel)
  {
    boolean wasSet = false;
    if (aCarShopModel == null)
    {
      return wasSet;
    }

    CarShopModel existingCarShopModel = carShopModel;
    carShopModel = aCarShopModel;
    if (existingCarShopModel != null && !existingCarShopModel.equals(aCarShopModel))
    {
      existingCarShopModel.removeAppointmentCalendar(this);
    }
    carShopModel.addAppointmentCalendar(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    for(int i=appointments.size(); i > 0; i--)
    {
      Appointment aAppointment = appointments.get(i - 1);
      aAppointment.delete();
    }
    CarShopModel placeholderCarShopModel = carShopModel;
    this.carShopModel = null;
    if(placeholderCarShopModel != null)
    {
      placeholderCarShopModel.removeAppointmentCalendar(this);
    }
    Business existingBusiness = business;
    business = null;
    if (existingBusiness != null)
    {
      existingBusiness.delete();
    }
  }

}