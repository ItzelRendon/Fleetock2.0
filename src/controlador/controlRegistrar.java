/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Calendar;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import modelo.modeloRegistrar;
import modelo.modeloRegistrar2;
import vista.vistaLogin;
import vista.vistaRegistrar;
import vista.vistaRegistrar2;



public class controlRegistrar implements ActionListener {
    //Variables para poder acceder al modelo y la vista.
    private modeloRegistrar modelo;
    private vistaRegistrar vista;
    private vistaLogin vistaLogin;

    //Permite acceder a funciones de la fecha.
    Calendar calendario = Calendar.getInstance();
    //Agarra el año en curso.
    int anioActual = calendario.get(Calendar.YEAR);
    //Agarra el mes en curso.
    int mesActual = calendario.get(Calendar.MONTH);
    //Agarra el dia en curso.
    int diaActual = calendario.get(Calendar.DAY_OF_MONTH);

    //Constructor de la clase.
    public controlRegistrar(modeloRegistrar modelo, vistaRegistrar vista, vistaLogin vl)
    {
        this.modelo = modelo;
        this.vista = vista;
        this.vista.btn_listo.addActionListener(this);
        this.vistaLogin=vl;
        ValidarCampos(this.vista.txt_Nombre);
        ValidarCampos(this.vista.txt_Apellidos);
        
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
    //Le asigna un nombre a la ventana y la muestra al centro de la pantalla.
    public void iniciarVista()
    {
        vista.setVisible(true);
        
        vista.spfld_dia.setMinimum(1);
        vista.spfld_dia.setMaximum(31);
        
        vista.yrch_anio.setMinimum(anioActual-100);
        vista.yrch_anio.setMaximum(anioActual-15);
        
        vista.spfld_dia.setValue(1);
        vista.mthch_mes.setMonth(0);
        vista.yrch_anio.setValue(anioActual-15);
        
        vista.mthch_mes.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent e) {
                if(vista.mthch_mes.getMonth()==1)
                    vista.spfld_dia.setMaximum(28);
                else
                    vista.spfld_dia.setMaximum(31);
            }
        });
        
        Hora hora =  new Hora(vista.lblHora);
        
    }
    
    //Limpia los campos de la vista.
    public void limpiarCajasTexto()
    {
        vista.txt_Apellidos.setText("");
        vista.txt_ConfirmarContrasenia.setText("");
        vista.txt_Contrasenia.setText("");
        vista.txt_Email.setText("");
        vista.txt_Nombre.setText("");
        vista.txt_TockName.setText("");
        vista.spfld_dia.setValue(0);
        vista.mthch_mes.setMonth(0);
        vista.yrch_anio.setValue(anioActual-15);
    }
    public void ValidarCampos(KeyEvent ke)
    {
        if((vista.txt_Nombre.equals(Character.isDigit(ke.getKeyChar())) || vista.txt_Nombre.getText().length() == 80) || (vista.txt_Apellidos.equals(Character.isDigit(ke.getKeyChar())) || vista.txt_Apellidos.getText().length() == 80))
            ke.consume();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        //Paso a una variable string los valores seleccionados por el usuario en el formato date de la bd.
        String fechaNac= vista.yrch_anio.getValue()+"-"+(vista.mthch_mes.getMonth()+1)+"-"+vista.spfld_dia.getValue();
        
        //Derivo la edad del usuario a partir de la fecha de nacimiento seleccionada.
        int edad=anioActual-vista.yrch_anio.getValue();
        if(vista.mthch_mes.getMonth()>=mesActual && vista.spfld_dia.getValue()>=diaActual)
            edad-=1;
        
        //Si selecciona el boton.
        if(vista.btn_listo == e.getSource())
        {
            if(vista.txt_Nombre.getText().isEmpty() || vista.txt_Apellidos.getText().isEmpty() || vista.txt_TockName.getText().isEmpty())
            {
                JOptionPane.showMessageDialog(null, "Favor de llenar todos los campos.");
            }
            //Si no exite el usuario ingresado pero el email es invalido.
            else if(modelo.validarEmail(vista.txt_Email.getText())!=true)
            {
                //Limpia la caja de texto.
                vista.txt_Email.setText("");
                //Muestra un mensaje de error.
                JOptionPane.showMessageDialog(null, "Favor ingresar correctamente su email.");
            }
            //Si ya existe el nombre de usuario ingresado.
            else if(modelo.validarTockname(vista.txt_TockName.getText())>0)
            {
                //Limpia la caja de texto.
                vista.txt_TockName.setText("");
                //Manda un mensaje de error.
                JOptionPane.showMessageDialog(null, "Favor de usar otro Tockname.");
            }
            //Si no exite el usuario ingresado, el email es valido pero el genero no.
            else if(vista.cmbx_Sexo.getSelectedIndex()==0)
            {
                //Muestra un mensage de error.
                JOptionPane.showMessageDialog(null, "Ingrese un genero válido.");
            }
            else if(vista.txt_Contrasenia.getText().length()<8)
            {
                JOptionPane.showMessageDialog(null, "Ingrese una contraseña con 8 caracteres como minimo.");
            }
            //Si no existe el usuario ingresado, el email es valido, el genero es valido y ambas contraseñas ingresadas coinciden.
            else if(vista.txt_Contrasenia.getText().equals(vista.txt_ConfirmarContrasenia.getText()))
            {
                
                //Si se realiza con exito la sentencia sql de insertar.
                if(modelo.registrarInsertar(vista.txt_Nombre.getText(), vista.txt_Apellidos.getText(), vista.txt_Email.getText(), 
                    vista.txt_TockName.getText(), vista.txt_Contrasenia.getText(), String.valueOf(vista.cmbx_Sexo.getSelectedItem()),
                    fechaNac, edad, "activo", "Usuario"))
                {
                    
                    //Limpia el panel actual
                    this.vista.jpnRegistro.removeAll();
                    this.vista.jpnRegistro.revalidate();
                    this.vista.jpnRegistro.repaint();
                    //Crea objetos del siguiente panel
                    vistaRegistrar2 panelRegistrar2 = new  vistaRegistrar2();
                    modeloRegistrar2 modeloR2 = new modeloRegistrar2(); 
                    controlRegistrar2 controlR2 = new controlRegistrar2(modeloR2,panelRegistrar2,vista.txt_TockName.getText(),vista.txt_Contrasenia.getText(),vistaLogin); 
              
                    //Lo añade al panel
                    this.vista.jpnRegistro.add(panelRegistrar2);
                    this.vista.jpnRegistro.revalidate();
                    this.vista.jpnRegistro.repaint();
                    //Limpia las cajas de texto.
                    limpiarCajasTexto();
                    //Y lo muestra.
                    controlR2.iniciarVista();
                    
                }
                //Si no se ejecuta con exito la sentencia sql se mostrará un mensaje de error.
                else
                {
                    //Muestra un mensaje de error.
                    JOptionPane.showMessageDialog(null, "Error al insertar los datos.");
                }
            }
            //Si no existe el usuario ingresado, el email es valido pero las contraseñas ingresadas no coinciden.
            else
            {
                //Limpia las cajas de texto.
                vista.txt_Contrasenia.setText("");
                vista.txt_ConfirmarContrasenia.setText("");
                //Muestra un mensaje de error.
                JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden.");
            }
            
        }
    }
    
    
    
}
