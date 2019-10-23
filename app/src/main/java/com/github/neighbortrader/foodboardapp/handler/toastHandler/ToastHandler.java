package com.github.neighbortrader.foodboardapp.handler.toastHandler;

import android.content.Context;
import android.widget.Toast;

import com.github.neighbortrader.foodboardapp.R;
import com.github.neighbortrader.foodboardapp.handler.contextHandler.ContextHandler;

public class ToastHandler {

    private Exception e;
    Context context = ContextHandler.getAppContext();

    private ToastHandler(Exception e) {
        this.e = e;
    }

    public static ToastHandler buildErrorToastHandler(Exception e) {
        return new ToastHandler(e);
    }

    public static ToastHandler buildToastHandler() {
        return new ToastHandler(null);
    }

    public void makeToast(String message) {
        Toast toast = Toast.makeText(ContextHandler.getAppContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void errorToast() {
        Toast errorToast = Toast.makeText(ContextHandler.getAppContext(), e.getMessage(), Toast.LENGTH_SHORT);
        errorToast.show();
    }

    public void errorToastWithCostumeMassage(String message) {
        Toast errorToast = Toast.makeText(ContextHandler.getAppContext(), message, Toast.LENGTH_SHORT);
        errorToast.show();
    }

    public void notImplementedPlaceHolder() {
        Toast toast = Toast.makeText(ContextHandler.getAppContext(), context.getResources().getString(R.string.general_not_implemented), Toast.LENGTH_SHORT);
        toast.show();
    }

    public void notAvailabel() {
        Toast toast = Toast.makeText(ContextHandler.getAppContext(), context.getResources().getString(R.string.general_not_available), Toast.LENGTH_SHORT);
        toast.show();
    }
}
