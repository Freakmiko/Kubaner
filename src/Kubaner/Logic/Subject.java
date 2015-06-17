package Kubaner.Logic;

public class Subject {
    private String name;
    private int examLength;

    Subject(String name) {
        this.name = name;
    }

    Subject(String name, int examLength) {
        this.examLength = examLength;
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
     *
     * @return The length of the exam in minutes.
     */
    public int getExamLength() {
    	return examLength;
    }

    /**
     * Sets the length of the exam in minutes.
     *
     * @param examLength The length on Minutes.
     */
    public void setExamLength(int examLength) {
        this.examLength = examLength;
    }
}
