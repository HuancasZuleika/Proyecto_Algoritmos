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
    
    // Puntaje global: se comparte entre TODAS las misiones/quizzes.
    // Cada pantalla debe leer este valor al iniciar y actualizarlo
    // cada vez que el puntaje cambie, para que el progreso se mantenga
    // de una misión a la siguiente.
    public static double puntos = 10;
    
    // Método para restar vida
    public static void restarVida() {
        if (vidas > 0) vidas--;
    }
    
    // Da formato al puntaje para mostrarlo en pantalla:
    // números enteros se ven "12", los que tienen decimales se ven "12.5"
    public static String formatearPuntos(double p) {
        if (p == Math.floor(p)) {
            return String.valueOf((int) p);
        }
        return String.format("%.1f", p);
    }
    
}
 