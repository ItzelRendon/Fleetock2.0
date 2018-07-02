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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

/**
 *
 * @author Holi
 */
public class modeloRegistrar {
    
//    private String nombre, apellidos, email, tockname, contraseña, confirmarContraseña, sexo;
//    private int dia, mes, anio;
    private Conexion conexion = new Conexion();
    
    //VALIDA SI EL NOMBRE DE USUARIO NO ESTÁ EXISTENTE EN LA BD.
    public int validarTockname(String tockname)
    {
        ResultSet verificarTockname;
        int tocknameVerificado=0;
        try {
        Connection con = conexion.abrirConexion();
            
        Statement s = con.createStatement();
        verificarTockname = s.executeQuery("select count(usuario) from login where usuario = '"+ tockname +"'");
        if(verificarTockname.next()) 
        {
            //Si hay resultados obtengo el valor. 
             tocknameVerificado = verificarTockname.getInt(1);
        }
        conexion.cerrarConexion(con);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al intentar abrir la base de datos.");
        }catch(NullPointerException e){
            JOptionPane.showMessageDialog(null, "Error al intentar conectar con el servidor.");
            return 0;
        }
        return tocknameVerificado;
    }
    
    //VALIDA SI SE INGRESÓ UN CORREO ELECTRONICO VÁLIDO.
    public boolean validarEmail(String email)
    {
        String emailPattern ="^[a-zA-Z0-9_]+[@]{1}+[a-zA-Z0-9]+[.]{1}+[a-zA-Z0-9]+$";
        
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);
        if(matcher.matches())
            return true;
        else
            return false;
    }
    public int extraerIdUsuario()
    {
        Connection con;
        int nuevoIdUsuario=0, idUsuario=0;
        
        try {
            con = conexion.abrirConexion();
            Statement s = con.createStatement();
            /*Cuenta la cantidad de registros existentes en la tabla usuario
              para asignar el id correspondiente a cada registro.
            */
            ResultSet ultimoIdUsuario = s.executeQuery("select max(idUsuario) from usuario");
            //la contidad de columnas que tiene la consulta
            if(ultimoIdUsuario.next()) {
            //Si hay resultados obtengo el valor. 
             nuevoIdUsuario = ultimoIdUsuario.getInt(1);
            }
            if(nuevoIdUsuario<1)
            {
                idUsuario=1;
            }
            else
            {
                idUsuario=nuevoIdUsuario+1;
            }
            conexion.cerrarConexion(con);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al intentar abrir la base de datos.");
        }catch(NullPointerException e){
            JOptionPane.showMessageDialog(null, "Error al intentar conectar con el servidor.");
            return 0;
        }
        return idUsuario;
    }
    
    //LLENA UN REGISTRO DE LA TABLA USUARIO Y OTRO EN LA TABLA LOGIN
    public boolean registrarInsertar(String nombre, String apellidos, String email, String tockname, 
            String contrasenia, String sexo, String fechaNac, int edad, String estatus, String tipo)
    {
        int idLogin, idUsuario;
        
        try
        {
            //Se abre la conexion con la bd.
            Connection con = conexion.abrirConexion();
            //Permite crear consultas
            Statement s = con.createStatement();
            //asignar id 
            idUsuario=extraerIdUsuario();

            /*El registro no.1 de la tabla login siempre será el administrador.*/
            idLogin=idUsuario+1;
            //Inserta un registro en la tabla usuario.
            int registroUsuarios = s.executeUpdate(
                    "insert into usuario(idUsuario, nombre, apellidos, fechaNac, edad, sexo, email) values (" 
                            + idUsuario + ", '"  + nombre + "', '"
                            + apellidos + "', '" + fechaNac + "', " + edad + " , '" + sexo + "', '"+ email +"');"                
            );
            //Inserta un registro en la tabla login.
            int registroLogin = s.executeUpdate("insert into login(idLogin, usuario, contraseña, estatus, tipo, Usuario_idUsuario) "
                    + "values ("+ idLogin +", '"+ tockname +"', '"
                    + contrasenia +"', '"+ estatus +"', '"+tipo+"' ,"+ idUsuario +");");
            
            
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
    
}
