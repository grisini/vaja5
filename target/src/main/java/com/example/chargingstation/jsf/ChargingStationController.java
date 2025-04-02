package com.example.chargingstation.jsf;

import com.example.chargingstation.service.ChargingStationService;
import com.example.chargingstation.service.ProviderService;
import com.example.chargingstation.vao.ChargingStation;
import com.example.chargingstation.vao.Provider;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.List;
import java.util.UUID;

@Named
@RequestScoped
public class ChargingStationController {

    @Inject
    private ChargingStationService chargingStationService;

    @Inject
    private ProviderService providerService;

    private ChargingStation newStation = new ChargingStation();
    private ChargingStation editedStation = new ChargingStation();
    private boolean editingMode = false;

    @PostConstruct
    public void init() {
        newStation = new ChargingStation();
        newStation.setProvider(new Provider());
        editedStation = new ChargingStation();
        editedStation.setProvider(new Provider());
    }

    public String addStation() {
        try {
            // Auto-generate ID if not provided
            if (newStation.getId() == null || newStation.getId().isEmpty()) {
                newStation.setId(UUID.randomUUID().toString());
            }

            chargingStationService.addChargingStation(newStation);
            newStation = new ChargingStation();
            newStation.setProvider(new Provider());
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Uspeh", "Postaja uspešno dodana"));
            return null;
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Napaka", e.getMessage()));
            return null;
        }
    }

    public void startEdit(ChargingStation station) {
        this.editedStation = new ChargingStation(station.getId(), station.getName(),
                station.getRegion(), station.getProvider(),
                station.getChargingSpeed());
        this.editedStation.setActive(station.isActive());
        this.editedStation.setStatus(station.getStatus(), station.getUserEmail()); // Pass userEmail here
        this.editingMode = true;
    }



    public String updateStation() {
        try {
            chargingStationService.updateChargingStation(editedStation);
            this.editingMode = false;
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Uspeh", "Postaja uspešno posodobljena"));
            return null;
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Napaka", e.getMessage()));
            return null;
        }
    }

    public String cancelEdit() {
        this.editingMode = false;
        return null;
    }

    public String deleteStation(String stationId) {
        chargingStationService.deleteChargingStation(stationId);
        return null;
    }

    public String toggleStationStatus(String stationId) {
        chargingStationService.findChargingStationById(stationId).ifPresent(station -> {
            if (station.isActive()) {
                chargingStationService.deactivateChargingStation(stationId);
            } else {
                chargingStationService.activateChargingStation(stationId);
            }
        });
        return null;
    }

    // Getters and setters
    public ChargingStation getNewStation() { return newStation; }
    public void setNewStation(ChargingStation newStation) { this.newStation = newStation; }
    public ChargingStation getEditedStation() { return editedStation; }
    public void setEditedStation(ChargingStation editedStation) { this.editedStation = editedStation; }
    public boolean isEditingMode() { return editingMode; }
    public List<ChargingStation> getAllStations() { return chargingStationService.getAllChargingStations(); }
}