/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse223.carshop.model;
import java.sql.Date;
import java.sql.Time;

// line 100 "../../../../../CarShopModel.ump"
public class Appointment
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Appointment Attributes
  private Date date;
  private Time time;

  //Appointment Associations
  private Service service;
  private ServiceCombo combos;
  private Client client;
  private AppointmentCalendar appointmentCalendar;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Appointment(Date aDate, Time aTime, Client aClient, AppointmentCalendar aAppointmentCalendar)
  {
    date = aDate;
    time = aTime;
    boolean didAddClient = setClient(aClient);
    if (!didAddClient)
    {
      throw new RuntimeException("Unable to create appointment due to client. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddAppointmentCalendar = setAppointmentCalendar(aAppointmentCalendar);
    if (!didAddAppointmentCalendar)
    {
      throw new RuntimeException("Unable to create appointment due to appointmentCalendar. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setDate(Date aDate)
  {
    boolean wasSet = false;
    date = aDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setTime(Time aTime)
  {
    boolean wasSet = false;
    time = aTime;
    wasSet = true;
    return wasSet;
  }

  public Date getDate()
  {
    return date;
  }

  public Time getTime()
  {
    return time;
  }
  /* Code from template association_GetOne */
  public Service getService()
  {
    return service;
  }

  public boolean hasService()
  {
    boolean has = service != null;
    return has;
  }
  /* Code from template association_GetOne */
  public ServiceCombo getCombos()
  {
    return combos;
  }

  public boolean hasCombos()
  {
    boolean has = combos != null;
    return has;
  }
  /* Code from template association_GetOne */
  public Client getClient()
  {
    return client;
  }
  /* Code from template association_GetOne */
  public AppointmentCalendar getAppointmentCalendar()
  {
    return appointmentCalendar;
  }
  /* Code from template association_SetOptionalOneToMany */
  public boolean setService(Service aService)
  {
    boolean wasSet = false;
    Service existingService = service;
    service = aService;
    if (existingService != null && !existingService.equals(aService))
    {
      existingService.removeAppointment(this);
    }
    if (aService != null)
    {
      aService.addAppointment(this);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOptionalOneToMany */
  public boolean setCombos(ServiceCombo aCombos)
  {
    boolean wasSet = false;
    ServiceCombo existingCombos = combos;
    combos = aCombos;
    if (existingCombos != null && !existingCombos.equals(aCombos))
    {
      existingCombos.removeAppointment(this);
    }
    if (aCombos != null)
    {
      aCombos.addAppointment(this);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setClient(Client aClient)
  {
    boolean wasSet = false;
    if (aClient == null)
    {
      return wasSet;
    }

    Client existingClient = client;
    client = aClient;
    if (existingClient != null && !existingClient.equals(aClient))
    {
      existingClient.removeAppointment(this);
    }
    client.addAppointment(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setAppointmentCalendar(AppointmentCalendar aAppointmentCalendar)
  {
    boolean wasSet = false;
    if (aAppointmentCalendar == null)
    {
      return wasSet;
    }

    AppointmentCalendar existingAppointmentCalendar = appointmentCalendar;
    appointmentCalendar = aAppointmentCalendar;
    if (existingAppointmentCalendar != null && !existingAppointmentCalendar.equals(aAppointmentCalendar))
    {
      existingAppointmentCalendar.removeAppointment(this);
    }
    appointmentCalendar.addAppointment(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    if (service != null)
    {
      Service placeholderService = service;
      this.service = null;
      placeholderService.removeAppointment(this);
    }
    if (combos != null)
    {
      ServiceCombo placeholderCombos = combos;
      this.combos = null;
      placeholderCombos.removeAppointment(this);
    }
    Client placeholderClient = client;
    this.client = null;
    if(placeholderClient != null)
    {
      placeholderClient.removeAppointment(this);
    }
    AppointmentCalendar placeholderAppointmentCalendar = appointmentCalendar;
    this.appointmentCalendar = null;
    if(placeholderAppointmentCalendar != null)
    {
      placeholderAppointmentCalendar.removeAppointment(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "time" + "=" + (getTime() != null ? !getTime().equals(this)  ? getTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "service = "+(getService()!=null?Integer.toHexString(System.identityHashCode(getService())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "combos = "+(getCombos()!=null?Integer.toHexString(System.identityHashCode(getCombos())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "client = "+(getClient()!=null?Integer.toHexString(System.identityHashCode(getClient())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "appointmentCalendar = "+(getAppointmentCalendar()!=null?Integer.toHexString(System.identityHashCode(getAppointmentCalendar())):"null");
  }
}