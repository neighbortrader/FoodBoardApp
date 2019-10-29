package com.github.neighbortrader.foodboardapp.handler.tokenHandler;

import com.auth0.android.jwt.JWT;
import com.github.neighbortrader.foodboardapp.handler.clientmodelHandler.UserHandler;

import lombok.Getter;

public class TokenHandler {
    @Getter
    private static JWT jwtToken;

    public static void setJwtToken(JWT jwtToken) {
        TokenHandler.jwtToken = jwtToken;
        UserHandler.callUserStatusUpdate();
    }

    public static void removeToken() {
        jwtToken = null;
        UserHandler.callUserStatusUpdate();
    }

    public static boolean hasToken() {
        return (jwtToken != null) ? true : false;
    }
}