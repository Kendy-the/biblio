package com.biblio.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void init() {

        Connection conn =
        DBSQLite.get();

        if (conn == null) {

            System.err.println(
                "Impossible d'ouvrir la base."
            );

            return;
        }

        try (Statement stmt = conn.createStatement()) {

            stmt.execute("PRAGMA foreign_keys = ON");

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS user (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    firstName TEXT,
                    lastName TEXT,
                    email TEXT UNIQUE,
                    phone TEXT,
                    adresse TEXT,
                    role INTEGER,
                    password TEXT
                );
            """);
                    
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS book (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    isbn TEXT UNIQUE,
                    title TEXT NOT NULL,
                    year_pub TEXT,
                    author TEXT NOT NULL,
                    quantity INTEGER DEFAULT 0 CHECK(quantity >= 0),
                    user_id INTEGER,

                    FOREIGN KEY (user_id)
                        REFERENCES user(id)
                        ON DELETE SET NULL
                );
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS adherent (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    firstName TEXT,
                    lastName TEXT,
                    phone TEXT,
                    photo TEXT,
                    user_id INTEGER,

                    FOREIGN KEY (user_id)
                        REFERENCES user(id)
                        ON DELETE SET NULL
                        ON UPDATE CASCADE
                );
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS loan (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    startDate TEXT,
                    endDate TEXT,
                    book_id INTEGER,
                    adherent_id INTEGER,
                    status INTEGER DEFAULT 0,
                    user_id INTEGER,

                    FOREIGN KEY (book_id)
                        REFERENCES book(id)
                        ON DELETE RESTRICT
                        ON UPDATE CASCADE,

                    FOREIGN KEY (adherent_id)
                        REFERENCES adherent(id)
                        ON DELETE RESTRICT
                        ON UPDATE CASCADE,

                    FOREIGN KEY (user_id)
                        REFERENCES user(id)
                        ON DELETE SET NULL
                        ON UPDATE CASCADE
                );

            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS analytics_queue (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    event TEXT NOT NULL,
                    payload TEXT NOT NULL,
                    retry_count INTEGER DEFAULT 0,
                    status INTEGER DEFAULT 0,
                    created_at DATETIME,
                    sent_at DATETIME
                    );
                    
            """);

            stmt.execute("""
                INSERT OR IGNORE INTO user (id, firstName, lastName, email, phone, adresse, role, password)
                VALUES (1, 'Admin', 'Admin', 'admin@biblio.com', '1234567890', '123 Main St', 1, 'password')   
            """);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
