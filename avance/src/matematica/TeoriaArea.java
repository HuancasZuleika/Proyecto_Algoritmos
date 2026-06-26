/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package matematica;

/**
 *
 * @author jharleth
 */
public class TeoriaArea extends javax.swing.JFrame {

    Matematicas logicaMat = new Matematicas();
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(TeoriaArea.class.getName());

    // Timers independientes para cada figura y la secuencia
   
    private int ticksSegundos = 0;
    private Thread hiloSecuenciaArea;

    // Escalas y controles de estado independientes
    private double escalaTri = 1.0;
    private double escalaCua = 1.0;
    private double escalaRec = 1.0;

    private boolean creciendoTri = true;
    private boolean creciendoCua = true;
    private boolean creciendoRec = true;

    // Coordenadas fijas reales de tus JLabels en AbsoluteLayout
    private final int xTri = 120, yTri = 250, wTri = 290, hTri = 310;       // lblTriangulo
    private final int xCua = 400, yCua = 230, wCua = 330, hCua = 330;       // lblCuadrado
    private final int xRec = 670, yRec = 230, wRec = 310, hRec = 360;       // lblRectangulo

    private java.awt.Image imgTri;
    private java.awt.Image imgCua;
    private java.awt.Image imgRec;

    private java.awt.Image imgOriginalAtras;
    private java.awt.Image imgOriginalAdelante;

    /**
     * Creates new form TeoriaArea
     */
    public TeoriaArea() {
        initComponents();
        this.setLocationRelativeTo(null);

        // 1. PRIMERO cargamos las imágenes (esto ya lo tienes)
        try {
            imgTri = new javax.swing.ImageIcon(getClass().getResource("/imagen/triangulo_area.png")).getImage();
            imgCua = new javax.swing.ImageIcon(getClass().getResource("/imagen/cuadrado_area.png")).getImage();
            imgRec = new javax.swing.ImageIcon(getClass().getResource("/imagen/rectan_area.png")).getImage();

            aplicarZoomSutil(lblTriangulo, imgTri, xTri, yTri, wTri, hTri, 1.0);
            aplicarZoomSutil(lblCuadrado, imgCua, xCua, yCua, wCua, hCua, 1.0);
            aplicarZoomSutil(lblRectangulo, imgRec, xRec, yRec, wRec, hRec, 1.0);
        } catch (Exception e) {
            logger.log(java.util.logging.Level.SEVERE, "Error al cargar las imágenes de área.", e);
        }

        // 2. SEGUNDO inicializamos los timers en memoria (¡Esta línea es clave!)

        // 3. TERCERO configuramos los listeners de los botones
        configurarZoomBotones();

        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                logicaMat.detenerAudio();
            }
        });
    }


    private void configurarZoomBotones() {
        btnVolver.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                // Obtenemos el ancho y alto original del botón (según AbsoluteConstraints: w=?, h=90)
                java.awt.Image imgZoom = imgOriginalAtras.getScaledInstance(120, 100, java.awt.Image.SCALE_SMOOTH);
                btnVolver.setIcon(new javax.swing.ImageIcon(imgZoom));
                btnVolver.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                java.awt.Image imgNormal = imgOriginalAtras.getScaledInstance(100, 90, java.awt.Image.SCALE_SMOOTH);
                btnVolver.setIcon(new javax.swing.ImageIcon(imgNormal));
            }
        });

        btnSiguiente.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                // (Según AbsoluteConstraints: w=?, h=80)
                java.awt.Image imgZoom = imgOriginalAdelante.getScaledInstance(110, 90, java.awt.Image.SCALE_SMOOTH);
                btnSiguiente.setIcon(new javax.swing.ImageIcon(imgZoom));
                btnSiguiente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                java.awt.Image imgNormal = imgOriginalAdelante.getScaledInstance(100, 80, java.awt.Image.SCALE_SMOOTH);
                btnSiguiente.setIcon(new javax.swing.ImageIcon(imgNormal));
            }
        });
    }

    private void aplicarZoomSutil(javax.swing.JLabel label, java.awt.Image img, int xBase, int yBase, int wBase, int hBase, double factor) {
        if (img == null || label == null) {
            return;
        }

        int margen = 30;
        label.setBounds(xBase - margen, yBase - margen, wBase + (margen * 2), hBase + (margen * 2));

        int nuevoW = (int) Math.round(wBase * factor);
        int nuevoH = (int) Math.round(hBase * factor);

        int centroX = margen + (wBase - nuevoW) / 2;
        int centroY = margen + (hBase - nuevoH) / 2;

        java.awt.image.BufferedImage dimg = new java.awt.image.BufferedImage(wBase + (margen * 2), hBase + (margen * 2), java.awt.image.BufferedImage.TYPE_INT_ARGB);
        java.awt.Graphics2D g2d = dimg.createGraphics();

        g2d.setRenderingHint(java.awt.RenderingHints.KEY_INTERPOLATION, java.awt.RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(java.awt.RenderingHints.KEY_ANTIALIASING, java.awt.RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(java.awt.RenderingHints.KEY_RENDERING, java.awt.RenderingHints.VALUE_RENDER_QUALITY);

        g2d.drawImage(img, centroX, centroY, nuevoW, nuevoH, null);
        g2d.dispose();

        label.setIcon(new javax.swing.ImageIcon(dimg));
    }

private void iniciarSecuenciaArea() {
    ticksSegundos = 0;
    
    hiloSecuenciaArea = new Thread(() -> {
        try {
            // --- 1. INTRODUCCIÓN ---
            logicaMat.reproducirAudio("intro_Area.wav");
            
            // Esperamos un segundo (1000 ms) para dar tiempo a que el audio de la intro empiece a sonar bien
            Thread.sleep(1000); 
            
            // Esperamos a que la intro termine de sonar
            while (logicaMat.estaSonando()) {
                Thread.sleep(100);
            }
            
            // Una vez termina el audio, aplicamos el zoom de las tres figuras a la vez de forma segura
            javax.swing.SwingUtilities.invokeLater(() -> {
                ejecutarZoomParalelo(true, true, true);
            });
            
            // Mantenemos el zoom un momento en pantalla
            Thread.sleep(1500);
            
            // Regresan a tamaño normal
            javax.swing.SwingUtilities.invokeLater(() -> {
                ejecutarZoomParalelo(false, false, false);
            });
            Thread.sleep(500);

            // --- 3. CUADRADO ---
            logicaMat.reproducirAudio("A.cuadrado.wav");
            javax.swing.SwingUtilities.invokeLater(() -> {
                animarFiguraSola(1, true);
            });
            Thread.sleep(500);
            while (logicaMat.estaSonando()) {
                Thread.sleep(100);
            }
            javax.swing.SwingUtilities.invokeLater(() -> {
                animarFiguraSola(1, false);
            });
            Thread.sleep(500);
            
            // --- 2. TRIÁNGULO ---
            logicaMat.reproducirAudio("A.triangulo.wav");
            javax.swing.SwingUtilities.invokeLater(() -> {
                animarFiguraSola(2, true);
            });
            Thread.sleep(500);
            while (logicaMat.estaSonando()) {
                Thread.sleep(100);
            }
            javax.swing.SwingUtilities.invokeLater(() -> {
                animarFiguraSola(2, false);
            });
            Thread.sleep(500);

            // --- 4. RECTÁNGULO ---
            logicaMat.reproducirAudio("A.rectangulo.wav");
            javax.swing.SwingUtilities.invokeLater(() -> {
                animarFiguraSola(3, true);
            });
            Thread.sleep(500);
            while (logicaMat.estaSonando()) {
                Thread.sleep(100);
            }
            javax.swing.SwingUtilities.invokeLater(() -> {
                animarFiguraSola(3, false);
            });

            detenerTodoElSistema();

        } catch (InterruptedException e) {
            logger.log(java.util.logging.Level.INFO, "Secuencia de área interrumpida de forma segura.");
        }
    });
    hiloSecuenciaArea.start();
}// Para la intro: hace crecer las 3 imágenes ligeramente de golpe
private void ejecutarZoomParalelo(boolean tri, boolean cua, boolean rec) {
    double factor = 1.06;
    aplicarZoomSutil(lblTriangulo, imgTri, xTri, yTri, wTri, hTri, tri ? factor : 1.0);
    aplicarZoomSutil(lblCuadrado, imgCua, xCua, yCua, wCua, hCua, cua ? factor : 1.0);
    aplicarZoomSutil(lblRectangulo, imgRec, xRec, yRec, wRec, hRec, rec ? factor : 1.0);
}

// Para los audios individuales: hace zoom a una figura específica
private void animarFiguraSola(int figura, boolean aumentar) {
    double factor = 1.08; // Zoom un poco más notorio para el protagonista
    double valorZoom = aumentar ? factor : 1.0;
    
    if (figura == 1) {
        aplicarZoomSutil(lblCuadrado, imgCua, xCua, yCua, wCua, hCua, valorZoom);
    } else if (figura == 2) {
        aplicarZoomSutil(lblTriangulo, imgTri, xTri, yTri, wTri, hTri, valorZoom);
    } else if (figura == 3) {
        aplicarZoomSutil(lblRectangulo, imgRec, xRec, yRec, wRec, hRec, valorZoom);
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

        lblTriangulo = new javax.swing.JLabel();
        lblCuadrado = new javax.swing.JLabel();
        lblRectangulo = new javax.swing.JLabel();
        btnVolver = new javax.swing.JButton();
        btnSiguiente = new javax.swing.JButton();
        btnAudio = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTriangulo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/triangulo_area.png"))); // NOI18N
        getContentPane().add(lblTriangulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 270, 320, 310));

        lblCuadrado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/cuadrado_area.png"))); // NOI18N
        getContentPane().add(lblCuadrado, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 260, 360, 340));

        lblRectangulo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/rectan_area.png"))); // NOI18N
        getContentPane().add(lblRectangulo, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 230, 380, 360));

        btnVolver.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/btnatras (1).png"))); // NOI18N
        btnVolver.setBorderPainted(false);
        btnVolver.setContentAreaFilled(false);
        btnVolver.addActionListener(this::btnVolverActionPerformed);
        getContentPane().add(btnVolver, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, -1, 90));

        btnSiguiente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/btnAdelante.png"))); // NOI18N
        btnSiguiente.setBorderPainted(false);
        btnSiguiente.setContentAreaFilled(false);
        btnSiguiente.addActionListener(this::btnSiguienteActionPerformed);
        getContentPane().add(btnSiguiente, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 220, -1, 80));

        btnAudio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/volume1.png"))); // NOI18N
        btnAudio.setBorderPainted(false);
        btnAudio.setContentAreaFilled(false);
        btnAudio.addActionListener(this::btnAudioActionPerformed);
        getContentPane().add(btnAudio, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 20, -1, 70));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/fondo_teo.Area.png"))); // NOI18N
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 1090, 620));

        jTextArea1.setColumns(20);
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setText("\"¡Hola, pequeño arquitecto! \n¿Sabías que el ÁREA es el tamaño de la superficie de una figura? \nEs decir, todo el espacio que hay dentro de sus líneas.\n\nPara cada figura hay una fórmula mágica:\n\nCuadrado: ¡Solo multiplicas un lado por el otro!\n\nTriángulo: Imagina la mitad de un rectángulo. ¡Es base por altura entre dos!\n\nRectangulo: ¡Aqui vamos solo a multiplicar el ancho por la altura !\n\n¡Aprender a medir áreas nos ayuda a construir cosas asombrosas!\"");
        jTextArea1.setWrapStyleWord(true);
        jScrollPane1.setViewportView(jTextArea1);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 190, 400, 230));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/EjemploA.png"))); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 250, 690, 350));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        // TODO add your handling code here:
        logicaMat.detenerAudio();
        formularios_Temas menu = new formularios_Temas();
        menu.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnVolverActionPerformed

    private void btnSiguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSiguienteActionPerformed
        // TODO add your handling code here:
        logicaMat.detenerAudio();
        EjemploArea pantallaEjemplo = new EjemploArea();
        pantallaEjemplo.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnSiguienteActionPerformed

    private void btnAudioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAudioActionPerformed
        // Verificamos si el hilo de la secuencia está corriendo actualmente
        if (hiloSecuenciaArea != null && hiloSecuenciaArea.isAlive()) {
            detenerTodoElSistema();
        } else {
            try {
                java.net.URL urlStop = getClass().getResource("/imagen/stop_icon.png");
                if (urlStop != null) {
                    btnAudio.setIcon(new javax.swing.ImageIcon(urlStop));
                }
            } catch (Exception e) {
                logger.log(java.util.logging.Level.WARNING, "No se pudo cargar stop_icon.png.");
            }
            // ¡Arrancamos nuestra secuencia sincronizada!
            iniciarSecuenciaArea();
        }
    }//GEN-LAST:event_btnAudioActionPerformed

private void detenerTodoElSistema() {
    if (hiloSecuenciaArea != null && hiloSecuenciaArea.isAlive()) {
        hiloSecuenciaArea.interrupt();
    }
    
    logicaMat.detenerAudio();
    
    aplicarZoomSutil(lblTriangulo, imgTri, xTri, yTri, wTri, hTri, 1.0);
    aplicarZoomSutil(lblCuadrado, imgCua, xCua, yCua, wCua, hCua, 1.0);
    aplicarZoomSutil(lblRectangulo, imgRec, xRec, yRec, wRec, hRec, 1.0);
    
    ticksSegundos = 0;
    btnAudio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/volume1.png")));
}
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
        java.awt.EventQueue.invokeLater(() -> new TeoriaArea().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAudio;
    private javax.swing.JButton btnSiguiente;
    private javax.swing.JButton btnVolver;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel lblCuadrado;
    private javax.swing.JLabel lblRectangulo;
    private javax.swing.JLabel lblTriangulo;
    // End of variables declaration//GEN-END:variables
}
