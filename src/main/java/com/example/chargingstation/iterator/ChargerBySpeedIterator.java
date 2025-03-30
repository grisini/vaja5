package com.example.chargingstation.iterator;

import com.example.chargingstation.vao.ChargingStation;
import java.util.Iterator;
import java.util.List;

public class ChargerBySpeedIterator implements Iterator<ChargingStation> {
    private List<ChargingStation> chargingStations;
    private int position;
    private double minSpeed;

    public ChargerBySpeedIterator(List<ChargingStation> chargingStations, double minSpeed) {
        this.chargingStations = chargingStations;
        this.minSpeed = minSpeed;
        this.position = 0;
    }

    @Override
    public boolean hasNext() {
        while (position < chargingStations.size()) {
            if (chargingStations.get(position).getChargingSpeed() >= minSpeed) {
                return true;
            }
            position++;
        }
        return false;
    }

    @Override
    public ChargingStation next() {
        return chargingStations.get(position++);
    }
}