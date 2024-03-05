package persistence;

import model.ClinicalTrial;
import model.Patient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.stream.Stream;

// Represents a reader that reads trial from JSON data stored in file
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
        Patient patient = new Patient(patientId, gender, age, operationDate);
        trial.addPatient(patient);
    }
}
