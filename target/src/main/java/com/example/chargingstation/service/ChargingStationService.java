package com.example.chargingstation.service;

import com.example.chargingstation.dao.interfaces.ChargingStationDAO;
import com.example.chargingstation.iterator.*;
import com.example.chargingstation.vao.ChargingStation;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Named("chargingStationService") // Eksplicitno poimenovanje
@ApplicationScoped
public class ChargingStationService {

    @Inject
    private ChargingStationDAO chargingStationDAO;

    public enum ChargingStationStatus {
        AVAILABLE, OCCUPIED, OUT_OF_SERVICE, RESERVED
    }

    public List<ChargingStation> getActiveChargers() {
        return chargingStationDAO.getAllChargingStations().stream()
                .filter(ChargingStation::isActive)
                .toList();
    }

    public List<ChargingStation> getAllChargingStations() {
        return chargingStationDAO.getAllChargingStations();
    }

    public void addChargingStation(ChargingStation chargingStation) {
        if (chargingStation == null) {
            throw new IllegalArgumentException("Charging station cannot be null");
        }
        chargingStationDAO.addChargingStation(chargingStation);
    }

    public Optional<ChargingStation> findChargingStationById(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID cannot be null or empty");
        }
        return Optional.ofNullable(chargingStationDAO.getChargingStationById(id));
    }

    public void updateChargingStation(ChargingStation station) {
        if (station == null) {
            throw new IllegalArgumentException("Station cannot be null");
        }
        chargingStationDAO.updateChargingStation(station);
    }

    public void deleteChargingStation(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID cannot be null or empty");
        }
        chargingStationDAO.deleteChargingStation(id);
    }

    // Upravljanje stanja
    public void updateChargingStationStatus(String id, ChargingStationStatus status, String userEmail) {
        findChargingStationById(id).ifPresent(station -> {
            station.setStatus(status.toString(), userEmail);
            chargingStationDAO.updateChargingStation(station);
        });
    }

    public void startCharging(String stationId, String userEmail) {
        findChargingStationById(stationId).ifPresent(station -> {
            station.setStatus(ChargingStationStatus.OCCUPIED.toString(), userEmail);
            chargingStationDAO.updateChargingStation(station);
        });
    }

    public void stopCharging(String stationId) {
        findChargingStationById(stationId).ifPresent(station -> {
            station.setStatus(ChargingStationStatus.AVAILABLE.toString(), null);
            chargingStationDAO.updateChargingStation(station);
        });
    }

    public void deactivateChargingStation(String id) {
        findChargingStationById(id).ifPresent(station -> {
            station.setActive(false);
            station.setStatus(ChargingStationStatus.OUT_OF_SERVICE.toString(), null);
            chargingStationDAO.updateChargingStation(station);
        });
    }

    public void activateChargingStation(String id) {
        findChargingStationById(id).ifPresent(station -> {
            station.setActive(true);
            station.setStatus(ChargingStationStatus.AVAILABLE.toString(), null);
            chargingStationDAO.updateChargingStation(station);
        });
    }

    public Iterator<ChargingStation> getAllChargersAlphabeticalIterator() {
        return new AllChargersAlphabeticalIterator(chargingStationDAO.getAllChargingStations());
    }

    public Iterator<ChargingStation> getActiveChargersIterator() {
        return new ActiveChargerIterator(chargingStationDAO.getAllChargingStations());
    }

    public Iterator<ChargingStation> getChargersByRegionIterator(String region) {
        if (region == null || region.trim().isEmpty()) {
            throw new IllegalArgumentException("Region cannot be null or empty");
        }
        return new ChargerByRegionIterator(chargingStationDAO.getAllChargingStations(), region);
    }

    public Iterator<ChargingStation> getChargersBySpeedIterator(double minSpeed) {
        if (minSpeed < 0) {
            throw new IllegalArgumentException("Minimum speed cannot be negative");
        }
        return new ChargerBySpeedIterator(chargingStationDAO.getAllChargingStations(), minSpeed);
    }

    // Dodatne metode za delo z iteratorji
    public void processAllChargersAlphabetically(ChargingStationProcessor processor) {
        Iterator<ChargingStation> iterator = getAllChargersAlphabeticalIterator();
        while (iterator.hasNext()) {
            processor.process(iterator.next());
        }
    }

    public void processActiveChargers(ChargingStationProcessor processor) {
        Iterator<ChargingStation> iterator = getActiveChargersIterator();
        while (iterator.hasNext()) {
            processor.process(iterator.next());
        }
    }

    public void processChargersByRegion(String region, ChargingStationProcessor processor) {
        Iterator<ChargingStation> iterator = getChargersByRegionIterator(region);
        while (iterator.hasNext()) {
            processor.process(iterator.next());
        }
    }

    public void processChargersBySpeed(double minSpeed, ChargingStationProcessor processor) {
        Iterator<ChargingStation> iterator = getChargersBySpeedIterator(minSpeed);
        while (iterator.hasNext()) {
            processor.process(iterator.next());
        }
    }

    @FunctionalInterface
    public interface ChargingStationProcessor {
        void process(ChargingStation station);
    }
}