/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package matematica;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import matematica.Matematicas;

public class TeoriaPitagoras extends javax.swing.JFrame {
    
    Matematicas logicaMat = new Matematicas(); // Tu clase con el método de audio
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(TeoriaPitagoras.class.getName());

    /**
     * Creates new form TeoriaPitagoras
     */
    public TeoriaPitagoras() {
        initComponents();
        this.setLocationRelativeTo(null);
        this.addWindowListener(new java.awt.event.WindowAdapter() {
        @Override
        public void windowClosing(java.awt.event.WindowEvent e) {
            logicaMat.detenerAudio();
        }
    }); 
        // Inicializar los efectos de movimiento y zoom interactivo
       configurarMundoInteractivo();
    }

    private void configurarMundoInteractivo() {
        // 1. Animación del Personaje Triángulo (jLabel1) sin errores de sintaxis
        jLabel1.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                // Sube 15 píxeles (De Y=190 a Y=175)
                jLabel1.setBounds(190, 175, 330, 320); //
                jLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR)); // Paréntesis corregido aquí
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                // Regresa a su posición original (Y=190)
                jLabel1.setBounds(190, 190, 330, 320); //
            }
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                // Si le dan clic, reproduce el audio explicativo
                btnAudioActionPerformed(null); //
            }
        });
        
        // 2. Efecto Zoom: Botón de navegación atrás
        btnVolver.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ejecutarZoomBoton(btnVolver, "/imagen/btnatras (1).png", 110, 310, 90, 90, true);
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ejecutarZoomBoton(btnVolver, "/imagen/btnatras (1).png", 110, 310, 90, 90, false);
            }
        });
   
        // 3. Efecto Zoom: Botón de navegación adelante
        btnSiguiente.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ejecutarZoomBoton(btnSiguiente, "/imagen/btnAdelante.png", 1020, 320, 90, 80, true);
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ejecutarZoomBoton(btnSiguiente, "/imagen/btnAdelante.png", 1020, 320, 90, 80, false);
            }
        });
    }
    

    // Lógica matemática para redimensionar los botones fluidamente sin perder resolución
    private void ejecutarZoomBoton(JButton boton, String ruta, int x, int y, int wContenedor, int hContenedor, boolean escalar) {
        try {
            java.net.URL clipURL = getClass().getResource(ruta);
            if (clipURL == null) return;

            ImageIcon iconoOriginal = new ImageIcon(clipURL);
            Image img = iconoOriginal.getImage();

            int anchoImg = img.getWidth(null);
            int altoImg = img.getHeight(null);
            
            double ratioX = (double) wContenedor / anchoImg;
            double ratioY = (double) hContenedor / altoImg;
            double ratioProporcional = Math.min(ratioX, ratioY);

            int baseW = (int) (anchoImg * ratioProporcional);
            int baseH = (int) (altoImg * ratioProporcional);

            if (escalar) {
                int zoomW = (int) (baseW * 1.15);
                int zoomH = (int) (baseH * 1.15);
                int centroX = x - (zoomW - baseW) / 2;
                int centroY = y - (zoomH - baseH) / 2;

                boton.setBounds(centroX, centroY, zoomW, zoomH);
                boton.setIcon(new ImageIcon(img.getScaledInstance(zoomW, zoomH, Image.SCALE_SMOOTH)));
                boton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            } else {
                int centroX = x + (wContenedor - baseW) / 2;
                int centroY = y + (hContenedor - baseH) / 2;

                boton.setBounds(centroX, centroY, baseW, baseH);
                boton.setIcon(new ImageIcon(img.getScaledInstance(baseW, baseH, Image.SCALE_SMOOTH)));
            }
        } catch (Exception e) {
            logger.log(java.util.logging.Level.SEVERE, "Error controlando la escala del botón", e);
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

        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        btnAudio = new javax.swing.JButton();
        btnVolver = new javax.swing.JButton();
        btnSiguiente = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("¡Aprendamos el Teorema de Pitágoras!");
        setBackground(new java.awt.Color(255, 220, 220));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/triangulo_explorador.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 180, 330, 320));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/pizarra_pitagoras.png"))); // NOI18N
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 170, 330, 220));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/rampa_cubos.png"))); // NOI18N
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 370, 360, 190));

        btnAudio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/volume1.png"))); // NOI18N
        btnAudio.setBorderPainted(false);
        btnAudio.setContentAreaFilled(false);
        btnAudio.addActionListener(this::btnAudioActionPerformed);
        getContentPane().add(btnAudio, new org.netbeans.lib.awtextra.AbsoluteConstraints(1060, 50, 60, -1));

        btnVolver.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/btnatras (1).png"))); // NOI18N
        btnVolver.setBorderPainted(false);
        btnVolver.setContentAreaFilled(false);
        btnVolver.addActionListener(this::btnVolverActionPerformed);
        getContentPane().add(btnVolver, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 300, 110, 110));

        btnSiguiente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/btnAdelante.png"))); // NOI18N
        btnSiguiente.setBorderPainted(false);
        btnSiguiente.setContentAreaFilled(false);
        btnSiguiente.addActionListener(this::btnSiguienteActionPerformed);
        getContentPane().add(btnSiguiente, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 300, 110, 90));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/fondoTeo.Pit.png"))); // NOI18N
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 1140, 660));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/TeoremaPit_1.png"))); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 180, 740, 400));

        jLabel3.setText("jLabel3");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 240, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAudioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAudioActionPerformed
        // TODO add your handling code here:
        if (logicaMat.estaSonando()) {
            logicaMat.pausarAudio();
            btnAudio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/volume1.png")));
        } else {
            logicaMat.reproducirAudio("explicacionP.wav"); 
            btnAudio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/stop_icon.png")));
        }   
    }//GEN-LAST:event_btnAudioActionPerformed

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed

        logicaMat.detenerAudio();
        
        formularios_Temas menuPrincipal = new formularios_Temas();
        menuPrincipal.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnVolverActionPerformed

    private void btnSiguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSiguienteActionPerformed
        
        logicaMat.detenerAudio();
        
        EjemploPitagoras ventanaEjemplo = new EjemploPitagoras();
        ventanaEjemplo.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnSiguienteActionPerformed

    
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
        java.awt.EventQueue.invokeLater(() -> new TeoriaPitagoras().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAudio;
    private javax.swing.JButton btnSiguiente;
    private javax.swing.JButton btnVolver;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    // End of variables declaration//GEN-END:variables
}
