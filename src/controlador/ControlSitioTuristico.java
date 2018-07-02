/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;
import modelo.ModeloSitioTuristico; 
import java.awt.event.ActionEvent; 
import java.awt.event.ActionListener; 
import java.awt.event.MouseEvent; 
import java.awt.event.MouseListener; 
import javax.swing.JFrame; 
import javax.swing.JOptionPane;
import vista.PTipoSitioTuristico;
/**
 *
 * @author Fabiola Paez
 */
public class ControlSitioTuristico implements ActionListener, MouseListener{
    private ModeloSitioTuristico modelo; 
    private PTipoSitioTuristico vista; 
    
    public ControlSitioTuristico(ModeloSitioTuristico  modelo, PTipoSitioTuristico vista)
    {
        this.modelo= modelo; 
        this.vista= vista; 
        this.vista.btnAgregar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);
        this.vista.btnActualizar.addActionListener(this);
        this.vista.tbl_STuristicos.addMouseListener(this);
        this.vista.btn_Buscar.addActionListener(this);
        this.vista.btnCancelar.addActionListener(this);
        desabilitar(); 
    }
    public void limpiar()
    {
        vista.txtId.setText(""); 
        vista.taDescripcion.setText("");
        vista.txtTipo.setText("");
        desabilitar();
    }
    public void habilitar()
    {
        vista.btnActualizar.setEnabled(true);
        vista.btnAgregar.setEnabled(true);
        vista.btnEliminar.setEnabled(true);
        vista.btnCancelar.setEnabled(true);
    }
    public void desabilitar()
    {
        vista.btnActualizar.setEnabled(false);
        vista.btnEliminar.setEnabled(false);
    }
    public void iniciarVista()
    {
        vista.tbl_STuristicos.setModel(modelo.administradorConsultar()); 
        ocultaColumna(); 
        vista.setVisible(true); 
    }
      public String validacionCamposVacios()
    {
        if(vista.txtTipo.getText().isEmpty() || vista.taDescripcion.getText().isEmpty())
            return "Favor de llenar todos los campos"; 
        else return null;
    }
    public void ocultaColumna()
    {
        this.vista.tbl_STuristicos.getColumnModel().getColumn(0).setMaxWidth(0);
        this.vista.tbl_STuristicos.getColumnModel().getColumn(0).setMinWidth(0);
        this.vista.tbl_STuristicos.getTableHeader().getColumnModel().getColumn(0).setMaxWidth(0);
        this.vista.tbl_STuristicos.getTableHeader().getColumnModel().getColumn(0).setMinWidth(0);
        this.vista.tbl_STuristicos.getColumnModel().getColumn(0).setPreferredWidth(0);
    }
    @Override
    public void actionPerformed(ActionEvent evento) {
        if(vista.btnAgregar == evento.getSource())
        {
            if(validacionCamposVacios()==null)
            { 
                if(modelo.administradorInsertarS(vista.txtTipo.getText(), vista.taDescripcion.getText()))
                {
                    JOptionPane.showMessageDialog(null, "Registro agregado exitosamente");
                    this.vista.tbl_STuristicos.setModel(modelo.administradorConsultar());
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
                if(modelo.administradorEliminarS(Integer.parseInt(vista.txtId.getText())))
                {
                    JOptionPane.showMessageDialog(null, "Registro eliminado exitosamente");
                    this.vista.tbl_STuristicos.setModel(modelo.administradorConsultar());
                    ocultaColumna(); 
                    limpiar(); 
                }else 
                    JOptionPane.showMessageDialog(null, "Error al eliminar los datos");
            }
            else if(vista.btnActualizar == evento.getSource())
                {
                    if(validacionCamposVacios()==null)
                    {
                        if(modelo.administradorActualizarS(Integer.parseInt(vista.txtId.getText()),vista.txtTipo.getText(), vista.taDescripcion.getText()))
                        {
                            JOptionPane.showMessageDialog(null, "Registro actualizado exitosamente");
                            this.vista.tbl_STuristicos.setModel(modelo.administradorConsultar());
                            ocultaColumna(); 
                            limpiar(); 
                        }else 
                            JOptionPane.showMessageDialog(null, "Error al actualizar los datos");
                    }else 
                    {
                       JOptionPane.showMessageDialog(null, ""+validacionCamposVacios());
                    } 
                }
                else if(vista.btn_Buscar == evento.getSource()) 
                    {  
                        this.vista.tbl_STuristicos.setModel(modelo.Buscador(vista.txt_Buscar.getText()));
                        ocultaColumna(); 
                        vista.txt_Buscar.setText("");
                    }
                    else if(vista.btnCancelar == evento.getSource()) 
                        {  
                            this.limpiar();
                            this.desabilitar();
                        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(vista.tbl_STuristicos == e.getSource())
       {    int fila = vista.tbl_STuristicos.rowAtPoint(e.getPoint()); 
            if( fila > -1)
            {   vista.txtId.setText(String.valueOf(vista.tbl_STuristicos.getValueAt(fila, 0)));
                vista.txtTipo.setText(String.valueOf(vista.tbl_STuristicos.getValueAt(fila, 1)));
                vista.taDescripcion.setText(String.valueOf(vista.tbl_STuristicos.getValueAt(fila, 2)));
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
    
}
