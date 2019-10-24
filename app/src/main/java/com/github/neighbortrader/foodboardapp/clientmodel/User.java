package com.github.neighbortrader.foodboardapp.clientmodel;

import java.util.Hashtable;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

public class User implements ToNameValueMap {
    public static final String TAG = User.class.getSimpleName();

    @Getter
    @Setter
    private String username;
    @Getter
    @Setter
    private String password;
    @Getter
    @Setter
    private String email;
    @Getter
    @Setter
    private Address address;
    @Setter
    private boolean staySignedIn;

    private User(String username, String password, String email, Address address, boolean staySignedIn) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.address = address;
        this.staySignedIn = staySignedIn;
    }

    public User() {
    }

    public static User userBuilder(String username, String password, String email, Address address, boolean staySignedIn) {
        return new User(username, password, email, address, staySignedIn);
    }

    public static User userBuilder() {
        return new User();
    }

    public boolean isStaySignedIn() {
        return staySignedIn;
    }

    @Override
    public Map<String, String> toNameValueMap() {
        if (this != null) {
            Map<String, String> nameValueMap = new Hashtable<>();

            nameValueMap.put("username", username);
            nameValueMap.put("password", password);

            if (email != null && address != null) {
                nameValueMap.put("email", email);
                nameValueMap.putAll(address.toNameValueMap());
            }

            return nameValueMap;
        }
        return null;
    }
}
