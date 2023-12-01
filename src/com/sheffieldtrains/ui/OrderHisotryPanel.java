package com.sheffieldtrains.ui;

import com.sheffieldtrains.db.OrderRepository;
import com.sheffieldtrains.domain.order.Order;
import com.sheffieldtrains.domain.order.OrderStatus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

public class OrderHisotryPanel extends TopUIPanel {

    public OrderHisotryPanel(String panelName, JPanel parentParentPanel, JFrame topFrame) {
//        super(new BorderLayout());
        super(panelName, parentParentPanel, topFrame);
        this.panelName = panelName;
        this.parentParentPanel = parentParentPanel;
        this.topFrame = topFrame;
        layoutComponents();
    }

    private void layoutComponents() {
        //adds a title in the centre at the top in bold font size 40
        JLabel title_ph = new JLabel("Order History");
        title_ph.setBounds(500, 25, 600, 45);
        title_ph.setFont(new Font("Times New Roman", Font.BOLD, 40));
        add(title_ph);

        //adds back button
        JButton back_bt3 = new JButton("Back");
        back_bt3.setBounds(1025, 10, 150, 75);
        back_bt3.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        add(back_bt3);

        //Action listener for back button
        ActionListener back_pressed3 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //changes screen to Account Screen
                CardLayout cardLayout = (CardLayout) parentParentPanel.getLayout();
                cardLayout.show(parentParentPanel, "AccountScreen");
            }
        };
        back_bt3.addActionListener(back_pressed3);

        tableModel.addColumn("Order Number");
        tableModel.addColumn("Order Date");
        tableModel.addColumn("Order Status");
        //only show order history if there is an user logged in.
       /* if (UserSession.getCurrentUser()!=null) {
            List<Order> orders = OrderRepository.getAllHistoricalOrders(UserSession.getCurrentUser().getUserId());
            for (Order order : orders) {
                //  String email=order.getUser().getEmail();
                long orderNumber = order.getOrderNumber();
                // String productCode="product code";
                Date orderDate = order.getOrderDate();
                OrderStatus status = order.getStatus();
                tableModel.addRow(new Object[]{orderNumber, orderDate, status.toString()});
            }
        }*/
        JScrollPane scrollPane1 = new JScrollPane(table);
        scrollPane1.setBounds(400, 100, 600, 500);
        add(scrollPane1);
    }

    public void populateTableWithOrderHistoryData() {
        if (UserSession.getCurrentUser()!=null) {
            List<Order> orders = OrderRepository.getAllHistoricalOrders(UserSession.getCurrentUser().getUserId());
            for (Order order : orders) {
                //  String email=order.getUser().getEmail();
                long orderNumber = order.getOrderNumber();
                // String productCode="product code";
                Date orderDate = order.getOrderDate();
                OrderStatus status = order.getStatus();
                tableModel.addRow(new Object[]{orderNumber, orderDate, status.toString()});
            }
            table.revalidate();
//            table.repaint();
        }
    }
}
