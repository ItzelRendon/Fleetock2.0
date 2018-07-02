/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import com.toedter.calendar.JDateChooser;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author ITZEL
 */
public class modeloPNuevoViaje {
    private Conexion conexion = new Conexion();
    public String [] destinoDatos(int id){   
       try
        {
            //abrir conexión
            Connection con= conexion.abrirConexion(); 
            //generar consultas
            Statement s = con.createStatement(); 
            //consulta
            ResultSet rs = s.executeQuery("select nombre, foto from destino where idDestino = " + id);
                   
            //declaración del array
            String [] a = new String[2];
            rs.next();
            //copiar del resultset al array
            a[0] = rs.getString(1);
            a[1] = rs.getString(2);
            //cerrar conexión
            conexion.cerrarConexion(con); 
            return a; 
        }
        catch(SQLException e)
        {
          return null;    
        }
    }
    
    public String [] actDatos(int id){   
       try
        {                   
            //abrir conexión
            Connection con= conexion.abrirConexion(); 
            //generar consultas
            Statement s = con.createStatement(); 
            //consulta
            ResultSet rs = s.executeQuery("SELECT actividad.nombre, tiene.foto, tiene.localizacion FROM actividad INNER JOIN tiene ON tiene.Actividad_idActividad = actividad.idActividad INNER JOIN posee on posee.Actividad_idActividad = actividad.idActividad WHERE actividad.idActividad="+id+";");
                   
            //declaración del array
            String [] a = new String[4];
            rs.next();
            //copiar del resultset al array
            a[0] = rs.getString(1);
            a[1] = rs.getString(2);
            a[2] = rs.getString(3);
            //cerrar conexión
            conexion.cerrarConexion(con); 
            return a; 
        }
        catch(SQLException e)
        {
          return null;    
        }
    }
    
    public String [][] transDatos(int idDestino, int idEstilo){   
       try
        {                   
            //abrir conexión
            Connection con= conexion.abrirConexion(); 
            //generar consultas
            Statement s = con.createStatement(); 
            //consulta
            String consulta = "SELECT transporte.tipo, 1, transporte.foto, transporte.idTransporte FROM `transporte` INNER JOIN sedesplazaen on sedesplazaen.Transporte_idTransporte = transporte.idTransporte INNER JOIN corresponde on corresponde.Transporte_idTransporte = transporte.idTransporte INNER JOIN estiloviaje on estiloviaje.idEstiloViaje = corresponde.EstiloViaje_idEstiloViaje WHERE sedesplazaen.Destino_idDestino = "+idDestino+" and estiloviaje.presupuesto_min <= (SELECT estiloviaje.presupuesto_min from estiloviaje WHERE estiloviaje.idEstiloViaje = "+idEstilo+") GROUP BY transporte.tipo;";
            ResultSet rs = s.executeQuery(consulta);
            
            int count = 0;
            while (rs.next()) {
                ++count;
            }
            //declaración del array
            String [][] a = new String [count][3];
            //se regresa al primero
            rs.beforeFirst();
            //contador para copiar del resultset al array
            int i = 0;
            //copiar del resultset al array
            while (rs.next())
            {
                a[i][0] = rs.getString(1);
                a[i][1] = rs.getString(2);
                a[i][2] = rs.getString(3);
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
     
    public boolean insertarViaje(String nombre, String finicio, String ffin, String estado, int idEstilo, int idUsuario, int idDestino, List<JDateChooser> jdFecha, List<String> actSelec){
        try{
            //Se abre la conexion con la bd.
            Connection con = conexion.abrirConexion();
            //Permite crear consultas
            Statement s = con.createStatement();
            
            //Inserta un registro en la tabla Viaje.
            int idViaje = ultimoViaje()+1;
            int registroViaje = s.executeUpdate(
                    "INSERT INTO `viaje`(`idViaje`,`nombre`, `fecha_inicio`, `fecha_fin`, `estadoDelViaje`, "
                            + "`Usuario_idUsuario`, `EstiloViaje_idEstiloViaje`) VALUES "
                            + "("+idViaje+",'"+nombre+"', '"+finicio+"', '"+ffin+"','"+estado+"',"+idUsuario+","+idEstilo+");"                
            );
            
            //Inserta un registro en la tabla pertenece.
            int registroPertenece = s.executeUpdate("INSERT INTO `pertenece`(`Viaje_idViaje`, `Destino_idDestino`) "
                    + "VALUES ("+idViaje+","+idDestino+")");
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            //Inserta un registro en la tabla contiene.
            for(int i=0; i<actSelec.size(); i++){
                int registroContiene = s.executeUpdate("INSERT INTO `contiene`(`Viaje_idViaje`, `Actividad_idActividad`, "
                        + "`fechaActividad`) VALUES ("+idViaje+","+actSelec.get(i)+", '"+sdf.format(jdFecha.get(i).getDate())+"')");
            }
                        
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
    
    public int ultimoViaje(){
        try
        {                   
            //abrir conexión
            Connection con= conexion.abrirConexion(); 
            //generar consultas
            Statement s = con.createStatement(); 
            //consulta
            ResultSet rs = s.executeQuery("select idViaje from viaje order by viaje.idViaje DESC limit 1");
            rs.next();
            //copiar del resultset al array
            int  a  = Integer.parseInt(rs.getString(1));
            //cerrar conexión
            conexion.cerrarConexion(con); 
            return a; 
        }
        catch(SQLException e)
        {
          return -1;    
        }
    }

    
     
}
