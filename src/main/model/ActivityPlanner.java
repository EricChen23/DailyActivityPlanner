package model;

import java.util.ArrayList;

public class ActivityPlanner {
    public static final int HOURS  = 24;
    public static final int DAYS = 7;
    private String name;
    private int numActivities;
    private ArrayList<Activity> activities;
    private Activity[][] activityPlannerTable; // A planner table with 24 rows and 7 columns representing
                                       // 24 hours of the day and 7 days of the week respectively

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
        activityPlannerTable = new Activity[HOURS][DAYS];
    }

    public Activity[][] getActivityPlannerTable() {
        return activityPlannerTable;
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
            for (int i = 0; i < duration; i++) {
                activityPlannerTable[startTime + i][newActivity.getDay().ordinal()] = newActivity;
            }
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
        int day = activity.getDay().ordinal();
        for (int i  = 0; i < duration; i++) {
            if (activityPlannerTable[startTime + i][day] != null) {
                return false;
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
        Activity activity = activities.get(activityIndex);
        activities.remove(activityIndex);
        for (int i = 0; i < activity.getDuration(); i++) {
            activityPlannerTable[activity.getStartTime() + i][activity.getDay().ordinal()] = null;
        }
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
        int duration = toBeChanged.getDuration();
        int startTime = toBeChanged.getStartTime();
        Day currDay = toBeChanged.getDay();
        for (int i = 0; i < duration; i++) {
            activityPlannerTable[startTime + i][currDay.ordinal()] = null;
        }
        toBeChanged.setDay(newDay);
        if (noConflict(toBeChanged)) {
            activities.remove(activityIndex);
            numActivities--;
            return addActivity(toBeChanged);
        } else {
            toBeChanged.setDay(currDay);
            for (int i = 0; i < duration; i++) {
                activityPlannerTable[startTime + i][currDay.ordinal()] = toBeChanged;
            }
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
    public boolean setStartTime(int activityIndex, int newStartTime) {
        if (newStartTime < 0 || newStartTime > HOURS - 1) {
            return false;
        }
        Activity toBeChanged = activities.get(activityIndex - 1);
        int oldStartTime = toBeChanged.getStartTime();
        if (newStartTime + toBeChanged.getDuration() > HOURS) {
            return false;
        }
        for (int i = 0; i < toBeChanged.getDuration(); i++) {
            activityPlannerTable[oldStartTime + i][toBeChanged.getDay().ordinal()] = null;
        }
        toBeChanged.setStartTime(newStartTime);
        if (noConflict(toBeChanged)) {
            activities.remove(activityIndex - 1);
            numActivities--;
            return addActivity(toBeChanged);
        } else {
            toBeChanged.setStartTime(oldStartTime);
            for (int i = 0; i < toBeChanged.getDuration(); i++) {
                activityPlannerTable[oldStartTime + i][toBeChanged.getDay().ordinal()] = toBeChanged;
            }
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
    public boolean setDuration(int activityIndex, int newDuration) {
        if (newDuration < 0 || newDuration > HOURS) {
            return false;
        }
        Activity toBeChanged = activities.get(activityIndex - 1);
        int oldDuration = toBeChanged.getDuration();
        if (toBeChanged.getStartTime() + newDuration > HOURS) {
            return false;
        }
        for (int i = 0; i < oldDuration; i++) {
            activityPlannerTable[toBeChanged.getStartTime() + i][toBeChanged.getDay().ordinal()] = null;
        }
        toBeChanged.setDuration(newDuration);
        if (noConflict(toBeChanged)) {
            activities.remove(activityIndex - 1);
            numActivities--;
            return addActivity(toBeChanged);
        } else {
            toBeChanged.setDuration(oldDuration);
            for (int i = 0; i < toBeChanged.getDuration(); i++) {
                activityPlannerTable[toBeChanged.getStartTime() + i][toBeChanged.getDay().ordinal()] = toBeChanged;
            }
            return false;
        }
    }
}
