package com.github.neighbortrader.foodboardapp.handler.gsonHandler;

import com.google.gson.Gson;

public class GsonHandler {
    private static Gson gson;

    public static Gson getGsonInstance() {
        if (gson == null) {
            return new Gson();
        } else {
            return gson;
        }
    }
}
