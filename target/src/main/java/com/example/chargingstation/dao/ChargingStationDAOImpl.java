package com.example.chargingstation.dao;

import com.example.chargingstation.dao.interfaces.ChargingStationDAO;
import com.example.chargingstation.iterator.ActiveChargerIterator;
import com.example.chargingstation.iterator.AllChargersAlphabeticalIterator;
import com.example.chargingstation.iterator.ChargerByRegionIterator;
import com.example.chargingstation.iterator.ChargerBySpeedIterator;
import com.example.chargingstation.vao.ChargingStation;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Iterator;
import java.util.ArrayList;

@Named("chargingStationDAO")
@ApplicationScoped
public class ChargingStationDAOImpl implements ChargingStationDAO {
    private final Map<String, ChargingStation> chargingStations = new ConcurrentHashMap<>();

    @Override
    public void addChargingStation(ChargingStation station) {
        if (station == null || station.getId() == null) {
            throw new IllegalArgumentException("Charging station and its ID cannot be null");
        }
        chargingStations.put(station.getId(), station);
    }

    @Override
    public ChargingStation getChargingStationById(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID cannot be null or empty");
        }
        return chargingStations.get(id);
    }

    @Override
    public List<ChargingStation> getAllChargingStations() {
        return new ArrayList<>(chargingStations.values());
    }

    @Override
    public void updateChargingStation(ChargingStation station) {
        if (station == null || station.getId() == null) {
            throw new IllegalArgumentException("Charging station and its ID cannot be null");
        }
        if (!chargingStations.containsKey(station.getId())) {
            throw new IllegalArgumentException("Charging station with ID " + station.getId() + " does not exist");
        }
        chargingStations.put(station.getId(), station);
    }

    @Override
    public void deleteChargingStation(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID cannot be null or empty");
        }
        chargingStations.remove(id);
    }

    // Metode za iteratorje
    public Iterator<ChargingStation> getActiveChargersIterator() {
        return new ActiveChargerIterator(getAllChargingStations());
    }

    public Iterator<ChargingStation> getChargersBySpeedIterator(double minSpeed) {
        if (minSpeed < 0) {
            throw new IllegalArgumentException("Minimum speed cannot be negative");
        }
        return new ChargerBySpeedIterator(getAllChargingStations(), minSpeed);
    }

    public Iterator<ChargingStation> getChargersByRegionIterator(String region) {
        if (region == null || region.trim().isEmpty()) {
            throw new IllegalArgumentException("Region cannot be null or empty");
        }
        return new ChargerByRegionIterator(getAllChargingStations(), region);
    }

    public Iterator<ChargingStation> getAllChargersAlphabeticalIterator() {
        return new AllChargersAlphabeticalIterator(getAllChargingStations());
    }
}