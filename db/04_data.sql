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