/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse223.carshop.controller;
import java.util.*;

// line 27 "../../../../../CarshopTransferObjects.ump"
public class TOService extends TOBookableService
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOService Attributes
  private int duration;

  //TOService Associations
  private List<TOServiceBooking> tOServiceBookings;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOService(String aName, int aDuration)
  {
    super(aName);
    duration = aDuration;
    tOServiceBookings = new ArrayList<TOServiceBooking>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setDuration(int aDuration)
  {
    boolean wasSet = false;
    duration = aDuration;
    wasSet = true;
    return wasSet;
  }

  public int getDuration()
  {
    return duration;
  }
  /* Code from template association_GetMany */
  public TOServiceBooking getTOServiceBooking(int index)
  {
    TOServiceBooking aTOServiceBooking = tOServiceBookings.get(index);
    return aTOServiceBooking;
  }

  public List<TOServiceBooking> getTOServiceBookings()
  {
    List<TOServiceBooking> newTOServiceBookings = Collections.unmodifiableList(tOServiceBookings);
    return newTOServiceBookings;
  }

  public int numberOfTOServiceBookings()
  {
    int number = tOServiceBookings.size();
    return number;
  }

  public boolean hasTOServiceBookings()
  {
    boolean has = tOServiceBookings.size() > 0;
    return has;
  }

  public int indexOfTOServiceBooking(TOServiceBooking aTOServiceBooking)
  {
    int index = tOServiceBookings.indexOf(aTOServiceBooking);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfTOServiceBookings()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public TOServiceBooking addTOServiceBooking(TOTimeSlot aToTimeSlot, TOAppointment aTOAppointment)
  {
    return new TOServiceBooking(this, aToTimeSlot, aTOAppointment);
  }

  public boolean addTOServiceBooking(TOServiceBooking aTOServiceBooking)
  {
    boolean wasAdded = false;
    if (tOServiceBookings.contains(aTOServiceBooking)) { return false; }
    TOService existingToService = aTOServiceBooking.getToService();
    boolean isNewToService = existingToService != null && !this.equals(existingToService);
    if (isNewToService)
    {
      aTOServiceBooking.setToService(this);
    }
    else
    {
      tOServiceBookings.add(aTOServiceBooking);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeTOServiceBooking(TOServiceBooking aTOServiceBooking)
  {
    boolean wasRemoved = false;
    //Unable to remove aTOServiceBooking, as it must always have a toService
    if (!this.equals(aTOServiceBooking.getToService()))
    {
      tOServiceBookings.remove(aTOServiceBooking);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addTOServiceBookingAt(TOServiceBooking aTOServiceBooking, int index)
  {  
    boolean wasAdded = false;
    if(addTOServiceBooking(aTOServiceBooking))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTOServiceBookings()) { index = numberOfTOServiceBookings() - 1; }
      tOServiceBookings.remove(aTOServiceBooking);
      tOServiceBookings.add(index, aTOServiceBooking);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveTOServiceBookingAt(TOServiceBooking aTOServiceBooking, int index)
  {
    boolean wasAdded = false;
    if(tOServiceBookings.contains(aTOServiceBooking))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTOServiceBookings()) { index = numberOfTOServiceBookings() - 1; }
      tOServiceBookings.remove(aTOServiceBooking);
      tOServiceBookings.add(index, aTOServiceBooking);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addTOServiceBookingAt(aTOServiceBooking, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    for(int i=tOServiceBookings.size(); i > 0; i--)
    {
      TOServiceBooking aTOServiceBooking = tOServiceBookings.get(i - 1);
      aTOServiceBooking.delete();
    }
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "duration" + ":" + getDuration()+ "]";
  }
}