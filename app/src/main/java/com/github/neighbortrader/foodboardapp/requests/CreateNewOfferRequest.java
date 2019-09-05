package com.github.neighbortrader.foodboardapp.requests;

import android.content.Context;
import android.util.Log;

import com.github.neighbortrader.foodboardapp.clientmodel.Offer;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CreateNewOfferRequest extends AsyncRequest<Void> {
    public final static String TAG = CreateNewUserRequest.class.getSimpleName();

    private Offer offerToPost;
    private final String url = Constant.BASE_URL + Constant.ENDPOINT_CREATE_NEW_OFFER;

    public CreateNewOfferRequest(Context context, OnEventListener callback) {
        super(context, callback, RequestTyps.CREATE_NEW_OFFER);
    }

    public Offer getOfferToPost() {
        return offerToPost;
    }

    public void setOfferToPost(Offer offerToPost) {
        this.offerToPost = offerToPost;
    }

    @Override
    protected void construct() {
        this.offerToPost = null;
    }

    @Override
    protected List<Void> doInBackground(Void... params) {
        Log.d(TAG, "doInBackground()");

        if (offerToPost != null) {
            // TODO: implement request
            OkHttpClient client = new OkHttpClient();

            // Create a http request object.
            Request.Builder builder = new Request.Builder();
            builder = builder.url(url);
            builder = builder.post(nameValueMapToFormbody(offerToPost.toNameValueMap()));

            Request request = builder.build();

            Log.d(TAG, "Request: " + request);

            try {
                Response response = client.newCall(request).execute();
                Log.d(TAG, "Response: " + response);
            } catch (Exception e) {
                exception = e;
            }

        } else {
            exception = new NullPointerException("No offer to post has been set. Did you call setOfferToPost()?");
        }

        return null;
    }


}
