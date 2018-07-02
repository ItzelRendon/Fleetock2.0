/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Calendar;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import modelo.modeloEditarPerfil;
import modelo.modeloPerfil;
import modelo.modeloRegistrar;
import vista.vistaEditarPerfil;
import vista.vistaPerfil;
import java.io.File; 
import javax.swing.Icon;
import javax.swing.JTextField;
import vista.Inicio;

/**
 *
 * @author Esme
 */
public class controlEditarPerfil implements ActionListener{
    private vistaEditarPerfil vista;
    private modeloEditarPerfil modelo;
    private modeloRegistrar modeloRegistrar = new modeloRegistrar();
    public static String [] usuario;
    private JFileChooser archi = new JFileChooser();
    int ventana=0;
    private String destino = "C:/Fleetock/img/administrador/";
    String archivo="";
    int banderaFoto=0;
    public Inicio inicio;
    
    //Permite acceder a funciones de la fecha.
    Calendar calendario = Calendar.getInstance();
    //Agarra el año en curso.
    int anioActual = calendario.get(Calendar.YEAR);
    //Agarra el mes en curso.
    int mesActual = calendario.get(Calendar.MONTH);
    //Agarra el dia en curso.
    int diaActual = calendario.get(Calendar.DAY_OF_MONTH);
    
     public controlEditarPerfil(modeloEditarPerfil modelo, vistaEditarPerfil vista, String [] usuario, Inicio inicio)
    {
        this.modelo = modelo;
        this.vista = vista;
        this.usuario = usuario;
        this.vista.btnActualizar.addActionListener(this);
        this.vista.btnSeleccionar.addActionListener(this);
        this.vista.btnCambiarContrasenia.addActionListener(this);
        ValidarCampos(this.vista.txtNombre);
        ValidarCampos(this.vista.txtApellidos);
        this.inicio = inicio;
    }

    public controlEditarPerfil() {
    }
     public void ValidarCampos(JTextField txt)
    {
        txt.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char caracter = e.getKeyChar();
                if (!Character.isLetter(caracter) && !Character.isSpace(caracter)) {
                    e.consume();
                }
            }
        });
    }
     public void iniciarVista()
     {
         //Guarda los datos del usuario en un array
         String [] a = modelo.jalarDatosUsuario(Integer.parseInt(usuario[0]), Integer.parseInt(usuario[2]));
         
         //Muestra la vista.
         vista.setVisible(true);
         Hora hora =  new Hora(vista.lblHora);
         
         //Restringo valores de la fecha
        vista.dia.setMinimum(1);
        vista.dia.setMaximum(31);
        
        vista.año.setMinimum(anioActual-100);
        vista.año.setMaximum(anioActual-15);
        
        vista.dia.setValue(1);
        vista.mes.setMonth(0);
        vista.año.setValue(anioActual-15);
        
        vista.mes.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent e) {
                if(vista.mes.getMonth()==1)
                    vista.dia.setMaximum(28);
                else
                    vista.dia.setMaximum(31);
            }
        });
        
         //agregamos los rb al grupo de botone spara que solo uno se seleccione
         vista.grupobotones.add(vista.rbHombre);
         vista.grupobotones.add(vista.rbMujer);
         //Si se general la consulta correctamente 
         if(modelo.jalarDatosUsuario(Integer.parseInt(usuario[0]), Integer.parseInt(usuario[2]))!= null)
         {
                //Plasta los datos en sus campos correspondentes
                vista.txtNombre.setText(a[1]);
                vista.txtApellidos.setText(a[2]);
                vista.dia.setValue(modelo.jalarFecha(3,Integer.parseInt(usuario[0]), Integer.parseInt(usuario[2])));
                vista.mes.setMonth(modelo.jalarFecha(2, Integer.parseInt(usuario[0]), Integer.parseInt(usuario[2])));
                vista.año.setValue(modelo.jalarFecha(1,Integer.parseInt(usuario[0]), Integer.parseInt(usuario[2])));
                
                try {
                    if(a[5].equals("Mujer"))
                        vista.rbMujer.setSelected(true);
                    else if(a[5].equals("Hombre"))
                        vista.rbHombre.setSelected(true); 
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e);
                }
                vista.txtCorreo.setText(a[6]);
                vista.txtareaDescripcion.setText(a[7]);
                vista.txtTockname.setText(a[9]);
                
                File dir = new File(destino); 
                String[] ficheros = dir.list(); 
                int j=-1;
                ImageIcon img=null;
                for(int x=0; x<dir.list().length; x++)
                {   
                    if(String.valueOf(a[8]).equals(destino+ficheros[x]))
                    {
                        j=x;
                        break;
                    }  
                }
                if(j==-1)
                    img = new ImageIcon(getClass().getResource("../imagenes/icon-person-128.png"));
                else
                    img = new ImageIcon(destino+""+ficheros[j]);

                Icon fondo = new ImageIcon(img.getImage().getScaledInstance(140, 140, Image.SCALE_DEFAULT));
                vista.lblFoto.setIcon(fondo);
            }
    }
    
    //Metodo para crear un fichero en una direccion de la computadora.
    public static void copyFile_Java(String origen, String destino)
        {
            try 
            {
                CopyOption[]  options = new CopyOption[]
                {
                    StandardCopyOption.REPLACE_EXISTING,
                    StandardCopyOption.COPY_ATTRIBUTES

                }; 
                Files.copy(Paths.get(origen),Paths.get(destino), options); 
                //JOptionPane.showMessageDialog(null, "Imagen guardada");
            }catch(IOException e)
            {
                JOptionPane.showMessageDialog(null, "Error al guardar imagen");
                System.err.println(e.toString());
            }
        }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(vista.btnActualizar == e.getSource())
        {
            String [] a = modelo.jalarDatosUsuario(Integer.parseInt(usuario[0]), Integer.parseInt(usuario[2]));
            String [] b = modelo.validarTockname(vista.txtTockname.getText());
            //Permite acceder a funciones de la fecha.
            Calendar calendario = Calendar.getInstance();
            //Agarra el año en curso.
            int anioActual = calendario.get(Calendar.YEAR);
            //Agarra el mes en curso.
            int mesActual = calendario.get(Calendar.MONTH);
            //Agarra el dia en curso.
            int diaActual = calendario.get(Calendar.DAY_OF_MONTH);
            String fechanac=vista.año.getValue()+"-"+vista.mes.getMonth()+"-"+vista.dia.getValue();
            //Derivo la edad del usuario a partir de la fecha de nacimiento seleccionada.
            int edad=anioActual-vista.año.getValue();
            if(vista.mes.getMonth()>=mesActual && vista.dia.getValue()>=diaActual)
                edad-=1;
            String sexo="";
            
            //Agarra el texto de los rb
            if(vista.rbHombre.isSelected())
                sexo=vista.rbHombre.getText();
            else if(vista.rbMujer.isSelected())
                sexo=vista.rbMujer.getText();
            
            //Si no ha seleccionado ninguna foto se le asigna el valor de 1 a ventana para evitar errores al insertar datos en la bd.
            if (banderaFoto==0) 
                ventana=1;
            //Si ha seleccionado alguna foto, la guarda en la variable archivo.
            if(ventana == JFileChooser.APPROVE_OPTION)
            {  
                File fichero= archi.getSelectedFile();
                String ruta = fichero.getAbsolutePath();
                String nombre = fichero.getName();
                archivo = destino + ""+ nombre; 
                File folder = new File(destino); 
                if(!folder.exists())
                    folder.mkdirs(); 
                copyFile_Java(ruta,archivo); 
            }
           
            //Si ha dejado algun campo vacio, se muestra un mensaje de error.
            if(vista.txtNombre.getText().isEmpty() || vista.txtApellidos.getText().isEmpty() || vista.txtTockname.getText().isEmpty())
            {
                JOptionPane.showMessageDialog(null, "Favor de llenar todos los campos.");
            }
            
            //Si no exite el usuario ingresado pero el email es invalido.
            else if(modeloRegistrar.validarEmail(vista.txtCorreo.getText())!=true)
            {
                //Limpia la caja de texto.
                vista.txtApellidos.setText("");
                //Muestra un mensaje de error.
                JOptionPane.showMessageDialog(null, "Favor ingresar correctamente su email.");
            }
            
            //Si ya existe el nombre de usuario ingresado.
            else if(Integer.parseInt(b[0])>0 && !b[1].equals(usuario[0]))
            {
                
                //Limpia la caja de texto.
                vista.txtTockname.setText("");
                //Manda un mensaje de error.
                JOptionPane.showMessageDialog(null, "Favor de usar otro Tockname.");
            }
            
            //Si no exite el usuario ingresado, el email es valido pero el genero no.
            else if(!vista.rbHombre.isSelected() && !vista.rbMujer.isSelected())
            {
                //Muestra un mensage de error.
                JOptionPane.showMessageDialog(null, "Favor de seleccionar un sexo.");
            }
            
            //Si se realiza con exito la sentencia sql de insertar.
            else if(modelo.actualizarDatosUsuario(Integer.parseInt(a[0]), vista.txtNombre.getText(), 
                    vista.txtApellidos.getText(), fechanac, edad, sexo, vista.txtCorreo.getText(), 
                    vista.txtareaDescripcion.getText(), archivo, vista.txtTockname.getText()))
                {
                    //Muestra un mensaje de exito.
                    JOptionPane.showMessageDialog(null, "Registro modificado satisfactoriamente.");
                    //Limpia el panel actual
                    vista.pnlCambiante.removeAll();
                    vista.pnlCambiante.revalidate();
                    vista.pnlCambiante.repaint();
                    //Crea objetos del siguiente panel
                    vistaPerfil panelPerfil = new  vistaPerfil();
                    modeloPerfil modeloPerfil = new modeloPerfil(); 
                    controlPerfil controlPerfil = new controlPerfil(modeloPerfil,panelPerfil, usuario, inicio); 
                    //Lo añade al panel
                    vista.pnlCambiante.add(panelPerfil);
                    vista.pnlCambiante.revalidate();
                    vista.pnlCambiante.repaint();
                    //Y lo muestra.
                    controlPerfil.iniciarVista();
                }
            
                //Si no se ejecuta con exito la sentencia sql se mostrará un mensaje de error.
                else
                {
                    //Muestra un mensaje de error.
                    JOptionPane.showMessageDialog(null, "Error al insertar los datos.");
                }
            }
        //Si hace clic en el boton seleccionar, abre la ventana para seleccionar una foto y la plasma en su campo correspondiente
        else if(vista.btnSeleccionar == e.getSource())
        {
            FileNameExtensionFilter filtro = new FileNameExtensionFilter("Formato de archivo JPG", "jpg", "jpeg"); 
            archi.addChoosableFileFilter(filtro);
            archi.setDialogTitle("Abrir archivo"); 
            ventana = archi.showOpenDialog(null); 
            if( ventana == JFileChooser.APPROVE_OPTION)
            {
                vista.lblFoto.removeAll();
                vista.lblFoto.revalidate();
                vista.lblFoto.repaint();
                java.io.File file = archi.getSelectedFile(); 
                Image foto = vista.getToolkit().getImage(String.valueOf(file)); 
                foto = foto.getScaledInstance(110, 110, Image.SCALE_DEFAULT); 
                vista.lblFoto.setIcon(new ImageIcon(foto)); 
                banderaFoto=1;
            }
        }
        //Si quiere cambiar la contraseña se muestra una serie de pantallas emergentes como proceso para la modificacion.
        else if(vista.btnCambiarContrasenia == e.getSource())
        {
            String [] a; 
            String contra="";
            int bandera=0;
            a = modelo.jalarDatosUsuario(Integer.parseInt(usuario[0]), Integer.parseInt(usuario[2]));
            do
            {
                //Agarro el valor que ingrese en el input del option pane.
                contra = JOptionPane.showInputDialog(null, "Ingrese su contraseña", "Cambiar contraseña", JOptionPane.PLAIN_MESSAGE);
                //Si selecciona el botón cancelar se cancela el proceso.
                if(contra==null)
                {
                    JOptionPane.showMessageDialog(null, "Se ha cancelado el proceso", "Error", JOptionPane.WARNING_MESSAGE);
                    bandera=1;
                }
                //Si selecciona el botón aceptar pero no ingreso el dato, el sistema pide que lo haga y se repite el proceso.
                else if(contra.equals(""))
                {
                    JOptionPane.showMessageDialog(null, "Ingrese su contraseña", "Error", JOptionPane.ERROR_MESSAGE);
                }
                //Si la contraseña actual es incorrecta, se repite el proceso.
                else if(!a[10].equals(contra))
                {
                    JOptionPane.showMessageDialog(null, "Contraseña incorrecta", "Error", JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    //Si el usuario ingresó bien la contraseña, se le pide que introduzca la nueva contraseña.
                    do
                    {
                        String contraNueva = JOptionPane.showInputDialog(null, "Ingrese la nueva contraseña", "Cambiar contraseña", JOptionPane.PLAIN_MESSAGE);
                        //Si selecciona el botón cancelar, se cancela el proceso.
                        if(contraNueva==null)
                        {
                            JOptionPane.showMessageDialog(null, "Se ha cancelado el proceso", "Error", JOptionPane.WARNING_MESSAGE);
                            bandera=1;
                        }
                        //Si selecciona el botón aceptar pero no ingreso el dato, el sistema pide que lo haga y se repite el proceso.
                        else if(contraNueva.equals(""))
                        {
                            JOptionPane.showMessageDialog(null, "Ingrese la contraseña", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        //Si la longitud de la contraseña es menor a 8 caracteres le pide que la vuelva a ingresar con un minimo de 8 caracteres.
                        else if(contraNueva.length()<8)
                        {
                            JOptionPane.showMessageDialog(null, "Ingrese 8 caracteres como minimo.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                        //Si la contraseña ingresada es igual o mayor a 8 caracteres, se le pide que la confirme.
                        else
                        {
                            String contraConfirmar = JOptionPane.showInputDialog(null, "Confirmar contraseña", "Cambiar cantraseña", JOptionPane.INFORMATION_MESSAGE);
                            //Si selecciona el boton cancelar, se cancela el proceso.
                            if(null==contraConfirmar) {
                                JOptionPane.showMessageDialog(null, "Se ha cancelado el proceso", "Error", JOptionPane.WARNING_MESSAGE);
                                bandera=1;
                            }
                            //Si selecciona el botón aceptar pero no ingreso el dato, el sistema pide que lo haga y se repite el proceso.
                            else if(contraNueva.equals(""))
                            {
                                JOptionPane.showMessageDialog(null, "Ingrese la contraseña", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            //Si ambas contraseñas coinciden se modifica la contraseña en la bd.
                            else if(contraNueva.equals(contraConfirmar))
                            {
                                if(modelo.actualizarContraseña(Integer.parseInt(usuario[2]), contraNueva))
                               {
                                   bandera=1;
                                   JOptionPane.showMessageDialog(null, "Su contraseña ha sido actualizada", "", JOptionPane.INFORMATION_MESSAGE);
                               }
                            }
                            
                            //Si las contraseñas no coinciden, se repite el proceso.
                            else
                            {
                                JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }while(bandera==0);
                }
            }while(bandera==0);
            
        }
    }

}
