/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse223.carshop.model;
import java.util.*;
import java.sql.Time;

// line 50 "../../../../../CarShopModel.ump"
public class Business
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Business Attributes
  private String address;
  private String phoneNumber;
  private String emailAddress;

  //Business Associations
  private List<Owner> owner;
  private List<Holiday> holidays;
  private List<DailySchedule> schedules;
  private List<Garage> garages;
  private List<ServiceTemplate> serviceTemplate;
  private List<ServiceComboTemplate> comboTemplate;
  private AppointmentCalendar appointmentCalendar;
  private CarShopModel carShopModel;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Business(String aAddress, String aPhoneNumber, String aEmailAddress, AppointmentCalendar aAppointmentCalendar, CarShopModel aCarShopModel)
  {
    address = aAddress;
    phoneNumber = aPhoneNumber;
    emailAddress = aEmailAddress;
    owner = new ArrayList<Owner>();
    holidays = new ArrayList<Holiday>();
    schedules = new ArrayList<DailySchedule>();
    garages = new ArrayList<Garage>();
    serviceTemplate = new ArrayList<ServiceTemplate>();
    comboTemplate = new ArrayList<ServiceComboTemplate>();
    if (aAppointmentCalendar == null || aAppointmentCalendar.getBusiness() != null)
    {
      throw new RuntimeException("Unable to create Business due to aAppointmentCalendar. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    appointmentCalendar = aAppointmentCalendar;
    boolean didAddCarShopModel = setCarShopModel(aCarShopModel);
    if (!didAddCarShopModel)
    {
      throw new RuntimeException("Unable to create business due to carShopModel. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  public Business(String aAddress, String aPhoneNumber, String aEmailAddress, CarShopModel aCarShopModelForAppointmentCalendar, CarShopModel aCarShopModel)
  {
    address = aAddress;
    phoneNumber = aPhoneNumber;
    emailAddress = aEmailAddress;
    owner = new ArrayList<Owner>();
    holidays = new ArrayList<Holiday>();
    schedules = new ArrayList<DailySchedule>();
    garages = new ArrayList<Garage>();
    serviceTemplate = new ArrayList<ServiceTemplate>();
    comboTemplate = new ArrayList<ServiceComboTemplate>();
    appointmentCalendar = new AppointmentCalendar(aCarShopModelForAppointmentCalendar, this);
    boolean didAddCarShopModel = setCarShopModel(aCarShopModel);
    if (!didAddCarShopModel)
    {
      throw new RuntimeException("Unable to create business due to carShopModel. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setAddress(String aAddress)
  {
    boolean wasSet = false;
    address = aAddress;
    wasSet = true;
    return wasSet;
  }

  public boolean setPhoneNumber(String aPhoneNumber)
  {
    boolean wasSet = false;
    phoneNumber = aPhoneNumber;
    wasSet = true;
    return wasSet;
  }

  public boolean setEmailAddress(String aEmailAddress)
  {
    boolean wasSet = false;
    emailAddress = aEmailAddress;
    wasSet = true;
    return wasSet;
  }

  public String getAddress()
  {
    return address;
  }

  public String getPhoneNumber()
  {
    return phoneNumber;
  }

  public String getEmailAddress()
  {
    return emailAddress;
  }
  /* Code from template association_GetMany */
  public Owner getOwner(int index)
  {
    Owner aOwner = owner.get(index);
    return aOwner;
  }

  public List<Owner> getOwner()
  {
    List<Owner> newOwner = Collections.unmodifiableList(owner);
    return newOwner;
  }

  public int numberOfOwner()
  {
    int number = owner.size();
    return number;
  }

  public boolean hasOwner()
  {
    boolean has = owner.size() > 0;
    return has;
  }

  public int indexOfOwner(Owner aOwner)
  {
    int index = owner.indexOf(aOwner);
    return index;
  }
  /* Code from template association_GetMany */
  public Holiday getHoliday(int index)
  {
    Holiday aHoliday = holidays.get(index);
    return aHoliday;
  }

  public List<Holiday> getHolidays()
  {
    List<Holiday> newHolidays = Collections.unmodifiableList(holidays);
    return newHolidays;
  }

  public int numberOfHolidays()
  {
    int number = holidays.size();
    return number;
  }

  public boolean hasHolidays()
  {
    boolean has = holidays.size() > 0;
    return has;
  }

  public int indexOfHoliday(Holiday aHoliday)
  {
    int index = holidays.indexOf(aHoliday);
    return index;
  }
  /* Code from template association_GetMany */
  public DailySchedule getSchedule(int index)
  {
    DailySchedule aSchedule = schedules.get(index);
    return aSchedule;
  }

  public List<DailySchedule> getSchedules()
  {
    List<DailySchedule> newSchedules = Collections.unmodifiableList(schedules);
    return newSchedules;
  }

  public int numberOfSchedules()
  {
    int number = schedules.size();
    return number;
  }

  public boolean hasSchedules()
  {
    boolean has = schedules.size() > 0;
    return has;
  }

  public int indexOfSchedule(DailySchedule aSchedule)
  {
    int index = schedules.indexOf(aSchedule);
    return index;
  }
  /* Code from template association_GetMany */
  public Garage getGarage(int index)
  {
    Garage aGarage = garages.get(index);
    return aGarage;
  }

  public List<Garage> getGarages()
  {
    List<Garage> newGarages = Collections.unmodifiableList(garages);
    return newGarages;
  }

  public int numberOfGarages()
  {
    int number = garages.size();
    return number;
  }

  public boolean hasGarages()
  {
    boolean has = garages.size() > 0;
    return has;
  }

  public int indexOfGarage(Garage aGarage)
  {
    int index = garages.indexOf(aGarage);
    return index;
  }
  /* Code from template association_GetMany */
  public ServiceTemplate getServiceTemplate(int index)
  {
    ServiceTemplate aServiceTemplate = serviceTemplate.get(index);
    return aServiceTemplate;
  }

  public List<ServiceTemplate> getServiceTemplate()
  {
    List<ServiceTemplate> newServiceTemplate = Collections.unmodifiableList(serviceTemplate);
    return newServiceTemplate;
  }

  public int numberOfServiceTemplate()
  {
    int number = serviceTemplate.size();
    return number;
  }

  public boolean hasServiceTemplate()
  {
    boolean has = serviceTemplate.size() > 0;
    return has;
  }

  public int indexOfServiceTemplate(ServiceTemplate aServiceTemplate)
  {
    int index = serviceTemplate.indexOf(aServiceTemplate);
    return index;
  }
  /* Code from template association_GetMany */
  public ServiceComboTemplate getComboTemplate(int index)
  {
    ServiceComboTemplate aComboTemplate = comboTemplate.get(index);
    return aComboTemplate;
  }

  public List<ServiceComboTemplate> getComboTemplate()
  {
    List<ServiceComboTemplate> newComboTemplate = Collections.unmodifiableList(comboTemplate);
    return newComboTemplate;
  }

  public int numberOfComboTemplate()
  {
    int number = comboTemplate.size();
    return number;
  }

  public boolean hasComboTemplate()
  {
    boolean has = comboTemplate.size() > 0;
    return has;
  }

  public int indexOfComboTemplate(ServiceComboTemplate aComboTemplate)
  {
    int index = comboTemplate.indexOf(aComboTemplate);
    return index;
  }
  /* Code from template association_GetOne */
  public AppointmentCalendar getAppointmentCalendar()
  {
    return appointmentCalendar;
  }
  /* Code from template association_GetOne */
  public CarShopModel getCarShopModel()
  {
    return carShopModel;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfOwner()
  {
    return 0;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addOwner(Owner aOwner)
  {
    boolean wasAdded = false;
    if (owner.contains(aOwner)) { return false; }
    owner.add(aOwner);
    if (aOwner.indexOfBusiness(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aOwner.addBusiness(this);
      if (!wasAdded)
      {
        owner.remove(aOwner);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removeOwner(Owner aOwner)
  {
    boolean wasRemoved = false;
    if (!owner.contains(aOwner))
    {
      return wasRemoved;
    }

    int oldIndex = owner.indexOf(aOwner);
    owner.remove(oldIndex);
    if (aOwner.indexOfBusiness(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aOwner.removeBusiness(this);
      if (!wasRemoved)
      {
        owner.add(oldIndex,aOwner);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addOwnerAt(Owner aOwner, int index)
  {  
    boolean wasAdded = false;
    if(addOwner(aOwner))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfOwner()) { index = numberOfOwner() - 1; }
      owner.remove(aOwner);
      owner.add(index, aOwner);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveOwnerAt(Owner aOwner, int index)
  {
    boolean wasAdded = false;
    if(owner.contains(aOwner))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfOwner()) { index = numberOfOwner() - 1; }
      owner.remove(aOwner);
      owner.add(index, aOwner);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addOwnerAt(aOwner, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfHolidays()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addHoliday(Holiday aHoliday)
  {
    boolean wasAdded = false;
    if (holidays.contains(aHoliday)) { return false; }
    holidays.add(aHoliday);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeHoliday(Holiday aHoliday)
  {
    boolean wasRemoved = false;
    if (holidays.contains(aHoliday))
    {
      holidays.remove(aHoliday);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addHolidayAt(Holiday aHoliday, int index)
  {  
    boolean wasAdded = false;
    if(addHoliday(aHoliday))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfHolidays()) { index = numberOfHolidays() - 1; }
      holidays.remove(aHoliday);
      holidays.add(index, aHoliday);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveHolidayAt(Holiday aHoliday, int index)
  {
    boolean wasAdded = false;
    if(holidays.contains(aHoliday))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfHolidays()) { index = numberOfHolidays() - 1; }
      holidays.remove(aHoliday);
      holidays.add(index, aHoliday);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addHolidayAt(aHoliday, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfSchedules()
  {
    return 0;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfSchedules()
  {
    return 7;
  }
  /* Code from template association_AddUnidirectionalOptionalN */
  public boolean addSchedule(DailySchedule aSchedule)
  {
    boolean wasAdded = false;
    if (schedules.contains(aSchedule)) { return false; }
    if (numberOfSchedules() < maximumNumberOfSchedules())
    {
      schedules.add(aSchedule);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean removeSchedule(DailySchedule aSchedule)
  {
    boolean wasRemoved = false;
    if (schedules.contains(aSchedule))
    {
      schedules.remove(aSchedule);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_SetUnidirectionalOptionalN */
  public boolean setSchedules(DailySchedule... newSchedules)
  {
    boolean wasSet = false;
    ArrayList<DailySchedule> verifiedSchedules = new ArrayList<DailySchedule>();
    for (DailySchedule aSchedule : newSchedules)
    {
      if (verifiedSchedules.contains(aSchedule))
      {
        continue;
      }
      verifiedSchedules.add(aSchedule);
    }

    if (verifiedSchedules.size() != newSchedules.length || verifiedSchedules.size() > maximumNumberOfSchedules())
    {
      return wasSet;
    }

    schedules.clear();
    schedules.addAll(verifiedSchedules);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addScheduleAt(DailySchedule aSchedule, int index)
  {  
    boolean wasAdded = false;
    if(addSchedule(aSchedule))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfSchedules()) { index = numberOfSchedules() - 1; }
      schedules.remove(aSchedule);
      schedules.add(index, aSchedule);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveScheduleAt(DailySchedule aSchedule, int index)
  {
    boolean wasAdded = false;
    if(schedules.contains(aSchedule))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfSchedules()) { index = numberOfSchedules() - 1; }
      schedules.remove(aSchedule);
      schedules.add(index, aSchedule);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addScheduleAt(aSchedule, index);
    }
    return wasAdded;
  }
  /* Code from template association_IsNumberOfValidMethod */
  public boolean isNumberOfGaragesValid()
  {
    boolean isValid = numberOfGarages() >= minimumNumberOfGarages();
    return isValid;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfGarages()
  {
    return 1;
  }
  /* Code from template association_AddMandatoryManyToOne */
  public Garage addGarage(Technician aTechnician, CarShopModel aCarShopModel)
  {
    Garage aNewGarage = new Garage(aTechnician, aCarShopModel, this);
    return aNewGarage;
  }

  public boolean addGarage(Garage aGarage)
  {
    boolean wasAdded = false;
    if (garages.contains(aGarage)) { return false; }
    Business existingBusiness = aGarage.getBusiness();
    boolean isNewBusiness = existingBusiness != null && !this.equals(existingBusiness);

    if (isNewBusiness && existingBusiness.numberOfGarages() <= minimumNumberOfGarages())
    {
      return wasAdded;
    }
    if (isNewBusiness)
    {
      aGarage.setBusiness(this);
    }
    else
    {
      garages.add(aGarage);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeGarage(Garage aGarage)
  {
    boolean wasRemoved = false;
    //Unable to remove aGarage, as it must always have a business
    if (this.equals(aGarage.getBusiness()))
    {
      return wasRemoved;
    }

    //business already at minimum (1)
    if (numberOfGarages() <= minimumNumberOfGarages())
    {
      return wasRemoved;
    }

    garages.remove(aGarage);
    wasRemoved = true;
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addGarageAt(Garage aGarage, int index)
  {  
    boolean wasAdded = false;
    if(addGarage(aGarage))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfGarages()) { index = numberOfGarages() - 1; }
      garages.remove(aGarage);
      garages.add(index, aGarage);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveGarageAt(Garage aGarage, int index)
  {
    boolean wasAdded = false;
    if(garages.contains(aGarage))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfGarages()) { index = numberOfGarages() - 1; }
      garages.remove(aGarage);
      garages.add(index, aGarage);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addGarageAt(aGarage, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfServiceTemplate()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public ServiceTemplate addServiceTemplate(String aName, Time aDuration, Garage aGarage)
  {
    return new ServiceTemplate(aName, aDuration, this, aGarage);
  }

  public boolean addServiceTemplate(ServiceTemplate aServiceTemplate)
  {
    boolean wasAdded = false;
    if (serviceTemplate.contains(aServiceTemplate)) { return false; }
    Business existingBusiness = aServiceTemplate.getBusiness();
    boolean isNewBusiness = existingBusiness != null && !this.equals(existingBusiness);
    if (isNewBusiness)
    {
      aServiceTemplate.setBusiness(this);
    }
    else
    {
      serviceTemplate.add(aServiceTemplate);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeServiceTemplate(ServiceTemplate aServiceTemplate)
  {
    boolean wasRemoved = false;
    //Unable to remove aServiceTemplate, as it must always have a business
    if (!this.equals(aServiceTemplate.getBusiness()))
    {
      serviceTemplate.remove(aServiceTemplate);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addServiceTemplateAt(ServiceTemplate aServiceTemplate, int index)
  {  
    boolean wasAdded = false;
    if(addServiceTemplate(aServiceTemplate))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfServiceTemplate()) { index = numberOfServiceTemplate() - 1; }
      serviceTemplate.remove(aServiceTemplate);
      serviceTemplate.add(index, aServiceTemplate);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveServiceTemplateAt(ServiceTemplate aServiceTemplate, int index)
  {
    boolean wasAdded = false;
    if(serviceTemplate.contains(aServiceTemplate))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfServiceTemplate()) { index = numberOfServiceTemplate() - 1; }
      serviceTemplate.remove(aServiceTemplate);
      serviceTemplate.add(index, aServiceTemplate);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addServiceTemplateAt(aServiceTemplate, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfComboTemplate()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public ServiceComboTemplate addComboTemplate(String aName, boolean aIsRequired)
  {
    return new ServiceComboTemplate(aName, aIsRequired, this);
  }

  public boolean addComboTemplate(ServiceComboTemplate aComboTemplate)
  {
    boolean wasAdded = false;
    if (comboTemplate.contains(aComboTemplate)) { return false; }
    Business existingBusiness = aComboTemplate.getBusiness();
    boolean isNewBusiness = existingBusiness != null && !this.equals(existingBusiness);
    if (isNewBusiness)
    {
      aComboTemplate.setBusiness(this);
    }
    else
    {
      comboTemplate.add(aComboTemplate);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeComboTemplate(ServiceComboTemplate aComboTemplate)
  {
    boolean wasRemoved = false;
    //Unable to remove aComboTemplate, as it must always have a business
    if (!this.equals(aComboTemplate.getBusiness()))
    {
      comboTemplate.remove(aComboTemplate);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addComboTemplateAt(ServiceComboTemplate aComboTemplate, int index)
  {  
    boolean wasAdded = false;
    if(addComboTemplate(aComboTemplate))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfComboTemplate()) { index = numberOfComboTemplate() - 1; }
      comboTemplate.remove(aComboTemplate);
      comboTemplate.add(index, aComboTemplate);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveComboTemplateAt(ServiceComboTemplate aComboTemplate, int index)
  {
    boolean wasAdded = false;
    if(comboTemplate.contains(aComboTemplate))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfComboTemplate()) { index = numberOfComboTemplate() - 1; }
      comboTemplate.remove(aComboTemplate);
      comboTemplate.add(index, aComboTemplate);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addComboTemplateAt(aComboTemplate, index);
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
      existingCarShopModel.removeBusiness(this);
    }
    carShopModel.addBusiness(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    ArrayList<Owner> copyOfOwner = new ArrayList<Owner>(owner);
    owner.clear();
    for(Owner aOwner : copyOfOwner)
    {
      if (aOwner.numberOfBusiness() <= Owner.minimumNumberOfBusiness())
      {
        aOwner.delete();
      }
      else
      {
        aOwner.removeBusiness(this);
      }
    }
    holidays.clear();
    schedules.clear();
    for(int i=garages.size(); i > 0; i--)
    {
      Garage aGarage = garages.get(i - 1);
      aGarage.delete();
    }
    while (serviceTemplate.size() > 0)
    {
      ServiceTemplate aServiceTemplate = serviceTemplate.get(serviceTemplate.size() - 1);
      aServiceTemplate.delete();
      serviceTemplate.remove(aServiceTemplate);
    }
    
    while (comboTemplate.size() > 0)
    {
      ServiceComboTemplate aComboTemplate = comboTemplate.get(comboTemplate.size() - 1);
      aComboTemplate.delete();
      comboTemplate.remove(aComboTemplate);
    }
    
    AppointmentCalendar existingAppointmentCalendar = appointmentCalendar;
    appointmentCalendar = null;
    if (existingAppointmentCalendar != null)
    {
      existingAppointmentCalendar.delete();
    }
    CarShopModel placeholderCarShopModel = carShopModel;
    this.carShopModel = null;
    if(placeholderCarShopModel != null)
    {
      placeholderCarShopModel.removeBusiness(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "address" + ":" + getAddress()+ "," +
            "phoneNumber" + ":" + getPhoneNumber()+ "," +
            "emailAddress" + ":" + getEmailAddress()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "appointmentCalendar = "+(getAppointmentCalendar()!=null?Integer.toHexString(System.identityHashCode(getAppointmentCalendar())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "carShopModel = "+(getCarShopModel()!=null?Integer.toHexString(System.identityHashCode(getCarShopModel())):"null");
  }
}