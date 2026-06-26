package matematica;

public class TeoriaConjuntos extends javax.swing.JFrame {

    Matematicas logicaMat = new Matematicas();
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(TeoriaConjuntos.class.getName());

    // Timers independientes
    private javax.swing.Timer timerRelojSecuencia;
    private javax.swing.Timer timerGrupo1;
    private javax.swing.Timer timerGrupo2;
    private javax.swing.Timer timerCanasta;
    private int ticksSegundos = 0;

    // Escalas y booleanos independientes corregidos
    private double escalaG1 = 1.0;
    private double escalaG2 = 1.0;
    private double escalaCanasta = 1.0;

    private boolean creciendoG1 = true;
    private boolean creciendoG2 = true;
    private boolean creciendoCanasta = true;

    // ¡SINCRONIZADO CON LAS COORDENADAS REALES DE TU INTERFAZ!
    private final int xG1 = 210, yG1 = 340, wG1 = 280, hG1 = 270;      // Pedro (jLabel1)
    private final int xCanasta = 450, yCanasta = 170, wCanasta = 290, hCanasta = 220; // Canasta (jLabel4)
    private final int xG2 = 710, yG2 = 340, wG2 = 290, hG2 = 250;     // Ana (jLabel5)

    private java.awt.Image imgG1;
    private java.awt.Image imgG2;
    private java.awt.Image imgCanasta;

    /**
     * Creates new form TeoriaConjuntos
     */
    public TeoriaConjuntos() {
        initComponents();
        this.setLocationRelativeTo(null);

        // Cargamos tus imágenes reales en memoria
        try {
            imgG1 = new javax.swing.ImageIcon(getClass().getResource("/imagen/grupo_pedro.png")).getImage();
            imgG2 = new javax.swing.ImageIcon(getClass().getResource("/imagen/grupo_ana.png")).getImage();
            imgCanasta = new javax.swing.ImageIcon(getClass().getResource("/imagen/canasta_union.png")).getImage();

            // ¡EL TRUCO AQUÍ!: Dibujamos el estado inicial estático para evitar el salto al arrancar
            aplicarZoomSutil(jLabel1, imgG1, xG1, yG1, wG1, hG1, 1.0);
            aplicarZoomSutil(jLabel5, imgG2, xG2, yG2, wG2, hG2, 1.0);
            aplicarZoomSutil(jLabel4, imgCanasta, xCanasta, yCanasta, wCanasta, hCanasta, 1.0);

        } catch (Exception e) {
            logger.log(java.util.logging.Level.SEVERE, "Error al cargar las imágenes en el paquete /imagen/.", e);
        }

        prepararAnimacionesConjuntos();
        configurarZoomBotones();

        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                logicaMat.detenerAudio();
            }
        });

    }

    // --- NUEVO EFECTO: ZOOM EN LOS BOTONES AL PASAR EL MOUSE ---
    private void configurarZoomBotones() {
        // Zoom para el botón VOLVER
        btnVolver.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnVolver.setFont(new java.awt.Font("Comic Sans MS", 1, 14)); // Se hace negrita y más grande
                btnVolver.setBackground(new java.awt.Color(255, 204, 204));   // Cambia a un color tierno
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnVolver.setFont(new java.awt.Font("sansserif", 0, 12));    // Vuelve al estado normal
                btnVolver.setBackground(null);
            }
        });

        // Zoom para el botón SIGUIENTE
        btnSiguiente.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSiguiente.setFont(new java.awt.Font("Comic Sans MS", 1, 14));
                btnSiguiente.setBackground(new java.awt.Color(204, 255, 204));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSiguiente.setFont(new java.awt.Font("sansserif", 0, 12));
                btnSiguiente.setBackground(null);
            }
        });
    }

    // --- MOTORES DE ANIMACIÓN CON VELOCIDADES AJUSTADAS ---
    // Método de dibujo suave anti-parpadeo
    private void prepararAnimacionesConjuntos() {
        // 1. GRUPO DE PEDRO (jLabel1): Zoom lento, sutil e inmóvil (Tope 1.04)
        timerGrupo1 = new javax.swing.Timer(55, e -> {
            if (creciendoG1) {
                escalaG1 += 0.002; // Crecimiento milimétrico y lento
                if (escalaG1 >= 1.04) {
                    creciendoG1 = false;
                }
            } else {
                escalaG1 -= 0.002;
                if (escalaG1 <= 1.0) {
                    creciendoG1 = true;
                }
            }
            aplicarZoomSutil(jLabel1, imgG1, xG1, yG1, wG1, hG1, escalaG1);
        });

        // 2. GRUPO DE ANA (jLabel5): Zoom lento, sutil e inmóvil (Tope 1.04)
        timerGrupo2 = new javax.swing.Timer(55, e -> {
            if (creciendoG2) {
                escalaG2 += 0.002;
                if (escalaG2 >= 1.04) {
                    creciendoG2 = false;
                }
            } else {
                escalaG2 -= 0.002;
                if (escalaG2 <= 1.0) {
                    creciendoG2 = true;
                }
            }
            aplicarZoomSutil(jLabel5, imgG2, xG2, yG2, wG2, hG2, escalaG2);
        });

        // 3. LA CANASTA (jLabel4): Zoom más visible (Tope 1.09)
        timerCanasta = new javax.swing.Timer(35, e -> {
            if (creciendoCanasta) {
                escalaCanasta += 0.006;
                if (escalaCanasta >= 1.09) {
                    creciendoCanasta = false;
                }
            } else {
                escalaCanasta -= 0.006;
                if (escalaCanasta <= 1.0) {
                    creciendoCanasta = true;
                }
            }
            aplicarZoomSutil(jLabel4, imgCanasta, xCanasta, yCanasta, wCanasta, hCanasta, escalaCanasta);
        });

        // 4. DIRECTOR DE ORQUESTA
        timerRelojSecuencia = new javax.swing.Timer(500, e -> {
            ticksSegundos++;

            if (ticksSegundos == 1) {
                timerGrupo1.start();
            }
            if (ticksSegundos == 7) {
                timerGrupo2.start();
            }
            if (ticksSegundos == 14) {
                timerCanasta.start();
            }

            if (!logicaMat.estaSonando() && ticksSegundos > 4) {
                detenerTodoElSistema();
            }
        });
    }

    private void iniciarSecuenciaConjuntos() {
        ticksSegundos = 0;
        logicaMat.reproducirAudio("explicacionC.wav");
        timerRelojSecuencia.start();
    }

    private void detenerTodoElSistema() {
        if (timerRelojSecuencia != null) {
            timerRelojSecuencia.stop();
        }
        if (timerGrupo1 != null) {
            timerGrupo1.stop();
        }
        if (timerGrupo2 != null) {
            timerGrupo2.stop();
        }
        if (timerCanasta != null) {
            timerCanasta.stop();
        }

        logicaMat.detenerAudio();

        // Retornan todos a escala 1.0 estable en sus coordenadas originales
        aplicarZoomSutil(jLabel1, imgG1, xG1, yG1, wG1, hG1, 1.0);
        aplicarZoomSutil(jLabel5, imgG2, xG2, yG2, wG2, hG2, 1.0);
        aplicarZoomSutil(jLabel4, imgCanasta, xCanasta, yCanasta, wCanasta, hCanasta, 1.0);

        // Reset de variables individuales
        escalaG1 = 1.0;
        escalaG2 = 1.0;
        escalaCanasta = 1.0;
        ticksSegundos = 0;

        btnAudio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/volume1.png")));
    }

    private void aplicarZoomSutil(javax.swing.JLabel label, java.awt.Image img, int xBase, int yBase, int wBase, int hBase, double factor) {
        if (img == null || label == null) {
            return;
        }

        // 1. MANTENER EL COMPONENTE JLABEL COMPLETAMENTE FIJO EN SU SITIO ORIGINAL
        int margen = 30;
        label.setBounds(xBase - margen, yBase - margen, wBase + (margen * 2), hBase + (margen * 2));

        // 2. Calcular los tamaños escalados exactos de la imagen interna
        int nuevoW = (int) Math.round(wBase * factor);
        int nuevoH = (int) Math.round(hBase * factor);

        // Hallamos el centro absoluto relativo al margen
        int centroX = margen + (wBase - nuevoW) / 2;
        int centroY = margen + (hBase - nuevoH) / 2;

        // 3. Renderizado de alta definición en RAM
        java.awt.image.BufferedImage dimg = new java.awt.image.BufferedImage(wBase + (margen * 2), hBase + (margen * 2), java.awt.image.BufferedImage.TYPE_INT_ARGB);
        java.awt.Graphics2D g2d = dimg.createGraphics();

        // Filtros gráficos de suavizado para eliminar el parpadeo brusco
        g2d.setRenderingHint(java.awt.RenderingHints.KEY_INTERPOLATION, java.awt.RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(java.awt.RenderingHints.KEY_RENDERING, java.awt.RenderingHints.VALUE_RENDER_QUALITY);

        g2d.drawImage(img, centroX, centroY, nuevoW, nuevoH, null);
        g2d.dispose();

        // Inyectamos el icono final renderizado
        label.setIcon(new javax.swing.ImageIcon(dimg));
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
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btnAudio = new javax.swing.JButton();
        btnVolver = new javax.swing.JButton();
        btnSiguiente = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/grupo_pedro.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 300, 320, 270));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/canasta_union.png"))); // NOI18N
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 260, 290, 220));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/grupo_ana.png"))); // NOI18N
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 320, 300, 250));

        btnAudio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/volume1.png"))); // NOI18N
        btnAudio.setBorderPainted(false);
        btnAudio.setContentAreaFilled(false);
        btnAudio.setFocusPainted(false);
        btnAudio.addActionListener(this::btnAudioActionPerformed);
        getContentPane().add(btnAudio, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 20, 60, 60));

        btnVolver.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/btnatras (1).png"))); // NOI18N
        btnVolver.setBorderPainted(false);
        btnVolver.setContentAreaFilled(false);
        btnVolver.addActionListener(this::btnVolverActionPerformed);
        getContentPane().add(btnVolver, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 200, -1, 90));

        btnSiguiente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/btnAdelante.png"))); // NOI18N
        btnSiguiente.setBorderPainted(false);
        btnSiguiente.setContentAreaFilled(false);
        btnSiguiente.addActionListener(this::btnSiguienteActionPerformed);
        getContentPane().add(btnSiguiente, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 210, -1, 90));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/fondo_teo.Conj.png"))); // NOI18N
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 1200, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/teoriaCon.png"))); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 180, 790, 420));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        detenerTodoElSistema();
        logicaMat.detenerAudio();
        formularios_Temas menu = new formularios_Temas();
        menu.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnVolverActionPerformed

    private void btnSiguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSiguienteActionPerformed
        detenerTodoElSistema();
        logicaMat.detenerAudio();
        EjemploConjuntos pantallaEjemplo = new EjemploConjuntos();
        pantallaEjemplo.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnSiguienteActionPerformed

    private void btnAudioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAudioActionPerformed
        if (timerRelojSecuencia != null && timerRelojSecuencia.isRunning()) {
            detenerTodoElSistema();
        } else {
            // Protección total: si no encuentra stop_icon.png, el programa NO se caerá
            try {
                java.net.URL urlStop = getClass().getResource("/imagen/stop_icon.png");
                if (urlStop != null) {
                    btnAudio.setIcon(new javax.swing.ImageIcon(urlStop));
                }
            } catch (Exception e) {
                logger.log(java.util.logging.Level.WARNING, "No se pudo cargar stop_icon.png, pero la secuencia continuará.");
            }

            iniciarSecuenciaConjuntos();
        }
    }//GEN-LAST:event_btnAudioActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new TeoriaConjuntos().setVisible(true));
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
    // End of variables declaration//GEN-END:variables
}
