package com.biblio.utils;

import java.time.LocalDate;

public class BUtils {
    
    public static LocalDate toDate(String text){
        
        LocalDate d = null;

        try {
            d = LocalDate.parse(text);
        } catch (Exception e) {
           
        }

        return d;
    }
}
