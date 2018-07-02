/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.net.URI;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import modelo.ModeloInicio;
import modelo.ModeloPInicio;
import modelo.modeloCalculadora;
import modelo.modeloLogin;
import modelo.modeloPerfil;
import vista.Inicio;
import vista.PInicio;
import vista.vistaAyuda;
import vista.vistaCaalculadora;
import vista.vistaCalculadora;
import vista.vistaIniciarSesion;
import vista.vistaLogin;
import vista.vistaPerfil;

/**
 *
 * @author ITZEL
 */
public class ControlInicio implements ActionListener, WindowListener{

    private ModeloInicio modelo;
    private Inicio vista;
    private PInicio vistin;
    private boolean banMenu = true;
    public static String [] usuario;
    private String destino = "C:/Fleetock/img/usuario/";
    String archivo="";
    
    public ControlInicio(ModeloInicio modelo, Inicio vista, String [] usuario){
        banMenu=true;
        this.modelo = modelo;
        this.vista = vista;
        this.vista.btnMenu.addActionListener(this);
        this.vista.btnAyuda.addActionListener(this);
        this.vista.btnCalculadora.addActionListener(this);
        this.vista.btnCerrarSesion.addActionListener(this);
        this.vista.btnPerfil.addActionListener(this);
        this.vista.btnInicio.addActionListener(this);
        this.vista.addWindowListener(this);
        this.usuario=usuario; 
    }
    
    public void iniciarInicio(){
        banMenu=true;
        vista.setTitle("Fleetock");
        vista.pack();
        vista.setSize(1018, 606);
        vista.setLocationRelativeTo(null);
        vista. setIconImage(new ImageIcon(getClass().getResource("../imagenes/logo.png")).getImage());
        vista.setVisible(true);
    
        PInicio vistaPInicio = new  PInicio(); 
        ModeloPInicio modeloPInicio = new ModeloPInicio(); 
        ControlPInicio con = new ControlPInicio(modeloPInicio,vistaPInicio, this.usuario, this.vista); 
        this.vista.pnl_PInicio.add(vistaPInicio);
        this.vista.pnl_PInicio.revalidate();
        this.vista.pnl_PInicio.repaint();
        con.iniciarVista();
        vista.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        //Guarda en una variable de tipo array los datos del usuario para obtener el nombre del usuario y plasmarlo en el menu.
            String  [] a = modelo.datosUsuario(Integer.parseInt(usuario[0]),Integer.parseInt(usuario[2]));
            vista.lblNombreUsu.setText("@"+a[0]);
            
            //Busca en la direccion asignada la imagen correspondiente según el caso.
            File dir = new File(destino); 
            String[] ficheros = dir.list(); 
            int j = -1;
            ImageIcon img;
            for(int x=0; x<dir.list().length; x++)
            {   
                if(String.valueOf(a[1]).equals(destino+ficheros[x]))
                {
                    j=x;
                    break;
                }  
            }
            if(j==-1)
                //Cuando el usuario no cuanta con una foto.
                 img = new ImageIcon(getClass().getResource("../imagenes/icon-person-128.png"));
            else
                //Cuando el usuario tiene fot guardada en el bd.
                img = new ImageIcon(destino+""+ficheros[j]);
            
            
            Icon fondo = new ImageIcon(img.getImage().getScaledInstance(120, 117, Image.SCALE_DEFAULT));
            vista.lblFotoUsu.setIcon(fondo);
    }
        
    
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if(e.getSource() == vista.btnMenu)
        {        
            
            if(banMenu)
            {
                vista.Perfil.setSize(200,570);
                banMenu=false;
            } else if(!banMenu)
            {
                vista.Perfil.setSize(0,570);
                banMenu=true;
            }
        }
        else if(e.getSource() == vista.btnInicio){
            vista.lbl_Titulo.setText("Inicio");
            vista.Perfil.setSize(0,570);
            banMenu=true;
            this.vista.pnl_PInicio.removeAll();
            this.vista.pnl_PInicio.revalidate();
            this.vista.pnl_PInicio.repaint();
            
            PInicio vistaPInicio = new  PInicio(); 
            ModeloPInicio modeloPInicio = new ModeloPInicio(); 
            ControlPInicio con = new ControlPInicio(modeloPInicio,vistaPInicio, this.usuario, this.vista); 
            this.vista.pnl_PInicio.add(vistaPInicio);
            this.vista.pnl_PInicio.revalidate();
            this.vista.pnl_PInicio.repaint();
            con.iniciarVista();
        }
        else if(e.getSource() == vista.btnAyuda){
            vista.lbl_Titulo.setText("Ayuda");
            vista.Perfil.setSize(0,570);
            banMenu=true;
            this.vista.pnl_PInicio.removeAll();
            this.vista.pnl_PInicio.revalidate();
            this.vista.pnl_PInicio.repaint();
            
              vistaAyuda v = new vistaAyuda();
              this.vista.pnl_PInicio.add(v);
              this.vista.pnl_PInicio.revalidate();
              this.vista.pnl_PInicio.repaint();
              v.setVisible(true);
        }
        else if(e.getSource() == vista.btnCalculadora){
            vista.lbl_Titulo.setText("Calculadora");
            vista.Perfil.setSize(0,570);
            banMenu=true;
            this.vista.pnl_PInicio.removeAll();
            this.vista.pnl_PInicio.revalidate();
            this.vista.pnl_PInicio.repaint();
            
            vistaCalculadora panelCalculadora = new vistaCalculadora();
            modeloCalculadora modeloCalculadora = new modeloCalculadora();
            controlCalculadora control = new controlCalculadora(panelCalculadora,modeloCalculadora);
             this.vista.pnl_PInicio.add(panelCalculadora);
            this.vista.pnl_PInicio.revalidate();
            this.vista.pnl_PInicio.repaint();
            control.iniciarvista();
        }
        else if(e.getSource() == vista.btnPerfil){
            vista.lbl_Titulo.setText("Perfil");
            vista.Perfil.setSize(0,570);
            banMenu=true;
            //Limpia el panel
            this.vista.pnl_PInicio.removeAll();
            this.vista.pnl_PInicio.revalidate();
            this.vista.pnl_PInicio.repaint();
            //Crea objetos del siguiente panel
            vistaPerfil panelPerfil = new  vistaPerfil();
            modeloPerfil modeloPerfil = new modeloPerfil(); 
            controlPerfil controlPerfil = new controlPerfil(modeloPerfil,panelPerfil, usuario, vista); 
            //Lo añade al panel
            this.vista.pnl_PInicio.add(panelPerfil);
            this.vista.pnl_PInicio.revalidate();
            this.vista.pnl_PInicio.repaint();
            //Y lo muestra.
            controlPerfil.iniciarVista();
        }
        else if(e.getSource() == vista.btnCerrarSesion){
            if (JOptionPane.showConfirmDialog(vista,
                    "¿Estás seguro que deseas cerrar sesión?", "Fleetock",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                this.vista.dispose();
                modeloLogin mLogin = new modeloLogin();
                vistaLogin vLogin = new vistaLogin();
                vistaIniciarSesion np = new vistaIniciarSesion(); 
                controlLogin cLogin = new controlLogin(mLogin, vLogin,np);
                cLogin.iniciarVista();
            }
            else{
                vista.Perfil.setSize(0,570);
                banMenu=true;
            }
        }
    }
    
    @Override
    public void windowClosing(WindowEvent e) {
        if (JOptionPane.showConfirmDialog(vista,
                "¿Estás seguro que deseas cerrar sesión?", "Fleetock",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
            this.vista.dispose();
            modeloLogin mLogin = new modeloLogin();
            vistaLogin vLogin = new vistaLogin();
            vistaIniciarSesion np = new vistaIniciarSesion(); 
            controlLogin cLogin = new controlLogin(mLogin, vLogin, np);
            cLogin.iniciarVista();
        }
        else{
                vista.Perfil.setSize(0,570);
                banMenu=true;
        }
    }
    
    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }
    
}
