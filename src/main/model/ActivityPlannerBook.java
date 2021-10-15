package model;

import java.util.ArrayList;

// Represents an activity planner book which contains of 3 activity planners
public class ActivityPlannerBook {
    public static final int MAX_PLANNERS_NUMBER = 3;
    private int numPlanners;
    private String username;
    private ArrayList<ActivityPlanner> activityPlanners;

    /*
     *  MODIFIES: this
     *  EFFECTS: numPlanners is set to 0
     *           username is set to name
     *           activityPlanners is dynamically allocated
     */
    public ActivityPlannerBook(String name) {
        numPlanners = 0;
        username = name;
        activityPlanners = new ArrayList<>();
    }

    public int getNumPlanners() {
        return numPlanners;
    }

    public String getPlannerBookName() {
        return username;
    }

    public void setPlannerBookName(String newName) {
        username = newName;
    }

    /*
     * EFFECTS: return true if the activity planner book is empty
     *          return false otherwise.
     */
    public boolean isEmpty() {
        return numPlanners == 0;
    }

    /*
     * EFFECTS: check if plannerNumber does correspond to one of the existing planners in the planner book
     *          return true if it does
     *          return false otherwise.
     */
    public boolean indexIsValid(int plannerNumber) {
        int index = plannerNumber - 1;
        return (index >= 0 && index < numPlanners);
    }

    /*
     * REQUIRES: plannerNumber has been checked by indexIsValid method
     * EFFECTS: returns the corresponding activity planner
     */
    public ActivityPlanner getActivityPlanner(int plannerNumber) {
        return activityPlanners.get(plannerNumber - 1);
    }

    /*
     * REQUIRES: plannerNumber has been checked by indexIsValid method
     * EFFECTS: returns the corresponding activity planner name
     */
    public String getPlannerName(int plannerNumber) {
        return activityPlanners.get(plannerNumber - 1).getName();
    }

    /*
     *  EFFECTS: return true if numPlanners has not exceeded MAX_PLANNERS_NUMBER
     *           return false otherwise.
     */
    public boolean hasAvailableSpot() {
        return numPlanners < MAX_PLANNERS_NUMBER;
    }

    /*
     * REQUIRES: the activity planner book has available spot
     * MODIFIES: this
     * EFFECTS: adds a new planner to this activity planner book
     */
    public void createNewPlanner(String plannerName) {
        ActivityPlanner newPlanner = new ActivityPlanner(plannerName);
        activityPlanners.add(newPlanner);
        numPlanners++;
    }

    /*
     * REQUIRES: plannerNumber has been checked by indexIsValid method
     * MODIFIES: ActivityPlanner
     * EFFECTS: changes the name of the corresponding planner
     */
    public void changePlannerName(int plannerNumber, String plannerName) {
        activityPlanners.get(plannerNumber - 1).setName(plannerName);
    }

    /*
     * REQUIRES: plannerNumber has been checked by indexIsValid method
     * MODIFIES: this
     * EFFECTS: removes the planner from the planner book
     */
    public void deletePlanner(int plannerNumber) {
        int index = plannerNumber - 1;
        activityPlanners.remove(index);
        numPlanners--;
    }
}
