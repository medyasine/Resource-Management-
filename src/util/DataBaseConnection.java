package util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DataBaseConnection {
    private static Connection connection = null;
    static{
        try{
            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/catalogue";
            String username = "yassine";
            String password = "yassine";
            Properties propertiesconnection = new Properties();
            InputStream inStream= null;
            try{
                inStream = DataBaseConnection.class.getClassLoader().getResourceAsStream("util/application.properties");
                propertiesconnection.load(inStream);
                driver = propertiesconnection.getProperty("database.driver");
                url = propertiesconnection.getProperty("database.url");
                username = propertiesconnection.getProperty("database.username");
                password = propertiesconnection.getProperty("database.password");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Class.forName(driver);
            connection = DriverManager.getConnection(url,  username, password);
            System.out.println("Connection successful!");
        }catch(SQLException ex){
        }catch(ClassNotFoundException ex){
        }
    }
    public static Connection getConnection(){
        return connection;
    }
}
