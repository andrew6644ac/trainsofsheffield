package com.sheffieldtrains.ui;

import com.sheffieldtrains.domain.user.User;

public class AccountController {
    private AccountPanel view;

    public AccountController(AccountPanel view) {
        this.view = view;
    }

    public void setStaffButtonVisibility(){
        User user=UserSession.getCurrentUser();
        boolean shouldBeVisible=false;
        if (UserSession.getCurrentUser()!=null &&(user.isStaff()||user.isManager())) {
            shouldBeVisible=true;
        }
        view.getStaffButton().setVisible(shouldBeVisible);
    }
}
