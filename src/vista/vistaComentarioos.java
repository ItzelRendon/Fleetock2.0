/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

/**
 *
 * @author alfredo
 */
public class vistaComentarioos extends javax.swing.JPanel {

    /**
     * Creates new form Comentarioos
     */
    public vistaComentarioos() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel5 = new javax.swing.JPanel();
        scroll_comentarios = new javax.swing.JScrollPane();
        panle_comentarios = new javax.swing.JPanel();

        jPanel5.setLayout(null);

        javax.swing.GroupLayout panle_comentariosLayout = new javax.swing.GroupLayout(panle_comentarios);
        panle_comentarios.setLayout(panle_comentariosLayout);
        panle_comentariosLayout.setHorizontalGroup(
            panle_comentariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 498, Short.MAX_VALUE)
        );
        panle_comentariosLayout.setVerticalGroup(
            panle_comentariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 528, Short.MAX_VALUE)
        );

        scroll_comentarios.setViewportView(panle_comentarios);

        jPanel5.add(scroll_comentarios);
        scroll_comentarios.setBounds(0, 0, 500, 530);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 530, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel5;
    public javax.swing.JPanel panle_comentarios;
    private javax.swing.JScrollPane scroll_comentarios;
    // End of variables declaration//GEN-END:variables
}
