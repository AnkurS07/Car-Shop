/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse223.carshop.controller;

// line 18 "../../../../../CarshopTransferObjects.ump"
public class TOService
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOService Attributes
  private int duration;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOService(int aDuration)
  {
    duration = aDuration;
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

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "duration" + ":" + getDuration()+ "]";
  }
}