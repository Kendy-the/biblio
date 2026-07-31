package com.biblio.models;

import java.util.ArrayList;
import java.util.Map;
import java.util.Vector;

import com.biblio.config.BApp;
import com.biblio.controllers.AdherentController;
import com.biblio.controllers.BookController;
import com.biblio.repository.LoanRepository;

public class Loan extends Model{

    private int id, status = 0, userId;
    private String startDate, endDate, bookId, adherentId;
    
    public Loan(){}

    public Loan(int id, String startDate, String endDate, String bookId, String adherentId){
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.bookId = bookId;
        this.adherentId = adherentId;
        this.userId = User.getConnected().getId();
    }

    public Loan(int id, String startDate, String endDate, String bookId, String adherentId, int status){
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.bookId = bookId;
        this.adherentId = adherentId;
        this.status = status;
        this.userId = User.getConnected().getId();
    }

    public Loan(int id, String startDate, String endDate, String bookId, String adherentId, int status, int userId){
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.bookId = bookId;
        this.adherentId = adherentId;
        this.status = status;
        this.userId = userId;
    }

    public Loan(Map<String, String> data){

        this.startDate = data.get("startDate");
        this.endDate = data.get("endDate");
        this.bookId = data.get("bookId");
        this.adherentId = data.get("adherentId");
        this.setStatusString(data.get("status"));
        this.userId = User.getConnected().getId();
    }

    public Vector<?> toVector(){

        Vector<String> data = new Vector<>();

        Adherent a = this.getAdherent();

        data.add(String.valueOf(this.getId()));
        data.add(this.getBook().getBTitle());
        data.add(a.getFirstName() + " " + a.getLastName());
        data.add(this.getStartDate());
        data.add(this.getEndDate());

        data.add(this.getStatusString());

        return data;
    }

    public Adherent getAdherent(){
        return new AdherentController().getById(
            Integer.parseInt(this.adherentId)
        );
    }

    public Book getBook(){
        return new BookController().getById(
            Integer.parseInt(this.bookId)
        );
    }

    public ArrayList<String> toArray(){

        ArrayList<String> data = new ArrayList<>();

        Adherent a = this.getAdherent();

        if (this.getId() > 0) {
            data.add(String.valueOf(this.getId()));
        }
        
        data.add(this.getBook().getBTitle());
        data.add(a.getFirstName() + " " + a.getLastName());
        data.add(this.getStartDate());
        data.add(this.getEndDate());
        data.add(this.getStatusString());

        return data;
    }
    
    public Loan getById(int id){
        this.id = id;
        return new LoanRepository(this).getById();
    }
    
    public ArrayList<Loan> index(){
        return new LoanRepository(this).index();
    }

    public int save(){
        return new LoanRepository(this).save();
    }

    public boolean edit(int id, boolean isStatusEdit){
        this.id = id;
        return new LoanRepository(this).edit(isStatusEdit);
    }

    public String getStatusString(){
        return this.getStatus() > 0 ? 
        BApp.LOAN_RETURNED_STATUS.get() : 
        BApp.LOAN_ACTIF_STATUS.get();
    }

    public void setStatusString(String value){
        setStatus(
            value == BApp.LOAN_ACTIF_STATUS.get() ? 0 : 1
        );
    }

    public int getId(){ return this.id; }
    public void setId(int id){this.id = id;}

    public int getStatus() {return status;}
    public void setStatus(int status) {this.status = status;}

    public int getUserId() {return userId;}
    public void setUserId(int userId) {this.userId = userId;}

    public String getEndDate(){return this.endDate;}

    public String getAdherentId() { return adherentId; }
    public void setAdherentId(String adherentId) { this.adherentId = adherentId; }

    public String getBookId() {return bookId;}
    public void setBookId(String bookId) {this.bookId = bookId;}

    public String getStartDate() {return startDate;}
    public void setStartDate(String startDate) {this.startDate = startDate;}

}
