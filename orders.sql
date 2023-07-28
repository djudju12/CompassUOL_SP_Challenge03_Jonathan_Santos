CREATE DATABASE challenge3_orders;

\c challenge3_orders;

CREATE TABLE address (
    id SERIAL PRIMARY KEY,
    zip_code VARCHAR(255) NOT NULL,
    street VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    state VARCHAR(255) NOT NULL,
    number VARCHAR(255) NOT NULL,
    detailed_address VARCHAR(255),
    district VARCHAR(255)
);

CREATE TABLE orders (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    delivery_address_id INT,
    status VARCHAR(255) NOT NULL DEFAULT 'PEDINGING',
    foreign key (delivery_address_id) references address(id)
);

CREATE TABLE ordered_product (
    id SERIAL PRIMARY KEY,
    order_id INT NOT NULL,
    product_id INT NOT NULL,
    foreign key (order_id) references orders(id)
);