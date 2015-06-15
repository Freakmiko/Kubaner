package Kubaner.Tests;

import Kubaner.Logic.Subject;
import Kubaner.Logic.SubjectList;
import org.junit.Test;

import static org.junit.Assert.*;

public class SubjectListTest {

    @Test
    public void testCreateWithTwoSubjectsAddOneInbetween() throws Exception {
        SubjectList subjectList = new SubjectList();
        subjectList.create("ANA");
        subjectList.create("OOT");
        subjectList.create("GTI");
        assertEquals("GTI", subjectList.get(1).getName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetFromEmptyList() throws Exception {
        SubjectList subjectList = new SubjectList();
        subjectList.get(0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetIndexOutOfBounds() throws Exception {
        SubjectList subjectList = new SubjectList();
        subjectList.create("ANA");
        subjectList.get(1);
    }

    @Test
    public void testGet() throws Exception {
        SubjectList subjectList = new SubjectList();
        subjectList.create("ANA");
        assertEquals("ANA", subjectList.get(0).getName());
    }

    @Test
    public void testDeleteFromEmptyList() throws Exception {
        SubjectList subjectList = new SubjectList();
        assertFalse(subjectList.delete(0));
    }

    @Test
    public void testDeleteWithOneElement() throws Exception {
        SubjectList subjectList = new SubjectList();
        subjectList.create("ANA");
        assertTrue(subjectList.delete(0));
        assertTrue(subjectList.size() == 0);
    }

    @Test
    public void testDeleteOutOfBounds() throws Exception {
        SubjectList subjectList = new SubjectList();
        subjectList.create("ANA");
        assertFalse(subjectList.delete(1));
    }

    @Test
    public void testDeleteBetweenElements() throws Exception {
        SubjectList subjectList = new SubjectList();
        subjectList.create("ANA");
        subjectList.create("OOT");
        subjectList.create("GTI");
        assertTrue(subjectList.delete(1));
        assertEquals("OOT", subjectList.get(1).getName());
    }

    @Test
    public void testExist() throws Exception {
        SubjectList subjectList = new SubjectList();
        Subject sub = subjectList.create("ANA");
        assertTrue(subjectList.exist(sub));
    }
}