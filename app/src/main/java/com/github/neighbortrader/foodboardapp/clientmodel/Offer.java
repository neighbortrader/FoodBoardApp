package com.github.neighbortrader.foodboardapp.clientmodel;

import com.github.neighbortrader.foodboardapp.clientmodel.Price;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

public class Offer {
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
                '}';
    }

    public Offer(Price price, String groceryCategory, String description) {
        this.price = price;
        this.groceryCategory = groceryCategory;
        this.description = description;
    }
}
