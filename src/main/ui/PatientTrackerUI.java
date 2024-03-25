package ui;

import model.ClinicalTrial;
import model.Patient;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

// Represents GUI of Patient Tracker Application
public class PatientTrackerUI extends JFrame implements ActionListener {
    private final JFrame mainFrame;
    private ClinicalTrial clinicalTrial;
    private static final int FRAME_WIDTH = 1200;
    private static final int FRAME_HEIGHT = 600;
    private static final int BUTTON_WIDTH = 200;
    private static final int BUTTON_HEIGHT = 30;
    private static final int ELEMENT_WIDTH = 180;
    private static final int ELEMENT_HEIGHT = 30;
    private JPanel addPatientPanel;
    private JPanel displayPanel;
    private final Dimension buttonSize;
    private final Dimension elementSize;
    private JTextField idField;
    private JTextField genderField;
    private JTextField ageField;
    private JTextField operationField;
    private JTextArea patientListTextArea;
    private JTextArea followupListTextArea;
    private final JsonWriter jsonWriter;
    private final JsonReader jsonReader;
    private static final String JSON_STORE = "./data/patientTrackerUI.json";


    // EFFECTS: initializes the GUI
    public PatientTrackerUI() {
        mainFrame = new JFrame("Patient Tracking System");
        clinicalTrial = new ClinicalTrial("My Clinical Trial");
        mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        mainFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        buttonSize = new Dimension(BUTTON_WIDTH,BUTTON_HEIGHT);
        elementSize = new Dimension(ELEMENT_WIDTH,ELEMENT_HEIGHT);

        createAddPatientPanel();
        createDisplayPanel();
        createSaveLoadPanel();

        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // MODIFIES: this
    // EFFECTS: builds an area for patients information
    private void createDisplayPanel() {
        displayPanel = new JPanel(new GridLayout(1,2,20,10));
        createShowPatientsPanel();
        createFollowupPanel();
        mainFrame.add(displayPanel,BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: builds an area for "save" and "load" button
    private void createSaveLoadPanel() {
        JPanel saveLoadPanel = new JPanel(new GridLayout(1, 2, 20, 10));
        saveLoadPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JButton saveButton = new JButton("Save your work");
        saveButton.setActionCommand("save");
        saveButton.setPreferredSize(buttonSize);
        saveButton.addActionListener(this);
        saveLoadPanel.add(saveButton);
        JButton loadButton = new JButton("Load your previous work");
        loadButton.setActionCommand("load");
        loadButton.setPreferredSize(buttonSize);
        loadButton.addActionListener(this);
        saveLoadPanel.add(loadButton);
        mainFrame.add(saveLoadPanel, BorderLayout.SOUTH);
    }

    // MODIFIES: this
    // EFFECTS: builds an area to display followup list
    private void createFollowupPanel() {
        JPanel followupPanel = new JPanel(new BorderLayout());
        followupPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        //followupPanel.setSize((new Dimension((int) (0.5 * WIDTH), (int) (0.5 * HEIGHT))));
        JButton showFollowupButton = new JButton("Follow-up List");
        showFollowupButton.setActionCommand("showFollowups");
        showFollowupButton.setPreferredSize(buttonSize);
        showFollowupButton.addActionListener(this);
        followupPanel.add(showFollowupButton, BorderLayout.NORTH);
        followupListTextArea = new JTextArea();
        followupListTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(followupListTextArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        followupPanel.add(scrollPane, BorderLayout.CENTER);
        displayPanel.add(followupPanel);
    }

    // MODIFIES: this
    // EFFECTS: builds an area to display all patients
    private void createShowPatientsPanel() {
        JPanel showPatientsPanel = new JPanel(new BorderLayout());
        showPatientsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        //showPatientsPanel.setPreferredSize(new Dimension((int) (0.25 * WIDTH), (int) (0.25 * HEIGHT)));
        JButton showPatientsButton = new JButton("Show All Patients");
        showPatientsButton.setActionCommand("showPatients");
        showPatientsButton.setPreferredSize(buttonSize);
        showPatientsButton.addActionListener(this);
        showPatientsPanel.add(showPatientsButton, BorderLayout.NORTH);
        patientListTextArea = new JTextArea();
        patientListTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(patientListTextArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        showPatientsPanel.add(scrollPane, BorderLayout.CENTER);
        displayPanel.add(showPatientsPanel);
    }

    // MODIFIES: this
    // EFFECTS: builds an area for "add patient" function
    public void createAddPatientPanel() {
        addPatientPanel = new JPanel(new GridBagLayout());
        addPatientPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        setId(gbc);
        setGender(gbc);
        setAge(gbc);
        setOperation(gbc);
        //add patient button
        JButton addPatientButton = new JButton("Add Patient");
        addPatientButton.setActionCommand("addPatient");
        addPatientButton.setPreferredSize(buttonSize);
        addPatientButton.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 5;
        addPatientPanel.add(addPatientButton, gbc);
        mainFrame.add(addPatientPanel, BorderLayout.WEST);
    }

    // MODIFIES: this
    // EFFECTS: builds a blank and a label to collect patient's operation date
    private void setOperation(GridBagConstraints gbc) {
        //operation date label
        JLabel operationLabel = new JLabel("Operation Date (yyyy-mm-dd): ");
        operationLabel.setPreferredSize(elementSize);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 0, 30);
        addPatientPanel.add(operationLabel, gbc);
        //operation date text
        operationField = new JTextField(10);
        operationField.setPreferredSize(elementSize);
        gbc.gridx = 1;
        gbc.gridy = 3;
        addPatientPanel.add(operationField, gbc);
    }

    // MODIFIES: this
    // EFFECTS: builds a blank and a label to collect patient's age
    private void setAge(GridBagConstraints gbc) {
        //age label
        JLabel ageLabel = new JLabel("Age(0~100): ");
        ageLabel.setPreferredSize(elementSize);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 0, 30);
        addPatientPanel.add(ageLabel, gbc);
        //age text
        ageField = new JTextField(10);
        ageField.setPreferredSize(elementSize);
        gbc.gridx = 1;
        gbc.gridy = 2;
        addPatientPanel.add(ageField, gbc);
    }

    // MODIFIES: this
    // EFFECTS: builds a blank and a label to collect patient's gender
    private void setGender(GridBagConstraints gbc) {
        //gender label
        JLabel genderLabel = new JLabel("Gender(F/M): ");
        genderLabel.setPreferredSize(elementSize);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 0, 30);
        addPatientPanel.add(genderLabel, gbc);
        //gender text
        genderField = new JTextField(10);
        genderField.setPreferredSize(elementSize);
        gbc.gridx = 1;
        gbc.gridy = 1;
        addPatientPanel.add(genderField, gbc);
    }

    // MODIFIES: this
    // EFFECTS: builds a blank and a label to collect patient's id
    private void setId(GridBagConstraints gbc) {
        //id label
        JLabel idLabel = new JLabel("Patient ID: ");
        idLabel.setPreferredSize(elementSize);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 30);
        addPatientPanel.add(idLabel, gbc);
        //id text
        idField = new JTextField(10);
        idField.setPreferredSize(elementSize);
        gbc.gridx = 1;
        gbc.gridy = 0;
        addPatientPanel.add(idField, gbc);
    }

    // MODIFIES: this
    // EFFECTS:  adds a patient with id, gender, age, operation date into current trial
    private void addNewPatient() {
        String id = idField.getText();
        String gender = genderField.getText();
        int age = Integer.parseInt(ageField.getText());
        LocalDate operationDate = LocalDate.parse(operationField.getText());
        if (clinicalTrial.findPatient(id) != null) {
            JOptionPane.showMessageDialog(this, "Patient with ID "
                    + id + " already exists!", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            Patient newPatient = new Patient(id, gender, age, operationDate);
            clinicalTrial.addPatient(newPatient);
            JOptionPane.showMessageDialog(this, "New patient added successfully!");
            idField.setText("");
            genderField.setText("");
            ageField.setText("");
            operationField.setText("");
        }
    }

    // MODIFIES: this
    // EFFECTS: responses to different buttons
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("addPatient")) {
            addNewPatient();
        } else if (e.getActionCommand().equals("showPatients")) {
            displayPatientList();
        } else if (e.getActionCommand().equals("showFollowups")) {
            displayFollowupList();
        } else if (e.getActionCommand().equals("save")) {
            saveWork();
        } else if (e.getActionCommand().equals("load")) {
            loadWork();
        }
    }

    //-/***************************************************************************************
    // *    Title: <JSON serialization demo>
    // *    Code version: <20210307>
    // *    Availability: <https://github.com/stleary/JSON-java>
    // ***************************************************************************************/
    // MODIFIES: this
    // EFFECTS: loads trial from file
    private void loadWork() {
        try {
            clinicalTrial = jsonReader.read();
            JOptionPane.showMessageDialog(this, "Loaded " + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }


    //-/***************************************************************************************
    // *    Title: <JSON serialization demo>
    // *    Code version: <20210307>
    // *    Availability: <https://github.com/stleary/JSON-java>
    // ***************************************************************************************/
    // MODIFIES: this
    // EFFECTS: saves the trial to file
    private void saveWork() {
        try {
            jsonWriter.open();
            jsonWriter.write(clinicalTrial);
            jsonWriter.close();
            JOptionPane.showMessageDialog(this, "Saved " + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }


    // EFFECTS: show patients who need follow-up today
    private void displayFollowupList() {
        ArrayList<Patient> patients = clinicalTrial.getFollowUpList();
        StringBuilder sb = new StringBuilder();
        for (Patient patient : patients) {
            sb.append("\n").append(patient.printPatient());
        }
        followupListTextArea.setText(sb.toString());

    }

    // EFFECTS: show all enrolled patients in a panel
    private void displayPatientList() {
        ArrayList<Patient> patients = clinicalTrial.getPatientList();
        StringBuilder sb = new StringBuilder();
        for (Patient patient : patients) {
            sb.append("\n").append(patient.printPatient());
        }
        patientListTextArea.setText(sb.toString());
    }


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
