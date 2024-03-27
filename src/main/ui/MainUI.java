package ui;

import javax.swing.*;

// Represents the entry point of Patient Tracker UI
public class MainUI {

    //-/***************************************************************************************
    // *    splash.jpg
    // *    Availability: <https://www.shutterstock.com/search/clinical-information-system>
    // ***************************************************************************************/

    // EFFECTS: starts Patient Tracker UI
    public static void main(String[] args) {
        JWindow splash = new JWindow();
        splash.setSize(400,300);
        splash.setLocationRelativeTo(null);
        ImageIcon icon = new ImageIcon("data/splash.jpg");
        JLabel iconLabel = new JLabel(icon);
        splash.add(iconLabel);
        splash.setVisible(true);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        splash.dispose();
        SwingUtilities.invokeLater(PatientTrackerUI::new);

    }
}
