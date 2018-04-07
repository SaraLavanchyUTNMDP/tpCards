/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JDBC;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Lavanchy
 */
public class Connecction {
    
    private Connection conexion;
    static Connecction instance;
    
    /** Singleton Patron for my ddbb
     */
     public static Connecction getInstance() {
        if (instance == null) {
            instance = new Connecction();
        }
        return instance;
    }
     
    private Connecction(){
        try {
            //I create here the conection for my database.
            Class.forName("com.mysql.jdbc.Driver");
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/tpcards", "root", "");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }
    
    
    
}
