package com.github.neighbortrader.foodboardapp.clientmodel;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

import lombok.Getter;
import lombok.Setter;

public class Price {
    @Getter
    private Currency currency;

    @Getter
    @Setter
    private double value;

    NumberFormat currencyFormatter;

    Locale currentLocal;

    public Price(double value) {
        this.value = value;

        this.currentLocal = Locale.GERMANY;

        this.currency = Currency.getInstance(this.currentLocal);
        this.currencyFormatter = NumberFormat.getCurrencyInstance(this.currentLocal);
        this.currencyFormatter.setCurrency(this.currency);
    }

    public String getFormattedPrice() {
        return currencyFormatter.format(value);
    }
}
