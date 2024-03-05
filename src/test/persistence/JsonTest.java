package persistence;


import model.Patient;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkPatient(String id, Patient patient) {
        assertEquals(id, patient.getPatientId());
       // assertEquals(category, thingy.getCategory());
    }
}
