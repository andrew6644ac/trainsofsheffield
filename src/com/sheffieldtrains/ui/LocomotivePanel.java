package com.sheffieldtrains.ui;
import com.sheffieldtrains.domain.product.Locomotive;
import com.sheffieldtrains.service.ProductService;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import javax.swing.*;
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
        tableModel.addColumn("DccCode");
        tableModel.addColumn("Add to Basket");

        // Create the table with the custom model
        table = new JTable(tableModel);
        table.getTableHeader().setReorderingAllowed(false);

        List<Locomotive> locomotiveList= ProductService.getAllLocomotives();
        String[] rowData=new String[tableModel.getColumnCount()];
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
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        add(Main.createBackButton("MenuScreen"), BorderLayout.SOUTH);
    }
}
