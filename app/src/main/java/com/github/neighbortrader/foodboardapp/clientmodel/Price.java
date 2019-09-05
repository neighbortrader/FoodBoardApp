package com.github.neighbortrader.foodboardapp.clientmodel;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

public class Price implements ToNameValueMap{
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

    @Override
    public String toString() {
        return "Price{" +
                "currency=" + currency +
                ", value=" + value +
                ", currencyFormatter=" + currencyFormatter +
                ", currentLocal=" + currentLocal +
                '}';
    }

    public String getFormattedPrice() {
        return currencyFormatter.format(value);
    }

    @Override
    public Map<String, String> toNameValueMap() {
        Map<String, String> nameValueMap = new Hashtable<>();

        nameValueMap.put("value", Double.toString(value));

        return nameValueMap;
    }
}
