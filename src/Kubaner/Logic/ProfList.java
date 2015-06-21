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
        // The professor list is sorted lexicographically
        // so here the first name that is lexicographically after the
        // new professors name is searched and the new professor is
        // inserted right before that other professor.
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
    public Professor create(String name, Subject[] subjects, TimePeriod[] timePeriods) throws IllegalArgumentException {
        if(name == null || name.equals(""))
            throw new IllegalArgumentException("Name is null or empty!");
        if(subjects == null)
            throw new IllegalArgumentException("Subjects can't be null!");
        if(timePeriods == null)
            throw new IllegalArgumentException("TimePeriods can't be null!");

        Professor newProf = new Professor(name, subjects, timePeriods);
        add(newProf);
        return newProf;
    }

    /**
     * Gets a professor by index.
     *
     * @param index The index of the professor.
     * @return Returns the professor.
     * @throws IndexOutOfBoundsException If the index is out of bounds
     */
    public Professor get(int index) throws IndexOutOfBoundsException{
        if(index < 0 || index >= size())
            throw new IndexOutOfBoundsException("The index was out of bounds.");
        return professors.get(index);
    }

    /**
     * Gets all the professors in the list in an array.
     *
     * @return Returns the professors in an array.
     */
    public Professor[] toArray() {
        return professors.toArray(new Professor[size()]);
    }

    /**
     * Checks to see if a professor exists in the list.
     *
     * @param professor The professor that is looked for.
     * @return Returns true if the professor exists and false
     *         if the professor doesn't exist.
     */
    public boolean exists(Professor professor) {
        return professors.contains(professor);
    }

    /**
     * Checks if a professor with the given name already
     * exists in the list.
     *
     * @param professorName The name of the professor.
     * @return Returns true if a professor with that name exists
     *         returns false if no professor with that name exists.
     */
    public boolean exists(String professorName) {
        for(Professor prof : professors)
            if (prof.getName().equals(professorName))
                return true;

        return false;
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

