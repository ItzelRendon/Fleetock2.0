 package test;


import controlador.CambiaPanel;
import controlador.ControlAdministradorMenu;
import controlador.controlLogin;
import modelo.modeloLogin;
import vista.AdministradorMenu;
import vista.vistaIniciarSesion;
import vista.vistaLogin;
 
 
public class Main {
    public static void main(String[] args) {
        // -------- Vista del login
        modeloLogin mo =  new modeloLogin();
        vistaLogin v = new vistaLogin();
        vistaIniciarSesion np = new vistaIniciarSesion(); 
        controlLogin co = new controlLogin(mo,v,np);
        co.iniciarVista();
        
        AdministradorMenu m = new AdministradorMenu(); 
        ControlAdministradorMenu c = new ControlAdministradorMenu(m); 
        new CambiaPanel(v.pnlPrincipal, m); 
//        c.iniciarVista();
    }
}
