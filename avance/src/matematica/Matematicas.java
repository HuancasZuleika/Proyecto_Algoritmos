
package matematica;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.InputStream;

public class Matematicas {
    public final String TEMA1 = "Teoría de Conjuntos";
    public final String TEMA2 = "Operaciones Combinadas";
    public final String TEMA3 = "Teorema de Pitágoras";
    public final String TEMA4 = "Área de Figuras";
    
    // Método para obtener una frase motivadora según el tema
    public String getBienvenida(String tema) {
        return "¡Bienvenido al mundo de " + tema + ", pequeño explorador!";
    }
    
    public void reproducirAudio(String nombreArchivo) {
    try {
            InputStream audioSrc = getClass().getResourceAsStream("/audio/" + nombreArchivo);        if (audioSrc == null) {
            if (audioSrc == null) {
                System.out.println("No se encontró el archivo de audio");
            return;
            }
        }
        InputStream bufferedIn = new BufferedInputStream(audioSrc);
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedIn);
        Clip clip = AudioSystem.getClip();
        clip.open(audioStream);
        clip.start();
    } catch (Exception e) {
        System.out.println("Error al reproducir audio: " + e.getMessage());
    }
}
    
}
