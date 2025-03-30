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
    private String status; // Status polnilnice (prosto/zasedeno)
    private String currentUserEmail;
    private List<ChargingStationObserver> observers = new ArrayList<>();

    // Glavni konstruktor
    public ChargingStation(String id, String name, String region, String provider, double chargingSpeed) {
        this.id = id;
        this.name = name;
        this.region = region;
        this.provider = new Provider(id, name);
        this.chargingSpeed = chargingSpeed;
        this.isActive = true;
        this.status = "prosto"; // Privzeto stanje
        this.currentUserEmail = null; // Na začetku ni uporabnika
    }

    // Getters in setters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRegion() { // Dodana metoda za pridobitev regije
        return region;
    }

    public Provider getProvider() {
        return provider;
    }

    public double getChargingSpeed() {
        return chargingSpeed;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status, String userEmail) {
        this.status = status;
        this.currentUserEmail = userEmail;
        notifyObservers(); // Obvesti vse opazovalce ob spremembi statusa
    }

    public String getCurrentUserEmail() {
        return currentUserEmail;
    }

    // Metode za upravljanje opazovalcev
    public void attach(ChargingStationObserver observer) {
        observers.add(observer);
    }

    public void detach(ChargingStationObserver observer) {
        observers.remove(observer);
    }

    private void notifyObservers() {
        for (ChargingStationObserver observer : observers) {
            observer.update(this);
        }
    }
}