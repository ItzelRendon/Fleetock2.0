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
 * @author Esme
 */
public class modeloRegistrar2 {
    private Conexion conexion = new Conexion();
    public boolean insertarPreferencias(int idUsuario, int idTipoSitio)
    {
        try {
            //Se abre la conexion con la bd.
            Connection con = conexion.abrirConexion();
            //Permite crear consultas
            Statement s = con.createStatement();
            int registroUsuarios = s.executeUpdate(
                    "insert into prefiere(Usuario_idUsuario, TipoSitio_idTipoSitio) values (" 
                            + idUsuario + ", "  + idTipoSitio + ");"                
            );
            conexion.cerrarConexion(con);
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al intentar abrir la base de datos.");
            return false;
        }catch(NullPointerException e){
            JOptionPane.showMessageDialog(null, "Error al intentar conectar con el servidor.");
            return false;
        }
    }
    public String[] jalarIdUsuario(String usu, String contra)
    {
        String capturar="";
        int control=0;
        ResultSet sql;       
         try {
            Connection con = conexion.abrirConexion();
            Statement s = con.createStatement();
            sql = s.executeQuery("SELECT idLogin, usuario, Usuario_idUsuario FROM login WHERE usuario='" + usu + "' && contraseña='" + contra + "' ");
            String [] a = new String [3];
            int i=0;
            while(sql.next())
            {
                a[0]= sql.getString(1);
                a[1]= sql.getString(2);
                a[2]= sql.getString(3);
            }
           conexion.cerrarConexion(con);
           return a;
        }
        catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al intentar abrir la base de datos.");
            return null;
        }catch(NullPointerException e){
            JOptionPane.showMessageDialog(null, "Error al intentar conectar con el servidor.");
            return null;
        }
    }
}
