package Kubaner.Logic;

public class Time {
	
	private int Stunde;
	private int Minute;
	
	public Time(int stunde, int minute){
		this.Stunde=stunde;
		this.Minute=minute;
	}
	
	public int getStunde() {
		return Stunde;
	}

	public void setStunde(int stunde) {
		Stunde = stunde;
	}
	
	public int getMinute() {
		return Minute;
	}

	public void setMinute(int minute) {
		Minute = minute;
	}
	
	/**
	 * Returns true if this Time is earlier than the parameter Time
	 */
	public boolean isEarlier(Time t){
		if(Stunde<t.Stunde)
			return true;
		else if(t.Stunde==this.Stunde)
			if(Minute<t.Minute)
				return true;			
		
		return false;
	}
	
	/**
	 * Returns true if this Time is Later than the parameter Time
	 */
	public boolean isLater(Time t){
		if(Stunde>t.Stunde)
			return true;
		else if(Stunde>t.Stunde)
			if(Minute>t.Minute)
				return true;
			
		return false;
	}

}
