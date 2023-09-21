package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionSingleton {

    static String url = "jdbc:mysql://localhost:3306/dtb";
    static String username = "root";
    static String password = "#victoria02F";

    private static ConnectionSingleton instance=null;
    private static Connection connection=null;

    private ConnectionSingleton(){

    }

    public static ConnectionSingleton getInstance(){
        if (instance==null){
            instance=new ConnectionSingleton();
        }
        return instance;
    }

    public Connection getConnection(){
        if (connection==null){
            try {
                connection= DriverManager.getConnection(url,username,password);
            } catch (SQLException e) {
                System.out.println("Date de conectare invalide!");
            }
        }
        return connection;
    }
}
