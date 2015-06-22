package Kubaner.Logic;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Professor implements Serializable {
    private String name;
    private List<Subject> subjects;
    private List<TimePeriod> timePeriods;

    /**
     * This Creates a Professor with the given Name,
     * Subjects and the absence times.
     *
     * @param name The name of the professor
     * @param sub The subjects of the professor
     * @param timePeriods The Time periods that the professor is unavailable.
     */
    Professor(String name, Subject[] sub, TimePeriod[] timePeriods) {
        subjects = new LinkedList<>();
        this.timePeriods = new LinkedList<>();
        setName(name);
        for(Subject subject : sub) addSubject(subject);
        for(TimePeriod date: timePeriods) addTimePeriod(date);
    }

    /**
     * Gets the name of the professor
     *
     * @return The name of the professor
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the professor
     *
     * @param name The new name of the professor
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets all the subjects of the professor
     * as an array.
     *
     * @return The subjects as an array.
     */
    public Subject[] getSubjectArray() {
        return subjects.toArray(new Subject[subjects.size()]);
    }

    /**
     * Adds a subject to the professor.
     *
     * @param subject The new subject to be added.
     */
    public void addSubject(Subject subject) {
        subjects.add(subject);
    }

    /**
     * Deletes a subject from a professors list.
     *
     * @param index The index of the subject that should
     *              be removed.
     */
    public void deleteSubject(int index) {
        subjects.remove(index);
    }

    /**
     * Gets the time periods where the professor
     * is unavailable as an array.
     *
     * @return The unavailable time periods as an array.
     */
    public TimePeriod[] getTimePeriodArray() {
        return timePeriods.toArray(new TimePeriod[timePeriods.size()]);
    }

    /**
     * Adds a time period where the professor is unavailable.
     *
     * @param timePeriod The time period that the professor is absent.
     */
    public void addTimePeriod(TimePeriod timePeriod) {
        timePeriods.add(timePeriod);
    }

    /**
     * Deletes a time period from the professor.
     *
     * @param index The index of the time period that should
     *              be removed.
     */
    public boolean deleteTimePeriod(int index) {
        if(index < 0 || index > timePeriods.size())
            return false;
        timePeriods.remove(index);
        return true;
    }
    
    public boolean isAvailable(Time targetExamStartTime){
    	for(int i = 0; i < timePeriods.size(); i++){
    		if(timePeriods.get(i).laysBetween(targetExamStartTime)){
    			return true;
    		}
    	}
    	return false;
    }
}
