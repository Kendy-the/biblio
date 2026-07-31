package com.biblio.views;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.biblio.config.BApp;
import com.biblio.config.BFont;
import com.biblio.config.BPath;
import com.biblio.controllers.BookController;
import com.biblio.controllers.LoanController;
import com.biblio.models.Book;
import com.biblio.observer.BAppEventObservable;
import com.biblio.observer.BookListener;
import com.biblio.observer.LoanListener;
import com.biblio.views.components.BInfoCard;
import com.biblio.views.components.BComponentPan;

public class Dashboard extends Views{

    private BComponentPan bottomPan;
    private JLabel bottomTitle;
    private ArrayList<BInfoCard> cards = new ArrayList<BInfoCard>();

    private int totalBooks;
    private Long currentAdherent;
    private int totalExemplaire;
    private int currentLoan;
    private int availlableExemplaire;
    private int lateBook;

    private BInfoCard totalBookCard;
    private BInfoCard currentAdherentCard;
    private BInfoCard totalExemplaireCard;
    private BInfoCard currentLoanCard;
    private BInfoCard availlableExemplaireCard;
    private BInfoCard lateBookCard;

    private BBookListener bookListener;
    private BLoanListener loanListener;

    public Dashboard(){

        bookListener = new BBookListener();
        loanListener = new BLoanListener();

        /*
        *  TOP PARENT PAN
        */  
        this.top.setBTitle(
            BApp.SIDE_DASHBOARD.get(),
            true
        );
        this.top.setBDescribe("Bienvenue ! Voici les dernières nouvelles de biblio.");

        this.bottomTitle = new JLabel();

        this.initStyle();

        /*
        * CENTER
        */
        totalBooks = new BookController().getCountBooks();
        totalBookCard = new BInfoCard(
            "Livres au total",
            String.valueOf(totalBooks),
            getClass().getResource(BPath.IMG_PATH.get() + "book.png")
        );
        this.addCard(totalBookCard);
        
        currentAdherent = new LoanController().getCountCurrentAdherent();
        currentAdherentCard = new BInfoCard(
            "Emprunteurs actif",
            String.valueOf(currentAdherent),
            getClass().getResource(BPath.IMG_PATH.get() + "adherent.png")
        );
        this.addCard(currentAdherentCard);

        totalExemplaire =  new BookController().getCountBookExemplaire();
        totalExemplaireCard = new BInfoCard(
            "Exemplaires de livres",
            String.valueOf(totalExemplaire),
            getClass().getResource(BPath.IMG_PATH.get() + "file.png")
        );
        this.addCard(totalExemplaireCard);

        currentLoan = new LoanController().getCountCurrentLoans();
        currentLoanCard = new BInfoCard(
            "Pret en cours",
            String.valueOf(currentLoan),
            getClass().getResource(BPath.IMG_PATH.get() + "loan.png")
        );
        this.addCard(currentLoanCard);

        availlableExemplaire = new BookController().getCountBookAvaillableExemplaire();
        availlableExemplaireCard = new BInfoCard(
            "Exemplaires disponible",
            String.valueOf(availlableExemplaire),
            getClass().getResource(BPath.IMG_PATH.get() + "time.png")
        );
        this.addCard(availlableExemplaireCard);

        lateBook = new LoanController().getCountLateBook();
        lateBookCard = new BInfoCard(
            "Livres en retard",
            String.valueOf(lateBook),
            getClass().getResource(BPath.IMG_PATH.get() + "alert.png")
        );
        this.addCard(lateBookCard);

        /*
        * BOTTOM
        */
        this.bottomTitle.setText("Recent");
        this.bottomTitle.setFont(new Font(
            BFont.PRIMARY.get(),
            Font.BOLD,
            20
        ));
        
        ArrayList<Book> books = new BookController().getRecent();
        
        this.addBCard(new BInfoCard(
            books.get(0).getBTitle() != null
            ? books.get(0).getBTitle()
            : "Empty",
            getClass().getResource(BPath.IMG_PATH.get() + "user.png")
        ));
        
        this.addBCard(new BInfoCard(
            books.get(1).getBTitle() != null
            ? books.get(1).getBTitle()
            : "Empty",
            getClass().getResource(BPath.IMG_PATH.get() + "user.png")
        ));
        
        this.addBCard(new BInfoCard(
            books.get(2).getBTitle() != null
            ? books.get(2).getBTitle()
            : "Empty",
            getClass().getResource(BPath.IMG_PATH.get() + "user.png")
        ));

        BAppEventObservable.addBookListener(bookListener);
        BAppEventObservable.addLoanListener(loanListener);
        
    }

    private void initStyle(){

        /*
        * CENTER PARENT PAN
        */  
        this.center.setLayout(new GridLayout(
            2,
            3
        ));
        this.center.setBorder(new EmptyBorder(
            20,
            0,
            0,
            0
        ));

        FlowLayout bottomFlow = new FlowLayout();
        bottomFlow.setHgap(20);
        bottomFlow.setAlignment(FlowLayout.LEFT);

        this.bottomPan =  new BComponentPan();
        this.bottomPan.setLayout(bottomFlow);

        /*
        * BOTTOM PARENT PAN
        */    
        this.bottom.setLayout(new BoxLayout(
            this.bottom,
            BoxLayout.Y_AXIS
        ));
        this.bottom.setBorder(new EmptyBorder(
            0,
            0,
            20,
            0
        ));

        BComponentPan bottomTitlePan = new BComponentPan();
        bottomTitlePan.setLayout(bottomFlow);
        bottomTitlePan.add(this.bottomTitle);

        this.bottom.add(bottomTitlePan);
        this.bottom.add(this.bottomPan);

    }

    private void addCard(BInfoCard card){
    
        card.setPreferredSize(new Dimension(
            320,
            90
        ));
        this.cards.add(card);

        JPanel wrap = new JPanel(
            new FlowLayout(FlowLayout.LEFT)
        );

        wrap.add(card);
        this.center.add(wrap);
    }

    private void addBCard(BInfoCard card){

        card.setPreferredSize(new Dimension(
            300,
            280
        ));
        card.setMaximumSize(card.getPreferredSize());
        this.cards.add(card);
        this.bottomPan.add(card);
    }

    public Dashboard get(){
        return this;
    }

    public void refreshBook(){
        totalBooks = new BookController().getCountBooks();
        totalBookCard.setSubTitle(String.valueOf(totalBooks));

        totalExemplaire =  new BookController().getCountBookExemplaire();
        totalExemplaireCard.setSubTitle(String.valueOf(totalExemplaire));

        availlableExemplaire = new BookController().getCountBookAvaillableExemplaire();
        availlableExemplaireCard.setSubTitle(String.valueOf(availlableExemplaire));
    }

    public void refreshLoan(){
        currentAdherent = new LoanController().getCountCurrentAdherent();
        currentAdherentCard.setSubTitle(String.valueOf(currentAdherent));

        currentLoan = new LoanController().getCountCurrentLoans();
        currentLoanCard.setSubTitle(String.valueOf(currentLoan));

        availlableExemplaire = new BookController().getCountBookAvaillableExemplaire();
        availlableExemplaireCard.setSubTitle(String.valueOf(availlableExemplaire));
    }

    /*******************************************
    *          LISTENER PART METHODE           *
    ********************************************/

    class BBookListener implements BookListener{

        @Override
        public void addedBook(com.biblio.models.Book book) {
            refreshBook();
        }

        @Override
        public void editedBook(com.biblio.models.Book book) {
            refreshBook();
        }

        @Override
        public void deletedBook(com.biblio.models.Book book) {
            refreshBook();
        }

        @Override
        public void update(Object object) {}

    }

    class BLoanListener implements LoanListener {

        @Override
        public void addedLoan(com.biblio.models.Loan loan) {
            refreshLoan();
        }

        @Override
        public void editedLoan(com.biblio.models.Loan loan) {
            refreshLoan();
        }

        @Override
        public void deletedLoan(com.biblio.models.Loan loan) {
            refreshLoan();
        }

        @Override
        public void update(Object object) {}
    
    }

}
