DROP TABLE IF EXISTS customers;
CREATE TABLE customers(
user_name TEXT PRIMARY KEY,
full_name TEXT,
password_ TEXT);

DROP TABLE IF EXISTS tickets;
CREATE TABLE tickets(
id TEXT PRIMARY KEY,
theater_name TEXT REFERENCES theather(theather_name),
movie_name TEXT REFERENCES movies(title),
user_name TEXT REFERENCES customers(user_name),
ticket_date DATE,
ticket_time TIME
);

DROP TABLE IF EXISTS movies;
CREATE TABLE movies(
imdb_key INTEGER PRIMARY KEY,
title TEXT,
production_year INT,
playtime INT);

DROP TABLE IF EXISTS theaters;
CREATE TABLE theaters(
theather_name TEXT PRIMARY KEY,
capacity INT
);




INSERT INTO movies (imdb_key, title, production_year, playtime)
VALUES 	(tt0111161, "The Shawshank Redemption", 1994, 142),
	(tt0368226, "The Room", 2003, 99),
	(tt0468569, "The Dark Knight", 2008, 152),
	(tt0109830, "Forrest Gump", 1994, 142),
	(tt0110912, "Pulp Fiction", 1994, 154),
	(tt0108052, "Schindlers List", 1993, 195),
	(tt0050083, "12 Angry Men", 1957, 96),
	(tt1375666, "Inception", 2010, 148),
	(tt0137523, "Fight Club", 1999, 139);

INSERT INTO customers (user_name, full_name, password)
VALUES 	("gosta", "Gösta Persson", "gösta123"),
	("gunnhild", "Gunnhild Svensson", "gunnhild123"),
	("goran", "Göran Olsson", "goran123"),
	("gurra", "Gustav Olofsson", "gurra123"),
	("lisa", "Lisa Mattsson", "lisa123");

INSERT INTO theaters (theater_name, capacity)
VALUES	("Filmstaden", 127),
	("Kino", 52),
	("Folkets Bio i Lund Södran", 50);
