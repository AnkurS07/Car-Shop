/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse223.carshop.controller;
import java.util.*;

// line 46 "../../../../../CarshopTransferObjects.ump"
public class TOAppointment
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOAppointment Attributes
  private String mainServiceName;

  //TOAppointment Associations
  private TOBookableService toBookableService;
  private List<TOServiceBooking> toServiceBookings;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOAppointment(String aMainServiceName, TOBookableService aToBookableService)
  {
    mainServiceName = aMainServiceName;
    boolean didAddToBookableService = setToBookableService(aToBookableService);
    if (!didAddToBookableService)
    {
      throw new RuntimeException("Unable to create tOAppointment due to toBookableService. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    toServiceBookings = new ArrayList<TOServiceBooking>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setMainServiceName(String aMainServiceName)
  {
    boolean wasSet = false;
    mainServiceName = aMainServiceName;
    wasSet = true;
    return wasSet;
  }

  public String getMainServiceName()
  {
    return mainServiceName;
  }
  /* Code from template association_GetOne */
  public TOBookableService getToBookableService()
  {
    return toBookableService;
  }
  /* Code from template association_GetMany */
  public TOServiceBooking getToServiceBooking(int index)
  {
    TOServiceBooking aToServiceBooking = toServiceBookings.get(index);
    return aToServiceBooking;
  }

  public List<TOServiceBooking> getToServiceBookings()
  {
    List<TOServiceBooking> newToServiceBookings = Collections.unmodifiableList(toServiceBookings);
    return newToServiceBookings;
  }

  public int numberOfToServiceBookings()
  {
    int number = toServiceBookings.size();
    return number;
  }

  public boolean hasToServiceBookings()
  {
    boolean has = toServiceBookings.size() > 0;
    return has;
  }

  public int indexOfToServiceBooking(TOServiceBooking aToServiceBooking)
  {
    int index = toServiceBookings.indexOf(aToServiceBooking);
    return index;
  }
  /* Code from template association_SetOneToMany */
  public boolean setToBookableService(TOBookableService aToBookableService)
  {
    boolean wasSet = false;
    if (aToBookableService == null)
    {
      return wasSet;
    }

    TOBookableService existingToBookableService = toBookableService;
    toBookableService = aToBookableService;
    if (existingToBookableService != null && !existingToBookableService.equals(aToBookableService))
    {
      existingToBookableService.removeTOAppointment(this);
    }
    toBookableService.addTOAppointment(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfToServiceBookings()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public TOServiceBooking addToServiceBooking(TOService aToService, TOTimeSlot aToTimeSlot)
  {
    return new TOServiceBooking(aToService, aToTimeSlot, this);
  }

  public boolean addToServiceBooking(TOServiceBooking aToServiceBooking)
  {
    boolean wasAdded = false;
    if (toServiceBookings.contains(aToServiceBooking)) { return false; }
    TOAppointment existingTOAppointment = aToServiceBooking.getTOAppointment();
    boolean isNewTOAppointment = existingTOAppointment != null && !this.equals(existingTOAppointment);
    if (isNewTOAppointment)
    {
      aToServiceBooking.setTOAppointment(this);
    }
    else
    {
      toServiceBookings.add(aToServiceBooking);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeToServiceBooking(TOServiceBooking aToServiceBooking)
  {
    boolean wasRemoved = false;
    //Unable to remove aToServiceBooking, as it must always have a tOAppointment
    if (!this.equals(aToServiceBooking.getTOAppointment()))
    {
      toServiceBookings.remove(aToServiceBooking);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addToServiceBookingAt(TOServiceBooking aToServiceBooking, int index)
  {  
    boolean wasAdded = false;
    if(addToServiceBooking(aToServiceBooking))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfToServiceBookings()) { index = numberOfToServiceBookings() - 1; }
      toServiceBookings.remove(aToServiceBooking);
      toServiceBookings.add(index, aToServiceBooking);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveToServiceBookingAt(TOServiceBooking aToServiceBooking, int index)
  {
    boolean wasAdded = false;
    if(toServiceBookings.contains(aToServiceBooking))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfToServiceBookings()) { index = numberOfToServiceBookings() - 1; }
      toServiceBookings.remove(aToServiceBooking);
      toServiceBookings.add(index, aToServiceBooking);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addToServiceBookingAt(aToServiceBooking, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    TOBookableService placeholderToBookableService = toBookableService;
    this.toBookableService = null;
    if(placeholderToBookableService != null)
    {
      placeholderToBookableService.removeTOAppointment(this);
    }
    while (toServiceBookings.size() > 0)
    {
      TOServiceBooking aToServiceBooking = toServiceBookings.get(toServiceBookings.size() - 1);
      aToServiceBooking.delete();
      toServiceBookings.remove(aToServiceBooking);
    }
    
  }


  public String toString()
  {
    return super.toString() + "["+
            "mainServiceName" + ":" + getMainServiceName()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "toBookableService = "+(getToBookableService()!=null?Integer.toHexString(System.identityHashCode(getToBookableService())):"null");
  }
}