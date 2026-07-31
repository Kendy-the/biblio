package com.biblio.views.components;

import java.awt.Dimension;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import com.biblio.config.BPath;


public class BaseFrame extends JFrame{
    
    protected BasePan container = new BasePan();

    private int Bwidth = 1300;
    private int Bheight = 800;

    public BaseFrame(){

        this.setMinimumSize(new Dimension(this.Bwidth, this.Bheight));
        this.setPreferredSize(this.getMinimumSize());
        this.setSize(this.Bwidth, this.Bheight);
        this.setLocationRelativeTo(null);
        this.setTitle("Biblio");

        try {
            URL iconURL = getClass().getResource(BPath.IMG_PATH.get() + "logo.png");
            if (iconURL != null) {
                ImageIcon icon = new ImageIcon(iconURL);
                this.setIconImage(icon.getImage());
            } else {
                System.err.println("Icône introuvable !");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        this.setContentPane(this.container);
    }

    public void setBsize(int width){
        this.Bwidth = width;
    }

    public void setBsize(int width, int height){
        this.Bwidth = width;
        this.Bheight = height;
    }

}
