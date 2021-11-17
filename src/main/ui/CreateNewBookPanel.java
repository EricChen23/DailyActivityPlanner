package ui;

import javax.swing.*;
import java.awt.*;

// Represents the panel that accepts user input to create a brand-new activity planner book
public class CreateNewBookPanel extends JPanel {
    private JTextField grabName;
    private JButton create;
    private JLabel nameLabel;
    private JLabel emptyNameLabel;

    /*
     * MODIFIES: this
     * EFFECTS: sets up the panel for user to input the name of the brand-new activity planner book
     */
    public CreateNewBookPanel() {
        grabName = new JTextField("");
        create = new JButton("Create!");
        nameLabel = new JLabel("Enter your name: ");
        grabName.setPreferredSize(new Dimension(200, 20));
        setLayout(new GridBagLayout());
        add(nameLabel);
        add(grabName);
        add(create);
        emptyNameLabel = new JLabel("Name cannot be empty. Creation failed.");

        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 1;
        gc.gridy = 2;
        add(emptyNameLabel, gc);
        emptyNameLabel.setVisible(false);
    }

    public JTextField getBookName() {
        return grabName;
    }

    public JButton getCreateBtn() {
        return create;
    }

    /*
     * MODIFIES: this
     * EFFECTS: the label becomes visible if the input is empty
     */
    public void showEmptyLabel() {
        emptyNameLabel.setVisible(true);
    }

}
