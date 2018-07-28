package modelo;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

public class modeloAdministrador_Destino2 {
    private int destinoId;
    private int actividadId;
    private String foto;
    private String localizacion;
    private int transporteId;
    private int tipositioId;
    private Conexion conexion = new Conexion();
    
    public boolean InsertarTransporte(int destinoId, int transporteId) {            
        try {
            Connection con = conexion.abrirConexion();
            FileInputStream archivofoto;
            //Para ejecutar la consulta
            Statement s = con.createStatement();
            
            //Update en la tabla destino
            int registro = s.executeUpdate(
                 "insert into sedesplazaen (Destino_idDestino, Transporte_idTransporte)values("
                    + destinoId + ", '" 
                                + transporteId + "');");
            
            conexion.cerrarConexion(con);
            return true;
            
        }catch (SQLException e){
            return false;
        }
    }
    
    public boolean InsertarTipoSitio(int tipoSitioId, int destinoId) {            
        try {
            Connection con = conexion.abrirConexion();
            //Para ejecutar la consulta
            Statement s = con.createStatement();
            
            //Update en la tabla destino
            int registro = s.executeUpdate(
                 "insert into sedivideen (TipoSitio_idTipoSitio, Destino_idDestino)values("
                    + tipoSitioId + ", '" 
                                + destinoId + "');");
            
            conexion.cerrarConexion(con);
            return true;
            
        }catch (SQLException e){
            return false;
        }
    }   
    
    public boolean InsertarActividad(int actividadId, int destinoId, String foto, String localizacion) {            
        try {
            Connection con = conexion.abrirConexion();
            //Para ejecutar la consulta
            Statement s = con.createStatement();
            
            //Update en la tabla destino
            int registro = s.executeUpdate(
                 "insert into tiene (Actividad_idActividad, Destino_idDestino, foto, localizacion)values("
                    + actividadId + ", '" 
                         + destinoId + ", '" 
                             + foto + ", '" 
                                + localizacion + "');");
            
            conexion.cerrarConexion(con);
            return true;
            
        }catch (SQLException e){
            return false;
        }
    } 
    
    public String[] ConsultarActividades(){   
       try
        {
            //abrir conexión
            Connection con= conexion.abrirConexion(); 
            //generar consultas
            Statement s = con.createStatement(); 
            //consulta
            ResultSet rs = s.executeQuery("SELECT nombre FROM actividad");
            //número de registros obrenidos
            int count = 0;
            while (rs.next()) {
                ++count;
            }
            //declaración del array
            String [] a = new String [count];
            //se regresa al primero
            rs.beforeFirst();
            //contador para copiar del resultset al array
            int i = 0;
            //copiar del resultset al array
            while (rs.next())
            {
                a[i] = rs.getString(1);
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
    
    public DefaultTableModel Transporte(){
        try{
            //PARA ABRIR A LA BASE DE DATOS
            Connection con = conexion.abrirConexion();
            //PARA GENERAR CONSULTAS
            Statement s = con.createStatement();
            //PARA ESTABLECER EL MODELO AL JTABLE
            DefaultTableModel modelo;
            
            try{
                //EJECUTAR LA CONSULTA
                ResultSet rs = s.executeQuery("select * from transporte;");
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
    
    public DefaultTableModel TipoSitio(){
        try{
            //PARA ABRIR A LA BASE DE DATOS
            Connection con = conexion.abrirConexion();
            //PARA GENERAR CONSULTAS
            Statement s = con.createStatement();
            //PARA ESTABLECER EL MODELO AL JTABLE
            DefaultTableModel modelo;
            
            try{
                //EJECUTAR LA CONSULTA
                ResultSet rs = s.executeQuery("select * from tipositio;");
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