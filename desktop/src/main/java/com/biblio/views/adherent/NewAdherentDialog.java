package com.biblio.views.adherent;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.biblio.config.BColor;
import com.biblio.config.BApp;
import com.biblio.controllers.AdherentController;
import com.biblio.errors.BErrorMgr;
import com.biblio.models.Adherent;
import com.biblio.observer.BAppEventObservable;
import com.biblio.views.components.BButton;

public class NewAdherentDialog extends JDialog{

    private JTextField lastNameField;
    private JTextField firstNameField;
    private JTextField phoneField;

    private JLabel photoPreview;

    private String photoPath;

    private Adherent adherent;
  
    public NewAdherentDialog(JFrame parent,String title,boolean modal) {

        super(parent, title, modal);

        setSize(650, 350);
        setLocationRelativeTo(null);
        setResizable(false);

        init();
    }

    public NewAdherentDialog(
            JFrame parent,
            String title,
            boolean modal,
            Adherent adherent
    ) {

        super(parent, title, modal);

        this.adherent = adherent;

        setSize(650, 350);
        setLocationRelativeTo(null);
        setResizable(false);

        init();

        loadData();
    }

    public void showDialog(){
        this.setVisible(true);
    }

    private void init() {
        
        JPanel content = new JPanel();

        content.setBackground(
                BColor.WHITE.get()
        );

        // Nom

        JPanel panLastName = new JPanel();

        panLastName.setBackground(
                BColor.WHITE.get()
        );

        panLastName.setBorder(
                BorderFactory.createTitledBorder(
                        "Nom"
                )
        );

        lastNameField = new JTextField();

        lastNameField.setPreferredSize(
                new Dimension(180, 25)
        );

        panLastName.add(lastNameField);

        // Prénom

        JPanel panFirstName = new JPanel();

        panFirstName.setBackground(
                BColor.WHITE.get()
        );

        panFirstName.setBorder(
                BorderFactory.createTitledBorder(
                        "Prénom"
                )
        );

        firstNameField = new JTextField();

        firstNameField.setPreferredSize(
                new Dimension(180, 25)
        );

        panFirstName.add(firstNameField);

        // Téléphone

        JPanel panPhone = new JPanel();

        panPhone.setBackground(
                BColor.WHITE.get()
        );

        panPhone.setBorder(
                BorderFactory.createTitledBorder(
                        "Téléphone"
                )
        );

        phoneField = new JTextField();

        phoneField.setPreferredSize(
                new Dimension(180, 25)
        );

        panPhone.add(phoneField);

        // Photo

        JPanel panPhoto = new JPanel();

        panPhoto.setBackground(
                BColor.WHITE.get()
        );

        panPhoto.setBorder(
                BorderFactory.createTitledBorder(
                        "Photo"
                )
        );

        photoPreview = new JLabel();

        photoPreview.setPreferredSize(
                new Dimension(120, 120)
        );

        photoPreview.setBorder(
                BorderFactory.createEtchedBorder()
        );

        BButton btnUpload =
                new BButton("Choisir photo");

        btnUpload.addActionListener(
                this::choosePhoto
        );

        panPhoto.add(photoPreview);
        panPhoto.add(btnUpload);

        content.add(panLastName);
        content.add(panFirstName);
        content.add(panPhone);
        content.add(panPhoto);

        // Boutons

        JPanel control = new JPanel();

        BButton saveButton =
                new BButton("Enregistrer");

        saveButton.addActionListener(e -> {

            if(adherent != null){
                edit();
            }else{
                save();
            }

            setVisible(false);
        });

        BButton cancelButton =
                new BButton("Annuler");

        cancelButton.addActionListener(e -> {
            setVisible(false);
        });

        control.add(saveButton);
        control.add(cancelButton);

        getContentPane().add(
                content,
                BorderLayout.CENTER
        );

        getContentPane().add(
                control,
                BorderLayout.SOUTH
        );
    }

    private void choosePhoto(ActionEvent e) {

        JFileChooser chooser = new JFileChooser();

        int result = chooser.showOpenDialog(this);

        if(result == JFileChooser.APPROVE_OPTION){

            File sourceFile = chooser.getSelectedFile();

            try {
                photoPath = saveImage(sourceFile);
            } catch (Exception ie) {
                System.out.println("Error image "+ie.getMessage());
            }

            if (photoPath != null) {

                ImageIcon icon = new ImageIcon(photoPath);

                Image img =
                        icon.getImage()
                                .getScaledInstance(
                                        120,
                                        120,
                                        Image.SCALE_SMOOTH
                                );

                photoPreview.setIcon(
                        new ImageIcon(img)
                );
            }

        }
    }

    private String saveImage(File sourceFile) throws IOException{

        if(sourceFile == null)
            return null;

        File fileDirectory = new File(BApp.DATA_PATH.get() + "img");

            if (!fileDirectory.exists()) {
                fileDirectory.mkdirs();
            }

            String fileName = 
                        System.currentTimeMillis()
                        + "_" + sourceFile.getName();


            File destFile = new File(BApp.DATA_IMG_PATH.get() + fileName);

            Files.copy(
                sourceFile.toPath(), 
                destFile.toPath(),
                StandardCopyOption.REPLACE_EXISTING
            );

            return destFile.getPath();
    }

    private void loadData() {

        lastNameField.setText(
                adherent.getLastName()
        );

        firstNameField.setText(
                adherent.getFirstName()
        );

        phoneField.setText(
                adherent.getPhone()
        );

        photoPath =
                adherent.getPhoto();

        if(photoPath != null){

            ImageIcon icon =
                    new ImageIcon(photoPath);

            Image img =
                    icon.getImage()
                            .getScaledInstance(
                                    120,
                                    120,
                                    Image.SCALE_SMOOTH
                            );

            photoPreview.setIcon(
                    new ImageIcon(img)
            );
        }
    }

    private void save() {

        String msg;
        int icon;

        int insertId = new AdherentController()
                .save(getData());

        if(insertId > 0){

            msg = "Enregistrer avec success";
            icon = JOptionPane.INFORMATION_MESSAGE;

            // For Listeners
            Adherent a = new Adherent(getData());
            a.setId(insertId);

            BAppEventObservable.notifyAddedAdherent(a);
            clearFields();

        }else{

            if (BErrorMgr.hasErrors()) {
                msg = BErrorMgr.getErrorsMessage();
            } else {
                msg = "Erreur, champ invalide";
            }
            icon = JOptionPane.ERROR_MESSAGE;

        }

        JOptionPane.showMessageDialog(
                this,
                msg,
                "Enregistrer Adherent",
                icon
        );
    }

    private void edit() {

        String msg;
        int icon;

        if(new AdherentController()
                .edit(getData(), adherent)){

            msg = "Modifier avec success";
            icon = JOptionPane.INFORMATION_MESSAGE;

            // For Listeners
            Adherent  a = new Adherent(getData());
            a.setId(adherent.getId());
            
            BAppEventObservable.notifyEditedAdherent(a);

        }else
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
            "Modifier Adherent",
            icon
        );
    }

    private Map<String, String> getData() {

        Map<String, String> data =
                new HashMap<>();

        data.put(
                "lastName",
                lastNameField.getText()
        );

        data.put(
                "firstName",
                firstNameField.getText()
        );

        data.put(
                "phone",
                phoneField.getText()
        );

        data.put(
                "photo",
                photoPath != null ? photoPath : ""
        );

        return data;
    }

    private void clearFields() {

        lastNameField.setText("");
        firstNameField.setText("");
        phoneField.setText("");

        photoPreview.setIcon(null);

        photoPath = null;
    }

}
