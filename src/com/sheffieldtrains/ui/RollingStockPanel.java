package com.sheffieldtrains.ui;

import com.sheffieldtrains.domain.product.RollingStock;
import com.sheffieldtrains.service.ProductService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class RollingStockPanel extends TopUIPanel {

    public RollingStockPanel(String panelName, JPanel parentParentPanel, JFrame topFrame) {
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
                return column >= 8;
            }
        };
        tableModel.addColumn("Product Code");
        tableModel.addColumn("Brand");
        tableModel.addColumn("Product Name");
        tableModel.addColumn("Product Type");
        tableModel.addColumn("Price");
        tableModel.addColumn("Quantity");
        tableModel.addColumn("EraCode");
        tableModel.addColumn("RollingStockType");
        tableModel.addColumn("Add to Basket");
        List<RollingStock> rollingStockList = ProductService.getAllRollingStocks();
        String[] rowData = new String[tableModel.getColumnCount()];

        // Create the table with the custom model
        table = new JTable(tableModel);
        table.getTableHeader().setReorderingAllowed(false);

        for (RollingStock loc : rollingStockList) {
            rowData[0] = loc.getProductCode();
            rowData[1] = loc.getBrand();
            rowData[2] = loc.getProductName();
            rowData[3] = loc.getProductType().toString();
            rowData[4] = "" + loc.getPrice();
            rowData[5] = "" + loc.getQuantity();
            rowData[6] = loc.getEraCode();
            rowData[7] = loc.getRollingStockType().toString();
            rowData[8] = "Add to Basket";
            tableModel.addRow(rowData);
            JScrollPane rollingStockScrollPane = new JScrollPane(table);
            add(rollingStockScrollPane, BorderLayout.CENTER);
            add(Main.createBackButton("MenuScreen"), BorderLayout.SOUTH);
        }
    }
}