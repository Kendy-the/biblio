package com.biblio.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.biblio.repository.BookRepository;

public class Book extends Model{

    private int id, userId, quantity = 0;
    private String isbn, title, year_pub, author;
    
    public Book(){}

    public Book(int id, String isbn, String title, String year, String author){
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.year_pub = year;
        this.author = author;
        this.userId = User.getConnected().getId();
    }

    public Book(int id, String isbn, String title, String year, String author, int quantity){
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.year_pub = year;
        this.author = author;
        this.quantity = quantity;
        this.userId = User.getConnected().getId();
    }

    public Book(Map<String, String> data){

        this.isbn = data.get("isbn");
        this.title = data.get("title");
        this.year_pub = data.get("year");
        this.author = data.get("author");
        this.quantity = Integer.parseInt(
            data.get("quantity")
        );
        this.userId = User.getConnected().getId();
    }

    @Override
    public Vector<?> toVector(){

        Vector<String> data = new Vector<>();

        data.add(this.getIsbn());
        data.add(this.getBTitle());
        data.add(this.getYear_pub());
        data.add(this.getAuthor());
        data.add(String.valueOf(
            this.getQuantity()
        ));

        return data;
    }

    public ArrayList<String> toArray(){

        ArrayList<String> data = new ArrayList<>();

        data.add(this.getIsbn());
        data.add(this.getBTitle());
        data.add(this.getYear_pub());
        data.add(this.getAuthor());
        data.add(String.valueOf(
            this.getQuantity()
        ));

        return data;
    }
    
    public ArrayList<Book> index(){
        return new BookRepository(this).index();
    }

    public boolean isAvaillable(){
        return new BookRepository(this).isAvaillable();
    }

    public List<Book> getLoanedBooks(boolean distinct){
        return new BookRepository(this).getLoanedBooks(distinct);
    }

    public boolean decreaseQuantity(int value){
        return new BookRepository(this).decreaseQuantity(value);
    }

    public boolean increaseQuantity(int value){
        return new BookRepository(this).increaseQuantity(value);
    }


    public boolean save(){
        return new BookRepository(this).save();
    }

    public boolean edit(int id){
        this.id = id;
        return new BookRepository(this).edit();
    }

    public static Book getByIsbn(String isbn){
        return BookRepository.getByIsbn(isbn);
    }

    public int getId(){ return this.id; }
    public void setId(int id){ this.id = id;}

    public String getBTitle(){return this.title;}

    public int getQuantity() {return quantity;}
    public void setQuantity(int quantity) {this.quantity = quantity;}

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getYear_pub() {return year_pub;}
    public void setYear_pub(String year_pub) {this.year_pub = year_pub;}

    public String getIsbn() {return isbn;}
    public void setIsbn(String isbn) {this.isbn = isbn;}

    public int getUserId() {return userId;}
    public void setUserId(int userId) {this.userId = userId;}

}
