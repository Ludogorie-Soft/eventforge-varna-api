create table if not exists image(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    url VARCHAR(255),
    upload_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    type VARCHAR(255),
    event_id BIGINT,
    organisation_id BIGINT,
    FOREIGN KEY (event_id) REFERENCES event (id),
    FOREIGN KEY (organisation_id) REFERENCES organisation (id)
);
