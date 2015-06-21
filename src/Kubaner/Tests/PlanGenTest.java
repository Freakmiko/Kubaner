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
	Subject[] allSubjectsArray = new Subject[5]; // constructor for array
													// containing every subject
	Subject[] arraySubjectsKubaner = new Subject[2]; // kubaner subject array (1
														// subject)
	Subject[] arraySubjectsTodorov = new Subject[1]; // todorov subject array (2
														// subjects)
	Subject[] arraySubjectsSchramm = new Subject[2]; // schramm subject array (2
														// subjects)
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
		subList.create("ADS"); // 0
		subList.create("ANA"); // 1
		subList.create("TPE"); // 2
		subList.create("GDI"); // 3
		subList.create("OOT"); // 4

		// creating array containing all subjects
		allSubjectsArray = subList.toArray();

		// set duration of all exams to 20minutes
		for (int i = 0; i < allSubjectsArray.length; i++) {
			allSubjectsArray[i].setExamLength(20);
		}

		arraySubjectsKubaner[0] = allSubjectsArray[4];
		arraySubjectsKubaner[1] = allSubjectsArray[3];
		arraySubjectsTodorov[0] = allSubjectsArray[1];
		arraySubjectsSchramm[0] = allSubjectsArray[0];
		arraySubjectsSchramm[1] = allSubjectsArray[2];
		arraySubjectsStu1[0] = allSubjectsArray[1];
		arraySubjectsStu1[1] = allSubjectsArray[3];
		arraySubjectsStu1[2] = allSubjectsArray[4];
		arraySubjectsStu2[0] = allSubjectsArray[2];
		arraySubjectsStu2[1] = allSubjectsArray[4];
		arraySubjectsStu3[0] = allSubjectsArray[0];
		arraySubjectsStu3[1] = allSubjectsArray[3];
		arraySubjectsStu3[2] = allSubjectsArray[4];
		arraySubjectsStu3[3] = allSubjectsArray[2];
		arraySubjectsStu4[0] = allSubjectsArray[1];
		arraySubjectsStu5[0] = allSubjectsArray[0];
		arraySubjectsStu5[1] = allSubjectsArray[1];
		arraySubjectsStu5[2] = allSubjectsArray[2];
		arraySubjectsStu5[3] = allSubjectsArray[3];
		arraySubjectsStu5[4] = allSubjectsArray[4];

		TimePeriod[] time1 = new TimePeriod[] { new TimePeriod(new Time(0, 0),
				new Time(10, 0)) };
		TimePeriod[] time2 = time1;

		profList.create("Kubaner", arraySubjectsKubaner, time1);
		profList.create("Todorov", arraySubjectsTodorov, time1);
		profList.create("Schramm", arraySubjectsSchramm, time1);
		stuList.create("Student 1", arraySubjectsStu1, time2);
		stuList.create("Student 2", arraySubjectsStu2, time2);
		stuList.create("Student 3", arraySubjectsStu3, time2);
		stuList.create("Student 4", arraySubjectsStu4, time2);
		stuList.create("Student 5", arraySubjectsStu5, time2);
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
	public void RoomCountTest() throws Exception {
		assertEquals(1, plan.getRoomCount());
	}

	@Test
	public void ExamCountTest() throws Exception {
		int counter = 0;
		for (int i = 0; i < plan.getTimeLineNumber(); i++) {
			for (int j = 0; j < plan.getTimeLine(i).size(); j++) {
				try { // try to -cast- expected exam or break to exam
					@SuppressWarnings("unused")
					Exam tmp = (Exam) plan.getTimeLine(i).getTimeLineMember(j);
					// if it is an exam we can cast it and we -increment- the
					// counter,
					counter++;
				}
				// if it's a break we -can't cast- it, so we catch the exception
				// and -don't increment- the counter.
				catch (Exception e) {
				}
			}
		}
		assertEquals(15, counter);
	}

	@Test
	public void ProfExamTest() throws Exception {
		String profName = "";
		try {
			// move through every timeline
			for (int g = 0; g < plan.getTimeLineNumber(); g++) {
				for (int h = 0; h < plan.getTimeLine(g).size(); h++) {
					// cast from timeline to exam object
					Exam exam = (Exam) plan.getTimeLine(g).getTimeLineMember(h);
					// get name from professor of exam...
					String tmp = (exam.getProfArray()[0].getName());
					// ...check if correct:
					for (int i = 0; i < allSubjectsArray.length; i++) {
						// if exam subject exists in arrayAll (contains all
						// subjects)
						if (exam.getSubjectArray()[0]
								.equals(allSubjectsArray[i])) {
							// check profList for...
							for (int j = 0; j < profList.toArray().length; j++) {
								// ...specific subjects of professor
								for (int k = 0; k < profList.toArray()[j]
										.getSubjectArray().length; k++) {
									// if subject name in arrayAll at pos i
									// equals subject of specific professor at
									// pos j
									if (allSubjectsArray[i].equals(profList
											.toArray()[j].getSubjectArray()[k])) {
										// set profName as professor at pos j
										profName = profList.toArray()[j]
												.getName();
									}
								}
							}
						}
					}
					// check if professor can test this subject by comparing
					// profname we got and profname we checked
					assertEquals(profName, tmp);
				}
			}
		} catch (Exception e) {
		}
	}

	@Test
	public void StudExamTest() throws Exception {
		String stuName = "";
		try {
			// move through every timeline
			for (int g = 0; g < plan.getTimeLineNumber(); g++) {
				for (int h = 0; h < plan.getTimeLine(g).size(); h++) {
					// cast from timeline to exam object
					Exam exam = (Exam) plan.getTimeLine(g).getTimeLineMember(h);
					// get name of student from exam...
					String tmp = (exam.getStudent().getName());
					// ...check if correct:
					for (int i = 0; i < allSubjectsArray.length; i++) {

						if (exam.getSubjectArray()[0]
								.equals(allSubjectsArray[i])) {

							for (int j = 0; j < stuList.toArray().length; j++) {

								if (stuList.toArray()[j].equals(exam
										.getStudent())) {

									for (int k = 0; k < stuList.toArray()[j]
											.getSubjectArray().length; k++) {

										if (stuList.toArray()[j]
												.getSubjectArray()[k]
												.equals(exam.getSubjectArray()[0])) {

											stuName = stuList.toArray()[j]
													.getName();
										}
									}
								}
							}
						}
					}
					assertEquals(stuName, tmp);
				}
			}
		} catch (Exception e) {
		}
	}

	@Test
	public void DoubleExamTest() throws Exception {

		for (int g = 0; g < plan.getTimeLineNumber(); g++) {
			for (int h = 0; h < plan.getTimeLine(g).size(); h++) {
				try {
					Exam exam = (Exam) plan.getTimeLine(g).getTimeLineMember(h);
					for (int i = 0; i < plan.getTimeLineNumber(); i++) {
						for (int j = 0; j < plan.getTimeLine(i).size(); j++) {
							try {
								Exam tmp = (Exam) plan.getTimeLine(i)
										.getTimeLineMember(j);
								if (exam.equals(tmp)
										&& plan.getTimeLine(g)
												.getTimeLineMember(h) != plan
												.getTimeLine(i)
												.getTimeLineMember(j)) {
									fail("double!");
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

}
