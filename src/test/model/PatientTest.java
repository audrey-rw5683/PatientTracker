package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

// Represents a test suite for Patient
public class PatientTest {
    private Patient patient1;
    private Patient patient2;
    private Patient patient3;
    private LocalDate today;
    private FollowUpPeriod fup1;
    private FollowUpPeriod fup2;
    private FollowUpPeriod fup3;
    private FollowUpPeriod fup4;

    @BeforeEach
    void setup () {
        today = LocalDate.now();
        patient1 = new Patient("001", "F", 28, today.minusDays(3));
        patient2 = new Patient("002", "M", 65, today.minusDays(500));
        patient3 = new Patient("003", "F", 57, today.plusDays(1000));
        fup1 = new FollowUpPeriod("FU7D", today.minusDays(3));
        fup2 = new FollowUpPeriod("FU1M", today.minusDays(3));
        fup3 = new FollowUpPeriod("FU6M", today.minusDays(3));
        fup4 = new FollowUpPeriod("FU1Y", today.minusDays(3));
    }

    @Test
    void testConstructor() {
        assertEquals("001", patient1.getPatientId());
        assertEquals("F", patient1.getGender());
        assertEquals(28, patient1.getAge());
        assertEquals(today.minusDays(3), patient1.getOperationDate());
        assertEquals(4, patient1.getIsFollowedList().size());
        assertFalse(patient1.getIsFollowedList().contains(true));
        assertEquals("FU7D", patient1.getFollowUpMarks().get(0));
        assertEquals("FU1M", patient1.getFollowUpMarks().get(1));
        assertEquals("FU6M", patient1.getFollowUpMarks().get(2));
        assertEquals("FU1Y", patient1.getFollowUpMarks().get(3));
        assertFalse(patient1.isTrialCompleted());
        assertTrue(patient1.isNeedFollowUpToday());
        assertFalse(patient2.isNeedFollowUpToday());
        assertEquals(fup1.printPeriod(),patient1.getFollowUpPeriods().get(0).printPeriod());
        assertEquals(fup2.printPeriod(),patient1.getFollowUpPeriods().get(1).printPeriod());
        assertEquals(fup3.printPeriod(),patient1.getFollowUpPeriods().get(2).printPeriod());
        assertEquals(fup4.printPeriod(),patient1.getFollowUpPeriods().get(3).printPeriod());
        assertFalse(patient1.getIsFollowedList().get(0));
        assertFalse(patient1.getIsFollowedList().get(1));
        assertFalse(patient1.getIsFollowedList().get(2));
        assertFalse(patient1.getIsFollowedList().get(3));
    }

    @Test
    public void testIsTrialCompleted() {
        assertFalse(patient1.isTrialCompleted());
        for (FollowUpPeriod period : patient1.getFollowUpPeriods()) {
            period.setFollowed();
        }
        patient1.checkTrialCompleted();
        assertTrue(patient1.isTrialCompleted());
        assertFalse(patient2.getFollowUpPeriods().get(0).checkIsFollowed());
        assertFalse(patient2.isTrialCompleted());
        patient2.getFollowUpPeriods().get(1).setFollowed();
        assertFalse(patient2.isTrialCompleted());
    }

    @Test
    public void testIsTrialNotCompleted() {
        assertFalse(patient1.isTrialCompleted());
        for (int i = 0; i < patient1.getFollowUpPeriods().size() - 1; i++) {
            patient1.getFollowUpPeriods().get(i).setFollowed();
        }
        patient1.checkTrialCompleted();
        assertFalse(patient2.isTrialCompleted());
    }

    @Test
    public void testCheckNeedFollowUpToday() {
        assertTrue(patient1.checkNeedFollowUpToday());
        patient1.getCurrentPeriod().setFollowed();
        assertFalse(patient1.checkNeedFollowUpToday());
        assertFalse(patient2.checkNeedFollowUpToday());
    }


    @Test
    void testMarkFollowed() {
        assertFalse(patient1.getFollowUpPeriods().get(0).checkIsFollowed());
        patient1.markFollowed(1);
        assertTrue(patient1.getFollowUpPeriods().get(0).checkIsFollowed());
    }

    @Test
    void testGetCurrentPeriod() {
        assertEquals(fup1.printPeriod(), patient1.getCurrentPeriod().printPeriod());
        assertNull(patient2.getCurrentPeriod());
        assertNull(patient3.getCurrentPeriod());
    }

    @Test
    void testPrintFollowUpPeriods() {
        String expectedOutput = fup1.printPeriod() + "\n"
                + fup2.printPeriod() + "\n"
                + fup3.printPeriod() + "\n"
                + fup4.printPeriod() + "\n";
        assertEquals(expectedOutput, patient1.printFollowUpPeriods());
    }

    @Test
    void testGetFollowUpMarks() {
        assertEquals("FU7D", patient1.getFollowUpMarks().get(0));
        assertEquals("FU1M", patient1.getFollowUpMarks().get(1));
        assertEquals("FU6M", patient1.getFollowUpMarks().get(2));
        assertEquals("FU1Y", patient1.getFollowUpMarks().get(3));
    }
}
