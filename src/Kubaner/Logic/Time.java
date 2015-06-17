package Kubaner.Logic;

public class Time {
	
	private int hour;
	private int minute;
	
	public Time(int hour, int minute){
		this.hour=hour;
		this.minute=minute;
	}
	
	public int getHour() {
		return hour;
	}

	public void setHour(int stunde) {
		hour = stunde;
	}
	
	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}
	
	
	/**
	 * Adds an positive amount of minutes to current time of the current {@link Time} object
	 * and returns a new Time object which represents the new time.
	 * 
	 * @param additionalMinutes
	 * 		The amount of minutes to be added.
	 * @return
	 * 		A new Time object which contains the new time value.
	 * 
	 * @throws IllegalArgumentException
	 * 		if the additionalTime parameter is smaller than 0.
	 */
	public Time addMinutes(int additionalMinutes)
	{
		if(additionalMinutes < 0)
			throw new IllegalArgumentException("Cannot add a negative amount of minutes.");
		
		int newMinutes = (this.minute + additionalMinutes) % 60;
		int additionalHours = (this.minute + additionalMinutes) / 60;
		int newHour = (this.hour + additionalHours) % 24;
		
		Time newTime = new Time(newHour, newMinutes);
		
		return newTime;
	}
	
	/**
	 * Returns true if this Time is earlier than the parameter Time and false if not
	 * 
	 * @param t Time
	 */
	public boolean isEarlier(Time t){
		if(hour<t.hour)
			return true;
		else if(t.hour==this.hour)
			if(minute<t.minute)
				return true;			
		
		return false;
	}
	
	/**
	 * Returns true if this Time is Later than the parameter Time and false if not
	 * 
	 * @param t Time
	 */
	public boolean isLater(Time t){
		if(hour>t.hour)
			return true;
		else if(hour>t.hour)
			if(minute>t.minute)
				return true;
			
		return false;
	}
	
	/**
	 * Checks if another time object is later or equal to another Time object.
	 * 
	 * @param another
	 * 		Another Time object.
	 * @return
	 * 		true, if this Time is equal or later than the parameter Time, otherwise false.
	 */
	public boolean isLaterOrEqual(Time another)
	{
		return equals(another) || isLater(another);
	}
	
	/**
	 * Checks if another time object is earlier or equal to another Time object.
	 * 
	 * @param another
	 * 		Another Time object.
	 * @return
	 * 		true, if this Time is equal or earlier than the parameter Time, otherwise false.
	 */
	public boolean isEarlierOrEqual(Time another)
	{
		return equals(another) || isEarlier(another);
	}

	/**
	 * Checks if another time object is equal to the current Time object.
	 * 
	 * @param anotherTime
	 * 		A time object.
	 * @return
	 * 		True, if the hour and the minutes of both Time objects are equal, otherwise false.
	 */
	public boolean equals(Time anotherTime)
	{
		if(this == anotherTime)
			return true;
		else if(anotherTime == null)
			return false;
		else if(this.minute == anotherTime.minute
				&& this.hour == anotherTime.hour)
		{
			return true;
		}
		
		return false;
	}
}
