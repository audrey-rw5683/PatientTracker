package persistence;


import model.ClinicalTrial;
import model.FollowUpPeriod;
import model.Patient;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Represents a test suite for JsonWriter
//-/***************************************************************************************
// *    Title: <JSON serialization demo>
// *    Code version: <20210307>
// *    Availability: <https://github.com/stleary/JSON-java>
// ***************************************************************************************/
class JsonWriterTest extends JsonTest {
    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

    @Test
    void testWriterInvalidFile() {
        try {
            ClinicalTrial trial = new ClinicalTrial("currentTrial");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyClinicalTrial() {
        try {
            ClinicalTrial trial = new ClinicalTrial("currentTrial");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyClinicalTrial.json");
            writer.open();
            writer.write(trial);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyClinicalTrial.json");
            trial = reader.read();
            assertEquals("currentTrial", trial.getTrialName());
            assertEquals(0, trial.getPatientNum());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralClinicalTrial() {
        try {
            ClinicalTrial trial = new ClinicalTrial("currentTrial");
            LocalDate today = LocalDate.now();
            trial.addPatient(new Patient("001", "F", 28, today.minusDays(3)));
            trial.addPatient(new Patient("003", "M", 37, today.minusDays(180)));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralClinicalTrial.json");
            writer.open();
            writer.write(trial);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralClinicalTrial.json");
            trial = reader.read();
            assertEquals("currentTrial", trial.getTrialName());
            List<Patient> patients = trial.getPatientList();
            assertEquals(2, patients.size());
            checkPatient("001", patients.get(0));
            checkPatient("003", patients.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}