CREATE TABLE space_reservation (
                       id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       space_id BIGINT NOT NULL,
                       event_id BIGINT NOT NULL,
                       date DATE NOT NULL,
                       start_time TIME NOT NULL,
                       end_time TIME NOT NULL,
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                       CONSTRAINT fk_space FOREIGN KEY (space_id) REFERENCES space(id)
);
