package com.biblio.views.components;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JLabel;

import com.biblio.config.BFont;

public class BCenterTop extends BComponentPan{

    private JLabel BTitle = new JLabel("Page"),
    BDescribe = new JLabel("Petite description");

    private BComponentPan left = new BComponentPan(), 
    right = new BComponentPan();

    public BCenterTop(){

        this.initStyle();

        this.left.setLayout(new BoxLayout(
            this.left, 
            BoxLayout.Y_AXIS
        ));

        FlowLayout rightLayout = new FlowLayout();
        rightLayout.setHgap(17);
        this.right.setLayout(rightLayout);

        this.left.add(this.BTitle);
        this.left.add(this.BDescribe);
       
        this.setLayout(new BorderLayout());
        this.add(this.left, BorderLayout.WEST);
        this.add(this.right, BorderLayout.EAST);
    }

    public void addObject(Component object){
        this.right.add(object);
    }

    public void setBButton(BButton button){
        this.right.add(button);
    }

    public void setBTitle(String text, boolean ucFirst){

        if (ucFirst) {
            this.BTitle.setText(text.charAt(0) + 
            text.substring(1).toLowerCase());
        }else{
            
            this.BTitle.setText(text);
        }
    }

    public void setBDescribe(String text){
        this.BDescribe.setText(text);
    }

    private void initStyle(){

        BTitle.setFont(new Font(
            BFont.PRIMARY.get(),
            Font.BOLD,
            26
        ));

        BDescribe.setFont(new Font(
            BFont.PRIMARY.get(),
            Font.LAYOUT_LEFT_TO_RIGHT,
            15
        ));

    }

}
