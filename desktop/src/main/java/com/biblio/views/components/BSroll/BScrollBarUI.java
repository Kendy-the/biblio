package com.biblio.views.components.BSroll;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicScrollBarUI;

import com.biblio.views.components.BButton;

public class BScrollBarUI extends BasicScrollBarUI {

    @Override
    protected void configureScrollBarColors() {

        thumbColor = new Color(0, 100, 20, 150);

        trackColor = new Color(245, 245, 245);
    }

    @Override
    protected BButton createDecreaseButton(int orientation) {
        return createZeroButton();
    }

    @Override
    protected BButton createIncreaseButton(int orientation) {
        return createZeroButton();
    }

    private BButton createZeroButton() {

        BButton btn = new BButton();

        btn.setPreferredSize(new Dimension(0, 0));

        btn.setMinimumSize(new Dimension(0, 0));

        btn.setMaximumSize(new Dimension(0, 0));

        return btn;
    }

    @Override
    protected void paintTrack(
            Graphics g,
            JComponent c,
            Rectangle trackBounds
    ) {

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setColor(trackColor);

        g2.fillRoundRect(
                trackBounds.x,
                trackBounds.y,
                trackBounds.width,
                trackBounds.height,
                10,
                10
        );

        g2.dispose();
    }

    @Override
    protected void paintThumb(
            Graphics g,
            JComponent c,
            Rectangle thumbBounds
    ) {

        if (thumbBounds.isEmpty()) {
            return;
        }

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        g2.setColor(new Color(0, 100, 20, 180));

        g2.fillRoundRect(
                thumbBounds.x + 2,
                thumbBounds.y,
                thumbBounds.width - 4,
                thumbBounds.height,
                12,
                12
        );

        g2.dispose();
    }
}
