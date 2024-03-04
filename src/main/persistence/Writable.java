package persistence;

import org.json.JSONObject;

// Represent something that can be turned into a JSON object
// Inspired by JsonReader from workroom app:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
