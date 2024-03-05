package persistence;


import model.ClinicalTrial;
import model.Patient;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            ClinicalTrial wr = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyClinicalTrial() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyWorkRoom.json");
        try {
            ClinicalTrial wr = reader.read();
            assertEquals("currentTrial", wr.getTrialName());
            assertEquals(0, wr.getPatientNum());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralClinicalTrial() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralWorkRoom.json");
        try {
            ClinicalTrial wr = reader.read();
            assertEquals("currentTrial", wr.getTrialName());
            List<Patient> patients = wr.getPatientList();
            assertEquals(3, patients.size());
            checkPatient("001", patients.get(0));
            checkPatient("003", patients.get(1));
            checkPatient("004", patients.get(2));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}