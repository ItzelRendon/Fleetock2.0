/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import static controlador.controlPNuevoViaje.usuario;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import modelo.ModeloEditarViaje;
import modelo.modeloPerfil;
import vista.Inicio;
import vista.PEditarViaje;
import vista.vistaPerfil;

/**
 *
 * @author ITZEL
 */
public class ControlEditarViaje implements ActionListener, PropertyChangeListener{
    public PEditarViaje vista;
    public ModeloEditarViaje modelo;
    public static String [] usuario;
    public Inicio inicio;
    public int idViaje;
    String estado = "";
    
    public ControlEditarViaje(PEditarViaje vista, ModeloEditarViaje modelo, String [] usuario, int idViaje, Inicio inicio){
//        System.out.println("");
        this.vista = vista;
        this.modelo = modelo;
        this.idViaje = idViaje;
        this.inicio = inicio;
        this.usuario = usuario;
    }
    
    public void iniciarVista() throws ParseException{
        vista.setVisible(true);
//        System.out.println("idViaje: " + idViaje);
        datosViaje();
        vista.txtFechaInicio.addPropertyChangeListener(this);
        vista.txtFechaFin.addPropertyChangeListener(this);
        vista.btnEliminar.addActionListener(this);
        vista.btnFinalizar.addActionListener(this);
        vista.btnGuardar.addActionListener(this);
        actividadesViaje();
        
    }
    
    public void actividadesViaje(){
        this.vista.pnlPendiente.removeAll();
        this.vista.pnlPendiente.revalidate();
        this.vista.pnlPendiente.repaint();
        
        this.vista.pnlRealizadas.removeAll();
        this.vista.pnlRealizadas.revalidate();
        this.vista.pnlRealizadas.repaint();
        
        if(modelo.actividadesViaje(idViaje).length > 0){
            String [][] a = modelo.actividadesViaje(idViaje);
            for(int i=0; i<a.length; i++){
                JPanel grande = new JPanel(new BorderLayout());
               //Jp es el panel de la imagen
                JPanel jpImagen = new JPanel();
                jpImagen.setBackground(new java.awt.Color(153,153,153));
                //jele es el jlabel de la imagen
                JLabel jlImagen = new JLabel();
                
                //se carga la imagen y se añade al panel
                ImageIcon image = new ImageIcon(a[i][1]);
                Icon fondo = new ImageIcon(image.getImage().getScaledInstance(128, 128, Image.SCALE_DEFAULT));
                jlImagen.setIcon(fondo);
                //jlImagen.setText(a[i][3]); //ID de la actividad
                jpImagen.add(jlImagen);
                
                grande.add(jpImagen, BorderLayout.CENTER);
                //Fin de imagen
                
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyy");
   
                JPanel textos = new JPanel(new FlowLayout());
                JLabel txt1 = new JLabel(a[i][0]);
                String[] parts = a[i][2].split("-");
                JLabel txt2 = new JLabel(parts[2]+"-"+parts[1]+"-"+parts[0]);
              
//                JLabel txt2 = new JLabel(a[i][2]);
              
                textos.add(txt1);
                textos.add(txt2);
            
                //Pal fonfo del label
                textos.setBackground(new java.awt.Color(204,204,204));
                textos.setOpaque(true);
                
                grande.add(textos, BorderLayout.SOUTH);
                 Date fechaF = new Date();
                try {
                    if(sdf.parse(a[i][2]).after(fechaF)){
                        vista.pnlPendiente.add(grande);
                    }
                    else{
                        vista.pnlRealizadas.add(grande);
                    }
                } catch (ParseException ex) {
                    
                }
                //se añade el panel del texto al panel de actividades
                
                
 
            }
        }
        
    }
    
    public void datosViaje() throws ParseException{
        String[] viaje = modelo.viajeDatos(idViaje);
        String[] destino = modelo.destinoDatos(Integer.parseInt(viaje[5]));
        //Destino
        vista.lblDestino.setText(destino[0]);
        
        //Nombre
        vista.txtNombre.setText(viaje[0]);
        //Imagen del destino
        ImageIcon image = new ImageIcon(destino[1]);
        Icon fondo = new ImageIcon(image.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT));
        vista.lblImagen.setIcon(fondo);
        //Estado del viaje
        vista.lblEstado.setText("Estado del viaje: " + viaje[3]);
        estado = viaje[3];
        if(estado.equals("Terminado")){
            vista.btnFinalizar.setEnabled(false);
        }
        //Estilo de viaje
        if(modelo.estiloViaje().length > 0){
            String [][] a = modelo.estiloViaje();
            vista.cmb_EstiloViaje.removeAllItems();
            vista.cmb_EstiloViaje.addItem("Elige uno...");
            for(int i=0; i<a.length; i++)
                vista.cmb_EstiloViaje.addItem(a[i][0]);
            vista.cmb_EstiloViaje.setSelectedIndex(Integer.parseInt(viaje[4]));
        }
        else{
            vista.cmb_EstiloViaje.addItem("No se ha encontrado ningún estilo :(");
        }       
        vista.cmb_EstiloViaje.addActionListener(this);
        //Fecha de inicio
        vista.txtFechaInicio.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(viaje[1]));
        //Fecha de fin
        vista.txtFechaFin.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(viaje[2]));     
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        //btn finalizar
        if(e.getSource() == vista.btnFinalizar){
            //checa campos vacios
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date fechaF = new Date();
            if(vista.txtNombre.getText() == "" || estado == "" || vista.cmb_EstiloViaje.getSelectedIndex() == 0 || vista.txtFechaInicio.getDate().equals("") ){
                JOptionPane.showMessageDialog(null, "No se han acompletado todos los datos.", "¡Atención!", JOptionPane.ERROR_MESSAGE); 
            }
            else{
                //actualización de datos
                if(modelo.actualizarViaje(idViaje, vista.txtNombre.getText(), "Terminado", vista.cmb_EstiloViaje.getSelectedIndex(), sdf.format(vista.txtFechaInicio.getDate()), sdf.format(fechaF))){
                    JOptionPane.showMessageDialog(null, "El Viaje se ha finalizado.", "¡Atención!", JOptionPane.INFORMATION_MESSAGE);
                         modeloPerfil mP = new modeloPerfil();
                        vistaPerfil vP = new vistaPerfil();
                        controlPerfil cP = new controlPerfil(mP, vP, usuario, inicio);
                        this.inicio.pnl_PInicio.removeAll();
                        this.inicio.pnl_PInicio.revalidate();
                        this.inicio.pnl_PInicio.repaint();
                        
                        inicio.pnl_PInicio.add(vP);
                        inicio.pnl_PInicio.revalidate();
                        inicio.pnl_PInicio.repaint();
                        cP.iniciarVista();
                        inicio.lbl_Titulo.setText("Perfil");
                }
            }  
        }
        //btn eliminar
        else if(e.getSource() == vista.btnEliminar){
            String[] options = {"Sí", "No"};
            int x = JOptionPane.showOptionDialog(null, "¿Estás seguro que deseas eliminar el viaje?",
                "¡Atención!",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
            if (x == JOptionPane.YES_OPTION)
            {
                if(modelo.viajeEliminar(idViaje)){
                    JOptionPane.showMessageDialog(null, "El viaje se ha eliminado.", "¡Atención!", JOptionPane.INFORMATION_MESSAGE);
                     modeloPerfil mP = new modeloPerfil();
                        vistaPerfil vP = new vistaPerfil();
                        controlPerfil cP = new controlPerfil(mP, vP, usuario, inicio);
                        this.inicio.pnl_PInicio.removeAll();
                        this.inicio.pnl_PInicio.revalidate();
                        this.inicio.pnl_PInicio.repaint();
                        
                        inicio.pnl_PInicio.add(vP);
                        inicio.pnl_PInicio.revalidate();
                        inicio.pnl_PInicio.repaint();
                        cP.iniciarVista();
                        inicio.lbl_Titulo.setText("Perfil");
                }
                else{
                    JOptionPane.showMessageDialog(null, "El viaje no se ha eliminado.", "¡Atención!", JOptionPane.INFORMATION_MESSAGE);
                }
              }
        }
        //btn guardar
        else if(e.getSource() == vista.btnGuardar){
            //checa campos vacios
            if(vista.txtNombre.getText() == "" || estado == "" || vista.cmb_EstiloViaje.getSelectedIndex() == 0 || vista.txtFechaInicio.getDate().equals("") || vista.txtFechaFin.getDate().equals("")){
                JOptionPane.showMessageDialog(null, "No se han acompletado todos los datos.", "¡Atención!", JOptionPane.ERROR_MESSAGE); 
            }
            else{
                //actualización de datos
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                if(modelo.actualizarViaje(idViaje, vista.txtNombre.getText(), estado, vista.cmb_EstiloViaje.getSelectedIndex(), sdf.format(vista.txtFechaInicio.getDate()), sdf.format(vista.txtFechaFin.getDate()))){
                    JOptionPane.showMessageDialog(null, "Se han guardado las modificaciones.", "¡Atención!", JOptionPane.INFORMATION_MESSAGE);
                        modeloPerfil mP = new modeloPerfil();
                        vistaPerfil vP = new vistaPerfil();
                        controlPerfil cP = new controlPerfil(mP, vP, usuario, inicio);
                        this.inicio.pnl_PInicio.removeAll();
                        this.inicio.pnl_PInicio.revalidate();
                        this.inicio.pnl_PInicio.repaint();
                        
                        inicio.pnl_PInicio.add(vP);
                        inicio.pnl_PInicio.revalidate();
                        inicio.pnl_PInicio.repaint();
                        cP.iniciarVista();
                        inicio.lbl_Titulo.setText("Perfil");
                }
            }       
        }
    }
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt.getSource() == vista.txtFechaInicio || evt.getSource() == vista.txtFechaFin){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            //Fecha actual
            Date fechaF = new Date();
//            System.out.println("fecha fin: " +sdf.format(vista.txtFechaFin.getDate()) );
//            System.out.println("fecha inicio: " +sdf.format(vista.txtFechaInicio.getDate()));
//            System.out.println("fecha act: " + sdf.format(fechaF));
            if(vista.txtFechaFin.getDate().before(fechaF) && vista.txtFechaInicio.getDate().before(fechaF)){
                estado = "Terminado";
            }

            if(vista.txtFechaInicio.getDate().after(fechaF)){
                estado = "Por Realizar";
            }

            if(vista.txtFechaFin.getDate().after(fechaF)){
                estado = "En Curso";
            }
//            System.out.println("a"+estado);
            vista.lblEstado.setText("Estado del viaje: " + estado);
        }
    }
}
