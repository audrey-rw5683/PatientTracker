package model;

import java.time.LocalDate;

// Represents a followup period calculated from a patient's operation date
// along with a start date, end date and the completion status of the current follow-up
public class FollowUpPeriod {
    //private String followUpMark;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean isFollowed;

    //EFFECTS: makes a follow-up period according to the mark and operation date
    //if mark is FU7D, the follow-up period is post-operation 1-7 days
    //if mark is FU1M, the follow-up period is post-operation 23-37 days
    //if mark is FU6M, the follow-up period is post-operation 150-210 days
    //if mark is FU1Y, the follow-up period is post-operation 330-390 days
    public FollowUpPeriod(String followUpMark, LocalDate operationDate) {
        //this.followUpMark = followUpMark;
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

    //EFFECTS: produces a line containing follow-up start date, end date and completion status
    public String printPeriod() {
        return "From: " + getStartDate() + " to " + getEndDate() + ". Is followed? " + checkIsFollowed();
    }

    //EFFECTS: produces the follow-up start date
    public LocalDate getStartDate() {
        return startDate;
    }

    //EFFECTS: produces the follow-up end date
    public LocalDate getEndDate() {
        return endDate;
    }

    //EFFECTS: produces the follow-up status
    public boolean checkIsFollowed() {
        return isFollowed;
    }

    //MODIFIES: this
    //EFFECTS: mark the period as followed
    public void setFollowed() {
        isFollowed = true;
    }
}
