/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;


import static controlador.ControlInicio.usuario;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import modelo.ModeloInicio;
import modelo.ModeloPInicio;
import modelo.modeloPNuevoViaje;
import vista.Inicio;
import vista.PNuevoViaje;
import modelo.modeloComentarios;
import vista.PInicio;
import vista.vistaComentarioos;

/**
 *
 * @author ITZEL
 */
public class ControlPInicio extends JFrame implements ActionListener{
    private ModeloPInicio modelo;
    private PInicio vista;
    private JButton btnCom, btnDet;
    private int idDestino;
    //Usuario
    public static String [] usuario;
    //Bandera para los checkbox de las act
    boolean bandera;
    //Declaración de un arraylist para los checkbox de la actividad
    List<JCheckBox> jnombre = new ArrayList<>();
    //Declaración de un arraylist para los checkbox de las actividades seleccionadas
    List<String> actSelec = new ArrayList<>();
    //Vista de inicio
    private Inicio inicio;
    private vistaComentarioos viista;
    private modeloComentarios moodelo;
    private ModeloInicio modelon;
    
    //Encapsulamiento idDestino
    public int getIdDestino() {
        return idDestino;
    }
    

    public void setIdDestino(int idDestino) {
        this.idDestino = idDestino;
    }
    
    
    //Constructor de la clase
    public ControlPInicio(ModeloPInicio modelo, PInicio vista, String [] usuario, Inicio inicio){
        this.modelo = modelo;
        this.vista = vista;
        this.idDestino = -1;
        this.usuario = usuario;
        this.inicio = inicio;
    }
    
    public void iniciarVista(){
        vista.setSize(1000, 550);
        vista.setVisible(true);
        vista.btnTendencias.addActionListener(this);
        vista.btnSugerencias.addActionListener(this);
        vista.btnBuscar.addActionListener(this);
        vista.txtBuscar.addActionListener(this);
        vista.guardar.addActionListener(this);
        vista.Detalles.setVisible(false);
        vista.pnl_DetalleDestino.setVisible(false);
        destinosConsultasVarias("select nombre, clima, foto, idDestino from destino;");
        vista.btnAgregarV.addActionListener(this);
        //Va más rápido la barra
        vista.Detalles.getVerticalScrollBar().setUnitIncrement(16);
        vista.Destinos.getVerticalScrollBar().setUnitIncrement(16);
        //Se llena la lista de estilo de viaje
        if(modelo.estiloViaje().length > 0){
            String [][] a = modelo.estiloViaje();
            vista.cmb_EstiloViaje.removeAllItems();
            vista.cmb_EstiloViaje.addItem("Elige uno...");
            for(int i=0; i<a.length; i++)
                vista.cmb_EstiloViaje.addItem(a[i][0]);
        }
        else{
            vista.cmb_EstiloViaje.addItem("No se ha encontrado ningún estilo :(");
        }
        vista.cmb_EstiloViaje.addActionListener(this);
    }
    
    //Carga de comentarios
     public void mostrarcomentarios(String sentencia)
    {
        this.vista.panel_comentarios.removeAll();
        this.vista.panel_comentarios.revalidate();
        this.vista.panel_comentarios.repaint();
        
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        if(modelo.Comentarios(sentencia).length > 0)
        {    
            String [][] a = modelo.Comentarios(sentencia);
            for(int i=0; i<a.length; i++)
            {
                JPanel jp = new JPanel();
                jp.setBackground(new java.awt.Color(153,153,153));
                //jp.setBackground(new java.awt.Color(117,221,117));
                JLabel jl = new JLabel();
                
                //Crea donde ira la foto
                gridBagConstraints.insets = new Insets(10, 30, 10, 10);
                jl.setHorizontalAlignment(SwingConstants.CENTER);
                jl.setHorizontalTextPosition(SwingConstants.CENTER);
                jl.setPreferredSize(new Dimension(118, 100));
                
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = i+5;
                gridBagConstraints.fill = GridBagConstraints.VERTICAL;
                ImageIcon image = new ImageIcon(""+a[i][2]);
                jl.setIcon(image);
                jp.add(jl);
                vista.panel_comentarios.add(jp, gridBagConstraints);
               
                gridBagConstraints.insets = new Insets(10, 0, 10, 10);
                //JLabel jl2 = new JLabel("<html><p>"+a[i][1]+"</p><br><p>Comentario: "+a[i][0]+"</p></html>");
                JLabel jl2 = new JLabel("<html><p>"+ a[i][1] +"</p><p>"+ a[i][0] +"</p><p>Calificacion: "+ a[i][4] +"</p></html>");
                JPanel jp2 = new JPanel();
                jp2.setSize(100,100);
                jp2.setLayout(new BorderLayout());
                
               //Para acomodar el texto en la izquiera
                jl2.setHorizontalAlignment(SwingConstants.LEFT);
                jl2.setHorizontalTextPosition(SwingConstants.LEFT);
                jl2.setPreferredSize(new Dimension(200, 100));
                gridBagConstraints.fill = GridBagConstraints.VERTICAL;
                gridBagConstraints.gridx = 1;
                gridBagConstraints.gridy = i+5;
                jl2.setBackground(new java.awt.Color(117,221,117));
                jl2.setOpaque(true);
                
                jp2.add(jl2, BorderLayout.CENTER);
                vista.panel_comentarios.add(jp2, gridBagConstraints);
            }
                gridBagConstraints.weighty=1;
                gridBagConstraints.gridy++;
                vista.panel_comentarios.add(new JLabel(" "), gridBagConstraints);
        }
        else
        {
            JLabel jltexto = new JLabel("No se encontró ningun comentario :(");
            jltexto.setForeground(new Color(140,110,183));
            JPanel jptexto = new JPanel();
            gridBagConstraints.insets = new Insets(10, 30, 10, 10);
            jltexto.setHorizontalAlignment(SwingConstants.CENTER);
            jltexto.setHorizontalTextPosition(SwingConstants.CENTER);
            jltexto.setPreferredSize(new Dimension(300, 100));
            gridBagConstraints.fill = GridBagConstraints.VERTICAL;
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 5;
            jptexto.add(jltexto);
            jptexto.setBackground(Color.WHITE);
            vista.panel_comentarios.add(jptexto, gridBagConstraints);
        }
    }
    
    
    //Carga de destinos: tendencias, sugerencias, todos
    public void destinosConsultasVarias(String sentencia){
        //limpia el panel
        this.vista.pnl_Destinos.removeAll();
        this.vista.pnl_Destinos.revalidate();
        this.vista.pnl_Destinos.repaint();
        //hace invisible la parte derecha
        vista.Detalles.setVisible(false);
        vista.pnl_DetalleDestino.setVisible(false);
        //propiedades del gridlayout
        GridBagConstraints gridBagConstraints = new GridBagConstraints();

        //si devuelve registros la consulta
         if(modelo.todas(sentencia).length > 0){
            String [][] a = modelo.todas(sentencia);
            //ciclo para crear los objetos de los destinos
            for(int i=0; i<a.length; i++){
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
                ImageIcon image = new ImageIcon(a[i][2]);
                Icon fondo = new ImageIcon(image.getImage().getScaledInstance(128, 128, Image.SCALE_DEFAULT));
                jlImagen.setIcon(fondo);
                jpImagen.add(jlImagen);
               //vista.panel_comentarios.add(jpImagen, gridBagConstraints);

                vista.pnl_Destinos.add(jpImagen, gridBagConstraints);
                
                //especificaciones del label que está a un lado
                gridBagConstraints.insets = new Insets(10, 0, 10, 10);
                //label con la info del destino
                JLabel jlTexto = new JLabel("<html><p>"+a[i][0]+"</p><br><p>Clima: "+a[i][1]+"</p></html>");
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
                //se crean los botones
//                btnCom = new JButton("Comentarios");
//                btnCom.setName(""+a[i][3]);
                btnDet = new JButton("Detalles");
                btnDet.setName(""+a[i][3]);
//                jpbtn.add(btnCom);
                //se añaden los botones
                jpbtn.add(btnDet);
                jpTexto.add(jpbtn, BorderLayout.SOUTH);
                vista.pnl_Destinos.add(jpTexto, gridBagConstraints);
                //vista.panel_comentarios.add(jpTexto, gridBagConstraints);
                
//                btnCom.addActionListener(this);
                btnDet.addActionListener(this);
            }
            //para que los destinos no salgan tan separados uno del otro
            gridBagConstraints.weighty=1;
            gridBagConstraints.gridy++;
            vista.pnl_Destinos.add(new JLabel(" "), gridBagConstraints);
            //vista.panel_comentarios.add(new JLabel(" "), gridBagConstraints);
        }
         //en caso de que no salga ningún destino
         else{
             //label
            JLabel jltexto = new JLabel("No se encontró ningun destino :(");
            //color de la letra
            jltexto.setForeground(new Color(140,110,183));
            //panel y especificaciones del layout
            JPanel jptexto = new JPanel();
            gridBagConstraints.insets = new Insets(10, 30, 10, 10);
            jltexto.setHorizontalAlignment(SwingConstants.CENTER);
            jltexto.setHorizontalTextPosition(SwingConstants.CENTER);
            jltexto.setPreferredSize(new Dimension(300, 100));
            gridBagConstraints.fill = GridBagConstraints.VERTICAL;
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 5;
            //se añade el panel al texto
            jptexto.add(jltexto);
            //color del fondo
            jptexto.setBackground(Color.WHITE);
            //se añade el panel con el texto al panel principal
            vista.pnl_Destinos.add(jptexto, gridBagConstraints);

            //vista.panel_comentarios.add(jptexto, gridBagConstraints);

         }
        
    }
    
    //carga de actividades
    public void actConsultasVarias(String sentencia){
        bandera=false;
        this.vista.pnl_Actividades.removeAll();
        this.vista.pnl_Actividades.revalidate();
        this.vista.pnl_Actividades.repaint();
        jnombre.clear();
        actSelec.clear();
        
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        if(modelo.todas(sentencia).length > 0)
        {
            String [][] a = modelo.todas(sentencia);
            for(int i=0; i<a.length; i++){
                //Jp es el panel de la imagen
//                JPanel jpImagen = new JPanel();
//                jpImagen.setBackground(new java.awt.Color(153,153,153));
//                //jele es el jlabel de la imagen
//                JLabel jlImagen = new JLabel();
//               
//                //pa que la imagen esté en el medio del label
//                jlImagen.setHorizontalAlignment(SwingConstants.CENTER);
//                jlImagen.setHorizontalTextPosition(SwingConstants.CENTER);
//                jlImagen.setPreferredSize(new Dimension(118, 100));
//                
//                //el margen, padding y ubicación de la celda
//                gridBagConstraints.insets = new Insets(15, 5, 5, 5);
//                gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
//                gridBagConstraints.fill = GridBagConstraints.NONE;
                int x = (i>0)?i*5+5:i*5;
//                gridBagConstraints.gridx = 0;
//                gridBagConstraints.gridy = x;
                
                //se carga la imagen y se añade al panel
               // ImageIcon image = new ImageIcon(a[i][2]);
                //Icon fondo = new ImageIcon(image.getImage().getScaledInstance(128, 128, Image.SCALE_DEFAULT));
                //jlImagen.setIcon(fondo);
                //jlImagen.setText(a[i][3]); //ID de la actividad
//                jpImagen.add(jlImagen);
                
                //se agrega el panel de la imagen al panel de actividades
//                vista.pnl_Actividades.add(jpImagen, gridBagConstraints);      
              
                //checkbox para seleccionar actividad
                JCheckBox box = new JCheckBox(a[i][0]);
                if(vista.cmb_EstiloViaje.getSelectedIndex() == 0){
                    box.setEnabled(false);
                }
                box.setName(a[i][3]);//id actividad
                jnombre.add(box);
                //label del texto y panel del texto
                JTextArea jtaTexto = new JTextArea(a[i][1]);
                jtaTexto.setFont(new Font("Tahoma", Font.BOLD, 12));
                jtaTexto.setLineWrap(true);
                jtaTexto.setWrapStyleWord(true);
                jtaTexto.setOpaque(false);
                jtaTexto.setEditable(false);
                JPanel jpTexto = new JPanel();
                jpTexto.setLayout(new BorderLayout());
                
                //pa que el texto esté en el medio del label
                jtaTexto.setPreferredSize(new Dimension(50, 150));
                jpTexto.setSize(jtaTexto.getPreferredSize());
               
                //Ubicación del label
                gridBagConstraints.fill = GridBagConstraints.NONE;
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = x+5;
                
                //Pal fonfo del label
                jtaTexto.setBackground(new java.awt.Color(204,204,204));
                jtaTexto.setOpaque(true);
                
                //se añade el label al panel
                jpTexto.add(box, BorderLayout.NORTH);
                box.addActionListener(this);
                jpTexto.add(jtaTexto, BorderLayout.CENTER);
                
                //panel de los botones
                JPanel jpbtn = new JPanel();
                jpbtn.setLayout(new FlowLayout());
                btnCom = new JButton("Comentarios");
                btnCom.setBorder(null);
                btnCom.setName(""+a[i][3]); //Id del destino
                jpbtn.add(btnCom);
                //label de la calificación
                String msj = (modelo.actividadCal(Integer.parseInt(a[i][3])))!=null?modelo.actividadCal(Integer.parseInt(a[i][3])):"0";
                NumberFormat fm = new DecimalFormat("#0.00");    
                JLabel lblcal = new JLabel("Calificación: "+fm.format(Double.parseDouble(msj)));
                
                jpbtn.add(lblcal);
                
                //se añade el panel de los botones al panel del texto
                jpTexto.add(jpbtn, BorderLayout.SOUTH);
                
                //se añade el panel del texto al panel de actividades
                vista.pnl_Actividades.add(jpTexto, gridBagConstraints);
                
                //los listeners de los botones
                btnCom.addActionListener(this);
            }
            //Es para el espacio entre los labels
            gridBagConstraints.weighty=1;
            gridBagConstraints.gridy++;
            vista.pnl_Actividades.add(new JLabel(" "), gridBagConstraints);
        }
        else{
            JLabel jltexto = new JLabel("<html><br><br><p>No se encontró ninguna actividad :(</p></html>");
            jltexto.setForeground(new Color(140,110,183));
            JPanel jptexto = new JPanel();
            jptexto.add(jltexto);
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 5;
            vista.pnl_Actividades.add(jptexto, gridBagConstraints);
            gridBagConstraints.weighty=1;
            gridBagConstraints.gridy++;
            vista.pnl_Actividades.add(new JLabel(" "), gridBagConstraints);
        }
    }
    
    //carga de transportes
    public void transConsultasVarias(String sentencia){
        this.vista.pnl_Transportes.removeAll();
        this.vista.pnl_Transportes.revalidate();
        this.vista.pnl_Transportes.repaint();
        
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        if(modelo.todas(sentencia).length > 0){
            String [][] a = modelo.todas(sentencia);
            for(int i=0; i<a.length; i++){
                //Jp es el panel de la imagen
                JPanel jpImagen = new JPanel();
                jpImagen.setLayout(new BorderLayout());
                jpImagen.setBackground(new java.awt.Color(153,153,153));
                //jele es el jlabel de la imagen
                JLabel jlImagen = new JLabel();
               
                //pa que la imagen esté en el medio del label
                jlImagen.setHorizontalAlignment(SwingConstants.CENTER);
                jlImagen.setHorizontalTextPosition(SwingConstants.CENTER);
                jlImagen.setPreferredSize(new Dimension(118, 100));
                
                //el margen, padding y ubicación de la celda
                gridBagConstraints.insets = new Insets(15, 5, 5, 5);
                gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
                gridBagConstraints.fill = GridBagConstraints.NONE;
                int x = (i>0)?i*5+5:i*5;
                gridBagConstraints.gridx = 0;
                gridBagConstraints.gridy = x;
                
                //se carga la imagen y se añade al panel
                ImageIcon image = new ImageIcon(a[i][2]);
                Icon fondo = new ImageIcon(image.getImage().getScaledInstance(128, 128, Image.SCALE_DEFAULT));
                jlImagen.setIcon(fondo);
                //jlImagen.setText(a[i][3]); //ID del transporte
                jpImagen.add(jlImagen, BorderLayout.CENTER);
                
                JPanel jpaneltxt = new JPanel();
                jpaneltxt.setBackground(new java.awt.Color(204,204,204));
                jpaneltxt.setOpaque(true);
                JLabel lbtexto = new JLabel(a[i][0]);
                jpaneltxt.add(lbtexto);

                jpImagen.add(jpaneltxt, BorderLayout.SOUTH);
                
                //se agrega el panel de la imagen al panel de actividades
                vista.pnl_Transportes.add(jpImagen, gridBagConstraints);      
            }
            //Es para el espacio entre los labels
            gridBagConstraints.weighty=1;
            gridBagConstraints.gridy++;
            vista.pnl_Transportes.add(new JLabel(" "), gridBagConstraints);
 
        }
        else
        {
            JLabel jltexto = new JLabel("<html><br><br><p>No se encontró ningun transporte :(</p></html>");
            jltexto.setForeground(new Color(140,110,183));
            JPanel jptexto = new JPanel();
            jptexto.add(jltexto);
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 5;
            vista.pnl_Transportes.add(jptexto, gridBagConstraints);
            gridBagConstraints.weighty=1;
            gridBagConstraints.gridy++;
            vista.pnl_Transportes.add(new JLabel(" "), gridBagConstraints);
        }
        //Manda para arriba el scroll
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
        public void run() { 
            vista.Detalles.getVerticalScrollBar().setValue(0);
        }
        });
    }
    
    //carga tipositio
    public void tipoSitioConsulta(String sentencia){
        this.vista.pnl_TipoSitio.removeAll();
        this.vista.pnl_TipoSitio.revalidate();
        this.vista.pnl_TipoSitio.repaint();
        
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        if(modelo.todas(sentencia).length > 0){
            String [][] a = modelo.todas(sentencia);
            for(int i=0; i<a.length; i++){
                //Jp es el panel de la imagen
                JPanel jpTexto = new JPanel();
                jpTexto.setBackground(new java.awt.Color(153,153,153));
                       
                JPanel jpaneltxt = new JPanel();
                JTextArea jtaTexto = new JTextArea(a[i][0]+": "+a[i][1]);
                jtaTexto.setFont(new Font("Tahoma", Font.BOLD, 12));
                jtaTexto.setLineWrap(true);
                jtaTexto.setWrapStyleWord(true);
                jtaTexto.setOpaque(false);
                jtaTexto.setEditable(false);
                jtaTexto.setSize(408,80);
                jpaneltxt.add(jtaTexto);
                jpTexto.add(jpaneltxt);
                
                //se agrega el panel de la imagen al panel de actividades
                vista.pnl_TipoSitio.add(jpTexto);      
            }
        }
        else{
            JLabel jltexto = new JLabel("<html><br><br><p>No se encontró ningun tipo de sitio :(</p></html>");
            jltexto.setForeground(new Color(140,110,183));
            JPanel jptexto = new JPanel();
            jptexto.add(jltexto);
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = 5;
            vista.pnl_TipoSitio.add(jptexto, gridBagConstraints);
            gridBagConstraints.weighty=1;
            gridBagConstraints.gridy++;
            vista.pnl_TipoSitio.add(new JLabel(" "), gridBagConstraints);
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
    
    //Panel del estilo de viaje
    private JPanel getPanel(String [] a) {
        JPanel panel = new JPanel();
        JLabel label = new JLabel("<html><center><h1>"+vista.cmb_EstiloViaje.getSelectedItem()+"</h1><center>"
                + "<p>"+a[1]+"</p>"
                + "<p>Su presupuesto es de $"+a[3]+" a $"+a[2]+" pesos mexicanos</p>"
                + "</html>");
        ImageIcon image;
        image = new ImageIcon(a[4]);

        label.setIcon(image);
        panel.add(label);

        return panel;
    }
    //funcion para limpiar el texto de los comentarios
    public void limpiar()
    {
        vista.texto_comentarios.setText("");
        vista.texto_calificacion.setText("");
        vista.texto_id.setText("");
        vista.texto_nombre.setText("");
    }
    
    @Override
    public void actionPerformed(ActionEvent e) 
    { 
        //Accion para hacer un comentario
        if(vista.guardar == e.getSource())
        {
            if(modelo.insertarComentarios(vista.texto_comentarios.getText(), vista.texto_calificacion.getText(), Integer.parseInt(vista.texto_id.getText()), vista.texto_nombre.getText()))
            {
                JOptionPane.showMessageDialog(null, "Comentario agregado");
                limpiar();
            }
        } 
        //Botones de comentarios y detalles
        try 
        {
            JButton selectedButton = (JButton) e.getSource();
            String idD = selectedButton.getName();
            if(isNumeric(idD))
            {
                //ID DE DESTINO          
                this.idDestino = Integer.parseInt(idD);
                //Botón de comentarios
                if("Comentarios".equals(selectedButton.getText()))
                {
//                    this.vista.pnl_DetalleDestino.removeAll();
//                    this.vista.pnl_DetalleDestino.revalidate();
//                    this.vista.pnl_DetalleDestino.repaint();
//                    vistaComentarioos vic = new vistaComentarioos();
//                    modeloComentarios mod = new modeloComentarios();
//                    controlComentarios cc = new controlComentarios(mod,vic);
//                    this.vista.pnl_DetalleDestino.add(vic);
//                    cc.iniciarVista();
  //                  String [][] a = moodelo.todas("SELECT comentarios.comentario, usuario.nombre, usuario.foto FROM comentarios, destino, usuario WHERE comentarios.Usuario_idUsuario = usuario.idUsuario AND comentarios.Destino_idDestino = " + idDestino);
//                    System.out.println("Comentario: " + a[0][0]);
//                    System.out.println("Nombre: " + a[0][1]);
//                    System.out.println("Foto: " + a[0][2]);
//                    System.out.println("---------------------");
                }

                //Botón de detalles
                if(selectedButton.getText().equals("Detalles")){
                    //Muestra la pantalla de detalles
                    vista.Detalles.setVisible(true);
                    vista.pnl_DetalleDestino.setVisible(true);
                    
                    vista.texto_id.setText("" + idDestino);
                    String [] c = modelo.datosUsuario(Integer.parseInt(usuario[0]), Integer.parseInt(usuario[2]));
                    vista.texto_nombre.setText(""+ c[0]);
                    //trae los datos del destino
                    String [][] a = modelo.todas("select nombre, clima, foto, idDestino from destino where idDestino = " + idDestino);
                   
//                    System.out.println("ID: " + a[0][3]);
//                    System.out.println("Nombre: " + a[0][0]);
//                    System.out.println("Imagen: " + a[0][2]);
//                    System.out.println("Clima: " + a[0][1]);
//                    System.out.println("---------------------");
                    

                    String [][] b = modelo.Comentarios("SELECT comentarios.comentario, usuario.nombre, usuario.foto, destino.idDestino, comentarios.calificacion FROM comentarios, destino, usuario WHERE comentarios.Usuario_idUsuario = usuario.idUsuario AND comentarios.Destino_idDestino = " + idDestino);
                    for(int i=0; i<b.length; i++)
                    {
//                        System.out.println("ID: " + b[i][3]);
//                        System.out.println("Comentario: " + b[i][0]);
//                        System.out.println("Nombre: " + b[i][1]);
//                        System.out.println("Foto: " + b[i][2]);
//                        System.out.println("Calificacion: " + b[i][4]); 
//                        System.out.println("---------------------");
                    }


                    //pone titulo e imagen
                    vista.lbl_DestinoN.setText(a[0][0]);
                    ImageIcon image = new ImageIcon(a[0][2]);
                    Icon fondo = new ImageIcon(image.getImage().getScaledInstance(200, 200, Image.SCALE_DEFAULT));
                    vista.lbl_DestinoI.setIcon(fondo);
                    //Información de las actividades
                    actConsultasVarias("SELECT actividad.nombre, actividad.descripcion, tiene.foto, actividad.idActividad FROM actividad INNER JOIN tiene ON tiene.Actividad_idActividad = actividad.idActividad INNER JOIN posee on posee.Actividad_idActividad = actividad.idActividad WHERE tiene.Destino_idDestino = " + idDestino); 
                    //Información del transporte
                    transConsultasVarias("SELECT transporte.tipo, 1, transporte.foto, transporte.idTransporte FROM `transporte` INNER JOIN sedesplazaen on sedesplazaen.Transporte_idTransporte = transporte.idTransporte WHERE sedesplazaen.Destino_idDestino = " + idDestino);
                    //Información de tipo de sitio
                    tipoSitioConsulta("SELECT tipositio.tipo, tipositio.descripcion, 1, tipositio.idTipoSitio FROM `tipositio` INNER JOIN sedivideen ON sedivideen.TipoSitio_idTipoSitio = tipositio.idTipoSitio WHERE sedivideen.Destino_idDestino = " + idDestino);
                    //comentarios
                    mostrarcomentarios("SELECT comentarios.comentario, usuario.nombre, usuario.foto, destino.idDestino, comentarios.calificacion FROM usuario INNER JOIN comentarios ON comentarios.Usuario_idUsuario = usuario.idUsuario INNER JOIN destino on destino.idDestino = comentarios.Destino_idDestino WHERE comentarios.Destino_idDestino = " + idDestino);
                } 
            }
        } catch(ClassCastException ex) {}
        //Botón de agregar viaje del panel de detalle de viaje
        if(e.getSource() == vista.btnAgregarV){
            if(bandera && vista.cmb_EstiloViaje.getSelectedIndex()!=0){
//                System.out.println("entra");
                
                //limpia el panel
                inicio.pnl_PInicio.removeAll();
                inicio.pnl_PInicio.revalidate();
                inicio.pnl_PInicio.repaint();
               
                //inicia la siguiente vista 
                modeloPNuevoViaje mPNV = new modeloPNuevoViaje();
                PNuevoViaje vPNV = new PNuevoViaje();
                controlPNuevoViaje cPNV = new controlPNuevoViaje(mPNV, vPNV, (ArrayList) actSelec, usuario, inicio, idDestino, vista.cmb_EstiloViaje.getSelectedIndex());
                inicio.pnl_PInicio.add(vPNV);
                inicio.pnl_PInicio.revalidate();
                inicio.pnl_PInicio.repaint();
                cPNV.iniciarVista();
                inicio.lbl_Titulo.setText("Nuevo Viaje");
            }
            else{
                if(vista.cmb_EstiloViaje.getSelectedIndex() == 0){
                 //Error al no selecionar la menos una actividad
                JOptionPane.showMessageDialog(null, "Elige un estilo de viaje.", "¡Atención!", JOptionPane.ERROR_MESSAGE);   
                }
                else{
                //Error al no selecionar la menos una actividad
                JOptionPane.showMessageDialog(null, "No se ha seleccionada al menos una actividad.", "¡Atención!", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        //combobox del estilo de viaje
        else if(e.getSource() == vista.cmb_EstiloViaje){
            //Cuando se ha seleccionado algun estilo de viaje
            if(vista.cmb_EstiloViaje.getSelectedIndex() != 0) {
                //agarra el id del estilo del viaje
                String [] a = modelo.estiloViajeC((String) vista.cmb_EstiloViaje.getSelectedItem());
//                
//                System.out.println("-Estilo de viaje");
//                System.out.println("ID: " + a[0]);
//                System.out.println("Nombre: " + vista.cmb_EstiloViaje.getSelectedItem());
//                System.out.println("Descripción: " + a[1]);
//                System.out.println("Pres max: " + a[2]);
//                System.out.println("Pres min: " + a[3]);
//                System.out.println("Foto: " + a[4]);
//                System.out.println("---------------------");
                //muestra un mensaje con las especifiaciones del estilo de viaje
                JOptionPane.showConfirmDialog(null,
                            getPanel(a),
                            "Detalle de Estilo de Viaje",
                            JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.PLAIN_MESSAGE);
                //consulta de las actividades que incluyen en ese tipo de viaje
                actConsultasVarias("SELECT actividad.nombre, actividad.descripcion, tiene.foto, actividad.idActividad FROM actividad INNER JOIN tiene ON tiene.Actividad_idActividad = actividad.idActividad INNER JOIN posee on posee.Actividad_idActividad = actividad.idActividad WHERE tiene.Destino_idDestino = " + idDestino + " AND posee.EstiloViaje_idEstiloViaje <= " + vista.cmb_EstiloViaje.getSelectedIndex());
                //consulta del transporte que se incluye en ese tipo de viaje
                transConsultasVarias("SELECT transporte.tipo, 1, transporte.foto, transporte.idTransporte FROM `transporte` INNER JOIN sedesplazaen on sedesplazaen.Transporte_idTransporte = transporte.idTransporte INNER JOIN corresponde on corresponde.Transporte_idTransporte = transporte.idTransporte INNER JOIN estiloviaje on estiloviaje.idEstiloViaje = corresponde.EstiloViaje_idEstiloViaje WHERE sedesplazaen.Destino_idDestino = "+idDestino+" and estiloviaje.presupuesto_min <= (SELECT estiloviaje.presupuesto_min from estiloviaje WHERE estiloviaje.idEstiloViaje = "+vista.cmb_EstiloViaje.getSelectedIndex()+") GROUP BY transporte.tipo;");
            }
            else{
                //Cuando no se ha seleccionado una opción muestra todo
                actConsultasVarias("SELECT actividad.nombre, actividad.descripcion, tiene.foto, actividad.idActividad FROM actividad INNER JOIN tiene ON tiene.Actividad_idActividad = actividad.idActividad INNER JOIN posee on posee.Actividad_idActividad = actividad.idActividad WHERE tiene.Destino_idDestino = " + idDestino);
                transConsultasVarias("SELECT transporte.tipo, 1, transporte.foto, transporte.idTransporte FROM `transporte` INNER JOIN sedesplazaen on sedesplazaen.Transporte_idTransporte = transporte.idTransporte WHERE sedesplazaen.Destino_idDestino = " + idDestino); 
            }
            //manda el scroll hacia la parte del estilo de viaje
            javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() { 
                vista.Detalles.getVerticalScrollBar().setValue(vista.cmb_EstiloViaje.getY());
            }
            });
        }
        //Botón de buscar 
        else if(e.getSource() == vista.btnBuscar || e.getSource() == vista.txtBuscar){
            if(vista.txtBuscar.getText().equals(""))
                destinosConsultasVarias("select nombre, clima, foto, idDestino from destino;");
            else{
                destinosConsultasVarias("select nombre, clima, foto, idDestino from destino where nombre like '"+vista.txtBuscar.getText()+"%';");   
                vista.txtBuscar.requestFocus();
            }
        }
        //Botón de tendencias
        else if(e.getSource() == vista.btnTendencias){
            vista.txtBuscar.setText("");
            destinosConsultasVarias("SELECT nombre, clima, foto, idDestino from destino INNER JOIN pertenece on idDestino = Destino_idDestino GROUP BY nombre HAVING COUNT(*) > 1;");
        }
        //Botón de sugerencias
        else if(e.getSource() == vista.btnSugerencias){
            vista.txtBuscar.setText("");
            destinosConsultasVarias("SELECT destino.nombre, destino.clima, destino.foto, destino.idDestino FROM destino INNER JOIN sedivideen ON sedivideen.Destino_idDestino = idDestino INNER JOIN tipositio ON tipositio.idTipoSitio = sedivideen.TipoSitio_idTipoSitio INNER JOIN prefiere ON tipositio.idTipoSitio = prefiere.TipoSitio_idTipoSitio INNER JOIN usuario ON prefiere.Usuario_idUsuario = idUsuario WHERE usuario.idUsuario = " + usuario[0] + ";");
        }
        
        // CHECKBOX DE LAS ACTIVIDADES SELECCIONADAs
        
        for (int i = 0; i < jnombre.size(); i++) {
            if (jnombre.get(i) == e.getSource()) {
                if(jnombre.get(i).isSelected()){
                    bandera=true;
//                    System.out.println("id: " + jnombre.get(i).getName());
//                    System.out.println(jnombre.get(i).getText()+" " + b);
                    actSelec.add(jnombre.get(i).getName());
                }
                else{
                    for(int j = 0; j < actSelec.size(); j++){
                        if(jnombre.get(i).getName().equals(actSelec.get(j))){
                            actSelec.remove(j);
                        }
                    }
                    
                }
            }
            
//            System.out.println("Arraylist: ControlPInicio");
            for(int p = 0; p < actSelec.size(); p++){
                try{
                    System.out.println(actSelec.get(p));
                }catch(IndexOutOfBoundsException event){
//                    System.out.println("j");
                }
            }
        }
    }
    
}
