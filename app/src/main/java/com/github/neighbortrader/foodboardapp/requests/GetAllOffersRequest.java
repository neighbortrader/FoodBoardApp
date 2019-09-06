package com.github.neighbortrader.foodboardapp.requests;

import android.content.Context;
import android.util.Log;

import com.github.neighbortrader.foodboardapp.clientmodel.Offer;
import com.github.neighbortrader.foodboardapp.clientmodel.Price;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class GetAllOffersRequest extends AsyncRequest<Offer> {
    public final static String TAG = GetAllOffersRequest.class.getSimpleName();

    private final String url = Constant.BASE_URL + Constant.ENDPOINT_GET_ALL_OFFERS;

    public GetAllOffersRequest(Context context, OnEventListener callback) {
        super(context, callback);
    }

    @Override
    protected List<Offer> doInBackground(Void... params) {
        Log.d(TAG, "doInBackground()");

        OkHttpClient client = UnsafeOkHttpClient.getUnsafeOkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Log.d(TAG, "Request: " + request);

        try {
            Response response = client.newCall(request).execute();
            ResponseBody responseBody = response.body();

            Log.d(TAG, "Response: " + response);
            Log.d(TAG, "Responsebody: " + response.body().string());

            responseBody.string();

            ArrayList<Offer> resultList = new ArrayList<>();



            return null;

        } catch (Exception e) {
            exception = e;
            Log.e(TAG, "Exception while waiting for Result", e);
        }

        return null;
    }
}
