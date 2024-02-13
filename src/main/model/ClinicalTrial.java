package model;

import java.util.ArrayList;

public class ClinicalTrial {
    private String trialName;
    private int patientTarget;
    private ArrayList<Patient> patientList;

    public ClinicalTrial(String trialName, int patientTarget) {
        this.trialName = trialName;
        this.patientTarget = patientTarget;
        patientList = new ArrayList<>(patientTarget);
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

    public ArrayList<Patient> getPatientList() {
        return patientList;
    }

    public int getPatientNum() {
        return patientList.size();
    }

    public String getTrialName() {
        return trialName;
    }

    public void setTrialName(String trialName) {
        this.trialName = trialName;
    }
}
