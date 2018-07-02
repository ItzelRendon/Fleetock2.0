/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import com.sun.istack.internal.logging.Logger;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.logging.Level;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import modelo.ModeloActividad;
import vista.PActividad;
import vista.estiloDeViaje;

/**
 *
 * @author Fabiola Paez
 */
public class ControlActividad implements ActionListener, MouseListener, KeyListener{
    private ModeloActividad modelo; 
    private PActividad vista; 
    private estiloDeViaje vista2; 
    private int id, idEstilo; 
    public ControlActividad(ModeloActividad  modelo, PActividad vista, estiloDeViaje vista2)
    {
        this.modelo= modelo; 
        this.vista= vista;  
        this.vista2= vista2; 
        this.vista.btnAgregar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);
        this.vista.btnEliminarT.addActionListener(this);
        this.vista.btnActualizar.addActionListener(this);
        this.vista.tbl_Actividad.addMouseListener(this);
        this.vista.btnCancelar.addActionListener(this);
        this.vista.btn_Buscar.addActionListener(this);
        this.vista.btnAgregarTipo.addActionListener(this);
        this.vista2.btnAgregarE.addActionListener(this);
        this.vista2.btnCancelar.addActionListener(this);
        this.vista2.tbl_Posee.addMouseListener(this);
        this.vista.tblEstiloViaje.addMouseListener(this);
       desabilitar();
    }
    public void habilitar()
    {
        vista.btnActualizar.setEnabled(true);
        vista.btnAgregar.setEnabled(true);
        vista.btnEliminar.setEnabled(true);
        vista.btnCancelar.setEnabled(true);
        vista.btnEliminarT.setEnabled(true);
        vista.btnAgregarTipo.setEnabled(true);
    }
    public void desabilitar()
    {
        vista.btnActualizar.setEnabled(false);
        vista.btnEliminar.setEnabled(false);
        vista.btnEliminarT.setEnabled(false);
        vista.btnAgregarTipo.setEnabled(false);
    }
    public void limpiar()
    {
        vista.txtId.setText(""); 
        vista.txtDescripcion.setText("");
        vista.txtNombre.setText(""); 
        desabilitar();
    }
    public void ocultaColumna()
    {
        this.vista.tbl_Actividad.getColumnModel().getColumn(0).setMaxWidth(0);
        this.vista.tbl_Actividad.getColumnModel().getColumn(0).setMinWidth(0);
        this.vista.tbl_Actividad.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
        this.vista.tbl_Actividad.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
        this.vista.tbl_Actividad.getColumnModel().getColumn(0).setPreferredWidth(0);
    }
    public void ocultaColumna1()
    {
        this.vista.tblEstiloViaje.getColumnModel().getColumn(0).setMaxWidth(0);
        this.vista.tblEstiloViaje.getColumnModel().getColumn(0).setMinWidth(0);
        this.vista.tblEstiloViaje.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
        this.vista.tblEstiloViaje.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
        this.vista.tblEstiloViaje.getColumnModel().getColumn(0).setPreferredWidth(0);
    }
    public void ocultaColumna2()
    {
        this.vista2.tbl_Posee.getColumnModel().getColumn(0).setMaxWidth(0);
        this.vista2.tbl_Posee.getColumnModel().getColumn(0).setMinWidth(0);
        this.vista2.tbl_Posee.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
        this.vista2.tbl_Posee.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
        this.vista2.tbl_Posee.getColumnModel().getColumn(0).setPreferredWidth(0);
    }
    public void iniciarVista()
    {
        vista.tbl_Actividad.setModel(modelo.administradorConsultar()); 
        ocultaColumna(); 
        ocultaColumna1(); 
        vista.setVisible(true); 
    }
    public String validacionCamposVacios()
    {
        if(vista.txtNombre.getText().isEmpty() || vista.txtDescripcion.getText().isEmpty())
            return "Favor de llenar todos los campos"; 
        else return null;
    }
    @Override
    public void actionPerformed(ActionEvent evento) {
        if(vista.btnAgregar == evento.getSource())
        {
           if(validacionCamposVacios()==null)
           {    
                if(modelo.administradorInsertarA(vista.txtNombre.getText(),vista.txtDescripcion.getText()))
                {
                    JOptionPane.showMessageDialog(null, "Registro agregado exitosamente");
                    this.vista.tbl_Actividad.setModel(modelo.administradorConsultar());
                    ocultaColumna(); 
                    limpiar(); 
                }else 
                    JOptionPane.showMessageDialog(null, "Error al insertar los datos");
           }else 
           {
               JOptionPane.showMessageDialog(null, ""+validacionCamposVacios());
           } 
        }
        else if(vista.btnEliminar == evento.getSource())
            {
                if (JOptionPane.showConfirmDialog(vista,
                    "¿Estás seguro que deseas eliminar este registro?", "Fleetock",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                    if(modelo.administradorEliminarA(Integer.parseInt(vista.txtId.getText())))
                    {
                        JOptionPane.showMessageDialog(null, "Registro eliminado exitosamente");
                        this.vista.tbl_Actividad.setModel(modelo.administradorConsultar());
                        ocultaColumna(); 
                        limpiar(); 
                    }else 
                        JOptionPane.showMessageDialog(null, "Error al eliminar los datos");
                }
                else{

                }
            }
            else if(vista.btnActualizar == evento.getSource())
                {
                    if(validacionCamposVacios()==null)
                    {  
                        if(modelo.administradorActualizarA(Integer.parseInt(vista.txtId.getText()), 
                               vista.txtNombre.getText(),vista.txtDescripcion.getText()))
                        {
                           JOptionPane.showMessageDialog(null, "Registro actualizado exitosamente");
                           this.vista.tbl_Actividad.setModel(modelo.administradorConsultar());
                           ocultaColumna(); 
                           limpiar(); 
                        }else 
                            JOptionPane.showMessageDialog(null, "Error al actualizar los datos");
                    }else 
                    {
                        JOptionPane.showMessageDialog(null, ""+validacionCamposVacios());
                    } 
                }
                else if(vista.btnCancelar == evento.getSource())
                    {
                       limpiar(); 
                       desabilitar(); 
                    }
                    else if(vista.btn_Buscar == evento.getSource())
                        {
                           this.vista.tbl_Actividad.setModel(modelo.Buscador(vista.txt_Buscar.getText()));
                           ocultaColumna(); 
                           vista.txt_Buscar.setText("");
                        }
                        else if(vista.btnAgregarTipo == evento.getSource())
                        {
                            vista2.setTitle("Agregar Estilo de Viaje en Actividad");
                            vista2.setLocationRelativeTo(null);
                            vista2.setIconImage(new ImageIcon(getClass().getResource("../imagenes/logo.png")).getImage());
                            vista2.setVisible(true);
                            vista2.tbl_Posee.setModel(modelo.administradorConsultarP()); 
                            ocultaColumna2(); 
                        }
                        else if(vista.btnEliminarT==evento.getSource())
                        {
                            if(modelo.administradorEliminarP(idEstilo, Integer.parseInt(vista.txtId.getText())))
                            {
                                this.vista.tblEstiloViaje.setModel(modelo.administradorConsultarTipo(Integer.parseInt(vista.txtId.getText())));
                                ocultaColumna1();
                                this.vista2.dispose();
                            }
                        }
                        else if(vista2.btnAgregarE == evento.getSource())
                            {
                                if(modelo.administradorInsertarP(idEstilo, Integer.parseInt(vista.txtId.getText())))
                                {
                                    JOptionPane.showMessageDialog(null, "Registro modificado exitosamente");
                                    this.vista2.dispose();
                                    this.vista.tblEstiloViaje.setModel(modelo.administradorConsultarTipo(Integer.parseInt(vista.txtId.getText())));
                                    ocultaColumna1();
                                }else
                                    JOptionPane.showMessageDialog(null, "Error al modificar los datos");
                            }
                        else if(vista2.btnCancelar == evento.getSource())
                            {
                               vista2.dispose();
                            }
                        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
       if(vista.tbl_Actividad == e.getSource())
       {    int fila = vista.tbl_Actividad.rowAtPoint(e.getPoint()); 
            if( fila > -1)
            {   vista.txtId.setText(String.valueOf(vista.tbl_Actividad.getValueAt(fila, 0)));
                this.id = Integer.parseInt(vista.txtId.getText()); 
                vista.txtNombre.setText(String.valueOf(vista.tbl_Actividad.getValueAt(fila, 1)));
                vista.txtDescripcion.setText(String.valueOf(vista.tbl_Actividad.getValueAt(fila, 2)));
                habilitar(); 
                this.vista.tblEstiloViaje.setModel(modelo.administradorConsultarTipo(Integer.parseInt(vista.txtId.getText())));
                ocultaColumna1();
            }
       }
       else if(vista2.tbl_Posee == e.getSource())
       {    int fila = vista2.tbl_Posee.rowAtPoint(e.getPoint()); 
            if( fila > -1)
            {   this.idEstilo= Integer.parseInt(String.valueOf(vista2.tbl_Posee.getValueAt(fila, 0))); 
                habilitar(); 
            }
       }
       else if(vista.tblEstiloViaje == e.getSource())
       {    int fila = vista.tblEstiloViaje.rowAtPoint(e.getPoint()); 
            if( fila > -1)
            {   this.idEstilo= Integer.parseInt(String.valueOf(vista.tblEstiloViaje.getValueAt(fila, 0))); 
                habilitar(); 
            }
       }
    }

    @Override
    public void mousePressed(MouseEvent me) {
        
    }

    @Override
    public void mouseReleased(MouseEvent me) {
       
    }

    @Override
    public void mouseEntered(MouseEvent me) {
       
    }

    @Override
    public void mouseExited(MouseEvent me) {
       
    }

    @Override
    public void keyTyped(KeyEvent ke) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(KeyEvent ke) {
//        if(vista.txt_Buscar.getText() == ke.getSource())
//            try{
//                CargarTabla(vista.txt_Buscar.getText()); 
//            }catch(SQLException ex)
//            {
//                Logger.getLogger(PActividad.class.getName()).log(Level.SEVERE, null, ex)); 
//            }
    }
}
