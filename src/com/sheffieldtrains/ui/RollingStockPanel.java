package com.sheffieldtrains.ui;

import com.sheffieldtrains.domain.product.RollingStock;
import com.sheffieldtrains.service.ProductService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class RollingStockPanel extends TopUIPanel {

   /* private DefaultTableModel tableModel = new DefaultTableModel();
    private JTable rollingStockTable = new JTable(tableModel1);*/

    public RollingStockPanel(String panelName, JPanel parentParentPanel, JFrame topFrame) {
        super(new BorderLayout());
        /*super(panelName, parentParentPanel, topFrame);*/
        this.panelName = panelName;
        this.parentParentPanel = parentParentPanel;
        this.topFrame = topFrame;
        layoutComponents();
    }

    private void layoutComponents() {
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
        //int columnCount=0;
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

            // -------- RollingStock Table Model --------
    /*DefaultTableModel rollingStockModel = new DefaultTableModel(new String[]{"Product Code", "Brand", "Product Name", "Product Type", "Price", "Quantity","Gauge", "Add to Basket"}, 0);
        rollingStockModel.addRow(new Object[]{"S001", "Bachmann", "GWR Toad Guards Van", "34.99", "RollingStock", "4", "OO_GAUGE"});
        rollingStockModel.addRow(new Object[]{"S002", "Bachmann", "LNER Gresley Composite Coach", "45.99", "RollingStock", "3", "TT_GAUGE"});
        rollingStockModel.addRow(new Object[]{"S003", "Bachmann", "BR Mark 1 Coach", "42.99", "RollingStock", "5", "N_GAUGE"});
*/
            //rollingStockTable
   /* rollingStockTable = new JTable(rollingStockModel);
    ButtonColumn addButtonColumnRollingStock = new ButtonColumn(rollingStockTable, 7, basketTableModel, false,false,false);*/
            JScrollPane rollingStockScrollPane = new JScrollPane(table);
            /* JPanel rollingStockPanel = new JPanel(new BorderLayout());*/
            add(rollingStockScrollPane, BorderLayout.CENTER);
            add(Main.createBackButton("MenuScreen"), BorderLayout.SOUTH);
            //        cardHolder.add(rollingStockPanel, "RollingStockPanel");
        }
    }

    public JTable getTable(){
        return table;
    }
}