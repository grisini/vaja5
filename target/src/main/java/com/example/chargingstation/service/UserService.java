package com.example.chargingstation.service;

import com.example.chargingstation.chainofresponsibility.*;
import com.example.chargingstation.dao.UserDAO;
import com.example.chargingstation.vao.ChargingStation;
import com.example.chargingstation.vao.User;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.util.List;

@Named
@ApplicationScoped
public class UserService {

    @Inject
    private UserDAO userDAO;

    @Inject
    private AvailabilityHandler availabilityHandler;

    @Inject
    private BalanceHandler balanceHandler;

    @Inject
    private CompatibilityHandler compatibilityHandler;

    private ChargingHandler chargingHandler;

    @PostConstruct
    public void init() {

        initChargingHandler();


        if (userDAO.getAllUsers().isEmpty()) {
            User user1 = new User("1", "Janez Novak", "janez@example.com", 50.0, "Tesla");
            User user2 = new User("2", "Ana Kovač", "ana@example.com", 20.0, "Nissan");

            userDAO.addUser(user1);
            userDAO.addUser(user2);

            System.out.println("Initial users created successfully");
        }
    }

    @Produces
    @Named("chargingHandler")
    public ChargingHandler initChargingHandler() {
        availabilityHandler.setNextHandler(balanceHandler);
        balanceHandler.setNextHandler(compatibilityHandler);
        this.chargingHandler = availabilityHandler;
        return this.chargingHandler;
    }


    public void addUser(User user) {
        if (user == null || user.getName() == null || user.getName().isEmpty()) {
            throw new IllegalArgumentException("Invalid user data");
        }
        userDAO.addUser(user);
    }

    public User getUserById(String id) {
        return userDAO.getUserById(id);
    }

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public void updateUser(User user) {
        userDAO.updateUser(user);
    }

    public void deleteUser(String id) {
        userDAO.deleteUser(id);
    }
}