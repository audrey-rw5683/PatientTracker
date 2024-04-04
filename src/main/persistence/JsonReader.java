package persistence;

import model.*;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.stream.Stream;

// Represents a reader that reads trial from JSON data stored in file
//-/***************************************************************************************
// *    Title: <JSON serialization demo>
// *    Code version: <20210307>
// *    Availability: <https://github.com/stleary/JSON-java>
// ***************************************************************************************/
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads trial from file and returns it;
    // throws IOException if an error occurs reading data from file
    public ClinicalTrial read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        EventLog.getInstance().logEvent(new Event("Loaded work"));
        return parseClinicalTrial(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses a trial from JSON object and returns it
    private ClinicalTrial parseClinicalTrial(JSONObject jsonObject) {
        String name = jsonObject.getString("Clinical Trial");
        ClinicalTrial trial = new ClinicalTrial(name);
        addPatients(trial, jsonObject);
        return trial;
    }

    // MODIFIES: trial
    // EFFECTS: parses patients from JSON object and adds them to trial
    private void addPatients(ClinicalTrial trial, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("Patients");
        for (Object json : jsonArray) {
            JSONObject nextPatient = (JSONObject) json;
            addPatient(trial, nextPatient);
        }
    }

    // MODIFIES: trial
    // EFFECTS: parses patient from JSON object and adds it to trial
    private void addPatient(ClinicalTrial trial, JSONObject jsonObject) {
        String patientId = jsonObject.getString("id");
        String gender = jsonObject.getString("gender");
        int age = jsonObject.getInt("age");
        LocalDate operationDate = LocalDate.parse(jsonObject.getString("operation date"));
        JSONArray followUpPeriodsArray = jsonObject.getJSONArray("followUpPeriods");
        ArrayList<FollowUpPeriod> followUpPeriods = getFollowUpPeriods(followUpPeriodsArray);
        boolean complete = jsonObject.getBoolean("trial completed");
        Patient patient = new Patient(patientId, gender, age, operationDate);
        patient.setFollowUpPeriods(followUpPeriods);
        patient.setTrialCompleted(complete);
        trial.addPatient(patient);
    }

    // MODIFIES: patient
    // EFFECTS: parses followup period from JSON object and adds it to patient
    private static ArrayList<FollowUpPeriod> getFollowUpPeriods(JSONArray followUpPeriodsArray) {
        ArrayList<FollowUpPeriod> followUpPeriods = new ArrayList<>();
        for (Object json : followUpPeriodsArray) {
            JSONObject nextPeriod = (JSONObject) json;
            LocalDate startDate = LocalDate.parse(nextPeriod.getString("startDate"));
            LocalDate endDate = LocalDate.parse(nextPeriod.getString("endDate"));
            boolean isFollowed = nextPeriod.getBoolean("isFollowed");
            FollowUpPeriod period = new FollowUpPeriod(startDate, endDate, isFollowed);
            followUpPeriods.add(period);
        }
        return followUpPeriods;
    }


}
