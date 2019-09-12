package com.github.neighbortrader.foodboardapp.controller.requests;

import android.content.Context;
import android.util.Log;

import com.github.neighbortrader.foodboardapp.Model.clientmodel.Grocery;
import com.github.neighbortrader.foodboardapp.Model.clientmodel.Offer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import lombok.Getter;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GroceryRequestController extends AsyncRequestController<GroceryRequestController> {
    public static String TAG = GroceryRequestController.class.getSimpleName();

    @Getter
    private static ArrayList<String> groceriesCategories;

    protected GroceryRequestController(Context context, OnEventListener callback) {
        super(context, callback);
    }

    public static GroceryRequestController builder(RequestTyps requestTyps, Context context, OnEventListener callback, Offer... offers) {
        GroceryRequestController categoryHandler = null;

        switch (requestTyps) {
            case GET_ALL_CATEGORIES:
                categoryHandler = new GroceryRequestController(context, callback);
                categoryHandler.setRequestTyps(RequestTyps.POST_NEW_OFFER);
                categoryHandler.setUrl(Urls.BASE_URL + Urls.ENDPOINT_GET_ALL_CATEGORIES);
                break;

            default:
                return null;
        }

        return categoryHandler;
    }

    @Override
    protected GroceryRequestController doInBackground(Void... params) {
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
