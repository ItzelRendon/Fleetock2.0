/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import com.toedter.calendar.JDateChooser;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import modelo.modeloPNuevoViaje;
import modelo.modeloPerfil;
import vista.PNuevoViaje;
import vista.Inicio;
import vista.vistaPerfil;

/**
 *
 * @author ITZEL
 */
public class controlPNuevoViaje implements ActionListener, PropertyChangeListener{
    private modeloPNuevoViaje modelo;
    private PNuevoViaje vista;
    public static String [] usuario;
    public Inicio inicio;
    //Declaración de un arraylist para los checkbox de las actividades seleccionadas
    List<String> actSelec = new ArrayList<>();
    List<JDateChooser> jdFecha = new ArrayList<>();
    int idDestino, idEstilo;
    String a[];
    
    public controlPNuevoViaje(modeloPNuevoViaje modelo, PNuevoViaje vista, ArrayList actSelec, String [] usuario, Inicio inicio, int idDestino, int idEstilo) {
        this.modelo = modelo;
        this.vista = vista;
        this.usuario=usuario; 
        this.inicio = inicio;
        this.actSelec = actSelec;
        this.idDestino = idDestino;
        this.idEstilo = idEstilo;
    }
    
    public void iniciarVista(){
        vista.setSize(1000, 550);
        vista.setVisible(true);
        vista.btnGuardarV.addActionListener(this);
        System.out.println("Arraylist");
        
        for(int p = 0; p < actSelec.size(); p++){
            try{
                System.out.println(actSelec.get(p));
            }catch(IndexOutOfBoundsException event){
                System.out.println("j");
            }
        }
        a=modelo.destinoDatos(this.idDestino);
        vista.lblDestino.setText("Destino Final: " + a[0]);
        ImageIcon image = new ImageIcon(a[1]);
        Icon fondo = new ImageIcon(image.getImage().getScaledInstance(128, 128, Image.SCALE_DEFAULT));
        vista.lblImagen.setIcon(fondo);
        Date fechaIm = new Date();
        vista.fechaInicio.setMinSelectableDate(fechaIm);
        Calendar c = Calendar.getInstance(); 
        Date fechaF = new Date();
        c.setTime(fechaF); 
        c.add(Calendar.DATE, 1);
        fechaF = c.getTime();
        vista.fechaFin.setMinSelectableDate(fechaF);
        actividades();
        vista.fechaInicio.addPropertyChangeListener(this);
        vista.fechaFin.addPropertyChangeListener(this);
        transporte();
    }
    
    public void actividades(){
         GridBagConstraints gridBagConstraints = new GridBagConstraints();
        //si devuelve registros la consulta
        if(!actSelec.isEmpty()){
            //ciclo para crear los objetos de los destinos
            for(int i=0; i<actSelec.size(); i++){
                if(modelo.actDatos(Integer.parseInt(actSelec.get(i))).length > 0){
                    String b[]  = modelo.actDatos(Integer.parseInt(actSelec.get(i)));
                    
                    System.out.println("ID Actividad: " + actSelec.get(i));
                    System.out.println("Nombre: " + b[0]);
                    System.out.println("Imagen: " + b[1]);
                    System.out.println("Localización: " + b[2]);
                    System.out.println("------------------------------------");
                    
                    //panel de la imagen
                    JPanel jpImagen = new JPanel();
                    jpImagen.setBackground(new java.awt.Color(153,153,153));
                    JLabel jlImagen = new JLabel();

                    //configuración del layout
                    gridBagConstraints.insets = new Insets(10, 30, 10, 10);
                    jlImagen.setHorizontalAlignment(SwingConstants.CENTER);
                    jlImagen.setHorizontalTextPosition(SwingConstants.CENTER);
                    jlImagen.setPreferredSize(new Dimension(118, 100));
                    gridBagConstraints.fill = GridBagConstraints.VERTICAL;
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = i+5;

                    //imagen
                    ImageIcon image = new ImageIcon(b[1]);
                    Icon fondo = new ImageIcon(image.getImage().getScaledInstance(128, 128, Image.SCALE_DEFAULT));
                    jlImagen.setIcon(fondo);
                    jpImagen.add(jlImagen);
                    vista.pnl_Destinos.add(jpImagen, gridBagConstraints);

                    //especificaciones del label que está a un lado
                    gridBagConstraints.insets = new Insets(10, 0, 10, 10);
                    //label con la info del destino
                    JLabel jlTexto = new JLabel("<html><p>"+b[0]+"</p><br><p>Clima: "+b[2]+"</p></html>");
                    //panel de la información
                    JPanel jpTexto = new JPanel();
                    jpTexto.setSize(400,100);
                    jpTexto.setLayout(new BorderLayout());

                    //especificaciones del panel
                    jlTexto.setHorizontalAlignment(SwingConstants.LEFT);
                    jlTexto.setHorizontalTextPosition(SwingConstants.LEFT);
                    jlTexto.setPreferredSize(new Dimension(200, 100));
                    gridBagConstraints.fill = GridBagConstraints.VERTICAL;
                    gridBagConstraints.gridx = 1;
                    gridBagConstraints.gridy = i+5;
                    //color del panel
                    jlTexto.setBackground(new java.awt.Color(204,204,204));
                    jlTexto.setOpaque(true);
                    //se añade el texto al panel
                    jpTexto.add(jlTexto, BorderLayout.CENTER);
                    //se crea el panel de los botones
                    JPanel jpbtn = new JPanel();
                    jpbtn.setLayout(new FlowLayout());

                    JLabel lblFecha = new JLabel("Fecha: ");
                    lblFecha.setName(""+b[i]);
                    jpbtn.add(lblFecha);

                    JDateChooser jdF = new JDateChooser();
                    jdF.setName(""+b[i]);
                    jdF.setEnabled(false);
                    // draw label border to verify the new label size
                    jdF.setBorder(new LineBorder(new java.awt.Color(240,240,240)));
                    // change label size
                    jdF.setPreferredSize(new Dimension(150, 20));
                    jdFecha.add(jdF);
                    jpbtn.add(jdF); 
                    jpTexto.add(jpbtn, BorderLayout.SOUTH);
                    vista.pnl_Destinos.add(jpTexto, gridBagConstraints);
                }
                //para que los destinos no salgan tan separados uno del otro
                gridBagConstraints.weighty=1;
                gridBagConstraints.gridy++;
                vista.pnl_Destinos.add(new JLabel(" "), gridBagConstraints);
            }
        }
        //en caso de que no salga ningún destino
        else{
            JOptionPane.showMessageDialog(null, "No se seleccionaron actividades.", "¡Atención!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void transporte(){
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        //si devuelve registros la consulta
        if(modelo.transDatos(idDestino, 1).length > 0){
            //ciclo para crear los objetos de los destinos
            for(int i=0; i<modelo.transDatos(idDestino, idEstilo).length; i++){
                
//                    System.out.println("idDestino: " + idDestino);
//                    System.out.println("idEstilo: " + idEstilo);
                    String b[][]  = modelo.transDatos(idDestino, idEstilo);
                    
                    System.out.println("id: " + b[i][2]);
                    System.out.println("Tipo: " + b[i][0]);
                    System.out.println("Imagen: " + b[i][1]);
                    System.out.println("------------------------------------");
                    
                    //panel de la imagen
                    JPanel jpImagen = new JPanel();
                    jpImagen.setBackground(new java.awt.Color(240,240,240));
                    JLabel jlImagen = new JLabel();
                    jlImagen.setBackground(new java.awt.Color(204,204,204));

                    //configuración del layout
                    gridBagConstraints.insets = new Insets(10, 30, 10, 10);
                    jlImagen.setHorizontalAlignment(SwingConstants.CENTER);
                    jlImagen.setHorizontalTextPosition(SwingConstants.CENTER);
                    jlImagen.setPreferredSize(new Dimension(118, 100));
                    gridBagConstraints.fill = GridBagConstraints.VERTICAL;
                    gridBagConstraints.gridx = 0;
                    gridBagConstraints.gridy = i+5;

                    //imagen
                    ImageIcon image = new ImageIcon(b[i][2]);
                    Icon fondo = new ImageIcon(image.getImage().getScaledInstance(128, 128, Image.SCALE_DEFAULT));
                    jlImagen.setIcon(fondo);
                    jpImagen.add(jlImagen);
                    vista.pnlTrans.add(jpImagen, gridBagConstraints);

                    //especificaciones del label que está a un lado
                    gridBagConstraints.insets = new Insets(10, 0, 10, 10);
                    //label con la info del destino
                    JLabel jlTexto = new JLabel("<html> <p>&nbsp;"+b[i][0]+"</p></html>");
                    //panel de la información
                    JPanel jpTexto = new JPanel();
                    jpTexto.setBackground(new java.awt.Color(204,204,204));
                    jpTexto.setOpaque(true);
                    jpTexto.setSize(200,100);
                    jpTexto.setLayout(new BorderLayout());

                    //especificaciones del panel
                    jlTexto.setHorizontalAlignment(SwingConstants.LEFT);
                    jlTexto.setHorizontalTextPosition(SwingConstants.LEFT);
                    jlTexto.setPreferredSize(new Dimension(200, 100));
                    gridBagConstraints.fill = GridBagConstraints.VERTICAL;
                    gridBagConstraints.gridx = 1;
                    gridBagConstraints.gridy = i+5;
                    //color del panel
                    jlTexto.setBackground(new java.awt.Color(204,204,204));
                    jlTexto.setOpaque(true);
                    //se añade el texto al panel
                    jpTexto.add(jlTexto, BorderLayout.CENTER);
                    vista.pnlTrans.add(jpTexto, gridBagConstraints);
                
                }
                //para que los destinos no salgan tan separados uno del otro
                gridBagConstraints.weighty=1;
                gridBagConstraints.gridy++;
                vista.pnlTrans.add(new JLabel(" "), gridBagConstraints);
            }
        //en caso de que no salga ningún destino
        else{
            JLabel jltexto = new JLabel("<html><br><br><p>No se encontró ningún Transporte :(</p></html>");
            jltexto.setForeground(new Color(140,110,183));
            JPanel jptexto = new JPanel();
            jptexto.add(jltexto);
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 5;
            vista.pnlTrans.add(jptexto, gridBagConstraints);
            gridBagConstraints.weighty=1;
            gridBagConstraints.gridy++;
            vista.pnlTrans.add(new JLabel(" "), gridBagConstraints);}
    }
   
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == vista.btnGuardarV){
            if(vista.txtNombreViaje.getText().equals("")  || vista.fechaInicio.getDate().equals("") || vista.fechaFin.getDate().equals("")){
                JOptionPane.showMessageDialog(null, "No se han acompletado todos los datos.", "¡Atención!", JOptionPane.ERROR_MESSAGE);
            }
            else{
                boolean ban = false;
                for(int i=0; i<jdFecha.size(); i++){
                    if(jdFecha.get(i).getDate() == null){
                        ban = true;
                    }
                }
                if(!ban){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    //Fecha actual
                    Date fechaF = new Date();
                    String estado = "";
                    if(vista.fechaFin.getDate().before(fechaF) && vista.fechaInicio.getDate().before(fechaF)){
                        estado = "Terminado";
                    }

                    if(vista.fechaInicio.getDate().after(fechaF)){
                        estado = "Por Realizar";
                    }

                    if(vista.fechaFin.getDate().after(fechaF)){
                        estado = "En Curso";
                    }
                    System.out.println("Nombre: " + vista.txtNombreViaje.getText());
                    System.out.println("Fecha inicio: "+sdf.format(vista.fechaInicio.getDate()));
                    System.out.println("Fecha fin: "+sdf.format(vista.fechaFin.getDate()));
                    System.out.println("Estado: " + estado);
                    System.out.println("idEstilo: " + idEstilo);
                    System.out.println("idUsuario: " + Integer.parseInt(usuario[2]));
                    System.out.println("idDestino: " + idDestino);
                    
                    if(modelo.insertarViaje(vista.txtNombreViaje.getText(),sdf.format(vista.fechaInicio.getDate()), sdf.format(vista.fechaFin.getDate()), estado, idEstilo, Integer.parseInt(usuario[2]), idDestino, jdFecha, actSelec)){
                        JOptionPane.showMessageDialog(null, "Su viaje se ha guardado con éxito", "Viaje guardado", JOptionPane.INFORMATION_MESSAGE);
                        modeloPerfil mP = new modeloPerfil();
                        vistaPerfil vP = new vistaPerfil();
                        controlPerfil cP = new controlPerfil(mP, vP, usuario, inicio);
                        this.inicio.pnl_PInicio.removeAll();
                        this.inicio.pnl_PInicio.revalidate();
                        this.inicio.pnl_PInicio.repaint();
                        
                        inicio.pnl_PInicio.add(vP);
                        inicio.pnl_PInicio.revalidate();
                        inicio.pnl_PInicio.repaint();
                        cP.iniciarVista();
                        inicio.lbl_Titulo.setText("Perfil");
                        
                    }
                    else{
                       JOptionPane.showMessageDialog(null, "No se ha podido guardar el viaje. Vuelve a intentarlo", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Necesita asignar todas las fechas a las actividades", "Viaje guardado", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
        
    }
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        //fecha limite
        if(evt.getSource() == this.vista.fechaInicio){
            //Specifying date format that matches the given date
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            try{
                try{
                   //Setting the date to the given date
                   c.setTime(sdf.parse(sdf.format(vista.fechaInicio.getDate())));
                }catch(NullPointerException e){
                }
            }catch (ParseException ev){}
           
            //Number of Days to add
            c.add(Calendar.DATE, 1);  

            vista.fechaFin.setMinSelectableDate(c.getTime());
            for(int i=0; i<jdFecha.size(); i++){
                jdFecha.get(i).setMinSelectableDate(c.getTime());
                jdFecha.get(i).setEnabled(true);
            }
        }
        
        if(evt.getSource() == this.vista.fechaFin){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
             for(int i=0; i<jdFecha.size(); i++){
                try {
                    jdFecha.get(i).setMaxSelectableDate(sdf.parse(sdf.format(vista.fechaFin.getDate())));
                } catch (ParseException ex) {
                    
                }
                jdFecha.get(i).setEnabled(true);
            }
        }
    }   
}
