package com.github.neighbortrader.foodboardapp.handler.requests;

import android.content.Context;
import android.util.Log;

import com.auth0.android.jwt.JWT;
import com.github.neighbortrader.foodboardapp.clientmodel.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UserRequestHandler extends AsyncRequestHandler<UserRequestHandler> {
    public static String TAG = UserRequestHandler.class.getSimpleName();

    @Getter
    private static ArrayList<String> groceriesCategories;

    @Getter
    private JWT latestReceivedJWToken;

    @Setter
    private User userToCreate;

    protected UserRequestHandler(Context context, OnRequestEventListener callback, RequestTyps requestTyp) {
        super(context, callback);
        this.requestTyps = requestTyp;
    }

    public static UserRequestHandler builder(RequestTyps requestTyps, Context context, OnRequestEventListener callback, User... user) {

        UserRequestHandler userRequestController = null;

        switch (requestTyps) {
            case GET_JWT_TOKEN:
                userRequestController = new UserRequestHandler(context, callback, RequestTyps.GET_JWT_TOKEN);
                userRequestController.setUrl(Urls.BASE_URL + Urls.ENDPOINT_GET_JWT_TOKEN);
                break;

            case POST_NEW_USER:
                userRequestController = new UserRequestHandler(context, callback, RequestTyps.POST_NEW_USER);
                userRequestController.setUrl(Urls.BASE_URL + Urls.ENDPOINT_CREATE_NEW_USER);
                if (user.length > 0)
                    userRequestController.setUserToCreate(user[0]);
                break;
            default:
                return null;
        }

        return userRequestController;
    }

    public static JWT getJWTRequest(User userToGetJWTToken) throws Exception {
        Log.d(TAG, "getJWTRequest()");
        OkHttpClient client = UnsafeOkHttpClient.getUnsafeOkHttpClient();

        Map<String, String> nameValueMap = new HashMap<>();
        nameValueMap.put("username", userToGetJWTToken.getUsername());
        nameValueMap.put("password", userToGetJWTToken.getPassword());

        Request request = new Request.Builder()
                .url(Urls.BASE_URL + Urls.ENDPOINT_GET_JWT_TOKEN)
                .post(nameValueMapToRequestBody(nameValueMap))
                .build();

        Log.d(TAG, "Request: " + request);

        Response response = client.newCall(request).execute();

        if (response.code() != 200) {
            throw new Exception(String.format("Received http-statuscode %s\n%s", response.code(), response.body().string()));
        }

        String responseBody = response.body().string();

        if (responseBody.isEmpty()) {
            throw new Exception("received empty token. Password and or Username is invalid");
        }

        JWT jwtToken = new JWT(responseBody);

        if (jwtToken.isExpired(0)) {
            throw new Exception("received JWT-Token is expired");
        }

        return jwtToken;
    }

    @Override
    protected UserRequestHandler doInBackground(Void... params) {
        Log.d(TAG, "doInBackground()");
        switch (requestTyps) {
            case GET_JWT_TOKEN:
                try {
                    latestReceivedJWToken = getJWTRequest(userToCreate);
                    userToCreate.setJwtToken(latestReceivedJWToken);
                    return null;
                } catch (Exception e) {
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

            Request request = new Request.Builder()
                    .url(url)
                    .post(nameValueMapToRequestBody(userToCreate.toNameValueMap()))
                    .build();

            Log.d(TAG, "Request: " + request);

            publishProgress(request.toString());

            try {
                Response response = client.newCall(request).execute();
                publishProgress(response.toString());
                Log.d(TAG, "Response: " + response);

                if (response.code() != 200) {
                    throw new Exception(String.format("Received http-statuscode %s\n%s", response.code(), response.body().string()));
                }

                publishProgress("trying to get JWT-Token");

                latestReceivedJWToken = getJWTRequest(userToCreate);

            } catch (Exception e) {
                exception = e;
            }
        } else {
            exception = new NullPointerException("No user to create has been set. Did you call setUserToCreate()?");
        }

        return null;
    }
}
