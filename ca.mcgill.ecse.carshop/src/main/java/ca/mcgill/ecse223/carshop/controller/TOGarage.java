/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse223.carshop.controller;
import java.util.*;

// line 73 "../../../../../CarshopTransferObjects.ump"
public class TOGarage
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOGarage Associations
  private List<TOBusinessHour> tobusinessHours;
  private TOTechnician tOTechnician;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOGarage(TOTechnician aTOTechnician)
  {
    tobusinessHours = new ArrayList<TOBusinessHour>();
    boolean didAddTOTechnician = setTOTechnician(aTOTechnician);
    if (!didAddTOTechnician)
    {
      throw new RuntimeException("Unable to create togarage due to tOTechnician. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetMany */
  public TOBusinessHour getTobusinessHour(int index)
  {
    TOBusinessHour aTobusinessHour = tobusinessHours.get(index);
    return aTobusinessHour;
  }

  public List<TOBusinessHour> getTobusinessHours()
  {
    List<TOBusinessHour> newTobusinessHours = Collections.unmodifiableList(tobusinessHours);
    return newTobusinessHours;
  }

  public int numberOfTobusinessHours()
  {
    int number = tobusinessHours.size();
    return number;
  }

  public boolean hasTobusinessHours()
  {
    boolean has = tobusinessHours.size() > 0;
    return has;
  }

  public int indexOfTobusinessHour(TOBusinessHour aTobusinessHour)
  {
    int index = tobusinessHours.indexOf(aTobusinessHour);
    return index;
  }
  /* Code from template association_GetOne */
  public TOTechnician getTOTechnician()
  {
    return tOTechnician;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfTobusinessHours()
  {
    return 0;
  }
  /* Code from template association_AddUnidirectionalMany */
  public boolean addTobusinessHour(TOBusinessHour aTobusinessHour)
  {
    boolean wasAdded = false;
    if (tobusinessHours.contains(aTobusinessHour)) { return false; }
    tobusinessHours.add(aTobusinessHour);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeTobusinessHour(TOBusinessHour aTobusinessHour)
  {
    boolean wasRemoved = false;
    if (tobusinessHours.contains(aTobusinessHour))
    {
      tobusinessHours.remove(aTobusinessHour);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addTobusinessHourAt(TOBusinessHour aTobusinessHour, int index)
  {  
    boolean wasAdded = false;
    if(addTobusinessHour(aTobusinessHour))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTobusinessHours()) { index = numberOfTobusinessHours() - 1; }
      tobusinessHours.remove(aTobusinessHour);
      tobusinessHours.add(index, aTobusinessHour);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveTobusinessHourAt(TOBusinessHour aTobusinessHour, int index)
  {
    boolean wasAdded = false;
    if(tobusinessHours.contains(aTobusinessHour))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfTobusinessHours()) { index = numberOfTobusinessHours() - 1; }
      tobusinessHours.remove(aTobusinessHour);
      tobusinessHours.add(index, aTobusinessHour);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addTobusinessHourAt(aTobusinessHour, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetOneToOptionalOne */
  public boolean setTOTechnician(TOTechnician aNewTOTechnician)
  {
    boolean wasSet = false;
    if (aNewTOTechnician == null)
    {
      //Unable to setTOTechnician to null, as togarage must always be associated to a tOTechnician
      return wasSet;
    }
    
    TOGarage existingTogarage = aNewTOTechnician.getTogarage();
    if (existingTogarage != null && !equals(existingTogarage))
    {
      //Unable to setTOTechnician, the current tOTechnician already has a togarage, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    TOTechnician anOldTOTechnician = tOTechnician;
    tOTechnician = aNewTOTechnician;
    tOTechnician.setTogarage(this);

    if (anOldTOTechnician != null)
    {
      anOldTOTechnician.setTogarage(null);
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    tobusinessHours.clear();
    TOTechnician existingTOTechnician = tOTechnician;
    tOTechnician = null;
    if (existingTOTechnician != null)
    {
      existingTOTechnician.setTogarage(null);
    }
  }

}