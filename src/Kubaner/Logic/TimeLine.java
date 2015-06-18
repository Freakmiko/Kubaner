package Kubaner.Logic;

import java.util.ArrayList;
import java.util.List;

public class TimeLine {
	
	public String room;
	private List<TimeLineMember> memberList;
	
	public TimeLine(){
		memberList = new ArrayList<TimeLineMember>();
	}
	
	public void add(TimeLineMember member){
		memberList.add(member);
	}
	
	public void insert(int index, TimeLineMember member){
		memberList.add(index, member);
	}
	
	public void delete(int index){
		memberList.remove(index);
	}
	
	public int size(){
		return memberList.size();
	}
	
	public void moveMember(int oldIndex, int newIndex){
		TimeLineMember help = memberList.remove(oldIndex);
		memberList.add(newIndex, help);
	}

	public String getRoom(){
		return this.room;
	}
	
	public void setRoom(String room){
		this.room = room;
	}
	
	public TimeLineMember getTimeLineMember(int index){
		return memberList.get(index);
	}
	

}
