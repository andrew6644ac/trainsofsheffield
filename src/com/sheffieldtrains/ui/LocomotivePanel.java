package com.sheffieldtrains.ui;

import com.sheffieldtrains.domain.product.DCCCode;
import com.sheffieldtrains.domain.product.Locomotive;
import com.sheffieldtrains.service.ProductService;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class LocomotivePanel extends TopUIPanel {
    private DefaultTableModel tableModel = new DefaultTableModel();
    private JTable table = new JTable(tableModel);

    public LocomotivePanel(String panelName, JPanel parentParentPanel, JFrame topFrame) {
        super(new BorderLayout());
        /*super(panelName, parentParentPanel, topFrame);*/
        this.panelName=panelName;
        this.parentParentPanel=parentParentPanel;
        this.topFrame=topFrame;
        layoutComponents();
    }

    public JTable getTable(){
        return table;
    }

    private void layoutComponents() {
        // ------locomotive
        tableModel.addColumn("Product Code");
        tableModel.addColumn("Brand");
        tableModel.addColumn("Product Name");
        tableModel.addColumn("Product Type");
        tableModel.addColumn("Price");
        tableModel.addColumn("Quantity");
        tableModel.addColumn("EraCode");
        tableModel.addColumn("DccCode");
        tableModel.addColumn("Add to Basket");
        List<Locomotive> locomotiveList= ProductService.getAllLocomotives();
        String[] rowData=new String[tableModel.getColumnCount()];
        //int columnCount=0;
        for(Locomotive loc: locomotiveList){
            rowData[0]=loc.getProductCode();
            rowData[1]=loc.getBrand();
            rowData[2]=loc.getProductName();
            rowData[3]=loc.getProductType().toString();
            rowData[4]=""+loc.getPrice();
            rowData[5]=""+ loc.getQuantity();
            rowData[6]=loc.getEraCode();
            rowData[7]=loc.getDccCode().toString();
            rowData[8]="Add to Basket";
            tableModel.addRow(rowData);
        }
// 添加示例数据，这里您可以连接数据库并加载真实数据
  /*      tableModel.addRow(new Object[]{"L001", "Hornby", "Class A3 \"Flying Scotsman\"","Locomotive", "199.99", "OO_GAUGE", "5", "Add to Basket"});
        tableModel.addRow(new Object[]{"L002", "Hornby", "Class A4 \"Mallard\"","Locomotive",  "220.99", "OO_GAUGE","7", "Add to Basket"});*/
       /* JPanel locomotivePanel = new JPanel(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(table);
        locomotivePanel.add(scrollPane, BorderLayout.CENTER);
        locomotivePanel.add(Main.createBackButton("MenuScreen"), BorderLayout.SOUTH);*/
//        parentParentPanel.add(locomotivePanel, "LocomotivePanel");

        // The ButtonColumn needs to know which table model is the basket model, so it can add rows to it
        /*ButtonColumn addButtonColumn = new ButtonColumn(table, 7, basketTableModel, false,false,false);*/
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        add(Main.createBackButton("MenuScreen"), BorderLayout.SOUTH);
    }
}
