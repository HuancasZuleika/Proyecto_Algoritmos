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
 
    /**
     * Anima la aparición de un JDialog con un efecto "zoom" (pop),
     * creciendo desde el centro hasta su tamaño final. Le da un
     * aire de "juego" en vez de una ventana estática.
     */
    private static void animarZoomEntrada(javax.swing.JDialog dialog, int anchoFinal, int altoFinal) {
        java.awt.Point centro = dialog.getLocation();
        centro.translate(anchoFinal / 2, altoFinal / 2);
 
        dialog.setSize(1, 1);
        dialog.setLocation(centro.x, centro.y);
 
        final int pasos = 10;
        final int[] paso = {0};
        javax.swing.Timer timer = new javax.swing.Timer(15, null);
        timer.addActionListener(e -> {
            if (!dialog.isDisplayable()) {
                ((javax.swing.Timer) e.getSource()).stop();
                return;
            }
            paso[0]++;
            double avance = Math.min(1.0, paso[0] / (double) pasos);
            double suavizado = 1 - Math.pow(1 - avance, 3); // ease-out
            int w = Math.max(1, (int) (anchoFinal * suavizado));
            int h = Math.max(1, (int) (altoFinal * suavizado));
            dialog.setSize(w, h);
            dialog.setLocation(centro.x - w / 2, centro.y - h / 2);
            if (paso[0] >= pasos) {
                dialog.setSize(anchoFinal, altoFinal);
                dialog.setLocation(centro.x - anchoFinal / 2, centro.y - altoFinal / 2);
                ((javax.swing.Timer) e.getSource()).stop();
            }
        });
        timer.start();
    }
 
    /**
     * Programa un pequeño "temblor" (feedback de error) que arranca justo
     * después de que termina el zoom de entrada.
     */
    private static void programarTemblor(javax.swing.JDialog dialog, int retrasoMs) {
        javax.swing.Timer espera = new javax.swing.Timer(retrasoMs, null);
        espera.setRepeats(false);
        espera.addActionListener(e -> animarTemblor(dialog));
        espera.start();
    }
 
    private static void animarTemblor(javax.swing.JDialog dialog) {
        if (!dialog.isDisplayable()) {
            return;
        }
        final java.awt.Point base = dialog.getLocation();
        final int totalPasos = 8;
        final int[] paso = {0};
        javax.swing.Timer timer = new javax.swing.Timer(30, null);
        timer.addActionListener(e -> {
            if (!dialog.isDisplayable()) {
                ((javax.swing.Timer) e.getSource()).stop();
                return;
            }
            paso[0]++;
            int amplitud = (int) Math.round(7 * (1.0 - (paso[0] / (double) totalPasos)));
            int offset = (paso[0] % 2 == 0) ? amplitud : -amplitud;
            dialog.setLocation(base.x + offset, base.y);
            if (paso[0] >= totalPasos) {
                dialog.setLocation(base.x, base.y);
                ((javax.swing.Timer) e.getSource()).stop();
            }
        });
        timer.start();
    }
 
 
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
            juego.actualizarPuntosEnPantalla();
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
    // IMPORTANTE: se agrega AL FINAL (después de los textos y el botón),
    // porque en un JPanel con layout(null) el primer componente agregado
    // queda AL FRENTE. Si el fondo se agrega primero, tapa todo lo demás.
   javax.swing.JLabel lblFondoImg = new javax.swing.JLabel();
    lblFondoImg.setBounds(0, 0, 349, 215);
    
    // CAMBIA ESTA LÍNEA por la ruta exacta que copiaste en el Paso 1:
    java.net.URL urlImagen = getClass().getResource("/imagen/Ventana (1).png");
    
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
    animarZoomEntrada(dialog, 349, 215);
    programarTemblor(dialog, 220);
    dialog.setVisible(true);
}
 
    /**
     * Ventana personalizada de "¡Correcto!" (tipo juego) para reemplazar
     * el JOptionPane simple cuando el niño acierta la respuesta.
     */
    public void mostrarExitoPersonalizado(java.awt.Frame juego, double puntosGanados) {
        mostrarExitoPersonalizado(juego, puntosGanados, null);
    }
 
    public void mostrarExitoPersonalizado(java.awt.Frame juego, double puntosGanados, String mensajePersonalizado) {
        javax.swing.JDialog dialog = new javax.swing.JDialog(juego, true);
        dialog.setUndecorated(true);
        dialog.setSize(380, 230);
        dialog.setLocationRelativeTo(juego);
        dialog.setBackground(new java.awt.Color(0, 0, 0, 0));
 
        javax.swing.JPanel panelFondo = new javax.swing.JPanel();
        panelFondo.setLayout(null);
        panelFondo.setOpaque(false);
 
        javax.swing.JLabel lblMascota = new javax.swing.JLabel();
        lblMascota.setBounds(12, 65, 95, 120);
        java.net.URL urlMascota = getClass().getResource("/imagen/muñeco_correcto.png");
        if (urlMascota == null) {
            urlMascota = getClass().getResource("/imagen/feliz.png");
        }
        if (urlMascota != null) {
            Image mascotaEscalada = new javax.swing.ImageIcon(urlMascota).getImage()
                    .getScaledInstance(95, 120, Image.SCALE_SMOOTH);
            lblMascota.setIcon(new javax.swing.ImageIcon(mascotaEscalada));
        }
        panelFondo.add(lblMascota);
 
        // Botón de cierre (X)
        javax.swing.JButton btnCerrar = new javax.swing.JButton("X");
        btnCerrar.setBounds(170, 10, 40, 40);
        btnCerrar.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 20));
        btnCerrar.setForeground(java.awt.Color.YELLOW);
        btnCerrar.setBackground(new java.awt.Color(30, 60, 30));
        btnCerrar.setFocusPainted(false);
        btnCerrar.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.ORANGE, 2));
        btnCerrar.addActionListener(e -> dialog.dispose());
        panelFondo.add(btnCerrar);
 
        // Título dorado - empieza DESPUÉS de la mascota (x=115) para no taparla
        javax.swing.JLabel lblTitulo = new javax.swing.JLabel("¡Correcto! +" + GameData.formatearPuntos(puntosGanados) + " Puntos", javax.swing.SwingConstants.CENTER);
        lblTitulo.setBounds(112, 58, 258, 28);
        lblTitulo.setFont(new java.awt.Font("Comic Sans MS", java.awt.Font.BOLD, 17));
        lblTitulo.setForeground(new java.awt.Color(255, 215, 0));
        panelFondo.add(lblTitulo);
 
        // Mensaje - también empieza DESPUÉS de la mascota
        String textoExplicativo = mensajePersonalizado != null ? mensajePersonalizado :
              "<html><center> Muy bien ¡LO LOGRASTE! <br>"
            + "Gracias ha esto haz GANADO " + GameData.formatearPuntos(puntosGanados) + " PUNTOS¡¡¡<br>"
            + "<b> VAMOS BIENNNN¡¡</b></center></html>";
 
        javax.swing.JLabel lblMensaje = new javax.swing.JLabel(textoExplicativo, javax.swing.SwingConstants.CENTER);
        lblMensaje.setBounds(112, 92, 258, 110);
        lblMensaje.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 13));
        lblMensaje.setForeground(java.awt.Color.WHITE); 
        panelFondo.add(lblMensaje);
 
        // Fondo (reutilizamos el mismo marco decorado)
        // IMPORTANTE: se agrega AL FINAL, después de mascota/botón/título/mensaje,
        // para que quede DETRÁS de ellos y no los tape (ver nota en mostrarErrorPersonalizado).
        javax.swing.JLabel lblFondoImg = new javax.swing.JLabel();
        lblFondoImg.setBounds(0, 0, 380, 230);
        java.net.URL urlFondo = getClass().getResource("/imagen/Ventana (1).png");
        if (urlFondo != null) {
            Image fondoEscalado = new javax.swing.ImageIcon(urlFondo).getImage()
                    .getScaledInstance(380, 230, Image.SCALE_SMOOTH);
            lblFondoImg.setIcon(new javax.swing.ImageIcon(fondoEscalado));
        } else {
            panelFondo.setOpaque(true);
            panelFondo.setBackground(new java.awt.Color(20, 40, 30));
            panelFondo.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.YELLOW, 2));
        }
        panelFondo.add(lblFondoImg);
 
        // Lluvia de estrellitas doradas (efecto "confeti" de victoria)
        // También se agrega DESPUÉS del fondo pero ANTES que el resto ya fue
        // agregado arriba, así que va detrás de los textos/botón/mascota y
        // encima del fondo, tal como se espera visualmente.
        final java.net.URL urlEstrella = getClass().getResource("/imagen/ESTRELLQAQ1-removebg-preview.png");
        final Image imgEstrella = (urlEstrella != null) ? new javax.swing.ImageIcon(urlEstrella).getImage() : null;
 
        if (imgEstrella != null) {
            final int numEstrellas = 20;
            final double[][] particulas = new double[numEstrellas][3]; // x, y, velocidad
            final java.util.Random rnd = new java.util.Random();
            for (int i = 0; i < numEstrellas; i++) {
                particulas[i][0] = rnd.nextInt(380);
                particulas[i][1] = -rnd.nextInt(230);
                particulas[i][2] = 3.5 + rnd.nextDouble() * 5.5;
            }
 
            javax.swing.JPanel panelEstrellas = new javax.swing.JPanel(null) {
                @Override
                protected void paintComponent(java.awt.Graphics g) {
                    super.paintComponent(g);
                    for (double[] p : particulas) {
                        g.drawImage(imgEstrella, (int) p[0], (int) p[1], 16, 16, this);
                    }
                }
            };
            panelEstrellas.setOpaque(false);
            panelEstrellas.setBounds(0, 0, 380, 230);
            panelFondo.add(panelEstrellas);
            // Empujamos el panel de estrellas justo detrás de los textos/botón/mascota,
            // pero delante del fondo, para que se vean "sobre" el marco sin tapar el mensaje.
            panelFondo.setComponentZOrder(panelEstrellas, panelFondo.getComponentCount() - 2);
 
            javax.swing.Timer timerEstrellas = new javax.swing.Timer(40, null);
            timerEstrellas.addActionListener(e -> {
                if (!dialog.isDisplayable()) {
                    ((javax.swing.Timer) e.getSource()).stop();
                    return;
                }
                for (double[] p : particulas) {
                    p[1] += p[2];
                    if (p[1] > 230) {
                        p[1] = -20;
                        p[0] = rnd.nextInt(380);
                    }
                }
                panelEstrellas.repaint();
            });
            timerEstrellas.start();
 
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    timerEstrellas.stop();
                }
            });
        }
 
        dialog.setContentPane(panelFondo);
        animarZoomEntrada(dialog, 380, 230);
        dialog.setVisible(true);
    }
 
    /**
     * Ventana personalizada de "Pista" para reemplazar el JOptionPane
     * cuando el niño pide ayuda.
     */
    public void mostrarPistaPersonalizada(java.awt.Frame juego, String pista) {
        javax.swing.JDialog dialog = new javax.swing.JDialog(juego, true);
        dialog.setUndecorated(true);
        dialog.setSize(360, 220);
        dialog.setLocationRelativeTo(juego);
        dialog.setBackground(new java.awt.Color(0, 0, 0, 0));
 
        javax.swing.JPanel panelFondo = new javax.swing.JPanel();
        panelFondo.setLayout(null);
        panelFondo.setOpaque(false);
 
        javax.swing.JButton btnCerrar = new javax.swing.JButton("X");
        btnCerrar.setBounds(160, 12, 40, 40);
        btnCerrar.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 20));
        btnCerrar.setForeground(java.awt.Color.YELLOW);
        btnCerrar.setBackground(new java.awt.Color(30, 30, 80));
        btnCerrar.setFocusPainted(false);
        btnCerrar.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.CYAN, 2));
        btnCerrar.addActionListener(e -> dialog.dispose());
        panelFondo.add(btnCerrar);
 
        javax.swing.JLabel lblTitulo = new javax.swing.JLabel("✨ Pista Espacial ✨", javax.swing.SwingConstants.CENTER);
        lblTitulo.setBounds(10, 65, 339, 25);
        lblTitulo.setFont(new java.awt.Font("Comic Sans MS", java.awt.Font.BOLD, 18));
        lblTitulo.setForeground(java.awt.Color.CYAN);
        panelFondo.add(lblTitulo);
 
        javax.swing.JLabel lblMensaje = new javax.swing.JLabel(
                "<html><center>" + pista + "</center></html>", javax.swing.SwingConstants.CENTER);
        lblMensaje.setBounds(15, 100, 329, 90);
        lblMensaje.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 14));
        lblMensaje.setForeground(java.awt.Color.WHITE);
        panelFondo.add(lblMensaje);
 
        // Fondo: se agrega AL FINAL para que quede detrás del título/mensaje/botón.
        javax.swing.JLabel lblFondoImg = new javax.swing.JLabel();
        lblFondoImg.setBounds(0, 0, 360, 220);
        java.net.URL urlFondo = getClass().getResource("/imagen/Ventana (1).png");
        if (urlFondo != null) {
            Image fondoEscalado = new javax.swing.ImageIcon(urlFondo).getImage()
                    .getScaledInstance(360, 220, Image.SCALE_SMOOTH);
            lblFondoImg.setIcon(new javax.swing.ImageIcon(fondoEscalado));
        } else {
            panelFondo.setOpaque(true);
            panelFondo.setBackground(new java.awt.Color(30, 20, 50));
            panelFondo.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.CYAN, 2));
        }
        panelFondo.add(lblFondoImg);
 
        dialog.setContentPane(panelFondo);
        animarZoomEntrada(dialog, 360, 220);
        dialog.setVisible(true);
    }
 
    /**
     * Ventana genérica de "respuesta incorrecta" (mismo estilo visual que
     * mostrarErrorPersonalizado), pero sin depender de QuizViaLactea1 ni
     * tocar puntos directamente: el que llama ya debe haber restado los
     * puntos antes de mostrar este mensaje.
     */
    public void mostrarErrorSimplePersonalizado(java.awt.Frame juego, String mensaje) {
        javax.swing.JDialog dialog = new javax.swing.JDialog(juego, true);
        dialog.setUndecorated(true);
        dialog.setSize(349, 215);
        dialog.setLocationRelativeTo(juego);
        dialog.setBackground(new java.awt.Color(0, 0, 0, 0));
 
        javax.swing.JPanel panelFondo = new javax.swing.JPanel();
        panelFondo.setLayout(null);
        panelFondo.setOpaque(false);
 
        javax.swing.JButton btnCerrar = new javax.swing.JButton("X");
        btnCerrar.setBounds(154, 12, 40, 40);
        btnCerrar.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 20));
        btnCerrar.setForeground(java.awt.Color.YELLOW);
        btnCerrar.setBackground(new java.awt.Color(100, 30, 30));
        btnCerrar.setFocusPainted(false);
        btnCerrar.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.ORANGE, 2));
        btnCerrar.addActionListener(e -> dialog.dispose());
        panelFondo.add(btnCerrar);
 
        javax.swing.JLabel lblTitulo = new javax.swing.JLabel("¡Casi!", javax.swing.SwingConstants.CENTER);
        lblTitulo.setBounds(10, 65, 329, 25);
        lblTitulo.setFont(new java.awt.Font("Comic Sans MS", java.awt.Font.BOLD, 18));
        lblTitulo.setForeground(java.awt.Color.WHITE);
        panelFondo.add(lblTitulo);
 
        javax.swing.JLabel lblMensaje = new javax.swing.JLabel(
                "<html><center>" + mensaje + "</center></html>", javax.swing.SwingConstants.CENTER);
        lblMensaje.setBounds(15, 100, 319, 90);
        lblMensaje.setFont(new java.awt.Font("Arial", java.awt.Font.PLAIN, 14));
        lblMensaje.setForeground(java.awt.Color.WHITE);
        panelFondo.add(lblMensaje);
 
        javax.swing.JLabel lblFondoImg = new javax.swing.JLabel();
        lblFondoImg.setBounds(0, 0, 349, 215);
        java.net.URL urlImagen = getClass().getResource("/imagen/Ventana (1).png");
        if (urlImagen != null) {
            lblFondoImg.setIcon(new javax.swing.ImageIcon(urlImagen));
        } else {
            panelFondo.setOpaque(true);
            panelFondo.setBackground(new java.awt.Color(30, 20, 50));
            panelFondo.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.YELLOW, 2));
        }
        panelFondo.add(lblFondoImg);
 
        dialog.setContentPane(panelFondo);
        animarZoomEntrada(dialog, 349, 215);
        programarTemblor(dialog, 220);
        dialog.setVisible(true);
    }
}