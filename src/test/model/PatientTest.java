package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class PatientTest {
    private Patient p1;
    private Patient p2;

    @BeforeEach
    void setup () {
        p1 = new Patient("001", 'F', 28, LocalDate.of(2024, 2, 10));
        p2 = new Patient("002", 'M', 28, LocalDate.of(2024, 1, 10));
    }

    @Test
    void constructorTest() {
        p1.getCurrentPeriod().markFollowed();
        assertTrue(p1.getCurrentPeriod().checkIsCompleted());
    }
}
