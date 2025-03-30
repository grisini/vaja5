package com.example.chargingstation.iterator;

import com.example.chargingstation.vao.ChargingStation;
import java.util.Iterator;
import java.util.List;

public class ChargerByRegionIterator implements Iterator<ChargingStation> {
    private List<ChargingStation> chargingStations;
    private int position;
    private String region;

    public ChargerByRegionIterator(List<ChargingStation> chargingStations, String region) {
        this.chargingStations = chargingStations;
        this.region = region;
        this.position = 0;
    }

    @Override
    public boolean hasNext() {
        while (position < chargingStations.size()) {
            if (chargingStations.get(position).getRegion().equalsIgnoreCase(region)) {
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