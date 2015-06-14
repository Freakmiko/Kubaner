package Kubaner.Logic;

public class TimePeriod {

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
		if(tp.end.isEarlier(start) || end.isEarlier(tp.start))
			return false;
		return true;
		
	}
	
}
