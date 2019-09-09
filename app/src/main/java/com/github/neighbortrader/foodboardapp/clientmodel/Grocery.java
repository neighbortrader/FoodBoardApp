package com.github.neighbortrader.foodboardapp.clientmodel;

import android.util.Log;

import org.json.JSONObject;

import java.util.ArrayList;

import lombok.Getter;

public class Grocery {
    public static String TAG = Grocery.class.getSimpleName();

    private static ArrayList<Grocery> currentGroceries = new ArrayList<>();

    public static ArrayList<Grocery> getCurrentGroceries() {
        return currentGroceries;
    }

    public static void updateCurrentGroceries(Grocery grocery) {
        Grocery.currentGroceries.add(grocery);
    }

    public static void updateCurrentGroceries(ArrayList<Grocery> currentGroceries) {
        for (Grocery grocery : currentGroceries) {
            updateCurrentGroceries(grocery);
        }
    }

    @Getter
    private String groceryName;

    protected Grocery(String groceryName) {
        this.groceryName = groceryName;
    }

    public static Grocery buildGrocery(String groceryName) {
        return new Grocery(groceryName);
    }

    public static Grocery createOfferFromJSON(JSONObject jsonObject) {
        try {
            //TODO: wait for Endpoint implementation
            //Grocery groceryName = Grocery.buildGrocery(jsonObject.getString(""))


            //return
        } catch (Exception e) {
            Log.e(TAG, "Unknown Error while trying to create Offer", e);
        }

        return null;
    }
}
