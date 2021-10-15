package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ActivityPlannerBookTest {
    ActivityPlannerBook activityPlannerBook;

    @BeforeEach
    void runBefore() {
        activityPlannerBook = new ActivityPlannerBook("Book");
    }

    @Test
    void testConstructor() {
        assertEquals(0, activityPlannerBook.getNumPlanners());
        assertEquals("Book", activityPlannerBook.getPlannerBookName());
        assertFalse(activityPlannerBook.indexIsValid(1));
        assertTrue(activityPlannerBook.hasAvailableSpot());
    }

    @Test
    void testAddOnePlanner() {
        activityPlannerBook.createNewPlanner("Planner 1");
        assertEquals(1, activityPlannerBook.getNumPlanners());
        assertEquals("Planner 1", activityPlannerBook.getPlannerName(1));
        assertTrue(activityPlannerBook.indexIsValid(1));
        assertFalse(activityPlannerBook.indexIsValid(2));
        assertTrue(activityPlannerBook.hasAvailableSpot());
    }

    @Test
    void testAddTwoPlanners() {
        activityPlannerBook.createNewPlanner("Planner 1");
        activityPlannerBook.createNewPlanner("Planner 2");
        assertEquals(2, activityPlannerBook.getNumPlanners());
        assertEquals("Planner 1", activityPlannerBook.getPlannerName(1));
        assertEquals("Planner 2", activityPlannerBook.getPlannerName(2));
        assertTrue(activityPlannerBook.hasAvailableSpot());
        assertFalse(activityPlannerBook.indexIsValid(-1));
        assertTrue(activityPlannerBook.indexIsValid(1));
        assertTrue(activityPlannerBook.indexIsValid(2));
        assertFalse(activityPlannerBook.indexIsValid(3));
    }

    @Test
    void testAddThreePlanners() {
        activityPlannerBook.createNewPlanner("Planner 1");
        activityPlannerBook.createNewPlanner("Planner 2");
        activityPlannerBook.createNewPlanner("Planner 3");
        assertEquals(3, activityPlannerBook.getNumPlanners());
        assertEquals("Planner 1", activityPlannerBook.getPlannerName(1));
        assertEquals("Planner 2", activityPlannerBook.getPlannerName(2));
        assertEquals("Planner 3", activityPlannerBook.getPlannerName(3));
        assertFalse(activityPlannerBook.hasAvailableSpot());
        assertFalse(activityPlannerBook.indexIsValid(-1));
        assertTrue(activityPlannerBook.indexIsValid(1));
        assertTrue(activityPlannerBook.indexIsValid(2));
        assertTrue(activityPlannerBook.indexIsValid(3));
        assertFalse(activityPlannerBook.indexIsValid(4));
    }

    @Test
    void testChangePlannerName() {
        activityPlannerBook.createNewPlanner("Planner 1");
        assertEquals("Planner 1", activityPlannerBook.getPlannerName(1));
        activityPlannerBook.changePlannerName(1, "New Planner 1");
        assertEquals("New Planner 1", activityPlannerBook.getPlannerName(1));
        assertEquals(1, activityPlannerBook.getNumPlanners());
    }

    @Test
    void deleteFirstPlanner() {
        activityPlannerBook.createNewPlanner("Planner 1");
        activityPlannerBook.createNewPlanner("Planner 2");
        activityPlannerBook.createNewPlanner("Planner 3");
        activityPlannerBook.deletePlanner(1);
        assertEquals("Planner 2", activityPlannerBook.getPlannerName(1));
        assertEquals("Planner 3", activityPlannerBook.getPlannerName(2));
        assertEquals(2, activityPlannerBook.getNumPlanners());
    }

    @Test
    void deleteMiddlePlanner() {
        activityPlannerBook.createNewPlanner("Planner 1");
        activityPlannerBook.createNewPlanner("Planner 2");
        activityPlannerBook.createNewPlanner("Planner 3");
        activityPlannerBook.deletePlanner(2);
        assertEquals("Planner 1", activityPlannerBook.getPlannerName(1));
        assertEquals("Planner 3", activityPlannerBook.getPlannerName(2));
        assertEquals(2, activityPlannerBook.getNumPlanners());
    }

    @Test
    void deleteLastPlanner() {
        activityPlannerBook.createNewPlanner("Planner 1");
        activityPlannerBook.createNewPlanner("Planner 2");
        activityPlannerBook.createNewPlanner("Planner 3");
        activityPlannerBook.deletePlanner(3);
        assertEquals("Planner 1", activityPlannerBook.getPlannerName(1));
        assertEquals("Planner 2", activityPlannerBook.getPlannerName(2));
        assertEquals(2, activityPlannerBook.getNumPlanners());
    }

    @Test
    void deleteOnlyPlanner() {
        activityPlannerBook.createNewPlanner("Planner 1");
        assertEquals(1, activityPlannerBook.getNumPlanners());
        activityPlannerBook.deletePlanner(1);
        assertTrue(activityPlannerBook.isEmpty());
    }
}
