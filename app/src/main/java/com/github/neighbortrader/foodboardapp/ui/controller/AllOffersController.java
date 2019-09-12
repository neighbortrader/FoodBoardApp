package com.github.neighbortrader.foodboardapp.ui.controller;

import android.content.Context;

import com.github.neighbortrader.foodboardapp.ui.model.AllOffersModel;
import com.github.neighbortrader.foodboardapp.etc.MyApplication;
import com.github.neighbortrader.foodboardapp.ui.view.AllOffersActivity;

public class AllOffersController {

    public static String TAG = AllOffersController.class.getSimpleName();

    AllOffersModel allOffersModel;
    AllOffersActivity allOffersActivity;

    Context context;

    public AllOffersController(AllOffersActivity allOffersActivity) {
        this.allOffersModel = new AllOffersModel(this);
        this.allOffersActivity = allOffersActivity;
        context = MyApplication.getAppContext();

        allOffersModel.iniAppDataAndUser();
    }

    public void destroy() {
        allOffersModel.destroy();
    }

    public AllOffersModel getAllOffersModel(){
        return  allOffersModel;
    }


}
