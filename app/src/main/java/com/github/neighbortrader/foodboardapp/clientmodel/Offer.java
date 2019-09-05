package com.github.neighbortrader.foodboardapp.clientmodel;

import com.github.neighbortrader.foodboardapp.clientmodel.Price;

import java.time.LocalDateTime;
import java.util.Hashtable;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

public class Offer implements ToNameValueMap {
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

    public Offer(Price price, String groceryCategory, String description) {
        this.price = price;
        this.groceryCategory = groceryCategory;
        this.description = description;
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
}
