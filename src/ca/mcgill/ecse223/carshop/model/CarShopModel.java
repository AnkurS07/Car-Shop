/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse223.carshop.model;
import java.util.*;
import java.sql.Date;
import java.sql.Time;

// line 3 "../../../../../CarShopModel.ump"
public class CarShopModel
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //CarShopModel Associations
  private List<UserAccount> accounts;
  private List<UserRole> userRoles;
  private List<Business> businesses;
  private List<Holiday> holidays;
  private List<Garage> garages;
  private List<DailySchedule> schedules;
  private List<AppointmentCalendar> appointmentCalendars;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public CarShopModel()
  {
    accounts = new ArrayList<UserAccount>();
    userRoles = new ArrayList<UserRole>();
    businesses = new ArrayList<Business>();
    holidays = new ArrayList<Holiday>();
    garages = new ArrayList<Garage>();
    schedules = new ArrayList<DailySchedule>();
    appointmentCalendars = new ArrayList<AppointmentCalendar>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetMany */
  public UserAccount getAccount(int index)
  {
    UserAccount aAccount = accounts.get(index);
    return aAccount;
  }

  public List<UserAccount> getAccounts()
  {
    List<UserAccount> newAccounts = Collections.unmodifiableList(accounts);
    return newAccounts;
  }

  public int numberOfAccounts()
  {
    int number = accounts.size();
    return number;
  }

  public boolean hasAccounts()
  {
    boolean has = accounts.size() > 0;
    return has;
  }

  public int indexOfAccount(UserAccount aAccount)
  {
    int index = accounts.indexOf(aAccount);
    return index;
  }
  /* Code from template association_GetMany */
  public UserRole getUserRole(int index)
  {
    UserRole aUserRole = userRoles.get(index);
    return aUserRole;
  }

  public List<UserRole> getUserRoles()
  {
    List<UserRole> newUserRoles = Collections.unmodifiableList(userRoles);
    return newUserRoles;
  }

  public int numberOfUserRoles()
  {
    int number = userRoles.size();
    return number;
  }

  public boolean hasUserRoles()
  {
    boolean has = userRoles.size() > 0;
    return has;
  }

  public int indexOfUserRole(UserRole aUserRole)
  {
    int index = userRoles.indexOf(aUserRole);
    return index;
  }
  /* Code from template association_GetMany */
  public Business getBusiness(int index)
  {
    Business aBusiness = businesses.get(index);
    return aBusiness;
  }

  public List<Business> getBusinesses()
  {
    List<Business> newBusinesses = Collections.unmodifiableList(businesses);
    return newBusinesses;
  }

  public int numberOfBusinesses()
  {
    int number = businesses.size();
    return number;
  }

  public boolean hasBusinesses()
  {
    boolean has = businesses.size() > 0;
    return has;
  }

  public int indexOfBusiness(Business aBusiness)
  {
    int index = businesses.indexOf(aBusiness);
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
  public AppointmentCalendar getAppointmentCalendar(int index)
  {
    AppointmentCalendar aAppointmentCalendar = appointmentCalendars.get(index);
    return aAppointmentCalendar;
  }

  public List<AppointmentCalendar> getAppointmentCalendars()
  {
    List<AppointmentCalendar> newAppointmentCalendars = Collections.unmodifiableList(appointmentCalendars);
    return newAppointmentCalendars;
  }

  public int numberOfAppointmentCalendars()
  {
    int number = appointmentCalendars.size();
    return number;
  }

  public boolean hasAppointmentCalendars()
  {
    boolean has = appointmentCalendars.size() > 0;
    return has;
  }

  public int indexOfAppointmentCalendar(AppointmentCalendar aAppointmentCalendar)
  {
    int index = appointmentCalendars.indexOf(aAppointmentCalendar);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfAccounts()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public UserAccount addAccount(String aUserName, String aPassword)
  {
    return new UserAccount(aUserName, aPassword, this);
  }

  public boolean addAccount(UserAccount aAccount)
  {
    boolean wasAdded = false;
    if (accounts.contains(aAccount)) { return false; }
    CarShopModel existingCarShopModel = aAccount.getCarShopModel();
    boolean isNewCarShopModel = existingCarShopModel != null && !this.equals(existingCarShopModel);
    if (isNewCarShopModel)
    {
      aAccount.setCarShopModel(this);
    }
    else
    {
      accounts.add(aAccount);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeAccount(UserAccount aAccount)
  {
    boolean wasRemoved = false;
    //Unable to remove aAccount, as it must always have a carShopModel
    if (!this.equals(aAccount.getCarShopModel()))
    {
      accounts.remove(aAccount);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addAccountAt(UserAccount aAccount, int index)
  {  
    boolean wasAdded = false;
    if(addAccount(aAccount))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAccounts()) { index = numberOfAccounts() - 1; }
      accounts.remove(aAccount);
      accounts.add(index, aAccount);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveAccountAt(UserAccount aAccount, int index)
  {
    boolean wasAdded = false;
    if(accounts.contains(aAccount))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAccounts()) { index = numberOfAccounts() - 1; }
      accounts.remove(aAccount);
      accounts.add(index, aAccount);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addAccountAt(aAccount, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfUserRoles()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */


  public boolean addUserRole(UserRole aUserRole)
  {
    boolean wasAdded = false;
    if (userRoles.contains(aUserRole)) { return false; }
    CarShopModel existingCarShopModel = aUserRole.getCarShopModel();
    boolean isNewCarShopModel = existingCarShopModel != null && !this.equals(existingCarShopModel);
    if (isNewCarShopModel)
    {
      aUserRole.setCarShopModel(this);
    }
    else
    {
      userRoles.add(aUserRole);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeUserRole(UserRole aUserRole)
  {
    boolean wasRemoved = false;
    //Unable to remove aUserRole, as it must always have a carShopModel
    if (!this.equals(aUserRole.getCarShopModel()))
    {
      userRoles.remove(aUserRole);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addUserRoleAt(UserRole aUserRole, int index)
  {  
    boolean wasAdded = false;
    if(addUserRole(aUserRole))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfUserRoles()) { index = numberOfUserRoles() - 1; }
      userRoles.remove(aUserRole);
      userRoles.add(index, aUserRole);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveUserRoleAt(UserRole aUserRole, int index)
  {
    boolean wasAdded = false;
    if(userRoles.contains(aUserRole))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfUserRoles()) { index = numberOfUserRoles() - 1; }
      userRoles.remove(aUserRole);
      userRoles.add(index, aUserRole);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addUserRoleAt(aUserRole, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfBusinesses()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Business addBusiness(String aAddress, String aPhoneNumber, String aEmailAddress, Owner aOwner, AppointmentCalendar aAppointmentCalendar)
  {
    return new Business(aAddress, aPhoneNumber, aEmailAddress, aOwner, aAppointmentCalendar, this);
  }

  public boolean addBusiness(Business aBusiness)
  {
    boolean wasAdded = false;
    if (businesses.contains(aBusiness)) { return false; }
    CarShopModel existingCarShopModel = aBusiness.getCarShopModel();
    boolean isNewCarShopModel = existingCarShopModel != null && !this.equals(existingCarShopModel);
    if (isNewCarShopModel)
    {
      aBusiness.setCarShopModel(this);
    }
    else
    {
      businesses.add(aBusiness);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeBusiness(Business aBusiness)
  {
    boolean wasRemoved = false;
    //Unable to remove aBusiness, as it must always have a carShopModel
    if (!this.equals(aBusiness.getCarShopModel()))
    {
      businesses.remove(aBusiness);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addBusinessAt(Business aBusiness, int index)
  {  
    boolean wasAdded = false;
    if(addBusiness(aBusiness))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBusinesses()) { index = numberOfBusinesses() - 1; }
      businesses.remove(aBusiness);
      businesses.add(index, aBusiness);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveBusinessAt(Business aBusiness, int index)
  {
    boolean wasAdded = false;
    if(businesses.contains(aBusiness))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBusinesses()) { index = numberOfBusinesses() - 1; }
      businesses.remove(aBusiness);
      businesses.add(index, aBusiness);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addBusinessAt(aBusiness, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfHolidays()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Holiday addHoliday(Date aDate)
  {
    return new Holiday(aDate, this);
  }

  public boolean addHoliday(Holiday aHoliday)
  {
    boolean wasAdded = false;
    if (holidays.contains(aHoliday)) { return false; }
    CarShopModel existingCarShopModel = aHoliday.getCarShopModel();
    boolean isNewCarShopModel = existingCarShopModel != null && !this.equals(existingCarShopModel);
    if (isNewCarShopModel)
    {
      aHoliday.setCarShopModel(this);
    }
    else
    {
      holidays.add(aHoliday);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeHoliday(Holiday aHoliday)
  {
    boolean wasRemoved = false;
    //Unable to remove aHoliday, as it must always have a carShopModel
    if (!this.equals(aHoliday.getCarShopModel()))
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
  public static int minimumNumberOfGarages()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Garage addGarage(Technician aTechnician, Business aBusiness)
  {
    return new Garage(aTechnician, this, aBusiness);
  }

  public boolean addGarage(Garage aGarage)
  {
    boolean wasAdded = false;
    if (garages.contains(aGarage)) { return false; }
    CarShopModel existingCarShopModel = aGarage.getCarShopModel();
    boolean isNewCarShopModel = existingCarShopModel != null && !this.equals(existingCarShopModel);
    if (isNewCarShopModel)
    {
      aGarage.setCarShopModel(this);
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
    //Unable to remove aGarage, as it must always have a carShopModel
    if (!this.equals(aGarage.getCarShopModel()))
    {
      garages.remove(aGarage);
      wasRemoved = true;
    }
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
  public static int minimumNumberOfSchedules()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public DailySchedule addSchedule(DailySchedule.Day aDay, Time aOpeningTime, Time aClosingTime)
  {
    return new DailySchedule(aDay, aOpeningTime, aClosingTime, this);
  }

  public boolean addSchedule(DailySchedule aSchedule)
  {
    boolean wasAdded = false;
    if (schedules.contains(aSchedule)) { return false; }
    CarShopModel existingCarShopModel = aSchedule.getCarShopModel();
    boolean isNewCarShopModel = existingCarShopModel != null && !this.equals(existingCarShopModel);
    if (isNewCarShopModel)
    {
      aSchedule.setCarShopModel(this);
    }
    else
    {
      schedules.add(aSchedule);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeSchedule(DailySchedule aSchedule)
  {
    boolean wasRemoved = false;
    //Unable to remove aSchedule, as it must always have a carShopModel
    if (!this.equals(aSchedule.getCarShopModel()))
    {
      schedules.remove(aSchedule);
      wasRemoved = true;
    }
    return wasRemoved;
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
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfAppointmentCalendars()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public AppointmentCalendar addAppointmentCalendar(Business aBusiness)
  {
    return new AppointmentCalendar(this, aBusiness);
  }

  public boolean addAppointmentCalendar(AppointmentCalendar aAppointmentCalendar)
  {
    boolean wasAdded = false;
    if (appointmentCalendars.contains(aAppointmentCalendar)) { return false; }
    CarShopModel existingCarShopModel = aAppointmentCalendar.getCarShopModel();
    boolean isNewCarShopModel = existingCarShopModel != null && !this.equals(existingCarShopModel);
    if (isNewCarShopModel)
    {
      aAppointmentCalendar.setCarShopModel(this);
    }
    else
    {
      appointmentCalendars.add(aAppointmentCalendar);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeAppointmentCalendar(AppointmentCalendar aAppointmentCalendar)
  {
    boolean wasRemoved = false;
    //Unable to remove aAppointmentCalendar, as it must always have a carShopModel
    if (!this.equals(aAppointmentCalendar.getCarShopModel()))
    {
      appointmentCalendars.remove(aAppointmentCalendar);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addAppointmentCalendarAt(AppointmentCalendar aAppointmentCalendar, int index)
  {  
    boolean wasAdded = false;
    if(addAppointmentCalendar(aAppointmentCalendar))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAppointmentCalendars()) { index = numberOfAppointmentCalendars() - 1; }
      appointmentCalendars.remove(aAppointmentCalendar);
      appointmentCalendars.add(index, aAppointmentCalendar);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveAppointmentCalendarAt(AppointmentCalendar aAppointmentCalendar, int index)
  {
    boolean wasAdded = false;
    if(appointmentCalendars.contains(aAppointmentCalendar))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAppointmentCalendars()) { index = numberOfAppointmentCalendars() - 1; }
      appointmentCalendars.remove(aAppointmentCalendar);
      appointmentCalendars.add(index, aAppointmentCalendar);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addAppointmentCalendarAt(aAppointmentCalendar, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    while (accounts.size() > 0)
    {
      UserAccount aAccount = accounts.get(accounts.size() - 1);
      aAccount.delete();
      accounts.remove(aAccount);
    }
    
    while (userRoles.size() > 0)
    {
      UserRole aUserRole = userRoles.get(userRoles.size() - 1);
      aUserRole.delete();
      userRoles.remove(aUserRole);
    }
    
    while (businesses.size() > 0)
    {
      Business aBusiness = businesses.get(businesses.size() - 1);
      aBusiness.delete();
      businesses.remove(aBusiness);
    }
    
    while (holidays.size() > 0)
    {
      Holiday aHoliday = holidays.get(holidays.size() - 1);
      aHoliday.delete();
      holidays.remove(aHoliday);
    }
    
    while (garages.size() > 0)
    {
      Garage aGarage = garages.get(garages.size() - 1);
      aGarage.delete();
      garages.remove(aGarage);
    }
    
    while (schedules.size() > 0)
    {
      DailySchedule aSchedule = schedules.get(schedules.size() - 1);
      aSchedule.delete();
      schedules.remove(aSchedule);
    }
    
    while (appointmentCalendars.size() > 0)
    {
      AppointmentCalendar aAppointmentCalendar = appointmentCalendars.get(appointmentCalendars.size() - 1);
      aAppointmentCalendar.delete();
      appointmentCalendars.remove(aAppointmentCalendar);
    }
    
  }

  // line 13 "../../../../../CarShopModel.ump"
   public java.util.Date getCurrentDate(){
    java.util.Calendar cal = java.util.Calendar.getInstance();
    cal.set(Calendar.HOUR_OF_DAY, 0);
    cal.set(Calendar.MINUTE, 0);
    cal.set(Calendar.SECOND, 0);
    cal.set(Calendar.MILLISECOND, 0);
    java.util.Date date = cal.getTime();
    return date;
  }

}