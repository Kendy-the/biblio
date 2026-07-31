package com.biblio.views.components.BTable;

import java.awt.FlowLayout;

import com.biblio.config.BApp;
import com.biblio.models.User;
import com.biblio.views.components.BButton;
import com.biblio.views.components.BComponentPan;

public class BActionPanel extends BComponentPan {

    BButton btnEdit;
    BButton btnDelete;

    public BActionPanel() {

        setOpaque(true);

        setLayout(new FlowLayout(
                FlowLayout.CENTER,
                5,
                5
        ));

        btnEdit = new BButton("Edit");
        btnDelete = new BButton("Delete");

        btnEdit.setFocusable(false);
        btnDelete.setFocusable(false);

        add(btnEdit);

        if (User.getConnected().getStringRole().equals(BApp.USER_ADMIN_ROLE.get())) {
            add(btnDelete);
        }
    }
}
