package persistence;


import model.Patient;

import static org.junit.jupiter.api.Assertions.assertEquals;


//-/***************************************************************************************
// *    Title: <JSON serialization demo>
// *    Code version: <20210307>
// *    Availability: <https://github.com/stleary/JSON-java>
// ***************************************************************************************/
public class JsonTest {
    protected void checkPatient(String id, Patient patient) {
        assertEquals(id, patient.getPatientId());
    }
}
