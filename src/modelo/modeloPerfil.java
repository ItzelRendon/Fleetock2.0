/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import controlador.controlPerfil;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author Esme
 */
public class modeloPerfil {
    private Conexion conexion= new Conexion();
    controlPerfil nombreUsuario = new controlPerfil();
    
   public String [] datosUsuario(int id, int idU)
    {   try
        {
            //abrir conexión
            Connection con= conexion.abrirConexion(); 
            //generar consultas
            Statement s = con.createStatement(); 
            //consulta
            ResultSet rs = s.executeQuery("select nombre, apellidos, usuario, foto from usuario, login where "
                    + "idUsuario = "+idU+" && login.idLogin ="+id+";");
           //la contidad de columnas que tiene la consulta
           String a [] = new String [4];
            if(rs.next()) {
            //Si hay resultados obtengo el valor. 
             a[0] = rs.getString(1);
             a[1]= rs.getString(2);
             a[2]= rs.getString(3);
             a[3]= rs.getString(4);
            }
            //cerrar conexión
            conexion.cerrarConexion(con); 
            return a; 
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, "Error al intentar abrir la base de datos.");
            return null;    
        }catch(NullPointerException e){
            JOptionPane.showMessageDialog(null, "Error al intentar conectar con el servidor.");
            return null;
        }
    }
    public String[][] usuarioViajes(int id)
    {   
        try
        {
            //abrir conexión
            Connection con= conexion.abrirConexion(); 
            //generar consultas
            Statement s = con.createStatement(); 
            //consulta
            ResultSet rs = s.executeQuery("select idViaje, viaje.nombre, viaje.estadoDelViaje, destino.foto, pertenece.Viaje_idViaje from " +
            "viaje, login, usuario, pertenece, destino where usuario.idUsuario = " +
            ""+id+" && login.Usuario_idUsuario = "+ id +" && " +
            "viaje.Usuario_idUsuario=usuario.idUsuario && " +
            "viaje.idViaje=pertenece.Viaje_idViaje && " +
            "pertenece.Destino_idDestino=destino.idDestino;");
            //número de registros obrenidos
            int count = 0;
            while (rs.next()) {
                ++count;
            }
            //declaración del array
            String [][] a = new String [count][5];
            //se regresa al primero
            rs.beforeFirst();
            //contador para copiar del resultset al array
            int i = 0;
            //copiar del resultset al array
            while (rs.next())
            {
                a[i][0] = rs.getString(1);
                a[i][1] = rs.getString(2);
                a[i][2] = rs.getString(3);
                a[i][3] = rs.getString(4);
                a[i][4] = rs.getString(5);
                i++;
            }  
            //cerrar conexión
            conexion.cerrarConexion(con); 
            return a; 
        }
        catch(SQLException e)
        {
           JOptionPane.showMessageDialog(null, "Error al intentar abrir la base de datos.");
          return null;    
        }catch(NullPointerException e){
            JOptionPane.showMessageDialog(null, "Error al intentar conectar con el servidor.");
            return null;
        }
    }
    
    public int contarDestinos(int idViaje, int idUsuario)
    {   
        try
        {
            //abrir conexión
            Connection con= conexion.abrirConexion(); 
            //generar consultas
            Statement s = con.createStatement(); 
            //consulta
            ResultSet rs = s.executeQuery("select COUNT(Viaje_idViaje) from pertenece, "
                    + "viaje where pertenece.Viaje_idViaje="+idViaje+" && viaje.Usuario_idUsuario="+idUsuario+" && "
                    + "pertenece.Viaje_idViaje=viaje.idViaje");
           
            int cantidad=0;
            //copiar del resultset al array
            while (rs.next())
            {
                cantidad = rs.getInt(1);
               
            }  
            //cerrar conexión
            conexion.cerrarConexion(con); 
            return cantidad; 
        }
        catch(SQLException e)
        {
           JOptionPane.showMessageDialog(null, "Error al intentar abrir la base de datos.");
          return 0;    
        }catch(NullPointerException e){
            JOptionPane.showMessageDialog(null, "Error al intentar conectar con el servidor.");
            return 0;
        }
    }
}
