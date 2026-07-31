package com.biblio.views.book;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.biblio.config.BApp;
import com.biblio.controllers.BookController;
import com.biblio.errors.BErrorMgr;
import com.biblio.models.Loan;
import com.biblio.observer.BAppEventObservable;
import com.biblio.observer.BookListener;
import com.biblio.observer.LoanListener;
import com.biblio.views.Views;
import com.biblio.views.components.BButton;
import com.biblio.views.components.BaseFrame;
import com.biblio.views.components.BSroll.BScrollPan;
import com.biblio.views.components.BTable.BTable;
import com.biblio.views.components.BTable.BTableActionCellEditor;
import com.biblio.views.components.BTable.BTableActionCellRender;
import com.biblio.views.components.BTable.BTableActionListener;

public class Book extends Views{

    private BaseFrame parent;
    private NewBookDialog newBookDialog;

    private BButton newBook;
    private SearchBookDialog searchBookDialog;
    private BButton searchBButton;
    private BButton save;

    private ArrayList<com.biblio.models.Book> books;
    private BTable booksTable;
    private DefaultTableModel tableModel;

    private BBookListener bookListener;
    private BLoanListener loanListener;

    public Book(BaseFrame parent){

        super();

        this.parent = parent;
        bookListener = new BBookListener();
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

    public Book get(){
        return this;
    }

    /*******************************************
    *           LOGIC PART METHODE            *
    ********************************************/

    public void index(){

        this.books = new BookController().index();

        for (com.biblio.models.Book book : books) {
            booksTable.addRow(book);
        }

    }

    public void initApp(){
        booksTable = new BTable(
            "ISBN",
            "Titre",
            "Annee",
            "Auteur",
            "Quantite",
            "Actions"
        );
        tableModel = (DefaultTableModel) booksTable.getModel();

        index();
        initEvent();
    }


    private void initEvent(){
        BAppEventObservable.addBookListener(bookListener);
        BAppEventObservable.addLoanListener(loanListener);
    }

    public void editBook(int row){

        booksTable.setEditRow(row);

        Object isbn = tableModel.getValueAt(row, 0);

        com.biblio.models.Book b = this.getBookByIsbn((String) isbn);

        if (b != null) { 
            
            NewBookDialog editBookDialog = new NewBookDialog(
                parent, 
                "Modifier livre", 
                true,
                b
            );
            editBookDialog.showDialog();

        }
        
    }

    public void deleteBook(int row){

        if (booksTable.isEditing()) {
            booksTable.getCellEditor().stopCellEditing();
        }

        Object isbn = tableModel.getValueAt(row, 0);

        com.biblio.models.Book b = this.getBookByIsbn((String) isbn);

        String msg = "Erreur, lors de la suppression";

        if (b != null) { 

            if (!wantToDelete(b.getBTitle())) {
                return;
            }

            if(BookController.delete(b)) {
                msg = "Supprimer avec success";
                tableModel.removeRow(row);

                // For listeners
                BAppEventObservable.notifyDeletedBook(b);
            }else{
               
                if (BErrorMgr.hasErrors()) {
                    msg = BErrorMgr.getErrorsMessage();
                } else {
                    msg = "Erreur, champ invalide";
                }
            }
        }

        JOptionPane.showMessageDialog(
            parent, 
            msg,
            "Supprimer livre",
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    public com.biblio.models.Book getBookByIsbn(String isbn){
        return BookController.getByIsbn(isbn);
    }

    private int getEditRow(ArrayList<com.biblio.models.Book> books , com.biblio.models.Book book){
        
        int i = 0;
        for (com.biblio.models.Book b : books) {
            if (b.getId() == book.getId()) {
                return i;
            }
        }

        return -1;
    }

    private void forRefreshLoaned(Loan loan){

        com.biblio.models.Book b = loan.getBook();
        booksTable.setEditingRow(getEditRow(books, b));

        int i = 0;
        for (String field : b.toArray()) {
            tableModel.setValueAt(field, booksTable.getEditingRow(), i);
            i++;
        }
    }

    public void refreshBooks() {
        booksTable.clearRows();
        index();
    }

    /*******************************************
    *           DESIGN PART METHODE            *
    ********************************************/

    private void BTop(){

        this.top.setBTitle(
            BApp.SIDE_BOOK.get(),
            true
        );
        this.top.setBDescribe("Gérez la collection de livres de votre bibliothèque");

        // add book
        this.newBookDialog = new NewBookDialog(
            parent, 
            "Nouveau Livre",
            true
        );
        
        this.newBook = new BButton(
        "Ajouter un livre",
            getClass().getResource(BApp.IMG_PATH.get() + "new.png")
        );
        this.newBook.setRounded(10);
        this.newBook.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                newBookDialog.showDialog();
            }
        });

        // Search book
        this.searchBookDialog = new SearchBookDialog(
            parent,
            "Recherche livre",
            true
        );
        this.searchBButton = new BButton(
            "Chercher un livre",
            getClass().getResource(BApp.IMG_PATH.get() + "search.png")
        );
        this.searchBButton.setRounded(10);
        this.searchBButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                searchBookDialog.showDialog();
            }
        });

        this.top.setBButton(searchBButton);
        this.top.setBButton(newBook);
    }

    private void BCenter(){
        
        // Set-Up Table
        initApp();

        booksTable.setActionColumn(5);
        booksTable.getColumnModel()
        .getColumn(5)
        .setCellRenderer(
                new BTableActionCellRender()
        );

        booksTable.getColumnModel()
        .getColumn(5)
        .setCellEditor(
            new BTableActionCellEditor( booksTable,
                new BTableActionListener() {

                    @Override
                    public void onEdit(int row) {
                        editBook(row);
                    }

                    @Override
                    public void onDelete(int row) {
                        deleteBook(row);
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
        this.center.add(new BScrollPan(booksTable));
    }

    private void BBottom(){

        this.save = new BButton("Sauvegarder");
        this.save.setRounded(10);
        this.save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){

                JOptionPane.showMessageDialog(null, 
                    "Enregistrer avec success", 
                    "Livre Enregistrer",
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

    class BBookListener implements BookListener{

        @Override
        public void addedBook(com.biblio.models.Book book) {
            refreshBooks();
        }

        @Override 
        public void editedBook(com.biblio.models.Book book) {
            refreshBooks();
        }

        @Override
        public void deletedBook(com.biblio.models.Book book) {}

        @Override
        public void update(Object object) {}

    }

    class BLoanListener implements LoanListener {

        @Override
        public void addedLoan(Loan loan) {
            forRefreshLoaned(loan);
        }

        @Override
        public void editedLoan(Loan loan) {
            forRefreshLoaned(loan);
        }

        @Override
        public void deletedLoan(Loan loan) {
            forRefreshLoaned(loan);
        }

        @Override
        public void update(Object object) {}
    
    }

}
