package persistence;

import org.json.JSONObject;

//-/***************************************************************************************
// *    Title: <JSON serialization demo>
// *    Code version: <20210307>
// *    Availability: <https://github.com/stleary/JSON-java>
// ***************************************************************************************/

// Represents an interface that converts classes to JSON objects
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
