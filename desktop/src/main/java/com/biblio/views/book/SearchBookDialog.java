package com.biblio.views.book;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.biblio.config.BColor;
import com.biblio.controllers.BookController;
import com.biblio.models.Book;
import com.biblio.views.components.BButton;
import com.biblio.views.components.BSroll.BScrollPan;
import com.biblio.views.components.BTable.BTable;

public class SearchBookDialog extends JDialog {

    private JTextField authorField, titleField, isbnField;

    private BTable table;

    public SearchBookDialog(JFrame parent,String title,boolean modal) {

        super(parent, title, modal);

        setSize(900, 500);
        setLocationRelativeTo(null);
        setResizable(false);

        init();
    }

    public void showDialog(){
        this.setVisible(true);
    }

    private void init() {

        JPanel searchPanel = new JPanel();

        searchPanel.setBackground(
                BColor.WHITE.get()
        );

        // Auteur
        JPanel panAuthor = new JPanel();

        panAuthor.setBackground(
                BColor.WHITE.get()
        );

        panAuthor.setBorder(
                BorderFactory.createTitledBorder(
                        "Auteur"
                )
        );

        authorField = new JTextField();

        authorField.setPreferredSize(
                new Dimension(180, 25)
        );

        panAuthor.add(authorField);

        // Isbn
        JPanel panIsbn = new JPanel();

        panIsbn.setBackground(
                BColor.WHITE.get()
        );

        panIsbn.setBorder(
                BorderFactory.createTitledBorder(
                        "Isbn"
                )
        );

        isbnField = new JTextField();

        isbnField.setPreferredSize(
                new Dimension(180, 25)
        );

        panIsbn.add(isbnField);

        // Titre

        JPanel panTitle = new JPanel();

        panTitle.setBackground(
                BColor.WHITE.get()
        );

        panTitle.setBorder(
                BorderFactory.createTitledBorder(
                        "Titre"
                )
        );

        titleField = new JTextField();

        titleField.setPreferredSize(
                new Dimension(180, 25)
        );

        panTitle.add(titleField);

        searchPanel.add(panIsbn);
        searchPanel.add(panAuthor);
        searchPanel.add(panTitle);

        // Boutons

        BButton btnSearch =
                new BButton("Rechercher");

        btnSearch.addActionListener(e -> {
            searchBooks();
        });

        BButton btnClose = new BButton("Fermer");

        btnClose.addActionListener(e -> {
            dispose();
        });

        searchPanel.add(btnSearch);
        searchPanel.add(btnClose);

        // Tableau

        table = new BTable(
                "ISBN",
                "Titre",
                "Année",
                "Auteur",
                "Quantite / disponible"
        );

        BScrollPan scroll = new BScrollPan(table);

        getContentPane().add(
                searchPanel,
                BorderLayout.NORTH
        );

        getContentPane().add(
                scroll,
                BorderLayout.CENTER
        );
    }

    private void searchBooks() {

        String author = authorField.getText().trim();

        String title = titleField.getText().trim();

        String isbn = isbnField.getText().trim();

        table.clearRows();

        List<Book> books =
                new BookController()
                .search(isbn, author, title);

        for (Book book : books) {

            table.addRow(book);
        }
    }
}
