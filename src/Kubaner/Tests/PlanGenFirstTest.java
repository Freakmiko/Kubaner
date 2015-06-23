package Kubaner.Tests;

import static org.junit.Assert.*;
import Kubaner.Logic.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.After;

public class PlanGenFirstTest {
	PlanGenerator alpha = new PlanGenerator();
	Plan plan;
	ProfList profList;
	SubjectList subList;
	StudentList stuList;
	
	//constructor for array containing every subject
	Subject[] allSubjectsArray = new Subject[5]; 
	
	//Kubaner has 2 subjects, Todorov has 1 subject, Schramm has 2 subjects
	Subject[] arraySubjectsKubaner = new Subject[2]; 
	Subject[] arraySubjectsTodorov = new Subject[1]; 
	Subject[] arraySubjectsSchramm = new Subject[2]; 
	
	//subject arrays for the new students
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
							   
		// the subjects        //position of subjects in allSubjectsArray, alphabetical order!
		subList.create("ADS"); // 0
		subList.create("ANA"); // 1
		subList.create("GDI"); // 2
		subList.create("OOT"); // 3
		subList.create("TPE"); // 4
		
		//writing all subjects of subList to allSubjectsArray
		allSubjectsArray = subList.toArray();
		
		//set duration of all exams to 10minutes
		for (int i = 0; i < allSubjectsArray.length; i++) {
			allSubjectsArray[i].setExamLength(10);}
		
		//Kubaner gets GDI and OOT
		arraySubjectsKubaner[0] = allSubjectsArray[2];
		arraySubjectsKubaner[1] = allSubjectsArray[3];
		//Todorov gets ANA
		arraySubjectsTodorov[0] = allSubjectsArray[1];
		//Schramm gets ADS and TPE
		arraySubjectsSchramm[0] = allSubjectsArray[0];
		arraySubjectsSchramm[1] = allSubjectsArray[4];
		
		//creating 5 students
		//student 1 ANA, OOT, TPE
		arraySubjectsStu1[0] = allSubjectsArray[1];
		arraySubjectsStu1[1] = allSubjectsArray[3];
		arraySubjectsStu1[2] = allSubjectsArray[4];
		//student 2 GDI, TPE
		arraySubjectsStu2[0] = allSubjectsArray[2];
		arraySubjectsStu2[1] = allSubjectsArray[4];
		//student 3 ADS, OOT, TPE, GDI
		arraySubjectsStu3[0] = allSubjectsArray[0];
		arraySubjectsStu3[1] = allSubjectsArray[3];
		arraySubjectsStu3[2] = allSubjectsArray[4];
		arraySubjectsStu3[3] = allSubjectsArray[2];
		//student 4 ANA
		arraySubjectsStu4[0] = allSubjectsArray[1];
		//student 5 ANA, GDI, OOT, TPE, ADS
		arraySubjectsStu5[0] = allSubjectsArray[0];
		arraySubjectsStu5[1] = allSubjectsArray[1];
		arraySubjectsStu5[2] = allSubjectsArray[2];
		arraySubjectsStu5[3] = allSubjectsArray[3];
		arraySubjectsStu5[4] = allSubjectsArray[4];
		
		//creating timeperiod
		//time 1 from 0:00 until 10:00
		TimePeriod[] time1 = new TimePeriod[] { new TimePeriod(new Time(0, 0),new Time(10, 0)) };

		
		profList.create("Kubaner", arraySubjectsKubaner, time1);
		profList.create("Todorov", arraySubjectsTodorov, time1);
		profList.create("Schramm", arraySubjectsSchramm, time1);
		stuList.create("Student 1", arraySubjectsStu1, time1);
		stuList.create("Student 2", arraySubjectsStu2, time1);
		stuList.create("Student 3", arraySubjectsStu3, time1);
		stuList.create("Student 4", arraySubjectsStu4, time1);
		stuList.create("Student 5", arraySubjectsStu5, time1);
		
		plan = alpha.generatePlan(new Time(10, 0));
	}

	@After
	public void reset() {
		plan = null;
		profList = null;
		subList = null;
		stuList = null;
	}

	@Test
	/**
	 * Check for occupancy of a room outside exam period
	 * @throws Exception
	 */
	public void RoomTimeLogicTest() throws Exception{
		Time[] timeArray = new Time[plan.getRoomCount()];
		for(int i = 0; i < plan.getRoomCount(); i++){
			timeArray[i] = new Time(10,0);
		}
		for (int i = 0; i < plan.getTimeLineNumber(); i++) {
			for (int j = 0; j < plan.getTimeLine(i).size(); j++) {
				timeArray[i] = timeArray[i].addMinutes(plan.getTimeLine(i).getTimeLineMember(j).getLength());
			}
		}
		for(int i = 0; i < plan.getRoomCount(); i++){
			if(timeArray[i].isLater(new Time(24,0))){
				fail();
			}
		}
	}
	
	
	
	
	@Test
	/**
	 * Check if professor has to attend multiple exams at the same time / overlapping exams
	 * @throws Exception
	 */
	public void ProfTimeLogicTest() throws Exception{
		//creating timearray containing every exam for each professor
		TimePeriod[][] timeArray = new TimePeriod[15][profList.size()];
		for (int k = 0; k < profList.size() ; k++){
			int m = 1;
			timeArray[0][k] = new TimePeriod(new Time(0, 0),new Time(10, 0));
			for (int i = 0; i < plan.getTimeLineNumber(); i++) {
				for (int j = 0; j < plan.getTimeLine(i).size(); j++) {
					try { // try to -cast- expected exam or break to exam
						Exam tmp = (Exam) plan.getTimeLine(i).getTimeLineMember(j);
						if(tmp.isDoubleExam() && ( tmp.getProfArray()[0].equals(profList.toArray()[k]) || tmp.getProfArray()[1].equals(profList.toArray()[k]) )){
							Time tmpTime = new Time(10,0);
							for (int l = 0; l < j; l++) {
								tmpTime = tmpTime.addMinutes(plan.getTimeLine(i).getTimeLineMember(l).getLength());
							}
							Time tmpTime2 = tmpTime;
							tmpTime2 = tmpTime2.addMinutes(tmp.getLength());
							timeArray[m][k] = new TimePeriod(tmpTime, tmpTime2);
							m++;
						}
						else {
							if(tmp.getProfArray()[0].equals(profList.toArray()[k])){
							Time tmpTime = new Time(10,0);
							for (int l = 0; l < j; l++) {
								tmpTime = tmpTime.addMinutes(plan.getTimeLine(i).getTimeLineMember(l).getLength());
							}
							Time tmpTime2 = tmpTime;
							tmpTime2 = tmpTime2.addMinutes(tmp.getLength());
							timeArray[m][k] = new TimePeriod(tmpTime, tmpTime2);
							m++;
							}
						}
						}
					catch (Exception e) {
					}				
				}
			}
		}
		//check for overlapping / multiple exams
		for(int i = 0; i < profList.size(); i++){
			for(int j =  0; j < 15; j++){
				if(timeArray[j][i]==null){
				}
				else {
					for(int k = 0; k < 15; k++){
						if(timeArray[k][i]==null){
						}
						else if(!(k==j)){
							if(timeArray[j][i].getStart().isEarlier(timeArray[k][i].getStart()) && timeArray[j][i].getStart().isEarlier(timeArray[k][i].getEnd()) && timeArray[j][i].getEnd().isEarlier(timeArray[k][i].getEnd()) && timeArray[j][i].getEnd().isEarlierOrEqual(timeArray[k][i].getStart())){	
							} else if(timeArray[j][i].getStart().isLater(timeArray[k][i].getStart()) && timeArray[j][i].getStart().isLaterOrEqual(timeArray[k][i].getEnd()) && timeArray[j][i].getEnd().isLater(timeArray[k][i].getEnd()) && timeArray[j][i].getEnd().isLater(timeArray[k][i].getStart())){
							} else fail("Prof: " + profList.toArray()[i].getName() + "\n" + "Start: " + timeArray[j][i].getStart().getHour() + ":" + timeArray[j][i].getStart().getMinute() + " Uhr" + "\n" +
									"Ende: "  + timeArray[j][i].getEnd().getHour() + ":" + timeArray[j][i].getEnd().getMinute() + " Uhr" + "\n" +
									"Start2: " + timeArray[k][i].getStart().getHour() + ":" + timeArray[k][i].getStart().getMinute() + " Uhr" + "\n" +
									"Ende2: "  + timeArray[k][i].getEnd().getHour() + ":" + timeArray[k][i].getEnd().getMinute() + " Uhr" + "\n");
						}
					}
				}
			}
		}
	}
	
	
	
	
	
	
	
	
	@Test
	/**
	 * Check for the quantity of used rooms
	 * @throws Exception
	 */
	public void RoomCountTest() throws Exception {
		assertEquals(5, plan.getRoomCount());
	}

	@Test
	/**
	 * Check for the quantity of created exams
	 * @throws Exception
	 */
	public void ExamCountTest() throws Exception {
		int counter = 0;
		for (int i = 0; i < plan.getTimeLineNumber(); i++) {
			for (int j = 0; j < plan.getTimeLine(i).size(); j++) {
				try { // try to -cast- expected exam or break to exam
					Exam tmp = (Exam) plan.getTimeLine(i).getTimeLineMember(j);
					// if it is an exam we can cast it and we -increment- the counter,
					if(tmp.isDoubleExam()){
						counter++;
						}
					counter++;
				}
				// if it's a break we -can't cast- it, so we catch the exception and -don't increment- the counter.
				catch (Exception e) {
				}
			}
		}
		// check if total number of exams matches count of subjects per student to test
		 int counter2 = 0;
		 for(int k = 0; k < stuList.toArray().length; k++){
		 counter2 += stuList.toArray()[k].getSubjectArray().length;
		 }
		 assertEquals(counter2, counter); 
	}

	@Test
	/**
	 * Check if the professor of an created exam can test this subject
	 * @throws Exception
	 */
	public void ProfExamTest() throws Exception {
		Professor profName = null;
		// move through every timeline
		for (int g = 0; g < plan.getTimeLineNumber(); g++) {
			for (int h = 0; h < plan.getTimeLine(g).size(); h++) {
				// cast from timeline to exam object
				try {
					Exam exam = (Exam) plan.getTimeLine(g).getTimeLineMember(h);
					// get name from professor of exam... (for comparison!)
					Professor tmp = (exam.getProfArray()[0]);
					
					// ...check if correct:
					for (int i = 0; i < allSubjectsArray.length; i++) {
						// if exam subject exists in arrayAll (contains all subjects)
						if (exam.getSubjectArray()[0].equals(allSubjectsArray[i])) {
							// search in professor list for...
							for (int j = 0; j < profList.toArray().length; j++) {
								// ...specific subjects of professor
								for (int k = 0; k < profList.toArray()[j].getSubjectArray().length; k++) {
									// if subject name in arrayAll at pos i equals subject of specific professor at pos j
									if (allSubjectsArray[i].equals(profList.toArray()[j].getSubjectArray()[k])) {
										// set profName as professor at pos j
										profName = profList.toArray()[j];
									}
								}
							}
						}
					}
					// check if professor can test this subject by comparing profname we got and profname we checked
					assertEquals(profName, tmp);
				} catch (Exception e) {
				}
			}
		}
	}

	@Test
	/**
	 * Check if student is tested in the right subject
	 * @throws Exception
	 */
	public void StudExamTest() throws Exception {
		Student stuName = null;
		
			// move through every timeline
			for (int g = 0; g < plan.getTimeLineNumber(); g++) {
				for (int h = 0; h < plan.getTimeLine(g).size(); h++) {
					// cast from timeline to exam object
					try {
					Exam exam = (Exam) plan.getTimeLine(g).getTimeLineMember(h);
					// get name of student from exam... (for comparison!)
					Student tmp = (exam.getStudent());
					
					// ...check if correct:
					for (int i = 0; i < allSubjectsArray.length; i++) {
						// if exam subject exists in arrayAll (contains all subjects)
						if (exam.getSubjectArray()[0].equals(allSubjectsArray[i])) {
							//search in student list for...
							for (int j = 0; j < stuList.toArray().length; j++) {
								//...the specific student, if found:
								if (stuList.toArray()[j].equals(exam.getStudent())) {
									//check subjects of student
									for (int k = 0; k < stuList.toArray()[j].getSubjectArray().length; k++) {
										//if there is a match...
										if (stuList.toArray()[j].getSubjectArray()[k].equals(exam.getSubjectArray()[0])) {
											//...set stuName as found name
											stuName = stuList.toArray()[j];
										}
									}
								}
							}
						}
					}
					// check if student is tested in this subject by comparing name of student of exam we got and student we checked
					assertEquals(stuName, tmp);
				} catch (Exception e) {
				}
			}
		}
	}
	
	@Test
	/**
	 * Check if there is a duplicate of an exam
	 * @throws Exception
	 */
	public void DoubleExamTest() throws Exception {
		//move through every timeline
		for (int g = 0; g < plan.getTimeLineNumber(); g++) {
			for (int h = 0; h < plan.getTimeLine(g).size(); h++) {
				try {
					Exam exam = (Exam) plan.getTimeLine(g).getTimeLineMember(h);
					for (int i = 0; i < plan.getTimeLineNumber(); i++) {
						for (int j = 0; j < plan.getTimeLine(i).size(); j++) {
							try {
								Exam tmp = (Exam) plan.getTimeLine(i).getTimeLineMember(j);
								//compares every exam with the others, if g / h matches i / j we have a double one, so the test fails
								if (exam.equals(tmp)&& plan.getTimeLine(g).getTimeLineMember(h) != plan.getTimeLine(i).getTimeLineMember(j)) {
									fail("double exam!");
								}
							} catch (Exception e) {
							}
						}
					}
				} catch (Exception f) {
				}
			}
		}
	}
	
	@Test
	/**
	 * Checks the length of an double exam
	 * @throws Exception
	 */
	public void DoubleExamTimeTest() throws Exception {
		for (int g = 0; g < plan.getTimeLineNumber(); g++) {
			for (int h = 0; h < plan.getTimeLine(g).size(); h++) {
				try {
					Exam exam = (Exam) plan.getTimeLine(g).getTimeLineMember(h);
					if(exam.isDoubleExam()){
						assertEquals(exam.getSubjectArray()[0].getExamLength()+exam.getSubjectArray()[1].getExamLength(),exam.getLength());
					}
					
				} catch (Exception f) {
				}
			}

		}
	}
	
	@Test
	/**
	 * Check if there is an assessor
	 * @throws Exception
	 */
	public void AssessorExistTest() throws Exception {
		for (int g = 0; g < plan.getTimeLineNumber(); g++) {
			for (int h = 0; h < plan.getTimeLine(g).size(); h++) {
				try {
					Exam exam = (Exam) plan.getTimeLine(g).getTimeLineMember(h);
						assertNotNull(exam.getAssessor());
				} catch (Exception f) {
				}
			}
		}
	}
	
	@Test
	/**
	 * Check if assessor == the testing professor
	 * @throws Exception
	 */
	public void AssessorProfTest() throws Exception {
		for (int g = 0; g < plan.getTimeLineNumber(); g++) {
			for (int h = 0; h < plan.getTimeLine(g).size(); h++) {
				try {
					Exam exam = (Exam) plan.getTimeLine(g).getTimeLineMember(h);
						assertNotEquals(exam.getProfArray()[0].getName(), exam.getAssessor());
				} catch (Exception f) {
				}
			}
		}
	}

	@Test
	/**
	 * Check if student is tested in all his subjects 
	 * by counting all his exams and comparing with the quantity of subjets
	 * @throws Exception
	 */
	public void StudAllExamTestedTest() throws Exception {
		for (int i = 0; i < stuList.size(); i++) {
			int counter = 0;
			Student student = stuList.get(i);
			for (int g = 0; g < plan.getTimeLineNumber(); g++) {
				for (int h = 0; h < plan.getTimeLine(g).size(); h++) {
					try {
						Exam exam = (Exam) plan.getTimeLine(g).getTimeLineMember(h);
						if (exam.getStudent().equals(student)) {
							for (int j = 0; j < student.getSubjectArray().length; j++) {
								if (exam.getSubjectArray()[0].equals(student.getSubjectArray()[j])) {
									counter++;
								}
								if (exam.isDoubleExam() && exam.getSubjectArray()[1].equals(student.getSubjectArray()[j])) {
									counter++;
								}
							}
						}
					} catch (Exception f) {
					}
				}
			}
			assertEquals(student.getSubjectArray().length, counter);
		}
	}
}