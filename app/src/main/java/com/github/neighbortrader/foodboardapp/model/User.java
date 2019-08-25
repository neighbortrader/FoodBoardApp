package com.github.neighbortrader.foodboardapp.model;

import lombok.Getter;
import lombok.Setter;

public class User {

    @Getter
    @Setter
    private String nickname;

    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private Address address;

}
