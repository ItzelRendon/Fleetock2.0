/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;
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
    private Conexion conexion= new Conexion(); // Se crea una conexion 
    // Inserta datos en la BD
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
            return id; // Se regresa el id de la fila insertada para el manejo de las imeagenes en el controlador
        }
        catch(SQLException e)
        {   return -1;    
        }
    }
    // Actualiza los datos en la BD
    public boolean administradorActualizarE(int eId, String eTipo, String eDescripcion, double ePMax, double ePMin, String eFoto)
    {   try
        {   Connection con= conexion.abrirConexion(); 
            Statement s= con.createStatement(); 
            int registro =s.executeUpdate("update estiloviaje set tipo='"+eTipo+"',descripcion='"+eDescripcion+"',presupuesto_max="+ePMax+",presupuesto_min="+ePMin+",foto='"+eFoto+"'where idEstiloViaje="+eId+";"); 
            conexion.cerrarConexion(con); 
            return true; 
        }
        catch(SQLException e)
        {   return false; 
        }
    }
    // Agrega la ruta de la imagen en la BD
    public boolean administradorAgregaRutaE(int eId,String eFoto)
    {   try
        {   Connection con= conexion.abrirConexion(); 
            Statement s= con.createStatement(); 
            int registro =s.executeUpdate("update estiloviaje set foto='"+eFoto+"'where idEstiloViaje="+eId+";"); 
            conexion.cerrarConexion(con); 
            return true; 
        }
        catch(SQLException e)
        {   return false; 
        }
    }
    // Elimina un registro en la BD
    public boolean administradorEliminarE(int eId)
    {   try
        {   Connection con= conexion.abrirConexion(); 
            Statement s= con.createStatement(); 
            int registro =s.executeUpdate("delete from estiloviaje where idEstiloViaje="+eId+";");  
            conexion.cerrarConexion(con); 
            return true; 
        }
        catch(SQLException e)
        {   return false; 
        }
    }
    // Checa que no este en otras tablas el registro que se quiere borrar 
    public int administradorConsultaParaEliminarE(int eId)
    {   try
        {   Connection con= conexion.abrirConexion(); 
            Statement s= con.createStatement(); 
            ResultSet registro =s.executeQuery(
                "SELECT COUNT(*) from estiloviaje inner join corresponde on corresponde.EstiloViaje_idEstiloViaje=estiloviaje.idEstiloViaje WHERE estiloviaje.idEstiloViaje="+eId+" OR "
                + "(SELECT COUNT(*) from estiloviaje inner join posee on posee.EstiloViaje_idEstiloViaje=estiloviaje.idEstiloViaje WHERE estiloviaje.idEstiloViaje="+eId+" ) OR "
                + "(SELECT COUNT(*) from estiloviaje inner join viaje on viaje.EstiloViaje_idEstiloViaje=estiloviaje.idEstiloViaje WHERE estiloviaje.idEstiloViaje="+eId+");");  
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
            {   ResultSet rs = s.executeQuery("select idEstiloViaje as ID,tipo as Tipo,descripcion as Descripción, presupuesto_max as 'Presupuesto Máximo', presupuesto_min as 'Presupuesto Mínimo', foto as Foto from estiloviaje;"); 
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
            {   ResultSet rs = s.executeQuery("SELECT idEstiloViaje as ID,tipo as Tipo,descripcion as Descripción, presupuesto_max as 'Presupuesto Máximo', presupuesto_min as 'Presupuesto Mínimo', foto as Foto FROM estiloviaje WHERE "
                + "tipo LIKE '%"+buscar+"%'"
                + "OR descripcion LIKE '%"+buscar+"%'"
                + "OR presupuesto_max LIKE '%"+buscar+"%'"
                + "OR presupuesto_min LIKE '%"+buscar+"%'"
                + "OR foto LIKE '%"+buscar+"%';"); 
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