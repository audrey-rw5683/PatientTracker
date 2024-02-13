package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ClinicalTrialTest {
    private Patient p1;
    private Patient p2;
    private ClinicalTrial testTrial;

    @BeforeEach
    void setup () {
        p1 = new Patient("001", 'F', 28, LocalDate.of(2024, 2, 10));
        p2 = new Patient("002", 'M', 28, LocalDate.of(2024, 1, 10));
        testTrial = new ClinicalTrial("trial1", 50);
    }

    @Test
    void getFollowUpListTest() {
        assertTrue(testTrial.getFollowUpList().isEmpty());
        testTrial.addPatient(p1);
        assertFalse(testTrial.getFollowUpList().isEmpty());
        assertEquals(p1, testTrial.findPatient("001"));
        System.out.println(p1.getCurrentPeriod().printPeriod());
    }
}


