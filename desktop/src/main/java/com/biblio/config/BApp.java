package com.biblio.config;

public enum BApp {

    ACTIF_DB("SQLITE"),
    ENV("DEV"),

    LOAN_ACTIF_STATUS("ACTIF"),
    LOAN_RETURNED_STATUS("RETOURNER"),

    USER_USER_ROLE("USER"),
    USER_ADMIN_ROLE("ADMIN"),
    
    IMG_PATH("/assets/img/"),
    DATA_PATH("/data/"),
    DATA_IMG_PATH("/data/img/"),
    
    SIDE_DASHBOARD("DASHBOARD"),
    SIDE_BOOK("LIVRE"),
    SIDE_LOAN("PRET"),
    SIDE_ADHERENT("ADHERENT"),
    SIDE_INVENTAIRE("INVENTAIRE"),
    SIDE_RAPPORT("RAPPORT"),
    SIDE_SETTING("SETTING"),
    SIDE_HELP("AIDE");
    
    private final String text;

    public String get(){
        return text;
    }

    BApp(String text){
        this.text = text;
    }

}
