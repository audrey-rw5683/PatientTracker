# Clinical Trial Patient Tracking System

## Usage
This application helps clinical researchers to track patients enrolled in a clinical trial, with features that include 
recording basic patients' information, the planned follow-up time, follow-up completion, 
and withdraw or complete the trial.
## User

- *Clinical Researchers*
- *Doctors*
- *Clinical Trial Companies*

## Personal Motivation
Currently, I work as a data manager for a clinical trial company, and our database is only used to store 
information relevant to the trial, but we don't have a simple tool for researchers to remind them when to 
follow up with patients. 
By using this app, patient data is captured in a timely manner, 
the data obtained is of higher quality, and the results of clinical trials are more credible.

## User Stories

- As a user, I want to be able to add multiple patients to the clinical trial and specify the patient's id, age and enrollment date (complete)
- As a user, I want to be able to see the series of planned follow-up times of the patient selected (complete)
- As a user, I want to be able to mark a patient as followed-up or completed in the clinical trial (complete)
- As a user, I want to be able to remove a patient out of the clinical trial (complete)
- As a user, I want to be able to view the list of patients in the clinical trial (complete)
- As a user, I want to be able to see the number of all patients in my clinical trial, the number of patients that have been followed up and the number of patients that have completed this trial (complete)
- As a user, I want to be able to save my clinical trial progress  (complete)
- As a user, I want to be able to be able to load my clinical trial progress (complete)
- As a user, I want to be able to add multiple patients to the clinical trial in the GUI.(complete) 
- As a user, I want to be able to see patients who need follow up today in the GUI.(complete)
- As a user, I want to be able to load and save current clinical trial progress in the GUI.(complete)

# Instructions for Grader
- You can enter the GUI by running "MainUI.java" in ui package.
- You can generate the first required action related to the user story "adding multiple patients to a clinical trial" by filling patient's id, gender, age and operation date and then clicking button labelled "Add patient".
- You can generate the second required action related to the user story "adding multiple patients to a clinical trial" by clicking the button labelled "Follow-up List" to filter out patients need follow-up today after loading or adding patients.
- You can show all enrolled patients by clicking the button labelled "Show all patients" after loading or adding patients.
- You can locate my visual component by watching the splash screen when you start the GUI.
- You can save the state of my application by clicking the button labelled "Save your work".
- You can reload the state of my application by clicking the button labelled "Load your previous work".
- You can quit the GUI by closing the frame.

# Phase 4: Task 2
- Wed Apr 03 11:07:31 PDT 2024
- Loaded work
- Wed Apr 03 11:07:31 PDT 2024
- Added Patient: 001
- Wed Apr 03 11:07:56 PDT 2024
- Added Patient: 002
- Wed Apr 03 11:07:58 PDT 2024
- Showed all enrolled patients
- Wed Apr 03 11:08:00 PDT 2024
- Showed follow-up list
- Wed Apr 03 11:08:05 PDT 2024
- Saved work


# Phase 4: Task 3
If I had the time, what I would most like to improve is the data structures. 
Because during phase 1, we hadn't learned about sets and hashmaps yet. But when I learned about them, I realized that they would be more suitable for my project. 
I would use Patient as the key, and the list of follow-up periods is the value of each key.
With hashmap, I can retrieve data more quickly, and if the patients in the project are stored as a set, I wouldn't have to worry about duplicate elements.

## Citations
- JSON serialization demo, Code version: <20210307>, Availability: <https://github.com/stleary/JSON-java>
- splash.jpg Availability: <https://www.shutterstock.com/search/clinical-information-system>
- AlarmSystem <https://github.students.ubc.ca/CPSC210/AlarmSystem>