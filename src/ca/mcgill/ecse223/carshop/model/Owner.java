/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse223.carshop.model;
import java.util.*;

// line 34 "../../../../../CarShopModel.ump"
public class Owner extends UserRole
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Owner Associations
  private List<Business> business;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Owner(CarShopModel aCarShopModel)
  {
    super(aCarShopModel);
    business = new ArrayList<Business>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetMany */
  public Business getBusiness(int index)
  {
    Business aBusiness = business.get(index);
    return aBusiness;
  }

  public List<Business> getBusiness()
  {
    List<Business> newBusiness = Collections.unmodifiableList(business);
    return newBusiness;
  }

  public int numberOfBusiness()
  {
    int number = business.size();
    return number;
  }

  public boolean hasBusiness()
  {
    boolean has = business.size() > 0;
    return has;
  }

  public int indexOfBusiness(Business aBusiness)
  {
    int index = business.indexOf(aBusiness);
    return index;
  }
  /* Code from template association_IsNumberOfValidMethod */
  public boolean isNumberOfBusinessValid()
  {
    boolean isValid = numberOfBusiness() >= minimumNumberOfBusiness();
    return isValid;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfBusiness()
  {
    return 1;
  }
  /* Code from template association_AddMandatoryManyToOne */
  public Business addBusiness(String aAddress, String aPhoneNumber, String aEmailAddress, AppointmentCalendar aAppointmentCalendar, CarShopModel aCarShopModel)
  {
    Business aNewBusiness = new Business(aAddress, aPhoneNumber, aEmailAddress, this, aAppointmentCalendar, aCarShopModel);
    return aNewBusiness;
  }

  public boolean addBusiness(Business aBusiness)
  {
    boolean wasAdded = false;
    if (business.contains(aBusiness)) { return false; }
    Owner existingOwner = aBusiness.getOwner();
    boolean isNewOwner = existingOwner != null && !this.equals(existingOwner);

    if (isNewOwner && existingOwner.numberOfBusiness() <= minimumNumberOfBusiness())
    {
      return wasAdded;
    }
    if (isNewOwner)
    {
      aBusiness.setOwner(this);
    }
    else
    {
      business.add(aBusiness);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeBusiness(Business aBusiness)
  {
    boolean wasRemoved = false;
    //Unable to remove aBusiness, as it must always have a owner
    if (this.equals(aBusiness.getOwner()))
    {
      return wasRemoved;
    }

    //owner already at minimum (1)
    if (numberOfBusiness() <= minimumNumberOfBusiness())
    {
      return wasRemoved;
    }

    business.remove(aBusiness);
    wasRemoved = true;
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addBusinessAt(Business aBusiness, int index)
  {  
    boolean wasAdded = false;
    if(addBusiness(aBusiness))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBusiness()) { index = numberOfBusiness() - 1; }
      business.remove(aBusiness);
      business.add(index, aBusiness);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveBusinessAt(Business aBusiness, int index)
  {
    boolean wasAdded = false;
    if(business.contains(aBusiness))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBusiness()) { index = numberOfBusiness() - 1; }
      business.remove(aBusiness);
      business.add(index, aBusiness);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addBusinessAt(aBusiness, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    for(int i=business.size(); i > 0; i--)
    {
      Business aBusiness = business.get(i - 1);
      aBusiness.delete();
    }
    super.delete();
  }

}