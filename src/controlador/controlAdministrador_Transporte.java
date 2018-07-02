package controlador;

import java.io.File;

import static com.sun.javafx.tk.ImageLoader.*;
import static controlador.ControlEstiloViaje.copyFile_Java;
import java.awt.Image;
import modelo.modeloAdministrador_Transporte;
import vista.Administrador_Transporte;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.swing.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import vista.estiloDeViaje;
import vista.estiloViajeT;

public class controlAdministrador_Transporte implements ActionListener, MouseListener{
     private modeloAdministrador_Transporte modelo;
     private Administrador_Transporte vista;
     private estiloViajeT vista2; 
     //Se crea una carpeta en el Disco C
    private String destino = "C:/Fleetock/img/administrador/transporte/";
    private JFileChooser archivo = new JFileChooser();
    public int p=0; 
    File file;
    int ventana =0;
    String rutaArchivo="";
    boolean bandera = false; 
     private int id, idEstilo;
    
     public controlAdministrador_Transporte(modeloAdministrador_Transporte modelo, Administrador_Transporte vista, estiloViajeT vista2){
         this.modelo = modelo;
         this.vista = vista;
         this.vista2= vista2; 
         this.vista.btn_Insertar.addActionListener(this);
         this.vista.btn_Eliminar.addActionListener(this);
         this.vista.btn_Actualizar.addActionListener(this);
         this.vista.tbl_Transporte.addMouseListener(this);
         this.vista.tbl_Corresponde.addMouseListener(this);
         this.vista.btn_Seleccionar.addActionListener(this);
         this.vista.btn_BorrarTexto.addActionListener(this);
         this.vista.btn_Buscar.addActionListener(this);
         this.vista.btnEstilo.addActionListener(this);
         this.vista.btnEliminarT.addActionListener(this);
         // Botones y tabla del JFrame
        this.vista2.btnAgregarE.addActionListener(this);
        this.vista2.btnCancelar.addActionListener(this);
        this.vista2.tbl_Estilo.addMouseListener(this);
         desabilitar();
     }
     //oculta la columna de id
     public void ocultaColumna()
    {
        this.vista.tbl_Transporte.getColumnModel().getColumn(0).setMaxWidth(0);
        this.vista.tbl_Transporte.getColumnModel().getColumn(0).setMinWidth(0);
        this.vista.tbl_Transporte.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
        this.vista.tbl_Transporte.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
        this.vista.tbl_Transporte.getColumnModel().getColumn(0).setPreferredWidth(0);
    }
     //oculta la columna id
     public void ocultaColumna1()
    {
        this.vista.tbl_Corresponde.getColumnModel().getColumn(0).setMaxWidth(0);
        this.vista.tbl_Corresponde.getColumnModel().getColumn(0).setMinWidth(0);
        this.vista.tbl_Corresponde.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
        this.vista.tbl_Corresponde.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
        this.vista.tbl_Corresponde.getColumnModel().getColumn(0).setPreferredWidth(0);
    }
     //oculta la columna id
     public void ocultaColumna2()
    {
        this.vista2.tbl_Estilo.getColumnModel().getColumn(0).setMaxWidth(0);
        this.vista2.tbl_Estilo.getColumnModel().getColumn(0).setMinWidth(0);
        this.vista2.tbl_Estilo.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
        this.vista2.tbl_Estilo.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
        this.vista2.tbl_Estilo.getColumnModel().getColumn(0).setPreferredWidth(0);
    }
     public void iniciarVista(){
         vista.tbl_Transporte.setModel(modelo.transporteConsultar());
         ocultaColumna(); 
         vista.setVisible(true);
     }
     
     public void Limpiar(){
        vista.txt_Id.setText("");
        vista.txt_Tipo.setText("");
        vista.txtUrl.setText("");
        vista.lbl_Foto.setIcon(null);
        vista.lbl_Foto.removeAll();
        vista.lbl_Foto.revalidate();
        vista.lbl_Foto.repaint();
     }
     
     //Habilita los botones
     public void habilitar(){
         vista.btn_BorrarTexto.setEnabled(true);
         vista.btn_Actualizar.setEnabled(true);
         vista.btn_Eliminar.setEnabled(true);
         vista.btnEstilo.setEnabled(true);
         vista.btnEliminarT.setEnabled(true);
     }
     
     //Desabilita los botones
     public void desabilitar(){
         vista.btn_Actualizar.setEnabled(false);
         vista.btn_Eliminar.setEnabled(false);
         vista.btnEstilo.setEnabled(false);
         vista.btnEliminarT.setEnabled(false);
     }
     
     //Compara si los campos no estan vacios para poder continuar
     public String CamposVacios()
    {
        if(vista.txt_Tipo.getText().isEmpty() || vista.txtUrl.getText().isEmpty())
            return "Favor de llenar todos los campos"; 
        else return null;
    }
     //Compara si no estan vacios
     public String CamposVacios2()
    {
        if(vista2.txtCosto.getText().isEmpty() || idEstilo<1)
            return "Favor de llenar todos los campos"; 
        else return null;
    }
     
     @Override
     public void actionPerformed(ActionEvent evento){
         //Boton Insertar
         if(vista.btn_Insertar == evento.getSource()) {
             if(CamposVacios()==null)
            {   int idGenerado = modelo.administradorInsertarT(vista.txt_Tipo.getText(),"m"); 
                if(idGenerado!=0)
                {   
                    //Crea el fichero
                    JOptionPane.showMessageDialog(null, "Registro agregado exitosamente");
                    String ruta = file.getAbsolutePath(); 
                    rutaArchivo = destino+""+idGenerado+".jpg"; 
                    File folder = new File(destino); 
                    if(!folder.exists())
                        folder.mkdirs(); 
                    copyFile_Java(ruta,rutaArchivo); 
                    vista.txtUrl.setText(rutaArchivo);
                    modelo.administradorAgregaRutaT(idGenerado, rutaArchivo); 
                    this.vista.tbl_Transporte.setModel(modelo.transporteConsultar());
                    ocultaColumna();
                    Limpiar(); 
                    desabilitar(); 
                    vista.lbl_Foto.setIcon(new ImageIcon(getClass().getResource("../imagenes/fondo.jpg")));
                }else 
                    JOptionPane.showMessageDialog(null, "Error al insertar los datos");
            }else 
            {
               JOptionPane.showMessageDialog(null, ""+CamposVacios());
            } 
         }
         //Boton Eliminar
         else if(vista.btn_Eliminar == evento.getSource()) {
             if (JOptionPane.showConfirmDialog(vista,
                    "¿Estás seguro que deseas eliminar este registro?", "Fleetock",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                    if(modelo.transporteEliminar(Integer.parseInt(vista.txt_Id.getText()), vista.txt_Tipo.getText(), vista.txtUrl.getText())){
                        JOptionPane.showMessageDialog(null, "Registro eliminado exitosamente");
                        this.vista.tbl_Transporte.setModel(modelo.transporteConsultar());
                        ocultaColumna();
                        Limpiar();
                        desabilitar();
                        File dir = new File(destino); 
                        String[] ficheros = dir.list(); 
                        File archivoel = new File(destino+ficheros[p]);
                        archivoel.delete();
                    }
             }else{}
         }
         //Boton Actualizar
         else if(vista.btn_Actualizar == evento.getSource()) {
                if(CamposVacios()==null)
                    {
                        if(bandera == true)
                        {   File dir = new File(destino); 
                            String[] ficheros = dir.list(); 
                            File archivoel = new File(destino+ficheros[p]);
                            archivoel.delete();
                            String ruta = file.getAbsolutePath(); 
                            rutaArchivo = destino+""+Integer.parseInt(vista.txt_Id.getText())+".jpg"; 
                            File folder = new File(destino); 
                            if(!folder.exists())
                                folder.mkdirs(); 
                            copyFile_Java(ruta,rutaArchivo); 
                            vista.txtUrl.setText(rutaArchivo);  
                        }
                        if(modelo.transporteActualizar(Integer.parseInt(vista.txt_Id.getText()), vista.txt_Tipo.getText(), vista.txtUrl.getText()))
                        {
                            JOptionPane.showMessageDialog(null, "Registro actualizado exitosamente");
                            this.vista.tbl_Transporte.setModel(modelo.transporteConsultar());
                            ocultaColumna();
                            Limpiar();
                            desabilitar();
                            vista.lbl_Foto.setIcon(new ImageIcon(getClass().getResource("../imagenes/fondo.jpg")));
                        }else 
                            JOptionPane.showMessageDialog(null, "Error al actualizar los datos");
                    }else 
                    {
                       JOptionPane.showMessageDialog(null, ""+CamposVacios());
                    } 
            }
         //Boton borrar texto
         else if(vista.btn_BorrarTexto == evento.getSource()) {
                Limpiar();
                desabilitar();
                this.vista.tbl_Transporte.setModel(modelo.transporteConsultar());
                ocultaColumna();
        }
         //Boton actualizar
         else if(vista.btn_Seleccionar == evento.getSource()) {
            FileNameExtensionFilter filtro = new FileNameExtensionFilter("Formato de archivo JPG", "jpg", "jpeg"); 
            archivo.addChoosableFileFilter(filtro);
            archivo.setDialogTitle("Abrir archivo"); 
            ventana = archivo.showOpenDialog(null); 
            if( ventana == JFileChooser.APPROVE_OPTION)
            {

                vista.lbl_Foto.removeAll();
                vista.lbl_Foto.revalidate();
                vista.lbl_Foto.repaint();
                file = archivo.getSelectedFile(); 
                //System.out.println("extension "+ getFileExtension(file));
                if( !"jpg".equals(getFileExtension(file)) && !"png".equals(getFileExtension(file)))
                {
                    JOptionPane.showMessageDialog(null, "Favor de introducir un archivo jpg o png.");
                    vista.txtUrl.setText(""); 
                }else{ 
                Image foto = vista.getToolkit().getImage(String.valueOf(file)); 
                foto = foto.getScaledInstance(168, 159, Image.SCALE_DEFAULT); 
                vista.lbl_Foto.setIcon(new ImageIcon(foto)); 
                vista.txtUrl.setText(""+bandera); 
                bandera=true; }
                File file = archivo.getSelectedFile();
                Image foto = vista.getToolkit().getImage(String.valueOf(file));   
                foto = foto.getScaledInstance(159, 171, Image.SCALE_DEFAULT);
                vista.lbl_Foto.setIcon(new ImageIcon(foto));
                habilitar();
            }
         }
         //Boton buscar
         else if(vista.btn_Buscar == evento.getSource()) 
               {  
                    this.vista.tbl_Transporte.setModel(modelo.Buscador(vista.txt_Buscar.getText()));
                    ocultaColumna();
                    vista.txt_Buscar.setText("");
               }
         
                else if(vista.btnEstilo == evento.getSource())
                {
                    vista2.setTitle("Agregar Estilo de Viaje en Transporte");
                    vista2.setLocationRelativeTo(null);
                    vista2.setIconImage(new ImageIcon(getClass().getResource("../imagenes/logo.png")).getImage());
                    vista2.setVisible(true);
                    vista2.tbl_Estilo.setModel(modelo.administradorConsultarP()); 
                    ocultaColumna2(); 
                    desabilitar();
                }else if(vista2.btnCancelar == evento.getSource())
                {
                   vista2.dispose();
                }
                else if(vista2.btnAgregarE == evento.getSource())
                {
                   if(CamposVacios2()==null){
                    if(modelo.administradorInsertarP(Integer.parseInt(vista.txt_Id.getText()),idEstilo, Double.parseDouble(vista2.txtCosto.getText())))
                    {
                        JOptionPane.showMessageDialog(null, "Registro agregado exitosamente");
                        this.vista2.dispose();
                        this.vista.tbl_Corresponde.setModel(modelo.administradorConsultarTipo(Integer.parseInt(vista.txt_Id.getText())));
                        ocultaColumna1();
                    }else
                        JOptionPane.showMessageDialog(null, "Error al agregar los datos");
                   } else{
                       JOptionPane.showMessageDialog(null, ""+CamposVacios());
                    }
                       
                }
                else if(vista.btnEliminarT == evento.getSource())
                {
                    //JOptionPane.showMessageDialog(null, ""+vista.txt_Id.getText()+""+idEstilo);
                      if(modelo.administradorEliminarP(Integer.parseInt(vista.txt_Id.getText()), idEstilo))
                            {
                                JOptionPane.showMessageDialog(null, "Registro eliminado exitosamente");
                                this.vista.tbl_Corresponde.setModel(modelo.administradorConsultarTipo(Integer.parseInt(vista.txt_Id.getText())));
                                ocultaColumna1();
//                                this.vista2.dispose();
                            }
                }
     }
      
     private static String getFileExtension(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
        return fileName.substring(fileName.lastIndexOf(".")+1);
        else return "";
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
     
     @Override
     public void mouseClicked(MouseEvent e){
        habilitar();
        if(vista.tbl_Transporte == e.getSource())
        {
            int fila = vista.tbl_Transporte.rowAtPoint(e.getPoint());
            if(fila > -1)
            {
                vista.txt_Id.setText(String.valueOf(vista.tbl_Transporte.getValueAt(fila, 0)));
                this.id = Integer.parseInt(vista.txt_Id.getText());
                vista.txt_Tipo.setText(String.valueOf(vista.tbl_Transporte.getValueAt(fila, 1)));
                vista.txtUrl.setText(String.valueOf(vista.tbl_Transporte.getValueAt(fila, 2)));
                this.vista.tbl_Corresponde.setModel(modelo.administradorConsultarTipo(Integer.parseInt(vista.txt_Id.getText())));
                habilitar(); 
            }
            File dir = new File(destino); 
            String[] ficheros = dir.list(); 
            for(p=0; p<dir.list().length; p++)
            {   
                if(String.valueOf(vista.tbl_Transporte.getValueAt(fila, 2)).equals(destino+ficheros[p]))
                break;  
            }
            if(ficheros == null)
            {
                JOptionPane.showMessageDialog(null, "No hay ficheros en el directorio espeificado");
            }
            else
            {
                int x = vista.lbl_Foto.getWidth(); 
                int y = vista.lbl_Foto.getHeight(); 
                Imagen imagen = new Imagen(x, y, destino+""+ficheros[p]); 
                vista.lbl_Foto.removeAll();
                vista.lbl_Foto.revalidate();
                vista.lbl_Foto.repaint();
                vista.lbl_Foto.add(imagen); 
                vista.lbl_Foto.repaint();
            }
        }else if(vista2.tbl_Estilo == e.getSource())
                {    int fila = vista2.tbl_Estilo.rowAtPoint(e.getPoint()); 
                     if( fila > -1)
                     {   this.idEstilo= Integer.parseInt(String.valueOf(vista2.tbl_Estilo.getValueAt(fila, 0))); 
                         habilitar(); 
                     }
                }
                else if(vista.tbl_Corresponde == e.getSource())
                    {    int fila = vista.tbl_Corresponde.rowAtPoint(e.getPoint()); 
                         if( fila > -1)
                         {   //JOptionPane.showMessageDialog(null, ""+vista.txt_Id.getText()+""+idEstilo);
                             this.idEstilo= Integer.parseInt(String.valueOf(vista.tbl_Corresponde.getValueAt(fila, 1))); 
                             habilitar(); 
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