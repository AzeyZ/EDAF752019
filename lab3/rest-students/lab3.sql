-- Delete tables if they exist
PRAGMA foreign_keys=OFF;

DROP TABLE IF EXISTS customers;
DROP TABLE IF EXISTS tickets;
DROP TABLE IF EXISTS screenings;
DROP TABLE IF EXISTS movies;
DROP TABLE IF EXISTS theaters;

PRAGMA foreign_keys=ON;

-- Create tables
CREATE TABLE customers(
user_name TEXT PRIMARY KEY,
full_name TEXT,
pass_word TEXT

-- user_name TEXT PRIMARY KEY,
-- full_name TEXT,
-- pass_word TEXT);
);

CREATE TABLE tickets(
ticket_id TEXT DEFAULT (lower(hex(randomblob(16)))),
user_name TEXT REFERENCES customers(user_name),
screening_time,
screening_date,
theater_name,
FOREIGN KEY (screening_time, screening_date, theater_name) REFERENCES screenings(screening_time, screening_date, theater_name),
PRIMARY KEY (ticket_id)

-- ticket_id TEXT DEFAULT (lower(hex(randomblob(16)))),
-- theater_name TEXT REFERENCES theaters(theater_name),
-- user_name TEXT REFERENCES customers(user_name),
-- start_time TIME REFERENCES screenings(start_time),
-- screening_date DATE REFERENCES screenings(screening_date),
-- PRIMARY KEY (ticket_id)
);

CREATE TABLE screenings(
screening_id TEXT DEFAULT (lower(hex(randomblob(16)))),
screening_time TIME,
screening_date DATE,
production_year INT,
movie_name TEXT,
reamaining_seats INT,
theater_name TEXT REFERENCES theaters(theater_name),
FOREIGN KEY (movie_name, production_year) REFERENCES movies (movie_name, production_year),
PRIMARY KEY (screening_time, screening_date, theater_name)

-- start_time TIME,
-- screening_date DATE,
-- imdb_key TEXT REFERENCES movies(imdb_key),
-- theater_name TEXT REFERENCES theaters(theater_name),
-- PRIMARY KEY (start_time, screening_date, imdb_key)
);

CREATE TABLE movies(
movie_name TEXT, 
production_year INT,
playtime INT,
imdb_key TEXT,
PRIMARY KEY (movie_name, production_year)

-- imdb_key TEXT PRIMARY KEY,
-- title TEXT,
-- production_year INT,
-- playtime INT);
);

CREATE TABLE theaters(
theater_name TEXT PRIMARY KEY,
capacity INT

-- theater_name TEXT PRIMARY KEY,
-- capacity INT
);

-- Insert data into tables
INSERT INTO movies (imdb_key, movie_name, production_year, playtime)
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

--INSERT INTO screenings (screening_time, screening_date, production_year, movie_name, theater_name)
--VALUES 	('19:00:00', '2019-02-10', 2003, "The Room", "Filmstaden"),
--	('15:00:00', '2019-02-10', 1994,"Forrest Gump", "Kino"),
--	('20:00:00', '2019-02-11', 2008,"The Dark Knight", "Filmstaden");

--INSERT INTO tickets (user_name, screening_time, screening_date, theater_name)
--VALUES	("gosta", '19:00:00', '2019-02-10', "Filmstaden"),
--	("gunnhild", '19:00:00', '2019-02-10', "Filmstaden"),
--	("gurra", '19:00:00', '2019-02-10', "Filmstaden");
