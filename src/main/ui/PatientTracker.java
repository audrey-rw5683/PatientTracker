package ui;

import model.ClinicalTrial;
import model.Patient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class PatientTracker {
    Scanner scanner = new Scanner(System.in);
    ClinicalTrial currentTrial;
    LocalDate today;
    private Patient p1;

    public PatientTracker() {
        init();
        runPatientTracker();
    }

    public void init() {
        today = LocalDate.now();
        this.currentTrial = new ClinicalTrial("currentTrial", 50);
        p1 = new Patient("001", 'F', 28, LocalDate.of(2024, 2, 10));
        currentTrial.addPatient(p1);
    }

    public void displayMainMenu() {
        System.out.println("Clinic Management System");
        System.out.println("1. Management");
        System.out.println("2. Reminder");
        System.out.println("3. Current Status");
        System.out.println("4. Quit");
        System.out.print("Enter your choice: ");
    }

    public void displayManageSubMenu() {
        System.out.println("Manage Menu:");
        System.out.println("1. Add Patient");
        System.out.println("2. Follow-up");
        System.out.println("3. Remove Patient");
        System.out.println("4. Back");
    }

    public void displayFollowUpPeriod() {
        System.out.println("Choose one followup period: ");
        System.out.println("1. Post-operation 1-7 days");
        System.out.println("2. Post-operation 23-37 days");
        System.out.println("3. Post-operation 150-210 days");
        System.out.println("4. Post-operation 330-360 days");
    }

    public void runPatientTracker() {
        while (true) {
            displayMainMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    doManagement(scanner);
                    break;
                case 2:
                    doReminder();
                    break;
                case 3:
                    doCurrentStatus();
                    break;
                case 4:
                    System.out.println("Exiting the program...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void doManagement(Scanner scanner) {
        while (true) {
            displayManageSubMenu();
            System.out.print("Enter your choice: ");
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

    public void doAddPatient() {
        System.out.print("Enter patient ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter patient gender (M/F): ");
        char gender = scanner.nextLine().charAt(0);
        System.out.print("Enter patient age: ");
        int age = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter operation Date (yyyy-mm-dd): ");
        System.out.print("Enter operation year: ");
        int year = scanner.nextInt();
        System.out.print("Enter operation month: ");
        int month = scanner.nextInt();
        System.out.print("Enter operation day: ");
        int day = scanner.nextInt();
        LocalDate operationDate = LocalDate.of(year, month, day);
        Patient newPatient = new Patient(id, gender, age, operationDate);
        currentTrial.addPatient(newPatient);
    }

    public void doFollowUp() {
        System.out.print("Enter patient ID: ");
        String id = scanner.nextLine();
        Patient targetPatient = currentTrial.findPatient(id);
        if (targetPatient != null && targetPatient.getCurrentPeriod() != null) {
            targetPatient.getCurrentPeriod().markFollowed();
            System.out.println("Follow-up marked as completed for patient " + id);
        } else {
            System.out.println("Patient not found or no current follow-up period available.");
        }
    }

    public void doRemovePatient() {
        System.out.print("Enter patient ID: ");
        String id = scanner.nextLine();
        Patient targetPatient = currentTrial.findPatient(id);
        currentTrial.removePatient(targetPatient);
    }

    public void doReminder() {
        ArrayList<Patient> patientsToday = currentTrial.getFollowUpList();
        for (Patient p : patientsToday) {
            System.out.println("Follow up list: ");
            System.out.println(p.getPatientId());
            System.out.println(p.getCurrentPeriod().printPeriod());
        }
    }

    public void doCurrentStatus() {
        System.out.println("Clinical Trial Name: " + currentTrial.getTrialName());
        System.out.println("The Number of Patients: " + currentTrial.getPatientNum());
        System.out.println("Today is: " + today);
        ArrayList<Patient> allPatient = currentTrial.getPatientList();
        for (Patient p : allPatient) {
            System.out.println(p.getPatientId());
            System.out.println(p.printFollowUpPeriod());
        }
    }
}


