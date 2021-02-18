/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse223.carshop.model;
import java.util.*;

// line 95 "../../../../../CarShopModel.ump"
public class ServiceComboTemplate
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //ServiceComboTemplate Attributes
  private String name;
  private boolean isRequired;

  //ServiceComboTemplate Associations
  private List<ServiceTemplate> serviceTemplate;
  private List<ServiceCombo> combos;
  private Business business;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public ServiceComboTemplate(String aName, boolean aIsRequired, Business aBusiness)
  {
    name = aName;
    isRequired = aIsRequired;
    serviceTemplate = new ArrayList<ServiceTemplate>();
    combos = new ArrayList<ServiceCombo>();
    boolean didAddBusiness = setBusiness(aBusiness);
    if (!didAddBusiness)
    {
      throw new RuntimeException("Unable to create comboTemplate due to business. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean setIsRequired(boolean aIsRequired)
  {
    boolean wasSet = false;
    isRequired = aIsRequired;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }

  public boolean getIsRequired()
  {
    return isRequired;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isIsRequired()
  {
    return isRequired;
  }
  /* Code from template association_GetMany */
  public ServiceTemplate getServiceTemplate(int index)
  {
    ServiceTemplate aServiceTemplate = serviceTemplate.get(index);
    return aServiceTemplate;
  }

  public List<ServiceTemplate> getServiceTemplate()
  {
    List<ServiceTemplate> newServiceTemplate = Collections.unmodifiableList(serviceTemplate);
    return newServiceTemplate;
  }

  public int numberOfServiceTemplate()
  {
    int number = serviceTemplate.size();
    return number;
  }

  public boolean hasServiceTemplate()
  {
    boolean has = serviceTemplate.size() > 0;
    return has;
  }

  public int indexOfServiceTemplate(ServiceTemplate aServiceTemplate)
  {
    int index = serviceTemplate.indexOf(aServiceTemplate);
    return index;
  }
  /* Code from template association_GetMany */
  public ServiceCombo getCombo(int index)
  {
    ServiceCombo aCombo = combos.get(index);
    return aCombo;
  }

  public List<ServiceCombo> getCombos()
  {
    List<ServiceCombo> newCombos = Collections.unmodifiableList(combos);
    return newCombos;
  }

  public int numberOfCombos()
  {
    int number = combos.size();
    return number;
  }

  public boolean hasCombos()
  {
    boolean has = combos.size() > 0;
    return has;
  }

  public int indexOfCombo(ServiceCombo aCombo)
  {
    int index = combos.indexOf(aCombo);
    return index;
  }
  /* Code from template association_GetOne */
  public Business getBusiness()
  {
    return business;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfServiceTemplate()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addServiceTemplate(ServiceTemplate aServiceTemplate)
  {
    boolean wasAdded = false;
    if (serviceTemplate.contains(aServiceTemplate)) { return false; }
    serviceTemplate.add(aServiceTemplate);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeServiceTemplate(ServiceTemplate aServiceTemplate)
  {
    boolean wasRemoved = false;
    if (serviceTemplate.contains(aServiceTemplate))
    {
      serviceTemplate.remove(aServiceTemplate);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addServiceTemplateAt(ServiceTemplate aServiceTemplate, int index)
  {  
    boolean wasAdded = false;
    if(addServiceTemplate(aServiceTemplate))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfServiceTemplate()) { index = numberOfServiceTemplate() - 1; }
      serviceTemplate.remove(aServiceTemplate);
      serviceTemplate.add(index, aServiceTemplate);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveServiceTemplateAt(ServiceTemplate aServiceTemplate, int index)
  {
    boolean wasAdded = false;
    if(serviceTemplate.contains(aServiceTemplate))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfServiceTemplate()) { index = numberOfServiceTemplate() - 1; }
      serviceTemplate.remove(aServiceTemplate);
      serviceTemplate.add(index, aServiceTemplate);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addServiceTemplateAt(aServiceTemplate, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfCombos()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public ServiceCombo addCombo(Appointment aAppointments)
  {
    return new ServiceCombo(this, aAppointments);
  }

  public boolean addCombo(ServiceCombo aCombo)
  {
    boolean wasAdded = false;
    if (combos.contains(aCombo)) { return false; }
    ServiceComboTemplate existingServiceComboTemplate = aCombo.getServiceComboTemplate();
    boolean isNewServiceComboTemplate = existingServiceComboTemplate != null && !this.equals(existingServiceComboTemplate);
    if (isNewServiceComboTemplate)
    {
      aCombo.setServiceComboTemplate(this);
    }
    else
    {
      combos.add(aCombo);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeCombo(ServiceCombo aCombo)
  {
    boolean wasRemoved = false;
    //Unable to remove aCombo, as it must always have a serviceComboTemplate
    if (!this.equals(aCombo.getServiceComboTemplate()))
    {
      combos.remove(aCombo);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addComboAt(ServiceCombo aCombo, int index)
  {  
    boolean wasAdded = false;
    if(addCombo(aCombo))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCombos()) { index = numberOfCombos() - 1; }
      combos.remove(aCombo);
      combos.add(index, aCombo);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveComboAt(ServiceCombo aCombo, int index)
  {
    boolean wasAdded = false;
    if(combos.contains(aCombo))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCombos()) { index = numberOfCombos() - 1; }
      combos.remove(aCombo);
      combos.add(index, aCombo);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addComboAt(aCombo, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetOneToMany */
  public boolean setBusiness(Business aBusiness)
  {
    boolean wasSet = false;
    if (aBusiness == null)
    {
      return wasSet;
    }

    Business existingBusiness = business;
    business = aBusiness;
    if (existingBusiness != null && !existingBusiness.equals(aBusiness))
    {
      existingBusiness.removeComboTemplate(this);
    }
    business.addComboTemplate(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    serviceTemplate.clear();
    while (combos.size() > 0)
    {
      ServiceCombo aCombo = combos.get(combos.size() - 1);
      aCombo.delete();
      combos.remove(aCombo);
    }
    
    Business placeholderBusiness = business;
    this.business = null;
    if(placeholderBusiness != null)
    {
      placeholderBusiness.removeComboTemplate(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "isRequired" + ":" + getIsRequired()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "business = "+(getBusiness()!=null?Integer.toHexString(System.identityHashCode(getBusiness())):"null");
  }
}