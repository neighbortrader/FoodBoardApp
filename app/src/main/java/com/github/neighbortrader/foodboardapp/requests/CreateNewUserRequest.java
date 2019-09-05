package com.github.neighbortrader.foodboardapp.requests;

import android.content.Context;
import android.util.Log;

import com.github.neighbortrader.foodboardapp.clientmodel.User;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CreateNewUserRequest extends AsyncRequest<Void> {

    public final static String TAG = CreateNewUserRequest.class.getSimpleName();

    private User userToCreate;
    private final String url = Constant.BASE_URL + Constant.ENDPOINT_CREATE_NEW_USER;


    public CreateNewUserRequest(Context context, OnEventListener callback) {
        super(context, callback);
    }


    public void setUserToCreate(User userToCreate) {
        this.userToCreate = userToCreate;
    }

    @Override
    protected List<Void> doInBackground(Void... params) {
        Log.d(TAG, "doInBackground()");

        if (userToCreate != null) {
            OkHttpClient client = new OkHttpClient();

            // Create a http request object.
            Request.Builder builder = new Request.Builder();
            builder = builder.url(url);
            builder = builder.post(nameValueMapToFormbody(userToCreate.toNameValueMap()));

            Request request = builder.build();

            Log.d(TAG, "Request: " + request);

            try {
                Response response = client.newCall(request).execute();

                Log.d(TAG, "Response: " + response);
            } catch (Exception e) {
                exception = e;
            }
        } else {
            exception = new NullPointerException("No user to create has been set. Did you call setUserToCreate()?");
        }

        return null;
    }
}
