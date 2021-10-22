package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// Represents an activity planner which holds activities
public class ActivityPlanner implements Writable {
    public static final int HOURS  = 24;
    private String name;
    private int numActivities;
    private ArrayList<Activity> activities;

    /*
     * MODIFIES: this
     * EFFECTS: name is set to plannerName
     *          activities is dynamically allocated
     *          activityPlannerTable is dynamically allocated
     */
    public ActivityPlanner(String plannerName) {
        name = plannerName;
        numActivities = 0;
        activities = new ArrayList<>();
    }


    public int getNumActivities() {
        return numActivities;
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        name = newName;
    }

    /*
     * EFFECTS: return true if the activity planner is empty
     *          return false otherwise.
     */
    public boolean isEmpty() {
        return (numActivities == 0);
    }

    /*
     * EFFECTS: returns the specified activity
     */
    public Activity getActivity(int activityNumber) {
        return activities.get(activityNumber - 1);
    }

    /*
     * EFFECTS: return true if the activity number corresponds to
     *          one of the activities in the activity planner
     *          return false otherwise.
     */
    public boolean indexIsValid(int activityNumber) {
        return (activityNumber > 0 && activityNumber <= getNumActivities());
    }

    /*
     * REQUIRES: newActivity is not null
     * MODIFIES: this
     * EFFECTS: adds the new activity to this planner
     *          return true if the addition is successful
     *          return false otherwise.
     */
    public boolean addActivity(Activity newActivity) {
        int startTime = newActivity.getStartTime();
        int duration = newActivity.getDuration();
        if (startTime < 0 || startTime >= HOURS || duration <= 0 || (startTime + duration) > HOURS) {
            return false;
        } else if (noConflict(newActivity)) {
            int insertIndex = findInsertIndex(newActivity);
            activities.add(insertIndex, newActivity);
            numActivities++;
            return true;
        } else {
            return false;
        }
    }

    /*
     * REQUIRES: activity is not null
     * EFFECTS: checks if the activity has time conflicts with the current planner
     *          return true if there is no conflict
     *          return false otherwise.
     */
    public boolean noConflict(Activity activity) {
        int startTime = activity.getStartTime();
        int duration = activity.getDuration();
        Day day = activity.getDay();
        for (Activity a : activities) {
            if (day == a.getDay()) {
                if (startTime < a.getStartTime()) {
                    if ((startTime + duration) > a.getStartTime()) {
                        return false;
                    }
                } else if (startTime == a.getStartTime()) {
                    return false;
                } else if (startTime < (a.getStartTime() + a.getDuration())) {
                    return false;
                }
            }
        }
        return true;
    }

    /*
     * REQUIRES: activity is not null
     * EFFECTS: finds the insertion index of the new activity such that
     *          activities is sorted in the order of SUN -> SAT and start time
     */
    public int findInsertIndex(Activity newActivity) {
        if (activities.isEmpty()) {
            return 0;
        } else {
            int startTime = newActivity.getStartTime();
            Day day = newActivity.getDay();
            int index;
            for (index = 0; index < activities.size(); index++) {
                if (day.ordinal() < activities.get(index).getDay().ordinal()) {
                    return index;
                } else if (day.ordinal() == activities.get(index).getDay().ordinal()) {
                    if (startTime <= activities.get(index).getStartTime()) {
                        return index;
                    }
                }
            }
            return index;
        }
    }

    /*
     * EFFECTS: returns a string representation of the current activities in the planner
     */
    public String viewActivities() {
        if (activities.isEmpty()) {
            return "There are no activities. ";
        }
        String summary = "\n";
        int number = 1;
        for (Activity activity: activities) {
            summary += number + " --> ";
            summary += activity.getBriefDescription();
            summary += " from " + activity.getStartTime() + ":00";
            summary += " to " + (activity.getStartTime() + activity.getDuration()) + ":00";
            summary += " on " + activity.getDay() + "\n";
            number++;
        }
        return summary;
    }

    /*
     * REQUIRES: activityNumber has been checked by indexIsValid method
     * MODIFIES: this
     * EFFECTS: completely removes the activity from the planner
     */
    public void deleteActivity(int activityNumber) {
        int activityIndex = activityNumber - 1;
        activities.remove(activityIndex);
        numActivities--;
    }

    /*
     * REQUIRES: activityNumber has been checked by indexIsValid method
     * EFFECTS: returns the brief description of the specified activity
     */
    public String getActivityBriefDescription(int activityIndex) {
        return activities.get(activityIndex - 1).getBriefDescription();
    }

    /*
     * REQUIRES: activityNumber has been checked by indexIsValid method
     * EFFECTS: returns the detailed description of the specified activity
     */
    public String getActivityDetailedDescription(int activityIndex) {
        return activities.get(activityIndex - 1).getDetailedDescription();
    }

    /*
     * REQUIRES: activityNumber has been checked by indexIsValid method
     * EFFECTS: returns the day of the specified activity
     */
    public Day getActivityDay(int activityIndex) {
        return activities.get(activityIndex - 1).getDay();
    }

    /*
     * REQUIRES: activityNumber has been checked by indexIsValid method
     * EFFECTS: returns the start time of the specified activity
     */
    public int getActivityStartTime(int activityIndex) {
        return activities.get(activityIndex - 1).getStartTime();
    }

    /*
     * REQUIRES: activityNumber has been checked by indexIsValid method
     * EFFECTS: returns the duration of the specified activity
     */
    public int getActivityDuration(int activityIndex) {
        return activities.get(activityIndex - 1).getDuration();
    }

    /*
     * REQUIRES: activityNumber has been checked by indexIsValid method
     * MODIFIES: Activity
     * EFFECTS: sets the brief description of the specified activity
     */
    public void setActivityBriefDescription(int activityIndex, String newBriefDescription) {
        activities.get(activityIndex - 1).setBriefDescription(newBriefDescription);
    }

    /*
     * REQUIRES: activityNumber has been checked by indexIsValid method
     * MODIFIES: Activity
     * EFFECTS: sets the detailed description of the specified activity
     */
    public void setActivityDetailedDescription(int activityIndex, String newDetailedDescription) {
        activities.get(activityIndex - 1).setDetailedDescription(newDetailedDescription);
    }

    /*
     * REQUIRES: activityNumber has been checked by indexIsValid method
     * MODIFIES: Activity
     * EFFECTS: sets the new day of the specified activity
     *          returns true if the new day has been updated
     *          returns false if nothing has changed due to time conflicts
     */
    public boolean setActivityDay(int activityNumber, Day newDay) {
        int activityIndex = activityNumber - 1;
        Activity toBeChanged = activities.get(activityIndex);
        Day currDay = toBeChanged.getDay();
        toBeChanged.setDay(newDay);
        activities.remove(activityIndex);
        numActivities--;
        if (addActivity(toBeChanged)) {
            return true;
        } else {
            toBeChanged.setDay(currDay);
            addActivity(toBeChanged);
            return false;
        }
    }

    /*
     * REQUIRES: activityNumber has been checked by indexIsValid method
     * MODIFIES: Activity
     * EFFECTS: sets the new start time of the specified activity
     *          returns true if the start time has been updated
     *          returns false if nothing has changed due to time conflicts
     */
    public boolean setStartTime(int activityNumber, int newStartTime) {
        if (newStartTime < 0 || newStartTime > HOURS - 1) {
            return false;
        }
        Activity toBeChanged = activities.get(activityNumber - 1);
        int oldStartTime = toBeChanged.getStartTime();
        if (newStartTime + toBeChanged.getDuration() > HOURS) {
            return false;
        }
        activities.remove(activityNumber - 1);
        numActivities--;
        toBeChanged.setStartTime(newStartTime);
        if (addActivity(toBeChanged)) {
            return true;
        } else {
            toBeChanged.setStartTime(oldStartTime);
            addActivity(toBeChanged);
            return false;
        }
    }

    /*
     * REQUIRES: activityNumber has been checked by indexIsValid method
     * MODIFIES: Activity
     * EFFECTS: sets the new duration of the specified activity
     *          returns true if the duration has been updated
     *          returns false if nothing has changed due to time conflicts
     */
    public boolean setDuration(int activityNumber, int newDuration) {
        if (newDuration < 0 || newDuration > HOURS) {
            return false;
        }
        Activity toBeChanged = activities.get(activityNumber - 1);
        int startTime = toBeChanged.getStartTime();
        int oldDuration = toBeChanged.getDuration();
        if (startTime + newDuration > HOURS) {
            return false;
        }
        activities.remove(activityNumber - 1);
        numActivities--;
        toBeChanged.setDuration(newDuration);
        if (addActivity(toBeChanged)) {
            return true;
        } else {
            toBeChanged.setDuration(oldDuration);
            addActivity(toBeChanged);
            return false;
        }
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("planner name", name);
        json.put("activities", activitiesToJson());
        return json;
    }

    // EFFECTS: returns activities in this activity planner as a JSON array
    private JSONArray activitiesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Activity a : activities) {
            jsonArray.put(a.toJson());
        }

        return jsonArray;
    }
}
