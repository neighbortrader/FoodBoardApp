package com.github.neighbortrader.foodboardapp.clientmodel;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Hashtable;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

public class Offer implements ToNameValueMap {
    public static String TAG = Offer.class.getSimpleName();

    @Getter
    @Setter
    private User user;

    @Getter
    private Price price;

    @Getter
    @Setter
    private Grocery groceryCategory;

    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    private LocalDateTime purchaseDate;

    @Getter
    @Setter
    private LocalDateTime expireDate;

    @Getter
    @Setter
    private LocalDateTime creationDate;

    public Offer(Price price, Grocery groceryCategory, String description, LocalDateTime purchaseDate, LocalDateTime expireDate, LocalDateTime creationDate) {
        this.price = price;

        if (groceryCategory == null)
            throw new IllegalStateException("Unknown Grocery");

        this.groceryCategory = groceryCategory;
        this.description = description;
        this.purchaseDate = purchaseDate;
        this.expireDate = expireDate;
        this.creationDate = null;       // creationDate gets set during request
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static Offer createOfferFromJSON(JSONObject jsonObject) {
        try {
            Price price = new Price(jsonObject.getDouble("price"));
            int grocerieId = jsonObject.getInt("grocerieId");
            String description = jsonObject.getString("description");

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

            LocalDateTime purchaseDate = LocalDateTime.parse(jsonObject.getString("purchaseDate"), dateTimeFormatter);
            LocalDateTime expireDate = LocalDateTime.parse(jsonObject.getString("expireDate"), dateTimeFormatter);

            return new Offer(price, Grocery.findGrocery(grocerieId), description, purchaseDate, expireDate, LocalDateTime.now());
        } catch (JSONException e) {
            Log.e(TAG, "JSONException while trying to create Offer", e);
        } catch (RuntimeException e) {
            Log.e(TAG, "RuntimeException while trying to create Offer (Probably while calling LocalDateTime.parse)", e);
        } catch
        (Exception e) {
            Log.e(TAG, "Unknown Error while trying to create Offer", e);
        }

        return null;
    }

    @Override
    public String toString() {
        return "Offer{" +
                "user=" + user +
                ", price=" + price +
                ", groceryCategory=" + groceryCategory +
                ", description='" + description + '\'' +
                ", purchaseDate=" + purchaseDate +
                ", expireDate=" + expireDate +
                ", creationDate=" + creationDate +
                '}';
    }

    @Override
    public Map<String, String> toNameValueMap() {
        Map<String, String> nameValueMap = new Hashtable<>();

        nameValueMap.putAll(price.toNameValueMap());
        nameValueMap.put("groceryCategory", groceryCategory.toString());
        nameValueMap.put("description", description);

        nameValueMap.put("purchaseDate", purchaseDate.toString());
        nameValueMap.put("expireDate", expireDate.toString());
        nameValueMap.put("CreationDate", creationDate.toString());

        return nameValueMap;
    }
}
