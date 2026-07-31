package com.biblio.views.components.BSroll;

import java.awt.Component;

import javax.swing.JScrollPane;

import com.biblio.config.BColor;

public class BScrollPan extends JScrollPane {

    public BScrollPan(Component view) {

        super(view);

        setBorder(null);

        getViewport().setBackground(BColor.WHITE.get());

        setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        getVerticalScrollBar().setUnitIncrement(16);

        setVerticalScrollBar(new BScrollBar());
    }
}
