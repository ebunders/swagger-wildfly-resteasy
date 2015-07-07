package de.xakte.swr.boundary;

import javax.json.Json;
import javax.json.JsonObject;

/**
 * Created by Ernst Bunders on 30-6-15.
 */
public class Util {
    public static JsonObject json(String name, String value) {
        return Json.createObjectBuilder().add(name, value).build();
    }
}
