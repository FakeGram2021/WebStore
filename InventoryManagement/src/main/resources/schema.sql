CREATE TABLE IF NOT EXISTS ADMIN(
    id UUID,
    email VARCHAR,
    username VARCHAR,
    password VARCHAR,
    PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS ARTICLE(
    id UUID,
    name VARCHAR,
    description VARCHAR,
    price NUMERIC,
    amount_in_stock INT,
    image_url VARCHAR,
    version BIGINT,
    PRIMARY KEY (id)
);