package model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;


public class Patient {
    private String patientId;
    private char gender;
    private int age;
    private LocalDate operationDate;
    private ArrayList<FollowUpPeriod> followUpPeriods;
    private ArrayList<Boolean> isFollowedList;
    private ArrayList<String> followUpMarks = new ArrayList<>(Arrays.asList("FU7D", "FU1M", "FU6M", "FU1Y"));
    private boolean needFollowUpToday;
    private boolean trialCompleted;

    //EFFECTS: constructs a patient with id, gender, age and operation date
    //generates a list of follow-up periods according to the operation date
    //The patient has not completed the trial by default
    public Patient(String patientId, char gender, int age, LocalDate operationDate) {
        this.patientId = patientId;
        this.gender = gender;
        this.age = age;
        this.operationDate = operationDate;

        isFollowedList = new ArrayList<>(4);
        for (int i = 0; i < 4; i++) {
            this.isFollowedList.add(false);
        }

        followUpPeriods = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            this.followUpPeriods.add(new FollowUpPeriod(followUpMarks.get(i), operationDate));
        }

        trialCompleted = false;

        needFollowUpToday = checkNeedFollowUpToday();
    }


    //MODIFIES: this
    //EFFECTS: when each period is followed, the trial is completed
    public void checkTrialCompleted() {
        for (FollowUpPeriod period : followUpPeriods) {
            if (!period.checkIsFollowed()) {
                trialCompleted = false;
            } else {
                trialCompleted = true;
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: when today is in one of periods, the patient needs a follow-up
    public boolean checkNeedFollowUpToday() {
        LocalDate today = LocalDate.now();
        ArrayList<Boolean> needFollowUp = new ArrayList<>();
        for (FollowUpPeriod period : followUpPeriods) {
            if (today.isBefore(period.getEndDate())
                    && today.isAfter(period.getStartDate())
                    && (!period.checkIsFollowed())) {
                needFollowUp.add(true);
            } else {
                needFollowUp.add(false);
            }
        }
        return needFollowUp.contains(true);
    }


    //MODIFIES: this
    //EFFECTS: choose from 1 to 4 in the console menu, set one period as followed
    public void markFollowed(int index) {
        getFollowUpPeriods().get(index - 1).setFollowed();
    }



    //EFFECTS: gets the current period where today is in
    public FollowUpPeriod getCurrentPeriod() {
        LocalDate today = LocalDate.now();
        for (FollowUpPeriod period : followUpPeriods) {
            if (today.isBefore(period.getEndDate())
                    && today.isAfter(period.getStartDate())) {
                return period;
            }
        }
        return null;
    }


    //EFFECTS: generates a patient's all follow-up periods
    public String printFollowUpPeriods() {
        String result = "";
        for (FollowUpPeriod period : followUpPeriods) {
            String str = period.printPeriod() + "\n";
            result += str;
        }
        return result;
    }

    public boolean isNeedFollowUpToday() {
        return needFollowUpToday;
    }

    public boolean isTrialCompleted() {
        return trialCompleted;
    }

    public ArrayList<FollowUpPeriod> getFollowUpPeriods() {
        return followUpPeriods;
    }

    public String getPatientId() {
        return patientId;
    }

    public int getAge() {
        return age;
    }

    public ArrayList<String> getFollowUpMarks() {
        return followUpMarks;
    }

    public ArrayList<Boolean> getIsFollowedList() {
        return isFollowedList;
    }

    public LocalDate getOperationDate() {
        return operationDate;
    }

    public char getGender() {
        return gender;
    }


}
