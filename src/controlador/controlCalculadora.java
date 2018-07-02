/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import static controlador.ControlPInicio.isNumeric;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.xml.bind.Marshaller.Listener;
import modelo.modeloCalculadora;
import vista.vistaCaalculadora;
import vista.vistaCalculadora;

/**
 *
 * @author alfredo
 */
public class controlCalculadora implements ActionListener{
    private vistaCalculadora vista;
    private modeloCalculadora modelo;
    private int idDestino;
    
    public controlCalculadora(vistaCalculadora vista,modeloCalculadora modelo)
    {
        this.vista = vista;
        this.modelo = modelo;
        this.idDestino = -1;
    }
    
    public int getIdDestino() {
        return idDestino;
    }
    
     public void setIdDestino(int idDestino) {
        this.idDestino = idDestino;
    }
    
    public void iniciarvista()
    {
        vista.setSize(1000, 500);
        vista.setVisible(true);
        //vista.jSpinner1.addChangeListener((ChangeListener) this);
        //mandan a llamar las funciones en cuanto inicia la vista
        mostrardestinos("SELECT destino.nombre, destino.foto, destino.idDestino FROM destino");
        mostraractividades("SELECT actividad.nombre, actividad.descripcion FROM actividad");
        mostrartransporte("SELECT transporte.tipo, transporte.foto FROM transporte");
    }
    
    //muestra destinos
    public void mostrardestinos(String sentencia)
    {
        FlowLayout flow = new FlowLayout();
        if(modelo.destinos(sentencia) != null)
        {
            String [][] a = modelo.destinos(sentencia);
            for(int i=0; i<a.length; i++)
                
            {    //Aqui se crea todo donde ira la imagen          
                JPanel jpImagen = new JPanel();
                jpImagen.setBackground(new java.awt.Color(153,153,153));
                JLabel jlImagen = new JLabel();
                
                jlImagen.setHorizontalAlignment(SwingConstants.CENTER);
                jlImagen.setHorizontalTextPosition(SwingConstants.CENTER);
                jlImagen.setPreferredSize(new Dimension(70, 100));
                ImageIcon image = new ImageIcon(""+a[i][1]);
                jlImagen.setIcon(image);
                jpImagen.add(jlImagen);
                vista.panel_destinos.add(jpImagen);
                
                //Aqui se crea donde se ira el texto
                JLabel jlTexto = new JLabel("<html><p>"+a[i][0]+"</p></html>");
                JPanel jpTexto = new JPanel();
                jpTexto.setSize(100,100);
                jpTexto.setLayout(new BorderLayout());
               
                jlTexto.setHorizontalAlignment(SwingConstants.LEFT);
                jlTexto.setHorizontalTextPosition(SwingConstants.LEFT);
                jlTexto.setPreferredSize(new Dimension(100, 100));
                jpTexto.add(jlTexto, BorderLayout.CENTER);
                vista.panel_destinos.add(jpTexto);
                
                //Aqui se crea el boton para selecionar
                JPanel jpbtn = new JPanel();
                jpbtn.setLayout(new FlowLayout());
                JCheckBox check = new JCheckBox("seleccionar");
                check.setName(""+ a[i][2]);
                jpbtn.add(check);
                
                check.addActionListener(this);
                
                jpTexto.add(jpbtn, BorderLayout.SOUTH);
                vista.panel_destinos.add(jpTexto);
            }           
        }
    }
    
    //Funcion donde se consulta en la base de datos para cargar las actividades
    public void mostraractividades(String sentencia)
    {
        FlowLayout flow = new FlowLayout();
        if(modelo.actividades(sentencia) != null)
        {
            String [][] a = modelo.actividades(sentencia);
            for(int i=0; i<a.length; i++)
            {        
                //AQUI SE CREA DONDE IRA EL TEXTO
                JLabel jlTexto1 = new JLabel("<html><p>"+a[i][0]+"</p></html>");
                JPanel jpTexto1 = new JPanel();
                jpTexto1.setBackground(new java.awt.Color(153,153,153));
                jpTexto1.setSize(100,100);
                jpTexto1.setLayout(new BorderLayout());
               
                jlTexto1.setHorizontalAlignment(SwingConstants.LEFT);
                jlTexto1.setHorizontalTextPosition(SwingConstants.LEFT);
                jlTexto1.setPreferredSize(new Dimension(100, 100));
                jpTexto1.add(jlTexto1, BorderLayout.CENTER);
                vista.panel_actividades.add(jpTexto1);
                
                //AQUI DONDE IRA LA OTRA INFORMACION DE LAS ACTVIDADES
                JLabel jlTexto = new JLabel("<html><p>"+a[i][1]+"</p></html>");
                JPanel jpTexto = new JPanel();
                jpTexto.setSize(150,100);
                jpTexto.setLayout(new BorderLayout());
               
                jlTexto.setHorizontalAlignment(SwingConstants.LEFT);
                jlTexto.setHorizontalTextPosition(SwingConstants.LEFT);
                jlTexto.setPreferredSize(new Dimension(150, 100));
                jpTexto.add(jlTexto, BorderLayout.CENTER);
                vista.panel_actividades.add(jpTexto);
                
                JPanel jpbtn = new JPanel();
                jpbtn.setLayout(new FlowLayout());
                JCheckBox check = new JCheckBox("Seleccionar");
                jpbtn.add(check);
                
                jpTexto.add(jpbtn, BorderLayout.SOUTH);
                vista.panel_actividades.add(jpTexto);
            }           
        }
    }
   
    //FUNCION PARA CARGAR LOS TRANSPORTE DESDE LA BASE DE DATOS
    public void mostrartransporte(String sentencia)
    {
        FlowLayout flow = new FlowLayout();
        if(modelo.transporte(sentencia) != null)
        {
            String [][] a = modelo.transporte(sentencia);
            for(int i=0; i<a.length; i++)
            {       
                //AQUI SE CREA DONDE IRA LA IMAGEN
                JPanel jpImagen = new JPanel();
                jpImagen.setBackground(new java.awt.Color(153,153,153));
                JLabel jlImagen = new JLabel();
                
                jlImagen.setHorizontalAlignment(SwingConstants.CENTER);
                jlImagen.setHorizontalTextPosition(SwingConstants.CENTER);
                jlImagen.setPreferredSize(new Dimension(70, 100));
                ImageIcon image = new ImageIcon(""+a[i][1]);
                jlImagen.setIcon(image);
                jpImagen.add(jlImagen);
                vista.panel_transporte.add(jpImagen);
                
                //AQUI SE CREA DONDE IRA EL TEXTO
                JLabel jlTexto = new JLabel("<html><p>"+a[i][0]+"</p></html>");
                JPanel jpTexto = new JPanel();
                jpTexto.setSize(100,100);
                jpTexto.setLayout(new BorderLayout());
               
                jlTexto.setHorizontalAlignment(SwingConstants.LEFT);
                jlTexto.setHorizontalTextPosition(SwingConstants.LEFT);
                jlTexto.setPreferredSize(new Dimension(100, 100));
                jpTexto.add(jlTexto, BorderLayout.CENTER);
                vista.panel_transporte.add(jpTexto);
                
                //AQUI SE CREA EL BOTON SELECCIONAR
                JPanel jpbtn = new JPanel();
                jpbtn.setLayout(new FlowLayout());
                JCheckBox check = new JCheckBox("Seleccionar");
                jpbtn.add(check);               
                jpTexto.add(jpbtn, BorderLayout.SOUTH);
                vista.panel_transporte.add(jpTexto);
            }
            
        }
    }
    
       //Validación de números
    public static boolean isNumeric(String str)
    {
      try
      {
        double d = Integer.parseInt(str);
      }
      catch(NumberFormatException nfe)
      {
        return false;
      }
      return true;
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e)
    {}
}