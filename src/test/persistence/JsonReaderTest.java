package persistence;


import model.ClinicalTrial;
import model.Patient;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

// Represents a test suite for JsonReader
//-/***************************************************************************************
// *    Title: <JSON serialization demo>
// *    Code version: <20210307>
// *    Availability: <https://github.com/stleary/JSON-java>
// ***************************************************************************************/
class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            ClinicalTrial trial = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyClinicalTrial() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyClinicalTrial.json");
        try {
            ClinicalTrial trial = reader.read();
            assertEquals("currentTrial", trial.getTrialName());
            assertEquals(0, trial.getPatientNum());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralClinicalTrial() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralClinicalTrial.json");
        try {
            ClinicalTrial trial = reader.read();
            assertEquals("currentTrial", trial.getTrialName());
            List<Patient> patients = trial.getPatientList();
            assertEquals(3, patients.size());
            checkPatient("001", patients.get(0));
            checkPatient("003", patients.get(1));
            checkPatient("004", patients.get(2));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}