/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Astronomia;

/**
 *
 * @author sayuri
 */
public class GameData {

    public static int puntos = 10;

    // Suma 10 puntos
    public static void sumarPuntos() {
        puntos += 10;
    }

    // Resta 3 puntos (sin bajar de 0)
    public static void restarPuntos() {
        if (puntos >= 3) {
            puntos -= 3;
        } else {
            puntos = 0;
        }
    }

    // Reinicia el juego
    public static void reiniciarJuego() {
        puntos = 10;
    }

    public static int vidas = 3;

    public static void restarVida() {
        if (vidas > 0) {
            vidas--;
        }
    }

}
 
