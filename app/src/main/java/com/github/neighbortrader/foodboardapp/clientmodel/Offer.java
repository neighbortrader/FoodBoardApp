package com.github.neighbortrader.foodboardapp.clientmodel;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.github.neighbortrader.foodboardapp.clientmodel.Price;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
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
    private String groceryCategory;

    @Getter
    @Setter
    private String description;

    @Getter
    @Setter
    private LocalDateTime purchaseDate;

    @Getter
    @Setter
    private LocalDateTime expireDate;

    @Override
    public String toString() {
        return "Offer{" +
                "price=" + price +
                ", groceryCategory='" + groceryCategory + '\'' +
                ", description='" + description + '\'' +
                ", purchaseDate=" + purchaseDate +
                ", expireDate=" + expireDate +
                '}';
    }

    public Offer(Price price, String groceryCategory, String description, LocalDateTime purchaseDate, LocalDateTime expireDate) {
        this.price = price;
        this.groceryCategory = groceryCategory;
        this.description = description;
        this.purchaseDate = purchaseDate;
        this.expireDate = expireDate;
    }


    @Override
    public Map<String, String> toNameValueMap() {
        Map<String, String> nameValueMap = new Hashtable<>();

        nameValueMap.putAll(price.toNameValueMap());
        nameValueMap.put("groceryCategory", groceryCategory);
        nameValueMap.put("description", description);

        // TODO: check those strings
        nameValueMap.put("purchaseDate", purchaseDate.toString());
        nameValueMap.put("expireDate", expireDate.toString());

        return nameValueMap;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static Offer createOfferFromJSON(JSONObject jsonObject) {
        try {
            Price price = new Price(jsonObject.getDouble("price"));
            String groceryCategory = jsonObject.getString("groceries");
            String description = jsonObject.getString("description");
            LocalDateTime purchaseDate = LocalDateTime.parse(jsonObject.getString("purchaseDate"));
            LocalDateTime expireDate = LocalDateTime.parse(jsonObject.getString("expireDate"));

            return new Offer(price, groceryCategory, description, purchaseDate, expireDate);
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
}
