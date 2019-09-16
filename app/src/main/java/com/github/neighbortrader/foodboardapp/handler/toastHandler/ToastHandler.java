package com.github.neighbortrader.foodboardapp.handler.toastHandler;

import android.widget.Toast;

import com.github.neighbortrader.foodboardapp.handler.contextHandler.ContextHandler;

public class ToastHandler {

    private Exception e;

    private ToastHandler(Exception e) {
        this.e = e;
    }

    public static ToastHandler buildErrorToastHandler(Exception e){
        return new ToastHandler(e);
    }

    public static ToastHandler buildToastHandler(){
        return new ToastHandler(null);
    }

    public void makeToast(String message){
        Toast toast=Toast.makeText(ContextHandler.getAppContext(),message,Toast.LENGTH_SHORT);
        toast.show();
    }

    public void errorToast(){
        Toast errorToast=Toast.makeText(ContextHandler.getAppContext(),e.getMessage(),Toast.LENGTH_SHORT);
        errorToast.show();
    }

    public void errorToastWithCostumeMassage(String message){
        Toast errorToast=Toast.makeText(ContextHandler.getAppContext(),message,Toast.LENGTH_SHORT);
        errorToast.show();
    }
}
