package com.biblio.views.components;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import com.biblio.config.BColor;

public class BComponentPan extends JPanel{

    protected Color fondColor = BColor.WHITE.get();
    protected Color formColor = BColor.PRIMARY_500.get();

    protected void paintComponent(Graphics g){
        g.setColor(this.fondColor);
    }

    public void setFondColor(Color color){
        this.fondColor = color;
    }

    public void setFormColor(Color color){
        this.formColor = color;
    }
}
