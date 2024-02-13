package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class FollowUpPeriodTest {
    private FollowUpPeriod period1;

    @BeforeEach
    void setup() {
        period1 = new FollowUpPeriod("FU7D", LocalDate.of(2024, 2, 10));
    }

    @Test
    void printPeriodTest(){
        System.out.println(period1.printPeriod());
    }
}
