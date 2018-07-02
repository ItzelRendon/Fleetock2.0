package controlador;

import java.io.File;
import static com.sun.javafx.tk.ImageLoader.*;
import java.awt.Image;
import modelo.modeloAdministrador_Destino;
import vista.Administrador_Destino;

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
import modelo.modeloAdministrador_Destino2;
import controlador.controlAdministrador_Destino2;
import vista.AdministradorMenu;
import vista.Administrador_Destino2;

public class controlAdministrador_Destino implements ActionListener, MouseListener{
     private modeloAdministrador_Destino modelo;
     private Administrador_Destino vista;
     private Administrador_Destino2 vistaDestino2;
     //Se crea una carpeta en el Disco C
     private String destino = "C:/Fleetock/img/administrador/";
     private JFileChooser archivo = new JFileChooser();
     int ventana =0;
     String archi="";
     public int p=0;
    
     public controlAdministrador_Destino(modeloAdministrador_Destino modelo, Administrador_Destino vista, Administrador_Destino2 vistaDestino2){
         this.modelo = modelo;
         this.vista = vista;
         this.vistaDestino2=vistaDestino2;
         this.vista.btn_Insertar.addActionListener(this);
         this.vista.btn_Eliminar.addActionListener(this);
         this.vista.btn_Actualizar.addActionListener(this);
         this.vista.tbl_Destino.addMouseListener(this);
         this.vista.btn_Seleccionar.addActionListener(this);
         this.vista.btn_BorrarTexto.addActionListener(this);
         this.vista.btn_Buscar.addActionListener(this);
         this.vista.btn_Act.addActionListener(this);
         
         desabilitar();
     }
     
     public void iniciarVista(){
         vista.tbl_Destino.setModel(modelo.destinoConsultar());
         vista.setVisible(true);
     }
     
     //Limpia los JTextField
     public void Limpiar(){
        vista.txt_Id.setText("");
        vista.txt_Nombre.setText("");
        vista.txt_Pais.setText("");
        vista.txt_Clima.setText("");
        vista.txt_Foto.setText("");
        vista.lbl_Foto.setIcon(null);
        vista.lbl_Foto.removeAll();
        vista.lbl_Foto.revalidate();
        vista.lbl_Foto.repaint();
     }
     
     //Se valida si los campos estas vacios o no
     public String validacionCamposVacios()
    {
        if(vista.txt_Nombre.getText().isEmpty() || vista.txt_Pais.getText().isEmpty() || vista.txt_Clima.getText().isEmpty() || vista.txt_Foto.getText().isEmpty())
            return "Favor de llenar todos los campos"; 
        else return null;
    }
     
     //Habilita botones
     public void habilitar(){
         vista.btn_BorrarTexto.setEnabled(true);
         vista.btn_Actualizar.setEnabled(true);
         vista.btn_Eliminar.setEnabled(true);
         vista.btn_Act.setEnabled(true);
     }
     
     //Desabilita botones
     public void desabilitar(){
         vista.btn_BorrarTexto.setEnabled(false);
         vista.btn_Actualizar.setEnabled(false);
         vista.btn_Eliminar.setEnabled(false);
         vista.btn_Act.setEnabled(false);
     }
     
     //Compara si los campos no estan vacios para habilitar los botones
     public void CamposVacios(){
        if(vista.txt_Id.getText()!="" && vista.txt_Nombre.getText()!="" && vista.txt_Pais.getText()!="" && vista.txt_Clima.getText()!="" && vista.txt_Foto.getText()!=""){
            desabilitar();
        }
        else{
             habilitar();
        }
     }
     
     @Override
     public void actionPerformed(ActionEvent evento){
         CamposVacios();//Funcion para habilitar 
         if(vista.btn_Insertar == evento.getSource()) {
            //if(validacionCamposVacios()==null)
            //{
                if(ventana == JFileChooser.APPROVE_OPTION)
                { 
                    //Genera un fichero
                    File fichero= archivo.getSelectedFile();
                    String ruta = fichero.getAbsolutePath();
                    String nombre = fichero.getName(); 
                    archi = destino + ""+ nombre; 
                    File folder = new File(destino); 
                    if(!folder.exists())
                        folder.mkdirs(); 
                    //Genera la ruta donde se guarda la imagen
                    copyFile_Java(ruta,archi); 
                    vista.txt_Foto.setText(archi);
                }
                else 
                {
                   System.out.println(""+vista.txt_Foto.getText());
                }
                //Inserta Destino
                if(modelo.destinoInsertar(vista.txt_Nombre.getText(), vista.txt_Pais.getText(), vista.txt_Clima.getText(), vista.txt_Foto.getText())){
                    JOptionPane.showMessageDialog(null, "Registro insertado exitosamente");
                    this.vista.tbl_Destino.setModel(modelo.destinoConsultar());
                    Limpiar();
                }
          //}
         //else 
            //JOptionPane.showMessageDialog(null, ""+validacionCamposVacios());
     }
         //Boton Eliminar
         if(vista.btn_Eliminar == evento.getSource()) {
             //Muestra mensaje
             if (JOptionPane.showConfirmDialog(vista,
                    "¿Estás seguro que deseas eliminar este registro?", "Fleetock",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                        if(modelo.destinoEliminar(Integer.parseInt(vista.txt_Id.getText()), vista.txt_Nombre.getText(), vista.txt_Pais.getText(), vista.txt_Clima.getText(), vista.txt_Foto.getText())){
                           File dir = new File(destino); 
                           String[] ficheros = dir.list(); 
                           File archivoel = new File(destino+ficheros[p]);
                           //Elimina imagen del la carpeta en Disco C
                           archivoel.delete();
                           JOptionPane.showMessageDialog(null, "Registro eliminado exitosamente");
                           this.vista.tbl_Destino.setModel(modelo.destinoConsultar());
                           Limpiar();
                }
            }
         }
         //Boton Actualizar
         else if(vista.btn_Actualizar == evento.getSource()) {
             if(validacionCamposVacios()==null)
            {
                if(ventana == JFileChooser.APPROVE_OPTION)
                {   
                    //Actualiza imagen en el fichero
                    File fichero= archivo.getSelectedFile();
                    String ruta = fichero.getAbsolutePath();
                    String nombre = fichero.getName(); 
                    archi = destino + ""+ nombre; 
                    File folder = new File(destino); 
                    if(!folder.exists())
                        folder.mkdirs(); 
                    copyFile_Java(ruta,archi); 
                    vista.txt_Foto.setText(archi);
                }
                //Se manda llamar el modelo
                if(modelo.destinoActualizar(Integer.parseInt(vista.txt_Id.getText()), vista.txt_Nombre.getText(), vista.txt_Pais.getText(), vista.txt_Clima.getText(), vista.txt_Foto.getText())){
                JOptionPane.showMessageDialog(null, "Registro actualizado exitosamente");
                this.vista.tbl_Destino.setModel(modelo.destinoConsultar());
                Limpiar();
             }
            }
            else 
            JOptionPane.showMessageDialog(null, ""+validacionCamposVacios());
         }
         else if(vista.btn_BorrarTexto == evento.getSource()) {
                 Limpiar();
                 this.vista.tbl_Destino.setModel(modelo.destinoConsultar());
         }
         //Boton Seleccionar Imagen desde documentos
         else if(vista.btn_Seleccionar == evento.getSource()) {
            //Inserta y muestra imagen seleccionada
            FileNameExtensionFilter filtro = new FileNameExtensionFilter("Formato de archivo JPG", "jpg", "jpeg");
            archivo.addChoosableFileFilter(filtro);
            archivo.setDialogTitle("Abrir Archivo");
            ventana = archivo.showOpenDialog(null);
            if(ventana == JFileChooser.APPROVE_OPTION)
            {
                //Limpia Jlabel y reemplaza a nueva imagen seleccionada
                vista.lbl_Foto.removeAll();
                vista.lbl_Foto.revalidate();
                vista.lbl_Foto.repaint();
                File file = archivo.getSelectedFile();
                Image foto = vista.getToolkit().getImage(String.valueOf(file));   
                foto = foto.getScaledInstance(159, 171, Image.SCALE_DEFAULT);
                vista.lbl_Foto.setIcon(new ImageIcon(foto));
                habilitar();
            }
         }
         else if(vista.btn_Buscar == evento.getSource()) {
                //Busca el registro
                this.vista.tbl_Destino.setModel(modelo.Buscador(vista.txt_Buscar.getText()));
                vista.txt_Buscar.setText("");
        }
         else if(vista.btn_Act == evento.getSource()) { 
            //Inicia Vista nueva Destino2
            modeloAdministrador_Destino2 modelo = new modeloAdministrador_Destino2(); 
            controlAdministrador_Destino2 con = new controlAdministrador_Destino2(modelo,vistaDestino2, this.vista.txt_Id.getText()); 
            con.iniciarVista(); 
         }
     }
     
     //Guarda la Imagen
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
         if(vista.tbl_Destino == e.getSource()){
             habilitar();
             int fila = vista.tbl_Destino.rowAtPoint(e.getPoint());
             if(fila > -1){
                 //Guarda los valores de la tabla a los JTextField
                 vista.txt_Id.setText(String.valueOf(vista.tbl_Destino.getValueAt(fila, 0)));
                 vista.txt_Nombre.setText(String.valueOf(vista.tbl_Destino.getValueAt(fila, 1)));
                 vista.txt_Pais.setText(String.valueOf(vista.tbl_Destino.getValueAt(fila, 2)));
                 vista.txt_Clima.setText(String.valueOf(vista.tbl_Destino.getValueAt(fila, 3)));
                 vista.txt_Foto.setText(String.valueOf(vista.tbl_Destino.getValueAt(fila, 4)));
            }
            //Busca si la imagen existe en el fichero por medio de su ruta y la muestra
            File dir = new File(destino); 
            String[] ficheros = dir.list(); 
            for(p=0; p<dir.list().length; p++)
            {   
                //Compara si existe imagen
                if(String.valueOf(vista.tbl_Destino.getValueAt(fila, 4)).equals(destino+ficheros[p]))
                break;  
            }
            if(ficheros == null)
            {
                JOptionPane.showMessageDialog(null, "No hay ficheros en el directorio especificado");

            }else
            {
                //Muestra la imagen seleccionada en el JLabel
                int x = vista.lbl_Foto.getWidth(); 
                int y = vista.lbl_Foto.getHeight(); 
                Imagen imagen = new Imagen(x, y, destino+""+ficheros[p]); 
                vista.lbl_Foto.removeAll();
                vista.lbl_Foto.revalidate();
                vista.lbl_Foto.repaint();
                vista.lbl_Foto.add(imagen); 
                vista.lbl_Foto.repaint();
            }
       }
    }
     
     @Override
     public void mousePressed(MouseEvent e){
         
     }
     
     @Override
     public void mouseReleased(MouseEvent e){
         
     }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}