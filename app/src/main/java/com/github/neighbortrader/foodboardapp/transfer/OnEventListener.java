package com.github.neighbortrader.foodboardapp.transfer;

import java.util.List;

public interface OnEventListener<T> {
    void onResponse(List<T> object);
    void onFailure(Exception e);
}