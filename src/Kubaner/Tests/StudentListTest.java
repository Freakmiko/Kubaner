package Kubaner.Tests;

import Kubaner.Logic.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class StudentListTest {
    StudentList studentList;
    SubjectList subjectList;

    @Before
    public void setup() {
        subjectList = new SubjectList();
        subjectList.create("ANA");
        subjectList.create("GTI");
        studentList = new StudentList();
    }

    @Test
    public void testCreateProfessor() throws Exception {
        Student student = studentList.create("Kubaner", subjectList.toArray(),new TimePeriod[] {new TimePeriod(new Time(0,0), new Time(12,0))});
        assertEquals("Kubaner", student.getName());
        // TODO: Sollen hier noch die Faecher und die Zeiten ueberprueft werden?
    }

    @Test
    public void testCreateWithTwoProfessorsCreateOneInbetween() throws Exception {
        // TODO: Das ist sollte nicht so getestet werden! (Momentan kein anderer Weg)
        studentList.create("Kubaner", subjectList.toArray(), new TimePeriod[]{new TimePeriod(new Time(0, 0), new Time(12, 0))});
        studentList.create("Todorov", subjectList.toArray(), new TimePeriod[]{new TimePeriod(new Time(0, 0), new Time(12, 0))});
        studentList.create("Schramm", subjectList.toArray(), new TimePeriod[]{new TimePeriod(new Time(0, 0), new Time(12, 0))});
        assertEquals("Schramm", studentList.get(1).getName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateWithEmptyName() throws Exception {
        studentList.create("", subjectList.toArray(), new TimePeriod[] {new TimePeriod(new Time(0,0), new Time(12, 0))});
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateNullName() throws Exception {
        studentList.create(null, subjectList.toArray(), new TimePeriod[] {new TimePeriod(new Time(0,0), new Time(12, 0))});
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateNullSubjectList() throws Exception {
        studentList.create("Kubaner", null, new TimePeriod[] {new TimePeriod(new Time(0,0), new Time(12, 0))});
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateNullTimePeriod() throws Exception {
        studentList.create("Kubaner", subjectList.toArray(), null);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetFromEmptyList() throws Exception {
        studentList.get(1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetOutOfBounds() throws Exception {
        studentList.create("Kubaner", subjectList.toArray(), new TimePeriod[]{new TimePeriod(new Time(0, 0), new Time(12, 0))});
        studentList.get(1);
    }

    @Test
    public void testGet() throws Exception {
        studentList.create("Kubaner", subjectList.toArray(), new TimePeriod[]{new TimePeriod(new Time(0, 0), new Time(12, 0))});
        assertEquals("Kubaner", studentList.get(0).getName());
    }

    @Test
    public void testDeleteFromEmptyList() throws Exception {
        assertFalse(studentList.delete(0));
    }

    @Test
    public void testDeleteWithOneElement() throws Exception {
        studentList.create("Kubaner", subjectList.toArray(), new TimePeriod[]{new TimePeriod(new Time(0, 0), new Time(12, 0))});
        assertTrue(studentList.delete(0));
    }

    @Test
    public void testDeleteBetweenElements() throws Exception {
        studentList.create("Kubaner", subjectList.toArray(), new TimePeriod[]{new TimePeriod(new Time(0, 0), new Time(12, 0))});
        studentList.create("Todorov", subjectList.toArray(), new TimePeriod[]{new TimePeriod(new Time(0, 0), new Time(12, 0))});
        studentList.create("Schramm", subjectList.toArray(), new TimePeriod[]{new TimePeriod(new Time(0, 0), new Time(12, 0))});
        assertTrue(studentList.delete(1));
        assertEquals("Todorov", studentList.get(1).getName());
    }

    @Test
    public void testExists() throws Exception {
        Student student = studentList.create("Kubaner", subjectList.toArray(), new TimePeriod[]{new TimePeriod(new Time(0, 0), new Time(12, 0))});
        assertTrue(studentList.exists(student));
    }

    @Test
    public void testSize() throws Exception {
        studentList.create("Kubaner", subjectList.toArray(), new TimePeriod[]{new TimePeriod(new Time(0, 0), new Time(12, 0))});
        studentList.create("Todorov", subjectList.toArray(), new TimePeriod[]{new TimePeriod(new Time(0, 0), new Time(12, 0))});
        studentList.create("Schramm", subjectList.toArray(), new TimePeriod[]{new TimePeriod(new Time(0, 0), new Time(12, 0))});
        assertEquals(3, studentList.size());
    }

    @Test
    public void testToArray() throws Exception {
        studentList.create("Kubaner", subjectList.toArray(), new TimePeriod[]{new TimePeriod(new Time(0, 0), new Time(12, 0))});
        studentList.create("Todorov", subjectList.toArray(), new TimePeriod[]{new TimePeriod(new Time(0, 0), new Time(12, 0))});
        studentList.create("Schramm", subjectList.toArray(), new TimePeriod[]{new TimePeriod(new Time(0, 0), new Time(12, 0))});
        Student[] arr = studentList.toArray();
        assertEquals("Kubaner", arr[0].getName());
        assertEquals("Schramm", arr[1].getName());
        assertEquals("Todorov", arr[2].getName());
    }

    @Test
    public void testExistsWithOneStudent() throws Exception {
        studentList.create("Kubaner", subjectList.toArray(),new TimePeriod[] {new TimePeriod(new Time(0,0), new Time(12,0))});
        assertTrue(studentList.exists("Kubaner"));
    }

    @Test
    public void testExistsWithThreeStudents() throws Exception {
        studentList.create("Kubaner", subjectList.toArray(),new TimePeriod[] {new TimePeriod(new Time(0,0), new Time(12,0))});
        studentList.create("Todorov", subjectList.toArray(),new TimePeriod[] {new TimePeriod(new Time(0,0), new Time(12,0))});
        studentList.create("Schramm", subjectList.toArray(),new TimePeriod[] {new TimePeriod(new Time(0,0), new Time(12,0))});
        assertTrue(studentList.exists("Kubaner"));
        assertTrue(studentList.exists("Todorov"));
        assertTrue(studentList.exists("Schramm"));
    }
}