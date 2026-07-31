package com.biblio.database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.biblio.config.BApp;

public class DBSQLite {

    private static Connection connection;

    public static Connection get() {

        try {

            if (connection == null || connection.isClosed()) {

                String appData = System.getenv("APPDATA");

                File appDir = new File(
                    appData,
                    "biblio"
                );

                if (!appDir.exists()) {
                    appDir.mkdirs();
                }

                String dbPath = new File(
                    appDir,
                    "biblio" + BApp.ENV.get() + ".db"
                ).getAbsolutePath();

                System.out.println(
                    "Base SQLite : " + dbPath
                );

                connection = DriverManager.getConnection(
                    "jdbc:sqlite:" + dbPath
                );

                connection.createStatement()
                          .execute(
                              "PRAGMA foreign_keys = ON"
                          );
            }

        } catch (SQLException e) {

            e.printStackTrace();

            return null;
        }

        return connection;
    }
}
