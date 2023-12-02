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
    private JButton promoteButton;
    private JButton demoteButton;

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

        promoteButton = new JButton("Promote");
        promoteButton.setBounds(1025, 105, 150, 75);
        promoteButton.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        add(promoteButton);
        promoteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 权限检查：只有管理者才能使用此功能
                if (UserSession.getCurrentUser().isManager()) {
                    // 打开搜索和提升的子面板
                    JDialog searchDialog = new JDialog(topFrame, "Search and Promote User", true);
                    SearchAndPromotePanel searchPanel = new SearchAndPromotePanel(searchDialog);
                    searchDialog.add(searchPanel);
                    searchDialog.setSize(400, 200);
                    searchDialog.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(
                            null, // Parent component (null for centering on the screen)
                            "Only Managers Can Promote/Demote A User", // Message
                            "Error", // Title
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

         demoteButton = new JButton("Demote");
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
                    if (UserSession.getCurrentUser().isManager()) {
                        if (user.isStaff()) {
                            UserService.demoteStaff(user);
                            tableModel.setValueAt("false", selectedRow, 3);
                            // Notify the table that the data has changed
                            tableModel.fireTableRowsUpdated(selectedRow, selectedRow);

                        } else {
                            JOptionPane.showMessageDialog(
                                    null, // Parent component (null for centering on the screen)
                                    "User is not staff, cannot be further demoted", // Message
                                    "Error", // Title
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(
                                null, // Parent component (null for centering on the screen)
                                "Only Managers Can Promote/Demote A User", // Message
                                "Error", // Title
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        JButton refreshButton = new JButton("Refresh");
        refreshButton.setBounds(1025, 295, 150, 75);
        refreshButton.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        add(refreshButton);
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshUserTable(); // 调用刷新表格的方法
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

    public void takeSecurityMeaures() {
        User user=UserSession.getCurrentUser();
        boolean shouldPromoteButtonVisible=false;
        if (user!=null) {
            if (user.isManager()) {
                shouldPromoteButtonVisible = true;
            }
        }
        demoteButton.setVisible(shouldPromoteButtonVisible);
        promoteButton.setVisible(shouldPromoteButtonVisible);
    }

    class SearchAndPromotePanel extends JPanel {
        private JTextField emailField;
        private JButton searchButton;
        private JButton confirmButton;
        private JLabel statusLabel;
        private JDialog parentDialog;

        public SearchAndPromotePanel(JDialog parentDialog) {
            this.parentDialog = parentDialog;
            setLayout(new FlowLayout());
            emailField = new JTextField(20);
            add(emailField);

            searchButton = new JButton("Search");
            add(searchButton);

            confirmButton = new JButton("Confirm");
            confirmButton.setEnabled(false); // 初始时禁用确认按钮
            add(confirmButton);

            statusLabel = new JLabel(" ");
            add(statusLabel);

            searchButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String email = emailField.getText();
                    // 执行数据库搜索
                    User user = searchUserByEmail(email);
                    if (user != null) {
                        if (!user.isStaff()) {
                            statusLabel.setText("User found. Ready to promote.");
                            confirmButton.setEnabled(true);
                        } else {
                            statusLabel.setText("User is already a staff.");
                        }
                    } else {
                        statusLabel.setText("User not found.");
                    }
                }
            });

            confirmButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String email = emailField.getText();
                    // 执行提升操作
                    User user = UserService.getUser(email);
                    if (user != null && !user.isStaff()) {
                        UserService.promoteToStaff(user);
                        JOptionPane.showMessageDialog(SearchAndPromotePanel.this, "User promoted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                        parentDialog.dispose();
                    }
                }
            });
        }

        private User searchUserByEmail(String email) {
            // 这里应该实现数据库查询用户的逻辑
            // 假设 UserService 有一个根据电子邮箱获取用户的方法
            return UserService.getUser(email);
        }

    }
    private void refreshUserTable() {
        tableModel.setRowCount(0); // 清除表格现有数据
        List<User> users = UserService.getAllUsers(); // 重新获取所有用户数据
        for (User user : users) {
            String[] rowData = {
                    "" + user.getUserId(),
                    user.getForename() + " " + user.getSurname(),
                    user.getEmail(),
                    "" + user.isStaff()
            };
            tableModel.addRow(rowData);
        }
    }
}


