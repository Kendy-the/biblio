package com.biblio.views.components;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import com.biblio.config.BColor;

public class BButton extends JButton{

    private Color backgroundColor;
    private Color hoverColor;
    private Color pressedColor;
    private Color formColor;

    private int rounded = 0;

    public BButton(){ this.initB(); }

    public BButton(String text) {

        super(text);
        this.initB();
    }

    public BButton(String text, URL iconPath){

        super(text, new ImageIcon(iconPath));
        this.initB();
    }

    public void setRounded(int rd){
        this.rounded = rd;
    }

    public void setFondColor(Color color){
        this.backgroundColor = color;
    }

    public void setFormColor(Color color){
        this.formColor = color;
    }

    public void setHoverColor(Color color){
        this.hoverColor = color;
    }

    public void setPressedColor(Color color){
        this.pressedColor = color;
    }

    private void initB()
    {
        this.backgroundColor = BColor.PRIMARY_100.get();
        this.hoverColor = BColor.PRIMARY_200.get();
        this.pressedColor = BColor.PRIMARY_300.get();
        this.formColor = BColor.WHITE.get();

        initStyle();
        initEvents();
    }

    private void initStyle() {

        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);

        setForeground(BColor.PRIMARY_900.get());
        setFont(new Font("SansSerif", Font.BOLD, 14));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        setHorizontalAlignment(SwingConstants.LEFT);
        setHorizontalTextPosition(SwingConstants.RIGHT);
        setVerticalAlignment(SwingConstants.CENTER);
        setIconTextGap(15);

    }

    private void initEvents() {

        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                backgroundColor = hoverColor;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                backgroundColor = BColor.PRIMARY_100.get();
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                backgroundColor = pressedColor;
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                backgroundColor = hoverColor;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        g2.setColor(backgroundColor);

        g2.fillRoundRect(
                0,
                0,
                getWidth(),
                getHeight(),
                this.rounded,
                this.rounded
        );

        g2.setColor(formColor);

        g2.drawRoundRect(0, 
            0, 
            getWidth(), 
            getHeight(),
            this.rounded,
            this.rounded
        );

        super.paintComponent(g2);

        g2.dispose();
    }

}
