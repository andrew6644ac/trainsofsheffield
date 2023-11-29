package com.sheffieldtrains.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TopUIPanel extends JPanel {
    protected DefaultTableModel tableModel = new DefaultTableModel();
    protected JTable table = new JTable(tableModel);

    public TopUIPanel(String panelName, JPanel parentParentPanel, JFrame topFrame) {
        super(null);
        this.panelName = panelName;
        this.parentParentPanel = parentParentPanel;
        this.topFrame = topFrame;
    }

    //   private JPanel login_panel = new JPanel(null);
    protected String panelName;
    protected JPanel parentParentPanel;
    protected JFrame topFrame;

    public TopUIPanel(BorderLayout borderLayout) {
        super(borderLayout);
    }

    public String getPanelName() {
        return panelName;
    }

    public JPanel getParentParentPanel() {
        return parentParentPanel;
    }

    public JFrame getTopFrame() {
        return topFrame;
    }

    public JTable getTable() {
        return table;
    }
}
