/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse223.carshop.controller;

// line 54 "../../../../../CarshopTransferObjects.ump"
public class TOTechnician
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum TechnicianType { Tire, Engine, Transmission, Electronics, Fluids }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOTechnician Attributes
  private TechnicianType totype;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOTechnician(TechnicianType aTotype)
  {
    totype = aTotype;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setTotype(TechnicianType aTotype)
  {
    boolean wasSet = false;
    totype = aTotype;
    wasSet = true;
    return wasSet;
  }

  public TechnicianType getTotype()
  {
    return totype;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "totype" + "=" + (getTotype() != null ? !getTotype().equals(this)  ? getTotype().toString().replaceAll("  ","    ") : "this" : "null");
  }
}