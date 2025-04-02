package com.example.chargingstation.service;

import com.example.chargingstation.dao.ProviderDAOImpl;
import com.example.chargingstation.vao.ChargingStation;
import com.example.chargingstation.vao.Provider;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
@Named
@ApplicationScoped
public class ProviderService {
    private final ProviderDAOImpl providerDAO = ProviderDAOImpl.getInstance();
    private final ChargingStationService chargingStationService = new ChargingStationService();

    public void addProvider(Provider provider) {
        providerDAO.addProvider(provider);
    }

    public Optional<Provider> getProviderById(String id) {
        return providerDAO.getProviderById(id);
    }

    public List<Provider> getAllProviders() {
        return providerDAO.getAllProviders();
    }

    public void updateProvider(Provider provider) {
        providerDAO.updateProvider(provider);
    }

    public void deleteProvider(String id) {
        // Najprej odstranimo vse polnilnice tega ponudnika
        getProviderById(id).ifPresent(provider -> {
            provider.getChargingStations().forEach(station ->
                    chargingStationService.deactivateChargingStation(station.getId()));
        });
        providerDAO.deleteProvider(id);
    }

    public void addChargingStationToProvider(String providerId, ChargingStation station) {
        getProviderById(providerId).ifPresent(provider -> {
            provider.addChargingStation(station);
            chargingStationService.addChargingStation(station);
            providerDAO.updateProvider(provider);
        });
    }

    public void removeChargingStationFromProvider(String providerId, String stationId) {
        getProviderById(providerId).ifPresent(provider -> {
            provider.removeChargingStation(stationId);
            chargingStationService.findChargingStationById(stationId)
                    .ifPresent(station -> chargingStationService.deactivateChargingStation(stationId));
            providerDAO.updateProvider(provider);
        });
    }

    public List<Provider> findProvidersByName(String name) {
        return providerDAO.findProvidersByName(name);
    }

    public List<ChargingStation> getActiveChargingStations(String providerId) {
        return getProviderById(providerId)
                .map(Provider::getActiveChargingStations)
                .orElse(List.of());
    }

    public double getAverageChargingSpeed(String providerId) {
        return getProviderById(providerId)
                .map(Provider::getAverageChargingSpeed)
                .orElse(0.0);
    }
}