DROP DATABASE IF EXISTS biblio;
CREATE DATABASE IF NOT EXISTS biblio;

use biblio;

CREATE TABLE IF NOT EXISTS user(
    id INT AUTO_INCREMENT PRIMARY KEY,
    firstName VARCHAR(200),
    lastName VARCHAR(200),
    email VARCHAR(200),
    phone VARCHAR(200),
    adresse VARCHAR(200),
    role int,
    password VARCHAR(200)
);

CREATE TABLE IF NOT EXISTS book(
    id INT AUTO_INCREMENT PRIMARY KEY,
    isbn VARCHAR(255),
    title VARCHAR(255),
    year_pub VARCHAR(255),
    author VARCHAR(255),
    quantity INT DEFAULT 0,
    user_id INT,

    FOREIGN KEY(user_id) REFERENCES user(id)
);

CREATE TABLE IF NOT EXISTS adherent(
    id INT AUTO_INCREMENT PRIMARY KEY,
    firstName VARCHAR(200),
    lastName VARCHAR(200),
    phone VARCHAR(200),
    photo VARCHAR(200),
    user_id INT,

    FOREIGN KEY(user_id) REFERENCES user(id)
);

CREATE TABLE IF NOT EXISTS loan(
    id INT AUTO_INCREMENT PRIMARY KEY,
    startDate DATE,
    endDate DATE,
    book_id INT,
    adherent_id INT,
    status INT DEFAULT 0,
    user_id INT,

    FOREIGN KEY(book_id) REFERENCES book(id),
    FOREIGN KEY(adherent_id) REFERENCES adherent(id),
    FOREIGN KEY(user_id) REFERENCES user(id)
);
