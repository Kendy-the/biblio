package com.biblio.views.layout;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.biblio.analytic.AnalyticsScheduler;
import com.biblio.analytic.AnalyticsService;
import com.biblio.config.BApp;
import com.biblio.observer.Observable;
import com.biblio.observer.Observator;
import com.biblio.views.adherent.NewAdherentDialog;
import com.biblio.views.auth.Login;
import com.biblio.views.book.NewBookDialog;
import com.biblio.views.components.BButton;
import com.biblio.views.components.BSideBar;
import com.biblio.views.components.BaseFrame;
import com.biblio.views.components.BMenu.BMenu;
import com.biblio.views.components.BMenu.BMenuBar;
import com.biblio.views.components.BMenu.BMenuItem;
import com.biblio.views.loan.NewLoanDialog;

public class AppLayout extends BaseFrame implements Observable{

    protected BMenuBar menuBar;
    
    protected BMenu appMenu;
    protected BMenuItem quitMenu;
    
    protected BMenu adherentMenu;
    protected BMenuItem newAdherent, listAdherent;
    protected NewAdherentDialog newAdherentDialog;
    
    protected BMenu bookMenu;
    protected BMenuItem newBook, listBook;
    protected NewBookDialog newBookDialog;
    
    protected BMenu loanMenu;
    protected BMenuItem newLoan, listLoan;
    protected NewLoanDialog newLoanDialog;
    
    protected BSideBar sideBar;
    protected BButton activeSide;
    protected BButton dashboardSide,
    bookSide, adherentSide, loanSide, helpSide,
    inventaireSide, reportSide, settingSide;

    protected SideBarListener sideBarListener;
    private ArrayList<Observator> observators;

    public AppLayout(){

        this.observators = new ArrayList<Observator>();
        this.activeSide = new BButton(getName());
        /*
        * MENU BAR
        */
        this.menuBar = new BMenuBar();

        /*
        * APP MENU
        */
        this.appMenu = new BMenu("Biblio");
        this.quitMenu = new BMenuItem("Quit");
        this.quitMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                System.exit(0);
            }
        });
        this.appMenu.add(this.quitMenu);
        menuBar.add(this.appMenu);

        /*
        * BOOK MENU
        */
        this.bookMenu = new BMenu("Livres");
        this.newBook = new BMenuItem("Nouveau livre");

        this.newBookDialog =  new NewBookDialog(
            this, 
            "Nouveau Livre", 
            true
        );
        this.newBook.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
               newBookDialog.showDialog();
            }
            
        });
        this.listBook = new BMenuItem("Tous les livres");
        this.listBook.addActionListener(e->{
            activeSide = new BButton( BApp.SIDE_BOOK.get());
            run();
        });

        this.bookMenu.add(newBook);
        this.bookMenu.add(this.listBook);
        menuBar.add(this.bookMenu);

        /*
        * ADHERENT MENU
        */
        this.adherentMenu = new BMenu("Adherent");
        this.newAdherent = new BMenuItem("Nouveau Adherent");

        this.newAdherentDialog = new NewAdherentDialog(
            this, 
            "Nouveau Adherent", 
            true
        );
        this.newAdherent.addActionListener(e -> {
            newAdherentDialog.showDialog();
        });
        this.listAdherent = new BMenuItem("Tous les Adherent");
        this.listAdherent.addActionListener(e->{
            activeSide = new BButton(BApp.SIDE_ADHERENT.get());
            run();
        });

        this.adherentMenu.add(newAdherent);
        this.adherentMenu.add(this.listAdherent);
        menuBar.add(this.adherentMenu);

        /*
        * LOAN MENU
        */
        this.loanMenu = new BMenu("Pret");
        this.newLoan = new BMenuItem("Nouveau Pret");

        this.newLoanDialog = new NewLoanDialog(
            this,
            "Nouveau pret",
            true
        );
        this.newLoan.addActionListener(e->{
            newLoanDialog.showDialog();
        });
        this.listLoan = new BMenuItem("Tous les Pret");
        this.listLoan.addActionListener(e->{
            activeSide = new BButton(BApp.SIDE_LOAN.get());
            run();
        });

        this.loanMenu.add(newLoan);
        this.loanMenu.add(this.listLoan);
        menuBar.add(this.loanMenu);

        /*
        * SIDE BAR TOP
        */
        this.sideBar = new BSideBar();
        this.sideBarListener = new SideBarListener();

        this.dashboardSide = new BButton(
            BApp.SIDE_DASHBOARD.get(),
            getClass().getResource(BApp.IMG_PATH.get() + "home.png")
        );
        this.sideBar.setTopBButton(this.dashboardSide);
        this.dashboardSide.addActionListener(sideBarListener);

        this.bookSide = new BButton(
            BApp.SIDE_BOOK.get(),
            getClass().getResource(BApp.IMG_PATH.get() + "book.png")
        );
        this.sideBar.setTopBButton(this.bookSide);
        this.bookSide.addActionListener(sideBarListener);

        this.adherentSide = new BButton(
            BApp.SIDE_ADHERENT.get(),
            getClass().getResource(BApp.IMG_PATH.get() + "adherent.png")
        );
        this.sideBar.setTopBButton(this.adherentSide);
        this.adherentSide.addActionListener(sideBarListener);

        this.loanSide = new BButton(
            BApp.SIDE_LOAN.get(),
            getClass().getResource(BApp.IMG_PATH.get() + "loan.png")
        );
        this.sideBar.setTopBButton(this.loanSide);
        this.loanSide.addActionListener(sideBarListener);

        // this.inventaireSide = new BButton(
        //     BConstant.SIDE_INVENTAIRE.get(),
        //     BConstant.IMG_PATH.get() + "shelf.png"
        // );
        // this.sideBar.setTopBButton(this.inventaireSide);
        // this.inventaireSide.addActionListener(sideBarListener);

        this.reportSide = new BButton(
            BApp.SIDE_RAPPORT.get(),
            getClass().getResource(BApp.IMG_PATH.get() + "etat.png")
        );
        this.sideBar.setTopBButton(this.reportSide);
        this.reportSide.addActionListener(sideBarListener);

        /* 
        * SIDE BAR BOTTOM
        */
        this.settingSide = new BButton(
            BApp.SIDE_SETTING.get(),
            getClass().getResource(BApp.IMG_PATH.get() + "setting.png")
        );
        this.sideBar.setBottomBButton(settingSide);
        this.settingSide.addActionListener(sideBarListener);

        this.helpSide = new BButton(
            BApp.SIDE_HELP.get(),
            getClass().getResource(BApp.IMG_PATH.get() + "question.png")
        );
        this.sideBar.setBottomBButton(helpSide);
        helpSide.addActionListener(e->{
            JOptionPane.showMessageDialog(
                null, 
                "Besoin d'aide ?\nVeuillez nous contacter\n"+
                "Phone : (509) 3780 - 0137 / 4042 - 1847\n"+
                "Email : kendythe.c@gmail.com\n"+
                "ING. PRESUME, DEV"
            );
        });

        this.sideBar.setButtonRounded(20);
        this.activeSide = dashboardSide;

        this.container.add(this.sideBar, BorderLayout.WEST);
        this.setJMenuBar(this.menuBar);
        this.initApp();
    }

    private void initApp(){
        this.setDefaultCloseOperation(BaseFrame.DO_NOTHING_ON_CLOSE);
        this.setVisible(true);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {

                int choice = JOptionPane.showConfirmDialog(
                    AppLayout.this,
                    "Voulez-vous vous déconnecter ?",
                    "Confirmation",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
                );

                if (choice == JOptionPane.YES_OPTION) {

                    dispose();

                    new Login().setVisible(true);
                    AnalyticsScheduler.stop();
                    AnalyticsService.shutdown();
                }
            }
        });
    }

    public class SideBarListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            activeSide = (BButton) e.getSource();
            run();
        }
        
    }

    @Override
    public void setObservator(Observator observator) {
        this.observators.add(observator);
    }

    @Override
    public void run() {
        for (Observator observator : observators) {
            observator.update();
        }
    }

}
