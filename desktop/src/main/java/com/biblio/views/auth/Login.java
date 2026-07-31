package com.biblio.views.auth;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.biblio.analytic.AnalyticsService;
import com.biblio.config.BColor;
import com.biblio.config.BPath;
import com.biblio.controllers.UserController;
import com.biblio.views.App;
import com.biblio.views.components.BButton;

public class Login extends JFrame {

    private static final int FIELD_WIDTH = 350;
    private static final int FIELD_HEIGHT = 40;

    private JTextField emailField;
    private JPasswordField passwordField;

    private JLabel errorLabel;

    private BButton loginButton;

    public Login() {

        setTitle("Connexion");

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

        setSize(500, 600);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLocationRelativeTo(null);

        init();
    }

    private void init() {

        JPanel content = new JPanel();

        content.setLayout(
                new BorderLayout()
        );

        content.setBackground(
                BColor.WHITE.get()
        );

        /*
         * FORMULAIRE
         */

        JPanel wrapper = new JPanel(
                new FlowLayout(
                        FlowLayout.CENTER
                )
        );

        wrapper.setBackground(
                BColor.WHITE.get()
        );

        JPanel form = new JPanel();

        form.setLayout(
                new BoxLayout(
                        form,
                        BoxLayout.Y_AXIS
                )
        );

        form.setBackground(
                BColor.WHITE.get()
        );

        form.setBorder(
                new EmptyBorder(
                        40,
                        0,
                        40,
                        0
                )
        );

        /*
         * TITRE
         */

        JLabel title = new JLabel(
                "Gestion Bibliothèque"
        );

        title.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        26
                )
        );

        title.setForeground(
                BColor.PRIMARY_900.get()
        );

        title.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        JLabel subtitle = new JLabel(
                "BTI / 2026"
        );

        subtitle.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        16
                )
        );

        subtitle.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        /*
         * EMAIL
         */

        emailField = createTextField();

        addField(
                form,
                "Email",
                emailField
        );

        /*
         * PASSWORD
         */

        passwordField =
                createPasswordField();

        addField(
                form,
                "Mot de passe",
                passwordField
        );

        // Listener
        passwordField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    authenticate();
                }
            }
        });

        /*
         * MESSAGE ERREUR
         */

        errorLabel = new JLabel("");

        errorLabel.setForeground(
                BColor.DANGER_500.get()
        );

        errorLabel.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        errorLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        /*
         * BOUTON
         */

        loginButton = new BButton(
                "Se connecter"
        );

        loginButton.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );

        loginButton.addActionListener(
                e -> authenticate()
        );

        /*
         * AJOUTS
         */

        form.add(title);

        form.add(
                Box.createVerticalStrut(5)
        );

        form.add(subtitle);

        form.add(
                Box.createVerticalStrut(40)
        );

        form.add(errorLabel);

        form.add(
                Box.createVerticalStrut(15)
        );

        form.add(loginButton);

        wrapper.add(form);

        content.add(
                wrapper,
                BorderLayout.CENTER
        );

        add(content);
    }

    private void authenticate() {

        String email =
                emailField
                .getText()
                .trim();

        String password =
                String.valueOf(
                        passwordField
                        .getPassword()
                );

        /*
         * Exemple simple
         */
        if(
            new UserController()
            .login(email, password)
        )
         {

            dispose();

            new App().setVisible(true);

            AnalyticsService.login();

        } else {

            errorLabel.setText(
                "Email ou mot de passe incorrect"
            );

            passwordField.setText("");
        }
    }

    private JTextField createTextField() {

        JTextField field =
                new JTextField();

        field.setPreferredSize(
                new Dimension(
                        FIELD_WIDTH,
                        FIELD_HEIGHT
                )
        );

        return field;
    }

    private JPasswordField createPasswordField() {

        JPasswordField field =
                new JPasswordField();

        field.setPreferredSize(
                new Dimension(
                        FIELD_WIDTH,
                        FIELD_HEIGHT
                )
        );

        return field;
    }

    private void addField(
            JPanel panel,
            String title,
            javax.swing.JComponent component
    ) {

        JPanel container =
                new JPanel(
                        new BorderLayout()
                );

        container.setBackground(
                BColor.WHITE.get()
        );

        container.setBorder(
                BorderFactory
                .createTitledBorder(
                    BorderFactory
                    .createLineBorder(
                            BColor.PRIMARY_100.get()
                    ),
                    title
                )
        );

        container.setMaximumSize(
                new Dimension(
                    FIELD_WIDTH,
                    70
                )
        );

        panel.add(container);

        panel.add(
                Box.createVerticalStrut(
                        15
                )
        );

        container.add(
                component,
                BorderLayout.CENTER
        );
    }
}
