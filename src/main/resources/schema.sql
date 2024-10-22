CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- DROP TABLE users;

CREATE TABLE IF NOT EXISTS users (
    id UUID DEFAULT uuid_generate_v4(),
    login VARCHAR(512),
    email VARCHAR(512),
    password VARCHAR(512),
    name VARCHAR(512),
    about TEXT,
    avatar VARCHAR(512),
    CONSTRAINT PK_USER PRIMARY KEY (id),
    CONSTRAINT UQ_USER_LOGIN UNIQUE (login),
    CONSTRAINT UQ_USER_EMAIL UNIQUE (email)
);