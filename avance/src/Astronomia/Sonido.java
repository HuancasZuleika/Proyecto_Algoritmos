/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Astronomia;

import javax.sound.sampled.*;
import java.net.URL;

/**
 *
 * @author jharleth
 */
public class Sonido {

    private static Clip musica;

    public static void reproducir(String archivo) {
        try {
            URL url = Sonido.class.getResource("/audio/" + archivo);

            if (url == null) {
                System.out.println("No se encontró el sonido: " + archivo);
                return;
            }

            AudioInputStream audio = AudioSystem.getAudioInputStream(url);
            Clip clip = AudioSystem.getClip();
            clip.open(audio);
            clip.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void reproducirMusica(String archivo, float decibelios) {
        try {
            if (musica != null && musica.isRunning()) {
                return;
            }

            URL url = Sonido.class.getResource("/audio/" + archivo);

            if (url == null) {
                System.out.println("No se encontró la música: " + archivo);
                return;
            }

            AudioInputStream audio = AudioSystem.getAudioInputStream(url);

            musica = AudioSystem.getClip();
            musica.open(audio);

            // AJUSTE DE VOLUMEN PARA LA MÚSICA DE FONDO
            if (musica.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                FloatControl gainControl = (FloatControl) musica.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(decibelios);
            }

            musica.loop(Clip.LOOP_CONTINUOUSLY);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void reproducirMusica(String archivo) {
        reproducirMusica(archivo, -18.0f); // Baja por defecto unos -18 dB
    }

    public static void detenerMusica() {
        if (musica != null) {
            musica.stop();
            musica.close();
            musica = null;
        }
    }

}
