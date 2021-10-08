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
    private int endTime;                // the ending time of the activity in 24h format

    /*
     * REQUIRES: briefDescription has a non-zero length
     *           detailedDescription has a non-zero length
     *           startTime >= 0 and startTime <= 24
     *           endTime > startTime and endTime <= 24
     * EFFECTS: briefDescription of the activity is set to userBriefDescription
     *          detailedDescription of the activity is set to userDetailedDescription
     *          day of the activity is set to userDay
     *          startTime of the activity is set to userStartTime
     *          endTime of the activity is set to userEndTime
     */
    public Activity(String userBriefDescription, String userDetailedDescription,
                    Day userDay, int userStartTime, int userEndTime) {
        briefDescription = userBriefDescription;
        detailedDescription = userDetailedDescription;
        day = userDay;
        startTime = userStartTime;
        endTime = userEndTime;
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

    public int getEndTime() {
        return endTime;
    }

    /*
     * REQUIRES: updatedBriefDescription has a non-zero length
     * MODIFIES: this
     * EFFECTS: briefDescription is updated to updatedBriefDescription
     */
    public void modifyBriefDescription(String updatedBriefDescription) {
        briefDescription = updatedBriefDescription;
    }

    /*
     * REQUIRES: updatedDetailedDescription has a non-zero length
     * MODIFIES: this
     * EFFECTS: detailedDescription is updated to updatedDetailedDescription
     */
    public void modifyDetailedDescription(String updatedDetailedDescription) {
        detailedDescription = updatedDetailedDescription;
    }

    /*
     * REQUIRES: time on updatedDay has no conflict
     * MODIFIES: this
     * EFFECTS: day is updated to updatedDay
     */
    public void modifyDay(Day updatedDay) {
        day = updatedDay;
    }

    /*
     * REQUIRES: updatedStartTime >= 0
     *           updatedStartTime < endTime
     * MODIFIES: this
     * EFFECTS: startTime is updated to updatedStartTime
     */
    public void modifyStartTime(int updatedStartTime) {
        startTime = updatedStartTime;
    }

    /*
     * REQUIRES: updatedEndTime > startTime
     *           updatedEndTime <= 24
     * MODIFIES: this
     * EFFECTS: endTime is updated to updatedEndTime
     */
    public void modifyEndTime(int updatedEndTime) {
        endTime = updatedEndTime;
    }

    /*
     * EFFECTS: returns a string representation of activity
     */
    @Override
    public String toString() {
        return "Brief description: " + briefDescription + "\n" + "Detailed description: " + detailedDescription + "\n"
                + "Start time: " + startTime + "\n" + "End time: " + endTime;
    }
}
