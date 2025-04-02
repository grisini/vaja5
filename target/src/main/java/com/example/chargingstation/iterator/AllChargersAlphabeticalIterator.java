package com.example.chargingstation.iterator;

import com.example.chargingstation.vao.ChargingStation;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class AllChargersAlphabeticalIterator implements Iterator<ChargingStation> {
    private List<ChargingStation> chargingStations;
    private int position;

    public AllChargersAlphabeticalIterator(List<ChargingStation> chargingStations) {
        this.chargingStations = chargingStations;
        Collections.sort(this.chargingStations, (c1, c2) -> c1.getName().compareToIgnoreCase(c2.getName()));
        this.position = 0;
    }

    @Override
    public boolean hasNext() {
        return position < chargingStations.size();
    }

    @Override
    public ChargingStation next() {
        return chargingStations.get(position++);
    }
}