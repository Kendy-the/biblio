package com.biblio.views.components.BMenu;

import javax.swing.*;

import com.biblio.config.BColor;
import com.biblio.config.BFont;

import java.awt.*;

public class BMenu extends JMenu {

    public BMenu(String text) {

        super(text);

        setFont(new Font(
            BFont.SECONDARY.get(), 
            Font.BOLD, 
            14
        ));

        setForeground(BColor.PRIMARY_900.get());

        setOpaque(false);

        setBorderPainted(false);
    }

}
