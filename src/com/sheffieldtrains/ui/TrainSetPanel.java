package com.sheffieldtrains.ui;

import com.sheffieldtrains.domain.product.TrackPack;
import com.sheffieldtrains.domain.product.TrainSet;
import com.sheffieldtrains.service.ProductService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TrainSetPanel extends TopUIPanel {

    public TrainSetPanel(String panelName, JPanel parentParentPanel, JFrame topFrame) {
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
        tableModel.addColumn("EraCode);");
        tableModel.addColumn("Contents");
        tableModel.addColumn("Add to Basket");
        List<TrainSet> trainSetList = ProductService.getAllTrainSets();
        String[] rowData = new String[tableModel.getColumnCount()];
        //int columnCount=0;
        for (TrainSet loc : trainSetList) {
            rowData[0] = loc.getProductCode();
            rowData[1] = loc.getBrand();
            rowData[2] = loc.getProductName();
            rowData[3] = loc.getProductType().toString();
            rowData[4] = "" + loc.getPrice();
            rowData[5] = "" + loc.getQuantity();
            rowData[6] = loc.getEraCode();
            rowData[7] = loc.getContents();
            rowData[8] = "Add to Basket";
            tableModel.addRow(rowData);

            JScrollPane scrollPane = new JScrollPane(table);
            add(scrollPane, BorderLayout.CENTER);
            add(Main.createBackButton("MenuScreen"), BorderLayout.SOUTH);
        }
    }

}
