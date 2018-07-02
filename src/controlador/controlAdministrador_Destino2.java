package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import modelo.modeloAdministrador_Destino;
import modelo.modeloAdministrador_Destino2;
import vista.AdministradorMenu;
import vista.Administrador_Destino2;
import vista.Administrador_Destino;
public class controlAdministrador_Destino2 implements ActionListener, MouseListener{
    private modeloAdministrador_Destino2 modelo;
    private Administrador_Destino2 vista;
    String destinoId;
    
     public controlAdministrador_Destino2(modeloAdministrador_Destino2 modelo, Administrador_Destino2 vista, String destinoId)
    {
        this.modelo = modelo; 
        this.vista = vista;
        this.vista.tbl_Actividad.addMouseListener(this);
        this.vista.tbl_Transportes.addMouseListener(this);
        this.vista.tbl_TipoSitio.addMouseListener(this);
        this.vista.tbl_Comentarios.addMouseListener(this);
        this.vista.btn_AgregarActividades.addActionListener(this);
        this.vista.btn_AgregarTransportes.addActionListener(this);
        this.vista.btn_AgregarTipoSitio.addActionListener(this);
        this.vista.txt_destinoId.setText(destinoId);
        this.vista.btn_Salir.addActionListener(this);
    }
     
     //Limpis los JTextField
     public void Limpiar(){
        vista.txt_destinoId.setText("");
        vista.txt_IdActividad.setText("");
        vista.txt_Localizacion.setText("");
        vista.txt_IdTransporte.setText("");
        vista.txt_Foto.setText("");
        vista.txt_TipoSitioId.setText("");
     }
     
     public void iniciarVista(){
         vista.setTitle("Destino");
         vista.setLocationRelativeTo(null);
         vista.setIconImage(new ImageIcon(getClass().getResource("../imagenes/logo.png")).getImage());
         vista.tbl_Actividad.setModel(modelo.Actividad());
         vista.tbl_TipoSitio.setModel(modelo.TipoSitio());
         vista.tbl_Transportes.setModel(modelo.Transporte());
         vista.tbl_Comentarios.setModel(modelo.Comentarios(vista.txt_destinoId.getText()));
         vista.setVisible(true);
     }
     
     //Habilita botones
     public void habilitar(){
         vista.btn_AgregarActividades.setEnabled(true);
         vista.btn_AgregarTipoSitio.setEnabled(true);
         vista.btn_AgregarTransportes.setEnabled(true);
     }
     
     //Desabilita botones
     public void desabilitar(){
         vista.btn_AgregarActividades.setEnabled(false);
         vista.btn_AgregarTipoSitio.setEnabled(false);
         vista.btn_AgregarTransportes.setEnabled(false);
     }
     
     public void actionPerformed(ActionEvent evento){
         //Agrega datos a las tablas de muchos a muchos
         //Agrega Transportes a los Destinos
         if(vista.btn_AgregarTransportes == evento.getSource()) {
             if(modelo.InsertarTransporte(Integer.parseInt(vista.txt_destinoId.getText()), (Integer.parseInt(vista.txt_IdTransporte.getText())))){
                JOptionPane.showMessageDialog(null, "Se agrego exitosamente");
                Limpiar();
               }
             }
         //Agrega Actividades a los Destinos
         if(vista.btn_AgregarActividades == evento.getSource()) {
             if(modelo.InsertarActividad(Integer.parseInt(vista.txt_IdActividad.getText()), (Integer.parseInt(vista.txt_destinoId.getText())), vista.txt_Foto.getText(), vista.txt_Localizacion.getText())){
                JOptionPane.showMessageDialog(null, "Se agrego exitosamente");
                Limpiar();
               }
             }
         //Agrega Tipo de Sitio a los Destinos
         if(vista.btn_AgregarTipoSitio == evento.getSource()) {
             if(modelo.InsertarTipoSitio(Integer.parseInt(vista.txt_TipoSitioId.getText()), (Integer.parseInt(vista.txt_destinoId.getText())))){
                JOptionPane.showMessageDialog(null, "Se agrego exitosamente");
                Limpiar();
               }
             }
         //Cierra el JFrame para agregar los anteriores
         if(vista.btn_Salir == evento.getSource()) {
             //Mensaje
             if (JOptionPane.showConfirmDialog(vista,
                    "¿Estás seguro que deseas Salir?", "Fleetock",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                        //Cierra el JFrame
                        vista.dispose();
                        vista.setVisible(false);
                        Limpiar();
                }
            }
     }
     
     public void mouseClicked(MouseEvent e){
         habilitar();
         //Tabla Actividad
         if(vista.tbl_Actividad == e.getSource()){
             habilitar();
             int fila = vista.tbl_Actividad.rowAtPoint(e.getPoint());
             if(fila > -1){
                 vista.txt_IdActividad.setText(String.valueOf(vista.tbl_Actividad.getValueAt(fila, 0)));
             }
           }
         //Tabla Transportes
         if(vista.tbl_Transportes == e.getSource()){
             habilitar();
             int fila = vista.tbl_Transportes.rowAtPoint(e.getPoint());
             if(fila > -1){
                 vista.txt_IdTransporte.setText(String.valueOf(vista.tbl_Transportes.getValueAt(fila, 0)));
             }
           }
         //Tabla Tipo de Sitio
         if(vista.tbl_TipoSitio == e.getSource()){
             habilitar();
             int fila = vista.tbl_TipoSitio.rowAtPoint(e.getPoint());
             if(fila > -1){
                 vista.txt_TipoSitioId.setText(String.valueOf(vista.tbl_TipoSitio.getValueAt(fila, 0)));
             }
           }
        }
     
    @Override
    public void mousePressed(MouseEvent e){
         
     }
     
    @Override
    public void mouseReleased(MouseEvent e){
         
     }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}