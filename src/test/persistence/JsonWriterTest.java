package persistence;

import model.Activity;
import model.ActivityPlannerBook;
import model.Day;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

// With reference to JsonSerializationDemo
public class JsonWriterTest extends JsonTest{
    @Test
    void testWriterInvalidFile() {
        try {
            ActivityPlannerBook apb = new ActivityPlannerBook("Test");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            System.out.println("Successfully caught IOException");
        }
    }

    @Test
    void testWriterEmptyBook() {
        try {
            ActivityPlannerBook apb = new ActivityPlannerBook("Eric");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyBook.json");
            writer.open();
            writer.write(apb);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyBook.json");
            apb = reader.read();
            assertEquals("Eric", apb.getPlannerBookName());
            assertEquals(0, apb.getNumPlanners());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterOnePlannerNoActivity() {
        try {
            ActivityPlannerBook apb = new ActivityPlannerBook("Test");
            apb.createNewPlanner("Empty");
            JsonWriter writer = new JsonWriter("./data/testWriterOnePlannerNoActivity.json");
            writer.open();
            writer.write(apb);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterOnePlannerNoActivity.json");
            apb = reader.read();
            assertEquals(1, apb.getNumPlanners());
            assertTrue(apb.getActivityPlanner(1).isEmpty());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterOnePlannerSeveralActivities() {
        try {
            ActivityPlannerBook apb = new ActivityPlannerBook("Test");
            apb.createNewPlanner("Empty");
            Activity activityOne = new Activity("Act 1", "Activity 1", Day.SUN, 6, 6);
            Activity activityTwo = new Activity("Act 2", "Activity 2", Day.TUE, 6, 6);
            Activity activityThree = new Activity("Act 3", "Activity 3", Day.THUR, 6, 6);
            Activity activityFour = new Activity("Act 4", "Activity 4", Day.WED, 6, 6);
            Activity activityFive = new Activity("Act 5", "Activity 5", Day.FRI, 6, 6);
            apb.getActivityPlanner(1).addActivity(activityOne);
            apb.getActivityPlanner(1).addActivity(activityTwo);
            apb.getActivityPlanner(1).addActivity(activityThree);
            apb.getActivityPlanner(1).addActivity(activityFour);
            apb.getActivityPlanner(1).addActivity(activityFive);

            JsonWriter writer = new JsonWriter("./data/testWriterOnePlannerSeveralActivities.json");
            writer.open();
            writer.write(apb);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterOnePlannerSeveralActivities.json");
            apb = reader.read();
            assertEquals(1, apb.getNumPlanners());
            assertFalse(apb.getActivityPlanner(1).isEmpty());
            checkActivity("Act 1", "Activity 1", Day.SUN, 6, 6, apb.getActivityPlanner(1).getActivity(1));
            checkActivity("Act 2", "Activity 2", Day.TUE, 6, 6, apb.getActivityPlanner(1).getActivity(2));
            checkActivity("Act 4", "Activity 4", Day.WED, 6, 6, apb.getActivityPlanner(1).getActivity(3));
            checkActivity("Act 3", "Activity 3", Day.THUR, 6, 6, apb.getActivityPlanner(1).getActivity(4));
            checkActivity("Act 5", "Activity 5", Day.FRI, 6, 6, apb.getActivityPlanner(1).getActivity(5));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterSeveralEmptyPlanners() {
        try {
            ActivityPlannerBook apb = new ActivityPlannerBook("TestBook");
            apb.createNewPlanner("Test1");
            apb.createNewPlanner("Test2");
            apb.createNewPlanner("Test3");

            Activity activity = new Activity("Act in 2", "Activity in Test 2", Day.TUE, 7,12);
            apb.getActivityPlanner(2).addActivity(activity);

            JsonWriter writer = new JsonWriter("./data/testWriterSeveralEmptyPlanners.json");
            writer.open();
            writer.write(apb);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterSeveralEmptyPlanners.json");
            apb = reader.read();
            assertEquals(3, apb.getNumPlanners());
            assertTrue(apb.getActivityPlanner(1).isEmpty());
            assertFalse(apb.getActivityPlanner(2).isEmpty());
            assertTrue(apb.getActivityPlanner(3).isEmpty());
            checkActivity("Act in 2", "Activity in Test 2", Day.TUE, 7,12, apb.getActivityPlanner(2).getActivity(1));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
