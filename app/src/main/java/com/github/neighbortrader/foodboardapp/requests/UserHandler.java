package com.github.neighbortrader.foodboardapp.requests;

import android.content.Context;
import android.util.Log;

import com.auth0.android.jwt.JWT;
import com.github.neighbortrader.foodboardapp.clientmodel.Grocery;
import com.github.neighbortrader.foodboardapp.clientmodel.Offer;
import com.github.neighbortrader.foodboardapp.clientmodel.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UserHandler extends AsyncRequest<User> {
    public static String TAG = UserHandler.class.getSimpleName();

    @Getter
    private static ArrayList<String> groceriesCategories;

    private JWT receivedJWToken;

    @Setter
    private User userToCreate;

    public JWT getReceivedJWToken() {
        return receivedJWToken;
    }

    protected UserHandler(Context context, OnEventListener callback, RequestTyps requestTyp) {
        super(context, callback);
        this.requestTyps = requestTyp;
    }

    // TODO: implement
    public static UserHandler builder(RequestTyps requestTyps, Context context, OnEventListener callback, User... user) {

        UserHandler userHandler = null;

        switch (requestTyps) {
            case GET_JWT_TOKEN:
                userHandler = new UserHandler(context, callback, RequestTyps.GET_JWT_TOKEN);
                userHandler.setUrl(Urls.BASE_URL + Urls.ENDPOINT_GET_JWT_TOKEN);
                break;

            case POST_NEW_USER:
                userHandler = new UserHandler(context, callback, RequestTyps.POST_NEW_USER);
                userHandler.setUrl(Urls.BASE_URL + Urls.ENDPOINT_CREATE_NEW_USER);
                if (user.length > 0)
                    userHandler.setUserToCreate(user[0]);

                break;
            default:
                return null;
        }

        return userHandler;
    }

    @Override
    protected List<User> doInBackground(Void... params) {
        Log.d(TAG, "doInBackground()");
        switch (requestTyps) {
            case GET_JWT_TOKEN:
                try {
                    receivedJWToken = getJWTRequest();
                    return null;    // Always null (should think about jwtHandler)
                }  catch (Exception e) {
                    exception = e;
                }
                break;

            case POST_NEW_USER:
                postNewUser(userToCreate);
                break;
        }

        return null;
    }

    public User postNewUser(User userToCreate) {
        if (userToCreate != null) {
            OkHttpClient client = UnsafeOkHttpClient.getUnsafeOkHttpClient();

            Request.Builder builder = new Request.Builder();
            builder = builder.url(url);
            builder = builder.post(nameValueMapToFormbody(userToCreate.toNameValueMap()));

            Request request = builder.build();

            Log.d(TAG, "Request: " + request);

            publishProgress(request.toString());

            try {
                Response response = client.newCall(request).execute();
                publishProgress(response.toString());
                Log.d(TAG, "Response: " + response);

                if (response.code() != 200) {
                    throw new Exception(String.format("Received http-statuscode %s\n%s", response.code(), response.body().string()));
                }

            } catch (Exception e) {
                exception = e;
            }
        } else {
            exception = new NullPointerException("No user to create has been set. Did you call setUserToCreate()?");
        }

        return null;
    }


    public static JWT getJWTRequest() throws Exception {
        OkHttpClient client = UnsafeOkHttpClient.getUnsafeOkHttpClient();

        Request request = new Request.Builder()
                .url(Urls.BASE_URL + Urls.ENDPOINT_GET_JWT_TOKEN)
                .build();


        Log.d(TAG, "Request: " + request);

        Response response = client.newCall(request).execute();

        if (response.code() != 200) {
            throw new Exception(String.format("Received http-statuscode %s\n%s", response.code(), response.body().string()));
        }

        //TODO: implement jwt reading
        return null;
    }
}
