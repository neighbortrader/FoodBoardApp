package com.github.neighbortrader.foodboardapp.Model.viewmodel;

import androidx.lifecycle.ViewModel;

import com.github.neighbortrader.foodboardapp.Model.clientmodel.Offer;
import com.github.neighbortrader.foodboardapp.view.AllOffersActivity;

import java.util.ArrayList;

public class AllOffersModel extends ViewModel {

    AllOffersActivity allOffersActivity;
    private ArrayList<Offer> currentOffers;

    public AllOffersModel(AllOffersActivity allOffersActivity) {
        this.allOffersActivity = allOffersActivity;
        currentOffers = new ArrayList<>();
    }

    public void addOffer(Offer offer) {
        currentOffers.add(offer);
        allOffersActivity.updateUi();
    }

    public void addOffers(ArrayList<Offer> newOffers) {
        for (Offer offer : newOffers) {
            this.currentOffers.add(offer);
        }

        allOffersActivity.updateUi();
    }


}
