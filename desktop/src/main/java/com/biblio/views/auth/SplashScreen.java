package com.biblio.views.auth;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;

import com.biblio.config.BColor;
import com.biblio.config.BPath;

public class SplashScreen extends JFrame {

    private JProgressBar progressBar;
    private JLabel loadingLabel;

    private Timer timer;

    public SplashScreen() {

        setTitle("Gestion Bibliothèque");

        setSize(600, 400);

        setLocationRelativeTo(null);

        setUndecorated(true);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        init();
        
        startLoading();
        setVisible(true);
    }

    private void init() {

        JPanel content = new JPanel();

        content.setBackground(
                BColor.WHITE.get()
        );

        content.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BColor.PRIMARY_200.get(),
                                2
                        ),
                        BorderFactory.createEmptyBorder(
                                30,
                                30,
                                30,
                                30
                        )
                )
        );

        content.setLayout(
            new BoxLayout(
                content,
                BoxLayout.Y_AXIS
            )
        );

        /*
         * LOGO
         */

        JLabel logo = new JLabel();

        /*
         * for an image :
         *
         * logo.setIcon(new ImageIcon(
         * BConstant.IMG_PATH.get() + "logo.png"));
         *
         */

        ImageIcon icon = new ImageIcon(
            getClass().getResource(BPath.IMG_PATH.get() + "book.png")
        );

        logo.setIcon(icon);

        logo.setAlignmentX(CENTER_ALIGNMENT);

        /*
         * TITRE
         */

        JLabel title = new JLabel(
                "Gestion de Bibliothèque"
        );

        title.setFont(
            new Font(
                "SansSerif",
                Font.BOLD,
                28
            )
        );

        title.setForeground(
            BColor.PRIMARY_600.get()
        );

        title.setAlignmentX(
            CENTER_ALIGNMENT
        );

        JLabel subtitle = new JLabel(
            "BTI / 2026"
        );

        subtitle.setFont(
            new Font(
                "SansSerif",
                Font.PLAIN,
                18
            )
        );

        subtitle.setForeground(
            Color.GRAY
        );

        subtitle.setAlignmentX(
            CENTER_ALIGNMENT
        );

        /*
         * Chargement
         */

        loadingLabel = new JLabel(
            "Chargement..."
        );

        loadingLabel.setAlignmentX(
            CENTER_ALIGNMENT
        );

        progressBar = new JProgressBar(
            0,
            100
        );

        progressBar.setStringPainted(
            true
        );

        progressBar.setForeground(
            BColor.PRIMARY_900.get()
        );

        progressBar.setPreferredSize(
            new Dimension(
                450,
                25
            )
        );

        progressBar.setMaximumSize(
                new Dimension(
                        450,
                        25
                )
        );

        /*
         * Footer
         */

        JLabel student = new JLabel(
                "Developped By : PRESUME"
        );

        student.setAlignmentX(
                CENTER_ALIGNMENT
        );

        JLabel professor = new JLabel(
                "Professeur : Elso POINT DU JOUR"
        );

        professor.setAlignmentX(
                CENTER_ALIGNMENT
        );

        /*
         * Ajout
         */

        content.add(Box.createVerticalGlue());

        content.add(logo);

        content.add(Box.createVerticalStrut(20));

        content.add(title);

        content.add(Box.createVerticalStrut(5));

        content.add(subtitle);

        content.add(Box.createVerticalStrut(40));

        content.add(progressBar);

        content.add(Box.createVerticalStrut(10));

        content.add(loadingLabel);

        content.add(Box.createVerticalGlue());

        content.add(student);

        content.add(Box.createVerticalStrut(5));

        // content.add(professor);

        add(content, BorderLayout.CENTER);

        /*
         * Fermeture au clic
         */

        content.setCursor(
                Cursor.getPredefinedCursor(
                        Cursor.HAND_CURSOR
                )
        );

        content.addMouseListener(
                new MouseAdapter() {

                    @Override
                    public void mouseClicked(
                            MouseEvent e
                    ) {

                        closeSplash();
                    }
                }
        );
    }

    private void startLoading() {

        timer = new Timer(
                50,
                e -> {

                    int value =
                            progressBar
                                    .getValue();

                    progressBar.setValue(
                            value + 1
                    );

                    if (value >= 100) {

                        timer.stop();

                        closeSplash();
                    }
                }
        );

        timer.start();
    }

    private void closeSplash() {

        dispose();

        /*
         * Ouvrir Login
         */

        new Login()
                .setVisible(true);
    }
}
