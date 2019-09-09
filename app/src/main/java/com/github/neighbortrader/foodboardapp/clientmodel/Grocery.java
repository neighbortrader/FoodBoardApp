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
    private int groceryId;

    @Getter
    private String groceryName;

    public Grocery(int groceryId, String groceryName) {
        this.groceryId = groceryId;
        this.groceryName = groceryName;
    }

    public static Grocery buildGrocery(int groceryId, String groceryName) {
        Grocery newGrocerie = new Grocery(groceryId, groceryName);
        return newGrocerie;
    }

    public static Grocery findGrocery(int groceryId){
        for (Grocery grocery : Grocery.currentGroceries){
            if (grocery.getGroceryId() == groceryId)
                return grocery;
        }

        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Grocery grocery = (Grocery) o;
        return groceryId == grocery.groceryId &&
                Objects.equals(groceryName, grocery.groceryName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groceryId, groceryName);
    }

    public static Grocery createGroceryFromJSON(JSONObject jsonObject) {
        try {
           return Grocery.buildGrocery(jsonObject.getInt("id"), jsonObject.getString("name"));
        } catch (Exception e) {
            Log.e(TAG, "Unknown Error while trying to create Offer", e);
        }

        return null;
    }

    @Override
    public Map<String, String> toNameValueMap() {
        Map<String, String> nameValueMap = new Hashtable<>();

        nameValueMap.put("id", String.valueOf(groceryId));
        nameValueMap.put("name", groceryName);

        return nameValueMap;
    }
}
