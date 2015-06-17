package Kubaner.Logic;

public class Subject {
    private String name;
    private int examLength;

    
    //TODO: Add setter and constructor parameter for examLength
    
    
    Subject(String name) {
        this.name = name;
    }

    /**
     * Gets the name of the subject.
     *
     * @return The name of the subject.
     */
    public String getName() {
        return name;
    }
    
    
    /**
     * Gets the length of the exam in the current subject in minutes.
     * @return
     */
    public int getExamLength() {
    	return examLength;
    }
}
