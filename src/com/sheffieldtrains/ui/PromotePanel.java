package com.sheffieldtrains.ui;

import com.sheffieldtrains.domain.user.User;
import com.sheffieldtrains.service.OrderService;
import com.sheffieldtrains.service.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class PromotePanel extends TopUIPanel {

    public PromotePanel(String panelName, JPanel parentParentPanel, JFrame topFrame) {
//        super(new BorderLayout());
        super(panelName, parentParentPanel, topFrame);
        this.panelName = panelName;
        this.parentParentPanel = parentParentPanel;
        this.topFrame = topFrame;
        layoutComponents();
    }

    private void layoutComponents() {
        //adds a title in the centre at the top in bold font size 40
        JLabel title_pr = new JLabel("Promote/Demote");
        title_pr.setBounds(480, 25, 600, 45);
        title_pr.setFont(new Font("Times New Roman", Font.BOLD, 40));
        add(title_pr);

        //adds back button
        JButton back_bt10 = new JButton("Back");
        back_bt10.setBounds(1025, 10, 150, 75);
        back_bt10.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        add(back_bt10);

        //Action listener for back button
        ActionListener back_pressed10 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //changes screen to Account Screen
                CardLayout cardLayout = (CardLayout) parentParentPanel.getLayout();
                cardLayout.show(parentParentPanel, "StaffScreen");
            }
        };
        back_bt10.addActionListener(back_pressed10);

        JButton promoteButton = new JButton("Promote");
        promoteButton.setBounds(1025, 105, 150, 75);
        promoteButton.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        add(promoteButton);
        promoteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) { // Check if any row is selected
                        String email = (String) tableModel.getValueAt(selectedRow, 2); // Assuming first column contains item name
                        User user=UserService.getUser(email);
                        if (!user.isStaff()) {
                            UserService.promoteToStaff(user);
                            tableModel.setValueAt("true", selectedRow, 3);
                            // Notify the table that the data has changed
                            tableModel.fireTableRowsUpdated(selectedRow, selectedRow);
                           /* tableModel.removeRow(selectedRow);
                            table.revalidate();
                            table.repaint();*/
                        }
                        else {
                            JOptionPane.showMessageDialog(
                                    null, // Parent component (null for centering on the screen)
                                    "User is already staff", // Message
                                    "Error", // Title
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
        });

        JButton demoteButton = new JButton("Demote");
        demoteButton.setBounds(1025, 200, 150, 75);
        demoteButton.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        add(demoteButton);
        demoteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) { // Check if any row is selected
                    String email = (String) tableModel.getValueAt(selectedRow, 2); // Assuming first column contains item name
                    User user=UserService.getUser(email);
                    if (user.isStaff()) {
                        UserService.demoteStaff(user);
                        tableModel.setValueAt("false", selectedRow, 3);
                        // Notify the table that the data has changed
                        tableModel.fireTableRowsUpdated(selectedRow, selectedRow);

                    }
                    else {
                        JOptionPane.showMessageDialog(
                                null, // Parent component (null for centering on the screen)
                                "User is not staff, cannot be further demoted", // Message
                                "Error", // Title
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        tableModel.addColumn("User ID");
        tableModel.addColumn("User Name");
        tableModel.addColumn("User Email");
        tableModel.addColumn("Is Staff");
        /*tableModel.addColumn("User Role 2");
        tableModel.addColumn("User Role 3");
        tableModel.addColumn("User Role 4");*/

        List<User> users = UserService.getAllUsers();
        String[] rowData = new String[tableModel.getColumnCount()];
        for (User user : users) {
            rowData[0]=""+user.getUserId();
            rowData[1]=""+ user.getForename()+ " "+ user.getSurname() ;
            rowData[2]=""+user.getEmail();
            rowData[3]="" + user.isStaff();
            tableModel.addRow(rowData);
        }
        JScrollPane scrollPane1 = new JScrollPane(table);
        scrollPane1.setBounds(400, 100, 600, 500);
        add(scrollPane1);
    }

}


