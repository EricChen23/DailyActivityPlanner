package model;

/*
 * Represents an activity having its brief description, detailed description,
 * day of occurrence, and corresponding time (24h format)
 */
public class Activity {
    private String briefDescription;    // the brief description of the activity
    private String detailedDescription; // the detailed description of the activity
    private Day day;                    // the day of occurrence of the activity
    private int startTime;              // the starting time of the activity in 24h format
    private int duration;                // the duration of the activity in whole hours

    /*
     * REQUIRES: startTime >= 0 and startTime <= 23
     *           duration > 0
     *           startTime + duration <= 24
     * MODIFIES: this
     * EFFECTS: briefDescription of the activity is set to activityBriefDescription
     *          detailedDescription of the activity is set to activityDetailedDescription
     *          day of the activity is set to activityDay
     *          startTime of the activity is set to activityStartTime
     *          duration of the activity is set to activityDuration
     */
    public Activity(String activityBriefDescription, String activityDetailedDescription,
                    Day activityDay, int activityStartTime, int activityDuration) {
        briefDescription = activityBriefDescription;
        detailedDescription = activityDetailedDescription;
        day = activityDay;
        startTime = activityStartTime;
        duration = activityDuration;
    }

    public String getBriefDescription() {
        return briefDescription;
    }

    public String getDetailedDescription() {
        return detailedDescription;
    }

    public Day getDay() {
        return day;
    }

    public int getStartTime() {
        return startTime;
    }

    public int getDuration() {
        return duration;
    }

    /*
     * MODIFIES: this
     * EFFECTS: briefDescription is updated to updatedBriefDescription
     */
    public void setBriefDescription(String updatedBriefDescription) {
        briefDescription = updatedBriefDescription;
    }

    /*
     * MODIFIES: this
     * EFFECTS: detailedDescription is updated to updatedDetailedDescription
     */
    public void setDetailedDescription(String updatedDetailedDescription) {
        detailedDescription = updatedDetailedDescription;
    }

    /*
     * REQUIRES: time on updatedDay has no conflict
     * MODIFIES: this
     * EFFECTS: day is updated to updatedDay
     */
    public void setDay(Day updatedDay) {
        day = updatedDay;
    }

    /*
     * REQUIRES: updatedStartTime >= 0
     *           updatedStartTime <= 23
     *           updatedStartTime + duration <= 24
     * MODIFIES: this
     * EFFECTS: startTime is updated to updatedStartTime
     */
    public void setStartTime(int updatedStartTime) {
        startTime = updatedStartTime;
    }

    /*
     * REQUIRES: startTime + updatedDuration <= 24
     * MODIFIES: this
     * EFFECTS: endTime is updated to updatedEndTime
     */
    public void setDuration(int updatedDuration) {
        duration = updatedDuration;
    }

    /*
     * EFFECTS: returns a string representation of activity
     */
    @Override
    public String toString() {
        return "Brief description: " + briefDescription + "\n" + "Detailed description: " + detailedDescription + "\n"
                + "Day: " + day + "\n" + "Start time: " + startTime + " h\n" + "Duration: " + duration + " h\n";
    }
}
