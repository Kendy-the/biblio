package com.biblio.views.components.BTable;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import java.awt.Component;
import java.awt.event.ActionEvent;

public class BTableActionCellEditor extends AbstractCellEditor implements TableCellEditor {

    private final BActionPanel panel;

    @SuppressWarnings("unused")
    private final JTable table;

    private final BTableActionListener listener;

    private int row;

    public BTableActionCellEditor( JTable table, BTableActionListener listener) {

        this.table = table;
        this.listener = listener;

        panel = new BActionPanel();

        panel.btnEdit.addActionListener(
                this::editAction
        );

        panel.btnDelete.addActionListener(
                this::deleteAction
        );
    }

    private void editAction(ActionEvent e) {

        listener.onEdit(row);

        fireEditingStopped();
    }

    private void deleteAction(ActionEvent e) {

        listener.onDelete(row);

        fireEditingStopped();
    }

    @Override
    public Object getCellEditorValue() {
        return null;
    }

    @Override
    public Component getTableCellEditorComponent(
            JTable table,
            Object value,
            boolean isSelected,
            int row,
            int column
    ) {

        this.row = row;

        return panel;
    }
}
