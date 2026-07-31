package com.biblio.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.biblio.database.DB;
import com.biblio.models.Book;

public class BookRepository extends Repository{

    private Book book;

    public BookRepository(Book book){
        super();
        this.book = book;
    }

    public boolean isLoaned(){
        String query = "SELECT * FROM loan WHERE book_id = ? AND (status IS NULL OR status = 0)";

        try {
            pst = db.prepareStatement(query);
            pst.setInt(1, book.getId());
            rs = pst.executeQuery();

            while (rs.next()) {
                return true;
            }

        } catch (Exception e) {
            System.err.println("error : " + e.getMessage());
        }

        return false;
    }

    public boolean delete(){

        String query = "DELETE FROM book WHERE id = ?";

        try{
            this.pst = db.prepareStatement(query);

            this.pst.setLong(1, this.book.getId());

            if(this.pst.executeUpdate() == 0) 
                return false;

        }catch(Exception e){
            System.err.print("Error, book Delete : " + e.getMessage());
            return false;
        }

        return true;
    }

    public boolean isAvaillable(){

        String query = "SELECT quantity FROM book WHERE id = ?";

        try {
            
            pst = db.prepareStatement(query);
            pst.setInt(1, book.getId());
            rs = pst.executeQuery();

            while (rs.next()) {
                return rs.getInt("quantity") > 1;
            }

        } catch (Exception e) {
            System.err.println("error : " + e.getMessage());
        }

        return false;
    }

    public ArrayList<Book> getLoanedBooks(boolean distinct){

        ArrayList<Book> books = new ArrayList<Book>();
        
        String query = "SELECT ";

        if (distinct) query = "DISTINCT ";

        query += "b.id, isbn, title, year_pub, quantity, author FROM book b INNER JOIN loan l ON b.id = l.book_id WHERE status IS NULL OR status = 0;";

        try {

            pst = db.prepareStatement(query);
            rs = pst.executeQuery();

            while (rs.next()) {

                books.add(new Book(
                    rs.getInt("id"),
                    rs.getString("isbn"),
                    rs.getString("title"),
                    rs.getString("year_pub"),
                    rs.getString("author"),
                    rs.getInt("quantity")
                ));
            }

        } catch (Exception e) {
            System.err.println("error : " + e.getMessage());
        }

        return books;
    }

    public Book getById(){

        String query = "SELECT * FROM book WHERE id = ?";

        try {
            pst = db.prepareStatement(query);
            pst.setInt(1, book.getId());
            rs = pst.executeQuery();

            while (rs.next()) {

                return new Book(
                    rs.getInt("id"),
                    rs.getString("isbn"),
                    rs.getString("title"),
                    rs.getString("year_pub"),
                    rs.getString("author"),
                    rs.getInt("quantity")
                );
            }

        } catch (Exception e) {
            System.err.println("error : " + e.getMessage());
        }

        return null;
    }

    public boolean decreaseQuantity(int value){

        String query = "UPDATE book SET quantity = quantity - ? WHERE id = ?";

        try{
            this.pst = db.prepareStatement(query);

            this.pst.setInt(1, value);
            this.pst.setInt(2, this.book.getId());

            if(this.pst.executeUpdate() == 0) 
                return false;

        }catch(Exception e){
            System.err.print("Error, Book update : " + e.getMessage());
            return false;
        }

        return true;
    }

    public boolean increaseQuantity(int value){

        String query = "UPDATE book SET quantity = quantity + ? WHERE id = ?";

        try{
            this.pst = db.prepareStatement(query);

            this.pst.setInt(1, value);
            this.pst.setInt(2, this.book.getId());

            if(this.pst.executeUpdate() == 0) 
                return false;

        }catch(Exception e){
            System.err.print("Error, Book update : " + e.getMessage());
            return false;
        }

        return true;
    }

    public static Book getByIsbn(String isbn){

        if(db == null){
            db = DB.get();
        }

        PreparedStatement pst;
        ResultSet rs;

        String query = "SELECT * FROM book WHERE isbn = ?";

        try {
            pst = db.prepareStatement(query);
            pst.setString(1, isbn);
            rs = pst.executeQuery();

            while (rs.next()) {

                return new Book(
                    rs.getInt("id"),
                    rs.getString("isbn"),
                    rs.getString("title"),
                    rs.getString("year_pub"),
                    rs.getString("author"),
                    rs.getInt("quantity")
                );
            }

        } catch (Exception e) {
            System.err.println("error : " + e.getMessage());
        }

        return null;
    }
    
    public ArrayList<Book> index(){

        ArrayList<Book> books = new ArrayList<Book>();
        
        String query = "SELECT * FROM book ORDER BY id DESC";

        try {

            pst = db.prepareStatement(query);
            rs = pst.executeQuery();

            while (rs.next()) {

                books.add(new Book(
                    rs.getInt("id"),
                    rs.getString("isbn"),
                    rs.getString("title"),
                    rs.getString("year_pub"),
                    rs.getString("author"),
                    rs.getInt("quantity")
                ));
            }

        } catch (Exception e) {
            System.err.println("error : " + e.getMessage());
        }

        return books;
    }

    public boolean edit(){

        String query = "UPDATE book SET isbn = ?, title = ?, year_pub = ?, author = ?, quantity = ? WHERE id = ?";

        try{
            this.pst = db.prepareStatement(query);

            this.pst.setString(1, this.book.getIsbn());
            this.pst.setString(2, this.book.getBTitle());
            this.pst.setString(3, this.book.getYear_pub());
            this.pst.setString(4, this.book.getAuthor());
            this.pst.setInt(5, this.book.getQuantity());
            this.pst.setInt(6, this.book.getId());

            if(this.pst.executeUpdate() == 0) 
                return false;

        }catch(Exception e){
            System.err.print("Error, Book update : " + e.getMessage());
            return false;
        }

        return true;
    }

    public boolean save(){

        String query = "INSERT INTO book(isbn, title, year_pub, author, quantity, user_id) VALUES(?,?,?,?,?,?)";

        try{
            this.pst = db.prepareStatement(query);

            this.pst.setString(1, this.book.getIsbn());
            this.pst.setString(2, this.book.getBTitle());
            this.pst.setString(3, this.book.getYear_pub());
            this.pst.setString(4, this.book.getAuthor());
            this.pst.setInt(5, this.book.getQuantity());
            this.pst.setInt(6, this.book.getUserId());

            if(this.pst.executeUpdate() == 0) 
                return false;

        }catch(Exception e){
            System.err.print("Error, Book insertion : " + e.getMessage());
            return false;
        }

        return true;
    }
}
