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
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.biblio.config.BColor;
import com.biblio.controllers.UserController;
import com.biblio.models.User;
import com.biblio.views.components.BButton;

public class ProfilePanel extends JPanel {

    private static final int FIELD_WIDTH = 450;
    private static final int FIELD_HEIGHT = 45;

    private JTextField lastNameField;
    private JTextField firstNameField;
    private JTextField addressField;
    private JTextField phoneField;

    private BButton update;

    public ProfilePanel() {

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
        lastNameField = createTextField();
        firstNameField = createTextField();
        addressField = createTextField();
        phoneField = createTextField();

        // Ajout des champs
        addField(
            formPanel,
            "Nom",
            lastNameField
        );

        addField(
            formPanel,
            "Prénom",
            firstNameField
        );

        addField(
            formPanel,
            "Adresse",
            addressField
        );

        addField(
            formPanel,
            "Téléphone",
            phoneField
        );

        update = new BButton("Modifier");
        update.addActionListener(e->{
            updateProfile();
        });

        addField(
            formPanel, 
            "", 
            update
        );

        setFields();

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

    public String getLastName() {
        return lastNameField.getText().trim();
    }

    public String getFirstName() {
        return firstNameField.getText().trim();
    }

    public String getAddress() {
        return addressField.getText().trim();
    }

    public String getPhone() {
        return phoneField.getText().trim();
    }

    /*******************************************
    *                   LOGIC
    *******************************************/
    private void setFields(){
        
        lastNameField.setText(
            User.getConnected().getLastName()
        );

        firstNameField.setText(
            User.getConnected().getFirstName()
        );

        addressField.setText(
            User.getConnected().getAdresse()
        );

        phoneField.setText(
            User.getConnected().getPhone()
        );
    }

    private void updateProfile(){
        if (new UserController().edit(getData(), User.getConnected())) {
            JOptionPane.showMessageDialog(
                this,
                "Modifier avec success",
                "Modifier compte",
                JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    private Map<String, String> getData(){

        Map<String, String> data = new HashMap<>();

        data.put("firstName", getFirstName());
        data.put("lastName", getLastName());
        data.put("email", User.getConnected().getEmail());
        data.put("phone", getPhone());
        data.put("adresse", getAddress());
        data.put("password", User.getConnected().getPassword());
        data.put("role", User.getConnected().getRole());

        return data;
    }
}
