package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String DB_URL = "jdbc:mysql://localhost:3306/management-shop-pet";
    public static final String USER = "root";
    public static final String PASSWORD = "admin";

    public DBConnection() {

    }

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            System.out.println("Connected to database.");
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println("Could not connect: " + ex.getMessage());
        }
        return conn;
    }
}
