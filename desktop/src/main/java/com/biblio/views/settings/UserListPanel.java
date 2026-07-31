package com.biblio.views.settings;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.biblio.config.BColor;
import com.biblio.controllers.UserController;
import com.biblio.models.User;
import com.biblio.observer.BAppEventObservable;
import com.biblio.observer.UserListener;

import java.awt.*;
import java.util.ArrayList;

import com.biblio.views.components.BTable.BTable;
import com.biblio.views.components.BTable.BTableActionCellEditor;
import com.biblio.views.components.BTable.BTableActionCellRender;
import com.biblio.views.components.BTable.BTableActionListener;
import com.biblio.views.components.BSroll.BScrollPan;

public class UserListPanel extends JPanel {

    private ArrayList<User> users;
    private BTable table;
    private DefaultTableModel tableModel;

    private BUserListener userListener;

    public UserListPanel() {

        userListener = new BUserListener();
        BAppEventObservable.addUserListener(userListener);

        setLayout(new BorderLayout());
        setBackground(BColor.WHITE.get());

        table = new BTable(
                "ID",
                "Nom",
                "Email",
                "Rôle",
                "Action"
        );
        tableModel = (DefaultTableModel) table.getModel();

        users = new UserController().index();
        for (User user : users) {
            table.addRow(user);
        }

        table.setActionColumn(4);
        table.getColumnModel()
        .getColumn(4)
        .setCellRenderer(
                new BTableActionCellRender()
        );

        table.getColumnModel()
        .getColumn(4)
        .setCellEditor(
            new BTableActionCellEditor( table,
                new BTableActionListener() {

                    @Override
                    public void onDelete(int row) {
                        delete(row);
                    }

                    @Override
                    public void onEdit(int row) {
                        JOptionPane.showMessageDialog(
                            table,
                            "Fonctionnalite a venir, Bientot"
                        );
                    }
                }
            )
        );

        add(new BScrollPan(table), BorderLayout.CENTER);
    }

    public void delete(int row){

        if (table.isEditing()) {
            table.getCellEditor().stopCellEditing();
        }

        Object id = tableModel.getValueAt(row, 0);

        com.biblio.models.User u = this.getById(Integer.parseInt((String)id));

        String msg = "Erreur, lors de la suppression";

        if (u != null) { 

            if(UserController.delete(u)) {
                msg = "Supprimer avec success";
                tableModel.removeRow(row);
            }

        }

        JOptionPane.showMessageDialog(
            this, 
            msg,
            "Supprimer user",
            JOptionPane.INFORMATION_MESSAGE
        );

    }

    public com.biblio.models.User getById(int id){

        for (com.biblio.models.User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }

        return null;
    }

    public void refreshUsers() {
        table.clearRows();
        users = new UserController().index();
        
        for (User user : users) {
            table.addRow(user);
        }
    }

    /*******************************************
    *          LISTENER PART METHODE           *
    ********************************************/

    class BUserListener implements UserListener {

        @Override
        public void addedUser(User user) {
            users.add(user);
            table.addRow(user);
        }

        @Override
        public void editedUser(User user) {
            refreshUsers();
        }

        @Override
        public void deletedUser(User user) {
            refreshUsers();
        }

        @Override
        public void update(Object object) {}
        
    }
}
