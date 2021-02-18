/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse223.carshop.model;

// line 28 "../../../../../CarShopModel.ump"
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

  public Technician(CarShopSystem aCarShopSystem, TechnicianType aTechnicianType)
  {
    super(aCarShopSystem);
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
  /* Code from template association_SetOptionalOneToOne */
  public boolean setGarage(Garage aNewGarage)
  {
    boolean wasSet = false;
    if (garage != null && !garage.equals(aNewGarage) && equals(garage.getTechnician()))
    {
      //Unable to setGarage, as existing garage would become an orphan
      return wasSet;
    }

    garage = aNewGarage;
    Technician anOldTechnician = aNewGarage != null ? aNewGarage.getTechnician() : null;

    if (!this.equals(anOldTechnician))
    {
      if (anOldTechnician != null)
      {
        anOldTechnician.garage = null;
      }
      if (garage != null)
      {
        garage.setTechnician(this);
      }
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Garage existingGarage = garage;
    garage = null;
    if (existingGarage != null)
    {
      existingGarage.delete();
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