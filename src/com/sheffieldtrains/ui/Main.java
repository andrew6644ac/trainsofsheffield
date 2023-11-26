package com.sheffieldtrains.ui;

//import statements
import com.sheffieldtrains.db.UserAlreadyExistException;
import com.sheffieldtrains.domain.user.User;
import com.sheffieldtrains.service.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Main {
    public static void main(String[] args) {

        LoggedIn loggedIn = new LoggedIn();

        //Creates window with name in title bar and sets size of window
        JFrame frame = new JFrame("Trains Of Sheffield");
        frame.setSize(1200, 800);

        //creates a cardholder panel to hold all screens within
        JPanel cardHolder = new JPanel(new CardLayout());



        //creates a login page panel
        JPanel login_panel = new JPanel(null);

        //adds a title in the centre at the top in bold font size 40
        JLabel title = new JLabel("Trains Of Sheffield");
        title.setBounds(450, 25, 600, 30);
        title.setFont(new Font("Times New Roman", Font.BOLD, 40));
        login_panel.add(title);

        //adds email label
        JLabel email_lb = new JLabel("Please enter your email:");
        email_lb.setBounds(405, 100, 200, 30);
        email_lb.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        login_panel.add(email_lb);

        //adds email text box
        JTextField email_tb = new JTextField(20);
        email_tb.setBounds(600, 100, 300, 30);
        login_panel.add(email_tb);

        //adds password label
        JLabel password_lb = new JLabel("Please enter your password:");
        password_lb.setBounds(405, 150, 250, 30);
        password_lb.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        login_panel.add(password_lb);

        //adds password text box
        JTextField password_tb = new JTextField(20);
        password_tb.setBounds(630, 150, 270, 30);
        login_panel.add(password_tb);

        //adds login button
        JButton login_bt = new JButton("Login");
        login_bt.setBounds(425, 650, 150, 75);
        login_bt.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        login_panel.add(login_bt);

        //adds sign up button
        JButton signup_bt = new JButton("Sign up");
        signup_bt.setBounds(625, 650, 150, 75);
        signup_bt.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        login_panel.add(signup_bt);

        //Action listener for login button
        ActionListener login_pressed = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //checks email and password when button is pressed and changes screen if correct
                String email_input_lg = email_tb.getText();
                String password_input_lg = password_tb.getText();
                //Need to check email exists in DB if exists get password and check if matches with entered password.
                if ((email_input_lg.equals("email") || email_input_lg.equals("staff")  || email_input_lg.equals("manager")) && password_input_lg.equals("password")) {
                    CardLayout cardLayout = (CardLayout) cardHolder.getLayout();
                    cardLayout.show(cardHolder, "MenuScreen");
                    loggedIn.email = email_input_lg;
                } else {
                    //Creates label to say incorrect input
                    JLabel incorrect_lb = new JLabel("Email or Password is incorrect.");
                    incorrect_lb.setBounds(450, 400, 400, 30);
                    incorrect_lb.setFont(new Font("Times New Roman", Font.BOLD, 25));
                    incorrect_lb.setForeground(Color.RED);
                    login_panel.add(incorrect_lb);
                    frame.repaint();
                }

            }
        };

        //Action listener for signup button
        ActionListener signup_pressed = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //changes screen to signup Screen
                CardLayout cardLayout = (CardLayout) cardHolder.getLayout();
                cardLayout.show(cardHolder, "SignupScreen");
            }
        };






        //Creates a main menu panel
        JPanel menu_panel = new JPanel(null);

        //adds logout button
        JButton logout_bt1 = new JButton("Logout");
        logout_bt1.setBounds(1025, 10, 150, 75);
        logout_bt1.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        menu_panel.add(logout_bt1);

        //Action listener for logout button
        ActionListener logout_pressed1 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //changes screen to Login Screen
                CardLayout cardLayout = (CardLayout) cardHolder.getLayout();
                cardLayout.show(cardHolder, "LoginScreen");
            }
        };

        //adds account button
        JButton account_bt = new JButton("Account");
        account_bt.setBounds(25, 10, 150, 75);
        account_bt.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        menu_panel.add(account_bt);

        //Action listener for account button
        ActionListener account_pressed = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //changes screen to Account Screen
                CardLayout cardLayout = (CardLayout) cardHolder.getLayout();
                cardLayout.show(cardHolder, "AccountScreen");
            }
        };

        //adds view basket button
        JButton viewBasket_bt = new JButton("View Basket");
        viewBasket_bt.setBounds(480, 680, 250, 75);
        viewBasket_bt.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        menu_panel.add(viewBasket_bt);

        //Action listener for account button
        ActionListener viewBasket_pressed = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //changes screen to basket Screen
                CardLayout cardLayout = (CardLayout) cardHolder.getLayout();
                cardLayout.show(cardHolder, "BasketScreen");
            }
        };


        //todo
        //adds temp label
        JLabel temp_lb = new JLabel("selected item is: Locomotive");
        temp_lb.setBounds(405, 150, 250, 30);
        temp_lb.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        menu_panel.add(temp_lb);

        String[] stock_types = {"Locomotive", "Rolling Stock", "Controller", "Track", "Track Pack", "Train Set"};
        JComboBox<String> stock_sort_CB = new JComboBox<>(stock_types);

        stock_sort_CB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the selected item
                String stock_selected = (String) stock_sort_CB.getSelectedItem();
                temp_lb.setText("selected item is: " + stock_selected);
                menu_panel.repaint();
            }
        });

        stock_sort_CB.setBounds(900, 100, 200, 100);
        menu_panel.add(stock_sort_CB);
        menu_panel.repaint();

        //Add buttons for staff and moderator if logged in as so.
        //Add scroll pane
        //can update labels not delete and replace






        //Creates sign up panel
        JPanel signup_panel = new JPanel(null);

        //adds a title in the centre at the top in bold font size 40
        JLabel title_su = new JLabel("Sign Up");
        title_su.setBounds(540, 25, 600, 45);
        title_su.setFont(new Font("Times New Roman", Font.BOLD, 40));
        signup_panel.add(title_su);

        //adds logout button
        JButton logout_bt2 = new JButton("Back");
        logout_bt2.setBounds(1025, 10, 150, 75);
        logout_bt2.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        signup_panel.add(logout_bt2);

        //Action listener for logout button
        ActionListener logout_pressed2 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //changes screen to Login Screen
                CardLayout cardLayout = (CardLayout) cardHolder.getLayout();
                cardLayout.show(cardHolder, "LoginScreen");
            }
        };

        //adds email label
        JLabel email_lb_su = new JLabel("Email:");
        email_lb_su.setBounds(525, 100, 200, 30);
        email_lb_su.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        signup_panel.add(email_lb_su);

        //adds email text box
        JTextField email_tb_su = new JTextField(20);
        email_tb_su.setBounds(580, 100, 300, 30);
        signup_panel.add(email_tb_su);

        //adds password label
        JLabel password_lb_su = new JLabel("Password:");
        password_lb_su.setBounds(498, 150, 250, 30);
        password_lb_su.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        signup_panel.add(password_lb_su);

        //adds password text box
        JTextField password_tb_su = new JTextField(20);
        password_tb_su.setBounds(580, 150, 270, 30);
        signup_panel.add(password_tb_su);

        //adds forename label
        JLabel forename_lb = new JLabel("Forename:");
        forename_lb.setBounds(494, 200, 250, 30);
        forename_lb.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        signup_panel.add(forename_lb);

        //adds forename text box
        JTextField forename_tb = new JTextField(20);
        forename_tb.setBounds(580, 200, 270, 30);
        signup_panel.add(forename_tb);

        //adds surname label
        JLabel surname_lb = new JLabel("Surname:");
        surname_lb.setBounds(503, 250, 250, 30);
        surname_lb.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        signup_panel.add(surname_lb);

        //adds surname text box
        JTextField surname_tb = new JTextField(20);
        surname_tb.setBounds(580, 250, 270, 30);
        signup_panel.add(surname_tb);

        //adds surname label
        JLabel postcode_lb = new JLabel("Postcode:");
        postcode_lb.setBounds(502, 300, 250, 30);
        postcode_lb.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        signup_panel.add(postcode_lb);

        //adds surname text box
        JTextField postcode_tb = new JTextField(20);
        postcode_tb.setBounds(580, 300, 270, 30);
        signup_panel.add(postcode_tb);


        //These are for the Address table:
        //adds house number label
        JLabel houseNumber_lb = new JLabel("House Number:");
        houseNumber_lb.setBounds(453, 350, 250, 30);
        houseNumber_lb.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        signup_panel.add(houseNumber_lb);

        //adds house number text box
        JTextField houseNumber_tb = new JTextField(20);
        houseNumber_tb.setBounds(580, 350, 270, 30);
        signup_panel.add(houseNumber_tb);

        //adds road name label
        JLabel roadName_lb = new JLabel("Road Name:");
        roadName_lb.setBounds(480, 400, 250, 30);
        roadName_lb.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        signup_panel.add(roadName_lb);

        //adds road name text box
        JTextField roadName_tb = new JTextField(20);
        roadName_tb.setBounds(580, 400, 270, 30);
        signup_panel.add(roadName_tb);

        //adds City name label
        JLabel cityName_lb = new JLabel("City Name:");
        cityName_lb.setBounds(488, 450, 250, 30);
        cityName_lb.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        signup_panel.add(cityName_lb);

        //adds City name text box
        JTextField cityName_tb = new JTextField(20);
        cityName_tb.setBounds(580, 450, 270, 30);
        signup_panel.add(cityName_tb);

        //adds sign up button
        JButton signUp_bt = new JButton("Sign Up");
        signUp_bt.setBounds(525, 650, 150, 75);
        signUp_bt.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        signup_panel.add(signUp_bt);

        //Action listener for sign up button
        ActionListener signUp_pressed = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //gets text from text boxes
                String email_input_su = email_tb_su.getText();
                String password_input_su = password_tb_su.getText();
                String forename_input = forename_tb.getText();
                String surname_input = surname_tb.getText();
                String postcode_input = postcode_tb.getText();
                String houseNum_input = houseNumber_tb.getText();
                String roadName_input = roadName_tb.getText();
                String cityName_input = cityName_tb.getText();

                //converts strings to arrays and finds lengths
                char[] email_array = email_input_su.toCharArray();
                int email_length = email_array.length;
                char[] password_array = password_input_su.toCharArray();
                int password_length = password_array.length;
                char[] forename_array = forename_input.toCharArray();
                int forename_length = forename_array.length;
                char[] surname_array = surname_input.toCharArray();
                int surname_length = surname_array.length;
                char[] postcode_array = postcode_input.toCharArray();
                int postcode_length = postcode_array.length;
                char[] houseNum_array = houseNum_input.toCharArray();
                int houseNum_length = houseNum_array.length;
                char[] roadName_array = roadName_input.toCharArray();
                int roadName_length = roadName_array.length;
                char[] cityName_array = cityName_input.toCharArray();
                int cityName_length = cityName_array.length;

                //if any text box is empty adds label to say so
                if (email_input_su.isBlank() || password_input_su.isBlank() || forename_input.isBlank()
                        || surname_input.isBlank() || postcode_input.isBlank() || houseNum_input.isBlank()
                        || roadName_input.isBlank() || cityName_input.isBlank()) {
                    JLabel incorrect1_lb = new JLabel("Please ensure you have entered valid information");
                    incorrect1_lb.setBounds(415, 550, 900, 30);
                    incorrect1_lb.setFont(new Font("Times New Roman", Font.PLAIN, 20));
                    incorrect1_lb.setForeground(Color.RED);
                    signup_panel.add(incorrect1_lb);
                    frame.repaint();
                //if any text box contains more characters than it's supposed to it adds a label to say so
                } else if (email_length > 50 || password_length > 20 || forename_length > 20 || surname_length > 20
                        || houseNum_length > 10 || postcode_length > 20 || roadName_length > 100 || cityName_length > 100) {
                    JLabel incorrect2_lb = new JLabel("Please ensure your information does not exceed the length limit");
                    incorrect2_lb.setBounds(390, 600, 900, 30);
                    incorrect2_lb.setFont(new Font("Times New Roman", Font.PLAIN, 20));
                    incorrect2_lb.setForeground(Color.RED);
                    signup_panel.add(incorrect2_lb);
                    frame.repaint();
                    //todo check if info is already existing

                } else {
                    /*try {
                        //an example showing how to register a new user. If an user with the same email exists,
                        //an UserAlreadyExistException wil be thrown. Need to do something about it.
                        User user = UserService.registerUser(email_input_su,
                                password_input_su,
                                forename_input,
                                surname_input,
                                houseNum_input,
                                roadName_input,
                                cityName_input,
                                postcode_input);
                        System.out.println("User registered");
                    }
                    catch(UserAlreadyExistException ex){
                        //todo: if the email id is already used, need to tell that such an user exists already. A pop up screen maybe?? .
                        System.out.println("User already exists");
                    }*/
                    CardLayout cardLayout = (CardLayout) cardHolder.getLayout();
                    cardLayout.show(cardHolder, "LoginScreen");
                }
            }
        };







        //Creates account panel
        JPanel account_panel = new JPanel(null);

        //adds a title in the centre at the top in bold font size 40
        JLabel title_ac = new JLabel("Account");
        title_ac.setBounds(540, 25, 600, 45);
        title_ac.setFont(new Font("Times New Roman", Font.BOLD, 40));
        account_panel.add(title_ac);

        //adds back button
        JButton back_bt2 = new JButton("Back");
        back_bt2.setBounds(1025, 10, 150, 75);
        back_bt2.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        account_panel.add(back_bt2);

        //Action listener for back button
        ActionListener back_pressed2 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //changes screen to Menu Screen
                CardLayout cardLayout = (CardLayout) cardHolder.getLayout();
                cardLayout.show(cardHolder, "MenuScreen");
            }
        };

        //adds Purchase History button
        JButton pHistory_bt = new JButton("Purchase History");
        pHistory_bt.setBounds(25, 10, 200, 75);
        pHistory_bt.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        account_panel.add(pHistory_bt);

        //Action listener for purchase history button
        ActionListener pHistory_pressed = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //changes screen to Account Screen
                CardLayout cardLayout = (CardLayout) cardHolder.getLayout();
                cardLayout.show(cardHolder, "PHistoryScreen");
            }
        };

        //adds bank details button
        JButton bDetails_bt = new JButton("Bank Details");
        bDetails_bt.setBounds(230, 10, 150, 75);
        bDetails_bt.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        account_panel.add(bDetails_bt);

        //Action listener for bank details button
        ActionListener bDetails_pressed = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //changes screen to Menu Screen
                CardLayout cardLayout = (CardLayout) cardHolder.getLayout();
                cardLayout.show(cardHolder, "BankDetailsScreen");
            }
        };

        //adds edit details button
        JButton eDetails_bt = new JButton("Edit Details");
        eDetails_bt.setBounds(865, 10, 150, 75);
        eDetails_bt.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        account_panel.add(eDetails_bt);

        //Action listener for edit details button
        ActionListener eDetails_pressed = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //changes screen to edit details screen
                CardLayout cardLayout = (CardLayout) cardHolder.getLayout();
                cardLayout.show(cardHolder, "EditDetailsScreen");
            }
        };

        //adds staff button
        JButton staff_bt = new JButton("Staff");
        staff_bt.setBounds(1025, 85, 150, 75);
        staff_bt.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        account_panel.add(staff_bt);

        //Action listener for edit details button
        ActionListener staff_pressed = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //changes screen to edit details screen
                //todo check if they have authority else put label saying they don't
                CardLayout cardLayout = (CardLayout) cardHolder.getLayout();
                cardLayout.show(cardHolder, "StaffScreen");
            }
        };







        //Creates bank details panel
        JPanel bankDetails_panel = new JPanel(null);

        //adds a title in the centre at the top in bold font size 40
        JLabel title_bd = new JLabel("Bank Details");
        title_bd.setBounds(500, 25, 600, 45);
        title_bd.setFont(new Font("Times New Roman", Font.BOLD, 40));
        bankDetails_panel.add(title_bd);

        //adds back button
        JButton back_bt4 = new JButton("Back");
        back_bt4.setBounds(1025, 10, 150, 75);
        back_bt4.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        bankDetails_panel.add(back_bt4);

        //Action listener for back button
        ActionListener back_pressed4 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //changes screen to Account Screen
                CardLayout cardLayout = (CardLayout) cardHolder.getLayout();
                cardLayout.show(cardHolder, "AccountScreen");
            }
        };

        //adds card number label
        JLabel cardNumber_lb = new JLabel("Card Number:");
        cardNumber_lb.setBounds(466, 100, 250, 30);
        cardNumber_lb.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        bankDetails_panel.add(cardNumber_lb);

        //adds card number text box
        JTextField cardNumber_tb = new JTextField(20);
        cardNumber_tb.setBounds(580, 100, 270, 30);
        bankDetails_panel.add(cardNumber_tb);

        //adds expiration date label
        JLabel expiration_lb = new JLabel("Expiration Date:");
        expiration_lb.setBounds(448, 150, 250, 30);
        expiration_lb.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        bankDetails_panel.add(expiration_lb);

        //adds expiration date text box
        JTextField expiration_tb = new JTextField(20);
        expiration_tb.setBounds(580, 150, 270, 30);
        bankDetails_panel.add(expiration_tb);

        //adds cvv label
        JLabel cvv_lb = new JLabel("CVV Code:");
        cvv_lb.setBounds(490, 200, 250, 30);
        cvv_lb.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        bankDetails_panel.add(cvv_lb);

        //adds cvv text box
        JTextField cvv_tb = new JTextField(20);
        cvv_tb.setBounds(580, 200, 270, 30);
        bankDetails_panel.add(cvv_tb);

        //adds confirm button
        JButton confirm_bt_bd = new JButton("Confirm");
        confirm_bt_bd.setBounds(500, 680, 150, 75);
        confirm_bt_bd.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        bankDetails_panel.add(confirm_bt_bd);

        //Action listener for confirm button
        ActionListener confirm_pressed3 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //todo check info is valid including date, num and length
                //todo put info into db
                String cardNum_input = cardNumber_tb.getText();
                String expire_input = expiration_tb.getText();
                String cvv_input = cvv_tb.getText();
            }
        };






        //Creates Edit Details panel
        JPanel editDetails_panel = new JPanel(null);

        //adds a title in the centre at the top in bold font size 40
        JLabel title_ed = new JLabel("Edit Details");
        title_ed.setBounds(500, 25, 600, 45);
        title_ed.setFont(new Font("Times New Roman", Font.BOLD, 40));
        editDetails_panel.add(title_ed);

        //adds back button
        JButton back_bt5 = new JButton("Back");
        back_bt5.setBounds(1025, 10, 150, 75);
        back_bt5.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        editDetails_panel.add(back_bt5);

        //Action listener for back button
        ActionListener back_pressed5 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //changes screen to Account Screen
                CardLayout cardLayout = (CardLayout) cardHolder.getLayout();
                cardLayout.show(cardHolder, "AccountScreen");
            }
        };

        //adds addInfo button
        JButton addInfo_bt = new JButton("Add Info");
        addInfo_bt.setBounds(525, 550, 150, 75);
        addInfo_bt.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        editDetails_panel.add(addInfo_bt);


        //adds email label
        JLabel email_lb_ed = new JLabel("Email:");
        email_lb_ed.setBounds(525, 100, 200, 30);
        email_lb_ed.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        editDetails_panel.add(email_lb_ed);

        //adds email text box
        JTextField email_tb_ed = new JTextField(20);
        email_tb_ed.setBounds(580, 100, 300, 30);
        editDetails_panel.add(email_tb_ed);

        //adds password label
        JLabel password_lb_ed = new JLabel("Password:");
        password_lb_ed.setBounds(498, 150, 250, 30);
        password_lb_ed.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        editDetails_panel.add(password_lb_ed);

        //adds password text box
        JTextField password_tb_ed = new JTextField(20);
        password_tb_ed.setBounds(580, 150, 270, 30);
        editDetails_panel.add(password_tb_ed);

        //adds forename label
        JLabel forename_lb_ed = new JLabel("Forename:");
        forename_lb_ed.setBounds(494, 200, 250, 30);
        forename_lb_ed.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        editDetails_panel.add(forename_lb_ed);

        //adds forename text box
        JTextField forename_tb_ed = new JTextField(20);
        forename_tb_ed.setBounds(580, 200, 270, 30);
        editDetails_panel.add(forename_tb_ed);

        //adds surname label
        JLabel surname_lb_ed = new JLabel("Surname:");
        surname_lb_ed.setBounds(503, 250, 250, 30);
        surname_lb_ed.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        editDetails_panel.add(surname_lb_ed);

        //adds surname text box
        JTextField surname_tb_ed = new JTextField(20);
        surname_tb_ed.setBounds(580, 250, 270, 30);
        editDetails_panel.add(surname_tb_ed);

        //adds surname label
        JLabel postcode_lb_ed = new JLabel("Postcode:");
        postcode_lb_ed.setBounds(502, 300, 250, 30);
        postcode_lb_ed.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        editDetails_panel.add(postcode_lb_ed);

        //adds surname text box
        JTextField postcode_tb_ed = new JTextField(20);
        postcode_tb_ed.setBounds(580, 300, 270, 30);
        editDetails_panel.add(postcode_tb_ed);


        //These are for the Address table:
        //adds house number label
        JLabel houseNumber_lb_ed = new JLabel("House Number:");
        houseNumber_lb_ed.setBounds(453, 350, 250, 30);
        houseNumber_lb_ed.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        editDetails_panel.add(houseNumber_lb_ed);

        //adds house number text box
        JTextField houseNumber_tb_ed = new JTextField(20);
        houseNumber_tb_ed.setBounds(580, 350, 270, 30);
        editDetails_panel.add(houseNumber_tb_ed);

        //adds road name label
        JLabel roadName_lb_ed = new JLabel("Road Name:");
        roadName_lb_ed.setBounds(480, 400, 250, 30);
        roadName_lb_ed.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        editDetails_panel.add(roadName_lb_ed);

        //adds road name text box
        JTextField roadName_tb_ed = new JTextField(20);
        roadName_tb_ed.setBounds(580, 400, 270, 30);
        editDetails_panel.add(roadName_tb_ed);

        //adds City name label
        JLabel cityName_lb_ed = new JLabel("City Name:");
        cityName_lb_ed.setBounds(488, 450, 250, 30);
        cityName_lb_ed.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        editDetails_panel.add(cityName_lb_ed);

        //adds City name text box
        JTextField cityName_tb_ed = new JTextField(20);
        cityName_tb_ed.setBounds(580, 450, 270, 30);
        editDetails_panel.add(cityName_tb_ed);

        //adds confirm button
        JButton confirm_bt_ed = new JButton("Confirm");
        confirm_bt_ed.setBounds(525, 650, 150, 75);
        confirm_bt_ed.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        editDetails_panel.add(confirm_bt_ed);

        ActionListener addInfo_pressed = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //todo Adds existing info to text boxes
                //todo gets user details where email is loggedIn.email
                email_tb_ed.setText(loggedIn.email);
                //todo somehow clear on logout
            }
        };

        //Action listener for edit details button
        ActionListener confirm_pressed = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //gets text from text boxes
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
                int cityName_length = cityName_array.length;

                //if any text box is empty adds label to say so
                if (email_input_ed.isBlank() || password_input_ed.isBlank() || forename_input_ed.isBlank()
                        || surname_input_ed.isBlank() || postcode_input_ed.isBlank() || houseNum_input_ed.isBlank()
                        || roadName_input_ed.isBlank() || cityName_input_ed.isBlank()) {
                    JLabel incorrect1_lb_ed = new JLabel("Please ensure you fill all details");
                    incorrect1_lb_ed.setBounds(415, 550, 900, 30);
                    incorrect1_lb_ed.setFont(new Font("Times New Roman", Font.PLAIN, 20));
                    incorrect1_lb_ed.setForeground(Color.RED);
                    editDetails_panel.add(incorrect1_lb_ed);
                    frame.repaint();
                    //if any text box contains more characters than it's supposed to it adds a label to say so
                } else if (email_length > 50 || password_length > 20 || forename_length > 20 || surname_length > 20
                        || houseNum_length > 10 || postcode_length > 20 || roadName_length > 100 || cityName_length > 100) {
                    JLabel incorrect2_lb_ed = new JLabel("Please ensure your information does not exceed the length limit");
                    incorrect2_lb_ed.setBounds(390, 600, 900, 30);
                    incorrect2_lb_ed.setFont(new Font("Times New Roman", Font.PLAIN, 20));
                    incorrect2_lb_ed.setForeground(Color.RED);
                    editDetails_panel.add(incorrect2_lb_ed);
                    frame.repaint();
                    //todo check if info is already existing for different user if required
                } else {
                    //todo change details in db and on account page
                    //todo if email is changed update loggedIn.email
                    CardLayout cardLayout = (CardLayout) cardHolder.getLayout();
                    cardLayout.show(cardHolder, "AccountScreen");
                }
            }
        };






        //Creates purchase history panel
        JPanel pHistory_panel = new JPanel(null);

        //adds a title in the centre at the top in bold font size 40
        JLabel title_ph = new JLabel("Purchase History");
        title_ph.setBounds(500, 25, 600, 45);
        title_ph.setFont(new Font("Times New Roman", Font.BOLD, 40));
        pHistory_panel.add(title_ph);

        //adds back button
        JButton back_bt3 = new JButton("Back");
        back_bt3.setBounds(1025, 10, 150, 75);
        back_bt3.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        pHistory_panel.add(back_bt3);

        //Action listener for back button
        ActionListener back_pressed3 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //changes screen to Account Screen
                CardLayout cardLayout = (CardLayout) cardHolder.getLayout();
                cardLayout.show(cardHolder, "AccountScreen");
            }
        };





        //Creates staff area panel
        JPanel staff_panel = new JPanel(null);

        //adds a title in the centre at the top in bold font size 40
        JLabel title_st = new JLabel("Staff Area");
        title_st.setBounds(500, 25, 600, 45);
        title_st.setFont(new Font("Times New Roman", Font.BOLD, 40));
        staff_panel.add(title_st);

        //adds back button
        JButton back_bt6 = new JButton("Back");
        back_bt6.setBounds(1025, 10, 150, 75);
        back_bt6.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        staff_panel.add(back_bt6);

        //Action listener for back button
        ActionListener back_pressed6 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //changes screen to Account Screen
                CardLayout cardLayout = (CardLayout) cardHolder.getLayout();
                cardLayout.show(cardHolder, "AccountScreen");
            }
        };

        //adds customer info button
        JButton cInfo_bt = new JButton("Customer Info");
        cInfo_bt.setBounds(815, 10, 200, 75);
        cInfo_bt.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        staff_panel.add(cInfo_bt);

        //Action listener for customer info button
        ActionListener cInfo_pressed = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //changes screen to customer info Screen
                CardLayout cardLayout = (CardLayout) cardHolder.getLayout();
                cardLayout.show(cardHolder, "CustomerInfoScreen");
            }
        };

        //adds promote/demote button
        JButton promote_bt = new JButton("Promote/Demote");
        promote_bt.setBounds(10, 10, 220, 75);
        promote_bt.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        staff_panel.add(promote_bt);

        //Action listener for promote info button
        ActionListener promote_pressed = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //changes screen to customer info Screen
                CardLayout cardLayout = (CardLayout) cardHolder.getLayout();
                cardLayout.show(cardHolder, "PromoteScreen");
            }
        };

        //adds order detail button
        JButton oDetail_bt = new JButton("Order details");
        oDetail_bt.setBounds(240, 10, 220, 75);
        oDetail_bt.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        staff_panel.add(oDetail_bt);

        //Action listener for promote info button
        ActionListener oDetail_pressed = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //changes screen to customer info Screen
                CardLayout cardLayout = (CardLayout) cardHolder.getLayout();
                cardLayout.show(cardHolder, "OrderDetailScreen");
            }
        };





        //Creates customer info area panel
        JPanel customerInfo_panel = new JPanel(null);

        //adds a title in the centre at the top in bold font size 40
        JLabel title_ci = new JLabel("Customer Info");
        title_ci.setBounds(500, 25, 600, 45);
        title_ci.setFont(new Font("Times New Roman", Font.BOLD, 40));
        customerInfo_panel.add(title_ci);

        //adds back button
        JButton back_bt7 = new JButton("Back");
        back_bt7.setBounds(1025, 10, 150, 75);
        back_bt7.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        customerInfo_panel.add(back_bt7);

        //Action listener for back button
        ActionListener back_pressed7 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //changes screen to Account Screen
                CardLayout cardLayout = (CardLayout) cardHolder.getLayout();
                cardLayout.show(cardHolder, "StaffScreen");
            }
        };

        //adds Email: label
        JLabel email_lb_ci = new JLabel("Email:");
        email_lb_ci.setBounds(300, 100, 70, 30);
        email_lb_ci.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        customerInfo_panel.add(email_lb_ci);

        //adds email text box
        JTextField email_tb_ci = new JTextField(20);
        email_tb_ci.setBounds(370, 100, 500, 30);
        customerInfo_panel.add(email_tb_ci);

        //adds search button
        JButton search_bt = new JButton("Search");
        search_bt.setBounds(880, 95, 150, 40);
        search_bt.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        customerInfo_panel.add(search_bt);

        //adds email label
        JLabel email_lb_ci2 = new JLabel("Email");
        email_lb_ci2.setBounds(525, 150, 400, 30);
        email_lb_ci2.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        customerInfo_panel.add(email_lb_ci2);

        //adds password label
        JLabel password_lb_ci = new JLabel("Password");
        password_lb_ci.setBounds(498, 200, 450, 30);
        password_lb_ci.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        customerInfo_panel.add(password_lb_ci);

        //adds forename label
        JLabel forename_lb_ci = new JLabel("Forename");
        forename_lb_ci.setBounds(494, 250, 450, 30);
        forename_lb_ci.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        customerInfo_panel.add(forename_lb_ci);

        //adds surname label
        JLabel surname_lb_ci = new JLabel("Surname");
        surname_lb_ci.setBounds(503, 300, 450, 30);
        surname_lb_ci.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        customerInfo_panel.add(surname_lb_ci);

        //adds postcode label
        JLabel postcode_lb_ci = new JLabel("Postcode");
        postcode_lb_ci.setBounds(502, 350, 450, 30);
        postcode_lb_ci.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        customerInfo_panel.add(postcode_lb_ci);

        //adds house number label
        JLabel houseNumber_lb_ci = new JLabel("House Number");
        houseNumber_lb_ci.setBounds(453, 400, 450, 30);
        houseNumber_lb_ci.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        customerInfo_panel.add(houseNumber_lb_ci);

        //adds road name label
        JLabel roadName_lb_ci = new JLabel("Road Name");
        roadName_lb_ci.setBounds(480, 450, 450, 30);
        roadName_lb_ci.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        customerInfo_panel.add(roadName_lb_ci);

        //adds City name label
        JLabel cityName_lb_ci = new JLabel("City Name");
        cityName_lb_ci.setBounds(488, 500, 450, 30);
        cityName_lb_ci.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        customerInfo_panel.add(cityName_lb_ci);

        //Action listener for search button
        ActionListener search_pressed = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //changes screen to searches and displays user info
                //todo get user info from db if email matches
                String emailInputted = email_tb_ci.getText();
                email_lb_ci2.setText(emailInputted);
            }
        };





        //Creates basket panel
        JPanel basket_panel = new JPanel(null);

        //adds a title in the centre at the top in bold font size 40
        JLabel title_bs = new JLabel("Basket");
        title_bs.setBounds(500, 25, 600, 45);
        title_bs.setFont(new Font("Times New Roman", Font.BOLD, 40));
        basket_panel.add(title_bs);

        //adds back button
        JButton back_bt8 = new JButton("Back");
        back_bt8.setBounds(1025, 10, 150, 75);
        back_bt8.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        basket_panel.add(back_bt8);

        //Action listener for back button
        ActionListener back_pressed8 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //changes screen to Account Screen
                CardLayout cardLayout = (CardLayout) cardHolder.getLayout();
                cardLayout.show(cardHolder, "MenuScreen");
            }
        };

        //adds confirm button
        JButton confirm_bt_bs = new JButton("Confirm");
        confirm_bt_bs.setBounds(510, 680, 150, 75);
        confirm_bt_bs.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        basket_panel.add(confirm_bt_bs);

        //Action listener for back button
        ActionListener confirm_pressed2 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //changes screen to Account Screen
                //todo check if bank details present in db and go to screen accordingly
                /*
                if (bank details in db == null) {
                    CardLayout cardLayout = (CardLayout) cardHolder.getLayout();
                    cardLayout.show(cardHolder, "BankDetailsScreen");
                } else {
                    CardLayout cardLayout = (CardLayout) cardHolder.getLayout();
                    cardLayout.show(cardHolder, "ConfirmScreen");
                }

                */
                CardLayout cardLayout = (CardLayout) cardHolder.getLayout();
                cardLayout.show(cardHolder, "ConfirmScreen");
            }
        };




        //Creates confirm panel
        JPanel confirm_panel = new JPanel(null);

        //adds a title in the centre at the top in bold font size 40
        JLabel title_cf = new JLabel("Confirmation");
        title_cf.setBounds(480, 25, 600, 45);
        title_cf.setFont(new Font("Times New Roman", Font.BOLD, 40));
        confirm_panel.add(title_cf);

        //adds back button
        JButton back_bt9 = new JButton("Menu");
        back_bt9.setBounds(1025, 10, 150, 75);
        back_bt9.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        confirm_panel.add(back_bt9);

        //Action listener for back button
        ActionListener back_pressed9 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //changes screen to Account Screen
                CardLayout cardLayout = (CardLayout) cardHolder.getLayout();
                cardLayout.show(cardHolder, "MenuScreen");
            }
        };

        //adds details about order
        JLabel details_lb = new JLabel("Order number... ");
        details_lb.setBounds(480, 150, 600, 45);
        details_lb.setFont(new Font("Times New Roman", Font.BOLD, 40));
        confirm_panel.add(details_lb);




        //Creates promote panel
        JPanel promote_panel = new JPanel(null);

        //adds a title in the centre at the top in bold font size 40
        JLabel title_pr = new JLabel("Promote/Demote");
        title_pr.setBounds(480, 25, 600, 45);
        title_pr.setFont(new Font("Times New Roman", Font.BOLD, 40));
        promote_panel.add(title_pr);

        //adds back button
        JButton back_bt10 = new JButton("Back");
        back_bt10.setBounds(1025, 10, 150, 75);
        back_bt10.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        promote_panel.add(back_bt10);

        //Action listener for back button
        ActionListener back_pressed10 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //changes screen to Account Screen
                CardLayout cardLayout = (CardLayout) cardHolder.getLayout();
                cardLayout.show(cardHolder, "StaffScreen");
            }
        };





        //Creates order detail panel
        JPanel orderDetail_panel = new JPanel(null);

        //adds a title in the centre at the top in bold font size 40
        JLabel title_od = new JLabel("Order Details");
        title_od.setBounds(480, 25, 600, 45);
        title_od.setFont(new Font("Times New Roman", Font.BOLD, 40));
        orderDetail_panel.add(title_od);

        //adds back button
        JButton back_bt11 = new JButton("Back");
        back_bt11.setBounds(1025, 10, 150, 75);
        back_bt11.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        orderDetail_panel.add(back_bt11);

        //Action listener for back button
        ActionListener back_pressed11 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //changes screen to Account Screen
                CardLayout cardLayout = (CardLayout) cardHolder.getLayout();
                cardLayout.show(cardHolder, "StaffScreen");
            }
        };




        //Adds the screens to the cardholder
        cardHolder.add(login_panel, "LoginScreen");
        cardHolder.add(menu_panel, "MenuScreen");
        cardHolder.add(signup_panel, "SignupScreen");
        cardHolder.add(account_panel, "AccountScreen");
        cardHolder.add(pHistory_panel, "PHistoryScreen");
        cardHolder.add(bankDetails_panel, "BankDetailsScreen");
        cardHolder.add(editDetails_panel, "EditDetailsScreen");
        cardHolder.add(staff_panel, "StaffScreen");
        cardHolder.add(customerInfo_panel, "CustomerInfoScreen");
        cardHolder.add(basket_panel, "BasketScreen");
        cardHolder.add(confirm_panel, "ConfirmScreen");
        cardHolder.add(promote_panel, "PromoteScreen");
        cardHolder.add(orderDetail_panel, "OrderDetailScreen");

        //Action listeners
        login_bt.addActionListener(login_pressed);
        signup_bt.addActionListener(signup_pressed);
        logout_bt1.addActionListener(logout_pressed1);
        logout_bt2.addActionListener(logout_pressed2);
        account_bt.addActionListener(account_pressed);
        back_bt2.addActionListener(back_pressed2);
        back_bt3.addActionListener(back_pressed3);
        pHistory_bt.addActionListener(pHistory_pressed);
        signUp_bt.addActionListener(signUp_pressed);
        back_bt4.addActionListener(back_pressed4);
        bDetails_bt.addActionListener(bDetails_pressed);
        back_bt5.addActionListener(back_pressed5);
        eDetails_bt.addActionListener(eDetails_pressed);
        confirm_bt_ed.addActionListener(confirm_pressed);
        back_bt6.addActionListener(back_pressed6);
        staff_bt.addActionListener((staff_pressed));
        cInfo_bt.addActionListener((cInfo_pressed));
        back_bt7.addActionListener(back_pressed7);
        addInfo_bt.addActionListener(addInfo_pressed);
        search_bt.addActionListener(search_pressed);
        back_bt8.addActionListener(back_pressed8);
        viewBasket_bt.addActionListener(viewBasket_pressed);
        back_bt9.addActionListener(back_pressed9);
        confirm_bt_bs.addActionListener(confirm_pressed2);
        promote_bt.addActionListener(promote_pressed);
        back_bt10.addActionListener(back_pressed10);
        oDetail_bt.addActionListener(oDetail_pressed);
        back_bt11.addActionListener(back_pressed11);
        confirm_bt_bd.addActionListener(confirm_pressed3);

        //required stuff
        frame.add(cardHolder);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }
}
