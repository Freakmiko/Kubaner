package Kubaner.Logic;

public class Exam implements TimeLineMember {

	String assessor;
	int length;
	Student stu;
	Subject sub;
	Professor prof;
		
	@Override
	public int getLength() {
		return this.length;
	}
	
	public Student getStudent(){
		return this.stu;
	}
	
	public Subject getSubject(){
		return this.sub;
	}
	
	public Professor getProfessor(){
		return this.prof;
	}
	
	public String getAssessor(){
		return this.assessor;
	}
	
	public void setAssessor(String ass){
		this.assessor = ass;
	}
	
}
