package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// Represents a clinical trial with a list of enrolled patients
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
            EventLog.getInstance().logEvent(new Event("Added Patient: " + patient.getPatientId()));
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
        EventLog.getInstance().logEvent(new Event("Showed follow-up list"));
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
        EventLog.getInstance().logEvent(new Event("Showed all enrolled patients"));
        return patientList;
    }

    public int getPatientNum() {
        return patientList.size();
    }

    public String getTrialName() {
        return trialName;
    }

    //-/***************************************************************************************
    // *    Title: <JSON serialization demo>
    // *    Code version: <20210307>
    // *    Availability: <https://github.com/stleary/JSON-java>
    // ***************************************************************************************/
    // EFFECTS: returns this trial as JSON object
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("Clinical Trial", trialName);
        json.put("Patients", patientsToJson());
        return json;
    }

    //-/***************************************************************************************
    // *    Title: <JSON serialization demo>
    // *    Code version: <20210307>
    // *    Availability: <https://github.com/stleary/JSON-java>
    // ***************************************************************************************/
    // EFFECTS: returns patients in this trial as a JSON array
    private JSONArray patientsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (Patient p : patientList) {
            jsonArray.put(p.toJson());
        }

        return jsonArray;
    }
}
