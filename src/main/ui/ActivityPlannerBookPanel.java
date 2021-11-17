package ui;


import model.ActivityPlannerBook;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

// Represents the activity planner book panel
public class ActivityPlannerBookPanel extends JPanel {
    TitledBorder title;
    private JButton createPlannerBtn;
    private JButton changeNameBtn;
    private JButton choosePlannerBtn;
    private JButton delPlannerBtn;
    private JButton saveBtn;
    private JButton backBtn;
    private JButton quitBtn;

    /*
     * MODIFIES: this
     * EFFECTS: constructs the panel with buttons related to activity planner book features
     */
    public ActivityPlannerBookPanel(ActivityPlannerBook apb) {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 0);
        title = BorderFactory.createTitledBorder(apb.getPlannerBookName() + "'s Activity Planner Book");
        title.setTitleJustification(TitledBorder.CENTER);
        setBorder(title);
        initButtons();

        add(changeNameBtn, gbc);
        add(createPlannerBtn, gbc);
        add(choosePlannerBtn, gbc);
        add(delPlannerBtn, gbc);
        add(saveBtn, gbc);
        add(backBtn, gbc);
        add(quitBtn, gbc);
    }

    /*
     * MODIFIES: this
     * EFFECTS: initializes the buttons with corresponding features
     */
    private void initButtons() {
        createPlannerBtn = new JButton("create a new activity planner");
        changeNameBtn = new JButton("change name of this planner book");
        choosePlannerBtn = new JButton("go to an existing activity planner");
        delPlannerBtn = new JButton("delete an existing activity planner");
        saveBtn = new JButton("save activity planner book to file");
        backBtn = new JButton("go back to main menu");
        quitBtn = new JButton("quit");
    }

    public JButton getCreatePlannerBtn() {
        return createPlannerBtn;
    }

    public JButton getChangeNameBtn() {
        return changeNameBtn;
    }

    public JButton getChoosePlannerBtn() {
        return choosePlannerBtn;
    }

    public JButton getDelPlannerBtn() {
        return delPlannerBtn;
    }

    public JButton getSaveBtn() {
        return saveBtn;
    }

    public JButton getBackBtn() {
        return backBtn;
    }

    public JButton getQuitBtn() {
        return quitBtn;
    }

    /*
     * MODIFIES: this
     * EFFECTS: sets the border to the name of the owner of the activity planner book
     */
    public void setName(String newName) {
        title.setTitle(newName + "'s Activity Planner Book");
    }


}
