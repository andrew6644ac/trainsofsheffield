package com.sheffieldtrains.ui;

import com.sheffieldtrains.domain.user.User;
import com.sheffieldtrains.service.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPanel extends TopUIPanel {


   public LoginPanel(String panelName, JPanel parentParentPanel, JFrame topFrame) {
       super( panelName,  parentParentPanel,  topFrame);

       //adds a title in the centre at the top in bold font size 40
       JLabel title = new JLabel("Trains Of Sheffield");
       title.setBounds(450, 25, 600, 30);
       title.setFont(new Font("Times New Roman", Font.BOLD, 40));
       add(title);

       //adds email label
       JLabel email_lb = new JLabel("Please enter your email:");
       email_lb.setBounds(405, 100, 200, 30);
       email_lb.setFont(new Font("Times New Roman", Font.PLAIN, 20));
       add(email_lb);

       //adds email text box
       JTextField email_tb = new JTextField(20);
       email_tb.setBounds(600, 100, 300, 30);
       add(email_tb);

       //adds password label
       JLabel password_lb = new JLabel("Please enter your password:");
       password_lb.setBounds(405, 150, 250, 30);
       password_lb.setFont(new Font("Times New Roman", Font.PLAIN, 20));
       add(password_lb);

       //adds password text box
       JTextField password_tb = new JTextField(20);
       password_tb.setBounds(630, 150, 270, 30);
       add(password_tb);

       //adds login button
       JButton login_bt = new JButton("Login");
       login_bt.setBounds(425, 650, 150, 75);
       login_bt.setFont(new Font("Times New Roman", Font.PLAIN, 25));
       add(login_bt);

       //adds sign up button
       JButton signup_bt = new JButton("Sign up");
       signup_bt.setBounds(625, 650, 150, 75);
       signup_bt.setFont(new Font("Times New Roman", Font.PLAIN, 25));
       add(signup_bt);

       //Action listener for login button
       ActionListener login_pressed = new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               //checks email and password when button is pressed and changes screen if correct
               String email = email_tb.getText();
               String password = password_tb.getText();
               if (checkIfInputBlank(new String[] {email, password})){
                   JLabel incorrect_lb = new JLabel("Email or Password is incorrect.");
                   incorrect_lb.setBounds(450, 400, 400, 30);
                   incorrect_lb.setFont(new Font("Times New Roman", Font.BOLD, 25));
                   incorrect_lb.setForeground(Color.RED);
                   add(incorrect_lb);
                   topFrame.repaint();
               };
               User user=UserService.getUser(email);
               if (user!=null && user.getPassword().equals(password)){
                   //login successful.
                   UserSession.setCurrentUser(user);
                   moveToScreen("MenuScreen");
               }
             /*  //Need to check email exists in DB if exists get password and check if matches with entered password.
               if ((email_input_lg.equals("email") || email_input_lg.equals("staff") || email_input_lg.equals("manager")) && password_input_lg.equals("password")) {
                   CardLayout cardLayout = (CardLayout) parentParentPanel.getLayout();
                   cardLayout.show(parentParentPanel, "MenuScreen");
//                loggedIn.email = email_input_lg;

               } */
               else {
                   //Creates label to say incorrect input
                   JLabel incorrect_lb = new JLabel("Email or Password is incorrect.");
                   incorrect_lb.setBounds(450, 400, 400, 30);
                   incorrect_lb.setFont(new Font("Times New Roman", Font.BOLD, 25));
                   incorrect_lb.setForeground(Color.RED);
                   add(incorrect_lb);
                   topFrame.repaint();
               }
           }
       };

       //Action listener for signup button
       ActionListener signup_pressed = new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               //changes screen to signup Screen
               /*CardLayout cardLayout = (CardLayout) parentParentPanel.getLayout();
               cardLayout.show(parentParentPanel, "SignupScreen");*/
               moveToScreen("SignupScreen");
           }
       };

       login_bt.addActionListener(login_pressed);
       signup_bt.addActionListener(signup_pressed);
   }

    private void moveToScreen(String screenName) {
        CardLayout cardLayout = (CardLayout) parentParentPanel.getLayout();
        cardLayout.show(parentParentPanel, screenName);
    }

    private boolean checkIfInputBlank(String[] strings) {
       for(String str: strings){
           if (str==null||str.isBlank()) {
               return true;
           }
       }
       return false;
    }
}
