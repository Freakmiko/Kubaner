package Kubaner.Logic;

import java.util.LinkedList;
import java.util.List;

public class Professor {
    private String name;
    private List<Subject> subjects;
    private List<AbsenceTime> absenceTimes;

    /**
     * This Creates a Professor with the given Name,
     * Subjects and the absence times.
     *
     * @param name The name of the professor
     * @param sub The subjects of the professor
     * @param absenceTimes The desired dates of the professor
     */
    Professor(String name, Subject[] sub, AbsenceTime[] absenceTimes) {
        subjects = new LinkedList<>();
        this.absenceTimes = new LinkedList<>();
        setName(name);
        for(Subject subject : sub) addSubject(subject);
        for(AbsenceTime date: absenceTimes) addAbsenceTime(date);
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
        return (Subject[]) subjects.toArray();
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
     * Gets the absence times of a professor as an array.
     *
     * @return The absence times as an arry.
     */
    public AbsenceTime[] getAbsenceTimeArray() {
        return (AbsenceTime[]) absenceTimes.toArray();
    }

    /**
     * Adds an absence time to the professor.
     *
     * @param absenceTime The time that the professor is absent.
     */
    public void addAbsenceTime(AbsenceTime absenceTime) {
        absenceTimes.add(absenceTime);
    }

    /**
     * Deletes an absence time from a professor
     *
     * @param index The index of the absence time that should
     *              be removed.
     */
    public void deleteAbsenceTime(int index) {
        absenceTimes.remove(index);
    }
}
