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
            throw new IllegalArgumentException("ID cannot be null or empty");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        this.id = id;
        this.name = name;
        this.chargingStations = new ArrayList<>();
    }

    public Provider() {
        this.chargingStations = new ArrayList<>();
    }

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<ChargingStation> getChargingStations() { return chargingStations; }
    public void setChargingStations(List<ChargingStation> chargingStations) {
        this.chargingStations = chargingStations != null ? chargingStations : new ArrayList<>();
    }

    // Charging station management
    public void addChargingStation(ChargingStation station) {
        if (station != null && !chargingStations.contains(station)) {
            this.chargingStations.add(station);
            station.setProvider(this);
        }
    }

    public boolean removeChargingStation(String stationId) {
        return chargingStations.removeIf(station -> station.getId().equals(stationId));
    }

    public ChargingStation findChargingStationById(String stationId) {
        return chargingStations.stream()
                .filter(station -> station.getId().equals(stationId))
                .findFirst()
                .orElse(null);
    }

    // Filter methods
    public List<ChargingStation> getActiveChargingStations() {
        return chargingStations.stream()
                .filter(ChargingStation::isActive)
                .collect(Collectors.toList());
    }

    public List<ChargingStation> getChargingStationsInRegion(String region) {
        if (region == null) return List.of();
        return chargingStations.stream()
                .filter(station -> region.equalsIgnoreCase(station.getRegion()))
                .collect(Collectors.toList());
    }

    // Iterators
    public Iterator<ChargingStation> getActiveChargingStationsIterator() {
        return getActiveChargingStations().iterator();
    }

    public Iterator<ChargingStation> getChargingStationsBySpeedIterator(double minSpeed) {
        return chargingStations.stream()
                .filter(station -> station.getChargingSpeed() >= minSpeed)
                .iterator();
    }

    // Statistics
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
        return "Provider{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", chargingStationsCount=" + chargingStations.size() +
                ", averageSpeed=" + getAverageChargingSpeed() +
                '}';
    }
}