CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    firstname VARCHAR(100) NULL,
    surname VARCHAR(100) NULL,
    avatar VARCHAR(100) NULL,
    active INT NOT NULL
);

CREATE TABLE IF NOT EXISTS forgotpassword_keys ( 
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    key VARCHAR(4) NOT NULL,
    created_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS forgotpassword_questions ( 
    id SERIAL PRIMARY KEY,
    question VARCHAR(255) NOT NULL,
    active INT NOT NULL
);

CREATE TABLE IF NOT EXISTS forgotpassword_questions_users_answers ( 
    id SERIAL PRIMARY KEY,
    forgotpassword_questions_id INT NOT NULL,
    users_id INT NOT NULL,
    answer VARCHAR(255) NOT NULL,
    FOREIGN KEY (forgotpassword_questions_id) REFERENCES forgotpassword_questions (id),
    FOREIGN KEY (users_id) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS roles (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    active INT NOT NULL
);

CREATE TABLE IF NOT EXISTS users_roles ( 
    users_id INT NOT NULL,
    roles INT NOT NULL,
    FOREIGN KEY (users_id) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS users_rolesprovider_activationkey ( 
    id SERIAL PRIMARY KEY,
    users_id INT NOT NULL,
    roles_id INT NOT NULL,
    activationkey uuid NOT NULL,
    FOREIGN KEY (users_id) REFERENCES users (id),
    FOREIGN KEY (roles_id) REFERENCES roles (id)
);

CREATE TABLE IF NOT EXISTS educemaden_organizations (
    id INT NOT NULL,
    active VARCHAR(20) NULL,
    name VARCHAR(255) NOT NULL,
    creation_date varchar(50) NULL,
    inep_code varchar(100) NULL,
    phone varchar(100) NOT NULL,
    type varchar(100) NULL,
    website varchar(255) NULL,
    login varchar(50) NULL,
    address varchar(50) NULL,
    responsible varchar(50) NULL
);

CREATE TABLE IF NOT EXISTS users_educemaden_organizations ( 
    id SERIAL PRIMARY KEY,
    users_id INT NOT NULL,
    educemaden_organizations_id INT NOT NULL,
    activationkey uuid NOT NULL,
    FOREIGN KEY (users_id) REFERENCES users (id)
);
