package Astronomia;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.Timer;
 
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
 
/**
 *
 * @author sayuri
 */
public class QuizViaLactea4 extends javax.swing.JFrame {
    //Animaciones
    private Timer animacionAyuda;
    private Timer animacionSaturno;
    private Timer animacionBotones;
 
    //Posiciones
    private int ayudaY;
 
    //Para Saturno
    private double angulo = 0;
 
    //Imagen original
    private Image imagenSaturno;
    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(QuizViaLactea4.class.getName());
 
    //Puntos
    private int puntos = 10;
 
    public QuizViaLactea4() {
        initComponents();
        ayudaY = jBtnAyuda.getY();
 
        imagenSaturno = new ImageIcon(
                getClass().getResource("/imagen/Saturno (1) (1).png")
        ).getImage();
 
        iniciarAnimaciones();
 
        // --- Asignamos qué botón responde qué, igual que en el Quiz1 ---
        jButton1.addActionListener(this::jButton1ActionPerformed); // SATURNO (correcta)
        jBtnSo.addActionListener(this::jBtnSoActionPerformed);       // SOL (incorrecta)
        jBtnvia.addActionListener(this::jBtnviaActionPerformed);     // VIA LACTEA (incorrecta)
    }
 
    private void iniciarAnimaciones(){
 
        animarAyuda();
 
        animarSaturno();
 
    }
 
    private void animarAyuda(){
        efectoHover(jBtnAyuda);
        efectoHover(jButton1);
        efectoHover(jBtnvia);
        efectoHover(jBtnSo);
 
        animacionAyuda = new Timer(35, new ActionListener(){
 
            boolean abajo=true;
            int mover=0;
 
            @Override
            public void actionPerformed(ActionEvent e){
 
                if(abajo){
 
                    mover++;
 
                    if(mover>=8)
                        abajo=false;
 
                }else{
 
                    mover--;
 
                    if(mover<=0)
                        abajo=true;
 
                }
 
                jBtnAyuda.setLocation(
                        jBtnAyuda.getX(),
                        ayudaY+mover
                );
 
            }
 
        });
 
        animacionAyuda.start();
 
    }
 
    // Saturno gira despacio sobre si mismo, como un disco
    private void animarSaturno(){
 
        final int w = jBtnSATURNO.getWidth();
        final int h = jBtnSATURNO.getHeight();
 
        animacionSaturno = new Timer(40, e -> {
 
            angulo += 1.2; // grados que gira en cada paso (mientras mas chico, mas lento)
 
            BufferedImage rotada = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = rotada.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.rotate(Math.toRadians(angulo), w / 2.0, h / 2.0);
            g2.drawImage(imagenSaturno, 0, 0, w, h, null);
            g2.dispose();
 
            jBtnSATURNO.setIcon(new ImageIcon(rotada));
 
        });
 
        animacionSaturno.start();
 
    }
 
    private void efectoHover(JButton boton){
 
        boton.addMouseListener(new MouseAdapter(){
 
            @Override
            public void mouseEntered(MouseEvent e){
 
                boton.setSize(
 
                        boton.getWidth()+8,
 
                        boton.getHeight()+8
 
                );
 
            }
 
            @Override
            public void mouseExited(MouseEvent e){
 
                boton.setSize(
 
                        boton.getWidth()-8,
 
                        boton.getHeight()-8
 
                );
 
            }
 
        });
 
    }
 
    // ================== Respuestas: correcta / incorrecta ==================
 
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        // Es la opción "SATURNO" -> correcta
        marcarBoton(jButton1, true);
 
        int ganados = 10;
        puntos += ganados;
        actualizarPuntosEnPantalla();
 
        VentanaPersonalizada vp = new VentanaPersonalizada();
        vp.mostrarExitoPersonalizado(this, ganados);
    }
 
    private void jBtnSoActionPerformed(java.awt.event.ActionEvent evt) {
        // Es la opción "SOL" -> incorrecta
        marcarBoton(jBtnSo, false);
        manejarRespuestaIncorrecta();
    }
 
    private void jBtnviaActionPerformed(java.awt.event.ActionEvent evt) {
        // Es la opción "VIA LACTEA" -> incorrecta
        marcarBoton(jBtnvia, false);
        manejarRespuestaIncorrecta();
    }
 
    private void manejarRespuestaIncorrecta(){
        puntos -= 3;
        actualizarPuntosEnPantalla();
 
        VentanaPersonalizada vp = new VentanaPersonalizada();
        vp.mostrarErrorSimplePersonalizado(this, "Esa no es la respuesta correcta, intenta de nuevo.");
    }
 
    // Pinta un borde de color alrededor del botón: verde si es correcta, rojo si no
    private void marcarBoton(JButton boton, boolean esCorrecta){
        boton.setBorderPainted(true);
        if (esCorrecta) {
            boton.setBorder(BorderFactory.createLineBorder(new Color(0, 200, 0), 6));
        } else {
            boton.setBorder(BorderFactory.createLineBorder(new Color(200, 0, 0), 6));
        }
    }
 
    private void actualizarPuntosEnPantalla(){
        jBtnP.setText(String.valueOf(puntos));
    }
 
    // ==========================================================================
 
   /*
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jBtnSo = new javax.swing.JButton();
        jBtnSATURNO = new javax.swing.JButton();
        jBtnvia = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jBtnAyuda = new javax.swing.JButton();
        jBtnP = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jBtnSo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/OpcionSo (1) (1).png"))); // NOI18N
        jBtnSo.setBorderPainted(false);
        jBtnSo.setContentAreaFilled(false);
        getContentPane().add(jBtnSo, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 650, -1, 60));

        jBtnSATURNO.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/Saturno (1) (1).png"))); // NOI18N
        jBtnSATURNO.setBorderPainted(false);
        jBtnSATURNO.setContentAreaFilled(false);
        jBtnSATURNO.addActionListener(this::jBtnSATURNOActionPerformed);
        getContentPane().add(jBtnSATURNO, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 270, 420, 270));

        jBtnvia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/OpcionV (1).png"))); // NOI18N
        jBtnvia.setBorderPainted(false);
        jBtnvia.setContentAreaFilled(false);
        getContentPane().add(jBtnvia, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 560, -1, 70));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/OpcionS (1).png"))); // NOI18N
        jButton1.setBorderPainted(false);
        jButton1.setContentAreaFilled(false);
        getContentPane().add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 560, -1, 70));

        jBtnAyuda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/Ayuda (1) (1).png"))); // NOI18N
        jBtnAyuda.setBorderPainted(false);
        jBtnAyuda.setContentAreaFilled(false);
        jBtnAyuda.addActionListener(this::jBtnAyudaActionPerformed);
        getContentPane().add(jBtnAyuda, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 330, -1, -1));

        jBtnP.setBackground(new java.awt.Color(51, 0, 51));
        jBtnP.setBorderPainted(false);
        getContentPane().add(jBtnP, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 130, 110, 50));

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/Acumulador (1).png"))); // NOI18N
        jButton2.setBorderPainted(false);
        jButton2.setContentAreaFilled(false);
        jButton2.addActionListener(this::jButton2ActionPerformed);
        getContentPane().add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 50, -1, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/Lactea6 (1).png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 1150, 780));

        jButton4.setText("jButton4");
        getContentPane().add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 180, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jBtnSATURNOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSATURNOActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jBtnSATURNOActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jBtnAyudaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnAyudaActionPerformed



    }//GEN-LAST:event_jBtnAyudaActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new QuizViaLactea4().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBtnAyuda;
    private javax.swing.JButton jBtnP;
    private javax.swing.JButton jBtnSATURNO;
    private javax.swing.JButton jBtnSo;
    private javax.swing.JButton jBtnvia;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
