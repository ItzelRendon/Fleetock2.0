/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;
import modelo.ModeloSitioTuristico; 
import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener; 
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent; 
import java.awt.event.MouseListener; 
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import modelo.Conexion;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import vista.PTipoSitioTuristico;
/**
 *
 * @author Fabiola Paez
 */
public class ControlSitioTuristico implements ActionListener, MouseListener, KeyListener{
    private ModeloSitioTuristico modelo; // Se crea un objeto del modelo 
    private PTipoSitioTuristico vista; // Se crea un objeto de la vista
    // Constructor de parametros 
    public ControlSitioTuristico(ModeloSitioTuristico  modelo, PTipoSitioTuristico vista)
    {   this.modelo= modelo; 
        this.vista= vista; 
        this.vista.btnAgregar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);
        this.vista.btnActualizar.addActionListener(this);
        this.vista.btnCancelar.addActionListener(this);
        this.vista.btnReporte.addActionListener(this); 
        this.vista.tbl_STuristicos.addMouseListener(this);
        this.vista.txt_Buscar.addKeyListener(this);
        deshabilitar(); 
    }
    public void limpiar() // Limpia los cuadros de texto cuando se agrega, modifica y se elimina 
    {   vista.txtId.setText(""); 
        vista.taDescripcion.setText("");
        vista.txtTipo.setText("");
        deshabilitar();
    }
    public void habilitar() // Habilita los botones cuando se selecciona una fila de la tabla 
    {   vista.btnActualizar.setEnabled(true);
        vista.btnAgregar.setEnabled(true);
        vista.btnEliminar.setEnabled(true);
        vista.btnCancelar.setEnabled(true);
    }
    public void deshabilitar() // Se deshabilitan los botones cuando recien se crea el objeto o no tiene seleccionado una fila de la tabla 
    {   vista.btnActualizar.setEnabled(false);
        vista.btnEliminar.setEnabled(false);
    }
    public void iniciarVista() // Muestra la vista 
    {   vista.tbl_STuristicos.setModel(modelo.administradorConsultar()); 
        ocultaColumna(); 
        vista.setVisible(true); 
    }
    public String validacionCamposVacios() // Valida que los campos no esten vacios
    {   if(vista.txtTipo.getText().isEmpty() || vista.taDescripcion.getText().isEmpty())
            return "Favor de llenar todos los campos"; 
        else 
            return null;
    }
    public void ocultaColumna() // Oculta la primer columna de la tabla 
    {   this.vista.tbl_STuristicos.getColumnModel().getColumn(0).setMaxWidth(0);
        this.vista.tbl_STuristicos.getColumnModel().getColumn(0).setMinWidth(0);
        this.vista.tbl_STuristicos.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
        this.vista.tbl_STuristicos.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
        this.vista.tbl_STuristicos.getColumnModel().getColumn(0).setPreferredWidth(0);
    }
    @Override
    public void actionPerformed(ActionEvent evento) 
    {   if(vista.btnAgregar == evento.getSource()) 
        {   // Se insertan los datos en la base de datos
            if(validacionCamposVacios()==null)
            {   if(modelo.administradorInsertarS(vista.txtTipo.getText(), vista.taDescripcion.getText()))
                {   JOptionPane.showMessageDialog(null, "Registro agregado exitosamente");
                    this.vista.tbl_STuristicos.setModel(modelo.administradorConsultar());
                    ocultaColumna(); 
                    limpiar(); 
                }else 
                    JOptionPane.showMessageDialog(null, "Error al insertar los datos");
            }else 
            {   JOptionPane.showMessageDialog(null, ""+validacionCamposVacios());
            } 
        }
        else if(vista.btnEliminar == evento.getSource()) 
            {   // Se envian los datos para eliminar el registro en la base de datos
                if (JOptionPane.showConfirmDialog(vista,
                    "¿Estás seguro que deseas eliminar este registro?", "Fleetock",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                    // Para saber si este registro esta relacionado con otra tabla, si es asi mandar un mensaje 
                    int pregunta = modelo.administradorConsultaParaEliminarS(Integer.parseInt(vista.txtId.getText())); // Para consultar que ese registro no tenga datos relacioandos con otra tabla
                    if( pregunta > 0)
                        JOptionPane.showMessageDialog(null, "No se puede eliminar, este registro se encuentra en otra tabla");
                    else if (pregunta == -1)
                         JOptionPane.showMessageDialog(null, "Error al eliminar los datos");
                    else if(modelo.administradorEliminarS(Integer.parseInt(vista.txtId.getText())))
                        {   JOptionPane.showMessageDialog(null, "Registro eliminado exitosamente");
                            this.vista.tbl_STuristicos.setModel(modelo.administradorConsultar());
                            ocultaColumna(); 
                            limpiar(); 
                        }
                        else 
                            JOptionPane.showMessageDialog(null, "Error al eliminar los datos");
                }
            }
            else if(vista.btnActualizar == evento.getSource())
                {   // Se envian los datos para actualizar un registro en la base de datos
                    if(validacionCamposVacios()==null)
                    {   if(modelo.administradorActualizarS(Integer.parseInt(vista.txtId.getText()),vista.txtTipo.getText(), vista.taDescripcion.getText()))
                        {   JOptionPane.showMessageDialog(null, "Registro actualizado exitosamente");
                            this.vista.tbl_STuristicos.setModel(modelo.administradorConsultar());
                            ocultaColumna(); 
                            limpiar(); 
                        }else 
                            JOptionPane.showMessageDialog(null, "Error al actualizar los datos");
                    }else 
                    {   JOptionPane.showMessageDialog(null, ""+validacionCamposVacios());
                    } 
                }
                else if(vista.btnCancelar == evento.getSource()) 
                    {   // Limpia los cuadros de texto y deshabilita los botones cuando cancela
                        this.limpiar();
                        this.deshabilitar();
                    }
                    else if(vista.btnReporte == evento.getSource()) 
                    {   try
                        {   try
                            {   Conexion conexion = new Conexion(); 
                                Connection con = conexion.abrirConexion();
                                if( con != null)
                                {   JasperReport reporte= null; 
                                    String path="src\\reportes\\reporteTipoSitio.jasper"; 
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
    public void mouseClicked(MouseEvent e) // Carga los elementos de la fila que selecciono a los textfield
    {   if(vista.tbl_STuristicos == e.getSource())
        {   int fila = vista.tbl_STuristicos.rowAtPoint(e.getPoint()); 
            if( fila > -1)
            {   vista.txtId.setText(String.valueOf(vista.tbl_STuristicos.getValueAt(fila, 0)));
                vista.txtTipo.setText(String.valueOf(vista.tbl_STuristicos.getValueAt(fila, 1)));
                vista.taDescripcion.setText(String.valueOf(vista.tbl_STuristicos.getValueAt(fila, 2)));
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
    // Metodos de entrada de datos 
    @Override
    public void keyTyped(KeyEvent ke) {}
    @Override
    public void keyPressed(KeyEvent ke) {}
    @Override
    public void keyReleased(KeyEvent ke) 
    {   // Buscador en tiempo real (busca lo que se esta tecleando) 
        vista.tbl_STuristicos.setModel(modelo.Buscador(String.valueOf(vista.txt_Buscar.getText())));
        ocultaColumna(); 
    }
}