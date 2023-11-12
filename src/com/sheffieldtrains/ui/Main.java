package com.sheffieldtrains.ui;

//import statements
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Main {
    public static void main(String[] args) {

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
                if (email_input_lg.equals("email")  && password_input_lg.equals("password")) {
                    CardLayout cardLayout = (CardLayout) cardHolder.getLayout();
                    cardLayout.show(cardHolder, "MenuScreen");
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

        //Add buttons for staff and moderator if logged in as so.










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

        //Action listener for logout button
        ActionListener signUp_pressed = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //adds to database when clicked if not null
                JLabel temp_lb = new JLabel("sign up clicked");
                temp_lb.setBounds(450, 600, 250, 30);
                temp_lb.setFont(new Font("Times New Roman", Font.PLAIN, 20));
                temp_lb.setForeground(Color.RED);
                signup_panel.add(temp_lb);
                frame.repaint();
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






        //Adds the screens to the cardholder
        cardHolder.add(login_panel, "LoginScreen");
        cardHolder.add(menu_panel, "MenuScreen");
        cardHolder.add(signup_panel, "SignupScreen");
        cardHolder.add(account_panel, "AccountScreen");
        cardHolder.add(pHistory_panel, "PHistoryScreen");

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

        //required stuff
        frame.add(cardHolder);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

}
