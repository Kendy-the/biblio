package com.biblio.views.components;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import com.biblio.config.BColor;
import com.biblio.config.BFont;

public class BTopBar extends BComponentPan{

    private JLabel title = new JLabel("BIBLIO");
    private JLabel lbl = new JLabel("L'Organisation et le management de votre biblioteque avec Biblio");

    private Font font = new Font("Courrier",Font.BOLD,25);
    private Border lineBorder = BorderFactory.createLineBorder(BColor.PRIMARY_500.get(),1,true);

    private BButton profile = null;

    public BTopBar(){
        this.init();
    }

    public BTopBar(BButton profile){
        this.profile = profile;
        this.init();
    }

    public BTopBar(String title, String lbl){

        this.setBTitle(title);
        this.setBLabel(lbl);

        this.init();
    }

    public void init(){
        this.setPreferredSize(new Dimension(this.getWidth(), 60));
        this.setLayout(new BorderLayout());

        this.setBorder(new CompoundBorder(
            lineBorder,
            new EmptyBorder(10,15,10,15)
        ));

        this.title.setFont(this.font);
        this.title.setPreferredSize(new Dimension(200, this.getHeight()));

        this.showProfile();

        this.lbl.setFont(new Font(
            BFont.PRIMARY.get(),
            Font.ITALIC,
            18
        ));
        this.lbl.setHorizontalAlignment(JLabel.CENTER);

        this.add(this.title, BorderLayout.WEST);
        this.add(this.lbl, BorderLayout.CENTER);
       
    }

    private void showProfile(){

        if(this.profile != null){
            this.add(this.profile, BorderLayout.EAST);
        }
    }

    public void setBLabel(String lbl){
        this.lbl.setText(lbl);
    }

    public void setBTitle(String title){
        title = title.toUpperCase();
        this.title.setText(title);
    }

    public void setProfile(BButton profile){
        this.profile = profile;
        this.showProfile();
    }

    public void paintComponent(Graphics g){

        g.setColor(this.fondColor);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        
    }
}
