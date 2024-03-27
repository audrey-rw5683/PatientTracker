package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

// Represents a patient with id, gender, age, operation date
// along with their corresponding follow-up times,
// whether each follow-up is completed, and the overall trial completion status of each patient.
public class Patient implements Writable {
    private final String patientId;
    private final String gender;
    private final int age;
    private final LocalDate operationDate;
    private ArrayList<FollowUpPeriod> followUpPeriods;
    private ArrayList<Boolean> isFollowedList;
    private final ArrayList<String> followUpMarks = new ArrayList<>(Arrays.asList("FU7D", "FU1M", "FU6M", "FU1Y"));
    private final boolean needFollowUpToday;
    private boolean trialCompleted;

    //EFFECTS: constructs a patient with id, gender, age and operation date
    //generates a list of follow-up periods according to the operation date
    //The patient has not completed the trial by default
    public Patient(String patientId, String gender, int age, LocalDate operationDate) {
        this.patientId = patientId;
        this.gender = gender;
        this.age = age;
        this.operationDate = operationDate;



        followUpPeriods = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            this.followUpPeriods.add(new FollowUpPeriod(followUpMarks.get(i), operationDate));
        }

        isFollowedList = new ArrayList<>(4);
        for (int i = 0; i < 4; i++) {
            Boolean value = followUpPeriods.get(i).checkIsFollowed();
            this.isFollowedList.add(value);
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
        getFollowUpPeriods().get(index - 1).isFollowed();
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

    //EFFECTS: generates a patient's all information
    public String printPatient() {
        StringBuilder result = new StringBuilder();
        String id = "ID: " + getPatientId() + " ";
        String age = "Age: " + getAge() + " ";
        String gender = "\nGender: " + getGender() + " ";
        String opdate = "Operation Date: " + getOperationDate() + " ";
        String compelte = "\nTrial completed? " + isTrialCompleted() + " ";
        String periods = "\nFollow-ups: \n" + printFollowUpPeriods() + " ";
        result.append(id).append(age).append(gender).append(opdate).append(compelte).append(periods);
        return result.toString();
    }


    //EFFECTS: generates a patient's all follow-up periods
    public String printFollowUpPeriods() {
        StringBuilder result = new StringBuilder();
        for (FollowUpPeriod period : followUpPeriods) {
            String str =  period.printPeriod() + "\n";
            result.append(str);
        }
        return result.toString();
    }

    public boolean isNeedFollowUpToday() {
        return needFollowUpToday;
    }

    public boolean isTrialCompleted() {
        return trialCompleted;
    }

    public void setTrialCompleted(boolean b) {
        trialCompleted = b;
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

    public String getGender() {
        return gender;
    }

    public void setFollowUpPeriods(ArrayList<FollowUpPeriod> newPeriods) {
        this.followUpPeriods = newPeriods;
    }

    //-/***************************************************************************************
    // *    Title: <JSON serialization demo>
    // *    Code version: <20210307>
    // *    Availability: <https://github.com/stleary/JSON-java>
    // ***************************************************************************************/
    //EFFECTS: creates JSONObject for a patient
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("id", patientId);
        json.put("gender", gender);
        json.put("age", age);
        json.put("operation date", operationDate);

        JSONArray followUpPeriodsJson = new JSONArray();
        for (FollowUpPeriod period : followUpPeriods) {
            followUpPeriodsJson.put(period.toJson());
        }
        json.put("followUpPeriods", followUpPeriodsJson);
        json.put("trial completed", trialCompleted);
        return json;
    }
}
