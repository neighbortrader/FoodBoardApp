package com.github.neighbortrader.foodboardapp.clientmodel;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.github.neighbortrader.foodboardapp.handler.clientmodelHandler.UserHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Hashtable;
import java.util.Map;
import java.util.Random;

import lombok.Getter;
import lombok.Setter;

public class Offer implements ToNameValueMap, Parcelable {
    public static final Creator<Offer> CREATOR = new Creator<Offer>() {
        @Override
        public Offer createFromParcel(Parcel in) {
            return new Offer(in);
        }

        @Override
        public Offer[] newArray(int size) {
            return new Offer[size];
        }
    };
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

    private Offer(User user, Price price, Grocery groceryCategory, String description, LocalDateTime purchaseDate, LocalDateTime expireDate, LocalDateTime creationDate) {
        this.user = user;
        this.price = price;

        if (groceryCategory == null)
            throw new IllegalStateException("Unknown Grocery");

        this.groceryCategory = groceryCategory;
        this.description = description;
        this.purchaseDate = purchaseDate;
        this.expireDate = expireDate;
        this.creationDate = creationDate;
    }

    protected Offer(Parcel in) {
        description = in.readString();
    }

    public static Offer createOffer(Price price, Grocery groceryCategory, String description, LocalDateTime purchaseDate, LocalDateTime expireDate) {
        return new Offer(UserHandler.getCurrentUserInstance(), price, groceryCategory, description, purchaseDate, expireDate, null);
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
            LocalDateTime creationDate = LocalDateTime.parse(jsonObject.getString("creationDate"), dateTimeFormatter);

            return new Offer(UserHandler.getCurrentUserInstance(), price, Grocery.findGrocery(grocerieId), description, purchaseDate, expireDate, creationDate);
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
        User user = UserHandler.getCurrentUserInstance();

        Random r = new Random();

        double randomValue = 0 + (20) * r.nextDouble();
        Price price = new Price(randomValue);

        if (Grocery.amountOfCurrentGroceries() == 0) {
            return null;
        }

        int randomInt = r.nextInt(Grocery.getCurrentGroceries().size()) + 1;
        Grocery grocery = Grocery.findGrocery(randomInt);

        String description = "Testbeschreibung";
        LocalDateTime purchaseDate = LocalDateTime.now();
        LocalDateTime expireDate = purchaseDate.plusDays(r.nextInt(20))
                .plusHours(r.nextInt(24))
                .plusMinutes(r.nextInt(60))
                .plusSeconds(r.nextInt(60));

        return new Offer(user, price, grocery, description, purchaseDate, expireDate, null);
    }

    @Override
    public String toString() {
        return "Offer{" +
                "user=" + user +
                ", priceEditText=" + price +
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(description);
    }
}
