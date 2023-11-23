package com.sheffieldtrains.service;

import com.sheffieldtrains.db.UserRepository;
import com.sheffieldtrains.domain.user.BankDetail;
import com.sheffieldtrains.domain.user.User;
import com.sheffieldtrains.domain.user.UserRole;

import java.util.Date;

public class UserService {
    public static User registerUser(String email,
                            String password,
                            String forename,
                            String surname,
                            String houseNumber,
                            String roadName,
                            String cityName,
                            String postcode){
        return UserRepository.registerUser(email, password, forename, surname, houseNumber, roadName, cityName, postcode);
    }

    public static User registerUser(User user){
        return registerUser(user.getEmail(),
                user.getPassword(),
                user.getForename(),
                user.getSurname(),
                user.getAddress().getHouseNumber(),
                user.getAddress().getRoadName(),
                user.getAddress().getCityName(),
                user.getAddress().getPostcode());
    }

    /*Get User information via his/her email.*/
    public static User getUser(String email) {
        if (email==null){
            throw new UnknownUserException("Cannot identify user if no registered email is provided.");
        }
       return UserRepository.getUser(email);
    }

    /*Send newly edited user info.*/
    public static void modifyUserDetails(User user){
        UserRepository.modifyUserDetails(user);
    }

   /* public static void modifyUserAddress(User user) {
        UserRepository.modifyUserAddress(user);
    }*/

    /*promote a user to staff*/
    public static User promoteToStaff(User user){
        //todo: check if the user is even a customer, if not, make it to be a customer first.
        return addUserRole(user, UserRole.STAFF);
    }

    private static User addUserRole(User user, UserRole role) {
        if (!user.hasRole(role)) {
            user.addRole(role);
            UserRepository.addUserRole(user.getUserID(), role);
        }
        return user;
    }

    /*demote a user to staff*/
    public static User demoteStaff(User user){
        user.demote();
        UserRepository.demoteUser(user);
        return user;
    }

    public static User makeUserCustomer(User user){
        addUserRole(user, UserRole.CUSTOMER);
        return user;
    }


    public static void addOrModifyBankDetails(Integer userId,
                                              String bankCardName,
                                              String cardHolderName,
                                              String bankCardNumber,
                                              Date cardExpiryDate,
                                              int securityCode) {
        UserRepository.addOrModifyBankDetails(userId, bankCardName, cardHolderName, bankCardNumber, cardExpiryDate, securityCode);
    }

    public static BankDetail getBankDetail(Integer userId){
        if (userId==null){
            throw new UnknownUserException("User Id has to be supplied.");
        }
       return UserRepository.getBankDetail(userId);
    }

    public void deleteUser(Integer userID){
        UserRepository.deleteUser(userID);
    }
}
