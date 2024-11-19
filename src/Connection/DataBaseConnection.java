package Connection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.*;
import javax.swing.JOptionPane;

public class DataBaseConnection {
    static String url = "jdbc:mysql://localhost:3306/dbjavamysql";
    static String user = "root";
    static String pass = "Thor2006bzrp";
    
    public static Connection conectar(){
        Connection con = null;
        try{
            con = DriverManager.getConnection(url,user,pass);
            System.out.println("Conexion exitosa");
        }catch(SQLException e){
            e.printStackTrace();
        }
        return con;
    }
}
   /* 
    private static DataBaseConnection instance;
    private Connection connection;

    public static DataBaseConnection getInstance() {
        if (instance == null) {
            instance = new DataBaseConnection();
        }
        return instance;
    }

    private DataBaseConnection() {

    }

    public void connectToDatabase() throws SQLException {
        try{ 
            String server = "localhost";
            String port = "3306";
            String database = "dbjavamysql";
            String userName = "root";
            String password = "Thor2006bzrp";
            connection = java.sql.DriverManager.getConnection("jdbc:mysql://" + server + ":" + port + "/" + database, userName, password);
            System.out.println("Conexion exitosa");
        }catch(SQLException e ){
           System.out.println("Errror en al conexion");
           e.printStackTrace();
        }
       }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
   }

*/