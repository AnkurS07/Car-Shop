/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse223.carshop.model;
import java.util.*;

// line 104 "../../../../../CarShopModel.ump"
public class ServiceCombo
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //ServiceCombo Associations
  private List<Service> services;
  private ServiceComboTemplate serviceComboTemplate;
  private Appointment appointments;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public ServiceCombo(ServiceComboTemplate aServiceComboTemplate, Appointment aAppointments)
  {
    services = new ArrayList<Service>();
    boolean didAddServiceComboTemplate = setServiceComboTemplate(aServiceComboTemplate);
    if (!didAddServiceComboTemplate)
    {
      throw new RuntimeException("Unable to create combo due to serviceComboTemplate. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddAppointments = setAppointments(aAppointments);
    if (!didAddAppointments)
    {
      throw new RuntimeException("Unable to create combos due to appointments. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetMany */
  public Service getService(int index)
  {
    Service aService = services.get(index);
    return aService;
  }

  public List<Service> getServices()
  {
    List<Service> newServices = Collections.unmodifiableList(services);
    return newServices;
  }

  public int numberOfServices()
  {
    int number = services.size();
    return number;
  }

  public boolean hasServices()
  {
    boolean has = services.size() > 0;
    return has;
  }

  public int indexOfService(Service aService)
  {
    int index = services.indexOf(aService);
    return index;
  }
  /* Code from template association_GetOne */
  public ServiceComboTemplate getServiceComboTemplate()
  {
    return serviceComboTemplate;
  }
  /* Code from template association_GetOne */
  public Appointment getAppointments()
  {
    return appointments;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfServices()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addService(Service aService)
  {
    boolean wasAdded = false;
    if (services.contains(aService)) { return false; }
    services.add(aService);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeService(Service aService)
  {
    boolean wasRemoved = false;
    if (services.contains(aService))
    {
      services.remove(aService);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addServiceAt(Service aService, int index)
  {  
    boolean wasAdded = false;
    if(addService(aService))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfServices()) { index = numberOfServices() - 1; }
      services.remove(aService);
      services.add(index, aService);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveServiceAt(Service aService, int index)
  {
    boolean wasAdded = false;
    if(services.contains(aService))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfServices()) { index = numberOfServices() - 1; }
      services.remove(aService);
      services.add(index, aService);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addServiceAt(aService, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetOneToMany */
  public boolean setServiceComboTemplate(ServiceComboTemplate aServiceComboTemplate)
  {
    boolean wasSet = false;
    if (aServiceComboTemplate == null)
    {
      return wasSet;
    }

    ServiceComboTemplate existingServiceComboTemplate = serviceComboTemplate;
    serviceComboTemplate = aServiceComboTemplate;
    if (existingServiceComboTemplate != null && !existingServiceComboTemplate.equals(aServiceComboTemplate))
    {
      existingServiceComboTemplate.removeCombo(this);
    }
    serviceComboTemplate.addCombo(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToOptionalOne */
  public boolean setAppointments(Appointment aNewAppointments)
  {
    boolean wasSet = false;
    if (aNewAppointments == null)
    {
      //Unable to setAppointments to null, as combos must always be associated to a appointments
      return wasSet;
    }
    
    ServiceCombo existingCombos = aNewAppointments.getCombos();
    if (existingCombos != null && !equals(existingCombos))
    {
      //Unable to setAppointments, the current appointments already has a combos, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    Appointment anOldAppointments = appointments;
    appointments = aNewAppointments;
    appointments.setCombos(this);

    if (anOldAppointments != null)
    {
      anOldAppointments.setCombos(null);
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    services.clear();
    ServiceComboTemplate placeholderServiceComboTemplate = serviceComboTemplate;
    this.serviceComboTemplate = null;
    if(placeholderServiceComboTemplate != null)
    {
      placeholderServiceComboTemplate.removeCombo(this);
    }
    Appointment existingAppointments = appointments;
    appointments = null;
    if (existingAppointments != null)
    {
      existingAppointments.setCombos(null);
    }
  }

}