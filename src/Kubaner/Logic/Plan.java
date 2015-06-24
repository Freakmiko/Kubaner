package Kubaner.Logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class Plan implements Serializable {
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
		LinkedList<String> assessors = new LinkedList<String>();
		
		//get nearly all information stored in the plan to work with it
		for(int i = 0; i < timeline.length; i++) {
			for(int i2 = 0; i2 < timeline[i].size(); i2++) {
				member = timeline[i].getTimeLineMember(i2);
				
				if(member instanceof Exam) {
					exam = (Exam)member;
					if(exam.getAssessor() != null && exam.getAssessor() != "")
						assessors.add(exam.getAssessor());
					
					subjects = exam.getSubjectArray();
					for(int i3 = 0; i3 < subjects.length; i3++)
						if(subjects[i3] != null && subjectList.exists(subjects[i3]) == false)
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
		cols = 1 + subjectList.size() + assessors.size();
		
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
						//column <= subjectList.size() - subject/professor column
						if(i <= subjectList.size()) {
							value = subjectList.get(i - 1).getName();
						
							for(int i3 = 0; i3 < profList.size(); i3++) {
								subjects = profList.get(i3).getSubjectArray();
							
								for(int i4 = 0; i4 < subjects.length; i4++) {
									if(subjects[i4].getName() == subjectList.get(i - 1).getName())
										value += " - " + profList.get(i3).getName();
								}
							}
							
						//column > subjectList.size() - assessor column
						} else
							value = assessors.get(i - (subjectList.size() + 1));
					
					//row > 0
					} else {
						if(i <= subjectList.size()) {
							//value at i2, i is still an instance of the class Time
							exam = getExamAtTime((Time)master.getValueAt(i2, 0), subjectList.get(i - 1), startTimeList);
						
							if(exam != null)
								value = "" + exam.getStudent().getName() + "   " + findExamRoom(exam);
							else 
								value = "";
							
						//assessor column
						} else {
							exam = getExamWithInvestigatorAtTime((Time)master.getValueAt(i2, 0)
									, assessors.get(i - subjectList.size()), startTimeList);
							
							if(exam != null)
								value = "" + exam.getStudent().getName() + "   " + findExamRoom(exam);
							else 
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
	
	
	/**
	 * Creates and saves a table as PDF-file at a certain data-path.
	 * @param fileName - the data-path.
	 * @param planType - 0 for master plan, 1 for student plan and 2 for professor plan.
	 * @param investigator - The name of the investigator, only necessary for plantype 2.
	 * @return The room name, or "" if no room was found.
	 */
	public void createPdf(String fileName, ProfList profList) throws FileNotFoundException, DocumentException {
		MasterModel model;
		
		PdfPTable table = null; 
		
		//null -> NOT landscape format, numbers = space in pixels
		//between border and table: left, right, top, bottom  
		Document document = new Document(PageSize.A4, 20, 15, 15, 15);
		File file = new File(fileName);
		PdfWriter.getInstance(document, new FileOutputStream(file));
		
		PdfPCell cell;
		
		int cols;
		int rows;
		
		String value;
		
		model = createAbstractTableModel();
		table = new PdfPTable(model.getColumnCount()); 
			
		cols = model.getColumnCount();
		rows = model.getRowCount();
		
		//make a header cell
		cell = new PdfPCell(new Phrase("Master-Plan"));
		cell.setColspan(cols);
		table.addCell(cell);
		
		//initialize PDF table as master plan
		for(int i = 0; i < rows; i++) {
			for(int i2 = 0; i2 < cols; i2++) {
				value = (String)model.getValueAt(i, i2);
				table.addCell(new Phrase(value));
			}
		}
		
		//Write the file
		document.open();
		document.add(table);
		document.add(new Phrase("\n"));
		
		document.add(getPdfPTableType1());
		document.add(new Phrase("\n"));
		
		for(Professor prof : profList) {
			document.add(getPdfPTableType2(prof.getName()));
			document.add(new Phrase("\n"));
		}
        document.close();
	}
	
	
	private PdfPTable getPdfPTableType1() {
		ArrayList<Time[]> startTimeList = createTimeList();
		
		int rows = calculateRows(startTimeList) - 1;
		int cols = timeline.length * 3 + 1;
		
		PdfPTable table = new PdfPTable(cols); 
		PdfPCell cell;
		
		String data[][];
		String value;
		
		TimeLineMember member;
		Exam exam;
		Subject[] subjects;
		
		//make a header cell
		cell = new PdfPCell(new Phrase("Student-Plan"));
		cell.setColspan(cols);
		table.addCell(cell);
		
		
		//initialize the first row of student plan
		table.addCell(new Phrase(""));
		
		for(int i = 1; i < cols; i++) {
			if(i % 3 == 1)
				table.addCell(new Phrase("Raum"));
			else if(i % 3 == 2)
				table.addCell(new Phrase("Fächer/Fach"));
			else
				table.addCell(new Phrase("Prüfling"));
		}
		
		Time time = null;
		data = new String[rows][cols];
		
		//calculate data for the remaining table
		for(int i = 0; i < cols; i++) {
			for(int i2 = 0; i2 < rows; i2++) {
				value = "";
				//column 0: Times
				if(i == 0) {
					time = getNextStartTime(time, startTimeList);
					if(time.getMinute() < 10)
						value = time.getHour() + ":0" + time.getMinute();
					else
						value = time.getHour() + ":" + time.getMinute();
					
				//room column
				} else if(i % 3 == 1) {
					value = timeline[i / 3].getRoom();
				
				//subject or student column
				} else {
					if(i2 < timeline[(i - 1) / 3].size()) {
						member = timeline[(i - 1) / 3].getTimeLineMember(i2);
						if(member instanceof Exam) {
							exam = (Exam)member;
							
							//subject column
							if(i % 3 == 2) {
								subjects = exam.getSubjectArray();
								for(int i3 = 0; i3 < subjects.length; i3++) {
									if(subjects[i3] != null)
										value += subjects[i3].getName();
								}
								
							//i % 3 == 0 student column
							} else
								value = exam.getStudent().getName();
						}
					}
				}
				data[i2][i] = value;	
			}
		}
		
		for(int i = 0; i < rows; i++)
			for(int i2 = 0; i2 < cols; i2++)
				table.addCell(new Phrase(data[i][i2]));
		
		return table;
	}
	
	private PdfPTable getPdfPTableType2(String investigator) {
		ArrayList<Time[]> startTimeList = createTimeList();
		
		int cols = 4;
		
		PdfPTable table = new PdfPTable(cols); 
		PdfPCell cell;
		
		Exam exam;
		
		//make a header cell
		cell = new PdfPCell(new Phrase("Investigator-Plan: " + investigator));
		cell.setColspan(cols);
		table.addCell(cell);
		
		
		//initialize the first row of student plan
		table.addCell(new Phrase(""));
		
		for(int i = 1; i < cols; i++) {
			if(i % 3 == 1)
				table.addCell(new Phrase("COM"));
			else if(i % 3 == 2)
				table.addCell(new Phrase("Raum"));
			else
				table.addCell(new Phrase("Mitprüfer"));
		}
		
		Time time = null;
		String value;
		Professor[] profs;
		
		//calculate data for the remaining table
		time = this.getNextStartTime(time, startTimeList);
		
		if(time != null) {
			do {
				value = "";
				exam = getExamWithInvestigatorAtTime(time, investigator, startTimeList);
				
				if(exam != null) {
					if(time.getMinute() < 10)
						value = time.getHour() + ":0" + time.getMinute();
					else
						value = time.getHour() + ":" + time.getMinute();
					
					table.addCell(new Phrase(value));
					table.addCell(new Phrase(exam.getStudent().getName()));
					
					TimeLine line = timelineOfExam(exam);
					if(line != null)
						table.addCell(new Phrase(line.getRoom()));
					else
						table.addCell("");
					
					value = "";
					if(exam.getAssessor() != null && exam.getAssessor() != "" && exam.getAssessor().equals(investigator) == false)
						value += investigator;
					
					profs = exam.getProfArray();
					for(int i3 = 0; i3 < profs.length; i3++) {
						if(profs[i3] != null && profs[i3].getName().equals(investigator) == false) {
							if(value.equals(""))
								value = profs[i3].getName();
							else
								value += ", " + profs[i3].getName();
						}
					}
					table.addCell(new Phrase(value));
				}
				
				time = this.getNextStartTime(time, startTimeList);
			} while(time != null);
		}
		return table;
	}
	
	private Exam getExamWithInvestigatorAtTime(Time time, String investigator, ArrayList<Time[]> startTimeList) {
		TimeLineMember member;
		Exam exam;
		
		Professor[] profs;
		
		for(int i = 0; i < timeline.length; i++) {
			for(int i2 = 0; i2 < timeline[i].size(); i2++) {
				member = timeline[i].getTimeLineMember(i2);
				if(member instanceof Exam) {
					exam = (Exam)member;
					
					if(exam.getAssessor().equals(investigator))
						return exam;
					
					profs = exam.getProfArray();
					for(int i3 = 0; i3 < profs.length; i3++)
						if(profs[i3] != null && profs[i3].getName().equals(investigator)) {
							if(startTimeList.get(i)[i2].getHour() == time.getHour() && startTimeList.get(i)[i2].getMinute() == time.getMinute()) {
								return exam;
							}
						}
				}
			}
		}
		return null;
	}


	private TimeLine timelineOfExam(Exam exam) {
		for(int i = 0; i < timeline.length; i++) {
			for(int i2 = 0; i2 < timeline[i].size(); i2++) {
				if(timeline[i].getTimeLineMember(i2) == exam) {
					return timeline[i];
				}
			}
		}
		return null;
	}
	
	public Time getStartTime() {
		return startTime;
	}
	
}
