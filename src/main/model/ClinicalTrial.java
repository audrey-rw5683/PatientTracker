package model;

import java.util.ArrayList;

public class ClinicalTrial {
    private String trialName;
    private ArrayList<Patient> patientList;

    public ClinicalTrial(String trialName) {
        this.trialName = trialName;
        patientList = new ArrayList<>();
    }

    public void addPatient(Patient patient) {
        if (!patientList.contains(patient)) {
            patientList.add(patient);
            System.out.println("The patient is added successfully!");
        } else {
            System.out.println("This patient is already in the list!");
        }


    }

    //input might be null
    public void removePatient(Patient patient) {
        if (patientList.contains(patient)) {
            patientList.remove(patient);
            System.out.println("The patient is removed successfully!");
        } else {
            System.out.println("Cannot find this patient");
        }
    }

    public Patient findPatient(String id) {
        for (Patient p : patientList) {
            if (p.getPatientId().equals(id)) {
                return p;
            }
        }
        return null;
    }

    public ArrayList<Patient> getFollowUpList() {
        ArrayList<Patient> followUpList = new ArrayList<>();
        for (Patient p : patientList) {
            if (p.isNeedFollowUpToday()) {
                followUpList.add(p);
            }
        }
        return followUpList;
    }

    public int getFollowUpNum() {
        return getFollowUpList().size();
    }

    public int getCompletedNum() {
        return getCompletedList().size();
    }

    public ArrayList<Patient> getCompletedList() {
        ArrayList<Patient> completedList = new ArrayList<>();
        for (Patient p : getPatientList()) {
            if (p.isTrialCompleted()) {
                completedList.add(p);
            }
        }
        return completedList;
    }


    public ArrayList<Patient> getPatientList() {
        return patientList;
    }

    public int getPatientNum() {
        return patientList.size();
    }

    public String getTrialName() {
        return trialName;
    }
}
