package com.biblio.views.components.BTable;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import com.biblio.config.BColor;

public class BTableActionCellRender extends BActionPanel implements TableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(
            JTable table,
            Object value,
            boolean isSelected,
            boolean hasFocus,
            int row,
            int column) {

        if (isSelected) {

            setBackground(
                    table.getSelectionBackground()
            );

        } else {
            setBackground(BColor.WHITE.get());
        }

        return this;
    }
}
