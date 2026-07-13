/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package menu_p;

/**
 *
 * @author sayuri
 */
import formularios.menu;
import javax.swing.*;
import java.awt.*;

public class MiniExploradores extends JFrame {

    private JProgressBar barra;
    private JLabel porcentaje;

    public MiniExploradores() {
        
        

        // ANCHO Y ALTURA DE LA VENTANA
        setTitle("Carga");
        setSize(969, 745);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        FondoPanel fondo = new FondoPanel();
        fondo.setLayout(null);

        // =========================================
        // BARRA DE CARGA
        // =========================================
        barra = new JProgressBar();
// Cambiado de 340 a 320 para subirla un poco
        barra.setBounds(270, 320, 340, 25);
        barra.setMinimum(0);
        barra.setMaximum(100);
        barra.setForeground(new Color(180, 0, 255));
        barra.setBackground(new Color(40, 40, 40));
        barra.setBorder(BorderFactory.createLineBorder(new Color(120, 255, 255), 3));
        barra.setStringPainted(false);

        porcentaje = new JLabel("0%");
        porcentaje.setFont(new Font("Arial", Font.BOLD, 18));
        porcentaje.setForeground(Color.WHITE);
// Cambiado de 370 a 350 para mantener la distancia con la barra
        porcentaje.setBounds(420, 350, 80, 30);

        fondo.add(barra);
        fondo.add(porcentaje);

        fondo.add(barra);
        fondo.add(porcentaje);

        add(fondo);

        iniciarCarga();
    }

    // ANIMACION DE CARGA
    private void iniciarCarga() {

        Thread hilo = new Thread(() -> {

            for (int i = 0; i <= 100; i++) {

                barra.setValue(i);
                porcentaje.setText(i + "%");

                try {

                    Thread.sleep(45);

                } catch (InterruptedException e) {

                    e.printStackTrace();
                }
            }

            new menu().setVisible(true);

            dispose();

        });

        hilo.start();
    }

    class FondoPanel extends JPanel {

        private Image imagen;

        public FondoPanel() {

            java.net.URL ruta
                    = getClass().getResource("/Imagen/PPP.png");

            if (ruta != null) {

                imagen = new ImageIcon(ruta).getImage();

            } else {

                System.out.println(
                        "No se encontró la imagen");
            }
        }

        @Override
        protected void paintComponent(Graphics g) {

            super.paintComponent(g);

            g.drawImage(
                    imagen,
                    0,
                    0,
                    getWidth(),
                    getHeight(),
                    this
            );
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            new MiniExploradores().setVisible(true);

        });
    }
}
