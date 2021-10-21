package persistence;

import model.Activity;
import model.ActivityPlanner;
import model.ActivityPlannerBook;
import model.Day;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads ActivityPlannerBook from JSON data stored in file
// With reference to JsonSerializationDemo
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads ActivityPlannerBook from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ActivityPlannerBook read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseActivityPlannerBook(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses ActivityPlannerBook from JSON object and returns it
    private ActivityPlannerBook parseActivityPlannerBook(JSONObject jsonObject) {
        String name = jsonObject.getString("planner book name");
        ActivityPlannerBook apb = new ActivityPlannerBook(name);
        addActivityPlanners(apb, jsonObject);
        return apb;
    }

    // MODIFIES: apb
    // EFFECTS: parses Activity Planner from JSON object and adds them to ActivityPlannerBook
    private void addActivityPlanners(ActivityPlannerBook apb, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("activityPlanners");
        for (Object json : jsonArray) {
            JSONObject nextActivityPlanner = (JSONObject) json;
            addActivityPlanner(apb, nextActivityPlanner);
        }
    }

    // MODIFIES: apb
    // EFFECTS: parses Activity Planner from JSON object and adds it to ActivityPlannerBook
    private void addActivityPlanner(ActivityPlannerBook apb, JSONObject jsonObject) {
        String name = jsonObject.getString("planner name");
        JSONArray jsonArray = jsonObject.getJSONArray("activities");
        ActivityPlanner ap = new ActivityPlanner(name);
        for (Object json : jsonArray) {
            JSONObject nextActivity = (JSONObject) json;
            String briefDescription = nextActivity.getString("briefDescription");
            String detailedDescription = nextActivity.getString("detailedDescription");
            Day day = Day.valueOf(nextActivity.getString("day"));
            int startTime = nextActivity.getInt("startTime");
            int duration = nextActivity.getInt("duration");
            Activity a = new Activity(briefDescription, detailedDescription, day, startTime, duration);
            ap.addActivity(a);
        }
        apb.loadActivityPlanner(ap);
    }
}

