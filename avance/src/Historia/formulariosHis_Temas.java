
package Historia;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Image;
import formularios.inicio;
import matematica.TeoriaPitagoras;

public class formulariosHis_Temas extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(formulariosHis_Temas.class.getName());

    /**
     * Creates new form formulariosHis_Temas
     */
    public formulariosHis_Temas() {
        initComponents();
        // === BOTÓN PREINCAS ===
        String rutaPreincas = "/imagen/Preincas2.0.png";
        btnPreincas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                aplicarEfectoZoom(btnPreincas, rutaPreincas, 70, 330, 240, 180, true);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                aplicarEfectoZoom(btnPreincas, rutaPreincas, 70, 330, 240, 180, false);
            }
        });

        // === BOTÓN INDEPENDENCIA ===
        String rutaIndependencia = "/imagen/Independ.png";
        btnIndepen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                aplicarEfectoZoom(btnIndepen, rutaIndependencia, 330, 420, 230, 210, true);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                aplicarEfectoZoom(btnIndepen, rutaIndependencia, 330, 420, 230, 210, false);
            }
        });

        // === BOTÓN CONQUISTA ===
        String rutaConquista = "/imagen/conquista.png"; // O la nueva que uses en PNG
        btnConquista.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                aplicarEfectoZoom(btnConquista, rutaConquista, 580, 310, 250, 210, true);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                aplicarEfectoZoom(btnConquista, rutaConquista, 580, 310, 250, 210, false);
            }
        });

        // === BOTÓN INCAS ===
        String rutaImperio = "/imagen/Incas2.0.png";
        btnImperio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                aplicarEfectoZoom(btnImperio, rutaImperio , 860, 420, 280, 230, true);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                aplicarEfectoZoom(btnImperio, rutaImperio , 860, 420, 280, 230, false);
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

        btnAtras = new javax.swing.JButton();
        btnPreincas = new javax.swing.JButton();
        btnImperio = new javax.swing.JButton();
        btnConquista = new javax.swing.JButton();
        btnIndepen = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnAtras.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/btnatras (1).png"))); // NOI18N
        btnAtras.setBorderPainted(false);
        btnAtras.setContentAreaFilled(false);
        btnAtras.addActionListener(this::btnAtrasActionPerformed);
        getContentPane().add(btnAtras, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 30, 120, 90));

        btnPreincas.setFont(new java.awt.Font("Berlin Sans FB Demi", 1, 24)); // NOI18N
        btnPreincas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/Preincas2.0.png"))); // NOI18N
        btnPreincas.setText("Culturas Preincas");
        btnPreincas.setBorder(javax.swing.BorderFactory.createTitledBorder("Culturas Preincas "));
        btnPreincas.setBorderPainted(false);
        btnPreincas.setContentAreaFilled(false);
        btnPreincas.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnPreincas.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnPreincas.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnPreincas.addActionListener(this::btnPreincasActionPerformed);
        getContentPane().add(btnPreincas, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 220, 290, 240));

        btnImperio.setFont(new java.awt.Font("Bodoni MT Black", 1, 24)); // NOI18N
        btnImperio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/Incas2.0.png"))); // NOI18N
        btnImperio.setText("Imperio Inca\n");
        btnImperio.setBorderPainted(false);
        btnImperio.setContentAreaFilled(false);
        btnImperio.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnImperio.setInheritsPopupMenu(true);
        btnImperio.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnImperio.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnImperio.addActionListener(this::btnImperioActionPerformed);
        getContentPane().add(btnImperio, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 350, 260, 220));

        btnConquista.setFont(new java.awt.Font("Bodoni MT Black", 1, 24)); // NOI18N
        btnConquista.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/conquista.png"))); // NOI18N
        btnConquista.setText("Conquista de Perú");
        btnConquista.setBorderPainted(false);
        btnConquista.setContentAreaFilled(false);
        btnConquista.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnConquista.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnConquista.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnConquista.addActionListener(this::btnConquistaActionPerformed);
        getContentPane().add(btnConquista, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 220, 290, 210));

        btnIndepen.setFont(new java.awt.Font("Bodoni MT Black", 1, 24)); // NOI18N
        btnIndepen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/Independ.png"))); // NOI18N
        btnIndepen.setText("Independencia del Perú\n");
        btnIndepen.setBorderPainted(false);
        btnIndepen.setContentAreaFilled(false);
        btnIndepen.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnIndepen.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnIndepen.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnIndepen.addActionListener(this::btnIndepenActionPerformed);
        getContentPane().add(btnIndepen, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 330, 360, 230));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/fondoMundoHist.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1170, 660));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnImperioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImperioActionPerformed
        // TODO add your handling code here:
        TeoriaImperio pantallaTeoria = new TeoriaImperio();
        pantallaTeoria.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnImperioActionPerformed

    private void btnPreincasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreincasActionPerformed
        // TODO add your handling code here:
        TeoriaPreinca pantallaTeoria = new TeoriaPreinca();
        pantallaTeoria.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnPreincasActionPerformed

    private void btnIndepenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIndepenActionPerformed
        // TODO add your handling code here:
        TeoriaIndependencia pantallaTeoria = new TeoriaIndependencia();
        pantallaTeoria.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnIndepenActionPerformed

    private void btnConquistaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConquistaActionPerformed
        // TODO add your handling code here:
        TeoriaConquista pantallaTeoria = new TeoriaConquista();
        pantallaTeoria.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnConquistaActionPerformed

    private void btnAtrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtrasActionPerformed
        // TODO add your handling code here:
        inicio pantallaInicio = new inicio();
        pantallaInicio.setVisible(true);

        // Cerramos la ventana actual de temas
        this.dispose();
    }//GEN-LAST:event_btnAtrasActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new formulariosHis_Temas().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAtras;
    private javax.swing.JButton btnConquista;
    private javax.swing.JButton btnImperio;
    private javax.swing.JButton btnIndepen;
    private javax.swing.JButton btnPreincas;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
