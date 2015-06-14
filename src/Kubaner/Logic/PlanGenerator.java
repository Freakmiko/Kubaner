package Kubaner.Logic;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class PlanGenerator {
	
	private StudentList studentList;
	private SubjectList subjectList;
	private ProfList profList;
	
	
	public Plan generatePlan(Date startTime)
	{
		ExamList[] examLists = new ExamList[subjectList.size()];
		//create a list for each subject
		for(int i = 0; i < subjectList.size(); i++)
			examLists[i] = new ExamList(subjectList.get(i));
		
		//loop through all students and add them to every ExamList with the corresponding subject
		for(Student stud : studentList)
		{
			for(Subject sub : stud.getSubjectArray())
			{
				for(ExamList list : examLists)
				{
					if(list.getSubject() == sub)
						list.add(stud);
				}
			}
		}
		
		//sort the array
		sortArray(examLists);
		
		
		Plan plan = new Plan(startTime);
		
		for(ExamList exam: examLists)
		{
			TimeLine timeLine = new TimeLine();
			
			for(Student stud: exam)
			{
				
			}
		}
		
		return plan;
	}
	
	private void sortArray(ExamList[] array)
	{
		for(int j = array.length-1; j < 0; j-- ){
			for(int i = 0; i < j; i++){
				if(array[i].compareTo(array[i+1]) < 0){
					ExamList help = array[i+1];
					array[i+1] = array[i];
					array[i] = help;
				}	
			}
		}
	}
	
	
	private class ExamList implements Comparable<ExamList>, Iterable<Student>
	{
		private Subject subject;
		private List<Student> students;
		
		ExamList(Subject subject)
		{
			this.subject = subject;
			students = new ArrayList<Student>();
		}
		
		void add(Student student)
		{
			this.students.add(student);
		}
		
		Student get(int index)
		{
			return this.students.get(index);
		}
		
		int size()
		{
			return students.size();
		}
		
	    Subject getSubject()
	    {
	    	return this.subject;
	    }

		
		@Override
		public int compareTo(ExamList o)
		{	
			if(o.size() > this.size())
				return -1;
			else if(o.size() < this.size())
				return 1;
			else return 0;
		}

		@Override
		public Iterator<Student> iterator()
		{
			return this.students.iterator();
		}
	}

}
