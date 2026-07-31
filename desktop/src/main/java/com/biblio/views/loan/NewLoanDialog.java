package com.biblio.views.loan;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.text.MaskFormatter;

import com.biblio.config.BColor;
import com.biblio.config.BApp;
import com.biblio.controllers.AdherentController;
import com.biblio.controllers.BookController;
import com.biblio.controllers.LoanController;
import com.biblio.errors.BErrorMgr;
import com.biblio.models.Adherent;
import com.biblio.models.Book;
import com.biblio.models.Loan;

import com.biblio.observer.AdherentListener;
import com.biblio.observer.BAppEventObservable;
import com.biblio.observer.BookListener;

import com.biblio.views.components.BButton;
import com.biblio.views.components.BComponentPan;

public class NewLoanDialog extends JDialog{

    private JFormattedTextField endDateField;
    private JFormattedTextField startDateField;

    private JComboBox<Object> bookIdField;
    
    private JComboBox<Object> adherentIdField;
    
    @SuppressWarnings("rawtypes")
    private JComboBox statusField;

    private Loan loan;
    private ArrayList<Adherent> adherents;
    private ArrayList<Book> books;

    private BBookListener bookListener;
    private BAdherentListener adherentListener;

    private MaskFormatter mask;
  
    public NewLoanDialog(JFrame parent,String title,boolean modal) {

        super(parent, title, modal);

        setSize(650, 300);
        setLocationRelativeTo(null);
        setResizable(false);

        init();
    }

    public NewLoanDialog(JFrame parent,String title,boolean modal,Loan loan) {

        super(parent, title, modal);

        this.loan = loan;

        setSize(650, 300);
        setLocationRelativeTo(null);
        setResizable(false);

        init();

        loadData();
    }

    public void showDialog(){
        this.setVisible(true);
    }

    @SuppressWarnings("unchecked")
    private void init() {

        bookListener = new BBookListener();
        adherentListener = new BAdherentListener();
        
        BAppEventObservable.addBookListener(bookListener);
        BAppEventObservable.addAdherentListener(adherentListener);
        
        JPanel content = new JPanel();

        content.setBackground(
                BColor.WHITE.get()
        );

        // Mask Date
        mask = null;
        try {
            mask = new MaskFormatter("####-##-##");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // end date

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
                new Dimension(250, 25)
        );

        panEndDate.add(endDateField);

        // start date

        JPanel panStartDate = new JPanel();

        panStartDate.setBackground(
                BColor.WHITE.get()
        );

        panStartDate.setBorder(
                BorderFactory.createTitledBorder(
                        "Date debut"
                )
        );

        startDateField = new JFormattedTextField(mask);

        startDateField.setPreferredSize(
                new Dimension(250, 25)
        );

        panStartDate.add(startDateField);

        // bookId

        JPanel panBookId = new JPanel();

        panBookId.setBackground(
                BColor.WHITE.get()
        );

        panBookId.setBorder(
                BorderFactory.createTitledBorder(
                        "Livre"
                )
        );

        bookIdField = new JComboBox<Object>();
        bookIdField.setPreferredSize(
                new Dimension(250, 25)
        );

        books = new BookController().index();
        setBookIdField(books);
        bookIdField.setSelectedIndex(-1);

        panBookId.add(bookIdField);

        // AdherentId

        JPanel panAdherentId = new JPanel();

        panAdherentId.setBackground(
                BColor.WHITE.get()
        );

        panAdherentId.setBorder(
                BorderFactory.createTitledBorder(
                        "Adherent"
                )
        );

        adherentIdField = new JComboBox<Object>();
        adherentIdField.setPreferredSize(
            new Dimension(250, 25)
        );
       
        adherents = new AdherentController().index();
        setAdherentIdField(adherents);

        adherentIdField.setSelectedIndex(-1);

        panAdherentId.add(adherentIdField);

        // StatusField

        JPanel panstatusField = new JPanel();

        panstatusField.setBackground(
            BColor.WHITE.get()
        );

        panstatusField.setBorder(
            BorderFactory.createTitledBorder(
                    "Statut"
            )
        );

        statusField = new JComboBox<>();
        statusField.setPreferredSize(
                new Dimension(250, 25)
        );
        statusField.addItem(
            BApp.LOAN_ACTIF_STATUS.get()
        );
        statusField.addItem(
            BApp.LOAN_RETURNED_STATUS.get()
        );

        panstatusField.add(statusField);

        BComponentPan p1 = new BComponentPan();
        p1.add(panStartDate);
        p1.add(panEndDate);
        
        BComponentPan p2 = new BComponentPan();
        p2.add(panBookId);
        p2.add(panAdherentId);

        content.add(p1);
        content.add(p2);
        content.add(panstatusField);

        // Boutons

        JPanel control = new JPanel();

        BButton saveButton =
                new BButton("Enregistrer");

        saveButton.addActionListener(e -> {

            if(loan != null){
                edit();
            }else{
                save();
            }

            setVisible(false);
        });

        BButton cancelButton =
                new BButton("Annuler");

        cancelButton.addActionListener(e -> {
            setVisible(false);
        });

        control.add(saveButton);
        control.add(cancelButton);

        getContentPane().add(
                content,
                BorderLayout.CENTER
        );

        getContentPane().add(
                control,
                BorderLayout.SOUTH
        );
    }

    public void setAdherentIdField(ArrayList<Adherent> adhs){
        for (Adherent a  : adhs) {
            adherentIdField.addItem(a.getFirstName() + " " + a.getLastName());
        }
    }

    public void setBookIdField(ArrayList<Book> bks){
        for (Book b : bks) {
            bookIdField.addItem(b.getBTitle());
        }
    }

    private void loadData() {

        endDateField.setText(
            loan.getEndDate()
        );

        startDateField.setText(
            loan.getStartDate()
        );

        bookIdField.setSelectedIndex(getSelectedBook());

        adherentIdField.setSelectedIndex(getSelectedAdherent());

        statusField.setSelectedIndex(loan.getStatus());
    }

    private int getSelectedBook(){

        int i = 0;
        for (Book b : books) {
            if (
                b.getId() ==  Integer.parseInt(
                    loan.getBookId()
                )) {
                return i;
            }
            i++;
        }

        return -1;
    }

    private int getSelectedAdherent(){

        int i = 0;
        for (Adherent a : adherents) {
            if (
                a.getId() ==  Integer.parseInt(
                    loan.getAdherentId()
                )) {
                return i;
            }
            i++;
        }

        return -1;
    }

    private void save() {

        String msg;
        int icon;

        int insertId = new LoanController()
                .save(getData());

        if(insertId > 0){

           msg = "Enregistrer avec success";
           icon = JOptionPane.INFORMATION_MESSAGE;

            // For Listeners
            Loan l = new Loan(getData());
            l.setId(insertId);

            BAppEventObservable.notifyAddedLoan(l);
            clearFields();

        }else{
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
            "Enregistrer Pret",
            icon
        );
    }

    private void edit() {

        String msg;
        int icon;

        if(new LoanController()
                .edit(getData(), loan)){

            msg = "Modifier avec success";
            icon = JOptionPane.INFORMATION_MESSAGE;

            // For Listeners
            Loan  l = new Loan(getData());
            l.setId(loan.getId());
            
            BAppEventObservable.notifyEditedLoan(l);
            clearFields();

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
            "Enregistrer Pret",
            icon
        );
    }

    private Map<String, String> getData() {

        Map<String, String> data =
                new HashMap<>();
        
        int selected = -1;

        data.put(
            "endDate",
            String.valueOf(endDateField.getText())
        );

        data.put(
            "startDate",
            String.valueOf(startDateField.getText())
        );

        selected = bookIdField.getSelectedIndex();
        if (selected < 0) {

            BErrorMgr.addError("Livre", 
            "Veuillez selectionner un *Livre !"
            );

            return new HashMap<>();
        }

        data.put(
            "bookId",
            String.valueOf(
                books.get(
                    bookIdField.getSelectedIndex()
                ).getId()
            )
        );

        selected = adherentIdField.getSelectedIndex();
        if (selected < 0) {

            BErrorMgr.addError("Adherent", 
            "Veuillez selectionner un *Adherent !"
            );

            return new HashMap<>();
        }

        data.put(
            "adherentId",
            String.valueOf(
                adherents.get(
                    adherentIdField.getSelectedIndex()
                ).getId()
            )
        );

        data.put(
            "status",
            String.valueOf(
                statusField.getSelectedItem()
            )
        );

        return data;
    }

    private void clearFields() {

        endDateField.setValue(null);
        startDateField.setValue(null);
        bookIdField.setSelectedIndex(-1);
        adherentIdField.setSelectedIndex(-1);
        statusField.setSelectedIndex(0);
    }

    /*******************************************
    *          LISTENER PART METHODE           *
    ********************************************/

    class BBookListener implements BookListener{

        @Override
        public void addedBook(com.biblio.models.Book book) {
            books.add(book);
            bookIdField.addItem(book.getBTitle());
        }
        
        @Override
        public void editedBook(com.biblio.models.Book book) {}

        @Override
        public void deletedBook(com.biblio.models.Book book) {}

        @Override
        public void update(Object object) {}

    }

    class BAdherentListener implements AdherentListener {

        @Override
        public void addedAdherent(com.biblio.models.Adherent adherent) {
            adherents.add(adherent);
            adherentIdField.addItem(adherent.getFirstName() + " " + adherent.getLastName());
        }

        @Override
        public void update(Object object) {}

        @Override
        public void editedAdherent(com.biblio.models.Adherent adherent) {}

        @Override
        public void deletedAdherent(com.biblio.models.Adherent adherent) {}

    }
   
}
