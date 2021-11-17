package ui;

import model.Activity;
import model.ActivityPlanner;
import model.Day;
import persistence.*;
import model.ActivityPlannerBook;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;

// Represents the activity planner book GUI
public class ActivityPlannerBookFrame extends JFrame {
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 500;

    private static final String JSON_STORE = "./data/activityplannerbook.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private ActivityPlannerBook apb;
    private ActivityPlanner ap;
    private int selectedIndex;

    private WelcomePanel startPanel;
    private CreateNewBookPanel creationPanel;
    private ActivityPlannerBookPanel bookPanel;
    private ActivityPlannerPanel plannerPanel;

    private JButton createBookBtn;

    private JButton createPlannerBtn;
    private JButton changeBookNameBtn;
    private JButton choosePlannerBtn;
    private JButton delPlannerBtn;
    private JButton saveBtn;
    private JButton backBtn;
    private JButton quitBtn;

    private JTextField brief;
    private JTextField detail;
    private JComboBox<Day> day;
    private JComboBox<String> start;
    private JComboBox<String> duration;

    private JButton selectActivityBtn;
    private JButton addActivityBtn;
    private JButton delActivityBtn;
    private JButton modBriefBtn;
    private JButton modDetailedBtn;
    private JButton modDayBtn;
    private JButton modStartBtn;
    private JButton modDurBtn;
    private JButton previousBtn;
    private JButton viewBtn;

    private JTextField grabName;
    private String bookName;

    private JComboBox plannerBox;
    private JComboBox activityBox;

    /*
     * MODIFIES: this
     * EFFECTS: constructs the frame
     */
    public ActivityPlannerBookFrame() {
        init();
    }

    /*
     * MODIFIES: this
     * EFFECTS: initializes the frame
     */
    private void init() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        setTitle("Activity Planner Book");
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        chooseNewOrOld();
    }

    /*
     * MODIFIES: this
     * EFFECTS: adds the start panel to the frame and action listener to the buttons
     *          on the start panel
     */
    private void chooseNewOrOld() {
        startPanel = new WelcomePanel();
        add(startPanel);
        JButton newBookBtn = startPanel.getNewBook();
        JButton loadBookBtn = startPanel.getLoadBook();
        newBookBtn.addActionListener(e -> newBookWizard());
        loadBookBtn.addActionListener(e -> loadBookAction());
    }

    /*
     * MODIFIES: this
     * EFFECTS: guides the user to enter the name of the new activity planner book
     */
    private void newBookWizard() {
        getContentPane().removeAll();
        creationPanel = new CreateNewBookPanel();
        add(creationPanel);
        grabName = creationPanel.getBookName();
        createBookBtn = creationPanel.getCreateBtn();
        refresh();
        makeBook();
    }

    /*
     * MODIFIES: this
     * EFFECTS: loads the previously saved activity planner book
     */
    private void loadBookAction() {
        try {
            apb = jsonReader.read();
            String bookOwner = apb.getPlannerBookName();
            JOptionPane.showMessageDialog(null, "Loaded " + bookOwner + "'s planner book from " + JSON_STORE,
                    "success", JOptionPane.INFORMATION_MESSAGE);
            bookPanel = new ActivityPlannerBookPanel(apb);
            getContentPane().removeAll();
            add(bookPanel);
            refresh();
            runBook();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Unable to read from file: " + JSON_STORE, "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: creates a brand-new activity planner book
     */
    private void makeBook() {
        createBookBtn.addActionListener(e -> {
            bookName = grabName.getText();
            if (bookName.isEmpty()) {
                creationPanel.showEmptyLabel();
            } else {
                apb = new ActivityPlannerBook(bookName);
                getContentPane().removeAll();
                bookPanel = new ActivityPlannerBookPanel(apb);
                add(bookPanel);
                refresh();
                runBook();
            }
        });
    }

    /*
     * MODIFIES: this
     * EFFECTS: updates the frame
     */
    private void refresh() {
        repaint();
        revalidate();
    }

    /*
     * MODIFIES: this
     * EFFECTS: defines the buttons of the activity planner book
     */
    private void runBook() {
        createPlannerBtn = bookPanel.getCreatePlannerBtn();
        changeBookNameBtn = bookPanel.getChangeNameBtn();
        choosePlannerBtn = bookPanel.getChoosePlannerBtn();
        delPlannerBtn = bookPanel.getDelPlannerBtn();
        saveBtn = bookPanel.getSaveBtn();
        backBtn = bookPanel.getBackBtn();
        quitBtn = bookPanel.getQuitBtn();
        defineChangeBookNameAction();
        defineCreatePlannerAction();
        defineChoosePlannerAction();
        defineDelPlannerAction();
        defineSaveAction();
        defineBackAction();
        defineQuitAction();
    }

    /*
     * MODIFIES: this
     * EFFECTS: defines the change book name button so user can change the name,
     *          name will not be changed if input is empty
     */
    private void defineChangeBookNameAction() {
        changeBookNameBtn.addActionListener(e -> {
            String newBookName = JOptionPane.showInputDialog(null,"Enter the new planner book name");
            if (newBookName != null) {
                if (newBookName.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Name cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "name successfully changed!", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    apb.setPlannerBookName(newBookName);
                    bookPanel.setName(newBookName);
                    refresh();
                }
            }
        });
    }

    /*
     * MODIFIES: this
     * EFFECTS: defines the create planner button;
     *          success if name is not empty and there is available spot in the planner book
     *          fail if name is empty or the planner book is full
     */
    private void defineCreatePlannerAction() {
        createPlannerBtn.addActionListener(e -> {
            String plannerName = JOptionPane.showInputDialog(null,"Enter the name of planner");
            if (plannerName != null) {
                if (plannerName.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Name cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (!apb.hasAvailableSpot()) {
                    JOptionPane.showMessageDialog(null, "Reached maximum number of planners", "Error",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, plannerName + " successfully created!", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    apb.createNewPlanner(plannerName);
                }
            }
        });
    }

    /*
     * MODIFIES: this
     * EFFECTS: defines choose planner button
     */
    private void defineChoosePlannerAction() {
        choosePlannerBtn.addActionListener(e -> {
            if (apb.isEmpty()) {
                JOptionPane.showMessageDialog(null, "There is no existing planners", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                plannerBox = new JComboBox();
                for (int i = 1; i <= apb.getNumPlanners(); i++) {
                    plannerBox.addItem(apb.getPlannerName(i));
                }
                int sign = JOptionPane.showConfirmDialog(null, plannerBox, "Select a planner",
                        JOptionPane.OK_CANCEL_OPTION);
                if (sign == 0) {
                    int index = plannerBox.getSelectedIndex();
                    goToPlanner(index);
                }
            }
        });
    }

    /*
     * MODIFIES: this
     * EFFECTS: defines delete planner button
     */
    private void defineDelPlannerAction() {
        delPlannerBtn.addActionListener(e -> {
            if (apb.isEmpty()) {
                JOptionPane.showMessageDialog(null, "There is no existing planners", "Error",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                plannerBox = new JComboBox();
                for (int i = 1; i <= apb.getNumPlanners(); i++) {
                    plannerBox.addItem(apb.getPlannerName(i));
                }
                int sign = JOptionPane.showConfirmDialog(null, plannerBox, "Select a planner",
                        JOptionPane.OK_CANCEL_OPTION);
                if (sign == 0) {
                    int index = plannerBox.getSelectedIndex();
                    String plannerName = apb.getPlannerName(index + 1);
                    apb.deletePlanner(index + 1);
                    JOptionPane.showMessageDialog(null, plannerName + " successfully deleted!", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }

    /*
     * MODIFIES: this
     * EFFECTS: defines back button, sends user back to start menu
     */
    private void defineBackAction() {
        backBtn.addActionListener(e -> {
            getContentPane().removeAll();
            add(startPanel);
            refresh();
        });
    }

    /*
     * MODIFIES: this
     * EFFECTS: defines save button, saves the activity planner book to file
     */
    private void defineSaveAction() {
        saveBtn.addActionListener(e -> {
            try {
                jsonWriter.open();
                jsonWriter.write(apb);
                jsonWriter.close();
                JOptionPane.showMessageDialog(null, apb.getPlannerBookName() + " has been saved to " + JSON_STORE,
                        "success", JOptionPane.INFORMATION_MESSAGE);
            } catch (FileNotFoundException ffe) {
                JOptionPane.showMessageDialog(null, "Unable to write to file: " + JSON_STORE, "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    /*
     * MODIFIES: this
     * EFFECTS: defines quit button, exits the application with a double check confirmation
     */
    private void defineQuitAction() {
        quitBtn.addActionListener(e -> {
            int sign = JOptionPane.showConfirmDialog(null, "Quit","Double Check", JOptionPane.OK_CANCEL_OPTION);
            if (sign == 0) {
                System.exit(0);
            }
        });
    }

    /*
     * MODIFIES: this
     * EFFECTS: sets up the activity planner panel
     */
    private void goToPlanner(int index) {
        ap = apb.getActivityPlanner(index + 1);
        plannerPanel = new ActivityPlannerPanel(ap);
        getContentPane().removeAll();
        add(plannerPanel);
        setTitle(ap.getName());
        refresh();
        runPlanner();
    }

    /*
     * MODIFIES: this
     * EFFECTS: helper function that defines the buttons on the planner panel
     */
    private void runPlanner() {
        selectActivityBtn = plannerPanel.getSelectActivityBtn();
        addActivityBtn = plannerPanel.getAddActivityBtn();
        delActivityBtn = plannerPanel.getDelActivityBtn();
        modBriefBtn = plannerPanel.getModBriefBtn();
        modDetailedBtn = plannerPanel.getModDetailedBtn();
        modDayBtn = plannerPanel.getModDayBtn();
        modStartBtn = plannerPanel.getModStartBtn();
        modDurBtn = plannerPanel.getModDurBtn();
        previousBtn = plannerPanel.getBackBtn();
        viewBtn = plannerPanel.getViewBtn();
        defineSelectBtnAction();
        defineAddBtnAction();
        defineDelBtnAction();
        defineModBriefAction();
        defineModDetailAction();
        defineModDayAction();
        defineModStartBtnAction();
        defineModDurBtnAction();
        definePrevAction();
        defineViewBtnAction();
    }

    /*
     * MODIFIES: this
     * EFFECTS: defines select button
     */
    private void defineSelectBtnAction() {
        selectActivityBtn.addActionListener(e -> {
            if (ap.getNumActivities() > 0) {
                activityBox = new JComboBox();
                for (int i = 1; i <= ap.getNumActivities(); i++) {
                    activityBox.addItem(ap.getActivityBriefDescription(i));
                }
                int sign = JOptionPane.showConfirmDialog(null, activityBox, "Select an activity",
                        JOptionPane.OK_CANCEL_OPTION);
                if (sign == 0) {
                    int index = activityBox.getSelectedIndex();
                    selectedIndex = index;
                    plannerPanel.setActivityLabel(index);
                }
            } else {
                JOptionPane.showMessageDialog(null, "There is no activities",
                        "selection failed", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    /*
     * MODIFIES: this
     * EFFECTS: constructs the helper pane for creating a new activity
     */
    private JPanel makeCreateActPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setPreferredSize(new Dimension(600, 200));
        JPanel labels = new JPanel(new GridLayout(0,1,2,2));
        labels.add(new JLabel("Brief Description", SwingConstants.TRAILING));
        labels.add(new JLabel("Detailed Description", SwingConstants.TRAILING));
        labels.add(new JLabel("Day", SwingConstants.TRAILING));
        labels.add(new JLabel("Start time", SwingConstants.TRAILING));
        labels.add(new JLabel("Duration", SwingConstants.TRAILING));
        panel.add(labels, BorderLayout.LINE_START);
        JPanel inputPanel = new JPanel(new GridLayout(0,1,2,2));
        initActElements();
        inputPanel.add(brief);
        inputPanel.add(detail);
        inputPanel.add(day);
        inputPanel.add(start);
        inputPanel.add(duration);
        panel.add(inputPanel);
        return panel;
    }

    /*
     * MODIFIES: this
     * EFFECTS: helper function that initializes the five elements needed to create a new activity
     */
    private void initActElements() {
        brief = new JTextField("");
        detail = new JTextField("");
        day = new JComboBox<>();
        start = new JComboBox<>();
        duration = new JComboBox<>();
        for (Day d : Day.values()) {
            day.addItem(d);
        }
        for (int i = 0; i <= 24; i++) {
            if (i < 24) {
                start.addItem(i + ":00");
            }
            if (i > 0) {
                duration.addItem(i + " hours");
            }
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: checks if the brief description or the detailed description user entered is empty,
     *          pops out a window with an error message if so.
     */
    private boolean checkEmptyDescription() {
        if (brief.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "brief description cannot be empty",
                    "Creation failed", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (detail.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "detailed description cannot be empty",
                    "Creation failed", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    /*
     * MODIFIES: this
     * EFFECTS: defines add activity button
     */
    private void defineAddBtnAction() {
        addActivityBtn.addActionListener(e -> {
            int sign = JOptionPane.showConfirmDialog(null, makeCreateActPanel(),
                    "Create new activity", JOptionPane.OK_CANCEL_OPTION);
            if (sign == 0) {
                if (checkEmptyDescription()) {
                    if (ap.addActivity(new Activity(brief.getText(), detail.getText(),
                            Day.values()[day.getSelectedIndex()],
                            start.getSelectedIndex(), duration.getSelectedIndex() + 1))) {
                        JOptionPane.showMessageDialog(null, "successfully created",
                                "success", JOptionPane.INFORMATION_MESSAGE);
                        plannerPanel.updateDisplay();
                    } else {
                        JOptionPane.showMessageDialog(null, "conflict detected, creation failed",
                                "creation failed", JOptionPane.ERROR_MESSAGE);
                    }
                    refresh();
                }
            }
        });
    }

    /*
     * MODIFIES: this
     * EFFECTS: defines delete activity button
     */
    private void defineDelBtnAction() {
        delActivityBtn.addActionListener(e -> {
            if (plannerPanel.getHasSelected()) {
                if (JOptionPane.showConfirmDialog(null, "Confirm deletion", "Double check", 2) == 0) {
                    JOptionPane.showMessageDialog(null, "successfully deleted\n" + ap.getActivity(selectedIndex + 1),
                            "deletion succeeded", JOptionPane.INFORMATION_MESSAGE);
                    ap.deleteActivity(selectedIndex + 1);
                    plannerPanel.setActivityLabel(-1);
                    plannerPanel.updateDisplay();
                    refresh();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Activity selection needed",
                        "deletion failed", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    /*
     * MODIFIES: this
     * EFFECTS: defines modify brief description button
     */
    private void defineModBriefAction() {
        modBriefBtn.addActionListener(e -> {
            if (plannerPanel.getHasSelected()) {
                String newBrief = JOptionPane.showInputDialog(null, "Enter new brief description");
                if (newBrief != null) {
                    if (newBrief.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "brief description cannot be empty\n",
                                "update failed", JOptionPane.ERROR_MESSAGE);
                    } else {
                        ap.getActivity(selectedIndex + 1).setBriefDescription(newBrief);
                        JOptionPane.showMessageDialog(null,
                                "successfully updated\n" + ap.getActivity(selectedIndex + 1),
                                "brief description updated", JOptionPane.INFORMATION_MESSAGE);
                        plannerPanel.updateDisplay();
                        plannerPanel.setActivityLabel(selectedIndex);
                        refresh();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Activity selection needed",
                        "modification failed", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    /*
     * MODIFIES: this
     * EFFECTS: defines modify detailed description button
     */
    private void defineModDetailAction() {
        modDetailedBtn.addActionListener(e -> {
            if (plannerPanel.getHasSelected()) {
                String newDetailed = JOptionPane.showInputDialog(null, "Enter new detailed description");
                if (newDetailed != null) {
                    if (newDetailed.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "detailed description cannot be empty\n",
                                "update failed", JOptionPane.ERROR_MESSAGE);
                    } else {
                        ap.getActivity(selectedIndex + 1).setDetailedDescription(newDetailed);
                        JOptionPane.showMessageDialog(null,
                                "successfully updated\n" + ap.getActivity(selectedIndex + 1),
                                "detailed description updated", JOptionPane.INFORMATION_MESSAGE);
                        plannerPanel.updateDisplay();
                        refresh();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Activity selection needed",
                        "modification failed", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    /*
     * MODIFIES: this
     * EFFECTS: defines modify day of occurrence button
     */
    private void defineModDayAction() {
        modDayBtn.addActionListener(e -> {
            if (plannerPanel.getHasSelected()) {
                day = new JComboBox<>();
                for (Day d : Day.values()) {
                    day.addItem(d);
                }
                if (JOptionPane.showConfirmDialog(null, day, "Select new day", JOptionPane.OK_CANCEL_OPTION) == 0) {
                    int index = day.getSelectedIndex();
                    if (ap.setActivityDay(selectedIndex + 1, Day.values()[index])) {
                        JOptionPane.showMessageDialog(null,"New day: " + Day.values()[index],
                                "modification success", JOptionPane.INFORMATION_MESSAGE);
                        plannerPanel.updateDisplay();
                        refresh();
                    } else {
                        JOptionPane.showMessageDialog(null, "Conflict detected", "modification failed",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Activity selection needed",
                        "modification failed", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    /*
     * MODIFIES: this
     * EFFECTS: defines modify start time button
     */
    private void defineModStartBtnAction() {
        modStartBtn.addActionListener(e -> {
            if (plannerPanel.getHasSelected()) {
                start = new JComboBox<>();
                for (int i = 0; i < 24; i++) {
                    start.addItem(Integer.toString(i));
                }
                if (JOptionPane.showConfirmDialog(null, start, "Select new start time", 2) == 0) {
                    int index = start.getSelectedIndex();
                    if (ap.setStartTime(selectedIndex + 1, index)) {
                        JOptionPane.showMessageDialog(null,"New start time: " + index + ":00",
                                "modification success", JOptionPane.INFORMATION_MESSAGE);
                        plannerPanel.updateDisplay();
                        refresh();
                    } else {
                        JOptionPane.showMessageDialog(null, "Conflict detected", "modification failed",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Activity selection needed",
                        "modification failed", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    /*
     * MODIFIES: this
     * EFFECTS: defines modify duration button
     */
    private void defineModDurBtnAction() {
        modDurBtn.addActionListener(e -> {
            if (plannerPanel.getHasSelected()) {
                duration = new JComboBox<>();
                for (int i = 1; i <= 24; i++) {
                    duration.addItem(Integer.toString(i));
                }
                if (JOptionPane.showConfirmDialog(null, duration, "Select new duration", 2) == 0) {
                    int index = duration.getSelectedIndex() + 1;
                    if (ap.setDuration(selectedIndex + 1, index)) {
                        JOptionPane.showMessageDialog(null,"New duration: " + index + " hours",
                                "modification success", JOptionPane.INFORMATION_MESSAGE);
                        plannerPanel.updateDisplay();
                        refresh();
                    } else {
                        JOptionPane.showMessageDialog(null, "Conflict detected", "modification failed",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Activity selection needed",
                        "modification failed", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    /*
     * MODIFIES: this
     * EFFECTS: defines view summary button
     */
    private void defineViewBtnAction() {
        viewBtn.addActionListener(e -> {
            if (plannerPanel.getHasSelected()) {
                Activity activity = ap.getActivity(selectedIndex + 1);
                String summary = activity.getBriefDescription() + "\n";
                summary += activity.getDetailedDescription() + "\n";
                summary += "From " + activity.getStartTime() + ":00";
                summary += " to " + (activity.getStartTime() + activity.getDuration()) + ":00";
                summary += " on " + activity.getDay() + "\n";
                JOptionPane.showMessageDialog(null, summary, "Summary of the selected activity",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Activity selection needed",
                        "modification failed", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    /*
     * MODIFIES: this
     * EFFECTS: defines go to previous menu button
     */
    private void definePrevAction() {
        previousBtn.addActionListener(e -> {
            getContentPane().removeAll();
            add(bookPanel);
            setTitle("Activity Planner Book");
            refresh();
        });
    }
}
