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
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.biblio.config.BColor;
import com.biblio.config.BApp;
import com.biblio.controllers.UserController;
import com.biblio.errors.BErrorMgr;
import com.biblio.models.User;
import com.biblio.observer.BAppEventObservable;
import com.biblio.views.components.BButton;

public class AddUserPanel extends JPanel {

    private static final int FIELD_WIDTH = 450;
    private static final int FIELD_HEIGHT = 45;

    private JPanel formPanel;

    private JTextField emailField;
    private JComboBox<Object> roleField;
    private JTextField passwordField;

    private BButton save;

    public AddUserPanel() {

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
        formPanel = new JPanel();

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
        emailField = createTextField();

        roleField = new JComboBox<Object>();
        roleField.addItem(BApp.USER_USER_ROLE.get());
        roleField.addItem(BApp.USER_ADMIN_ROLE.get());

        passwordField = createTextField();

        // Ajout des champs

        addField(
            formPanel,
            "Email",
            emailField
        );

        addField(
            formPanel,
            "Role",
            roleField
        );

        addField(
            formPanel,
            "Mot de passe",
            passwordField
        );

        save = new BButton("Enregistrer");
        save.addActionListener(e->{
            save();
        });

        addField(
            formPanel, 
            "", 
            save
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

    private JTextField createTextField() {

        JTextField field = new JTextField();

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
    public Map<String, String> getData(){

        Map<String, String> data = new HashMap<>();

        data.put(
            "email",
            emailField.getText().trim()
        );

        data.put(
            "role", 
            String.valueOf(roleField.getSelectedIndex())
        );

        data.put(
            "password",
            passwordField.getText().trim()
        );

        return data;
    }

    /****************************************
    *               LOGIQUE
    ****************************************/
    public void save(){
        
        String msg;
        int icon;
        int saved = new UserController().save(getData());

        if(saved > 0){

            msg = "Utilisateur ajouté avec success";
            icon = JOptionPane.INFORMATION_MESSAGE;
            
            // For Listener
            User u = new User(getData(), false);
            u.setId(saved);
            BAppEventObservable.notifyAddedUser(u);
            
            clearField();

        }else
        {
            if (BErrorMgr.hasErrors()) {
                msg = BErrorMgr.getErrorsMessage();
            } else {
                msg = "Erreur, champ invalide";
            }
            icon = JOptionPane.ERROR_MESSAGE;
        }

        JOptionPane.showMessageDialog(
            null, 
            msg,
            "Ajouter Utilisateur",
            icon
        );
    }

    public void clearField(){
        emailField.setText("");
        passwordField.setText("");
    }
}
