package com.github.neighbortrader.foodboardapp.clienttransfermodel;

import com.github.neighbortrader.foodboardapp.clientmodel.Price;

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


}
