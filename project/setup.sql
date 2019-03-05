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
used_amount INT
);

CREATE TABLE restocks (
buy_amount INT,
buy_date DATE,
buy_time TIME
);

CREATE TABLE pallets (
pallet_id TEXT DEFAULT (lower(hex(randomblop()))),
production_date DATE,
produection_time TIME,
block INT
);

CREATE TABLE deliveries (
delivery_date DATE,
delivery_time TIME
);

CREATE TABLE customers (
name TEXT,
address TEXT
);

CREATE TABLE orders (
due_date DATE,
due_time TIME
);


