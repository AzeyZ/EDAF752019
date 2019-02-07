DROP TABLE IF EXISTS customers;
CREATE TABLE customers(
user_name TEXT PRIMARY KEY,
full_name TEXT,
pass_word TEXT);

-- Delete tables if they exist
PRAGMA foreign_keys=OFF;

DROP TABLE IF EXISTS tickets;
DROP TABLE IF EXISTS screenings;
DROP TABLE IF EXISTS movies;
DROP TABLE IF EXISTS theaters;

PRAGMA foreign_keys=ON;

-- Create tables
CREATE TABLE tickets(
id BLOB PRIMARY KEY,
theater_name TEXT REFERENCES theather(theather_name),
user_name TEXT REFERENCES customers(user_name)
);

CREATE TABLE screenings(
start_time TIME,
screening_date DATE,
id BLOB REFERENCES tickets(id),
imdb_key TEXT REFERENCES movies(imdb_key),
PRIMARY KEY (start_time, screening_date, theater_name)
);

CREATE TABLE movies(
imdb_key TEXT PRIMARY KEY,
title TEXT,
production_year INT,
playtime INT);

CREATE TABLE theaters(
theater_name TEXT PRIMARY KEY,
capacity INT
);

-- Insert data into tables
INSERT INTO movies (imdb_key, title, production_year, playtime)
VALUES 	("tt0111161", "The Shawshank Redemption", 1994, 142),
	("tt0368226", "The Room", 2003, 99),
	("tt0468569", "The Dark Knight", 2008, 152),
	("tt0109830", "Forrest Gump", 1994, 142),
	("tt0110912", "Pulp Fiction", 1994, 154),
	("tt0108052", "Schindlers List", 1993, 195),
	("tt0050083", "12 Angry Men", 1957, 96),
	("tt1375666", "Inception", 2010, 148),
	("tt0137523", "Fight Club", 1999, 139);

INSERT INTO customers (user_name, full_name, pass_word)
VALUES 	("gosta", "Gösta Persson", "gösta123"),
	("gunnhild", "Gunnhild Svensson", "gunnhild123"),
	("goran", "Göran Olsson", "goran123"),
	("gurra", "Gustav Olofsson", "gurra123"),
	("lisa", "Lisa Mattsson", "lisa123");

INSERT INTO theaters (theater_name, capacity)
VALUES	("Filmstaden", 127),
	("Kino", 52),
	("Folkets Bio i Lund Södran", 50);

INSERT INTO tickets (id,theater_name, user_name)
VALUES	(lower(hex(randomblob(16))), "Filmstaden", "gosta");

INSERT INTO screenings(start_time, screening_date, id, imdb_key)
VALUES ();
