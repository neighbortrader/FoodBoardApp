package com.github.neighbortrader.foodboardapp.requests;

import android.content.Context;
import android.util.Log;

import com.auth0.android.jwt.JWT;
import com.github.neighbortrader.foodboardapp.clientmodel.Offer;
import com.github.neighbortrader.foodboardapp.clientmodel.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OfferHandler extends AsyncRequest<Offer> {

    public final static String TAG = OfferHandler.class.getSimpleName();

    private Offer offerToCreate;

    protected OfferHandler(Context context, OnEventListener callback) {
        super(context, callback);
    }

    public static OfferHandler builder(RequestTyps requestTyps, Context context, OnEventListener callback, Offer... offers) {
        OfferHandler offerHandler = null;

        switch (requestTyps) {
            case POST_NEW_OFFER:
                offerHandler = new OfferHandler(context, callback);
                offerHandler.setRequestTyps(RequestTyps.POST_NEW_OFFER);
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

    public void setOfferToCreate(Offer offerToCreate) {
        this.offerToCreate = offerToCreate;
    }

    @Override
    protected List<Offer> doInBackground(Void... params) {
        Log.d(TAG, "doInBackground()");

        switch (requestTyps) {
            case POST_NEW_OFFER:
                User user = User.getCurrentUserInstance();

                if (offerToCreate != null && user != null) {
                    try {
                        JWT jwtToken = user.getJwtToken();

                        if (jwtToken.isExpired(0)) {
                            Log.w(TAG, "jwtToken is expired, trying to fetch a new one");

                            jwtToken = UserHandler.getJWTRequest(user);

                            if (jwtToken == null) {
                                throw new IllegalStateException(String.format("Could not post offer. JWT-Token was expired and it was not possible to fetch a new one"));
                            } else {
                                Log.d(TAG, "Successfully received new Token");
                            }
                        }

                        offerToCreate.setCreationDate(LocalDateTime.now());

                        OkHttpClient client = new OkHttpClient();

                        Request.Builder builder = new Request.Builder();
                        builder = builder.url(url);
                        builder = builder.post(nameValueMapToRequestBody(offerToCreate.toNameValueMap()));

                        Request request = builder.build();

                        publishProgress(request.toString());

                        Log.d(TAG, "Request: " + request);

                        Response response = client.newCall(request).execute();
                        Log.d(TAG, "Response: " + response);

                        if (response.code() != 200) {
                            throw new Exception(String.format("Received http-statuscode %s\n%s", response.code(), response.body().string()));
                        }
                    } catch (Exception e) {
                        exception = e;
                    }
                } else {
                    exception = new NullPointerException("Could not post new offer");
                }

                return null;

            case GET_ALL_OFFERS:
                OkHttpClient client = UnsafeOkHttpClient.getUnsafeOkHttpClient();

                Request request = new Request.Builder()
                        .url(url)
                        .build();

                Log.d(TAG, "Request: " + request);

                publishProgress(request.toString());

                try {
                    Response response = client.newCall(request).execute();

                    Log.d(TAG, "Response: " + response);

                    publishProgress(response.toString());

                    if (response.code() != 200) {
                        throw new Exception(String.format("Received http-statuscode: %s\n%s", response.code(), response.body().string()));
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
