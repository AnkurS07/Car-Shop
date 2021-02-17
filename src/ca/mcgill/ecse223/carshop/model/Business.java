/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse223.carshop.model;
import java.util.*;
import java.sql.Date;
import java.sql.Time;

// line 49 "../../../../../CarShopModel.ump"
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
  private Owner owner;
  private List<Holiday> holidays;
  private List<DailySchedule> schedules;
  private List<Garage> garages;
  private List<Service> services;
  private List<ServiceCombo> combos;
  private AppointmentCalendar appointmentCalendar;
  private CarShopModel carShopModel;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Business(String aAddress, String aPhoneNumber, String aEmailAddress, Owner aOwner, AppointmentCalendar aAppointmentCalendar, CarShopModel aCarShopModel)
  {
    address = aAddress;
    phoneNumber = aPhoneNumber;
    emailAddress = aEmailAddress;
    boolean didAddOwner = setOwner(aOwner);
    if (!didAddOwner)
    {
      throw new RuntimeException("Unable to create business due to owner. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    holidays = new ArrayList<Holiday>();
    schedules = new ArrayList<DailySchedule>();
    garages = new ArrayList<Garage>();
    services = new ArrayList<Service>();
    combos = new ArrayList<ServiceCombo>();
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

  public Business(String aAddress, String aPhoneNumber, String aEmailAddress, Owner aOwner, CarShopModel aCarShopModelForAppointmentCalendar, CarShopModel aCarShopModel)
  {
    address = aAddress;
    phoneNumber = aPhoneNumber;
    emailAddress = aEmailAddress;
    boolean didAddOwner = setOwner(aOwner);
    if (!didAddOwner)
    {
      throw new RuntimeException("Unable to create business due to owner. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    holidays = new ArrayList<Holiday>();
    schedules = new ArrayList<DailySchedule>();
    garages = new ArrayList<Garage>();
    services = new ArrayList<Service>();
    combos = new ArrayList<ServiceCombo>();
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
  /* Code from template association_GetOne */
  public Owner getOwner()
  {
    return owner;
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
  /* Code from template association_GetMany */
  public ServiceCombo getCombo(int index)
  {
    ServiceCombo aCombo = combos.get(index);
    return aCombo;
  }

  public List<ServiceCombo> getCombos()
  {
    List<ServiceCombo> newCombos = Collections.unmodifiableList(combos);
    return newCombos;
  }

  public int numberOfCombos()
  {
    int number = combos.size();
    return number;
  }

  public boolean hasCombos()
  {
    boolean has = combos.size() > 0;
    return has;
  }

  public int indexOfCombo(ServiceCombo aCombo)
  {
    int index = combos.indexOf(aCombo);
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
  /* Code from template association_SetOneToMandatoryMany */
  public boolean setOwner(Owner aOwner)
  {
    boolean wasSet = false;
    //Must provide owner to business
    if (aOwner == null)
    {
      return wasSet;
    }

    if (owner != null && owner.numberOfBusiness() <= Owner.minimumNumberOfBusiness())
    {
      return wasSet;
    }

    Owner existingOwner = owner;
    owner = aOwner;
    if (existingOwner != null && !existingOwner.equals(aOwner))
    {
      boolean didRemove = existingOwner.removeBusiness(this);
      if (!didRemove)
      {
        owner = existingOwner;
        return wasSet;
      }
    }
    owner.addBusiness(this);
    wasSet = true;
    return wasSet;
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
  public Garage addGarage(CarShopModel aCarShopModel)
  {
    Garage aNewGarage = new Garage(aCarShopModel, this);
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
  public static int minimumNumberOfServices()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Service addService(String aName, Time aDuration, Garage aGarage)
  {
    return new Service(aName, aDuration, this, aGarage);
  }

  public boolean addService(Service aService)
  {
    boolean wasAdded = false;
    if (services.contains(aService)) { return false; }
    Business existingBusiness = aService.getBusiness();
    boolean isNewBusiness = existingBusiness != null && !this.equals(existingBusiness);
    if (isNewBusiness)
    {
      aService.setBusiness(this);
    }
    else
    {
      services.add(aService);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeService(Service aService)
  {
    boolean wasRemoved = false;
    //Unable to remove aService, as it must always have a business
    if (!this.equals(aService.getBusiness()))
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
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfCombos()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public ServiceCombo addCombo(String aName, boolean aIsRequired)
  {
    return new ServiceCombo(aName, aIsRequired, this);
  }

  public boolean addCombo(ServiceCombo aCombo)
  {
    boolean wasAdded = false;
    if (combos.contains(aCombo)) { return false; }
    Business existingBusiness = aCombo.getBusiness();
    boolean isNewBusiness = existingBusiness != null && !this.equals(existingBusiness);
    if (isNewBusiness)
    {
      aCombo.setBusiness(this);
    }
    else
    {
      combos.add(aCombo);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeCombo(ServiceCombo aCombo)
  {
    boolean wasRemoved = false;
    //Unable to remove aCombo, as it must always have a business
    if (!this.equals(aCombo.getBusiness()))
    {
      combos.remove(aCombo);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addComboAt(ServiceCombo aCombo, int index)
  {  
    boolean wasAdded = false;
    if(addCombo(aCombo))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCombos()) { index = numberOfCombos() - 1; }
      combos.remove(aCombo);
      combos.add(index, aCombo);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveComboAt(ServiceCombo aCombo, int index)
  {
    boolean wasAdded = false;
    if(combos.contains(aCombo))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCombos()) { index = numberOfCombos() - 1; }
      combos.remove(aCombo);
      combos.add(index, aCombo);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addComboAt(aCombo, index);
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
    Owner placeholderOwner = owner;
    this.owner = null;
    if(placeholderOwner != null)
    {
      placeholderOwner.removeBusiness(this);
    }
    holidays.clear();
    schedules.clear();
    for(int i=garages.size(); i > 0; i--)
    {
      Garage aGarage = garages.get(i - 1);
      aGarage.delete();
    }
    while (services.size() > 0)
    {
      Service aService = services.get(services.size() - 1);
      aService.delete();
      services.remove(aService);
    }
    
    while (combos.size() > 0)
    {
      ServiceCombo aCombo = combos.get(combos.size() - 1);
      aCombo.delete();
      combos.remove(aCombo);
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
            "  " + "owner = "+(getOwner()!=null?Integer.toHexString(System.identityHashCode(getOwner())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "appointmentCalendar = "+(getAppointmentCalendar()!=null?Integer.toHexString(System.identityHashCode(getAppointmentCalendar())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "carShopModel = "+(getCarShopModel()!=null?Integer.toHexString(System.identityHashCode(getCarShopModel())):"null");
  }
}