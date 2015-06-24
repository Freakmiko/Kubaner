package Kubaner.Logic;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PlanGenerator {

	private StudentList studentList;
	private SubjectList subjectList;
	private ProfList profList;

	public PlanGenerator() {
		studentList = new StudentList();
		subjectList = new SubjectList();
		profList = new ProfList();
	}

	public StudentList getStudentList() {
		return studentList;
	}

	public SubjectList getSubjectList() {
		return subjectList;
	}

	public ProfList getProfList() {
		return profList;
	}

	/**
	 * The argument plan will be stored in a file described by the argument url.
	 * But if plan is null, the current lists of this class will be stored. This
	 * concerns the following lists: studentList, subjectList, profList.
	 * 
	 * @param plan
	 *            - the plan to store.
	 * @param url
	 *            - the url of the file, where the data should be stored.
	 * @throws IOException
	 *             - if there are problems with the url.
	 */
	public void savePlan(Plan plan, String url) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(
				url));

		if (plan != null) {
			oos.writeObject(plan);
			oos.close();

			// save studentList, subjectList, profList
		} else {
			oos.writeObject(studentList);
			oos.writeObject(subjectList);
			oos.writeObject(profList);

			oos.close();
		}
	}

	/**
	 * The data stored in a file described by the argument url will be restored.
	 * If an instance of the class plan is stored in the file, the plan will be
	 * restored and the lists for this generator will be restored from the
	 * information stored in the plan. But maybe there is no plan in the file.
	 * In this case the program will try to restore the 3 lists directly from
	 * the file.
	 * 
	 * @param url
	 *            - the url of the file, where the data should be stored.
	 * @return Returns the plan restored from the file or null if there was no
	 *         plan stored in the file.
	 * @throws IOException
	 *             - if there are problems with the url.
	 */
	public Plan loadPlan(String url) throws IOException, ClassNotFoundException {
		Plan plan = null;
		Object obj;
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(url));

		obj = ois.readObject();

		if (obj != null)
			if (obj.getClass() == Plan.class) {
				plan = (Plan) obj;
				restoreLists(plan);
			} else {
				studentList = (StudentList) obj;
				subjectList = (SubjectList) ois.readObject();
				profList = (ProfList) ois.readObject();
			}

		ois.close();
		return plan;
	}

	private void restoreLists(Plan plan) {
		TimeLine timeline;
		TimeLineMember member;
		Exam exam;

		Student student;
		Subject subjects[];
		Professor prof[];

		for (int i = 0; i < plan.getTimeLineNumber(); i++) {
			timeline = plan.getTimeLine(i);

			for (int i2 = 0; i2 < timeline.size(); i2++) {
				member = timeline.getTimeLineMember(i2);

				if (member instanceof Exam) {
					exam = (Exam) member;
					subjects = exam.getSubjectArray();

					for (int i3 = 0; i3 < subjects.length; i3++) {
						this.subjectList.create(subjects[i3].getName());
					}

					student = exam.getStudent();
					this.studentList.create(student.getName(),
							student.getSubjectArray(),
							student.getTimePeriodArray());

					prof = exam.getProfArray();

					for (int i3 = 0; i3 < prof.length; i3++) {
						this.profList.create(prof[i3].getName(),
								prof[i3].getSubjectArray(),
								prof[i3].getTimePeriodArray());
					}
				}
			}
		}
	}

	/*
	 * Methods for the plan generation:
	 */

	/**
	 * Generates a new {@link Plan} based on the data which was given to the
	 * PlanManager.
	 * 
	 * @param startTime
	 *            A {@link Time} object which defines the start time of every
	 *            {@link TimeLine} in the plan.
	 * @return The generated plan object.
	 */
	public Plan generatePlan(Time startTime) {
		StudentPerSubjectList[] examLists = new StudentPerSubjectList[subjectList
				.size()];
		// create a list for each subject
		for (int i = 0; i < subjectList.size(); i++)
			examLists[i] = new StudentPerSubjectList(subjectList.get(i));

		// loop through all students and add them to every ExamList with the
		// corresponding subject
		for (Student stud : studentList) {
			for (Subject sub : stud.getSubjectArray()) {
				for (StudentPerSubjectList list : examLists) {
					if (list.getSubject() == sub)
						list.add(stud);
				}
			}
		}
		// sort the array
		sortArray(examLists);

		// create new plan object
		Plan plan = new Plan(startTime);

		// create a timeline for every subject
		int indexExam = 1;
		for (StudentPerSubjectList exam : examLists) {
			Professor prof = null;
			for (Professor professor : this.getProfList()) {
				for (Subject sub : professor.getSubjectArray()) {
					if (sub.getName().equals(exam.getSubject().getName())) {
						prof = professor;
					}
				}
			}

			TimeLine timeLine = new TimeLine();
			timeLine.setRoom("Room: " + indexExam);

			Time currentTime = new Time(startTime.getHour(), startTime.getMinute());

			// create an exam and optional a break for every student
			for (Student stud : exam) {
				Time oldTime = new Time(currentTime.getHour(), currentTime.getMinute());
				currentTime = getTimeWhenProfIsAvailable(prof, currentTime);

				// add a break if prof is unavailable
				int breakTime = oldTime.getMinutesBetween(currentTime);
				if (breakTime > 0) {
					timeLine.add(new Break(breakTime));
				}

				Subject[] subjectArray = { exam.subject, null };
				Professor[] profArray = { prof, null };

				// add the exam to the time line
				timeLine.add(new Exam(profArray, stud, subjectArray,
						exam.subject.getExamLength(), ""));
			}
			plan.add(timeLine);
			indexExam++;
		}
		mergeTimeLines(plan);	
		while (checkPropeties(plan));
		while(mergeBreakes(plan));
		
		for(int i = 0; i < plan.getRoomCount(); i++){
			TimeLine line = plan.getTimeLine(i);
			for(int j = 0; j < line.size(); j++){
				System.out.println(line.getTimeLineMember(j).getClass() + ": " + line.getTimeLineMember(j).getLength());
			}
		}
		return plan;
	}

	/**
	 * Merges the timelines of a plan.
	 * 
	 * @param plan
	 *            The plan of which timelines should be merged.
	 */
	private void mergeTimeLines(Plan plan) {
		for (int targetLineIndex = 0; targetLineIndex < plan
				.getTimeLineNumber(); targetLineIndex++) {
			TimeLine targetLine = plan.getTimeLine(targetLineIndex);

			// loop through every other timeline
			for (int sourceLineIndex = 0; sourceLineIndex < plan
					.getTimeLineNumber(); sourceLineIndex++) {
				if (sourceLineIndex != targetLineIndex) // if Timelines are not
														// identical
				{
					TimeLine sourceLine = plan.getTimeLine(sourceLineIndex);

					Time targetExamStartTime = new Time(plan.getStartTime().getHour(), plan.getStartTime().getMinute());

					for (TimeLineMember targetMember : targetLine) {
						if (targetMember.getClass() == Exam.class) {
							Exam targetExam = (Exam) targetMember;
							if (targetExam.isDoubleExam())
								continue;

							for (int sourceMemberIndex = 0; sourceMemberIndex < sourceLine
									.size(); sourceMemberIndex++) {
								if (sourceLine.getTimeLineMember(
										sourceMemberIndex).getClass() == Exam.class) {
									Exam sourceExam = (Exam) sourceLine
											.getTimeLineMember(sourceMemberIndex);
									if (sourceExam.getStudent() == targetExam
											.getStudent()
											&& !sourceExam.isDoubleExam()
											&& sourceExam.getProfArray()[0]
													.isAvailable(targetExamStartTime)) {

										// copy prof and subject from source to
										// target exam.
										targetExam.getSubjectArray()[1] = sourceExam
												.getSubjectArray()[0];

										targetExam.getProfArray()[1] = sourceExam
												.getProfArray()[0];

										targetExam.setLength(targetExam
												.getLength()
												+ sourceExam.getLength());

										// replace the source exam with a break
										sourceLine.insert(
												sourceMemberIndex,
												new Break(targetExam.getLength()));
										sourceLine.delete(sourceMemberIndex+1);
										
									}
								}
							}
						}

						targetExamStartTime = targetExamStartTime
								.addMinutes(targetMember.getLength());
					}

				}

			}

		}
		removeEmptyTimeLines(plan);
	}

	public boolean mergeBreakes(Plan plan) {
		boolean change = false;
		for (int timelines = 0; timelines < plan.getRoomCount(); timelines++) {
			TimeLine line = plan.getTimeLine(timelines);
			for (int j = 0; j < line.size() - 1; j++) {
				if (line.getTimeLineMember(j).getClass() == Break.class
						&& line.getTimeLineMember(j + 1).getClass() == Break.class) {
					line.getTimeLineMember(j)
							.setLength(
									line.getTimeLineMember(j).getLength()
											+ line.getTimeLineMember(j + 1)
													.getLength());
					line.delete(j + 1);
					change = true;
				}
			}
		}
		return change;
	}

	/**
	 * Removes the empty TimeLines of a plan object.
	 * 
	 * @param plan
	 */
	private void removeEmptyTimeLines(Plan plan) {
		int index = 0;
		while (index < plan.getTimeLineNumber()) {
			if (!plan.getTimeLine(index).containsExams()) {
				plan.removeTimeLine(index);
			} else {
				index++;
			}
		}
	}

	/**
	 * Finds the first time, starting at a specific time, when a
	 * {@link Professor} is available.
	 * 
	 * @param prof
	 * @param startTime
	 *            A {@link Time} object which define the start time the search
	 *            should begin.
	 * @return The end time of the {@link TimePeriod} when the prof is
	 *         unavailable.
	 */
	private Time getTimeWhenProfIsAvailable(Professor prof, Time startTime) {
		if (prof.getTimePeriodArray().length == 0) {
			return startTime;
		}
		for (TimePeriod period : prof.getTimePeriodArray()) {
			if (period.laysBetween(startTime))
				return startTime;
			else if (period.getStart().isLater(startTime)) {
				return period.getStart();
			}
		}
		throw new IllegalArgumentException("Prof ist nie da");
	}

	/**
	 * Sorts an array of {@link StudentPerSubjectList, StudentPerSubjectLists}
	 * after the length of every list.
	 * 
	 * @param array
	 *            The array which should be sorted.
	 */
	private void sortArray(StudentPerSubjectList[] array) {
		for (int j = array.length - 1; j < 0; j--) {
			for (int i = 0; i < j; i++) {
				if (array[i].compareTo(array[i + 1]) < 0) {
					StudentPerSubjectList help = array[i + 1];
					array[i + 1] = array[i];
					array[i] = help;
				}
			}
		}
	}
	
	private boolean checkPropeties(Plan plan){
		boolean change = false;
		for(int indexOfTimeLine = 0; indexOfTimeLine < plan.getTimeLineNumber(); indexOfTimeLine++){
			TimeLine line = plan.getTimeLine(indexOfTimeLine);
			Time currentStartTime = new Time(plan.getStartTime().getHour(), plan.getStartTime().getMinute());
			Time currentEndTime= new Time(plan.getStartTime().getHour(), plan.getStartTime().getMinute());
			for(int indexOfExam = 0; indexOfExam < line.size(); indexOfExam++ ){
				currentEndTime.addMinutes(line.getTimeLineMember(indexOfExam).getLength());
				if(line.getTimeLineMember(indexOfExam).getClass()==Break.class){
					currentStartTime.addMinutes(line.getTimeLineMember(indexOfExam).getLength());
					continue;
				}
				Exam currentExam = (Exam)line.getTimeLineMember(indexOfExam);
				for(int indexOfSecondTimeline = indexOfTimeLine+1; indexOfSecondTimeline < plan.getTimeLineNumber(); indexOfSecondTimeline++){
					TimeLine secondTimeLine = plan.getTimeLine(indexOfSecondTimeline);
					Time secondStartTime = new Time(plan.getStartTime().getHour(), plan.getStartTime().getMinute());
					Time secondEndTime = new Time(plan.getStartTime().getHour(), plan.getStartTime().getMinute());
					for(int i = 0; i < secondTimeLine.size(); i++){
						secondEndTime.addMinutes(secondTimeLine.getTimeLineMember(i).getLength());
						if(secondTimeLine.getTimeLineMember(i).getClass() == Exam.class){
							if(new TimePeriod(secondStartTime, secondEndTime ).overlaying(new TimePeriod(currentStartTime, currentEndTime))){
								Exam secondExam = (Exam)secondTimeLine.getTimeLineMember(i);
								if (secondExam.getStudent() == currentExam
										.getStudent()) {
									plan.getTimeLine(indexOfSecondTimeline).insert(i,
											new Break(currentExam.getLength()));
									i++;
									change = true;
								} else if (secondExam.getProfArray()[0] == currentExam.getProfArray()[0]
												|| (secondExam.getProfArray()[1] == currentExam.getProfArray()[1]&&secondExam.getProfArray()[1]!= null)
												|| secondExam.getProfArray()[0] == currentExam.getProfArray()[1]
												|| secondExam.getProfArray()[1] == currentExam.getProfArray()[0]) {
								plan.getTimeLine(indexOfSecondTimeline).insert(i,
										new Break(currentExam.getLength()));
								i++;
								change = true;
								System.out.println(currentStartTime.getMinutesBetween(secondEndTime));
								}
							}
							else if(secondStartTime.isLater(currentEndTime)){
								break;
							}
						}
						secondStartTime.addMinutes(secondTimeLine.getTimeLineMember(i).getLength());
					}
				}
				currentStartTime.addMinutes(currentExam.getLength());
			}
		}
		return change;
	}

	/**
	 * An internal helper class which stores the students which are tested in
	 * the same subject.
	 * 
	 * @author Max & Max
	 *
	 */
	private class StudentPerSubjectList implements
			Comparable<StudentPerSubjectList>, Iterable<Student> {
		private Subject subject;
		private List<Student> students;

		StudentPerSubjectList(Subject subject) {
			this.subject = subject;
			students = new ArrayList<Student>();
		}

		void add(Student student) {
			this.students.add(student);
		}

		int size() {
			return students.size();
		}

		Subject getSubject() {
			return this.subject;
		}

		@Override
		public int compareTo(StudentPerSubjectList o) {
			if (o.size() > this.size())
				return -1;
			else if (o.size() < this.size())
				return 1;
			else
				return 0;
		}

		@Override
		public Iterator<Student> iterator() {
			return this.students.iterator();
		}
	}

}