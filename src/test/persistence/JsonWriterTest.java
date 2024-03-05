package persistence;


import model.ClinicalTrial;
import model.Patient;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonWriterTest extends JsonTest {
    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

    @Test
    void testWriterInvalidFile() {
        try {
            ClinicalTrial wr = new ClinicalTrial("currentTrial");
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            ClinicalTrial wr = new ClinicalTrial("currentTrial");
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyWorkroom.json");
            writer.open();
            writer.write(wr);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyWorkroom.json");
            wr = reader.read();
            assertEquals("currentTrial", wr.getTrialName());
            assertEquals(0, wr.getPatientNum());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            ClinicalTrial wr = new ClinicalTrial("currentTrial");
            LocalDate today = LocalDate.now();
            wr.addPatient(new Patient("001", "F", 28, today.minusDays(3)));
            wr.addPatient(new Patient("003", "M", 37, today.minusDays(180)));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralWorkroom.json");
            writer.open();
            writer.write(wr);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralWorkroom.json");
            wr = reader.read();
            assertEquals("currentTrial", wr.getTrialName());
            List<Patient> patients = wr.getPatientList();
            assertEquals(2, patients.size());
            checkPatient("001", patients.get(0));
            checkPatient("003", patients.get(1));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}