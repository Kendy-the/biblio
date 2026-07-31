package com.biblio.views.adherent;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.biblio.config.BColor;
import com.biblio.controllers.AdherentController;
import com.biblio.models.Adherent;
import com.biblio.views.components.BButton;
import com.biblio.views.components.BSroll.BScrollPan;
import com.biblio.views.components.BTable.BTable;

public class SearchAdherentDialog extends JDialog {

    private JTextField lastNameField;
    private JTextField firstNameField;
    private JTextField phoneField;

    private BTable table;

    public SearchAdherentDialog(JFrame parent,String title,boolean modal) {

        super(parent, title, modal);

        setSize(900, 500);
        setLocationRelativeTo(null);
        setResizable(false);

        init();
    }

    public void showDialog() {
        this.setVisible(true);
    }

    private void init() {

        JPanel searchPanel = new JPanel();

        searchPanel.setBackground(
                BColor.WHITE.get()
        );

        // LAST NAME
        JPanel panLastName = new JPanel();

        panLastName.setBackground(
                BColor.WHITE.get()
        );

        panLastName.setBorder(
                BorderFactory.createTitledBorder(
                        "Nom"
                )
        );

        lastNameField = new JTextField();

        lastNameField.setPreferredSize(
                new Dimension(180, 25)
        );

        panLastName.add(lastNameField);

        // FIRST NAME
        JPanel panFirstName = new JPanel();

        panFirstName.setBackground(
                BColor.WHITE.get()
        );

        panFirstName.setBorder(
                BorderFactory.createTitledBorder(
                        "Prénom"
                )
        );

        firstNameField = new JTextField();

        firstNameField.setPreferredSize(
                new Dimension(180, 25)
        );

        panFirstName.add(firstNameField);

        // PHONE
        JPanel panPhone = new JPanel();

        panPhone.setBackground(
                BColor.WHITE.get()
        );

        panPhone.setBorder(
                BorderFactory.createTitledBorder(
                        "Téléphone"
                )
        );

        phoneField = new JTextField();

        phoneField.setPreferredSize(
                new Dimension(180, 25)
        );

        panPhone.add(phoneField);

        searchPanel.add(panFirstName);
        searchPanel.add(panLastName);
        searchPanel.add(panPhone);

        // BUTTON SEARCH
        BButton btnSearch = new BButton("Rechercher");

        btnSearch.addActionListener(e -> {
            searchAdherents();
        });

        // BUTTON CLOSE
        BButton btnClose = new BButton("Fermer");

        btnClose.addActionListener(e -> {
            dispose();
        });

        searchPanel.add(btnSearch);
        searchPanel.add(btnClose);

        // TABLE
        table = new BTable(
                "ID",
                "Prénom",
                "Nom",
                "Téléphone"
        );

        BScrollPan scroll = new BScrollPan(table);

        getContentPane().add(
                searchPanel,
                BorderLayout.NORTH
        );

        getContentPane().add(
                scroll,
                BorderLayout.CENTER
        );
    }

    private void searchAdherents() {

        String lastName =
                lastNameField.getText().trim();

        String firstName =
                firstNameField.getText().trim();

        String phone =
                phoneField.getText().trim();

        table.clearRows();

        List<Adherent> adherents =
                new AdherentController()
                        .search(
                                lastName,
                                firstName,
                                phone
                        );

        for (Adherent a : adherents) {

            table.addRow(a);
        }
    }
}
