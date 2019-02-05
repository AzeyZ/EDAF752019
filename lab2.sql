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
