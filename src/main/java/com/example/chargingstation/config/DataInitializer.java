package com.example.chargingstation.config;


import com.example.chargingstation.service.ChargingStationService;
import com.example.chargingstation.service.UserService;
import com.example.chargingstation.vao.ChargingStation;
import com.example.chargingstation.vao.User;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@ApplicationScoped
public class DataInitializer {

    @Inject
    private ChargingStationService chargingStationService;

    @Inject
    private UserService userService;
    @PostConstruct
    public void init() {
        // Dodajanje testnih polnilnic
        ChargingStation station1 = new ChargingStation("1", "Maribor", "Mariborska regija", "Tesla", 22.0);
        ChargingStation station2 = new ChargingStation("2", "Ljubljana", "Ljubljanska regija", "Nissan", 50.0);

        chargingStationService.addChargingStation(station1);
        chargingStationService.addChargingStation(station2);

        // Dodajanje testnih uporabnikov
        User user1 = new User("1", "Janez Novak", "janez@example.com", 50.0, "Tesla");
        User user2 = new User("2", "Ana Kovač", "ana@example.com", 20.0, "Nissan");

        userService.addUser(user1);
        userService.addUser(user2);
    }
}
