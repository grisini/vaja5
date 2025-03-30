package com.example.chargingstation.observers;


import com.example.chargingstation.vao.ChargingStation;

public interface ChargingStationObserver {
    void update(ChargingStation chargingStation);
}