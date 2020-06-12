CREATE TABLE IF NOT EXISTS ranks(
    id BIGINT(19) AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    rate DECIMAL(10,2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO ranks (name, rate) VALUES
( 'Associate', '100.00'),
('Junior Partner', '200.00'),
('Partner', '500.00'),
('Senior Partner', '1000.00'),
('Managing Partner', '9000.00');
