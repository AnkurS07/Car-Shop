/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse223.carshop.model;

// line 38 "../../../../../CarShopModel.ump"
public class Technician extends UserRole
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum TechnicianType { TiresTechnician, EngineTehcnician, TransmissionTechnician, ElectronicsTechnician, FluidsTechnician }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Technician Attributes
  private TechnicianType technicianType;

  //Technician Associations
  private Garage garage;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Technician(CarShopModel aCarShopModel, TechnicianType aTechnicianType)
  {
    super(aCarShopModel);
    technicianType = aTechnicianType;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setTechnicianType(TechnicianType aTechnicianType)
  {
    boolean wasSet = false;
    technicianType = aTechnicianType;
    wasSet = true;
    return wasSet;
  }

  public TechnicianType getTechnicianType()
  {
    return technicianType;
  }
  /* Code from template association_GetOne */
  public Garage getGarage()
  {
    return garage;
  }

  public boolean hasGarage()
  {
    boolean has = garage != null;
    return has;
  }
  /* Code from template association_SetOptionalOneToOptionalOne */
  public boolean setGarage(Garage aNewGarage)
  {
    boolean wasSet = false;
    if (aNewGarage == null)
    {
      Garage existingGarage = garage;
      garage = null;
      
      if (existingGarage != null && existingGarage.getTechnician() != null)
      {
        existingGarage.setTechnician(null);
      }
      wasSet = true;
      return wasSet;
    }

    Garage currentGarage = getGarage();
    if (currentGarage != null && !currentGarage.equals(aNewGarage))
    {
      currentGarage.setTechnician(null);
    }

    garage = aNewGarage;
    Technician existingTechnician = aNewGarage.getTechnician();

    if (!equals(existingTechnician))
    {
      aNewGarage.setTechnician(this);
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    if (garage != null)
    {
      garage.setTechnician(null);
    }
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "technicianType" + "=" + (getTechnicianType() != null ? !getTechnicianType().equals(this)  ? getTechnicianType().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "garage = "+(getGarage()!=null?Integer.toHexString(System.identityHashCode(getGarage())):"null");
  }
}