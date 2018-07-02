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
 * @author Holi
 */
public class modeloEditarPerfil {
    private Conexion conexion= new Conexion();
    
    public boolean actualizarDatosUsuario(int id, String nombre, String apellidos, String fechaNac, int edad, String sexo, String email, String descripcion, String foto, String user)
    {   try
        {
            Connection con= conexion.abrirConexion(); 
            Statement s= con.createStatement(); 
            int registro =s.executeUpdate("update usuario, login set usuario.nombre='"+nombre+"', "
                    + "usuario.apellidos='"+apellidos+"', usuario.fechaNac='"+fechaNac+"', usuario.edad="+edad+", "
                    + "usuario.sexo='"+sexo+"', usuario.email='"+email+"', usuario.descripcion='"+descripcion+"', "
                    + "usuario.foto='"+foto+"', login.usuario='"+user+"' WHERE usuario.idUsuario="+id+" && login.Usuario_idUsuario="+id+";"); 
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
    public boolean actualizarContraseña(int id, String contraseña)
    {   try
        {
            Connection con= conexion.abrirConexion(); 
            Statement s= con.createStatement(); 
            int registro =s.executeUpdate("update login set contraseña='"+contraseña+"' where login.Usuario_idUsuario="+id+";"); 
            conexion.cerrarConexion(con); 
            return true; 
        }
        catch(SQLException e)
        {
            JOptionPane.showMessageDialog(null, "Error al intentar abrir la base de datos.");
            return false; 
        }
        catch(NullPointerException e){
            JOptionPane.showMessageDialog(null, "Error al intentar conectar con el servidor.");
            return false;
        }
    }

    public String[] jalarDatosUsuario(int id, int idU)
    {   
        try
        {
            //abrir conexión
            Connection con= conexion.abrirConexion(); 
            //generar consultas
            Statement s = con.createStatement(); 
            //consulta
            ResultSet rs = s.executeQuery("select usuario.idUsuario, usuario.nombre, usuario.apellidos, usuario.fechaNac"
                    + ", usuario.edad, usuario.sexo, usuario.email, usuario.descripcion, usuario.foto, login.usuario, login.contraseña from usuario, "
                    + "login where usuario.idUsuario = "+idU+" && login.Usuario_idUsuario = "+idU+";");
            
            //declaración del array
            String [] a = new String [11];
            //contador para copiar del resultset al array
            int i = 0;
            //copiar del resultset al array
            while (rs.next())
            {
                a[0] = rs.getString(1);
                a[1] = rs.getString(2);
                a[2] = rs.getString(3);
                a[3] = rs.getString(4);
                a[4] = rs.getString(5);
                a[5] = rs.getString(6);
                a[6] = rs.getString(7);
                a[7] = rs.getString(8);
                a[8] = rs.getString(9);
                a[9] = rs.getString(10);
                a[10] = rs.getString(11);
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
    public int jalarFecha(int opcion, int id, int idU)
    {   
        try
        {
            //abrir conexión
            Connection con= conexion.abrirConexion(); 
            //generar consultas
            Statement s = con.createStatement(); 
            //consulta
            ResultSet rs=null;
            switch(opcion)
            {
                case 1:
                    rs = s.executeQuery("select year(fechaNac) from usuario, login where "
                    + "usuario.idUsuario = "+idU+" && login.Usuario_idUsuario ="+idU+";");
                    break;
                case 2:
                    rs = s.executeQuery("select month(fechaNac) from usuario, login where "
                    + "usuario.idUsuario = "+idU+" && login.Usuario_idUsuario ="+idU+";");
                    break;
                case 3:
                    rs = s.executeQuery("select day(fechaNac) from usuario, login where "
                    + "usuario.idUsuario = "+idU+" && login.Usuario_idUsuario ="+idU+";");
                    break;
            };
                  
            //declaración del array
            int fecha=0;
            //contador para copiar del resultset al array
            int i = 0;
            //copiar del resultset al array
            while (rs.next())
            {
                fecha = rs.getInt(1);
                i++;
            }  
            conexion.cerrarConexion(con); 
            return fecha; 
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
    
    
    public String [] validarTockname(String tockname)
    {
        ResultSet verificarTockname;
        String [] tocknameVerificado= new String[2];
        try {
        Connection con = conexion.abrirConexion();
            
        Statement s = con.createStatement();
        verificarTockname = s.executeQuery("select count(usuario), idLogin from login where usuario = '"+ tockname +"'");
        if(verificarTockname.next()) 
        {
            //Si hay resultados obtengo el valor. 
             tocknameVerificado[0] = verificarTockname.getString(1);
             tocknameVerificado[1] = verificarTockname.getString(2);
        }
        conexion.cerrarConexion(con);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al intentar abrir la base de datos.");
        }
        catch(NullPointerException e){
            JOptionPane.showMessageDialog(null, "Error al intentar conectar con el servidor.");
        }
        return tocknameVerificado;
    }
}
