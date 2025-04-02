package com.example.chargingstation.chainofresponsibility;

import com.example.chargingstation.vao.ChargingStation;
import com.example.chargingstation.vao.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public abstract class ChargingHandler {
    protected ChargingHandler nextHandler;

    public void setNextHandler(ChargingHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public abstract boolean handle(ChargingStation station, User user);
}