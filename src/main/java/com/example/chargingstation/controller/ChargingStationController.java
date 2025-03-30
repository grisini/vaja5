package com.example.chargingstation.controller;


import com.example.chargingstation.service.ChargingStationService;
import com.example.chargingstation.service.UserService;
import com.example.chargingstation.vao.ChargingStation;
import com.example.chargingstation.vao.User;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class ChargingStationController {

    @Inject
    private ChargingStationService chargingStationService;

    @Inject
    private UserService userService;

    private List<ChargingStation> stations;
    private List<User> users;

    private String newUserName;
    private String newUserEmail;

    @PostConstruct
    public void init() {
        stations = chargingStationService.getAllChargingStations();
        users = userService.getAllUsers();
    }

    public void printDataToConsole() {
        System.out.println("=== POLNILNE POSTAJE ===");
        stations.forEach(System.out::println);

        System.out.println("=== UPORABNIKI ===");
        users.forEach(System.out::println);
    }

    public void addNewUser() {
        User newUser = new User(
                String.valueOf(users.size() + 1),
                newUserName,
                newUserEmail,
                0.0,
                "Tesla" // privzeto vozilo
        );
        userService.addUser(newUser);
        users = userService.getAllUsers(); // osveži seznam
    }

    // Getterji in setterji
    public List<ChargingStation> getStations() { return stations; }
    public List<User> getUsers() { return users; }
    public String getNewUserName() { return newUserName; }
    public void setNewUserName(String newUserName) { this.newUserName = newUserName; }
    public String getNewUserEmail() { return newUserEmail; }
    public void setNewUserEmail(String newUserEmail) { this.newUserEmail = newUserEmail; }
}