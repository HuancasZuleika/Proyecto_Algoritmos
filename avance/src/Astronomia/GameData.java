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
    
    // Método para restar vida
    public static void restarVida() {
        if (vidas > 0) vidas--;
    }
    
}
