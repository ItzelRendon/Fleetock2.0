package test;

import controlador.controlLogin;
import modelo.modeloLogin;
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
    }
}
