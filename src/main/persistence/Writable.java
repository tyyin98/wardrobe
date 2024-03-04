package persistence;

import org.json.JSONObject;

// Represent something that can be turned into a string-like object
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
