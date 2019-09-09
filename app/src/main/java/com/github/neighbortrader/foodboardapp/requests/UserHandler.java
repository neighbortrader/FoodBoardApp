package com.github.neighbortrader.foodboardapp.requests;

import android.content.Context;
import android.util.Log;

import com.auth0.android.jwt.JWT;
import com.github.neighbortrader.foodboardapp.clientmodel.Grocery;
import com.github.neighbortrader.foodboardapp.clientmodel.Offer;
import com.github.neighbortrader.foodboardapp.clientmodel.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UserHandler extends AsyncRequest<User> {
    public static String TAG = UserHandler.class.getSimpleName();

    @Getter
    private static ArrayList<String> groceriesCategories;

    protected UserHandler(Context context, OnEventListener callback) {
        super(context, callback);
    }

    // TODO: implement
    public static UserHandler builder(RequestTyps requestTyps, Context context, OnEventListener callback, Offer... offers) {

        UserHandler userHandler = null;

        switch (requestTyps){
            case GET_JWT_TOKEN:
                userHandler = new UserHandler(context, callback);
                break;

            case POST_NEW_USER:
                break;
            default:
                return null;
        }

        return userHandler;
    }

    @Override
    protected List<User> doInBackground(Void... params) {
        Log.d(TAG, "doInBackground()");

        OkHttpClient client = UnsafeOkHttpClient.getUnsafeOkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        publishProgress(request.toString());

        Log.d(TAG, "Request: " + request);

        try {
            Response response = client.newCall(request).execute();


        } catch (Exception e) {
            exception = e;
            Log.e(TAG, "Exception while waiting for Result", e);
        }

        return null;
    }

    private class JWTHandler extends AsyncRequest<JWT>{
        protected JWTHandler(Context context, OnEventListener callback) {
            super(context, callback);
            setUrl(Urls.BASE_URL + Urls.ENDPOINT_GET_JWT_TOKEN);
        }

        @Override
        protected List<JWT> doInBackground(Void... params) {
            OkHttpClient client = UnsafeOkHttpClient.getUnsafeOkHttpClient();

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            publishProgress(request.toString());

            Log.d(TAG + "JWTHandler", "Request: " + request);

            try {
                Response response = client.newCall(request).execute();

                //TODO: implement jwt reading
                return null;

            } catch (Exception e) {
                exception = e;
                Log.e(TAG + "JWTHandler", "Exception while waiting for Result", e);
            }

            return null;
        }


    }
}
