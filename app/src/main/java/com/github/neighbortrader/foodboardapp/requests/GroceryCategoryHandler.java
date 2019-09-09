package com.github.neighbortrader.foodboardapp.requests;

import android.content.Context;
import android.util.Log;

import com.github.neighbortrader.foodboardapp.clientmodel.Grocery;
import com.github.neighbortrader.foodboardapp.clientmodel.Offer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GroceryCategoryHandler extends AsyncRequest<Grocery> {
    public static String TAG = GroceryCategoryHandler.class.getSimpleName();

    @Getter
    private static ArrayList<String> groceriesCategories;

    protected GroceryCategoryHandler(Context context, OnEventListener callback) {
        super(context, callback);
    }

    public static GroceryCategoryHandler builder(RequestTyps requestTyps, Context context, OnEventListener callback, Offer... offers) {
        GroceryCategoryHandler categoryHandler = null;

        switch (requestTyps) {
            case GET_ALL_CATEGORIES:
                categoryHandler = new GroceryCategoryHandler(context, callback);
                categoryHandler.setRequestTyps(RequestTyps.CREATE_NEW_OFFER);
                categoryHandler.setUrl(Urls.BASE_URL + Urls.ENDPOINT_CREATE_NEW_OFFER);
                break;

            default:
                return null;
        }

        return categoryHandler;
    }

    public static void insertNewGroceryCategories(){

    }

    @Override
    protected List<Grocery> doInBackground(Void... params) {
        OkHttpClient client = UnsafeOkHttpClient.getUnsafeOkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Log.d(TAG, "Request: " + request);

        try {
            Response response = client.newCall(request).execute();

            Log.d(TAG, "Response: " + response);

            String jsonData = response.body().string();
            JSONArray jsonArray = new JSONArray(jsonData);

            ArrayList<Offer> receivedOffers = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                receivedOffers.add(Offer.createOfferFromJSON(jsonObject));
            }

            return receivedOffers;
        } catch (Exception e) {
            exception = e;
            Log.e(TAG, "Exception while waiting for Result", e);
        }

        return null;
    }
}
