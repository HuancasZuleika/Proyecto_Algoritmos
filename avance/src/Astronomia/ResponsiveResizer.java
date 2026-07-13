/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Astronomia;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.HashMap;
import java.util.Map;

public class ResponsiveResizer extends ComponentAdapter {

    private final Map<JComponent, double[]> relativeBounds = new HashMap<>();
    private final Map<JComponent, ImageIcon> originalIcons = new HashMap<>();
    private JComponent background;
    private final JFrame frame;

    public ResponsiveResizer(JFrame frame) {
        this.frame = frame;
    }

    public void registerRelative(JComponent comp, double xPct, double yPct, double wPct, double hPct) {
        relativeBounds.put(comp, new double[]{xPct, yPct, wPct, hPct});
        if (comp instanceof JLabel) {
            Icon icon = ((JLabel) comp).getIcon();
            if (icon instanceof ImageIcon) {
                originalIcons.put(comp, (ImageIcon) icon);
            }
        }
    }

    public void registerBackground(JComponent comp) {
        this.background = comp;
        registerRelative(comp, 0, 0, 1.0, 1.0);
    }

    public void start() {
        frame.addComponentListener(this);
        applyBounds();
    }

    @Override
    public void componentResized(ComponentEvent e) {
        applyBounds();
    }

    private void applyBounds() {
        Container content = frame.getContentPane();
        int w = content.getWidth();
        int h = content.getHeight();
        if (w == 0 || h == 0) return;

        for (Map.Entry<JComponent, double[]> entry : relativeBounds.entrySet()) {
            JComponent comp = entry.getKey();
            double[] pct = entry.getValue();

            int x = (int) (pct[0] * w);
            int y = (int) (pct[1] * h);
            int cw = (int) (pct[2] * w);
            int ch = (int) (pct[3] * h);

            comp.setBounds(x, y, cw, ch);

            if (comp instanceof JLabel && originalIcons.containsKey(comp) && cw > 0 && ch > 0) {
                ImageIcon original = originalIcons.get(comp);
                Image scaled = getScaledCoverImage(original.getImage(), cw, ch);
                ((JLabel) comp).setIcon(new ImageIcon(scaled));
            }
        }
        content.revalidate();
        content.repaint();
    }

    private Image getScaledCoverImage(Image img, int targetW, int targetH) {
        int iw = img.getWidth(null);
        int ih = img.getHeight(null);
        if (iw <= 0 || ih <= 0) return img;

        double scale = Math.max((double) targetW / iw, (double) targetH / ih);
        int scaledW = (int) (iw * scale);
        int scaledH = (int) (ih * scale);

        java.awt.image.BufferedImage buffer = new java.awt.image.BufferedImage(
                Math.max(scaledW, 1), Math.max(scaledH, 1), java.awt.image.BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = buffer.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        int x = (targetW - scaledW) / 2;
        int y = (targetH - scaledH) / 2;
        g2.drawImage(img, x, y, scaledW, scaledH, null);
        g2.dispose();
        return buffer;
    }
}