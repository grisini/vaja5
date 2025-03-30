package com.example.chargingstation.observers;

import com.example.chargingstation.vao.ChargingStation;

import java.util.ArrayList;
import java.util.List;

public class ChargingStationDisplay implements ChargingStationObserver {
    private List<ChargingStation> chargingStations;

    public ChargingStationDisplay(List<ChargingStation> chargingStations) {
        this.chargingStations = chargingStations;
    }

    @Override
    public void update(ChargingStation chargingStation) {
        System.out.println("📟 [Zaslon polnilne postaje] Trenutno stanje polnilnic:");
        List<String> freeStations = new ArrayList<>();
        List<String> occupiedStations = new ArrayList<>();

        for (ChargingStation station : chargingStations) {
            if (station.getStatus().equals("prosto")) {
                freeStations.add(station.getName());
            } else if (station.getStatus().equals("zasedeno")) {
                occupiedStations.add(station.getName());
            }
        }

        System.out.println("✅ Proste polnilnice: " + String.join(", ", freeStations));
        System.out.println("⛔ Zasedene polnilnice: " + String.join(", ", occupiedStations));
    }
}
