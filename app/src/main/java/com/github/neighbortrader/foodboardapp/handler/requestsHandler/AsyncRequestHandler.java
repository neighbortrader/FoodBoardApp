package com.github.neighbortrader.foodboardapp.handler.requestsHandler;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.JsonObject;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public abstract class AsyncRequestHandler <T extends AsyncRequestHandler> extends AsyncTask<Void, String, T> {

    public static final String TAG = AsyncRequestHandler.class.getSimpleName();
    public Exception exception;
    @Setter
    @Getter
    protected String url;
    @Setter
    @Getter
    protected RequestTyps requestTyps;
    private OnRequestEventListener callBack;
    private Context context;

    @Getter
    @Setter
    protected int lastHttpStatuscode;

    protected AsyncRequestHandler(Context context, OnRequestEventListener callback) {
        callBack = callback;
        this.context = context;
    }

    protected static RequestBody nameValueMapToRequestBody(Map<String, String> nameValueMap) {
        MediaType JSON = MediaType.get("application/json; charset=utf-8");
        JsonObject body = new JsonObject();

        nameValueMap.forEach(body::addProperty);

        return RequestBody.create(body.toString(), JSON);
    }

    @Override
    abstract protected T doInBackground(Void... params);

    @Override
    protected void onPostExecute(T result) {
        if (callBack != null) {
            if (exception == null) {
                callBack.onResponse(result);
            } else {
                callBack.onFailure(exception);
            }
        }
    }

    @Override
    protected void onProgressUpdate(String... values) {
        Log.d(TAG, "onProgressUpdate()");
        callBack.onProgress(values[0]);
    }

}
