CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NULL,
    firstname VARCHAR(100) NULL,
    surname VARCHAR(100) NULL,
    avatar VARCHAR(100) NULL,
    active INT NOT NULL
);

CREATE TABLE IF NOT EXISTS roles
(
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    active INT NOT NULL
);

CREATE TABLE IF NOT EXISTS users_roles
(
    users_id INT NOT NULL,
    roles INT NOT NULL,
    FOREIGN KEY (users_id) REFERENCES users (id)
);
