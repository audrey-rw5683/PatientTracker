package ui;

import java.io.FileNotFoundException;

// Represents the entry point of Patient Tracker console-based application
public class Main {

    //-/***************************************************************************************
    // *    Title: <JSON serialization demo>
    // *    Code version: <20210307>
    // *    Availability: <https://github.com/stleary/JSON-java>
    // ***************************************************************************************/

    // EFFECTS: starts Patient Tracker console-based application
    public static void main(String[] args) {
        try {
            new PatientTracker();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
    }
}
