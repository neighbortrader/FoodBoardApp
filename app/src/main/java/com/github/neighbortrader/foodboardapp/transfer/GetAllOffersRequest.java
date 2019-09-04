package com.github.neighbortrader.foodboardapp.transfer;

import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.github.neighbortrader.foodboardapp.MyApplication;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetAllOffersRequest implements Get {
    public final static String TAG = GetAllOffersRequest.class.getSimpleName();
    private Context context = MyApplication.getAppContext();

    @Override
    public boolean get() {
        //HttpUrl.Builder urlBuilder = HttpUrl.parse(Constant.BASE_URL + Constant.ENDPOINT_GET_ALL_OFFERS_REQUEST).newBuilder();
        //urlBuilder.addQueryParameter("v", "1.0");
        final String url = Constant.BASE_URL + Constant.ENDPOINT_GET_ALL_OFFERS_REQUEST;

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        //request.header(("Authorization", "your token"));

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "Request Callback: onFailure()", e);
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                } else {
                    Log.d(TAG, String.format("Successful response:\n%s", response.toString()));
                    Looper.prepare();
                    Toast.makeText(context, String.format("Successful response:\n%s", response.toString()), Toast.LENGTH_SHORT).show();
                }
            }
        });

        return true;
    }
}
