package com.github.neighbortrader.foodboardapp.requests;

import android.content.Context;
import android.util.Log;

import com.github.neighbortrader.foodboardapp.clientmodel.Price;
import com.github.neighbortrader.foodboardapp.clientmodel.Offer;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class GetAllOffersRequest extends AsyncRequest<Offer> {
    public final static String TAG = GetAllOffersRequest.class.getSimpleName();

    private final String url = Constant.BASE_URL + Constant.ENDPOINT_GET_ALL_OFFERS;

    GetAllOffersRequest(Context context, OnEventListener callback) {
        super(context, callback, RequestTyps.GET_ALL_OFFERS);
    }

    @Override
    protected void construct() {
        // in this case empty
    }

    @Override
    protected List<Offer> doInBackground(Void... params) {
        {
            Log.d(TAG, "doInBackground()");

            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Log.d(TAG, "Request: " + request);

            try {
                Response response = client.newCall(request).execute();

                Log.d(TAG, "Response: " + response);

                // TODO READ OBJECTS FROM REQUEST

                // This is dummy data
                ArrayList<Offer> resultList = new ArrayList<>();

                resultList.add(new Offer(new Price(1d), "a", "Test"));

                return resultList;

            } catch (Exception e) {
                exception = e;
            }

            return null;
        }
    }
}
