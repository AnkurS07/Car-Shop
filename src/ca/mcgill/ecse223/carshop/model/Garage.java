/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse223.carshop.model;
import java.util.*;
import java.sql.Time;

// line 94 "../../../../../CarShopModel.ump"
public class Garage
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Garage Associations
  private Technician technician;
  private List<Service> services;
  private List<DailySchedule> schedules;
  private CarShopModel carShopModel;
  private Business business;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Garage(Technician aTechnician, CarShopModel aCarShopModel, Business aBusiness)
  {
    boolean didAddTechnician = setTechnician(aTechnician);
    if (!didAddTechnician)
    {
      throw new RuntimeException("Unable to create garage due to technician. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    services = new ArrayList<Service>();
    schedules = new ArrayList<DailySchedule>();
    boolean didAddCarShopModel = setCarShopModel(aCarShopModel);
    if (!didAddCarShopModel)
    {
      throw new RuntimeException("Unable to create garage due to carShopModel. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
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
  public Service getService(int index)
  {
    Service aService = services.get(index);
    return aService;
  }

  public List<Service> getServices()
  {
    List<Service> newServices = Collections.unmodifiableList(services);
    return newServices;
  }

  public int numberOfServices()
  {
    int number = services.size();
    return number;
  }

  public boolean hasServices()
  {
    boolean has = services.size() > 0;
    return has;
  }

  public int indexOfService(Service aService)
  {
    int index = services.indexOf(aService);
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
  public CarShopModel getCarShopModel()
  {
    return carShopModel;
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
  public static int minimumNumberOfServices()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Service addService(String aName, Time aDuration, Business aBusiness)
  {
    return new Service(aName, aDuration, aBusiness, this);
  }

  public boolean addService(Service aService)
  {
    boolean wasAdded = false;
    if (services.contains(aService)) { return false; }
    Garage existingGarage = aService.getGarage();
    boolean isNewGarage = existingGarage != null && !this.equals(existingGarage);
    if (isNewGarage)
    {
      aService.setGarage(this);
    }
    else
    {
      services.add(aService);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeService(Service aService)
  {
    boolean wasRemoved = false;
    //Unable to remove aService, as it must always have a garage
    if (!this.equals(aService.getGarage()))
    {
      services.remove(aService);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addServiceAt(Service aService, int index)
  {  
    boolean wasAdded = false;
    if(addService(aService))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfServices()) { index = numberOfServices() - 1; }
      services.remove(aService);
      services.add(index, aService);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveServiceAt(Service aService, int index)
  {
    boolean wasAdded = false;
    if(services.contains(aService))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfServices()) { index = numberOfServices() - 1; }
      services.remove(aService);
      services.add(index, aService);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addServiceAt(aService, index);
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
      existingCarShopModel.removeGarage(this);
    }
    carShopModel.addGarage(this);
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
    for(int i=services.size(); i > 0; i--)
    {
      Service aService = services.get(i - 1);
      aService.delete();
    }
    schedules.clear();
    CarShopModel placeholderCarShopModel = carShopModel;
    this.carShopModel = null;
    if(placeholderCarShopModel != null)
    {
      placeholderCarShopModel.removeGarage(this);
    }
    Business placeholderBusiness = business;
    this.business = null;
    if(placeholderBusiness != null)
    {
      placeholderBusiness.removeGarage(this);
    }
  }

}