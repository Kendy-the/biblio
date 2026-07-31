package com.biblio.observer;

import com.biblio.models.Loan;

public interface LoanListener {
    public void update(Object object);
    public void addedLoan(Loan loan);
    public void editedLoan(Loan loan);
    public void deletedLoan(Loan loan);
}
