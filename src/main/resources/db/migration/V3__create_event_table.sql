create table if not exists event(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    description TEXT,
    address VARCHAR(255),
    event_categories VARCHAR(255),
    organisation_id BIGINT,
    is_online BOOLEAN,
    is_one_time BOOLEAN DEFAULT TRUE,
    price DOUBLE(10,2) DEFAULT 0.0,
    min_age INTEGER DEFAULT 0,
    max_age INTEGER DEFAULT 0,
    recurrence_details VARCHAR(255) DEFAULT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    starts_at TIMESTAMP,
    ends_at TIMESTAMP,
    FOREIGN KEY (organisation_id) REFERENCES organisation (id)
);