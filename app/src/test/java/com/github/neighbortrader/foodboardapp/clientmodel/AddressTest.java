package com.github.neighbortrader.foodboardapp.clientmodel;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AddressTest {

    @Test
    public void createAdresse_defaulCall_succesfull(){
        Address address = new Address("Teststraße", "2", "10315",
                "Berlin");

        Map<String, String> nameValueMap = address.toNameValueMap();

        assertEquals(4, nameValueMap.size());
    }


}