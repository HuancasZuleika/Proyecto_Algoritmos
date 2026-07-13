/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Astronomia;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 *
 * @author Usuario
 */
public class QuizViaLactea2 extends javax.swing.JFrame {
 
private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(QuizViaLactea2.class.getName());
 
    // Puntaje: arranca donde quedó la misión anterior (GameData.puntos)
    private double puntos = GameData.puntos;
 
    // Zonas correctas de la Vía Láctea donde debe caer cada botón.
    // Coordenadas absolutas dentro de la ventana.
    // AJUSTABLE: si alguna zona no coincide bien con el dibujo, solo hay
    // que cambiar los números de este mapa (x, y, ancho, alto).
    private final java.util.Map<javax.swing.JButton, java.awt.Rectangle> zonaCorrecta = new java.util.HashMap<>();
    private final java.util.Set<javax.swing.JButton> resueltos = new java.util.HashSet<>();
 
    // Clip de audio de esta pantalla
    private javax.sound.sampled.Clip clipMusica;
 
    /**
     * Creates new form QuizViaLactea2
     */
    public QuizViaLactea2() {
        initComponents();
        activarLogicaMision2();
    }
 
    private void activarLogicaMision2() {
        javax.swing.JPanel contenedor = (javax.swing.JPanel) getContentPane();
 
        // Puntaje en pantalla (jButton6 se usa como "display", sin acción de clic)
        jButton6.setText(GameData.formatearPuntos(puntos));
        jButton6.setFont(new java.awt.Font("Comic Sans MS", java.awt.Font.BOLD, 20));
        jButton6.setForeground(java.awt.Color.WHITE);
        jButton6.setFocusPainted(false);
 
        // Botón de ayuda (-2 puntos, con la ventana animada)
        jButton5.addActionListener(e -> {
            puntos -= 2;
            actualizarPuntosEnPantalla();
            VentanaPersonalizada vp = new VentanaPersonalizada();
            vp.mostrarPistaPersonalizada(this,
                    "Arrastra cada botón hasta la parte de la Vía Láctea que le corresponde. "
                    + "El Núcleo está en el centro, el Bulbo Central lo rodea, "
                    + "el Disco Galáctico son los brazos espirales, y el Halo es la zona más externa.");
        });
 
        // Zonas correctas sobre el dibujo (jLabel2 está en x=580,y=280,430x360)
        zonaCorrecta.put(jButton2, new java.awt.Rectangle(779, 442, 38, 39));  // Nucleo
        zonaCorrecta.put(jButton4, new java.awt.Rectangle(747, 458, 54, 45)); // Bulbo Central
        zonaCorrecta.put(jButton1, new java.awt.Rectangle(800, 455, 65, 50)); // Disco Galactico
        zonaCorrecta.put(jButton3, new java.awt.Rectangle(596, 509, 75, 67)); // Halo
 
        for (javax.swing.JButton b : zonaCorrecta.keySet()) {
            habilitarArrastre(b, contenedor);
        }
 
        // Animación de flote para el botón de Ayuda
        animarBotonAyuda();
 
        // Música de fondo al abrir el formulario
        reproducirMusicaFondo();
    }
 
    /**
     * Hace que el botón de Ayuda (jButton5) flote suavemente hacia arriba
     * y abajo de forma continua, como si tuviera vida propia.
     */
    private void animarBotonAyuda() {
        final java.awt.Point posOriginal = jButton5.getLocation();
        final double[] angulo = {0};
 
        javax.swing.Timer timerFlote = new javax.swing.Timer(30, e -> {
            angulo[0] += 0.08;
            int desplazamiento = (int) (Math.sin(angulo[0]) * 8); // sube/baja 8px
            jButton5.setLocation(posOriginal.x, posOriginal.y + desplazamiento);
        });
        timerFlote.start();
    }
 
    /**
     * Reproduce el audio una sola vez al abrir el formulario.
     * AJUSTABLE: cambiá "QUIZ2.wav" por el nombre exacto de tu archivo
     * (con la extensión y mayúsculas/minúsculas tal cual está guardado
     * en src/audio/).
     */
    private void reproducirMusicaFondo() {
        try {
            java.net.URL url = getClass().getResource("/audio/Quiz2.wav");
            clipMusica = javax.sound.sampled.AudioSystem.getClip();
            javax.sound.sampled.AudioInputStream audioIn = javax.sound.sampled.AudioSystem.getAudioInputStream(url);
            clipMusica.open(audioIn);
            clipMusica.start(); // se reproduce una sola vez, no en bucle
        } catch (Exception ex) {
            logger.log(java.util.logging.Level.SEVERE, "No se pudo reproducir la música", ex);
        }
    }
 
    /**
     * Detiene y libera el clip de audio, si está sonando.
     * Se llama antes de pasar a la siguiente misión para que no siga de fondo.
     */
    private void detenerMusicaFondo() {
        if (clipMusica != null && clipMusica.isRunning()) {
            clipMusica.stop();
            clipMusica.close();
        }
    }
 
    private void habilitarArrastre(javax.swing.JButton btn, javax.swing.JPanel contenedor) {
        btn.putClientProperty("posOriginal", btn.getLocation());
        final java.awt.Point[] offset = {null};
 
        java.awt.event.MouseAdapter arrastre = new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                if (resueltos.contains(btn)) return;
                offset[0] = e.getPoint();
                contenedor.setComponentZOrder(btn, 0);
            }
 
            @Override
            public void mouseDragged(java.awt.event.MouseEvent e) {
                if (resueltos.contains(btn)) return;
                java.awt.Point actual = btn.getLocation();
                btn.setLocation(actual.x + e.getX() - offset[0].x, actual.y + e.getY() - offset[0].y);
            }
 
            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                if (resueltos.contains(btn)) return;
                procesarSoltado(btn);
            }
        };
        btn.addMouseListener(arrastre);
        btn.addMouseMotionListener(arrastre);
    }
 
    private void procesarSoltado(javax.swing.JButton btn) {
        java.awt.Rectangle zonaSuya = zonaCorrecta.get(btn);
        java.awt.Rectangle bounds = btn.getBounds();
        java.awt.Point origen = (java.awt.Point) btn.getClientProperty("posOriginal");
 
        if (bounds.intersects(zonaSuya)) {
            // ¡Correcto!
            resueltos.add(btn);
            btn.setLocation(zonaSuya.x - (bounds.width - zonaSuya.width) / 2, zonaSuya.y - (bounds.height - zonaSuya.height) / 2);
            btn.setBackground(new java.awt.Color(190, 255, 190));
            puntos += 2.5;
            actualizarPuntosEnPantalla();
 
            VentanaPersonalizada vp = new VentanaPersonalizada();
            vp.mostrarExitoPersonalizado(this, 2.5, "<html><center>¡Muy bien!<br>" + btn.getText() + " va justo ahí.</center></html>");
            return;
        }
 
        boolean cayoEnOtraZona = false;
        for (java.awt.Rectangle zona : zonaCorrecta.values()) {
            if (bounds.intersects(zona)) {
                cayoEnOtraZona = true;
                break;
            }
        }
 
        btn.setLocation(origen);
        if (cayoEnOtraZona) {
            puntos -= 3;
            actualizarPuntosEnPantalla();
            VentanaPersonalizada vp = new VentanaPersonalizada();
            vp.mostrarErrorSimplePersonalizado(this, "Esa no es la parte correcta de la Vía Láctea para \"" + btn.getText() + "\". ¡Inténtalo de nuevo!");
        }
        // Si lo soltó fuera del dibujo, no se penaliza: solo vuelve a su lugar.
    }
 
    private void actualizarPuntosEnPantalla() {
        jButton6.setText(GameData.formatearPuntos(puntos));
        GameData.puntos = puntos; // mantiene el puntaje global al día
    }
 
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jBtnSiguiente = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        jButton1.setForeground(new java.awt.Color(0, 0, 0));
        jButton1.setText("Disco Galactico");
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 500, 180, 40));

        jButton2.setBackground(new java.awt.Color(255, 255, 255));
        jButton2.setFont(new java.awt.Font("Segoe UI Black", 0, 24)); // NOI18N
        jButton2.setForeground(new java.awt.Color(0, 0, 0));
        jButton2.setText("Nucleo");
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 400, 180, 40));

        jButton3.setBackground(new java.awt.Color(255, 255, 255));
        jButton3.setFont(new java.awt.Font("Segoe UI Black", 0, 24)); // NOI18N
        jButton3.setForeground(new java.awt.Color(0, 0, 0));
        jButton3.setText("Halo");
        getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 450, 180, 40));

        jButton4.setBackground(new java.awt.Color(255, 255, 255));
        jButton4.setFont(new java.awt.Font("Segoe UI Black", 0, 18)); // NOI18N
        jButton4.setForeground(new java.awt.Color(0, 0, 0));
        jButton4.setText("Bulbo Central");
        jButton4.addActionListener(this::jButton4ActionPerformed);
        getContentPane().add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 550, 180, 40));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/ViaLactea.png"))); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 280, 430, 360));

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/Ayuda (1) (1).png"))); // NOI18N
        jButton5.setBorderPainted(false);
        jButton5.setContentAreaFilled(false);
        getContentPane().add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 130, 150, 120));

        jButton6.setBackground(new java.awt.Color(51, 0, 51));
        getContentPane().add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 150, 90, 50));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/Tablero de Opciones (1) (1).png"))); // NOI18N
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 250, 320, 410));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/Acumulador (1).png"))); // NOI18N
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 70, 260, 130));

        jBtnSiguiente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/btnAdelante.png"))); // NOI18N
        jBtnSiguiente.setBorderPainted(false);
        jBtnSiguiente.setContentAreaFilled(false);
        jBtnSiguiente.addActionListener(this::jBtnSiguienteActionPerformed);
        getContentPane().add(jBtnSiguiente, new org.netbeans.lib.awtextra.AbsoluteConstraints(1030, 520, 100, 90));

        jLabel5.setFont(new java.awt.Font("Swis721 BT", 1, 36)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 153, 153));
        jLabel5.setText("Averiguamos las Partes ");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 190, 440, 60));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/Lactea3 (1).png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, -20, 1150, 790));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jBtnSiguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSiguienteActionPerformed
if (resueltos.size() < zonaCorrecta.size()) {
      VentanaPersonalizada vp = new VentanaPersonalizada();
      vp.mostrarErrorSimplePersonalizado(this, "Todavía te faltan partes de la Vía Láctea por ubicar.");
      return;
  }
  detenerMusicaFondo();   // <-- esta línea nueva: corta la música antes de pasar de pantalla
  QuizViaLactea3 quiz = new QuizViaLactea3();
quiz.setVisible(true);
this.dispose();
        
    
        
    }//GEN-LAST:event_jBtnSiguienteActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new QuizViaLactea2().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnSiguiente;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    // End of variables declaration//GEN-END:variables
}
