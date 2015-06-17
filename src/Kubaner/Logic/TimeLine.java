package Kubaner.Logic;

public class TimeLine {
	
	public String room;
	private TimeLineMember[] timeLine;
	
	
	public TimeLine(int timeLineMemberCount, String room) {
		this.timeLine = new TimeLineMember[timeLineMemberCount];
		this.room = room;
	}
	
	public TimeLine(String room) {
		this.timeLine = new TimeLineMember[96]; // 24 Stunden, je 15 Minuten
		this.room = room;
	}
	
	public TimeLine(int timeLineMemberCount) {
		this.timeLine = new TimeLineMember[timeLineMemberCount];
		this.room = "Testraum";
	}
	
	public TimeLine() {
		this.timeLine = new TimeLineMember[96]; // 24 Stunden, je 15 Minuten
		this.room = "Testraum";
	}
	
	
	public void insertAt(int index, TimeLineMember time){
		this.timeLine[index] = time;		
	}
	
	public void deleteAt(int index){
		this.timeLine[index] = null;
	}
	
	public void moveMember(int oldIndex, int newIndex){
		
		//es fehlen hier noch Exceptions und andere ausnahmen
		
		TimeLineMember tmpOld = this.timeLine[oldIndex];
		if(newIndex > oldIndex){
			for(int i = oldIndex; i == newIndex; i++){
				this.timeLine[i] = this.timeLine[i+1];
			}
			this.timeLine[newIndex] = tmpOld;
		}
		if(newIndex < oldIndex){
			for(int i = oldIndex; i == newIndex; i--){
				this.timeLine[i] = this.timeLine[i-1];
			}
			this.timeLine[newIndex] = tmpOld;	
		}
		
		if(newIndex == oldIndex){
		}
		this.timeLine[newIndex] = this.timeLine[oldIndex];
	}

	public String getRoom(){
		return this.room;
	}
	
	public void setRoom(String room){
		this.room = room;
	}
	
	public int elementCount(){
		int count = 0;
		for(int i = 0; i >= timeLine.length; i++){
			if(timeLine[i]!=null){
				count++;
			}
		}
		return count;
	}
	
	public TimeLineMember getTimeLineMember(int index){
		return this.timeLine[index];
	}
	

}
