package Kubaner.Logic;

import java.io.Serializable;

public class TimePeriod implements Serializable {

	private Time start;
	private Time end;
	
	public TimePeriod(Time von, Time bis){
		this.start=von;
		this.end=bis;
	}
	
	public Time getStart() {
		return start;
	}

	public void setStart(Time start) {
		this.start = start;
	}

	public Time getEnd() {
		return end;
	}

	public void setEnd(Time end) {
		this.end = end;
	}
	
	/**
	 * Returns true if this TimePeriod start before tp ends or 
	 * if tp starts before this TimePeriod ends
	 * 
	 * @param tp TimePeriod
	 */
	public boolean overlaying(TimePeriod tp){		
		if(tp.getEnd().isEarlier(start) || end.isEarlier(tp.getStart()))
			return false;
		if(tp.getEnd().equals(start) || end.equals(tp.getStart())){
			return false;
		}	
		return true;
		
	}
	
	/**
	 * Checks if a {@link Time} object is inside the current {@link TimePeriod}
	 * or matches the start or end times.
	 * 
	 * @param time
	 * 		a {@link Time} object.
	 * @return
	 * 		true, if the parameter time lays between or is equal to the start or the end time.
	 */
	public boolean laysBetween(Time time)
	{
		return start.isEarlierOrEqual(time) && end.isLaterOrEqual(time);
	}
	
}
