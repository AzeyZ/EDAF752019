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
-- Unsure about the keys, feel free to change!
CREATE TABLE products (
product_name TEXT PRIMARY KEY
);

CREATE TABLE materials (
ingredient TEXT PRIMARY KEY,
amount INT,
-- Added unit to match rest-API description
unit TEXT
);

CREATE TABLE used_materials (
used_amount INT,
ingredient TEXT,
product_name TEXT,
unit TEXT,
FOREIGN KEY (unit) REFERENCES materials (unit),
FOREIGN KEY (ingredient) REFERENCES materials (ingredient),
FOREIGN KEY (product_name) REFERENCES products (product_name),
PRIMARY KEY (used_amount, ingredient, product_name)
);

CREATE TABLE restocks (
buy_amount INT,
buy_date TEXT,
buy_time TEXT,
ingredient TEXT,
FOREIGN KEY (ingredient) REFERENCES materials (ingredient),
PRIMARY KEY (ingredient, buy_amount, buy_date, buy_time)
);

CREATE TABLE pallets (
pallet_id TEXT DEFAULT (lower(hex(randomblob()))),
production_date TEXT,
produection_time TEXT,
blocked BOOLEAN,
product_name TEXT,
customer_name TEXT,
delivery_date TEXT,
delivery_time TEXT,
FOREIGN KEY (customer_name) REFERENCES customers (name),
FOREIGN KEY (product_name) REFERENCES products (product_name),
FOREIGN KEY (delivery_date) REFERENCES delivieries (delivery_date),
FOREIGN KEY (delivery_time) REFERENCES deliveries (delivery_time),
PRIMARY KEY (pallet_id)
);

CREATE TABLE deliveries (
delivery_date TEXT,
delivery_time TEXT,
customer_name TEXT,
pallet_id TEXT,
-- Might want to add delivery id?
FOREIGN KEY (customer_name) REFERENCES customers (name),
FOREIGN KEY (pallet_id) REFERENCES pallets (pallet_id),
PRIMARY KEY (delivery_date, delivery_time, pallet_id, customer_name)
);

CREATE TABLE customers (
customer_name TEXT PRIMARY KEY,
address TEXT
);

CREATE TABLE orders (
due_date TEXT,
due_time TEXT,
product_name TEXT,
customer_name TEXT,
FOREIGN KEY (product_name) REFERENCES products (product_name),
FOREIGN KEY (customer_name) REFERENCES customers (name),
PRIMARY KEY (due_date, due_time, product_name, customer_name)
);


