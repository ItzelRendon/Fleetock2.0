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
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
public class ControlEstiloViaje implements ActionListener, MouseListener, KeyListener{
    private ModeloEstiloViaje modelo; // Se crea un objeto Estilo de viaje como modelo 
    private PEstiloViaje vista; // Se crea un nuevo objeto tipo jpanel para controlar todo lo que exista en la vista 
    private String destino = "C:/Fleetock/img/administrador/estiloviaje/";//  Se utiliza como ruta para guardar las imagenes 
    private JFileChooser archivo = new JFileChooser();// Se crea un objeto para poder acceder a las carpetas de la computadora y acceder a las imagenes 
    FileNameExtensionFilter filtro = new FileNameExtensionFilter("jpg, jpeg & png","jpg","jpeg","png","JPG","JPEG","PNG"); // Se crea un filtro para que solo tome esos formatos
    public int p=0; // Bandera para validar que haya selecionado una imagen 
    File file; // Para controlar la imagen que se suba 
    int ventana =0;// Para comprobar que este un archivo arriba 
    String rutaArchivo="";// para guardar la nueva ruta del archivo que va en la base de datos 
    boolean bandera = false; // bandera cuando se modifica un archivo 
    boolean band=false; // bandera que indica si quiere modificar un registro pero este no tiene imagen que quitar 
    public ControlEstiloViaje(ModeloEstiloViaje  modelo, PEstiloViaje vista)// Se crea un constructor de parametros 
    {   this.modelo= modelo; 
        this.vista= vista; 
        this.vista.btnAgregar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);
        this.vista.btnActualizar.addActionListener(this);
        this.vista.tbl_EstiloViaje.addMouseListener(this);
        this.vista.btnSeleccionar.addActionListener(this);
        this.vista.btnCancelar.addActionListener(this);
        this.vista.btnReporte.addActionListener(this);
        this.vista.txt_Buscar.addKeyListener(this);
        this.desabilitar(); // Se desabilitan los botones cuando recien creas el objeto 
    }
    public void habilitar()// Permite que se puedan usar los botones 
    {   vista.btnActualizar.setEnabled(true);
        vista.btnAgregar.setEnabled(true);
        vista.btnEliminar.setEnabled(true);
        vista.btnCancelar.setEnabled(true);
    }
    public void desabilitar()// Hace que no se puedan usar los botones 
    {   vista.btnActualizar.setEnabled(false);
        vista.btnEliminar.setEnabled(false);
    }
    public void ocultaColumna()// Oculta la columna del id 
    {   this.vista.tbl_EstiloViaje.getColumnModel().getColumn(0).setMaxWidth(0);
        this.vista.tbl_EstiloViaje.getColumnModel().getColumn(0).setMinWidth(0);
        this.vista.tbl_EstiloViaje.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
        this.vista.tbl_EstiloViaje.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
        this.vista.tbl_EstiloViaje.getColumnModel().getColumn(0).setPreferredWidth(0);
    }
    public void limpiar()// Limpia los cuadros de texto 
    {   vista.txtId.setText(""); 
        vista.taDescripcion.setText("");
        vista.txtTipo.setText("");
        vista.txtPMax.setText("");
        vista.txtPMin.setText("");  
        vista.lblFoto.removeAll();
        vista.lblFoto.revalidate();
        vista.lblFoto.repaint();
        vista.lblFoto.setIcon(new ImageIcon(getClass().getResource("../imagenes/fondo.jpg")));
        vista.txtUrl.setText("");
        desabilitar();
    }
    public String validacionCamposVacios()// Verifica que introduzcan todos los datos 
    {   if(vista.txtTipo.getText().isEmpty() || vista.taDescripcion.getText().isEmpty() || vista.txtPMax.getText().isEmpty() || vista.txtPMin.getText().isEmpty() || vista.txtUrl.getText().isEmpty())
            return "Favor de llenar todos los campos"; 
        else if (!isNumericD(vista.txtPMax.getText()) || !isNumericD(vista.txtPMin.getText()))
            return "Favor de introducir caracteres válidos";
        else return null;
    }
    private static boolean isNumericD(String cadena)// Devuelve true si solo captura numeros enteros o con decimales
    {   try 
        {   Double.parseDouble(cadena);
            return true;
        }catch (NumberFormatException nfe){
            return false;
        }
    }
    public void iniciarVista()// Permite mostrar la vista
    {   vista.tbl_EstiloViaje.setModel(modelo.administradorConsultar()); 
        ocultaColumna(); 
        vista.setVisible(true); 
    }
    @Override
    public void actionPerformed(ActionEvent evento) 
    {   if(vista.btnAgregar == evento.getSource())//  Se agrega un nuevo elemento y lo agrega a la tabla 
        {   if(validacionCamposVacios()==null)
            {   // Primero se insertan los datos en la BD sin la ruta del archivo aun, y se guarda el id generado de este nuevo registro
                int idGenerado = modelo.administradorInsertarE(vista.txtTipo.getText(), vista.taDescripcion.getText(), Double.parseDouble(vista.txtPMax.getText()), Double.parseDouble(vista.txtPMin.getText()),"0"); 
                if(idGenerado!=-1)
                {   // Si el insertar los datos fue correcto se procede a obtener la ruta del archivo 
                    String ruta="";
                    if (bandera == true) // Para saber si selecciono otra imagen // Esto es por si selecciona una fila de la tabla y quiere volver a agregar ese campo
                        ruta = file.getAbsolutePath(); 
                    else 
                        ruta = vista.txtUrl.getText();
                    JOptionPane.showMessageDialog(null, "Registro agregado exitosamente");
                    rutaArchivo = destino+""+idGenerado+"."+getFileExtension(ruta); // Se concatena la ruta del archivo
                    File folder = new File(destino); 
                    if(!folder.exists()) // Si no exite la ruta donde se guardan las imagenes, se crea una
                        folder.mkdirs(); 
                    copyFile_Java(ruta,rutaArchivo); // Guarda el archivo en la ruta
                    if( !modelo.administradorAgregaRutaE(idGenerado, rutaArchivo )) // Actualiza el campo foto en la base de datos 
                        JOptionPane.showMessageDialog(null, "Error al insertar los datos");
                    this.vista.tbl_EstiloViaje.setModel(modelo.administradorConsultar());
                    ocultaColumna();
                    limpiar(); 
                }else 
                    JOptionPane.showMessageDialog(null, "Error al insertar los datos");
            }else 
            {
               JOptionPane.showMessageDialog(null, ""+validacionCamposVacios());
            } 
        }
        else if(vista.btnEliminar == evento.getSource()) // Elimina un registro de la tabla 
            {   if (JOptionPane.showConfirmDialog(vista,"¿Estás seguro que deseas eliminar este registro?", "Fleetock",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
                {   int pregunta = modelo.administradorConsultaParaEliminarE(Integer.parseInt(vista.txtId.getText())); // Para consultar que ese registro no tenga datos relacioandos con otra tabla
                    if( pregunta > 0)
                        JOptionPane.showMessageDialog(null, "No se puede eliminar, este registro se encuentra en otra tabla");
                    else if (pregunta == -1)
                         JOptionPane.showMessageDialog(null, "Error al eliminar los datos");
                    else if(modelo.administradorEliminarE(Integer.parseInt(vista.txtId.getText())))
                        {   JOptionPane.showMessageDialog(null, "Registro eliminado exitosamente");
                            this.vista.tbl_EstiloViaje.setModel(modelo.administradorConsultar());
                            ocultaColumna();
                            if( band == true ) // Condición para saber si tiene foto que eliminar
                            {   File dir = new File(destino); 
                                String[] ficheros = dir.list(); 
                                File archivoel = new File(destino+ficheros[p]);
                                archivoel.delete();
                            }
                            limpiar(); 
                        }else 
                            JOptionPane.showMessageDialog(null, "Error al eliminar los datos");
                }
            }
            else if(vista.btnActualizar == evento.getSource()) // Actuliza campos de una fila de la tabla 
                {   if(validacionCamposVacios()==null)
                     {  if(bandera == true) // Para saber si selecciono otra imagen 
                        {   if( band == true )// Para saber si antes tenia una imagen que ahora se borrara
                            {   File dir = new File(destino); 
                                String[] ficheros = dir.list(); 
                                File archivoel = new File(destino+ficheros[p]);
                                archivoel.delete();
                            }
                            String ruta = file.getAbsolutePath(); 
                            rutaArchivo = destino+""+Integer.parseInt(vista.txtId.getText())+"."+getFileExtension(ruta); 
                            File folder = new File(destino); 
                            if(!folder.exists())
                                folder.mkdirs(); 
                            copyFile_Java(ruta,rutaArchivo); 
                            vista.txtUrl.setText(rutaArchivo);  
                        }
                        if(modelo.administradorActualizarE(Integer.parseInt(vista.txtId.getText()),vista.txtTipo.getText(), vista.taDescripcion.getText(), Double.parseDouble(vista.txtPMax.getText()), Double.parseDouble(vista.txtPMin.getText()), vista.txtUrl.getText()))
                        {   JOptionPane.showMessageDialog(null, "Registro actualizado exitosamente");
                            this.vista.tbl_EstiloViaje.setModel(modelo.administradorConsultar());
                            ocultaColumna();
                            limpiar(); 
                            vista.lblFoto.setIcon(new ImageIcon(getClass().getResource("../imagenes/fondo.jpg")));
                        }else 
                            JOptionPane.showMessageDialog(null, "Error al actualizar los datos");
                    }else 
                       JOptionPane.showMessageDialog(null, ""+validacionCamposVacios());
                }
                else if(vista.btnSeleccionar == evento.getSource())
                    {   archivo.addChoosableFileFilter(filtro); // Se agrega el filtro al objeto archivo
                        archivo.setDialogTitle("Abrir archivo"); // Se le asigna un titulo
                        ventana = archivo.showOpenDialog(null); // Ventana guarda si se ha seleccionado una imagen
                        if( ventana == JFileChooser.APPROVE_OPTION)
                        {   vista.lblFoto.removeAll();
                            vista.lblFoto.revalidate();
                            vista.lblFoto.repaint();
                            file = archivo.getSelectedFile();
                            String rutafile=file.getAbsolutePath();
                            // Compara que haya extensiones correctas en las imagenes 
                            if( !"jpg".equals(getFileExtension(rutafile)) && !"png".equals(getFileExtension(rutafile)) && !"jpeg".equals(getFileExtension(rutafile))
                                && !"JPG".equals(getFileExtension(rutafile)) && !"PNG".equals(getFileExtension(rutafile)) && !"JPEG".equals(getFileExtension(rutafile)))
                            {   JOptionPane.showMessageDialog(null, "Favor de introducir un archivo jpg, jpeg o png.");
                                vista.txtUrl.setText(""); 
                            }
                            else
                            {   // Si ingreso una extension correcta carga la imagen a el label 
                                Image foto = vista.getToolkit().getImage(String.valueOf(file)); 
                                foto = foto.getScaledInstance(168, 159, Image.SCALE_DEFAULT); 
                                vista.lblFoto.setIcon(new ImageIcon(foto)); 
                                vista.txtUrl.setText(""+bandera); // Para indicar en la validacion que ya hay seleccionada una imagen
                                bandera=true; 
                            }
                        }
                    }
                    else if(vista.btnCancelar == evento.getSource())
                        {   this.limpiar();
                            this.desabilitar();
                        }
                        else if(vista.btnReporte == evento.getSource()) 
                        {   try
                            {   try
                                {   Conexion conexion = new Conexion(); 
                                    Connection con = conexion.abrirConexion();
                                    if( con != null)
                                    {   JasperReport reporte= null; 
                                        String path="src\\reportes\\reporteEstiloViaje.jasper"; 
                                        reporte = (JasperReport) JRLoader.loadObjectFromFile(path); 
                                        JasperPrint jprint = JasperFillManager.fillReport(reporte, null,con); 
                                        JasperViewer view = new JasperViewer(jprint, false); 
                                        view.setDefaultCloseOperation(DISPOSE_ON_CLOSE); 
                                        view.setVisible(true);
                                    }
                                }
                                catch(SQLException e)
                                {   JOptionPane.showMessageDialog(null, "Error al intentar generar el reporte");
                                }
                            }catch(JRException ex)
                            {   JOptionPane.showMessageDialog(null, "Error al intentar generar el reporte"); 
                            }
                        }      
    }
    public static void copyFile_Java(String origen, String destino) // Guarda la imagen en la ruta del servidor
    {   try 
        {   CopyOption[]  options = new CopyOption[]
            {   StandardCopyOption.REPLACE_EXISTING,
                StandardCopyOption.COPY_ATTRIBUTES    
            }; 
            Files.copy(Paths.get(origen),Paths.get(destino), options); 
            //JOptionPane.showMessageDialog(null, "Imagen guardada");
        }catch(IOException e)
        {   JOptionPane.showMessageDialog(null, "Error al guardar imagen");
            System.err.println(e.toString());
        }
    }
    private static String getFileExtension(String file) // Regresa la extension de la imagen
    {   String fileName = file;
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
        return fileName.substring(fileName.lastIndexOf(".")+1);
        else return "";
    }
    @Override
    public void mouseClicked(MouseEvent e) 
    {   if(vista.tbl_EstiloViaje == e.getSource())
        {    int fila = vista.tbl_EstiloViaje.rowAtPoint(e.getPoint()); 
            if( fila > -1) // Para colocar lo que tiene la fila en los textfield
            {   vista.txtId.setText(String.valueOf(vista.tbl_EstiloViaje.getValueAt(fila, 0)));
                vista.txtTipo.setText(String.valueOf(vista.tbl_EstiloViaje.getValueAt(fila, 1)));
                vista.taDescripcion.setText(String.valueOf(vista.tbl_EstiloViaje.getValueAt(fila, 2)));
                vista.txtPMax.setText(String.valueOf(vista.tbl_EstiloViaje.getValueAt(fila, 3)));
                vista.txtPMin.setText(String.valueOf(vista.tbl_EstiloViaje.getValueAt(fila, 4)));
                vista.txtUrl.setText(String.valueOf(vista.tbl_EstiloViaje.getValueAt(fila, 5)));
                habilitar(); 
                band=false; // Se limpia esta bandera
            }
            File dir = new File(destino); // Se crea un objeto tipo file
            String[] ficheros = dir.list(); // Se crea un arreglo que contiene la lista del fichero 
            for(p=0; p<dir.list().length; p++) // Se busca en el fichero la imagen, con la ruta que se guardo en la BD
            {   if(String.valueOf(vista.tbl_EstiloViaje.getValueAt(fila, 5)).equals(destino+ficheros[p]))
                {   band=true;
                    break;
                }
            }
            if(band == false) // Si no tiene imagen se coloca una imagen que indica que en ese registro no se cuenta con imagen 
            {   vista.lblFoto.removeAll();
                vista.lblFoto.revalidate();
                vista.lblFoto.repaint();
                vista.lblFoto.setIcon(new ImageIcon(getClass().getResource("../imagenes/fondoSinImagen.jpg")));
            }
            else // Si existe la imagen se carga en el label 
            {   int x = vista.lblFoto.getWidth(); 
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
    public void mousePressed(MouseEvent me) {}
    @Override
    public void mouseReleased(MouseEvent me) {}
    @Override
    public void mouseEntered(MouseEvent me) {}
    @Override
    public void mouseExited(MouseEvent me) {}
    // Metodos de entrada de teclado 
    @Override
    public void keyTyped(KeyEvent ke) {}
    @Override
    public void keyPressed(KeyEvent ke) {}
    @Override
    public void keyReleased(KeyEvent ke) 
    {   // Buscador en tiempo real (busca lo que se esta tecleando) 
        vista.tbl_EstiloViaje.setModel(modelo.Buscador(String.valueOf(vista.txt_Buscar.getText())));
        ocultaColumna(); 
    }
}