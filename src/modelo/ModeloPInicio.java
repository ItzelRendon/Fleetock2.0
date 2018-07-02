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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;



/**
 *
 * @author Itzel
 */
public class ModeloPInicio {
    private Conexion conexion= new Conexion();
    
    public String [] datosUsuario(int id, int idU)
    {   try
        {
            //abrir conexión
            Connection con= conexion.abrirConexion(); 
            //generar consultas
            Statement s = con.createStatement(); 
            //consulta
            ResultSet rs = s.executeQuery("select usuario.idUsuario, foto from usuario, login where "
                    + "idUsuario = "+idU+" && login.Usuario_idUsuario="+idU+";");
           //la contidad de columnas que tiene la consulta
            String a [] = new String[2];
            if(rs.next()) {
            //Si hay resultados obtengo el valor. 
             a[0] = rs.getString(1);
             a[1] = rs.getString(2);
            }
            //cerrar conexión
            conexion.cerrarConexion(con); 
//            
            return a; 
        }
        catch(SQLException e)
        {
            System.out.println("Error!");
            return null;    
        }
    }


    public String[][] Comentarios(String sentencia){   
       try
        {
            //abrir conexión
            Connection con= conexion.abrirConexion(); 
            //generar consultas
            Statement s = con.createStatement(); 
            //consulta
            ResultSet rs = s.executeQuery(sentencia);
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
          return null;    
        }
    }
    

    //metodo donde se hacen las consultas de actividades, destinos y transportes

    public String[][] todas(String sentencia){   
       try
        {
            //abrir conexión
            Connection con= conexion.abrirConexion(); 
            //generar consultas
            Statement s = con.createStatement(); 
            //consulta
            ResultSet rs = s.executeQuery(sentencia);
            //número de registros obrenidos
            int count = 0;
            while (rs.next()) {
                ++count;
            }
            //declaración del array
            String [][] a = new String [count][4];
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
    
    //Metodo donde se extrae la informción del estilo de viaje
    public String [] estiloViajeC(String nombre){   
       try
        {
            //abrir conexión
            Connection con= conexion.abrirConexion(); 
            //generar consultas
            Statement s = con.createStatement(); 
            //consulta
            ResultSet rs = s.executeQuery("select * from estiloviaje where tipo = '" + nombre + "';");
        
            //declaración del array
            String []  a = new String [5];
            rs.next();
            //copiar del resultset al array
            a[0] = rs.getString(1);
            a[1] = rs.getString(3);
            a[2] = rs.getString(4);
            a[3] = rs.getString(5);
            a[4] = rs.getString(6);
 
            //cerrar conexión
            conexion.cerrarConexion(con); 
            return a; 
        }
        catch(SQLException e)
        {
          return null;    
        }
    }
    

    public boolean insertarComentarios(String comentarios, String calificacion, int destino, String usuario)
    {
        try
        {
            Connection con = conexion.abrirConexion();
            Statement s = con.createStatement();
            int registro = s.executeUpdate("insert into comentarios(comentario, calificacion, Destino_idDestino, Usuario_idUsuario, Actividad_idActividad) values('" + comentarios + "', '" + calificacion + "', '" + destino + "', '" + usuario + "', '" + "0" + "');");
            conexion.cerrarConexion(con);
            return true;
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    public String[] ObteneridUsuario(String usu, String contra)
    {
        ResultSet sql;       
         try {
            Connection con = conexion.abrirConexion();
            Statement s = con.createStatement();
            sql = s.executeQuery("SELECT usuario.idUsuario FROM usuario, login WHERE usuario.idUsuario = login.idLogin && login.usuario='" + usu + "' && login.contraseña='" + contra + "' ");
            String [] a = new String [3];
            int i=0;
            while(sql.next())
            {
                a[0]= sql.getString(1);
            }
           conexion.cerrarConexion(con);
           return a;
        }
        catch (SQLException ex)
        {
            Logger.getLogger(modeloRegistrar.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
         catch(NullPointerException e){
            JOptionPane.showMessageDialog(null, "Error al intentar conectar con el servidor.");
            return null;
         }
    }
    //calificación de la actividades
    public String actividadCal(int id){   
       try
        {
            //abrir conexión
            Connection con= conexion.abrirConexion(); 
            //generar consultas
            Statement s = con.createStatement(); 
            //consulta
            ResultSet rs = s.executeQuery("SELECT avg(calificacion) FROM `comentarios` where comentarios.Actividad_idActividad = " + id + ";");
        
            //declaración del array
            String  a;
            rs.next();
            //copiar del resultset al array
            a = rs.getString(1);

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
