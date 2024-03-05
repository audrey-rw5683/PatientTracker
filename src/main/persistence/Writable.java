package persistence;

import org.json.JSONObject;

//-/***************************************************************************************
// *    Title: <JSON serialization demo>
// *    Code version: <20210307>
// *    Availability: <https://github.com/stleary/JSON-java>
// ***************************************************************************************/
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
