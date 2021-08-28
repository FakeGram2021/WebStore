
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

CREATE TABLE IF NOT EXISTS CUSTOMER_ORDER(
    id UUID NOT NULL,
    creation_time TIMESTAMP,
    address VARCHAR,
    city VARCHAR,
    country VARCHAR,
    email VARCHAR,
    first_name VARCHAR,
    last_name VARCHAR,
    phone VARCHAR,
    zipcode VARCHAR,
    price NUMERIC(19,2) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS ORDER_ITEM(
    id UUID NOT NULL,
    quantity INT NOT NULL,
    article_id UUID,
    PRIMARY KEY(id),
    FOREIGN KEY (article_id) REFERENCES ARTICLE(id)
);

CREATE TABLE IF NOT EXISTS CUSTOMER_ORDER_ITEMS(
    customer_order_id UUID,
    items_id UUID,
    UNIQUE (items_id),
    FOREIGN KEY (items_id) REFERENCES ORDER_ITEM(id),
    FOREIGN KEY (customer_order_id) REFERENCES CUSTOMER_ORDER(id)
);