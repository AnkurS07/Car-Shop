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
  private TechnicianType type;

  //TOTechnician Associations
  private TOGarage togarage;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOTechnician(TechnicianType aType)
  {
    type = aType;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setType(TechnicianType aType)
  {
    boolean wasSet = false;
    type = aType;
    wasSet = true;
    return wasSet;
  }

  public TechnicianType getType()
  {
    return type;
  }
  /* Code from template association_GetOne */
  public TOGarage getTogarage()
  {
    return togarage;
  }

  public boolean hasTogarage()
  {
    boolean has = togarage != null;
    return has;
  }
  /* Code from template association_SetOptionalOneToOne */
  public boolean setTogarage(TOGarage aNewTogarage)
  {
    boolean wasSet = false;
    if (togarage != null && !togarage.equals(aNewTogarage) && equals(togarage.getTOTechnician()))
    {
      //Unable to setTogarage, as existing togarage would become an orphan
      return wasSet;
    }

    togarage = aNewTogarage;
    TOTechnician anOldTOTechnician = aNewTogarage != null ? aNewTogarage.getTOTechnician() : null;

    if (!this.equals(anOldTOTechnician))
    {
      if (anOldTOTechnician != null)
      {
        anOldTOTechnician.togarage = null;
      }
      if (togarage != null)
      {
        togarage.setTOTechnician(this);
      }
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    TOGarage existingTogarage = togarage;
    togarage = null;
    if (existingTogarage != null)
    {
      existingTogarage.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "type" + "=" + (getType() != null ? !getType().equals(this)  ? getType().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "togarage = "+(getTogarage()!=null?Integer.toHexString(System.identityHashCode(getTogarage())):"null");
  }
}