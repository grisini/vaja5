package com.example.chargingstation.vao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class Provider {
    private String id;
    private String name;
    private List<ChargingStation> chargingStations;

    public Provider(String id, String name) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID cannot be null or empty.");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }
        this.id = id;
        this.name = name;
        this.chargingStations = new ArrayList<>();
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<ChargingStation> getChargingStations() {
        return chargingStations;
    }

    // Metode za upravljanje s polnilnicami
    public void addChargingStation(ChargingStation station) {
        this.chargingStations.add(station);
    }

    public void removeChargingStation(String stationId) {
        chargingStations.removeIf(station -> station.getId().equals(stationId));
    }

    public ChargingStation findChargingStationById(String stationId) {
        return chargingStations.stream()
                .filter(station -> station.getId().equals(stationId))
                .findFirst()
                .orElse(null);
    }

    // Metode za filtriranje
    public List<ChargingStation> getActiveChargingStations() {
        return chargingStations.stream()
                .filter(ChargingStation::isActive)
                .collect(Collectors.toList());
    }

    public List<ChargingStation> getChargingStationsInRegion(String region) {
        return chargingStations.stream()
                .filter(station -> station.getRegion().equalsIgnoreCase(region))
                .collect(Collectors.toList());
    }

    // Iteratorji
    public Iterator<ChargingStation> getActiveChargingStationsIterator() {
        return chargingStations.stream()
                .filter(ChargingStation::isActive)
                .iterator();
    }

    public Iterator<ChargingStation> getChargingStationsBySpeedIterator(double minSpeed) {
        return chargingStations.stream()
                .filter(station -> station.getChargingSpeed() >= minSpeed)
                .iterator();
    }

    // Statistika
    public double getAverageChargingSpeed() {
        return chargingStations.stream()
                .mapToDouble(ChargingStation::getChargingSpeed)
                .average()
                .orElse(0.0);
    }

    public int getNumberOfActiveChargingStations() {
        return (int) chargingStations.stream()
                .filter(ChargingStation::isActive)
                .count();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Provider{")
                .append("id='").append(id).append('\'')
                .append(", name='").append(name).append('\'')
                .append(", chargingStations=[");

        for (ChargingStation station : chargingStations) {
            sb.append("\n  ").append(station);
        }

        sb.append("\n]}");
        return sb.toString();
    }
}