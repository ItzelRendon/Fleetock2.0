/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import modelo.ModeloInicio;
import modelo.ModeloPInicio;

import modelo.modeloLogin;
import modelo.modeloRegistrar;
import vista.AdministradorMenu;
import vista.Inicio;
import vista.PInicio;
import vista.vistaIniciarSesion;
import vista.vistaLogin;
import vista.vistaPerfil;
import vista.vistaRegistrar;
/**
 *
 * @author alfredo
 */
public class controlLogin implements ActionListener
{
    private modeloLogin modelo;
    private vistaLogin vista;
    private ModeloPInicio modelin;
    private PInicio vistin;
    public String usu="";
    vistaIniciarSesion nuevoPanel = new  vistaIniciarSesion(); 
   
    public controlLogin()
    {
        this.usu="";
    }
    
    public controlLogin(modeloLogin modelo, vistaLogin vista, vistaIniciarSesion panel)
    {
        this.modelo = modelo;
        this.vista = vista; 
        this.nuevoPanel = panel; 
        new CambiaPanel(vista.jpnFijo, panel);
        panel.iniciar.addActionListener(this);
        this.vista.btnIniciarSesion.addActionListener(this);
        this.vista.btnRegistrar.addActionListener(this);  
        panel.txtcontraseña.addActionListener(this); 
        panel.txtusuario.addActionListener(this); 
        Hora hora =  new Hora(nuevoPanel.lblHora);
    }
    
    public void iniciarVista()
    {
        vista.setTitle("Login");
        vista.pack();
        vista.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        vista.setLocationRelativeTo(null);
        vista.setVisible(true);
        Hora hora =  new Hora(nuevoPanel.lblHora);
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {  
        //SE GUARDA LO QUE LE USUARIO TECLEA PARA EL INICIO DE SESION
        usu = nuevoPanel.txtusuario.getText();
        String contra = new String(nuevoPanel.txtcontraseña.getPassword());
       //VERIFICA SI SE INGRESO EN EL PAPEL INICIAR SESION
        if(nuevoPanel.iniciar == e.getSource()|| nuevoPanel.txtcontraseña == e.getSource()|| nuevoPanel.txtusuario == e.getSource())
        {
            switch (modelo.ingresar(usu, contra)) {
                //SI EL USUARIO ES ADMINISTRADOR SE ABRE ESTE PANEL
                case 1:
                    vista.setTitle("Administrador"); 
                    AdministradorMenu nuevoPanel = new  AdministradorMenu(); 
                    ControlAdministradorMenu con = new ControlAdministradorMenu(nuevoPanel); 
                    new CambiaPanel(this.vista.pnlPrincipal, nuevoPanel); 
                    con.iniciarVista();
                    break;
                case 2:
                    //SI ES USUARIO SE ABRE EL INICIO
                    vistaPerfil vistaperfil = new vistaPerfil();
                    Inicio inicioV = new Inicio();
                    ModeloInicio inicioM = new ModeloInicio();
                    ControlInicio i = new ControlInicio(inicioM, inicioV, modelo.jalarIdUsuario(usu, contra));
//                    inicioV.lblNombreUsu.setText("@"+usu);
                    i.iniciarInicio();
                    
                    vista.setVisible(false);
                    break;
                case 3:
                    //MENSAJE 
                    JOptionPane.showMessageDialog(null, "USUARIO/CONTRASEÑA INCORRECTOS");
                    break;
                default:

                    break;
            }
        }
        //SI SE PRESIONA EL BOTON REGISTRAR
        else if(vista.btnRegistrar == e.getSource())
        {
            //SE LIMPIA LA PANTALLA
            vista.setTitle("Registrarse");  
            this.vista.jpnFijo.removeAll();
            this.vista.jpnFijo.revalidate();
            this.vista.jpnFijo.repaint();
            //SE CARGA LA PANTALLA REGISTRAR
            vistaRegistrar panelRegistrar = new  vistaRegistrar(); 
            modeloRegistrar modeloR = new modeloRegistrar(); 
            controlRegistrar controlR = new controlRegistrar(modeloR,panelRegistrar, vista); 
            new CambiaPanel(this.vista.jpnFijo, panelRegistrar); 
            controlR.iniciarVista();
            
        }
        //SI SE PRESIONA EL BOTON INICIAR SECION
        else if(e.getSource() == vista.btnIniciarSesion)
        {
            //SE CARGA LA PANTALLA INICIAR SESION
            vista.setTitle("Login"); 
            vistaIniciarSesion panelRegistrar = new  vistaIniciarSesion();
            modeloLogin modelo = new modeloLogin(); 
            controlLogin control = new controlLogin(modelo, vista, panelRegistrar); 
            new CambiaPanel(this.vista.jpnFijo, panelRegistrar); 
        }
    }

    
}
