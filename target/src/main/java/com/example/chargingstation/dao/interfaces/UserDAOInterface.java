package com.example.chargingstation.dao.interfaces;

import com.example.chargingstation.vao.User;

import java.util.List;

public interface UserDAOInterface {
    void addUser(User user);
    User getUserById(String id);
    List<User> getAllUsers();
    void updateUser(User user);
    void deleteUser(String id);

    List<User> findUsersByName(String name);

    List<User> findUsersByEmail(String email);
}