/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import vista.vistaLogin;
import vista.prueba;

/**
 *
 * @author Fabiola Paez
 */
public class controlPrueba implements ActionListener{
    private prueba vista;
    
    public controlPrueba(prueba vista)
    {
        this.vista = vista;
    }

     public void iniciarVista()
    {
        
        vista.setVisible(true);
    }
    

    @Override
    public void actionPerformed(ActionEvent ae) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
