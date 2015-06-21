package Kubaner.Tests;

import static org.junit.Assert.*;
import Kubaner.Logic.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.After;

public class PlanGenTest {
	PlanGenerator alpha = new PlanGenerator();
	Plan plan;
	ProfList profList;
	SubjectList subList;
	StudentList stuList;
    Subject[] subjectAllArray = new Subject[5]; //array mit allen fächern
    Subject[] arraySubjectsKubaner = new Subject[2]; //kubaner fach array (1 fach)
    Subject[] arraySubjectsTodorov = new Subject[1]; //todorov fach array (2 fächer)
    Subject[] arraySubjectsSchramm = new Subject[2]; //schramm fach array (2 fächer)
    Subject[] arraySubjectsStu1 = new Subject[3];
    Subject[] arraySubjectsStu2 = new Subject[2];
    Subject[] arraySubjectsStu3 = new Subject[4];
    Subject[] arraySubjectsStu4 = new Subject[1];
    Subject[] arraySubjectsStu5 = new Subject[5];
    

    @Before
    public void setup() {
    	profList = alpha.getProfList();
		subList = alpha.getSubjectList();
		stuList = alpha.getStudentList();
        subList.create("ADS"); //0
        subList.create("ANA"); //1
        subList.create("TPE"); //2
        subList.create("GDI"); //3
        subList.create("OOT"); //4
        subjectAllArray = subList.toArray();
        for (int i = 0; i < subjectAllArray.length; i++ ){
        subjectAllArray[i].setExamLength(20);}
        arraySubjectsKubaner[0] = subjectAllArray[4];
        arraySubjectsKubaner[1] = subjectAllArray[3];
        arraySubjectsTodorov[0] = subjectAllArray[1];
        arraySubjectsSchramm[0] = subjectAllArray[0];
        arraySubjectsSchramm[1] = subjectAllArray[2];
        arraySubjectsStu1[0] = subjectAllArray[1];
        arraySubjectsStu1[1] = subjectAllArray[3];
        arraySubjectsStu1[2] = subjectAllArray[4];
        arraySubjectsStu2[0] = subjectAllArray[2];
        arraySubjectsStu2[1] = subjectAllArray[4];
        arraySubjectsStu3[0] = subjectAllArray[0];
        arraySubjectsStu3[1] = subjectAllArray[3];
        arraySubjectsStu3[2] = subjectAllArray[4];
        arraySubjectsStu3[3] = subjectAllArray[2];
        arraySubjectsStu4[0] = subjectAllArray[1];
        arraySubjectsStu5[0] = subjectAllArray[0];
        arraySubjectsStu5[1] = subjectAllArray[1];
        arraySubjectsStu5[2] = subjectAllArray[2];
        arraySubjectsStu5[3] = subjectAllArray[3];
        arraySubjectsStu5[4] = subjectAllArray[4];
		TimePeriod[] time1 = new TimePeriod[] {new TimePeriod(new Time(0,0), new Time(10,0))};
		TimePeriod[] time2 = time1;
        profList.create("Kubaner", arraySubjectsKubaner,time1);
        profList.create("Todorov", arraySubjectsTodorov,time1);
        profList.create("Schramm", arraySubjectsSchramm,time1);
        stuList.create("Student 1", arraySubjectsStu1,time2);
        stuList.create("Student 2", arraySubjectsStu2,time2);
        stuList.create("Student 3", arraySubjectsStu3,time2);
        stuList.create("Student 4", arraySubjectsStu4,time2);
        stuList.create("Student 5", arraySubjectsStu5,time2);
        plan = alpha.generatePlan(new Time(10,0));
        
    }
    
    @After
	public void reset() {
    	plan = null;
    	profList = null;
   		subList = null;
		stuList = null;
	}
    
	@Test
	public void RoomCountTest() throws Exception {
        assertEquals(1,plan.getRoomCount());
	}
	
	@Test
	public void ExamCountTest() throws Exception {
		int counter = 0;
			for(int i = 0; i < plan.getTimeLineNumber(); i++){
				for(int j = 0; j < plan.getTimeLine(i).size(); j++){
					try{ 
					Exam tmp = (Exam) plan.getTimeLine(i).getTimeLineMember(j);
					if(tmp.getStudent().getName()!=null){
						counter++;
					}
					}
					catch (Exception e){
					}
				}
			}
        assertEquals(15,counter);
	}
	
	@Test
	public void StudExamTest() throws Exception {
		String stuName = "";
		Exam exam = (Exam) plan.getTimeLine(0).getTimeLineMember(0);
		String tmp = (exam.getStudent().getName());
		for(int i = 0; i < subjectAllArray.length; i++){
			if(exam.getSubjectArray()[0].getName().equals(subjectAllArray[i].getName())){
				for(int j = 0; j < stuList.toArray().length; j++){
					if(stuList.toArray()[j].equals(exam.getStudent())){
						for(int k = 0; k < stuList.toArray()[j].getSubjectArray().length; k++){
							if(stuList.toArray()[j].getSubjectArray()[k].equals(exam.getSubjectArray()[0])){
								stuName = stuList.toArray()[j].getName();
							}
						}
					}

				}
				
			}
		}
        assertEquals(stuName,tmp);
	}
	
	@Test
	public void ProfExamTest() throws Exception {
		String profName = "";
		Exam exam = (Exam) plan.getTimeLine(0).getTimeLineMember(0);
		String tmp = (exam.getProfArray()[0].getName());
		for(int i = 0; i < subjectAllArray.length; i++){
			if(exam.getSubjectArray()[0].getName().equals(subjectAllArray[i].getName())){
				for(int j = 0; j < profList.toArray().length; j++){
					for(int k = 0; k < profList.toArray()[j].getSubjectArray().length; k++){
						if(subjectAllArray[i].getName().equals(profList.toArray()[j].getSubjectArray()[k].getName())){
							profName = profList.toArray()[j].getName();
						}
					}
				}
				
			}
		}
        assertEquals(profName,tmp);
	}
	

}

