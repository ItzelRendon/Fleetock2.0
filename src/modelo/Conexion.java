package modelo;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.sql.*;
import javax.swing.JOptionPane;

public class Conexion {
    
    
    public Connection abrirConexion() throws SQLException{
        Connection con;
        //Para conectarnos a nuestrza base de datos
        try{
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/fleetock-bd", "root", "");
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "No se pudo abrir la conexión con la BD.");
            con=null;
        }
        return con;
    }
            
    public void cerrarConexion(Connection c)
            throws SQLException{
        try{
            if(!c.isClosed()){
                c.close();
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error al cerrar la conexión de la BD.");
        }
    }
}