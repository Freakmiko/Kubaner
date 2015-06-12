package Kubaner.Logic;

import java.util.LinkedList;
import java.util.List;

public class SubjectList {

    List<Subject> subjects;

    public SubjectList() {
        subjects = new LinkedList<>();
    }

    void add(Subject subject) {
        for(int i = 0; i < size(); i++) {
            if(subjects.get(i).getName().compareTo(subject.getName()) >= 1) {
                subjects.add(subject);
                break;
            }
        }
    }

    public Subject create(String name) {
        Subject newSubject = new Subject(name);
        subjects.add(newSubject);
        return newSubject;
    }

    public Subject get(int index) {
        return subjects.get(index);
    }

    public Subject[] toArray() {
        return (Subject[])subjects.toArray();
    }

    public boolean exist(Subject subject) {
        return subjects.contains(subject);
    }

    public boolean delete(int index) {
        if(index < 0 || index >= size())
            return false;
        subjects.remove(index);
        return true;
    }

    public int size() {
        return subjects.size();
    }
}
