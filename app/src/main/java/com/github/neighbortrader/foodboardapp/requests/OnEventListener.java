package com.github.neighbortrader.foodboardapp.requests;

import java.util.List;

import butterknife.Optional;

public interface OnEventListener<T> {
    /**
     * onResponse either gives null or a List of the desired generic with 1 or more objects.
     * @param object
     */
    void onResponse(List<T> object);
    void onFailure(Exception e);
    void onProgress(String progressUpdate);
}