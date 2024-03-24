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


public class PatientTrackerUI extends JFrame implements ActionListener {
    private JFrame mainFrame;
    private ClinicalTrial clinicalTrial;
    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 600;
    private static final int BUTTON_WIDTH = 200;
    private static final int BUTTON_HEIGHT = 30;
    private static final int ELEMENT_WIDTH = 60;
    private static final int ELEMENT_HEIGHT = 30;
    private JPanel addPatientPanel;
    private JPanel showPatientsPanel;
    private JPanel followupPanel;
    private JPanel saveLoadPanel;
    private JButton addPatientButton;
    private JButton showPatientsButton;
    private JButton showFollowupButton;
    private JButton loadButton;
    private JButton saveButton;
    private Dimension buttonSize;
    private Dimension elementSize;
    private JTextField idField;
    private JTextField genderField;
    private JTextField ageField;
    private JTextField operationField;
    private JTextArea patientListTextArea;
    private JTextArea followupListTextArea;
    private final JsonWriter jsonWriter;
    private final JsonReader jsonReader;
    private static final String JSON_STORE = "./data/patientTrackerUI.json";

    public PatientTrackerUI() {
        mainFrame = new JFrame("Patient Tracking System");
        clinicalTrial = new ClinicalTrial("My Clinical Trial");
        mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        mainFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        buttonSize = new Dimension(BUTTON_WIDTH,BUTTON_HEIGHT);
        elementSize = new Dimension(ELEMENT_WIDTH,ELEMENT_HEIGHT);

        createAddPatientPanel();
        createShowPatientsPanel();
        createFollowupPanel();
        createSaveLoadPanel();

        //pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
        //setResizable(false);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    private void createSaveLoadPanel() {
        saveLoadPanel = new JPanel(new FlowLayout());
        saveLoadPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        saveButton = new JButton("Save your work");
        saveButton.setActionCommand("save");
        saveButton.setPreferredSize(buttonSize);
        saveButton.addActionListener(this);
        saveLoadPanel.add(saveButton);
        loadButton = new JButton("Load your previous work");
        loadButton.setActionCommand("load");
        loadButton.setPreferredSize(buttonSize);
        loadButton.addActionListener(this);
        saveLoadPanel.add(loadButton);
        mainFrame.add(saveLoadPanel, BorderLayout.NORTH);
    }

    private void createFollowupPanel() {
        followupPanel = new JPanel(new BorderLayout());
        followupPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        showFollowupButton = new JButton("Patients who needs follow today");
        showFollowupButton.setActionCommand("showFollowups");
        showFollowupButton.setPreferredSize(buttonSize);
        showFollowupButton.addActionListener(this);
        followupPanel.add(showFollowupButton, BorderLayout.NORTH);
        followupListTextArea = new JTextArea();
        followupListTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(followupListTextArea);
        followupPanel.add(scrollPane, BorderLayout.CENTER);
        mainFrame.add(followupPanel, BorderLayout.SOUTH);
    }

    private void createShowPatientsPanel() {
        showPatientsPanel = new JPanel(new BorderLayout());
        showPatientsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        showPatientsButton = new JButton("Show All Patients");
        showPatientsButton.setActionCommand("showPatients");
        showPatientsButton.setPreferredSize(buttonSize);
        showPatientsButton.addActionListener(this);
        showPatientsPanel.add(showPatientsButton, BorderLayout.NORTH);
        patientListTextArea = new JTextArea();
        patientListTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(patientListTextArea);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        showPatientsPanel.add(scrollPane, BorderLayout.CENTER);
        mainFrame.add(showPatientsPanel, BorderLayout.CENTER);
    }


    public void createAddPatientPanel() {
        addPatientPanel = new JPanel(new GridBagLayout());
        addPatientPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
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
        //gender label
        JLabel genderLabel = new JLabel("Gender: ");
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
        //age label
        JLabel ageLabel = new JLabel("Age: ");
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
        //operation date label
        JLabel operationLabel = new JLabel("Operation Date: ");
        ageLabel.setPreferredSize(elementSize);
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
        //add patient button
        addPatientButton = new JButton("Add Patient");
        addPatientButton.setActionCommand("addPatient");
        addPatientButton.setPreferredSize(buttonSize);
        addPatientButton.addActionListener(this);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 5;
        addPatientPanel.add(addPatientButton, gbc);

        mainFrame.add(addPatientPanel, BorderLayout.WEST);
    }




    private void addNewPatient() {
        String id = idField.getText();
        String gender = genderField.getText();
        int age = Integer.parseInt(ageField.getText());
        LocalDate operationDate = LocalDate.parse(operationField.getText());
        if (clinicalTrial.findPatient(id) != null) {
            JOptionPane.showMessageDialog(this, "Patient with ID " + id + " already exists!", "Error", JOptionPane.ERROR_MESSAGE);
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

    private void loadWork() {
        try {
            clinicalTrial = jsonReader.read();
            System.out.println("Loaded " + clinicalTrial.getTrialName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    private void saveWork() {
        try {
            jsonWriter.open();
            jsonWriter.write(clinicalTrial);
            jsonWriter.close();
            System.out.println("Saved " + clinicalTrial.getTrialName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    private void displayFollowupList() {
        ArrayList<Patient> patients = clinicalTrial.getFollowUpList();
        StringBuilder sb = new StringBuilder();
        for (Patient patient : patients) {
            sb.append("\n").append(patient.printPatient());
        }
        followupListTextArea.setText(sb.toString());

    }

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
        SwingUtilities.invokeLater(() -> {
            new PatientTrackerUI();
        });

    }

}
