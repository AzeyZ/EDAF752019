-- Delete tables if they exist
PRAGMA foreign_keys=OFF;

DROP TABLE IF EXISTS customers;

PRAGMA foreign_keys=ON;

-- Create tables
CREATE TABLE products (
product_name TEXT PRIMARY KEY
);
