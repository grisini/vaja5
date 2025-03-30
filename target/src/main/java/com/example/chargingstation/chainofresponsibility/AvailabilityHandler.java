package com.example.chargingstation.chainofresponsibility;

import com.example.chargingstation.vao.ChargingStation;
import com.example.chargingstation.vao.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class AvailabilityHandler extends ChargingHandler {
    @Override
    public boolean handle(ChargingStation station, User user) {
        if (!station.getStatus().equals("prosto")) {
            System.out.println("Polnilnica je zasedena.");
            return false;
        }
        // Če je polnilnica na voljo, preverimo naslednji pogoj v verigi
        return nextHandler == null || nextHandler.handle(station, user);
    }
}
