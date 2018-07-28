package controlador;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
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
    private AdministradorMenu menu;
    int destinoId;
    int tipoSitioId;
    int actividadId;
    String foto;
    String localizacion;
    int transporteId;
    
     public controlAdministrador_Destino2(modeloAdministrador_Destino2 modelo, Administrador_Destino2 vista, int id, AdministradorMenu menu)
    {
        this.modelo = modelo; 
        this.vista = vista;
        this.menu = menu;
        destinoId=id;
        this.vista.tbl_Transportes.addMouseListener(this);
        this.vista.tbl_TipoSitio.addMouseListener(this);
        this.vista.btn_Aceptar.addActionListener(this);
    }
     
     public void iniciarVista(){
         vista.setVisible(true);
         vista.tbl_TipoSitio.setModel(modelo.TipoSitio());
         vista.tbl_Transportes.setModel(modelo.Transporte());
         if(modelo.ConsultarActividades().length > 0)
         {    
            String [] Actividades = modelo.ConsultarActividades();
            for(int i=0; i<Actividades.length; i++)
            {
                vista.jcb_Actividad.addItem(Actividades[i]);
            }
         }
     }
     
    public void actionPerformed(ActionEvent evento){
        //Agrega datos a las tablas de muchos a muchos
        if(vista.btn_Aceptar == evento.getSource()) 
        {
            modelo.InsertarTipoSitio(tipoSitioId, destinoId);
            modelo.InsertarTransporte(destinoId, transporteId);
        }
        //Agrega Transportes a los Destinos
//        if(vista.btn_AgregarTransportes == evento.getSource()) {
//            if(modelo.InsertarTransporte(Integer.parseInt(vista.txt_destinoId.getText()), (Integer.parseInt(vista.txt_IdTransporte.getText())))){
//               JOptionPane.showMessageDialog(null, "Se agrego exitosamente");
//              }
//            }
         //Cierra el JFrame para agregar los anteriores
        if(vista.btn_Aceptar == evento.getSource()) {
            //Limpia el panel actual
            this.vista.panelDestino.removeAll();
            this.vista.panelDestino.revalidate();
            this.vista.panelDestino.repaint();
            //Crea objetos del siguiente panel
            Administrador_Destino panel = new  Administrador_Destino();
            modeloAdministrador_Destino modelo = new modeloAdministrador_Destino(); 
            controlAdministrador_Destino control = new controlAdministrador_Destino(modelo, panel, menu); 
            //Lo añade al panel
            this.vista.panelDestino.add(panel);
            this.vista.panelDestino.revalidate();
            this.vista.panelDestino.repaint();
            //Y lo muestra.
            control.iniciarVista();
            }
     }
     
     public void mouseClicked(MouseEvent e){
        //Tabla Transportes
         if(vista.tbl_Transportes == e.getSource()){
             int fila = vista.tbl_Transportes.rowAtPoint(e.getPoint());
             if(fila > -1){
                 transporteId=Integer.parseInt(String.valueOf(vista.tbl_Transportes.getValueAt(fila, 0)));
             }
           }
        //Tabla Tipo de Sitio
         if(vista.tbl_TipoSitio == e.getSource()){
             int fila = vista.tbl_TipoSitio.rowAtPoint(e.getPoint());
             if(fila > -1){
                 tipoSitioId=Integer.parseInt(String.valueOf(vista.tbl_TipoSitio.getValueAt(fila, 0)));
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