package Kubaner.Tests;

import Kubaner.Logic.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ProfListTest {
    ProfList profList;
    SubjectList subjectList;

    @Before
    public void setup() {
        subjectList = new SubjectList();
        subjectList.create("ANA");
        subjectList.create("GTI");
        profList = new ProfList();
    }

    @Test
    public void testCreateProfessor() throws Exception {
        Professor prof = profList.create("Kubaner", subjectList.toArray(),new TimePeriod[] {new TimePeriod(new Time(0,0), new Time(12,0))});
        assertEquals("Kubaner", prof.getName());
        // TODO: Sollen hier noch die Faecher und die Zeiten ueberprueft werden?
    }

    @Test
    public void testCreateWithTwoProfessorsCreateOneInbetween() throws Exception {
        // TODO: Das ist sollte nicht so getestet werden! (Momentan kein anderer Weg)
        profList.create("Kubaner", subjectList.toArray(),new TimePeriod[] {new TimePeriod(new Time(0,0), new Time(12,0))});
        profList.create("Todorov", subjectList.toArray(),new TimePeriod[] {new TimePeriod(new Time(0,0), new Time(12,0))});
        profList.create("Schramm", subjectList.toArray(),new TimePeriod[] {new TimePeriod(new Time(0,0), new Time(12,0))});
        assertEquals("Schramm", profList.get(1).getName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateWithEmptyName() throws Exception {
        profList.create("", subjectList.toArray(), new TimePeriod[] {new TimePeriod(new Time(0,0), new Time(12, 0))});
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateNullName() throws Exception {
        profList.create(null, subjectList.toArray(), new TimePeriod[] {new TimePeriod(new Time(0,0), new Time(12, 0))});
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateNullSubjectList() throws Exception {
        profList.create("Kubaner", null, new TimePeriod[] {new TimePeriod(new Time(0,0), new Time(12, 0))});
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateNullTimePeriod() throws Exception {
        profList.create("Kubaner", subjectList.toArray(), null);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetFromEmptyList() throws Exception {
        profList.get(1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetOutOfBounds() throws Exception {
        profList.create("Kubaner", subjectList.toArray(),new TimePeriod[] {new TimePeriod(new Time(0,0), new Time(12,0))});
        profList.get(1);
    }

    @Test
    public void testGet() throws Exception {
        profList.create("Kubaner", subjectList.toArray(),new TimePeriod[] {new TimePeriod(new Time(0,0), new Time(12,0))});
        assertEquals("Kubaner", profList.get(0).getName());
    }

    @Test
    public void testDeleteFromEmptyList() throws Exception {
        assertFalse(profList.delete(0));
    }

    @Test
    public void testDeleteWithOneElement() throws Exception {
        profList.create("Kubaner", subjectList.toArray(), new TimePeriod[]{new TimePeriod(new Time(0, 0), new Time(12, 0))});
        assertTrue(profList.delete(0));
    }

    @Test
    public void testDeleteBetweenElements() throws Exception {
        profList.create("Kubaner", subjectList.toArray(),new TimePeriod[] {new TimePeriod(new Time(0,0), new Time(12,0))});
        profList.create("Todorov", subjectList.toArray(),new TimePeriod[] {new TimePeriod(new Time(0,0), new Time(12,0))});
        profList.create("Schramm", subjectList.toArray(),new TimePeriod[] {new TimePeriod(new Time(0,0), new Time(12,0))});
        assertTrue(profList.delete(1));
        assertEquals("Todorov", profList.get(1).getName());
    }

    @Test
    public void testExist() throws Exception {
        Professor prof = profList.create("Kubaner", subjectList.toArray(), new TimePeriod[]{new TimePeriod(new Time(0, 0), new Time(12, 0))});
        assertTrue(profList.exists(prof));
    }

    @Test
    public void testSize() throws Exception {
        profList.create("Kubaner", subjectList.toArray(),new TimePeriod[] {new TimePeriod(new Time(0,0), new Time(12,0))});
        profList.create("Todorov", subjectList.toArray(),new TimePeriod[] {new TimePeriod(new Time(0,0), new Time(12,0))});
        profList.create("Schramm", subjectList.toArray(),new TimePeriod[] {new TimePeriod(new Time(0,0), new Time(12,0))});
        assertEquals(3, profList.size());
    }

    @Test
    public void testToArray() throws Exception {
        profList.create("Kubaner", subjectList.toArray(),new TimePeriod[] {new TimePeriod(new Time(0,0), new Time(12,0))});
        profList.create("Todorov", subjectList.toArray(),new TimePeriod[] {new TimePeriod(new Time(0,0), new Time(12,0))});
        profList.create("Schramm", subjectList.toArray(),new TimePeriod[] {new TimePeriod(new Time(0,0), new Time(12,0))});
        Professor[] arr = profList.toArray();
        assertEquals("Kubaner", arr[0].getName());
        assertEquals("Schramm", arr[1].getName());
        assertEquals("Todorov", arr[2].getName());
    }

    @Test
    public void testExistsWithOneProfessor() throws Exception {
        profList.create("Kubaner", subjectList.toArray(),new TimePeriod[] {new TimePeriod(new Time(0,0), new Time(12,0))});
        assertTrue(profList.exists("Kubaner"));
    }

    @Test
    public void testExistsWithThreeProfessors() throws Exception {
        profList.create("Kubaner", subjectList.toArray(),new TimePeriod[] {new TimePeriod(new Time(0,0), new Time(12,0))});
        profList.create("Todorov", subjectList.toArray(),new TimePeriod[] {new TimePeriod(new Time(0,0), new Time(12,0))});
        profList.create("Schramm", subjectList.toArray(),new TimePeriod[] {new TimePeriod(new Time(0,0), new Time(12,0))});
        assertTrue(profList.exists("Kubaner"));
        assertTrue(profList.exists("Todorov"));
        assertTrue(profList.exists("Schramm"));
    }
}