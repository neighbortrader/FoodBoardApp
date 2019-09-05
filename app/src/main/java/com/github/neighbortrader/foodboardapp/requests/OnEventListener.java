package com.github.neighbortrader.foodboardapp.requests;

import java.util.List;

public interface OnEventListener<T> {
    /**
     * onResponse either gives null or a List of the desired generic with 1 or more objects.
     * @param object
     */
    void onResponse(List<T> object);
    void onFailure(Exception e);
}