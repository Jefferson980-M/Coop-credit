CREATE TABLE affiliates (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT UNIQUE,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    document VARCHAR(255) NOT NULL UNIQUE,
    salary DOUBLE PRECISION NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP
);

CREATE TABLE risk_evaluations (
    id BIGSERIAL PRIMARY KEY,
    score INTEGER NOT NULL,
    risk_level VARCHAR(50),
    recommendation VARCHAR(50),
    detail VARCHAR(255)
);

CREATE TABLE credit_applications (
    id BIGSERIAL PRIMARY KEY,
    affiliate_id BIGINT NOT NULL,
    risk_evaluation_id BIGINT,
    amount DOUBLE PRECISION NOT NULL,
    term INTEGER NOT NULL,
    rate DOUBLE PRECISION, -- Tasa propuesta
    purpose VARCHAR(255),
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL
);

CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    role VARCHAR(50) NOT NULL
);
