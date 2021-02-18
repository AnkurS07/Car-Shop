/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse223.carshop.model;
import java.util.*;
import java.sql.Time;

// line 97 "../../../../../CarShopModel.ump"
public class Garage
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Garage Associations
  private Technician technician;
  private List<ServiceTemplate> serviceTemplate;
  private List<DailySchedule> schedules;
  private CarShopSystem carShopSystem;
  private Business business;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Garage(Technician aTechnician, CarShopSystem aCarShopSystem, Business aBusiness)
  {
    boolean didAddTechnician = setTechnician(aTechnician);
    if (!didAddTechnician)
    {
      throw new RuntimeException("Unable to create garage due to technician. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    serviceTemplate = new ArrayList<ServiceTemplate>();
    schedules = new ArrayList<DailySchedule>();
    boolean didAddCarShopSystem = setCarShopSystem(aCarShopSystem);
    if (!didAddCarShopSystem)
    {
      throw new RuntimeException("Unable to create garage due to carShopSystem. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddBusiness = setBusiness(aBusiness);
    if (!didAddBusiness)
    {
      throw new RuntimeException("Unable to create garage due to business. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public Technician getTechnician()
  {
    return technician;
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
  public DailySchedule getSchedule(int index)
  {
    DailySchedule aSchedule = schedules.get(index);
    return aSchedule;
  }

  public List<DailySchedule> getSchedules()
  {
    List<DailySchedule> newSchedules = Collections.unmodifiableList(schedules);
    return newSchedules;
  }

  public int numberOfSchedules()
  {
    int number = schedules.size();
    return number;
  }

  public boolean hasSchedules()
  {
    boolean has = schedules.size() > 0;
    return has;
  }

  public int indexOfSchedule(DailySchedule aSchedule)
  {
    int index = schedules.indexOf(aSchedule);
    return index;
  }
  /* Code from template association_GetOne */
  public CarShopSystem getCarShopSystem()
  {
    return carShopSystem;
  }
  /* Code from template association_GetOne */
  public Business getBusiness()
  {
    return business;
  }
  /* Code from template association_SetOneToOptionalOne */
  public boolean setTechnician(Technician aNewTechnician)
  {
    boolean wasSet = false;
    if (aNewTechnician == null)
    {
      //Unable to setTechnician to null, as garage must always be associated to a technician
      return wasSet;
    }
    
    Garage existingGarage = aNewTechnician.getGarage();
    if (existingGarage != null && !equals(existingGarage))
    {
      //Unable to setTechnician, the current technician already has a garage, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    Technician anOldTechnician = technician;
    technician = aNewTechnician;
    technician.setGarage(this);

    if (anOldTechnician != null)
    {
      anOldTechnician.setGarage(null);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfServiceTemplate()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public ServiceTemplate addServiceTemplate(String aName, Time aDuration, Business aBusiness)
  {
    return new ServiceTemplate(aName, aDuration, aBusiness, this);
  }

  public boolean addServiceTemplate(ServiceTemplate aServiceTemplate)
  {
    boolean wasAdded = false;
    if (serviceTemplate.contains(aServiceTemplate)) { return false; }
    Garage existingGarage = aServiceTemplate.getGarage();
    boolean isNewGarage = existingGarage != null && !this.equals(existingGarage);
    if (isNewGarage)
    {
      aServiceTemplate.setGarage(this);
    }
    else
    {
      serviceTemplate.add(aServiceTemplate);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeServiceTemplate(ServiceTemplate aServiceTemplate)
  {
    boolean wasRemoved = false;
    //Unable to remove aServiceTemplate, as it must always have a garage
    if (!this.equals(aServiceTemplate.getGarage()))
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
  public static int minimumNumberOfSchedules()
  {
    return 0;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfSchedules()
  {
    return 7;
  }
  /* Code from template association_AddUnidirectionalOptionalN */
  public boolean addSchedule(DailySchedule aSchedule)
  {
    boolean wasAdded = false;
    if (schedules.contains(aSchedule)) { return false; }
    if (numberOfSchedules() < maximumNumberOfSchedules())
    {
      schedules.add(aSchedule);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean removeSchedule(DailySchedule aSchedule)
  {
    boolean wasRemoved = false;
    if (schedules.contains(aSchedule))
    {
      schedules.remove(aSchedule);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_SetUnidirectionalOptionalN */
  public boolean setSchedules(DailySchedule... newSchedules)
  {
    boolean wasSet = false;
    ArrayList<DailySchedule> verifiedSchedules = new ArrayList<DailySchedule>();
    for (DailySchedule aSchedule : newSchedules)
    {
      if (verifiedSchedules.contains(aSchedule))
      {
        continue;
      }
      verifiedSchedules.add(aSchedule);
    }

    if (verifiedSchedules.size() != newSchedules.length || verifiedSchedules.size() > maximumNumberOfSchedules())
    {
      return wasSet;
    }

    schedules.clear();
    schedules.addAll(verifiedSchedules);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addScheduleAt(DailySchedule aSchedule, int index)
  {  
    boolean wasAdded = false;
    if(addSchedule(aSchedule))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfSchedules()) { index = numberOfSchedules() - 1; }
      schedules.remove(aSchedule);
      schedules.add(index, aSchedule);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveScheduleAt(DailySchedule aSchedule, int index)
  {
    boolean wasAdded = false;
    if(schedules.contains(aSchedule))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfSchedules()) { index = numberOfSchedules() - 1; }
      schedules.remove(aSchedule);
      schedules.add(index, aSchedule);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addScheduleAt(aSchedule, index);
    }
    return wasAdded;
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
      existingCarShopSystem.removeGarage(this);
    }
    carShopSystem.addGarage(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMandatoryMany */
  public boolean setBusiness(Business aBusiness)
  {
    boolean wasSet = false;
    //Must provide business to garage
    if (aBusiness == null)
    {
      return wasSet;
    }

    if (business != null && business.numberOfGarages() <= Business.minimumNumberOfGarages())
    {
      return wasSet;
    }

    Business existingBusiness = business;
    business = aBusiness;
    if (existingBusiness != null && !existingBusiness.equals(aBusiness))
    {
      boolean didRemove = existingBusiness.removeGarage(this);
      if (!didRemove)
      {
        business = existingBusiness;
        return wasSet;
      }
    }
    business.addGarage(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Technician existingTechnician = technician;
    technician = null;
    if (existingTechnician != null)
    {
      existingTechnician.setGarage(null);
    }
    for(int i=serviceTemplate.size(); i > 0; i--)
    {
      ServiceTemplate aServiceTemplate = serviceTemplate.get(i - 1);
      aServiceTemplate.delete();
    }
    schedules.clear();
    CarShopSystem placeholderCarShopSystem = carShopSystem;
    this.carShopSystem = null;
    if(placeholderCarShopSystem != null)
    {
      placeholderCarShopSystem.removeGarage(this);
    }
    Business placeholderBusiness = business;
    this.business = null;
    if(placeholderBusiness != null)
    {
      placeholderBusiness.removeGarage(this);
    }
  }

}