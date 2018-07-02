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
 * @author alfredo
 */
public class modeloLogin {
    private Conexion conexion = new Conexion();
    //En esta funcion se hace la consulta donde tenemos dos parametros donde lo usaremos para que el usuario escriba 
    //su usuario y contraseña
    public int ingresar(String usu, String contra)
    {
        String capturar="";
        int control=0;
        ResultSet sql;       
         try {
            Connection con = conexion.abrirConexion();
            Statement s = con.createStatement();
            //En este variable se guarda la consulta y que es de tipo result set
            sql = s.executeQuery("SELECT * FROM login WHERE usuario='" + usu + "' && contraseña='" + contra + "' ");
            //este se usa para recorrer toda la consulta
            while(sql.next())
            {
                //Lo adquirido de la consukta se pasa a una variable de tipo string llamada captura
                capturar = sql.getString("tipo");
            }
            //Se compara el tipo de usuario
            if(capturar.equals("Administrador"))
            {
                //Se retorna un 1 si captura es Administrador
                control = 1;
            }
            if(capturar.equals("Usuario"))
            {
                //Se retorna un 2 si captura es Usuario
                control = 2;
            }
            if((!capturar.equals("Administrador")) && (!capturar.equals("Usuario")))
            {
                //Se retorna un 3 si no es nignuno de los dos
                control = 3;
            }
           conexion.cerrarConexion(con);
        }
        catch (SQLException ex)
        {
            Logger.getLogger(modeloRegistrar.class.getName()).log(Level.SEVERE, null, ex);
        }
         catch(NullPointerException e){
             //Arroja un mensaje 
            JOptionPane.showMessageDialog(null, "Error al intentar conectar con el servidor.");
        }
         return control;
    }
    //En esta funcion se usua para obtener informacion del usuario
    public String[] jalarIdUsuario(String usu, String contra)
    {
        ResultSet sql;       
         try {
            Connection con = conexion.abrirConexion();
            Statement s = con.createStatement();
            sql = s.executeQuery("SELECT idLogin, usuario, Usuario_idUsuario FROM login WHERE usuario='" + usu + "' && contraseña='" + contra + "' ");
            String [] a = new String [3];
            int i=0;
            while(sql.next())
            {
                //Aqui se guarda el resultado de la consulta en un array
                a[0]= sql.getString(1);
                a[1]= sql.getString(2);
                a[2]= sql.getString(3);
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
    
}
