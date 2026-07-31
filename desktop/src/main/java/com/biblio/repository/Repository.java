package com.biblio.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.biblio.config.BApp;
import com.biblio.database.DB;
import com.biblio.database.DBSQLite;

public class Repository {
    
    protected static Connection db;
    protected PreparedStatement pst;
    protected ResultSet rs;

    public Repository(){

        if (db == null) {
            if (BApp.ACTIF_DB.get().equals("SQLITE")) {
                db = DBSQLite.get();
            }else {
                db = DB.get();
            }
        }
    }
}
