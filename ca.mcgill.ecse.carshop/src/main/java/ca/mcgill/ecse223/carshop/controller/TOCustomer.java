/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse223.carshop.controller;

// line 33 "../../../../../CarshopTransferObjects.ump"
public class TOCustomer
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOCustomer Attributes
  private int noShowCount;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOCustomer()
  {
    noShowCount = 0;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setNoShowCount(int aNoShowCount)
  {
    boolean wasSet = false;
    noShowCount = aNoShowCount;
    wasSet = true;
    return wasSet;
  }

  public int getNoShowCount()
  {
    return noShowCount;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "noShowCount" + ":" + getNoShowCount()+ "]";
  }
}