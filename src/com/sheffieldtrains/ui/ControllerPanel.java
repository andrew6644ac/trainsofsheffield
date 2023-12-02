package com.sheffieldtrains.ui;

import com.sheffieldtrains.domain.product.Controller;
import com.sheffieldtrains.service.ProductService;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ControllerPanel extends TopUIPanel {

    public ControllerPanel(String panelName, JPanel parentParentPanel, JFrame topFrame) {
        super(new BorderLayout());
        /*super(panelName, parentParentPanel, topFrame);*/
        this.panelName = panelName;
        this.parentParentPanel = parentParentPanel;
        this.topFrame = topFrame;
        layoutComponents();
    }

    private void layoutComponents() {
        // Create the table model and override the isCellEditable method
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make the first seven columns non-editable
                return column >= 7;
            }
        };
        tableModel.addColumn("Product Code");
        tableModel.addColumn("Brand");
        tableModel.addColumn("Product Name");
        tableModel.addColumn("Product Type");
        tableModel.addColumn("Price");
        tableModel.addColumn("Quantity");
        tableModel.addColumn("IsDigital");
        tableModel.addColumn("Add to Basket");

        // Create the table with the custom model
        table = new JTable(tableModel);
        table.getTableHeader().setReorderingAllowed(false);

        List<Controller> controllerList = ProductService.getAllControllers();
        String[] rowData = new String[tableModel.getColumnCount()];
        for (Controller loc : controllerList) {
            rowData[0] = loc.getProductCode();
            rowData[1] = loc.getBrand();
            rowData[2] = loc.getProductName();
            rowData[3] = loc.getProductType().toString();
            rowData[4] = "" + loc.getPrice();
            rowData[5] = "" + loc.getQuantity();
            rowData[6] = "" + loc.isDigital();
            rowData[7] = "Add to Basket";

            // Add the row data to the table model
            tableModel.addRow(rowData);
        }
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        add(Main.createBackButton("MenuScreen"), BorderLayout.SOUTH);
    }
}