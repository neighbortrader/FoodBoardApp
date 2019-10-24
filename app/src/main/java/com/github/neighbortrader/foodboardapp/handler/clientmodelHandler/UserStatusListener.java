package com.github.neighbortrader.foodboardapp.handler.clientmodelHandler;

import com.github.neighbortrader.foodboardapp.clientmodel.User;

public interface UserStatusListener {
    void onUserStatusChanged(User currentUser, boolean hasUser);
}
