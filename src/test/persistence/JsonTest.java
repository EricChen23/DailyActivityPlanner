package persistence;

import model.Activity;
import model.Day;

import static org.junit.jupiter.api.Assertions.assertEquals;

// With reference to JsonSerializationDemo
public class JsonTest {
    protected void checkActivity(String brief, String detailed, Day day, int startTime, int duration, Activity activity) {
        assertEquals(brief, activity.getBriefDescription());
        assertEquals(detailed, activity.getDetailedDescription());
        assertEquals(day, activity.getDay());
        assertEquals(startTime, activity.getStartTime());
        assertEquals(duration, activity.getDuration());
    }
}
