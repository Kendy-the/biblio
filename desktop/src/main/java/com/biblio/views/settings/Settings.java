package com.biblio.views.settings;

import java.awt.CardLayout;

import javax.swing.border.EmptyBorder;

import com.biblio.config.BApp;
import com.biblio.models.User;
import com.biblio.views.Views;
import com.biblio.views.components.BButton;
import com.biblio.views.components.BaseFrame;

public class Settings extends Views {

    @SuppressWarnings("unused")
    private BaseFrame parent;

    private CardLayout cardLayout;

    private ProfilePanel profilePanel;
    private PasswordPanel passwordPanel;
    private AddUserPanel addUserPanel;
    private UserListPanel userListPanel;

    private BButton btnProfile;
    private BButton btnPassword;
    private BButton btnAddUser;
    private BButton btnList;

    public Settings(BaseFrame parent) {

        this.parent = parent;

        initTop();
        initCenter();

    }

    public Settings get(){
        return this;
    }

    private void initTop() {

        top.setBTitle("Settings", true);
        top.setBDescribe("Gérez votre compte et les utilisateurs");

        btnProfile = new BButton("Profil");
        btnPassword = new BButton("Mot de passe");
        btnAddUser = new BButton("Ajouter utilisateur");
        btnList = new BButton("Utilisateurs");

        btnProfile.addActionListener(e ->
                cardLayout.show(center, "profile"));

        btnPassword.addActionListener(e ->
                cardLayout.show(center, "password"));

        btnAddUser.addActionListener(e ->
                cardLayout.show(center, "add"));

        btnList.addActionListener(e ->
                cardLayout.show(center, "list"));

        top.setBButton(btnProfile);
        top.setBButton(btnPassword);

        if (User.getConnected().getStringRole().equals(BApp.USER_ADMIN_ROLE.get())) {
            top.setBButton(btnAddUser);
            top.setBButton(btnList);
        }
    }

    private void initCenter() {

        cardLayout = new CardLayout();

        center.setLayout(cardLayout);
        center.setBorder(new EmptyBorder(20,10,10,30));

        profilePanel = new ProfilePanel();
        passwordPanel = new PasswordPanel();
        addUserPanel = new AddUserPanel();
        userListPanel = new UserListPanel();

        center.add(profilePanel, "profile");
        center.add(passwordPanel, "password");
        center.add(addUserPanel, "add");
        center.add(userListPanel, "list");
    }
}
