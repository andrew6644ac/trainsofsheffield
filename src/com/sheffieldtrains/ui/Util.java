package com.sheffieldtrains.ui;

import com.sheffieldtrains.domain.order.Order;
import com.sheffieldtrains.domain.product.ProductType;
import com.sheffieldtrains.domain.user.User;
import com.sheffieldtrains.service.UserService;

import javax.swing.table.DefaultTableModel;
import java.util.Date;
import java.util.Vector;

public class Util {
    public static Order createOrderFromTableModel(DefaultTableModel basketTableModel) {{
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
    }


}
