/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse223.carshop.model;
import java.util.*;

// line 24 "../../../../../CarShopModel.ump"
public class UserAccount
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<String, UserAccount> useraccountsByUserName = new HashMap<String, UserAccount>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //UserAccount Attributes
  private String userName;
  private String password;

  //UserAccount Associations
  private UserRole userRole;
  private CarShopModel carShopModel;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public UserAccount(String aUserName, String aPassword, CarShopModel aCarShopModel)
  {
    password = aPassword;
    if (!setUserName(aUserName))
    {
      throw new RuntimeException("Cannot create due to duplicate userName. See http://manual.umple.org?RE003ViolationofUniqueness.html");
    }
    boolean didAddCarShopModel = setCarShopModel(aCarShopModel);
    if (!didAddCarShopModel)
    {
      throw new RuntimeException("Unable to create account due to carShopModel. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setUserName(String aUserName)
  {
    boolean wasSet = false;
    String anOldUserName = getUserName();
    if (anOldUserName != null && anOldUserName.equals(aUserName)) {
      return true;
    }
    if (hasWithUserName(aUserName)) {
      return wasSet;
    }
    userName = aUserName;
    wasSet = true;
    if (anOldUserName != null) {
      useraccountsByUserName.remove(anOldUserName);
    }
    useraccountsByUserName.put(aUserName, this);
    return wasSet;
  }

  public boolean setPassword(String aPassword)
  {
    boolean wasSet = false;
    password = aPassword;
    wasSet = true;
    return wasSet;
  }

  public String getUserName()
  {
    return userName;
  }
  /* Code from template attribute_GetUnique */
  public static UserAccount getWithUserName(String aUserName)
  {
    return useraccountsByUserName.get(aUserName);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithUserName(String aUserName)
  {
    return getWithUserName(aUserName) != null;
  }

  public String getPassword()
  {
    return password;
  }
  /* Code from template association_GetOne */
  public UserRole getUserRole()
  {
    return userRole;
  }

  public boolean hasUserRole()
  {
    boolean has = userRole != null;
    return has;
  }
  /* Code from template association_GetOne */
  public CarShopModel getCarShopModel()
  {
    return carShopModel;
  }
  /* Code from template association_SetOptionalOneToOptionalOne */
  public boolean setUserRole(UserRole aNewUserRole)
  {
    boolean wasSet = false;
    if (aNewUserRole == null)
    {
      UserRole existingUserRole = userRole;
      userRole = null;
      
      if (existingUserRole != null && existingUserRole.getUserAccount() != null)
      {
        existingUserRole.setUserAccount(null);
      }
      wasSet = true;
      return wasSet;
    }

    UserRole currentUserRole = getUserRole();
    if (currentUserRole != null && !currentUserRole.equals(aNewUserRole))
    {
      currentUserRole.setUserAccount(null);
    }

    userRole = aNewUserRole;
    UserAccount existingUserAccount = aNewUserRole.getUserAccount();

    if (!equals(existingUserAccount))
    {
      aNewUserRole.setUserAccount(this);
    }
    wasSet = true;
    return wasSet;
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
      existingCarShopModel.removeAccount(this);
    }
    carShopModel.addAccount(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    useraccountsByUserName.remove(getUserName());
    if (userRole != null)
    {
      userRole.setUserAccount(null);
    }
    CarShopModel placeholderCarShopModel = carShopModel;
    this.carShopModel = null;
    if(placeholderCarShopModel != null)
    {
      placeholderCarShopModel.removeAccount(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "userName" + ":" + getUserName()+ "," +
            "password" + ":" + getPassword()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "userRole = "+(getUserRole()!=null?Integer.toHexString(System.identityHashCode(getUserRole())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "carShopModel = "+(getCarShopModel()!=null?Integer.toHexString(System.identityHashCode(getCarShopModel())):"null");
  }
}