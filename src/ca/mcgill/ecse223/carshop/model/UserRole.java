/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse223.carshop.model;

// line 19 "../../../../../CarShopModel.ump"
public abstract class UserRole
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //UserRole Associations
  private CarShopSystem carShopSystem;
  private UserAccount userAccount;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public UserRole(CarShopSystem aCarShopSystem)
  {
    boolean didAddCarShopSystem = setCarShopSystem(aCarShopSystem);
    if (!didAddCarShopSystem)
    {
      throw new RuntimeException("Unable to create userRole due to carShopSystem. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public CarShopSystem getCarShopSystem()
  {
    return carShopSystem;
  }
  /* Code from template association_GetOne */
  public UserAccount getUserAccount()
  {
    return userAccount;
  }

  public boolean hasUserAccount()
  {
    boolean has = userAccount != null;
    return has;
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
      existingCarShopSystem.removeUserRole(this);
    }
    carShopSystem.addUserRole(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOptionalOneToOptionalOne */
  public boolean setUserAccount(UserAccount aNewUserAccount)
  {
    boolean wasSet = false;
    if (aNewUserAccount == null)
    {
      UserAccount existingUserAccount = userAccount;
      userAccount = null;
      
      if (existingUserAccount != null && existingUserAccount.getUserRole() != null)
      {
        existingUserAccount.setUserRole(null);
      }
      wasSet = true;
      return wasSet;
    }

    UserAccount currentUserAccount = getUserAccount();
    if (currentUserAccount != null && !currentUserAccount.equals(aNewUserAccount))
    {
      currentUserAccount.setUserRole(null);
    }

    userAccount = aNewUserAccount;
    UserRole existingUserRole = aNewUserAccount.getUserRole();

    if (!equals(existingUserRole))
    {
      aNewUserAccount.setUserRole(this);
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    CarShopSystem placeholderCarShopSystem = carShopSystem;
    this.carShopSystem = null;
    if(placeholderCarShopSystem != null)
    {
      placeholderCarShopSystem.removeUserRole(this);
    }
    if (userAccount != null)
    {
      userAccount.setUserRole(null);
    }
  }

}