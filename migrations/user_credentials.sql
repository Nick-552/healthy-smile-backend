--liquibase formatted sql

--changeset Nick-552:1
CREATE TABLE IF NOT EXISTS user_credentials
(
    id              BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    username        VARCHAR(255)                    UNIQUE NOT NULL,
    password        VARCHAR(255)                           NOT NULL,
    category        VARCHAR(255)                           NOT NULL
);
CREATE TABLE IF NOT EXISTS doctors
(

)
--rollback drop table user_credentials