package com.github.neighbortrader.foodboardapp.handler.clientmodelHandler;

import com.github.neighbortrader.foodboardapp.clientmodel.Address;
import com.github.neighbortrader.foodboardapp.clientmodel.User;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class UserHandlerTest {
    @Test
    public void setCurrentUserInstance_defaultCall_successfulCall() {
        User user = User.userBuilder("username", "password",
                "username@email.com", new Address("teststratße", "1",
                        "10315", "Berlin"), true);

        UserHandler.initUserStatusListener((currentUser, hasUser) -> {

        });

        UserHandler.setCurrentUserInstance(user);

        boolean hasUser = false;

        if (UserHandler.getCurrentUserInstance() !=  null) {
            hasUser = true;
        } else {
            hasUser = false;
        }

        assertTrue(hasUser);
    }

    @Test
    public void setCurrentUserInstance_nullCall_noUserSet() {
        UserHandler.initUserStatusListener((currentUser, hasUser) -> {

        });

        UserHandler.setCurrentUserInstance(null);

        assertNull(UserHandler.getCurrentUserInstance());
    }


}