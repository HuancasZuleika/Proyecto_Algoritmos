
package matematica;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.InputStream;

public class Matematicas {
    
    private Clip clipActual;
    private long posicionAudio = 0; 
    private String ultimoArchivo = "";
    
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
            if (!ultimoArchivo.equals(nombreArchivo)) {
                detenerAudio();
                posicionAudio = 0;
                ultimoArchivo = nombreArchivo;
            }

            if (clipActual == null || !clipActual.isOpen()) {
                InputStream audioSrc = getClass().getResourceAsStream("/audio/" + nombreArchivo);
                if (audioSrc == null) return;
                
                InputStream bufferedIn = new BufferedInputStream(audioSrc);
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedIn);
                
                clipActual = AudioSystem.getClip();              
                clipActual.open(audioStream);
            }

            clipActual.setMicrosecondPosition(posicionAudio);
            clipActual.start();
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    public void pausarAudio() {
        if (clipActual != null && clipActual.isRunning()) {
            posicionAudio = clipActual.getMicrosecondPosition(); 
            clipActual.stop();
        }
    }

    public void detenerAudio() {
        if (clipActual != null) {
            clipActual.stop();
            clipActual.close();
            posicionAudio = 0; 
        }
    }
    
    public boolean estaSonando() {
        return clipActual != null && clipActual.isRunning();
    }
    
}
