package ui;

import persistence.*;
import model.ActivityPlannerBook;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ActivityPlannerBookFrame extends JFrame {
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 1000;

    private static final String JSON_STORE = "./data/activityplannerbook.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private ActivityPlannerBook apb;
    private WelcomePanel startPanel;
    private CreateNewBookPanel creationPanel;
    private ActivityPlannerBookPanel book;
    private ActivityPlannerPanel planner;

    private JButton newBookBtn;
    private JButton loadBookBtn;
    private JButton createBookBtn;

    private JButton createPlannerBtn;
    private JButton changeBookNameBtn;
    private JButton choosePlannerBtn;
    private JButton delPlannerBtn;
    private JButton saveBtn;
    private JButton backBtn;
    private JButton quitBtn;

    private JTextField grabName;
    private String bookName;

    private JComboBox plannerBox;


    public ActivityPlannerBookFrame() {
        init();
    }

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

    private void chooseNewOrOld() {
        startPanel = new WelcomePanel();
        add(startPanel);
        newBookBtn = startPanel.getNewBook();
        loadBookBtn = startPanel.getLoadBook();
        newBookBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newBookWizard();
            }
        });
        loadBookBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadBookAction();
            }
        });
    }

    private void newBookWizard() {
        getContentPane().removeAll();
        creationPanel = new CreateNewBookPanel();
        add(creationPanel);
        grabName = creationPanel.getBookName();
        createBookBtn = creationPanel.getCreateBtn();
        refresh();
        makeBook();
    }

    private void loadBookAction() {
        try {
            apb = jsonReader.read();
            String bookOwner = apb.getPlannerBookName();
            JOptionPane.showMessageDialog(null, "Loaded " + bookOwner + "'s planner book from " + JSON_STORE,
                    "success", JOptionPane.INFORMATION_MESSAGE);
            book = new ActivityPlannerBookPanel(apb);
            getContentPane().removeAll();
            add(book);
            refresh();
            runBook();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Unable to read from file: " + JSON_STORE, "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void makeBook() {
        createBookBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bookName = grabName.getText();
                if (bookName.isEmpty()) {
                    creationPanel.showEmptyLabel();
                } else {
                    apb = new ActivityPlannerBook(bookName);
                    getContentPane().removeAll();
                    book = new ActivityPlannerBookPanel(apb);
                    add(book);
                    refresh();
                    runBook();
                }
            }
        });
    }

    private void refresh() {
        repaint();
        revalidate();
    }

    private void runBook() {
        createPlannerBtn = book.getCreatePlannerBtn();
        changeBookNameBtn = book.getChangeNameBtn();
        choosePlannerBtn = book.getChoosePlannerBtn();
        delPlannerBtn = book.getDelPlannerBtn();
        saveBtn = book.getSaveBtn();
        backBtn = book.getBackBtn();
        quitBtn = book.getQuitBtn();
        defineChangeBookNameAction();
        defineCreatePlannerAction();
        defineChoosePlannerAction();
        defineDelPlannerAction();
        defineSaveAction();
        defineBackAction();
        defineQuitAction();
    }

    private void defineChangeBookNameAction() {
        changeBookNameBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newBookName = JOptionPane.showInputDialog(null,"Enter the new planner book name");
                if (newBookName == null) {
                    // do nothing
                } else if (newBookName.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Name cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "name successfully changed!", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    apb.setPlannerBookName(newBookName);
                    book.setName(newBookName);
                    refresh();
                }
            }
        });
    }

    private void defineCreatePlannerAction() {
        createPlannerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String plannerName = JOptionPane.showInputDialog(null,"Enter the name of planner");
                if (plannerName == null) {
                    // do nothing
                } else if (plannerName.isEmpty()) {
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

    private void defineChoosePlannerAction() {
        choosePlannerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (apb.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "There is no existing planners", "Error",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    plannerBox = new JComboBox();
                    for (int i = 1; i <= apb.getNumPlanners(); i++) {
                        plannerBox.addItem(apb.getPlannerName(i));
                    }
                    int sign = JOptionPane.showConfirmDialog(null, plannerBox, "Planner", JOptionPane.OK_CANCEL_OPTION);
                    if (sign == 0) {
                        int index = plannerBox.getSelectedIndex();
                        goToPlanner(index);
                    }
                }
            }
        });
    }

    private void defineDelPlannerAction() {
        delPlannerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (apb.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "There is no existing planners", "Error",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    plannerBox = new JComboBox();
                    for (int i = 1; i <= apb.getNumPlanners(); i++) {
                        plannerBox.addItem(apb.getPlannerName(i));
                    }
                    int sign = JOptionPane.showConfirmDialog(null, plannerBox, "Planner", JOptionPane.OK_CANCEL_OPTION);
                    if (sign == 0) {
                        int index = plannerBox.getSelectedIndex();
                        String plannerName = apb.getPlannerName(index + 1);
                        apb.deletePlanner(index + 1);
                        JOptionPane.showMessageDialog(null, plannerName + " successfully deleted!", "Success",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });
    }

    private void defineBackAction() {
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getContentPane().removeAll();
                add(startPanel);
                refresh();
            }
        });
    }

    private void defineSaveAction() {
        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
            }
        });
    }

    private void defineQuitAction() {
        quitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int sign = JOptionPane.showConfirmDialog(null, "Quit","Double Check", JOptionPane.OK_CANCEL_OPTION);
                if (sign == 0) {
                    System.exit(0);
                }
            }
        });
    }

    private void goToPlanner(int index) {
        planner = new ActivityPlannerPanel(apb.getActivityPlanner(index + 1));
        getContentPane().removeAll();
        add(planner);
        refresh();
        runPlanner();
    }

    private void runPlanner() {

    }
}
