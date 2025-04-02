package com.example.chargingstation.jsf;

import com.example.chargingstation.service.UserService;
import com.example.chargingstation.vao.User;
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
public class UserController {

    @Inject
    private UserService userService;

    private User newUser = new User();
    private User editedUser = new User();
    private boolean editingMode = false;

    @PostConstruct
    public void init() {
        newUser = new User();
        editedUser = new User();
    }

    public String addUser() {
        try {
            if (newUser.getId() == null || newUser.getId().isEmpty()) {
                newUser.setId(UUID.randomUUID().toString());
            }

            userService.addUser(newUser);
            newUser = new User();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Uspeh", "Uporabnik uspešno dodan"));
            return null;
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Napaka", e.getMessage()));
            return null;
        }
    }

    public void startEdit(User user) {
        this.editedUser = new User(user.getId(), user.getName(), user.getEmail(),
                user.getBalance(), user.getCarType());
        this.editingMode = true;
    }

    public String updateUser() {
        try {
            userService.updateUser(editedUser);
            this.editingMode = false;
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Uspeh", "Uporabnik uspešno posodobljen"));
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

    public String deleteUser(String userId) {
        try {
            userService.deleteUser(userId);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Uspeh", "Uporabnik uspešno izbrisan"));
            return null;
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Napaka", e.getMessage()));
            return null;
        }
    }

    // Getters and setters
    public User getNewUser() { return newUser; }
    public void setNewUser(User newUser) { this.newUser = newUser; }
    public User getEditedUser() { return editedUser; }
    public void setEditedUser(User editedUser) { this.editedUser = editedUser; }
    public boolean isEditingMode() { return editingMode; }
    public List<User> getAllUsers() { return userService.getAllUsers(); }
}