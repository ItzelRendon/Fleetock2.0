package controlador;

import static com.sun.javafx.tk.ImageLoader.*;
import java.awt.Image;
import modelo.modeloAdministrador_Usuario;
import vista.Administrador_Usuario;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import javax.swing.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class controlAdministrador_Usuario implements ActionListener, MouseListener{
     private modeloAdministrador_Usuario modelo;
     private Administrador_Usuario vista;
    
     public controlAdministrador_Usuario(modeloAdministrador_Usuario modelo, Administrador_Usuario vista){
         this.modelo = modelo;
         this.vista = vista;
         this.vista.btn_BloquearUsuario.addActionListener(this);
         this.vista.btn_DesbloquearUsuario.addActionListener(this);
         this.vista.btn_Actualizar.addActionListener(this);
         this.vista.tbl_Login.addMouseListener(this);
         this.vista.btn_BorrarTexto.addActionListener(this);
         this.vista.btn_Buscar.addActionListener(this);
         
         desabilitar();
     }
     
     public void iniciarVista(){
         vista.tbl_Login.setModel(modelo.BloquearUsuariosConsultar());
         vista.setVisible(true);
     }
     
     //Limpiar JTextField
     public void Limpiar(){
        vista.txt_Id.setText("");
        vista.txt_Usuario.setText("");
        vista.txt_Contraseña.setText("");
        vista.txt_Estatus.setText("");
        vista.txt_Tipo.setText("");
     }
     
     public void habilitar(){
         //Habilita botones
         vista.btn_BorrarTexto.setEnabled(true);
         vista.btn_BloquearUsuario.setEnabled(true);
         vista.btn_DesbloquearUsuario.setEnabled(true);
         vista.btn_Actualizar.setEnabled(true);
         
         //Habilita JTextField
         vista.txt_Usuario.setEnabled(true);
         vista.txt_Contraseña.setEnabled(true);
         vista.txt_Estatus.setEnabled(true);
         vista.txt_Tipo.setEnabled(true);
     }
     
     public void desabilitar(){
         //Desabilita botones
         vista.btn_BorrarTexto.setEnabled(false);
         vista.btn_BloquearUsuario.setEnabled(false);
         vista.btn_DesbloquearUsuario.setEnabled(false);
         vista.btn_Actualizar.setEnabled(false);
         
         //Desabilita JTextField
         vista.txt_Id.setEnabled(false);
         vista.txt_Usuario.setEnabled(false);
         vista.txt_Contraseña.setEnabled(false);
         vista.txt_Estatus.setEnabled(false);
         vista.txt_Tipo.setEnabled(false);
     }
     
     //Valida si los campos no estan vacios y habilita botones y JTextField
     public void CamposVacios(){
        if(vista.txt_Id.getText()!="" && vista.txt_Usuario.getText()!="" && vista.txt_Contraseña.getText()!="" && vista.txt_Estatus.getText()!="" && vista.txt_Tipo.getText()!=""){
            desabilitar();
        }
        else{
             habilitar();
        }
     }
     
     @Override
     public void actionPerformed(ActionEvent evento){
        CamposVacios();
        //Boton Bloquear Usuario
        if(vista.btn_BloquearUsuario == evento.getSource()) {
            if(modelo.BloquearUsuario(Integer.parseInt(vista.txt_Id.getText()))){
                JOptionPane.showMessageDialog(null, "Usuario bloqueado exitosamente");
                this.vista.tbl_Login.setModel(modelo.BloquearUsuariosConsultar());
                Limpiar();
               }
        }
        //Boton DesbloquearUsuario
        else if(vista.btn_DesbloquearUsuario == evento.getSource()) {
            if(modelo.DesbloquearUsuario(Integer.parseInt(vista.txt_Id.getText()))){
                JOptionPane.showMessageDialog(null, "Usuario desbloqueado exitosamente");
                this.vista.tbl_Login.setModel(modelo.BloquearUsuariosConsultar());
                Limpiar();
            }
        }
        //Boton Actualizar
        else if(vista.btn_Actualizar == evento.getSource()) {
            if(modelo.ActualizarUsuario(Integer.parseInt(vista.txt_Id.getText()), vista.txt_Usuario.getText(), vista.txt_Contraseña.getText(),vista.txt_Tipo.getText())){
                JOptionPane.showMessageDialog(null, "Usuario actualizado exitosamente");
                this.vista.tbl_Login.setModel(modelo.BloquearUsuariosConsultar());
                Limpiar();
            }
        }
        //Boton borrar texto
        else if(vista.btn_BorrarTexto == evento.getSource()) {
           Limpiar();
           this.vista.tbl_Login.setModel(modelo.BloquearUsuariosConsultar());
        }
        //Buscar registro
        else if(vista.btn_Buscar == evento.getSource()) {  
                this.vista.tbl_Login.setModel(modelo.Buscador(vista.txt_Buscar.getText()));
                vista.txt_Buscar.setText("");
        }
    }
              
     @Override
     public void mouseClicked(MouseEvent e){
         habilitar();
         if(vista.tbl_Login == e.getSource()){
             habilitar();
             int fila = vista.tbl_Login.rowAtPoint(e.getPoint());
             if(fila > -1){
                 //Guardar datos de la tabla a los JTextField
                 vista.txt_Id.setText(String.valueOf(vista.tbl_Login.getValueAt(fila, 0)));
                 vista.txt_Usuario.setText(String.valueOf(vista.tbl_Login.getValueAt(fila, 1)));
                 vista.txt_Contraseña.setText(String.valueOf(vista.tbl_Login.getValueAt(fila, 2)));
                 vista.txt_Tipo.setText(String.valueOf(vista.tbl_Login.getValueAt(fila, 3)));
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
