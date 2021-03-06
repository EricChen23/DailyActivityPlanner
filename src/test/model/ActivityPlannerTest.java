package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ActivityPlannerTest {
    ActivityPlanner testPlanner;
    Activity activityMonEightNine;
    Activity activityTueEightNine;

    @BeforeEach
    void runBefore() {
        testPlanner = new ActivityPlanner("Test Planner");
        activityMonEightNine = new Activity("A1MON8-9", "MONDAY8TO9", Day.MON, 8, 1);
        activityTueEightNine = new Activity("A2TUE8-9", "TUESDAY8TO9", Day.TUE, 8, 1);
    }

    @Test
    void testConstructor() {
        assertEquals("Test Planner", testPlanner.getName());
        testPlanner.setName("New Test Planner");
        assertEquals("New Test Planner", testPlanner.getName());
        assertTrue(testPlanner.isEmpty());
        assertEquals(0, testPlanner.getNumActivities());
        assertEquals("There are no activities. ", testPlanner.viewActivities());
    }

    @Test
    void testIndexIsValid() {
        boolean isValid = testPlanner.indexIsValid(1);
        assertFalse(isValid);
        boolean successfullyAdded = testPlanner.addActivity(activityMonEightNine);
        assertTrue(successfullyAdded);
        isValid = testPlanner.indexIsValid(1);
        assertTrue(isValid);
        isValid = testPlanner.indexIsValid(0);
        assertFalse(isValid);
        isValid = testPlanner.indexIsValid(2);
        assertFalse(isValid);
    }

    @Test
    void testAddNegativeStarTimeActivity() {
        Activity invalidStartTimeActivity = new Activity("N/A", "N/A", Day.SUN, -1, 5);
        boolean successfullyAdded = testPlanner.addActivity(invalidStartTimeActivity);
        assertFalse(successfullyAdded);
    }

    @Test
    void testStartTimeOverTwentyThree() {
        Activity invalidStartTimeActivity = new Activity("N/A", "N/A", Day.SUN, 24, 5);
        boolean successfullyAdded = testPlanner.addActivity(invalidStartTimeActivity);
        assertFalse(successfullyAdded);
    }

    @Test
    void testNegativeDuration() {
        Activity invalidDurationActivity = new Activity("N/A", "N/A", Day.SUN, 5, -1);
        boolean successfullyAdded = testPlanner.addActivity(invalidDurationActivity);
        assertFalse(successfullyAdded);
    }

    @Test
    void testDurationOverNight() {
        Activity invalidDurationActivity = new Activity("N/A", "N/A", Day.SUN, 5, 20);
        boolean successfullyAdded = testPlanner.addActivity(invalidDurationActivity);
        assertFalse(successfullyAdded);
    }

    @Test
    void testAddOneActivity() {
        boolean successfullyAdded = testPlanner.addActivity(new Activity("A1SUN8-9", "SUNDAY8TO9", Day.SUN, 8, 1));
        assertTrue(successfullyAdded);
        assertEquals("A1SUN8-9", testPlanner.getActivityBriefDescription(1));
        assertEquals("SUNDAY8TO9", testPlanner.getActivityDetailedDescription(1));
        assertEquals(Day.SUN, testPlanner.getActivityDay(1));
        assertEquals(8, testPlanner.getActivityStartTime(1));
        assertEquals(1, testPlanner.getActivityDuration(1));
        assertEquals(1, testPlanner.getNumActivities());
    }

    @Test
    void testAddTwoActivity() {
        boolean successfullyAddedOne = testPlanner.addActivity(activityMonEightNine);
        boolean successfullyAddedTwo = testPlanner.addActivity(activityTueEightNine);
        assertTrue(successfullyAddedOne);
        assertTrue(successfullyAddedTwo);
        assertEquals("A1MON8-9", testPlanner.getActivityBriefDescription(1));
        assertEquals("MONDAY8TO9", testPlanner.getActivityDetailedDescription(1));
        assertEquals(Day.MON, testPlanner.getActivityDay(1));
        assertEquals(8, testPlanner.getActivityStartTime(1));
        assertEquals(1, testPlanner.getActivityDuration(1));
        assertEquals("A2TUE8-9", testPlanner.getActivityBriefDescription(2));
        assertEquals("TUESDAY8TO9", testPlanner.getActivityDetailedDescription(2));
        assertEquals(2, testPlanner.getNumActivities());
    }

    @Test
    void testAddWithConflict() {
        Activity activityConflictOne = new Activity("A1MON8-9", "MONDAY8TO9", Day.MON, 8, 1);
        boolean successfullyAddedOne = testPlanner.addActivity(activityMonEightNine);
        boolean successfullyAddedTwo = testPlanner.addActivity(activityTueEightNine);
        boolean unsuccessfullyAddedOne = testPlanner.addActivity(activityConflictOne);
        assertEquals(2, testPlanner.getNumActivities());
        assertTrue(successfullyAddedOne);
        assertTrue(successfullyAddedTwo);
        assertFalse(unsuccessfullyAddedOne);

        testPlanner.setActivityBriefDescription(1, "New A1MON8-9");
        testPlanner.setActivityDetailedDescription(1, "New MONDAY8TO9");
        assertEquals("New A1MON8-9", testPlanner.getActivityBriefDescription(1));
        assertEquals("New MONDAY8TO9", testPlanner.getActivityDetailedDescription(1));
    }

    @Test
    void removeOnlyOne() {
        boolean successfullyAdded = testPlanner.addActivity(activityMonEightNine);
        assertTrue(successfullyAdded);
        assertEquals(1, testPlanner.getNumActivities());
        assertFalse(testPlanner.isEmpty());
        testPlanner.deleteActivity(1);
        assertEquals(0, testPlanner.getNumActivities());
        assertTrue(testPlanner.isEmpty());
        assertEquals("There are no activities. ", testPlanner.viewActivities());
    }

    @Test
    void testRemoveThenAdd() {
        Activity activityNewOne = new Activity("AN1TUE7-8", "TUESDAY7TO8", Day.TUE, 7, 1);
        boolean successfullyAddedTwo = testPlanner.addActivity(activityTueEightNine);
        boolean successfullyAddedOne = testPlanner.addActivity(activityMonEightNine);
        assertTrue(successfullyAddedOne);
        assertTrue(successfullyAddedTwo);
        testPlanner.deleteActivity(1);
        assertEquals(1, testPlanner.getNumActivities());
        assertEquals("A2TUE8-9", testPlanner.getActivityBriefDescription(1));
        boolean successfullyAddedNewOne = testPlanner.addActivity(activityNewOne);
        assertTrue(successfullyAddedNewOne);
        String summary = "\n";
        summary += 1 + " --> AN1TUE7-8 from 7:00 to 8:00 on TUE\n";
        summary += 2 + " --> A2TUE8-9 from 8:00 to 9:00 on TUE\n";
        assertEquals(summary, testPlanner.viewActivities());
    }

    @Test
    void testModifyWithoutConflict() {
        boolean successfullyAddedOne = testPlanner.addActivity(activityMonEightNine);
        boolean successfullyAddedTwo = testPlanner.addActivity(activityTueEightNine);
        boolean successfullyChangedTwo = testPlanner.setActivityDay(2, Day.SUN);
        assertTrue(successfullyAddedOne);
        assertTrue(successfullyAddedTwo);
        assertTrue(successfullyChangedTwo);
        assertEquals(2, testPlanner.getNumActivities());
        assertEquals(Day.SUN, testPlanner.getActivityDay(1));
    }

    @Test
    void testModifyWithConflict() {
        boolean successfullyAddedOne = testPlanner.addActivity(activityMonEightNine);
        boolean successfullyAddedTwo = testPlanner.addActivity(activityTueEightNine);
        boolean unsuccessfullyChangedTwo = testPlanner.setActivityDay(2, Day.MON);
        assertTrue(successfullyAddedOne);
        assertTrue(successfullyAddedTwo);
        assertFalse(unsuccessfullyChangedTwo);
        assertEquals(2, testPlanner.getNumActivities());
        assertEquals(Day.MON, testPlanner.getActivityDay(1));
        assertEquals(Day.TUE, testPlanner.getActivityDay(2));
    }

    @Test
    void testSetStartTime() {
        boolean successfullyAddedOne = testPlanner.addActivity(activityMonEightNine);
        boolean successfullyAddedTwo = testPlanner.addActivity(activityTueEightNine);
        boolean successfullyUpdated = testPlanner.setStartTime(2, 7);
        assertTrue(successfullyAddedOne);
        assertTrue(successfullyAddedTwo);
        assertTrue(successfullyUpdated);
        successfullyUpdated = testPlanner.setStartTime(2, -1);
        assertFalse(successfullyUpdated);
        successfullyUpdated = testPlanner.setStartTime(2, 24);
        assertFalse(successfullyUpdated);
        assertEquals(7, testPlanner.getActivityStartTime(2));
        successfullyUpdated = testPlanner.setActivityDay(2, Day.MON);
        assertTrue(successfullyUpdated);
        assertEquals(7, testPlanner.getActivityStartTime(1));
        successfullyUpdated = testPlanner.setStartTime(1, 8);
        assertFalse(successfullyUpdated);
        Activity activityOnWed = new Activity("N/A", "N/A", Day.WED, 6, 10);
        boolean successfullyAddedThree = testPlanner.addActivity(activityOnWed);
        assertTrue(successfullyAddedThree);
        successfullyUpdated = testPlanner.setStartTime(3, 15);
        assertFalse(successfullyUpdated);
    }

    @Test
    void testSetDuration() {
        boolean successfullyAddedOne = testPlanner.addActivity(activityMonEightNine);
        boolean successfullyAddedTwo = testPlanner.addActivity(activityTueEightNine);
        assertTrue(successfullyAddedOne);
        assertTrue(successfullyAddedTwo);
        assertEquals(activityMonEightNine, testPlanner.getActivity(1));
        boolean successfullyUpdated = testPlanner.setDuration(1, -1);
        assertFalse(successfullyUpdated);
        successfullyUpdated = testPlanner.setDuration(1, 25);
        assertFalse(successfullyUpdated);
        successfullyUpdated = testPlanner.setDuration(1, 17);
        assertFalse(successfullyUpdated);
        successfullyUpdated = testPlanner.setDuration(1, 5);
        assertTrue(successfullyUpdated);
        assertEquals(5, testPlanner.getActivityDuration(1));
        successfullyUpdated = testPlanner.setStartTime(1, 9);
        assertTrue(successfullyUpdated);
        successfullyUpdated = testPlanner.setActivityDay(1, Day.TUE);
        assertTrue(successfullyUpdated);
        successfullyUpdated = testPlanner.setDuration(1, 5);
        assertFalse(successfullyUpdated);
    }

    @Test
    void testNoConflict() {
        ActivityPlanner ap = new ActivityPlanner("Test");
        boolean successfullyAdded = ap.addActivity(activityMonEightNine);
        boolean successfullyUpdated = ap.setDuration(1,4);
        assertTrue(successfullyAdded);
        assertTrue(successfullyUpdated);
        Activity testActEarly = new Activity("T", "T", Day.MON, 4, 5);
        Activity testActLate = new Activity("T", "T", Day.MON, 10, 5);
        successfullyAdded = ap.addActivity(testActEarly);
        assertFalse(successfullyAdded);
        successfullyAdded = ap.addActivity(testActLate);
        assertFalse(successfullyAdded);
        assertEquals(1, ap.getNumActivities());
    }

    @Test
    void testDurationSameDay() {
        ActivityPlanner ap = new ActivityPlanner("Test");
        boolean success = ap.addActivity(activityMonEightNine);
        assertTrue(success);
        boolean updated = ap.setDuration(1, 10);
        assertTrue(updated);
        Activity conflictAct = new Activity("C", "C", Day.MON, 19, 4);
        success = ap.addActivity(conflictAct);
        assertTrue(success);
        updated = ap.setDuration(1, 15);
        assertFalse(updated);
    }

    @Test
    void testSetStartTimeConflict() {
        ActivityPlanner ap = new ActivityPlanner("Test");
        boolean success = ap.addActivity(activityMonEightNine);
        assertTrue(success);
        Activity conflictAct = new Activity("C", "C", Day.MON, 4, 4);
        success = ap.addActivity(conflictAct);
        assertTrue(success);
        boolean updated = ap.setStartTime(2, 7);
        assertFalse(updated);
        assertEquals(2, ap.getNumActivities());
    }

}
