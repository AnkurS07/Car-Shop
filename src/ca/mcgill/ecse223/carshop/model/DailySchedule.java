/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse223.carshop.model;
import java.sql.Time;
import java.util.*;

// line 57 "../../../../../CarShopModel.ump"
public class DailySchedule
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum Day { Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //DailySchedule Attributes
  private Day day;
  private Time openingTime;
  private Time closingTime;

  //DailySchedule Associations
  private List<Break> breaks;
  private CarShopSystem carShopSystem;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public DailySchedule(Day aDay, Time aOpeningTime, Time aClosingTime, CarShopSystem aCarShopSystem)
  {
    day = aDay;
    openingTime = aOpeningTime;
    closingTime = aClosingTime;
    breaks = new ArrayList<Break>();
    boolean didAddCarShopSystem = setCarShopSystem(aCarShopSystem);
    if (!didAddCarShopSystem)
    {
      throw new RuntimeException("Unable to create schedule due to carShopSystem. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setDay(Day aDay)
  {
    boolean wasSet = false;
    day = aDay;
    wasSet = true;
    return wasSet;
  }

  public boolean setOpeningTime(Time aOpeningTime)
  {
    boolean wasSet = false;
    openingTime = aOpeningTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setClosingTime(Time aClosingTime)
  {
    boolean wasSet = false;
    closingTime = aClosingTime;
    wasSet = true;
    return wasSet;
  }

  public Day getDay()
  {
    return day;
  }

  public Time getOpeningTime()
  {
    return openingTime;
  }

  public Time getClosingTime()
  {
    return closingTime;
  }
  /* Code from template association_GetMany */
  public Break getBreak(int index)
  {
    Break aBreak = breaks.get(index);
    return aBreak;
  }

  public List<Break> getBreaks()
  {
    List<Break> newBreaks = Collections.unmodifiableList(breaks);
    return newBreaks;
  }

  public int numberOfBreaks()
  {
    int number = breaks.size();
    return number;
  }

  public boolean hasBreaks()
  {
    boolean has = breaks.size() > 0;
    return has;
  }

  public int indexOfBreak(Break aBreak)
  {
    int index = breaks.indexOf(aBreak);
    return index;
  }
  /* Code from template association_GetOne */
  public CarShopSystem getCarShopSystem()
  {
    return carShopSystem;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfBreaks()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Break addBreak(Time aStartTime, Time aEndTime)
  {
    return new Break(aStartTime, aEndTime, this);
  }

  public boolean addBreak(Break aBreak)
  {
    boolean wasAdded = false;
    if (breaks.contains(aBreak)) { return false; }
    DailySchedule existingDailySchedule = aBreak.getDailySchedule();
    boolean isNewDailySchedule = existingDailySchedule != null && !this.equals(existingDailySchedule);
    if (isNewDailySchedule)
    {
      aBreak.setDailySchedule(this);
    }
    else
    {
      breaks.add(aBreak);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeBreak(Break aBreak)
  {
    boolean wasRemoved = false;
    //Unable to remove aBreak, as it must always have a dailySchedule
    if (!this.equals(aBreak.getDailySchedule()))
    {
      breaks.remove(aBreak);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addBreakAt(Break aBreak, int index)
  {  
    boolean wasAdded = false;
    if(addBreak(aBreak))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBreaks()) { index = numberOfBreaks() - 1; }
      breaks.remove(aBreak);
      breaks.add(index, aBreak);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveBreakAt(Break aBreak, int index)
  {
    boolean wasAdded = false;
    if(breaks.contains(aBreak))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBreaks()) { index = numberOfBreaks() - 1; }
      breaks.remove(aBreak);
      breaks.add(index, aBreak);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addBreakAt(aBreak, index);
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
      existingCarShopSystem.removeSchedule(this);
    }
    carShopSystem.addSchedule(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    while (breaks.size() > 0)
    {
      Break aBreak = breaks.get(breaks.size() - 1);
      aBreak.delete();
      breaks.remove(aBreak);
    }
    
    CarShopSystem placeholderCarShopSystem = carShopSystem;
    this.carShopSystem = null;
    if(placeholderCarShopSystem != null)
    {
      placeholderCarShopSystem.removeSchedule(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "day" + "=" + (getDay() != null ? !getDay().equals(this)  ? getDay().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "openingTime" + "=" + (getOpeningTime() != null ? !getOpeningTime().equals(this)  ? getOpeningTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "closingTime" + "=" + (getClosingTime() != null ? !getClosingTime().equals(this)  ? getClosingTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "carShopSystem = "+(getCarShopSystem()!=null?Integer.toHexString(System.identityHashCode(getCarShopSystem())):"null");
  }
}