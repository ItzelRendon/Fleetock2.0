/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import modelo.ModeloInicio;
import modelo.modeloRegistrar;
import modelo.modeloRegistrar2;
import vista.Inicio;
import vista.vistaLogin;
import vista.vistaPerfil;
import vista.vistaRegistrar2;

/**
 *
 * @author Holi
 */
public class controlRegistrar2 implements ActionListener{
    private modeloRegistrar modeloRegistrar = new modeloRegistrar();
    private modeloRegistrar2 modelo;
    private vistaRegistrar2 vista;
    private vistaLogin vistaLogin;
    private String usu="", contra="";
    public static String [] a;
    public controlRegistrar2(modeloRegistrar2 modelo, vistaRegistrar2 vista, String usu, String contra, vistaLogin cl)
    {
        this.modelo = modelo;
        this.vista = vista;
        this.vista.btn_listo.addActionListener(this);
        this.usu=usu;
        this.contra=contra;
        this.vistaLogin=cl;
    }
    public void iniciarVista()
    {
        vista.setVisible(true);
        Hora hora =  new Hora(vista.lblHora);
    }
    public int agarrarId(int id)
    {
        int idUsuario =id;
        return idUsuario;
    }
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        
         //Si selecciona el boton.
        if(vista.btn_listo == e.getSource())
        {
            //Agarrar el id del usuario
            int idUsuario=(modeloRegistrar.extraerIdUsuario())-1;
            System.out.println(idUsuario);
            int seleccionados=0, bandera=0;
            if(vista.chbx_Bosque.isSelected())
            {
                bandera=1;
                if(!modelo.insertarPreferencias(idUsuario, 3))
                    JOptionPane.showMessageDialog(null, "Error al ingresar datos en la base de datos.");
            }
                
            if(vista.chbx_Desierto.isSelected())
            {
                bandera=1;
                if(!modelo.insertarPreferencias(idUsuario, 2))
                        JOptionPane.showMessageDialog(null, "Error al ingresar datos en la base de datos.");
            }
                
            if(vista.chbx_Montania.isSelected())
            {
                bandera=1;
                if(!modelo.insertarPreferencias(idUsuario, 4))
                    JOptionPane.showMessageDialog(null, "Error al ingresar datos en la base de datos.");
            }
                
            if(vista.chbx_Playa.isSelected())
            {
                bandera=1;
                if(!modelo.insertarPreferencias(idUsuario, 1))
                    JOptionPane.showMessageDialog(null, "Error al ingresar datos en la base de datos.");
            }
                
            if(vista.chbx_Selva.isSelected())
            {
                bandera=1;
                if(!modelo.insertarPreferencias(idUsuario, 9))
                    JOptionPane.showMessageDialog(null, "Error al ingresar datos en la base de datos.");
            }
            if(vista.chbx_ciudad.isSelected())
            {
                bandera=1;
                if(!modelo.insertarPreferencias(idUsuario, 14))
                    JOptionPane.showMessageDialog(null, "Error al ingresar datos en la base de datos.");
            }
            if(vista.chbx_manglar.isSelected())
            {
                bandera=1;
                if(!modelo.insertarPreferencias(idUsuario, 13))
                    JOptionPane.showMessageDialog(null, "Error al ingresar datos en la base de datos.");
            }
            if(vista.chbx_sabana.isSelected())
            {
                bandera=1;
                if(!modelo.insertarPreferencias(idUsuario, 8))
                    JOptionPane.showMessageDialog(null, "Error al ingresar datos en la base de datos.");
            }
            if(vista.chbx_glaciar.isSelected())
            {
                bandera=1;
                if(!modelo.insertarPreferencias(idUsuario, 10))
                    JOptionPane.showMessageDialog(null, "Error al ingresar datos en la base de datos.");
            }
            if(bandera==0)
            {
                JOptionPane.showMessageDialog(null, "Seleccione al menos una preferencia de destino.");
            }
            else
            {
                JOptionPane.showMessageDialog(null, "¡Gracias!");
                vistaLogin.dispose();
                vistaPerfil vistaperfil = new vistaPerfil();
                Inicio inicioV = new Inicio();
                ModeloInicio inicioM = new ModeloInicio();
                a = modelo.jalarIdUsuario(usu, contra);
                ControlInicio i = new ControlInicio(inicioM, inicioV, a);
                
                i.iniciarInicio();
                
            }
                
        }
        
    }
    
}
