package com.example.chargingstation.dao;

import com.example.chargingstation.dao.interfaces.UserDAOInterface;
import com.example.chargingstation.vao.User;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

@Named("userDAO")
@ApplicationScoped
public class UserDAO implements UserDAOInterface {
    private final Map<String, User> users = new ConcurrentHashMap<>();

    // Regex za validacijo e-poštnega naslova
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    @PostConstruct
    public void init() {
        // Inicializacijska logika, če je potrebna
    }

    @Override
    public void addUser(User user) {
        validateUser(user);
        if (users.containsKey(user.getId())) {
            throw new IllegalArgumentException("User with ID " + user.getId() + " already exists.");
        }
        users.put(user.getId(), user);
    }

    @Override
    public User getUserById(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty.");
        }
        User user = users.get(id);
        if (user == null) {
            throw new IllegalArgumentException("User with ID " + id + " not found.");
        }
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public void updateUser(User user) {
        validateUser(user);
        if (!users.containsKey(user.getId())) {
            throw new IllegalArgumentException("User with ID " + user.getId() + " does not exist.");
        }
        users.put(user.getId(), user);
    }

    @Override
    public void deleteUser(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty.");
        }
        if (!users.containsKey(id)) {
            throw new IllegalArgumentException("User with ID " + id + " not found.");
        }
        users.remove(id);
    }

    @Override
    public List<User> findUsersByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }
        List<User> result = new ArrayList<>();
        for (User user : users.values()) {
            if (user.getName().equalsIgnoreCase(name)) {
                result.add(user);
            }
        }
        return result;
    }

    @Override
    public List<User> findUsersByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty.");
        }
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format.");
        }
        List<User> result = new ArrayList<>();
        for (User user : users.values()) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                result.add(user);
            }
        }
        return result;
    }

    private void validateUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }
        if (user.getId() == null || user.getId().trim().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty.");
        }
        if (user.getName() == null || user.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("User name cannot be null or empty.");
        }
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("User email cannot be null or empty.");
        }
        if (!isValidEmail(user.getEmail())) {
            throw new IllegalArgumentException("Invalid email format.");
        }
    }

    private boolean isValidEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }
}