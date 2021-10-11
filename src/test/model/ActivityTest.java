package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ActivityTest {
    Activity testActivity;

    @BeforeEach
    void runBefore() {
        String briefDescription = "Brief description";
        String detailedDescription = "Detailed description";
        Day day = Day.MON;
        testActivity = new Activity(briefDescription, detailedDescription, day, 17, 19);
    }

    @Test
    void testConstructor() {
        assertEquals("Brief description", testActivity.getBriefDescription());
        assertEquals("Detailed description", testActivity.getDetailedDescription());
        assertEquals(Day.MON, testActivity.getDay());
        assertEquals(17, testActivity.getStartTime());
        assertEquals(19, testActivity.getDuration());
    }

    @Test
    void testModifyBriefDescription() {
        testActivity.setBriefDescription("Modified brief description");
        assertEquals("Modified brief description", testActivity.getBriefDescription());
    }

    @Test
    void testModifyDetailedDescription() {
        testActivity.setDetailedDescription("Modified detailed description");
        assertEquals("Modified detailed description", testActivity.getDetailedDescription());
    }

    @Test
    void testModifyDay(){
        testActivity.setDay(Day.FRI);
        assertEquals(Day.FRI, testActivity.getDay());
    }

    @Test
    void testModifyStartTime() {
        testActivity.setStartTime(4);
        assertEquals(4, testActivity.getStartTime());
    }

    @Test
    void testModifyDuration() {
        testActivity.setDuration(16);
        assertEquals(16, testActivity.getDuration());
    }

    @Test
    void testToString() {
        String expectedOutput = "Brief description: " + testActivity.getBriefDescription() + "\n"
                                + "Detailed description: " + testActivity.getDetailedDescription() + "\n"
                                + "Day: " + testActivity.getDay() +  "\n"
                                + "Start time: " + testActivity.getStartTime() + " h\n"
                                + "Duration: " + testActivity.getDuration() + " h\n";
        assertEquals(expectedOutput, testActivity.toString());
    }
}