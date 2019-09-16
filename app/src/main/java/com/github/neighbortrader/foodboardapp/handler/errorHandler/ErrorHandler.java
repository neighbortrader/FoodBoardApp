package com.github.neighbortrader.foodboardapp.handler.errorHandler;

import android.widget.Toast;

import com.github.neighbortrader.foodboardapp.handler.contextHandler.ContextHandler;

public class ErrorHandler {

    private Exception e;

    private ErrorHandler(Exception e) {
        this.e = e;
    }

    public static ErrorHandler buildErrorHandler(Exception e){
        return new ErrorHandler(e);
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
