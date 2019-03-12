-- Delete tables if they exist
PRAGMA foreign_keys=OFF;

DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS materials;
DROP TABLE IF EXISTS used_materials;
DROP TABLE IF EXISTS restocks;
DROP TABLE IF EXISTS pallets;
DROP TABLE IF EXISTS deliveries;
DROP TABLE IF EXISTS customers;
DROP TABLE IF EXISTS orders;

PRAGMA foreign_keys=ON;

-- Create tables
CREATE TABLE products (
product_name TEXT PRIMARY KEY
);

CREATE TABLE materials (
ingredient TEXT PRIMARY KEY,
amount INT
);

CREATE TABLE used_materials (
used_amount INT,
-- Unsure about the keys, feel free to change
FOREIGN KEY (ingredient) REFERENCES materials (ingredient),
PRIMARY KEY (used_amount, ingredient)
);

CREATE TABLE restocks (
buy_amount INT,
buy_date DATE,
buy_time TIME,
-- Unsure about the keys, feel free to change
FOREIGN KEY (ingredient) REFERENCES materials (ingredient),
PRIMARY KEY (ingredient, buy_amount, buy_date, buy_time)
);

CREATE TABLE pallets (
pallet_id TEXT DEFAULT (lower(hex(randomblob()))),
production_date DATE,
produection_time TIME,
block INT,
PRIMARY KEY (pallet_id)
);

CREATE TABLE deliveries (
delivery_date DATE,
delivery_time TIME,
-- Unsure about the keys, feel free to change
-- Might want to add delivery id?
FOREIGN KEY (pallet_id) REFERENCES pallets (pallet_id),
FOREIGN KEY (customer_name) REFERENCES customers (name),
PRIMARY KEY (delivery_date, delivery_time, pallet_id, customer_name)
);

CREATE TABLE customers (
name TEXT PRIMARY KEY,
address TEXT
);

CREATE TABLE orders (
due_date DATE,
due_time TIME,
-- Unsure about the keys, feel free to change
FOREIGN KEY (product_name) REFERENCES products (product_name),
FOREIGN KEY (customer_name) REFERENCES customers (name),
PRIMARY KEY (due_date, due_time, product_name, customer_name)
);


