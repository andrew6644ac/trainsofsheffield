package com.sheffieldtrains.ui;

import com.sheffieldtrains.domain.user.Address;
import com.sheffieldtrains.domain.user.User;
import com.sheffieldtrains.service.EmailInUseException;
import com.sheffieldtrains.service.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class EditDetailPanel extends TopUIPanel {
    private HashMap<String, JTextField> textFieldMap=new HashMap<>();
    public EditDetailPanel(String panelName, JPanel parentParentPanel, JFrame topFrame) {
//        super(new BorderLayout());
        super(panelName, parentParentPanel, topFrame);
        this.panelName = panelName;
        this.parentParentPanel = parentParentPanel;
        this.topFrame = topFrame;
        layoutComponents();
    }

    private void layoutComponents() {

        //adds a title in the centre at the top in bold font size 40
        JLabel title_ed = new JLabel("Edit User Details");
        title_ed.setBounds(500, 25, 600, 45);
        title_ed.setFont(new Font("Times New Roman", Font.BOLD, 40));
        add(title_ed);

        //adds back button
        JButton back_bt5 = new JButton("Back");
        back_bt5.setBounds(1025, 10, 150, 75);
        back_bt5.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        add(back_bt5);

        //Action listener for back button
        ActionListener back_pressed5 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //changes screen to Account Screen

                CardLayout cardLayout = (CardLayout) parentParentPanel.getLayout();
                cardLayout.show(parentParentPanel, "AccountScreen");
            }
        };

        //adds addInfo button
        JButton addInfo_bt = new JButton("Add Info");
        addInfo_bt.setBounds(525, 550, 150, 75);
        addInfo_bt.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        //todo: no use for the add info button. need to remove.
        add(addInfo_bt);


        //adds email label
        JLabel email_lb_ed = new JLabel("Email:");
        email_lb_ed.setBounds(525, 100, 200, 30);
        email_lb_ed.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        add(email_lb_ed);

        //adds email text box
        JTextField email_tb_ed = new JTextField(20);
        email_tb_ed.setBounds(580, 100, 300, 30);
        add(email_tb_ed);
        textFieldMap.put("getEmail", email_tb_ed );

        //adds password label
        JLabel password_lb_ed = new JLabel("Password:");
        password_lb_ed.setBounds(498, 150, 250, 30);
        password_lb_ed.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        add(password_lb_ed);

        //adds password text box
        JTextField password_tb_ed = new JTextField(20);
        password_tb_ed.setBounds(580, 150, 270, 30);
        add(password_tb_ed);
        textFieldMap.put("getPassword", password_tb_ed );

        //adds forename label
        JLabel forename_lb_ed = new JLabel("Forename:");
        forename_lb_ed.setBounds(494, 200, 250, 30);
        forename_lb_ed.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        add(forename_lb_ed);

        //adds forename text box
        JTextField forename_tb_ed = new JTextField(20);
        forename_tb_ed.setBounds(580, 200, 270, 30);
        add(forename_tb_ed);
        textFieldMap.put("getForename", forename_tb_ed );

        //adds surname label
        JLabel surname_lb_ed = new JLabel("Surname:");
        surname_lb_ed.setBounds(503, 250, 250, 30);
        surname_lb_ed.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        add(surname_lb_ed);

        //adds surname text box
        JTextField surname_tb_ed = new JTextField(20);
        surname_tb_ed.setBounds(580, 250, 270, 30);
        add(surname_tb_ed);
        textFieldMap.put("getSurname", surname_tb_ed );

        //adds surname label
        JLabel postcode_lb_ed = new JLabel("Postcode:");
        postcode_lb_ed.setBounds(502, 300, 250, 30);
        postcode_lb_ed.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        add(postcode_lb_ed);

        //adds surname text box
        JTextField postcode_tb_ed = new JTextField(20);
        postcode_tb_ed.setBounds(580, 300, 270, 30);
        add(postcode_tb_ed);
        textFieldMap.put("getPostcode", postcode_tb_ed );

        //These are for the Address table:
        //adds house number label
        JLabel houseNumber_lb_ed = new JLabel("House Number:");
        houseNumber_lb_ed.setBounds(453, 350, 250, 30);
        houseNumber_lb_ed.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        add(houseNumber_lb_ed);

        //adds house number text box
        JTextField houseNumber_tb_ed = new JTextField(20);
        houseNumber_tb_ed.setBounds(580, 350, 270, 30);
        add(houseNumber_tb_ed);
        textFieldMap.put("getHouseNumber", houseNumber_tb_ed );

        //adds road name label
        JLabel roadName_lb_ed = new JLabel("Road Name:");
        roadName_lb_ed.setBounds(480, 400, 250, 30);
        roadName_lb_ed.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        add(roadName_lb_ed);

        //adds road name text box
        JTextField roadName_tb_ed = new JTextField(20);
        roadName_tb_ed.setBounds(580, 400, 270, 30);
        add(roadName_tb_ed);
        textFieldMap.put("getRoadName", roadName_tb_ed );

        //adds City name label
        JLabel cityName_lb_ed = new JLabel("City Name:");
        cityName_lb_ed.setBounds(488, 450, 250, 30);
        cityName_lb_ed.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        add(cityName_lb_ed);

        //adds City name text box
        JTextField cityName_tb_ed = new JTextField(20);
        cityName_tb_ed.setBounds(580, 450, 270, 30);
        add(cityName_tb_ed);
        textFieldMap.put("getCityName", cityName_tb_ed );

        //adds confirm button
        JButton confirm_bt_ed = new JButton("Confirm");
        confirm_bt_ed.setBounds(525, 650, 150, 75);
        confirm_bt_ed.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        add(confirm_bt_ed);

        ActionListener addInfo_pressed = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //todo Adds existing info to text boxes
                //todo gets user details where email is loggedIn.email
//                email_tb_ed.setText(loggedIn.email);
                //todo somehow clear on logout
            }
        };

        //Action listener for edit details button
        ActionListener confirm_pressed = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               /* //gets text from text boxes
                String email_input_ed = email_tb_ed.getText();
                String password_input_ed = password_tb_ed.getText();
                String forename_input_ed = forename_tb_ed.getText();
                String surname_input_ed = surname_tb_ed.getText();
                String postcode_input_ed = postcode_tb_ed.getText();
                String houseNum_input_ed = houseNumber_tb_ed.getText();
                String roadName_input_ed = roadName_tb_ed.getText();
                String cityName_input_ed = cityName_tb_ed.getText();

                //converts strings to arrays and finds lengths
                char[] email_array = email_input_ed.toCharArray();
                int email_length = email_array.length;
                char[] password_array = password_input_ed.toCharArray();
                int password_length = password_array.length;
                char[] forename_array = forename_input_ed.toCharArray();
                int forename_length = forename_array.length;
                char[] surname_array = surname_input_ed.toCharArray();
                int surname_length = surname_array.length;
                char[] postcode_array = postcode_input_ed.toCharArray();
                int postcode_length = postcode_array.length;
                char[] houseNum_array = houseNum_input_ed.toCharArray();
                int houseNum_length = houseNum_array.length;
                char[] roadName_array = roadName_input_ed.toCharArray();
                int roadName_length = roadName_array.length;
                char[] cityName_array = cityName_input_ed.toCharArray();
                int cityName_length = cityName_array.length;*/

                //if any text box is empty adds label to say so
               /* if (email_input_ed.isBlank() || password_input_ed.isBlank() || forename_input_ed.isBlank()
                        || surname_input_ed.isBlank() || postcode_input_ed.isBlank() || houseNum_input_ed.isBlank()
                        || roadName_input_ed.isBlank() || cityName_input_ed.isBlank()) {
                    JLabel incorrect1_lb_ed = new JLabel("Please ensure you fill all details");
                    incorrect1_lb_ed.setBounds(415, 550, 900, 30);
                    incorrect1_lb_ed.setFont(new Font("Times New Roman", Font.PLAIN, 20));
                    incorrect1_lb_ed.setForeground(Color.RED);
                    add(incorrect1_lb_ed);
                    topFrame.repaint();
                    //if any text box contains more characters than it's supposed to it adds a label to say so
                } else if (email_length > 50 || password_length > 20 || forename_length > 20 || surname_length > 20
                        || houseNum_length > 10 || postcode_length > 20 || roadName_length > 100 || cityName_length > 100) {
                    JLabel incorrect2_lb_ed = new JLabel("Please ensure your information does not exceed the length limit");
                    incorrect2_lb_ed.setBounds(390, 600, 900, 30);
                    incorrect2_lb_ed.setFont(new Font("Times New Roman", Font.PLAIN, 20));
                    incorrect2_lb_ed.setForeground(Color.RED);
                    add(incorrect2_lb_ed);
                    topFrame.repaint();*/
                String email = textFieldMap.get("getEmail").getText();
                String password = textFieldMap.get("getPassword").getText();
                String forename = textFieldMap.get("getForename").getText();
                String surname = textFieldMap.get("getSurname").getText();
                String houseNumber = textFieldMap.get("getHouseNumber").getText();
                String roadName = textFieldMap.get("getRoadName").getText();
                String cityName = textFieldMap.get("getCityName").getText();
                String postcode = textFieldMap.get("getPostcode").getText();
                boolean shouldProgress=true;
                if (!inputValid(new String[]{email, password, forename, surname, houseNumber, roadName, cityName, postcode})) {
                    JOptionPane.showMessageDialog(
                            null, // Parent component (null for centering on the screen)
                            "Please ensure all fields are filled", // Message
                            "User Details Editing Error", // Title
                            JOptionPane.ERROR_MESSAGE);
                    shouldProgress=false;
                }
                if (shouldProgress){
                        try {
                            User user = new User(UserSession.getCurrentUser().getUserId(),
                                    email,
                                    password,
                                    forename,
                                    surname);
                            Address address = new Address(houseNumber, roadName, cityName, postcode);
                            user.setAddress(address);
                            UserService.modifyUserDetails(user);
                        }
                        catch (EmailInUseException ex){
                            JOptionPane.showMessageDialog(
                                    null, // Parent component (null for centering on the screen)
                                    "That email has been used by another user. Please select a different one", // Message
                                    "User Details Editing Error", // Title
                                    JOptionPane.ERROR_MESSAGE);
                            shouldProgress=false;
                        }
                        if (shouldProgress) {
                            CardLayout cardLayout = (CardLayout) parentParentPanel.getLayout();
                            cardLayout.show(parentParentPanel, "AccountScreen");
                        }
                }
            }
        };

        back_bt5.addActionListener(back_pressed5);
        confirm_bt_ed.addActionListener(confirm_pressed);
        addInfo_bt.addActionListener(addInfo_pressed);
    }


    public void populateDetailFields(){
        User user=UserSession.getCurrentUser();
        Address address=user.getAddress();
        if (user==null||address==null) {
            throw new RuntimeException("user is null or address is null");
        }
        textFieldMap.get("getEmail").setText(user.getEmail());
        textFieldMap.get("getPassword").setText(user.getPassword());
        textFieldMap.get("getForename").setText(user.getForename());
        textFieldMap.get("getSurname").setText(user.getSurname());
        textFieldMap.get("getHouseNumber").setText(address.getHouseNumber());
        textFieldMap.get("getRoadName").setText(address.getRoadName());
        textFieldMap.get("getCityName").setText(address.getCityName());
        textFieldMap.get("getPostcode").setText(address.getPostcode());
    }

    private boolean inputValid(String[] strings) {
        for (String input : strings) {
            if (input == null || input.isBlank()) {
                return false;
            }
        }
        return true;
    }
}
