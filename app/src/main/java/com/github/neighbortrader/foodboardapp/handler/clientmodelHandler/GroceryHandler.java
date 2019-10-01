package com.github.neighbortrader.foodboardapp.handler.clientmodelHandler;

import android.content.SharedPreferences;
import android.util.Log;

import com.github.neighbortrader.foodboardapp.clientmodel.Grocery;
import com.github.neighbortrader.foodboardapp.handler.contextHandler.ContextHandler;
import com.github.neighbortrader.foodboardapp.handler.gsonHandler.GsonHandler;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class GroceryHandler {
    public static final String TAG = GroceryHandler.class.getSimpleName();
    private static final String SHARED_PREFERENCES_FILE_GROCERY_INFO = "groceryData";

    private static ArrayList<Grocery> currentGroceries = new ArrayList<>();

    public static void updateCurrentGroceries(Grocery grocery) {
        if (!currentGroceries.contains(grocery))
            GroceryHandler.currentGroceries.add(grocery);
    }

    public static int amountOfCurrentGroceries() {
        return currentGroceries.size();
    }

    public static ArrayList<Grocery> getCurrentGroceries() {
        return currentGroceries;
    }

    public static void updateCurrentGroceries(ArrayList<Grocery> currentGroceries) {
        for (Grocery grocery : currentGroceries) {
            updateCurrentGroceries(grocery);
        }
    }

    public static Grocery buildGrocery(int groceryId, String groceryName) {
        return new Grocery(groceryId, groceryName);
    }

    public static Grocery findGrocery(int groceryId) {
        for (Grocery grocery : GroceryHandler.currentGroceries) {
            if (grocery.getGroceryId() == groceryId)
                return grocery;
        }

        return new Grocery(-1, "Unknown grocery");
    }

    public static Grocery findGrocery(String groceryName) {
        for (Grocery grocery : GroceryHandler.currentGroceries) {
            if (grocery.getGroceryName().equals(groceryName))
                return grocery;
        }

        return new Grocery(-1, "Unknown grocery");
    }

    public static Grocery createGroceryFromJSON(JSONObject jsonObject) {
        try {
            return GroceryHandler.buildGrocery(jsonObject.getInt("id"), jsonObject.getString("name"));
        } catch (Exception e) {
            Log.e(TAG, "Unknown Error while trying to create Offer", e);
        }

        return null;
    }

    public static void saveCurrentGroceriesToSharedPreferences() {
        Log.d(TAG, "saveCurrentGroceriesToSharedPreferences()");

        if (GroceryHandler.getCurrentGroceries() != null) {
            SharedPreferences sharedPreferences = ContextHandler.getAppContext().getSharedPreferences(SHARED_PREFERENCES_FILE_GROCERY_INFO, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            Gson gson = new Gson();
            String groceryJson = gson.toJson(GroceryHandler.getCurrentGroceries());
            editor.putString(SHARED_PREFERENCES_FILE_GROCERY_INFO, groceryJson);
            editor.commit();
        }
    }

    public static void loadGroceriesFromSharedPreferences() {
        Log.d(TAG, "loadGroceriesFromSharedPreferences()");

        SharedPreferences sharedPreferences = ContextHandler.getAppContext().getSharedPreferences(SHARED_PREFERENCES_FILE_GROCERY_INFO, MODE_PRIVATE);
        Gson gson = GsonHandler.getGsonInstance();
        String groceryJson = sharedPreferences.getString(SHARED_PREFERENCES_FILE_GROCERY_INFO, "");

        if (groceryJson != null) {
            Type type = new TypeToken<List<Grocery>>() {
            }.getType();
            List<Grocery> groceryList = gson.fromJson(groceryJson, type);

            if (groceryList != null) {
                for (Grocery grocery : groceryList) {
                    GroceryHandler.updateCurrentGroceries(grocery);
                }
            }
        }
    }

}
