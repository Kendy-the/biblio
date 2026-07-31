package com.biblio.views.report;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import com.biblio.config.BApp;
import com.biblio.controllers.LoanController;
import com.biblio.models.Loan;

import com.biblio.observer.BAppEventObservable;
import com.biblio.observer.LoanListener;
import com.biblio.utils.BUtils;
import com.biblio.views.Views;
import com.biblio.views.components.BButton;
import com.biblio.views.components.BInfoCard;
import com.biblio.views.components.BComponentPan;
import com.biblio.views.components.BaseFrame;
import com.biblio.views.components.BSroll.BScrollPan;
import com.biblio.views.components.BTable.BTable;

public class Report extends Views {

    private BaseFrame parent;
    private BComponentPan statisticsPanel;

    private BTable reportTable;

    private BButton generateButton;
    private BButton exportButton;
    private BButton printButton;

    private  List<Loan> loans;

    private MaskFormatter mask;
    private JFormattedTextField startDate;
    private JFormattedTextField endDate;

    private JComboBox<Object> status;

    private BInfoCard activeLoansCard;
    private BInfoCard adherentsCard;
    private BInfoCard booksCard;

    private BLoanListener loanListener;

    public Report(BaseFrame parent) {

        super();

        this.parent = parent;

        loanListener = new BLoanListener();
        BAppEventObservable.addLoanListener(loanListener);

        try {
            mask = new MaskFormatter("####-##-##");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        buildTop();
        buildCenter();
        buildbottom();

        loadCurrentLoans();
    }

    public Report get(){ return this;}

    private void buildTop() {

        top.setBTitle(
            BApp.SIDE_RAPPORT.get(),
            true
        );

        top.setBDescribe(
            "Générez les rapports des prêts en cours"
        );

        // DATE

        startDate = new JFormattedTextField(mask);
        startDate.setPreferredSize(
            new Dimension(80,30)
        );

        endDate = new JFormattedTextField(mask);
        endDate.setPreferredSize(
            new Dimension(80,30)
        );

        // STATUT
        status = new JComboBox<>();
        status.setPreferredSize(
            new Dimension(80,30)
        );
        status.addItem(BApp.LOAN_ACTIF_STATUS.get());
        status.addItem(BApp.LOAN_RETURNED_STATUS.get());
        status.setSelectedIndex(0);

        generateButton = new BButton(
            "Générer",
            getClass().getResource(BApp.IMG_PATH.get() + "search.png")
        );

        generateButton.setRounded(10);

        generateButton.addActionListener(e -> {
            generateReport();
        });

        exportButton = new BButton(
            "Exporter",
            getClass().getResource(BApp.IMG_PATH.get() + "download.png")
        );

        exportButton.setRounded(10);

        exportButton.addActionListener(e -> {
            exportReport();
        });

        top.addObject(new JLabel("DE"));
        top.addObject(startDate);
        top.addObject(new JLabel("A"));
        top.addObject(endDate);
        top.addObject(status);
        top.addObject(generateButton);
        top.addObject(exportButton);
    }

    private void buildCenter() {

        center.setLayout(new BorderLayout());

        statisticsPanel = new BComponentPan();
        statisticsPanel.setLayout(
            new GridLayout(1, 3, 15, 15)
        );

        
        statisticsPanel.setBorder(
            new EmptyBorder(20, 0, 20, 0)
        );

        activeLoansCard = new BInfoCard(
            "Prets",
            String.valueOf(
                0
            ),
            getClass().getResource(BApp.IMG_PATH.get() + "loan.png")
        );

        adherentsCard = new BInfoCard(
            "Adherents",
            String.valueOf(
                0
            ),
            getClass().getResource(BApp.IMG_PATH.get() + "adherent.png")
        );

        booksCard = new BInfoCard(
            "Livres",
            String.valueOf(
                0
            ),
            getClass().getResource(BApp.IMG_PATH.get() + "book.png")
        );
        
        updateStatistics(
            new LoanController()
            .getCurrentLoans()
        );

        statisticsPanel.add(activeLoansCard);
        statisticsPanel.add(adherentsCard);
        statisticsPanel.add(booksCard);

        reportTable = new BTable(
            "ID",
            "Livre",
            "Adhérent",
            "Date début",
            "Date fin",
            "Statut"
        );

        center.add(
            statisticsPanel,
            BorderLayout.NORTH
        );

        center.add(
            new BScrollPan(reportTable),
            BorderLayout.CENTER
        );
    }

    private void buildbottom() {

        bottom.setLayout(
            new FlowLayout(
                FlowLayout.RIGHT
            )
        );

        printButton = new BButton(
            "Imprimer",
            getClass().getResource(BApp.IMG_PATH.get() + "printer.png")
        );

        printButton.setRounded(10);

        printButton.addActionListener(e -> {

            JOptionPane.showMessageDialog(
                parent,
                "Impression du rapport..."
            );

            PdfExporter.exportTable(
                reportTable,
                "Rapport des prêts en cours"
            );

        });

        bottom.add(printButton);
    }

    private void loadCurrentLoans() {

        loans =
            new LoanController().getCurrentLoans();

        populateTable(loans);
    }

    private void generateReport() {

        List<Loan> loansReport =
            new LoanController().getLoans(
                BUtils.toDate(startDate.getText()),
                BUtils.toDate(endDate.getText()),
                String.valueOf(status.getSelectedItem())
            );

        populateTable(loansReport);

        startDate.setValue(null);
        endDate.setValue(null);
    }

    private void populateTable(
        List<Loan> loans
    ) {

        reportTable.clearRows();

        for (Loan loan : loans) {

            reportTable.addRow(loan);
        }
    }

    private void updateStatistics(
        List<Loan> loans
    ) {

        activeLoansCard.setSubTitle(
            String.valueOf( loans.size())
        );

        adherentsCard.setSubTitle(
            String.valueOf(
                getTotalAdherent(loans)
            )
        );

        booksCard.setSubTitle(
            String.valueOf(
                getTotalBook(loans)
            )
        );
    }

    public Long getTotalBook(List<Loan> loans){
        return loans.stream()
            .map(
            l -> l.getBook()
                    .getIsbn()
            )
            .distinct()
        .count();
    }

    private Long getTotalAdherent(List<Loan> loans){
        return loans.stream()
            .map(
            l -> l.getAdherent()
                    .getId()
            )
            .distinct()
        .count();
    }

    private void exportReport() {

        JOptionPane.showMessageDialog(
            parent,
            "Export PDF..."
        );

        PdfExporter.exportTable(
            reportTable,
            "Rapport des prêts en cours"
        );
    }

    public void refeshReport() {
        loadCurrentLoans();
        populateTable(loans);
        updateStatistics(loans);
    }

    /*******************************************
    *          LISTENER PART METHODE           *
    ********************************************/

    class BLoanListener implements LoanListener {

        @Override
        public void addedLoan(Loan loan) {
            refeshReport();
        }

        @Override
        public void editedLoan(Loan loan) {
            refeshReport();
        }

        @Override
        public void deletedLoan(Loan loan) {
            refeshReport();
        }

        @Override
        public void update(Object object) {}
    
    }

}
