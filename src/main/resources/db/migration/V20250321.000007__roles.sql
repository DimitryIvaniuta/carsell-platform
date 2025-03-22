CREATE TABLE user_roles
(
    user_id BIGINT REFERENCES USERS (ID) ON DELETE CASCADE,
    role    VARCHAR(10) NOT NULL,
    PRIMARY KEY (user_id, role)
);