CREATE TABLE space_resources (
                                space_id BIGINT NOT NULL,
                                resource_id BIGINT NOT NULL,
                                PRIMARY KEY (space_id, resource_id),
                                CONSTRAINT fk_resource_space FOREIGN KEY (space_id) REFERENCES space(id),
                                CONSTRAINT fk_resource_resource FOREIGN KEY (resource_id) REFERENCES space_resource(id)
);
