package com.github.neighbortrader.foodboardapp.requests;

import android.content.Context;
import android.util.Log;

import com.auth0.android.jwt.JWT;
import com.github.neighbortrader.foodboardapp.clientmodel.Offer;
import com.github.neighbortrader.foodboardapp.clientmodel.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OfferHandler extends AsyncRequest<OfferHandler> {

    public final static String TAG = OfferHandler.class.getSimpleName();

    @Getter
    private Offer offerToPost;

    @Getter
    @Setter
    private ArrayList<Offer> receivedOffers;

    protected OfferHandler(Context context, OnEventListener callback) {
        super(context, callback);
    }

    public static OfferHandler builder(RequestTyps requestTyps, Context context, OnEventListener callback, Offer... offers) {
        OfferHandler offerHandler = null;

        switch (requestTyps) {
            case POST_NEW_OFFER:
                offerHandler = new OfferHandler(context, callback);
                offerHandler.setRequestTyps(RequestTyps.POST_NEW_OFFER);
                offerHandler.setOfferToPost(offers[0]);
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

    public void setOfferToPost(Offer offerToPost) {
        this.offerToPost = offerToPost;
    }

    @Override
    protected OfferHandler doInBackground(Void... params) {
        Log.d(TAG, "doInBackground()");

        switch (requestTyps) {
            case POST_NEW_OFFER:
                postOfferRequest(offerToPost);
                break;

            case GET_ALL_OFFERS:
                setReceivedOffers(getOffersRequest());
                break;
        }
        return this;
    }

    public void postOfferRequest(Offer offerToPost){
        User user = User.getCurrentUserInstance();

        if (offerToPost != null && user != null) {
            try {
                JWT jwtToken = user.getJwtToken();

                if (jwtToken == null) {
                    Log.w(TAG, "no jwtToken found, trying to fetch one");
                    jwtToken = UserHandler.getJWTRequest(user);
                    user.setJwtToken(jwtToken);
                }

                if (jwtToken.isExpired(0)) {
                    Log.w(TAG, "jwtToken is expired, trying to fetch a new one");

                    jwtToken = UserHandler.getJWTRequest(user);

                    if (jwtToken == null) {
                        throw new IllegalStateException(String.format("Could not post offer. JWT-Token was expired and it was not possible to fetch a new one"));
                    } else {
                        Log.d(TAG, "Successfully received new Token");
                        user.setJwtToken(jwtToken);
                    }
                }

                OkHttpClient client = new OkHttpClient();

                Request.Builder builder = new Request.Builder();
                builder = builder.url(url);
                builder = builder.post(nameValueMapToRequestBody(offerToPost.toNameValueMap()))
                        .header("Authorization", String.format("bearer %s", jwtToken.toString()));

                Request request = builder.build();

                publishProgress(request.toString());

                Log.d(TAG, "Request: " + request);

                Response response = client.newCall(request).execute();
                Log.d(TAG, "Response: " + response);

                setLastHttpStatuscode(response.code() );

                if (getLastHttpStatuscode() != 200) {
                    throw new Exception(String.format("Received http-statuscode %s\n%s", response.code(), response.body().string()));
                }

                user.addOffer(offerToPost);

            } catch (Exception e) {
                exception = e;
            }
        } else {
            exception = new NullPointerException("Could not post new offer");
        }
    }

    public ArrayList<Offer> getOffersRequest(){
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
}
