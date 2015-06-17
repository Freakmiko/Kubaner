package Kubaner.Logic;

public class TimeLine {
	
	public String room;
	private TimeLineMember timeLine[];
	
	public void insertAt(int index, TimeLineMember time){
		this.timeLine[index] = time;		
	}
	
	public void deleteAt(int index){
		this.timeLine[index] = null;
	}
	
	public void moveMember(int oldIndex, int newIndex){
		
		//es fehlen hier noch Exceptions und andere ausnahmen
		
		TimeLineMember tmpOld = this.timeLine[oldIndex];
		TimeLineMember tmpNew = this.timeLine[newIndex];
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
		
		for(int i)
		
		return 0;
	}
	
	public TimeLineMember getTimeLineMember(int index){
		return this.timeLine[index];
	}
	

}
