package com.example.chargingstation.jsf;

import com.example.chargingstation.service.ProviderService;
import com.example.chargingstation.vao.Provider;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

@Named
@RequestScoped
public class ProviderController {

    private static final Logger logger = Logger.getLogger(ProviderController.class.getName());

    @Inject
    private ProviderService providerService;

    private Provider newProvider;
    private Provider editedProvider;
    private boolean editingMode = false;

    @PostConstruct
    public void init() {
        newProvider = new Provider();
        editedProvider = new Provider();
    }

    public String addProvider() {
        try {
            // Auto-generate ID if not provided
            if (newProvider.getId() == null || newProvider.getId().isEmpty()) {
                newProvider.setId(UUID.randomUUID().toString());
            }

            providerService.addProvider(newProvider);
            newProvider = new Provider(); // Reset form
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Uspeh", "Ponudnik uspešno dodan"));
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error adding provider", e);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Napaka", e.getMessage()));
        }
        return null;
    }

    public String startEdit(Provider provider) {
        this.editedProvider = new Provider(provider.getId(), provider.getName());
        this.editedProvider.setChargingStations(provider.getChargingStations());
        this.editingMode = true;
        return null;
    }

    public String updateProvider() {
        try {
            providerService.updateProvider(editedProvider);
            this.editingMode = false;
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Uspeh", "Ponudnik uspešno posodobljen"));
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error updating provider", e);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Napaka", e.getMessage()));
        }
        return null;
    }

    public String cancelEdit() {
        this.editingMode = false;
        return null;
    }

    public String deleteProvider(String providerId) {
        try {
            providerService.deleteProvider(providerId);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Uspeh", "Ponudnik uspešno izbrisan"));
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error deleting provider", e);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Napaka", e.getMessage()));
        }
        return null;
    }

    // Getters and setters
    public Provider getNewProvider() {
        return newProvider;
    }

    public void setNewProvider(Provider newProvider) {
        this.newProvider = newProvider;
    }

    public Provider getEditedProvider() {
        return editedProvider;
    }

    public void setEditedProvider(Provider editedProvider) {
        this.editedProvider = editedProvider;
    }

    public boolean isEditingMode() {
        return editingMode;
    }

    public List<Provider> getAllProviders() {
        return providerService.getAllProviders();
    }
}