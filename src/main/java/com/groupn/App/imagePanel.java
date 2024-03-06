package com.groupn.App;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class imagePanel extends JPanel {
    Image image = Toolkit.getDefaultToolkit().getImage("Logo/Logo.jpg");

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D whatToBePrinted = (Graphics2D) g;
        int width = getWidth();
        int height = getHeight();
        Shape roundedRect = new RoundRectangle2D.Double(0, 0, width - 1, height - 1, 20, 20);
        whatToBePrinted.setClip(roundedRect);
        whatToBePrinted.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        whatToBePrinted.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        whatToBePrinted.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        whatToBePrinted.drawImage(image, 0, 0, width, height, this);
        whatToBePrinted.setColor(Color.BLACK);
        whatToBePrinted.setStroke(new BasicStroke(3));
        whatToBePrinted.draw(roundedRect);
    }
}
