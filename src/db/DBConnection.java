package db;

import java.sql.Connection;
import java.sql.DriverManager;

    public class DBConnection {
        public static Connection getConnection() throws Exception {
            String url = "jdbc:mysql://localhost:3306/bankapp";
            String user = "root";
            String pass = "Hemanth@123";

            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, user, pass);
        }
    }


