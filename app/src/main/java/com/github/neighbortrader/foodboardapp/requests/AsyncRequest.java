package com.github.neighbortrader.foodboardapp.requests;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

public abstract class AsyncRequest<T> extends AsyncTask<Void, Void, List<T>> {

    private OnEventListener<T> callBack;
    private Context context;
    public Exception exception;

    public AsyncRequest(Context context, OnEventListener callback) {
        callBack = callback;
        this.context = context;
    }

    @Override
    abstract protected List<T> doInBackground(Void... params);

    @Override
    protected void onPostExecute(List<T> result) {
        if (callBack != null) {
            if (exception == null) {
                callBack.onResponse(result);
            } else {
                callBack.onFailure(exception);
            }
        }
    }
}
