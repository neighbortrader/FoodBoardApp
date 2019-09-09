package com.github.neighbortrader.foodboardapp.clientmodel;

import android.util.Log;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.Objects;

import lombok.Getter;

public class Grocery implements ToNameValueMap{
    public static String TAG = Grocery.class.getSimpleName();

    private static ArrayList<Grocery> currentGroceries = new ArrayList<>();

    public static ArrayList<Grocery> getCurrentGroceries() {
        return currentGroceries;
    }

    public static void updateCurrentGroceries(Grocery grocery) {
        if (!currentGroceries.contains(grocery))
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
        Grocery newGrocerie = new Grocery(groceryName);

        Grocery.updateCurrentGroceries(newGrocerie);

        return newGrocerie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Grocery grocery = (Grocery) o;
        return Objects.equals(groceryName, grocery.groceryName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groceryName);
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

    @Override
    public Map<String, String> toNameValueMap() {
        Map<String, String> nameValueMap = new Hashtable<>();

        nameValueMap.put("grocerie", groceryName);

        return nameValueMap;
    }
}
