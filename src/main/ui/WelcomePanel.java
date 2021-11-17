package ui;


import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

// Represents the welcome panel

public class WelcomePanel extends JPanel {
    private JLabel welcome;
    private JButton newBook;
    private JButton loadBook;

    /*
     *  MODIFIES: this
     *  EFFECTS: constructs the welcome panel with grid bag layout
     */
    public WelcomePanel() {
        welcome = new JLabel("Welcome to Activity Planner Book");
        welcome.setFont(new Font("Bold", Font.BOLD, 40));
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

    /*
     * MODIFIES: this
     * EFFECTS: adds a background image on this panel
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon bg = new ImageIcon("./data/book.jpg");
        g.drawImage(bg.getImage(), 0, 0, null);
    }
}
