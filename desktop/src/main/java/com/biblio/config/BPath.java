package com.biblio.config;

public enum BPath {

    IMG_PATH("/assets/img/"),
    DATA_PATH("/data/"),
    DATA_IMG_PATH("/data/img/");        

    private final String text;

    public String get(){
        return text;
    }

    BPath(String text){
        this.text = text;
    }
}
