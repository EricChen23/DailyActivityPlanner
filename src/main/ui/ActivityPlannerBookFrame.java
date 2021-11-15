package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ActivityPlannerBookFrame extends JFrame {
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 1000;

    private JPanel book;

    private JFrame frame;
    private JButton newBook;
    private JButton loadBook;
    private JButton create;
    private JTextField grabName;
    private JLabel nameLabel;
    private String bookName;

    public ActivityPlannerBookFrame() {
        init();
    }

    private void init() {
        frame = new JFrame("Activity Planner Book");
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        chooseNewOrOld();
    }

    private void chooseNewOrOld() {
        newBook = new JButton("Create new activity planner book");
        loadBook = new JButton("Load existing activity planner book");
        frame.setLayout(new GridBagLayout());
        frame.add(newBook, new GridBagConstraints());
        frame.add(loadBook, new GridBagConstraints());
        defineNewBookAction();
        defineLoadBookAction();
    }

    private void defineNewBookAction() {
        newBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newBookWizard();
            }
        });
    }

    private void defineLoadBookAction() {
        loadBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadBookAction();
            }
        });
    }

    private void newBookWizard() {
        frame.getContentPane().removeAll();
        grabName = new JTextField("");
        create = new JButton("Create!");
        nameLabel = new JLabel("Enter your name: ");
        grabName.setPreferredSize(new Dimension(200, 20));
        frame.add(nameLabel);
        frame.add(grabName);
        frame.add(create, new GridBagConstraints());
        refresh();
        makeBook();
    }

    private void loadBookAction() {

    }

    private void makeBook() {
        create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.getContentPane().removeAll();
                bookName = grabName.getText();
                book = new ActivityPlannerBookPanel(bookName);
                frame.add(book);
                refresh();
            }
        });
    }

    private void refresh() {
        frame.pack();
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.repaint();
    }
}
