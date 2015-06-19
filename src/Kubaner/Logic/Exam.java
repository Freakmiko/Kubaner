package Kubaner.Logic;

public class Exam implements TimeLineMember
{

	private String assessor;
	private int length;
	private Student stu;
	private Subject[] sub;
	private Professor[] prof;
	
	
	public Exam(Professor[] prof, Student stu, Subject[] sub, int length, String assessor)
	{
		this.prof = prof;
		this.stu = stu;
		this.sub  =sub;
		this.length = length;
		this.assessor = assessor;
	}
	
	
	@Override
	public int getLength() {
		return this.length;
	}
	
	public Student getStudent(){
		return this.stu;
	}
	
	public Subject[] getSubjectArray(){
		return this.sub;
	}
	
	public Professor[] getProfArray(){
		return this.prof;
	}
	
	public String getAssessor(){
		return this.assessor;
	}
	
	public void setAssessor(String ass){
		this.assessor = ass;
	}
	
	public boolean isDoubleExam(){
		return sub[0]!= null && sub[1]!= null;
	}
	
}
