package com.sheffieldtrains.ui;

import com.sheffieldtrains.domain.user.User;
import com.sheffieldtrains.service.UserService;

public class UserSession {
    private static User currentUser;
    public static void  setCurrentUser(User user){
        currentUser=user;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void revalidateUser(User currentUser) {
        String email= UserSession.getCurrentUser().getEmail();
        UserSession.setCurrentUser(UserService.getUser(email));
    }
}
