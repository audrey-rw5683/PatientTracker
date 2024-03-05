package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

// Represents a test suite for ClinicalTrial
public class ClinicalTrialTest {
    private Patient patient1;
    private Patient patient2;
    private Patient patient3;
    private LocalDate today;
    private ClinicalTrial testTrial;

    @BeforeEach
    void setup() {
        today = LocalDate.now();
        patient1 = new Patient("001", "F", 28, today.minusDays(3));
        patient2 = new Patient("002", "M", 65, today.minusDays(500));
        patient3 = new Patient("003", "F", 57, today.minusDays(180));
        testTrial = new ClinicalTrial("Trial-1");
    }

    @Test
    void testAddSamePatient() {
        assertTrue(testTrial.getPatientList().isEmpty());
        testTrial.addPatient(patient1);
        assertEquals(1, testTrial.getPatientNum());
        assertEquals(patient1, testTrial.getPatientList().get(0));
        testTrial.addPatient(patient1);
        assertEquals(1, testTrial.getPatientNum());
        assertEquals(patient1, testTrial.getPatientList().get(0));
    }

    @Test
    void testAddDifferentPatient() {
        assertTrue(testTrial.getPatientList().isEmpty());
        testTrial.addPatient(patient1);
        assertEquals(1, testTrial.getPatientNum());
        assertEquals(patient1, testTrial.getPatientList().get(0));
        testTrial.addPatient(patient2);
        assertEquals(2, testTrial.getPatientNum());
        assertEquals(patient2, testTrial.getPatientList().get(1));
    }

    @Test
    void testRemoveExistingPatient() {
        assertTrue(testTrial.getPatientList().isEmpty());
        testTrial.addPatient(patient1);
        testTrial.addPatient(patient2);
        assertEquals(2, testTrial.getPatientNum());
        testTrial.removePatient(patient1);
        assertEquals(1, testTrial.getPatientNum());
        assertEquals(patient2, testTrial.getPatientList().get(0));
        testTrial.removePatient(patient2);
        assertEquals(0, testTrial.getPatientNum());
        assertTrue(testTrial.getPatientList().isEmpty());
    }

    @Test
    void testRemoveNonExistingPatient() {
        assertTrue(testTrial.getPatientList().isEmpty());
        testTrial.addPatient(patient1);
        testTrial.addPatient(patient2);
        assertEquals(2, testTrial.getPatientNum());
        testTrial.removePatient(patient3);
        assertEquals(2, testTrial.getPatientNum());
        testTrial.removePatient(patient1);
        assertEquals(patient2, testTrial.getPatientList().get(0));
        testTrial.removePatient(patient2);
        assertEquals(0, testTrial.getPatientNum());
        assertTrue(testTrial.getPatientList().isEmpty());
    }

    @Test
    void testFindPatient() {
        assertNull(testTrial.findPatient("001"));
        testTrial.addPatient(patient1);
        testTrial.addPatient(patient2);
        assertNull(testTrial.findPatient("003"));
        assertEquals(patient1,testTrial.findPatient("001"));
    }

    @Test
    void testGetFollowUpList() {
        assertTrue(testTrial.getFollowUpList().isEmpty());
        testTrial.addPatient(patient2);
        assertTrue(testTrial.getFollowUpList().isEmpty());
        testTrial.addPatient(patient1);
        assertEquals(1,testTrial.getFollowUpNum());
        assertEquals(patient1, testTrial.getFollowUpList().get(0));
    }

    @Test
    void testGetCompletedList() {
        assertTrue(testTrial.getCompletedList().isEmpty());
        testTrial.addPatient(patient2);
        testTrial.addPatient(patient1);
        assertTrue(testTrial.getCompletedList().isEmpty());
        for (FollowUpPeriod period : patient1.getFollowUpPeriods()) {
            period.setFollowed();
        }
        patient1.checkTrialCompleted();
        assertFalse(testTrial.getCompletedList().isEmpty());
        assertEquals(1, testTrial.getCompletedNum());
        assertEquals(patient1, testTrial.getCompletedList().get(0));
        patient2.getFollowUpPeriods().get(1).setFollowed();
        assertEquals(1, testTrial.getCompletedNum());
        assertEquals(patient1, testTrial.getCompletedList().get(0));
    }

    @Test
    void testGetTrialName() {
        assertEquals("Trial-1", testTrial.getTrialName());
    }
}
