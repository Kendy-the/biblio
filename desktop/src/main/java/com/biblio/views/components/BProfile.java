package com.biblio.views.components;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.biblio.config.BColor;


public class BProfile extends BComponentPan{

    private String name = "USER";
    private String imgPath = "src/assets/img/user.png";

    private int rounded = 0;

    public BProfile(String name){
        setBName(name);
    }

    public void setRounded(int rounded){
        this.rounded = (
            rounded > 0 ? rounded : 0
        );
    }

    public void setBName(String name){
        name = name.toUpperCase();
        this.name = name;
    }

    public void setBPath(String path){
        this.imgPath = path;
    }

    public void paintComponent(Graphics g){

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(this.fondColor);
        g2d.fillRoundRect(
            0, 
            0, 
            this.getWidth(), 
            this.getHeight(),
            this.rounded,
            this.rounded
        );

        try {
            BufferedImage img = ImageIO.read(
                new File(this.imgPath)
            );

            g2d.drawImage(img, this.getWidth()/16, this.getHeight() / 4, this);

        } catch (IOException e) {
            e.printStackTrace();
        }

        Font font1 = new Font("Courrier", Font.BOLD, 16);
        g2d.setFont(font1);
        g2d.setColor(BColor.PRIMARY_900.get());

        FontMetrics fm = g2d.getFontMetrics();
        int height = fm.getHeight();
        int width = fm.stringWidth(this.name);

        g2d.drawString(this.name, this.getWidth() / 2- (width / 2), (this.getHeight() / 2) + (height / 4));

    }
}
