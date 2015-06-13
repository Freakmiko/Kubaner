package Kubaner.Logic;


public class Student {
	
	private String name;
	private TimePeriod[] absence;
	private Subject[] faecher;
	
	public Student(String name, Subject[] subj, TimePeriod[] abs){
		
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name=name;
	}
	
	public Subject[] getSubjectArray(){
		return faecher;
	}
	
	public void deleteSubject(int index){
		if(index>=0&&index<faecher.length){
			faecher[index]=null;
			shiftAllToTheLeft(index+1);
		}
			
	}
	
	public void addSubject(Subject sub){
		//Array voll --> neues anlegen
		if(faecher[faecher.length-1]!=null){
			Subject[] tmp = new Subject[faecher.length+1];
			
			for(int i=0;i<faecher.length;i++)
				tmp[i]=faecher[i];	
			
			tmp[tmp.length-1]=sub;
			
			faecher=tmp;
		}
		//an passender Stelle einfügen
		else{
			for(int i=0;i<faecher.length;i++){
				if(faecher[i]==null){
					faecher[i]=sub;
					return;
				}
			}
		}
		
	}
	
	public TimePeriod[] getAbsenceTime(){
		return absence;
	}
	
	public void addAbsenceTime(TimePeriod abs){
		//array um eins vergrößern
		TimePeriod[] tmp = new TimePeriod[absence.length+1];
		for(int i=0;i<absence.length;i++)
			tmp[i]=absence[i];
		tmp[absence.length]=abs;
		absence=tmp;
	}	
	
	public boolean deleteAbsenceTime(int index){
		if(index>=0 && index <absence.length){
			absence[index]=null;
			shiftAllAbsToTheLeft(index+1);
			return true;
		}
		return false;
	}
	
	private void shiftAllAbsToTheLeft(int indexOfFirstElementToBeShifted){
		for(int i=indexOfFirstElementToBeShifted;i<absence.length;i++){
			absence[i-1]=absence[i];
			absence[i]=null;				
		}
	}
	
	public TimePeriod[] getAbsenceTimeArary(){
		return absence;
	}
	
	//Make sure indexOfFirstElementToBeShifted >0
	private void shiftAllToTheLeft(int indexOfFirstElementToBeShifted){
		for(int i=indexOfFirstElementToBeShifted;i<faecher.length;i++){
			faecher[i-1]=faecher[i];
			faecher[i]=null;				
		}
	}

}
