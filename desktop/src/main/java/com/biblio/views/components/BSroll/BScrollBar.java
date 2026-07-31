package com.biblio.views.components.BSroll;

import java.awt.Dimension;

import javax.swing.JScrollBar;

public class BScrollBar extends JScrollBar {

    public BScrollBar() {

        super(VERTICAL);

        setPreferredSize(new Dimension(10, 0));

        setUI(new BScrollBarUI());

        setOpaque(false);
    }
}
