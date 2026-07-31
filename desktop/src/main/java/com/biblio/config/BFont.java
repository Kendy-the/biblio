package com.biblio.config;

public enum BFont {
    
    PRIMARY("Courrier"),
    SECONDARY("SansSerif");

    private final String font;

    BFont(String font){
        this.font = font;
    }

    public String get(){
        return this.font;
    }
}
