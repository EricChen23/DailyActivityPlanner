package ui;

import model.ActivityPlanner;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

// Represents the activity planner panel
public class ActivityPlannerPanel extends JSplitPane {

    private ActivityPlanner ap;

    private JPanel displayPanel;
    private JScrollPane scrollPane;
    private JPanel buttonPanel;

    private JButton selectActivityBtn;
    private JButton addActivityBtn;
    private JButton delActivityBtn;
    private JButton modBriefBtn;
    private JButton modDetailedBtn;
    private JButton modDayBtn;
    private JButton modStartBtn;
    private JButton modDurBtn;
    private JButton backBtn;
    private JButton viewBtn;

    private JLabel selectLabel;
    private JLabel activityLabel;

    private Boolean hasSelected;
    private int selectedIndex;

    /*
     * MODIFIES: this
     * EFFECTS: constructs the activity planner with two sections
     *          displays the activities on top and functions for modifications on bottom
     */
    public ActivityPlannerPanel(ActivityPlanner ap) {
        super(JSplitPane.VERTICAL_SPLIT);
        this.ap = ap;
        initDisplayPanel();
        initButtonPanel();
        scrollPane = new JScrollPane(displayPanel);
        scrollPane.setForeground(Color.WHITE);
        scrollPane.setBounds(10, 10, 10, 10);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        hasSelected = false;
        setTopComponent(scrollPane);
        setBottomComponent(buttonPanel);
        setOneTouchExpandable(true);
        setDividerSize(10);
        setResizeWeight(1);
    }

    /*
     * MODIFIES: this
     * EFFECTS: updates the display of the activities on top half of the panel
     */
    public void updateDisplay() {
        displayPanel.removeAll();
        for (int i = 1; i <= ap.getNumActivities(); i++) {
            String summary = ap.getActivity(i).getBriefDescription();
            summary += " from " + ap.getActivity(i).getStartTime() + ":00";
            summary += " to " + (ap.getActivity(i).getStartTime() + ap.getActivity(i).getDuration()) + ":00";
            summary += " on " + ap.getActivity(i).getDay() + "\n";
            JLabel event = new JLabel(summary, SwingConstants.LEFT);
            event.setFont(new Font("Plain", Font.PLAIN, 20));
            event.setBorder(new EmptyBorder(5, 5, 5, 5));
            displayPanel.add(event);
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: initializes the display of the activities (top half of the panel)
     */
    private void initDisplayPanel() {
        displayPanel = new JPanel();
        displayPanel.setLayout(new BoxLayout(displayPanel, BoxLayout.Y_AXIS));
        updateDisplay();
    }

    /*
     * MODIFIES: this
     * EFFECTS: initializes the display of the functions (bottom half of the panel)
     */
    private void initButtonPanel() {
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        initButtons();
        buttonPanel.add(selectActivityBtn, gbc);
        buttonPanel.add(addActivityBtn, gbc);
        buttonPanel.add(delActivityBtn, gbc);
        gbc.gridx = 1;
        buttonPanel.add(modBriefBtn, gbc);
        buttonPanel.add(modDetailedBtn, gbc);
        buttonPanel.add(modDayBtn, gbc);
        gbc.gridx = 2;
        buttonPanel.add(modStartBtn, gbc);
        buttonPanel.add(modDurBtn, gbc);
        buttonPanel.add(backBtn, gbc);
        gbc.gridx = 3;
        buttonPanel.add(viewBtn,gbc);
        selectLabel = new JLabel("Currently Selected Activity: ");
        activityLabel = new JLabel("None");
        buttonPanel.add(selectLabel, gbc);
        buttonPanel.add(activityLabel,gbc);
    }

    /*
     * MODIFIES: this
     * EFFECTS: initializes the buttons on the panel
     */
    private void initButtons() {
        selectActivityBtn = new JButton("Select an activity for modification");
        addActivityBtn = new JButton("create new activity");
        delActivityBtn = new JButton("delete an activity");
        modBriefBtn = new JButton("modify brief description");
        modDetailedBtn = new JButton("modify detailed description");
        modDayBtn = new JButton("modify day of occurrence");
        modStartBtn = new JButton("modify start time");
        modDurBtn = new JButton("modify duration");
        backBtn = new JButton("go back to previous menu");
        viewBtn = new JButton("view selected activity in detail");
    }

    /*
     * MODIFIES: this
     * EFFECTS: sets the activity label to the corresponding activity based on provided index
     *          sets to none if index is -1 ; adjusts the hasSelected flag respectively
     */
    public void setActivityLabel(int index) {
        if (index >= 0) {
            selectedIndex = index + 1;
            activityLabel.setText(ap.getActivity(selectedIndex).getBriefDescription());
            hasSelected = true;
        } else {
            activityLabel.setText("None");
            hasSelected = false;
        }
    }

    public JButton getSelectActivityBtn() {
        return selectActivityBtn;
    }

    public JButton getAddActivityBtn() {
        return addActivityBtn;
    }

    public JButton getDelActivityBtn() {
        return delActivityBtn;
    }

    public JButton getModBriefBtn() {
        return modBriefBtn;
    }

    public JButton getModDetailedBtn() {
        return modDetailedBtn;
    }

    public JButton getModDayBtn() {
        return modDayBtn;
    }

    public JButton getModStartBtn() {
        return modStartBtn;
    }

    public JButton getModDurBtn() {
        return modDurBtn;
    }

    public JButton getBackBtn() {
        return backBtn;
    }

    public JButton getViewBtn() {
        return viewBtn;
    }

    public Boolean getHasSelected() {
        return hasSelected;
    }

}
