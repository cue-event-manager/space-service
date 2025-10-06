CREATE TABLE space_status (
                              id BIGINT PRIMARY KEY AUTO_INCREMENT,
                              name VARCHAR(100) NOT NULL,
                              description VARCHAR(255),
                              can_be_reserved BOOLEAN DEFAULT TRUE,
                              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
