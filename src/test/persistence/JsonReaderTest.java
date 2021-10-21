package persistence;

import model.ActivityPlanner;
import model.ActivityPlannerBook;
import model.Day;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

// Reference to JsonSerializationDemo
class JsonReaderTest extends JsonTest{

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            ActivityPlannerBook apb = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            System.out.println("Successfully caught IOException");
        }
    }

    @Test
    void testReaderEmptyBook() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyBook.json");
        try {
            ActivityPlannerBook apb = reader.read();
            assertEquals("Eric", apb.getPlannerBookName());
            assertEquals(0, apb.getNumPlanners());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderOnePlannerNoActivity() {
        JsonReader reader = new JsonReader("./data/testReaderOnePlannerNoActivity.json");
        try {
            ActivityPlannerBook apb = reader.read();
            assertEquals("Eric", apb.getPlannerBookName());
            assertEquals(1, apb.getNumPlanners());
            ActivityPlanner ap = apb.getActivityPlanner(1);
            assertEquals("Planner1", ap.getName());
            assertEquals(0, ap.getNumActivities());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderOnePlannerSeveralActivities() {
        JsonReader reader = new JsonReader("./data/testReaderOnePlannerSeveralActivities.json");
        try {
            ActivityPlannerBook apb = reader.read();
            assertEquals(1, apb.getNumPlanners());
            assertFalse(apb.getActivityPlanner(1).isEmpty());
            checkActivity("Act 1", "Activity 1", Day.SUN, 6, 6, apb.getActivityPlanner(1).getActivity(1));
            checkActivity("Act 2", "Activity 2", Day.TUE, 6, 6, apb.getActivityPlanner(1).getActivity(2));
            checkActivity("Act 4", "Activity 4", Day.WED, 6, 6, apb.getActivityPlanner(1).getActivity(3));
            checkActivity("Act 3", "Activity 3", Day.THUR, 6, 6, apb.getActivityPlanner(1).getActivity(4));
            checkActivity("Act 5", "Activity 5", Day.FRI, 6, 6, apb.getActivityPlanner(1).getActivity(5));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderSeveralEmptyPlanners() {
        JsonReader reader = new JsonReader("./data/testReaderSeveralEmptyPlanners.json");
        try {
            ActivityPlannerBook apb = reader.read();
            assertEquals(3, apb.getNumPlanners());
            assertTrue(apb.getActivityPlanner(1).isEmpty());
            assertFalse(apb.getActivityPlanner(2).isEmpty());
            assertTrue(apb.getActivityPlanner(3).isEmpty());
            checkActivity("Act in 2", "Activity in Test 2", Day.TUE, 7,12, apb.getActivityPlanner(2).getActivity(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
