package com.biblio.views;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.border.EmptyBorder;

import com.biblio.config.BApp;
import com.biblio.observer.Observator;
import com.biblio.views.book.Book;
import com.biblio.views.adherent.Adherent;
import com.biblio.views.components.BComponentPan;
import com.biblio.views.layout.AppLayout;
import com.biblio.views.loan.Loan;
import com.biblio.views.report.Report;
import com.biblio.views.settings.Settings;

public class App extends AppLayout{

    private CardLayout cardLayout;
    private BComponentPan mainPan;

    public App(){

        /*
        * CONFIGURE
        */
        this.initMain();    
        
        /*
        *  VIEWS
        */
        this.mainPan.add(
            new Dashboard().get(), 
            BApp.SIDE_DASHBOARD.get()
        );

        this.mainPan.add(
            new Book(this).get(), 
            BApp.SIDE_BOOK.get()
        );

        this.mainPan.add(
            new Adherent(this).get(), 
            BApp.SIDE_ADHERENT.get()
        );

        this.mainPan.add(
            new Loan(this).get(), 
            BApp.SIDE_LOAN.get()
        );

        this.mainPan.add(
            new Report(this).get(), 
            BApp.SIDE_RAPPORT.get()
        );

        this.mainPan.add(
            new Settings(this).get(), 
            BApp.SIDE_SETTING.get()
        );

    }

    private void initMain(){
        
        cardLayout = new CardLayout();

        this.mainPan = new BComponentPan();
        this.mainPan.setLayout(cardLayout);

        this.mainPan.setBorder(new EmptyBorder(
            15,
            20,
            15,
            20
        ));

        super.setObservator(new Observator() {
            @Override
            public void update() {
                getActiveComponent();
            }
        });

        this.container.add(this.mainPan, BorderLayout.CENTER);
    }

    public void getActiveComponent(){

        if (activeSide != null) {
            
            switch (activeSide.getText()) {
    
                case "DASHBOARD" :
                    cardLayout.show(
                        this.mainPan, 
                        BApp.SIDE_DASHBOARD.get()
                    );
                    this.container.repaint();
                break;
    
                case "LIVRE" :
                    cardLayout.show(
                        this.mainPan, 
                        BApp.SIDE_BOOK.get()
                    );
                    this.container.repaint();
                break;
    
                case "ADHERENT" :
                    cardLayout.show(
                        this.mainPan, 
                        BApp.SIDE_ADHERENT.get()
                    );
                    this.container.repaint();
                break;
    
                case "PRET" :
                    cardLayout.show(
                        this.mainPan, 
                        BApp.SIDE_LOAN.get()
                    );
                    this.container.repaint();
                break;
    
                case "RAPPORT" :
                    cardLayout.show(
                        this.mainPan, 
                        BApp.SIDE_RAPPORT.get()
                    );
                    this.container.repaint();
                break;
    
                case "SETTING" :
                    cardLayout.show(
                        this.mainPan, 
                        BApp.SIDE_SETTING.get()
                    );
                    this.container.repaint();
                break;
                
            }

        }
       
    }

}
