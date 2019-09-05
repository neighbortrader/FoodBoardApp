package com.github.neighbortrader.foodboardapp.requests;

import android.content.Context;
import android.util.Log;

import com.github.neighbortrader.foodboardapp.Constant;
import com.github.neighbortrader.foodboardapp.clientmodel.Price;
import com.github.neighbortrader.foodboardapp.clientmodel.Offer;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AsyncAllOffersRequest extends AsyncRequest<Offer> {
    public final static String TAG = AsyncAllOffersRequest.class.getSimpleName();

    public AsyncAllOffersRequest(Context context, OnEventListener callback) {
        super(context, callback);
    }

    @Override
    protected List<Offer> doInBackground(Void... params) {
        {
            Log.d(TAG, "doInBackground()");
            //HttpUrl.Builder urlBuilder = HttpUrl.parse(Constant.BASE_URL + Constant.ENDPOINT_GET_ALL_OFFERS_REQUEST).newBuilder();
            //urlBuilder.addQueryParameter("v", "1.0");
            final String url = Constant.BASE_URL + Constant.ENDPOINT_GET_ALL_OFFERS_REQUEST;

            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            //request.header(("Authorization", "your token"));

            try {
                Response response = client.newCall(request).execute();

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
