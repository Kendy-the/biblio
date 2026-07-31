package com.biblio.views.components.BMenu;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JMenuItem;

import com.biblio.config.BColor;
import com.biblio.config.BFont;

public class BMenuItem extends JMenuItem {

    private Color fondColor;

    public BMenuItem(String text) {

        super(text);

        fondColor = BColor.WHITE.get();

        setFont(new Font(
            BFont.SECONDARY.get(), 
            Font.PLAIN, 
            13
        ));

        setBackground(fondColor);

        setForeground(BColor.PRIMARY_900.get());

        setBorder(BorderFactory.createEmptyBorder(
                8,
                15,
                8,
                15
        ));
    }

}
