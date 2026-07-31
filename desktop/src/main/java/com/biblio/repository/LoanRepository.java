package com.biblio.repository;

import java.util.ArrayList;

import com.biblio.controllers.BookController;
import com.biblio.models.Loan;

public class LoanRepository extends Repository{

    private Loan loan;

    public LoanRepository(Loan loan){
        super();
        this.loan = loan;
    }

    public boolean delete(){

        String query = "DELETE FROM loan WHERE id = ?";

        try{
            this.pst = db.prepareStatement(query);

            this.pst.setLong(1, this.loan.getId());

            if(this.pst.executeUpdate() == 0) 
                return false;

        }catch(Exception e){
            System.err.print("Error, Loan Delete : " + e.getMessage());
            return false;
        }

        new BookController().increaseQuantity(
           this.loan.getBookId(), 
            1
        );

        return true;
    }

    public Loan getById(){

        String query = "SELECT * FROM loan WHERE id = ?";

        try {
            pst = db.prepareStatement(query);
            pst.setInt(1, this.loan.getId());
            rs = pst.executeQuery();

            while (rs.next()) {

                return new Loan(
                    rs.getInt("id"),
                    rs.getString("startDate"),
                    rs.getString("endDate"),
                    rs.getString("book_id"),
                    rs.getString("adherent_id"),
                    rs.getInt("status")
                );
            }

        } catch (Exception e) {
            System.err.println("error : " + e.getMessage());
        }

        return null;
    }
    
    public ArrayList<Loan> index(){

        ArrayList<Loan> loans = new ArrayList<Loan>();
        
        String query = "SELECT * FROM loan";

        try {

            pst = db.prepareStatement(query);
            rs = pst.executeQuery();

            while (rs.next()) {

                loans.add(new Loan(
                    rs.getInt("id"),
                    rs.getString("startDate"),
                    rs.getString("endDate"),
                    rs.getString("book_id"),
                    rs.getString("adherent_id"),
                    rs.getInt("status")
                ));
            }

        } catch (Exception e) {
            System.err.println("error : " + e.getMessage());
        }

        return loans;
    }

    public boolean edit(boolean isStatusEdit){

        String query = "UPDATE loan SET startDate = ?, endDate = ?, book_id = ?, adherent_id = ?, status = ? WHERE id = ?";

        try{
            this.pst = db.prepareStatement(query);

            this.pst.setString(1, this.loan.getStartDate());
            this.pst.setString(2, this.loan.getEndDate());
            this.pst.setString(3, this.loan.getBookId());
            this.pst.setString(4, this.loan.getAdherentId());
            this.pst.setInt(5, this.loan.getStatus());
            this.pst.setInt(6, this.loan.getId());

            if(this.pst.executeUpdate() == 0) 
                return false;

        }catch(Exception e){
            System.err.print("Error, Loan update : " + e.getMessage());
            return false;
        }

        if(isStatusEdit && this.loan.getStatus() == 1){
            new BookController().increaseQuantity(
                this.loan.getBookId(), 
                1
            );
        }

        return true;
    }

    public int save(){

        String query = "INSERT INTO loan(startDate, endDate , book_id, adherent_id, status, user_id) VALUES(?,?,?,?,?,?)";

        try{
            this.pst = db.prepareStatement(query);

            this.pst.setString(1, this.loan.getStartDate());
            this.pst.setString(2, this.loan.getEndDate());
            this.pst.setString(3, this.loan.getBookId());
            this.pst.setString(4, this.loan.getAdherentId());
            this.pst.setInt(5, this.loan.getStatus());
            this.pst.setInt(6, this.loan.getUserId());

            if(this.pst.executeUpdate() == 0) 
                return 0;

        }catch(Exception e){
            System.err.print("Error, loan insertion : " + e.getMessage());
            return 0;
        }

        new BookController().decreaseQuantity(
           this.loan.getBookId(), 
            1
        );

        Loan lastest = getLastRecord();
        return (lastest != null ? lastest.getId() : 0);
    }

    public Loan getLastRecord(){

        String query = "SELECT * FROM loan ORDER BY id DESC LIMIT 1";

        try {

            pst = db.prepareStatement(query);
            rs = pst.executeQuery();

            while (rs.next()) {

                return new Loan(
                    rs.getInt("id"),
                    rs.getString("startDate"),
                    rs.getString("endDate"),
                    rs.getString("book_id"),
                    rs.getString("adherent_id"),
                    rs.getInt("status")
                );
            }

        } catch (Exception e) {
            System.err.println("error : " + e.getMessage());
        }

        return null;
    }
}
