package com.example.chargingstation.vao;

import com.example.chargingstation.observers.ChargingStationObserver;
import java.util.ArrayList;
import java.util.List;

public class ChargingStation {
    private String id;
    private String name;
    private String region;
    private Provider provider;
    private double chargingSpeed;
    private boolean isActive;
    private String status; // AVAILABLE, OCCUPIED, OUT_OF_SERVICE, RESERVED
    private String currentUserEmail;
    private List<ChargingStationObserver> observers = new ArrayList<>();

    // Main constructor
    public ChargingStation(String id, String name, String region, Provider provider, double chargingSpeed) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID cannot be null or empty");
        }
        this.id = id;
        this.name = name;
        this.region = region;
        this.provider = provider;
        this.chargingSpeed = chargingSpeed;
        this.isActive = true;
        this.status = "AVAILABLE";
        this.currentUserEmail = null;
    }

    // No-arg constructor for JSF/CDI
    public ChargingStation() {}

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public Provider getProvider() { return provider; }
    public void setProvider(Provider provider) { this.provider = provider; }

    public double getChargingSpeed() { return chargingSpeed; }
    public void setChargingSpeed(double chargingSpeed) {
        if (chargingSpeed < 0) {
            throw new IllegalArgumentException("Charging speed cannot be negative");
        }
        this.chargingSpeed = chargingSpeed;
    }

    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { isActive = active; }

    public String getStatus() { return status; }
    public void setStatus(String status, String userEmail) {
        if (!List.of("AVAILABLE", "OCCUPIED", "OUT_OF_SERVICE", "RESERVED").contains(status)) {
            throw new IllegalArgumentException("Invalid status value");
        }
        this.status = status;
        this.currentUserEmail = userEmail;
        notifyObservers();
    }

    public String getCurrentUserEmail() { return currentUserEmail; }
    public void setCurrentUserEmail(String currentUserEmail) { this.currentUserEmail = currentUserEmail; }

    // Observer pattern methods
    public void attach(ChargingStationObserver observer) {
        if (observer != null) {
            observers.add(observer);
        }
    }

    public void detach(ChargingStationObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers() {
        observers.forEach(observer -> observer.update(this));
    }

    @Override
    public String toString() {
        return "ChargingStation{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", region='" + region + '\'' +
                ", provider=" + provider.getName() +
                ", chargingSpeed=" + chargingSpeed +
                ", isActive=" + isActive +
                ", status='" + status + '\'' +
                ", currentUserEmail='" + currentUserEmail + '\'' +
                '}';
    }

    public String getUserEmail() {return currentUserEmail;}
    }
