package com.github.neighbortrader.foodboardapp.Model.clientmodel;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Hashtable;
import java.util.Map;
import java.util.Random;

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

    private Offer(User user, Price price, Grocery groceryCategory, String description, LocalDateTime purchaseDate, LocalDateTime expireDatea) {
        this.user = user;
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

            return new Offer(User.getCurrentUserInstance(), price, Grocery.findGrocery(grocerieId), description, purchaseDate, expireDate);
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

    public static Offer createRandomOffer() {
        User user = User.getCurrentUserInstance();

        Random r = new Random();

        double randomValue = 0 + (20) * r.nextDouble();
        Price price = new Price(randomValue);

        if (!Grocery.isCurrentSessionHasGroceries()){
            return null;
        }

        int randomInt = r.nextInt(Grocery.getCurrentGroceries().size()) + 1;
        Grocery grocery = Grocery.findGrocery(randomInt);

        String description = "Testbeschreibung";
        LocalDateTime purchaseDate = LocalDateTime.now();
        LocalDateTime exexpireDate = purchaseDate.plusDays(r.nextInt(20))
                .plusHours(r.nextInt(24))
                .plusMinutes(r.nextInt(60))
                .plusSeconds(r.nextInt(60));

        return new Offer(user, price, grocery, description, purchaseDate, exexpireDate);
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

        nameValueMap.put("description", description);
        nameValueMap.put("price", Double.toString(price.getValue()));
        nameValueMap.put("purchaseDate", purchaseDate.toString());
        nameValueMap.put("expireDate", expireDate.toString());
        nameValueMap.put("CreationDate", creationDate.toString());
        nameValueMap.put("grocerieId", Integer.toString(groceryCategory.getGroceryId()));

        return nameValueMap;
    }
}
