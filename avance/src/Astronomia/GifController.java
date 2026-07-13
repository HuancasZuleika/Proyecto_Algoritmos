/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Astronomia;

import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 *
 * @author User
 */
public class GifController {
    public static void cargarGif(JLabel label, String nombreGif) {
        String rutaCompleta = "/gif/" + nombreGif;
        URL rutaResource = label.getClass().getResource(rutaCompleta);

        if (rutaResource != null) {
            ImageIcon gifIcono = new ImageIcon(rutaResource);
            label.setIcon(gifIcono);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setVerticalAlignment(SwingConstants.CENTER);
            label.repaint();
        } else {
            System.err.println("Error: No se pudo encontrar el GIF en: " + rutaCompleta);
        }
    }
}
