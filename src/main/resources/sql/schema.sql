CREATE TABLE products (
    id          VARCHAR(10)    PRIMARY KEY,
    name        VARCHAR(20)    NOT NULL,
    description VARCHAR(200),
    price       DECIMAL(10, 2) NOT NULL,
    model       VARCHAR(10)
);
