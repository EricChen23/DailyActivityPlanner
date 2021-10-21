package ui;

import model.Activity;
import model.ActivityPlanner;
import model.ActivityPlannerBook;
import model.Day;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// Reference to Teller App Console and JsonSerializationDemo
// Activity planner application
public class ActivityPlannerConsole {
    private ActivityPlannerBook activityPlannerBook;
    private Scanner input;
    private static final String JSON_STORE = "./data/activityplannerbook.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the ActivityPlanner application
    public ActivityPlannerConsole() throws FileNotFoundException {
        runActivityPlannerConsole();
    }

    /*
     * MODIFIES: this
     * EFFECTS: processes user input on the main menu (regarding planners)
     */
    private void runActivityPlannerConsole() {
        boolean keepGoing = true;
        String command;

        init();

        while (keepGoing) {
            displayPlannerMenu();
            command = input.next();
            command = command.toLowerCase();
            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processPlannerCommand(command);
            }
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: processes user command on the main menu (regarding planners)
     */
    private void processPlannerCommand(String command) {
        switch (command) {
            case "n":
                createPlanner();
                break;
            case "c":
                choosePlannerPrompt();
                break;
            case "ch":
                changePlannerNamePrompt();
                break;
            case "d":
                deletePlannerPrompt();
                break;
            case "s":
                saveBook();
            case "l":
                loadBook();
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: initialize activity planner book
     */
    private void init() {
        input = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        System.out.println("Whats your first name?");
        activityPlannerBook = new ActivityPlannerBook(input.next());
    }

    /*
     * MODIFIES: this
     * EFFECTS: processes user input on the secondary menu (regarding activities)
     */
    private void runPlannerPrompt(int plannerNumber) {
        boolean keepGoing = true;
        String command;

        while (keepGoing) {
            displayActivityMenu(plannerNumber);
            command = input.next();
            command = command.toLowerCase();
            if (command.equals("b")) {
                keepGoing = false;
            } else {
                processActivityCommand(command, plannerNumber);
            }
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: processes user command on the secondary menu (regarding activities)
     */
    private void processActivityCommand(String command, int plannerNumber) {
        if (command.equals("a")) {
            addActivity(plannerNumber);
        } else if (command.equals("d")) {
            deleteActivity(plannerNumber);
        } else if (command.equals("mbd")) {
            modifyBriefDescription(plannerNumber);
        } else if (command.equals("mdd")) {
            modifyDetailedDescription(plannerNumber);
        } else if (command.equals("mday")) {
            modifyDay(plannerNumber);
        } else if (command.equals("ms")) {
            modifyStartTime(plannerNumber);
        } else if (command.equals("md")) {
            modifyDuration(plannerNumber);
        } else if (command.equals("v")) {
            viewActivities(plannerNumber);
        }
    }

    // EFFECTS: displays menu of options to user on the main menu (regarding planners)
    private void displayPlannerMenu() {
        System.out.println("\nSelect from: ");
        System.out.println("\tn -> create a new activity planner");
        System.out.println("\tc -> choose an existing activity planner");
        System.out.println("\tch -> change name of an existing activity planner");
        System.out.println("\td -> delete an existing activity planner");
        System.out.println("\ts -> save activity planner book to file");
        System.out.println("\tl -> load activity planner book from file");
        System.out.println("\tq -> quit");
    }

    // EFFECTS: displays menu of options to user on the secondary menu (regarding activities)
    private void displayActivityMenu(int plannerNumber) {
        System.out.println("\n" + activityPlannerBook.getPlannerName(plannerNumber) + " ----Select from: ");
        System.out.println("\ta -> add activity");
        System.out.println("\td -> delete activity");
        System.out.println("\tmbd -> modify brief description of the activity");
        System.out.println("\tmdd -> modify detailed description of the activity");
        System.out.println("\tmday -> modify day of the activity");
        System.out.println("\tms -> modify start time of the activity");
        System.out.println("\tmd -> modify duration of the activity");
        System.out.println("\tv -> view activities");
        System.out.println("\tb -> back to main menu");
    }

    // EFFECTS: displays the planner names
    private void displayPlannerNames() {
        for (int i = 1; i <= activityPlannerBook.getNumPlanners(); i++) {
            System.out.print("Planner " + i + ": ");
            System.out.print(activityPlannerBook.getPlannerName(i) + "; \t");
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: prompts the user to create a new planner
     */
    private void createPlanner() {
        if (activityPlannerBook.hasAvailableSpot()) {
            String plannerName;
            System.out.print("Enter the name of the new activity planner: ");
            System.out.println("(Planner name cannot be empty and has to be one word)");
            plannerName = input.next();
            activityPlannerBook.createNewPlanner(plannerName);
            System.out.println("\n*" + plannerName + " is successfully created*");
        } else {
            System.out.println("\nExceeded max number of planners. Please delete or modify an existing planner.");
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: prompts the user to change the name of the selected planner
     */
    private void changePlannerName(int plannerNumber) {
        System.out.println("\nPlanner to be changed: " + activityPlannerBook.getPlannerName(plannerNumber));
        System.out.print("Enter the name of the new activity planner: ");
        System.out.println("(Planner name cannot be empty and has to be one word)");
        String plannerName;
        plannerName = input.next();
        activityPlannerBook.changePlannerName(plannerNumber, plannerName);
        System.out.println("Planner name has been updated to: " + activityPlannerBook.getPlannerName(plannerNumber));
    }

    /*
     * MODIFIES: this
     * EFFECTS: prompts the user to select the planner for name change
     */
    private void changePlannerNamePrompt() {
        if (activityPlannerBook.getNumPlanners() == 0) {
            System.out.println("\nThere are no existing planners. ");
        } else {
            displayPlannerNames();
            System.out.println("\nPlease enter the planner number for name change: ");
            String inputStr = input.next();
            try {
                int plannerNumber = Integer.parseInt(inputStr);
                if (activityPlannerBook.indexIsValid(plannerNumber)) {
                    changePlannerName(plannerNumber);
                } else {
                    System.out.println("Invalid input. ");
                    System.out.println();
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. ");
                System.out.println();
            }
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: prompts the user to choose one of the existing planners for further actions
     */
    private void choosePlannerPrompt() {
        if (activityPlannerBook.isEmpty()) {
            System.out.println("\nThere are no existing planners. ");
        } else {
            displayPlannerNames();
            System.out.println("\nPlease enter the planner number: ");
            String inputStr = input.next();
            try {
                int plannerNumber = Integer.parseInt(inputStr);
                if (activityPlannerBook.indexIsValid(plannerNumber)) {
                    runPlannerPrompt(plannerNumber);
                } else {
                    System.out.println("Invalid input. ");
                    System.out.println();
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. ");
                System.out.println();
            }
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: prompts the user to delete one of the existing planners
     */
    private void deletePlannerPrompt() {
        if (activityPlannerBook.isEmpty()) {
            System.out.print("\nThere are no existing planners." + "\n");
        } else {
            displayPlannerNames();
            System.out.println("\nPlease enter the planner number to be deleted: ");
            String inputStr = input.next();
            try {
                int plannerNumber = Integer.parseInt(inputStr);
                if (activityPlannerBook.indexIsValid(plannerNumber)) {
                    String removedPlanner = activityPlannerBook.getPlannerName(plannerNumber);
                    activityPlannerBook.deletePlanner(plannerNumber);
                    System.out.println("\n*" + removedPlanner + " has been deleted.*");
                } else {
                    System.out.println("Invalid input. ");
                    System.out.println();
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. ");
                System.out.println();
            }
        }
    }

    /*
     * REQUIRES: plannerNumber has been checked by indexIsValid method
     * MODIFIES: this
     * EFFECTS: prompts the user to add a new activity to the selected planner
     */
    private void addActivity(int plannerNumber) {
        input.nextLine();
        System.out.println("\nEnter the brief description of the new activity: ");
        String briefDescription = input.nextLine();
        System.out.println("\nEnter the detailed description of the new activity: ");
        String detailedDescription = input.nextLine();
        System.out.println("\nEnter the day the new activity occurs: (SUN, MON, TUE, WED, THUR, FRI, SAT)");
        Day day = getValidDay();
        if (day == null) {
            return;
        }
        System.out.println("\nEnter the start time of the new activity: (Between 0 - 23)");
        int startTime = getValidStartTime();
        if (startTime == -1) {
            System.out.println("Invalid input");
            return;
        }
        System.out.println("\nEnter the duration of the new activity: (Cannot go to next day)");
        int duration = getValidDuration();
        if (duration == -1) {
            System.out.println("Invalid input");
            return;
        }
        addActivity(plannerNumber, briefDescription, detailedDescription, day, startTime, duration);
    }

    /*
     * MODIFIES: this
     * EFFECTS: adds the activity to the selected planner
     *          lets the user know if the activity is successfully added or not
     */
    private void addActivity(int plannerNumber, String brief, String detailed, Day day, int startTime, int duration) {
        Activity newActivity = new Activity(brief, detailed, day, startTime, duration);
        ActivityPlanner activityPlanner = activityPlannerBook.getActivityPlanner(plannerNumber);
        if (activityPlanner.addActivity(newActivity)) {
            System.out.println("\n*New activity successfully added.*");
        } else {
            System.out.println("\nDetected time conflicts / invalid time. New activity failed to be added. ");
            System.out.println();
        }
    }

    /*
     * EFFECTS: ensures that the user input is a valid day
     *          return the user input day if it is valid
     *          returns null if it is not
     */
    private Day getValidDay() {
        Day day;
        try {
            day = Day.valueOf(input.next().toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid day entered. ");
            System.out.println();
            return null;
        }
        return day;
    }

    /*
     * EFFECTS: ensures that the user input is an integer
     *          returns the integer if it is
     *          returns -1 otherwise
     */
    private int getValidStartTime() {
        String inputStr = input.next();
        try {
            return Integer.parseInt(inputStr);
        } catch (NumberFormatException e) {
            System.out.println("Invalid startTime entered. ");
            System.out.println();
            return -1;
        }
    }

    /*
     * EFFECTS: ensures that the user input is an integer
     *          returns the integer if it is
     *          returns -1 otherwise
     */
    private int getValidDuration() {
        String inputStr = input.next();
        try {
            return Integer.parseInt(inputStr);
        } catch (NumberFormatException e) {
            System.out.println("Invalid duration entered. ");
            System.out.println();
            return -1;
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: displays the activities of the selected planner and
     *          prompts the user to select the activity for further actions
     *          returns the activity number if is valid
     *          returns -1 otherwise
     */
    private int displayActivities(int plannerNumber) {
        ActivityPlanner activityPlanner = activityPlannerBook.getActivityPlanner(plannerNumber);
        System.out.println(activityPlanner.viewActivities());
        if (!activityPlanner.isEmpty()) {
            System.out.println("Enter the activity number for action: ");
            String inputStr = input.next();
            try {
                int activityNumber = Integer.parseInt(inputStr);
                if (activityPlanner.indexIsValid(activityNumber)) {
                    return activityNumber;
                } else {
                    System.out.println("Invalid input. ");
                    System.out.println();
                    return -1;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. ");
                System.out.println();
                return -1;
            }
        }
        return -1;
    }

    /*
     * REQUIRES: plannerNumber has been checked by indexIsValid method
     * MODIFIES: this
     * EFFECTS: prompts the user to delete the selected activity from the selected planner
     */
    private void deleteActivity(int plannerNumber) {
        int activityNumber = displayActivities(plannerNumber);
        if (activityNumber != -1) {
            ActivityPlanner activityPlanner = activityPlannerBook.getActivityPlanner(plannerNumber);
            activityPlanner.deleteActivity(activityNumber);
            System.out.println("\n*The activity has been deleted*");
        }
    }

    /*
     * REQUIRES: plannerNumber has been checked by indexIsValid method
     * MODIFIES: this
     * EFFECTS: prompts the user to modify the brief description of the selected activity from the selected planner
     */
    private void modifyBriefDescription(int plannerNumber) {
        int activityNumber = displayActivities(plannerNumber);
        if (activityNumber != -1) {
            ActivityPlanner activityPlanner = activityPlannerBook.getActivityPlanner(plannerNumber);
            System.out.println("\nEnter new brief description: ");
            input.nextLine();
            activityPlanner.setActivityBriefDescription(activityNumber, input.nextLine());
            System.out.println("\n*The brief description has been updated*");
        }
    }

    /*
     * REQUIRES: plannerNumber has been checked by indexIsValid method
     * MODIFIES: this
     * EFFECTS: prompts the user to modify the detailed description of the selected activity from the selected planner
     */
    private void modifyDetailedDescription(int plannerNumber) {
        int activityNumber = displayActivities(plannerNumber);
        if (activityNumber != -1) {
            ActivityPlanner activityPlanner = activityPlannerBook.getActivityPlanner(plannerNumber);
            System.out.println("\nEnter new detailed description: ");
            input.nextLine();
            activityPlanner.setActivityDetailedDescription(activityNumber, input.nextLine());
            System.out.println("\n*The detailed description has been updated*");
        }
    }

    /*
     * REQUIRES: plannerNumber has been checked by indexIsValid method
     * MODIFIES: this
     * EFFECTS: prompts the user to modify the day of the selected activity from the selected planner
     *          lets the user know if the new day has been updated
     *          or if it fails to update due to time conflicts
     */
    private void modifyDay(int plannerNumber) {
        int activityNumber = displayActivities(plannerNumber);
        if (activityNumber != -1) {
            ActivityPlanner activityPlanner = activityPlannerBook.getActivityPlanner(plannerNumber);
            System.out.println("\nEnter new day: (SUN, MON, TUE, WED, THUR, FRI, SAT)");
            Day day;
            try {
                day = Day.valueOf(input.next().toUpperCase());
                if (activityPlanner.setActivityDay(activityNumber, day)) {
                    System.out.println("\n*The day of occurrence has been updated*");
                } else {
                    System.out.println("\n*Update failed. Time is conflicted*");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid day entered. ");
                System.out.println();
            }
        }
    }

    /*
     * REQUIRES: plannerNumber has been checked by indexIsValid method
     * MODIFIES: this
     * EFFECTS: prompts the user to modify the start time of the selected activity from the selected planner
     *          lets the user know if the new start time has been updated
     *          or if it fails to update due to time conflicts
     */
    private void modifyStartTime(int plannerNumber) {
        int activityNumber = displayActivities(plannerNumber);
        if (activityNumber != -1) {
            ActivityPlanner activityPlanner = activityPlannerBook.getActivityPlanner(plannerNumber);
            System.out.println("\nEnter new start time: (0 - 23)");
            String inputStr = input.next();
            try {
                int newStartTime = Integer.parseInt(inputStr);
                if (activityPlanner.setStartTime(activityNumber, newStartTime)) {
                    System.out.println("\n*The start time has been updated*");
                } else {
                    System.out.println("\n*Update failed. Time is conflicted*");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid start time entered. ");
                System.out.println();
            }
        }
    }

    /*
     * REQUIRES: plannerNumber has been checked by indexIsValid method
     * MODIFIES: this
     * EFFECTS: prompts the user to modify the duration of the selected activity from the selected planner
     *          lets the user know if the new duration has been updated
     *          or if it fails to update due to time conflicts
     */
    private void modifyDuration(int plannerNumber) {
        int activityNumber = displayActivities(plannerNumber);
        if (activityNumber != -1) {
            ActivityPlanner activityPlanner = activityPlannerBook.getActivityPlanner(plannerNumber);
            System.out.println("\nEnter new duration: (Cannot go to next day)");
            String inputStr = input.next();
            try {
                int newDuration = Integer.parseInt(inputStr);
                if (activityPlanner.setDuration(activityNumber, newDuration)) {
                    System.out.println("\n*The duration has been updated*");
                } else {
                    System.out.println("\n*Update failed. Time is conflicted*");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid duration entered. ");
                System.out.println();
            }
        }
    }

    /*
     * REQUIRES: plannerNumber has been checked by indexIsValid method
     * EFFECTS: display all activities of the selected planner and
     *          prompts the user to see detailed description of the specific activity
     */
    private void viewActivities(int plannerNumber) {
        ActivityPlanner activityPlanner = activityPlannerBook.getActivityPlanner(plannerNumber);
        System.out.println(activityPlanner.viewActivities());
        if (!activityPlanner.isEmpty()) {
            System.out.println("To view the activity in detail, please enter its corresponding number, ");
            System.out.println("Press any other key to go back to the previous menu: ");
            String inputStr = input.next();
            try {
                int activityNumber = Integer.parseInt(inputStr);
                if (activityPlannerBook.getActivityPlanner(plannerNumber).indexIsValid(activityNumber)) {
                    System.out.println(activityPlanner.getActivityDetailedDescription(activityNumber) + "\n");
                }
            } catch (NumberFormatException e) {
                return;
            }
        }
    }

    // EFFECTS: saves the planner book to file
    private void saveBook() {
        try {
            jsonWriter.open();
            jsonWriter.write(activityPlannerBook);
            jsonWriter.close();
            System.out.println(activityPlannerBook.getPlannerBookName() + " has been saved to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void loadBook() {
        try {
            activityPlannerBook = jsonReader.read();
            String bookOwner = activityPlannerBook.getPlannerBookName();
            System.out.println("Loaded " + bookOwner + "'s planner book from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

}
