package com.train.restAsssured.utility;

import io.restassured.path.json.JsonPath;

public class Utils {

    /**
     * Converts response string to JSON
     * @param response
     * @return JSON
     */
    public static JsonPath rawToJSON(String response) {
        return new JsonPath(response);
    }
}
