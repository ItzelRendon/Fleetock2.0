/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import modelo.ModeloActividad;
import modelo.ModeloEstiloViaje;
import modelo.ModeloSitioTuristico;
import modelo.modeloAdministrador_Destino;
import modelo.modeloAdministrador_Transporte;
import modelo.modeloAdministrador_Usuario;
import modelo.modeloLogin;
import vista.AdministradorMenu;
import vista.Administrador_Usuario;
import vista.Administrador_Destino;
import vista.Administrador_Destino2;
import vista.Administrador_Transporte;
import vista.PEstiloViaje;
import vista.PActividad;
import vista.PTipoSitioTuristico;
import vista.estiloDeViaje;
import vista.estiloViajeT;
import vista.vistaIniciarSesion;
import vista.vistaLogin;
/**
 *
 * @author Fabiola Paez
 */
public class ControlAdministradorMenu implements ActionListener, MouseListener{
    private AdministradorMenu vista; 
    public ControlAdministradorMenu(AdministradorMenu vista)
    {
        this.vista= vista; 
        vista.btnEstiloViaje.addActionListener(this);
        vista.btnActividad.addActionListener(this);
        vista.btnDestino.addActionListener(this);
        vista.btnTransporte.addActionListener(this);
        vista.btnSitios.addActionListener(this);
        vista.btnUsuarios.addActionListener(this);
        vista.btnSalir.addActionListener(this);
    }
    public void iniciarVista()
    {
        vista.setVisible(true); 
    }
    @Override
    public void actionPerformed(ActionEvent evento) {
        if(vista.btnEstiloViaje == evento.getSource())
        {
            PEstiloViaje nuevoPanel = new  PEstiloViaje(); 
            ModeloEstiloViaje mo = new ModeloEstiloViaje(); 
            ControlEstiloViaje con = new ControlEstiloViaje(mo,nuevoPanel); 
            new CambiaPanel(this.vista.PanelMenu,nuevoPanel);
            con.iniciarVista(); 
        }
        else if(vista.btnActividad == evento.getSource())
        {
            PActividad nuevoPanel = new  PActividad(); 
            ModeloActividad mo = new ModeloActividad(); 
            estiloDeViaje hola = new estiloDeViaje(); 
            ControlActividad con = new ControlActividad(mo,nuevoPanel, hola); 
            new CambiaPanel(this.vista.PanelMenu,nuevoPanel);
            con.iniciarVista(); 
        }
        else if(vista.btnSitios == evento.getSource())
        {
            PTipoSitioTuristico nuevoPanel = new  PTipoSitioTuristico(); 
            ModeloSitioTuristico mo = new ModeloSitioTuristico(); 
            ControlSitioTuristico con = new ControlSitioTuristico(mo,nuevoPanel); 
            new CambiaPanel(this.vista.PanelMenu,nuevoPanel);
            con.iniciarVista(); 
        }
        else if(vista.btnUsuarios == evento.getSource())
        {
            Administrador_Usuario nuevoPanel = new  Administrador_Usuario(); 
            modeloAdministrador_Usuario mo = new modeloAdministrador_Usuario(); 
            controlAdministrador_Usuario con = new controlAdministrador_Usuario(mo,nuevoPanel); 
            new CambiaPanel(this.vista.PanelMenu,nuevoPanel);
            con.iniciarVista();
        }
        else if(vista.btnDestino == evento.getSource())
        {
            Administrador_Destino2 menu = new Administrador_Destino2(); 
            Administrador_Destino nuevoPanel = new  Administrador_Destino(); 
            modeloAdministrador_Destino mo = new modeloAdministrador_Destino(); 
            controlAdministrador_Destino con = new controlAdministrador_Destino(mo,nuevoPanel, menu); 
            new CambiaPanel(this.vista.PanelMenu,nuevoPanel);
            con.iniciarVista(); 
        }
        else if(vista.btnTransporte== evento.getSource())
        {
            Administrador_Transporte nuevoPanel = new  Administrador_Transporte(); 
            modeloAdministrador_Transporte mo = new modeloAdministrador_Transporte(); 
            estiloViajeT np = new estiloViajeT(); 
            controlAdministrador_Transporte con = new controlAdministrador_Transporte(mo,nuevoPanel,np); 
            new CambiaPanel(this.vista.PanelMenu,nuevoPanel);
            con.iniciarVista(); 
        }
        else if(vista.btnSalir == evento.getSource()){
            if (JOptionPane.showConfirmDialog(vista,
                    "¿Estás seguro que deseas cerrar sesión?", "Fleetock",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                
                modeloLogin mLogin = new modeloLogin();
                vistaLogin vLogin = new vistaLogin();
                vistaIniciarSesion np = new vistaIniciarSesion(); 
                controlLogin cLogin = new controlLogin(mLogin, vLogin,np);
                new CambiaPanel(this.vista,vLogin.pnlPrincipal);
                //cLogin.iniciarVista();
            }
            else{
                
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent me) {
        
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
