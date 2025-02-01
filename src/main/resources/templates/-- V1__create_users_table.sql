
CREATE TABLE users (
                       id UUID PRIMARY KEY,
                       email VARCHAR(255) UNIQUE NOT NULL,
                       password_hash VARCHAR(100) NOT NULL,
                       first_name VARCHAR(50),
                       last_name VARCHAR(50),
                       birth_date DATE
);