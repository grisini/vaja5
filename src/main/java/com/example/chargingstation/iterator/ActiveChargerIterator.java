package com.example.chargingstation.iterator;

import com.example.chargingstation.vao.ChargingStation;
import java.util.Iterator;
import java.util.List;

public class ActiveChargerIterator implements Iterator<ChargingStation> {
    private List<ChargingStation> chargingStations;
    private int position;

    public ActiveChargerIterator(List<ChargingStation> chargingStations) {
        this.chargingStations = chargingStations;
        this.position = 0;
    }

    @Override
    public boolean hasNext() {
        while (position < chargingStations.size()) {
            if (chargingStations.get(position).isActive()) {
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