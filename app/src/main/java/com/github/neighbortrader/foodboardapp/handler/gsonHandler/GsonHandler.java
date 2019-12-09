package com.github.neighbortrader.foodboardapp.handler.gsonHandler;

import com.google.gson.Gson;

public class GsonHandler {
    private static volatile Gson gson;

    public static Gson getGsonInstance() {
        if (gson == null) {
            synchronized (GsonHandler.class) {
                if (gson == null) {
                    gson = new Gson();
                }
            }
        }
        return gson;
    }
}
