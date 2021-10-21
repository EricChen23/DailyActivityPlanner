package ui;

import java.io.FileNotFoundException;

// runs the ActivityPlannerConsole
// with reference to JsonSerializationDemo
public class Main {
    public static void main(String[] args) {
        try {
            new ActivityPlannerConsole();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
    }
}
