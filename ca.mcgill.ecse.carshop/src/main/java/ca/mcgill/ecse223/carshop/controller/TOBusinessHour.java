/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package ca.mcgill.ecse223.carshop.controller;
import java.sql.Time;

<<<<<<< HEAD
// line 77 "../../../../../CarshopTransferObjects.ump"
=======
// line 52 "../../../../../CarshopTransferObjects.ump"
>>>>>>> master
public class TOBusinessHour
{

  //------------------------
<<<<<<< HEAD
  // ENUMERATIONS
  //------------------------

  public enum DayOfWeek { Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday }

  //------------------------
=======
>>>>>>> master
  // MEMBER VARIABLES
  //------------------------

  //TOBusinessHour Attributes
<<<<<<< HEAD
  private DayOfWeek dayOfWeek;
=======
  private String dayOfWeek;
>>>>>>> master
  private Time startTime;
  private Time endTime;

  //------------------------
  // CONSTRUCTOR
  //------------------------

<<<<<<< HEAD
  public TOBusinessHour(DayOfWeek aDayOfWeek, Time aStartTime, Time aEndTime)
=======
  public TOBusinessHour(String aDayOfWeek, Time aStartTime, Time aEndTime)
>>>>>>> master
  {
    dayOfWeek = aDayOfWeek;
    startTime = aStartTime;
    endTime = aEndTime;
  }

  //------------------------
  // INTERFACE
  //------------------------

<<<<<<< HEAD
  public boolean setDayOfWeek(DayOfWeek aDayOfWeek)
=======
  public boolean setDayOfWeek(String aDayOfWeek)
>>>>>>> master
  {
    boolean wasSet = false;
    dayOfWeek = aDayOfWeek;
    wasSet = true;
    return wasSet;
  }

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

<<<<<<< HEAD
  public DayOfWeek getDayOfWeek()
=======
  public String getDayOfWeek()
>>>>>>> master
  {
    return dayOfWeek;
  }

  public Time getStartTime()
  {
    return startTime;
  }

  public Time getEndTime()
  {
    return endTime;
  }

  public void delete()
  {}


  public String toString()
  {
<<<<<<< HEAD
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "dayOfWeek" + "=" + (getDayOfWeek() != null ? !getDayOfWeek().equals(this)  ? getDayOfWeek().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
=======
    return super.toString() + "["+
            "dayOfWeek" + ":" + getDayOfWeek()+ "]" + System.getProperties().getProperty("line.separator") +
>>>>>>> master
            "  " + "startTime" + "=" + (getStartTime() != null ? !getStartTime().equals(this)  ? getStartTime().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "endTime" + "=" + (getEndTime() != null ? !getEndTime().equals(this)  ? getEndTime().toString().replaceAll("  ","    ") : "this" : "null");
  }
}