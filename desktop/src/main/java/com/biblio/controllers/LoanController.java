package com.biblio.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.biblio.config.BApp;
import com.biblio.errors.BErrorMgr;
import com.biblio.errors.BErrors;
import com.biblio.models.Loan;
import com.biblio.repository.LoanRepository;
import com.biblio.utils.BUtils;

public class LoanController extends Controller{
    
    public LoanController(){}

    public int save(Map<String, String> data){
        
        if(!isCorrectData(data)) return 0;

        return new Loan(data).save();
    }

    public boolean edit(Map<String, String> data, Loan loan){
        
        if(!isCorrectData(data)) return false;

        if (loan.getStatusString().equals(data.get("status"))) {
            return new Loan(data).edit(loan.getId(), false);
        }

        return new Loan(data).edit(loan.getId(), true);

    }

    public static boolean delete(Loan loan){

        if(
            !isLoanExists(loan.getId())
        ) return false;

        return new LoanRepository(loan).delete();
    }

    public static boolean isCorrectData(Map<String, String> data){
        if(
            data.isEmpty() ||
            isEmptyField(data)
        ) {
            BErrorMgr.addError("data", BErrors.ERROR_EMPTY_FIELD.get());
            return false;
        }

        if (!isDateValid("startDate", data.get("startDate")) ||
            !isDateValid("endDate", data.get("endDate"))
        ) {
            return false;
        }

        if (
            BUtils.toDate(data.get("startDate"))
            .isBefore(LocalDate.now())
        ) {
            BErrorMgr.addError("date pret", "La {Date de pret ("
                + data.get("startDate")+
                ")} doit etre \n"+
                "superieur ou egal (>) a la {Date du jour ("
                + LocalDate.now() +")}"
            );

            return false;
        }

        if (
            !BUtils.toDate(data.get("endDate"))
            .isAfter(
                BUtils.toDate(data.get("startDate"))
            ))
        {
            BErrorMgr.addError("date retour", "La {Date de retour ("
                + data.get("endDate")+
                ")} doit etre \n"+
                "superieur (>) a la {Date de pret ("
                + data.get("startDate")+
                ")}"
            );

            return false;
        }

        if (
            !new BookController().isAvaillable(data.get("bookId"))
        ) {
            BErrorMgr.addError("livre", 
                "Livre non disponible\n"
                + "{Quantite stock = 1}"
            );
                return false;
        }

        return true;
    }

    public List<Loan> getCurrentLoans(){
        
        List<Loan> current = new ArrayList<>();

        for (Loan loan : index()) {
            if (loan.getStatus() != 1) {
                current.add(loan);
            }
        }

        return current;
    }

    public int getCountCurrentLoans(){
        return getCurrentLoans().size();
    }

    public Long getCountCurrentAdherent(){
        return getCurrentLoans().stream()
            .map(
            l -> l.getAdherent()
                    .getId()
            )
            .distinct()
            .count();
    }

    public List<Loan> getCurrentLoans(LocalDate starDate, LocalDate endDate){

        List<Loan> current = new ArrayList<>();

        if (starDate != null || endDate != null) {

            for (Loan loan : search(starDate, endDate)) {
                if (loan.getStatus() != 1) {
                    current.add(loan);
                }
            }

        }else{
            return getCurrentLoans();
        }

        return current;
    }

    public List<Loan> getLoans(String status){
        
        List<Loan> current = new ArrayList<>();

        for (Loan loan : index()) {
            if (
                loan.getStatus() 
                == (
                    BApp.LOAN_ACTIF_STATUS.get()
                    .equals(status) ? 0 : 1
                )
            ) current.add(loan);
        }

        return current;
    }

    public List<Loan> getLoans(LocalDate starDate, LocalDate endDate, String status){

        List<Loan> current = new ArrayList<>();

        if (starDate != null || endDate != null ) {

            for (Loan loan : search(starDate, endDate)) {

                if (
                    loan.getStatus() 
                    == (
                        BApp.LOAN_ACTIF_STATUS.get()
                        .equals(status) ? 0 : 1
                    )
                ) current.add(loan);
            }

        }else{
            return getLoans(status);
        }

        return current;
    }

    public int getCountLateBook(){
        return getLateBook().size();
    }

    public List<Loan> getLateBook(){

        List<Loan> current = new ArrayList<>();

        for (Loan loan : search(null, LocalDate.now())) {
            if (loan.getStatus() != 1) {
                current.add(loan);
            }
        }

        return current;
    }

    public List<Loan> search(LocalDate startDate, LocalDate endDate) {

        List<Loan> result = new ArrayList<>();

        for (Loan a : index()) {

            LocalDate start = LocalDate.parse(a.getStartDate());
            LocalDate end = LocalDate.parse(a.getEndDate());

            boolean matchFirst = true;
            boolean matchLast = true;

            if (startDate != null) {
                
                matchFirst =
                startDate.equals(start) ||
                startDate.isBefore(start);
                
            }

            if (endDate != null) {
                matchLast =
                endDate.equals(end) ||
                endDate.isAfter(end);

            }

            if (
                (startDate != null || endDate != null) 
                && (matchFirst && matchLast)) 
            {
                result.add(a);
            }

        }

        return result;
    }

    public ArrayList<Loan> index(){

        return new Loan().index();
        
    }

    public boolean saveTable(){
        return true;
    }

    private static boolean isLoanExists(int id){
        if (new Loan().getById(id) != null) {
            return true;
        }
        return false;
    }

    private static boolean isEmptyField(Map<String, String> data){
        
        if (
            data.get("startDate").isBlank() ||
            data.get("endDate").isBlank() ||
            data.get("bookId").isBlank() 
        ) {
           return true;
        }

        return false;
    }
}
