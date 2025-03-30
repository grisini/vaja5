package com.example.chargingstation.chainofresponsibility;

import com.example.chargingstation.vao.ChargingStation;
import com.example.chargingstation.vao.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

@Named
@ApplicationScoped
public class CompatibilityHandler extends ChargingHandler {
    @Override
    public boolean handle(ChargingStation station, User user) {
        if (!station.getProvider().equals(user.getCarType())) { // Predpostavimo, da je ponudnik kompatibilen z vrsto vozila
            System.out.println("Vozilo ni kompatibilno s polnilnico.");
            return false;
        }
        // Če je vozilo kompatibilno, vrni true (vsi pogoji so izpolnjeni)
        return true;
    }
}