package dbconnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/livraria";
    private static final String USER = "root";
    private static final String PASSWORD = "qwe123";
    private static Connection conn = null;


    public static Connection getConnection() throws SQLException {
        if(conn == null || conn.isClosed()){
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return conn;
    }

    public static void closeConnection(){
        if(conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                conn = null;
            }
        }
    }
}
