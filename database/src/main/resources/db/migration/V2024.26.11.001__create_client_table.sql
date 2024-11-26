CREATE TABLE client (
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    national_identifier VARCHAR(50) NOT NULL,
    status VARCHAR(50) NOT NULL
);