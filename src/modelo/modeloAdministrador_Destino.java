package modelo;

import java.io.FileInputStream;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class modeloAdministrador_Destino {
    private int destinoId;
    private String destinoNombre;
    private String destinoPais;
    private String destinoImagen;
    private Conexion conexion = new Conexion();
    
    public boolean destinoInsertar(String destinoNombre, String destinoPais, String destinoImagen) {  
        try {
            Connection con = conexion.abrirConexion();
            FileInputStream archivofoto;
            //Para ejecutar la consulta
            Statement s = con.createStatement();
            
            //Update en la tabla destino
            int registro = s.executeUpdate(
                 "insert into destino(nombre, pais, foto)values("
                         + "'"+destinoNombre+"','"+destinoPais+"','"+destinoImagen+"');");
            
            conexion.cerrarConexion(con);
            return true;
            
        }catch (SQLException e){
            return false;
        }
    }
    
    public int ConsultarID() {
        int id=0;
        ResultSet rs;
        try {
            Connection con = conexion.abrirConexion();
            Statement s = con.createStatement();
            rs = s.executeQuery("SELECT MAX(idDestino) FROM destino"); 
            while (rs.next())
            {
                id=rs.getInt (1);
            }
            conexion.cerrarConexion(con);
                        
        }catch (SQLException e){
            return 0;
        }
        return id;
    }
    
    public boolean destinoActualizar(int destinoId, String destinoNombre, String destinoPais, String destinoImagen) {                                           
        try {
            Connection con = conexion.abrirConexion();
            
            //Para ejecutar la consulta
            Statement s = con.createStatement();
            
            //Update en la tabla destino
            int registro = s.executeUpdate(
                "update destino set nombre = '" 
                    + destinoNombre + "', pais = '" + destinoPais
                            + "', foto = '" + destinoImagen
                                + "'where idDestino = " + destinoId + ";");
                    
            conexion.cerrarConexion(con);
            return true;
            
        }catch (SQLException e){
            return false;
        }
    }     
    
    public boolean destinoEliminar(int destinoId, String destinoNombre, String destinoPais, String destinoImagen) {                                         
        try {
            Connection con = conexion.abrirConexion();
            
            //Para ejecutar la consulta
            Statement s = con.createStatement();
            
            //Update en la tabla destino
            int registro = s.executeUpdate(
            //BORRA EN LA TABLA DE DESTINO
                "delete from destino where idDestino = " + destinoId + ";");
            
            conexion.cerrarConexion(con);
            return true;
            
        }catch (SQLException e){
            return false;
        }
    }     
    
    public DefaultTableModel destinoConsultar(){
        try{
            //PARA ABRIR A LA BASE DE DATOS
            Connection con = conexion.abrirConexion();
            //PARA GENERAR CONSULTAS
            Statement s = con.createStatement();
            //PARA ESTABLECER EL MODELO AL JTABLE
            DefaultTableModel modelo;
            
            try{
                //EJECUTAR LA CONSULTA
                ResultSet rs = s.executeQuery("select idDestino as ID, nombre as Nombre, pais as Pais, foto as Foto  from destino;");
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
    
    public DefaultTableModel Buscador(String destinoNombre){
        try{
            //PARA ABRIR A LA BASE DE DATOS
            Connection con = conexion.abrirConexion();
            //PARA GENERAR CONSULTAS
            Statement s = con.createStatement();
            //PARA ESTABLECER EL MODELO AL JTABLE
            DefaultTableModel modelo;
            
            try{
                //EJECUTAR LA CONSULTA
                ResultSet rs = s.executeQuery("select * from destino where nombre='" + destinoNombre + "'");
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