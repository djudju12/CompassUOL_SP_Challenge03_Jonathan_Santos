CREATE DATABASE challenge3_products;

\c challenge3_products;

CREATE TABLE products (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL,
    price MONEY NOT NULL ,
    description TEXT
);