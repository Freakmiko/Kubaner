package Kubaner.Logic;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TimeLine implements Iterable<TimeLineMember> {
	
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
	
	public boolean containsExams(){
		for(int i = 0; i < memberList.size(); i++){
			if(memberList.get(i).getClass() == Exam.class){
				return true;
			}
		}
		return false;
	}

	@Override
	public Iterator<TimeLineMember> iterator() {
		// TODO Auto-generated method stub
		return memberList.iterator();
	}
	

}
