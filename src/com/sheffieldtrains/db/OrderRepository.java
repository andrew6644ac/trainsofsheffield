package com.sheffieldtrains.db;

import com.sheffieldtrains.domain.order.Order;
import com.sheffieldtrains.domain.order.OrderLine;
import com.sheffieldtrains.domain.order.OrderStatus;
import com.sheffieldtrains.domain.product.Gauge;
import com.sheffieldtrains.domain.product.Product;
import com.sheffieldtrains.domain.user.Address;
import com.sheffieldtrains.domain.user.User;
import com.sheffieldtrains.domain.user.UserRole;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderRepository extends Repository {
    private static Connection connection;
    private static String dbURL = "jdbc:mysql://127.0.0.1/team066"; /*"jdbc:mysql://stusql.dcs.shef.ac.uk/team066";*/
    private static String userName =/* "team066";*/ "root";
    private static String password = /*"aNohqu4mo";*/ "password";

    private static String allPendingOrderSQL = """
                    /*SELECT
                        o.orderNumber,
                        o.orderDate,
                        o.status,
                        u.email AS userEmail,
                        u.forename AS userForename,
                        u.surname AS userSurname,
                        ur.roleName,
                        a.houseNumber,
                        a.roadName,
                        a.cityName,
                        a.postcode,
                        ol.lineNumber,
                        ol.productCode,
                        ol.quantity,
                        p.brand,
                        p.productName,
                        p.price,
                        p.gauge,
                        p.productType
                    FROM
                        `order` o 
                    JOIN `user` u ON o.userID = u.userID
                    JOIN 'userinrole' ur ON u.userId=ur.userId
                    JOIN `address` a ON u.houseNumber = a.houseNumber AND u.postcode = a.postcode
                    JOIN `orderline` ol ON o.orderNumber = ol.orderNumber
                    JOIN  `product` p ON ol.productCode = p.productCode
                    WHERE
                        o.status = 'PENDING'
                    OrderBy o.orderNumber ASC;*/
                """;

    private static Connection getConnection(){
        if (connection!=null) {
            return connection;
        }
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(dbURL, userName, password);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return connection;
    }

    public static void persistOrder(Order order) {
        //Todo: to implement
    }

    public static List<Order> getAllOrders(String userId) {
        //Todo
        return null;
    }

    public static List<Order> getAllHistoricalOrders() {
        //Todo
        return null;
    }

    public static List<Order> getHistoricalOrdersFromUser(String userId) {
        //Todo
        return null;
    }

    public static List<Order> getAllOrdersToBeProcessed() throws Exception {
        /*try {
            Map<Integer, Order> orderMap = new HashMap<>();
            PreparedStatement stmt = connection.prepareStatement(allPendingOrderSQL);
            ResultSet resultSet = stmt.executeQuery();

            List<Order> orders = new ArrayList<>();

            while (resultSet.next()) {
                // Create Order
                Order order = new Order();
                order.setOrderNumber(resultSet.getLong("orderNumber"));
                order.setOrderDate(resultSet.getDate("orderDate"));
//                order.setStatus(OrderStatus.valueOf(status));

                // Create User
                User user = new User();
                user.setUserID(resultSet.getInt("userID"));
                user.setEmail(resultSet.getString("userEmail"));
                user.setForename(resultSet.getString("userEmail"));
                user.setSurname(resultSet.getString("userSurname"));
                user.setRole(UserRole.valueOf(resultSet.getString("roleName")));

                // Create Address
                Address address = new Address();
                address.setHouseNumber(resultSet.getString("houseNumber"));
                address.setRoadName(resultSet.getString("roadName"));
                address.setCityName(resultSet.getString("cityName"));
                address.setPostcode(resultSet.getString("postcode"));

                // Set address properties...
                user.setAddress(address);
                order.setUser(user);

                // Create OrderLines
                List<OrderLine> orderLines = new ArrayList<>();

                do {
                    OrderLine orderLine = new OrderLine();
                    orderLine.setLineNumber(resultSet.getInt("lineNumber"));
                    // Create Product and set properties...
                 *//*   ol.lineNumber,
                            ol.productCode,
                            ol.quantity,
                            p.brand,
                            p.productName,
                            p.price,
                            p.gauge,
                            p.productType*//*

                   *//* String productCode,
                    String brand,
                    String productName,
                    float price,
                    Gauge gauge*//*
                   *//* Product product=new Product(
                            resultSet.getString("productCode"),
                            resultSet.getString("brand"),
                            resultSet.getString("productName"),
                            resultSet.getFloat("price"),
                            Gauge.valueOf(resultSet.getString("gauge")),
                            resultSet.getString("productType")
                        )
*//*

                    );

                   *//* orderLine.setProduct(product);
                    orderLine.setQuantity(resultSet.getInt("quantity"));

                    orderLines.add(orderLine);
                } while (resultSet.next() && resultSet.getInt("orderNumber") == orderNumber);

                order.setOrderLines(orderLines);
                orders.add(order);
            } *//*
        }
        catch (SQLException ex) {
            throw new RuntimeException("DB Error, cannot get orders");
        }

    }

}*/
        return null;
    }

}