package ui;

import java.io.FileNotFoundException;

// Represents the entry point of Patient Tracker Application
//-/***************************************************************************************
// *    Title: <JSON serialization demo>
// *    Code version: <20210307>
// *    Availability: <https://github.com/stleary/JSON-java>
// ***************************************************************************************/
public class Main {
    public static void main(String[] args) {
        try {
            new PatientTracker();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
    }
}
