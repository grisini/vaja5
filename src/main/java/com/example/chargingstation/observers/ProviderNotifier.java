package com.example.chargingstation.observers;

import com.example.chargingstation.observers.ChargingStationObserver;
import com.example.chargingstation.vao.ChargingStation;

public class ProviderNotifier implements ChargingStationObserver {
    @Override
    public void update(ChargingStation chargingStation) {
        System.out.println("🏢 Ponudnik obveščen: Polnilnica " + chargingStation.getName() + " pri ponudniku " + chargingStation.getProvider() + " je zdaj " + chargingStation.getStatus() + ".");
    }
}
