package com.biblio.views.components;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;

public class BSideBar extends BComponentPan{ 

    private BComponentPan topPan;
    private BComponentPan bottomPan;

    private ArrayList<BButton> topBButtons;
    private ArrayList<BButton> bottomBButtons;
    private int buttonRounded = 0;

    public BSideBar(){

        /*
        * ARRAY - BUTTONS
        */
        this.topBButtons = new ArrayList<BButton>();
        this.bottomBButtons = new ArrayList<BButton>();

        this.setPreferredSize(new Dimension(220, this.getHeight()));
        this.setLayout(new BorderLayout());
        this.topPan = new BComponentPan();
        this.bottomPan = new BComponentPan();
        this.add(this.topPan, BorderLayout.NORTH);
        this.add(this.bottomPan, BorderLayout.SOUTH);

        this.topPan.setLayout(new BoxLayout(this.topPan, BoxLayout.Y_AXIS));
        this.topPan.setOpaque(false);

        this.bottomPan.setLayout(new BoxLayout(this.bottomPan, BoxLayout.Y_AXIS));
        this.bottomPan.setOpaque(false);
    }

    public void setBTitle(String text){

    }

    public void setButtonRounded(int rounded){

        this.buttonRounded = (
            rounded > 0 ? rounded : 0
        );

        for (BButton bButton : this.bottomBButtons) {
            bButton.setRounded(rounded);
        }

        for (BButton bButton : this.topBButtons) {
            bButton.setRounded(rounded);
        }
    }

    private void setBButton(BButton button){

        button.setText(
            button.getText().
            toUpperCase()
        );

        button.setPreferredSize(new Dimension(
            (int)this.getPreferredSize().getWidth() - 30, 
            45
        ));

        button.setRounded(this.buttonRounded);
        button.setMaximumSize(button.getPreferredSize());
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    public void setTopBButton(BButton button){

        this.topPan.add(Box.createRigidArea(new Dimension(0, 20)));
        this.setBButton(button);

        this.topBButtons.add(button);
        this.topPan.add(button);
    }

    public void setBottomBButton(BButton button){

        this.setBButton(button);

        this.bottomBButtons.add(button);
        this.bottomPan.add(button);
        this.bottomPan.add(Box.createRigidArea(new Dimension(0, 20)));
    }

    @Override
    protected void paintComponent(Graphics g){

        g.setColor(this.fondColor);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

    }

}
