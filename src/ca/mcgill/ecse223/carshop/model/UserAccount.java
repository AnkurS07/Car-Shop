/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse223.carshop.model;
import java.util.*;

// line 13 "../../../../../CarShopModel.ump"
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
  private CarShopSystem carShopSystem;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public UserAccount(String aUserName, String aPassword, CarShopSystem aCarShopSystem)
  {
    password = aPassword;
    if (!setUserName(aUserName))
    {
      throw new RuntimeException("Cannot create due to duplicate userName. See http://manual.umple.org?RE003ViolationofUniqueness.html");
    }
    boolean didAddCarShopSystem = setCarShopSystem(aCarShopSystem);
    if (!didAddCarShopSystem)
    {
      throw new RuntimeException("Unable to create account due to carShopSystem. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
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
  public CarShopSystem getCarShopSystem()
  {
    return carShopSystem;
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
      existingCarShopSystem.removeAccount(this);
    }
    carShopSystem.addAccount(this);
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
    CarShopSystem placeholderCarShopSystem = carShopSystem;
    this.carShopSystem = null;
    if(placeholderCarShopSystem != null)
    {
      placeholderCarShopSystem.removeAccount(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "userName" + ":" + getUserName()+ "," +
            "password" + ":" + getPassword()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "userRole = "+(getUserRole()!=null?Integer.toHexString(System.identityHashCode(getUserRole())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "carShopSystem = "+(getCarShopSystem()!=null?Integer.toHexString(System.identityHashCode(getCarShopSystem())):"null");
  }
}