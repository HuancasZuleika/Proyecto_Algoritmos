/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Astronomia;

import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

/**
 *
 * @author User
 */
public class AstronautaController {
    private final JLabel lblAstronauta;

    // Expresiones
    private final ImageIcon normal;
    private final ImageIcon parpadeo;
    
    
    // Movimiento
    private Timer timerFlotar;
    private Timer timerParpadeo;
    private Timer timerAbrirOjos;
    private Timer timerHablar;
    private boolean hablandoAhora = false;

    private int yInicial;
    private int direccion = 1;

    private final Random random = new Random();

    public AstronautaController(JLabel lblAstronauta) {

        this.lblAstronauta = lblAstronauta;

        normal = new ImageIcon(getClass().getResource("/imagen/normal.png"));
        parpadeo = new ImageIcon(getClass().getResource("/imagen/parpadeo.png"));
        
        
        lblAstronauta.setIcon(normal);

        yInicial = lblAstronauta.getY();
    }

    public void iniciar() {

        iniciarFlotacion();
        iniciarParpadeo();

    }

    private void iniciarFlotacion() {

        timerFlotar = new Timer(60, e -> {

            lblAstronauta.setLocation(
                    lblAstronauta.getX(),
                    lblAstronauta.getY() + direccion);

            if (lblAstronauta.getY() >= yInicial + 20)
                direccion = -1;

            if (lblAstronauta.getY() <= yInicial - 20)
                direccion = 1;

        });

        timerFlotar.start();

    }
    
    private void iniciarParpadeo() {

        programarSiguienteParpadeo();

    }
    
    private void programarSiguienteParpadeo() {

        int tiempo = 3000 + random.nextInt(4000);
        // entre 3 y 7 segundos

        timerParpadeo = new Timer(tiempo, e -> {
            
            if (hablandoAhora) {
                programarSiguienteParpadeo();
                return;
            }

            lblAstronauta.setIcon(parpadeo);

            timerAbrirOjos = new Timer(180, ev -> {

                lblAstronauta.setIcon(normal);

                timerParpadeo.stop();

                programarSiguienteParpadeo();

            });

            timerAbrirOjos.setRepeats(false);
            timerAbrirOjos.start();

        });

        timerParpadeo.setRepeats(false);
        timerParpadeo.start();

    }
    
    // Inicia la animación de hablar
    public void empezarHablar() {

        if (hablandoAhora) return;

        hablandoAhora = true;

        // Si estaba parpadeando, volvemos a la cara normal
        lblAstronauta.setIcon(normal);

        timerHablar = new Timer(500, e -> {

            if (lblAstronauta.getIcon() == normal) {
                
                lblAstronauta.setIcon(normal);
            }

        });

        timerHablar.start();
    }
    
    public void dejarDeHablar(){
        hablandoAhora = false;
        
        if (timerHablar != null) {
            timerHablar.stop();
        }
        lblAstronauta.setIcon(normal);
        
    }
    
    public void detenerTodo() {

        hablandoAhora = false;

        if (timerFlotar != null) {
            timerFlotar.stop();
        }

        if (timerParpadeo != null) {
            timerParpadeo.stop();
        }

        if (timerAbrirOjos != null) {
            timerAbrirOjos.stop();
        }

        if (timerHablar != null) {
            timerHablar.stop();
        }

        lblAstronauta.setIcon(normal);
    }
    
}
