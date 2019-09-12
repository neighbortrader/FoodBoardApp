package com.github.neighbortrader.foodboardapp.handler.requestsHandler;

import android.content.Context;
import android.util.Log;

import com.github.neighbortrader.foodboardapp.clientmodel.Grocery;
import com.github.neighbortrader.foodboardapp.clientmodel.Offer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import lombok.Getter;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GroceryRequestHandler extends AsyncRequestHandler<GroceryRequestHandler> {
    public static String TAG = GroceryRequestHandler.class.getSimpleName();

    @Getter
    private static ArrayList<String> groceriesCategories;

    protected GroceryRequestHandler(Context context, OnRequestEventListener callback) {
        super(context, callback);
    }

    public static GroceryRequestHandler builder(RequestTyps requestTyps, Context context, OnRequestEventListener callback, Offer... offers) {
        GroceryRequestHandler categoryHandler = null;

        switch (requestTyps) {
            case GET_ALL_CATEGORIES:
                categoryHandler = new GroceryRequestHandler(context, callback);
                categoryHandler.setRequestTyps(RequestTyps.POST_NEW_OFFER);
                categoryHandler.setUrl(Urls.BASE_URL + Urls.ENDPOINT_GET_ALL_CATEGORIES);
                break;

            default:
                return null;
        }

        return categoryHandler;
    }

    @Override
    protected GroceryRequestHandler doInBackground(Void... params) {
        Log.d(TAG, "doInBackground()");

        OkHttpClient client = UnsafeOkHttpClient.getUnsafeOkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        publishProgress(request.toString());

        Log.d(TAG, "Request: " + request);

        try {
            Response response = client.newCall(request).execute();

            Log.d(TAG, "Response: " + response);

            publishProgress(response.toString());

            if (response.code() != 200) {
                throw new Exception(String.format("Received http-statuscode %s\n%s", response.code(), response.body().string()));
            }

            String jsonData = response.body().string();
            JSONArray jsonArray = new JSONArray(jsonData);

            ArrayList<Grocery> receivedCategories = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                receivedCategories.add(Grocery.createGroceryFromJSON(jsonObject));
            }

            Grocery.updateCurrentGroceries(receivedCategories);

            return this;
        } catch (Exception e) {
            exception = e;
            Log.e(TAG, "Exception while waiting for Result", e);
        }

        return null;
    }
}
