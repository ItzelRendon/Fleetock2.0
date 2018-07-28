/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;
import controlador.Imagen;
import java.awt.Image;
import modelo.ModeloEstiloViaje;  
import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener; 
import java.awt.event.MouseEvent; 
import java.awt.event.MouseListener; 
import vista.PEstiloViaje;
import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.filechooser.FileNameExtensionFilter;
import modelo.Conexion;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
/**
 *
 * @author Fabiola Paez
 */
public class ControlEstiloViaje implements ActionListener, MouseListener{
    private ModeloEstiloViaje modelo; // Se crea un objeto Estilo de viaje como modelo 
    private PEstiloViaje vista; // Se crea un nuevo objeto tipo jpanel para controlar todo lo que exista en la vista 
    private String destino = "C:/Fleetock/img/administrador/estiloviaje/";//  Se utiliza para guardar la ruta de las imagenes 
    private JFileChooser archivo = new JFileChooser();// Se crea un objeto para poder acceder  a la computadora y acceder a las imagenes 
    public int p=0; // banedera para validar que este arriba una imagen 
    File file; // para controlar la imagen que se suba 
    int ventana =0;// Para comprobar que este un archivo arriba 
    String rutaArchivo="";// para guardar la nueva ruta del archivo que va en la base de datos 
    boolean bandera = false; // bandera cuando se modifica un archivo 
    public ControlEstiloViaje(ModeloEstiloViaje  modelo, PEstiloViaje vista)// Se crea un controlador de parametros 
    {
        this.modelo= modelo; 
        this.vista= vista; 
        this.vista.btnAgregar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);
        this.vista.btnActualizar.addActionListener(this);
        this.vista.tbl_EstiloViaje.addMouseListener(this);
        this.vista.btnSeleccionar.addActionListener(this);
        this.vista.btnCancelar.addActionListener(this);
        this.vista.btnGenerarR.addActionListener(this);
        this.vista.btn_Buscar.addActionListener(this);
        this.desabilitar(); // Se desabilitan los botones cuando recien creas el objeto 
    }
    public void habilitar()// Permite que se puedan usar los botones 
    {
        vista.btnActualizar.setEnabled(true);
        vista.btnAgregar.setEnabled(true);
        vista.btnEliminar.setEnabled(true);
        vista.btnCancelar.setEnabled(true);
    }
    public void desabilitar()// Permite que no se puedan usar los botones 
    {
        vista.btnActualizar.setEnabled(false);
        vista.btnEliminar.setEnabled(false);
    }
    public void ocultaColumna()// oculta la columna del id 
    {
        this.vista.tbl_EstiloViaje.getColumnModel().getColumn(0).setMaxWidth(0);
        this.vista.tbl_EstiloViaje.getColumnModel().getColumn(0).setMinWidth(0);
        this.vista.tbl_EstiloViaje.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
        this.vista.tbl_EstiloViaje.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
        this.vista.tbl_EstiloViaje.getColumnModel().getColumn(0).setPreferredWidth(0);
    }
    public void limpiar()// limpia los cuadros de texto 
    {
        vista.txtId.setText(""); 
        vista.taDescripcion.setText("");
        vista.txtTipo.setText("");
        vista.txtPMax.setText("");
        vista.txtPMin.setText("");  
        vista.lblFoto.removeAll();
        vista.lblFoto.revalidate();
        vista.lblFoto.repaint();
        desabilitar();
    }
    public String validacionCamposVacios()// valida que introduzca todos los datos 
    {
        if(vista.txtTipo.getText().isEmpty() || vista.taDescripcion.getText().isEmpty() || vista.txtPMax.getText().isEmpty() || vista.txtPMin.getText().isEmpty() || vista.txtUrl.getText().isEmpty())
            return "Favor de llenar todos los campos"; 
        else if (!isNumericD(vista.txtPMax.getText()) || !isNumericD(vista.txtPMin.getText()))
            return "Favor de introducir caracteres válidos";
        else return null;
    }
    private static boolean isNumericD(String cadena)// devuelve si captura numeros
    {
        try 
        {
            Double.parseDouble(cadena);
            return true;
        }catch (NumberFormatException nfe){
            return false;
        }
    }
    public void iniciarVista()// permite mostrar la vista
    {
        vista.tbl_EstiloViaje.setModel(modelo.administradorConsultar()); 
        ocultaColumna(); 
        vista.setVisible(true); 
    }
    @Override
    public void actionPerformed(ActionEvent evento) {
        if(vista.btnAgregar == evento.getSource())//  se agrega un nuevo elemento 
        {
            if(validacionCamposVacios()==null)
            {   int idGenerado = modelo.administradorInsertarE(vista.txtTipo.getText(), vista.taDescripcion.getText(), Double.parseDouble(vista.txtPMax.getText()), Double.parseDouble(vista.txtPMin.getText()),"m"); 
                if(idGenerado!=0)
                {   
                    JOptionPane.showMessageDialog(null, "Registro agregado exitosamente");
                    String ruta = file.getAbsolutePath(); 
                    rutaArchivo = destino+""+idGenerado+".jpg"; 
                    File folder = new File(destino); 
                    if(!folder.exists())
                        folder.mkdirs(); 
                    copyFile_Java(ruta,rutaArchivo); 
                    vista.txtUrl.setText(rutaArchivo);
                    modelo.administradorAgregaRutaE(idGenerado, rutaArchivo); 
                    this.vista.tbl_EstiloViaje.setModel(modelo.administradorConsultar());
                    ocultaColumna();
                    limpiar(); 
                    vista.lblFoto.setIcon(new ImageIcon(getClass().getResource("../imagenes/fondo.jpg")));
                }else 
                    JOptionPane.showMessageDialog(null, "Error al insertar los datos");
            }else 
            {
               JOptionPane.showMessageDialog(null, ""+validacionCamposVacios());
            } 
        }
        else if(vista.btnEliminar == evento.getSource())
            {
                if (JOptionPane.showConfirmDialog(vista,
                    "¿Estás seguro que deseas eliminar este registro?", "Fleetock",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                    if(modelo.administradorEliminarE(Integer.parseInt(vista.txtId.getText())))
                    {
                        JOptionPane.showMessageDialog(null, "Registro eliminado exitosamente");
                        this.vista.tbl_EstiloViaje.setModel(modelo.administradorConsultar());
                        ocultaColumna();
                        limpiar(); 
                        File dir = new File(destino); 
                        String[] ficheros = dir.list(); 
                        File archivoel = new File(destino+ficheros[p]);
                        archivoel.delete();
                    }else 
                        JOptionPane.showMessageDialog(null, "Error al eliminar los datos");
                }else {}
            }
            else if(vista.btnActualizar == evento.getSource())
                {
                    if(validacionCamposVacios()==null)
                    {
                        if(bandera == true)
                        {   File dir = new File(destino); 
                            String[] ficheros = dir.list(); 
                            File archivoel = new File(destino+ficheros[p]);
                            archivoel.delete();
                            String ruta = file.getAbsolutePath(); 
                            rutaArchivo = destino+""+Integer.parseInt(vista.txtId.getText())+".jpg"; 
                            File folder = new File(destino); 
                            if(!folder.exists())
                                folder.mkdirs(); 
                            copyFile_Java(ruta,rutaArchivo); 
                            vista.txtUrl.setText(rutaArchivo);  
                        }
                        if(modelo.administradorActualizarE(Integer.parseInt(vista.txtId.getText()),vista.txtTipo.getText(), vista.taDescripcion.getText(), Double.parseDouble(vista.txtPMax.getText()), Double.parseDouble(vista.txtPMin.getText()), vista.txtUrl.getText()))
                        {
                            JOptionPane.showMessageDialog(null, "Registro actualizado exitosamente");
                            this.vista.tbl_EstiloViaje.setModel(modelo.administradorConsultar());
                            ocultaColumna();
                            limpiar(); 
                            vista.lblFoto.setIcon(new ImageIcon(getClass().getResource("../imagenes/fondo.jpg")));
                        }else 
                            JOptionPane.showMessageDialog(null, "Error al actualizar los datos");
                    }else 
                    {
                       JOptionPane.showMessageDialog(null, ""+validacionCamposVacios());
                    } 
                }
                else if(vista.btnSeleccionar == evento.getSource())
                    {
                        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Formato de archivo JPG", "jpg", "jpeg"); 
                        archivo.addChoosableFileFilter(filtro);
                        archivo.setDialogTitle("Abrir archivo"); 
                        ventana = archivo.showOpenDialog(null); 
                        if( ventana == JFileChooser.APPROVE_OPTION)
                        {
                            
                            vista.lblFoto.removeAll();
                            vista.lblFoto.revalidate();
                            vista.lblFoto.repaint();
                            file = archivo.getSelectedFile(); 
                            //System.out.println("extension "+ getFileExtension(file));
                            if( !"jpg".equals(getFileExtension(file)) && !"png".equals(getFileExtension(file)))
                            {
                                JOptionPane.showMessageDialog(null, "Favor de introducir un archivo jpg o png.");
                                vista.txtUrl.setText(""); 
                            }else{ 
                            Image foto = vista.getToolkit().getImage(String.valueOf(file)); 
                            foto = foto.getScaledInstance(168, 159, Image.SCALE_DEFAULT); 
                            vista.lblFoto.setIcon(new ImageIcon(foto)); 
                            vista.txtUrl.setText(""+bandera); 
                            bandera=true; }
                        }
                    }
                    else if(vista.btn_Buscar == evento.getSource())
                        {
                            this.vista.tbl_EstiloViaje.setModel(modelo.Buscador(vista.txt_Buscar.getText()));
                            ocultaColumna();
                            vista.txt_Buscar.setText("");
                        }
                        else if(vista.btnCancelar == evento.getSource())
                            {
                                this.limpiar();
                                this.desabilitar();
                            }else if(vista.btnGenerarR == evento.getSource())
                            {
                                //JOptionPane.showMessageDialog(null, "Generar reporte");
                                try{
//                                Conexion con = new Conexion(); 
//                                Connection conn = con.getConexion(); 
                                try
                                {
                                    Conexion miConexion = new Conexion(); 
                                    Connection con = miConexion.abrirConexion();
                                    if(con != null)
                                        System.out.println("Conexion abierta");
                                    JasperReport reporte= null; 
                                String path="src\\reportes\\estiloviaje.jasper"; 
                                reporte = (JasperReport) JRLoader.loadObjectFromFile(path); 
                                JasperPrint jprint = JasperFillManager.fillReport(reporte, null,con); 
                                 
                                JasperViewer view = new JasperViewer(jprint, false); 
                                view.setDefaultCloseOperation(DISPOSE_ON_CLOSE); 
                                
                                view.setVisible(true); 
                                }
                                catch(SQLException e)
                                {
                                    System.out.println("Error");
                                }
                                
                                }catch(JRException ex)
                                {
                                    Logger.getLogger(ControlEstiloViaje.class.getName()).log(Level.SEVERE, null, ex); 
                                }
                            }      
    }
    public static void copyFile_Java(String origen, String destino)
    {
        try 
        {
            CopyOption[]  options = new CopyOption[]
            {
                StandardCopyOption.REPLACE_EXISTING,
                StandardCopyOption.COPY_ATTRIBUTES
                
            }; 
            Files.copy(Paths.get(origen),Paths.get(destino), options); 
            //JOptionPane.showMessageDialog(null, "Imagen guardada");
        }catch(IOException e)
        {
            JOptionPane.showMessageDialog(null, "Error al guardar imagen");
            System.err.println(e.toString());
        }
    }
 private static String getFileExtension(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
        return fileName.substring(fileName.lastIndexOf(".")+1);
        else return "";
    }
    @Override
    public void mouseClicked(MouseEvent e) {
       if(vista.tbl_EstiloViaje == e.getSource())
       {    int fila = vista.tbl_EstiloViaje.rowAtPoint(e.getPoint()); 
            if( fila > -1)
            {   vista.txtId.setText(String.valueOf(vista.tbl_EstiloViaje.getValueAt(fila, 0)));
                vista.txtTipo.setText(String.valueOf(vista.tbl_EstiloViaje.getValueAt(fila, 1)));
                vista.taDescripcion.setText(String.valueOf(vista.tbl_EstiloViaje.getValueAt(fila, 2)));
                vista.txtPMax.setText(String.valueOf(vista.tbl_EstiloViaje.getValueAt(fila, 3)));
                vista.txtPMin.setText(String.valueOf(vista.tbl_EstiloViaje.getValueAt(fila, 4)));
                vista.txtUrl.setText(String.valueOf(vista.tbl_EstiloViaje.getValueAt(fila, 5)));
                habilitar(); 
            }
            File dir = new File(destino); 
            String[] ficheros = dir.list(); 
            for(p=0; p<dir.list().length; p++)
            {   
                if(String.valueOf(vista.tbl_EstiloViaje.getValueAt(fila, 5)).equals(destino+ficheros[p]))
                break;  
            }
            if(ficheros == null)
            {
                JOptionPane.showMessageDialog(null, "No hay ficheros en el directorio espeificado");

            }else
            {
                int x = vista.lblFoto.getWidth(); 
                int y = vista.lblFoto.getHeight(); 
                Imagen imagen = new Imagen(x, y, destino+""+ficheros[p]); 
                vista.lblFoto.removeAll();
                vista.lblFoto.revalidate();
                vista.lblFoto.repaint();
                vista.lblFoto.add(imagen); 
                vista.lblFoto.repaint();
            }
       }
    }

    @Override
    public void mousePressed(MouseEvent me) {
        
    }

    @Override
    public void mouseReleased(MouseEvent me) {
        
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        
    }

    @Override
    public void mouseExited(MouseEvent me) {
       
    }
    
}