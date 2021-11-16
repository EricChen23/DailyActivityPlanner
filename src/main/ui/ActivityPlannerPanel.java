package ui;

import model.ActivityPlanner;

import javax.swing.*;
import java.awt.*;

public class ActivityPlannerPanel extends JSplitPane {

    private ActivityPlanner ap;

    private JPanel plannerTable;
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

    private JLabel selected;

    private Boolean hasSelected;

    public ActivityPlannerPanel(ActivityPlanner ap) {
        super(JSplitPane.VERTICAL_SPLIT);
        this.ap = ap;
        initTable();
        initButtonPanel();
        hasSelected = false;
        setTopComponent(plannerTable);
        setBottomComponent(buttonPanel);
        setOneTouchExpandable(true);
        setDividerSize(10);
        setResizeWeight(0.8);
    }

    private void initTable() {
        plannerTable = new JPanel();
        plannerTable.setLayout(null);

    }

    private void initButtonPanel() {
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 0);
        initButtons();
        buttonPanel.add(selectActivityBtn, gbc);
        buttonPanel.add(addActivityBtn, gbc);
        buttonPanel.add(delActivityBtn, gbc);
        buttonPanel.add(modBriefBtn, gbc);
        buttonPanel.add(modDetailedBtn, gbc);
        buttonPanel.add(modDayBtn, gbc);
        buttonPanel.add(modStartBtn, gbc);
        buttonPanel.add(modDurBtn, gbc);
        buttonPanel.add(backBtn, gbc);
        selected = new JLabel("Currently Selected Activity: ");
        buttonPanel.add(selected);
    }

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
    }

    public void setHasSelected(boolean b) {
        hasSelected = b;
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

    public Boolean getHasSelected() {
        return hasSelected;
    }
}
