package ui;

import model.Event;
import model.EventLog;

import javax.swing.*;

//Represents a frame for printing event log.

//-/***************************************************************************************
// *    AlarmSystem
// *    Availability: <https://github.students.ubc.ca/CPSC210/AlarmSystem>
// ***************************************************************************************/
public class LogScreenUI extends JFrame {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;
    private final JTextArea logArea;

    //EFFECTS: creates a new screen for printing log
    public LogScreenUI() {
        logArea = new JTextArea();
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);
        add(scrollPane);
        setSize(WIDTH, HEIGHT);
        //setPosition(parent);
        setVisible(true);
    }

    //EFFECTS: prints event log in the text area
    public void printLog(EventLog el) {
        for (Event next : el) {
            logArea.setText(logArea.getText() + next.toString() + "\n\n");
        }
        repaint();
    }
}
