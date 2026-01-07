ALTER TABLE products
ADD provider_id INT,
ADD provider_product_id INT,
ADD active BOOLEAN;

CREATE TABLE providers(
  id SERIAL PRIMARY KEY,
  name TEXT NOT NULL
);

INSERT INTO providers (name)
VALUES ('Java Microservice Inc'),
       ('Fake Provider');


