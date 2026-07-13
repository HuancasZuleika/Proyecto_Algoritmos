/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Astronomia;

import java.io.IOException;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author User
 */
public class AudioController {
    
    private Clip clip;
    
    public void reproducir(String ruta, Runnable alFinalizar) {

        try {

            if (clip != null && clip.isRunning()) {
                clip.stop();
                clip.close();
            }

            URL url = getClass().getResource(ruta);

            AudioInputStream audio = AudioSystem.getAudioInputStream(url);

            clip = AudioSystem.getClip();

            clip.open(audio);

            clip.addLineListener(event -> {

                if (event.getType() == LineEvent.Type.STOP) {

                    clip.close();

                    if (alFinalizar != null) {
                        alFinalizar.run();
                    }

                }

            });

            clip.start();

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {

            ex.printStackTrace();

        }

    }
    
    public boolean estaReproduciendo(){
        return clip != null && clip.isRunning();
    }
    
    public void detener() {

        if (clip != null) {
            clip.stop();
            clip.close();
        }

    }
    
    
}
