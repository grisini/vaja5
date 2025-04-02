package com.example.chargingstation.vao;

public class User {
    private String id;
    private String name;
    private String email;
    private double balance;
    private String carType;

    public User(String id, String name, String email, double balance, String carType) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID cannot be null or empty");
        }
        this.id = id;
        this.name = name;
        this.email = email;
        this.balance = balance;
        this.carType = carType;
    }

    public User() {}

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) {
        if (email != null && !email.contains("@")) {
            throw new IllegalArgumentException("Email must contain @");
        }
        this.email = email;
    }

    public double getBalance() { return balance; }
    public void setBalance(double balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative");
        }
        this.balance = balance;
    }

    public String getCarType() { return carType; }
    public void setCarType(String carType) { this.carType = carType; }

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