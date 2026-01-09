-- Envers revision info table
CREATE TABLE revinfo (
     rev INTEGER NOT NULL,
     revtstmp BIGINT,
     PRIMARY KEY (rev)
);

-- Sequence for revision numbers
CREATE SEQUENCE revinfo_seq START WITH 1 INCREMENT BY 50;

-- Products audit table
CREATE TABLE products_aud (
      id INTEGER NOT NULL,
      rev INTEGER NOT NULL,
      revtype SMALLINT,
      title TEXT,
      price NUMERIC(10,2),
      description TEXT,
      category TEXT,
      provider_id INTEGER,
      provider_product_id INTEGER,
      active BOOLEAN,
      PRIMARY KEY (id, rev),
      FOREIGN KEY (rev) REFERENCES revinfo (rev)
);
