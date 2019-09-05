package com.github.neighbortrader.foodboardapp.requests;

import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

import okhttp3.FormBody;

public abstract class AsyncRequest<T> extends AsyncTask<Void, Void, List<T>> {

    private OnEventListener<T> callBack;
    private Context context;
    public Exception exception;

    private RequestTyps requestTyps;

    // Do subclass level processing in this method
    protected abstract void construct();

    public AsyncRequest(Context context, OnEventListener callback, RequestTyps requestTyps) {
        callBack = callback;
        this.context = context;
        this.requestTyps = requestTyps;
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

    public RequestTyps getRequestTyps() {
        return requestTyps;
    }

    public void setRequestTyps(RequestTyps requestTyps) {
        this.requestTyps = requestTyps;
    }
}
