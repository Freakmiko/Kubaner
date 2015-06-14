package Kubaner.Logic;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ProfList implements Iterable<Professor> {

    private List<Professor> professors;

    public ProfList() {
        professors = new LinkedList<>();
    }

    /**
     * Adds a professor to the list.
     *
     * @param professor The professor that will be added.
     */
    void add(Professor professor) {
        for(int i = 0; i < size(); i++) {
            if(professors.get(i).getName().compareTo(professor.getName()) >= 1) {
                professors.add(i, professor);
                return;
            }
        }
        // if the professor hasn't been added
        // to the list until now he's added last on the list.
        professors.add(professor);
    }

    /**
     * Creates a new professor and adds it to the list.
     *
     * @param name The name of the professor.
     * @param subjects The subjects the professor has.
     * @param timePeriods The time periods where the professor isn't available.
     * @return Returns the newly created professor.
     */
    public Professor create(String name, Subject[] subjects, TimePeriod[] timePeriods) {
        Professor newProf = new Professor(name, subjects, timePeriods);
        add(newProf);
        return newProf;
    }

    /**
     * Gets a professor by index.
     *
     * @param index The index of the professor.
     * @return Returns the professor.
     * @throws IllegalArgumentException If the index is out of bounds
     */
    public Professor get(int index) throws IllegalArgumentException{
        if(index < 0 || index >= size())
            throw new IllegalArgumentException("The index was out of bounds.");
        return professors.get(index);
    }

    /**
     * Gets all the professors in the list in an array.
     *
     * @return Returns the professors in an array.
     */
    public Professor[] toArray() {
        return (Professor[])professors.toArray();
    }

    /**
     * Checks to see if a professor exists in the list.
     *
     * @param professor The professor that is looked for.
     * @return Returns true if the professor exists and false
     *         if the professor doesn't exist.
     */
    public boolean exist(Professor professor) {
        return professors.contains(professor);
    }

    /**
     * Deletes a professor from the list by index.
     *
     * @param index The index of the professor.
     * @return Returns true if the professor was deleted and
     *         false if the professor wasn't deleted.
     */
    public boolean delete(int index) {
        if(index < 0 || index >= size())
            return false;
        professors.remove(index);
        return true;
    }

    /**
     * Gets the size of the professor list.
     *
     * @return Returns the size of the professor list.
     */
    public int size() {
        return professors.size();
    }

    /**
     * Gets the iterator of the ProfList
     *
     * @return Returns the iterator.
     */
	@Override
	public Iterator<Professor> iterator() {
        return professors.iterator();
	}
}

