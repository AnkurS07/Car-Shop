/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse223.carshop.controller;

// line 41 "../../../../../CarshopTransferObjects.ump"
public class TOServiceBooking
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOServiceBooking Associations
  private TOService toService;
  private TOTimeSlot toTimeSlot;
  private TOAppointment tOAppointment;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOServiceBooking(TOService aToService, TOTimeSlot aToTimeSlot, TOAppointment aTOAppointment)
  {
    boolean didAddToService = setToService(aToService);
    if (!didAddToService)
    {
      throw new RuntimeException("Unable to create tOServiceBooking due to toService. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (!setToTimeSlot(aToTimeSlot))
    {
      throw new RuntimeException("Unable to create TOServiceBooking due to aToTimeSlot. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddTOAppointment = setTOAppointment(aTOAppointment);
    if (!didAddTOAppointment)
    {
      throw new RuntimeException("Unable to create toServiceBooking due to tOAppointment. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public TOService getToService()
  {
    return toService;
  }
  /* Code from template association_GetOne */
  public TOTimeSlot getToTimeSlot()
  {
    return toTimeSlot;
  }
  /* Code from template association_GetOne */
  public TOAppointment getTOAppointment()
  {
    return tOAppointment;
  }
  /* Code from template association_SetOneToMany */
  public boolean setToService(TOService aToService)
  {
    boolean wasSet = false;
    if (aToService == null)
    {
      return wasSet;
    }

    TOService existingToService = toService;
    toService = aToService;
    if (existingToService != null && !existingToService.equals(aToService))
    {
      existingToService.removeTOServiceBooking(this);
    }
    toService.addTOServiceBooking(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setToTimeSlot(TOTimeSlot aNewToTimeSlot)
  {
    boolean wasSet = false;
    if (aNewToTimeSlot != null)
    {
      toTimeSlot = aNewToTimeSlot;
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setTOAppointment(TOAppointment aTOAppointment)
  {
    boolean wasSet = false;
    if (aTOAppointment == null)
    {
      return wasSet;
    }

    TOAppointment existingTOAppointment = tOAppointment;
    tOAppointment = aTOAppointment;
    if (existingTOAppointment != null && !existingTOAppointment.equals(aTOAppointment))
    {
      existingTOAppointment.removeToServiceBooking(this);
    }
    tOAppointment.addToServiceBooking(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    TOService placeholderToService = toService;
    this.toService = null;
    if(placeholderToService != null)
    {
      placeholderToService.removeTOServiceBooking(this);
    }
    toTimeSlot = null;
    TOAppointment placeholderTOAppointment = tOAppointment;
    this.tOAppointment = null;
    if(placeholderTOAppointment != null)
    {
      placeholderTOAppointment.removeToServiceBooking(this);
    }
  }

}