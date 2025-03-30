package com.example.chargingstation.dao.interfaces;


import com.example.chargingstation.vao.ChargingStation;
import java.util.List;
import java.util.Optional;

public interface ChargingStationDAO {
    List<ChargingStation> getAllChargingStations();
    void addChargingStation(ChargingStation chargingStation);
    ChargingStation getChargingStationById(String id);
    void updateChargingStation(ChargingStation station);
    void deleteChargingStation(String id);
}