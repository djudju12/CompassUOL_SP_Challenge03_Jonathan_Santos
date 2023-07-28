CREATE DATABASE challenge3_products;

\c challenge3_products;

CREATE TABLE products (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    price MONEY,
    description TEXT
);