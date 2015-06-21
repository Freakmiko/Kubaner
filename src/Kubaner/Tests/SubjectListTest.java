package Kubaner.Tests;

import Kubaner.Logic.Subject;
import Kubaner.Logic.SubjectList;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SubjectListTest {
    SubjectList subjectList;

    @Before
    public void setup() {
        subjectList = new SubjectList();
    }

    @Test
    public void testCreateWithTwoSubjectsCreateOneInbetween() throws Exception {
        subjectList.create("ANA");
        subjectList.create("OOT");
        subjectList.create("GTI");
        assertEquals("GTI", subjectList.get(1).getName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateWithEmptyName() throws Exception {
        subjectList.create("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateWithNullName() throws Exception {
        subjectList.create(null);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetFromEmptyList() throws Exception {
        subjectList.get(0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetIndexOutOfBounds() throws Exception {
        subjectList.create("ANA");
        subjectList.get(1);
    }

    @Test
    public void testGet() throws Exception {
        subjectList.create("ANA");
        assertEquals("ANA", subjectList.get(0).getName());
    }

    @Test
    public void testDeleteFromEmptyList() throws Exception {
        assertFalse(subjectList.delete(0));
    }

    @Test
    public void testDeleteWithOneElement() throws Exception {
        subjectList.create("ANA");
        assertTrue(subjectList.delete(0));
        assertTrue(subjectList.size() == 0);
    }

    @Test
    public void testDeleteOutOfBounds() throws Exception {
        subjectList.create("ANA");
        assertFalse(subjectList.delete(1));
    }

    @Test
    public void testDeleteBetweenElements() throws Exception {
        subjectList.create("ANA");
        subjectList.create("OOT");
        subjectList.create("GTI");
        assertTrue(subjectList.delete(1));
        assertEquals("OOT", subjectList.get(1).getName());
    }

    @Test
    public void testExist() throws Exception {
        Subject sub = subjectList.create("ANA");
        assertTrue(subjectList.exists(sub));
    }

    @Test
    public void testSize() throws Exception {
        subjectList.create("ANA");
        subjectList.create("OOT");
        subjectList.create("GTI");
        assertEquals(3, subjectList.size());
    }

    @Test
    public void testToArray() throws Exception {
        subjectList.create("ANA");
        subjectList.create("OOT");
        subjectList.create("GTI");
        Subject[] arr = subjectList.toArray();
        assertEquals("ANA", arr[0].getName());
        assertEquals("GTI", arr[1].getName());
        assertEquals("OOT", arr[2].getName());
    }
}