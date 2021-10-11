package model;

import model.Activity;
import java.util.ArrayList;

public class ActivityPlanner {
    public static final int HOURS  = 24;
    public static final int DAYS = 7;
    String name;
    int numActivities = 0;
    ArrayList<Activity> activities;
    Activity[][] activityPlannerTable; // A planner table with 24 rows and 7 columns representing
                                       // 24 hours of the day and 7 days of the week respectively

    /*
     * REQUIRES: plannerName has a non-zero length
     * EFFECTS: name is set to plannerName
     *          activities is dynamically allocated
     *          activityPlannerTable is dynamically allocated
     */
    public ActivityPlanner(String plannerName) {
        name = plannerName;
        activities = new ArrayList<>();
        activityPlannerTable = new Activity[HOURS][DAYS];
    }

    public boolean isEmpty() {
        return (numActivities == 0);
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

    /* REQUIRES: newActivity is not null
     * MODIFIES:
     */
    public boolean addActivity(Activity newActivity) {
        int startTime = newActivity.getStartTime();
        int duration = newActivity.getDuration();
        if (startTime < 0 || startTime >= HOURS || (startTime + duration) > HOURS) {
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

    public int findInsertIndex(Activity newActivity) {
        if (activities.isEmpty()) {
            return 0;
        } else {
            int startTime = newActivity.getStartTime();
            Day day = newActivity.getDay();
            int index;
            for (index = 0; index < activities.size(); index++) {
                if (day.ordinal() <= activities.get(index).getDay().ordinal()
                        && startTime < activities.get(index).getStartTime()) {
                    return index;
                }
            }
            return index;
        }
    }

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

    public void deleteActivity(int activityIndex) {
        activities.remove(activityIndex - 1);
        numActivities--;
    }

    public void setActivityBriefDescription(int activityIndex, String newBriefDescription) {
        activities.get(activityIndex - 1).setBriefDescription(newBriefDescription);
    }

    public void setActivityDetailedDescription(int activityIndex, String newDetailedDescription) {
        activities.get(activityIndex - 1).setDetailedDescription(newDetailedDescription);
    }

    public boolean setActivityDay(int activityIndex, Day newDay) {
        Activity toBeChanged = activities.get(activityIndex - 1);
        int duration = toBeChanged.getDuration();
        int startTime = toBeChanged.getStartTime();
        Day currDay = toBeChanged.getDay();
        for (int i = 0; i < duration; i++) {
            activityPlannerTable[startTime + i][currDay.ordinal()] = null;
        }
        toBeChanged.setDay(newDay);
        if (noConflict(toBeChanged)) {
            activities.remove(activityIndex - 1);
            numActivities--;
            return addActivity(toBeChanged);
        } else {
            toBeChanged.setDay(currDay);
            return false;
        }
    }

    public boolean setStartTime(int activityIndex, int newStartTime) {
        Activity toBeChanged = activities.get(activityIndex - 1);
        int oldStartTime = toBeChanged.getStartTime();
        int duration = toBeChanged.getDuration();
        for (int i = 0; i < duration; i++) {
            activityPlannerTable[oldStartTime + i][toBeChanged.getDay().ordinal()] = null;
        }
        toBeChanged.setStartTime(newStartTime);
        if (noConflict(toBeChanged)) {
            activities.remove(activityIndex - 1);
            numActivities--;
            return addActivity(toBeChanged);
        } else {
            toBeChanged.setStartTime(oldStartTime);
            return false;
        }
    }

    public boolean setDuration(int activityIndex, int newDuration) {
        Activity toBeChanged = activities.get(activityIndex - 1);
        int startTime = toBeChanged.getStartTime();
        int oldDuration = toBeChanged.getDuration();
        for (int i = 0; i < oldDuration; i++) {
            activityPlannerTable[startTime + i][toBeChanged.getDay().ordinal()] = null;
        }
        toBeChanged.setDuration(newDuration);
        if (noConflict(toBeChanged)) {
            activities.remove(activityIndex - 1);
            numActivities--;
            return addActivity(toBeChanged);
        } else {
            toBeChanged.setDuration(oldDuration);
            return false;
        }
    }

    public String viewActivityDetailedDescription(int activityNumber) {
        return activities.get(activityNumber - 1).getDetailedDescription();
    }

}