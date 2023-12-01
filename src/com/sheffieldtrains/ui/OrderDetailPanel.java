package com.sheffieldtrains.ui;

import com.sheffieldtrains.db.OrderFullfillmentException;
import com.sheffieldtrains.db.OrderRepository;
import com.sheffieldtrains.domain.order.Order;
import com.sheffieldtrains.service.OrderService;
import com.sheffieldtrains.service.UserService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Date;
import java.util.List;

public class OrderDetailPanel extends TopUIPanel {

    public OrderDetailPanel(String panelName, JPanel parentParentPanel, JFrame topFrame) {
//        super(new BorderLayout());
        super(panelName, parentParentPanel, topFrame);
        this.panelName = panelName;
        this.parentParentPanel = parentParentPanel;
        this.topFrame = topFrame;
        layoutComponents();
    }

    private void layoutComponents() {
        JLabel title_od = new JLabel("Order Details");
        title_od.setBounds(480, 25, 600, 45);
        title_od.setFont(new Font("Times New Roman", Font.BOLD, 40));
        add(title_od);

        //adds back button
        JButton back_bt11 = new JButton("Back");
        back_bt11.setBounds(1025, 10, 150, 75);
        back_bt11.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        add(back_bt11);

        //Action listener for back button
        ActionListener back_pressed11 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //changes screen to Account Screen
                CardLayout cardLayout = (CardLayout) parentParentPanel.getLayout();
                cardLayout.show(parentParentPanel, "StaffScreen");
            }
        };
        back_bt11.addActionListener(back_pressed11);

        tableModel.addColumn("Email");
        tableModel.addColumn("Order Number");
        //todo: now don't display product code as it's the order line which will do it.
        tableModel.addColumn("Product Code");
        tableModel.addColumn("Order Date");
        List<Order> orders= OrderRepository.getAllOrdersToBeFulfilled();

        for(Order order: orders){
            String email=order.getUser().getEmail();
            long orderNumber=order.getOrderNumber();
         //todo: need to add columns to show the next line?
            String productCode="product code";
            Date orderDate=order.getOrderDate();
            tableModel.addRow(new Object[]{email, orderNumber, productCode,orderDate});
        };

//        JTable ordersTable = new JTable(model);
        JScrollPane scrollPane1 = new JScrollPane(table);
        scrollPane1.setBounds(400, 100, 600, 500);
        add(scrollPane1);

// Add event listener to the table
        /*ordersTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                // Display the detailed information of the selected order
            }
        });*/

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                // Display the detailed information of the selected order
            }
        });

// Add fulfill and reject buttons
        JButton fulfillButton = new JButton("Fulfill Order");
        fulfillButton.setBounds(50, 100, 150, 30);
        fulfillButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            try {
                if (selectedRow != -1) { // Check if any row is selected
                    Long orderNumber = (Long) tableModel.getValueAt(selectedRow, 1); // Assuming first column contains item name
                    OrderService.fulfillOrder(orderNumber);
                    tableModel.removeRow(selectedRow);
                    table.revalidate();
                    table.repaint();
                }
            }
            catch(OrderFullfillmentException ex){
               /*JLabel errorLabel =new JLabel(ex.getMessage());
                errorLabel.setBounds(415, 550, 900, 30);
                errorLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
                errorLabel.setForeground(Color.RED);*/
                //todo: display error message orderDetail_panel.(errorLabel);
                JOptionPane.showMessageDialog(
                        null, // Parent component (null for centering on the screen)
                        ex.getMessage(), // Message
                        "Order Fulfillment Error", // Title
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        add(fulfillButton);

        JButton rejectButton = new JButton("Reject Order");
        rejectButton.setBounds(50, 150, 150, 30);
        rejectButton.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) { // Check if any row is selected
                Long orderNumber = (Long) tableModel.getValueAt(selectedRow, 1); // Assuming first column contains item name
                OrderService.rejectOrder(orderNumber);
                tableModel.removeRow(selectedRow);
                table.revalidate();
                table.repaint();
            }
        });
        add(rejectButton);;
    }
}