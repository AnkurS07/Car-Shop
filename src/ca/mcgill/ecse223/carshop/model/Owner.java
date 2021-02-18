/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse223.carshop.model;
import java.util.*;

// line 23 "../../../../../CarShopModel.ump"
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

  public Owner(CarShopSystem aCarShopSystem, Business... allBusiness)
  {
    super(aCarShopSystem);
    business = new ArrayList<Business>();
    boolean didAddBusiness = setBusiness(allBusiness);
    if (!didAddBusiness)
    {
      throw new RuntimeException("Unable to create Owner, must have at least 1 business. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
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
  /* Code from template association_AddManyToManyMethod */
  public boolean addBusiness(Business aBusiness)
  {
    boolean wasAdded = false;
    if (business.contains(aBusiness)) { return false; }
    business.add(aBusiness);
    if (aBusiness.indexOfOwner(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aBusiness.addOwner(this);
      if (!wasAdded)
      {
        business.remove(aBusiness);
      }
    }
    return wasAdded;
  }
  /* Code from template association_AddMStarToMany */
  public boolean removeBusiness(Business aBusiness)
  {
    boolean wasRemoved = false;
    if (!business.contains(aBusiness))
    {
      return wasRemoved;
    }

    if (numberOfBusiness() <= minimumNumberOfBusiness())
    {
      return wasRemoved;
    }

    int oldIndex = business.indexOf(aBusiness);
    business.remove(oldIndex);
    if (aBusiness.indexOfOwner(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aBusiness.removeOwner(this);
      if (!wasRemoved)
      {
        business.add(oldIndex,aBusiness);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_SetMStarToMany */
  public boolean setBusiness(Business... newBusiness)
  {
    boolean wasSet = false;
    ArrayList<Business> verifiedBusiness = new ArrayList<Business>();
    for (Business aBusiness : newBusiness)
    {
      if (verifiedBusiness.contains(aBusiness))
      {
        continue;
      }
      verifiedBusiness.add(aBusiness);
    }

    if (verifiedBusiness.size() != newBusiness.length || verifiedBusiness.size() < minimumNumberOfBusiness())
    {
      return wasSet;
    }

    ArrayList<Business> oldBusiness = new ArrayList<Business>(business);
    business.clear();
    for (Business aNewBusiness : verifiedBusiness)
    {
      business.add(aNewBusiness);
      if (oldBusiness.contains(aNewBusiness))
      {
        oldBusiness.remove(aNewBusiness);
      }
      else
      {
        aNewBusiness.addOwner(this);
      }
    }

    for (Business anOldBusiness : oldBusiness)
    {
      anOldBusiness.removeOwner(this);
    }
    wasSet = true;
    return wasSet;
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
    ArrayList<Business> copyOfBusiness = new ArrayList<Business>(business);
    business.clear();
    for(Business aBusiness : copyOfBusiness)
    {
      aBusiness.removeOwner(this);
    }
    super.delete();
  }

}