create table if not exists organisation(
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255),
    bullstat VARCHAR(255),
    user_id BIGINT,
    address VARCHAR(255),
    website VARCHAR(255),
    facebook_link VARCHAR(255),
    charity_option VARCHAR(255),
    organisation_purpose VARCHAR(2000),
    registered_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES user(id)
);

create table if not exists organisation_priority (
                                       id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                       category VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS priority_id_organisation_id (
    organisation_id BIGINT,
    organisation_priority_id BIGINT,
    FOREIGN KEY (organisation_id) REFERENCES organisation(id),
    FOREIGN KEY (organisation_priority_id) REFERENCES organisation_priority(id),
    PRIMARY KEY (organisation_id, organisation_priority_id)
);
