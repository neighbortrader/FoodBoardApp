package com.github.neighbortrader.foodboardapp.clientmodel;

import android.content.Context;

import com.github.neighbortrader.foodboardapp.R;
import com.github.neighbortrader.foodboardapp.handler.contextHandler.ContextHandler;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

public class Price implements ToNameValueMap {
    NumberFormat currencyFormatter;
    Locale currentLocal;
    @Getter
    private Currency currency;
    @Getter
    @Setter
    private double value;

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

    public static String reformatPrice(String formattedPrice) {
        Context context = ContextHandler.getAppContext();
        formattedPrice = formattedPrice.replaceAll(context.getString(R.string.general_currency), "");
        formattedPrice = formattedPrice.replaceAll("\\s", "");
        return formattedPrice.replaceAll(",", ".");
    }

    @Override
    public Map<String, String> toNameValueMap() {
        Map<String, String> nameValueMap = new Hashtable<>();

        nameValueMap.put("value", Double.toString(value));

        return nameValueMap;
    }
}
