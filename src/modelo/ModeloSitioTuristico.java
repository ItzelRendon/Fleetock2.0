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
    private Conexion conexion= new Conexion(); // Se crea una nueva conexion a la base de datos  
    // Inserta un registro de la BD
    public boolean administradorInsertarS(String sTipo, String sDescripcion)
    {   try
        {   Connection con= conexion.abrirConexion(); 
            Statement s= con.createStatement(); 
            int registro =s.executeUpdate("insert into tipositio(tipo, descripcion)values('"+sTipo+"','"+sDescripcion+"');"
            ); 
            conexion.cerrarConexion(con); 
            return true; 
        }
        catch(SQLException e)
        {   return false;    
        }
    }
    // Actualiza un registro de la BD
    public boolean administradorActualizarS(int sId,String sTipo, String sDescripcion)
    {   try
        {   Connection con= conexion.abrirConexion(); 
            Statement s= con.createStatement(); 
            int registro =s.executeUpdate("update tipositio set tipo='"+sTipo+"',descripcion='"+sDescripcion+"'where idTipoSitio="+sId+";"); 
            conexion.cerrarConexion(con); 
            return true; 
        }
        catch(SQLException e)
        {   return false; 
        }
    }
    // Checa que no este en otras tablas el registro que se quiere borrar 
    public int administradorConsultaParaEliminarS(int eId)
    {   try
        {   Connection con= conexion.abrirConexion(); 
            Statement s= con.createStatement(); 
            ResultSet registro =s.executeQuery(
                "SELECT COUNT(*) from tipositio inner join prefiere on prefiere.TipoSitio_idTipoSitio=tipositio.idTipoSitio WHERE tipositio.idTipoSitio="+eId+" OR "
                + "(SELECT COUNT(*) from tipositio inner join sedivideen on sedivideen.TipoSitio_idTipoSitio=tipositio.idTipoSitio WHERE tipositio.idTipoSitio="+eId+");");  
            int resultado=0; 
            while(registro.next())
                resultado=registro.getInt("COUNT(*)");
            conexion.cerrarConexion(con); 
            return resultado; 
        }
        catch(SQLException e)
        {   return -1; 
        }
    }
    // Elimina un registro de la BD 
    public boolean administradorEliminarS(int sId)
    {   try
        {   Connection con= conexion.abrirConexion(); 
            Statement s= con.createStatement(); 
            int registro =s.executeUpdate("delete from tipositio where idTipoSitio="+sId+";");  
            conexion.cerrarConexion(con); 
            return true; 
        }
        catch(SQLException e)
        {   return false; 
        }
    }
    // Hace la consulta de los registros que estan en la BD y devuelve el modelo para llenar la tabla 
    public DefaultTableModel administradorConsultar()
    {   try
        {   // para abrir conexion a la BD 
            Connection con = conexion.abrirConexion(); 
            // para generar consultas 
            Statement s= con.createStatement(); 
            // para establecer el modelo al jtable 
            DefaultTableModel modelo;
            try
            {   ResultSet rs = s.executeQuery("select idTipoSitio as ID, tipo as Tipo, descripcion as Descripción from tipositio;"); 
                // para establecer el modelo al jtable 
                modelo= new DefaultTableModel(); 
                // obteniendo la informacion de las columnas que esta siendo consultadas 
                ResultSetMetaData rsMD = rs.getMetaData(); 
                // la cantidad de conlumnas que tien la consulta
                int cantidadColumnas = rsMD.getColumnCount(); 
                // establecer como cabecera el nombre de las columnas 
                for( int i= 1; i<= cantidadColumnas; i++)
                    modelo.addColumn(rsMD.getColumnLabel(i));  
                // creando las filas para el jtable 
                while (rs.next())
                {   Object[] fila = new Object[cantidadColumnas]; 
                    for ( int i=0; i< cantidadColumnas; i++)
                        fila[i]= rs.getObject(i+1); 
                    modelo.addRow(fila); 
                }
                return modelo; 
            }finally
            {   //cerrar objeto de result 
                conexion.cerrarConexion(con); 
            }
        }
        catch(SQLException e)
        {   return null; 
        }
    }
    // Consulta los datos que se estan ingresando buscando una coincidencia en la tabla de la BD 
    public DefaultTableModel Buscador(String buscar) 
    {   try 
        {   Connection con = conexion.abrirConexion();
            Statement s = con.createStatement();
            DefaultTableModel modelo = new DefaultTableModel();
            try
            {   ResultSet rs = s.executeQuery("SELECT idTipoSitio as ID, tipo as Tipo, descripcion as Descripción FROM tipositio WHERE "
                    + "tipo LIKE '%"+buscar+"%'"
                    + "OR descripcion LIKE '%"+buscar+"%';"); 
                ResultSetMetaData rsMd = rs.getMetaData();
                int cantidadColumnas = rsMd.getColumnCount();
                for(int i=1; i<=cantidadColumnas; i++)
                    modelo.addColumn(rsMd.getColumnLabel(i));
                //Creando filas para el jtable
                while (rs.next())
                {   Object[]fila=new Object[cantidadColumnas];
                    for(int i = 0; i<cantidadColumnas; i++)
                        fila[i]=rs.getObject(i+1);
                    modelo.addRow(fila);
                }
                return modelo;
            }
            finally
            {   conexion.cerrarConexion(con);
            }
        } catch (SQLException ex) 
        {   return null; 
        }      
    }  
}