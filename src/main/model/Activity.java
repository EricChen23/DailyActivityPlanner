package model;

// Represents an activity having its brief description, detailed description, and corresponding time (24h format)
public class Activity {
    private String briefDescription;    // the brief description of the activity
    private String detailedDescription; // the detailed description of the activity
    private int startTime;              // the starting time of the activity in 24h format
    private int endTime;                // the ending time of the activity in 24h format

    /*
     * REQUIRES: briefDescription has a non-zero length
     *           detailedDescription has a non-zero length
     *           startTime >= 0 and startTime <= 24
     *           endTime > startTime and endTime <= 24
     * EFFECTS: briefDescription of the activity is set to userBriefDescription
     *          detailedDescription of the activity is set to userDetailedDescription
     *          startTime of the activity is set to userStartTime
     *          endTime of the activity is set to userEndTime
     */
    public Activity(String userBriefDescription, String userDetailedDescription, int userStartTime, int userEndTime) {
        briefDescription = userBriefDescription;
        detailedDescription = userDetailedDescription;
        startTime = userStartTime;
        endTime = userEndTime;
    }

    public String getBriefDescription() {
        return briefDescription;
    }

    public String getDetailedDescription() {
        return detailedDescription;
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
