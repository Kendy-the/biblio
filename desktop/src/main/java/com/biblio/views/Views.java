package com.biblio.views;

import java.awt.BorderLayout;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import com.biblio.views.components.BCenterTop;
import com.biblio.views.components.BComponentPan;
import com.biblio.views.components.BSroll.BScrollPan;

public class Views extends BComponentPan{

    protected BCenterTop top;
    protected BComponentPan center;
    protected BComponentPan bottom;

    public Views() {

        super();

        this.setLayout(new BorderLayout());

        this.top = new BCenterTop();
        this.center = new BComponentPan();
        this.bottom = new BComponentPan();


        BScrollPan scrollPane = new BScrollPan(this.center);
        scrollPane.setHorizontalScrollBarPolicy(
            JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        scrollPane.setVerticalScrollBarPolicy(
            JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
        );

        this.add(this.top, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(this.bottom, BorderLayout.SOUTH);
    }

    public static boolean wantToDelete(String object){
        if (JOptionPane.showConfirmDialog(
            null,
            "Etes Vous Sur De Vouloir Supprimer {" + object + "}?",
            "Delete " + object,
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        ) == JOptionPane.YES_OPTION) {
            return true;
        }
        
        return false;
    }
}
