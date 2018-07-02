/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;
import modelo.Conexion; 
import java.sql.Connection; 
import java.sql.ResultSet; 
import java.sql.ResultSetMetaData;
import java.sql.SQLException; 
import java.sql.Statement; 
import javax.swing.table.DefaultTableModel; 
/**
 *
 * @author Fabiola Paez
 */
public class ModeloActividad {
    private Conexion conexion= new Conexion();
    
    public boolean administradorInsertarA(String aNombre, String aDescripcion)
    {   try
        {
            Connection con= conexion.abrirConexion(); 
            Statement s= con.createStatement(); 
            int registro =s.executeUpdate("insert into actividad(nombre, descripcion)values('"+aNombre+"','"+aDescripcion+"');"); 
            conexion.cerrarConexion(con); 
            return true; 
        }
        catch(SQLException e)
        {
          return false;    
        }
    }
    public boolean administradorInsertarP(int pIdEstiloViaje, int pIdActividad)
    {   try
        {
            Connection con= conexion.abrirConexion(); 
            Statement s= con.createStatement(); 
            int registro =s.executeUpdate("insert into posee(EstiloViaje_idEstiloViaje, Actividad_idActividad)values("+pIdEstiloViaje+","+pIdActividad+");"
            ); 
            conexion.cerrarConexion(con); 
            return true; 
        }
        catch(SQLException e)
        {
          return false;    
        }
    }
    public boolean administradorInsertarAT(int idA, int idT)
    {   try
        {
            Connection con= conexion.abrirConexion(); 
            Statement s= con.createStatement(); 
            int registro =s.executeUpdate("insert into posee(EstiloViaje_idEstiloViaje,Actividad_idActividad)values('"+idA+"','"+idT+"');"); 
            conexion.cerrarConexion(con); 
            return true; 
        }
        catch(SQLException e)
        {
          return false;    
        }
    }
    public boolean administradorActualizarA(int aId,String aNombre, String aDescripcion)
    {   try
        {
            Connection con= conexion.abrirConexion(); 
            Statement s= con.createStatement(); 
            int registro =s.executeUpdate("update actividad set nombre='"+aNombre+"',descripcion='"+aDescripcion+"'where idActividad="+aId+";"); 
            conexion.cerrarConexion(con); 
            return true; 
        }
        catch(SQLException e)
        {
            return false; 
        }
    }
    public boolean administradorEliminarAT(int aId)
    {   try
        {
            Connection con= conexion.abrirConexion(); 
            Statement s= con.createStatement(); 
            int registro =s.executeUpdate("delete from posee where EstiloViaje_idEstiloViaje="+aId+";");  
            conexion.cerrarConexion(con); 
            return true; 
        }
        catch(SQLException e)
        {
            return false; 
        }
    }
    public boolean administradorEliminarA(int aId)
    {   try
        {
            Connection con= conexion.abrirConexion(); 
            Statement s= con.createStatement(); 
            int registro =s.executeUpdate("delete from actividad where idActividad="+aId+";");  
            conexion.cerrarConexion(con); 
            return true; 
        }
        catch(SQLException e)
        {
            return false; 
        }
    }
     public boolean administradorEliminarP(int pIdEstiloViaje, int pIdActividad)
    {   try
        {
            Connection con= conexion.abrirConexion(); 
            Statement s= con.createStatement(); 
            int registro =s.executeUpdate("delete from posee where EstiloViaje_idEstiloViaje="+pIdEstiloViaje+" and Actividad_idActividad="+pIdActividad+";");  
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
                ResultSet rs = s.executeQuery("select idActividad as ID, nombre as Nombre, descripcion as Descripción from actividad;"); 
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
    public DefaultTableModel administradorConsultarTipo(int idActividad)
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
                ResultSet rs = s.executeQuery("select EstiloViaje_idEstiloViaje,tipo as 'Estilo de viaje' from estiloviaje INNER JOIN posee where idEstiloViaje = EstiloViaje_idEstiloViaje and Actividad_idActividad="+idActividad+";"); 
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
    public DefaultTableModel administradorConsultarP()
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
                ResultSet rs = s.executeQuery("select idEstiloViaje as ID, tipo as Tipo from estiloviaje;"); 
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
    public DefaultTableModel Buscador(String nombre)
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
                ResultSet rs = s.executeQuery("select * from actividad where nombre='" + nombre + "'");
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
