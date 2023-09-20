DROP TABLE IF EXISTS users CASCADE;



CREATE TABLE IF NOT EXISTS users
(
    id          BIGSERIAL PRIMARY KEY,
    email       VARCHAR(255) NOT NULL,
    username    VARCHAR(255) NOT NULL,
    password    VARCHAR(255) NOT NULL,
    phoneNumber VARCHAR(255) NULL,
    role        VARCHAR(255) NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx ON users (email);