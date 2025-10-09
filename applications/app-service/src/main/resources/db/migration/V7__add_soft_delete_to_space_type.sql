ALTER TABLE space_type
ADD COLUMN deleted_at TIMESTAMP NULL;

ALTER TABLE space_type
ADD CONSTRAINT unique_name_not_deleted UNIQUE (name);
