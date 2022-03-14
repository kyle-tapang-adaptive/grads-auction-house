CREATE TABLE auction_user
(
    id           SERIAL PRIMARY KEY,
    username     VARCHAR(50) UNIQUE NOT NULL,
    password     VARCHAR(50)        NOT NULL,
    is_admin     BOOLEAN            NOT NULL,
    first_name   VARCHAR(50)        NOT NULL,
    last_name    VARCHAR(50)        NOT NULL,
    organisation VARCHAR(50)        NOT NULL,
    blocked      BOOLEAN            NOT NUll
);

CREATE TABLE auction_lot
(
    id                  SERIAL PRIMARY KEY,
    owner               VARCHAR(50) NOT NULL REFERENCES auction_user (username),
    symbol              VARCHAR(50) NOT NULL,
    min_price           NUMERIC     NOT NULL,
    quantity            INT         NOT NULL,
    status              VARCHAR(50) NOT NULL,
    total_sold_quantity INT,
    total_revenue       NUMERIC,
    closing_time        TIMESTAMP
);

CREATE TABLE bid
(
    id             SERIAL PRIMARY KEY,
    auction_lot_id INT         NOT NULL REFERENCES auction_lot (id),
    username       VARCHAR(50) NOT NULL REFERENCES auction_user (username),
    quantity       INT         NOT NULL,
    price          NUMERIC     NOT NULL,
    state          VARCHAR(50) NOT NULL,
    win_quantity   INT
);

INSERT INTO auction_user (username, password, is_admin, first_name, last_name, organisation, blocked)
VALUES ('ADMIN', 'adminpassword', TRUE, 'admin', 'admin', 'Adaptive', FALSE);