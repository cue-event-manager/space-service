CREATE TABLE space (
                       id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       name VARCHAR(100) NOT NULL,
                       campus_id BIGINT NOT NULL,
                       status_id BIGINT NOT NULL,
                       type_id BIGINT NOT NULL,
                       capacity INT,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                       CONSTRAINT fk_space_campus FOREIGN KEY (campus_id) REFERENCES campus(id),
                       CONSTRAINT fk_space_status FOREIGN KEY (status_id) REFERENCES space_status(id),
                       CONSTRAINT fk_space_type FOREIGN KEY (type_id) REFERENCES space_type(id)
);
