package ui;

import model.ActivityPlanner;
import model.ActivityPlannerBook;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActivityPlannerBookPanel extends JPanel {
    private ActivityPlannerBook activityPlannerBook;
    private JButton createPlanner;
    private JLabel full;

    public ActivityPlannerBookPanel(String name) {
        activityPlannerBook = new ActivityPlannerBook(name);
        setLayout(new BorderLayout());
        createPlanner = new JButton("Create new activity planner");
        createPlanner.setHorizontalAlignment(JLabel.CENTER);
        createPlanner.setVerticalAlignment(JLabel.CENTER);
        add(createPlanner, BorderLayout.CENTER);

        createPlanner.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                plannerWizard();
            }
        });
    }

    private void plannerWizard() {
        if (!activityPlannerBook.hasAvailableSpot()) {
            full = new JLabel("Your planner book is full. You cannot create more planners.");
            add(full, BorderLayout.PAGE_END);
            repaint();
        } else {
            full = new JLabel("Your planner book is full. You cannot create more planners.");
            add(full, BorderLayout.PAGE_END);
            repaint();
        }
    }
}
