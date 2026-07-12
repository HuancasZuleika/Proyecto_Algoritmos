/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Astronomia;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
 
/**
 *
 * @author Usuario
 */
public class QuizViaLactea extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(QuizViaLactea.class.getName());
 
    // --- Nuevos elementos para las animaciones de emocion ---
    private JPanel panelEfectos;
    private final List<float[]> particulasCohete = new ArrayList<>();
    private final List<float[]> particulasClic = new ArrayList<>();
    private final Color[] coloresClic = {
        new Color(255, 245, 150), // dorado
        new Color(120, 220, 255), // celeste
        new Color(255, 140, 220), // rosado
        Color.WHITE
    };
    private JLabel lblInvitacion;
 
    /**
     * Creates new form QuizViaLactea
     */
    public QuizViaLactea() {
        initComponents();
        try {
    java.net.URL urlSonido = getClass().getResource("/audio/BienvenidaLactea.wav");
    System.out.println("URL encontrada: " + urlSonido); // <-- linea nueva de diagnostico
    
    AudioInputStream audioStream = AudioSystem.getAudioInputStream(urlSonido);
    Clip clip = AudioSystem.getClip();
    clip.open(audioStream);
    clip.start();
            } catch (Exception e) {
    e.printStackTrace();
     }
        
    
         ResponsiveResizer resizer = new ResponsiveResizer(this);
         resizer.registerBackground(jLabelFondo);
 
         resizer.start();
 
    // --- Animacion del boton cohete: flota suavemente arriba y abajo ---
    final int posicionYOriginal = jBtnCohete.getY(); // guarda su posicion inicial
    final int amplitudPixeles = 10;   // que tanto sube y baja
    final double[] angulo = {0};     // usamos array para poder modificarlo dentro del lambda
 
    Timer timerCohete = new Timer(30, e -> {
        angulo[0] += 0.12;
        int offsetY = (int) (Math.sin(angulo[0]) * amplitudPixeles);
        jBtnCohete.setLocation(jBtnCohete.getX(), posicionYOriginal + offsetY);
 
        // --- Genera una nueva particula de estela debajo del cohete ---
        int xParticula = jBtnCohete.getX() + jBtnCohete.getWidth() / 2 - 5 + (int) (Math.random() * 10 - 5);
        int yParticula = jBtnCohete.getY() + (int) (jBtnCohete.getHeight() * 0.78); // cerca de la punta de la llama, dentro del area visible
        particulasCohete.add(new float[]{xParticula, yParticula, 220f, 14f});
 
        // --- Actualiza y elimina las particulas viejas ---
        Iterator<float[]> it = particulasCohete.iterator();
        while (it.hasNext()) {
            float[] p = it.next();
            p[1] += 1.5f;   // cae un poco
            p[2] -= 10f;    // se desvanece
            p[3] -= 0.3f;   // se encoge
            if (p[2] <= 0 || p[3] <= 0) {
                it.remove();
            }
        }
        if (panelEfectos != null) {
            panelEfectos.repaint();
        }
    });
    timerCohete.start();
    
    // --- Animacion de zoom (respiracion) para el nino y la nina ---
final ImageIcon iconoNinoOriginal = (ImageIcon) jLabelNino.getIcon();
final ImageIcon iconoNinaOriginal = (ImageIcon) jLabelNina.getIcon();
 
final int anchoNinoBase = jLabelNino.getWidth();
final int altoNinoBase  = jLabelNino.getHeight();
final int anchoNinaBase = jLabelNina.getWidth();
final int altoNinaBase  = jLabelNina.getHeight();
 
final int centroXNino = jLabelNino.getX() + anchoNinoBase / 2;
final int centroYNino = jLabelNino.getY() + altoNinoBase / 2;
final int centroXNina = jLabelNina.getX() + anchoNinaBase / 2;
final int centroYNina = jLabelNina.getY() + altoNinaBase / 2;
 
final double[] anguloZoom = {0};
final double amplitudZoom = 0.06; // que tanto crece/achica (6%). Sube a 0.10 para mas notorio.
 
javax.swing.Timer timerZoom = new javax.swing.Timer(30, e -> {
    anguloZoom[0] += 0.08; // velocidad del pulso
    double factor = 1.0 + amplitudZoom * Math.sin(anguloZoom[0]);
 
    int wNino = (int) (anchoNinoBase * factor);
    int hNino = (int) (altoNinoBase * factor);
    Image escaladoNino = iconoNinoOriginal.getImage().getScaledInstance(wNino, hNino, Image.SCALE_SMOOTH);
    jLabelNino.setIcon(new ImageIcon(escaladoNino));
    jLabelNino.setBounds(centroXNino - wNino / 2, centroYNino - hNino / 2, wNino, hNino);
 
    int wNina = (int) (anchoNinaBase * factor);
    int hNina = (int) (altoNinaBase * factor);
    Image escaladoNina = iconoNinaOriginal.getImage().getScaledInstance(wNina, hNina, Image.SCALE_SMOOTH);
    jLabelNina.setIcon(new ImageIcon(escaladoNina));
    jLabelNina.setBounds(centroXNina - wNina / 2, centroYNina - hNina / 2, wNina, hNina);
});
timerZoom.start();
 
    // ================== NUEVO: Estrellas titilando + estela del cohete ==================
    final int numEstrellas = 35;
    final Random rnd = new Random();
    final double[] estrellaX = new double[numEstrellas];
    final double[] estrellaY = new double[numEstrellas];
    final double[] estrellaFase = new double[numEstrellas];
    final double[] estrellaTam = new double[numEstrellas];
    for (int i = 0; i < numEstrellas; i++) {
        estrellaX[i] = rnd.nextDouble() * getWidth();
        estrellaY[i] = rnd.nextDouble() * getHeight();
        estrellaFase[i] = rnd.nextDouble() * Math.PI * 2;
        estrellaTam[i] = 6 + rnd.nextDouble() * 8; // destellos grandes, faciles de ver
    }
    final double[] faseGlobal = {0};
 
    panelEfectos = new JPanel() {
        @Override
        public boolean contains(int x, int y) {
            return false; // deja pasar los clics hacia los componentes de abajo (como jLabelFondo)
        }
 
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
 
            // Estrellas titilando (forma de destello en cruz, color dorado bien distinto del fondo)
            for (int i = 0; i < numEstrellas; i++) {
                double brillo = (Math.sin(faseGlobal[0] + estrellaFase[i]) + 1) / 2.0; // 0 a 1
                if (brillo < 0.35) {
                    continue; // parpadeo: a veces se "apaga" del todo
                }
                int alpha = (int) (brillo * 255);
                int cx = (int) estrellaX[i];
                int cy = (int) estrellaY[i];
                int tam = (int) (estrellaTam[i] * brillo);
 
                g2.setColor(new Color(255, 245, 150, alpha)); // dorado brillante
                // cuerpo del destello
                g2.fillOval(cx - tam / 4, cy - tam / 4, tam / 2, tam / 2);
                // rayos de la cruz
                g2.drawLine(cx - tam, cy, cx + tam, cy);
                g2.drawLine(cx, cy - tam, cx, cy + tam);
            }
 
            // Estela de particulas del cohete
            for (float[] p : particulasCohete) {
                int alpha = Math.max(0, Math.min(255, (int) p[2]));
                g2.setColor(new Color(255, 200, 80, alpha)); // color fuego/naranja
                int tam = (int) p[3];
                if (tam > 0) {
                    g2.fillOval((int) p[0], (int) p[1], tam, tam);
                }
            }
 
            // Explosion de destellos al tocar el fondo
            for (float[] p : particulasClic) {
                int alpha = Math.max(0, Math.min(255, (int) p[2]));
                Color base = coloresClic[(int) p[6]];
                g2.setColor(new Color(base.getRed(), base.getGreen(), base.getBlue(), alpha));
                int tam = (int) p[3];
                if (tam > 0) {
                    g2.fillOval((int) p[0] - tam / 2, (int) p[1] - tam / 2, tam, tam);
                }
            }
        }
    };
    panelEfectos.setOpaque(false);
    panelEfectos.setBounds(0, 0, Math.max(getWidth(), 1160), Math.max(getHeight(), 770));
    getContentPane().add(panelEfectos, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, Math.max(getWidth(), 1160), Math.max(getHeight(), 770)));
    // Lo colocamos justo encima del fondo, pero detras de botones y personajes
    getContentPane().setComponentZOrder(panelEfectos, getContentPane().getComponentCount() - 2);
 
    Timer timerEstrellas = new Timer(50, e -> {
        faseGlobal[0] += 0.05;
 
        // Actualiza la explosion de destellos al tocar el fondo
        Iterator<float[]> itClic = particulasClic.iterator();
        while (itClic.hasNext()) {
            float[] p = itClic.next();
            p[0] += p[4]; // se mueve en X segun su velocidad
            p[1] += p[5]; // se mueve en Y segun su velocidad
            p[2] -= 12f;  // se desvanece
            p[3] -= 0.15f; // se encoge
            if (p[2] <= 0 || p[3] <= 0) {
                itClic.remove();
            }
        }
 
        panelEfectos.repaint();
    });
    timerEstrellas.start();
 
    // ================== NUEVO: Efecto al tocar el fondo ==================
    jLabelFondo.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            generarExplosionEstrellas(evt.getX(), evt.getY());
        }
    });
 
    // ================== NUEVO: Texto animado (maquina de escribir + pulso) ==================
    // JLabel personalizado con contorno negro para que se lea bien sobre cualquier fondo
    lblInvitacion = new JLabel() {
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setFont(getFont());
            java.awt.FontMetrics fm = g2.getFontMetrics();
            String texto = getText();
            int textWidth = fm.stringWidth(texto);
            int x = (getWidth() - textWidth) / 2;
            int y = (getHeight() + fm.getAscent()) / 2 - 4;
 
            // Contorno negro (se dibuja el texto varias veces alrededor)
            g2.setColor(new Color(0, 0, 0, getForeground().getAlpha()));
            g2.drawString(texto, x - 2, y);
            g2.drawString(texto, x + 2, y);
            g2.drawString(texto, x, y - 2);
            g2.drawString(texto, x, y + 2);
 
            // Texto blanco encima
            g2.setColor(getForeground());
            g2.drawString(texto, x, y);
            g2.dispose();
        }
    };
    lblInvitacion.setFont(new java.awt.Font("Segoe UI Black", java.awt.Font.BOLD, 30));
    lblInvitacion.setForeground(Color.WHITE);
    lblInvitacion.setOpaque(false);
    lblInvitacion.setHorizontalAlignment(SwingConstants.CENTER);
    lblInvitacion.setBounds(330, 470, 500, 45);
    getContentPane().add(lblInvitacion, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 470, 500, 45));
    getContentPane().setComponentZOrder(lblInvitacion, 0); // siempre al frente, visible
 
    final String textoCompleto = "¡Toca el cohete para comenzar!";
    final int[] indiceLetra = {0};
 
    Timer timerEscritura = new Timer(80, null);
    timerEscritura.addActionListener(e -> {
        if (indiceLetra[0] <= textoCompleto.length()) {
            lblInvitacion.setText(textoCompleto.substring(0, indiceLetra[0]));
            indiceLetra[0]++;
        } else {
            timerEscritura.stop();
 
            // Cuando termina de escribirse, empieza a pulsar suavemente
            final double[] fasePulso = {0};
            Timer timerPulso = new Timer(40, ev -> {
                fasePulso[0] += 0.1;
                double brillo = (Math.sin(fasePulso[0]) + 1) / 2.0; // 0 a 1
                int alpha = (int) (140 + brillo * 115); // entre 140 y 255
                lblInvitacion.setForeground(new Color(255, 255, 255, alpha));
            });
            timerPulso.start();
        }
    });
    timerEscritura.start();
 
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelNino = new javax.swing.JLabel();
        jLabelNina = new javax.swing.JLabel();
        jBtnCohete = new javax.swing.JButton();
        jLabelFondo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabelNino.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/NiñoViaLactea (1).png"))); // NOI18N
        jLabelNino.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelNinoMouseClicked(evt);
            }
        });
        getContentPane().add(jLabelNino, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 460, 260, 250));

        jLabelNina.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/NiñaViaLactea1.png"))); // NOI18N
        getContentPane().add(jLabelNina, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 450, 230, 280));

        jBtnCohete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/CoheteViaLactea (1).png"))); // NOI18N
        jBtnCohete.setBorderPainted(false);
        jBtnCohete.setContentAreaFilled(false);
        jBtnCohete.addActionListener(this::jBtnCoheteActionPerformed);
        getContentPane().add(jBtnCohete, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 430, 130, 360));

        jLabelFondo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/Lactea1 (1).png"))); // NOI18N
        getContentPane().add(jLabelFondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1160, 770));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBtnCoheteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnCoheteActionPerformed
   QuizViaLactea1 quiz = new QuizViaLactea1();
    quiz.setVisible(true);
    this.dispose();     }//GEN-LAST:event_jBtnCoheteActionPerformed

    private void jLabelNinoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelNinoMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabelNinoMouseClicked

    
        /**
     * Crea una pequena explosion de destellos de colores en el punto (x,y)
     * cuando el usuario toca el fondo. Es puramente decorativo, no avanza de pantalla.
     */
    private void generarExplosionEstrellas(int x, int y) {
        Random r = new Random();
        int cantidad = 18;
        for (int i = 0; i < cantidad; i++) {
            double angulo = r.nextDouble() * Math.PI * 2;
            double velocidad = 2 + r.nextDouble() * 4;
            float vx = (float) (Math.cos(angulo) * velocidad);
            float vy = (float) (Math.sin(angulo) * velocidad);
            float tam = 8 + r.nextFloat() * 6;
            int colorIndex = r.nextInt(coloresClic.length);
            particulasClic.add(new float[]{x, y, 255f, tam, vx, vy, colorIndex});
        }
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
        java.awt.EventQueue.invokeLater(() -> new QuizViaLactea().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnCohete;
    private javax.swing.JLabel jLabelFondo;
    private javax.swing.JLabel jLabelNina;
    private javax.swing.JLabel jLabelNino;
    // End of variables declaration//GEN-END:variables
}
