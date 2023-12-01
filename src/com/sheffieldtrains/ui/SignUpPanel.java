package com.sheffieldtrains.ui;

import com.sheffieldtrains.db.UserAlreadyExistException;
import com.sheffieldtrains.domain.user.User;
import com.sheffieldtrains.service.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUpPanel extends TopUIPanel {
    public SignUpPanel(String panelName, JPanel parentParentPanel, JFrame topFrame) {
        super(panelName, parentParentPanel, topFrame);
        layoutComponents();
    }

    private void layoutComponents() {

        JLabel email_lb_su = new JLabel("Email:");
        email_lb_su.setBounds(525, 100, 200, 30);
        email_lb_su.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        add(email_lb_su);

        //adds email text box
        JTextField email_tb_su = new JTextField(20);
        email_tb_su.setBounds(580, 100, 300, 30);
        add(email_tb_su);

        //adds password label
        JLabel password_lb_su = new JLabel("Password:");
        password_lb_su.setBounds(498, 150, 250, 30);
        password_lb_su.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        add(password_lb_su);

        //adds password text box
        JTextField password_tb_su = new JTextField(20);
        password_tb_su.setBounds(580, 150, 270, 30);
        add(password_tb_su);

        //adds forename label
        JLabel forename_lb = new JLabel("Forename:");
        forename_lb.setBounds(494, 200, 250, 30);
        forename_lb.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        add(forename_lb);

        //adds forename text box
        JTextField forename_tb = new JTextField(20);
        forename_tb.setBounds(580, 200, 270, 30);
        add(forename_tb);

        //adds surname label
        JLabel surname_lb = new JLabel("Surname:");
        surname_lb.setBounds(503, 250, 250, 30);
        surname_lb.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        add(surname_lb);

        //adds surname text box
        JTextField surname_tb = new JTextField(20);
        surname_tb.setBounds(580, 250, 270, 30);
        add(surname_tb);

        //adds surname label
        JLabel postcode_lb = new JLabel("Postcode:");
        postcode_lb.setBounds(502, 300, 250, 30);
        postcode_lb.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        add(postcode_lb);

        //adds surname text box
        JTextField postcode_tb = new JTextField(20);
        postcode_tb.setBounds(580, 300, 270, 30);
        add(postcode_tb);


        //These are for the Address table:
        //adds house number label
        JLabel houseNumber_lb = new JLabel("House Number:");
        houseNumber_lb.setBounds(453, 350, 250, 30);
        houseNumber_lb.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        add(houseNumber_lb);

        //adds house number text box
        JTextField houseNumber_tb = new JTextField(20);
        houseNumber_tb.setBounds(580, 350, 270, 30);
        add(houseNumber_tb);

        //adds road name label
        JLabel roadName_lb = new JLabel("Road Name:");
        roadName_lb.setBounds(480, 400, 250, 30);
        roadName_lb.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        add(roadName_lb);

        //adds road name text box
        JTextField roadName_tb = new JTextField(20);
        roadName_tb.setBounds(580, 400, 270, 30);
        add(roadName_tb);

        //adds City name label
        JLabel cityName_lb = new JLabel("City Name:");
        cityName_lb.setBounds(488, 450, 250, 30);
        cityName_lb.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        add(cityName_lb);

        //adds City name text box
        JTextField cityName_tb = new JTextField(20);
        cityName_tb.setBounds(580, 450, 270, 30);
        add(cityName_tb);

        //adds sign up button
        JButton signUp_bt = new JButton("Sign Up");
        signUp_bt.setBounds(525, 650, 150, 75);
        signUp_bt.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        add(signUp_bt);

        //Action listener for sign up button
        ActionListener signUp_pressed = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //gets text from text boxes
                String email_input = email_tb_su.getText();
                String password_input = password_tb_su.getText();
                String forename_input = forename_tb.getText();
                String surname_input = surname_tb.getText();
                String postcode_input = postcode_tb.getText();
                String houseNum_input = houseNumber_tb.getText();
                String roadName_input = roadName_tb.getText();
                String cityName_input = cityName_tb.getText();

                //if any text box is empty adds label to say so
                if (email_input.isBlank() || password_input.isBlank() || forename_input.isBlank()
                        || surname_input.isBlank() || postcode_input.isBlank() || houseNum_input.isBlank()
                        || roadName_input.isBlank() || cityName_input.isBlank()) {
                    JLabel incorrect1_lb = new JLabel("Please ensure you have entered valid information");
                    incorrect1_lb.setBounds(415, 550, 900, 30);
                    incorrect1_lb.setFont(new Font("Times New Roman", Font.PLAIN, 20));
                    incorrect1_lb.setForeground(Color.RED);
                    add(incorrect1_lb);
                    topFrame.repaint();
                    //if any text box contains more characters than it's supposed to it adds a label to say so
                } else {
                    if (email_input.length() > 50 || password_input.length() > 20 || forename_input.length() > 20 || surname_input.length() > 20
                            || houseNum_input.length() > 10 || postcode_input.length() > 20 || roadName_input.length() > 100 || cityName_input.length() > 100) {
                        JLabel incorrect2_lb = new JLabel("Please ensure your information does not exceed the length limit");
                        incorrect2_lb.setBounds(390, 600, 900, 30);
                        incorrect2_lb.setFont(new Font("Times New Roman", Font.PLAIN, 20));
                        incorrect2_lb.setForeground(Color.RED);
                        add(incorrect2_lb);
                        topFrame.repaint();
                    } else {
                        try {
                            //an example showing how to register a new user. If an user with the same email exists,
                            //an UserAlreadyExistException wil be thrown. Need to do something about it.
                            User user = UserService.registerUser(email_input,
                                    password_input,
                                    forename_input,
                                    surname_input,
                                    houseNum_input,
                                    roadName_input,
                                    cityName_input,
                                    postcode_input);
                            /*if (user!=null){
                                UserSession.setCurrentUser(user);
                            }*/
                            System.out.println("User registered");
                        } catch (UserAlreadyExistException ex) {
                            //todo: if the email id is already used, need to tell that such an user exists already. A pop up screen maybe?? .
                            JLabel existingUserErrorLabel = new JLabel("The email is already in use. Please use a different one");
                            add(existingUserErrorLabel);
                        /*    revalidate();
                            repaint();*/
                            topFrame.repaint();
                        }
                        CardLayout cardLayout = (CardLayout) parentParentPanel.getLayout();
                        cardLayout.show(parentParentPanel, "LoginScreen");
                    }
                }
            };
        };
        signUp_bt.addActionListener(signUp_pressed);
    }
}
