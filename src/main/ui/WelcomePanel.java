package ui;

import model.ActivityPlannerBook;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomePanel extends JPanel {
    private JLabel welcome;
    private JButton newBook;
    private JButton loadBook;

    public WelcomePanel() {
        welcome = new JLabel("Welcome to Activity Planner Book");
        newBook = new JButton("Create new activity planner book");
        loadBook = new JButton("Load existing activity planner book");
        GridBagLayout gbl = new GridBagLayout();
        GridBagConstraints gc = new GridBagConstraints();
        gc.anchor = GridBagConstraints.CENTER;
        gc.weightx = 0.1;
        gc.weighty = 0.1;
        gc.gridx = 0;
        gc.gridy = 0;
        setLayout(gbl);

        add(welcome, gc);
        gc.gridy = 1;
        add(newBook, gc);
        gc.gridy = 2;
        add(loadBook, gc);
    }

    public JButton getNewBook() {
        return newBook;
    }

    public JButton getLoadBook() {
        return loadBook;
    }


}
