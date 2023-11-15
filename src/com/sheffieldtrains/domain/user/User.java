package com.sheffieldtrains.domain.user;

import com.sheffieldtrains.domain.order.Order;

import java.util.List;

public class User {
        private Integer userID;
        private String email;
        private String password;
        private String forename;
        private String surname;
        private UserRole role;
        private Address address;
        private List<Order> orders;

    public User(Integer userID,
                String email,
                String password,
                String forename,
                String surname,
                List<Order> orders) {
        this.userID = userID;
        this.email = email;
        this.password = password;
        this.forename = forename;
        this.surname = surname;
        this.orders = orders;
    }

    public User() { }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public void setAddress(Address address) {
        this.address=address;
    }
}
