package com.github.neighbortrader.foodboardapp.clientmodel;

import java.util.Hashtable;
import java.util.Map;
import java.util.Objects;

import lombok.Getter;

public class Grocery implements ToNameValueMap {
    public static String TAG = Grocery.class.getSimpleName();

    @Getter
    private int groceryId;
    @Getter
    private String groceryName;

    public Grocery(int groceryId, String groceryName) {
        this.groceryId = groceryId;
        this.groceryName = groceryName;
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

    @Override
    public Map<String, String> toNameValueMap() {
        Map<String, String> nameValueMap = new Hashtable<>();

        nameValueMap.put("id", String.valueOf(groceryId));
        nameValueMap.put("name", groceryName);

        return nameValueMap;
    }

    @Override
    public String toString() {
        return groceryName;
    }
}
