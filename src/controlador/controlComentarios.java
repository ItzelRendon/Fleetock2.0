/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import modelo.ModeloPInicio;
import modelo.modeloComentarios;
import vista.PInicio;
import vista.vistaComentarioos;



/**
 *
 * @author alfredo
 */
public class controlComentarios implements ActionListener{
    private modeloComentarios modelo;
    private vistaComentarioos vista;
    
    public controlComentarios(modeloComentarios modelo, vistaComentarioos vista)
    {
        this.modelo = modelo;
        this.vista = vista;
    }
    
    public void iniciarVista()
    {
        vista.setSize(500, 530);
        vista.setVisible(true);
        mostrarcomentarios("SELECT comentario, nombre, foto, from comentarios, destino, usuario");
    }
    //FUNCION PARA CARGAR LOS COMENTARIOS DE LA BASE DE DATOS
    public void mostrarcomentarios(String sentencia)
    {
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        if(modelo.todas(sentencia) != null){

            String [][] a = modelo.todas(sentencia);
            for(int i=0; i<a.length; i++){
                //AQUI SE CREA DONDE IRA LA IMAGEN
                JPanel jp = new JPanel();
//                jp.setBackground(new java.awt.Color(153,153,153));
                jp.setBackground(new java.awt.Color(117,221,117));
                JLabel jl = new JLabel();
                
                gridBagConstraints.insets = new Insets(10, 30, 10, 10);
                jl.setHorizontalAlignment(SwingConstants.CENTER);
                jl.setHorizontalTextPosition(SwingConstants.CENTER);
                jl.setPreferredSize(new Dimension(118, 100));
                
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = i+5;
                gridBagConstraints.fill = GridBagConstraints.VERTICAL;
                
                //AQUI SE CARGA LA IMAGEN A UN PANEL
                ImageIcon image = new ImageIcon(""+a[i][2]);
                jl.setIcon(image);
                jp.add(jl);
                vista.panle_comentarios.add(jp, gridBagConstraints);
               
                //AQUI SE CREA AL ESPACIO PARA LOS COMENTARIOS
                gridBagConstraints.insets = new Insets(10, 0, 10, 10);
                JLabel jl2 = new JLabel("<html><p>"+a[i][1]+"</p><br><p>Comentario: "+a[i][0]+"</p></html>");
                JPanel jp2 = new JPanel();
                jp2.setSize(400,100);
                jp2.setLayout(new BorderLayout());
               
                jl2.setHorizontalAlignment(SwingConstants.LEFT);
                jl2.setHorizontalTextPosition(SwingConstants.LEFT);
                jl2.setPreferredSize(new Dimension(200, 100));
                gridBagConstraints.fill = GridBagConstraints.VERTICAL;
                gridBagConstraints.gridx = 1;
                gridBagConstraints.gridy = i+5;
                jl2.setBackground(new java.awt.Color(117,221,117));
                jl2.setOpaque(true);
                
                jp2.add(jl2, BorderLayout.CENTER);
                vista.panle_comentarios.add(jp2, gridBagConstraints);
            }
            //AQUI SE CREA UN ESPACIO EN BLANCO
            gridBagConstraints.weighty=1;
            gridBagConstraints.gridy++;
            vista.panle_comentarios.add(new JLabel(" "), gridBagConstraints);
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {}
}
