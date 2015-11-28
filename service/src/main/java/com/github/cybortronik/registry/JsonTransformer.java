package com.github.cybortronik.registry;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by stanislav on 11/6/15.
 */
public class JsonTransformer {

    private Gson gson;

    public JsonTransformer() {
        gson = new GsonBuilder().create();
    }

    public String toJson(Object obj) {
        return gson.toJson(obj);
    }

    public <T> T fromJson(String json, Class<T> tClass) {
        return gson.fromJson(json, tClass);
    }
}
