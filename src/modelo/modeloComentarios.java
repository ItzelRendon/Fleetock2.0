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
import modelo.Conexion;
/**
 *
 * @author alfredo
 */
public class modeloComentarios {
    private Conexion conexion = new Conexion();
    //aqui se hace la consulta de los comentarios desde la base de datos
      public String[][] todas(String sentencia)
    {   try
        {
            //abrir conexión
            Connection con= conexion.abrirConexion(); 
            //generar consultas
            Statement s = con.createStatement(); 
            //consulta
            //ResultSet rs = s.executeQuery("SELECT comentario, nombre, foto FROM comentarios, usuario WHERE Usuario_idUsuario = idUsuario;");
            ResultSet rs = s.executeQuery(sentencia);
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
                a[i][0] = rs.getString("comentario");
                a[i][1] = rs.getString("nombre");
                a[i][2] = rs.getString("foto");
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
