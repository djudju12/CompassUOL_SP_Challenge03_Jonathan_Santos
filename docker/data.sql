-- Users
\c challenge3_auth;
INSERT INTO users(username, password, first_name, last_name)
VALUES ('admin', '$2a$12$NXu6EixieWDzOuUbhI2gZ.PWI60nB7G3Fr6Na36vKUkruI/15bas6', 'jonathan', 'santos');

-- products
\c challenge3_products;
INSERT INTO products(name, description, price)
VALUES ('product 1', 'description 1', 10.00);
INSERT INTO products(name, description, price)
VALUES ('product 2', 'description 2', 20.00);
INSERT INTO products(name, description, price)
VALUES ('product 3', 'description 3', 30.00);
INSERT INTO products(name, description, price)
VALUES ('product 4', 'description 4', 40.00);
INSERT INTO products(name, description, price)
VALUES ('product 5', 'description 5', 50.00);

-- orders
\c challenge3_orders;
INSERT INTO orders(delivery_address_id, status, user_id)
VALUES (1, 'PENDING', 1);
INSERT INTO orders(delivery_address_id, status, user_id)
VALUES (1, 'PENDING', 1);
INSERT INTO orders(delivery_address_id, status, user_id)
VALUES (1, 'PENDING', 1);
INSERT INTO orders(delivery_address_id, status, user_id)
VALUES (1, 'PENDING', 1);
-- order_items
INSERT INTO ordered_product(order_id, product_id)
VALUES (2, 1);
INSERT INTO ordered_product(order_id, product_id)
VALUES (2, 2);
INSERT INTO ordered_product(order_id, product_id)
VALUES (2, 3);
INSERT INTO ordered_product(order_id, product_id)
VALUES (2, 4);
INSERT INTO ordered_product(order_id, product_id)
VALUES (3, 5);
INSERT INTO ordered_product(order_id, product_id)
VALUES (4, 1);

-- addresses
INSERT INTO address(zip_code, street, city, state, number, detailed_address, district)
VALUES ('96825750','Rua Jacarandá','Santa Cruz do Sul','RS','321','','Monte Verde');
