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
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import modelo.Conexion;
import modelo.ModeloActividad;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import vista.PActividad;
import vista.estiloDeViaje;

/**
 *
 * @author Fabiola Paez
 */
public class ControlActividad implements ActionListener, MouseListener, KeyListener{
    private ModeloActividad modelo; // Se crea un objeto tipo modelo 
    private PActividad vista; // Se crea un objeto para la vista 
    private estiloDeViaje vista2; // Secrea un objeto para la vista que agrega estilo de viaje a las activides
    private int idEstilo; // Variable para guardar el id del estilo de viaje que se desee agregar o eliminar 
    // Se crea un constructor de parametros 
    public ControlActividad(ModeloActividad  modelo, PActividad vista, estiloDeViaje vista2)
    {   this.modelo= modelo; 
        this.vista= vista;  
        this.vista2= vista2; 
        this.vista.btnAgregar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);
        this.vista.btnActualizar.addActionListener(this);
        this.vista.btnCancelar.addActionListener(this);
        this.vista.btnAgregarTipo.addActionListener(this);
        this.vista.btnEliminarTipo.addActionListener(this);
        this.vista.btnReporte.addActionListener(this);
        this.vista.tblEstiloViaje.addMouseListener(this);
        this.vista.tblActividad.addMouseListener(this);
        this.vista.txtBuscar.addKeyListener(this);
        // Botones y tabla del Jpanel estiloDeViaje 
        this.vista2.btnAgregarE.addActionListener(this);
        this.vista2.btnCancelar.addActionListener(this);
        this.vista2.tblPosee.addMouseListener(this);
        this.vista2.txtBuscar.addKeyListener(this);
        deshabilitar();
    }
    public void habilitar() // Metodo para habilitar los botones 
    {   vista.btnActualizar.setEnabled(true);
        vista.btnAgregar.setEnabled(true);
        vista.btnEliminar.setEnabled(true);
        vista.btnCancelar.setEnabled(true);
        vista.btnEliminarTipo.setEnabled(true);
        vista.btnAgregarTipo.setEnabled(true);
    }
    public void deshabilitar() // Metodo para deshabilitar los botones 
    {   vista.btnActualizar.setEnabled(false);
        vista.btnEliminar.setEnabled(false);
        vista.btnEliminarTipo.setEnabled(false);
        vista.btnAgregarTipo.setEnabled(false);
    }
    public void limpiar() // Metodo para limpiar los cuadros de texto 
    {   vista.txtId.setText(""); 
        vista.txtDescripcion.setText("");
        vista.txtNombre.setText(""); 
        deshabilitar();
    }
    public void ocultaColumnaActividad() // Oculta la columna del id en la tabla de actividades 
    {   this.vista.tblActividad.getColumnModel().getColumn(0).setMaxWidth(0);
        this.vista.tblActividad.getColumnModel().getColumn(0).setMinWidth(0);
        this.vista.tblActividad.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
        this.vista.tblActividad.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
        this.vista.tblActividad.getColumnModel().getColumn(0).setPreferredWidth(0);
    }
    public void ocultaColumnaEstilo() // Oculta la columna del id de la tabla para ver el estilo de viaje de las actividades  
    {   this.vista.tblEstiloViaje.getColumnModel().getColumn(0).setMaxWidth(0);
        this.vista.tblEstiloViaje.getColumnModel().getColumn(0).setMinWidth(0);
        this.vista.tblEstiloViaje.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
        this.vista.tblEstiloViaje.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
        this.vista.tblEstiloViaje.getColumnModel().getColumn(0).setPreferredWidth(0);
    }
    public void ocultaColumnaPosee() // Oculta la columna del id de la tabla para agregar estilo de viaje a las actividades 
    {   this.vista2.tblPosee.getColumnModel().getColumn(0).setMaxWidth(0);
        this.vista2.tblPosee.getColumnModel().getColumn(0).setMinWidth(0);
        this.vista2.tblPosee.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
        this.vista2.tblPosee.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
        this.vista2.tblPosee.getColumnModel().getColumn(0).setPreferredWidth(0);
    }
    public void iniciarVista() // Muetra la vista principal 
    {   vista.tblActividad.setModel(modelo.administradorConsultar()); 
        ocultaColumnaActividad(); 
        ocultaColumnaEstilo(); 
        vista.setVisible(true); 
    }
    public String validacionCamposVacios() // Verifica que no haya campos vacios 
    {   if(vista.txtNombre.getText().isEmpty() || vista.txtDescripcion.getText().isEmpty())
            return "Favor de llenar todos los campos"; 
        else return null;
    }
    @Override
    public void actionPerformed(ActionEvent evento) 
    {   if(vista.btnAgregar == evento.getSource())
        {   // Manda los datos para generar un registro 
            if(validacionCamposVacios()==null)
            {    if(modelo.administradorInsertarA(vista.txtNombre.getText(),vista.txtDescripcion.getText()))
                {   JOptionPane.showMessageDialog(null, "Registro agregado exitosamente");
                    this.vista.tblActividad.setModel(modelo.administradorConsultar());
                    ocultaColumnaActividad(); 
                    limpiar(); 
                }
                else 
                    JOptionPane.showMessageDialog(null, "Error al insertar los datos");
            }
            else
               JOptionPane.showMessageDialog(null, ""+validacionCamposVacios());
        }
        else if(vista.btnEliminar == evento.getSource())
            {   // Manda el id del registro que se va ha de eliminar 
                if (JOptionPane.showConfirmDialog(vista,"¿Estás seguro que deseas eliminar este registro?", "Fleetock",
                    JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
                {   int pregunta = modelo.administradorConsultaParaEliminarA(Integer.parseInt(vista.txtId.getText())); // Para consultar que ese registro no tenga datos relacioandos con otra tabla
                    if( pregunta > 0)
                        JOptionPane.showMessageDialog(null, "No se puede eliminar, este registro se encuentra en otra tabla");
                    else if (pregunta == -1)
                         JOptionPane.showMessageDialog(null, "Error al eliminar los datos");
                    else if(modelo.administradorEliminarA(Integer.parseInt(vista.txtId.getText())))
                        {   JOptionPane.showMessageDialog(null, "Registro eliminado exitosamente");
                            this.vista.tblActividad.setModel(modelo.administradorConsultar());
                            ocultaColumnaActividad(); 
                            limpiar(); 
                        }
                        else 
                            JOptionPane.showMessageDialog(null, "Error al eliminar los datos");
                }
            }
            else if(vista.btnActualizar == evento.getSource())
                {   // Manda el id del registro que se ha de modificar 
                    if(validacionCamposVacios()==null)
                    {   if(modelo.administradorActualizarA(Integer.parseInt(vista.txtId.getText()), 
                               vista.txtNombre.getText(),vista.txtDescripcion.getText()))
                        {   JOptionPane.showMessageDialog(null, "Registro actualizado exitosamente");
                            this.vista.tblActividad.setModel(modelo.administradorConsultar());
                            ocultaColumnaActividad(); 
                            limpiar(); 
                        }
                        else 
                            JOptionPane.showMessageDialog(null, "Error al actualizar los datos");
                    }
                    else 
                        JOptionPane.showMessageDialog(null, ""+validacionCamposVacios());
                }
                else if(vista.btnCancelar == evento.getSource())
                    {   // Limpia los cuadros de texto y deshabilita los botones cuando cancela 
                        limpiar(); 
                        deshabilitar(); 
                    }
                    else if(vista.btnAgregarTipo == evento.getSource())
                    {   // Carga la vista que permite agregar estilos de viaje a una actividad
                        vista2.setTitle("Agregar Estilo de Viaje en Actividad");
                        vista2.setLocationRelativeTo(null);
                        vista2.setIconImage(new ImageIcon(getClass().getResource("../imagenes/logo.png")).getImage());
                        vista2.setVisible(true);
                        vista2.tblPosee.setModel(modelo.administradorConsultarEstiloV()); 
                        ocultaColumnaPosee(); 
                    }
                    else if(vista.btnEliminarTipo==evento.getSource())
                    {   // Elimina un tipo de viaje a una actividad 
                        if(modelo.administradorEliminarP(idEstilo, Integer.parseInt(vista.txtId.getText())))
                        {   this.vista.tblEstiloViaje.setModel(modelo.administradorConsultarEstiloA(Integer.parseInt(vista.txtId.getText())));
                            ocultaColumnaEstilo();
                        }
                    }
                    else if(vista2.btnAgregarE == evento.getSource())
                        {   // Envia el id correspondiente a el estilo de viaje que se agregara 
                            if(modelo.administradorInsertarP(idEstilo, Integer.parseInt(vista.txtId.getText())))
                            {   JOptionPane.showMessageDialog(null, "Registro modificado exitosamente");
                                this.vista2.dispose(); // Se cierra la vista para agregar un estilo de viaje a actividad 
                                // Se actualiza la tabla con los etilos de viaje de la actividad 
                                this.vista.tblEstiloViaje.setModel(modelo.administradorConsultarEstiloA(Integer.parseInt(vista.txtId.getText())));  
                                ocultaColumnaEstilo();
                            }
                            else
                                JOptionPane.showMessageDialog(null, "Error al modificar los datos");
                        }
                        else if(vista2.btnCancelar == evento.getSource())
                            {   // Si da click en cancelar la vista para agregar un estilo de viaje a una actividad se cerrara 
                                vista2.dispose();
                            }
                            else if(vista.btnReporte == evento.getSource()) // Genera un reporte de todas las actividades 
                                {   try
                                    {   try
                                        {   Conexion conexion = new Conexion(); 
                                            Connection con = conexion.abrirConexion();
                                            if( con != null)
                                            {   JasperReport reporte= null; 
                                                String path="src\\reportes\\reporteActividad.jasper"; 
                                                reporte = (JasperReport) JRLoader.loadObjectFromFile(path); 
                                                JasperPrint jprint = JasperFillManager.fillReport(reporte, null,con); 
                                                JasperViewer view = new JasperViewer(jprint, false); 
                                                view.setDefaultCloseOperation(DISPOSE_ON_CLOSE); 
                                                view.setVisible(true);
                                            }
                                        }
                                        catch(SQLException e)
                                        {   JOptionPane.showMessageDialog(null, "Error al intentar generar el reporte");
                                        }
                                    }catch(JRException ex)
                                    {   JOptionPane.showMessageDialog(null, "Error al intentar generar el reporte"); 
                                    }
                                }
                        
    }
    @Override
    public void mouseClicked(MouseEvent e) 
    {   if(vista.tblActividad == e.getSource())
        {    // Carga la fila seleccionada a los campos de texto 
            int fila = vista.tblActividad.rowAtPoint(e.getPoint()); 
            if( fila > -1)
            {   vista.txtId.setText(String.valueOf(vista.tblActividad.getValueAt(fila, 0)));
                vista.txtNombre.setText(String.valueOf(vista.tblActividad.getValueAt(fila, 1)));
                vista.txtDescripcion.setText(String.valueOf(vista.tblActividad.getValueAt(fila, 2)));
                habilitar();
                // Carga los estilos de viaje (a la tabla) que tiene la actividad seleccionada 
                this.vista.tblEstiloViaje.setModel(modelo.administradorConsultarEstiloA(Integer.parseInt(vista.txtId.getText())));
                ocultaColumnaEstilo();
            }
        }
        else if(vista2.tblPosee == e.getSource())
        {   // Guarda el id del estilo de viaje por si despues desea agregarlo
            int fila = vista2.tblPosee.rowAtPoint(e.getPoint()); 
            if( fila > -1)
            {   this.idEstilo = Integer.parseInt(String.valueOf(vista2.tblPosee.getValueAt(fila, 0))); 
                habilitar(); 
            }
        }
        else if(vista.tblEstiloViaje == e.getSource())
        {   // Guarda el id del estilo de vaieje por si despues desea eliminarlo 
            int fila = vista.tblEstiloViaje.rowAtPoint(e.getPoint()); 
            if( fila > -1)
            {   this.idEstilo = Integer.parseInt(String.valueOf(vista.tblEstiloViaje.getValueAt(fila, 0))); 
                habilitar(); 
            }
        }
    }
    @Override
    public void mousePressed(MouseEvent me) {}
    @Override
    public void mouseReleased(MouseEvent me) {}
    @Override
    public void mouseEntered(MouseEvent me) {}
    @Override
    public void mouseExited(MouseEvent me) {}
    @Override
    public void keyTyped(KeyEvent ke) {}
    @Override
    public void keyPressed(KeyEvent ke) {}
    @Override
    // Buscador en tiempo real (busca lo que se esta tecleando)
    public void keyReleased(KeyEvent ke) 
    {   vista.tblActividad.setModel(modelo.Buscador(String.valueOf(vista.txtBuscar.getText())));
        ocultaColumnaActividad();
        vista2.tblPosee.setModel(modelo.BuscadorEstiloViaje(String.valueOf(vista2.txtBuscar.getText())));
        ocultaColumnaPosee();
    }
}
