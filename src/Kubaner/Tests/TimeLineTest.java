package Kubaner.Tests;

import Kubaner.Logic.Break;
import Kubaner.Logic.TimeLine;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TimeLineTest {

    TimeLine timeLine;

    @Before
    public void setup() throws Exception {
        timeLine = new TimeLine();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddNull() throws Exception {
        timeLine.add(null);
    }

    @Test
    public void testAddTimeLineMember() throws Exception {
        timeLine.add(new Break(10));
        assertEquals(10, timeLine.getTimeLineMember(0).getLength());
    }

    @Test
    public void testAddThreeTimeLineMember() throws Exception {
        timeLine.add(new Break(1));
        timeLine.add(new Break(10));
        timeLine.add(new Break(30));
        assertEquals(1, timeLine.getTimeLineMember(0).getLength());
        assertEquals(10, timeLine.getTimeLineMember(1).getLength());
        assertEquals(30, timeLine.getTimeLineMember(2).getLength());
    }

    @Test
    public void testInsertTimeLineMemberInEmptyList() throws Exception {
        timeLine.insert(0, new Break(10));
        assertEquals(10, timeLine.getTimeLineMember(0).getLength());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testInsertTimeLineMemberInOutOfBoundsIndex() throws Exception {
        timeLine.insert(10, new Break(10));
    }

    @Test (expected = IllegalArgumentException.class)
    public void testInsertNull() throws Exception {
        timeLine.insert(0, null);
    }

    @Test
    public void testInsertTimeLineMemberInbetween() throws Exception {
        timeLine.add(new Break(30));
        timeLine.add(new Break(60));
        timeLine.insert(1, new Break(69));
        assertEquals(69, timeLine.getTimeLineMember(1).getLength());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testDeleteFromEmptyTimeLine() throws Exception {
        timeLine.delete(0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testDeleteOutOfBoundsWithOneMember() throws Exception {
        timeLine.add(new Break(10));
        timeLine.delete(1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testDeleteWithOneMember() throws Exception {
        timeLine.add(new Break(10));
        timeLine.delete(0);
        timeLine.getTimeLineMember(0);
    }

    @Test
    public void testDeleteBetweenMembers() throws Exception {
        timeLine.add(new Break(10));
        timeLine.add(new Break(69));
        timeLine.add(new Break(30));
        timeLine.delete(1);
        assertEquals(30, timeLine.getTimeLineMember(1).getLength());
    }

    @Test
    public void testSizeOfEmptyTimeLine() throws Exception {
        assertEquals(0, timeLine.size());
    }

    @Test
    public void testSizeOfTimeLineWithOneMember() throws Exception {
        timeLine.add(new Break(0));
        assertEquals(1, timeLine.size());
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testMoveMemberToIndexOutOfBounds() throws Exception {
        timeLine.add(new Break(10));
        timeLine.moveMember(0, 1);
    }

    @Test
    public void testMoveMemberSwitchTwoMembers() throws Exception {
        timeLine.add(new Break(69));
        timeLine.add(new Break(88));
        timeLine.moveMember(0, 1);
        assertEquals(88, timeLine.getTimeLineMember(0).getLength());
    }

    @Test
    public void testSetRoom() throws Exception {
        timeLine.setRoom("RAUM");
        assertEquals("RAUM", timeLine.getRoom());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetRoomEmptyName() throws Exception {
        timeLine.setRoom("");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSetRoomNullName() throws Exception {
        timeLine.setRoom(null);
    }

    @Test
    public void testContainsExamsWithZeroMembers() throws Exception {
        assertFalse(timeLine.containsExams());
    }

    @Test
    public void testContainsExamsWithOneMember() throws Exception {
        timeLine.add(new Break(10));
        assertTrue(timeLine.containsExams());
    }
}