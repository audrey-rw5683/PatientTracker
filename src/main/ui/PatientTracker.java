package ui;

import model.ClinicalTrial;
import model.Patient;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

//Patient Tracker Application
public class PatientTracker {
    private static final String JSON_STORE = "./data/patientTracker.json";
    Scanner scanner = new Scanner(System.in);
    ClinicalTrial currentTrial;
    LocalDate today;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the tracker application
    public PatientTracker() throws FileNotFoundException {
        init();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runPatientTracker();
    }

    // MODIFIES: this
    // EFFECTS: initializes the trial
    public void init() {
        today = LocalDate.now();
        this.currentTrial = new ClinicalTrial("currentTrial");
        // p1 is after operation 3 days
        Patient p1 = new Patient("001", "F", 28, today.minusDays(3));
        // p2 is after operation 33 days
        Patient p2 = new Patient("002", "F", 45, today.minusDays(33));
        // p3 is after operation 180 days
        Patient p3 = new Patient("003", "M", 37, today.minusDays(180));
        // p4 is after operation 500 days
        Patient p4 = new Patient("004", "M", 82, today.minusDays(500));

        p2.getFollowUpPeriods().get(0).setFollowed();
        p3.getFollowUpPeriods().get(0).setFollowed();
        p3.getFollowUpPeriods().get(1).setFollowed();
        p4.getFollowUpPeriods().get(0).setFollowed();
        p4.getFollowUpPeriods().get(1).setFollowed();
        p4.getFollowUpPeriods().get(2).setFollowed();

        currentTrial.getPatientList().add(p1);
        currentTrial.getPatientList().add(p2);
        currentTrial.getPatientList().add(p3);
        currentTrial.getPatientList().add(p4);
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    public void runPatientTracker() {
        while (true) {
            displayMainMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    doManagement();
                    break;
                case 2:
                    doReminder();
                    break;
                case 3:
                    doCurrentStatus();
                    break;
                case 4:
                    saveClinicalTrial();
                    break;
                case 5:
                    loadClinicalTrial();
                    break;
                case 6:
                    System.out.println("\nExiting the program...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.\n");
            }
        }
    }

    // EFFECTS: displays the main menu
    public void displayMainMenu() {
        System.out.println("\nClinic Management System");
        System.out.println("1. Management");
        System.out.println("2. Reminder");
        System.out.println("3. Current Status");
        System.out.println("4. Save today's follow-up progress");
        System.out.println("5. Load previous follow-up progress");
        System.out.println("6. Quit");
        System.out.print("Enter your choice: ");
    }

    // EFFECTS: displays the management menu
    public void displayManageSubMenu() {
        System.out.println("\nManage Menu:");
        System.out.println("1. Add Patient");
        System.out.println("2. Follow-up");
        System.out.println("3. Remove Patient");
        System.out.println("4. Back");
    }

    // EFFECTS: displays the patient's all follow-up periods
    public void displayFollowUpPeriod(Patient patient) {
        System.out.println("\nChoose one followup period as completed: ");
        System.out.println("1. Post-operation 1-7 days" + patient.getFollowUpPeriods().get(0).printPeriod());
        System.out.println("2. Post-operation 23-37 days" + patient.getFollowUpPeriods().get(1).printPeriod());
        System.out.println("3. Post-operation 150-210 days" + patient.getFollowUpPeriods().get(2).printPeriod());
        System.out.println("4. Post-operation 330-360 days" + patient.getFollowUpPeriods().get(3).printPeriod());
        System.out.println("5. back to management");
        System.out.println("Trial completed? " + patient.isTrialCompleted());
    }



    // MODIFIES: this
    // EFFECTS: goes to management sub menu
    private void doManagement() {
        while (true) {
            displayManageSubMenu();
            System.out.print("\nEnter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    doAddPatient();
                    break;
                case 2:
                    doFollowUp();
                    break;
                case 3:
                    doRemovePatient();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    // REQUIRES: valid inputs
    // MODIFIES: this
    // EFFECTS: processes adding patient
    public void doAddPatient() {
        System.out.print("\nEnter patient ID: ");
        String id = scanner.nextLine();
        if (currentTrial.findPatient(id) != null) {
            System.out.println("Patient with ID " + id + " already exists.");
            doManagement();
        }
        System.out.print("Enter patient gender (M/F): ");
        String gender = scanner.nextLine();
        System.out.print("Enter patient age (0-100): ");
        int age = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter operation Date (yyyy-mm-dd): ");
        System.out.print("Enter operation year (yyyy): ");
        int year = scanner.nextInt();
        System.out.print("Enter operation month (mm): ");
        int month = scanner.nextInt();
        System.out.print("Enter operation day (dd): ");
        int day = scanner.nextInt();
        LocalDate operationDate = LocalDate.of(year, month, day);
        Patient newPatient = new Patient(id, gender, age, operationDate);
        currentTrial.addPatient(newPatient);
        System.out.println("The patient is added successfully!");
    }

    // MODIFIES: this
    // EFFECTS: processes following up
    public void doFollowUp() {
        System.out.print("\nEnter patient ID: ");
        String id = scanner.nextLine();
        if (currentTrial.findPatient(id) == null) {
            System.out.println("Cannot find this patient");
            doManagement();
        }
        Patient targetPatient = currentTrial.findPatient(id);
        if (targetPatient != null) {
            System.out.println("Patient History: ");
            displayFollowUpPeriod(targetPatient);
            int index = scanner.nextInt();
            if (index > 4) {
                doManagement();
            }
            targetPatient.markFollowed(index);
            targetPatient.checkTrialCompleted();
            System.out.println("\nFollow-up marked as completed for patient " + id);
            System.out.println("Patient History: ");
            displayFollowUpPeriod(targetPatient);
        } else {
            System.out.println("Patient not found or no current follow-up period available.");
        }
    }

    // MODIFIES: this
    // EFFECTS: processes moving patient
    public void doRemovePatient() {
        System.out.print("\nEnter patient ID: ");
        String id = scanner.nextLine();
        if (currentTrial.findPatient(id) == null) {
            System.out.println("Cannot find this patient");
            doManagement();
        }
        Patient targetPatient = currentTrial.findPatient(id);
        currentTrial.removePatient(targetPatient);
        System.out.println("This patient is removed successfully!");
    }

    // MODIFIES: this
    // EFFECTS: only displays a list of patients who needs follow-up today
    // patients who missed follow-up won't show up here
    public void doReminder() {
        ArrayList<Patient> patientsToday = currentTrial.getFollowUpList();
        System.out.println("\nFollow up list: ");
        for (Patient p : patientsToday) {
            System.out.println(p.getPatientId());
            System.out.println(p.getCurrentPeriod().printPeriod());
        }
    }

    // MODIFIES: this
    // EFFECTS: summarize all patients
    public void doCurrentStatus() {
        System.out.println("\nClinical Trial Name: " + currentTrial.getTrialName());
        System.out.println("The Number of all patients: " + currentTrial.getPatientNum());
        System.out.println("The Number of patients who need follow-up today: " + currentTrial.getFollowUpNum());
        System.out.println("The Number of patients who has completed this trial: " + currentTrial.getCompletedNum());
        System.out.println("Today is: " + today);
        ArrayList<Patient> allPatient = currentTrial.getPatientList();
        for (Patient p : allPatient) {
            System.out.println(p.getPatientId());
            System.out.println(p.printFollowUpPeriods());
        }
    }

    // EFFECTS: saves the workroom to file
    private void saveClinicalTrial() {
        try {
            jsonWriter.open();
            jsonWriter.write(currentTrial);
            jsonWriter.close();
            System.out.println("Saved " + currentTrial.getTrialName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void loadClinicalTrial() {
        try {
            currentTrial = jsonReader.read();
            System.out.println("Loaded " + currentTrial.getTrialName() + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}


