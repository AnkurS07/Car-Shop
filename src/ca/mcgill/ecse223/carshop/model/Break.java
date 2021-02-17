/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse223.carshop.model;
import java.sql.Time;

// line 76 "../../../../../CarShopModel.ump"
public class Break
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Break Attributes
  private Time startTime;
  private Time endTime;

  //Break Associations
  private DailySchedule dailySchedule;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Break(Time aStartTime, Time aEndTime, DailySchedule aDailySchedule)
  {
    startTime = aStartTime;
    endTime = aEndTime;
    boolean didAddDailySchedule = setDailySchedule(aDailySchedule);
    if (!didAddDailySchedule)
    {
      throw new RuntimeException("Unable to create break due to dailySchedule. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setStartTime(Time aStartTime)
  {
    boolean wasSet = false;
    startTime = aStartTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setEndTime(Time aEndTime)
  {
    boolean wasSet = false;
    endTime = aEndTime;
    wasSet = true;
    return wasSet;
  }

  public Time getStartTime()
  {
    return startTime;
  }

  public Time getEndTime()
  {
    return endTime;
  }
  /* Code from template association_GetOne */
  public DailySchedule getDailySchedule()
  {
    return dailySchedule;
  }
  /* Code from template association_SetOneToMany */
  public boolean setDailySchedule(DailySchedule aDailySchedule)
  {
    boolean wasSet = false;
    if (aDailySchedule == null)
    {
      return wasSet;
    }

    DailySchedule existingDailySchedule = dailySchedule;
    dailySchedule = aDailySchedule;
    if (existingDailySchedule != null && !existingDailySchedule.equals(aDailySchedule))
    {
      existingDailySchedule.removeBreak(this);
    }
    dailySchedule.addBreak(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    DailySchedule placeholderDailySchedule = dailySchedule;
    this.dailySchedule = null;
    if(placeholderDailySchedule != null)
    {
      placeholderDailySchedule.removeBreak(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "startTime" + "=" + (getStartTime() != null ? !getStartTime().equals(this)  ? getStartTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "endTime" + "=" + (getEndTime() != null ? !getEndTime().equals(this)  ? getEndTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "dailySchedule = "+(getDailySchedule()!=null?Integer.toHexString(System.identityHashCode(getDailySchedule())):"null");
  }
}