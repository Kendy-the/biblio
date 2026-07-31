package com.biblio.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class DB {

    public static Connection get(){

        Connection db = null;

        String dns = "jdbc:mysql://localhost:3306/biblio";

        try {
            db = DriverManager.getConnection(dns, "root", "My1234");
        } catch (Exception e) {
            System.err.println("Error connecting to the database : " + e.getMessage());
            e.printStackTrace();
        }
        
        return db;
    }
    
}
