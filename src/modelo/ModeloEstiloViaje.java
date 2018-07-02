/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;
import modelo.Conexion; 
//import java.sql.*; 
import java.sql.Connection; 
import java.sql.PreparedStatement;
import java.sql.ResultSet; 
import java.sql.ResultSetMetaData;
import java.sql.SQLException; 
import java.sql.Statement; 
import javax.swing.table.DefaultTableModel; 
/**
 *
 * @author Fabiola Paez
 */
public class ModeloEstiloViaje {
    private Conexion conexion= new Conexion();
    
    public int administradorInsertarE(String eTipo, String eDescripcion, double ePMax, double ePMin, String eFoto)
    {   try
        {   int id=0; 
            Connection con= conexion.abrirConexion(); 
            PreparedStatement s= con.prepareStatement("insert into estiloviaje(tipo, descripcion, presupuesto_max, presupuesto_min, foto)values('"+eTipo+"','"+eDescripcion+"',"+ePMax+","+ePMin+",'"+eFoto+"');"
            ,PreparedStatement.RETURN_GENERATED_KEYS); 
            s.executeUpdate(); 
            ResultSet generatedKeys = s.getGeneratedKeys();
            if (generatedKeys.next())
                id = generatedKeys.getInt(1);
            conexion.cerrarConexion(con); 
            return id; 
        }
        catch(SQLException e)
        {
          return 0;    
        }
    }
    public boolean administradorActualizarE(int eId, String eTipo, String eDescripcion, double ePMax, double ePMin, String eFoto)
    {   try
        {
            Connection con= conexion.abrirConexion(); 
            Statement s= con.createStatement(); 
            int registro =s.executeUpdate("update estiloviaje set tipo='"+eTipo+"',descripcion='"+eDescripcion+"',presupuesto_max="+ePMax+",presupuesto_min="+ePMin+",foto='"+eFoto+"'where idEstiloViaje="+eId+";"); 
            conexion.cerrarConexion(con); 
            return true; 
        }
        catch(SQLException e)
        {
            return false; 
        }
    }
    public boolean administradorAgregaRutaE(int eId,String eFoto)
    {   try
        {
            Connection con= conexion.abrirConexion(); 
            Statement s= con.createStatement(); 
            int registro =s.executeUpdate("update estiloviaje set foto='"+eFoto+"'where idEstiloViaje="+eId+";"); 
            conexion.cerrarConexion(con); 
            return true; 
        }
        catch(SQLException e)
        {
            return false; 
        }
    }
    public boolean administradorEliminarE(int eId)
    {   try
        {
            Connection con= conexion.abrirConexion(); 
            Statement s= con.createStatement(); 
            int registro =s.executeUpdate("delete from estiloviaje where idEstiloViaje="+eId+";");  
            conexion.cerrarConexion(con); 
            return true; 
        }
        catch(SQLException e)
        {
            return false; 
        }
    }
    public DefaultTableModel administradorConsultar()
    {
         try
        {   // para abrir conexion a la BD 
            Connection con = conexion.abrirConexion(); 
            // para generar consultas 
            Statement s= con.createStatement(); 
            // para establecer el modelo al jtable 
            DefaultTableModel modelo; 
            
            try
            {    
                ResultSet rs = s.executeQuery("select idEstiloViaje as ID,tipo as Tipo,descripcion as Descripcion, presupuesto_max as PresupuestoMax, presupuesto_min as PresupuestoMin, foto as Foto from estiloviaje;"); 
                // para establecer el modelo al jtable 
                modelo= new DefaultTableModel(); 
                
                // obteniendo la informacion de las columnas que esta siendo consultadas 
                
                ResultSetMetaData rsMD = rs.getMetaData(); 
                
                // la cantidad de conlumnas que tien la consulta
                
                int cantidadColumnas = rsMD.getColumnCount(); 
                
                // establecer como cabecera el nombre de las columnas 
                
                for( int i= 1; i<= cantidadColumnas; i++)
                {
                    modelo.addColumn(rsMD.getColumnLabel(i)); 
                }
                 
                // creando las filas para el jtable 
                
                while (rs.next())
                {
                    Object[] fila = new Object[cantidadColumnas]; 
                    for ( int i=0; i< cantidadColumnas; i++)
                    {
                        fila[i]= rs.getObject(i+1); 
                        
                    }
                    modelo.addRow(fila); 
                }
                return modelo; 
            }finally
            {
                //cerrar objeto de result 
                conexion.cerrarConexion(con); 
            }
        }
        catch(SQLException e)
        {
            return null; 
        }
    }
    public DefaultTableModel Buscador(String tipo)
    {
        try
        {
            //PARA ABRIR A LA BASE DE DATOS
            Connection con = conexion.abrirConexion();
            //PARA GENERAR CONSULTAS
            Statement s = con.createStatement();
            //PARA ESTABLECER EL MODELO AL JTABLE
            DefaultTableModel modelo;
            
            try
            {
                //EJECUTAR LA CONSULTA
                ResultSet rs = s.executeQuery("select idEstiloViaje as ID,tipo as Tipo,descripcion as Descripcion, presupuesto_max as PresupuestoMax, presupuesto_min as PresupuestoMin, foto as Foto from estiloviaje where tipo='" + tipo + "'");
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
        }catch (SQLException e) {
        return null;
        }
    }
}
