/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Astronomia;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author Usuario
 */
public class QuizViaLactea3 extends javax.swing.JFrame {
    
   
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(QuizViaLactea3.class.getName());
 
    // Puntaje: arranca donde quedó la misión anterior (GameData.puntos)
    private double puntos = GameData.puntos;
 
    // Clip de audio de esta pantalla
    private Clip clipMusica;
 
    // Respuesta correcta de esta adivinanza (la Vía Láctea)
    private boolean respondido = false;
 
    /**
     * Creates new form QuizViaLactea3
     */
    public QuizViaLactea3() {
        initComponents();
        activarLogicaMision3();
    }
 
    private void activarLogicaMision3() {
        // Puntaje en pantalla (jButton3 se usa como "display", sin acción de clic)
        jButton3.setText(GameData.formatearPuntos(puntos));
        jButton3.setFont(new java.awt.Font("Comic Sans MS", java.awt.Font.BOLD, 20));
        jButton3.setForeground(java.awt.Color.WHITE);
        jButton3.setFocusPainted(false);
 
        // Botón de ayuda (-2 puntos, con la ventana animada)
        jButton1.addActionListener(e -> {
            puntos -= 2;
            actualizarPuntosEnPantalla();
            VentanaPersonalizada vp = new VentanaPersonalizada();
            vp.mostrarPistaPersonalizada(this,
                    "Soy una gigantesca espiral formada por miles de millones de "
                    + "estrellas, incluido nuestro Sol. ¡Es la galaxia donde vivimos!");
        });
 
        // Respuestas: cada botón se conecta con si es o no la correcta
        configurarRespuesta(jBtnSol, false);
        configurarRespuesta(jBtnSaturno, false);
        configurarRespuesta(jBtnViaLactea, true);
        configurarRespuesta(jBtnTierra, false);
 
        // Botón Siguiente
        jBtnSiguiente.addActionListener(e -> {
            if (!respondido) {
                VentanaPersonalizada vp = new VentanaPersonalizada();
                vp.mostrarErrorSimplePersonalizado(this, "Todavía te falta responder la adivinanza.");
                return;
            }
            detenerMusicaFondo();
            QuizViaLactea4 quiz = new QuizViaLactea4();
            quiz.setVisible(true);
            this.dispose();
        });
 
        // Animaciones
        animarBotonAyuda();
        animarAstronauta();
        animarBotonesRespuesta();
 
        // Música de fondo al abrir el formulario
        reproducirMusicaFondo();
    }
 
    /**
     * Conecta un botón de respuesta con la lógica de correcto/incorrecto,
     * igual que en la Misión 2: acierto suma puntos y muestra la ventana
     * de éxito; error resta puntos y muestra la ventana de error.
     */
    private void configurarRespuesta(javax.swing.JButton btn, boolean esCorrecta) {
        btn.addActionListener(e -> {
            if (respondido) return; // ya se respondió, no hacer nada más
 
            VentanaPersonalizada vp = new VentanaPersonalizada();
            if (esCorrecta) {
                respondido = true;
                puntos += 10;
                actualizarPuntosEnPantalla();
                btn.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(190, 255, 190), 4));
                vp.mostrarExitoPersonalizado(this, 10, "<html><center>¡Muy bien!<br>Es la Vía Láctea.</center></html>");
            } else {
                puntos -= 3;
                actualizarPuntosEnPantalla();
                vp.mostrarErrorSimplePersonalizado(this,
                        "Parece que has elegido mal.<br>"
                        + "¿Quieres una pista?<br>"
                        + "<b>Dirígete al botón de 'HELP'</b><br>"
                        + "(cuesta 2 puntos adicionales)");
            }
        });
    }
 
    /**
     * Hace que el botón de Ayuda (jButton1) flote suavemente hacia arriba
     * y abajo de forma continua.
     */
    private void animarBotonAyuda() {
        final java.awt.Point posOriginal = jButton1.getLocation();
        final double[] angulo = {0};
 
        javax.swing.Timer timerFlote = new javax.swing.Timer(30, e -> {
            angulo[0] += 0.08;
            int desplazamiento = (int) (Math.sin(angulo[0]) * 8); // sube/baja 8px
            jButton1.setLocation(posOriginal.x, posOriginal.y + desplazamiento);
        });
        timerFlote.start();
    }
 
    /**
     * Hace que el astronauta (jButton2) se mueva de izquierda a derecha
     * en bucle, como si estuviera flotando en el espacio.
     * AJUSTABLE: cambiá el "40" por otro valor si querés más o menos recorrido.
     */
    private void animarAstronauta() {
        final java.awt.Point posOriginal = jButton2.getLocation();
        final double[] angulo = {0};
 
        javax.swing.Timer timerMovimiento = new javax.swing.Timer(30, e -> {
            angulo[0] += 0.04; // velocidad del movimiento
            int desplazamiento = (int) (Math.sin(angulo[0]) * 40); // recorrido horizontal en px
            jButton2.setLocation(posOriginal.x + desplazamiento, posOriginal.y);
        });
        timerMovimiento.start();
    }
 
    /**
     * Hace flotar suavemente los 4 botones de respuesta (cada uno con su
     * propio ritmo para que no se muevan todos exactamente igual), y además
     * los agranda un poco cuando el mouse pasa por encima.
     */
    private void animarBotonesRespuesta() {
        javax.swing.JButton[] botones = {jBtnSol, jBtnSaturno, jBtnViaLactea, jBtnTierra};
        double[] desfaseInicial = {0, 1.5, 3.0, 4.5}; // para que floten desincronizados
 
        for (int i = 0; i < botones.length; i++) {
            javax.swing.JButton btn = botones[i];
            java.awt.Dimension tamOriginal = btn.getSize();
            java.awt.Point posOriginal = btn.getLocation();
            double[] angulo = {desfaseInicial[i]};
 
            // Flote continuo
            javax.swing.Timer timerFlote = new javax.swing.Timer(30, e -> {
                angulo[0] += 0.06;
                int desplazamiento = (int) (Math.sin(angulo[0]) * 6);
                btn.setLocation(posOriginal.x, posOriginal.y + desplazamiento);
            });
            timerFlote.start();
 
            // Efecto hover: crece un poco al pasar el mouse
            btn.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    int nuevoAncho = (int) (tamOriginal.width * 1.1);
                    int nuevoAlto = (int) (tamOriginal.height * 1.1);
                    btn.setSize(nuevoAncho, nuevoAlto);
                }
 
                @Override
                public void mouseExited(java.awt.event.MouseEvent e) {
                    btn.setSize(tamOriginal);
                }
            });
        }
    }
 
    /**
     * Reproduce el audio de esta pantalla una sola vez al abrir el formulario.
     */
    private void reproducirMusicaFondo() {
        try {
            java.net.URL url = getClass().getResource("/audio/Quiz3Lactea.wav");
            clipMusica = AudioSystem.getClip();
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
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
 
    private void actualizarPuntosEnPantalla() {
        jButton3.setText(GameData.formatearPuntos(puntos));
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

        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jBtnSaturno = new javax.swing.JButton();
        jBtnViaLactea = new javax.swing.JButton();
        jBtnTierra = new javax.swing.JButton();
        jBtnSol = new javax.swing.JButton();
        jBtnSiguiente = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Segoe UI Black", 0, 36)); // NOI18N
        jLabel2.setText("ADIVINA QUIEN SOY ?");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 210, -1, -1));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/Ayuda (1) (1).png"))); // NOI18N
        jButton1.setBorderPainted(false);
        jButton1.setContentAreaFilled(false);
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 160, -1, 120));

        jButton3.setBackground(new java.awt.Color(51, 0, 51));
        getContentPane().add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 140, 110, 50));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/Acumulador (1).png"))); // NOI18N
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 60, 260, 140));

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/NIÑOFINALQUIZ1 (1) (1).png"))); // NOI18N
        jButton2.setBorderPainted(false);
        jButton2.setContentAreaFilled(false);
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 260, 320, 490));

        jBtnSaturno.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/OpcionC (1).png"))); // NOI18N
        jBtnSaturno.setBorderPainted(false);
        jBtnSaturno.setContentAreaFilled(false);
        getContentPane().add(jBtnSaturno, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 280, -1, -1));

        jBtnViaLactea.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/Opcion D (1).png"))); // NOI18N
        jBtnViaLactea.setBorderPainted(false);
        jBtnViaLactea.setContentAreaFilled(false);
        getContentPane().add(jBtnViaLactea, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 470, -1, -1));

        jBtnTierra.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/OpcionE (1).png"))); // NOI18N
        jBtnTierra.setBorderPainted(false);
        jBtnTierra.setContentAreaFilled(false);
        getContentPane().add(jBtnTierra, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 480, -1, 170));

        jBtnSol.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/OpcionA (2).png"))); // NOI18N
        jBtnSol.setBorderPainted(false);
        jBtnSol.setContentAreaFilled(false);
        getContentPane().add(jBtnSol, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 280, -1, -1));

        jBtnSiguiente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/btnAdelante.png"))); // NOI18N
        jBtnSiguiente.setBorderPainted(false);
        jBtnSiguiente.setContentAreaFilled(false);
        getContentPane().add(jBtnSiguiente, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 630, -1, 90));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/Lactea4 (1).jpg"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1150, 760));

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
        java.awt.EventQueue.invokeLater(() -> new QuizViaLactea3().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnSaturno;
    private javax.swing.JButton jBtnSiguiente;
    private javax.swing.JButton jBtnSol;
    private javax.swing.JButton jBtnTierra;
    private javax.swing.JButton jBtnViaLactea;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    // End of variables declaration//GEN-END:variables
}
