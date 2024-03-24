package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

// Represents a test suite for FollowUpPeriod
public class FollowUpPeriodTest {
    private FollowUpPeriod periodFU7D;
    private FollowUpPeriod periodFU1M;
    private FollowUpPeriod periodFU6M;
    private FollowUpPeriod periodFU1Y;
    private LocalDate operationDate;

    @BeforeEach
    public void setUp() {
        operationDate = LocalDate.of(2023, 2, 1);
        periodFU7D = new FollowUpPeriod("FU7D", operationDate);
        periodFU1M = new FollowUpPeriod("FU1M", operationDate);
        periodFU6M = new FollowUpPeriod("FU6M", operationDate);
        periodFU1Y = new FollowUpPeriod("FU1Y", operationDate);
    }

    @Test
    void testConstructor1 () {
        assertEquals(operationDate.plusDays(1), periodFU7D.getStartDate());
        assertEquals(operationDate.plusDays(7), periodFU7D.getEndDate());
        assertFalse(periodFU7D.checkIsFollowed());
    }

    @Test
    void testConstructor2 () {
        assertEquals(operationDate.plusDays(23), periodFU1M.getStartDate());
        assertEquals(operationDate.plusDays(37), periodFU1M.getEndDate());
        assertFalse(periodFU1M.checkIsFollowed());
    }

    @Test
    void testConstructor3 () {
        assertEquals(operationDate.plusDays(150), periodFU6M.getStartDate());
        assertEquals(operationDate.plusDays(210), periodFU6M.getEndDate());
        assertFalse(periodFU6M.checkIsFollowed());

    }

    @Test
    void testConstructor4 () {
        assertEquals(operationDate.plusDays(330), periodFU1Y.getStartDate());
        assertEquals(operationDate.plusDays(390), periodFU1Y.getEndDate());
        assertFalse(periodFU1Y.checkIsFollowed());
    }

    @Test
    void testConstructorInvalid () {
        FollowUpPeriod periodInvalid = new FollowUpPeriod("Invalid", operationDate);
        assertNull(periodInvalid.getStartDate());
        assertNull(periodInvalid.getEndDate());
        assertFalse(periodInvalid.checkIsFollowed());
    }

    @Test
    public void testPrintPeriod() {
        String expectedFU7D = "From: 2023-02-02 to 2023-02-08. Is followed? false";
        String expectedFU1M = "From: 2023-02-24 to 2023-03-10. Is followed? false";
        String expectedFU6M = "From: 2023-07-01 to 2023-08-30. Is followed? false";
        String expectedFU1Y = "From: 2023-12-28 to 2024-02-26. Is followed? false";

        assertEquals(expectedFU7D, periodFU7D.printPeriod());
        assertEquals(expectedFU1M, periodFU1M.printPeriod());
        assertEquals(expectedFU6M, periodFU6M.printPeriod());
        assertEquals(expectedFU1Y, periodFU1Y.printPeriod());
    }

    @Test
    public void testGetStartDate() {
        LocalDate expectedFU7D = operationDate.plusDays(1);
        LocalDate expectedFU1M = operationDate.plusDays(23);
        LocalDate expectedFU6M = operationDate.plusDays(150);
        LocalDate expectedFU1Y = operationDate.plusDays(330);

        assertEquals(expectedFU7D, periodFU7D.getStartDate());
        assertEquals(expectedFU1M, periodFU1M.getStartDate());
        assertEquals(expectedFU6M, periodFU6M.getStartDate());
        assertEquals(expectedFU1Y, periodFU1Y.getStartDate());
    }

    @Test
    public void testGetEndDate() {
        LocalDate expectedFU7D = operationDate.plusDays(7);
        LocalDate expectedFU1M = operationDate.plusDays(37);
        LocalDate expectedFU6M = operationDate.plusDays(210);
        LocalDate expectedFU1Y = operationDate.plusDays(390);

        assertEquals(expectedFU7D, periodFU7D.getEndDate());
        assertEquals(expectedFU1M, periodFU1M.getEndDate());
        assertEquals(expectedFU6M, periodFU6M.getEndDate());
        assertEquals(expectedFU1Y, periodFU1Y.getEndDate());
    }

    @Test
    public void testCheckIsFollowed() {
        assertFalse(periodFU7D.checkIsFollowed());
        assertFalse(periodFU1M.checkIsFollowed());
        assertFalse(periodFU6M.checkIsFollowed());
        assertFalse(periodFU1Y.checkIsFollowed());
    }

    @Test
    public void testSetFollowed() {
        periodFU7D.isFollowed();
        assertTrue(periodFU7D.checkIsFollowed());
        periodFU1M.isFollowed();
        assertTrue(periodFU1M.checkIsFollowed());
        periodFU6M.isFollowed();
        assertTrue(periodFU6M.checkIsFollowed());
        periodFU1Y.isFollowed();
        assertTrue(periodFU1Y.checkIsFollowed());
    }
}
