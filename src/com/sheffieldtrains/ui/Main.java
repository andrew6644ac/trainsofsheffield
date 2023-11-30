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
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;
import javax.swing.JFrame;

import javax.swing.table.*;

class ButtonColumn extends AbstractCellEditor implements TableCellRenderer, TableCellEditor, ActionListener {
    private JTable table;
    private JButton renderButton;
    private JButton editButton;
    private String text;
    private DefaultTableModel basketModel;
    private boolean isDeleteButton;
    private boolean isAddButton;
    private boolean isRemoveButton;

    public ButtonColumn(JTable table, int column, DefaultTableModel basketModel,
                        boolean isDeleteButton, boolean isAddButton, boolean isRemoveButton) {
        super();
        this.table = table;
        this.basketModel = basketModel;
        this.isDeleteButton = isDeleteButton;
        this.isAddButton = isAddButton;
        this.isRemoveButton = isRemoveButton;

        renderButton = new JButton();
        editButton = new JButton();
        editButton.setFocusPainted(false);
        editButton.addActionListener(this);

        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(column).setCellRenderer(this);
        columnModel.getColumn(column).setCellEditor(this);
    }

    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        if (isAddButton) {
            renderButton.setText("+");
        } else if (isRemoveButton) {
            renderButton.setText("-");
        } else if (isDeleteButton) {
            renderButton.setText("Delete");
        } else {
            renderButton.setText("Add to Basket");
        }

        return renderButton;
    }

    @Override
    public Component getTableCellEditorComponent(
            JTable table, Object value, boolean isSelected, int row, int column) {

        editButton.setText(value == null ? "" : value.toString());
        return editButton;
    }

    @Override
    public Object getCellEditorValue() {
        return text;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        fireEditingStopped(); // Stop cell editing and save any changes.

        int viewRow = table.getSelectedRow();
        if (viewRow != -1) {
            int modelRow = table.convertRowIndexToModel(viewRow);

            if (isDeleteButton) {
                basketModel.removeRow(modelRow);
            } else if (isAddButton) {
                int quantity = (Integer) basketModel.getValueAt(modelRow, 5);
                basketModel.setValueAt(quantity + 1, modelRow, 5);
            } else if (isRemoveButton) {
                int quantity = (Integer) basketModel.getValueAt(modelRow, 5);
                if (quantity > 1) {
                    basketModel.setValueAt(quantity - 1, modelRow, 5);
                }
            } else {
                // Assume this is the Add to Basket operation.
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                Object[] rowData = new Object[6];
                for (int i = 0; i < 5; i++) {
                    rowData[i] = model.getValueAt(modelRow, i);
                }
                rowData[5] = 1; // Initial quantity set to 1.
                basketModel.addRow(rowData);
            }

            table.revalidate();
            table.repaint();
        }
    }
}



public class Main {
    // Class-level variables for card layout
    private static JPanel cardHolder = new JPanel(new CardLayout());
    private static CardLayout cardLayout = (CardLayout) cardHolder.getLayout();

   /* private static DefaultTableModel tableModel = new DefaultTableModel();
    private static JTable table = new JTable(tableModel);*/


    private static DefaultTableModel rollingStockModel;
    private static JTable rollingStockTable;


    private static DefaultTableModel controllerModel;
    private static JTable controllerTable;


    private static DefaultTableModel trackModel;
    private static JTable trackTable;

    private static DefaultTableModel trackPackModel;
    private static JTable trackPackTable;


    private static DefaultTableModel  trainSetModel;
    private static JTable trainSetTable ;

    // ... existing class-level variables ...
     static DefaultTableModel basketTableModel = new DefaultTableModel(new String[]{"Product Code", "Brand", "Product Name", "Product Type", "Price","Quantity", "Increase", "Reduce", "Remove Item"}, 0);
     static JTable basketTable = new JTable(basketTableModel);
    private static OrderHistoryController orderHistoryController;

    private static EditDetailPanel editDetails_panel;

    public static OrderHistoryController getOrderHistoryController(){
        return orderHistoryController;
    }

    public static EditDetailPanel getEditDetails_panel(){
        return editDetails_panel;
    }

    // 计算购物车总价的方法
    public static double calculateTotalPrice(DefaultTableModel model) {
        double totalPrice = 0.0;
        for (int i = 0; i < model.getRowCount(); i++) {
            double price = 0.0;
            int quantity = 0;
            try {
                price = Double.parseDouble(model.getValueAt(i, 4).toString()); // 假设第五列是价格列
                quantity = Integer.parseInt(model.getValueAt(i, 5).toString()); // 假设第六列是数量列
                totalPrice += price * quantity; // 价格乘以数量
            } catch (NumberFormatException nfe) {
                System.err.println("无法解析价格或数量: " + nfe.getMessage());
            }
        }
        return totalPrice;
    }


    // 添加这个方法到 Main 类里面，但是在 main 方法之外
    // Add this method to the Main class, but outside of the main method
     static JButton createBackButton(String backPanelName) {
        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(150, 75)); // Set the preferred size
        backButton.setFont(new Font("Times New Roman", Font.BOLD, 24)); // Set the font size
        backButton.setMargin(new Insets(10, 20, 10, 20)); // Set the padding
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardHolder, backPanelName);
            }
        });
        return backButton;
    }



    public static void main(String[] args) {
        LoggedIn loggedIn = new LoggedIn();
        //Creates window with name in title bar and sets size of window
        JFrame frame = new JFrame("Trains Of Sheffield");
        frame.setSize(1200, 800);

        //creates a cardholder panel to hold all screens within



        // ------locomotive
        /*tableModel.addColumn("Product Code");
        tableModel.addColumn("Brand");
        tableModel.addColumn("Product Name");
        tableModel.addColumn("Product Type");
        tableModel.addColumn("Price");
        tableModel.addColumn("Quantity");
        tableModel.addColumn("Gauge");
        tableModel.addColumn("Add to Basket");
// 添加示例数据，这里您可以连接数据库并加载真实数据
        tableModel.addRow(new Object[]{"L001", "Hornby", "Class A3 \"Flying Scotsman\"","Locomotive", "199.99", "OO_GAUGE", "5", "Add to Basket"});
        tableModel.addRow(new Object[]{"L002", "Hornby", "Class A4 \"Mallard\"","Locomotive",  "220.99", "OO_GAUGE","7", "Add to Basket"});*/


// 创建包含表格的面板，并将其添加到 cardHolder
       /* JPanel locomotivePanel = new JPanel(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane(table);
        locomotivePanel.add(scrollPane, BorderLayout.CENTER);

        locomotivePanel.add(createBackButton("MenuScreen"), BorderLayout.SOUTH);*/
        LocomotivePanel locomotivePanel = new LocomotivePanel("LocomotivePanel", cardHolder, frame);
//        cardHolder.add(locomotivePanel, "LocomotivePanel");

        // The ButtonColumn needs to know which table model is the basket model, so it can add rows to it
//        ButtonColumn addButtonColumn = new ButtonColumn(table, 7, basketTableModel, false,false,false);
        ButtonColumn addButtonColumn = new ButtonColumn(locomotivePanel.getTable(), locomotivePanel.getTable().getColumnCount()-1, basketTableModel, false,false,false);


      /*  // -------- RollingStock Table Model --------
        DefaultTableModel rollingStockModel = new DefaultTableModel(new String[]{"Product Code", "Brand", "Product Name", "Product Type", "Price", "Quantity","Gauge", "Add to Basket"}, 0);
        rollingStockModel.addRow(new Object[]{"S001", "Bachmann", "GWR Toad Guards Van", "34.99", "RollingStock", "4", "OO_GAUGE"});
        rollingStockModel.addRow(new Object[]{"S002", "Bachmann", "LNER Gresley Composite Coach", "45.99", "RollingStock", "3", "TT_GAUGE"});
        rollingStockModel.addRow(new Object[]{"S003", "Bachmann", "BR Mark 1 Coach", "42.99", "RollingStock", "5", "N_GAUGE"});

        //rollingStockTable
        rollingStockTable = new JTable(rollingStockModel);
        ButtonColumn addButtonColumnRollingStock = new ButtonColumn(rollingStockTable, 7, basketTableModel, false,false,false);
        JScrollPane rollingStockScrollPane = new JScrollPane(rollingStockTable);
        JPanel rollingStockPanel = new JPanel(new BorderLayout());
        rollingStockPanel.add(rollingStockScrollPane, BorderLayout.CENTER);
        rollingStockPanel.add(createBackButton("MenuScreen"), BorderLayout.SOUTH);
        cardHolder.add(rollingStockPanel, "RollingStockPanel");*/

        RollingStockPanel rollingStockPanel=new RollingStockPanel("RollingStockPanel", cardHolder, frame);
        ButtonColumn addButtonColumnRollingStock = new ButtonColumn(rollingStockPanel.getTable(), rollingStockPanel.getTable().getColumnCount()-1, basketTableModel, false,false,false);

        // -------- Controller Table Model --------
       /* DefaultTableModel controllerModel = new DefaultTableModel(new String[]{"Product Code", "Brand", "Product Name",  "Product Type", "Price", "Quantity", "Gauge","Add to Basket"}, 0);
        controllerModel.addRow(new Object[]{"C001", "GenericBrand", "Standard Controller", "27.99", "Controller", "3"});
        controllerModel.addRow(new Object[]{"C002", "GenericBrand", "DCC Controller", "58.99", "Controller", "5"});
        controllerModel.addRow(new Object[]{"C003", "GenericBrand", "DCC Elite Controller", "75.99", "Controller", "9"});


        // Controller Table
        controllerTable = new JTable(controllerModel);
        ButtonColumn addButtonColumnController = new ButtonColumn(controllerTable, 7, basketTableModel, false,false,false);
        JScrollPane controllerScrollPane = new JScrollPane(controllerTable);
        JPanel controllerPanel = new JPanel(new BorderLayout());
        controllerPanel.add(controllerScrollPane, BorderLayout.CENTER);
        controllerPanel.add(createBackButton("MenuScreen"), BorderLayout.SOUTH);
        cardHolder.add(controllerPanel, "ControllerPanel");*/
        ControllerPanel controllerPanel = new ControllerPanel("ControllerPanel", cardHolder, frame);
        ButtonColumn addButtonColumnController = new ButtonColumn(controllerPanel.getTable(),
                                                            controllerPanel.getTable().getColumnCount()-1,
                                                                    basketTableModel,
                                                        false,
                                                            false,
                                                        false);


        // -------- Track Table Model --------
       /* DefaultTableModel trackModel = new DefaultTableModel(new String[]{"Product Code", "Brand", "Product Name", "Product Type", "Price","Quantity", "Gauge","Add to Basket"}, 0);
        trackModel.addRow(new Object[]{"R001", "Peco", "2nd Radius Starter Oval", "15.99", "Track", "6", "OO_GAUGE"});
        trackModel.addRow(new Object[]{"R002", "Peco", "3rd Radius Starter Oval", "17.99", "Track", "7", "TT_GAUGE"});
        trackModel.addRow(new Object[]{"R003", "Peco", "4th Radius Starter Oval", "19.99", "Track", "9", "N_GAUGE"});



        // Track Table
        trackTable = new JTable(trackModel);
        ButtonColumn addButtonColumnTrack = new ButtonColumn(trackTable, 7, basketTableModel, false,false,false);
        JScrollPane trackScrollPane = new JScrollPane(trackTable);
        JPanel trackPanel = new JPanel(new BorderLayout());
        trackPanel.add(trackScrollPane, BorderLayout.CENTER);
        trackPanel.add(createBackButton("MenuScreen"), BorderLayout.SOUTH);
        cardHolder.add(trackPanel, "TrackPanel");*/

        TrackPanel trackPanel = new TrackPanel("TrackPanel", cardHolder, frame);
        ButtonColumn addButtonColumnTrack = new ButtonColumn(trackPanel.getTable(), trackPanel.getTable().getColumnCount()-1, basketTableModel, false,false,false);


        // -------- Track Table Model --------

        /*DefaultTableModel trackPackModel = new DefaultTableModel(new String[]{"Product Code", "Brand", "Product Name", "Product Type", "Price", "Quantity", "Gauge","Add to Basket"}, 0);
        trackPackModel.addRow(new Object[]{"P001", "PackBrand", "Starter Pack", "39.99", "TrackPack", "4", "OO_GAUGE"});
        trackPackModel.addRow(new Object[]{"P002", "PackBrand", "Extension Pack A", "49.99", "TrackPack", "4", "TT_GAUGE"});
        trackPackModel.addRow(new Object[]{"P003", "PackBrand", "Expansion Pack B", "59.99", "TrackPack", "2", "N_GAUGE"});
// ... Add to JScrollPane and JPanel as before ...
        // Track Pack Table
        trackPackTable = new JTable(trackPackModel);
        ButtonColumn addButtonColumnTrackPack = new ButtonColumn(trackPackTable, 7, basketTableModel, false,false,false);
        JScrollPane trackPackScrollPane = new JScrollPane(trackPackTable);
        JPanel trackPackPanel = new JPanel(new BorderLayout());
        trackPackPanel.add(trackPackScrollPane, BorderLayout.CENTER);
        trackPackPanel.add(createBackButton("MenuScreen"), BorderLayout.SOUTH);
        cardHolder.add(trackPackPanel, "TrackPackPanel");*/

        TrackPackPanel trackPackPanel = new TrackPackPanel("TrackPackPanel", cardHolder, frame);
        ButtonColumn addButtonColumnTrackPack = new ButtonColumn(trackPackPanel.getTable(), trackPackPanel.getTable().getColumnCount()-1, basketTableModel, false,false,false);



        // -------- Train Set Table Model --------
      /*  DefaultTableModel trainSetModel = new DefaultTableModel(new String[]{"Product Code", "Brand", "Product Name", "Product Type", "Price","Quantity", "Gauge","Add to Basket"}, 0);
        trainSetModel.addRow(new Object[]{"M001", "Eurostar", "Eurostar Train Set", "TrainSet","399.99", "7",  "OO_GAUGE", });
        trainSetModel.addRow(new Object[]{"M002", "Mallard", "Mallard Record Breaker Train Set","TrainSet", "349.99", "5",  "OO_GAUGE", });
        trainSetModel.addRow(new Object[]{"M003", "FlyingScotsman", "Flying Scotsman Train Set", "TrainSet","299.99","9", "OO_GAUGE",});
        // Train Set Table
        trainSetTable = new JTable(trainSetModel);
        ButtonColumn addButtonColumnTrainSet = new ButtonColumn(trainSetTable, 7, basketTableModel, false,false,false);
        JScrollPane trainSetScrollPane = new JScrollPane(trainSetTable);
        JPanel trainSetPanel = new JPanel(new BorderLayout());
        trainSetPanel.add(trainSetScrollPane, BorderLayout.CENTER);
        trainSetPanel.add(createBackButton("MenuScreen"), BorderLayout.SOUTH);
        cardHolder.add(trainSetPanel, "TrainSetPanel");*/

        TrainSetPanel trainSetPanel = new TrainSetPanel("TrainSetPanel", cardHolder, frame);
        ButtonColumn addButtonColumnTrainSet = new ButtonColumn(trainSetPanel.getTable(), trainSetPanel.getTable().getColumnCount()-1, basketTableModel, false,false,false);


        //set up login panel
        JPanel login_panel = new LoginPanel("LoginScreen", cardHolder, frame);

        //Creates a main menu panel
        JPanel menu_panel = new JPanel(null);

        //adds logout button
        JButton logout_bt1 = new JButton("Logout");
        logout_bt1.setBounds(1025, 10, 150, 75);
        logout_bt1.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        menu_panel.add(logout_bt1);

        //Action listener for logout button
        ActionListener logout_pressed1 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //changes screen to Login Screen
                CardLayout cardLayout = (CardLayout) cardHolder.getLayout();
                cardLayout.show(cardHolder, "LoginScreen");
            }
        };

        //adds account button
        JButton account_bt = new JButton("Admin");
        account_bt.setBounds(25, 10, 150, 75);
        account_bt.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        menu_panel.add(account_bt);

        //Action listener for account button
        ActionListener account_pressed = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //changes screen to Account Screen
                CardLayout cardLayout = (CardLayout) cardHolder.getLayout();
                cardLayout.show(cardHolder, "AccountScreen");
            }
        };

        if (Main.basketTableModel.getColumnCount() == 0) {
        }


// 为删除按钮添加事件处理器
        // Delete button for the basket table
        ButtonColumn deleteButtonColumn = new ButtonColumn(basketTable, 8, basketTableModel, true,false,false){
            @Override
            public void actionPerformed(ActionEvent e) {
                // This stops any cell editing that is currently happening
                fireEditingStopped();

                // Get the selected row from the view
                int viewRow = basketTable.getSelectedRow();

                // Only proceed if a row is actually selected
                if (viewRow != -1) {
                    // Convert the row index from the view's coordinate system to the model's
                    int modelRow = basketTable.convertRowIndexToModel(viewRow);

                    // Remove the row from the model
                    basketTableModel.removeRow(modelRow);

                    // Notify the table that the model data has changed and repaint the table
                    basketTable.revalidate();
                    basketTable.repaint();
                }
            }
        };
// 创建按钮列
        ButtonColumn addQuantityButtonColumn = new ButtonColumn(basketTable, 6, basketTableModel, false, true, false);
        ButtonColumn removeQuantityButtonColumn = new ButtonColumn(basketTable, 7, basketTableModel, false, false, true);
// 设置列的首选宽度，如果需要的话
// 例如：Main.basketTable.getColumnModel().getColumn(0).setPreferredWidth(100);

// 创建包含篮子表格的滚动窗格
//        JScrollPane basketScrollPane = new JScrollPane(Main.basketTable);

// 创建将包含篮子表格的面板
       /* JPanel basketPanel = new JPanel(new BorderLayout());

        basketPanel.add(basketScrollPane, BorderLayout.CENTER);

// 添加'返回'按钮到面板
        basketPanel.add(createBackButton("MenuScreen"), BorderLayout.SOUTH);
*/
// 将篮子面板添加到卡片布局容器
//        cardHolder.add(basketPanel, "BasketPanel");



        BasketPanel basketPanel=new BasketPanel("BasketPanel", cardHolder,  frame);

        basketPanel.setTable(basketTable, basketTableModel);

        //adds view basket button
        JButton viewBasket_bt = new JButton("View Basket");
        viewBasket_bt.setBounds(480, 680, 250, 75);
        viewBasket_bt.setFont(new Font("Times New Roman", Font.BOLD, 25));
        menu_panel.add(viewBasket_bt);
// 在basketPanel上方预留一个位置来放置总价标签
        JLabel totalPriceLabel = new JLabel("Total Price: $0.00");
        totalPriceLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
        totalPriceLabel.setBounds(300, 680, 250, 75);
// 添加确认订单的按钮
       /* JButton confirmOrderButton = new JButton("Confirm Order");
        confirmOrderButton.setFont(new Font("Times New Roman", Font.BOLD, 25));
        confirmOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 计算购物车总价
            *//*    double totalPrice = calculateTotalPrice(basketTableModel);

                // 输出总价以供调试
                System.out.println("计算的总价: $" + String.format("%.2f", totalPrice));

                // 更新总价标签的文本
                totalPriceLabel.setText("Total Price: $" + String.format("%.2f", totalPrice));*//*
               *//* if (UserSession.getCurrentUser().hasBankDetail()) {*//*
                   Order order = createOrderFromTableModel(basketTableModel);
                   if (!order.isEmpty()) {
                       OrderService.confirmOrderForUser(UserSession.getCurrentUser().getUserId(), order);
                   }
                basketTableModel.setRowCount(0);
                cardLayout.show(cardHolder, "MenuScreen");
                }

        });*/




/*// 创建一个面板来容纳总价标签和按钮
        JPanel bottomPanel = new JPanel(new BorderLayout());

// 添加总价标签到底部面板的北部
        bottomPanel.add(totalPriceLabel, BorderLayout.CENTER);*/

// 创建一个新的面板来容纳"Back"和"Confirm Order"按钮
//        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
// 设置按钮的首选大小
//        confirmOrderButton.setPreferredSize(new Dimension(250, 75));
        viewBasket_bt.setPreferredSize(new Dimension(250, 75));

/*// 将"Back"按钮和"Confirm Order"按钮添加到按钮面板
        buttonPanel.add(createBackButton("MenuScreen"));
        buttonPanel.add(confirmOrderButton);

// 将按钮面板添加到底部面板的南部
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

// 将底部面板添加到购物车面板的底部
        basketPanel.add(bottomPanel, BorderLayout.SOUTH);

// 刷新界面以显示添加的组件
        basketPanel.revalidate();
        basketPanel.repaint();*/





        //Action listener for account button
        ActionListener viewBasket_pressed = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //changes screen to basket Screen
                // Switch to the basket panel to show the table
                cardLayout.show(cardHolder, "BasketPanel");
            }
        };



        //adds temp label
        JLabel temp_lb = new JLabel("Select Category to View Product");
        temp_lb.setBounds(450, 150, 350, 30);
        temp_lb.setFont(new Font("Times New Roman", Font.BOLD, 20));
        menu_panel.add(temp_lb);

        String[] stock_types = {"Locomotive", "Rolling Stock", "Controller", "Track", "Track Pack", "Train Set"};
        JComboBox<String> stock_sort_CB = new JComboBox<>(stock_types);
        // ... [添加 JComboBox 相关代码之后]

        stock_sort_CB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 获取选中的项
                String stock_selected = (String) stock_sort_CB.getSelectedItem();
                temp_lb.setText("Selected Category is: " + stock_selected);
                CardLayout cardLayout = (CardLayout) cardHolder.getLayout();
                // 如果选中了 "Locomotive"，显示表格面板
                if ("Locomotive".equals(stock_selected)) {
                    cardLayout.show(cardHolder, "LocomotivePanel");
                } else if ("Rolling Stock".equals(stock_selected)) {
                    cardLayout.show(cardHolder, "RollingStockPanel");
                } else if ("Controller".equals(stock_selected)) {
                    cardLayout.show(cardHolder, "ControllerPanel");
                } else if ("Track".equals(stock_selected)) {
                    cardLayout.show(cardHolder, "TrackPanel");
                } else if ("Track Pack".equals(stock_selected)) {
                    cardLayout.show(cardHolder, "TrackPackPanel");
                } else if ("Train Set".equals(stock_selected)) {
                    cardLayout.show(cardHolder, "TrainSetPanel");
                }
// 注意，对于每个选项，您需要创建并添加一个对应的面板到cardHolder中。

                menu_panel.repaint();
            }
        });

// ... [添加其余组件的代码]


        stock_sort_CB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the selected item
                String stock_selected = (String) stock_sort_CB.getSelectedItem();
                temp_lb.setText("Selected Category is: " + stock_selected);
                menu_panel.repaint();
            }
        });

        stock_sort_CB.setBounds(510, 150, 180, 150);
        menu_panel.add(stock_sort_CB);
        menu_panel.repaint();

        //Add buttons for staff and moderator if logged in as so.
        //Add scroll pane
        //can update labels not delete and replace


        //Creates sign up panel
        /*JPanel signup_panel = new JPanel(null);*/
        JPanel signup_panel = new SignUpPanel( "SignupScreen", cardHolder,  frame);

        //adds a title in the centre at the top in bold font size 40
        JLabel title_su = new JLabel("Sign Up");
        title_su.setBounds(540, 25, 600, 45);
        title_su.setFont(new Font("Times New Roman", Font.BOLD, 40));
        signup_panel.add(title_su);

        //adds logout button
        JButton logout_bt2 = new JButton("Back");
        logout_bt2.setBounds(1025, 10, 150, 75);
        logout_bt2.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        signup_panel.add(logout_bt2);

        //Action listener for logout button
        ActionListener logout_pressed2 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //changes screen to Login Screen
                CardLayout cardLayout = (CardLayout) cardHolder.getLayout();
                cardLayout.show(cardHolder, "LoginScreen");
            }
        };


        //Creates account panel
        AccountPanel account_panel = new AccountPanel("AccountScreen", cardHolder,  frame);
        /*JPanel account_panel = new JPanel(null);

        //adds a title in the centre at the top in bold font size 40
        JLabel title_ac = new JLabel("Account");
        title_ac.setBounds(540, 25, 600, 45);
        title_ac.setFont(new Font("Times New Roman", Font.BOLD, 40));
        account_panel.add(title_ac);

        //adds back button
        JButton back_bt2 = new JButton("Back");
        back_bt2.setBounds(1025, 10, 150, 75);
        back_bt2.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        account_panel.add(back_bt2);

        //Action listener for back button
        ActionListener back_pressed2 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //changes screen to Menu Screen
                CardLayout cardLayout = (CardLayout) cardHolder.getLayout();
                cardLayout.show(cardHolder, "MenuScreen");
            }
        };

        //adds Purchase History button
        JButton pHistory_bt = new JButton("Purchase History");
        pHistory_bt.setBounds(25, 10, 200, 75);
        pHistory_bt.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        account_panel.add(pHistory_bt);

        //Action listener for purchase history button
        ActionListener pHistory_pressed = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //changes screen to Account Screen
                CardLayout cardLayout = (CardLayout) cardHolder.getLayout();
                cardLayout.show(cardHolder, "PHistoryScreen");
                orderHistoryController.notifyChange();
            }
        };

        //adds bank details button
        JButton bDetails_bt = new JButton("Bank Details");
        bDetails_bt.setBounds(230, 10, 150, 75);
        bDetails_bt.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        account_panel.add(bDetails_bt);

        //Action listener for bank details button
        ActionListener bDetails_pressed = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //changes screen to Menu Screen
                CardLayout cardLayout = (CardLayout) cardHolder.getLayout();
                cardLayout.show(cardHolder, "BankDetailsScreen");
            }
        };

        //adds edit details button
        JButton eDetails_bt = new JButton("Edit Details");
        eDetails_bt.setBounds(865, 10, 150, 75);
        eDetails_bt.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        account_panel.add(eDetails_bt);

        //Action listener for edit details button
        ActionListener eDetails_pressed = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //changes screen to edit details screen
                CardLayout cardLayout = (CardLayout) cardHolder.getLayout();
                cardLayout.show(cardHolder, "EditDetailsScreen");
            }
        };

        //adds staff button
        JButton staff_bt = new JButton("Staff");
        staff_bt.setBounds(1025, 95, 150, 75);
        staff_bt.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        account_panel.add(staff_bt);

        //Action listener for edit details button
        ActionListener staff_pressed = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //changes screen to edit details screen
                //todo check if they have authority else put label saying they don't
                CardLayout cardLayout = (CardLayout) cardHolder.getLayout();
                cardLayout.show(cardHolder, "StaffScreen");
            }
        };
*/



        //Creates bank details panel
        JPanel bankDetails_panel = new JPanel(null);

        //adds a title in the centre at the top in bold font size 40
        JLabel title_bd = new JLabel("Bank Details");
        title_bd.setBounds(500, 25, 600, 45);
        title_bd.setFont(new Font("Times New Roman", Font.BOLD, 40));
        bankDetails_panel.add(title_bd);

        //adds back button
        JButton back_bt4 = new JButton("Back");
        back_bt4.setBounds(1025, 10, 150, 75);
        back_bt4.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        bankDetails_panel.add(back_bt4);

        //Action listener for back button
        ActionListener back_pressed4 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //changes screen to Account Screen
                CardLayout cardLayout = (CardLayout) cardHolder.getLayout();
                cardLayout.show(cardHolder, "AccountScreen");
            }
        };

        ActionListener backToAccountListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //changes screen to Account Screen
                CardLayout cardLayout = (CardLayout) cardHolder.getLayout();
                cardLayout.show(cardHolder, "AccountScreen");
            }
        };

        //adds card number label
        JLabel cardNumber_lb = new JLabel("Card Number:");
        cardNumber_lb.setBounds(466, 100, 250, 30);
        cardNumber_lb.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        bankDetails_panel.add(cardNumber_lb);

        //adds card number text box
        JTextField cardNumber_tb = new JTextField(20);
        cardNumber_tb.setBounds(580, 100, 270, 30);
        bankDetails_panel.add(cardNumber_tb);

        //adds expiration date label
        JLabel expiration_lb = new JLabel("Expiration Date:");
        expiration_lb.setBounds(448, 150, 250, 30);
        expiration_lb.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        bankDetails_panel.add(expiration_lb);

        //adds expiration date text box
        JTextField expiration_tb = new JTextField(20);
        expiration_tb.setBounds(580, 150, 270, 30);
        bankDetails_panel.add(expiration_tb);

        //adds cvv label
        JLabel cvv_lb = new JLabel("CVV Code:");
        cvv_lb.setBounds(490, 200, 250, 30);
        cvv_lb.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        bankDetails_panel.add(cvv_lb);

        //adds cvv text box
        JTextField cvv_tb = new JTextField(20);
        cvv_tb.setBounds(580, 200, 270, 30);
        bankDetails_panel.add(cvv_tb);
        //adds confirm button
        JButton confirm_bt_bd = new JButton("Confirm");
        confirm_bt_bd.setBounds(500, 680, 150, 75);
        confirm_bt_bd.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        bankDetails_panel.add(confirm_bt_bd);

        // Add Bank Card Name label and text field
        JLabel bankCardNameLabel = new JLabel("Bank Card Name:");
        bankCardNameLabel.setBounds(440, 250, 250, 30); // Adjust the position and size as needed
        bankCardNameLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        bankDetails_panel.add(bankCardNameLabel);

        JTextField bankCardNameTextField = new JTextField(20);
        bankCardNameTextField.setBounds(580, 250, 270, 30); // Adjust the position and size as needed
        bankDetails_panel.add(bankCardNameTextField);

// Add Card Holder Name label and text field
        JLabel cardHolderNameLabel = new JLabel("Card Holder Name:");
        cardHolderNameLabel.setBounds(425, 300, 250, 30); // Adjust the position and size as needed
        cardHolderNameLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        bankDetails_panel.add(cardHolderNameLabel);

        JTextField cardHolderNameTextField = new JTextField(20);
        cardHolderNameTextField.setBounds(580, 300, 270, 30); // Adjust the position and size as needed
        bankDetails_panel.add(cardHolderNameTextField);



        //Action listener for confirm button
        ActionListener confirm_pressed3 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //todo check info is valid including date, num and length
                //todo put info into db
                String cardNum_input = cardNumber_tb.getText();
                String expire_input = expiration_tb.getText();
                String cvv_input = cvv_tb.getText();
                String bankCardNameInput = bankCardNameTextField.getText();
                String cardHolderNameInput = cardHolderNameTextField.getText();
            }
        };





        editDetails_panel=new EditDetailPanel("EditDetailsScreen", cardHolder, frame);
        //Creates Edit Details panel
      /*  JPanel editDetails_panel = new JPanel(null);

        //adds a title in the centre at the top in bold font size 40
        JLabel title_ed = new JLabel("Edit User Details");
        title_ed.setBounds(500, 25, 600, 45);
        title_ed.setFont(new Font("Times New Roman", Font.BOLD, 40));
        editDetails_panel.add(title_ed);

        //adds back button
        JButton back_bt5 = new JButton("Back");
        back_bt5.setBounds(1025, 10, 150, 75);
        back_bt5.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        editDetails_panel.add(back_bt5);

        //Action listener for back button
        ActionListener back_pressed5 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //changes screen to Account Screen
                CardLayout cardLayout = (CardLayout) cardHolder.getLayout();
                cardLayout.show(cardHolder, "AccountScreen");
            }
        };

        //adds addInfo button
        JButton addInfo_bt = new JButton("Add Info");
        addInfo_bt.setBounds(525, 550, 150, 75);
        addInfo_bt.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        editDetails_panel.add(addInfo_bt);


        //adds email label
        JLabel email_lb_ed = new JLabel("Email:");
        email_lb_ed.setBounds(525, 100, 200, 30);
        email_lb_ed.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        editDetails_panel.add(email_lb_ed);

        //adds email text box
        JTextField email_tb_ed = new JTextField(20);
        email_tb_ed.setBounds(580, 100, 300, 30);
        editDetails_panel.add(email_tb_ed);

        //adds password label
        JLabel password_lb_ed = new JLabel("Password:");
        password_lb_ed.setBounds(498, 150, 250, 30);
        password_lb_ed.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        editDetails_panel.add(password_lb_ed);

        //adds password text box
        JTextField password_tb_ed = new JTextField(20);
        password_tb_ed.setBounds(580, 150, 270, 30);
        editDetails_panel.add(password_tb_ed);

        //adds forename label
        JLabel forename_lb_ed = new JLabel("Forename:");
        forename_lb_ed.setBounds(494, 200, 250, 30);
        forename_lb_ed.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        editDetails_panel.add(forename_lb_ed);

        //adds forename text box
        JTextField forename_tb_ed = new JTextField(20);
        forename_tb_ed.setBounds(580, 200, 270, 30);
        editDetails_panel.add(forename_tb_ed);

        //adds surname label
        JLabel surname_lb_ed = new JLabel("Surname:");
        surname_lb_ed.setBounds(503, 250, 250, 30);
        surname_lb_ed.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        editDetails_panel.add(surname_lb_ed);

        //adds surname text box
        JTextField surname_tb_ed = new JTextField(20);
        surname_tb_ed.setBounds(580, 250, 270, 30);
        editDetails_panel.add(surname_tb_ed);

        //adds surname label
        JLabel postcode_lb_ed = new JLabel("Postcode:");
        postcode_lb_ed.setBounds(502, 300, 250, 30);
        postcode_lb_ed.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        editDetails_panel.add(postcode_lb_ed);

        //adds surname text box
        JTextField postcode_tb_ed = new JTextField(20);
        postcode_tb_ed.setBounds(580, 300, 270, 30);
        editDetails_panel.add(postcode_tb_ed);


        //These are for the Address table:
        //adds house number label
        JLabel houseNumber_lb_ed = new JLabel("House Number:");
        houseNumber_lb_ed.setBounds(453, 350, 250, 30);
        houseNumber_lb_ed.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        editDetails_panel.add(houseNumber_lb_ed);

        //adds house number text box
        JTextField houseNumber_tb_ed = new JTextField(20);
        houseNumber_tb_ed.setBounds(580, 350, 270, 30);
        editDetails_panel.add(houseNumber_tb_ed);

        //adds road name label
        JLabel roadName_lb_ed = new JLabel("Road Name:");
        roadName_lb_ed.setBounds(480, 400, 250, 30);
        roadName_lb_ed.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        editDetails_panel.add(roadName_lb_ed);

        //adds road name text box
        JTextField roadName_tb_ed = new JTextField(20);
        roadName_tb_ed.setBounds(580, 400, 270, 30);
        editDetails_panel.add(roadName_tb_ed);

        //adds City name label
        JLabel cityName_lb_ed = new JLabel("City Name:");
        cityName_lb_ed.setBounds(488, 450, 250, 30);
        cityName_lb_ed.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        editDetails_panel.add(cityName_lb_ed);

        //adds City name text box
        JTextField cityName_tb_ed = new JTextField(20);
        cityName_tb_ed.setBounds(580, 450, 270, 30);
        editDetails_panel.add(cityName_tb_ed);

        //adds confirm button
        JButton confirm_bt_ed = new JButton("Confirm");
        confirm_bt_ed.setBounds(525, 650, 150, 75);
        confirm_bt_ed.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        editDetails_panel.add(confirm_bt_ed);

        ActionListener addInfo_pressed = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //todo Adds existing info to text boxes
                //todo gets user details where email is loggedIn.email
                email_tb_ed.setText(loggedIn.email);
                //todo somehow clear on logout
            }
        };

        //Action listener for edit details button
        ActionListener confirm_pressed = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //gets text from text boxes
                String email_input_ed = email_tb_ed.getText();
                String password_input_ed = password_tb_ed.getText();
                String forename_input_ed = forename_tb_ed.getText();
                String surname_input_ed = surname_tb_ed.getText();
                String postcode_input_ed = postcode_tb_ed.getText();
                String houseNum_input_ed = houseNumber_tb_ed.getText();
                String roadName_input_ed = roadName_tb_ed.getText();
                String cityName_input_ed = cityName_tb_ed.getText();

                //converts strings to arrays and finds lengths
                char[] email_array = email_input_ed.toCharArray();
                int email_length = email_array.length;
                char[] password_array = password_input_ed.toCharArray();
                int password_length = password_array.length;
                char[] forename_array = forename_input_ed.toCharArray();
                int forename_length = forename_array.length;
                char[] surname_array = surname_input_ed.toCharArray();
                int surname_length = surname_array.length;
                char[] postcode_array = postcode_input_ed.toCharArray();
                int postcode_length = postcode_array.length;
                char[] houseNum_array = houseNum_input_ed.toCharArray();
                int houseNum_length = houseNum_array.length;
                char[] roadName_array = roadName_input_ed.toCharArray();
                int roadName_length = roadName_array.length;
                char[] cityName_array = cityName_input_ed.toCharArray();
                int cityName_length = cityName_array.length;

                //if any text box is empty adds label to say so
                if (email_input_ed.isBlank() || password_input_ed.isBlank() || forename_input_ed.isBlank()
                        || surname_input_ed.isBlank() || postcode_input_ed.isBlank() || houseNum_input_ed.isBlank()
                        || roadName_input_ed.isBlank() || cityName_input_ed.isBlank()) {
                    JLabel incorrect1_lb_ed = new JLabel("Please ensure you fill all details");
                    incorrect1_lb_ed.setBounds(415, 550, 900, 30);
                    incorrect1_lb_ed.setFont(new Font("Times New Roman", Font.PLAIN, 20));
                    incorrect1_lb_ed.setForeground(Color.RED);
                    editDetails_panel.add(incorrect1_lb_ed);
                    frame.repaint();
                    //if any text box contains more characters than it's supposed to it adds a label to say so
                } else if (email_length > 50 || password_length > 20 || forename_length > 20 || surname_length > 20
                        || houseNum_length > 10 || postcode_length > 20 || roadName_length > 100 || cityName_length > 100) {
                    JLabel incorrect2_lb_ed = new JLabel("Please ensure your information does not exceed the length limit");
                    incorrect2_lb_ed.setBounds(390, 600, 900, 30);
                    incorrect2_lb_ed.setFont(new Font("Times New Roman", Font.PLAIN, 20));
                    incorrect2_lb_ed.setForeground(Color.RED);
                    editDetails_panel.add(incorrect2_lb_ed);
                    frame.repaint();
                    //todo check if info is already existing for different user if required
                } else {
                    //todo change details in db and on account page
                    //todo if email is changed update loggedIn.email
                    CardLayout cardLayout = (CardLayout) cardHolder.getLayout();
                    cardLayout.show(cardHolder, "AccountScreen");
                }
            }
        };*/






        //Creates purchase history panel
        OrderHisotryPanel pHistory_panel = new OrderHisotryPanel("PHistoryScreen", cardHolder, frame);
        orderHistoryController=new OrderHistoryController(pHistory_panel);
       /* JPanel pHistory_panel = new JPanel(null);

        //adds a title in the centre at the top in bold font size 40
        JLabel title_ph = new JLabel("Purchase History");
        title_ph.setBounds(500, 25, 600, 45);
        title_ph.setFont(new Font("Times New Roman", Font.BOLD, 40));
        pHistory_panel.add(title_ph);

        //adds back button
        JButton back_bt3 = new JButton("Back");
        back_bt3.setBounds(1025, 10, 150, 75);
        back_bt3.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        pHistory_panel.add(back_bt3);

        //Action listener for back button
        ActionListener back_pressed3 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //changes screen to Account Screen
                CardLayout cardLayout = (CardLayout) cardHolder.getLayout();
                cardLayout.show(cardHolder, "AccountScreen");
            }
        };
*/


        //Creates staff area panel
        JPanel staff_panel = new JPanel(null);

        // creates table(showing stocking)
        DefaultTableModel productModel = new DefaultTableModel();
        productModel.addColumn("Product Code");
        productModel.addColumn("Brand");
        productModel.addColumn("Product Name");
        productModel.addColumn("Price");
        productModel.addColumn("Product Type");
        productModel.addColumn("Quantity");
        productModel.addColumn("Gauge");

        try {
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://stusql.dcs.shef.ac.uk:3306/team066?user=team066&password=aNohqu4mo"
            );

            String sql = "SELECT productCode, brand, productName, price, productType, quantity,gauge FROM Product";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String productCode = rs.getString("productCode");
                String brand = rs.getString("brand");
                String productName = rs.getString("productName");
                double price = rs.getDouble("price");
                String productType = rs.getString("productType");
                int quantity = rs.getInt("quantity");
                String gauge = rs.getString("gauge");

                productModel.addRow(new Object[]{productCode, brand, productName, price, productType,quantity, gauge});
            }

            rs.close();
            ps.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();

        }

        // Create the table and add it to the scroll panel, then add it to the staff_panel
        JTable productTable = new JTable(productModel);
        JScrollPane productScrollPane = new JScrollPane(productTable);
        productScrollPane.setBounds(300, 100, 800, 600);
        staff_panel.add(productScrollPane);

        // creat Update changes button
        JButton confirmButton1 = new JButton("Update Changes");
        confirmButton1.setBounds(50, 500, 200, 50);
        confirmButton1.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        staff_panel.add(confirmButton1);
        // creat Reload product button
        JButton refreshButton = new JButton("Reload Product");
        refreshButton.setBounds(50, 600, 200, 50);
        refreshButton.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        staff_panel.add(refreshButton);
        //creat Add new product button
        JButton addItemButton = new JButton("Add New Product");
        addItemButton.setBounds(50, 200, 200, 50); // 设置按钮位置和大小
        addItemButton.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        staff_panel.add(addItemButton);
        //creat save new product button
        JButton insertDataButton = new JButton("Save New Product");
        insertDataButton.setBounds(50, 300, 200, 50); // 设置按钮的位置和大小
        insertDataButton.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        staff_panel.add(insertDataButton);
        //creat Delete selected product button
        JButton deleteButton = new JButton("Delete Selected Product");
        deleteButton.setBounds(50, 400, 200, 50);
        deleteButton.setFont(new Font("Times New Roman", Font.PLAIN, 15));
        staff_panel.add(deleteButton);

        //Action listener for Reload Button(also called refresh button)
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshProductTable(productModel);
            }
        });
        staff_panel.add(refreshButton);

        //Action listener for add new product button(also called additem button)
        addItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add a new row to the table with the number of columns matching the number of columns in the table
                productModel.addRow(new Object[]{"", "", "", "", "", "",""});
            }
        });

        //Action listener for Delete selected button(also call insertdata button)
        insertDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Gets the last row of the table (the newly added row)
                int lastRow = productModel.getRowCount() - 1;

                // Get data from a table
                String productCode = (String) productModel.getValueAt(lastRow, 0);
                String brand = (String) productModel.getValueAt(lastRow, 1);
                String productName = (String) productModel.getValueAt(lastRow, 2);
                Double price = null;
                Integer quantity = null;
                String gauge = (String) productModel.getValueAt(lastRow, 6);

                // Converts strings to Double and Integer
                try {
                    price = Double.parseDouble(productModel.getValueAt(lastRow, 3).toString());
                    quantity = Integer.parseInt(productModel.getValueAt(lastRow, 5).toString());
                } catch (NumberFormatException ex) {
                    // Process conversion error
                    JOptionPane.showMessageDialog(frame, "Invalid number format in Price or Quantity.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String productType = (String) productModel.getValueAt(lastRow, 4);

                // Call method to insert data into the database
                insertNewProductInDatabase(productCode, brand, productName, price, productType, quantity, gauge);
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSelectedProduct(productTable, productModel);
            }
        });


        //Action listener for Update change button (also called confirm button)
        //Event listeners designed to update the database
        confirmButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = productTable.getSelectedRow();
                if (selectedRow >= 0) {
                    // Get row data
                    String productCode = (String) productModel.getValueAt(selectedRow, 0);
                    String brand = (String) productModel.getValueAt(selectedRow, 1);
                    String productName = (String) productModel.getValueAt(selectedRow, 2);
                    // Type conversion: Converts a string to Double and Integer
                    Double price = null;
                    Integer quantity = null;
                    String gauge = (String) productModel.getValueAt(selectedRow, 6);
                    try {
                        price = Double.parseDouble(productModel.getValueAt(selectedRow, 3).toString());
                        quantity = Integer.parseInt(productModel.getValueAt(selectedRow, 5).toString());
                    } catch (NumberFormatException ex) {
                        ex.printStackTrace();
                        // Handle invalid number formats
                        JOptionPane.showMessageDialog(frame, "Invalid number format in Price or Quantity.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    String productType = (String) productModel.getValueAt(selectedRow, 4);

                    // updata data to database
                    updateProductInDatabase(productCode, brand, productName, price, productType, quantity,gauge );
                }
            }
        })
        ;

        //adds a title in the centre at the top in bold font size 40
        JLabel title_st = new JLabel("Staff Area");
        title_st.setBounds(500, 25, 600, 45);
        title_st.setFont(new Font("Times New Roman", Font.BOLD, 40));
        staff_panel.add(title_st);

        //adds back button
        JButton back_bt6 = new JButton("Back");
        back_bt6.setBounds(1025, 10, 150, 75);
        back_bt6.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        staff_panel.add(back_bt6);

        //Action listener for back button
        ActionListener back_pressed6 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //changes screen to Account Screen
                CardLayout cardLayout = (CardLayout) cardHolder.getLayout();
                cardLayout.show(cardHolder, "AccountScreen");
            }
        };

        //adds customer info button
        JButton cInfo_bt = new JButton("Customer Info");
        cInfo_bt.setBounds(815, 10, 200, 75);
        cInfo_bt.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        staff_panel.add(cInfo_bt);

        //Action listener for customer info button
        ActionListener cInfo_pressed = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //changes screen to customer info Screen
                CardLayout cardLayout = (CardLayout) cardHolder.getLayout();
                cardLayout.show(cardHolder, "CustomerInfoScreen");
            }
        };

        //adds promote/demote button
        JButton promote_bt = new JButton("Promote/Demote");
        promote_bt.setBounds(10, 10, 220, 75);
        promote_bt.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        staff_panel.add(promote_bt);

        //Action listener for promote info button
        ActionListener promote_pressed = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //changes screen to customer info Screen
                CardLayout cardLayout = (CardLayout) cardHolder.getLayout();
                cardLayout.show(cardHolder, "PromoteScreen");
            }
        };

        //adds order detail button
        JButton oDetail_bt = new JButton("Order details");
        oDetail_bt.setBounds(240, 10, 220, 75);
        oDetail_bt.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        staff_panel.add(oDetail_bt);

        //Action listener for promote info button
        ActionListener oDetail_pressed = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //changes screen to customer info Screen
                CardLayout cardLayout = (CardLayout) cardHolder.getLayout();
                cardLayout.show(cardHolder, "OrderDetailScreen");
            }
        };


        //Creates customer info area panel
        JPanel customerInfo_panel = new JPanel(null);

        //adds a title in the centre at the top in bold font size 40
        JLabel title_ci = new JLabel("Customer Info");
        title_ci.setBounds(500, 25, 600, 45);
        title_ci.setFont(new Font("Times New Roman", Font.BOLD, 40));
        customerInfo_panel.add(title_ci);

        //adds back button
        JButton back_bt7 = new JButton("Back");
        back_bt7.setBounds(1025, 10, 150, 75);
        back_bt7.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        customerInfo_panel.add(back_bt7);

        //Action listener for back button
        ActionListener back_pressed7 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //changes screen to Account Screen
                CardLayout cardLayout = (CardLayout) cardHolder.getLayout();
                cardLayout.show(cardHolder, "StaffScreen");
            }
        };

        //adds Email: label
        JLabel email_lb_ci = new JLabel("Email:");
        email_lb_ci.setBounds(300, 100, 70, 30);
        email_lb_ci.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        customerInfo_panel.add(email_lb_ci);

        //adds email text box
        JTextField email_tb_ci = new JTextField(20);
        email_tb_ci.setBounds(370, 100, 500, 30);
        customerInfo_panel.add(email_tb_ci);

        //adds search button
        JButton search_bt = new JButton("Search");
        search_bt.setBounds(880, 95, 150, 40);
        search_bt.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        customerInfo_panel.add(search_bt);

        //adds email label
        JLabel email_lb_ci2 = new JLabel("Email");
        email_lb_ci2.setBounds(525, 150, 400, 30);
        email_lb_ci2.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        customerInfo_panel.add(email_lb_ci2);

        //adds password label
        JLabel password_lb_ci = new JLabel("Password");
        password_lb_ci.setBounds(498, 200, 450, 30);
        password_lb_ci.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        customerInfo_panel.add(password_lb_ci);

        //adds forename label
        JLabel forename_lb_ci = new JLabel("Forename");
        forename_lb_ci.setBounds(494, 250, 450, 30);
        forename_lb_ci.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        customerInfo_panel.add(forename_lb_ci);

        //adds surname label
        JLabel surname_lb_ci = new JLabel("Surname");
        surname_lb_ci.setBounds(503, 300, 450, 30);
        surname_lb_ci.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        customerInfo_panel.add(surname_lb_ci);

        //adds postcode label
        JLabel postcode_lb_ci = new JLabel("Postcode");
        postcode_lb_ci.setBounds(502, 350, 450, 30);
        postcode_lb_ci.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        customerInfo_panel.add(postcode_lb_ci);

        //adds house number label
        JLabel houseNumber_lb_ci = new JLabel("House Number");
        houseNumber_lb_ci.setBounds(453, 400, 450, 30);
        houseNumber_lb_ci.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        customerInfo_panel.add(houseNumber_lb_ci);

        //adds road name label
        JLabel roadName_lb_ci = new JLabel("Road Name");
        roadName_lb_ci.setBounds(480, 450, 450, 30);
        roadName_lb_ci.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        customerInfo_panel.add(roadName_lb_ci);

        //adds City name label
        JLabel cityName_lb_ci = new JLabel("City Name");
        cityName_lb_ci.setBounds(488, 500, 450, 30);
        cityName_lb_ci.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        customerInfo_panel.add(cityName_lb_ci);

        //Action listener for search button
        ActionListener search_pressed = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //changes screen to searches and displays user info
                //todo get user info from db if email matches
                String emailInputted = email_tb_ci.getText();
                email_lb_ci2.setText(emailInputted);
            }
        };


        //Creates basket panel
        JPanel basket_panel = new JPanel(null);

        //adds a title in the centre at the top in bold font size 40
        JLabel title_bs = new JLabel("Basket");
        title_bs.setBounds(500, 25, 600, 45);
        title_bs.setFont(new Font("Times New Roman", Font.BOLD, 40));
        basket_panel.add(title_bs);

        //adds back button
        JButton back_bt8 = new JButton("Back");
        back_bt8.setBounds(1025, 10, 150, 75);
        back_bt8.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        basket_panel.add(back_bt8);

        //Action listener for back button
        ActionListener back_pressed8 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //changes screen to Account Screen
                CardLayout cardLayout = (CardLayout) cardHolder.getLayout();
                cardLayout.show(cardHolder, "MenuScreen");
            }
        };

        //adds confirm button
        JButton confirm_bt_bs = new JButton("Confirm");
        confirm_bt_bs.setBounds(510, 680, 150, 75);
        confirm_bt_bs.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        basket_panel.add(confirm_bt_bs);

        //Action listener for back button
        ActionListener confirm_pressed2 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //changes screen to Account Screen
                //todo check if bank details present in db and go to screen accordingly
                /*
                if (bank details in db == null) {
                    CardLayout cardLayout = (CardLayout) cardHolder.getLayout();
                    cardLayout.show(cardHolder, "BankDetailsScreen");
                } else {
                    CardLayout cardLayout = (CardLayout) cardHolder.getLayout();
                    cardLayout.show(cardHolder, "ConfirmScreen");
                }

                */
                CardLayout cardLayout = (CardLayout) cardHolder.getLayout();
                cardLayout.show(cardHolder, "ConfirmScreen");
            }
        };




        //Creates confirm panel
        JPanel confirm_panel = new JPanel(null);

        //adds a title in the centre at the top in bold font size 40
        JLabel title_cf = new JLabel("Confirmation");
        title_cf.setBounds(480, 25, 600, 45);
        title_cf.setFont(new Font("Times New Roman", Font.BOLD, 40));
        confirm_panel.add(title_cf);

        //adds back button
        JButton back_bt9 = new JButton("Menu");
        back_bt9.setBounds(1025, 10, 150, 75);
        back_bt9.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        confirm_panel.add(back_bt9);

        //Action listener for back button
        ActionListener back_pressed9 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //changes screen to Account Screen
                CardLayout cardLayout = (CardLayout) cardHolder.getLayout();
                cardLayout.show(cardHolder, "MenuScreen");
            }
        };

        //adds details about order
        JLabel details_lb = new JLabel("Order number... ");
        details_lb.setBounds(480, 150, 600, 45);
        details_lb.setFont(new Font("Times New Roman", Font.BOLD, 40));
        confirm_panel.add(details_lb);




        //Creates promote panel
        PromotePanel promote_panel = new PromotePanel("PromoteScreen", cardHolder, frame);

      /*  //adds a title in the centre at the top in bold font size 40
        JLabel title_pr = new JLabel("Promote/Demote");
        title_pr.setBounds(480, 25, 600, 45);
        title_pr.setFont(new Font("Times New Roman", Font.BOLD, 40));
        promote_panel.add(title_pr);

        //adds back button
        JButton back_bt10 = new JButton("Back");
        back_bt10.setBounds(1025, 10, 150, 75);
        back_bt10.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        promote_panel.add(back_bt10);

        //Action listener for back button
        ActionListener back_pressed10 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //changes screen to Account Screen
                CardLayout cardLayout = (CardLayout) cardHolder.getLayout();
                cardLayout.show(cardHolder, "StaffScreen");
            }
        };

*/


        //Creates order detail panel
//        JPanel orderDetail_panel = new JPanel(null);
        OrderDetailPanel orderDetail_panel =new OrderDetailPanel("OrderDetailScreen", cardHolder, frame);
        //adds a title in the centre at the top in bold font size 40
      /*  JLabel title_od = new JLabel("Order Details");
        title_od.setBounds(480, 25, 600, 45);
        title_od.setFont(new Font("Times New Roman", Font.BOLD, 40));
        orderDetail_panel.add(title_od);

        //adds back button
        JButton back_bt11 = new JButton("Back");
        back_bt11.setBounds(1025, 10, 150, 75);
        back_bt11.setFont(new Font("Times New Roman", Font.PLAIN, 25));
        orderDetail_panel.add(back_bt11);

        //Action listener for back button
        ActionListener back_pressed11 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //changes screen to Account Screen
                CardLayout cardLayout = (CardLayout) cardHolder.getLayout();
                cardLayout.show(cardHolder, "StaffScreen");
            }
        };

        // Initialize the table model
        DefaultTableModel model = new DefaultTableModel();
       *//* model.addColumn("email");
        model.addColumn("Order ID");
        model.addColumn("Product Code");
        model.addColumn("Order Date");
        // Add table
        model = new DefaultTableModel(); *//*
        model.addColumn("email");
        model.addColumn("Order Number");
        model.addColumn("Product Code");
        model.addColumn("Order Date");

        // Initialize JTable with the model
        JTable table = new JTable(model);
        orderDetail_panel.add(new JScrollPane(table)); // Add table to panel inside a scroll pane

        try {
            // Establish database connection
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://stusql.dcs.shef.ac.uk:3306/team066?user=team066&password=aNohqu4mo"
            );

            // JOIN query to retrieve user and their order details
            String sql = "SELECT User.email, Order1.orderNumber, Order1.orderDate, OrderLine.productCode " +
                    "FROM User JOIN Order1 ON User.userID = Order1.userID " +
                    "JOIN OrderLine ON Order1.orderNumber = OrderLine.orderNumber";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            // Process ResultSet and add data to the model
            while (rs.next()) {
                String email = rs.getString("email");
                int orderNumber = rs.getInt("orderNumber");
                String productCode = rs.getString("productCode");
                Date orderDate = rs.getDate("orderDate");

                model.addRow(new Object[]{email, orderNumber, productCode,orderDate});
            }

            // Close resources
            rs.close();
            ps.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately
        }

        JTable ordersTable = new JTable(model);
        JScrollPane scrollPane1 = new JScrollPane(ordersTable);
        scrollPane1.setBounds(400, 100, 600, 500);
        orderDetail_panel.add(scrollPane1);

// Add event listener to the table
        ordersTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                // Display the detailed information of the selected order
            }
        });

// Add fulfill and reject buttons
        JButton fulfillButton = new JButton("Fulfill Order");
        fulfillButton.setBounds(50, 100, 150, 30);
        fulfillButton.addActionListener(e -> {
            // Code to fulfill the order
        });
        orderDetail_panel.add(fulfillButton);

        JButton rejectButton = new JButton("Reject Order");
        rejectButton.setBounds(50, 150, 150, 30);
        rejectButton.addActionListener(e -> {
            // Code to reject the order
        });
        orderDetail_panel.add(rejectButton);

// ... Configure the back button, etc. ...

// Add orderDetail_panel to cardHolder
        cardHolder.add(orderDetail_panel, "OrderDetailScreen");*/




        //Adds the screens to the cardholder
        cardHolder.add(login_panel, "LoginScreen");
        cardHolder.add(menu_panel, "MenuScreen");
        cardHolder.add(signup_panel, "SignupScreen");
        cardHolder.add(account_panel, "AccountScreen");
        cardHolder.add(pHistory_panel, "PHistoryScreen");
        cardHolder.add(bankDetails_panel, "BankDetailsScreen");
        cardHolder.add(editDetails_panel, "EditDetailsScreen");
        cardHolder.add(staff_panel, "StaffScreen");
        cardHolder.add(customerInfo_panel, "CustomerInfoScreen");
        cardHolder.add(basket_panel, "BasketScreen");
        // Add the basketPanel to the card layout container
//        cardHolder.add(basketPanel, "BasketPanel");
        cardHolder.add(confirm_panel, "ConfirmScreen");
        cardHolder.add(promote_panel, "PromoteScreen");
        cardHolder.add(orderDetail_panel, "OrderDetailScreen");
        cardHolder.add(locomotivePanel, "LocomotivePanel");
        cardHolder.add(rollingStockPanel, "RollingStockPanel");
        cardHolder.add(controllerPanel, "ControllerPanel");
        cardHolder.add(trackPanel, "TrackPanel");
        cardHolder.add(trackPackPanel, "TrackPackPanel");
        cardHolder.add(trainSetPanel, "TrainSetPanel");


        //Action listeners
      /*  login_bt.addActionListener(login_pressed);
        signup_bt.addActionListener(signup_pressed);*/
        logout_bt1.addActionListener(logout_pressed1);
        logout_bt2.addActionListener(logout_pressed2);
        account_bt.addActionListener(account_pressed);
       // back_bt2.addActionListener(back_pressed2);
       /* back_bt3.addActionListener(back_pressed3);*/
      //  pHistory_bt.addActionListener(pHistory_pressed);
        /*signUp_bt.addActionListener(signUp_pressed);*/
        back_bt4.addActionListener(back_pressed4);
      //  bDetails_bt.addActionListener(bDetails_pressed);
     /*   back_bt5.addActionListener(back_pressed5);*/
      //  eDetails_bt.addActionListener(eDetails_pressed);
//        confirm_bt_ed.addActionListener(confirm_pressed);
        back_bt6.addActionListener(back_pressed6);
     //   staff_bt.addActionListener((staff_pressed));
        cInfo_bt.addActionListener((cInfo_pressed));
        back_bt7.addActionListener(back_pressed7);
//        addInfo_bt.addActionListener(addInfo_pressed);
        search_bt.addActionListener(search_pressed);
        back_bt8.addActionListener(back_pressed8);
        viewBasket_bt.addActionListener(viewBasket_pressed);
        back_bt9.addActionListener(back_pressed9);
//        confirm_bt_bs.addActionListener(confirm_pressed2);
        promote_bt.addActionListener(promote_pressed);
//        back_bt10.addActionListener(back_pressed10);
        oDetail_bt.addActionListener(oDetail_pressed);
        /*back_bt11.addActionListener(back_pressed11);*/
        confirm_bt_bd.addActionListener(confirm_pressed3);

        //required stuff
        frame.add(cardHolder);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
//        cardLayout.show(cardHolder, "LocomotivePanel");
//        cardLayout.show(cardHolder, "LoginScreen");

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


    // the method to Update changes
    private static void updateProductInDatabase(String productCode, String brand, String productName, Double price, String productType, Integer quantity,String gauge) {
        // Here are the database connect and update statements
        try {
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://stusql.dcs.shef.ac.uk:3306/team066?user=team066&password=aNohqu4mo"
            );

            String sql = "UPDATE Product SET brand = ?, productName = ?, price = ?, productType = ?, quantity = ?,gauge = ? WHERE productCode = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, brand);
            ps.setString(2, productName);
            ps.setDouble(3, price);
            ps.setString(4, productType);
            ps.setInt(5, quantity);
            ps.setString(6, gauge);
            ps.setString(7, productCode);

            ps.executeUpdate();

            ps.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    //the method to Reload data(product)
    private static void refreshProductTable(DefaultTableModel productModel) {
        // Clear existing data
        productModel.setRowCount(0);

        // Reload data
        try {
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://stusql.dcs.shef.ac.uk:3306/team066?user=team066&password=aNohqu4mo");

            String sql = "SELECT productCode, brand, productName, price, productType, quantity, gauge FROM Product";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String productCode = rs.getString("productCode");
                String brand = rs.getString("brand");
                String productName = rs.getString("productName");
                double price = rs.getDouble("price");
                String productType = rs.getString("productType");
                int quantity = rs.getInt("quantity");
                String gauge = rs.getString("gauge");
                productModel.addRow(new Object[]{productCode, brand, productName, price, productType,quantity,gauge});
            }

            rs.close();
            ps.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //the method to add new data(product)
    private static void insertNewProductInDatabase(String productCode, String brand, String productName, Double price, String productType, Integer quantity, String gauge) {
        // Here are the database connect and insert statements
        try {
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://stusql.dcs.shef.ac.uk:3306/team066?user=team066&password=aNohqu4mo"
            );

            String sql = "INSERT INTO Product (productCode, brand, productName, price, productType, quantity, gauge) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, productCode);
            ps.setString(2, brand);
            ps.setString(3, productName);
            ps.setDouble(4, price);
            ps.setString(5, productType);
            ps.setInt(6, quantity);
            ps.setString(7, gauge);

            ps.executeUpdate();

            ps.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
            // This is where any SQL exceptions are handled
        }
    }

    // Method to delete the selected product from the table and the database
    private static void deleteSelectedProduct(JTable productTable, DefaultTableModel productModel) {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow >= 0) {
            // Assume that the product code is in the first column of the table
            String productCode = (String) productModel.getValueAt(selectedRow, 0);
            // Removes the selected row from the table model
            productModel.removeRow(selectedRow);
            // Delete records from the database
            deleteProductFromDatabase(productCode);
        } else {
            JOptionPane.showMessageDialog(null, "Please select a product to delete.", "No Selection", JOptionPane.ERROR_MESSAGE);
        }
    }

    //// Method to delete a product from the database based on its product code
    private static void deleteProductFromDatabase(String productCode) {
        try {
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://stusql.dcs.shef.ac.uk:3306/team066?user=team066&password=aNohqu4mo");

            String sql = "DELETE FROM Product WHERE productCode = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, productCode);
            ps.executeUpdate();
            ps.close();
            conn.close();

            JOptionPane.showMessageDialog(null, "Product deleted successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error deleting product.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}