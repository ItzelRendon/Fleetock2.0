/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author ITZEL
 */
public class ModeloEditarViaje {
    private Conexion conexion = new Conexion();
    
    //Metodo de extración de datos de viaje
    public String [] viajeDatos(int id){   
       try
        {
            //abrir conexión
            Connection con= conexion.abrirConexion(); 
            //generar consultas
            Statement s = con.createStatement(); 
            //consulta
            ResultSet rs = s.executeQuery("select nombre, fecha_inicio, fecha_fin, estadoDelViaje, EstiloViaje_idEstiloViaje, pertenece.Destino_idDestino from viaje INNER JOIN pertenece on pertenece.Viaje_idViaje = viaje.idViaje where viaje.idViaje = " + id);
            //declaración del array
            String [] a = new String[6];
            rs.next();
            //copiar del resultset al array
            a[0] = rs.getString(1);
            a[1] = rs.getString(2);
            a[2] = rs.getString(3);
            a[3] = rs.getString(4);
            a[4] = rs.getString(5);
            a[5] = rs.getString(6);
            //cerrar conexión
            conexion.cerrarConexion(con); 
            return a; 
        }
        catch(SQLException e)
        {
          return null;    
        }
    }
    
    //metodo de la consulta de los nombres de estilos de viaje 
    public String[][] estiloViaje(){   
       try
        {
            //abrir conexión
            Connection con= conexion.abrirConexion(); 
            //generar consultas
            Statement s = con.createStatement(); 
            //consulta
            ResultSet rs = s.executeQuery("select tipo from estiloviaje;");
            //número de registros obrenidos
            int count = 0;
            while (rs.next()) {
                ++count;
            }
            //declaración del array
            String [][] a = new String [count][1];
            //se regresa al primero
            rs.beforeFirst();
            //contador para copiar del resultset al array
            int i = 0;
            //copiar del resultset al array
            while (rs.next())
            {
                a[i][0] = rs.getString(1);
                i++;
            }  
            //cerrar conexión
            conexion.cerrarConexion(con); 
            return a; 
        }
        catch(SQLException e)
        {
          return null;    
        }
    }
    
    //método de consulta del destino del viaje
    public String [] destinoDatos(int id){   
       try
        {
            //abrir conexión
            Connection con= conexion.abrirConexion(); 
            //generar consultas
            Statement s = con.createStatement(); 
            //consulta
            ResultSet rs = s.executeQuery("select nombre, foto from destino where idDestino = " + id);
                   
            //declaración del array
            String [] a = new String[2];
            rs.next();
            //copiar del resultset al array
            a[0] = rs.getString(1);
            a[1] = rs.getString(2);
            //cerrar conexión
            conexion.cerrarConexion(con); 
            return a; 
        }
        catch(SQLException e)
        {
          return null;    
        }
    }
    
    public boolean actualizarViaje(int idViaje, String nombre, String estado, int estilo, String fechai, String fechaf)
    {   try
        {
            Connection con= conexion.abrirConexion(); 
            Statement s= con.createStatement(); 
            int registro =s.executeUpdate("UPDATE `viaje` SET `nombre`='"+nombre+"',`fecha_inicio`='"+fechai+"',"
                    + "`fecha_fin`='"+fechaf+"',`estadoDelViaje`='"+estado+"',"
                    + "`EstiloViaje_idEstiloViaje`= " + estilo
                    + " WHERE idViaje = " + idViaje); 
            conexion.cerrarConexion(con); 
            return true; 
        }
        catch(SQLException e)
        {
            return false; 
        }catch(NullPointerException e){
            JOptionPane.showMessageDialog(null, "Error al intentar conectar con el servidor.");
            return false;
        }
    }
    
    public boolean viajeEliminar(int idViaje) {                                         
        try {
            Connection con = conexion.abrirConexion();
            
            //Para ejecutar la consulta
            Statement s = con.createStatement();
            
            //Delete en la tabla pertenece
            int registroPertenece = s.executeUpdate("delete from pertenece where Viaje_idViaje = " + idViaje + ";");
            int registroContiene = s.executeUpdate("delete from contiene where Viaje_idViaje = " + idViaje + ";");
            int registroViaje = s.executeUpdate("delete from viaje where idViaje = " + idViaje + ";");
            
            conexion.cerrarConexion(con);
            return true;
            
        }catch (SQLException e){
            return false;
        }
    }     
    
    public String[][] actividadesViaje(int idViaje){   
       try
        {
            //abrir conexión
            Connection con= conexion.abrirConexion(); 
            //generar consultas
            Statement s = con.createStatement(); 
            //consulta
            ResultSet rs = s.executeQuery("SELECT actividad.nombre, tiene.foto, contiene.fechaActividad FROM `actividad` " +
                                "INNER JOIN contiene on contiene.Actividad_idActividad = actividad.idActividad " +
                                "INNER JOIN tiene ON tiene.Actividad_idActividad = actividad.idActividad " +
                                "WHERE contiene.Viaje_idViaje = " + idViaje);
            //número de registros obrenidos
            int count = 0;
            while (rs.next()) {
                ++count;
            }
            //declaración del array
            String [][] a = new String [count][3];
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
                i++;
            }  
            //cerrar conexión
            conexion.cerrarConexion(con); 
            return a; 
        }
        catch(SQLException e)
        {
          return null;    
        }
    }
    
}
