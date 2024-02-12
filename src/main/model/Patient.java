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
    private boolean isTrialCompleted;


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

        isTrialCompleted = false;
    }

    public void markIsFollowed(String id, String mark) {
        for (String followUpMark : followUpMarks) {
            int count = 0;
            if (mark == followUpMark) {
                isFollowedList.set(count, true);
            }
            count++;
        }
    }

    public String getPatientId() {
        return patientId;
    }

    public void setTrialCompleted(boolean trialCompleted) {
        isTrialCompleted = trialCompleted;
    }
}
