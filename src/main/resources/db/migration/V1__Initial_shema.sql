CREATE SCHEMA mcr;

CREATE TABLE products(
    id SERIAL PRIMARY KEY,
    title TEXT NOT NULL,
    price NUMERIC(10,2),
    description TEXT,
    category TEXT
);

