package com.sheffieldtrains.ui;

import com.sheffieldtrains.domain.user.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class AccountPanel extends TopUIPanel {

    JButton staffButton;
    public AccountPanel(String panelName, JPanel parentParentPanel, JFrame topFrame) {
        super(panelName, parentParentPanel, topFrame);
        this.panelName = panelName;
        this.parentParentPanel = parentParentPanel;
        this.topFrame = topFrame;
        layoutComponents();
    }

    public JButton getStaffButton() {
        return staffButton;
    }

    private void layoutComponents() {
        JLabel title_ac = new JLabel("Admin Area");
        title_ac.setBounds(540, 25, 600, 45);
        title_ac.setFont(new Font("Times New Roman", Font.BOLD, 40));
        add(title_ac);

        //adds back button
        JButton back_bt2 = new JButton("Back");
        back_bt2.setBounds(1025, 10, 150, 75);
        back_bt2.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        add(back_bt2);

        //Action listener for back button
        ActionListener back_pressed2 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //changes screen to Menu Screen
                CardLayout cardLayout = (CardLayout) parentParentPanel.getLayout();
                cardLayout.show(parentParentPanel, "MenuScreen");
            }
        };

        //adds Purchase History button
        JButton pHistory_bt = new JButton("Purchase History");
        pHistory_bt.setBounds(25, 10, 200, 75);
        pHistory_bt.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        add(pHistory_bt);

        //Action listener for purchase history button
        ActionListener pHistory_pressed = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //changes screen to Account Screen
                CardLayout cardLayout = (CardLayout) parentParentPanel.getLayout();
                cardLayout.show(parentParentPanel, "PHistoryScreen");
                Main.getOrderHistoryController().notifyChange();
            }
        };

        //adds bank details button
        JButton bDetails_bt = new JButton("Bank Details");
        bDetails_bt.setBounds(230, 10, 150, 75);
        bDetails_bt.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        add(bDetails_bt);

        //Action listener for bank details button
        ActionListener bDetails_pressed = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //changes screen to Menu Screen
                CardLayout cardLayout = (CardLayout) parentParentPanel.getLayout();
                cardLayout.show(parentParentPanel, "BankDetailsScreen");
            }
        };

        //adds edit details button
        JButton eDetails_bt = new JButton("Edit Details");
        eDetails_bt.setBounds(865, 10, 150, 75);
        eDetails_bt.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        add(eDetails_bt);

        //Action listener for edit details button
        ActionListener eDetails_pressed = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //changes screen to edit details screen
                CardLayout cardLayout = (CardLayout) parentParentPanel.getLayout();
                Main.getEditDetails_panel().populateDetailFields();
                cardLayout.show(parentParentPanel, "EditDetailsScreen");
            }
        };

        //adds staff button
        JButton staff_bt = new JButton("Staff");
        staffButton = staff_bt;
        staff_bt.setBounds(1025, 95, 150, 75);
        staff_bt.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        add(staff_bt);

        //Action listener for edit details button
        ActionListener staff_pressed = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //changes screen to edit details screen
                //todo check if they have authority else put label saying they don't
                CardLayout cardLayout = (CardLayout) parentParentPanel.getLayout();
                cardLayout.show(parentParentPanel, "StaffScreen");
            }
        };

        back_bt2.addActionListener(back_pressed2);
        pHistory_bt.addActionListener(pHistory_pressed);
        bDetails_bt.addActionListener(bDetails_pressed);
        eDetails_bt.addActionListener(eDetails_pressed);
        staff_bt.addActionListener((staff_pressed));
    }

    public void takeSecurityMeaures() {
        User user=UserSession.getCurrentUser();
        boolean shouldStaffButtonVisible=false;
        if (user!=null) {
            if (user.isStaff() || user.isManager()) {
                shouldStaffButtonVisible = true;
            }
        }
        staffButton.setVisible(shouldStaffButtonVisible);
    }

}
