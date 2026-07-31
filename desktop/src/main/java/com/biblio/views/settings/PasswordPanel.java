package com.biblio.views.settings;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import com.biblio.config.BColor;
import com.biblio.controllers.UserController;
import com.biblio.models.User;
import com.biblio.views.components.BButton;

public class PasswordPanel extends JPanel {

    private static final int FIELD_WIDTH = 450;
    private static final int FIELD_HEIGHT = 45;

    private JPasswordField oldPasswordField;
    private JPasswordField newPasswordField;
    private JPasswordField confirmPasswordField;

    private BButton update;

    public PasswordPanel() {

        setLayout(new BorderLayout());
        setBackground(BColor.WHITE.get());

        // Panel qui centre le formulaire
        JPanel wrapperPanel = new JPanel(
            new FlowLayout(FlowLayout.CENTER)
        );

        wrapperPanel.setBackground(
            BColor.WHITE.get()
        );

        // Formulaire vertical
        JPanel formPanel = new JPanel();

        formPanel.setLayout(
            new BoxLayout(
                formPanel,
                BoxLayout.Y_AXIS
            )
        );

        formPanel.setBackground(
            BColor.WHITE.get()
        );

        formPanel.setBorder(
            new EmptyBorder(
                30,
                0,
                30,
                0
            )
        );

        // Initialisation des champs
        oldPasswordField = createPasswordField();
        newPasswordField = createPasswordField();
        confirmPasswordField = createPasswordField();

        // Ajout des champs
        addField(
            formPanel,
            "Ancien mot de passe",
            oldPasswordField
        );

        addField(
            formPanel,
            "Nouveau mot de passe",
            newPasswordField
        );

        addField(
            formPanel,
            "Confirmation nouveau mot de passe",
            confirmPasswordField
        );

        // Bouton Modifier
        update = new BButton("Modifier");

        update.addActionListener(e -> {

            if (!getNewPassword()
                    .equals(getConfirmPassword())) {

                JOptionPane.showMessageDialog(
                    this,
                    "Les mots de passe ne correspondent pas.",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE
                );

                return;
            }

            updatePassword();

        });

        addField(
            formPanel,
            "",
            update
        );

        wrapperPanel.add(formPanel);

        add(
            wrapperPanel,
            BorderLayout.CENTER
        );
    }

    private void addField(
        JPanel panel,
        String title,
        JComponent component
    ) {

        JPanel fieldPanel = wrap(
            title,
            component
        );

        fieldPanel.setAlignmentX(
            Component.LEFT_ALIGNMENT
        );

        panel.add(fieldPanel);

        panel.add(
            Box.createVerticalStrut(15)
        );
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

        field.setMaximumSize(
            new Dimension(
                FIELD_WIDTH,
                FIELD_HEIGHT
            )
        );

        return field;
    }

    private JPanel wrap(
        String title,
        JComponent component
    ) {

        JPanel panel = new JPanel(
            new BorderLayout()
        );

        panel.setBackground(
            BColor.WHITE.get()
        );

        panel.setBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(
                    BColor.PRIMARY_100.get()
                ),
                title
            )
        );

        panel.setMaximumSize(
            new Dimension(
                FIELD_WIDTH,
                70
            )
        );

        panel.setPreferredSize(
            new Dimension(
                FIELD_WIDTH,
                70
            )
        );

        panel.add(
            component,
            BorderLayout.CENTER
        );

        return panel;
    }

    // Getters

    public String getOldPassword() {

        return String.valueOf(
            oldPasswordField.getPassword()
        ).trim();
    }

    public String getNewPassword() {

        return String.valueOf(
            newPasswordField.getPassword()
        ).trim();
    }

    public String getConfirmPassword() {

        return String.valueOf(
            confirmPasswordField.getPassword()
        ).trim();
    }

    private String getValidatePassword(){

        if (
            User.getConnected()
            .getPassword()
            .equals(getOldPassword())
        ) {
            return getConfirmPassword();
        }

        JOptionPane.showMessageDialog(
            this, 
            "Erreur, Mot de passe incorrect",
            "Verification Mot de passe",
            JOptionPane.ERROR_MESSAGE
        );

        return null;
    }

    public void clearFields() {

        oldPasswordField.setText("");
        newPasswordField.setText("");
        confirmPasswordField.setText("");
    }

    private void updatePassword(){

        if (getValidatePassword() != null) {
            
            if (new UserController().edit(getData(), User.getConnected())) {
                JOptionPane.showMessageDialog(
                    this,
                    "Modifier avec success",
                    "Modifier Mot de passe",
                    JOptionPane.INFORMATION_MESSAGE
                );
            }else{
                JOptionPane.showMessageDialog(
                    this, 
                    "Erreur, peut pas modifier le mot de passe !",
                    "Modifier mot de passe",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
        clearFields();
    }

    private Map<String, String> getData(){

        Map<String, String> data = new HashMap<>();

        data.put("firstName", User.getConnected().getFirstName());
        data.put("lastName", User.getConnected().getLastName());
        data.put("email", User.getConnected().getEmail());
        data.put("phone", User.getConnected().getPhone());
        data.put("adresse", User.getConnected().getAdresse());
        data.put("password", getValidatePassword());
        data.put("role", User.getConnected().getRole());

        return data;
    }
}
