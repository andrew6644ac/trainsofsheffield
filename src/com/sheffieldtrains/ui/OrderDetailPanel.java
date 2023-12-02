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
import java.util.ArrayList;

public class OrderDetailPanel extends TopUIPanel {
    private Long selectedOrderNumber = null;
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

        JButton refreshButton = new JButton("Refresh Orders");
        refreshButton.setBounds(50, 200, 150, 30); // 设置位置和大小
        refreshButton.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        add(refreshButton);

        JButton moreDetailsButton = new JButton("More Details");
        moreDetailsButton.setBounds(50, 250, 150, 30); // 设置按钮位置和大小
        moreDetailsButton.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        add(moreDetailsButton);

        moreDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showOrderDetails(selectedOrderNumber);
            }
        });

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = table.getSelectedRow();
                selectedOrderNumber = (Long) tableModel.getValueAt(selectedRow, 1); // 获取选中行的订单号
            }
        });


        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reloadOrders();
            }
        });

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

    private void reloadOrders() {
        tableModel.setRowCount(0);
        List<Order> orders = OrderRepository.getAllOrdersToBeFulfilled(); // 假设这个方法获取最新的订单

        for (Order order : orders) {
            String email = order.getUser().getEmail();
            long orderNumber = order.getOrderNumber();
            String productCode = "product code";
            Date orderDate = order.getOrderDate();
            tableModel.addRow(new Object[]{email, orderNumber, productCode, orderDate});
        }
    }

    private void showOrderDetails(Long orderNumber) {
        if (orderNumber == null) {
            JOptionPane.showMessageDialog(this, "No order selected.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<OrderLine> orderLines = fetchOrderLines(orderNumber);
        JDialog detailDialog = new JDialog();
        detailDialog.setTitle("Order Details for Order " + orderNumber);

        // 创建表格模型，显示订单详情
        DefaultTableModel detailModel = new DefaultTableModel();
        detailModel.addColumn("Product Code");
        detailModel.addColumn("Quantity");

        for (OrderLine line : orderLines) {
            detailModel.addRow(new Object[]{line.getProductCode(), line.getQuantity()});
        }

        JTable detailTable = new JTable(detailModel);
        JScrollPane scrollPane = new JScrollPane(detailTable);
        detailDialog.add(scrollPane);

        detailDialog.pack();
        detailDialog.setVisible(true);
    }

    private List<OrderLine> fetchOrderLines(Long orderNumber) {
        List<OrderLine> orderLines = new ArrayList<>();

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://stusql.dcs.shef.ac.uk:3306/team066?user=team066&password=aNohqu4mo");
            String sql = "SELECT productCode, quantity FROM OrderLine WHERE orderNumber = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setLong(1, orderNumber);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String productCode = rs.getString("productCode");
                int quantity = rs.getInt("quantity");
                orderLines.add(new OrderLine(productCode, quantity));
            }

            rs.close();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching order details.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }

        return orderLines;
    }

    // OrderLine 类用于表示订单详情
    private class OrderLine {
        private String productCode;
        private int quantity;

        public OrderLine(String productCode, int quantity) {
            this.productCode = productCode;
            this.quantity = quantity;
        }

        public String getProductCode() {
            return productCode;
        }

        public int getQuantity() {
            return quantity;
        }
    }
}



