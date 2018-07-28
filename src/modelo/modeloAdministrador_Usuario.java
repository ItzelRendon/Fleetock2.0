package modelo;

import java.io.FileInputStream;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class modeloAdministrador_Usuario {
    private int usuarioId;
    private String usuarioUsuario;
    private String usuarioContraseña;
    private String usuarioTipo;
    private Conexion conexion = new Conexion();
    
    public boolean BloquearUsuario(int usuarioId) {            
        try {
            Connection con = conexion.abrirConexion();
            FileInputStream archivofoto;
            //Para ejecutar la consulta
            Statement s = con.createStatement();
            
            //Update en la tabla usuario
            int registro = s.executeUpdate(
           "update login set estatus = '" 
                    + "Bloqueado" + "'where idLogin = " + usuarioId + ";");
            
            conexion.cerrarConexion(con);
            return true;
            
        }catch (SQLException e){
            return false;
        }
    }     
    
    public boolean DesbloquearUsuario(int usuarioId) {                                           
        try {
            Connection con = conexion.abrirConexion();
            
            //Para ejecutar la consulta
            Statement s = con.createStatement();
            
            //Update en la tabla usuario
            int registro = s.executeUpdate(
                "update login set estatus = '" 
                    + "Activo" + "'where idLogin = " + usuarioId + ";");
                    
            conexion.cerrarConexion(con);
            return true;
            
        }catch (SQLException e){
            return false;
        }
    }     
    
    public boolean ActualizarUsuario(int usuarioId, String usuarioUsuario, String usuarioContraseña, String usuarioTipo) {                                         
        try {
            Connection con = conexion.abrirConexion();
            
            //Para ejecutar la consulta
            Statement s = con.createStatement();
            
            //Update en la tabla usuario
            int registro = s.executeUpdate(
            //BORRA EN LA TABLA DE USUARIO
                "update login set usuario = '" 
                    + usuarioUsuario + "', contraseña = '" + usuarioContraseña
                        + "', tipo = '" + usuarioTipo
                            + "'where idLogin = " + usuarioId + ";");
            
            conexion.cerrarConexion(con);
            return true;
            
        }catch (SQLException e){
            return false;
        }
    }  
    
    public DefaultTableModel BloquearUsuariosConsultar(){
        try{
            //PARA ABRIR A LA BASE DE DATOS
            Connection con = conexion.abrirConexion();
            //PARA GENERAR CONSULTAS
            Statement s = con.createStatement();
            //PARA ESTABLECER EL MODELO AL JTABLE
            DefaultTableModel modelo;
            
            try{
                //EJECUTAR LA CONSULTA
                ResultSet rs = s.executeQuery("select idLogin as ID, usuario as Usuario, contraseña as Contraseña, tipo as Tipo from login");
                //PARA ESTABLECER EL MODELO AL JTABLE
                modelo = new DefaultTableModel();
                //OBTENIENDO LA INFORMACION DE LAS COLUMNAS
                //QUE ESTAN SIENDO CONSULTADAS
                ResultSetMetaData rsMd = rs.getMetaData();
                //LA CANTIDAD DE COLUMNAS QUE TIENE LA CONSULTA
                int cantidadColumnas = rsMd.getColumnCount();
                //ESTABLECER COMO CABECERAS EL NOMBRE EL NOMBRE DE LAS COLUMNAS
                for(int i=1; i<=cantidadColumnas; i++){
                    modelo.addColumn(rsMd.getColumnLabel(i));
                }
                //CREANDO LAS FILAS PARA LA TABLE
                while (rs.next()){
                    Object[]fila=new Object[cantidadColumnas];
                    for(int i = 0; i<cantidadColumnas; i++){
                        fila[i]=rs.getObject(i+1);
                    }
                    modelo.addRow(fila);
                }
                return modelo;
            }finally{
            conexion.cerrarConexion(con);
        }
        } catch (SQLException e) {
        return null;
    }
} 
    
    public DefaultTableModel Buscador(String usuarioUsuario){
        try{
            //PARA ABRIR A LA BASE DE DATOS
            Connection con = conexion.abrirConexion();
            //PARA GENERAR CONSULTAS
            Statement s = con.createStatement();
            //PARA ESTABLECER EL MODELO AL JTABLE
            DefaultTableModel modelo;
            
            try{
                //EJECUTAR LA CONSULTA
                ResultSet rs = s.executeQuery("select * from login where usuario='" + usuarioUsuario + "'");
                //PARA ESTABLECER EL MODELO AL JTABLE
                modelo = new DefaultTableModel();
                //OBTENIENDO LA INFORMACION DE LAS COLUMNAS
                //QUE ESTAN SIENDO CONSULTADAS
                ResultSetMetaData rsMd = rs.getMetaData();
                //LA CANTIDAD DE COLUMNAS QUE TIENE LA CONSULTA
                int cantidadColumnas = rsMd.getColumnCount();
                //ESTABLECER COMO CABECERAS EL NOMBRE EL NOMBRE DE LAS COLUMNAS
                for(int i=1; i<=cantidadColumnas; i++){
                    modelo.addColumn(rsMd.getColumnLabel(i));
                }
                //CREANDO LAS FILAS PARA LA TABLE
                while (rs.next()){
                    Object[]fila=new Object[cantidadColumnas];
                    for(int i = 0; i<cantidadColumnas; i++){
                        fila[i]=rs.getObject(i+1);
                    }
                    modelo.addRow(fila);
                }
                return modelo;
            }finally{
            conexion.cerrarConexion(con);
        }
        } catch (SQLException e) {
        return null;
    }
}
}