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
        testActivity = new Activity(briefDescription, detailedDescription, 17, 19);
    }

    @Test
    void testConstructor() {
        assertEquals("Brief description", testActivity.getBriefDescription());
        assertEquals("Detailed description", testActivity.getDetailedDescription());
        assertEquals(17, testActivity.getStartTime());
        assertEquals(19, testActivity.getEndTime());
    }

    @Test
    void testModifyBriefDescription() {
        testActivity.modifyBriefDescription("Modified brief description");
        assertEquals("Modified brief description", testActivity.getBriefDescription());
    }

    @Test
    void testModifyDetailedDescription() {
        testActivity.modifyDetailedDescription("Modified detailed description");
        assertEquals("Modified detailed description", testActivity.getDetailedDescription());
    }

    @Test
    void testModifyStartTime() {
        testActivity.modifyStartTime(4);
        assertEquals(4, testActivity.getStartTime());
    }

    @Test
    void testModifyEndTime() {
        testActivity.modifyEndTime(16);
        assertEquals(16, testActivity.getEndTime());
    }
}