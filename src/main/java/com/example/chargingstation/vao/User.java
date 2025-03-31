package com.example.chargingstation.vao;

public class User {
    private String id;
    private String name;
    private String email;
    private double balance;
    private String carType;

    public User(String id, String name, String email, double balance, String carType) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.balance = balance;
        this.carType = carType;
    }


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public double getBalance() {
        return balance;
    }

    public String getCarType() {
        return carType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", balance=" + balance +
                ", carType='" + carType + '\'' +
                '}';
    }
}