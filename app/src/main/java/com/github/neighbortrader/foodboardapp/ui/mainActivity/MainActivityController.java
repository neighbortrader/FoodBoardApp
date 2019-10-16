package com.github.neighbortrader.foodboardapp.ui.mainActivity;

public class MainActivityController {
    MainActivityModel model;
    MainActivity mainActivity;

    public MainActivityController(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        model = new MainActivityModel(this);
    }
}
