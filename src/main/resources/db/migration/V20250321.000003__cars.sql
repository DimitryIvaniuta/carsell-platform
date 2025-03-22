CREATE TABLE cars
(
    id                  bigint           not null primary key,
    make                VARCHAR(100)     NOT NULL,
    model               VARCHAR(100)     NOT NULL,
    year                INTEGER          NOT NULL,
    price               NUMERIC(19, 2)   NOT NULL,
    description         TEXT,
    seller_id           BIGINT REFERENCES USERS (ID) ON DELETE CASCADE,
    discount_percentage NUMERIC(5, 2),
    discount_amount     NUMERIC(19, 2) GENERATED ALWAYS AS (
        price * (COALESCE(discount_percentage, 0) / 100)
        ) STORED,
    car_type            VARCHAR(20)      NOT NULL,
    trunk_capacity      DOUBLE PRECISION NOT NULL DEFAULT 0,
    four_wheel_drive    BOOLEAN,
    payload_capacity    DOUBLE PRECISION NOT NULL DEFAULT 0,
    CONSTRAINT fk_seller FOREIGN KEY (seller_id)
        REFERENCES users (id)
);