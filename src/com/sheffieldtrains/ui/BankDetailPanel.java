package com.sheffieldtrains.ui;

import com.sheffieldtrains.service.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BankDetailPanel extends TopUIPanel {
    private HashMap<String, JTextField> textFieldMap=new HashMap<>();
    private String dateFormatPattern = "yyyy-MM-dd";
    public BankDetailPanel(String panelName, JPanel parentParentPanel, JFrame topFrame) {
//        super(new BorderLayout());
        super(panelName, parentParentPanel, topFrame);
        this.panelName = panelName;
        this.parentParentPanel = parentParentPanel;
        this.topFrame = topFrame;
        layoutComponents();
    }


    private void layoutComponents() {
        //adds a title in the centre at the top in bold font size 40
        JLabel title_bd = new JLabel("Bank Details");
        title_bd.setBounds(500, 25, 600, 45);
        title_bd.setFont(new Font("Times New Roman", Font.BOLD, 40));
        add(title_bd);

        //adds back button
        JButton back_bt4 = new JButton("Back");
        back_bt4.setBounds(1025, 10, 150, 75);
        back_bt4.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        add(back_bt4);

        //Action listener for back button
        ActionListener back_pressed4 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //changes screen to Account Screen
                Main.updateAccountPanelForSecurity();
                Main.updatePromotePanelForSecurity();
                CardLayout cardLayout = (CardLayout) parentParentPanel.getLayout();
                cardLayout.show(parentParentPanel, "AccountScreen");
            }
        };

        back_bt4.addActionListener(back_pressed4);

        ActionListener backToAccountListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //changes screen to Account Screen
                CardLayout cardLayout = (CardLayout) parentParentPanel.getLayout();
                cardLayout.show(parentParentPanel, "AccountScreen");
            }
        };

        //adds card type label
        JLabel cardType_lb = new JLabel("Card Type:");
        cardType_lb.setBounds(/*466*/520, 100, 250, 30);
        cardType_lb.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        add(cardType_lb);

        //adds card type text box
        JTextField cardType_tb = new JTextField(20);
        cardType_tb.setBounds(680, 100, 270, 30);
        add(cardType_tb);
        textFieldMap.put("getBankCardName", cardType_tb);

        //adds card holder label
        JLabel cardHolder_lb = new JLabel("Card Holder  Name:");
        cardHolder_lb.setBounds(/*466*/520, 150, 250, 30);
        cardHolder_lb.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        add(cardHolder_lb);

        //adds card holder text box
        JTextField cardHolder__tb = new JTextField(20);
        cardHolder__tb.setBounds(680, 150, 270, 30);
        add(cardHolder__tb);
        textFieldMap.put("getCardHolderName", cardHolder__tb);

        //adds card number label
        JLabel cardNumber_lb = new JLabel("Card Number:");
        cardNumber_lb.setBounds(/*466*/520, 200, 250, 30);
        cardNumber_lb.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        add(cardNumber_lb);

        //adds card number text box
        JTextField cardNumber_tb = new JTextField(20);
        cardNumber_tb.setBounds(680, 200, 270, 30);
        add(cardNumber_tb);
        textFieldMap.put("getBankCardNumber", cardNumber_tb);

        //adds expiration date label
        JLabel expiration_lb = new JLabel("Expiration Date:");
        expiration_lb.setBounds(/*466*/520, 250, 250, 30);
        expiration_lb.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        add(expiration_lb);

        //adds expiration date text box
        JTextField expiration_tb = new JTextField(20);
        expiration_tb.setBounds(680, 250, 270, 30);
        add(expiration_tb);
        textFieldMap.put("getCardExpiryDate", expiration_tb);

        //adds cvv label
        JLabel cvv_lb = new JLabel("CVV Code:");
        cvv_lb.setBounds(/*466*/520, 300, 250, 30);
        cvv_lb.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        add(cvv_lb);

        //adds cvv text box
        JTextField cvv_tb = new JTextField(20);
        cvv_tb.setBounds(680, 300, 270, 30);
        add(cvv_tb);
        textFieldMap.put("getSecurityCode", cvv_tb);

        //adds confirm button
        JButton confirm_bt_bd = new JButton("Confirm");
        confirm_bt_bd.setBounds(500, 680, 150, 75);
        confirm_bt_bd.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        add(confirm_bt_bd);

        //Action listener for confirm button
        ActionListener confirm_pressed3 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //todo check info is valid including date, num and length
                //todo put info into db
                /*String cardNum_input = cardNumber_tb.getText();
                String expire_input = expiration_tb.getText();
                String cvv_input = cvv_tb.getText();*/
                if (validateFields()){
                    String bankCardName = textFieldMap.get("getBankCardName").getText();
                    String cardHolderName = textFieldMap.get("getCardHolderName").getText();
                    String bankCardNumber = textFieldMap.get("getBankCardNumber").getText();
                    String cardExpiryDate = textFieldMap.get("getCardExpiryDate").getText();
                    String securityCode = textFieldMap.get("getSecurityCode").getText();
                    Date expireDate=parseDate(cardExpiryDate, dateFormatPattern);
                    UserService.addOrModifyBankDetails(
                            UserSession.getCurrentUser().getUserId(),
                             bankCardName,
                             cardHolderName,
                             bankCardNumber,
                            expireDate,
                            Integer.valueOf(securityCode)
                    );
                    UserSession.revalidateUser(UserSession.getCurrentUser());

                    System.out.println("Entered bank details successfully");
                    CardLayout cardLayout = (CardLayout) parentParentPanel.getLayout();
                    System.out.println("Entered bank details successfully. JPanel is: "+ parentParentPanel+ " and Layout is" + cardLayout);

                    cardLayout.show(parentParentPanel, "MenuScreen);");
                    parentParentPanel.revalidate();
                    parentParentPanel.repaint();
                    JOptionPane.showMessageDialog(
                            null, // Parent component (null for centering on the screen)
                            "Bank Details Entered Successfully. Please Use Back Button To Go Back.",// Message
                            "Bank Details Entry  Error", // Title
                            JOptionPane.ERROR_MESSAGE);
//                    cardLayout.show(parentParentPanel, "AccountScreen);");
                }
            }
        };
        confirm_bt_bd.addActionListener(confirm_pressed3);
    }

    private boolean validateFields() {
        boolean validationPassed = true;
        try {
            for (Map.Entry<String, JTextField> entry : textFieldMap.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue().getText();
                if (value.isBlank()) {
                    throw new RuntimeException("The filed for "+ entry.getKey() + " is blank");
                }
            }
            String bankCardName = textFieldMap.get("getBankCardName").getText();
            String cardHolderName = textFieldMap.get("getCardHolderName").getText();
            String bankCardNumber = textFieldMap.get("getBankCardNumber").getText();
            String cardExpiryDate = textFieldMap.get("getCardExpiryDate").getText();
            String securityCode = textFieldMap.get("getSecurityCode").getText();
            //card number should be 16 digits
            if (!isValidDigitNumber(bankCardNumber, "\\d{16}")) {
                throw new RuntimeException("The card number has to be 16 digits");
            }
            if (!isValidDigitNumber(securityCode, "\\d{3}")) {
                throw new RuntimeException("The CVV has to be 3 digits");
            }
            if (!isValidDateFormat(cardExpiryDate, dateFormatPattern)) {
                throw new RuntimeException("The date should be in the format: "+"yyyy-MM-dd");
            }
        }
        catch(RuntimeException ex){
            JOptionPane.showMessageDialog(
                    null, // Parent component (null for centering on the screen)
                    ex.getMessage(),// Message
                    "Bank Details Entry  Error", // Title
                    JOptionPane.ERROR_MESSAGE);
            validationPassed=false;
         }
        return validationPassed;
    }

    private  boolean isValidDigitNumber(String input, String regPattern) {
        // Define a regular expression for a 16-digit number
       /* String regex = "\\d{16}";*/
        String regex=regPattern;

        // Compile the regular expression
        Pattern pattern = Pattern.compile(regex);

        // Create a matcher with the input string
        Matcher matcher = pattern.matcher(input);

        // Check if the input matches the pattern
        return matcher.matches();
    }

    private static boolean isValidDateFormat(String input, String pattern) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
        dateFormat.setLenient(false); // Disable lenient parsing
        try {
            dateFormat.parse(input);
            return true; // Parsing successful, input is in the correct format
        } catch (ParseException e) {
            return false; // Parsing failed, input is not in the correct format
        }
    }

    private  Date parseDate(String input, String pattern) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
            return dateFormat.parse(input);
        } catch (ParseException e) {
            // Handle parsing exception if needed
            e.printStackTrace();
            return null;
        }
    }

}
