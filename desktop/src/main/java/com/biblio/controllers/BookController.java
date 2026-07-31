package com.biblio.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.biblio.errors.BErrors;
import com.biblio.errors.BErrorMgr;
import com.biblio.models.Book;
import com.biblio.repository.BookRepository;

public class BookController extends Controller{
    
    public BookController(){}

    public boolean save(Map<String, String> data){

        if(!isCorrectData(data))
            return false;

        if (isIsbnExists(data.get("isbn")))
            return false;

        return new Book(data).save();
    }

    public boolean edit(Map<String, String> data, Book book){

        if(!isCorrectData(data))
            return false;
        
        if(
            isIsbnInValid(book.getIsbn(), data.get("isbn"))
        ) return false;

        return new Book(data).edit(book.getId());
    }

    public static boolean delete(Book book){

        if(
            !isIsbnExists(book.getIsbn())
        ) {
            BErrorMgr.addError("isbn", BErrors.ERROR_RESOURCE_NOT_EXISTS.get());
            return false;
        }

        if(isLoaned(book.getId())){
            BErrorMgr.addError("book", BErrors.ERROR_RESSOURCE_IN_RELATIONSHIP.get());
            return false;
        }

        if (book.getQuantity() > 1) {
            BErrorMgr.addError("quantity", BErrors.ERROR_RESSOURCE_HIGH.get());
            return false;
        }

        return new BookRepository(book).delete();
    }

    public static boolean isCorrectData(Map<String, String> data){

        if (
            data.isEmpty() ||
            isEmptyField(data)
        ) {
            BErrorMgr.addError("data", BErrors.ERROR_EMPTY_FIELD.get());
            return false;
        }

        if(!isInteger("year", data.get("year"))){
            return false;  
        }

        if(!isInteger("quantity", data.get("quantity"))){
            return false;  
        }

        if (!isGreaterThanZero("quantity", data.get("quantity"))) {
            return false;
        }

        return true;
    }

    public List<Book> search(String isbn, String author, String title) {

        List<Book> result = new ArrayList<>();

        for (Book book : new Book().index()) {

            boolean matchIsbn = isbn.isBlank()
                    || book.getIsbn()
                        .toLowerCase()
                        .contains(
                            isbn.toLowerCase()
                        );

            boolean matchAuthor = author.isBlank()
                    || book.getAuthor()
                        .toLowerCase()
                        .contains(
                            author.toLowerCase()
                        );

            boolean matchTitle =
                    title.isBlank()
                    || book.getBTitle()
                        .toLowerCase()
                        .contains(
                            title.toLowerCase()
                        );

            if(matchIsbn && matchAuthor && matchTitle){
                result.add(book);
            }
        }

        return result;
    }

    public ArrayList<Book> getRecent(){

        ArrayList<com.biblio.models.Book> books =  new ArrayList<>();
        ArrayList<com.biblio.models.Book> modBooks = index();

        if (modBooks.isEmpty()) {

            books.add(new Book());
            books.add(new Book());
            books.add(new Book());

        }else if (modBooks.size() == 1) {

            books.add(modBooks.get(0));
            books.add(new Book());
            books.add(new Book());

        }else if (modBooks.size() == 2) {
            books.add(modBooks.get(0));
            books.add(modBooks.get(1));
            books.add(new Book());
        }else if (modBooks.size() > 2) {
            books.add(modBooks.get(0));
            books.add(modBooks.get(1));
            books.add(modBooks.get(2));
        }
        
        return books;
    }

    public int getCountBooks(){
        return index().size();
    }

    public int getCountBookExemplaire(){

        int somme = getLoanedBooks(false).size();

        for (Book book : index()) {
            somme += book.getQuantity();
        }

        return somme;
    }

    public int getCountBookAvaillableExemplaire(){

        int somme = 0;

        for (Book book : index()) {
            somme += book.getQuantity();
        }

        return somme;

    }

    public List<Book> getLoanedBooks(boolean distinct){
        return new Book().getLoanedBooks(distinct);
    }

    public static boolean isLoaned(int bookId){
        return new BookRepository(
            new Book(bookId, null, null, null, null)
        ).isLoaned();
    }

    public ArrayList<Book> index(){
        return new Book().index();
    }

    public boolean increaseQuantity(String bookId, int value){
        Book b = new Book();
        b.setId(
            Integer.parseInt(bookId)
        );
        return b.increaseQuantity(value);
    }

    public boolean decreaseQuantity(String bookId, int value){
        Book b = new Book();
        b.setId(
            Integer.parseInt(bookId)
        );
        return b.decreaseQuantity(value);
    }

    public boolean isAvaillable(String value){
        Book b = new Book();
        b.setId(
            Integer.parseInt(value)
        );
        return b.isAvaillable();
    }

    public boolean saveTable(){
        return true;
    }

    private static boolean isIsbnInValid(String old, String newisbn){

        if (old.equals(newisbn)) {
            return false;
        }

        if (isIsbnExists(newisbn)) {
            BErrorMgr.addError("isbn", BErrors.ERROR_RESOURCE_EXISTS.get());
            return true;
        }

        return false;
    }

    public static Book getByIsbn(String isbn){
        return Book.getByIsbn(isbn);
    }

    private static boolean isIsbnExists(String isbn){
        if (Book.getByIsbn(isbn) != null) {
            return true;
        }
        return false;
    }

    public Book getById(int id){
        return new BookRepository(new Book(id, null, null, null, null)).getById();
    }

    private static boolean isEmptyField(Map<String, String> data){
        
        if (
            data.get("isbn").isBlank() ||
            data.get("title").isBlank() ||
            data.get("author").isBlank() ||
            data.get("year").isBlank() ||
            data.get("quantity").isBlank()
        ) {
            return true;
        }

        return false;
    }
    
}
