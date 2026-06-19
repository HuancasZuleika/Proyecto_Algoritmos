package matematica;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Image;
import formularios.inicio;

public class formularios_Temas extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(formularios_Temas.class.getName());

    /**
     * Creates new form formularios_Temas
     */
    public formularios_Temas() {
        initComponents();
        // === BOTÓN PITÁGORAS ===
        String rutaPitagoras = "/imagen/pitagoras2.0.png";
        btnPitagoras.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                aplicarEfectoZoom(btnPitagoras, rutaPitagoras, 70, 330, 240, 180, true);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                aplicarEfectoZoom(btnPitagoras, rutaPitagoras, 70, 330, 240, 180, false);
            }
        });

        // === BOTÓN CONJUNTOS ===
        String rutaConjuntos = "/imagen/Conjuntos2.0.png";
        btnConjuntos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                aplicarEfectoZoom(btnConjuntos, rutaConjuntos, 330, 420, 230, 210, true);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                aplicarEfectoZoom(btnConjuntos, rutaConjuntos, 330, 420, 230, 210, false);
            }
        });

        // === BOTÓN OPERACIONES ===
        String rutaOperaciones = "/imagen/Operacion2.1.png"; // O la nueva que uses en PNG
        btnOperaciones.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                aplicarEfectoZoom(btnOperaciones, rutaOperaciones, 580, 310, 250, 210, true);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                aplicarEfectoZoom(btnOperaciones, rutaOperaciones, 580, 310, 250, 210, false);
            }
        });

        // === BOTÓN ÁREA DE FIGURAS ===
        String rutaArea = "/imagen/areaFig..png";
        btnArea.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                aplicarEfectoZoom(btnArea, rutaArea, 860, 420, 280, 230, true);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                aplicarEfectoZoom(btnArea, rutaArea, 860, 420, 280, 230, false);
            }
        });
    }

    public void setAjustarImagenALabel(JLabel label, String rutaImagen) {
        ImageIcon originalIcon = new ImageIcon(getClass().getResource(rutaImagen));
        Image originalImage = originalIcon.getImage();

        int labelWidth = label.getWidth();
        int labelHeight = label.getHeight();

        Image scaledImage = originalImage.getScaledInstance(labelWidth, labelHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        label.setIcon(scaledIcon);
    }

    private void aplicarEfectoZoom(javax.swing.JButton boton, String rutaImagen, int x, int y, int anchoContenedor, int altoContenedor, boolean expandir) {
        try {
            java.net.URL imgURL = getClass().getResource(rutaImagen);
            if (imgURL == null) {
                return;
            }

            javax.swing.ImageIcon originalIcon = new javax.swing.ImageIcon(imgURL);
            java.awt.Image imgOriginal = originalIcon.getImage();

            // 1. Calcular proporciones para que NO se deforme la nueva imagen
            int imgAncho = imgOriginal.getWidth(null);
            int imgAlto = imgOriginal.getHeight(null);

            // Dejamos un pequeño espacio libre (30px) por si el botón tiene texto abajo
            int espacioTexto = 30;
            int maxAncho = anchoContenedor;
            int maxAlto = altoContenedor - espacioTexto;

            double escalaX = (double) maxAncho / imgAncho;
            double escalaY = (double) maxAlto / imgAlto;
            double escalaProporcional = Math.min(escalaX, escalaY);

            // Dimensiones base de la imagen ajustada
            int anchoFinalBase = (int) (imgAncho * escalaProporcional);
            int altoFinalBase = (int) (imgAlto * escalaProporcional);

            // 2. Si el mouse está encima, aplicamos el Zoom (15% más grande)
            if (expandir) {
                int anchoZoom = (int) (anchoFinalBase * 1.15);
                int altoZoom = (int) (altoFinalBase * 1.15);

                // Calculamos la posición para que crezca simétricamente desde el centro
                int nuevoX = x - (anchoZoom - anchoFinalBase) / 2;
                int nuevoY = y - (altoZoom - altoFinalBase) / 2;

                boton.setBounds(nuevoX, nuevoY, anchoZoom, altoZoom + espacioTexto);

                java.awt.Image scaledImage = imgOriginal.getScaledInstance(anchoZoom, altoZoom, java.awt.Image.SCALE_SMOOTH);
                boton.setIcon(new javax.swing.ImageIcon(scaledImage));
                boton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            } else {
                // Regresa a su estado y tamaño original centrado
                int nuevoX = x + (anchoContenedor - anchoFinalBase) / 2;
                int nuevoY = y + (altoContenedor - (altoFinalBase + espacioTexto)) / 2;

                boton.setBounds(nuevoX, nuevoY, anchoFinalBase, altoFinalBase + espacioTexto);

                java.awt.Image scaledImage = imgOriginal.getScaledInstance(anchoFinalBase, altoFinalBase, java.awt.Image.SCALE_SMOOTH);
                boton.setIcon(new javax.swing.ImageIcon(scaledImage));
            }

        } catch (Exception e) {
            logger.log(java.util.logging.Level.SEVERE, "Error al ajustar la nueva imagen", e);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnPitagoras = new javax.swing.JButton();
        btnConjuntos = new javax.swing.JButton();
        btnOperaciones = new javax.swing.JButton();
        btnVolver = new javax.swing.JButton();
        btnArea = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnPitagoras.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 18)); // NOI18N
        btnPitagoras.setForeground(new java.awt.Color(255, 255, 255));
        btnPitagoras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/pitagoras2.0.png"))); // NOI18N
        btnPitagoras.setText("Teorema de Pitágoras");
        btnPitagoras.setBorderPainted(false);
        btnPitagoras.setContentAreaFilled(false);
        btnPitagoras.setFocusPainted(false);
        btnPitagoras.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnPitagoras.setMargin(new java.awt.Insets(0, 0, 10, 0));
        btnPitagoras.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnPitagoras.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnPitagoras.addActionListener(this::btnPitagorasActionPerformed);
        getContentPane().add(btnPitagoras, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 310, 250, 230));

        btnConjuntos.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 18)); // NOI18N
        btnConjuntos.setForeground(new java.awt.Color(255, 255, 255));
        btnConjuntos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/Conjuntos2.0.png"))); // NOI18N
        btnConjuntos.setText("Teoria de Conjuntos \n\n");
        btnConjuntos.setBorderPainted(false);
        btnConjuntos.setContentAreaFilled(false);
        btnConjuntos.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnConjuntos.setMargin(new java.awt.Insets(0, 0, 10, 0));
        btnConjuntos.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnConjuntos.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnConjuntos.addActionListener(this::btnConjuntosActionPerformed);
        getContentPane().add(btnConjuntos, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 370, 240, 240));

        btnOperaciones.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 18)); // NOI18N
        btnOperaciones.setForeground(new java.awt.Color(255, 255, 255));
        btnOperaciones.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/Operacion2.1.png"))); // NOI18N
        btnOperaciones.setText("Operaciones Combinadas\n");
        btnOperaciones.setBorderPainted(false);
        btnOperaciones.setContentAreaFilled(false);
        btnOperaciones.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnOperaciones.setMargin(new java.awt.Insets(0, 0, 10, 0));
        btnOperaciones.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnOperaciones.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnOperaciones.addActionListener(this::btnOperacionesActionPerformed);
        getContentPane().add(btnOperaciones, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 320, 260, 240));

        btnVolver.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/btnatras (1).png"))); // NOI18N
        btnVolver.setContentAreaFilled(false);
        btnVolver.addActionListener(this::btnVolverActionPerformed);
        getContentPane().add(btnVolver, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 90, 90, 90));

        btnArea.setFont(new java.awt.Font("Arial Rounded MT Bold", 1, 18)); // NOI18N
        btnArea.setForeground(new java.awt.Color(255, 255, 255));
        btnArea.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/areaFig..png"))); // NOI18N
        btnArea.setText("Area de Figuras  Planas");
        btnArea.setBorderPainted(false);
        btnArea.setContentAreaFilled(false);
        btnArea.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnArea.setMargin(new java.awt.Insets(0, 0, 10, 0));
        btnArea.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnArea.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnArea.addActionListener(this::btnAreaActionPerformed);
        getContentPane().add(btnArea, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 360, 260, 260));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/fondoMat.png"))); // NOI18N
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 1160, 640));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnPitagorasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPitagorasActionPerformed

        TeoriaPitagoras pantallaTeoria = new TeoriaPitagoras();
        pantallaTeoria.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnPitagorasActionPerformed

    private void btnConjuntosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConjuntosActionPerformed
        TeoriaConjuntos pantallaTeoria = new TeoriaConjuntos();
        pantallaTeoria.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnConjuntosActionPerformed

    private void btnOperacionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOperacionesActionPerformed

        TeoriaOperaciones pantalla = new TeoriaOperaciones();
        pantalla.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnOperacionesActionPerformed

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        // TODO add your handling code here:
        inicio pantallaInicio = new inicio();
        pantallaInicio.setVisible(true);

        // Cerramos la ventana actual de temas
        this.dispose();

    }//GEN-LAST:event_btnVolverActionPerformed

    private void btnAreaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAreaActionPerformed
        // TODO add your handling code here:
        TeoriaArea pantallaTeoria = new TeoriaArea();
        pantallaTeoria.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnAreaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new formularios_Temas().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnArea;
    private javax.swing.JButton btnConjuntos;
    private javax.swing.JButton btnOperaciones;
    private javax.swing.JButton btnPitagoras;
    private javax.swing.JButton btnVolver;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
