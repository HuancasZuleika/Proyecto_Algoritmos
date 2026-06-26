
package matematica;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.Timer;

public class TeoriaOperaciones extends javax.swing.JFrame {

    Matematicas logicaMat = new Matematicas();
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(TeoriaOperaciones.class.getName());

    // Timers para controlar el parpadeo/brinco de cada paso
    private Timer timerZoomPaso1;
    private Timer timerZoomPaso2;
    private Timer timerZoomPaso3;

    // Variables de control de escala (factor de zoom entre 1.0 y 1.15)
    private double escala1 = 1.0;
    private double escala2 = 1.0;
    private double escala3 = 1.0;
    private boolean creciendo1 = true;
    private boolean creciendo2 = true;
    private boolean creciendo3 = true;

    private int pasoActual = 0;
    private Thread hiloSecuencia;

    // Imágenes originales para reescalar sin perder calidad
    private Image imgOriginalP1;
    private Image imgOriginalP2;
    private Image imgOriginalP3;
    
    private Image imgOriginalAtras;
private Image imgOriginalAdelante;

    // Variables para capturar las coordenadas exactas de tus Labels en tu diseño
    private int x1, y1, w1, h1;
    private int x2, y2, w2, h2;
    private int x5, y5, w5, h5;

    /**
     * Creates new form TeoriaOperaciones
     */
    public TeoriaOperaciones() {
        initComponents();
        this.setLocationRelativeTo(null);

        // 1. Guardar las posiciones que pusiste en NetBeans antes de que se alteren
        guardarPosicionesOriginales();

        // 2. Cargar los recursos gráficos de forma segura
        cargarImagenesOriginales();

        // 3. Configurar la lógica del Zoom rítmico
        prepararMotoresZoom();

        // 4. Activar los efectos en los botones que ya te funcionan
        activarEfectosBotonesNavegacion();

        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                logicaMat.detenerAudio();
            }
        });
    }

    private void guardarPosicionesOriginales() {
        // Captura dinámicamente el lugar exacto de tus labels en tu pantalla
        x1 = lblPaso1.getX();
        y1 = lblPaso1.getY();
        w1 = lblPaso1.getWidth();
        h1 = lblPaso1.getHeight();
        x5 = lblPaso2.getX();
        y5 = lblPaso2.getY();
        w5 = lblPaso2.getWidth();
        h5 = lblPaso2.getHeight();
        x2 = lblPaso3.getX();
        y2 = lblPaso3.getY();
        w2 = lblPaso3.getWidth();
        h2 = lblPaso3.getHeight();
    }

    private void cargarImagenesOriginales() {
        try {
            // Ajustamos las rutas exactas con la carpeta /imagen/ y las mayúsculas correctas
            imgOriginalP1 = new ImageIcon(getClass().getResource("/imagen/Tunel de los Parentesis.png")).getImage();
            imgOriginalP2 = new ImageIcon(getClass().getResource("/imagen/fabrica_multi.png")).getImage();
            imgOriginalP3 = new ImageIcon(getClass().getResource("/imagen/fuente_multi.png")).getImage();
        } catch (Exception e) {
            logger.log(java.util.logging.Level.SEVERE, "Error cargando recursos de imagen", e);
        }
    }

    private void prepararMotoresZoom() {
        // ANIMACIÓN ZOOM PASO 1 (Centro base original: X=200, Y=200, W=150, H=150)
        timerZoomPaso1 = new Timer(40, e -> {
            if (creciendo1) {
                escala1 += 0.005;
                if (escala1 >= 1.07) {
                    creciendo1 = false;
                }
            } else {
                escala1 -= 0.005;
                if (escala1 <= 1.0) {
                    creciendo1 = true;
                }
            }
            aplicarZoomLabel(lblPaso1, imgOriginalP1, x1, y1, w1, h1, escala1);
        });

        // ANIMACIÓN ZOOM PASO 2 (Centro base original: X=450, Y=200, W=150, H=150)
        timerZoomPaso2 = new Timer(40, e -> {
            if (creciendo2) {
                escala2 += 0.005;
                if (escala2 >= 1.07) {
                    creciendo2 = false;
                }
            } else {
                escala2 -= 0.005;
                if (escala2 <= 1.0) {
                    creciendo2 = true;
                }
            }
            aplicarZoomLabel(lblPaso2, imgOriginalP2, x5, y5, w5, h5, escala2);
        });

        // ANIMACIÓN ZOOM PASO 3 (Centro base original: X=700, Y=200, W=150, H=150)
        timerZoomPaso3 = new Timer(40, e -> {
            if (creciendo3) {
                escala3 += 0.005;
                if (escala3 >= 1.07) {
                    creciendo3 = false;
                }
            } else {
                escala3 -= 0.005;
                if (escala3 <= 1.0) {
                    creciendo3 = true;
                }
            }
            aplicarZoomLabel(lblPaso3, imgOriginalP3, x2, y2, w2, h2, escala3);
        });
    }

    // Efecto Zoom para los botones de Atrás y Adelante cuando pasas el mouse
    private void activarEfectosBotonesNavegacion() {
        String urlAtras = "/imagen/btnatras (1).png";
        String urlAdelante = "/imagen/btnAdelante.png";

        // Zoom botón Volver (Posición: X=40, Y=300)
        btnVolver.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ejecutarZoomBoton(btnVolver, urlAtras, 1000, 300, 90, 90, true);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ejecutarZoomBoton(btnVolver, urlAtras, 1000, 300, 90, 90, false);
            }
        });

        // Zoom botón Siguiente (Posición: X=1000, Y=300)
        btnSiguiente.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                ejecutarZoomBoton(btnSiguiente, urlAdelante, 1000, 300, 90, 90, true);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                ejecutarZoomBoton(btnSiguiente, urlAdelante, 1000, 300, 90, 90, false);
            }
        });
    }

    // Método matemático para expandir los JLabels simétricamente desde su centro
    private void aplicarZoomLabel(JLabel label, Image img, int xBase, int yBase, int wBase, int hBase, double factor) {
        if (img == null || label == null) {
            return;
        }

        // 1. Calcular las nuevas dimensiones de la IMAGEN basadas en el factor de zoom
        int nuevoW = (int) (wBase * factor);
        int nuevoH = (int) (hBase * factor);

        // 2. Mover la posición (X, Y) simétricamente hacia atrás para que crezca desde el centro
        int nuevoX = xBase - (nuevoW - wBase) / 2;
        int nuevoY = yBase - (nuevoH - hBase) / 2;

        // 3. Al Label le damos un tamaño un poco MAYOR (un margen extra de 60px) 
        // para que la imagen tenga espacio de sobra y nunca choque con el borde de la caja.
        int margenExtra = 60;
        label.setBounds(nuevoX - (margenExtra / 2), nuevoY - (margenExtra / 2), nuevoW + margenExtra, nuevoH + margenExtra);

        // 4. Escalamos la imagen de forma fluida
        ImageIcon iconoEscalado = new ImageIcon(img.getScaledInstance(nuevoW, nuevoH, Image.SCALE_SMOOTH));
        label.setIcon(iconoEscalado);

        // 5. OBLIGAMOS a Java Swing a redibujar el área completa y limpiar los bordes cortados
        label.revalidate();
        label.repaint();
    }

    // Método matemático para expandir los JButtons de navegación
    private void ejecutarZoomBoton(JButton boton, String ruta, int x, int y, int wContenedor, int hContenedor, boolean escalar) {
        try {
            java.net.URL clipURL = getClass().getResource(ruta);
            if (clipURL == null) {
                return;
            }
            Image img = new ImageIcon(clipURL).getImage();

            if (escalar) {
                int zoomW = (int) (wContenedor * 1.15);
                int zoomH = (int) (hContenedor * 1.15);
                int centroX = x - (zoomW - wContenedor) / 2;
                int centroY = y - (zoomH - hContenedor) / 2;
                boton.setBounds(centroX, centroY, zoomW, zoomH);
                boton.setIcon(new ImageIcon(img.getScaledInstance(zoomW, zoomH, Image.SCALE_SMOOTH)));
                boton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            } else {
                boton.setBounds(x, y, wContenedor, hContenedor);
                boton.setIcon(new ImageIcon(img.getScaledInstance(wContenedor, hContenedor, Image.SCALE_SMOOTH)));
            }
        } catch (Exception e) {
            logger.log(java.util.logging.Level.SEVERE, "Error en zoom de botón", e);
        }
    }

    private void iniciarSecuenciaInteractiva() {
        hiloSecuencia = new Thread(() -> {
            try {
                // =========================================================
                // ESCENA 0: INTRODUCCIÓN ("¡Todas hacen zoom al mismo tiempo!")
                // =========================================================
                pasoActual = 99;

                creciendo1 = creciendo2 = creciendo3 = true;
                timerZoomPaso1.start();
                timerZoomPaso2.start();
                timerZoomPaso3.start();

                logicaMat.reproducirAudio("introduccion_regla_oro.wav");
                Thread.sleep(500); // Pausa de estabilidad para que Java empiece a reproducir
                while (logicaMat.estaSonando()) {
                    Thread.sleep(100);
                }

                // Detener la fiesta y regresar al tamaño original seguro
                timerZoomPaso1.stop();
                timerZoomPaso2.stop();
                timerZoomPaso3.stop();
                aplicarZoomLabel(lblPaso1, imgOriginalP1, x1, y1, w1, h1, 1.0);
                aplicarZoomLabel(lblPaso2, imgOriginalP2, x5, y5, w5, h5, 1.0);
                aplicarZoomLabel(lblPaso3, imgOriginalP3, x2, y2, w2, h2, 1.0);

                // =========================================================
                // ESCENA 1: PASO 1 - PARÉNTESIS (Hace zoom solo la primera)
                // =========================================================
                pasoActual = 1;
                creciendo1 = true;
                timerZoomPaso1.start();
                logicaMat.reproducirAudio("paso1_parentesis.wav");
                Thread.sleep(500); // Espera obligatoria de inicio

                while (logicaMat.estaSonando()) {
                    Thread.sleep(100);
                }
                timerZoomPaso1.stop();
                aplicarZoomLabel(lblPaso1, imgOriginalP1, x1, y1, w1, h1, 1.0);

                // =========================================================
                // ESCENA 2: PASO 2 - MULTIPLICACIÓN Y DIVISIÓN
                // =========================================================
                pasoActual = 2;
                creciendo2 = true;
                timerZoomPaso2.start();
                logicaMat.reproducirAudio("paso2_multiplicacion.wav");
                Thread.sleep(500); // Espera obligatoria de inicio

                while (logicaMat.estaSonando()) {
                    Thread.sleep(100);
                }
                timerZoomPaso2.stop();
                aplicarZoomLabel(lblPaso2, imgOriginalP2, x5, y5, w5, h5, 1.0);

                // =========================================================
                // ESCENA 3: PASO 3 - SUMAS Y RESTAS
                // =========================================================
                pasoActual = 3;
                creciendo3 = true;
                timerZoomPaso3.start();
                logicaMat.reproducirAudio("paso3_sumas.wav");
                Thread.sleep(500); // Espera obligatoria de inicio

                while (logicaMat.estaSonando()) {
                    Thread.sleep(100);
                }
                timerZoomPaso3.stop();
                aplicarZoomLabel(lblPaso3, imgOriginalP3, x2, y2, w2, h2, 1.0);

                btnAudio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/volume1.png")));
                pasoActual = 0;

            } catch (InterruptedException ex) {
                logger.log(java.util.logging.Level.INFO, "Secuencia de zoom interrumpida de forma segura.");
            }
        });
        hiloSecuencia.start();
    }

    private void detenerTodoElSistema() {
        logicaMat.detenerAudio();
        if (hiloSecuencia != null && hiloSecuencia.isAlive()) {
            hiloSecuencia.interrupt();
        }
        timerZoomPaso1.stop();
        timerZoomPaso2.stop();
        timerZoomPaso3.stop();

        // Regresar todo al tamaño original normal
        aplicarZoomLabel(lblPaso1, imgOriginalP1, x1, y1, w1, h1, 1.0);
        aplicarZoomLabel(lblPaso2, imgOriginalP2, x5, y5, w5, h5, 1.0);
        aplicarZoomLabel(lblPaso3, imgOriginalP3, x2, y2, w2, h2, 1.0);

        pasoActual = 0;
        btnAudio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/volume1.png")));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblPaso1 = new javax.swing.JLabel();
        lblPaso2 = new javax.swing.JLabel();
        lblPaso3 = new javax.swing.JLabel();
        btnVolver = new javax.swing.JButton();
        btnSiguiente = new javax.swing.JButton();
        btnAudio = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblPaso1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPaso1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/Tunel de los Parentesis.png"))); // NOI18N
        getContentPane().add(lblPaso1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 310, 350, 250));

        lblPaso2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPaso2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/fabrica_multi.png"))); // NOI18N
        getContentPane().add(lblPaso2, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 230, 320, 320));

        lblPaso3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPaso3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/fuente_multi.png"))); // NOI18N
        getContentPane().add(lblPaso3, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 270, 260, 300));

        btnVolver.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/btnatras (1).png"))); // NOI18N
        btnVolver.setBorderPainted(false);
        btnVolver.setContentAreaFilled(false);
        btnVolver.addActionListener(this::btnVolverActionPerformed);
        getContentPane().add(btnVolver, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, 110, 100));

        btnSiguiente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/btnAdelante.png"))); // NOI18N
        btnSiguiente.setBorderPainted(false);
        btnSiguiente.setContentAreaFilled(false);
        btnSiguiente.addActionListener(this::btnSiguienteActionPerformed);
        getContentPane().add(btnSiguiente, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 220, 100, 90));

        btnAudio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/volume1.png"))); // NOI18N
        btnAudio.setBorderPainted(false);
        btnAudio.setContentAreaFilled(false);
        btnAudio.addActionListener(this::btnAudioActionPerformed);
        getContentPane().add(btnAudio, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 40, 60, -1));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/fondo_teo.Ope.png"))); // NOI18N
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1090, 640));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/teoriaOp.png"))); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 170, 780, 440));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        // TODO add your handling code here:
        detenerTodoElSistema();
        logicaMat.detenerAudio();
        formularios_Temas menu = new formularios_Temas();
        menu.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnVolverActionPerformed

    private void btnSiguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSiguienteActionPerformed
        detenerTodoElSistema();
        logicaMat.detenerAudio();
        EjemploOperaciones pantallaEjemplo = new EjemploOperaciones();
        pantallaEjemplo.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnSiguienteActionPerformed

    private void btnAudioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAudioActionPerformed

        if (pasoActual > 0 || logicaMat.estaSonando()) {
            detenerTodoElSistema();
        } else {
            // Ponemos un try-catch para que si falta el icono de STOP, el programa no se caiga e igual reproduzca el sonido y el zoom
            try {
                java.net.URL urlStop = getClass().getResource("/imagen/stop_icon.png");
                if (urlStop != null) {
                    btnAudio.setIcon(new javax.swing.ImageIcon(urlStop));
                }
            } catch (Exception e) {
                logger.log(java.util.logging.Level.WARNING, "No se encontró stop_icon.png, pero la secuencia continuará.");
            }

            iniciarSecuenciaInteractiva();
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
        java.awt.EventQueue.invokeLater(() -> new TeoriaOperaciones().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAudio;
    private javax.swing.JButton btnSiguiente;
    private javax.swing.JButton btnVolver;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel lblPaso1;
    private javax.swing.JLabel lblPaso2;
    private javax.swing.JLabel lblPaso3;
    // End of variables declaration//GEN-END:variables
}
