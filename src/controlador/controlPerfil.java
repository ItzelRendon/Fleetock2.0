/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.ParseException;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import modelo.ModeloEditarViaje;
import modelo.modeloEditarPerfil;
import modelo.modeloPerfil;
import vista.Inicio;
import vista.PEditarViaje;
import vista.vistaEditarPerfil;
import vista.vistaPerfil;
/**
 *
 * @author Esme
 */

public class controlPerfil implements ActionListener{

    private modeloPerfil modelo;
    private vistaPerfil vista;
    public static String [] usuario;
    private String destino = "C:/Fleetock/img/administrador/";
    public Inicio inicio;
    public controlPerfil(modeloPerfil modelo, vistaPerfil vista, String [] usuario, Inicio inicio)
    {
        this.modelo = modelo;
        this.vista = vista;
        this.usuario = usuario;
        this.vista.btnEditarPerfil.addActionListener(this);
        this.inicio = inicio;
        
    }

    public controlPerfil() {
        //Constructor vacio para crear instancias de él desde otras clases.
    }
    
    //Retorna la imagen correspondiente en un label segun el caso, aplica para mostrar las imagenes de los viajes y del usuario.
    public JLabel mostrarImagenes(JLabel label, int contador, String [][]a, String c[], int opcion, int tour)
    {
        int j=-1;
        ImageIcon img=null;
        File dir = new File(destino); 
        String[] f = dir.list(); 
        for(int x=0; x<dir.list().length; x++)
        {   
            switch(opcion)
            {
                case 1:
                    if(String.valueOf(c[3]).equals(destino+f[x]))
                    {
                        j=x;
                        break;
                    }
                    break;
                case 2:
                    if(tour==0)
                    {
                        if(String.valueOf(a[contador][3]).equals(destino+f[x]))
                        {
                            j=x;
                            break;
                        }
                    }
                    break;
            }
              
        }
        if(j==-1)
        {
            switch(opcion)
            {
                case 1:
                    img = new ImageIcon(getClass().getResource("../imagenes/usuario.png"));
                    break;
                case 2:
                    if(tour!=0)
                        img = new ImageIcon(getClass().getResource("../imagenes/destinomultiple.png"));
                    else
                        img = new ImageIcon(getClass().getResource("../imagenes/undestino.png"));
                    break;
            }
             
        }else
        {
            img = new ImageIcon(destino+""+f[j]);
        }
        Icon fondo = new ImageIcon(img.getImage().getScaledInstance(180, 150, Image.SCALE_DEFAULT));
        label.setIcon(fondo);
        return label;
    }
    
    //Muestra los viajes creados por el usuario
   public void mostrarViajes(int contadorFor, String [][] a, GridBagConstraints gridBagConstraints, int con, int con1, int cantidadDestinos)
   {
        JPanel jp = new JPanel();
        jp.setBackground(new java.awt.Color(210,210,210));
        JLabel jl = new JLabel();
        
        if(cantidadDestinos==1)
            //Si el viaje tiene más de un destino, plasma la imagen 'triptour'
            jp.add(mostrarImagenes(jl, contadorFor, a, null, 2, 1));
        else
            //Si no, muestra la imagen guardada en la bd o la de default, segun el caso.
            jp.add(mostrarImagenes(jl, contadorFor, a, null, 2, 0));
        
        //posicion del layout 
        gridBagConstraints.insets = new Insets(10, 0, 10, 10);
        JPanel jp2 = new JPanel();
        jp2.setSize(200,680);
        jp2.setLayout(new BorderLayout());
        JButton btn = new JButton("Ver");
        btn.setName(a[contadorFor][0]);
        JLabel jl2 = new JLabel("<html><p>"+a[contadorFor][1]+"</p>"+"\n"+"<p>Estado: "+a[contadorFor][2]+"</p></html>");
        //posiciono y le doy tamaño al label
        jl2.setHorizontalAlignment(SwingConstants.CENTER);
        jl2.setHorizontalTextPosition(SwingConstants.CENTER);
        jl2.setPreferredSize(new Dimension(200, 45));

        //le doy color al label dos
        jl2.setBackground(new java.awt.Color(200,200,200));
        jl2.setOpaque(true);

        //al panel dos le agrego el panel donde esta la imagen y la posiciono en el centro
        jp2.add(jp, BorderLayout.NORTH);
        //al panel dos le agrego el label en la parte sur del panel
        jp2.add(jl2, BorderLayout.CENTER);
        jp2.add(btn, BorderLayout.SOUTH);

        gridBagConstraints.gridx = con;
        gridBagConstraints.gridy = con1;
        //agrega el panel creado al panel de la interfaz
        vista.pnlViajes.add(jp2, gridBagConstraints);
        btn.addActionListener(this);
        
   }
    public void iniciarVista()
    {
        vista.setVisible(true);
        Hora hora =  new Hora(vista.lblHora);
        
        int b=0;
        int con=0, con1=0;
        String c [] = new String [4];
        //Agarra los datos del usuario para plasmarlos en la vista del perfil.
        c=modelo.datosUsuario(Integer.parseInt(usuario[0]), Integer.parseInt(usuario[2]));
        vista.lblNombre.setText(c[0]+" "+c[1]);
        vista.lblUsuario.setText("@"+c[2]);
        
        //Muestra la imagen del usuario.
        mostrarImagenes(vista.lblFotoUsuario, 0, null, c, 1, 0);
        
        //Crea el objeto del layout gridbag para mandarlo como parametro al metodo
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        
        //Si el usuario ha creado viajes, realiza las siguientes operaciones.
        if(modelo.usuarioViajes(Integer.parseInt(usuario[0]))!= null){
            //Obtiene los datos necesarios de los viajes
            String [][] a = modelo.usuarioViajes(Integer.parseInt(usuario[0]));
            //Va a realizar las acciones x veces hasta que las instancias que existan de viajes se acaben.
            for(int i=0; i<a.length; i++){
                //Se fija si el viaje cuanta con más de un destino
                int x= modelo.contarDestinos(Integer.parseInt(a[i][0]), Integer.parseInt(usuario[0]));
                //si es así
                if(x>1)
                {
                    //manda a llamar el metodo de mostrar viajes con los parametros necesarios para que lance los datos deseados
                    mostrarViajes(i, a, gridBagConstraints, con, con1, 1);
                    b=1;
                    break;
                }
                else if(b==0)
                {
                    //al igual que si solo tiene un destino (depende del ultimo parametro).
                    mostrarViajes(i, a, gridBagConstraints, con, con1, 0);
                    //contador e if para que muestre como maximo 3 viajes por fila.
                    con++;
                    if(con>2)
                    {
                        con=0;
                        con1++;
                    }
                }
            }
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton selectedButton = (JButton) e.getSource();
        String idV = selectedButton.getName();
        if("Ver".equals(selectedButton.getText())){
            this.vista.pnlCambiante.removeAll();
            this.vista.pnlCambiante.revalidate();
            this.vista.pnlCambiante.repaint();
            ModeloEditarViaje mEV = new ModeloEditarViaje();
            PEditarViaje vEV = new PEditarViaje();
            ControlEditarViaje cEV = new ControlEditarViaje(vEV, mEV, usuario, Integer.parseInt(idV), inicio);
            this.vista.pnlCambiante.add(vEV);
            this.vista.pnlCambiante.revalidate();
            this.vista.pnlCambiante.repaint();
            try {
                cEV.iniciarVista();
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(null, "Se ha producido un error.");
            }
        }
        if(vista.btnEditarPerfil == e.getSource())
        {
            this.vista.pnlCambiante.removeAll();
            this.vista.pnlCambiante.revalidate();
            this.vista.pnlCambiante.repaint();
            vistaEditarPerfil vistaEditarPerfil = new vistaEditarPerfil();
            modeloEditarPerfil modeloEditarPerfil = new modeloEditarPerfil();
            controlEditarPerfil controlEditarPerfil = new controlEditarPerfil(modeloEditarPerfil, vistaEditarPerfil, usuario, inicio);
            this.vista.pnlCambiante.add(vistaEditarPerfil);
            this.vista.pnlCambiante.revalidate();
            this.vista.pnlCambiante.repaint();
            controlEditarPerfil.iniciarVista();
        }

    }

    
}
