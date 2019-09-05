package com.github.neighbortrader.foodboardapp.requests;

import android.content.Context;
import android.util.Log;

import com.github.neighbortrader.foodboardapp.clientmodel.User;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PostNewUserRequest extends AsyncRequest<Void>  {

    public final static String TAG = PostNewUserRequest.class.getSimpleName();

    private User userToCreate;
    private final String url =  Constant.BASE_URL + Constant.ENDPOINT_CREATE_NEW_USER;

    public PostNewUserRequest(Context context, OnEventListener callback) {
        super(context, callback);
        this.userToCreate = null;
    }

    public PostNewUserRequest(Context context, OnEventListener callback, User userToCreate) {
        this(context, callback);
        this.userToCreate=userToCreate;
    }

    public void setUserToCreate(User userToCreate) {
        this.userToCreate = userToCreate;
    }

    @Override
    protected List<Void> doInBackground(Void... params) {
        Log.d(TAG, "doInBackground()");

        if (userToCreate != null) {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            try {
                Response response = client.newCall(request).execute();
            } catch (Exception e) {
                exception = e;
            }
        }else{
            throw new NullPointerException("No user to create has been set. Did you call setUserToCreate()?");
        }

        return null;
    }
}
