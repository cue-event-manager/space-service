CREATE TABLE space_status (
                              id BIGINT PRIMARY KEY AUTO_INCREMENT,
                              name VARCHAR(100) NOT NULL,
                              description VARCHAR(255),
                              can_be_reserved BOOLEAN DEFAULT TRUE,
                              deleted boolean not null default false,
                              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
