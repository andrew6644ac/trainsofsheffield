package com.sheffieldtrains.service;

import com.sheffieldtrains.domain.user.User;

import java.util.Date;

public class UserService {
    public static void registerUser(String email,
                            String password,
                            String forename,
                            String surname,
                            String houseNumber,
                            String roadName,
                            String cityName,
                            String postcode){
        //todo:
    }

    /*Get User information via his/her email.*/
    public static User getUser(Integer userId) {
        //todo:
        return null;
    }

    /*Send newly edited user info.*/
    public static void modifyUserDetails(User user){
        //todo:
    }

    /*promote a user to staff*/
    public static void promoteUser(Integer userId){
        //todo:
    }

    /*demote a user to staff*/
    public static void demoteUser(Integer userId){
        //todo:
    }

    public static void addBankDetails(Integer userId,
                                      String bankCardName,
                                      String cardHolderName,
                                      int bankCardNumber,
                                      Date cardExpiryDate,
                                      int securityCode) {
        //todo:
    }
}
