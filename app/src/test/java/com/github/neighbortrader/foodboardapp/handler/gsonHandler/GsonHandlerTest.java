package com.github.neighbortrader.foodboardapp.handler.gsonHandler;

import com.google.gson.Gson;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class GsonHandlerTest {
    @Test
    public void getGsonInstance_twoCalls_OneHashKey() {
        Gson gson1 = GsonHandler.getGsonInstance();
        Gson gson2 = GsonHandler.getGsonInstance();

        boolean hasSameHashKey = false;

        if (gson1.hashCode() == gson2.hashCode()) {
            hasSameHashKey = true;
        } else {
            hasSameHashKey = false;
        }

        assertTrue(hasSameHashKey);
    }

    @Test
    public void getGsonInstance_normalCall_getGsonInstance() {
        Gson gson = GsonHandler.getGsonInstance();

        boolean gotGsonInstance = false;

        if (gson != null) {
            gotGsonInstance = true;
        } else {
            gotGsonInstance = false;
        }

        assertTrue(gotGsonInstance);
    }
}