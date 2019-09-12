package com.github.neighbortrader.foodboardapp.handler.tokenHandler;

import com.github.neighbortrader.foodboardapp.clientmodel.User;

public class TokenHandler {
    public static void removeTokenFromUser(User user){
        user.setJwtToken(null);
    }
}
