/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse223.carshop.controller;
import java.util.*;

// line 37 "../../../../../CarshopTransferObjects.ump"
public abstract class TOBookableService
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOBookableService Attributes
  private String name;

  //TOBookableService Associations
  private List<TOAppointment> tOAppointments;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOBookableService(String aName)
  {
    name = aName;
    tOAppointments = new ArrayList<TOAppointment>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }
  /* Code from template association_GetMany */
  public TOAppointment getTOAppointment(int index)
  {
    TOAppointment aTOAppointment = tOAppointments.get(index);
    return aTOAppointment;
  }

  public List<TOAppointment> getTOAppointments()
  {
    List<TOAppointment> newTOAppointments = Collections.unmodifiableList(tOAppointments);
    return newTOAppointments;
  }

  public int numberOfTOAppointments()
  {
    int number = tOAppointments.size();
    return number;
  }

  public boolean hasTOAppointments()
  {
    boolean has = tOAppointments.size() > 0;
    return has;
  }

  public int indexOfTOAppointment(TOAppointment aTOAppointment)
  {
    int index = tOAppointments.indexOf(aTOAppointment);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfTOAppointments()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public TOAppointment addTOAppointment(String aMainServiceName)
  {
    return new TOAppointment(aMainServiceName, this);
  }

  public boolean addTOAppointment(TOAppointment aTOAppointment)
  {
    boolean wasAdded = false;
    if (tOAppointments.contains(aTOAppointment)) { return false; }
    TOBookableService existingToBookableService = aTOAppointment.getToBookableService();
    boolean isNewToBookableService = existingToBookableService != null && !this.equals(existingToBookableService);
    if (isNewToBookableService)
    {
      aTOAppointment.setToBookableService(this);
    }
    else
    {
      tOAppointments.add(aTOAppointment);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeTOAppointment(TOAppointment aTOAppointment)
  {
    boolean wasRemoved = false;
    //Unable to remove aTOAppointment, as it must always have a toBookableService
    if (!this.equals(aTOAppointment.getToBookableService()))
    {
      tOAppointments.remove(aTOAppointment);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addTOAppointmentAt(TOAppointment aTOAppointment, int index)
  {  
    boolean wasAdded = false;
    if(addTOAppointment(aTOAppointment))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTOAppointments()) { index = numberOfTOAppointments() - 1; }
      tOAppointments.remove(aTOAppointment);
      tOAppointments.add(index, aTOAppointment);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveTOAppointmentAt(TOAppointment aTOAppointment, int index)
  {
    boolean wasAdded = false;
    if(tOAppointments.contains(aTOAppointment))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTOAppointments()) { index = numberOfTOAppointments() - 1; }
      tOAppointments.remove(aTOAppointment);
      tOAppointments.add(index, aTOAppointment);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addTOAppointmentAt(aTOAppointment, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    for(int i=tOAppointments.size(); i > 0; i--)
    {
      TOAppointment aTOAppointment = tOAppointments.get(i - 1);
      aTOAppointment.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "]";
  }
}