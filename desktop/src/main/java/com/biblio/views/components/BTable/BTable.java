package com.biblio.views.components.BTable;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.biblio.config.BColor;
import com.biblio.models.Model;


public class BTable extends JTable {

    private final DefaultTableModel model;

    private int actionColumn = -1;
    private int rowEdit = -1;

    public BTable(String... columns) {

        model = new DefaultTableModel(columns, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return (column == actionColumn) || (row == rowEdit);
            }
        };

        setModel(model);

        initStyle();
    }

    public void setEditRow(int row) {
        this.rowEdit = row;
    }

    public void setActionColumn(int column) {
        this.actionColumn = column;
    }

    private void initStyle() {

        setRowHeight(40);

        setIntercellSpacing(new Dimension(10,0));

        setShowVerticalLines(false);
        setShowHorizontalLines(true);

        setGridColor(new Color(240, 240, 240));

        setSelectionBackground(new Color(0, 100, 20, 40));
        setSelectionForeground(BColor.BLACK.get());

        setFont(new Font("Segoe UI", Font.PLAIN, 14));

        setBackground(BColor.WHITE.get());

        setFillsViewportHeight(true);

        JTableHeader header = getTableHeader();

        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setPreferredSize(new Dimension(0, 45));

        header.setBackground(BColor.PRIMARY_100.get());
        header.setForeground(BColor.PRIMARY_900.get());

        header.setReorderingAllowed(false);
    }

    public void addRow(Model object) {
        model.addRow(object.toVector());
    }

    public void clearRows() {
        model.setRowCount(0);
    }

    public DefaultTableModel getTableModel() {
        return model;
    }

    @Override
    public Component prepareRenderer(
            javax.swing.table.TableCellRenderer renderer,
            int row,
            int column) {

        Component c = super.prepareRenderer(renderer, row, column);

        if (!isRowSelected(row)) {

            if (row % 2 == 0) {
                c.setBackground(Color.WHITE);
            } else {
                c.setBackground(new Color(248, 249, 250));
            }
        }

        return c;
    }
}
