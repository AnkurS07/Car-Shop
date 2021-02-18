/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse223.carshop.model;
import java.sql.Time;

// line 79 "../../../../../CarShopModel.ump"
public class Service
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Service Attributes
  private Time startTime;
  private Time endTime;

  //Service Associations
  private ServiceTemplate serviceTemplate;
  private Appointment appointments;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Service(Time aStartTime, Time aEndTime, ServiceTemplate aServiceTemplate)
  {
    startTime = aStartTime;
    endTime = aEndTime;
    boolean didAddServiceTemplate = setServiceTemplate(aServiceTemplate);
    if (!didAddServiceTemplate)
    {
      throw new RuntimeException("Unable to create service due to serviceTemplate. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setStartTime(Time aStartTime)
  {
    boolean wasSet = false;
    startTime = aStartTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setEndTime(Time aEndTime)
  {
    boolean wasSet = false;
    endTime = aEndTime;
    wasSet = true;
    return wasSet;
  }

  public Time getStartTime()
  {
    return startTime;
  }

  public Time getEndTime()
  {
    return endTime;
  }
  /* Code from template association_GetOne */
  public ServiceTemplate getServiceTemplate()
  {
    return serviceTemplate;
  }
  /* Code from template association_GetOne */
  public Appointment getAppointments()
  {
    return appointments;
  }

  public boolean hasAppointments()
  {
    boolean has = appointments != null;
    return has;
  }
  /* Code from template association_SetOneToMany */
  public boolean setServiceTemplate(ServiceTemplate aServiceTemplate)
  {
    boolean wasSet = false;
    if (aServiceTemplate == null)
    {
      return wasSet;
    }

    ServiceTemplate existingServiceTemplate = serviceTemplate;
    serviceTemplate = aServiceTemplate;
    if (existingServiceTemplate != null && !existingServiceTemplate.equals(aServiceTemplate))
    {
      existingServiceTemplate.removeService(this);
    }
    serviceTemplate.addService(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOptionalOneToOptionalOne */
  public boolean setAppointments(Appointment aNewAppointments)
  {
    boolean wasSet = false;
    if (aNewAppointments == null)
    {
      Appointment existingAppointments = appointments;
      appointments = null;
      
      if (existingAppointments != null && existingAppointments.getService() != null)
      {
        existingAppointments.setService(null);
      }
      wasSet = true;
      return wasSet;
    }

    Appointment currentAppointments = getAppointments();
    if (currentAppointments != null && !currentAppointments.equals(aNewAppointments))
    {
      currentAppointments.setService(null);
    }

    appointments = aNewAppointments;
    Service existingService = aNewAppointments.getService();

    if (!equals(existingService))
    {
      aNewAppointments.setService(this);
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    ServiceTemplate placeholderServiceTemplate = serviceTemplate;
    this.serviceTemplate = null;
    if(placeholderServiceTemplate != null)
    {
      placeholderServiceTemplate.removeService(this);
    }
    if (appointments != null)
    {
      appointments.setService(null);
    }
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "startTime" + "=" + (getStartTime() != null ? !getStartTime().equals(this)  ? getStartTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "endTime" + "=" + (getEndTime() != null ? !getEndTime().equals(this)  ? getEndTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "serviceTemplate = "+(getServiceTemplate()!=null?Integer.toHexString(System.identityHashCode(getServiceTemplate())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "appointments = "+(getAppointments()!=null?Integer.toHexString(System.identityHashCode(getAppointments())):"null");
  }
}