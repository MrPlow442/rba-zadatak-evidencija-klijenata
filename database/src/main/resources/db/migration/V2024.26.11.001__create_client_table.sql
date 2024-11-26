CREATE TABLE client (
    id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    national_identifier VARCHAR(50) NOT NULL,
    status VARCHAR(50) NOT NULL
);