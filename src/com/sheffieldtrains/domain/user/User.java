package com.sheffieldtrains.domain.user;

import com.sheffieldtrains.domain.order.Order;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class User {
        private Integer userID;
        private String email;
        private String password;
        private String forename;
        private String surname;

        private Set<UserRole> roles=new HashSet<>();
        private Address address;
        private List<Order> orders;

        private BankDetail bankDetail;

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

    public User(String email, String password, String forename, String surname) {
        this.email = email;
        this.password = password;
        this.forename = forename;
        this.surname = surname;
    }

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


    public void setAddress(Address address) {
        this.address=address;
    }

    public Set<UserRole> getRoles() {
        return roles;
    }

    public void addRole(UserRole userRole) {

        this.roles.add(userRole);
    }

    public Address getAddress() {
        return address;
    }

    public boolean hasRole(UserRole role) {
        return roles.contains(role);
    }

    public boolean isStaff(){
        return hasRole(UserRole.STAFF);
    }
    public void demote() {
        if (roles.contains(UserRole.STAFF)){
            roles.remove(UserRole.STAFF);
        }
    }

    public boolean isCustomer() {
        return hasRole(UserRole.CUSTOMER);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getEmail().equals(user.getEmail()) && getPassword().equals(user.getPassword()) && getForename().equals(user.getForename()) && getSurname().equals(user.getSurname()) && getRoles().equals(user.getRoles()) && getAddress().equals(user.getAddress()) && Objects.equals(getOrders(), user.getOrders());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmail(), getPassword(), getForename(), getSurname(), getRoles(), getAddress());
    }

    public BankDetail getBankDetail() {
        return bankDetail;
    }

    public void setBankDetail(BankDetail bankDetail) {
        this.bankDetail = bankDetail;
    }

    public boolean hasBankDetail(){
       return bankDetail==null;
    }
}
