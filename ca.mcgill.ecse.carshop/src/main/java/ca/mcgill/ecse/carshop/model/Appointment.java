/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse.carshop.model;
import java.util.*;

// line 2 "../../../../../CarShopStates.ump"
// line 101 "../../../../../carshop.ump"
public class Appointment
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Appointment State Machines
  public enum AppStatus { Booked, InProgress, Done, Final }
  private AppStatus appStatus;

  //Appointment Associations
  private Customer customer;
  private BookableService bookableService;
  private List<ServiceBooking> serviceBookings;
  private CarShop carShop;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Appointment(Customer aCustomer, BookableService aBookableService, CarShop aCarShop)
  {
    boolean didAddCustomer = setCustomer(aCustomer);
    if (!didAddCustomer)
    {
      throw new RuntimeException("Unable to create appointment due to customer. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddBookableService = setBookableService(aBookableService);
    if (!didAddBookableService)
    {
      throw new RuntimeException("Unable to create appointment due to bookableService. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    serviceBookings = new ArrayList<ServiceBooking>();
    boolean didAddCarShop = setCarShop(aCarShop);
    if (!didAddCarShop)
    {
      throw new RuntimeException("Unable to create appointment due to carShop. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    setAppStatus(AppStatus.Booked);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public String getAppStatusFullName()
  {
    String answer = appStatus.toString();
    return answer;
  }

  public AppStatus getAppStatus()
  {
    return appStatus;
  }

  public boolean noShow(Customer c)
  {
    boolean wasEventProcessed = false;
    
    AppStatus aAppStatus = appStatus;
    switch (aAppStatus)
    {
      case Booked:
        // line 6 "../../../../../CarShopStates.ump"
        addNoShow(c);
        setAppStatus(AppStatus.Done);
        wasEventProcessed = true;
        break;
      case InProgress:
        // line 20 "../../../../../CarShopStates.ump"
        rejectNoShow(c);
        setAppStatus(AppStatus.InProgress);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean cancel()
  {
    boolean wasEventProcessed = false;
    
    AppStatus aAppStatus = appStatus;
    switch (aAppStatus)
    {
      case Booked:
        if (canCancel())
        {
          setAppStatus(AppStatus.Done);
          wasEventProcessed = true;
          break;
        }
        if (!(canCancel()))
        {
        // line 9 "../../../../../CarShopStates.ump"
          rejectCancel();
          setAppStatus(AppStatus.Booked);
          wasEventProcessed = true;
          break;
        }
        break;
      case InProgress:
        // line 17 "../../../../../CarShopStates.ump"
        rejectCancel();
        setAppStatus(AppStatus.InProgress);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean update()
  {
    boolean wasEventProcessed = false;
    
    AppStatus aAppStatus = appStatus;
    switch (aAppStatus)
    {
      case Booked:
        if (canUpdate())
        {
          setAppStatus(AppStatus.Booked);
          wasEventProcessed = true;
          break;
        }
        if (!(canUpdate()))
        {
        // line 10 "../../../../../CarShopStates.ump"
          rejectUpdate();
          setAppStatus(AppStatus.Booked);
          wasEventProcessed = true;
          break;
        }
        break;
      case InProgress:
        if (!(canUpdate()))
        {
        // line 18 "../../../../../CarShopStates.ump"
          rejectUpdate();
          setAppStatus(AppStatus.Booked);
          wasEventProcessed = true;
          break;
        }
        if (canUpdate())
        {
          setAppStatus(AppStatus.InProgress);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean start()
  {
    boolean wasEventProcessed = false;
    
    AppStatus aAppStatus = appStatus;
    switch (aAppStatus)
    {
      case Booked:
        setAppStatus(AppStatus.InProgress);
        wasEventProcessed = true;
        break;
      case InProgress:
        setAppStatus(AppStatus.InProgress);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean end()
  {
    boolean wasEventProcessed = false;
    
    AppStatus aAppStatus = appStatus;
    switch (aAppStatus)
    {
      case InProgress:
        setAppStatus(AppStatus.Done);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean destroy()
  {
    boolean wasEventProcessed = false;
    
    AppStatus aAppStatus = appStatus;
    switch (aAppStatus)
    {
      case Done:
        setAppStatus(AppStatus.Final);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  private void setAppStatus(AppStatus aAppStatus)
  {
    appStatus = aAppStatus;

    // entry actions and do activities
    switch(appStatus)
    {
      case Final:
        delete();
        break;
    }
  }
  /* Code from template association_GetOne */
  public Customer getCustomer()
  {
    return customer;
  }
  /* Code from template association_GetOne */
  public BookableService getBookableService()
  {
    return bookableService;
  }
  /* Code from template association_GetMany */
  public ServiceBooking getServiceBooking(int index)
  {
    ServiceBooking aServiceBooking = serviceBookings.get(index);
    return aServiceBooking;
  }

  public List<ServiceBooking> getServiceBookings()
  {
    List<ServiceBooking> newServiceBookings = Collections.unmodifiableList(serviceBookings);
    return newServiceBookings;
  }

  public int numberOfServiceBookings()
  {
    int number = serviceBookings.size();
    return number;
  }

  public boolean hasServiceBookings()
  {
    boolean has = serviceBookings.size() > 0;
    return has;
  }

  public int indexOfServiceBooking(ServiceBooking aServiceBooking)
  {
    int index = serviceBookings.indexOf(aServiceBooking);
    return index;
  }
  /* Code from template association_GetOne */
  public CarShop getCarShop()
  {
    return carShop;
  }
  /* Code from template association_SetOneToMany */
  public boolean setCustomer(Customer aCustomer)
  {
    boolean wasSet = false;
    if (aCustomer == null)
    {
      return wasSet;
    }

    Customer existingCustomer = customer;
    customer = aCustomer;
    if (existingCustomer != null && !existingCustomer.equals(aCustomer))
    {
      existingCustomer.removeAppointment(this);
    }
    customer.addAppointment(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setBookableService(BookableService aBookableService)
  {
    boolean wasSet = false;
    if (aBookableService == null)
    {
      return wasSet;
    }

    BookableService existingBookableService = bookableService;
    bookableService = aBookableService;
    if (existingBookableService != null && !existingBookableService.equals(aBookableService))
    {
      existingBookableService.removeAppointment(this);
    }
    bookableService.addAppointment(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfServiceBookings()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public ServiceBooking addServiceBooking(Service aService, TimeSlot aTimeSlot)
  {
    return new ServiceBooking(aService, aTimeSlot, this);
  }

  public boolean addServiceBooking(ServiceBooking aServiceBooking)
  {
    boolean wasAdded = false;
    if (serviceBookings.contains(aServiceBooking)) { return false; }
    Appointment existingAppointment = aServiceBooking.getAppointment();
    boolean isNewAppointment = existingAppointment != null && !this.equals(existingAppointment);
    if (isNewAppointment)
    {
      aServiceBooking.setAppointment(this);
    }
    else
    {
      serviceBookings.add(aServiceBooking);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeServiceBooking(ServiceBooking aServiceBooking)
  {
    boolean wasRemoved = false;
    //Unable to remove aServiceBooking, as it must always have a appointment
    if (!this.equals(aServiceBooking.getAppointment()))
    {
      serviceBookings.remove(aServiceBooking);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addServiceBookingAt(ServiceBooking aServiceBooking, int index)
  {  
    boolean wasAdded = false;
    if(addServiceBooking(aServiceBooking))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfServiceBookings()) { index = numberOfServiceBookings() - 1; }
      serviceBookings.remove(aServiceBooking);
      serviceBookings.add(index, aServiceBooking);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveServiceBookingAt(ServiceBooking aServiceBooking, int index)
  {
    boolean wasAdded = false;
    if(serviceBookings.contains(aServiceBooking))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfServiceBookings()) { index = numberOfServiceBookings() - 1; }
      serviceBookings.remove(aServiceBooking);
      serviceBookings.add(index, aServiceBooking);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addServiceBookingAt(aServiceBooking, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetOneToMany */
  public boolean setCarShop(CarShop aCarShop)
  {
    boolean wasSet = false;
    if (aCarShop == null)
    {
      return wasSet;
    }

    CarShop existingCarShop = carShop;
    carShop = aCarShop;
    if (existingCarShop != null && !existingCarShop.equals(aCarShop))
    {
      existingCarShop.removeAppointment(this);
    }
    carShop.addAppointment(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Customer placeholderCustomer = customer;
    this.customer = null;
    if(placeholderCustomer != null)
    {
      placeholderCustomer.removeAppointment(this);
    }
    BookableService placeholderBookableService = bookableService;
    this.bookableService = null;
    if(placeholderBookableService != null)
    {
      placeholderBookableService.removeAppointment(this);
    }
    while (serviceBookings.size() > 0)
    {
      ServiceBooking aServiceBooking = serviceBookings.get(serviceBookings.size() - 1);
      aServiceBooking.delete();
      serviceBookings.remove(aServiceBooking);
    }
    
    CarShop placeholderCarShop = carShop;
    this.carShop = null;
    if(placeholderCarShop != null)
    {
      placeholderCarShop.removeAppointment(this);
    }
  }

  // line 31 "../../../../../CarShopStates.ump"
   private void addNoShow(Customer c){
    c.setNoShowCount(c.getNoShowCount() + 1);
  }

  // line 36 "../../../../../CarShopStates.ump"
   private void rejectUpdate(){
    
  }

  // line 40 "../../../../../CarShopStates.ump"
   private void rejectCancel(){
    
  }

  // line 44 "../../../../../CarShopStates.ump"
   private void rejectNoShow(Customer c){
    
  }

  // line 47 "../../../../../CarShopStates.ump"
   private boolean canUpdate(){
    return true;
  }

  // line 51 "../../../../../CarShopStates.ump"
   private boolean canCancel(){
    return true;
  }

}