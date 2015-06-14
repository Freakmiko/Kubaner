package Kubaner.Logic;

import java.util.Iterator;
import java.util.Vector;

/**
 * Holds all Students in lexicographical order
 */
public class StudentList implements Iterable<Student> {

	private Vector<Student> v = new Vector<Student>(100,10);
	
	public Student create(String name, Subject[] sub, TimePeriod[] abs){
		Student tmp = new Student(name,sub,abs);		
		add(tmp);
		return tmp;
	}
	
	/**
	 * Adds the given student to the studentList.
	 * All students are organized in lexicographical order
	 */
	void add(Student std){
		for(int i=0;i<v.size();i++){
			//String im Vektor ist größer--> Einfügen in Vektor --> Elemente automatisch geshifted
			if(v.elementAt(i).getName().compareTo(std.getName())>0){
				v.insertElementAt(std, i);
				return;
			}
		}
		//wird nur erreicht, wenn das Element ganz am Schluss eingefügt werden muss 
		v.addElement(std);
		return;
	}
	
	/**
	 * this method tries to return the student at the given index
	 * @param index	int  
	 * @throws IllegalArgumentException An IllegalArgumentException is thrown if the given index was too high/low
	 */
	//EXCEPTION NOCH AN TESTER ANPASSEN
	public Student get(int index) throws IllegalArgumentException{
		if(index>=0 && index<v.size())
			return v.get(index);
		throw new IllegalArgumentException("Unvalid index. Tried to call get() in class Studentlist with index: "+index+". (StudentList holds at the moment "+v.size()+" students.)");
	}
	
	/**
	 * This method returns all students in this studentList as an array.
	 * The students in this array are in lexicographical order.	 
	 */
	public Student[] toArray(){
		Student[] res = new Student[v.size()];
		for(int i=0;i<v.size();i++){
			res[i]=v.get(i);
		}
		return res;
	}
	
	/**
	 * Checks if a student object with exactly the same name
	 * as the given student object (parameter) exists in this StudentList
	 * (Knauber handles case that students have the same name, so the same name means the same student)
	 * @param std A Student
	 * @return true if such a student already exists and false if not
	 */
	public boolean exists(Student std){
		for(int i=0;i<v.size();i++){
			//falls name gleich
			if(std.getName().equals(v.elementAt(i).getName())){
				return true;				
			}			
		}
		return false;
	}
	
	/**
	 * This method tries to delete the student at the given index from this studentList
	 * 
	 * @param index int
	 * @return true if the student at the given index could be deleted and false if not
	 */
	public boolean delete(int index){
		if(index>=0 && index<v.size() ){
			v.remove(index);
			return true;
		}
		return false;
	}
	
	/**
	 * Returns the amount of students in this studentList
	 */
	public int size(){
		return v.size();
	}

	@Override
	public Iterator<Student> iterator() {
		// TODO Auto-generated method stub
		return null;
	}
}
