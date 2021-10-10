package ui;

import model.Activity;
import model.ActivityPlanner;


import java.util.ArrayList;
import java.util.Scanner;
import model.Day;

// Reference to Teller App
// Activity planner application
public class ActivityPlannerConsole {
    public static final int MAX_PLANNERS_NUMBER = 3;
    int numPlanners;
    ArrayList<ActivityPlanner> activityPlanners;

    private Scanner input;


    // EFFECTS: runs the teller application
    public ActivityPlannerConsole() {
        runActivityPlannerConsole();
    }

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

    private void init() {
        numPlanners = 0;
        activityPlanners = new ArrayList<>();
        input = new Scanner(System.in);
    }

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

    private void processPlannerCommand(String command) {
        switch (command) {
            case "n":
                createPlannerPrompt();
                break;
            case "c":
                choosePlannerPrompt();
                break;
            case "d":
                deletePlannerPrompt();
                break;
        }
    }


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

    private void displayPlannerMenu() {
        System.out.println("\nSelect from: ");
        System.out.println("\tn -> create a new activity planner");
        System.out.println("\tc -> choose an existing activity planner");
        System.out.println("\td -> delete an existing activity planner");
        System.out.println("\tq -> quit");
    }

    private void displayActivityMenu(int plannerNumber) {
        System.out.println("\n" + activityPlanners.get(plannerNumber - 1).getName() + " ----Select from: ");
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

    private void displayPlannerNames() {
        int index = 1;
        for (ActivityPlanner activityPlanner: activityPlanners) {
            if (activityPlanner != null) {
                System.out.print("Planner " + index + ": ");
                System.out.print(activityPlanner.getName() + "; \t");
                index++;
            }
        }
    }

    private void createPlannerPrompt() {
        if (numPlanners < 3) {
            createPlanner();
        } else {
            System.out.println("\nExceeded max number of planners. Please delete or modify an existing planner.");
        }
    }

    private void choosePlannerPrompt() {
        if (numPlanners == 0) {
            System.out.println("\nThere are no existing planners. ");
        } else {
            displayPlannerNames();
            System.out.print("\nPlease enter the planner number: ");
            if (input.hasNextInt()) {
                int plannerNumber = input.nextInt();
                if (plannerNumber > 0 && plannerNumber <= numPlanners) {
                    if (activityPlanners.get(plannerNumber - 1) == null) {
                        System.out.println("Invalid input. ");
                    } else {
                        runPlannerPrompt(plannerNumber);
                    }
                } else {
                    System.out.println("Invalid input. ");
                }
            } else {
                System.out.println("Invalid input. ");
            }
        }
    }

    private void deletePlannerPrompt() {
        if (numPlanners == 0) {
            System.out.print("\nThere are no existing planners." + "\n");
        } else {
            displayPlannerNames();
            System.out.print("\nPlease enter the planner number to be deleted: ");
            if (input.hasNextInt()) {
                int plannerNumber = input.nextInt();
                if (plannerNumber > 0 && plannerNumber <= numPlanners) {
                    deletePlanner(plannerNumber);
                } else {
                    System.out.println("Invalid input. ");
                }
            } else {
                System.out.println("Invalid input. ");
            }
        }
    }

    private void createPlanner() {
        boolean emptyInput = true;
        String plannerName = null;
        while (emptyInput) {
            System.out.print("Enter the name of the new activity planner: ");
            plannerName = input.next();
            if (plannerName.length() != 0) {
                emptyInput = false;
            } else {
                System.out.println("Name cannot be empty. ");
            }
        }
        ActivityPlanner newPlanner = new ActivityPlanner(plannerName);
        activityPlanners.add(newPlanner);
        numPlanners++;
        System.out.println("\n*" + plannerName + " is successfully added*");
    }

    private void deletePlanner(int plannerNumber) {
        if (activityPlanners.get(plannerNumber - 1) != null) {
            System.out.println("\n*" + activityPlanners.get(plannerNumber - 1).getName() + " has been deleted.*");
            activityPlanners.remove(plannerNumber - 1);
            numPlanners--;
        }
    }

    private void addActivity(int plannerNumber) {
        input.nextLine();
        System.out.println("\nEnter the brief description of the new activity: ");
        String briefDescription = input.nextLine();
        System.out.println("\nEnter the detailed description of the new activity: ");
        String detailedDescription = input.nextLine();
        System.out.println("\nEnter the day the new activity occurs: (SUN, MON, TUE, WED, THUR, FRI, SAT)");
        Day day;
        try {
            day = Day.valueOf(input.next().toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid day entered. ");
            return;
        }
        System.out.println("\nEnter the start time of the new activity: (Between 0 - 23)");
        int startTime = input.nextInt();
        System.out.println("\nEnter the duration of the new activity: (Cannot go over night)");
        int duration = input.nextInt();
        Activity newActivity = new Activity(briefDescription, detailedDescription, day, startTime, duration);
        if (activityPlanners.get(plannerNumber - 1).addActivity(newActivity)) {
            System.out.println("\n*New activity successfully added.*");
        } else {
            System.out.println("\nDetected time conflicts / invalid time. New activity failed to be added. ");
        }
    }

    private void deleteActivity(int plannerNumber) {

    }

    private void modifyBriefDescription(int plannerNumber) {

    }

    private void modifyDetailedDescription(int plannerNumber) {

    }

    private void modifyDay(int plannerNumber) {

    }

    private void modifyStartTime(int plannerNumber) {

    }

    private void modifyDuration(int plannerNumber) {

    }

    private void viewActivities(int plannerNumber) {
        ActivityPlanner activityPlanner = activityPlanners.get(plannerNumber - 1);
        System.out.println(activityPlanner.viewActivities());
        if (!activityPlanner.isEmpty()) {
            boolean keepGoing = true;
            while (keepGoing) {
                System.out.println("To view the activity in detail, please enter its corresponding number, ");
                System.out.print("Press any other key to go back to the previous menu: ");
                int activityNumber = input.nextInt();
                if (activityNumber > 0 && activityNumber <= activityPlanner.getNumActivities()) {
                    System.out.println(activityPlanner.viewActivityDetailedDescription(activityNumber) + "\n");
                } else {
                    keepGoing = false;
                }
            }
        }
    }



}
