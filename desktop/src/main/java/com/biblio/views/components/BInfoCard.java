package com.biblio.views.components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.biblio.config.BFont;

import java.awt.*;
import java.net.URL;

public class BInfoCard extends JPanel {

    private JLabel iconLabel;
    private JLabel titleLabel;
    private JLabel subtitleLabel;

    public BInfoCard(){
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(false);
    }

    public BInfoCard(String title, URL iconPath){
        
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setOpaque(false);

        ImageIcon icon = new ImageIcon(iconPath);
        iconLabel = new JLabel(icon);
        iconLabel.setPreferredSize(new Dimension(getWidth(), getHeight()));

        titleLabel = new JLabel(title);
        titleLabel.setFont(new Font(
            BFont.PRIMARY.get(), 
            Font.BOLD, 
            16
        ));

        this.add(iconLabel);
        this.add(titleLabel);

        setBorder(new EmptyBorder(15, 15, 15, 15));

    }

    public BInfoCard(String title, String subtitle, URL iconPath) {

        setLayout(new BorderLayout(15, 0));
        setOpaque(false);

        ImageIcon icon = new ImageIcon(iconPath);
        iconLabel = new JLabel(icon);
        iconLabel.setPreferredSize(new Dimension(70, 70));

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setOpaque(false);

        titleLabel = new JLabel(title);
        titleLabel.setFont(new Font(
            BFont.PRIMARY.get(), 
            Font.BOLD, 
            16
        ));

        subtitleLabel = new JLabel(subtitle);
        subtitleLabel.setFont(new Font(
            BFont.PRIMARY.get(), 
            Font.PLAIN, 
            20
        ));

        textPanel.add(titleLabel);
        textPanel.add(Box.createVerticalStrut(5));
        textPanel.add(subtitleLabel);

        add(iconLabel, BorderLayout.WEST);
        add(textPanel, BorderLayout.CENTER);

        setBorder(new EmptyBorder(15, 15, 15, 15));
    }

    public void setSubTitle(String subTitle){
        this.subtitleLabel.setText(subTitle);
    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        g2.setColor(new Color(255, 255, 255));
        g2.fillRoundRect(
                0,
                0,
                getWidth(),
                getHeight(),
                20,
                20
        );

        g2.dispose();

        super.paintComponent(g);
    }
}
