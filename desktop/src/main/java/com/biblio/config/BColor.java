package com.biblio.config;

import java.awt.Color;

public enum BColor {

    PRIMARY_50("#E6F4EA"),
    PRIMARY_100("#CCE9D5"),
    PRIMARY_200("#99D3AB"),
    PRIMARY_300("#66BD81"),
    PRIMARY_400("#33A757"),
    PRIMARY_500("#006414"), // couleur principale
    PRIMARY_600("#005510"),
    PRIMARY_700("#00460D"),
    PRIMARY_800("#00370A"),
    PRIMARY_900("#002807"),

    SECONDARY_500("#1E293B"),
    SUCCESS_500("#16A34A"),
    WARNING_500("#F59E0B"),
    DANGER_500("#DC2626"),

    WHITE("#FFFFFF"),
    BLACK("#000000");

    private final String hexadecimal;

    BColor(String hexadecimal) {
        this.hexadecimal = hexadecimal;
    }

    public String hexadecimal(){
        return this.hexadecimal;
    }

    public Color get() {
        return Color.decode(this.hexadecimal);
    }

    /**
    * Opacité de 0.0f à 1.0f
    */
    public Color opacity(float alpha) {
        Color base = get();

        return new Color(
                base.getRed(),
                base.getGreen(),
                base.getBlue(),
                Math.round(alpha * 255)
        );
    }

}
