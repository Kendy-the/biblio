package com.biblio.views.loan;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.text.MaskFormatter;

import com.biblio.config.BColor;
import com.biblio.controllers.LoanController;
import com.biblio.models.Loan;
import com.biblio.utils.BUtils;
import com.biblio.views.components.BButton;
import com.biblio.views.components.BSroll.BScrollPan;
import com.biblio.views.components.BTable.BTable;

public class SearchLoanDialog extends JDialog {

    private MaskFormatter mask;
    private JFormattedTextField endDateField;
    private JFormattedTextField startDateField;

    private BTable table;

    public SearchLoanDialog(JFrame parent,String title,boolean modal) {

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

        try {
            mask = new MaskFormatter("####-##-##");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        JPanel searchPanel = new JPanel();

        searchPanel.setBackground(
                BColor.WHITE.get()
        );

        // End date
        JPanel panEndDate = new JPanel();

        panEndDate.setBackground(
                BColor.WHITE.get()
        );

        panEndDate.setBorder(
                BorderFactory.createTitledBorder(
                        "Date de retour"
                )
        );

        endDateField = new JFormattedTextField(mask);

        endDateField.setPreferredSize(
                new Dimension(180, 25)
        );

        panEndDate.add(endDateField);

        // start date
        JPanel panStartDate = new JPanel();

        panStartDate.setBackground(
                BColor.WHITE.get()
        );

        panStartDate.setBorder(
                BorderFactory.createTitledBorder(
                        "Date pret"
                )
        );

        startDateField = new JFormattedTextField(mask);

        startDateField.setPreferredSize(
                new Dimension(180, 25)
        );

        panStartDate.add(startDateField);

        searchPanel.add(panStartDate);
        searchPanel.add(panEndDate);

        // BUTTON SEARCH
        BButton btnSearch = new BButton("Rechercher");

        btnSearch.addActionListener(e -> {
            searchLoans();
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
            "Livre",
            "Adherent",
            "Date pret",
            "Date de retour",
            "Statut"
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

    private void searchLoans() {

        List<Loan> loans =
            new LoanController()
                .search(
                    BUtils.toDate(startDateField.getText()),
                    BUtils.toDate(endDateField.getText())
                );
                
        table.clearRows();

        for (Loan a : loans) {

            table.addRow(a);
        }
    }
}
