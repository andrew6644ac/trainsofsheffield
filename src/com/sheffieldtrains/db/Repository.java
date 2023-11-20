package com.sheffieldtrains.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class Repository {
    protected static Connection connection;
    private static String dbURL = "jdbc:mysql://127.0.0.1/team066"; /*"jdbc:mysql://stusql.dcs.shef.ac.uk/team066";*/
    private static String userName =/* "team066";*/ "root";
    private static String password = /*"aNohqu4mo";*/ "password";

    public static Connection getConnection(){
        if (connection!=null) {
            return connection;
        }
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(dbURL, userName, password);
        } catch (Exception ex) {
            throw new RuntimeException("Cannot get a db connection");
        }
        return connection;
    }
}
