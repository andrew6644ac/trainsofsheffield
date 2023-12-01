package com.sheffieldtrains.ui;

import com.sheffieldtrains.domain.order.Order;
import com.sheffieldtrains.domain.product.ProductType;
import com.sheffieldtrains.domain.user.User;
import com.sheffieldtrains.service.OrderService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Vector;

public class BasketPanel extends TopUIPanel {
    public BasketPanel(String panelName, JPanel parentParentPanel, JFrame topFrame) {
        super(new BorderLayout());
//        super(panelName, parentParentPanel, topFrame);
        this.panelName = panelName;
        this.parentParentPanel = parentParentPanel;
        this.topFrame = topFrame;
        /*tableModel=new DefaultTableModel(new String[]{"Product Code", "Brand", "Product Name", "Product Type", "Price","Quantity", "Increase", "Reduce", "Remove Item"}, 0);
        table = new JTable(tableModel);*/
        layoutComponents();
    }

    private void processOrder() {
        if (UserSession.getCurrentUser().hasBankDetail()) {
            Order order = createOrderFromTableModel(tableModel);
            if (!order.isEmpty()) {
                OrderService.confirmOrderForUser(UserSession.getCurrentUser().getUserId(), order);
            }
            /*tableModel.setRowCount(0);
            CardLayout layout=(CardLayout) parentParentPanel.getLayout();
            layout.show(parentParentPanel, "MenuScreen");*/
        }
    }

    private static Order createOrderFromTableModel(DefaultTableModel basketTableModel) {
        Vector<Vector> dataVector = basketTableModel.getDataVector();
        Order order=new Order();
        User user =UserSession.getCurrentUser();
        order.setUser(user);
        order.setOrderDate(new Date());
        for (Vector<Object> rowData : dataVector) {
            /*String productCode,
            ProductType productType,
            int quantity,
            float productPrice*/
//            order.addOrderLine(dataVector.get(3),  );
            // Print or process the rowData as needed
            String productCode= (String) rowData.get(0);
            ProductType productType=ProductType.valueOf((String) rowData.get(3));
            float productPrice=Float.valueOf((String) rowData.get(4));
            int quantity=(Integer) rowData.get(5);
            order.addOrderLine(productCode,productType,quantity,productPrice);
            System.out.println("Row: " + rowData);
        }
        return order;
    }

    private void layoutComponents() {

        // Delete button for the basket table
   /*     ButtonColumn deleteButtonColumn = new ButtonColumn(table, tableModel.getColumnCount()-1, tableModel, true,false,false){
            @Override
            public void actionPerformed(ActionEvent e) {
                // This stops any cell editing that is currently happening
                fireEditingStopped();
                // Get the selected row from the view
                int viewRow = table.getSelectedRow();
                // Only proceed if a row is actually selected
                if (viewRow != -1) {
                    // Convert the row index from the view's coordinate system to the model's
                    int modelRow = table.convertRowIndexToModel(viewRow);
                    // Remove the row from the model
                    tableModel.removeRow(modelRow);
                    // Notify the table that the model data has changed and repaint the table
                    table.revalidate();
                    table.repaint();
                }
            }
        };

// 创建按钮列
        ButtonColumn addQuantityButtonColumn = new ButtonColumn(table, 6, tableModel, false, true, false);
        ButtonColumn removeQuantityButtonColumn = new ButtonColumn(table, 7, tableModel, false, false, true);
*/
        // 设置列的首选宽度，如果需要的话
// 例如：Main.basketTable.getColumnModel().getColumn(0).setPreferredWidth(100);

// 添加'返回'按钮到面板

        JScrollPane basketScrollPane = new JScrollPane(table);
        add(Main.createBackButton("MenuScreen"), BorderLayout.SOUTH);

// 创建将包含篮子表格的面板
       add(basketScrollPane, BorderLayout.CENTER);


        // 创建一个面板来容纳总价标签和按钮
        JPanel bottomPanel = new JPanel(new BorderLayout());

// 添加总价标签到底部面板的北部
//        bottomPanel.add(totalPriceLabel, BorderLayout.CENTER);

// 创建一个新的面板来容纳"Back"和"Confirm Order"按钮
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton confirmOrderButton = new JButton("Confirm Order");
        confirmOrderButton.setFont(new Font("Times New Roman", Font.BOLD, 25));
        confirmOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processOrder();
                tableModel.setRowCount(0);
                CardLayout layout=(CardLayout) parentParentPanel.getLayout();
                layout.show(parentParentPanel, "MenuScreen");
                /*if (UserSession.getCurrentUser().hasBankDetail()) {
                Order order = createOrderFromTableModel(basketTableModel);
                if (!order.isEmpty()) {
                    OrderService.confirmOrderForUser(UserSession.getCurrentUser().getUserId(), order);
                }
                tableModel.setRowCount(0);
                parentParentPanel.getLayout().show(parentParentPanel, "MenuScreen");*/
            }

        });
// 设置按钮的首选大小
        confirmOrderButton.setPreferredSize(new Dimension(250, 75));
//        viewBasket_bt.setPreferredSize(new Dimension(250, 75));

// 将"Back"按钮和"Confirm Order"按钮添加到按钮面板
        buttonPanel.add(Main.createBackButton("MenuScreen"));
        buttonPanel.add(confirmOrderButton);
        add(buttonPanel, BorderLayout.SOUTH);
// 将按钮面板添加到底部面板的南部
//        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

// 将底部面板添加到购物车面板的底部
//        basketPanel.add(bottomPanel, BorderLayout.SOUTH);

// 刷新界面以显示添加的组件
       /* basketPanel.revalidate();
        basketPanel.repaint();*/
        table.revalidate();
        table.repaint();

    }


    public void setTable(JTable basketTable, DefaultTableModel basketTableModel) {
        this.table=basketTable;
        this.tableModel=basketTableModel;
    }
}
