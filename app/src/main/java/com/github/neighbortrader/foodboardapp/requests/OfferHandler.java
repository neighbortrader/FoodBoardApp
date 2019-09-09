package com.github.neighbortrader.foodboardapp.requests;

import android.content.Context;
import android.util.Log;

import com.github.neighbortrader.foodboardapp.clientmodel.Offer;
import com.github.neighbortrader.foodboardapp.clientmodel.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OfferHandler extends AsyncRequest<Offer> {

    public final static String TAG = OfferHandler.class.getSimpleName();

    private Offer offerToCreate;

    public void setOfferToCreate(Offer offerToCreate) {
        this.offerToCreate = offerToCreate;
    }

    protected OfferHandler(Context context, OnEventListener callback) {
        super(context, callback);
    }

    public static OfferHandler builder(RequestTyps requestTyps, Context context, OnEventListener callback, Offer... offers) {
        OfferHandler offerHandler = null;

        switch (requestTyps) {
            case CREATE_NEW_OFFER:
                offerHandler = new OfferHandler(context, callback);
                offerHandler.setRequestTyps(RequestTyps.CREATE_NEW_OFFER);
                offerHandler.setOfferToCreate(offers[0]);
                offerHandler.setUrl(Urls.BASE_URL + Urls.ENDPOINT_CREATE_NEW_OFFER);
                break;

            case GET_ALL_OFFERS:
                offerHandler = new OfferHandler(context, callback);
                offerHandler.setRequestTyps(RequestTyps.GET_ALL_OFFERS);
                offerHandler.setUrl(Urls.BASE_URL + Urls.ENDPOINT_GET_ALL_OFFERS);
                break;

            default:
                return null;
        }

        return offerHandler;
    }

    @Override
    protected List<Offer> doInBackground(Void... params) {
        Log.d(TAG, "doInBackground()");

        switch (requestTyps) {
            case CREATE_NEW_OFFER:

                User user = User.getCurrentUserInstance();

                if (offerToCreate != null && user != null) {

                    OkHttpClient client = new OkHttpClient();

                    Request.Builder builder = new Request.Builder();
                    builder = builder.url(url);
                    builder = builder.post(nameValueMapToFormbody(offerToCreate.toNameValueMap()));

                    Request request = builder.build();

                    Log.d(TAG, "Request: " + request);

                    try {
                        Response response = client.newCall(request).execute();
                        Log.d(TAG, "Response: " + response);

                        if (response.code() != 200) {
                            throw new Exception(String.format("Received http-statuscode %s\n%s", response.code(), response.body().string()));
                        }

                    } catch (Exception e) {
                        exception = e;
                    }

                } else {
                    exception = new NullPointerException("No offer to post has been set. Is a the user logged in and did you call setOfferToPost()?");
                }

                return null;

            case GET_ALL_OFFERS:
                OkHttpClient client = UnsafeOkHttpClient.getUnsafeOkHttpClient();

                Request request = new Request.Builder()
                        .url(url)
                        .build();

                Log.d(TAG, "Request: " + request);

                try {
                    Response response = client.newCall(request).execute();

                    Log.d(TAG, "Response: " + response);
                    Log.d(TAG, response.body().string());

                    if (response.code() != 200) {
                        throw new Exception(String.format("Received http-statuscode %s\n%s", response.code(), response.body().string()));
                    }

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
        return null;
    }
}
