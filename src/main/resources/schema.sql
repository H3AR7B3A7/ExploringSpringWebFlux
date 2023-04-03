CREATE TABLE IF NOT EXISTS product
(
    id   SERIAL PRIMARY KEY,
    product_id VARCHAR(36),
    name VARCHAR(64) NOT NULL
);

CREATE TABLE IF NOT EXISTS rating
(
    id   SERIAL PRIMARY KEY,
    product_id LONG NOT NULL,
    rating INT NOT NULL
);
