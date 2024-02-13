package model;

import java.time.LocalDate;

public class FollowUpPeriod {
    private String followUpMark;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean isFollowed;

    public FollowUpPeriod(String followUpMark, LocalDate operationDate) {
        this.followUpMark = followUpMark;
        switch (followUpMark) {
            case "FU7D":
                startDate = operationDate.plusDays(1);
                endDate = operationDate.plusDays(7);
                isFollowed = false;
                break;
            case "FU1M":
                startDate = operationDate.plusDays(23);
                endDate = operationDate.plusDays(37);
                isFollowed = false;
                break;
            case "FU6M":
                startDate = operationDate.plusDays(150);
                endDate = operationDate.plusDays(210);
                isFollowed = false;
                break;
            case "FU1Y":
                startDate = operationDate.plusDays(330);
                endDate = operationDate.plusDays(390);
                isFollowed = false;
                break;
        }
    }

    public String printPeriod() {
        String str = "From: " + getStartDate() + " to " + getEndDate() + ". Is complete? " + checkIsCompleted();
        return str;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public boolean checkIsCompleted() {
        return isFollowed;
    }

    public void markFollowed() {
        isFollowed = true;
    }
}
