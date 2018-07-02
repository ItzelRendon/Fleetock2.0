package modelo;

import java.io.FileInputStream;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class modeloAdministrador_Transporte {
  private int transporteId;
    private String transporteTipo;
    private String transporteImagen;
    private Conexion conexion = new Conexion();
    
    public int administradorInsertarT(String transporteTipo, String transporteImagen)
    {   try
        {   int id=0; 
            Connection con= conexion.abrirConexion(); 
            PreparedStatement s= con.prepareStatement(
           "insert into transporte(tipo, foto)values("
                         + "'"+transporteTipo+"','"+transporteImagen+"');"
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
    public boolean administradorInsertarP(int pIdTransporte, int pIdEstilo, double costo)
    {   try
        {
            Connection con= conexion.abrirConexion(); 
            Statement s= con.createStatement(); 
            int registro =s.executeUpdate("insert into corresponde(Transporte_idTransporte, EstiloViaje_idEstiloViaje,costo)values("+pIdTransporte+","+pIdEstilo+","+costo+");"
            ); 
            conexion.cerrarConexion(con); 
            return true; 
        }
        catch(SQLException e)
        {
          return false;    
        }
    }
    public boolean administradorAgregaRutaT(int tId,String tFoto)
    {   try
        {
            Connection con= conexion.abrirConexion(); 
            Statement s= con.createStatement(); 
            int registro =s.executeUpdate("update transporte set foto='"+tFoto+"'where idTransporte="+tId+";"); 
            conexion.cerrarConexion(con); 
            return true; 
        }
        catch(SQLException e)
        {
            return false; 
        }
    }
    public DefaultTableModel administradorConsultarTipo(int idTransporte)
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
                ResultSet rs = s.executeQuery("select Transporte_idTransporte as ID,EstiloViaje_idEstiloViaje as 'Estilo de viaje', costo as Costo from transporte INNER JOIN corresponde where idTransporte = Transporte_idTransporte and idTransporte="+idTransporte+";"); 
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
    public boolean administradorEliminarP(int pIdTransporte, int pIdEstilo)
    {   try
        {
            Connection con= conexion.abrirConexion(); 
            Statement s= con.createStatement(); 
            int registro =s.executeUpdate("delete from corresponde where Transporte_idTransporte="+pIdTransporte+" and EstiloViaje_idEstiloViaje="+pIdEstilo+";");  
            conexion.cerrarConexion(con); 
            return true; 
        }
        catch(SQLException e)
        {
            return false; 
        }
    }
    public boolean transporteInsertar(String transporteTipo, String transporteImagen) {            
        try {
            Connection con = conexion.abrirConexion();
            FileInputStream archivofoto;
            //Para ejecutar la consulta
            Statement s = con.createStatement();
            
            //Update en la tabla destino
            int registro = s.executeUpdate(
           "insert into destino(tipo, foto)values("
                         + "'"+transporteTipo+"','"+transporteImagen+"');");
            
            conexion.cerrarConexion(con);
            return true;
            
        }catch (SQLException e){
            return false;
        }
    }     
    
    public boolean transporteActualizar(int transporteId, String transporteTipo, String transporteImagen) {                                           
        try {
            Connection con = conexion.abrirConexion();
            
            //Para ejecutar la consulta
            Statement s = con.createStatement();
            
            //Update en la tabla usuarios
            int registro = s.executeUpdate(
                "update transporte set tipo = '" 
                    + transporteTipo + "', foto = '" + transporteImagen
                                + "'where idTransporte = " + transporteId + ";");
                    
            conexion.cerrarConexion(con);
            return true;
            
        }catch (SQLException e){
            return false;
        }
    }     
    
    public boolean transporteEliminar(int transporteId, String transporteTipo, String transporteImagen) {                                         
        try {
            Connection con = conexion.abrirConexion();
            
            //Para ejecutar la consulta
            Statement s = con.createStatement();
            
            //Update en la tabla usuarios
            int registro = s.executeUpdate(
            //BORRA EN LA TABLA DE USUARIOS
                "delete from transporte where idTransporte = " + transporteId + ";");
            
            conexion.cerrarConexion(con);
            return true;
            
        }catch (SQLException e){
            return false;
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
    public DefaultTableModel transporteConsultar(){
        try{
            //PARA ABRIR A LA BASE DE DATOS
            Connection con = conexion.abrirConexion();
            //PARA GENERAR CONSULTAS
            Statement s = con.createStatement();
            //PARA ESTABLECER EL MODELO AL JTABLE
            DefaultTableModel modelo;
            
            try{
                //EJECUTAR LA CONSULTA
                ResultSet rs = s.executeQuery("select idTransporte as ID, tipo as Tipo, foto as Foto from transporte;");
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
    
    public DefaultTableModel Buscador(String transporteTipo){
        try{
            //PARA ABRIR A LA BASE DE DATOS
            Connection con = conexion.abrirConexion();
            //PARA GENERAR CONSULTAS
            Statement s = con.createStatement();
            //PARA ESTABLECER EL MODELO AL JTABLE
            DefaultTableModel modelo;
            
            try{
                //EJECUTAR LA CONSULTA
                ResultSet rs = s.executeQuery("select idTransporte as ID, tipo as Tipo, foto as Foto from transporte where tipo='" + transporteTipo + "'");
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