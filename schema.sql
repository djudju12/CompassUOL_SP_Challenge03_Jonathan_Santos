-- Users
CREATE TABLE IF NOT EXISTS users
(
    id         SERIAL PRIMARY KEY,
    first_name varchar(255) NOT NULL,
    last_name  varchar(255) NOT NULL,
    username   varchar(255) NOT NULL,
    password   varchar(255) NOT NULL
);

-- Products
CREATE TABLE IF NOT EXISTS products
(
    id          SERIAL PRIMARY KEY,
    name        varchar(255) NOT NULL,
    price       money        NOT NULL,
    description varchar(255) NOT NULL
);

-- Address
CREATE TABLE IF NOT EXISTS address
(
    id              SERIAL PRIMARY KEY,
    zip             varchar(32)  NOT NULL,
    street          varchar(255) NOT NULL,
    city            varchar(255) NOT NULL,
    state           varchar(32)  NOT NULL,
    district        varchar(32)  NOT NULL,
    address_details varchar(255) NOT NULL
);

-- Orders
CREATE TABLE IF NOT EXISTS orders
(
    id         SERIAL PRIMARY KEY,
    user_id    int          NOT NULL,
    quantity   int          NOT NULL,
    status     varchar(255) NOT NULL,
    address_id int          NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (address_id) REFERENCES address (id)
);

-- Order Items
CREATE TABLE IF NOT EXISTS order_items
(
    id         SERIAL PRIMARY KEY,
    order_id   int NOT NULL,
    product_id int NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders (id),
    FOREIGN KEY (product_id) REFERENCES products (id)
);


