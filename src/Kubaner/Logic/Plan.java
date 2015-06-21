package Kubaner.Logic;

import java.util.ArrayList;

public class Plan {
	private Time startTime;
	private TimeLine timeline[];
	
	Plan(Time startTime) {
		this.startTime = startTime;
		this.timeline = new TimeLine[0];
	}
	
	
	/**
	 * Extends the timeline-array by 1 and adds a new timeline in the new last field.
	 * @param newTimeLine - timeline to be added.
	 */
	public void add(TimeLine newTimeline) {
		TimeLine oldTimeLine[] = this.timeline;
		
		this.timeline = new TimeLine[this.timeline.length + 1];
		
		for(int i = 0; i < oldTimeLine.length; i++) {
			this.timeline[i] = oldTimeLine[i];
		}
		
		this.timeline[oldTimeLine.length] = newTimeline;
	}
	
	
	/**
	 * Returns the number of timelines stored in this plan.
	 * @return the number of timelines stored in this plan.
	 */
	public int getTimeLineNumber() {
		return this.timeline.length;
	}
	
	
	/**
	 * Returns the timeline stored at the argument index.
	 * @return TimeLine the timeline stored at the argument index.
	 */
	public TimeLine getTimeLine(int index) {
		return this.timeline[index];
	}
	
	
	/**
	 * Returns the number of the timelines as room count.
	 * @return The room count.
	 */
	//room count is here number of timelines
	public int getRoomCount() {
		return timeline.length;
	}
	
	
	/**
	 * Creates a new TimeLine.
	 * @param room - the room for the timeline
	 * @return TimeLine the created TimeLine.
	 */
	public TimeLine createNewTimeLine(String room) {
		TimeLine timeline = new TimeLine();
		
		timeline.setRoom(room);
		return timeline;
	}
	
	
	/**
	 * Moves a timeline member from one to another timeline. If the argument timelines are the same,
	 * the timeline member will be moved from the old to the new index within the timeline.
	 * @param oldLine - the timeline with the timeline member to be moved.
	 * @param oldIndex - the index of the member to be moved.
	 * @param newLine - the timeline where the timeline member gets inserted.
	 * @param newIndex - the index, where the member gets inserted.
	 * @throws IllegalArgumentException - if a TimeLine is null or the index's are not compatible with the timelines.
	 */
	public void moveBetweenTimelines(TimeLine oldLine, int oldIndex, TimeLine newLine, int newIndex) throws IllegalArgumentException {
		int oldLineIndex = -1;
		int newLineIndex = -1;
		
		TimeLineMember member;
		
		if(oldLine == null || newLine == null)
			throw new IllegalArgumentException("Timeline argument was null.");
		
		for(int i = 0; i < timeline.length; i++) {
			if(timeline[i] == oldLine) {
				oldLineIndex = i;
			}
			
			if(timeline[i] == newLine) {
				newLineIndex = i;
			}
		}
		
		
		//if one of the argument lines does not exist
		if(oldLineIndex == -1 || newLineIndex == -1) {
			throw new IllegalArgumentException("Incompatible index.");
		}
		
		
		//special case - movePeriod within timeline
		if(oldLine == newLine && oldIndex != newIndex) {
			//oldLineIndex == newLineIndex
			timeline[oldLineIndex].moveMember(oldIndex, newIndex);
		}
		
		
		member = timeline[oldLineIndex].getTimeLineMember(oldIndex);
		timeline[oldLineIndex].delete(oldIndex);
		
		timeline[newLineIndex].insert(newIndex, member);
	}
	
	
	/**
	 * Creates a MasterModel representing the master plan, using the values which are actually stored in this class.
	 * @return the created MasterModel.
	 */
	public MasterModel createAbstractTableModel() {
		int rows = 0;
		int cols;
		
		StudentList studentList = new StudentList();
		SubjectList subjectList = new SubjectList();
		ProfList profList = new ProfList();
		
		ArrayList<Time[]> startTimeList = new ArrayList<Time[]>();
		MasterModel master;
		Object value;
		Time actualTime;
		
		TimeLineMember member;
		Exam exam;
		
		Student student;
		Subject subjects[];
		Professor prof[];
		
		
		//get nearly all information stored in the plan to work with it
		for(int i = 0; i < timeline.length; i++) {
			for(int i2 = 0; i2 < timeline[i].size(); i2++) {
				member = timeline[i].getTimeLineMember(i2);
				
				if(member instanceof Exam) {
					exam = (Exam)member;
					subjects = exam.getSubjectArray();
					
					for(int i3 = 0; i3 < subjects.length; i3++)
						if(subjects[i3] != null && subjectList.exist(subjects[i3]) == false)
							subjectList.add(subjects[i3]);
					
					student = exam.getStudent();
					studentList.create(student.getName(), student.getSubjectArray(), student.getTimePeriodArray());
					
					prof = exam.getProfArray();
					
					for(int i3 = 0; i3 < prof.length; i3++)
						if(prof[i3] != null && profList.exists(prof[i3]) == false)
							profList.add(prof[i3]);
				}
			}
		}
		
		//generate startTimeList
		startTimeList = createTimeList();
		
		//calculate master model size
		rows = calculateRows(startTimeList);
		
		//+1 because first column stores start times
		cols = subjectList.size() + 1;
		
		master = new MasterModel(rows, cols);
		actualTime = new Time(startTime.getHour(), startTime.getMinute());
		
		
		for(int i = 0; i < cols; i++) {
			for(int i2 = 0; i2 < rows; i2++) {
				//first column: start times
				if(i == 0) {
					if(i2 > 0) {
						if(i2 > 1)
							actualTime = getNextStartTime(actualTime, startTimeList);
						//we will need the times to calculate other values in table model
						//so don't store it as String yet
						value = actualTime;
					} else
						value = "";
					
				//column > 0
				} else {
					if(i2 == 0) {
						value = subjectList.get(i - 1).getName();
						
						for(int i3 = 0; i3 < profList.size(); i3++) {
							subjects = profList.get(i3).getSubjectArray();
							
							for(int i4 = 0; i4 < subjects.length; i4++) {
								if(subjects[i4].getName() == subjectList.get(i - 1).getName())
									value += " - " + profList.get(i3).getName();
							}
						}	
					
					//row > 0
					} else {
						//value at i2, i is still an instance of the class Time
						exam = getExamAtTime((Time)master.getValueAt(i2, 0), subjectList.get(i - 1), startTimeList);
						
						if(exam != null) {
							value = "" + exam.getStudent().getName() + "   " + findExamRoom(exam);
						} else {
							value = "";
						}
					}
						
				}
				
				master.setValueAt(value, i2, i);
			}
		}
		
		//convert times from Time to String
		for(int i = 1; i < rows; i++) {
			value = master.getValueAt(i, 0);
			if(value != null && value instanceof Time) {
				Time time = (Time)value;
				if(time.getMinute() < 10)
					value = "" + time.getHour() + ":0" + time.getMinute();
				else
					value = "" + time.getHour() + ":" + time.getMinute();
				master.setValueAt(value, i, 0);
			}
		}
		
		return master;
	}
	
	
	/**
	 * Creates and returns an ArrayList<Time[]> which stores array with startTimes of every TimeLine object stored in timeline[].
	 * @return Returns an ArrayList<Time[]> which stores array with startTimes of every TimeLine object stored in timeline[].
	 */
	private ArrayList<Time[]> createTimeList() {
		ArrayList<Time[]> list = new ArrayList<Time[]>();
		Time[] timeArray;
		
		int nextDuration = 0;
		
		Time actualTime;
		
		for(int i = 0; i < timeline.length; i++) {
			actualTime = new Time(startTime.getHour(), startTime.getMinute());
			timeArray = new Time[timeline[i].size()];
			
			timeArray[0] = new Time(startTime.getHour(), startTime.getMinute());
			
			//i2 < timeline[i].size() - 1, we don't need the length of the last one
			for(int i2 = 0; i2 < timeline[i].size() - 1; i2++) {
				nextDuration = timeline[i].getTimeLineMember(i2).getLength();
				actualTime = actualTime.addMinutes(nextDuration);
				timeArray[i2 + 1] = new Time(actualTime.getHour(), actualTime.getMinute());
			}
			list.add(timeArray);
		}
		return list;
	}
	
	
	/**
	 * Determines and returns the next starting time of the argument startTimeList.
	 * @return Returns the next starting time of the argument startTimeList
	 * 			or null if there is no start time later than priorTime.
	 */
	private Time getNextStartTime(Time priorTime, ArrayList<Time[]> startTimeList) {
		Time nextTime = null;
		
		Time actualTime;
		
		for(int i = 0; i < startTimeList.size(); i++) {
			for(int i2 = 0; i2 < startTimeList.get(i).length; i2++) {
				actualTime = startTimeList.get(i)[i2];
				
				if(priorTime == null || actualTime.isLater(priorTime)) {
					if(nextTime == null || actualTime.isEarlier(nextTime))
						nextTime = actualTime;
					break;
				}
			}
		}
		
		if(nextTime == priorTime)
			return null;
		
		return nextTime;
	}
	
	
	/**
	 * Calculates the number of rows the master model should have.
	 * @return Returns the number of rows the master model should have.
	 */
	private int calculateRows(ArrayList<Time[]> startTimeList) {
		int rows = 1;
		
		Time time = null;
		
		 do {
			time = getNextStartTime(time, startTimeList);
			
			if(time != null)
					rows++;
		} while(time != null);
		return rows;
	}
	
	
	/**
	 * Searches for the Exam, which is taking place at a certain time. The subject of this exam is needed
	 * to find the right exam, because there could be more exams at the same time.
	 * @return The found exam or null if no exam was found.
	 */
	private Exam getExamAtTime(Time time, Subject sub, ArrayList<Time[]> startTimeList) {
		TimeLineMember member;
		Exam exam;
		
		Subject[] subjects;
		
		for(int i = 0; i < timeline.length; i++) {
			for(int i2 = 0; i2 < timeline[i].size(); i2++) {
				member = timeline[i].getTimeLineMember(i2);
				if(member instanceof Exam) {
					exam = (Exam)member;
					subjects = exam.getSubjectArray();
					
					for(int i3 = 0; i3 < subjects.length; i3++)
						if(subjects[i3] != null && subjects[i3].getName().equals(sub.getName())) {
							if(startTimeList.get(i)[i2].getHour() == time.getHour() && startTimeList.get(i)[i2].getMinute() == time.getMinute()) {
								return exam;
							}
						}
				}
			}
		}
		return null;
	}
	
	
	/**
	 * Searches for the room, in which the argument exam is taking place.
	 * @return The room name, or "" if no room was found.
	 */
	private String findExamRoom(Exam exam) {
		for(int i = 0; i < timeline.length; i++) {
			for(int i2 = 0; i2 < timeline[i].size(); i2++) {
				if(timeline[i].getTimeLineMember(i2) == exam)
					return timeline[i].getRoom();
			}
		}
		return "";
	}
	
	
	public void removeTimeLine(int index){
		if(0 <= index && index < timeline.length) {
			TimeLine newTimeline[] = new TimeLine[timeline.length - 1];
		
			for(int i = 0; i < newTimeline.length; i++) {
				if(i >= index) {
					newTimeline[i] = timeline[i + 1];
				}
				else{
					newTimeline[i] = timeline[i];
				}
			}
			this.timeline = newTimeline;
		}
	}


	public Time getStartTime() {
		return startTime;
	}
	
}
