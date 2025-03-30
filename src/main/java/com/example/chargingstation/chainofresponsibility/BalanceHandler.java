package com.example.chargingstation.chainofresponsibility;

import com.example.chargingstation.vao.ChargingStation;
import com.example.chargingstation.vao.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class BalanceHandler extends ChargingHandler {
    @Override
    public boolean handle(ChargingStation station, User user) {
        if (user.getBalance() < station.getChargingSpeed() * 0.5) { // Predpostavimo, da je cena polovica hitrosti polnjenja
            System.out.println("Uporabnik nima dovolj sredstev na računu.");
            return false;
        }
        // Če ima uporabnik dovolj sredstev, preverimo naslednji pogoj v verigi
        return nextHandler == null || nextHandler.handle(station, user);
    }
}