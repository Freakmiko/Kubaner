package Kubaner.Logic;

import java.util.LinkedList;
import java.util.List;

public class Professor {
    private String name;
    private List<Subject> subjects;
    private List<DesireDate> desireDates;

    Professor(String name, Subject[] sub, DesireDate[] des) {
        subjects = new LinkedList<>();
        desireDates = new LinkedList<>();
        setName(name);
        for(Subject subject : sub) addSubject(subject);
        for(DesireDate date: des) addDesireDate(date);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Subject[] getSubjectArray() {
        return (Subject[]) subjects.toArray();
    }

    public void addSubject(Subject subject) {
        subjects.add(subject);
    }

    public void deleteSubject(int index) {
        subjects.remove(index);
    }

    public DesireDate[] getDesireDateArray() {
        return (DesireDate[])desireDates.toArray();
    }

    public void addDesireDate(DesireDate date) {
        desireDates.add(date);
    }

    public void deleteDesireDate(int index) {
        desireDates.remove(index);
    }
}
