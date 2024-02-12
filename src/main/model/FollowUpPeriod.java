package model;

import java.time.LocalDate;

public class FollowUpPeriod {
    private String followUpMark;
    private LocalDate startDate;
    private LocalDate endDate;

    public FollowUpPeriod(String followUpMark, LocalDate operationDate) {
        this.followUpMark = followUpMark;
        switch (followUpMark) {
            case "FU7D":
                startDate = operationDate.plusDays(1);
                endDate = operationDate.plusDays(7);
                break;
            case "FU1M":
                startDate = operationDate.plusDays(23);
                endDate = operationDate.plusDays(37);
                break;
            case "FU6M":
                startDate = operationDate.plusDays(150);
                endDate = operationDate.plusDays(210);
                break;
            case "FU1Y":
                startDate = operationDate.plusDays(330);
                endDate = operationDate.plusDays(390);
                break;
        }
    }
}
