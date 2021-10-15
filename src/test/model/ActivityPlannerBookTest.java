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
        assertTrue(activityPlannerBook.isEmpty());
    }

    @Test
    void testSetPlannerBookName() {
        activityPlannerBook.setPlannerBookName("New Book");
        assertEquals("New Book", activityPlannerBook.getPlannerBookName());
    }

    @Test
    void testAddOnePlanner() {
        activityPlannerBook.createNewPlanner("Planner 1");
        assertEquals(1, activityPlannerBook.getNumPlanners());
        assertEquals("Planner 1", activityPlannerBook.getPlannerName(1));
        assertTrue(activityPlannerBook.indexIsValid(1));
        assertFalse(activityPlannerBook.indexIsValid(2));
        assertTrue(activityPlannerBook.hasAvailableSpot());
        assertFalse(activityPlannerBook.isEmpty());

        ActivityPlanner activityPlanner = activityPlannerBook.getActivityPlanner(1);
        assertEquals("Planner 1", activityPlanner.getName());
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
        assertFalse(activityPlannerBook.isEmpty());

        ActivityPlanner activityPlannerOne = activityPlannerBook.getActivityPlanner(1);
        ActivityPlanner activityPlannerTwo = activityPlannerBook.getActivityPlanner(2);
        assertEquals("Planner 1", activityPlannerOne.getName());
        assertEquals("Planner 2", activityPlannerTwo.getName());
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
        assertFalse(activityPlannerBook.isEmpty());

        ActivityPlanner activityPlannerOne = activityPlannerBook.getActivityPlanner(1);
        ActivityPlanner activityPlannerTwo = activityPlannerBook.getActivityPlanner(2);
        ActivityPlanner activityPlannerThree = activityPlannerBook.getActivityPlanner(3);
        assertEquals("Planner 1", activityPlannerOne.getName());
        assertEquals("Planner 2", activityPlannerTwo.getName());
        assertEquals("Planner 3", activityPlannerThree.getName());
    }

    @Test
    void testChangePlannerName() {
        activityPlannerBook.createNewPlanner("Planner 1");
        assertEquals("Planner 1", activityPlannerBook.getPlannerName(1));
        activityPlannerBook.changePlannerName(1, "New Planner 1");
        assertEquals("New Planner 1", activityPlannerBook.getPlannerName(1));
        assertEquals(1, activityPlannerBook.getNumPlanners());
        assertFalse(activityPlannerBook.isEmpty());
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
        assertFalse(activityPlannerBook.isEmpty());

        ActivityPlanner activityPlannerOne = activityPlannerBook.getActivityPlanner(1);
        ActivityPlanner activityPlannerTwo = activityPlannerBook.getActivityPlanner(2);
        assertEquals("Planner 2", activityPlannerOne.getName());
        assertEquals("Planner 3", activityPlannerTwo.getName());
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
        assertFalse(activityPlannerBook.isEmpty());

        ActivityPlanner activityPlannerOne = activityPlannerBook.getActivityPlanner(1);
        ActivityPlanner activityPlannerTwo = activityPlannerBook.getActivityPlanner(2);
        assertEquals("Planner 1", activityPlannerOne.getName());
        assertEquals("Planner 3", activityPlannerTwo.getName());
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
        assertFalse(activityPlannerBook.isEmpty());

        ActivityPlanner activityPlannerOne = activityPlannerBook.getActivityPlanner(1);
        ActivityPlanner activityPlannerTwo = activityPlannerBook.getActivityPlanner(2);
        assertEquals("Planner 1", activityPlannerOne.getName());
        assertEquals("Planner 2", activityPlannerTwo.getName());
    }

    @Test
    void deleteOnlyPlanner() {
        activityPlannerBook.createNewPlanner("Planner 1");
        assertEquals(1, activityPlannerBook.getNumPlanners());
        activityPlannerBook.deletePlanner(1);
        assertTrue(activityPlannerBook.isEmpty());
    }
}
