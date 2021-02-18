/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse223.carshop.model;
import java.sql.Date;

// line 53 "../../../../../CarShopModel.ump"
public class Holiday
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Holiday Attributes
  private Date date;

  //Holiday Associations
  private CarShopSystem carShopSystem;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Holiday(Date aDate, CarShopSystem aCarShopSystem)
  {
    date = aDate;
    boolean didAddCarShopSystem = setCarShopSystem(aCarShopSystem);
    if (!didAddCarShopSystem)
    {
      throw new RuntimeException("Unable to create holiday due to carShopSystem. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setDate(Date aDate)
  {
    boolean wasSet = false;
    date = aDate;
    wasSet = true;
    return wasSet;
  }

  public Date getDate()
  {
    return date;
  }
  /* Code from template association_GetOne */
  public CarShopSystem getCarShopSystem()
  {
    return carShopSystem;
  }
  /* Code from template association_SetOneToMany */
  public boolean setCarShopSystem(CarShopSystem aCarShopSystem)
  {
    boolean wasSet = false;
    if (aCarShopSystem == null)
    {
      return wasSet;
    }

    CarShopSystem existingCarShopSystem = carShopSystem;
    carShopSystem = aCarShopSystem;
    if (existingCarShopSystem != null && !existingCarShopSystem.equals(aCarShopSystem))
    {
      existingCarShopSystem.removeHoliday(this);
    }
    carShopSystem.addHoliday(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    CarShopSystem placeholderCarShopSystem = carShopSystem;
    this.carShopSystem = null;
    if(placeholderCarShopSystem != null)
    {
      placeholderCarShopSystem.removeHoliday(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "date" + "=" + (getDate() != null ? !getDate().equals(this)  ? getDate().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "carShopSystem = "+(getCarShopSystem()!=null?Integer.toHexString(System.identityHashCode(getCarShopSystem())):"null");
  }
}