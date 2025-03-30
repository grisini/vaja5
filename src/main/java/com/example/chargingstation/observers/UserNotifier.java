package com.example.chargingstation.observers;

import com.example.chargingstation.vao.ChargingStation;

public class UserNotifier implements ChargingStationObserver {
    @Override
    public void update(ChargingStation chargingStation) {
        if (chargingStation.getStatus().equals("zasedeno")) {
            System.out.println("📩 [EMAIL] Od: noreply@chargingstations.com");
            System.out.println("📩 Za: " + chargingStation.getCurrentUserEmail());
            System.out.println("📩 Zadeva: Polnjenje se je začelo! ⚡");
            System.out.println("Pozdravljeni,\n\nvaše polnjenje na polnilnici **" + chargingStation.getName() + "** se je uspešno začelo.\n🚗 Moč polnjenja: " + chargingStation.getChargingSpeed() + " kW\n\nLep pozdrav,\n[Ponudnik]");
            System.out.println("-------------------------------------------------");
        } else if (chargingStation.getStatus().equals("prosto")) {
            System.out.println("📩 [EMAIL] Od: noreply@chargingstations.com");
            System.out.println("📩 Za: " + chargingStation.getCurrentUserEmail());
            System.out.println("📩 Zadeva: Polnjenje končano! ✅");
            System.out.println("Pozdravljeni,\n\nvaše polnjenje na polnilnici **" + chargingStation.getName() + "** je končano.\n🔌\n\nLep pozdrav,\n[Ponudnik]");
            System.out.println("-------------------------------------------------");
        }
    }
}