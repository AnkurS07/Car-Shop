/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse223.carshop.controller;

// line 22 "../../../../../CarshopTransferObjects.ump"
public class TOComboItem
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOComboItem Attributes
  private boolean mandatory;

  //TOComboItem Associations
  private TOService service;
  private TOServiceCombo tOServiceCombo;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOComboItem(boolean aMandatory, TOService aService, TOServiceCombo aTOServiceCombo)
  {
    mandatory = aMandatory;
    if (!setService(aService))
    {
      throw new RuntimeException("Unable to create TOComboItem due to aService. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddTOServiceCombo = setTOServiceCombo(aTOServiceCombo);
    if (!didAddTOServiceCombo)
    {
      throw new RuntimeException("Unable to create service due to tOServiceCombo. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setMandatory(boolean aMandatory)
  {
    boolean wasSet = false;
    mandatory = aMandatory;
    wasSet = true;
    return wasSet;
  }

  public boolean getMandatory()
  {
    return mandatory;
  }
  /* Code from template association_GetOne */
  public TOService getService()
  {
    return service;
  }
  /* Code from template association_GetOne */
  public TOServiceCombo getTOServiceCombo()
  {
    return tOServiceCombo;
  }
  /* Code from template association_SetUnidirectionalOne */
  public boolean setService(TOService aNewService)
  {
    boolean wasSet = false;
    if (aNewService != null)
    {
      service = aNewService;
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setTOServiceCombo(TOServiceCombo aTOServiceCombo)
  {
    boolean wasSet = false;
    if (aTOServiceCombo == null)
    {
      return wasSet;
    }

    TOServiceCombo existingTOServiceCombo = tOServiceCombo;
    tOServiceCombo = aTOServiceCombo;
    if (existingTOServiceCombo != null && !existingTOServiceCombo.equals(aTOServiceCombo))
    {
      existingTOServiceCombo.removeService(this);
    }
    tOServiceCombo.addService(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    service = null;
    TOServiceCombo placeholderTOServiceCombo = tOServiceCombo;
    this.tOServiceCombo = null;
    if(placeholderTOServiceCombo != null)
    {
      placeholderTOServiceCombo.removeService(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "mandatory" + ":" + getMandatory()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "service = "+(getService()!=null?Integer.toHexString(System.identityHashCode(getService())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "tOServiceCombo = "+(getTOServiceCombo()!=null?Integer.toHexString(System.identityHashCode(getTOServiceCombo())):"null");
  }
}