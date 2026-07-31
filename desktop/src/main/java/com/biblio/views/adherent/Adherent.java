package com.biblio.views.adherent;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.biblio.config.BApp;
import com.biblio.controllers.AdherentController;
import com.biblio.errors.BErrorMgr;
import com.biblio.observer.AdherentListener;
import com.biblio.observer.BAppEventObservable;
import com.biblio.views.Views;
import com.biblio.views.components.BButton;
import com.biblio.views.components.BaseFrame;
import com.biblio.views.components.BSroll.BScrollPan;
import com.biblio.views.components.BTable.BTable;
import com.biblio.views.components.BTable.BTableActionCellEditor;
import com.biblio.views.components.BTable.BTableActionCellRender;
import com.biblio.views.components.BTable.BTableActionListener;

public class Adherent extends Views{

    private BaseFrame parent;
    private NewAdherentDialog newAdherentDialog;

    private BButton newAdherent;
    private SearchAdherentDialog searchAdherentDialog;
    private BButton searchBButton;
    private BButton save;

    private ArrayList<com.biblio.models.Adherent> adherents;
    private BTable adherentTable;
    private DefaultTableModel tableModel;

    private AAdherentListener adherentListener;

    public Adherent(BaseFrame parent){

        super();

        this.parent = parent;
        adherentListener = new AAdherentListener();

        /*
        * TOP - DESIGN
        */
        this.BTop();

        /*
        *  CENTER - DESIGN
        */
        this.BCenter();
        
        /*
        *  BOTTOM - DESIGN
        */
        this.BBottom();
    }

    public Adherent get(){
        return this;
    }

    /*******************************************
    *           LOGIC PART METHODE            *
    ********************************************/

    public void initApp(){
        adherentTable = new BTable(
            "id",
            "Preom",
            "Nom",
            "Telephone",
            "Photo",
            "Actions"
        );
        tableModel = (DefaultTableModel) adherentTable.getModel();

        index();
        initEvent();
    }

    private void initEvent(){
        BAppEventObservable.addAdherentListener(adherentListener);
    }

    public void index(){

        this.adherents = new AdherentController().index();

        for (com.biblio.models.Adherent adherent : adherents) {
            adherentTable.addRow(adherent);
        }

    }

    public void edit(int row){

        adherentTable.setEditRow(row);

        Object id = tableModel.getValueAt(row, 0);

        com.biblio.models.Adherent a = this.getById(Integer.parseInt((String)id));

        if (a != null) { 
            
            NewAdherentDialog editAdherentDialog = new NewAdherentDialog(
                parent, 
                "Modifier Adherent", 
                true,
                a
            );
            editAdherentDialog.showDialog();
        }
        
    }

    public void delete(int row){

        if (adherentTable.isEditing()) {
            adherentTable.getCellEditor().stopCellEditing();
        }

        Object id = tableModel.getValueAt(row, 0);

        com.biblio.models.Adherent a = this.getById(Integer.parseInt((String)id));

        String msg = "Erreur, lors de la suppression";

        if (a != null) { 

            if (!wantToDelete(a.getFirstName() + " " + a.getLastName())) {
                return;
            }

            if(AdherentController.delete(a)) {
                msg = "Supprimer avec success";
                tableModel.removeRow(row);

                // For listeners
                BAppEventObservable.notifyDeletedAdherent(a);
            }else{
                System.out.println("id"+a.getId());
                if (BErrorMgr.hasErrors()) {
                    msg = BErrorMgr.getErrorsMessage();
                } else {
                    msg = "Erreur, champ invalide";
                }
            }

        }

        JOptionPane.showMessageDialog(
            parent, 
            msg,
            "Supprimer adherent",
            JOptionPane.INFORMATION_MESSAGE
        );

    }

    public com.biblio.models.Adherent getById(int id){

        for (com.biblio.models.Adherent adherent : adherents) {
            if (adherent.getId() == id) {
                return adherent;
            }
        }

        return null;
    }

    public void refreshAdherents() {
        adherentTable.clearRows();
        index();
    }

    /*******************************************
    *           DESIGN PART METHODE            *
    ********************************************/

    private void BTop(){

        /*
        * TOP PARENT PAN
        */  
        this.top.setBTitle(
            BApp.SIDE_ADHERENT.get(),
            true
        );
        this.top.setBDescribe("Gérez les adherent de votre bibliothèque");

        // add Adherent
        this.newAdherentDialog = new NewAdherentDialog(
            parent, 
            "Nouveau Adherent",
            true
        );
       
        this.newAdherent = new BButton(
        "Ajouter un adherent",
            getClass().getResource(BApp.IMG_PATH.get() + "new.png")
        );
        this.newAdherent.setRounded(10);
        this.newAdherent.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                newAdherentDialog.showDialog();
            }
        });

        // Search book
        this.searchAdherentDialog = new SearchAdherentDialog(
            parent,
            "Recherche adherent",
            true
        );
        this.searchBButton = new BButton(
            "Chercher un adherent",
            getClass().getResource(BApp.IMG_PATH.get() + "search.png")
        );
        this.searchBButton.setRounded(10);
        this.searchBButton.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e){
                searchAdherentDialog.showDialog();
            }
        });

        this.top.setBButton(searchBButton);
        this.top.setBButton(newAdherent);
    }

    private void BCenter(){
        
        // Set-Up Table
        initApp();

        adherentTable.setActionColumn(5);
        adherentTable.getColumnModel()
        .getColumn(5)
        .setCellRenderer(
                new BTableActionCellRender()
        );

        adherentTable.getColumnModel()
        .getColumn(5)
        .setCellEditor(
            new BTableActionCellEditor( adherentTable,
                new BTableActionListener() {

                    @Override
                    public void onEdit(int row) {
                        edit(row);
                    }

                    @Override
                    public void onDelete(int row) {
                        delete(row);
                    }
                }
            )
        );

        this.center.setLayout(new GridLayout(1,1));
        this.center.setBorder(
            new EmptyBorder(
            20, 
            0,
            20,
            0
        ));
        this.center.add(new BScrollPan(adherentTable));
    }

    private void BBottom(){

        this.save = new BButton("Sauvegarder");
        this.save.setRounded(10);
        this.save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){

                JOptionPane.showMessageDialog(null, 
                    "Enregistrer avec success", 
                    "Adherent Enregistrer",
                    JOptionPane.INFORMATION_MESSAGE
                );
            }
        });

        bottom.setLayout(new BorderLayout());
        bottom.setBorder(new EmptyBorder(10,0,10,0));
        bottom.add(this.save, BorderLayout.EAST);
    }

    /*******************************************
    *          LISTENER PART METHODE           *
    ********************************************/

    class AAdherentListener implements AdherentListener{

        @Override
        public void addedAdherent(com.biblio.models.Adherent adherent) {
            refreshAdherents(); 
        }

        @Override
        public void editedAdherent(com.biblio.models.Adherent adherent) {
            refreshAdherents();
        }

        @Override 
        public void deletedAdherent(com.biblio.models.Adherent adherent) {}
        
        @Override
        public void update(Object object) {}
    }
}
