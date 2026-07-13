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

    public static int vidas = 3;
    public static double puntos = 10;

    public static void sumarPuntos() {
        puntos += 10;
    }

    public static void restarPuntos() {
        if (puntos >= 3) {
            puntos -= 3;
        } else {
            puntos = 0;
        }
    }

    public static void reiniciarJuego() {
        puntos = 10;
        vidas = 3;
    }

    public static void restarVida() {
        if (vidas > 0) vidas--;
    }

    public static String formatearPuntos(double p) {
        if (p == Math.floor(p)) {
            return String.valueOf((int) p);
        }
        return String.format("%.1f", p);
    }

}