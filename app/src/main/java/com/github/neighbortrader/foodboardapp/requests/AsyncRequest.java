package com.github.neighbortrader.foodboardapp.requests;

import android.content.Context;
import android.os.AsyncTask;

import java.text.Normalizer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;

public abstract class AsyncRequest<T> extends AsyncTask<Void, Void, List<T>> {

    private OnEventListener<T> callBack;
    private Context context;
    public Exception exception;

    // Do subclass level processing in this method
    protected abstract void construct();

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

    protected FormBody nameValueMapToFormbody(Map<String, String> nameValueMap){
        FormBody.Builder formBodyBuilder = new FormBody.Builder();

        nameValueMap.forEach((k, v) ->{
            formBodyBuilder.add(k, v);
        });

        return  formBodyBuilder.build();
    }

    public RequestTyps getRequestTyps() {
        return requestTyps;
    }

    public void setRequestTyps(RequestTyps requestTyps) {
        this.requestTyps = requestTyps;
    }
}
