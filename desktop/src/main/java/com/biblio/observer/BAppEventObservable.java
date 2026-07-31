package com.biblio.observer;

import java.util.ArrayList;

import com.biblio.models.Adherent;
import com.biblio.models.Book;
import com.biblio.models.Loan;
import com.biblio.models.User;

public class BAppEventObservable{

    private static ArrayList<LoanListener> loanListeners = new ArrayList<>();
    private static ArrayList<UserListener> userListeners = new ArrayList<>();
    private static ArrayList<BookListener> bookListeners = new ArrayList<>();
    private static ArrayList<AdherentListener> adherentListeners = new ArrayList<>();

    /*************************************************
    *   BOOK LISTENER
    ***************************************************/
    public static void addBookListener(BookListener listener){
        bookListeners.add(listener);
    }

    public static void notifyAddedBook(Book book){
        bookListeners.forEach((l)->{
            l.addedBook(book);
        });
    }

    public static void notifyEditedBook(Book book){
        bookListeners.forEach(
            l -> l.editedBook(book)
        );
    }

    public static void notifyDeletedBook(Book book){
        bookListeners.forEach(
            l -> l.deletedBook(book)
        );
    }

    /*************************************************
    *   ADHERENT LISTENER
    ***************************************************/
    public static void addAdherentListener(AdherentListener listener){
        adherentListeners.add(listener);
    }

    public static void notifyAddedAdherent(Adherent adherent){
        adherentListeners.forEach((l)->{
            l.addedAdherent(adherent);
        });
    }

    public static void notifyEditedAdherent(Adherent adherent){
        adherentListeners.forEach(
            l -> l.editedAdherent(adherent)
        );
    }

    public static void notifyDeletedAdherent(Adherent adherent){
        adherentListeners.forEach(
            l -> l.deletedAdherent(adherent)
        );
    }

    /*************************************************
    *   LOAN LISTENER
    ***************************************************/
    public static void addLoanListener(LoanListener listener){
        loanListeners.add(listener);
    }

    public static void notifyAddedLoan(Loan loan){
        loanListeners.forEach((l)->{
            l.addedLoan(loan);
        });
    }

    public static void notifyEditedLoan(Loan loan){
        loanListeners.forEach(
            l -> l.editedLoan(loan)
        );
    }

    public static void notifyDeletedLoan(Loan loan){
        loanListeners.forEach(
            l -> l.deletedLoan(loan)
        );
    }

    /*************************************************
    *   USER LISTENER
    ***************************************************/
    public static void addUserListener(UserListener listener){
        userListeners.add(listener);
    }

    public static void notifyAddedUser(User user){
        userListeners.forEach((l)->{
            l.addedUser(user);
        });
    }

    public static void notifyEditedUser(User user){
        userListeners.forEach(
            l -> l.editedUser(user)
        );
    }

    public static void notifyDeletedUser(User user){
        userListeners.forEach(
            l -> l.deletedUser(user)
        );
    }
    
}
