package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

public class ClinicalTrial implements Writable {
    private final String trialName;
    private final ArrayList<Patient> patientList;


    //EFFECTS: constructs a clinical trial with a name and a patient list
    public ClinicalTrial(String trialName) {
        this.trialName = trialName;
        patientList = new ArrayList<>();
    }

    //MODIFIES: this
    //EFFECTS: add the patient to the patient list,
    // same patient cannot be added twice
    public void addPatient(Patient patient) {
        if (null == findPatient(patient.getPatientId())) {
            patientList.add(patient);
        }
    }

    //MODIFIES: this
    //EFFECTS: remove the patient from the patient list
    // do nothing when the patient is not in the list
    public void removePatient(Patient patient) {
        if (null != findPatient(patient.getPatientId())) {
            patientList.remove(patient);
        }
    }


    //EFFECTS: find the patient with the given id
    // if there is no such patient, return null
    public Patient findPatient(String id) {
        for (Patient p : patientList) {
            if (p.getPatientId().equals(id)) {
                return p;
            }
        }
        return null;
    }


    //EFFECTS: generates a list of patients who needs follow-up today
    public ArrayList<Patient> getFollowUpList() {
        ArrayList<Patient> followUpList = new ArrayList<>();
        for (Patient p : patientList) {
            if (p.isNeedFollowUpToday()) {
                followUpList.add(p);
            }
        }
        return followUpList;
    }

    //EFFECTS: generates a list of patients who completed the trial
    public ArrayList<Patient> getCompletedList() {
        ArrayList<Patient> completedList = new ArrayList<>();
        for (Patient p : getPatientList()) {
            if (p.isTrialCompleted()) {
                completedList.add(p);
            }
        }
        return completedList;
    }

    public int getFollowUpNum() {
        return getFollowUpList().size();
    }

    public int getCompletedNum() {
        return getCompletedList().size();
    }

    //EFFECTS: generates a list of patients who enrolled in the trial
    public ArrayList<Patient> getPatientList() {
        return patientList;
    }

    public int getPatientNum() {
        return patientList.size();
    }

    public String getTrialName() {
        return trialName;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Clinical Trial", trialName);
        json.put("Patients", patientsToJson());
        return json;
    }

    // EFFECTS: returns things in this workroom as a JSON array
    private JSONArray patientsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Patient p : patientList) {
            jsonArray.put(p.toJson());
        }

        return jsonArray;
    }
}
