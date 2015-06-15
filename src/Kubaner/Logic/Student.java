package Kubaner.Logic;


public class Student {
	
	private String name;
	private TimePeriod[] absence;
	private Subject[] faecher;
	
	public Student(String name, Subject[] subj, TimePeriod[] absence){
		this.name=name;
		this.absence=absence;
		this.faecher=subj;
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
	
	/**
	 * Deletes the Subject at the given index.
	 * 
	 * @return true if the Subject at the given index could be deleted and false if not
	 */
	public boolean deleteSubject(int index){
		if(index>=0&&index<faecher.length){
			faecher[index]=null;
			shiftAllToTheLeft(index+1);
			return true;
		}
		return false;
			
	}
	
	/**
	 * Let's you add another subject to the student
	 * 
	 * @param sub the new subject
	 */
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
	
	/**
	 * Adds another TimePeriod where this student is absent
	 * 
	 * @param absence the TimePeriod when this student is absent 
	 */
	public void addTimePeriod(TimePeriod absence){
		//array um eins vergrößern
		TimePeriod[] tmp = new TimePeriod[this.absence.length+1];
		for(int i=0;i<this.absence.length;i++)
			tmp[i]=this.absence[i];
		tmp[this.absence.length]=absence;
		this.absence=tmp;
	}	
	
	/**
	 * Deletes the TimePeriod at the given index, so this student is no longer absent at this Time.
	 * 
	 * @return true if TimePeriod could be deleted and false if not
	 */
	public boolean deleteTimePeriod(int index){
		if(index>=0 && index <absence.length){
			absence[index]=null;
			shiftAllAbsToTheLeft(index+1);
			return true;
		}
		return false;
	}
	
	//Make sure indexOfFirstElementToBeShifted >0
	private void shiftAllAbsToTheLeft(int indexOfFirstElementToBeShifted){
		for(int i=indexOfFirstElementToBeShifted;i<absence.length;i++){
			absence[i-1]=absence[i];
			absence[i]=null;				
		}
	}
	
	/**
	 * Returns all TimePeriods where this student is absent as an array
	 */
	public TimePeriod[] getTimePeriodArray(){
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
