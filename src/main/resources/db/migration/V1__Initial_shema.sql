CREATE SCHEMA mcr;

CREATE TABLE products(
    id SERIAL PRIMARY KEY,
    title TEXT NOT NULL,
    price DOUBLE PRECISION,
    description TEXT,
    category TEXT
);