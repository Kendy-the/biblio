package com.biblio.views.book;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.biblio.config.BColor;
import com.biblio.controllers.BookController;
import com.biblio.errors.BErrorMgr;
import com.biblio.models.Book;
import com.biblio.observer.BAppEventObservable;
import com.biblio.views.components.BButton;

public class NewBookDialog extends JDialog{

    @SuppressWarnings("unused")
    private boolean sendData;
    
    @SuppressWarnings("unused")
    private JLabel isbnLabel, authorLabel, titleLabel, yearLabel, quantityLabel, icon;
    private JTextField isbnField, authorField, titleField, yearField, quantityField;

    private com.biblio.models.Book book;
    
    public NewBookDialog(JFrame parent, String title, boolean modal){
        
        super(parent, title, modal);

        this.setSize(550, 320);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);

        this.init();
    }

     
    public NewBookDialog(JFrame parent, String title, boolean modal, com.biblio.models.Book book){
        
        super(parent, title, modal);

        this.setSize(550, 320);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE);

        this.init();
        
        this.book = book;
        this.isbnField.setText(book.getIsbn());
        this.authorField.setText(book.getAuthor());
        this.yearField.setText(book.getYear_pub());
        this.titleField.setText(book.getBTitle());
        this.quantityField.setText(
            String.valueOf(book.getQuantity())
        );
        
    }

    public void showDialog(){
        this.sendData = false;
        this.setVisible(true);
    }

    public void init(){

        JPanel panIsbn = new JPanel();
        panIsbn.setBackground(BColor.WHITE.get());
        panIsbn.setPreferredSize(new Dimension(220, 60));

        this.isbnField = new JTextField();
        this.isbnField.setPreferredSize(new Dimension(100, 25));
        panIsbn.setBorder(BorderFactory.createTitledBorder("ISBN du livre"));

        this.isbnLabel = new JLabel("Saisir le numero :");
        panIsbn.add(this.isbnLabel);
        panIsbn.add(this.isbnField);

        // author
        JPanel panAuthor = new JPanel();
        panAuthor.setBackground(BColor.WHITE.get());
        panAuthor.setPreferredSize(new Dimension(220, 60));

        this.authorField = new JTextField();
        this.authorField.setPreferredSize(new Dimension(100, 25));
        panAuthor.setBorder(BorderFactory.createTitledBorder("Auteur du livre"));

        authorLabel = new JLabel("Saisir l'auteur :");
        panAuthor.add(authorLabel);
        panAuthor.add(authorField);

        // title
        JPanel panTitle = new JPanel();
        panTitle.setBackground(BColor.WHITE.get());
        panTitle.setPreferredSize(new Dimension(220, 60));

        this.titleField = new JTextField();
        this.titleField.setPreferredSize(new Dimension(100, 25));
        panTitle.setBorder(BorderFactory.createTitledBorder("Titre du livre"));

        titleLabel = new JLabel("Saisir le titre :");
        panTitle.add(titleLabel);
        panTitle.add(titleField);

        // year
        JPanel panYear = new JPanel();
        panYear.setBackground(BColor.WHITE.get());
        panYear.setPreferredSize(new Dimension(220, 60));

        this.yearField = new JTextField();
        this.yearField.setPreferredSize(new Dimension(100, 25));
        panYear.setBorder(BorderFactory.createTitledBorder("Annee du livre"));

        yearLabel = new JLabel("Saisir l'annee :");
        panYear.add(yearLabel);
        panYear.add(yearField);

        // quantity
        JPanel panQuantity = new JPanel();
        panQuantity.setBackground(BColor.WHITE.get());
        panQuantity.setPreferredSize(new Dimension(220, 90));

        this.quantityField = new JTextField();
        this.quantityField.setPreferredSize(new Dimension(100, 25));
        panQuantity.setBorder(BorderFactory.createTitledBorder("Quantite / disponible"));

        quantityLabel = new JLabel("Saisir la quantite :");
        panQuantity.add(quantityLabel);
        panQuantity.add(quantityField);
        

        JPanel control = new JPanel();
        BButton saveBouton = new BButton("save");

        saveBouton.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent arg0) {
                if (book != null) {
                    edit();
                }else{
                    save();
                }
                setVisible(false);
            }
            
        });
        
        BButton cancelBouton = new BButton("Annuler");
        cancelBouton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent arg0) {
                setVisible(false);
            }
        });

        JPanel content = new JPanel();
        content.setBackground(BColor.WHITE.get());
        content.add(panIsbn);
        content.add(panAuthor);
        content.add(panTitle);
        content.add(panYear);
        content.add(panQuantity);

        control.add(saveBouton);
        control.add(cancelBouton);

        this.getContentPane().add(content, BorderLayout.CENTER);
        this.getContentPane().add(control, BorderLayout.SOUTH);

    }

    public void save(){

        String msg;
        int icon;

        if(new BookController().save(this.getData())){
            msg = "Enregistrer avec success";
            icon = JOptionPane.INFORMATION_MESSAGE;
            
            // For listeneres
            BAppEventObservable.notifyAddedBook(new Book(this.getData()));
            clearField();
        }
        else
        {
            if (BErrorMgr.hasErrors()) {
                msg = BErrorMgr.getErrorsMessage();
            } else {
                msg = "Erreur, champ invalide";
            }
            icon = JOptionPane.ERROR_MESSAGE;
        }

        JOptionPane.showMessageDialog(
            null, 
            msg,
            "Enregistrer livre",
            icon
        );

    }

    public void edit(){

        String msg;
        int icon;

        if(new BookController().edit(this.getData(), this.book)){
            msg = "Modifier avec success";
            icon = JOptionPane.INFORMATION_MESSAGE;

            // For listeners
            BAppEventObservable.notifyEditedBook(new Book(this.getData()));
            clearField();
        }
        else
        {
            if (BErrorMgr.hasErrors()) {
                msg = BErrorMgr.getErrorsMessage();
            } else {
                msg = "Erreur, champ invalide";
            }
            icon = JOptionPane.ERROR_MESSAGE;
        }

        JOptionPane.showMessageDialog(
            null, 
            msg,
            "Modifier livre",
            icon
        );
    }

    public void clearField(){
        this.isbnField.setText(" ");
        this.titleField.setText(" ");
        this.yearField.setText(" ");
        this.authorField.setText(" ");
        this.quantityField.setText(" ");
    }

    private Map<String, String> getData(){
        Map<String, String> data = new HashMap<>();

        data.put("isbn", this.isbnField.getText());
        data.put("title", this.titleField.getText());
        data.put("year", this.yearField.getText());
        data.put("author", this.authorField.getText());
        data.put("quantity", this.quantityField.getText());

        return data;
    }

}
