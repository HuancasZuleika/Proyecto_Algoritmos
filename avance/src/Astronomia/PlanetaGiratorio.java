/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Astronomia;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.JButton;

/**
 *
 * @author sayuri
 */
public class PlanetaGiratorio extends JButton{

    private Image imagen;

    private double angulo;

    public PlanetaGiratorio(Image imagen){

        this.imagen=imagen;

        setContentAreaFilled(false);

        setBorderPainted(false);

    }

    public void girar(){

        angulo+=2;

        repaint();

    }

    @Override
    protected void paintComponent(Graphics g){

        Graphics2D g2=(Graphics2D)g.create();

        int w=getWidth();

        int h=getHeight();

        g2.rotate(Math.toRadians(angulo),w/2,h/2);

        g2.drawImage(imagen,0,0,w,h,this);

        g2.dispose();

    }

}
