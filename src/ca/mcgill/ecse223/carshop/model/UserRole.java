/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse223.carshop.model;

// line 30 "../../../../../CarShopModel.ump"
public abstract class UserRole
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //UserRole Associations
  private CarShopModel carShopModel;
  private UserAccount userAccount;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public UserRole(CarShopModel aCarShopModel)
  {
    boolean didAddCarShopModel = setCarShopModel(aCarShopModel);
    if (!didAddCarShopModel)
    {
      throw new RuntimeException("Unable to create userRole due to carShopModel. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public CarShopModel getCarShopModel()
  {
    return carShopModel;
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
  public boolean setCarShopModel(CarShopModel aCarShopModel)
  {
    boolean wasSet = false;
    if (aCarShopModel == null)
    {
      return wasSet;
    }

    CarShopModel existingCarShopModel = carShopModel;
    carShopModel = aCarShopModel;
    if (existingCarShopModel != null && !existingCarShopModel.equals(aCarShopModel))
    {
      existingCarShopModel.removeUserRole(this);
    }
    carShopModel.addUserRole(this);
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
    CarShopModel placeholderCarShopModel = carShopModel;
    this.carShopModel = null;
    if(placeholderCarShopModel != null)
    {
      placeholderCarShopModel.removeUserRole(this);
    }
    if (userAccount != null)
    {
      userAccount.setUserRole(null);
    }
  }

}