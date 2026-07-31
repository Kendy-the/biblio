package com.biblio.views.components.BMenu;

import javax.swing.*;
import java.awt.*;

public class BMenuBar extends JMenuBar {

    public BMenuBar() {

        setOpaque(false);
        setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

        setPreferredSize(new Dimension(0, 55));
    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        // Fond blanc
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, getWidth(), getHeight());

        // Ombre discrète
        g2.setColor(new Color(0, 0, 0, 20));
        g2.fillRect(0, getHeight() - 2, getWidth(), 2);

        g2.dispose();

        super.paintComponent(g);
    }
}
