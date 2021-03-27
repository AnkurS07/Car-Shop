/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse.carshop.model;
import java.text.SimpleDateFormat;
import java.io.Serializable;
import java.util.*;

// line 2 "../../../../../CarShopStates.ump"
// line 84 "../../../../../carshopPersistence.ump"
// line 102 "../../../../../carshop.ump"
public class Appointment implements Serializable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Appointment State Machines
  public enum AppStatus { Booked, Final, InProgress }
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
        // line 8 "../../../../../CarShopStates.ump"
        addNoShow(c);
        setAppStatus(AppStatus.Final);
        wasEventProcessed = true;
        break;
      case InProgress:
        // line 30 "../../../../../CarShopStates.ump"
        rejectNoShow(c);
        setAppStatus(AppStatus.InProgress);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean cancel(String currentDate,String sysDate)
  {
    boolean wasEventProcessed = false;
    
    AppStatus aAppStatus = appStatus;
    switch (aAppStatus)
    {
      case Booked:
        if (canCancel(currentDate,sysDate))
        {
          setAppStatus(AppStatus.Final);
          wasEventProcessed = true;
          break;
        }
        if (!(canCancel(currentDate,sysDate)))
        {
        // line 11 "../../../../../CarShopStates.ump"
          rejectCancel();
          setAppStatus(AppStatus.Booked);
          wasEventProcessed = true;
          break;
        }
        break;
      case InProgress:
        // line 28 "../../../../../CarShopStates.ump"
        reject();
        setAppStatus(AppStatus.InProgress);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean update(List<Service> newOptServices,List<TimeSlot> timeSlots,String currentDate,String sysDate,boolean isNewService)
  {
    boolean wasEventProcessed = false;
    
    AppStatus aAppStatus = appStatus;
    switch (aAppStatus)
    {
      case Booked:
        if (canUpdate(currentDate,sysDate))
        {
        // line 13 "../../../../../CarShopStates.ump"
          updateApp(newOptServices, timeSlots, isNewService);
          setAppStatus(AppStatus.Booked);
          wasEventProcessed = true;
          break;
        }
        if (!(canUpdate(currentDate,sysDate)))
        {
        // line 14 "../../../../../CarShopStates.ump"
          rejectUpdate();
          setAppStatus(AppStatus.Booked);
          wasEventProcessed = true;
          break;
        }
        break;
      case InProgress:
        // line 25 "../../../../../CarShopStates.ump"
        updateApp(newOptServices, timeSlots, isNewService);
        setAppStatus(AppStatus.InProgress);
        wasEventProcessed = true;
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
        // line 23 "../../../../../CarShopStates.ump"
        reject();
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
      case Booked:
        // line 18 "../../../../../CarShopStates.ump"
        reject();
        setAppStatus(AppStatus.Booked);
        wasEventProcessed = true;
        break;
      case InProgress:
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

  // line 37 "../../../../../CarShopStates.ump"
   private void addNoShow(Customer c){
    c.setNoShowCount(c.getNoShowCount() + 1);
  }

  // line 41 "../../../../../CarShopStates.ump"
   private void rejectUpdate(){
    throw new RuntimeException("Cannot update an appointment on the appointment date");
  }

  // line 45 "../../../../../CarShopStates.ump"
   private void rejectCancel(){
    throw new RuntimeException("Cannot cancel an appointment on the appointment date");
  }

  // line 49 "../../../../../CarShopStates.ump"
   private void rejectNoShow(Customer c){
    throw new RuntimeException("Cannot register a no-show since the appointment has already started");
  }

  // line 53 "../../../../../CarShopStates.ump"
   private void reject(){
    throw new RuntimeException("Action unavailable in the current state");
  }

  // line 57 "../../../../../CarShopStates.ump"
   private boolean canUpdate(String currentDate, String sysDate){
    return !currentDate.equals(sysDate);
  }

  // line 61 "../../../../../CarShopStates.ump"
   private boolean canCancel(String currentDate, String sysDate){
    return !currentDate.equals(sysDate);
  }

  // line 65 "../../../../../CarShopStates.ump"
   private void updateApp(List<Service> newOptServices, List<TimeSlot> timeSlots, boolean isNewService){
    List<String> servicescopy = new ArrayList<>();
	   for(int i=0;i<this.getServiceBookings().size();i++){
		   servicescopy.add(this.getServiceBooking(i).getService().getName());
	   }
    if(!isNewService) {
			// updating existing services of the app
			// make sure when check invalid before not failing because overlapping with existing services
			for(int i=0; i< servicescopy.size();i++) {
				// might be dangerous to do this but it should work
				// deletes the time slot and the associated service booking and re-create it after
				this.getServiceBooking(0).getTimeSlot().delete();
				this.getServiceBooking(0).delete();
				
				
			}
			for(int i=0;i<newOptServices.size();i++) {
				new ServiceBooking(newOptServices.get(i),timeSlots.get(i), this);
			}
		} else {
			// adding new services to the app
			for(int i = 0; i< newOptServices.size();i++) {
				new ServiceBooking(newOptServices.get(i), timeSlots.get(i), this);
			}
		}
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 87 "../../../../../carshopPersistence.ump"
  private static final long serialVersionUID = 23066424091059L ;

  
}