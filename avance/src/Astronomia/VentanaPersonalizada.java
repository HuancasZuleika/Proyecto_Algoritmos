/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Astronomia;

 
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
 
/**
 * Ventana emergente personalizada (sin barra de titulo de Windows,
 * con colores propios) para reemplazar JOptionPane y que no se note
 * que es "un formulario de Java".
 *
 * USO - pregunta con Si/No (para la pista):
 *
 *   boolean quierePista = VentanaPersonalizada.mostrarPregunta(
 *       this,
 *       "¡Oops! Incorrecto",
 *       "¿Necesitas una pista?\n(cuesta 2 puntos, sin pista pierdes 3)"
 *   );
 *
 * USO - solo mensaje informativo (para mostrar la pista, o "correcto"):
 *
 *   VentanaPersonalizada.mostrarMensaje(
 *       this,
 *       "Aquí está tu pista",
 *       "Nuestra casa no tiene anillos, y no es el Sol ni la Luna..."
 *   );
 */
public class VentanaPersonalizada {


// Método para mostrar el cuadro de diálogo personalizado de error
// Agregamos "QuizViaLactea juego" para poder acceder a sus puntos
public void mostrarErrorPersonalizado(QuizViaLactea1 juego, javax.swing.JButton botonPuntos) {
    // 1. Crear el JDialog modal con el tamaño exacto de tu imagen (349 x 215)
    javax.swing.JDialog dialog = new javax.swing.JDialog(juego, true);
    dialog.setUndecorated(true); 
    dialog.setSize(349, 215); 
    dialog.setLocationRelativeTo(juego); 
    
    // Hace que los bordes sobrantes del cuadro de Windows sean transparentes
    dialog.setBackground(new java.awt.Color(0, 0, 0, 0)); 

    // 2. Panel principal transparente
    javax.swing.JPanel panelFondo = new javax.swing.JPanel();
    panelFondo.setLayout(null);
    panelFondo.setOpaque(false); 

    // 3. Botón de cierre (X) - Centrado horizontalmente (154) para el círculo de arriba
    javax.swing.JButton btnCerrar = new javax.swing.JButton("X");
    btnCerrar.setBounds(154, 12, 40, 40); 
    btnCerrar.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 20));
    btnCerrar.setForeground(java.awt.Color.YELLOW);
    btnCerrar.setBackground(new java.awt.Color(100, 30, 30));
    btnCerrar.setFocusPainted(false);
    btnCerrar.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.ORANGE, 2));
    
    btnCerrar.addActionListener(new java.awt.event.ActionListener() {
        @Override
        public void actionPerformed(java.awt.event.ActionEvent e) {
            juego.puntos -= 3; 
            botonPuntos.setText(String.valueOf(juego.puntos)); 
            dialog.dispose(); 
        }
    });
    panelFondo.add(btnCerrar);

    // 4. Texto del Título (Blanco) - Ajustado al ancho de 349
    javax.swing.JLabel lblTitulo = new javax.swing.JLabel("¡Misión Interrumpida!", javax.swing.SwingConstants.CENTER);
    lblTitulo.setBounds(10, 65, 329, 25);
    lblTitulo.setFont(new java.awt.Font("Comic Sans MS", java.awt.Font.BOLD, 18)); // Reducido un punto para que quepa bien en los 349px
    lblTitulo.setForeground(java.awt.Color.WHITE); 
    panelFondo.add(lblTitulo);

    // 5. Texto explicativo del mensaje (Blanco) - Ajustado al ancho de 349
    String textoExplicativo = "<html><center>Parece que has elegido mal.<br>"
            + "Para obtener una pista, por favor,<br>"
            + "<b>dirígete al botón de 'HELP'</b></center></html>";

    javax.swing.JLabel lblMensaje = new javax.swing.JLabel(textoExplicativo, javax.swing.SwingConstants.CENTER);
    lblMensaje.setBounds(15, 100, 319, 90);
    lblMensaje.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 14)); // Reducido un poco para equilibrar el nuevo espacio
    lblMensaje.setForeground(java.awt.Color.WHITE); 
    panelFondo.add(lblMensaje);

    // 6. Imagen de fondo calibrada a 349 x 215
   javax.swing.JLabel lblFondoImg = new javax.swing.JLabel();
    lblFondoImg.setBounds(0, 0, 349, 215);
    
    // CAMBIA ESTA LÍNEA por la ruta exacta que copiaste en el Paso 1:
    java.net.URL urlImagen = getClass().getResource("/Astronomia/imagen/Ventana (1).png");
    
    if (urlImagen != null) {
        lblFondoImg.setIcon(new javax.swing.ImageIcon(urlImagen));
    } else {
        // Esto solo se ejecuta si la ruta sigue estando mal escrita
        panelFondo.setOpaque(true);
        panelFondo.setBackground(new java.awt.Color(30, 20, 50));
        panelFondo.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.YELLOW, 2));
    }
    panelFondo.add(lblFondoImg);

    dialog.setContentPane(panelFondo);
    dialog.setVisible(true);
}
}