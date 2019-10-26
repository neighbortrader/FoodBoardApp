package com.github.neighbortrader.foodboardapp.handler.tokenHandler;

import com.auth0.android.jwt.JWT;

import lombok.Getter;
import lombok.Setter;

public class TokenHandler {

    @Getter
    @Setter
    private static JWT jwtToken;

    public static void removeToken() {
        jwtToken = null;
    }

    public static boolean hasToken() {
        return (jwtToken != null) ? true : false;
    }
}