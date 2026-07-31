package com.biblio.views.loan;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.biblio.config.BApp;
import com.biblio.controllers.LoanController;
import com.biblio.observer.BAppEventObservable;
import com.biblio.observer.LoanListener;
import com.biblio.views.Views;
import com.biblio.views.components.BButton;
import com.biblio.views.components.BaseFrame;
import com.biblio.views.components.BSroll.BScrollPan;
import com.biblio.views.components.BTable.BTable;
import com.biblio.views.components.BTable.BTableActionCellEditor;
import com.biblio.views.components.BTable.BTableActionCellRender;
import com.biblio.views.components.BTable.BTableActionListener;

public class Loan extends Views{

    private BaseFrame parent;
    private NewLoanDialog newLoanDialog;

    private BButton newLoan;
    private SearchLoanDialog searchLoanDialog;
    private BButton searchBButton;
    private BButton save;

    private ArrayList<com.biblio.models.Loan> loans;
    private BTable loansTable;
    private DefaultTableModel tableModel;

    private BLoanListener loanListener;

    public Loan(BaseFrame parent){

        super();

        this.parent = parent;
        loanListener = new BLoanListener();

        /*
        * TOP - DESIGN
        */
        this.BTop();

        /*
        *  CENTER - DESIGN
        */
        this.BCenter();
        
        /*
        *  BOTTOM - DESIGN
        */
        this.BBottom();
    }

    public Loan get(){
        return this;
    }

    /*******************************************
    *           LOGIC PART METHODE            *
    ********************************************/

    public void initApp(){
        loansTable = new BTable(
            "ID",
            "Livre",
            "Adherent",
            "Date de pret",
            "date de retour",
            "Statut",
            "Action"
        );
        tableModel = (DefaultTableModel) loansTable.getModel();

        index();
        initEvent();
    }

    public void index(){

        this.loans = new LoanController().index();

        for (com.biblio.models.Loan loan : loans) {
            loansTable.addRow(loan);
        }
    }

    private void initEvent(){
        BAppEventObservable.addLoanListener(loanListener);
    }

    public void edit(int row){

        loansTable.setEditRow(row);

        Object id = tableModel.getValueAt(row, 0);

        com.biblio.models.Loan a = this.getById(Integer.parseInt((String)id));

        if (a != null) { 
            
            NewLoanDialog editLoanDialog = new NewLoanDialog(
                parent, 
                "Modifier Pret", 
                true,
                a
            );
            editLoanDialog.showDialog();
        }
        
    }

    public void delete(int row){

        if (loansTable.isEditing()) {
            loansTable.getCellEditor().stopCellEditing();
        }

        Object id = tableModel.getValueAt(row, 0);

        com.biblio.models.Loan l = this.getById(Integer.parseInt((String)id));

        String msg = "Erreur, lors de la suppression";

        if (l != null) { 

            if (!wantToDelete("ce pret et les informations le concernant")) {
                return;
            }

            if(LoanController.delete(l)) {
                msg = "Supprimer avec success";
                tableModel.removeRow(row);

                // For listeners
                BAppEventObservable.notifyDeletedLoan(l);
            }

        }

        JOptionPane.showMessageDialog(
            parent, 
            msg,
            "Supprimer loan",
            JOptionPane.INFORMATION_MESSAGE
        );

    }

    public com.biblio.models.Loan getById(int id){

        for (com.biblio.models.Loan loan : loans) {
            if (loan.getId() == id) {
                return loan;
            }
        }

        return null;
    }

    public void refreshLoans() {
        loansTable.clearRows();
        index();
    }

    /*******************************************
    *           DESIGN PART METHODE            *
    ********************************************/

    private void BTop(){

        this.top.setBTitle(
            BApp.SIDE_LOAN.get(),
            true
        );
        this.top.setBDescribe("Gérez les prets des adherents de votre bibliothèque");

        // add loan
        this.newLoanDialog = new NewLoanDialog(
            parent, 
            "Nouveau Pret",
            true
        );
        this.newLoan = new BButton(
        "Ajouter un pret",
            getClass().getResource(BApp.IMG_PATH.get() + "new.png")
        );
        this.newLoan.setRounded(10);
        this.newLoan.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                newLoanDialog.showDialog();
            }
        });

        // Search loan
        this.searchLoanDialog = new SearchLoanDialog(
            parent,
            "Recherche pret",
            true
        );
        this.searchBButton = new BButton(
            "Chercher un pret",
            getClass().getResource(BApp.IMG_PATH.get() + "search.png")
        );
        this.searchBButton.setRounded(10);
        this.searchBButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                searchLoanDialog.showDialog();
            }
        });

        this.top.setBButton(searchBButton);
        this.top.setBButton(newLoan);
    }

    private void BCenter(){
        
        // Set-Up Table
        initApp();

        loansTable.setActionColumn(6);
        loansTable.getColumnModel()
        .getColumn(6)
        .setCellRenderer(
                new BTableActionCellRender()
        );

        loansTable.getColumnModel()
        .getColumn(6)
        .setCellEditor(
            new BTableActionCellEditor( loansTable,
                new BTableActionListener() {

                    @Override
                    public void onEdit(int row) {
                        edit(row);
                    }

                    @Override
                    public void onDelete(int row) {
                        delete(row);
                    }
                }
            )
        );

        this.center.setLayout(new GridLayout(1,1));
        this.center.setBorder(
            new EmptyBorder(
            20, 
            0,
            20,
            0
        ));
        this.center.add(new BScrollPan(loansTable));
    }

    private void BBottom(){

        this.save = new BButton("Sauvegarder");
        this.save.setRounded(10);
        this.save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){

                JOptionPane.showMessageDialog(null, 
                    "Enregistrer avec success", 
                    "Pret Enregistrer",
                    JOptionPane.INFORMATION_MESSAGE
                );
            }
        });

        bottom.setLayout(new BorderLayout());
        bottom.setBorder(new EmptyBorder(10,0,10,0));
        bottom.add(this.save, BorderLayout.EAST);
    }

    /*******************************************
    *          LISTENER PART METHODE           *
    ********************************************/

    class BLoanListener implements LoanListener{

        @Override
        public void addedLoan(com.biblio.models.Loan loan) {
            refreshLoans();
        }

        @Override
        public void editedLoan(com.biblio.models.Loan loan) {
            refreshLoans();
        }

        @Override
        public void deletedLoan(com.biblio.models.Loan loan){}

        @Override
        public void update(Object object) {}

    }

}
