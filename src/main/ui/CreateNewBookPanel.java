package ui;

import javax.swing.*;
import java.awt.*;

public class CreateNewBookPanel extends JPanel {
    private JTextField grabName;
    private JButton create;
    private JLabel nameLabel;
    private JLabel emptyNameLabel;

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

    public void showEmptyLabel() {
        emptyNameLabel.setVisible(true);
    }

}
