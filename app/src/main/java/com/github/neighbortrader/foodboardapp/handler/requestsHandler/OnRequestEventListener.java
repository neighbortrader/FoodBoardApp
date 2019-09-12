package com.github.neighbortrader.foodboardapp.handler.requestsHandler;

public interface OnRequestEventListener<T> {
    /**
     * onResponse either gives null or a List of the desired generic with 1 or more objects.
     *
     * @param object
     */
    void onResponse(T object);

    void onFailure(Exception e);

    void onProgress(String progressUpdate);
}