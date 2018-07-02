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
public class ModeloSitioTuristico {
    private Conexion conexion= new Conexion();
    
    public boolean administradorInsertarS(String sTipo, String sDescripcion)
    {   try
        {
            Connection con= conexion.abrirConexion(); 
            Statement s= con.createStatement(); 
            int registro =s.executeUpdate("insert into tipositio(tipo, descripcion)values('"+sTipo+"','"+sDescripcion+"');"
            ); 
            conexion.cerrarConexion(con); 
            return true; 
        }
        catch(SQLException e)
        {
          return false;    
        }
    }
    public boolean administradorActualizarS(int sId,String sTipo, String sDescripcion)
    {   try
        {
            Connection con= conexion.abrirConexion(); 
            Statement s= con.createStatement(); 
            int registro =s.executeUpdate("update tipositio set tipo='"+sTipo+"',descripcion='"+sDescripcion+"'where idTipoSitio="+sId+";"); 
            conexion.cerrarConexion(con); 
            return true; 
        }
        catch(SQLException e)
        {
            return false; 
        }
    }
    public boolean administradorEliminarS(int sId)
    {   try
        {
            Connection con= conexion.abrirConexion(); 
            Statement s= con.createStatement(); 
            int registro =s.executeUpdate("delete from tipositio where idTipoSitio="+sId+";");  
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
                ResultSet rs = s.executeQuery("select idTipoSitio as ID, tipo as Tipo, descripcion as Descripción from tipositio;"); 
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
                ResultSet rs = s.executeQuery("select idTipoSitio as ID, tipo as Tipo, descripcion as Descripción from tipositio where tipo='" + tipo + "'");
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
