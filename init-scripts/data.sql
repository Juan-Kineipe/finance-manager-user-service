CREATE TABLE IF NOT EXISTS users (
    id BIGINT(20) NOT NULL AUTO_INCREMENT,
    user_name VARCHAR(255) DEFAULT NULL,
    first_name VARCHAR(255) DEFAULT NULL,
    last_name VARCHAR(255) DEFAULT NULL,
    password VARCHAR(255) DEFAULT NULL,
    account_non_expired BIT(1) DEFAULT NULL,
    account_non_locked BIT(1) DEFAULT NULL,
    credentials_non_expired BIT(1) DEFAULT NULL,
    enabled BIT(1) DEFAULT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY uk_user_name (user_name)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS permission (
    id BIGINT(20) NOT NULL AUTO_INCREMENT,
    description VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (id)
) ENGINE=InnoDB;

CREATE TABLE IF NOT EXISTS user_permission (
    id_user BIGINT(20) NOT NULL,
    id_permission BIGINT(20) NOT NULL,
    PRIMARY KEY (id_user, id_permission),
    KEY fk_user_permission_permission (id_permission),
    CONSTRAINT fk_user_permission FOREIGN KEY (id_user) REFERENCES users (id),
    CONSTRAINT fk_user_permission_permission FOREIGN KEY (id_permission) REFERENCES permission (id)
) ENGINE=InnoDB;

INSERT INTO users (user_name, first_name, last_name, password, account_non_expired, account_non_locked, credentials_non_expired, enabled) VALUES
    ('juan', 'Juan', 'Test', '$2a$10$8791PPc0iYD36p.lcX.J1OZXXnAjhE28u1SSf3gJ/Od3xssgocgUO', true, true, true, true),
    ('gaby', 'Gaby', 'Test', '$2a$10$TZQDxz3E0wfost0LC7knQ.Ghog/mxjx3UjG2vob.S0mPbiBlNAIZW', true, true, true, true);

INSERT INTO permission (description) VALUES ('ADMIN'), ('USER');

INSERT INTO user_permission (id_user, id_permission) VALUES (1, 1), (1, 2), (2, 2);