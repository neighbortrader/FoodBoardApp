package com.github.neighbortrader.foodboardapp.clienttransfermodel;

import java.io.IOException;

public interface Request {
    String post();

    String get() throws IOException;

}
